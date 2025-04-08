package Classification;

import java.awt.Color;
import java.awt.Toolkit;
import java.io.File;
import java.util.regex.Pattern;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.JTextComponent;
import tatukgis.jdk.DialogResult;
import tatukgis.jdk.TGIS_ClassificationAbstract;
import tatukgis.jdk.TGIS_ClassificationFactory;
import tatukgis.jdk.TGIS_ClassificationMethod;
import tatukgis.jdk.TGIS_ClassificationRenderType;
import tatukgis.jdk.TGIS_ClassificationVector;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_ColorMapMode;
import tatukgis.jdk.TGIS_FieldInfo;
import tatukgis.jdk.TGIS_FieldType;
import tatukgis.jdk.TGIS_FileType;
import tatukgis.jdk.TGIS_Layer;
import tatukgis.jdk.TGIS_LayerPixel;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_ShapeType;
import tatukgis.jdk.TGIS_Utils;

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
        
        cbField.removeAllItems();
        
        lyr = getLayer() ;
        
        if(lyr instanceof TGIS_LayerVector){
            lv = (TGIS_LayerVector) lyr;
            
            cbField.addItem("GIS_UID");
            cbField.addItem("GIS_AREA");
            cbField.addItem("GIS_LENGTH");
            cbField.addItem("GIS_CENTROID_X");
            cbField.addItem("GIS_CENTROID_Y");
            
            for(TGIS_FieldInfo field : lv.getFields()){
                switch(field.getFieldType()){
                    case TGIS_FieldType.Number: 
                        cbField.addItem(field.getName()); 
                        break;
                    case TGIS_FieldType.Float: 
                        cbField.addItem(field.getName()); 
                        break;
                }
            }
        }else if(lyr instanceof TGIS_LayerPixel){
            lp = (TGIS_LayerPixel) lyr;
            
            for(int i = 0; i < lp.getBandsCount(); i++){
                cbField.addItem(String.valueOf(i));
            }
        }
        
        cbField.setSelectedIndex(0);
    }
    
    private void fillCbColorRamps(){
        String ramp_name;
        
        for(int i = 0; i < TGIS_Utils.getGisColorRampList().getCount(); i++){
            ramp_name = TGIS_Utils.getGisColorRampList().getItems(i).getName();
            cbColorRamp.addItem(ramp_name);
            
            if("GreenBlue".equals(ramp_name)){
                cbColorRamp.setSelectedIndex(i);
            }
        }
    }
    
    private void doClassify(){
        TGIS_Layer lyr;
        TGIS_LayerVector lv = null;
        String method;
        String render_type;
        String interval;
        String class_id_field = "";
        Boolean create_field;
        TGIS_ClassificationAbstract classifier;
        TGIS_ClassificationVector classifier_vec;
        int colormap_mode; 
        
        if(cbMethod.getSelectedIndex() <= 0) {
            return;
        }
        
        create_field = false;
        lyr = getLayer();
        
        if(lyr instanceof TGIS_LayerVector){
            lv = (TGIS_LayerVector) lyr;
            
            // add "ClassIdField" if provided
            class_id_field = tbClassIdField.getText();
            create_field = class_id_field.length() > 0;
            if(create_field && (lv.FindField(class_id_field) < 0)){
                lv.AddField(class_id_field, TGIS_FieldType.Number, 3, 0);
            }
        }else if(!(lyr instanceof TGIS_LayerPixel)){
            JOptionPane.showMessageDialog(this, String.format("Layer %s is not supported", lyr.getName()));
        }
        
        classifier = TGIS_ClassificationFactory.CreateClassifier(lyr);
        
        // set common properties
        classifier.setTarget(cbField.getSelectedItem().toString());
        classifier.setNumClasses(cbClasses.getSelectedIndex() + 1);
        classifier.setStartColor(TGIS_Color.FromRGB(pStartColor.getBackground().getRGB()));
        classifier.setEndColor(TGIS_Color.FromRGB(pEndColor.getBackground().getRGB()));
        classifier.setShowLegend(cbxShowInLegend.isSelected());
        
        // set method
        method = cbMethod.getSelectedItem().toString();
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
                classifier.EstimateNumClasses();
                break;
            default:
                classifier.setMethod(TGIS_ClassificationMethod.NaturalBreaks);
                break;
        }
        
        // set interval
        classifier.setInterval(Float.valueOf(tbInterval.getText()));
        
        if((GIS_CLASSIFY_METHOD_SD.equals(method)) || (GIS_CLASSIFY_METHOD_SDC.equals(method))){
            interval = cbInterval.getSelectedItem().toString();
            switch (interval) {
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
        
        // NumClasses property is automatically calculated for methods:
        // DefinedInterval, Quartile, StandardDeviation(s)
        if(cbxUseColorRamp.isSelected()){
            if(method == GIS_CLASSIFY_METHOD_UNQ){
                colormap_mode = TGIS_ColorMapMode.Discrete; 
            }else {
                colormap_mode = TGIS_ColorMapMode.Continuous; 
            }
            classifier.setColorRamp(TGIS_Utils.getGisColorRampList().getItems(cbColorRamp.getSelectedIndex()).RealizeColorMap(
                colormap_mode,
                classifier.getNumClasses(), 
                false)
            );   
        }else{
            classifier.setColorRamp(null);
        }
        
        // vector-only params
        if(classifier instanceof TGIS_ClassificationVector){
            classifier_vec = (TGIS_ClassificationVector) classifier;
            classifier_vec.setStartSize(Integer.valueOf(tbStartSize.getText()));
            classifier_vec.setEndSize(Integer.valueOf(tbEndSize.getText()));
            classifier_vec.setClassIdField(class_id_field);
            
            // render type
            render_type = cbRenderBy.getSelectedItem().toString();
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
        cbField = new javax.swing.JComboBox<>();
        lblMethod = new javax.swing.JLabel();
        cbMethod = new javax.swing.JComboBox<>();
        lblRenderBy = new javax.swing.JLabel();
        cbRenderBy = new javax.swing.JComboBox<>();
        lblClasses = new javax.swing.JLabel();
        cbClasses = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        cbInterval = new javax.swing.JComboBox<>();
        tbInterval = new javax.swing.JTextField();
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
        tbClassIdField = new javax.swing.JTextField();
        cbxShowInLegend = new javax.swing.JCheckBox();
        cbxUseColorRamp = new javax.swing.JCheckBox();
        cbColorRamp = new javax.swing.JComboBox<>();
        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        GIS_Legend = new tatukgis.jdk.TGIS_ControlLegend();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Classification - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(800, 600));

        btnOpen.setText("Open...");
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });
        jPanel1.add(btnOpen);

        lblField.setText("Field: ");
        jPanel1.add(lblField);

        cbField.setPreferredSize(new java.awt.Dimension(150, 26));
        cbField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFieldActionPerformed(evt);
            }
        });
        jPanel1.add(cbField);

        lblMethod.setText("Method: ");
        jPanel1.add(lblMethod);

        cbMethod.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select ...", "Defined Interval", "Equal Interval", "Geometrical Interval", "Natural Breaks", "K-Means", "K-Means Spatial", "Quantile", "Quartile", "Standard Deviation", "Standard Deviation with Central", "Unique" }));
        cbMethod.setPreferredSize(new java.awt.Dimension(150, 26));
        cbMethod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMethodActionPerformed(evt);
            }
        });
        jPanel1.add(cbMethod);

        lblRenderBy.setText("Render by: ");
        jPanel1.add(lblRenderBy);

        cbRenderBy.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Size / Width", "Color", "Outline width", "Outline color" }));
        cbRenderBy.setSelectedIndex(1);
        cbRenderBy.setPreferredSize(new java.awt.Dimension(150, 26));
        cbRenderBy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbRenderByActionPerformed(evt);
            }
        });
        jPanel1.add(cbRenderBy);

        lblClasses.setText("Classes: ");
        jPanel1.add(lblClasses);

        cbClasses.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" }));
        cbClasses.setSelectedIndex(4);
        cbClasses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbClassesActionPerformed(evt);
            }
        });
        jPanel1.add(cbClasses);

        jLabel1.setText("Interval: ");
        jPanel1.add(jLabel1);

        cbInterval.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1 STDEV", "1/2 STDEV", "1/3 STDEV", "1/4 STDEV" }));
        cbInterval.setPreferredSize(new java.awt.Dimension(100, 26));
        cbInterval.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbIntervalActionPerformed(evt);
            }
        });
        jPanel1.add(cbInterval);

        tbInterval.setMinimumSize(new java.awt.Dimension(100, 24));
        tbInterval.setPreferredSize(new java.awt.Dimension(100, 24));
        tbInterval.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                validateEdit(evt);
            }
        });
        jPanel1.add(tbInterval);

        jPanel2.setMinimumSize(new java.awt.Dimension(100, 42));
        jPanel2.setPreferredSize(new java.awt.Dimension(0, 42));

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

        tbClassIdField.setPreferredSize(new java.awt.Dimension(100, 24));
        tbClassIdField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                validateEdit(evt);
            }
        });
        jPanel2.add(tbClassIdField);

        cbxShowInLegend.setSelected(true);
        cbxShowInLegend.setText("Show in legend");
        cbxShowInLegend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxShowInLegendActionPerformed(evt);
            }
        });
        jPanel2.add(cbxShowInLegend);

        cbxUseColorRamp.setSelected(true);
        cbxUseColorRamp.setText("Use color ramp");
        cbxUseColorRamp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxUseColorRampActionPerformed(evt);
            }
        });
        jPanel2.add(cbxUseColorRamp);

        cbColorRamp.setMinimumSize(new java.awt.Dimension(150, 26));
        cbColorRamp.setPreferredSize(new java.awt.Dimension(150, 26));
        cbColorRamp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbColorRampActionPerformed(evt);
            }
        });
        jPanel2.add(cbColorRamp);

        GIS_Legend.setGIS_Viewer(GIS);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(GIS_Legend, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1148, Short.MAX_VALUE))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(GIS_Legend, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbClassesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbClassesActionPerformed
        doClassify();
    }//GEN-LAST:event_cbClassesActionPerformed

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        String path;
        
        if(dlgOpen.showOpenDialog(this) == DialogResult.Ok){
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
        if((cbMethod.getSelectedItem().toString().equals(GIS_CLASSIFY_METHOD_DI)) || 
                (cbRenderBy.getSelectedItem().toString().equals(RENDER_TYPE_SIZE)) ||
                (cbRenderBy.getSelectedItem().toString().equals(RENDER_TYPE_OUTLINE_WIDTH)) &&
                (patternMatch)){
            doClassify();
        }
    }//GEN-LAST:event_validateEdit

    private void ValidateEdit(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ValidateEdit
        // TODO add your handling code here:
    }//GEN-LAST:event_ValidateEdit

    private void cbMethodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMethodActionPerformed
            String method;
            boolean patternMatch = Pattern.matches("([0-9]*)\\.([0-9]*)", tbInterval.getText());

            if (!patternMatch) {
                tbInterval.setText("100");
            }

            method = cbMethod.getSelectedItem().toString();

        switch (method) {
            case GIS_CLASSIFY_METHOD_DI:
                tbInterval.setVisible(true);
                tbInterval.setEnabled(true);
                cbInterval.setVisible(false);
                cbClasses.setEnabled(false);
                break;
            case GIS_CLASSIFY_METHOD_QR:
                cbInterval.setVisible(false);
                cbClasses.setEnabled(false);
                tbInterval.setVisible(true);
                tbInterval.setEnabled(false);
                break;
            case GIS_CLASSIFY_METHOD_SD:
            case GIS_CLASSIFY_METHOD_SDC:
                tbInterval.setVisible(false);
                cbInterval.setVisible(true);
                cbClasses.setEnabled(false);
                break;
            default:
                cbInterval.setVisible(false);
                cbClasses.setEnabled(true);
                tbInterval.setVisible(true);
                tbInterval.setEnabled(false);
                if(method == GIS_CLASSIFY_METHOD_UNQ){
                    cbColorRamp.setSelectedItem("Unique");
                }else{
                    cbColorRamp.setSelectedItem("GreenBlue");
                }
                break;
        }

            doClassify();
    }//GEN-LAST:event_cbMethodActionPerformed

    private void cbFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFieldActionPerformed
        doClassify();
    }//GEN-LAST:event_cbFieldActionPerformed

    private void cbRenderByActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbRenderByActionPerformed
        TGIS_Layer ll;
        
        ll = getLayer();
        
        if(ll instanceof TGIS_LayerVector){
            ll.getParamsList().ClearAndSetDefaults();
            if(RENDER_TYPE_SIZE.equals(cbRenderBy.getSelectedItem().toString())){
                if (TGIS_ShapeType.Polygon == ((TGIS_LayerVector) ll).getDefaultShapeType()) {
                    JOptionPane.showMessageDialog(this, "Method not allowed for polygons");
                    cbRenderBy.setSelectedIndex(1);
                }
            }
        }
        
        doClassify();
    }//GEN-LAST:event_cbRenderByActionPerformed

    private void cbIntervalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbIntervalActionPerformed
        doClassify();
    }//GEN-LAST:event_cbIntervalActionPerformed

    private void cbColorRampActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbColorRampActionPerformed
        doClassify();
    }//GEN-LAST:event_cbColorRampActionPerformed

    private void cbxShowInLegendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxShowInLegendActionPerformed
        doClassify();
    }//GEN-LAST:event_cbxShowInLegendActionPerformed

    private void cbxUseColorRampActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxUseColorRampActionPerformed
        doClassify();
    }//GEN-LAST:event_cbxUseColorRampActionPerformed

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
    private javax.swing.JButton btnOpen;
    private javax.swing.JComboBox<String> cbClasses;
    private javax.swing.JComboBox<String> cbColorRamp;
    private javax.swing.JComboBox<String> cbField;
    private javax.swing.JComboBox<String> cbInterval;
    private javax.swing.JComboBox<String> cbMethod;
    private javax.swing.JComboBox<String> cbRenderBy;
    private javax.swing.JCheckBox cbxShowInLegend;
    private javax.swing.JCheckBox cbxUseColorRamp;
    private javax.swing.JColorChooser dlgColor;
    private javax.swing.JFileChooser dlgOpen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblClassIdField;
    private javax.swing.JLabel lblClasses;
    private javax.swing.JLabel lblEndSize;
    private javax.swing.JLabel lblField;
    private javax.swing.JLabel lblMethod;
    private javax.swing.JLabel lblRenderBy;
    private javax.swing.JLabel lblStartColor;
    private javax.swing.JLabel lblStartSize;
    private javax.swing.JPanel pEndColor;
    private javax.swing.JPanel pStartColor;
    private javax.swing.JTextField tbClassIdField;
    private javax.swing.JTextField tbEndSize;
    private javax.swing.JTextField tbInterval;
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
    private final String GIS_CLASSIFY_METHOD_NB  = "Natural Breaks"                   ;
    private final String GIS_CLASSIFY_METHOD_KM  = "K-Means"                          ;
    private final String GIS_CLASSIFY_METHOD_KMS = "K-Means Spatial"                  ;
    private final String GIS_CLASSIFY_METHOD_QN  = "Quantile"                         ;
    private final String GIS_CLASSIFY_METHOD_QR  = "Quartile"                         ;
    private final String GIS_CLASSIFY_METHOD_SD  = "Standard Deviation"               ;
    private final String GIS_CLASSIFY_METHOD_SDC = "Standard Deviation with Central"  ;
    private final String GIS_CLASSIFY_METHOD_UNQ = "Unique"  ;
    
}
