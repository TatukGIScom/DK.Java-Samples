/*
 * =============================================================================
 * This source code is a part of TatukGIS Developer Kernel.
 * =============================================================================
 *
 * ShapeOperations sample - Interactive affine transformation of vector shapes.
 *
 * This sample demonstrates how to interactively apply affine geometric
 * transformations to individual vector shapes using the TatukGIS JDK API:
 *
 *   Rotate:    Spins the selected shape around its centroid.  Horizontal mouse
 *              movement maps to rotation angle (degrees converted to radians).
 *   Scale:     Grows or shrinks the shape by comparing the current mouse
 *              position to the previous one (ratio-based per axis).
 *   Move:      Translates the shape by the map-coordinate delta between
 *              consecutive mouse-move events.
 *
 * The sample uses a two-layer approach:
 *   1. The original shapefile layer holds the real data.
 *   2. An in-memory TGIS_LayerVector (edtLayer) renders a live preview of the
 *      shape being edited.  setCachedPaint(false) makes it a "tracking" layer
 *      so InvalidateWholeMap triggers an immediate repaint.
 * On commit (second mouse-release), the edited geometry is written back to the
 * source shape via CopyGeometry, and the preview layer is cleared.
 *
 * Data: Samples/3D/buildings.shp
 * =============================================================================
 */
package ShapeOperations;

import java.awt.Toolkit;
import tatukgis.jdk.TGIS_BrushStyle;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_Point;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.jdk.TGIS_ViewerMode;
import tatukgis.rtl.TPoint;

/**
 * Main window for the ShapeOperations sample.
 *
 * <p>Demonstrates interactive rotate, scale, and translate operations on
 * individual vector shapes using {@code TGIS_Shape.Transform} with an affine
 * transformation matrix.</p>
 */
public class ShapeOperationsUI extends javax.swing.JFrame {

    // -------------------------------------------------------------------------
    // Construction
    // -------------------------------------------------------------------------

    /** Creates the form and initialises all Swing components and GIS state. */
    public ShapeOperationsUI() {
        initComponents();
        initOwn();
    }

    /**
     * Initialises GIS-specific state after the Swing components are ready.
     *
     * <ul>
     *   <li>Opens the sample buildings shapefile.</li>
     *   <li>Creates the in-memory edit/preview overlay layer.</li>
     *   <li>Zooms in 4x and switches the viewer to Select mode so click-to-pick works.</li>
     * </ul>
     */
    private void initOwn(){
        currShape = null;
        edtShape = null;

        handleMouseMove = false;
        GIS.Lock();  // Suspend repaints while loading/configuring layers

        // GisSamplesDataDirDownload resolves the shared sample data folder
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/Samples/3D/buildings.shp");

        // Create the in-memory edit/preview overlay layer.
        // setCachedPaint(false) marks it as a "tracking" layer: the tile cache
        // is bypassed so a repaint request shows the latest geometry immediately.
        edtLayer = new TGIS_LayerVector();
        edtLayer.setCS(GIS.getCS());             // Match viewer coordinate system
        edtLayer.setCachedPaint(false);          // Tracking layer
        edtLayer.getParams().getArea().setPattern(TGIS_BrushStyle.Clear);       // Transparent fill
        edtLayer.getParams().getArea().setOutlineColor(TGIS_Color.getRed());    // Red outline
        GIS.Add(edtLayer);

        GIS.Unlock();

        // Zoom in 4x from the default extent so buildings are visible
        GIS.setZoom(GIS.getZoom() * 4);

        // Force Select mode so the viewer passes through click events for shape picking
        GIS.setMode(TGIS_ViewerMode.Select);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        jToolBar1 = new javax.swing.JToolBar();
        rbRotate = new javax.swing.JRadioButton();
        rbScale = new javax.swing.JRadioButton();
        rbMove = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        lblSelected = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ShapeTransform - TatukGIS DK sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(800, 600));

        GIS.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                GISMouseMoved(evt);
            }
        });
        GIS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                GISMouseReleased(evt);
            }
        });

        jToolBar1.setRollover(true);

        buttonGroup1.add(rbRotate);
        rbRotate.setSelected(true);
        rbRotate.setText("Rotate");
        rbRotate.setFocusable(false);
        rbRotate.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rbRotate.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rbRotate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbRotateActionPerformed(evt);
            }
        });
        jToolBar1.add(rbRotate);

        buttonGroup1.add(rbScale);
        rbScale.setText("Scale");
        rbScale.setFocusable(false);
        rbScale.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rbScale.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rbScale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbScaleActionPerformed(evt);
            }
        });
        jToolBar1.add(rbScale);

        buttonGroup1.add(rbMove);
        rbMove.setText("Move");
        rbMove.setFocusable(false);
        rbMove.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rbMove.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rbMove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbMoveActionPerformed(evt);
            }
        });
        jToolBar1.add(rbMove);
        jToolBar1.add(jSeparator1);

        lblSelected.setText("Select shape to start rotating");
        jToolBar1.add(lblSelected);

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

    // -------------------------------------------------------------------------
    // Mouse event handlers
    // -------------------------------------------------------------------------

    /**
     * Responds to mouse movement over the viewer.
     *
     * <p>While {@code handleMouseMove} is {@code true} (a shape is selected),
     * dispatches incremental affine transforms to the preview shape in
     * {@code edtLayer}.</p>
     *
     * <ul>
     *   <li>Rotate: horizontal pixel delta converted to radians via
     *       {@link Math#toRadians}.</li>
     *   <li>Scale: ratio of current to previous screen position per axis.</li>
     *   <li>Move: map-coordinate delta since the last event.</li>
     * </ul>
     */
    private void GISMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GISMouseMoved
        TGIS_Point ptg;

        if (edtShape == null) return;  // Nothing selected; ignore

        if (handleMouseMove) {
            // Convert current screen position to map coordinates
            ptg = GIS.ScreenToMap(new TPoint(evt.getX(), evt.getY()));

            if (rbRotate.isSelected())
                // Horizontal mouse movement drives rotation (1 pixel = 1 degree)
                RotateSelectedShape(edtShape, Math.toRadians(evt.getX() - prevX));
            else if (rbScale.isSelected())
                // Scale factor = current position / previous position (per axis)
                ScaleSelectedShape(edtShape, evt.getX() / (double)prevX, evt.getY() / (double)prevY);
            else if (rbMove.isSelected())
                // Delta in map units between this event and the last
                TranslateSelectedShape(edtShape, (ptg.X - prevPtg.X), ptg.Y - prevPtg.Y);

            // Update previous-position tracking for the next incremental step
            prevPtg = ptg;
            prevX = evt.getX();
            prevY = evt.getY();
        }
    }//GEN-LAST:event_GISMouseMoved

    /**
     * Handles both the shape-selection click (first mouse-release) and the
     * geometry-commit click (second mouse-release).
     *
     * <p><b>First release (currShape == null):</b>
     * <ul>
     *   <li>{@code GIS.Locate} finds the nearest shape within 5 pixels.</li>
     *   <li>{@code MakeEditable} returns a writable proxy of the source shape.</li>
     *   <li>A copy is placed in the preview layer for live editing.</li>
     *   <li>{@code handleMouseMove} is toggled to start receiving transforms.</li>
     * </ul>
     *
     * <p><b>Second release (currShape != null):</b>
     * <ul>
     *   <li>{@code CopyGeometry} writes the final geometry back to the source.</li>
     *   <li>{@code RevertAll} clears the preview layer.</li>
     *   <li>{@code InvalidateWholeMap} forces a full repaint.</li>
     * </ul>
     *
     * <p>Note: the Java edition also cancels an in-progress drag when
     * {@code handleMouseMove} is true but {@code currShape} is null — this
     * handles the edge case of a second click without a prior shape selection.
     */
    private void GISMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GISMouseReleased
        TGIS_Point ptg;
        TGIS_Shape shp;

        lblSelected.setText("No selected shape. Select shape");

        // --- COMMIT path: a shape was already selected and being edited ---
        if (currShape != null)
        {
            // Write the transformed preview geometry back to the real layer
            currShape.CopyGeometry(edtShape);

            // Discard all shapes in the preview layer
            edtLayer.RevertAll();

            currShape = null;
            edtShape = null;

            // Full repaint to show the committed shape in its real layer style
            GIS.InvalidateWholeMap();
            handleMouseMove = false;
            return;
        }

        // Guard: no layers or wrong interaction mode
        if (GIS.getIsEmpty()) return;
        if (GIS.getMode() != TGIS_ViewerMode.Select) return;

        // Cancel if a drag was started but no shape was found (edge case)
        if (handleMouseMove) {
            GIS.InvalidateWholeMap();
            currShape = null;
            handleMouseMove = false;

            // Restore the appropriate hint for the current mode
            if (rbRotate.isSelected())
                lblSelected.setText("Select shape to start rotating");
            else if (rbScale.isSelected())
                lblSelected.setText("Select shape to start scaling");
            else if (rbMove.isSelected())
                lblSelected.setText("Select shape to start moving");

            return;
        }

        // --- SELECT path: pick a shape from the map ---

        // Convert screen click to map coordinates
        ptg = GIS.ScreenToMap(new TPoint(evt.getX(), evt.getY()));

        // Locate the nearest shape; tolerance = 5 screen pixels in map units
        shp = (TGIS_Shape) GIS.Locate(ptg, 5 / GIS.getZoom());

        if (shp == null) return;

        // MakeEditable returns a proxy that allows geometry modification
        currShape = shp.MakeEditable();

        // Place a copy of the shape into the overlay layer for live editing
        edtShape = edtLayer.AddShape(currShape.CreateCopy());

        lblSelected.setText("Selected shape : " + currShape.getUid() + ". Click to commit changes");

        // Seed previous-position state for the first incremental delta
        prevPtg = ptg;
        prevX = evt.getX();
        prevY = evt.getY();

        // Toggle: start accepting mouse-move transforms
        handleMouseMove = !handleMouseMove;
    }//GEN-LAST:event_GISMouseReleased

    // -------------------------------------------------------------------------
    // Radio button action handlers (mode switching)
    // -------------------------------------------------------------------------

    /**
     * Switches to Rotate mode.  If a shape is currently being edited,
     * cancels the edit session so the user starts fresh in the new mode.
     */
    private void rbRotateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbRotateActionPerformed
        lblSelected.setText("Select shape to start rotating");

        if (currShape != null)
        {
            GIS.InvalidateTopmost();
            currShape = null;
            handleMouseMove = false;
        }
    }//GEN-LAST:event_rbRotateActionPerformed

    /**
     * Switches to Scale mode.  Cancels any in-progress edit session.
     */
    private void rbScaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbScaleActionPerformed
        lblSelected.setText("Select shape to start scaling");

        if (currShape != null)
        {
            GIS.InvalidateTopmost();
            currShape = null;
            handleMouseMove = false;
        }
    }//GEN-LAST:event_rbScaleActionPerformed

    /**
     * Switches to Move (translate) mode.  Cancels any in-progress edit session.
     */
    private void rbMoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbMoveActionPerformed
        lblSelected.setText("Select shape to start moving");

        if (currShape != null)
        {
            GIS.InvalidateTopmost();
            currShape = null;
            handleMouseMove = false;
        }
    }//GEN-LAST:event_rbMoveActionPerformed

    // -------------------------------------------------------------------------
    // Affine transform helpers
    // -------------------------------------------------------------------------

    /**
     * Core affine transform applied to a shape's geometry.
     *
     * <p>Applies a 3x3 affine matrix centred on the shape's centroid so that
     * rotate and scale operations appear to happen around the shape itself
     * rather than around the map origin.</p>
     *
     * <p>Matrix convention:</p>
     * <pre>
     *   x' = x*xx + y*xy + dx
     *   y' = x*yx + y*yy + dy
     *   z' = z  (z is preserved)
     * </pre>
     *
     * <p>After updating the geometry, {@code InvalidateWholeMap} triggers a
     * repaint so the preview layer reflects the new shape position.</p>
     *
     * @param shape the shape to transform (lives in edtLayer)
     * @param xx    matrix element [0,0] - x-to-x factor
     * @param yx    matrix element [1,0] - x-to-y factor
     * @param xy    matrix element [0,1] - y-to-x factor
     * @param yy    matrix element [1,1] - y-to-y factor
     * @param dx    translation along X in map units
     * @param dy    translation along Y in map units
     */
    private void TransformSelectedShape(TGIS_Shape shape, double xx, double yx, double xy, double yy, double dx, double dy) {
        TGIS_Point centroid;

        if (shape == null) return;

        // Compute the geometric centroid so the pivot is at the shape centre
        centroid = shape.Centroid();

        // Apply the affine matrix with the centroid as the pivot point.
        // The DK translates to the centroid, applies the matrix, then
        // translates back, so rotate/scale stay centred on the shape.
        shape.Transform(TGIS_Utils.GisPoint3DFrom2D(centroid),
                        xx, yx, 0,   // row 0 of 3x3 matrix
                        xy, yy, 0,   // row 1
                        0,  0,  1,   // row 2 (z pass-through)
                        dx, dy, 0,   // translation vector
                        false        // do not recalculate layer extent yet
                       );

        // Repaint the viewer so the preview shape reflects the new geometry
        GIS.InvalidateWholeMap();
    }

    /**
     * Rotates the shape by {@code angle} radians around its centroid.
     *
     * <p>Standard 2-D rotation matrix:</p>
     * <pre>
     *   | cos(a)  sin(a) |
     *   |-sin(a)  cos(a) |
     * </pre>
     *
     * @param shape the shape to rotate
     * @param angle rotation angle in radians (positive = counter-clockwise)
     */
    private void RotateSelectedShape(TGIS_Shape shape, double angle) {
        TransformSelectedShape(shape, Math.cos(angle), Math.sin(angle), -Math.sin(angle), Math.cos(angle), 0, 0);
    }

    /**
     * Scales the shape by independent factors along each axis.
     * Values greater than 1 enlarge; values between 0 and 1 shrink.
     *
     * @param shape the shape to scale
     * @param x     scale factor along X
     * @param y     scale factor along Y
     */
    private void ScaleSelectedShape(TGIS_Shape shape, double x, double y) {
        TransformSelectedShape(shape, x, 0, 0, y, 0, 0);
    }

    /**
     * Translates (moves) the shape by the given map-coordinate offset.
     * The identity submatrix leaves all coordinates unchanged; only dx/dy move.
     *
     * @param shape the shape to move
     * @param x     translation along X in map units
     * @param y     translation along Y in map units
     */
    private void TranslateSelectedShape(TGIS_Shape shape, double x, double y) {
        TransformSelectedShape(shape, 1, 0, 0, 1, x, y);
    }

    // -------------------------------------------------------------------------
    // Application entry point
    // -------------------------------------------------------------------------

    /** Application entry point. Sets the Windows look-and-feel and shows the form. */
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
            java.util.logging.Logger.getLogger(ShapeOperationsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ShapeOperationsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ShapeOperationsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ShapeOperationsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ShapeOperationsUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblSelected;
    private javax.swing.JRadioButton rbMove;
    private javax.swing.JRadioButton rbRotate;
    private javax.swing.JRadioButton rbScale;
    // End of variables declaration//GEN-END:variables
    /** In-memory overlay layer used as a live edit preview. */
    private TGIS_LayerVector edtLayer;

    /**
     * The original shape in the source layer, placed in editable state via
     * MakeEditable.  CopyGeometry writes the final geometry back here on commit.
     */
    private TGIS_Shape currShape;

    /**
     * Working copy of {@code currShape} that lives in {@code edtLayer} and
     * receives all intermediate transform calls during the drag session.
     */
    private TGIS_Shape edtShape;

    /** Screen-pixel X from the previous mouse event; used for rotate/scale. */
    private int prevX;

    /** Screen-pixel Y from the previous mouse event; used for scale. */
    private int prevY;

    /**
     * Map-coordinate position from the previous mouse event.
     * Used to compute the translation delta in map units.
     */
    private TGIS_Point prevPtg;

    /**
     * Flag that enables transform dragging.
     * Toggled by the first mouse-release (select) and cleared by the second
     * mouse-release (commit).
     */
    private boolean handleMouseMove;
}
