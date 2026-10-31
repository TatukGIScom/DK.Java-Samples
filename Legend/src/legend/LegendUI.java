// =============================================================================
// This source code is a part of TatukGIS Developer Kernel.
// =============================================================================
//
// Legend Sample - Java (Swing)
//
// Demonstrates how to use the TGIS_ControlLegend panel alongside a map viewer.
// The legend control provides an interactive, scrollable layer list that lets
// the user toggle layer visibility, reorder layers, expand/collapse symbology,
// and open layer property dialogs - all without writing any extra code.
//
// Key concepts shown:
//   - Placing TGIS_ControlLegend (inside a JScrollPane) next to TGIS_ViewerWnd
//     and linking them via setGIS_Viewer() so the legend reflects the loaded
//     map automatically.
//   - Switching between two display modes at runtime:
//       * TGIS_ControlLegendMode.Layers - flat list of every individual layer
//       * TGIS_ControlLegendMode.Groups - tree grouped by layer group membership
//   - Opening a .ttkproject file that bundles multiple SHP layers together.
//   - Controlling the viewer interaction mode (Zoom / Drag).
//   - Persisting any layer-style changes back to the project file with SaveAll.
//   - Reflecting the current map scale in a status label after each repaint.
// =============================================================================

package legend;

import java.awt.Toolkit;
import tatukgis.jdk.TGIS_ControlLegendMode;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.jdk.TGIS_ViewerMode;

/**
 * Main application window for the Legend sample.
 * <p>
 * Hosts a {@code TGIS_ViewerWnd} (map canvas) and a {@code TGIS_ControlLegend}
 * panel side-by-side. The legend panel is wrapped in a {@code JScrollPane} so
 * it remains usable when many layers are loaded.
 * </p>
 */
public class LegendUI extends javax.swing.JFrame {

    /**
     * Constructs the window: initialises all Swing components (via the
     * NetBeans-generated {@link #initComponents()}) then loads the sample map.
     */
    public LegendUI() {
        initComponents();
        initOwn();
    }

    /**
     * Loads the sample Poland multi-layer project after the Swing components
     * are ready.
     * <p>
     * Opening a {@code .ttkproject} causes {@code TGIS_ViewerWnd} to load all
     * referenced layer files. Because {@code GIS_Legend.setGIS_Viewer(GIS)} was
     * already called inside {@link #initComponents()}, the legend populates
     * itself automatically as each layer is registered with the viewer.
     * </p>
     */
    private void initOwn(){
        // GisSamplesDataDirDownload() resolves the shared sample-data directory
        // path (configurable via TatukGIS environment variable or system property).
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
        GIS_Legend = new tatukgis.jdk.TGIS_ControlLegend();  // Legend panel
        GIS = new tatukgis.jdk.TGIS_ViewerWnd();              // Map canvas
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lScale = new javax.swing.JLabel();                     // Shows current map scale
        bntGroups = new javax.swing.JButton();
        btnLayers = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Legend - TatukGIS DK sample");
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

        // Link the legend to the map viewer so it mirrors the viewer's layer list
        GIS_Legend.setGIS_Viewer(GIS);
        // Wrap the legend in a scroll pane so it remains usable with many layers
        jScrollPane1.setViewportView(GIS_Legend);

        // Register a paint listener to update the scale label after each map repaint
        GIS.addPaintListener(new tatukgis.jdk.PaintListener() {
            public void BeforePaint(tatukgis.jdk.PaintEvent evt) {
            }
            public void BeforePaintRenderer(tatukgis.jdk.RenderEvent evt) {
            }
            public void PaintExtra(tatukgis.jdk.RenderEvent evt) {
            }
            public void AfterPaint(tatukgis.jdk.PaintEvent evt) {
                // Delegate to the named handler so the scale label stays current
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

    /**
     * Resets the map viewport to show all loaded layers at their combined extent.
     * Useful after zooming into a specific area and wanting to return to the overview.
     */
    private void btnFullExtentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFullExtentActionPerformed
        // Zoom out to fit the complete bounding box of all visible layers
        GIS.FullExtent();
    }//GEN-LAST:event_btnFullExtentActionPerformed

    /**
     * Switches the viewer to Zoom mode.
     * In Zoom mode a left-click-drag draws a rubber-band rectangle that the
     * viewer zooms into; a right-click zooms back out by a fixed factor.
     */
    private void btnZoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZoomActionPerformed
        // TGIS_ViewerMode.Zoom: left-drag to zoom in, right-click to zoom out
        GIS.setMode(TGIS_ViewerMode.Zoom);
    }//GEN-LAST:event_btnZoomActionPerformed

    /**
     * Switches the viewer to Drag (pan) mode.
     * In Drag mode the user pans the map by click-dragging, which is the most
     * natural way to explore a map after zooming in.
     */
    private void btnDragActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDragActionPerformed
        // TGIS_ViewerMode.Drag: click-drag to pan the visible map extent
        GIS.setMode(TGIS_ViewerMode.Drag);
    }//GEN-LAST:event_btnDragActionPerformed

    /**
     * Saves all layer configuration changes (symbology, visibility, order) back
     * to the originating project or layer files.
     * Guards against calling SaveAll on an empty viewer to avoid an error.
     */
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if(GIS.getIsEmpty())
            return;  // Nothing loaded - nothing to save

        // Persist any legend changes (visibility flags, symbology edits, layer order)
        // back to the .ttkproject file and its referenced layer files.
        GIS.SaveAll();
    }//GEN-LAST:event_btnSaveActionPerformed

    /**
     * Switches the legend to flat Layers mode.
     * In Layers mode every individual layer appears as a separate top-level
     * item in the legend list, regardless of any group hierarchy.
     */
    private void btnLayersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLayersActionPerformed
        GIS_Legend.setMode(TGIS_ControlLegendMode.Layers);
    }//GEN-LAST:event_btnLayersActionPerformed

    /**
     * Switches the legend to Groups mode.
     * In Groups mode layers are nested inside their parent group nodes,
     * matching the logical structure defined in the project file.
     */
    private void bntGroupsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntGroupsActionPerformed
        GIS_Legend.setMode(TGIS_ControlLegendMode.Groups);
    }//GEN-LAST:event_bntGroupsActionPerformed

    /**
     * Fired by TGIS_ViewerWnd after every completed map repaint (via the
     * PaintListener registered in initComponents).
     * Updates the scale label with the human-readable map scale string
     * (e.g. "1 : 250 000") so the user always knows the current zoom level.
     */
    private void GISAfterPaint(tatukgis.jdk.PaintEvent evt) {//GEN-FIRST:event_GISAfterPaint
        // getScaleAsText() returns a formatted "1 : N" string for the current viewport
        lScale.setText(GIS.getScaleAsText());
    }//GEN-LAST:event_GISAfterPaint

    /**
     * Application entry point.
     * Attempts to use the Windows look-and-feel for a native appearance,
     * then shows the main window on the AWT event dispatch thread.
     *
     * @param args command-line arguments (not used)
     */
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

        // All Swing UI must be created and accessed on the event dispatch thread
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LegendUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;           // The main interactive map canvas
    private tatukgis.jdk.TGIS_ControlLegend GIS_Legend; // The interactive legend panel
    private javax.swing.JButton bntGroups;              // Switches legend to Groups mode
    private javax.swing.JButton btnDrag;                // Switches viewer to Drag mode
    private javax.swing.JButton btnFullExtent;          // Zooms to full extent
    private javax.swing.JButton btnLayers;              // Switches legend to Layers mode
    private javax.swing.JButton btnSave;                // Saves project configuration
    private javax.swing.JButton btnZoom;                // Switches viewer to Zoom mode
    private javax.swing.JLabel jLabel1;                 // Static "Scale:" caption
    private javax.swing.JPanel jPanel1;                 // Status bar panel at the bottom
    private javax.swing.JScrollPane jScrollPane1;       // Scroll wrapper for the legend
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lScale;                  // Displays the current map scale
    // End of variables declaration//GEN-END:variables
}
