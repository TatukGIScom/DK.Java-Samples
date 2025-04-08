package viewer;

import java.awt.Toolkit;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import tatukgis.jdk.TGIS_CSCoordinateSystem;
import tatukgis.jdk.TGIS_Extent;
import tatukgis.jdk.TGIS_FileType;
import tatukgis.jdk.TGIS_Layer;
import tatukgis.jdk.TGIS_LayerDXF;
import tatukgis.jdk.TGIS_LayerMIF;
import tatukgis.jdk.TGIS_LayerSHP;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_ShapeType;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.jdk.TGIS_ViewerWnd;

public class ExportUI extends javax.swing.JFrame {

    public ExportUI() {
        initComponents();
        initOwn();
    }
    
    public ExportUI(TGIS_ViewerWnd _gis) {
        GIS = _gis;
        initComponents();
        initOwn();
    }    
    
    private void initOwn(){
        int i;
        TGIS_Layer ll;
        String supportedExports;
        
        supportedExports = "Arcview Shape File_.shp-Autocad_.DXF-Mapinfo Interchange_.MIF-TatukGIS SQL Layer_.TTKLS" ;
        
        for(String tmp : (supportedExports.split("-"))){
            fcExport.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
                @Override
                public boolean accept(File file) {
                    if((tmp.split("_")[1]).contains(getFileExtension(file)))
                        return true;
                    else
                        return false;
                }

                @Override
                public String getDescription() {
                    return tmp.split("_")[0];
                }
            });                
        }
        
        cbLayer.removeAllItems();
        
        for(i = GIS.getItems().getCount() - 1 ; i >= 0; i--){
            ll = (TGIS_Layer)GIS.getItems().getItems(i);
            
            if(ll instanceof TGIS_LayerVector)
                cbLayer.addItem(ll.getName());
        }
        
        cbLayer.setSelectedIndex(0);
        cbShape.setSelectedIndex(0);
        rbWhole.setSelected(true);
        
        if((TGIS_Layer)GIS.Get(cbLayer.getSelectedItem().toString()) != null){
            CS = ((TGIS_Layer)GIS.Get(cbLayer.getSelectedItem().toString())).getCS();
            tbCS.setText(CS.getWKT());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgExtent = new javax.swing.ButtonGroup();
        fcExport = new javax.swing.JFileChooser();
        lbLayer = new javax.swing.JLabel();
        cbLayer = new javax.swing.JComboBox<>();
        lbExtent = new javax.swing.JLabel();
        rbWhole = new javax.swing.JRadioButton();
        rbTouched = new javax.swing.JRadioButton();
        rbClipped = new javax.swing.JRadioButton();
        lbShape = new javax.swing.JLabel();
        cbShape = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        lbQuery = new javax.swing.JLabel();
        tbQuery = new javax.swing.JTextField();
        lbCS = new javax.swing.JLabel();
        tbCS = new javax.swing.JTextField();
        btnCS = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Export");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));

        lbLayer.setText("Select layer to import from");

        cbLayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbLayerActionPerformed(evt);
            }
        });

        lbExtent.setText("Select extent");

        bgExtent.add(rbWhole);
        rbWhole.setText("Whole extent");

        bgExtent.add(rbTouched);
        rbTouched.setText("Touched by visible extent");

        bgExtent.add(rbClipped);
        rbClipped.setText("Clipped by visible extent");

        lbShape.setText("Select shape type");

        cbShape.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Any supported shape", "Only Arcs (lines)", "Only Polygons (areas)", "Only Points (markers)", "Only Multipoints" }));

        lbQuery.setText("Query statement");

        lbCS.setText("CS");

        btnCS.setText("...");
        btnCS.setToolTipText("ControlCS not implemented yet");
        btnCS.setEnabled(false);

        btnOk.setText("OK");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbShape, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2)
                    .addComponent(jSeparator1)
                    .addComponent(cbLayer, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tbQuery)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbClipped)
                            .addComponent(rbTouched)
                            .addComponent(rbWhole)
                            .addComponent(lbExtent)
                            .addComponent(lbShape)
                            .addComponent(lbLayer)
                            .addComponent(lbQuery)
                            .addComponent(lbCS))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tbCS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCS)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addComponent(btnOk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel)
                .addContainerGap(98, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbLayer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbLayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbExtent)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbWhole)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbTouched)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbClipped)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbShape)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbShape, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbQuery)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tbQuery, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbCS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tbCS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCS))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOk)
                    .addComponent(btnCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbLayerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbLayerActionPerformed
        CS = ((TGIS_Layer)GIS.Get(cbLayer.getSelectedItem().toString())).getCS();
        tbCS.setText(CS.getWKT());
    }//GEN-LAST:event_cbLayerActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        TGIS_LayerVector lv;
        TGIS_Extent extent;
        int shp_type;
        TGIS_FileType asd;
        boolean clipping;
        String ext;
        
        int returnVal = fcExport.showSaveDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            ext = getFileExtension(fcExport.getSelectedFile());
            
            if(ext.toLowerCase().contains("shp"))
                lv = new TGIS_LayerSHP();
            else if(ext.toLowerCase().contains("mif"))
                lv = new TGIS_LayerMIF();
            else if(ext.toLowerCase().contains("dxf"))
                lv = new TGIS_LayerDXF();
            else if(ext.toLowerCase().contains("ttkls"))
                lv = (TGIS_LayerVector)TGIS_Utils.GisCreateLayer("", fcExport.getSelectedFile().getName());
            else{
                JOptionPane.showMessageDialog(this,
                    "Unrecognized file extension",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            shp_type = TGIS_ShapeType.Unknown;
            extent = TGIS_Utils.GisWholeWorld();
            clipping = false;
            
            if(rbWhole.isSelected()){
                extent = TGIS_Utils.GisWholeWorld();
                clipping = false;
            }else if(rbTouched.isSelected()){
                extent = GIS.getVisibleExtent();
                clipping = false;
            }else if(rbClipped.isSelected()){
                extent = GIS.getVisibleExtent();
                clipping = true;
            }
            
            switch(cbShape.getSelectedIndex()){
                default :
                    JOptionPane.showMessageDialog(this,
                        "Untested shape type case.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    break;
                case 0 : shp_type = TGIS_ShapeType.Unknown ; break ;
                case 1 : shp_type = TGIS_ShapeType.Arc ; break ;
                case 2 : shp_type = TGIS_ShapeType.Polygon ; break ;
                case 3 : shp_type = TGIS_ShapeType.Point ; break ;
                case 4 : shp_type = TGIS_ShapeType.MultiPoint ; break ;
            }
            
            lv.setPath(fcExport.getSelectedFile().getAbsolutePath());
            lv.setCS(CS);
            lv.ImportLayer((TGIS_LayerVector)GIS.Get(cbLayer.getSelectedItem().toString()), 
                    extent,
                    shp_type,
                    tbQuery.getText(),
                    clipping);
            
        }
    }//GEN-LAST:event_btnOkActionPerformed
    
    public void setGIS(TGIS_ViewerWnd _gis){
        GIS = _gis;
    }
    
    public TGIS_ViewerWnd getGIS(){
        return GIS;
    }
    
    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
    
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExportUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgExtent;
    private javax.swing.JButton btnCS;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private javax.swing.JComboBox<String> cbLayer;
    private javax.swing.JComboBox<String> cbShape;
    private javax.swing.JFileChooser fcExport;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lbCS;
    private javax.swing.JLabel lbExtent;
    private javax.swing.JLabel lbLayer;
    private javax.swing.JLabel lbQuery;
    private javax.swing.JLabel lbShape;
    private javax.swing.JRadioButton rbClipped;
    private javax.swing.JRadioButton rbTouched;
    private javax.swing.JRadioButton rbWhole;
    private javax.swing.JTextField tbCS;
    private javax.swing.JTextField tbQuery;
    // End of variables declaration//GEN-END:variables
    private TGIS_ViewerWnd GIS;
    private TGIS_CSCoordinateSystem CS;
}
