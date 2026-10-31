package pixel;

/**
 * PixelUI - main window for the Pixel sample application (Java / JDK edition).
 *
 * <p>Demonstrates how to open and display raster (pixel) layers using different
 * rendering modes provided by TatukGIS.  The sample loads a pre-built
 * {@code .ttkproject} file that contains a raster layer and lets the user
 * switch between rendering profiles via a combo box:</p>
 * <ul>
 *   <li><b>Normal</b>              - standard colour rendering</li>
 *   <li><b>Normal with histogram</b> - histogram stretch for better contrast</li>
 *   <li><b>Grayscale</b>           - all bands collapsed to a grey channel</li>
 *   <li><b>Transparent</b>         - renders with alpha/transparency</li>
 *   <li><b>Colorize</b>            - maps values through a colour gradient</li>
 *   <li><b>Inversion</b>           - inverts all colour channels</li>
 *   <li><b>Inversion by RGB</b>    - inverts individual R, G, B channels</li>
 * </ul>
 *
 * <p>Key TatukGIS API concepts shown here:</p>
 * <ul>
 *   <li>{@code TGIS_ViewerWnd}  - the main map viewer component</li>
 *   <li>{@code TGIS_Utils.GisSamplesDataDirDownload()} - resolves the root path
 *       of the bundled sample data so hard-coded absolute paths are avoided</li>
 *   <li>{@code GIS.Open()} - opens a {@code .ttkproject} (or any supported
 *       raster/vector file) and replaces the current viewer content</li>
 *   <li>{@code GIS.FullExtent()} - zooms the view to fit all loaded layers</li>
 *   <li>{@code TGIS_ViewerMode} - {@code Zoom} (rubber-band) or {@code Drag}
 *       (pan) interaction mode</li>
 *   <li>{@code GIS.InvalidateWholeMap()} - forces a full repaint of the map
 *       after programmatic changes to layers or the view extent</li>
 * </ul>
 */

import java.awt.Toolkit;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.jdk.TGIS_ViewerMode;

public class PixelUI extends javax.swing.JFrame {

    /**
     * Constructs the frame and initialises all Swing components, then
     * sets the default selected project so the raster layer is visible
     * as soon as the window opens.
     */
    public PixelUI() {
        initComponents();
        initOwn();
    }

    /**
     * Custom post-construction initialisation.
     * Selects the first entry in the combo box, which fires
     * {@code comboBoxActionPerformed} and loads the raster layer.
     * Kept separate from the NetBeans-generated {@code initComponents()} so
     * that the designer does not overwrite this logic.
     */
    private void initOwn(){
        comboBox.setSelectedIndex(0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        jToolBar1 = new javax.swing.JToolBar();
        btnFullExtent = new javax.swing.JButton();
        btnZoom = new javax.swing.JButton();
        btnDrag = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        comboBox = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pixel - TatukGIS DK sample");
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
        jToolBar1.add(jSeparator1);

        // Populate with the available rendering-project file names.
        // Each entry is a .ttkproject that specifies a different colour
        // rendering mode for the same underlying raster data.
        comboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal.ttkproject", "Normal with histogram.ttkproject", "Grayscale.ttkproject", "Transparent.ttkproject", "Colorize.ttkproject", "Inversion.ttkproject", "Inversion by RGB.ttkproject" }));
        comboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxActionPerformed(evt);
            }
        });
        jToolBar1.add(comboBox);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
     * Resets the map view to show all loaded layers at once.
     * Guards against calling FullExtent() when no layers are loaded yet.
     */
    private void btnFullExtentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFullExtentActionPerformed
            if(GIS.getIsEmpty()) return;
            GIS.FullExtent();
    }//GEN-LAST:event_btnFullExtentActionPerformed

    /**
     * Switches the viewer to rubber-band Zoom mode.
     * In Zoom mode the user draws a rectangle to zoom in, or right-clicks
     * to zoom out.
     */
    private void btnZoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZoomActionPerformed
            if(GIS.getIsEmpty()) return;
            GIS.setMode(TGIS_ViewerMode.Zoom);
    }//GEN-LAST:event_btnZoomActionPerformed

    /**
     * Opens the .ttkproject file selected in the combo box.
     * {@code GIS.Open()} closes any previously loaded content and replaces it
     * with the layers defined in the project file.  Each project in this
     * sample points at the same raster image but with different rendering
     * settings (colour mode, histogram, etc.).
     * {@code GIS.InvalidateWholeMap()} forces a full repaint after loading.
     */
    private void comboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxActionPerformed
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload()+"/Samples/Projects/"+comboBox.getSelectedItem());
        GIS.InvalidateWholeMap();
    }//GEN-LAST:event_comboBoxActionPerformed

    /**
     * Switches the viewer to Drag (pan) mode.
     * In Drag mode the user can scroll the map by clicking and dragging.
     */
    private void btnDragActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDragActionPerformed
        if(evt.getSource() == btnDrag){
            if(GIS.getIsEmpty()) return;
            GIS.setMode(TGIS_ViewerMode.Drag);
        }
    }//GEN-LAST:event_btnDragActionPerformed

    /**
     * Application entry point.
     * Attempts to apply the Windows look-and-feel for visual consistency,
     * then creates and shows the main form on the Event Dispatch Thread.
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
            java.util.logging.Logger.getLogger(PixelUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PixelUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PixelUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PixelUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form on the Event Dispatch Thread */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PixelUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;    // TatukGIS map viewer component
    private javax.swing.JButton btnDrag;          // Switch to pan/drag mode
    private javax.swing.JButton btnFullExtent;    // Zoom to full extent
    private javax.swing.JButton btnZoom;          // Switch to zoom mode
    private javax.swing.JComboBox comboBox;       // Rendering project selector
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
