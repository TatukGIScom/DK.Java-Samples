/*
 * =============================================================================
 * This source code is a part of TatukGIS Developer Kernel.
 * =============================================================================
 *
 * Transform sample - Polynomial georeferencing of a raster image.
 * Java / JDK edition.
 *
 * This sample demonstrates how to georeference (rectify) an unregistered
 * raster image using the TatukGIS JDK polynomial transform API:
 *
 *   TGIS_TransformPolynomial
 *     Maps pixel (source) coordinates to real-world (target) coordinates using
 *     ground-control points (GCPs) and a fitted polynomial.  Order:
 *       First (affine):    translation, rotation, scale, shear.  Min 3 GCPs.
 *       Second:            corrects gentle curvature.  Min 6 GCPs.
 *       Third:             corrects stronger distortions.  Min 10 GCPs.
 *
 *   TGIS_LayerPixel.setTransform / getTransform
 *     Assigning a transform to a raster layer causes the DK to warp the image
 *     on-the-fly at render time, without modifying the source file.
 *
 *   setCuttingPolygon
 *     Optional WKT polygon in source/pixel coordinates that masks the visible
 *     area to a region of interest.
 *
 * Workflow:
 *   btnTransform  - 4-GCP first-order polynomial + CRS assignment (EPSG 102748).
 *   btnCutting    - Same GCPs + setCuttingPolygon masking the image.
 *   btnSave       - Save current transform to a ".trn" sidecar file.
 *   btnRead       - Reload a previously saved transform sidecar.
 *
 * Data: Samples/Rectify/satellite.jpg  (an unrectified aerial/satellite image)
 * =============================================================================
 */
package transform;

import java.awt.Toolkit;
import tatukgis.jdk.TGIS_LayerPixel;
import tatukgis.jdk.TGIS_PolynomialOrder;
import tatukgis.jdk.TGIS_TransformPolynomial;
import tatukgis.jdk.TGIS_Utils;

/**
 * Main window for the Transform sample.
 *
 * <p>Demonstrates polynomial georeferencing of a raster image using
 * {@link TGIS_TransformPolynomial} and {@code TGIS_LayerPixel.setTransform}.</p>
 */
public class TransformUI extends javax.swing.JFrame {

    /** Creates the form and initialises all Swing components and GIS state. */
    public TransformUI() {
        initComponents();
        initOwn();
    }

    /**
     * Initialises GIS-specific state after the Swing components are ready.
     * Opens the unrectified satellite image.  No transform is applied at
     * startup; the user clicks buttons to georeference.
     */
    private void initOwn(){
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));

        // GisSamplesDataDirDownload resolves the shared sample data folder
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/Samples/Rectify/satellite.jpg");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        btnTransform = new javax.swing.JButton();
        btnCutting = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnRead = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Transform - TatukGIS DK sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(800, 600));

        btnTransform.setText("Transform");
        btnTransform.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTransformActionPerformed(evt);
            }
        });

        btnCutting.setText("Cutting polygon");
        btnCutting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCuttingActionPerformed(evt);
            }
        });

        btnSave.setText("Save to file");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnRead.setText("Read from file");
        btnRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCutting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTransform, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRead, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTransform)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCutting)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRead)
                .addContainerGap(479, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // -------------------------------------------------------------------------
    // Button action handlers
    // -------------------------------------------------------------------------

    /**
     * Applies a first-order polynomial georeference to the satellite image.
     *
     * <ol>
     *   <li>Creates {@link TGIS_TransformPolynomial} and adds four corner GCPs.</li>
     *   <li>Fits a first-order (affine) polynomial via {@code Prepare()}.</li>
     *   <li>Assigns the transform to the raster layer and activates warping.</li>
     *   <li>Declares the CRS via {@code SetCSByEPSG(102748)} (NAD83 / Washington
     *       South State Plane in US survey feet).</li>
     *   <li>Recomputes the layer extent and zooms to full extent.</li>
     * </ol>
     *
     * <p>Source coordinates use image convention where Y increases upward from
     * the bottom of the image (hence the negative Y for the bottom-edge GCPs).
     * Target coordinates are in State Plane feet (EPSG 102748).</p>
     */
    private void btnTransformActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTransformActionPerformed
        TGIS_TransformPolynomial trn;
        TGIS_LayerPixel lp;

        // Access the first (and only) layer, which is the satellite image
        lp = (TGIS_LayerPixel)GIS.getItems().getItems(0);

        trn = new TGIS_TransformPolynomial();

        // Add four corner GCPs: source pixel -> target real-world coordinates.
        // AddPoint parameters: source point, target point, GCP index, enabled.
        trn.AddPoint(TGIS_Utils.GisPoint(-0.5, -944.5),
                      TGIS_Utils.GisPoint(1273285.84090909, 239703.615056818),
                      0,    // GCP index
                      true  // enabled
                     );
        trn.AddPoint(TGIS_Utils.GisPoint(-0.5, 0.5),
                      TGIS_Utils.GisPoint(1273285.84090909, 244759.524147727),
                      1,
                      true
                     );
        trn.AddPoint(TGIS_Utils.GisPoint(1246.5, 0.5),
                      TGIS_Utils.GisPoint(1279722.65909091, 245859.524147727),
                      2,
                      true
                     );
        trn.AddPoint(TGIS_Utils.GisPoint(1246.5, -944.5),
                      TGIS_Utils.GisPoint(1279744.93181818, 239725.887784091),
                      3,
                      true
                     );

        // Fit the polynomial (First = affine: translation, rotation, scale, shear)
        trn.Prepare(TGIS_PolynomialOrder.First);

        // Assign the transform to the layer and activate on-the-fly warping
        lp.setTransform(trn);
        lp.getTransform().setActive(true);

        // Declare the CRS so the viewer can align with other layers
        lp.SetCSByEPSG(102748);

        // Recompute extent in the new CRS and zoom to show the whole image
        GIS.RecalcExtent();
        GIS.FullExtent();
    }//GEN-LAST:event_btnTransformActionPerformed

    /**
     * Applies a first-order polynomial transform with a cutting-polygon mask.
     *
     * <p>Identical GCPs to {@link #btnTransformActionPerformed} but adds a
     * WKT polygon in pixel (source) coordinates via {@code setCuttingPolygon}.
     * Only pixels inside the polygon are rendered after warping; the rest of
     * the image is clipped at render time.</p>
     *
     * <p>The CRS is not set in this variant, so the result is displayed in the
     * warped target coordinate space without a named projection.</p>
     */
    private void btnCuttingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCuttingActionPerformed
        TGIS_TransformPolynomial trn;
        TGIS_LayerPixel lp;

        lp = (TGIS_LayerPixel)GIS.getItems().getItems(0);

        trn = new TGIS_TransformPolynomial();

        // Four corner GCPs (same pixel-to-world mapping as btnTransformActionPerformed)
        trn.AddPoint(TGIS_Utils.GisPoint(-0.5, -944.5),
                      TGIS_Utils.GisPoint(1273285.84090909, 239703.615056818),
                      0,
                      true
                     );
        trn.AddPoint(TGIS_Utils.GisPoint(-0.5, 0.5),
                      TGIS_Utils.GisPoint(1273285.84090909, 244759.524147727),
                      1,
                      true
                     );
        trn.AddPoint(TGIS_Utils.GisPoint(1246.5, 0.5),
                      TGIS_Utils.GisPoint(1279722.65909091, 244759.524147727),
                      2,
                      true
                     );
        trn.AddPoint(TGIS_Utils.GisPoint(1246.5, -944.5),
                      TGIS_Utils.GisPoint(1279744.93181818, 239725.887784091),
                      3,
                      true
                     );

        // WKT polygon in SOURCE (pixel) coordinates that masks the visible region.
        // Pixels outside this polygon are not rendered after the warp is applied.
        trn.setCuttingPolygon("POLYGON((421.508902077151 -320.017804154303, " +
                             "518.161721068249 -223.364985163205, " +
                             "688.725519287834 -210.572700296736, " +
                             "864.974777448071 -254.635014836795, " +
                             "896.244807121662 -335.652818991098, " +
                             "894.823442136499 -453.626112759644, " +
                             "823.755192878338 -615.661721068249, " +
                             "516.740356083086 -607.13353115727,  " +
                             "371.761127596439 -533.222551928783, " +
                             "340.491097922849 -456.46884272997,  " +
                             "421.508902077151 -320.017804154303))");

        // Fit the polynomial and activate the transform
        trn.Prepare(TGIS_PolynomialOrder.First);
        lp.setTransform(trn);
        lp.getTransform().setActive(true);

        GIS.RecalcExtent();
        GIS.FullExtent();
    }//GEN-LAST:event_btnCuttingActionPerformed

    /**
     * Saves the current polynomial transform to a ".trn" sidecar file.
     *
     * <p>The sidecar stores all GCPs and polynomial coefficients so the
     * georeferencing solution can be reloaded later without re-entering data.
     * This is a no-op if no transform has been assigned yet.</p>
     */
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        TGIS_LayerPixel lp;

        lp = (TGIS_LayerPixel)GIS.getItems().getItems(0);

        // Guard: only save if a transform has been assigned to the layer
        if (lp.getTransform() != null)
            lp.getTransform().SaveToFile("satellite.jpg" + GIS_TRN_EXT);

    }//GEN-LAST:event_btnSaveActionPerformed

    /**
     * Loads a polynomial transform from a ".trn" sidecar file and applies it.
     *
     * <p>If the layer already has a transform assigned, loads directly into it.
     * Otherwise, creates a new {@link TGIS_TransformPolynomial}, loads from file,
     * and assigns it.  In both cases the transform is activated and the view
     * is updated.</p>
     */
    private void btnReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReadActionPerformed
        TGIS_TransformPolynomial trn;
        TGIS_LayerPixel lp;

        lp = (TGIS_LayerPixel)GIS.getItems().getItems(0);

        // Re-use the existing transform object if one is already assigned;
        // otherwise create a new one and assign it after loading.
        if (lp.getTransform() != null)
            // Load directly into the existing transform object
            lp.getTransform().LoadFromFile("satellite.jpg" + GIS_TRN_EXT);
        else {
            trn = new TGIS_TransformPolynomial();
            trn.LoadFromFile("satellite.jpg" + GIS_TRN_EXT);
            lp.setTransform(trn);
        }

        // Activate on-the-fly warping and update the view
        lp.getTransform().setActive(true);

        GIS.RecalcExtent();
        GIS.FullExtent();
    }//GEN-LAST:event_btnReadActionPerformed

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
            java.util.logging.Logger.getLogger(TransformUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TransformUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TransformUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TransformUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TransformUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.JButton btnCutting;
    private javax.swing.JButton btnRead;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnTransform;
    // End of variables declaration//GEN-END:variables
    // Extension for transform sidecar files: "<image_path>.trn"
    private String GIS_TRN_EXT = ".trn";

 }
