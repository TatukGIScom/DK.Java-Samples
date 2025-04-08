package legend;

import java.awt.Toolkit;
import tatukgis.jdk.TGIS_ControlLegendMode;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.jdk.TGIS_ViewerMode;

public class LegendUI extends javax.swing.JFrame {

    public LegendUI() {  
        initComponents();
        initOwn();
    }
    
    private void initOwn(){
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/Poland/DCW/poland.ttkproject");    
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        btnFullExtent = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnZoom = new javax.swing.JButton();
        btnDrag = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnSave = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        GIS_Legend = new tatukgis.jdk.TGIS_ControlLegend();
        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lScale = new javax.swing.JLabel();
        bntGroups = new javax.swing.JButton();
        btnLayers = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Legend - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

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
        jToolBar1.add(jSeparator1);

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
        jToolBar1.add(jSeparator2);

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Save.png"))); // NOI18N
        btnSave.setFocusable(false);
        btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSave);

        GIS_Legend.setGIS_Viewer(GIS);
        jScrollPane1.setViewportView(GIS_Legend);

        GIS.addPaintListener(new tatukgis.jdk.PaintListener() {
            public void BeforePaint(tatukgis.jdk.PaintEvent evt) {
            }
            public void BeforePaintRenderer(tatukgis.jdk.RenderEvent evt) {
            }
            public void PaintExtra(tatukgis.jdk.RenderEvent evt) {
            }
            public void AfterPaint(tatukgis.jdk.PaintEvent evt) {
                GISAfterPaint(evt);
            }
            public void AfterPaintRenderer(tatukgis.jdk.RenderEvent evt) {
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Scale:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lScale)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(lScale))
        );

        bntGroups.setText("Groups");
        bntGroups.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntGroupsActionPerformed(evt);
            }
        });

        btnLayers.setText("Layers");
        btnLayers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLayersActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnLayers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bntGroups, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bntGroups)
                            .addComponent(btnLayers))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if(GIS.getIsEmpty())
            return;
        GIS.SaveAll();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnLayersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLayersActionPerformed
        GIS_Legend.setMode(TGIS_ControlLegendMode.Layers);
    }//GEN-LAST:event_btnLayersActionPerformed

    private void bntGroupsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntGroupsActionPerformed
        GIS_Legend.setMode(TGIS_ControlLegendMode.Groups);
    }//GEN-LAST:event_bntGroupsActionPerformed

    private void GISAfterPaint(tatukgis.jdk.PaintEvent evt) {//GEN-FIRST:event_GISAfterPaint
        lScale.setText(GIS.getScaleAsText());
    }//GEN-LAST:event_GISAfterPaint

    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LegendUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LegendUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LegendUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LegendUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LegendUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private tatukgis.jdk.TGIS_ControlLegend GIS_Legend;
    private javax.swing.JButton bntGroups;
    private javax.swing.JButton btnDrag;
    private javax.swing.JButton btnFullExtent;
    private javax.swing.JButton btnLayers;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnZoom;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lScale;
    // End of variables declaration//GEN-END:variables
}
