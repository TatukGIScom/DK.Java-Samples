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

public class ClassificationUI extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;


    public ClassificationUI() {
        initComponents();
        initOwn();
    }
    
    private void initOwn(){
        setOpenDialogFilter();
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/USA/States/California/Counties.shp");
        
        fillCbFields();
        fillCbColorRamps();
        
        //Initially hide those
        hideClasses();
        hideInterval();
        hideManual();
        hideStdDev();
        setColorRampControlEnabled(false);
    };
    
    private void setOpenDialogFilter(){
        String supportedFiles = TGIS_Utils.GisSupportedFiles(TGIS_FileType.All, false);
        for(String tmp : supportedFiles.split(("_"))){        
            dlgOpen.addChoosableFileFilter(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    String tmp2;
                    tmp2 = "";
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
    
    private String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".")+1);
        } else {
            return "";
        }
    }
    
    private TGIS_Layer getLayer(){
        TGIS_Layer res = null;
        if(GIS.getItems().getCount() > 0){
            res = (TGIS_Layer) GIS.getItems().getItems(0);
        }
        
        return res;
    }
    
    private void fillCbFields(){
        TGIS_Layer lyr;
        TGIS_LayerVector lv;
        TGIS_LayerPixel lp;
        
        cmbField.removeAllItems();
        
        lyr = getLayer() ;
        
        if(lyr instanceof TGIS_LayerVector){
            lv = (TGIS_LayerVector) lyr;
            
            cmbField.addItem("GIS_UID");
            cmbField.addItem("GIS_AREA");
            cmbField.addItem("GIS_LENGTH");
            cmbField.addItem("GIS_CENTROID_X");
            cmbField.addItem("GIS_CENTROID_Y");
            
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
            lp = (TGIS_LayerPixel) lyr;
            
            for(int i = 0; i < lp.getBandsCount(); i++){
                cmbField.addItem(String.valueOf(i));
            }
        }
        
        cmbField.setSelectedIndex(0);
    }
    
    private void setColorRampControlEnabled( boolean _enabled ){
        cbxColorRampName.setEnabled(_enabled);
        cmbColorRamps.setEnabled(_enabled);
        cmbColorMapMode.setEnabled(_enabled);
        cbxReverse.setEnabled(_enabled);
    }
    
    private void fillCbColorRamps(){
        String ramp_name;
        
        for(int i = 0; i < TGIS_Utils.getGisColorRampList().getCount(); i++){
            ramp_name = TGIS_Utils.getGisColorRampList().getItems(i).getName();
            cmbColorRamps.addItem(ramp_name);
            
            if("GreenBlue".equals(ramp_name)){
                cmbColorRamps.setSelectedIndex(i);
            }
        }
    }
    
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
        
        if(cmbMethod.getSelectedIndex() <= 0) {
            return;
        }
        
        create_field = false;
        lyr = getLayer();
        
        if(lyr == null)
            return;
        
        if(lyr instanceof TGIS_LayerVector){
            lv = (TGIS_LayerVector) lyr;
            
            // add "ClassIdField" if provided
            class_id_field = edtClassIdField.getText();
            create_field = class_id_field.length() > 0;
            if(create_field && (lv.FindField(class_id_field) < 0)){
                lv.AddField(class_id_field, TGIS_FieldType.Number, 3, 0);
            }
        }else if(!(lyr instanceof TGIS_LayerPixel)){
            JOptionPane.showMessageDialog(this, String.format("Layer %s is not supported", lyr.getName()));
        }
        
        classifier = TGIS_ClassificationFactory.CreateClassifier(lyr);
        
        // set common properties
        classifier.setTarget(cmbField.getSelectedItem().toString());
        // NumClasses property is automatically calculated for methods:
        // DefinedInterval, Quartile, StandardDeviation(s) }
        classifier.setNumClasses(cmbClasses.getSelectedIndex() + 1);
        classifier.setStartColor(TGIS_Color.FromRGB(pStartColor.getBackground().getRGB()));
        classifier.setEndColor(TGIS_Color.FromRGB(pEndColor.getBackground().getRGB()));
        classifier.setShowLegend(cbxShowInLegend.isSelected());
        
        // set method
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
        
        // set interval
        classifier.setInterval(Float.parseFloat(edtInterval.getText()));
        
        if((GIS_CLASSIFY_METHOD_SD.equals(method)) || (GIS_CLASSIFY_METHOD_SDC.equals(method))){
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
        
        // set manual
        class_breaks_arr = edtManualBreak.getText().split(",|;|\\/");
        for(String class_breaks_str : class_breaks_arr){
            try{
            class_breaks_val = Float.parseFloat(class_breaks_str);
            }catch( NumberFormatException e ){
                continue;
            }
            classifier.AddClassBreak(class_breaks_val);
        }
        
        // NumClasses property is automatically calculated for methods:
        // DefinedInterval, Quartile, StandardDeviation(s)
        if(cbxColorRamp.isSelected()){
            // colormap mode
            if(cmbColorMapMode.getSelectedItem() == __Global.GIS_COLORMAPMODE_CONTINUOUS){ 
              colormap_mode = TGIS_ColorMapMode.Continuous;
            }else{
              colormap_mode = TGIS_ColorMapMode.Discrete;
            }
              
            // ramp can be assigned directly (ColorRamp) or by name (ColorRampName)
            ramp_name = cmbColorRamps.getSelectedItem().toString() ;
            if(cbxColorRampName.isSelected())
            {
              classifier.setColorRampName(ramp_name);
            }else{
              classifier.setColorRamp(__Global.GisColorRampList().ByName( ramp_name ));
            }

            classifier.getColorRamp().setDefaultColorMapMode(colormap_mode);
            classifier.getColorRamp().setDefaultReverse(cbxReverse.isSelected()) ;
        }else{
            classifier.setColorRamp(null);
        }
       
        
        // vector-only params
        if(classifier instanceof TGIS_ClassificationVector){
            classifier_vec = (TGIS_ClassificationVector) classifier;
            classifier_vec.setStartSize(Integer.parseInt(tbStartSize.getText()));
            classifier_vec.setEndSize(Integer.parseInt(tbEndSize.getText()));
            classifier_vec.setClassIdField(class_id_field);
            
            // render type
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
        
        // before the classification starts, layer statistics must be provided
        if(classifier.MustCalculateStatistics()){
            int res = JOptionPane.showConfirmDialog(
            this,
            "Statistics need to be calculated.",
            "Confirmation",
            JOptionPane.YES_NO_OPTION);
            
            if(JOptionPane.YES_OPTION == res){
                lyr.getStatistics().Calculate();
            }else{
                lyr.getStatistics().ResetModified();
                return;
            }
        }
        
        if(classifier.Classify() && create_field && ( lv != null)){
            lv.SaveData();
        }
        
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
        setTitle("Classification - TatukGIS DK11 sample");
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

    private void cmbClassesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbClassesActionPerformed
        doClassify();
    }//GEN-LAST:event_cmbClassesActionPerformed

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        String path;
        
        if(dlgOpen.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            path = dlgOpen.getSelectedFile().getPath();
            GIS.Open(path);
            
            fillCbFields();
            fillCbColorRamps();
        }
    }//GEN-LAST:event_btnOpenActionPerformed

    private void pColorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pColorMouseClicked
        JPanel panel = (JPanel) (evt.getSource());
        Color initialBackground = panel.getBackground();
        Color background = JColorChooser.showDialog(this, "Change color",
            initialBackground);
        panel.setBackground(background);
        
        doClassify();
    }//GEN-LAST:event_pColorMouseClicked

    private void validateEdit(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_validateEdit
        boolean patternMatch = Pattern.matches("([0-9]*)\\.([0-9]*)", ((JTextComponent)evt.getSource()).getText());
        if((cmbMethod.getSelectedItem().toString().equals(GIS_CLASSIFY_METHOD_DI)) || 
                (cmbRenderBy.getSelectedItem().toString().equals(RENDER_TYPE_SIZE)) ||
                (cmbRenderBy.getSelectedItem().toString().equals(RENDER_TYPE_OUTLINE_WIDTH)) &&
                (patternMatch)){
            doClassify();
        }
    }//GEN-LAST:event_validateEdit

    private void ValidateEdit(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ValidateEdit
        // TODO add your handling code here:
    }//GEN-LAST:event_ValidateEdit

    private void setInterval( boolean _val ) 
    {
      edtInterval.setVisible(_val); 
      lblInterval.setVisible(_val);
    };

    private void showInterval() 
    {      
        setInterval( true ) ;
    }

    private void hideInterval()
    {
      setInterval( false ) ;
    }

    private void setStdDev( boolean _val ) 
    {
      cmbInterval.setVisible(_val); 
      lblInterval.setVisible(_val);  
    }

    private void showStdDev() 
    {
      setStdDev( true ) ;
    }

    private void hideStdDev() 
    {
      setStdDev( false ) ;
    }

    private void setClasses( boolean _val ) 
    {
      cmbClasses.setVisible(_val);
      lblClasses.setVisible(_val);
    }

    private void showClasses() 
    {
      setClasses( true ) ;
    }

    private void hideClasses() 
    {
      setClasses( false ) ;
    }

    private void setManual( boolean _val ) 
    {
      edtManualBreak.setVisible(_val);
      lblManualBreak.setVisible(_val);
      btnAddManualbreak.setVisible(_val);
    }

    private void showManual() 
    {
      setManual( true ) ;
    }

    private void hideManual() 
    {
      setManual( false ) ;
    }
    
    private void cmbMethodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMethodActionPerformed
        String method;
        boolean patternMatch = Pattern.matches("([0-9]*)\\.([0-9]*)", edtInterval.getText());

        if (!patternMatch) {
            edtInterval.setText("100");
        }

        cmbColorRamps.setSelectedItem(TGIS_ColorRampNames.getGreenBlue()) ;
        cmbColorMapMode.setSelectedItem(__Global.GIS_COLORMAPMODE_CONTINUOUS); 
        
        method = cmbMethod.getSelectedItem().toString();

        
        // no selection
        if(cmbMethod.getSelectedIndex() == 0) {
            hideInterval() ;
            hideStdDev() ;
            hideClasses() ;
            hideManual() ;
        }else if(method.equals(GIS_CLASSIFY_METHOD_DI)){
            hideStdDev() ;
            hideClasses() ;
            hideManual() ;

            showInterval() ;
        }else if(method.equals(GIS_CLASSIFY_METHOD_MN)){
            hideInterval() ;
            hideStdDev() ;
            hideClasses() ;

            showManual() ;
        }else if(method.equals(GIS_CLASSIFY_METHOD_QR)){
            hideInterval() ;
            hideStdDev() ;
            hideClasses() ;
            hideManual() ;

            cmbColorRamps.setSelectedItem(TGIS_ColorRampNames.getBrownGreen()) ;
        }else if(( method.equals(GIS_CLASSIFY_METHOD_SD) ) ||
                ( method.equals(GIS_CLASSIFY_METHOD_SDC) )){
            hideInterval() ;
            hideClasses() ;
            hideManual() ;

            showStdDev() ;

            cmbColorRamps.setSelectedItem(TGIS_ColorRampNames.getBrownGreen()) ;
        }else if(method.equals(GIS_CLASSIFY_METHOD_UNQ)){
            hideInterval() ;
            hideClasses() ;
            hideStdDev() ;
            hideManual() ;

            setColorRampControlEnabled(true);
            
            cbxColorRamp.setSelected(true);
            cmbColorRamps.setSelectedItem(TGIS_ColorRampNames.getUnique()) ;
            cmbColorMapMode.setSelectedItem(__Global.GIS_COLORMAPMODE_DISCRETE); 
        }else{
          hideInterval() ;
          hideStdDev() ;
          hideManual() ;

          showClasses() ;
        }

        doClassify();
    }//GEN-LAST:event_cmbMethodActionPerformed

    private void cmbFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFieldActionPerformed
        doClassify();
    }//GEN-LAST:event_cmbFieldActionPerformed

    private void cmbRenderByActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbRenderByActionPerformed
        TGIS_Layer ll;
        
        ll = getLayer();
        
        if(ll instanceof TGIS_LayerVector){
            ll.getParamsList().ClearAndSetDefaults();
            if(RENDER_TYPE_SIZE.equals(cmbRenderBy.getSelectedItem().toString())){
                if (TGIS_ShapeType.Polygon == ((TGIS_LayerVector) ll).getDefaultShapeType()) {
                    JOptionPane.showMessageDialog(this, "Method not allowed for polygons");
                    cmbRenderBy.setSelectedIndex(1);
                }
            }
        }
        
        doClassify();
    }//GEN-LAST:event_cmbRenderByActionPerformed

    private void cmbIntervalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbIntervalActionPerformed
        doClassify();
    }//GEN-LAST:event_cmbIntervalActionPerformed

    private void cmbColorRampsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbColorRampsActionPerformed
        doClassify();
    }//GEN-LAST:event_cmbColorRampsActionPerformed

    private void cbxShowInLegendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxShowInLegendActionPerformed
        doClassify();
    }//GEN-LAST:event_cbxShowInLegendActionPerformed

    private void cbxColorRampActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxColorRampActionPerformed
        setColorRampControlEnabled( cbxColorRamp.isSelected()) ;
        
        doClassify();
    }//GEN-LAST:event_cbxColorRampActionPerformed

    private void cmbColorMapModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbColorMapModeActionPerformed
        doClassify();
    }//GEN-LAST:event_cmbColorMapModeActionPerformed

    private void btnAddManualbreakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddManualbreakActionPerformed
        doClassify();
    }//GEN-LAST:event_btnAddManualbreakActionPerformed

    private void cbxForceStatisticRecalculationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxForceStatisticRecalculationActionPerformed
        doClassify() ;
    }//GEN-LAST:event_cbxForceStatisticRecalculationActionPerformed

    private void cbxReverseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxReverseActionPerformed
        doClassify() ;
    }//GEN-LAST:event_cbxReverseActionPerformed

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
        
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ClassificationUI().setVisible(true);
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private tatukgis.jdk.TGIS_ControlLegend GIS_Legend;
    private javax.swing.JButton btnAddManualbreak;
    private javax.swing.JButton btnOpen;
    private javax.swing.JCheckBox cbxColorRamp;
    private javax.swing.JCheckBox cbxColorRampName;
    private javax.swing.JCheckBox cbxForceStatisticRecalculation;
    private javax.swing.JCheckBox cbxReverse;
    private javax.swing.JCheckBox cbxShowInLegend;
    private javax.swing.JComboBox<String> cmbClasses;
    private javax.swing.JComboBox<String> cmbColorMapMode;
    private javax.swing.JComboBox<String> cmbColorRamps;
    private javax.swing.JComboBox<String> cmbField;
    private javax.swing.JComboBox<String> cmbInterval;
    private javax.swing.JComboBox<String> cmbMethod;
    private javax.swing.JComboBox<String> cmbRenderBy;
    private javax.swing.JColorChooser dlgColor;
    private javax.swing.JFileChooser dlgOpen;
    private javax.swing.JTextField edtClassIdField;
    private javax.swing.JTextField edtInterval;
    private javax.swing.JTextField edtManualBreak;
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
    private javax.swing.JPanel pEndColor;
    private javax.swing.JPanel pStartColor;
    private javax.swing.JTextField tbEndSize;
    private javax.swing.JTextField tbStartSize;
    // End of variables declaration//GEN-END:variables
    private final String RENDER_TYPE_SIZE  = "Size / Width";
    private final String RENDER_TYPE_COLOR = "Color" ;
    private final String RENDER_TYPE_OUTLINE_WIDTH = "Outline width" ;
    private final String RENDER_TYPE_OUTLINE_COLOR = "Outline color" ;

    private final String STD_INTERVAL_ONE = "1 STDEV" ;
    private final String STD_INTERVAL_ONE_HALF = "1/2 STDEV" ;
    private final String STD_INTERVAL_ONE_THIRD = "1/3 STDEV" ;
    private final String STD_INTERVAL_ONE_QUARTER = "1/4 STDEV" ;

    private final String GIS_CLASSIFY_METHOD_DI  = "Defined Interval"                 ;
    private final String GIS_CLASSIFY_METHOD_EI  = "Equal Interval"                   ;
    private final String GIS_CLASSIFY_METHOD_GI  = "Geometrical Interval"             ;
    private final String GIS_CLASSIFY_METHOD_MN  = "Manual"                           ;
    private final String GIS_CLASSIFY_METHOD_NB  = "Natural Breaks"                   ;
    private final String GIS_CLASSIFY_METHOD_KM  = "K-Means"                          ;
    private final String GIS_CLASSIFY_METHOD_KMS = "K-Means Spatial"                  ;
    private final String GIS_CLASSIFY_METHOD_QN  = "Quantile"                         ;
    private final String GIS_CLASSIFY_METHOD_QR  = "Quartile"                         ;
    private final String GIS_CLASSIFY_METHOD_SD  = "Standard Deviation"               ;
    private final String GIS_CLASSIFY_METHOD_SDC = "Standard Deviation with Central"  ;
    private final String GIS_CLASSIFY_METHOD_UNQ = "Unique"  ;
    
}
