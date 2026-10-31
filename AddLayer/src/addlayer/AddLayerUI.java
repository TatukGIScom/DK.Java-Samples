// =============================================================================
// This source code is a part of TatukGIS Developer Kernel.
// =============================================================================
//
// AddLayer Sample - Demonstrates how to programmatically add vector layers
// to a TatukGIS map viewer embedded in a Java Swing application.
//
// Key concepts illustrated:
//   - Creating a TGIS_LayerSHP instance directly (manual construction) and
//     adding it to the viewer via GIS.Add().
//   - Using GisCreateLayer (from TGIS_RegistredLayers) as a convenience factory
//     that resolves the correct layer class from the file extension automatically.
//   - Setting visual rendering parameters (line colour, width, outline width)
//     through the getParams().getLine() / getParams().getArea() accessor chain.
//   - Suppressing automatic .ttkgp config-file loading with setUseConfig(false)
//     so that the layer always starts with the explicitly assigned params.
//   - Fitting the viewport to all loaded layers with GIS.FullExtent().
//   - Switching the viewer interaction mode between Drag (pan) and Select
//     via GIS.setMode(TGIS_ViewerMode).
//   - Guarding zoom/extent actions with GIS.getIsEmpty() to avoid errors when
//     no layers are loaded.
//
// Data: DCW (Digital Chart of the World) Shapefiles for Poland, supplied
// via the TatukGIS sample data directory.
// =============================================================================

package addlayer;

import java.awt.Toolkit;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_LayerSHP;
import static tatukgis.jdk.TGIS_RegistredLayers.GisCreateLayer;  // Factory for resolving layer type by file extension
import tatukgis.jdk.TGIS_Utils;
import tatukgis.jdk.TGIS_ViewerMode;

/**
 * AddLayer sample — demonstrates how to programmatically add vector layers to a GIS viewer.
 * Creates shapefile layers (TGIS_LayerSHP) for country polygons and rivers polylines, sets visual
 * styling parameters (fill color, line width, line color), and adds them to the viewer using GIS.Add().
 * Provides zoom navigation and interaction mode switching (pan/select).
 */
public class AddLayerUI extends javax.swing.JFrame {

    /**
     * Constructs the form, initialises the Swing components (via the
     * NetBeans-generated {@link #initComponents()}), then loads the map layers.
     */
    public AddLayerUI() {
        initComponents();
        initOwn();
    }

    /**
     * Creates and configures the two map layers, then fits the viewport to
     * their combined extent.
     *
     * <p>This method is intentionally separated from {@link #initComponents()}
     * so that the generated Designer code remains untouched while all
     * application-specific GIS logic lives here.</p>
     */
    private void initOwn(){
        // --- Layer 1: Country outline (polygon / area layer) ---
        // Construct the layer directly.  TGIS_LayerSHP wraps an ESRI Shapefile;
        // the geometry type (polygon here) is inferred from the .shp file header.
        TGIS_LayerSHP layer = new TGIS_LayerSHP();

        // GisSamplesDataDirDownload() returns the root path where TatukGIS sample
        // datasets were installed or downloaded.  Forward slashes are used because
        // Java's File API accepts them on all platforms.
        layer.setPath(TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/Poland/DCW/country.shp") ;

        // A human-readable label shown in layer lists and legends.
        layer.setName("Country");

        // GIS.Add appends the layer to the internal stack.  Layers added earlier
        // are rendered first (drawn at the bottom of the visual stack).
        GIS.Add(layer);

        // --- Layer 2: Rivers (polyline layer) ---
        // GisCreateLayer is a static factory imported from TGIS_RegistredLayers.
        // It inspects the file extension and returns the appropriate TGIS_Layer
        // subclass.  Casting to TGIS_LayerSHP is safe here because the file is
        // a Shapefile.  The first argument becomes the layer's name property.
        layer = (TGIS_LayerSHP) (GisCreateLayer( "rivers",TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/Poland/DCW/lwaters.shp")) ;

        // setUseConfig(false) prevents the DK from loading a previously saved
        // .ttkgp configuration file for this layer, ensuring the rendering
        // parameters we set below take effect rather than any cached values.
        layer.setUseConfig(false);

        // OutlineWidth = 0 removes the contrasting halo normally drawn around
        // lines, yielding a clean single-colour stroke.
        layer.getParams().getLine().setOutlineWidth(0);

        // Line width in screen pixels at the reference zoom level.
        layer.getParams().getLine().setWidth(3);

        layer.getParams().getLine().setColor(TGIS_Color.getBlue());

        GIS.Add(layer);

        // Zoom the viewport to the combined bounding box of all layers so the
        // full map is visible immediately on startup.
        GIS.FullExtent();
    };

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    // -------------------------------------------------------------------------
    // NetBeans-generated UI code – do not modify manually.
    // -------------------------------------------------------------------------
    private void initComponents() {

        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        jToolBar1 = new javax.swing.JToolBar();
        btnFullExtent = new javax.swing.JButton();
        btnZoomIn = new javax.swing.JButton();
        btnZoomOut = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        cbxDragging = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("AddLayer - TatukGIS DK sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(800, 600));

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

        btnZoomIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/ZoomIn.png"))); // NOI18N
        btnZoomIn.setFocusable(false);
        btnZoomIn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnZoomIn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnZoomIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZoomInActionPerformed(evt);
            }
        });
        jToolBar1.add(btnZoomIn);

        btnZoomOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/ZoomOut.png"))); // NOI18N
        btnZoomOut.setFocusable(false);
        btnZoomOut.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnZoomOut.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnZoomOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZoomOutActionPerformed(evt);
            }
        });
        jToolBar1.add(btnZoomOut);
        jToolBar1.add(jSeparator1);

        cbxDragging.setText("Dragging");
        cbxDragging.setFocusable(false);
        cbxDragging.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        cbxDragging.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cbxDragging.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxDraggingActionPerformed(evt);
            }
        });
        jToolBar1.add(cbxDragging);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Toggles the viewer's active interaction mode.
     *
     * <ul>
     *   <li>{@code TGIS_ViewerMode.Drag}   – left-click and drag pans the map canvas.</li>
     *   <li>{@code TGIS_ViewerMode.Select} – left-click picks the topmost feature
     *       under the cursor.</li>
     * </ul>
     */
    private void cbxDraggingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxDraggingActionPerformed
        if(cbxDragging.isSelected()) {
            GIS.setMode(TGIS_ViewerMode.Drag);
        } else {
            GIS.setMode(TGIS_ViewerMode.Select);
        }
    }//GEN-LAST:event_cbxDraggingActionPerformed

    /**
     * Resets the viewport so that all loaded layers fit inside the viewer window.
     * The {@code getIsEmpty()} guard prevents an error when no layers are loaded.
     */
    private void btnFullExtentActionPerformed(java.awt.event.ActionEvent evt) {
        if(GIS.getIsEmpty())return;
        GIS.FullExtent();
    }

    /**
     * Doubles the current zoom level, shrinking the visible area by half.
     * The {@code getIsEmpty()} guard prevents an error when no layers are loaded.
     */
    private void btnZoomInActionPerformed(java.awt.event.ActionEvent evt) {
        if(GIS.getIsEmpty()) return;
        // Multiply the zoom by 2 to zoom in one step.
        GIS.setZoom(GIS.getZoom() * 2);
    }

    /**
     * Halves the current zoom level, doubling the visible area.
     * The {@code getIsEmpty()} guard prevents an error when no layers are loaded.
     */
    private void btnZoomOutActionPerformed(java.awt.event.ActionEvent evt) {
        if(GIS.getIsEmpty()) return;
        // Divide the zoom by 2 to zoom out one step.
        GIS.setZoom(GIS.getZoom() / 2);
    }

    /**
     * Application entry point.
     *
     * <p>Attempts to apply the Windows look and feel for a native appearance,
     * then creates and displays the main window on the Swing event-dispatch
     * thread via {@link java.awt.EventQueue#invokeLater}.</p>
     *
     * @param args Command-line arguments (not used).
     */
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
            java.util.logging.Logger.getLogger(AddLayerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddLayerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddLayerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddLayerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form on the Swing event-dispatch thread */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddLayerUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    /** TatukGIS Swing viewer control.  All layers are added to this component. */
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.JButton btnFullExtent;
    private javax.swing.JButton btnZoomIn;
    private javax.swing.JButton btnZoomOut;
    /** Checkbox that toggles between Drag (pan) and Select interaction modes. */
    private javax.swing.JCheckBox cbxDragging;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
