package viewer;

import java.awt.Toolkit;
import tatukgis.jdk.TGIS_Extent;
import tatukgis.jdk.TGIS_FieldInfo;
import tatukgis.jdk.TGIS_FieldType;
import tatukgis.jdk.TGIS_Layer;
import tatukgis.jdk.TGIS_LayerAbstract;
import tatukgis.jdk.TGIS_LayerPixel;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.jdk.TGIS_ViewerWnd;

public class SearchUI extends javax.swing.JFrame {

    public SearchUI() {
        initComponents();
        initOwn();
    }
    
    public SearchUI(TGIS_ViewerWnd _gis){
        GIS = _gis;
        initComponents();
        initOwn(); 
    }

    private void initOwn(){
        int i,j;
        TGIS_Layer ll;
        TGIS_LayerVector lv;
        
        if(GIS.getIsEmpty()) return;
        
        cbFields.removeAllItems();
        cbLayers.removeAllItems();
        btnSearch.setEnabled(false);
        rbVisible.setSelected(true);
        
        for(i = 0; i< GIS.getItems().getCount(); i++){
            ll = (TGIS_Layer)GIS.getItems().getItems(i);
            if(!(ll instanceof TGIS_LayerVector))
                continue;
            lv = (TGIS_LayerVector)ll;
            if(lv.getName() == null)
                lv.setName("[empty layer name]");
            cbLayers.addItem(lv.getName());
            
        }
        
        if(cbLayers.getItemCount() > 0){
            cbLayers.setSelectedIndex(0);
            
            lv = (TGIS_LayerVector)GIS.getItems().getItems(cbLayers.getSelectedIndex());
            for(j = 0; j < lv.getFields().getCount() ; j++){
                if(lv.FieldInfo(j).getDeleted()) continue;
                cbFields.addItem(lv.FieldInfo(j).getNewName());
            }
            
            cbFields.setSelectedIndex(0);
            btnSearch.setEnabled(true);
            tpLayer.setText("Layer: " + cbLayers.getItemAt(cbLayers.getSelectedIndex()));
        }else{
            tpLayer.setText("There is no Vector layer in the Viewer");
        }
    }
    
    public void setGIS(TGIS_ViewerWnd _gis){
        GIS = _gis;
    }
    
    public TGIS_ViewerWnd getGIS(){
        return GIS;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        extent = new javax.swing.ButtonGroup();
        rbVisible = new javax.swing.JRadioButton();
        rbFull = new javax.swing.JRadioButton();
        btnSearch = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        cbLayers = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cbFields = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbOperations = new javax.swing.JComboBox<>();
        tbValue = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tpLayer = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Search");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));

        extent.add(rbVisible);
        rbVisible.setText("Visible extent");
        rbVisible.setName(""); // NOI18N

        extent.add(rbFull);
        rbFull.setText("Full extent");

        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        jLabel1.setText("Layers:");

        cbLayers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbLayersActionPerformed(evt);
            }
        });

        jLabel2.setText("Fields:");

        jLabel3.setText("Operation");

        jLabel4.setText("Value:");

        cbOperations.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "=", "<>", ">", "<", ">=", "<=" }));

        tpLayer.setEditable(false);
        jScrollPane1.setViewportView(tpLayer);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cbLayers, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(cbFields, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(26, 26, 26)
                                .addComponent(jLabel4))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbOperations, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tbValue, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbFull)
                    .addComponent(rbVisible)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rbVisible)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbFull))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbLayers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSearch)
                    .addComponent(tbValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbOperations, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbFields, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbLayersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbLayersActionPerformed
        int j;
        
        cbFields.removeAllItems();

        for( j = 0;  
                j < ((TGIS_LayerVector)GIS.Get(cbLayers.getItemAt(cbLayers.getSelectedIndex()))).getFields().getCount();
                j++){
            if(((TGIS_LayerVector)GIS.Get(cbLayers.getItemAt(cbLayers.getSelectedIndex()))).FieldInfo(j).getDeleted()) continue;
            cbFields.addItem(((TGIS_LayerVector)GIS.getItems().getItems(cbLayers.getSelectedIndex())).FieldInfo(j).getNewName());
        }
        if(cbFields.getItemCount() > 0)
            cbFields.setSelectedIndex(0);        

        tpLayer.setText("Layer: " + cbLayers.getItemAt(cbLayers.getSelectedIndex()));
        
        
    }//GEN-LAST:event_cbLayersActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        TGIS_Shape shp;
//        final TGIS_Shape shpTmp;
        TGIS_LayerVector ll;
        TGIS_Extent ext;
        TGIS_FieldInfo fld;
        String sql;
        
        ll = (TGIS_LayerVector)GIS.getItems().getItems(cbLayers.getSelectedIndex());
        
        if((ll == null) ||
                ((TGIS_LayerAbstract)ll instanceof TGIS_LayerPixel) ||
                tbValue.getText() == "") return;
        if(rbVisible.isSelected())
            ext = GIS.getVisibleExtent();
        else
            ext = TGIS_Utils.GisWholeWorld();

        fld = ll.FieldInfo(ll.FindField(cbFields.getItemAt(cbFields.getSelectedIndex())));
        if(fld == null) return;
        if(fld.getFieldType() == TGIS_FieldType.String){
            sql = cbFields.getItemAt(cbFields.getSelectedIndex()) +
                    cbOperations.getItemAt(cbOperations.getSelectedIndex()) +
                    "'" + tbValue.getText() + "'";
        }else{
            sql = cbFields.getItemAt(cbFields.getSelectedIndex()) +
                    cbOperations.getItemAt(cbOperations.getSelectedIndex()) +
                    tbValue.getText();
        }
        
        shp = ll.FindFirst(ext, sql);
        while(shp != null){
            shp.setIsSelected(true);
            shp = ll.FindNext();
        }
        GIS.InvalidateWholeMap();
    }//GEN-LAST:event_btnSearchActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SearchUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cbFields;
    private javax.swing.JComboBox<String> cbLayers;
    private javax.swing.JComboBox<String> cbOperations;
    private javax.swing.ButtonGroup extent;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbFull;
    private javax.swing.JRadioButton rbVisible;
    private javax.swing.JTextField tbValue;
    private javax.swing.JTextPane tpLayer;
    // End of variables declaration//GEN-END:variables
    private TGIS_ViewerWnd GIS;
}
