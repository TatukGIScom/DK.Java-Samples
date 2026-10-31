/*
 * =============================================================================
 * This source code is a part of TatukGIS Developer Kernel.
 * =============================================================================
 *
 * Topology Sample – Demonstrates polygon set-algebra operations via TGIS_Topology.
 *
 * This sample loads two overlapping polygon shapes (A and B) from a shapefile
 * and lets the user compute the following topological combinations:
 *
 *   A + B   : Union            – area covered by either A or B (or both)
 *   A * B   : Intersection     – area covered by both A and B
 *   A - B   : Difference       – area in A but not in B
 *   B - A   : Difference       – area in B but not in A
 *   A xor B : SymDifference    – area in A or B but not in both (exclusive OR)
 *
 * Results are displayed in a separate in-memory TGIS_LayerVector rendered in red
 * with 50% transparency so the original shapes remain visible underneath.
 *
 * Key TatukGIS JDK classes used:
 *   TGIS_Topology            – engine that performs polygon boolean operations
 *   TGIS_TopologyCombineType – enumeration of the five combine modes
 *   TGIS_LayerVector         – in-memory vector layer used to display results
 *   TGIS_ShapePolygon        – strongly-typed polygon shape handle
 *   TGIS_ViewerWnd           – Swing-embedded map viewer component
 * =============================================================================
 */
package topology;

import java.awt.Toolkit;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_ShapePolygon;
import tatukgis.jdk.TGIS_Topology;
import tatukgis.jdk.TGIS_TopologyCombineType;
import tatukgis.jdk.TGIS_Utils;

/**
 * Main frame for the Topology sample application.
 *
 * <p>Demonstrates the five polygon boolean (set-algebra) operations provided by
 * {@link TGIS_Topology}: Union, Intersection, Difference (A-B),
 * Difference (B-A), and Symmetrical Difference (XOR).</p>
 *
 * <p>The UI presents five toolbar buttons, one per operation. Clicking a button
 * clears the output layer, executes the chosen combination, and repaints the map.</p>
 */
public class TopologyUI extends javax.swing.JFrame {

    /** The topology engine used to compute polygon boolean operations.
     *  Stateless and safe to reuse across multiple Combine() calls. */
    private TGIS_Topology topologyObj;

    /** In-memory vector layer that receives and displays the computed result shape.
     *  Cleared via RevertShapes() before every new operation. */
    private TGIS_LayerVector layerObj;

    /** Source polygon A – loaded from shape index 1 of the sample shapefile. */
    private TGIS_ShapePolygon shpA;

    /** Source polygon B – loaded from shape index 2 of the sample shapefile. */
    private TGIS_ShapePolygon shpB;

    /**
     * Constructs the frame, initialises designer components, then loads map data.
     */
    public TopologyUI() {
        initComponents();
        initOwn();
    }

    /**
     * Custom initialisation: creates the topology engine, opens the sample
     * shapefile, extracts the two source polygons as editable copies, and
     * adds a transparent output layer to the viewer.
     *
     * <p>Lock/Unlock brackets suspend viewer repaints during batch modifications,
     * which avoids flickering and improves performance when adding multiple layers.</p>
     */
    private void initOwn(){
        TGIS_LayerVector ll;

        GIS.Lock();  // Suspend repaints while modifying the viewer

        topologyObj = new TGIS_Topology();

        // Open the bundled topology sample shapefile containing two overlapping polygons
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/Samples//Topology//topology.shp");

        // Retrieve the first (and only) vector layer just loaded
        ll = (TGIS_LayerVector)GIS.getItems().getItems(0);
        if(ll == null) return;

        // MakeEditable returns a detached, editable copy of each shape so the
        // topology engine can safely access geometry without touching the layer.
        shpA = (TGIS_ShapePolygon)ll.GetShape(1).MakeEditable();
        if(shpA == null) return;

        shpB = (TGIS_ShapePolygon)ll.GetShape(2).MakeEditable();
        if(shpB == null) return;

        // Create a blank in-memory layer for displaying the topology result.
        // It sits on top of the source layer in the viewer stack.
        layerObj = new TGIS_LayerVector();
        layerObj.setName("output");
        layerObj.setTransparency(50);                          // 50% transparent so source shows through
        layerObj.getParams().getArea().setColor(TGIS_Color.getRed()); // Result polygon fill colour

        GIS.Add(layerObj);
        GIS.Unlock();      // Resume painting
        GIS.FullExtent();  // Zoom to fit all layers
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        jToolBar1 = new javax.swing.JToolBar();
        btnAplusB = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnAmultiplyB = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnAminusB = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnBminusA = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btnAxorB = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Topology - TatukGIS DK sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(800, 600));

        jToolBar1.setRollover(true);

        btnAplusB.setText("A + B");
        btnAplusB.setFocusable(false);
        btnAplusB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAplusB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAplusB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAplusBActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAplusB);
        jToolBar1.add(jSeparator1);

        btnAmultiplyB.setText("A * B");
        btnAmultiplyB.setFocusable(false);
        btnAmultiplyB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAmultiplyB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAmultiplyB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmultiplyBActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAmultiplyB);
        jToolBar1.add(jSeparator2);

        btnAminusB.setText("A - B");
        btnAminusB.setFocusable(false);
        btnAminusB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAminusB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAminusB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAminusBActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAminusB);
        jToolBar1.add(jSeparator3);

        btnBminusA.setText("B - A");
        btnBminusA.setFocusable(false);
        btnBminusA.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBminusA.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBminusA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBminusAActionPerformed(evt);
            }
        });
        jToolBar1.add(btnBminusA);
        jToolBar1.add(jSeparator4);

        btnAxorB.setText("A xor B");
        btnAxorB.setFocusable(false);
        btnAxorB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAxorB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAxorB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAxorBActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAxorB);
        jToolBar1.add(jSeparator5);

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
     * Computes the Union of A and B (A + B).
     * The resulting shape covers all area contained in either polygon,
     * merging any overlapping regions into a single contiguous polygon.
     */
    private void btnAplusBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAplusBActionPerformed
        TGIS_Shape tmp;

        // Clear the previous result before computing the new one
        layerObj.RevertShapes();
        tmp = topologyObj.Combine(shpA, shpB, TGIS_TopologyCombineType.Union);
        if(tmp == null) return;
        layerObj.AddShape(tmp);  // Transfer result into the output layer
        tmp = null;
        GIS.InvalidateWholeMap(); // Trigger a full repaint
    }//GEN-LAST:event_btnAplusBActionPerformed

    /**
     * Computes the Intersection of A and B (A * B).
     * The resulting shape covers only the area simultaneously inside
     * both polygon A and polygon B.
     */
    private void btnAmultiplyBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmultiplyBActionPerformed
        TGIS_Shape tmp;

        layerObj.RevertShapes();
        tmp = topologyObj.Combine(shpA, shpB, TGIS_TopologyCombineType.Intersection);
        if(tmp == null) return;
        layerObj.AddShape(tmp);
        tmp = null;
        GIS.InvalidateWholeMap();    }//GEN-LAST:event_btnAmultiplyBActionPerformed

    /**
     * Computes the Difference A minus B (A - B).
     * The result is the area of polygon A with the overlap of B removed.
     * The first Combine() argument is the "base" shape; the second is subtracted.
     */
    private void btnAminusBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAminusBActionPerformed
        TGIS_Shape tmp;

        layerObj.RevertShapes();
        tmp = topologyObj.Combine(shpA, shpB, TGIS_TopologyCombineType.Difference);
        if(tmp == null) return;
        layerObj.AddShape(tmp);
        tmp = null;
        GIS.InvalidateWholeMap();    }//GEN-LAST:event_btnAminusBActionPerformed

    /**
     * Computes the Difference B minus A (B - A).
     * Identical logic to A-B but with operands swapped so that
     * the area of A is subtracted from the area of B.
     */
    private void btnBminusAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBminusAActionPerformed
        TGIS_Shape tmp;

        layerObj.RevertShapes();
        // Note: shpB is the first (base) argument; shpA is what gets subtracted
        tmp = topologyObj.Combine(shpB, shpA, TGIS_TopologyCombineType.Difference);
        if(tmp == null) return;
        layerObj.AddShape(tmp);
        tmp = null;
        GIS.InvalidateWholeMap();    }//GEN-LAST:event_btnBminusAActionPerformed

    /**
     * Computes the Symmetrical Difference of A and B (A xor B).
     * The result covers area belonging to exactly one of the two polygons –
     * equivalent to Union minus Intersection, or a logical XOR of the two areas.
     */
    private void btnAxorBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAxorBActionPerformed
        TGIS_Shape tmp;

        layerObj.RevertShapes();
        tmp = topologyObj.Combine(shpA, shpB, TGIS_TopologyCombineType.SymmetricalDifference);
        if(tmp == null) return;
        layerObj.AddShape(tmp);
        tmp = null;
        GIS.InvalidateWholeMap();    }//GEN-LAST:event_btnAxorBActionPerformed

    /**
     * Application entry point.
     * Applies the Windows look-and-feel (if available) and launches the frame
     * on the Swing Event Dispatch Thread for thread safety.
     *
     * @param args command-line arguments (not used)
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
            java.util.logging.Logger.getLogger(TopologyUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TopologyUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TopologyUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TopologyUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form on the Event Dispatch Thread */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TopologyUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.JButton btnAminusB;
    private javax.swing.JButton btnAmultiplyB;
    private javax.swing.JButton btnAplusB;
    private javax.swing.JButton btnAxorB;
    private javax.swing.JButton btnBminusA;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
