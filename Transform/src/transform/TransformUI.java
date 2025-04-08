package transform;

import java.awt.Toolkit;
import tatukgis.jdk.TGIS_LayerPixel;
import tatukgis.jdk.TGIS_PolynomialOrder;
import tatukgis.jdk.TGIS_TransformPolynomial;
import tatukgis.jdk.TGIS_Utils;

public class TransformUI extends javax.swing.JFrame {

    public TransformUI() {
        initComponents();
        initOwn();
    }

    private void initOwn(){
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/Samples/Rectify/satellite.jpg");
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        btnTransform = new javax.swing.JButton();
        btnCutting = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnRead = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Transform - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(800, 600));

        btnTransform.setText("Transform");
        btnTransform.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTransformActionPerformed(evt);
            }
        });

        btnCutting.setText("Cutting polygon");
        btnCutting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCuttingActionPerformed(evt);
            }
        });

        btnSave.setText("Save to file");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnRead.setText("Read from file");
        btnRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCutting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTransform, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRead, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTransform)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCutting)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRead)
                .addContainerGap(479, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTransformActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTransformActionPerformed
        TGIS_TransformPolynomial trn;
        TGIS_LayerPixel lp;
        
        lp = (TGIS_LayerPixel)GIS.getItems().getItems(0);
        
        trn = new TGIS_TransformPolynomial();

        trn.AddPoint(TGIS_Utils.GisPoint(-0.5, -944.5),
                      TGIS_Utils.GisPoint(1273285.84090909, 239703.615056818),
                      0,
                      true
                     );
        trn.AddPoint(TGIS_Utils.GisPoint(-0.5, 0.5),
                      TGIS_Utils.GisPoint(1273285.84090909, 244759.524147727),
                      1,
                      true
                     );
        trn.AddPoint(TGIS_Utils.GisPoint(1246.5, 0.5),
                      TGIS_Utils.GisPoint(1279722.65909091, 245859.524147727),
                      2,
                      true
                     );
        trn.AddPoint(TGIS_Utils.GisPoint(1246.5, -944.5),
                      TGIS_Utils.GisPoint(1279744.93181818, 239725.887784091),
                      3,
                      true
                     );
        trn.Prepare(TGIS_PolynomialOrder.First);
        
        lp.setTransform(trn);
        lp.getTransform().setActive(true);
        lp.SetCSByEPSG( 102748 );
        GIS.RecalcExtent();
        GIS.FullExtent();
    }//GEN-LAST:event_btnTransformActionPerformed

    private void btnCuttingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCuttingActionPerformed
        TGIS_TransformPolynomial trn;
        TGIS_LayerPixel lp;
        
     
        lp = (TGIS_LayerPixel)GIS.getItems().getItems(0);

        trn = new TGIS_TransformPolynomial();
        trn.AddPoint(TGIS_Utils.GisPoint(-0.5, -944.5),
                      TGIS_Utils.GisPoint(1273285.84090909, 239703.615056818),
                      0,
                      true
                     );
        trn.AddPoint(TGIS_Utils.GisPoint(-0.5, 0.5),
                      TGIS_Utils.GisPoint(1273285.84090909, 244759.524147727),
                      1,
                      true
                     );
        trn.AddPoint(TGIS_Utils.GisPoint(1246.5, 0.5),
                      TGIS_Utils.GisPoint(1279722.65909091, 244759.524147727),
                      2,
                      true
                     );
        trn.AddPoint(TGIS_Utils.GisPoint(1246.5, -944.5),
                      TGIS_Utils.GisPoint(1279744.93181818, 239725.887784091),
                      3,
                      true
                     );
        trn.setCuttingPolygon("POLYGON((421.508902077151 -320.017804154303, " +
                             "518.161721068249 -223.364985163205, " +
                             "688.725519287834 -210.572700296736, " +
                             "864.974777448071 -254.635014836795, " +
                             "896.244807121662 -335.652818991098, " +
                             "894.823442136499 -453.626112759644, " +
                             "823.755192878338 -615.661721068249, " +
                             "516.740356083086 -607.13353115727,  " +
                             "371.761127596439 -533.222551928783, " +
                             "340.491097922849 -456.46884272997,  " +
                             "421.508902077151 -320.017804154303))" ) ;

        trn.Prepare(TGIS_PolynomialOrder.First);
        lp.setTransform(trn);
        lp.getTransform().setActive(true);
        GIS.RecalcExtent();
        GIS.FullExtent();
    }//GEN-LAST:event_btnCuttingActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        TGIS_LayerPixel lp;

        lp = (TGIS_LayerPixel)GIS.getItems().getItems(0);

        if (lp.getTransform() != null)
            lp.getTransform().SaveToFile("satellite.jpg" + GIS_TRN_EXT);
        
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReadActionPerformed
        TGIS_TransformPolynomial trn;
        TGIS_LayerPixel lp;

        lp = (TGIS_LayerPixel)GIS.getItems().getItems(0);
        
        if(lp.getTransform() != null)
            lp.getTransform().LoadFromFile("satellite.jpg" + GIS_TRN_EXT);
        else{
            trn = new TGIS_TransformPolynomial();
            trn.LoadFromFile("satellite.jpg" + GIS_TRN_EXT);
            lp.setTransform(trn);
        }
        
        lp.getTransform().setActive(true);

        GIS.RecalcExtent();
        GIS.FullExtent();
    }//GEN-LAST:event_btnReadActionPerformed

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
            java.util.logging.Logger.getLogger(TransformUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TransformUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TransformUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TransformUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TransformUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.JButton btnCutting;
    private javax.swing.JButton btnRead;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnTransform;
    // End of variables declaration//GEN-END:variables
    private String GIS_TRN_EXT = ".trn";
}
