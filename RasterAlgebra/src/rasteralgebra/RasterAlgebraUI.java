package rasteralgebra;

import java.awt.Toolkit;
import javax.swing.JOptionPane;
import tatukgis.jdk.TGIS_BusyEvent;
import tatukgis.jdk.TGIS_BusyEventArgs;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_Layer;
import tatukgis.jdk.TGIS_LayerPixel;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_RasterAlgebra;
import tatukgis.jdk.TGIS_Utils;

public class RasterAlgebraUI extends javax.swing.JFrame {

    public RasterAlgebraUI() {        
        initComponents();
        initOwn();
    }
    
    private void initOwn(){

    };
    
    private void applyRamp(TGIS_LayerPixel lp){
        lp.GenerateRamp(
            TGIS_Color.getBlue(), TGIS_Color.getLime(), TGIS_Color.getRed(),
            1.0 * Math.floor(lp.getMinHeight()),
            (lp.getMaxHeight() + lp.getMinHeight()) / 2.0,
            1.0 * Math.ceil(lp.getMaxHeight()), true,
            (lp.getMaxHeight() - lp.getMinHeight()) / 100.0,
            (lp.getMaxHeight() - lp.getMinHeight()) / 10.0,
            null, false
        );

        lp.getParams().getPixel().setGridShadow(false);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rbgResultType = new javax.swing.ButtonGroup();
        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        lblSrc = new javax.swing.JLabel();
        lblResultType = new javax.swing.JLabel();
        btnPixel = new javax.swing.JButton();
        btnGrid = new javax.swing.JButton();
        btnVector = new javax.swing.JButton();
        rbResultPixel = new javax.swing.JRadioButton();
        rbResultGrid = new javax.swing.JRadioButton();
        lblResult = new javax.swing.JLabel();
        tfFormula = new javax.swing.JTextField();
        btnExecute = new javax.swing.JButton();
        pbProgress = new javax.swing.JProgressBar();
        tGIS_ControlLegend1 = new tatukgis.jdk.TGIS_ControlLegend();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("RasterAlgebra - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(800, 600));

        lblSrc.setText("Choose source:");

        lblResultType.setText("Result type:");

        btnPixel.setText("Open pixel");
        btnPixel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPixelActionPerformed(evt);
            }
        });

        btnGrid.setText("Open grid");
        btnGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGridActionPerformed(evt);
            }
        });

        btnVector.setText("Open vector");
        btnVector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVectorActionPerformed(evt);
            }
        });

        rbgResultType.add(rbResultPixel);
        rbResultPixel.setSelected(true);
        rbResultPixel.setText("Pixel");

        rbgResultType.add(rbResultGrid);
        rbResultGrid.setText("Grid");

        lblResult.setText("Result = ");

        btnExecute.setText("Execute");
        btnExecute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExecuteActionPerformed(evt);
            }
        });

        tGIS_ControlLegend1.setGIS_Viewer(GIS);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pbProgress, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSrc)
                            .addComponent(lblResultType)
                            .addComponent(lblResult))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tfFormula)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnExecute))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnPixel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnGrid)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnVector))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(rbResultPixel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rbResultGrid)))
                                .addGap(0, 407, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tGIS_ControlLegend1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPixel)
                    .addComponent(lblSrc)
                    .addComponent(btnGrid)
                    .addComponent(btnVector))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblResultType)
                    .addComponent(rbResultPixel)
                    .addComponent(rbResultGrid))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblResult)
                    .addComponent(tfFormula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExecute))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pbProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tGIS_ControlLegend1, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPixelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPixelActionPerformed
            TGIS_LayerPixel lp;
            String path;

            GIS.Close();

            path = TGIS_Utils.GisSamplesDataDirDownload() + "\\World\\Countries\\USA\\States\\California\\San Bernardino\\DOQ\\37134877.jpg";

            lp = (TGIS_LayerPixel)TGIS_Utils.GisCreateLayer("Pixel", path);
            GIS.Add(lp);
            GIS.FullExtent();

            rbResultPixel.setSelected(true);
            tfFormula.setText("RGB(255 - pixel.R, 255 - pixel.G, 255 - pixel.B)");
    }//GEN-LAST:event_btnPixelActionPerformed

    private void btnGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGridActionPerformed
            TGIS_LayerPixel lp;
            String path;

            GIS.Close();

            path = TGIS_Utils.GisSamplesDataDirDownload() + "\\World\\Countries\\USA\\States\\California\\San Bernardino\\NED\\w001001.adf";

            lp = (TGIS_LayerPixel)TGIS_Utils.GisCreateLayer("Grid", path);
            lp.setUseConfig(false);
            GIS.Add(lp);
            applyRamp(lp);
            GIS.FullExtent();

            rbResultGrid.setSelected(true);
            tfFormula.setText("IF(grid < AVG(grid), MIN(grid), MAX(grid))");
    }//GEN-LAST:event_btnGridActionPerformed

    private void btnVectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVectorActionPerformed
            TGIS_LayerVector lv;
            String path;

            GIS.Close();

            path = TGIS_Utils.GisSamplesDataDirDownload() + "\\World\\Countries\\USA\\States\\California\\San Bernardino\\TIGER\\tl_2008_06071_edges_trunc.shp";

            lv = (TGIS_LayerVector)TGIS_Utils.GisCreateLayer("Vector", path);
            lv.setUseConfig(false);
            GIS.Add(lv);
            GIS.FullExtent();

            rbResultPixel.setSelected(true);
            tfFormula.setText("IF(NODATA(vector.GIS_UID), RGB(0,255,0), RGB(255,0,0))");
    }//GEN-LAST:event_btnVectorActionPerformed

    private void btnExecuteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExecuteActionPerformed
            TGIS_LayerPixel src;
            TGIS_LayerPixel dst;
            TGIS_RasterAlgebra ra;
            double gew;
            double lew;
            int w;
            int siz;
            int i;
            
            if(GIS.getIsEmpty()){
                JOptionPane.showMessageDialog(this,
                    "The viewer is empty!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if(GIS.Get(SAMPLE_RESULT) != null){
                GIS.Delete(SAMPLE_RESULT);
            }
            
            gew = GIS.getExtent().XMax - GIS.getExtent().XMin;
            
            src = null;
            siz = 0;
            
            for(i = 0; i < GIS.getItems().getCount(); i++){
                if(GIS.getItems().get(i) instanceof TGIS_LayerPixel){
                    src = (TGIS_LayerPixel)GIS.getItems().get(i);
                    lew = src.getExtent().XMax - src.getExtent().XMin;
                    w = (int)Math.round(gew*src.getBitWidth()/lew);
                    siz = Math.max(w, siz);
                }
                
                dst = new TGIS_LayerPixel();
                
                if(src != null){
                    dst.Build(rbResultGrid.isSelected(), GIS.getCS(), GIS.getExtent(), siz, 0);
                }else{
                    dst.Build(rbResultGrid.isSelected(), GIS.getCS(), GIS.getExtent(), GIS.getWidth(), 0);
                }
                
                dst.setName(SAMPLE_RESULT);
                
                GIS.Add(dst);
                
                ra = new TGIS_RasterAlgebra();
                ra.setBusyEvent(new TGIS_BusyEvent() {
                    @Override
                    public void Invoke(Object o, TGIS_BusyEventArgs tgsb) {
                        if(tgsb.getPos() < 0){
                            pbProgress.setValue(pbProgress.getMaximum());
                        }else if(tgsb.getPos() == 0){
                            pbProgress.setMinimum(0);
                            pbProgress.setMaximum((int)tgsb.getEndPos());
                            pbProgress.setValue(0);
                        }else{
                            if(tgsb.getPos() > 0){
                                pbProgress.setValue((int)tgsb.getPos());
                            }
                        }
                    }
                });
                
                for(i = 0; i < GIS.getItems().getCount(); i++){
                    ra.AddLayer((TGIS_Layer)GIS.getItems().get(i));
                }
                
                
                final TGIS_RasterAlgebra raTmp = ra ;
                final TGIS_LayerPixel dstTmp = dst ;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            raTmp.Execute(SAMPLE_RESULT + "=" + tfFormula.getText());
                        }catch(Exception e){
                            GIS.Delete(SAMPLE_RESULT);
                            JOptionPane.showMessageDialog(null,
                                "Incorrect formula!",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        }

                        if(dstTmp.IsGrid()){
                            applyRamp(dstTmp);
                        }

                        GIS.InvalidateWholeMap();
                    }
                }).start();
            }
    }//GEN-LAST:event_btnExecuteActionPerformed
    
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
            java.util.logging.Logger.getLogger(RasterAlgebraUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RasterAlgebraUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RasterAlgebraUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RasterAlgebraUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RasterAlgebraUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.JButton btnExecute;
    private javax.swing.JButton btnGrid;
    private javax.swing.JButton btnPixel;
    private javax.swing.JButton btnVector;
    private javax.swing.JLabel lblResult;
    private javax.swing.JLabel lblResultType;
    private javax.swing.JLabel lblSrc;
    private javax.swing.JProgressBar pbProgress;
    private javax.swing.JRadioButton rbResultGrid;
    private javax.swing.JRadioButton rbResultPixel;
    private javax.swing.ButtonGroup rbgResultType;
    private tatukgis.jdk.TGIS_ControlLegend tGIS_ControlLegend1;
    private javax.swing.JTextField tfFormula;
    // End of variables declaration//GEN-END:variables
    private final String SAMPLE_RESULT = "Result";
}
