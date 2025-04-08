package pixeloperations;

import RemObjects.Elements.System.VarParameter;
import java.awt.Toolkit;
import javax.swing.JFileChooser;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_Extent;
import tatukgis.jdk.TGIS_LayerPixel;
import tatukgis.jdk.TGIS_PixelOperation;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.jdk.TGIS_ViewerMode;

public class PixelOperationsUI extends javax.swing.JFrame {

    public PixelOperationsUI() {
        initComponents();
        initOwn();
    }

    private void initOwn(){
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/USA/States/California/San Bernardino/DOQ/37134877.jpg");
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        jToolBar1 = new javax.swing.JToolBar();
        btnOpen = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnFullExtent = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnZoom = new javax.swing.JButton();
        btnDrag = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        chbPixels = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tGIS_ControlLegend1 = new tatukgis.jdk.TGIS_ControlLegend();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PixelOperations - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(816, 750));

        jToolBar1.setRollover(true);

        btnOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Open.png"))); // NOI18N
        btnOpen.setFocusable(false);
        btnOpen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOpen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });
        jToolBar1.add(btnOpen);
        jToolBar1.add(jSeparator1);

        btnFullExtent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/FullExtent.png"))); // NOI18N
        btnFullExtent.setFocusable(false);
        btnFullExtent.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFullExtent.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnFullExtent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFullExtentActionPerformed(evt);
            }
        });
        jToolBar1.add(btnFullExtent);
        jToolBar1.add(jSeparator2);

        btnZoom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Zoom.png"))); // NOI18N
        btnZoom.setFocusable(false);
        btnZoom.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnZoom.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnZoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZoomActionPerformed(evt);
            }
        });
        jToolBar1.add(btnZoom);

        btnDrag.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Drag.png"))); // NOI18N
        btnDrag.setFocusable(false);
        btnDrag.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDrag.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDrag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDragActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDrag);
        jToolBar1.add(jSeparator3);

        chbPixels.setText("Change pixels");
        chbPixels.setFocusable(false);
        chbPixels.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        chbPixels.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        chbPixels.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbPixelsActionPerformed(evt);
            }
        });
        jToolBar1.add(chbPixels);

        tGIS_ControlLegend1.setGIS_Viewer(GIS);
        jScrollPane1.setViewportView(tGIS_ControlLegend1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 692, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFullExtentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFullExtentActionPerformed
        GIS.FullExtent();
    }//GEN-LAST:event_btnFullExtentActionPerformed

    private void btnZoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZoomActionPerformed
        GIS.setMode(TGIS_ViewerMode.Zoom);
    }//GEN-LAST:event_btnZoomActionPerformed

    private void btnDragActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDragActionPerformed
        GIS.setMode(TGIS_ViewerMode.Drag);
    }//GEN-LAST:event_btnDragActionPerformed

    private void chbPixelsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbPixelsActionPerformed
        TGIS_LayerPixel lp;
        
        if(GIS.getIsEmpty()) return;
        
        lp = (TGIS_LayerPixel)GIS.getItems().getItems(0);
        if(chbPixels.isSelected()) {
            lp.setPixelOperationEvent(new TGIS_PixelOperation() {
                @Override
                public boolean Invoke(Object o, TGIS_Extent tgise, int[] ints, VarParameter<int[]> vps, int i, int i1) {
                    int rmaxval, rminval;
                    int gmaxval, gminval;
                    int bmaxval, bminval;
                    int rdelta;
                    int gdelta;
                    int bdelta;
                    int r,g,b;
                    int j;
                    TGIS_Color pixval;
                    
                    pixval = new TGIS_Color();
                    
                    rmaxval = -1000;
                    rminval =  1000;
                    gmaxval = -1000;
                    gminval =  1000;
                    bmaxval = -1000;
                    bminval =  1000;
                    for ( j = 0; j < ints.length - 1 ; j++){
                        pixval.ARGB = ints[j];
                        r = pixval.getR() & 0xFF;
                        g = pixval.getG() & 0xFF;
                        b = pixval.getB() & 0xFF;
                        
                        if (r > rmaxval)
                            rmaxval = r;
                        if (g > gmaxval)
                            gmaxval = g;
                        if (b > bmaxval)
                            bmaxval = b;
                        
                        if (r < rminval)
                            rminval = r;
                        if (g < gminval)
                            gminval = g;
                        if (b < bminval)
                            bminval = b;
                    }
                    rdelta = Math.max(1,rmaxval -rminval);
                    gdelta = Math.max(1,gmaxval -gminval);
                    bdelta = Math.max(1,bmaxval -bminval);
                    
                    for ( j = 0; j < ints.length - 1; j++){
                        pixval.ARGB = ints[j];
                        r = pixval.getR() & 0xFF;
                        g = pixval.getG() & 0xFF;
                        b = pixval.getB() & 0xFF;
                        r = (int) ((((double)(r - rminval) / rdelta))*255);
                        g = (int) ((((double)(g - gminval) / gdelta))*255);
                        b = (int) ((((double)(b - bminval) / bdelta))*255);
                        pixval = TGIS_Color.FromARGB((byte) 0xFF, (byte)r, (byte)g, (byte)b);
                        vps.Value[j] = pixval.ARGB;
                    }       
                    return true;
                }
            });
        } else {
            lp.setPixelOperationEvent(null);
        }
        GIS.InvalidateWholeMap();
    }//GEN-LAST:event_chbPixelsActionPerformed

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        if(evt.getSource() == btnOpen){
            int returnVal = fc.showOpenDialog(GIS);
            if(returnVal == JFileChooser.APPROVE_OPTION){
                String filePath = fc.getSelectedFile().toString();		
                GIS.Open(filePath);
                GIS.InvalidateWholeMap();
                GIS.FullExtent();
            }
        }
    }//GEN-LAST:event_btnOpenActionPerformed

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
            java.util.logging.Logger.getLogger(PixelOperationsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PixelOperationsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PixelOperationsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PixelOperationsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PixelOperationsUI().setVisible(true);
            }
        });
    }
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.JButton btnDrag;
    private javax.swing.JButton btnFullExtent;
    private javax.swing.JButton btnOpen;
    private javax.swing.JButton btnZoom;
    private javax.swing.JCheckBox chbPixels;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar jToolBar1;
    private tatukgis.jdk.TGIS_ControlLegend tGIS_ControlLegend1;
    // End of variables declaration//GEN-END:variables
    private final JFileChooser fc = new JFileChooser();
}
