/*
 * =============================================================================
 * This source code is a part of TatukGIS Developer Kernel.
 * =============================================================================
 *
 * Classification sample (Java / Swing edition) — demonstrates thematic data
 * classification of vector and raster (pixel) layers using the TatukGIS JDK
 * (Java Developer Kernel) TGIS_ClassificationAbstract API.
 *
 * Key concepts shown:
 *   - Loading a layer with TGIS_ViewerWnd.Open
 *   - Creating a classifier with TGIS_ClassificationFactory.CreateClassifier,
 *     which returns the appropriate subclass for the layer type
 *     (TGIS_ClassificationVector or TGIS_ClassificationPixel)
 *   - Setting the classification target field (numeric attribute or band index)
 *   - Choosing a classification method from TGIS_ClassificationMethod:
 *       DefinedInterval, EqualInterval, GeometricalInterval, Manual,
 *       NaturalBreaks, KMeans, KMeansSpatial, Quantile, Quartile,
 *       StandardDeviation, StandardDeviationWithCentral, Unique
 *   - Controlling the visual output via:
 *       StartColor / EndColor           — color gradient endpoints
 *       NumClasses                      — number of class breaks (auto for some)
 *       Interval                        — interval size (DefinedInterval/StdDev)
 *       ColorRamp / ColorRampName       — named palette from GisColorRampList
 *       ColorRamp.DefaultColorMapMode   — Continuous or Discrete color mapping
 *       ColorRamp.DefaultReverse        — reverse the ramp direction
 *   - Vector-specific properties via TGIS_ClassificationVector:
 *       RenderType      — Color, Size, OutlineWidth, or OutlineColor
 *       StartSize / EndSize — symbol size range for the Size render type
 *       ClassIdField    — optional field to store the class ID per feature
 *   - Managing layer statistics:
 *       MustCalculateStatistics — check whether stats are needed
 *       layer.getStatistics().Calculate — explicit statistics computation
 *   - Adding the classification result to TGIS_ControlLegend
 *   - Refreshing the map with GIS.InvalidateWholeMap
 *
 * The sample opens the California Counties shapefile from the TatukGIS sample
 * dataset by default, but allows the user to open any supported file.
 * =============================================================================
 */
package Classification;

import java.awt.Color;
import java.awt.Toolkit;
import java.io.File;
import java.util.regex.Pattern;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.JTextComponent;
import tatukgis.jdk.TGIS_ClassificationAbstract;
import tatukgis.jdk.TGIS_ClassificationFactory;
import tatukgis.jdk.TGIS_ClassificationMethod;
import tatukgis.jdk.TGIS_ClassificationRenderType;
import tatukgis.jdk.TGIS_ClassificationVector;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_ColorMapMode;
import tatukgis.jdk.TGIS_ColorRampNames;
import tatukgis.jdk.TGIS_FieldInfo;
import tatukgis.jdk.TGIS_FieldType;
import tatukgis.jdk.TGIS_FileType;
import tatukgis.jdk.TGIS_Layer;
import tatukgis.jdk.TGIS_LayerPixel;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_ShapeType;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.jdk.__Global;

/**
 * Main Swing frame for the Classification sample.
 * <p>
 * Hosts a {@code TGIS_ViewerWnd} map component, a {@code TGIS_ControlLegend}
 * panel, and toolbar rows for selecting the classification field, method,
 * render type, class count, colors, and color-ramp settings.  Any change to
 * any control immediately invokes {@link #doClassify()} so the map updates
 * in real time.
 * </p>
 */
public class ClassificationUI extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    // -----------------------------------------------------------------------
    // Classification method display-string constants.
    // These match the items in cmbMethod and are compared against the selected
    // text to set TGIS_ClassificationMethod enum values.
    // -----------------------------------------------------------------------
    private final String RENDER_TYPE_SIZE          = "Size / Width";    // Vary symbol size by class
    private final String RENDER_TYPE_COLOR         = "Color";            // Vary fill color by class
    private final String RENDER_TYPE_OUTLINE_WIDTH = "Outline width";    // Vary outline width by class
    private final String RENDER_TYPE_OUTLINE_COLOR = "Outline color";    // Vary outline color by class

    // Standard deviation interval fraction display strings
    private final String STD_INTERVAL_ONE         = "1 STDEV";
    private final String STD_INTERVAL_ONE_HALF    = "1/2 STDEV";
    private final String STD_INTERVAL_ONE_THIRD   = "1/3 STDEV";
    private final String STD_INTERVAL_ONE_QUARTER = "1/4 STDEV";

    // Classification method display strings (must match cmbMethod items exactly)
    private final String GIS_CLASSIFY_METHOD_DI  = "Defined Interval";                 // Fixed-width interval classes
    private final String GIS_CLASSIFY_METHOD_EI  = "Equal Interval";                   // Equal data-range intervals
    private final String GIS_CLASSIFY_METHOD_GI  = "Geometrical Interval";             // Geometrically progressing breaks
    private final String GIS_CLASSIFY_METHOD_MN  = "Manual";                           // User-specified break values
    private final String GIS_CLASSIFY_METHOD_NB  = "Natural Breaks";                   // Jenks natural breaks
    private final String GIS_CLASSIFY_METHOD_KM  = "K-Means";                          // K-means clustering
    private final String GIS_CLASSIFY_METHOD_KMS = "K-Means Spatial";                  // Spatially aware K-means
    private final String GIS_CLASSIFY_METHOD_QN  = "Quantile";                         // Equal-count quantile classes
    private final String GIS_CLASSIFY_METHOD_QR  = "Quartile";                         // Four quartile classes
    private final String GIS_CLASSIFY_METHOD_SD  = "Standard Deviation";               // Std-dev symmetric classes
    private final String GIS_CLASSIFY_METHOD_SDC = "Standard Deviation with Central";  // Std-dev with centre class
    private final String GIS_CLASSIFY_METHOD_UNQ = "Unique";                           // One class per unique value

    /**
     * Constructs the frame: initialises the Swing components generated by the
     * NetBeans GUI builder, then performs custom initialisation via
     * {@link #initOwn()}.
     */
    public ClassificationUI() {
        initComponents();
        initOwn();
    }

    /**
     * Custom initialisation performed after the designer-generated components
     * are ready.
     * <p>
     * Sets up the file-open dialog filter, loads the default California Counties
     * shapefile, populates the field and color-ramp pickers, and hides the
     * optional toolbar controls that are only relevant for specific methods.
     * </p>
     */
    private void initOwn(){
        setOpenDialogFilter();

        // Open the default sample layer (California county polygons) and zoom to fit
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/USA/States/California/Counties.shp");

        fillCbFields();      // Populate field picker from the loaded layer
        fillCbColorRamps();  // Populate ramp picker from TGIS_Utils.GisColorRampList

        // Initially hide the optional controls; they are shown per-method in
        // cmbMethodActionPerformed when the user selects a classification method.
        hideClasses();
        hideInterval();
        hideManual();
        hideStdDev();
        setColorRampControlEnabled(false);
    }

    /**
     * Configures the file-open dialog with choosable filters for all file types
     * supported by the registered TatukGIS layer drivers.
     * <p>
     * {@code TGIS_Utils.GisSupportedFiles()} returns a delimited string of
     * format descriptions and extensions; this method splits it and adds a
     * {@link FileFilter} entry for each format.
     * </p>
     */
    private void setOpenDialogFilter(){
        String supportedFiles = TGIS_Utils.GisSupportedFiles(TGIS_FileType.All, false);
        for(String tmp : supportedFiles.split(("_"))){
            dlgOpen.addChoosableFileFilter(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    String tmp2 = "";
                    if(file.isFile()) {
                        tmp2 = getFileExtension(file);
                    }
                    return tmp.contains(tmp2);
                }

                @Override
                public String getDescription() {
                    return tmp.split("-")[0];
                }
            });
        }
    }

    /**
     * Returns the file extension (without the dot) of the given file, or an
     * empty string if the file has no extension.
     *
     * @param file the file whose extension is required
     * @return the lowercase extension string, e.g. {@code "shp"}, or {@code ""}
     */
    private String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".")+1);
        } else {
            return "";
        }
    }

    /**
     * Returns the first layer in the viewer's layer collection, or {@code null}
     * if no layers have been loaded yet.
     * Classification always operates on layer index 0.
     *
     * @return the first {@link TGIS_Layer}, or {@code null}
     */
    private TGIS_Layer getLayer(){
        TGIS_Layer res = null;
        if(GIS.getItems().getCount() > 0){
            res = (TGIS_Layer) GIS.getItems().getItems(0);
        }
        return res;
    }

    /**
     * Populates {@code cmbField} with classifiable fields from the current layer.
     * <p>
     * For vector layers ({@link TGIS_LayerVector}):
     * always includes virtual fields (GIS_UID, GIS_AREA, GIS_LENGTH,
     * GIS_CENTROID_X, GIS_CENTROID_Y) — these are computed on-the-fly by the
     * engine — then adds numeric attribute fields of type
     * {@link TGIS_FieldType#Number} or {@link TGIS_FieldType#Float}.
     * </p>
     * <p>
     * For raster layers ({@link TGIS_LayerPixel}):
     * adds one entry per band (band index as string), since bands are the
     * classifiable "fields" for pixel data.
     * </p>
     */
    private void fillCbFields(){
        TGIS_Layer lyr;
        TGIS_LayerVector lv;
        TGIS_LayerPixel lp;

        cmbField.removeAllItems();

        lyr = getLayer();

        if(lyr instanceof TGIS_LayerVector){
            lv = (TGIS_LayerVector) lyr;

            // Virtual fields — always available; computed on-the-fly by the engine
            cmbField.addItem("GIS_UID");        // Unique feature identifier
            cmbField.addItem("GIS_AREA");       // Feature area (polygon layers)
            cmbField.addItem("GIS_LENGTH");     // Feature perimeter / line length
            cmbField.addItem("GIS_CENTROID_X"); // Centroid X coordinate
            cmbField.addItem("GIS_CENTROID_Y"); // Centroid Y coordinate

            // Add numeric attribute fields from the layer schema
            for(TGIS_FieldInfo field : lv.getFields()){
                switch(field.getFieldType()){
                    case TGIS_FieldType.Number:
                        cmbField.addItem(field.getName());
                        break;
                    case TGIS_FieldType.Float:
                        cmbField.addItem(field.getName());
                        break;
                }
            }
        }else if(lyr instanceof TGIS_LayerPixel){
            // For raster layers each band index acts as a classifiable "field"
            lp = (TGIS_LayerPixel) lyr;
            for(int i = 0; i < lp.getBandsCount(); i++){
                cmbField.addItem(String.valueOf(i));
            }
        }

        cmbField.setSelectedIndex(0);
    }

    /**
     * Enables or disables all color-ramp sub-controls as a unit.
     * Called when {@code cbxColorRamp} is toggled to keep the ramp-name
     * checkbox, ramp picker, colormap mode, and reverse toggle in sync.
     *
     * @param _enabled {@code true} to enable all ramp sub-controls
     */
    private void setColorRampControlEnabled( boolean _enabled ){
        cbxColorRampName.setEnabled(_enabled);
        cmbColorRamps.setEnabled(_enabled);
        cmbColorMapMode.setEnabled(_enabled);
        cbxReverse.setEnabled(_enabled);
    }

    /**
     * Populates {@code cmbColorRamps} from the global
     * {@code TGIS_Utils.getGisColorRampList()} registry.
     * Pre-selects the "GreenBlue" ramp as the default.
     */
    private void fillCbColorRamps(){
        String ramp_name;

        for(int i = 0; i < TGIS_Utils.getGisColorRampList().getCount(); i++){
            ramp_name = TGIS_Utils.getGisColorRampList().getItems(i).getName();
            cmbColorRamps.addItem(ramp_name);

            // Pre-select GreenBlue as the default ramp
            if("GreenBlue".equals(ramp_name)){
                cmbColorRamps.setSelectedIndex(i);
            }
        }
    }

    /**
     * Core classification routine — called whenever any classification parameter
     * changes.  Assembles a fully configured {@link TGIS_ClassificationAbstract}
     * (or its {@link TGIS_ClassificationVector} subclass) and calls
     * {@code Classify()} to apply the result to the layer's rendering parameters.
     * <p>
     * Workflow:
     * <ol>
     *   <li>Validate that a method is selected and a layer is loaded.</li>
     *   <li>Optionally add a ClassIdField to the vector layer schema.</li>
     *   <li>Create a classifier via
     *       {@link TGIS_ClassificationFactory#CreateClassifier}.</li>
     *   <li>Set common properties: Target, NumClasses, StartColor, EndColor,
     *       ShowLegend, Method, Interval, manual break points, ColorRamp.</li>
     *   <li>Set vector-only properties: StartSize, EndSize, ClassIdField,
     *       RenderType.</li>
     *   <li>Check statistics: prompt the user if stats are required.</li>
     *   <li>Call {@code classifier.Classify()} to write rendering parameters.</li>
     *   <li>Call {@code GIS.InvalidateWholeMap()} to repaint the map.</li>
     * </ol>
     * </p>
     */
    private void doClassify(){
        TGIS_Layer lyr;
        TGIS_LayerVector lv = null;
        String method;
        String render_type;
        String std_interval;
        String class_id_field = "";
        Boolean create_field;
        TGIS_ClassificationAbstract classifier;
        TGIS_ClassificationVector classifier_vec;
        int colormap_mode;
        String[] class_breaks_arr;
        double class_breaks_val;
        String ramp_name;

        // Only proceed if the user has actually selected a method
        if(cmbMethod.getSelectedIndex() <= 0) {
            return;
        }

        create_field = false;
        lyr = getLayer();

        if(lyr == null)
            return;

        if(lyr instanceof TGIS_LayerVector){
            lv = (TGIS_LayerVector) lyr;

            // If a class ID field name is provided, add it to the layer schema
            // (if it does not already exist) to store the class index per feature.
            class_id_field = edtClassIdField.getText();
            create_field = class_id_field.length() > 0;
            if(create_field && (lv.FindField(class_id_field) < 0)){
                lv.AddField(class_id_field, TGIS_FieldType.Number, 3, 0);
            }
        }else if(!(lyr instanceof TGIS_LayerPixel)){
            JOptionPane.showMessageDialog(this, String.format("Layer %s is not supported", lyr.getName()));
        }

        // TGIS_ClassificationFactory.CreateClassifier inspects the layer type and
        // returns either TGIS_ClassificationVector or TGIS_ClassificationPixel.
        classifier = TGIS_ClassificationFactory.CreateClassifier(lyr);

        // --- Common properties ---

        // Target: the attribute field name (vector) or band index (pixel) to classify
        classifier.setTarget(cmbField.getSelectedItem().toString());

        // NumClasses is automatically calculated (ignored) for methods that
        // determine their own class count: DefinedInterval, Quartile,
        // StandardDeviation, StandardDeviationWithCentral.
        classifier.setNumClasses(cmbClasses.getSelectedIndex() + 1);

        // Color gradient: StartColor = lowest value, EndColor = highest value
        classifier.setStartColor(TGIS_Color.FromRGB(pStartColor.getBackground().getRGB()));
        classifier.setEndColor(TGIS_Color.FromRGB(pEndColor.getBackground().getRGB()));

        // ShowLegend: whether to populate the layer's legend with class entries
        classifier.setShowLegend(cbxShowInLegend.isSelected());

        // --- Classification method ---
        method = cmbMethod.getSelectedItem().toString();
        switch (method) {
            case GIS_CLASSIFY_METHOD_DI:
                classifier.setMethod(TGIS_ClassificationMethod.DefinedInterval);
                break;
            case GIS_CLASSIFY_METHOD_EI:
                classifier.setMethod(TGIS_ClassificationMethod.EqualInterval);
                break;
            case GIS_CLASSIFY_METHOD_GI:
                classifier.setMethod(TGIS_ClassificationMethod.GeometricalInterval);
                break;
            case GIS_CLASSIFY_METHOD_KM:
                classifier.setMethod(TGIS_ClassificationMethod.KMeans);
                break;
            case GIS_CLASSIFY_METHOD_KMS:
                classifier.setMethod(TGIS_ClassificationMethod.KMeansSpatial);
                break;
            case GIS_CLASSIFY_METHOD_MN:
                classifier.setMethod(TGIS_ClassificationMethod.Manual);
                break;
            case GIS_CLASSIFY_METHOD_NB:
                classifier.setMethod(TGIS_ClassificationMethod.NaturalBreaks);
                break;
            case GIS_CLASSIFY_METHOD_QN:
                classifier.setMethod(TGIS_ClassificationMethod.Quantile);
                break;
            case GIS_CLASSIFY_METHOD_QR:
                classifier.setMethod(TGIS_ClassificationMethod.Quartile);
                break;
            case GIS_CLASSIFY_METHOD_SD:
                classifier.setMethod(TGIS_ClassificationMethod.StandardDeviation);
                break;
            case GIS_CLASSIFY_METHOD_SDC:
                classifier.setMethod(TGIS_ClassificationMethod.StandardDeviationWithCentral);
                break;
            case GIS_CLASSIFY_METHOD_UNQ:
                classifier.setMethod(TGIS_ClassificationMethod.Unique);
                break;
            default:
                classifier.setMethod(TGIS_ClassificationMethod.NaturalBreaks);
                break;
        }

        // --- Interval ---
        // For DefinedInterval: the fixed class width.
        // For StandardDeviation methods: overridden below with a fraction.
        classifier.setInterval(Float.parseFloat(edtInterval.getText()));

        if((GIS_CLASSIFY_METHOD_SD.equals(method)) || (GIS_CLASSIFY_METHOD_SDC.equals(method))){
            // Standard deviation: Interval is a fraction of one standard deviation
            std_interval = cmbInterval.getSelectedItem().toString();
            switch (std_interval) {
                case STD_INTERVAL_ONE:
                    classifier.setInterval(1);
                    break;
                case STD_INTERVAL_ONE_HALF:
                    classifier.setInterval(1/2);
                    break;
                case STD_INTERVAL_ONE_THIRD:
                    classifier.setInterval(1/3);
                    break;
                case STD_INTERVAL_ONE_QUARTER:
                    classifier.setInterval(1/4);
                    break;
                default:
                    classifier.setInterval(1);
                    break;
            }
        }

        // --- Manual class breaks ---
        // Parse comma/semicolon/slash-delimited break values and add each to the
        // classifier via AddClassBreak.
        class_breaks_arr = edtManualBreak.getText().split(",|;|\\/");
        for(String class_breaks_str : class_breaks_arr){
            try{
                class_breaks_val = Float.parseFloat(class_breaks_str);
            }catch( NumberFormatException e ){
                continue;  // Skip values that cannot be parsed as numbers
            }
            classifier.AddClassBreak(class_breaks_val);
        }

        // --- Color ramp ---
        // NumClasses is automatically calculated for: DefinedInterval, Quartile,
        // StandardDeviation(s).
        if(cbxColorRamp.isSelected()){
            // Determine whether to render as smooth gradient or per-class solid blocks
            if(cmbColorMapMode.getSelectedItem() == __Global.GIS_COLORMAPMODE_CONTINUOUS){
                colormap_mode = TGIS_ColorMapMode.Continuous;
            }else{
                colormap_mode = TGIS_ColorMapMode.Discrete;
            }

            // Ramp can be assigned directly (setColorRamp) or by name (setColorRampName).
            // Assigning by name is preferred when serialising to a project file.
            ramp_name = cmbColorRamps.getSelectedItem().toString();
            if(cbxColorRampName.isSelected())
            {
                classifier.setColorRampName(ramp_name);
            }else{
                classifier.setColorRamp(__Global.GisColorRampList().ByName( ramp_name ));
            }

            classifier.getColorRamp().setDefaultColorMapMode(colormap_mode);
            classifier.getColorRamp().setDefaultReverse(cbxReverse.isSelected());
        }else{
            classifier.setColorRamp(null);  // Use plain StartColor/EndColor gradient
        }

        // --- Vector-only parameters ---
        if(classifier instanceof TGIS_ClassificationVector){
            classifier_vec = (TGIS_ClassificationVector) classifier;
            classifier_vec.setStartSize(Integer.parseInt(tbStartSize.getText()));  // Min symbol size
            classifier_vec.setEndSize(Integer.parseInt(tbEndSize.getText()));      // Max symbol size
            classifier_vec.setClassIdField(class_id_field);                        // Field to store class IDs

            // Render type: how the classification is visualised per class
            render_type = cmbRenderBy.getSelectedItem().toString();
            switch (render_type) {
                case RENDER_TYPE_SIZE:
                    classifier_vec.setRenderType(TGIS_ClassificationRenderType.Size);
                    break;
                case RENDER_TYPE_COLOR:
                    classifier_vec.setRenderType(TGIS_ClassificationRenderType.Color);
                    break;
                case RENDER_TYPE_OUTLINE_WIDTH:
                    classifier_vec.setRenderType(TGIS_ClassificationRenderType.OutlineWidth);
                    break;
                case RENDER_TYPE_OUTLINE_COLOR:
                    classifier_vec.setRenderType(TGIS_ClassificationRenderType.OutlineColor);
                    break;
                default:
                    classifier_vec.setRenderType(TGIS_ClassificationRenderType.Color);
                    break;
            }
        }

        /* --- Statistics management ---
           Classification algorithms (NaturalBreaks, KMeans, etc.) require layer
           statistics (min, max, mean, std dev) to be calculated first.
           MustCalculateStatistics() returns true when they are missing or stale.
           If so, prompt the user to confirm recalculation before proceeding. */
        if(classifier.MustCalculateStatistics()){
            int res = JOptionPane.showConfirmDialog(
                this,
                "Statistics need to be calculated.",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);

            if(JOptionPane.YES_OPTION == res){
                lyr.getStatistics().Calculate();     // Compute statistics now
            }else{
                lyr.getStatistics().ResetModified(); // Discard the stale-statistics flag
                return;
            }
        }

        // Run the classification; if a ClassIdField was requested, persist the IDs.
        if(classifier.Classify() && create_field && ( lv != null)){
            lv.SaveData();
        }

        // Repaint the viewer so the new classification colours are rendered
        GIS.InvalidateWholeMap();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dlgOpen = new javax.swing.JFileChooser();
        dlgColor = new javax.swing.JColorChooser();
        jPanel1 = new javax.swing.JPanel();
        btnOpen = new javax.swing.JButton();
        lblField = new javax.swing.JLabel();
        cmbField = new javax.swing.JComboBox<>();
        lblMethod = new javax.swing.JLabel();
        cmbMethod = new javax.swing.JComboBox<>();
        cbxForceStatisticRecalculation = new javax.swing.JCheckBox();
        lblRenderBy = new javax.swing.JLabel();
        cmbRenderBy = new javax.swing.JComboBox<>();
        lblClasses = new javax.swing.JLabel();
        cmbClasses = new javax.swing.JComboBox<>();
        lblInterval = new javax.swing.JLabel();
        edtInterval = new javax.swing.JTextField();
        cmbInterval = new javax.swing.JComboBox<>();
        lblManualBreak = new javax.swing.JLabel();
        edtManualBreak = new javax.swing.JTextField();
        btnAddManualbreak = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblStartColor = new javax.swing.JLabel();
        pStartColor = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        pEndColor = new javax.swing.JPanel();
        lblStartSize = new javax.swing.JLabel();
        tbStartSize = new javax.swing.JTextField();
        lblEndSize = new javax.swing.JLabel();
        tbEndSize = new javax.swing.JTextField();
        lblClassIdField = new javax.swing.JLabel();
        edtClassIdField = new javax.swing.JTextField();
        cbxShowInLegend = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        cbxColorRamp = new javax.swing.JCheckBox();
        cbxColorRampName = new javax.swing.JCheckBox();
        cmbColorRamps = new javax.swing.JComboBox<>();
        lblColorMapMode = new javax.swing.JLabel();
        cmbColorMapMode = new javax.swing.JComboBox<>();
        cbxReverse = new javax.swing.JCheckBox();
        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        GIS_Legend = new tatukgis.jdk.TGIS_ControlLegend();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Classification - TatukGIS DK sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(800, 600));

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        btnOpen.setText("Open...");
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });
        jPanel1.add(btnOpen);

        lblField.setText("Field: ");
        jPanel1.add(lblField);

        cmbField.setPreferredSize(new java.awt.Dimension(150, 26));
        cmbField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFieldActionPerformed(evt);
            }
        });
        jPanel1.add(cmbField);

        lblMethod.setText("Method: ");
        jPanel1.add(lblMethod);

        cmbMethod.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select ...", "Defined Interval", "Equal Interval", "Geometrical Interval", "Natural Breaks", "K-Means", "K-Means Spatial", "Quantile", "Quartile", "Standard Deviation", "Standard Deviation with Central", "Unique" }));
        cmbMethod.setPreferredSize(new java.awt.Dimension(150, 26));
        cmbMethod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMethodActionPerformed(evt);
            }
        });
        jPanel1.add(cmbMethod);

        cbxForceStatisticRecalculation.setSelected(true);
        cbxForceStatisticRecalculation.setText("Force statistic calculation");
        cbxForceStatisticRecalculation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxForceStatisticRecalculationActionPerformed(evt);
            }
        });
        jPanel1.add(cbxForceStatisticRecalculation);

        lblRenderBy.setText("Render by: ");
        jPanel1.add(lblRenderBy);

        cmbRenderBy.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Size / Width", "Color", "Outline width", "Outline color" }));
        cmbRenderBy.setSelectedIndex(1);
        cmbRenderBy.setPreferredSize(new java.awt.Dimension(150, 26));
        cmbRenderBy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbRenderByActionPerformed(evt);
            }
        });
        jPanel1.add(cmbRenderBy);

        lblClasses.setLabelFor(cmbClasses);
        lblClasses.setText("Classes: ");
        jPanel1.add(lblClasses);

        cmbClasses.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" }));
        cmbClasses.setSelectedIndex(4);
        cmbClasses.setMinimumSize(new java.awt.Dimension(72, 40));
        cmbClasses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbClassesActionPerformed(evt);
            }
        });
        jPanel1.add(cmbClasses);

        lblInterval.setText("Interval: ");
        jPanel1.add(lblInterval);

        edtInterval.setMinimumSize(new java.awt.Dimension(100, 24));
        edtInterval.setPreferredSize(new java.awt.Dimension(100, 24));
        edtInterval.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                validateEdit(evt);
            }
        });
        jPanel1.add(edtInterval);

        cmbInterval.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1 STDEV", "1/2 STDEV", "1/3 STDEV", "1/4 STDEV" }));
        cmbInterval.setPreferredSize(new java.awt.Dimension(100, 26));
        cmbInterval.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbIntervalActionPerformed(evt);
            }
        });
        jPanel1.add(cmbInterval);

        lblManualBreak.setText("Manual:");
        jPanel1.add(lblManualBreak);

        edtManualBreak.setText("0,10.5,20,50");
        jPanel1.add(edtManualBreak);

        btnAddManualbreak.setText("Add");
        btnAddManualbreak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddManualbreakActionPerformed(evt);
            }
        });
        jPanel1.add(btnAddManualbreak);

        jPanel2.setMinimumSize(new java.awt.Dimension(100, 42));
        jPanel2.setPreferredSize(new java.awt.Dimension(0, 42));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblStartColor.setText("Start color: ");
        jPanel2.add(lblStartColor);

        pStartColor.setBackground(new java.awt.Color(233, 248, 237));
        pStartColor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pStartColor.setMaximumSize(new java.awt.Dimension(20, 20));
        pStartColor.setMinimumSize(new java.awt.Dimension(20, 20));
        pStartColor.setPreferredSize(new java.awt.Dimension(20, 20));
        pStartColor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pColorMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pStartColorLayout = new javax.swing.GroupLayout(pStartColor);
        pStartColor.setLayout(pStartColorLayout);
        pStartColorLayout.setHorizontalGroup(
            pStartColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );
        pStartColorLayout.setVerticalGroup(
            pStartColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        jPanel2.add(pStartColor);

        jLabel2.setText("End color: ");
        jPanel2.add(jLabel2);

        pEndColor.setBackground(new java.awt.Color(0, 121, 0));
        pEndColor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pEndColor.setMaximumSize(new java.awt.Dimension(20, 20));
        pEndColor.setMinimumSize(new java.awt.Dimension(20, 20));
        pEndColor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pColorMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pEndColorLayout = new javax.swing.GroupLayout(pEndColor);
        pEndColor.setLayout(pEndColorLayout);
        pEndColorLayout.setHorizontalGroup(
            pEndColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );
        pEndColorLayout.setVerticalGroup(
            pEndColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        jPanel2.add(pEndColor);

        lblStartSize.setText("Start size:");
        jPanel2.add(lblStartSize);

        tbStartSize.setText("1");
        tbStartSize.setMinimumSize(new java.awt.Dimension(30, 24));
        tbStartSize.setPreferredSize(new java.awt.Dimension(50, 24));
        tbStartSize.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ValidateEdit(evt);
            }
        });
        jPanel2.add(tbStartSize);

        lblEndSize.setText("End size:");
        jPanel2.add(lblEndSize);

        tbEndSize.setText("100");
        tbEndSize.setMinimumSize(new java.awt.Dimension(30, 24));
        tbEndSize.setPreferredSize(new java.awt.Dimension(50, 24));
        tbEndSize.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                validateEdit(evt);
            }
        });
        jPanel2.add(tbEndSize);

        lblClassIdField.setText("Class ID field:");
        jPanel2.add(lblClassIdField);

        edtClassIdField.setPreferredSize(new java.awt.Dimension(100, 24));
        edtClassIdField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                validateEdit(evt);
            }
        });
        jPanel2.add(edtClassIdField);

        cbxShowInLegend.setSelected(true);
        cbxShowInLegend.setText("Show in legend");
        cbxShowInLegend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxShowInLegendActionPerformed(evt);
            }
        });
        jPanel2.add(cbxShowInLegend);

        jPanel4.setMinimumSize(new java.awt.Dimension(100, 42));
        jPanel4.setPreferredSize(new java.awt.Dimension(0, 42));
        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        cbxColorRamp.setText("Use color ramp");
        cbxColorRamp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxColorRampActionPerformed(evt);
            }
        });
        jPanel4.add(cbxColorRamp);

        cbxColorRampName.setText("Use ColorRampName");
        jPanel4.add(cbxColorRampName);

        cmbColorRamps.setMinimumSize(new java.awt.Dimension(150, 26));
        cmbColorRamps.setPreferredSize(new java.awt.Dimension(150, 26));
        cmbColorRamps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbColorRampsActionPerformed(evt);
            }
        });
        jPanel4.add(cmbColorRamps);

        lblColorMapMode.setText("Color map mode:");
        jPanel4.add(lblColorMapMode);

        cmbColorMapMode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Continuous", "Discrete" }));
        cmbColorMapMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbColorMapModeActionPerformed(evt);
            }
        });
        jPanel4.add(cmbColorMapMode);

        cbxReverse.setText("Reverse");
        cbxReverse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxReverseActionPerformed(evt);
            }
        });
        jPanel4.add(cbxReverse);

        GIS_Legend.setGIS_Viewer(GIS);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(GIS_Legend, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GIS, javax.swing.GroupLayout.PREFERRED_SIZE, 1152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GIS_Legend, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Re-classifies when the number-of-classes selection changes.
     */
    private void cmbClassesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbClassesActionPerformed
        doClassify();
    }//GEN-LAST:event_cmbClassesActionPerformed

    /**
     * Opens the file chooser dialog, loads the selected file, and repopulates
     * the field and color-ramp pickers for the new layer.
     */
    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        String path;

        if(dlgOpen.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            path = dlgOpen.getSelectedFile().getPath();
            GIS.Open(path);

            fillCbFields();      // Refresh field list for the new layer
            fillCbColorRamps();  // Ramp list is global but re-populate to ensure sync
        }
    }//GEN-LAST:event_btnOpenActionPerformed

    /**
     * Handles a click on either color swatch panel.
     * Opens the system color-chooser dialog and applies the new color as either
     * the start or end color depending on which panel was clicked, then
     * re-classifies.
     */
    private void pColorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pColorMouseClicked
        JPanel panel = (JPanel) (evt.getSource());
        Color initialBackground = panel.getBackground();
        Color background = JColorChooser.showDialog(this, "Change color", initialBackground);
        panel.setBackground(background);

        doClassify();
    }//GEN-LAST:event_pColorMouseClicked

    /**
     * Validates the typed character in an interval/size text field.
     * Only triggers {@link #doClassify()} when the current method or render type
     * makes the edited field meaningful and the text is a valid decimal number.
     */
    private void validateEdit(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_validateEdit
        boolean patternMatch = Pattern.matches("([0-9]*)\\.([0-9]*)", ((JTextComponent)evt.getSource()).getText());
        if((cmbMethod.getSelectedItem().toString().equals(GIS_CLASSIFY_METHOD_DI)) ||
                (cmbRenderBy.getSelectedItem().toString().equals(RENDER_TYPE_SIZE)) ||
                (cmbRenderBy.getSelectedItem().toString().equals(RENDER_TYPE_OUTLINE_WIDTH)) &&
                (patternMatch)){
            doClassify();
        }
    }//GEN-LAST:event_validateEdit

    /**
     * Placeholder key handler — reserved for future start-size validation.
     */
    private void ValidateEdit(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ValidateEdit
        // TODO add your handling code here:
    }//GEN-LAST:event_ValidateEdit

    // -----------------------------------------------------------------------
    // Visibility helpers for optional toolbar controls.
    // Each pair shows/hides both the input widget and its associated label.
    // -----------------------------------------------------------------------

    /** Shows or hides the fixed-interval text box and its label. */
    private void setInterval( boolean _val ) { edtInterval.setVisible(_val); lblInterval.setVisible(_val); }
    private void showInterval() { setInterval( true ); }
    private void hideInterval() { setInterval( false ); }

    /** Shows or hides the std-dev interval fraction combo and its label. */
    private void setStdDev( boolean _val ) { cmbInterval.setVisible(_val); lblInterval.setVisible(_val); }
    private void showStdDev() { setStdDev( true ); }
    private void hideStdDev() { setStdDev( false ); }

    /** Shows or hides the number-of-classes combo and its label. */
    private void setClasses( boolean _val ) { cmbClasses.setVisible(_val); lblClasses.setVisible(_val); }
    private void showClasses() { setClasses( true ); }
    private void hideClasses() { setClasses( false ); }

    /** Shows or hides the manual break entry controls. */
    private void setManual( boolean _val ) { edtManualBreak.setVisible(_val); lblManualBreak.setVisible(_val); btnAddManualbreak.setVisible(_val); }
    private void showManual() { setManual( true ); }
    private void hideManual() { setManual( false ); }

    /**
     * Handles classification method changes.
     * Resets the interval field if it is invalid, updates the default color ramp
     * and colormap mode for the newly selected method, shows or hides the
     * relevant optional controls (class count, interval, std-dev fraction,
     * manual breaks), then calls {@link #doClassify()}.
     * <p>
     * Methods that determine their own class count automatically
     * (DefinedInterval, Quartile, StandardDeviation variants) hide the
     * class-count combo; all others show it.
     * </p>
     */
    private void cmbMethodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMethodActionPerformed
        String method;
        boolean patternMatch = Pattern.matches("([0-9]*)\\.([0-9]*)", edtInterval.getText());

        // Reset interval to a safe default if the current value is not a valid number
        if (!patternMatch) {
            edtInterval.setText("100");
        }

        // Reset ramp defaults before applying method-specific overrides
        cmbColorRamps.setSelectedItem(TGIS_ColorRampNames.getGreenBlue());
        cmbColorMapMode.setSelectedItem(__Global.GIS_COLORMAPMODE_CONTINUOUS);

        method = cmbMethod.getSelectedItem().toString();

        // "Select …" placeholder — hide all optional controls
        if(cmbMethod.getSelectedIndex() == 0) {
            hideInterval();
            hideStdDev();
            hideClasses();
            hideManual();
        }else if(method.equals(GIS_CLASSIFY_METHOD_DI)){
            // Defined Interval: user specifies the interval width, class count is auto
            hideStdDev();
            hideClasses();
            hideManual();
            showInterval();
        }else if(method.equals(GIS_CLASSIFY_METHOD_MN)){
            // Manual: user enters comma-separated break values
            hideInterval();
            hideStdDev();
            hideClasses();
            showManual();
        }else if(method.equals(GIS_CLASSIFY_METHOD_QR)){
            // Quartile: always produces 4 classes — hide class count and interval
            hideInterval();
            hideStdDev();
            hideClasses();
            hideManual();
            cmbColorRamps.setSelectedItem(TGIS_ColorRampNames.getBrownGreen());
        }else if(( method.equals(GIS_CLASSIFY_METHOD_SD) ) ||
                ( method.equals(GIS_CLASSIFY_METHOD_SDC) )){
            // Standard deviation variants: interval is a fraction of one std dev
            hideInterval();
            hideClasses();
            hideManual();
            showStdDev();
            cmbColorRamps.setSelectedItem(TGIS_ColorRampNames.getBrownGreen());
        }else if(method.equals(GIS_CLASSIFY_METHOD_UNQ)){
            // Unique: one class per distinct value — force discrete color mapping
            hideInterval();
            hideClasses();
            hideStdDev();
            hideManual();
            setColorRampControlEnabled(true);
            cbxColorRamp.setSelected(true);
            cmbColorRamps.setSelectedItem(TGIS_ColorRampNames.getUnique());
            cmbColorMapMode.setSelectedItem(__Global.GIS_COLORMAPMODE_DISCRETE);
        }else{
            // All other methods allow the user to specify the number of classes
            hideInterval();
            hideStdDev();
            hideManual();
            showClasses();
        }

        doClassify();
    }//GEN-LAST:event_cmbMethodActionPerformed

    /**
     * Re-classifies when the target field selection changes.
     */
    private void cmbFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFieldActionPerformed
        doClassify();
    }//GEN-LAST:event_cmbFieldActionPerformed

    /**
     * Validates the selected render type against the layer's geometry type.
     * Size rendering is not meaningful for polygon layers, so it is disallowed.
     * {@code ClearAndSetDefaults()} resets any previous classification styling
     * before the new render type is applied.
     */
    private void cmbRenderByActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbRenderByActionPerformed
        TGIS_Layer ll;

        ll = getLayer();

        if(ll instanceof TGIS_LayerVector){
            // Clear per-feature rendering parameters set by a previous classification
            ll.getParamsList().ClearAndSetDefaults();
            if(RENDER_TYPE_SIZE.equals(cmbRenderBy.getSelectedItem().toString())){
                if (TGIS_ShapeType.Polygon == ((TGIS_LayerVector) ll).getDefaultShapeType()) {
                    JOptionPane.showMessageDialog(this, "Method not allowed for polygons");
                    cmbRenderBy.setSelectedIndex(1);  // Fall back to Color
                }
            }
        }

        doClassify();
    }//GEN-LAST:event_cmbRenderByActionPerformed

    /** Re-classifies when the std-dev interval fraction selection changes. */
    private void cmbIntervalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbIntervalActionPerformed
        doClassify();
    }//GEN-LAST:event_cmbIntervalActionPerformed

    /** Re-classifies when a different named color ramp is selected. */
    private void cmbColorRampsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbColorRampsActionPerformed
        doClassify();
    }//GEN-LAST:event_cmbColorRampsActionPerformed

    /** Re-classifies when the legend visibility toggle changes. */
    private void cbxShowInLegendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxShowInLegendActionPerformed
        doClassify();
    }//GEN-LAST:event_cbxShowInLegendActionPerformed

    /**
     * Enables or disables the ramp sub-controls when the "Use color ramp"
     * checkbox is toggled, then re-classifies.
     */
    private void cbxColorRampActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxColorRampActionPerformed
        setColorRampControlEnabled( cbxColorRamp.isSelected());
        doClassify();
    }//GEN-LAST:event_cbxColorRampActionPerformed

    /** Re-classifies when the colormap mode (Continuous/Discrete) changes. */
    private void cmbColorMapModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbColorMapModeActionPerformed
        doClassify();
    }//GEN-LAST:event_cmbColorMapModeActionPerformed

    /** Re-classifies when the "Add" manual break button is clicked. */
    private void btnAddManualbreakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddManualbreakActionPerformed
        doClassify();
    }//GEN-LAST:event_btnAddManualbreakActionPerformed

    /** Re-classifies when the Force Statistics Calculation toggle changes. */
    private void cbxForceStatisticRecalculationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxForceStatisticRecalculationActionPerformed
        doClassify();
    }//GEN-LAST:event_cbxForceStatisticRecalculationActionPerformed

    /** Re-classifies when the color ramp reverse toggle changes. */
    private void cbxReverseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxReverseActionPerformed
        doClassify();
    }//GEN-LAST:event_cbxReverseActionPerformed

    /**
     * Application entry point.
     * Attempts to apply the Windows look-and-feel for a native appearance,
     * then shows the ClassificationUI frame on the AWT Event Dispatch Thread.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String args[]) {
        /* Set the Windows look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClassificationUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form on the Event Dispatch Thread */
        java.awt.EventQueue.invokeLater(() -> {
            new ClassificationUI().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;           // TatukGIS JDK map viewer component
    private tatukgis.jdk.TGIS_ControlLegend GIS_Legend; // Legend panel linked to GIS viewer
    private javax.swing.JButton btnAddManualbreak;       // Toolbar: apply manual break values
    private javax.swing.JButton btnOpen;                 // Toolbar: open a different file
    private javax.swing.JCheckBox cbxColorRamp;          // Toggle color ramp override
    private javax.swing.JCheckBox cbxColorRampName;      // Assign ramp by name string
    private javax.swing.JCheckBox cbxForceStatisticRecalculation; // Force statistics auto-recalc
    private javax.swing.JCheckBox cbxReverse;            // Reverse ramp color direction
    private javax.swing.JCheckBox cbxShowInLegend;       // Toggle classification legend
    private javax.swing.JComboBox<String> cmbClasses;    // Number of classes (1–9)
    private javax.swing.JComboBox<String> cmbColorMapMode; // Continuous vs. Discrete
    private javax.swing.JComboBox<String> cmbColorRamps; // Named color ramp picker
    private javax.swing.JComboBox<String> cmbField;      // Target field / band selector
    private javax.swing.JComboBox<String> cmbInterval;   // Std-dev interval fraction
    private javax.swing.JComboBox<String> cmbMethod;     // Classification method selector
    private javax.swing.JComboBox<String> cmbRenderBy;   // Render type (Color, Size, …)
    private javax.swing.JColorChooser dlgColor;          // Color chooser dialog
    private javax.swing.JFileChooser dlgOpen;            // File open dialog
    private javax.swing.JTextField edtClassIdField;      // Class ID field name
    private javax.swing.JTextField edtInterval;          // Fixed interval value
    private javax.swing.JTextField edtManualBreak;       // Comma-separated break values
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblClassIdField;
    private javax.swing.JLabel lblClasses;
    private javax.swing.JLabel lblColorMapMode;
    private javax.swing.JLabel lblEndSize;
    private javax.swing.JLabel lblField;
    private javax.swing.JLabel lblInterval;
    private javax.swing.JLabel lblManualBreak;
    private javax.swing.JLabel lblMethod;
    private javax.swing.JLabel lblRenderBy;
    private javax.swing.JLabel lblStartColor;
    private javax.swing.JLabel lblStartSize;
    private javax.swing.JPanel pEndColor;    // Color swatch: end color (highest-value class)
    private javax.swing.JPanel pStartColor; // Color swatch: start color (lowest-value class)
    private javax.swing.JTextField tbEndSize;   // Max symbol size (Size render type)
    private javax.swing.JTextField tbStartSize; // Min symbol size (Size render type)
    // End of variables declaration//GEN-END:variables
}
