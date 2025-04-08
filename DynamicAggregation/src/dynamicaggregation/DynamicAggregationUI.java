package dynamicaggregation;

import java.awt.Toolkit;
import tatukgis.jdk.TGIS_DynamicAggregatorFactory;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_Utils;

public class DynamicAggregationUI extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    public DynamicAggregationUI() {   
        initComponents();
        initOwn();
    }
    
    private void initOwn(){
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "\\Samples\\Aggregation\\Aggregation.ttkproject");
        cbxMethod.addItem("Off");
        
        for(int i = 0; i < TGIS_DynamicAggregatorFactory.getNames().getCount(); i++){
            cbxMethod.addItem(TGIS_DynamicAggregatorFactory.getNames().getStrings(i));
        }
        
        cbxMethod.setSelectedIndex(0);
        cbxRadius.setEnabled(false);
        cbxThreshhold.setEnabled(false);
    };
    
    private void changeAggregation(){
        String dyn_agg_name = cbxMethod.getSelectedItem().toString();
        TGIS_LayerVector lv = (TGIS_LayerVector)GIS.Get("cities");
        lv.setTransparency(70);
        
        if( dyn_agg_name.equals("Off") ){
            cbxRadius.setEnabled(false);
            cbxThreshhold.setEnabled(false);
            lv.setDynamicAggregator(null);
        }else{
            cbxRadius.setEnabled(true);
            cbxThreshhold.setEnabled(true);
            lv.setDynamicAggregator(TGIS_DynamicAggregatorFactory.CreateInstance(dyn_agg_name, lv));
            lv.getDynamicAggregator().setRadiusAsText("SIZE: " + cbxRadius.getSelectedItem().toString());
            lv.getDynamicAggregator().setThreshold(Integer.parseInt(cbxThreshhold.getSelectedItem().toString()));
        }
        
        GIS.InvalidateWholeMap();
    }
    
    private void readDeafaultValues(){
        if( cbxMethod.getSelectedItem().equals("ShapeReduction") ) {
            cbxRadius.setSelectedIndex(0);
        } else {
            cbxRadius.setSelectedIndex(3);
        }
    }
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        pMenu = new javax.swing.JPanel();
        lblMethod = new javax.swing.JLabel();
        cbxMethod = new javax.swing.JComboBox<>();
        lblRadius = new javax.swing.JLabel();
        cbxRadius = new javax.swing.JComboBox<>();
        lblThreshhold = new javax.swing.JLabel();
        cbxThreshhold = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DynamicAggregation - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setPreferredSize(new java.awt.Dimension(1000, 600));
        setSize(new java.awt.Dimension(1000, 600));

        GIS.setMode(tatukgis.jdk.TGIS_ViewerMode.Zoom);

        lblMethod.setText("Aggregation method:");

        cbxMethod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxMethodActionPerformed(evt);
            }
        });

        lblRadius.setText("Radius:");

        cbxRadius.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5 pt", "10 pt", "20 pt", "40 pt", "80 pt", " " }));
        cbxRadius.setSelectedIndex(3);
        cbxRadius.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxRadiusActionPerformed(evt);
            }
        });

        lblThreshhold.setText("Threshhold:");

        cbxThreshhold.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "1", "2", "5", "10" }));
        cbxThreshhold.setSelectedIndex(1);
        cbxThreshhold.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxThreshholdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pMenuLayout = new javax.swing.GroupLayout(pMenu);
        pMenu.setLayout(pMenuLayout);
        pMenuLayout.setHorizontalGroup(
            pMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxMethod, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxRadius, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pMenuLayout.createSequentialGroup()
                        .addGroup(pMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMethod)
                            .addComponent(lblRadius)
                            .addComponent(lblThreshhold))
                        .addGap(0, 96, Short.MAX_VALUE))
                    .addComponent(cbxThreshhold, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pMenuLayout.setVerticalGroup(
            pMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMethod)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRadius)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxRadius, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblThreshhold)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxThreshhold, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(422, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleName("DynamicAggregation - TatukGIS DK11 sample");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cbxMethodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxMethodActionPerformed
        readDeafaultValues();
        changeAggregation();
    }//GEN-LAST:event_cbxMethodActionPerformed

    private void cbxRadiusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxRadiusActionPerformed
        changeAggregation();
    }//GEN-LAST:event_cbxRadiusActionPerformed

    private void cbxThreshholdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxThreshholdActionPerformed
        changeAggregation();
    }//GEN-LAST:event_cbxThreshholdActionPerformed
    
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
            java.util.logging.Logger.getLogger(DynamicAggregationUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DynamicAggregationUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DynamicAggregationUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DynamicAggregationUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new DynamicAggregationUI().setVisible(true);
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.JComboBox<String> cbxMethod;
    private javax.swing.JComboBox<String> cbxRadius;
    private javax.swing.JComboBox<String> cbxThreshhold;
    private javax.swing.JLabel lblMethod;
    private javax.swing.JLabel lblRadius;
    private javax.swing.JLabel lblThreshhold;
    private javax.swing.JPanel pMenu;
    // End of variables declaration//GEN-END:variables
}
