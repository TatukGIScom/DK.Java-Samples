package grid;

import RemObjects.Elements.System.VarParameter;
import java.awt.Toolkit;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_LayerPixel;
import tatukgis.jdk.TGIS_Point;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.jdk.TGIS_ViewerMode;
import tatukgis.rtl.TPoint;

public class GridUI extends javax.swing.JFrame {

    public GridUI() {
        initComponents();
        initOwn();
    }

    private void initOwn(){
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/USA/States/California/San Bernardino/NED/w001001.adf");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        lbAltitude = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        btnFullExtent = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnZoom = new javax.swing.JButton();
        btnDrag = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btnClear = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnShadow = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnUserDefined = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        btnUserINI = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        btnReloadINI = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tGIS_ControlLegend1 = new tatukgis.jdk.TGIS_ControlLegend();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Grid - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(800, 600));

        GIS.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                GISMouseMoved(evt);
            }
        });

        lbAltitude.setText("Altitude: ");

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
        jToolBar1.add(jSeparator3);

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
        jToolBar1.add(jSeparator4);

        btnClear.setText("Clear");
        btnClear.setFocusable(false);
        btnClear.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnClear.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        jToolBar1.add(btnClear);
        jToolBar1.add(jSeparator1);

        btnShadow.setText("Turn shadow on/off");
        btnShadow.setFocusable(false);
        btnShadow.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnShadow.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnShadow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShadowActionPerformed(evt);
            }
        });
        jToolBar1.add(btnShadow);
        jToolBar1.add(jSeparator2);

        btnUserDefined.setText("User defined");
        btnUserDefined.setFocusable(false);
        btnUserDefined.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUserDefined.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUserDefined.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserDefinedActionPerformed(evt);
            }
        });
        jToolBar1.add(btnUserDefined);
        jToolBar1.add(jSeparator5);

        btnUserINI.setText("User INI");
        btnUserINI.setFocusable(false);
        btnUserINI.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUserINI.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUserINI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserINIActionPerformed(evt);
            }
        });
        jToolBar1.add(btnUserINI);
        jToolBar1.add(jSeparator6);

        btnReloadINI.setText("Reload INI");
        btnReloadINI.setFocusable(false);
        btnReloadINI.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReloadINI.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnReloadINI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadINIActionPerformed(evt);
            }
        });
        jToolBar1.add(btnReloadINI);
        jToolBar1.add(jSeparator7);

        tGIS_ControlLegend1.setGIS_Viewer(GIS);
        jScrollPane1.setViewportView(tGIS_ControlLegend1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbAltitude)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbAltitude)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFullExtentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFullExtentActionPerformed
        if(evt.getSource() == btnFullExtent){
            if(GIS.getIsEmpty()) return;
            
            GIS.FullExtent();
        }
    }//GEN-LAST:event_btnFullExtentActionPerformed

    private void btnShadowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShadowActionPerformed
        TGIS_LayerPixel lp;
        
        lp = (TGIS_LayerPixel)GIS.getItems().getItems(0);
        lp.getParams().getPixel().setGridShadow(!lp.getParams().getPixel().getGridShadow()); 
        GIS.InvalidateWholeMap();       
    }//GEN-LAST:event_btnShadowActionPerformed

    private void btnZoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZoomActionPerformed
        if(evt.getSource() == btnZoom){
            if(GIS.getIsEmpty()) return;
            
            GIS.setMode(TGIS_ViewerMode.Zoom);
        }
    }//GEN-LAST:event_btnZoomActionPerformed

    private void btnDragActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDragActionPerformed
        if(evt.getSource() == btnDrag){
            if(GIS.getIsEmpty()) return;
            
            GIS.setMode(TGIS_ViewerMode.Drag);
        }
    }//GEN-LAST:event_btnDragActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        TGIS_LayerPixel lp;
        
        lp = (TGIS_LayerPixel)GIS.getItems().getItems(0);
        lp.getParams().getPixel().getAltitudeMapZones().Clear();
        GIS.InvalidateWholeMap();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnUserDefinedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserDefinedActionPerformed
        TGIS_LayerPixel lp;
        
        lp = (TGIS_LayerPixel)GIS.getItems().getItems(0);
        lp.getParams().getPixel().getAltitudeMapZones().Clear();
        lp.getParams().getPixel().getAltitudeMapZones().Add("200,  400 , OLIVE , VERY LOW");
        lp.getParams().getPixel().getAltitudeMapZones().Add("400,  700 , OLIVE , LOW");
        lp.getParams().getPixel().getAltitudeMapZones().Add("700,  900 , GREEN , MID");
        lp.getParams().getPixel().getAltitudeMapZones().Add("900,  1200, BLUE  , HIGH");
        lp.getParams().getPixel().getAltitudeMapZones().Add("1200, 1700, RED   , SKY");
        lp.getParams().getPixel().getAltitudeMapZones().Add("1700, 2200, YELLOW, HEAVEN");
        GIS.InvalidateWholeMap();
    }//GEN-LAST:event_btnUserDefinedActionPerformed

    private void btnUserINIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserINIActionPerformed
        TGIS_LayerPixel lp;
        
        lp = (TGIS_LayerPixel)GIS.getItems().getItems(0);
        lp.setConfigName(TGIS_Utils.GisSamplesDataDirDownload() + "/Samples/Projects/dem_ned.ini");
        lp.RereadConfig();
        GIS.InvalidateWholeMap();
    }//GEN-LAST:event_btnUserINIActionPerformed

    private void btnReloadINIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadINIActionPerformed
        TGIS_LayerPixel lp;
        
        lp = (TGIS_LayerPixel)GIS.getItems().getItems(0);
        lp.setConfigName(TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/USA/States/California/San Bernardino/NED/w001001.adf");
        lp.RereadConfig();
        GIS.InvalidateWholeMap();
    }//GEN-LAST:event_btnReloadINIActionPerformed

    private void GISMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GISMouseMoved
        TGIS_Point ptg;
        TGIS_LayerPixel lp;
        VarParameter<TGIS_Color> rgb;
        VarParameter<double[]> natives;
        VarParameter<Boolean> transp;
        
        rgb = new VarParameter<>();
        natives = new VarParameter<>();
        transp = new VarParameter<>();
        
        if ( GIS.getInPaint() ) return ;
        
        
        ptg = GIS.ScreenToMap(new TPoint(evt.getX(), evt.getY()));
        lp = (TGIS_LayerPixel)GIS.getItems().getItems(0);
        if (lp.Locate(ptg, rgb, natives, transp)) {
            lbAltitude.setText("Altitude: " + natives.Value[0]);
        } else {
            lbAltitude.setText("Altitude: Unknown");
        }
    }//GEN-LAST:event_GISMouseMoved

    public static void main(String args[]){
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
            java.util.logging.Logger.getLogger(GridUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GridUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GridUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GridUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GridUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDrag;
    private javax.swing.JButton btnFullExtent;
    private javax.swing.JButton btnReloadINI;
    private javax.swing.JButton btnShadow;
    private javax.swing.JButton btnUserDefined;
    private javax.swing.JButton btnUserINI;
    private javax.swing.JButton btnZoom;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lbAltitude;
    private tatukgis.jdk.TGIS_ControlLegend tGIS_ControlLegend1;
    // End of variables declaration//GEN-END:variables
}
