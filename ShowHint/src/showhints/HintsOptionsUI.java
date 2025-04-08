package showhints;

import tatukgis.jdk.TGIS_Layer;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_ViewerWnd;

public class HintsOptionsUI extends javax.swing.JFrame {
    public HintsOptionsUI() {
        initComponents();
    } 
    public HintsOptionsUI(String field, String layer, boolean display, TGIS_ViewerWnd wnd){
        hintField = field;
        hintLayer = layer;
        hintDisplay = display;
        wndClone = wnd;
        initComponents();
        initOwn();
    }
    private void initOwn(){
        int i;
        TGIS_Layer ll;
        
        chbDisplay.setSelected(hintDisplay);
        
        for ( i = 0 ; i < wndClone.getItems().getCount() ; i++){
            ll = (TGIS_Layer)wndClone.getItems().getItems(i);
            if ( ll instanceof TGIS_LayerVector) cbLayer.addItem(ll.getName());
            
            if ( cbLayer.getItemCount() <= 0 ) return;
            
            cbLayer.setSelectedIndex(0);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbLayer = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        lField = new javax.swing.JList<>();
        chbDisplay = new javax.swing.JCheckBox();
        btnCancel = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        cbLayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbLayerActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(lField);

        chbDisplay.setText("Show map hints");

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnOk.setText("Ok");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(chbDisplay)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 163, Short.MAX_VALUE)
                        .addComponent(btnOk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(cbLayer, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbLayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chbDisplay)
                    .addComponent(btnCancel)
                    .addComponent(btnOk))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbLayerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbLayerActionPerformed
        int j;
        TGIS_LayerVector lv;
        javax.swing.DefaultListModel<String> lm = new javax.swing.DefaultListModel<>();
        
        lv = (TGIS_LayerVector)wndClone.getItems().getItems(cbLayer.getSelectedIndex());
        for ( j = 0 ; j < lv.getFields().getCount() ; j++ ){
            if ( lv.FieldInfo(j).getDeleted()) continue;
            lm.addElement(lv.FieldInfo(j).getNewName());
        }
        lField.setModel(lm);
    }//GEN-LAST:event_cbLayerActionPerformed

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        hintField = lField.getSelectedValue();
        hintLayer = cbLayer.getSelectedItem().toString();
        dispose();
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        hintField = null;
        hintLayer = null;
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed
    
    public String getHintField(){
        return hintField;
    }

    public String getHintLayer(){
        return hintLayer;
    }
    
    public boolean getHintDisplay(){
        return hintDisplay;
    }
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HintsOptionsUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private javax.swing.JComboBox<String> cbLayer;
    private javax.swing.JCheckBox chbDisplay;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> lField;
    // End of variables declaration//GEN-END:variables
    private String hintField;
    private String hintLayer;
    private boolean hintDisplay;
    private TGIS_ViewerWnd wndClone;
}
