// Zooming - TatukGIS Developer Kernel sample (Java / Swing).
//
// Demonstrates multiple ways to zoom and navigate a loaded map:
//   1. Loading a TatukGIS project file (.ttkproject) which bundles all
//      layer definitions, styles, and coordinate system settings.
//   2. Full-extent button - zooms the viewer to display all loaded data.
//   3. Zoom mode - rubber-band rectangle zoom (left click + drag to zoom in,
//      right click to zoom out by one step).
//   4. Drag mode - panning by clicking and dragging the map.
//
// Note: the Java/Swing TGIS_ViewerWnd does not expose a direct mouse-wheel
// zoom-by-cursor API in this sample; basic zoom and drag modes are demonstrated.

package zooming;

import java.awt.Toolkit;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.jdk.TGIS_ViewerMode;

/**
 * Main frame for the Zooming sample.
 * Demonstrates viewer navigation: full extent, zoom mode, and drag mode.
 */
public class ZoomingUI extends javax.swing.JFrame {

    /**
     * Constructs the frame, initialises Swing components, then loads the
     * sample project file via initOwn().
     */
    public ZoomingUI() {
        initComponents();
        initOwn();
    }

    /**
     * Opens the sample Poland project file after the Swing components are ready.
     * A .ttkproject file is a TatukGIS project format that bundles multiple
     * layer definitions, their rendering styles, and coordinate system settings
     * into a single file, so no manual layer configuration is required.
     *
     * Separated from initComponents() so that NetBeans does not overwrite it
     * when regenerating the GUI builder code.
     */
    private void initOwn(){
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/Poland/DCW/poland.ttkproject");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        jToolBar1 = new javax.swing.JToolBar();
        fullExtent = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        zoom = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        drag = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Zooming - TatukGIS DK sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(800, 600));

        jToolBar1.setRollover(true);

        fullExtent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/FullExtent.png"))); // NOI18N
        fullExtent.setFocusable(false);
        fullExtent.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        fullExtent.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        fullExtent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fullExtentActionPerformed(evt);
            }
        });
        jToolBar1.add(fullExtent);
        jToolBar1.add(jSeparator1);

        zoom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Zoom.png"))); // NOI18N
        zoom.setFocusable(false);
        zoom.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zoom.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        zoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomActionPerformed(evt);
            }
        });
        jToolBar1.add(zoom);
        jToolBar1.add(jSeparator2);

        drag.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Drag.png"))); // NOI18N
        drag.setFocusable(false);
        drag.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        drag.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        drag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dragActionPerformed(evt);
            }
        });
        jToolBar1.add(drag);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * "Full Extent" button action.
     * Resets the viewport to display the complete spatial extent of all loaded
     * layers, fitting the entire dataset within the viewer bounds.
     */
    private void fullExtentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fullExtentActionPerformed
        if(evt.getSource() == fullExtent){
            // Do nothing if no layers are loaded
            if(GIS.getIsEmpty()) return;
            GIS.FullExtent();
        }
    }//GEN-LAST:event_fullExtentActionPerformed

    /**
     * "Zoom Mode" button action.
     * Switches the viewer to Zoom mode. The user can left-click and drag to draw
     * a rubber-band rectangle defining the new view area; a right-click zooms out
     * by one step.
     */
    private void zoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomActionPerformed
        if(evt.getSource() == zoom){
            // Do nothing if no layers are loaded
            if(GIS.getIsEmpty()) return;
            GIS.setMode(TGIS_ViewerMode.Zoom);
        }
    }//GEN-LAST:event_zoomActionPerformed

    /**
     * "Drag Mode" button action.
     * Switches the viewer to Drag mode, allowing the user to pan the map
     * by clicking and dragging with the mouse.
     */
    private void dragActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dragActionPerformed
        if(evt.getSource() == drag){
            // Do nothing if no layers are loaded
            if(GIS.getIsEmpty()) return;
            GIS.setMode(TGIS_ViewerMode.Drag);
        }
    }//GEN-LAST:event_dragActionPerformed

    /**
     * Application entry point.
     * Applies the Windows look and feel (when available) and shows the main frame.
     */
    public static void main(String args[]) {
        /* Apply the Windows look and feel for native appearance on Windows */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ZoomingUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ZoomingUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ZoomingUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ZoomingUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        // Show the frame on the Swing Event Dispatch Thread
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ZoomingUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.JButton drag;
    private javax.swing.JButton fullExtent;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton zoom;
    // End of variables declaration//GEN-END:variables
}
