package reclassification;

import RemObjects.Elements.System.VarParameter;
import java.awt.Toolkit;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.table.TableModel;
import tatukgis.jdk.TGIS_BusyEventArgs;
import tatukgis.jdk.TGIS_ClassificationMethod;
import tatukgis.jdk.TGIS_ClassificationPixel;
import tatukgis.jdk.TGIS_LayerPixel;
import tatukgis.jdk.TGIS_Reclassification;
import tatukgis.jdk.TGIS_Utils;

public class ReclassificationUI extends javax.swing.JFrame {

    public ReclassificationUI() {        
        initComponents();
        initOwn();
    }
    
    private void initOwn(){
        spTable.setBorder(BorderFactory.createEmptyBorder());
        
        // Set reclass table
        for(int j=0; j<=4; j++){
            int i = j+1;
            sgrdReclassTable.getModel().setValueAt(String.valueOf(100*i), j, 0);
            sgrdReclassTable.getModel().setValueAt(String.valueOf(100*(i+1)), j, 1);
            sgrdReclassTable.getModel().setValueAt(String.valueOf(i), j, 2);
        }

        sgrdReclassTable.getModel().setValueAt("311", 4, 0);
        sgrdReclassTable.getModel().setValueAt("", 4, 1);
        sgrdReclassTable.getModel().setValueAt("6", 4, 2);
        
        sgrdReclassTable.getModel().setValueAt("nodata", 5, 0);
        sgrdReclassTable.getModel().setValueAt("", 5, 1);
        sgrdReclassTable.getModel().setValueAt("999", 5, 2);
        
        btnUseTableActionPerformed(null);
    };
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        spAltitudeZones = new javax.swing.JScrollPane();
        edtAltitudeZones = new javax.swing.JTextArea();
        spReclassTable = new javax.swing.JScrollPane();
        edtReclassTable = new javax.swing.JTextArea();
        spTable = new javax.swing.JScrollPane();
        sgrdReclassTable = new javax.swing.JTable();
        GISLegend = new tatukgis.jdk.TGIS_ControlLegend();
        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        grpbxReclassification = new javax.swing.JPanel();
        btnUseTable = new javax.swing.JButton();
        btnUseAltitudeZones = new javax.swing.JButton();
        pnlContainer = new javax.swing.JPanel();
        btnReclassify = new javax.swing.JButton();
        spNoData = new javax.swing.JScrollPane();
        edtNoData = new javax.swing.JTextArea();
        chkNoData = new javax.swing.JCheckBox();
        progress = new javax.swing.JProgressBar();

        spAltitudeZones.setBorder(null);
        spAltitudeZones.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spAltitudeZones.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        spAltitudeZones.setPreferredSize(new java.awt.Dimension(600, 180));

        edtAltitudeZones.setEditable(false);
        edtAltitudeZones.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.selectedBackground"));
        edtAltitudeZones.setColumns(20);
        edtAltitudeZones.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        edtAltitudeZones.setLineWrap(true);
        edtAltitudeZones.setRows(5);
        edtAltitudeZones.setText("As an alternative to the Reclassification Table, reclassification can be run based on altitude map zones defined in the pixel layer Params property.  \n\nAdditionally, this method copies colors from zones and automatically transfers them to the output layer.");
        edtAltitudeZones.setWrapStyleWord(true);
        edtAltitudeZones.setMinimumSize(new java.awt.Dimension(600, 250));
        spAltitudeZones.setViewportView(edtAltitudeZones);

        spReclassTable.setBorder(null);
        spReclassTable.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spReclassTable.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        spReclassTable.setPreferredSize(new java.awt.Dimension(252, 150));

        edtReclassTable.setEditable(false);
        edtReclassTable.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.selectedBackground"));
        edtReclassTable.setColumns(20);
        edtReclassTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        edtReclassTable.setLineWrap(true);
        edtReclassTable.setRows(5);
        edtReclassTable.setText("In Reclassification Table you can define:\n- Value reclassification definition (Old value -> New value)\n- Range reclassification definition (values from [Start..End] -> New value)\n- Value for NODATA, by typing  \"nodata\" -> New value");
        edtReclassTable.setWrapStyleWord(true);
        edtReclassTable.setPreferredSize(new java.awt.Dimension(252, 150));
        spReclassTable.setViewportView(edtReclassTable);

        spTable.setBorder(null);
        spTable.setForeground(new java.awt.Color(245, 245, 245));
        spTable.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spTable.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        spTable.setPreferredSize(new java.awt.Dimension(450, 180));
        spTable.setWheelScrollingEnabled(false);

        sgrdReclassTable.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        sgrdReclassTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Start", "End", "New"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        sgrdReclassTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        sgrdReclassTable.setCellSelectionEnabled(true);
        sgrdReclassTable.setShowGrid(true);
        sgrdReclassTable.getTableHeader().setResizingAllowed(false);
        sgrdReclassTable.getTableHeader().setReorderingAllowed(false);
        spTable.setViewportView(sgrdReclassTable);
        sgrdReclassTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Reclassification - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setPreferredSize(new java.awt.Dimension(1200, 1000));
        setSize(new java.awt.Dimension(1200, 1000));

        GISLegend.setGIS_Viewer(GIS);

        grpbxReclassification.setBorder(javax.swing.BorderFactory.createTitledBorder("Raster Reclassification"));
        grpbxReclassification.setPreferredSize(new java.awt.Dimension(300, 400));

        btnUseTable.setText("Use table");
        btnUseTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUseTableActionPerformed(evt);
            }
        });

        btnUseAltitudeZones.setText("Use Altitude Zones");
        btnUseAltitudeZones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUseAltitudeZonesActionPerformed(evt);
            }
        });

        pnlContainer.setLayout(new javax.swing.BoxLayout(pnlContainer, javax.swing.BoxLayout.Y_AXIS));

        btnReclassify.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnReclassify.setText("Reclassify");
        btnReclassify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReclassifyActionPerformed(evt);
            }
        });

        spNoData.setBorder(null);
        spNoData.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spNoData.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        spNoData.setEnabled(false);

        edtNoData.setEditable(false);
        edtNoData.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.disabledBorderColor"));
        edtNoData.setColumns(20);
        edtNoData.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        edtNoData.setLineWrap(true);
        edtNoData.setRows(5);
        edtNoData.setText("Cell values outside the defined ranges will be filled with NODATA value.");
        edtNoData.setWrapStyleWord(true);
        spNoData.setViewportView(edtNoData);

        chkNoData.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        chkNoData.setSelected(true);
        chkNoData.setText("Assign NODATA to missing values");
        chkNoData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkNoDataActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout grpbxReclassificationLayout = new javax.swing.GroupLayout(grpbxReclassification);
        grpbxReclassification.setLayout(grpbxReclassificationLayout);
        grpbxReclassificationLayout.setHorizontalGroup(
            grpbxReclassificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(grpbxReclassificationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(grpbxReclassificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnReclassify, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUseTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUseAltitudeZones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(spNoData, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                    .addComponent(chkNoData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        grpbxReclassificationLayout.setVerticalGroup(
            grpbxReclassificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(grpbxReclassificationLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnUseTable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUseAltitudeZones)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 818, Short.MAX_VALUE)
                .addComponent(chkNoData)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spNoData, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReclassify, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(grpbxReclassification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 1448, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GISLegend, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(progress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(grpbxReclassification, javax.swing.GroupLayout.DEFAULT_SIZE, 1042, Short.MAX_VALUE)
                    .addComponent(GISLegend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progress, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUseAltitudeZonesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUseAltitudeZonesActionPerformed
        pnlContainer.remove(spReclassTable);
        pnlContainer.remove(spTable);
        pnlContainer.add(spAltitudeZones);
        
        revalidate();
        repaint();
        
        OpenSampleForUseAltitudeZones();
    }//GEN-LAST:event_btnUseAltitudeZonesActionPerformed

    private void btnUseTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUseTableActionPerformed
        pnlContainer.add(spReclassTable);
        pnlContainer.add(spTable);
        pnlContainer.remove(spAltitudeZones);
        
        revalidate();
        repaint();
        
        OpenSampleForReclassTable();
    }//GEN-LAST:event_btnUseTableActionPerformed

    private void chkNoDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkNoDataActionPerformed
        if(chkNoData.isSelected())
            edtNoData.setText("Cell values outside the defined ranges will be filled with NODATA value.");
        else
            edtNoData.setText("Cell values outside the defined ranges will be filled with original value.");
    }//GEN-LAST:event_chkNoDataActionPerformed

    private boolean tryParseDouble(final String number, VarParameter result){
        try {
            result.Value = Double.valueOf(number);
            return true;
        }
        catch (NumberFormatException e) {
            result.Value = 0;
            return false;
        }
    }
    
    private void btnReclassifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReclassifyActionPerformed
        TGIS_LayerPixel lp, lp_reclass;
        String name;
        TGIS_Reclassification reclassifier;
        String startValStr, endValStr, newValStr;
        VarParameter startVal, endVal, newVal;
        Float startValF, endValF, newValF;
        boolean startExist, endExist, newExist;
        int row;
        TableModel model ;
        
        lp = (TGIS_LayerPixel)GIS.getItems().getItems(0);
        
        // Remove a layer from GIS if exist
        name = lp.getName() + " (reclass)";
        if(GIS.Get(name) != null)
            GIS.Delete(name);
        
        // Prepare the destination layer
        lp_reclass = new TGIS_LayerPixel();
        lp_reclass.setName(name);
        lp_reclass.Build(true, lp.getCS(), lp.getExtent(), lp.getBitWidth(), lp.getBitHeight());
        
        reclassifier = new TGIS_Reclassification();
        
        reclassifier.setBusyEvent((Object o, TGIS_BusyEventArgs tgsb) -> {
            if (tgsb.getPos() < 0) {
                progress.setValue(0);
            } else {
                if (tgsb.getPos() == 0) {
                    progress.setMinimum(0);
                    progress.setMaximum(100);
                    progress.setValue(0);
                }
                else {
                    if (tgsb.getPos() > 0) {
                        progress.setValue((int)tgsb.getPos());
                    }
                }   
            }
        });
        
        model = sgrdReclassTable.getModel();
        
        for( row = 0; row < sgrdReclassTable.getRowCount()-1; row++){
            startValStr = model.getValueAt(row, 0).toString();
            endValStr = model.getValueAt(row, 1).toString();
            newValStr = model.getValueAt(row, 2).toString();
            
            if(startValStr.isEmpty())
                continue;
            
            startVal = new VarParameter();
            endVal = new VarParameter();
            newVal = new VarParameter();
            
            startExist = tryParseDouble(startValStr, startVal);
            endExist = tryParseDouble(endValStr, endVal);
            newExist = tryParseDouble(newValStr, newVal);
            
            startValF = Float.valueOf(0);
            endValF = Float.valueOf(0);
            newValF = Float.valueOf(0);
            
            
            if( startVal.Value != null )
                startValF = Float.valueOf(startVal.Value.toString()); 
            if( endVal.Value != null )           
                endValF = Float.valueOf(endVal.Value.toString());
            if( newVal.Value != null )
                newValF = Float.valueOf(newVal.Value.toString()); 
            
            // Assign a new value for the existing nodata
            if((startValStr.contains("nd") || startValStr.contains("nodata") || startValStr.contains("no-data")) && newExist)
                reclassifier.setReclassNoDataValue(newValF);
            // Simple value-to-value reclassification
            else if(endValStr.isEmpty() || (Objects.equals(startValF, endValF)) && startExist && newExist)
                reclassifier.AddReclassValue(startValF, newValF);
            // Assgin a new value for a value within the range
            else if(startExist && endExist && newExist)
                reclassifier.AddReclassRange(startValF, endValF, newValF);
        }
        
        // Assign NoData for unclassified cells if True, or leave existing values if False
        reclassifier.setUseNoDataForMissingValues(chkNoData.isSelected());
        
        // Run the reclassification tool
        reclassifier.Generate(lp, lp.getExtent(), lp_reclass, useAltitudeMapZones);
        
        if(!useAltitudeMapZones)
            ApplyUniqueStyle(lp_reclass, "UniqueDeep");
        
        lp_reclass.getParams().getPixel().setGridShadow(false);
        lp_reclass.getParams().getPixel().setAntialias(false);
        
        GIS.Add(lp_reclass);
        GIS.InvalidateWholeMap();
    }//GEN-LAST:event_btnReclassifyActionPerformed
   
    private void OpenSampleForUseAltitudeZones(){
        TGIS_LayerPixel lp ;
        
        useAltitudeMapZones = true ;
        
        GIS.Lock(); 
        
        try {
            GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "Samples/3D/elevation.grd");
            lp = (TGIS_LayerPixel)GIS.getItems().getItems(0);
            ApplyNaturalBreaksStyle(lp, "Geology");
        } finally {
            GIS.Unlock(); 
        }
    }
    
    private void OpenSampleForReclassTable(){
        TGIS_LayerPixel lp ;
        
        useAltitudeMapZones = false ;
        
        GIS.Lock(); 
        
        try {
            GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "World\\Countries\\Luxembourg\\CLC2018_CLC2018_V2018_20_Luxembourg.tif");
            lp = (TGIS_LayerPixel)GIS.getItems().getItems(0);
            ApplyUniqueStyle(lp, "UniquePastel");
        } finally {
            GIS.Unlock(); 
        }
    }
    
    private void ApplyNaturalBreaksStyle( TGIS_LayerPixel _lp, String _colorRampName ){
        TGIS_ClassificationPixel classifier ;
        
        classifier = new TGIS_ClassificationPixel(_lp) ;
        classifier.setMethod(TGIS_ClassificationMethod.NaturalBreaks);
        classifier.setColorRampName(_colorRampName);
        
        classifier.Classify();
    }
    
    private void ApplyUniqueStyle( TGIS_LayerPixel _lp, String _colorRampName ){
        TGIS_ClassificationPixel classifier ;
        
        classifier = new TGIS_ClassificationPixel(_lp) ;
        classifier.setMethod(TGIS_ClassificationMethod.Unique);
        classifier.setColorRampName(_colorRampName);
        
        classifier.Classify();
    }
    
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ReclassificationUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReclassificationUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReclassificationUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReclassificationUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReclassificationUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private tatukgis.jdk.TGIS_ControlLegend GISLegend;
    private javax.swing.JButton btnReclassify;
    private javax.swing.JButton btnUseAltitudeZones;
    private javax.swing.JButton btnUseTable;
    private javax.swing.JCheckBox chkNoData;
    private javax.swing.JTextArea edtAltitudeZones;
    private javax.swing.JTextArea edtNoData;
    private javax.swing.JTextArea edtReclassTable;
    private javax.swing.JPanel grpbxReclassification;
    private javax.swing.JPanel pnlContainer;
    private javax.swing.JProgressBar progress;
    private javax.swing.JTable sgrdReclassTable;
    private javax.swing.JScrollPane spAltitudeZones;
    private javax.swing.JScrollPane spNoData;
    private javax.swing.JScrollPane spReclassTable;
    private javax.swing.JScrollPane spTable;
    // End of variables declaration//GEN-END:variables
    private Boolean useAltitudeMapZones ;
}
