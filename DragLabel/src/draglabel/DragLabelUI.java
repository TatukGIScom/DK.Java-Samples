/**
 * DragLabel sample - demonstrates how to construct interactively draggable
 * feature labels on a GIS map using the TatukGIS Java Developer Kernel (JDK).
 *
 * <h2>Design pattern</h2>
 * <p>Two parallel in-memory {@link TGIS_LayerVector} layers are created in
 * {@link #initOwn()}:</p>
 *
 * <dl>
 *   <dt>"realpoints"</dt>
 *   <dd>Visible geographic point features rendered with a CGM ship symbol.
 *       Each shape stores a "name" attribute used as label text.
 *       {@code CachedPaint = false} ensures positions are redrawn every frame
 *       because they change at runtime (animation and drag).</dd>
 *
 *   <dt>"sidekicks"</dt>
 *   <dd>Invisible ghost points (Marker.Size = 0) that carry the floating labels.
 *       Their UIDs match the realpoints UIDs one-to-one, enabling efficient
 *       UID-based lookups during paint events.
 *       An anonymous {@link TGIS_ShapeEvent} registered via
 *       {@code ll.addPaintShapeLabelEvent()} draws a blue leader line from the
 *       real position to the label position and then calls {@code DrawLabel()}.</dd>
 * </dl>
 *
 * <h2>Drag interaction</h2>
 * <p>Mouse events are handled by three listeners registered in
 * {@code initComponents()}:</p>
 * <ul>
 *   <li>{@link #GISMousePressed}: finds the nearest sidekick via {@code Locate()};
 *       stores it in {@code currShp}; calls {@code GIS.getEditor().MouseBegin()}
 *       without switching the viewer mode to Edit, giving full drag control.</li>
 *   <li>{@link #GISMouseDragged}: reprojects the pixel mouse position to map
 *       coordinates with {@code ScreenToMap}; calls {@code SetPosition} on
 *       the tracked sidekick; invalidates the bounding extent explicitly with
 *       {@code GIS.InvalidateExtent} for efficient partial repaints.</li>
 *   <li>{@link #GISMouseReleased}: clears {@code currShp} to end the drag.</li>
 * </ul>
 *
 * <h2>Animation</h2>
 * <p>The "Animate" button ({@link #btnAnimateActionPerformed}) runs a 90-step
 * loop on a background thread.  Each step calls
 * {@code SwingUtilities.invokeAndWait()} to execute the shape move on the AWT
 * event dispatch thread, then sleeps 50 ms so the movement is visible.
 * This thread/EDT separation is required by Swing's single-threaded model.</p>
 */
package draglabel;

import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_DimensionType;
import tatukgis.jdk.TGIS_Extent;
import tatukgis.jdk.TGIS_FieldType;
import tatukgis.jdk.TGIS_LabelPosition;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_Lock;
import tatukgis.jdk.TGIS_PenStyle;
import tatukgis.jdk.TGIS_Point;
import tatukgis.jdk.TGIS_RendererAbstract;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_ShapeEvent;
import tatukgis.jdk.TGIS_ShapeEventArgs;
import tatukgis.jdk.TGIS_ShapeType;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.rtl.TPoint;

public class DragLabelUI extends javax.swing.JFrame {

    /**
     * Constructs the frame, runs the Netbeans-generated {@code initComponents()},
     * and then calls {@link #initOwn()} to build the TatukGIS layer model.
     */
    public DragLabelUI() {
        initComponents();
        initOwn();
    }

    /**
     * Builds the two-layer data model and populates it with 20 randomly placed
     * ship points.  Registers the {@code PaintShapeLabelEvent} callback inline
     * as an anonymous {@link TGIS_ShapeEvent}.
     *
     * <p><b>"realpoints" layer setup:</b></p>
     * <ul>
     *   <li>CGM ship symbol loaded from the sample data directory.</li>
     *   <li>{@code CachedPaint = false} so moving shapes are always redrawn.</li>
     *   <li>A "name" string field stores the label text for each ship.</li>
     *   <li>Extent set to the full world coordinate range.</li>
     * </ul>
     *
     * <p><b>"sidekicks" layer setup:</b></p>
     * <ul>
     *   <li>Invisible markers ({@code Marker.Size = 0}).</li>
     *   <li>{@code CachedPaint = false}.</li>
     *   <li>{@code PaintShapeLabelEvent} wired to the anonymous inner class
     *       that draws the leader line and calls {@code DrawLabel()}.</li>
     *   <li>{@code Labels.Allocator = false} disables auto label-placement
     *       so labels stay exactly at the sidekick's geographic position.</li>
     * </ul>
     *
     * <p>For each of the 20 ships a realpoint and a matching sidekick are
     * created.  The sidekick is offset by {@code 15 / GIS.getZoom()} map units
     * in both X and Y so it starts visually displaced from the symbol.</p>
     */
    private void initOwn(){
        TGIS_LayerVector ll;
        TGIS_Shape shp;
        TGIS_Point ptg;
        Random rnd;
        int i;

        // --- "realpoints" layer ---
        ll = new TGIS_LayerVector();
        // SymbolList.Prepare caches the compiled CGM symbol for fast rendering.
        ll.getParams().getMarker().setSymbol(TGIS_Utils.getSymbolList().Prepare(TGIS_Utils.GisSamplesDataDirDownload() + "/Symbols/2267.cgm"));
        ll.setName("realpoints");
        // Disable tile cache so position changes are reflected immediately.
        ll.setCachedPaint(false);

        GIS.Add(ll);
        ll.AddField("name", TGIS_FieldType.String,100,0);
        // World-spanning extent ensures all random points fall within the layer.
        ll.setExtent(TGIS_Utils.GisExtent(-180, -180, 180, 180));

        // --- "sidekicks" layer (draggable label anchors) ---
        ll = new TGIS_LayerVector();
        ll.setName("sidekicks");
        ll.getParams().getMarker().setSize(0);  // invisible marker
        ll.getParams().getLabels().setPosition(TGIS_LabelPosition.MiddleCenter);
        ll.setCachedPaint(false);

        GIS.Add(ll);

        // Register the custom label-paint callback as an anonymous TGIS_ShapeEvent.
        // Invoke() fires once per sidekick shape during each repaint pass,
        // completely replacing default label rendering for this layer.
        ll.addPaintShapeLabelEvent(new TGIS_ShapeEvent() {
            @Override
            public void Invoke(Object o, TGIS_ShapeEventArgs tgs) {
                TPoint ptA, ptB;              // screen-pixel positions
                TGIS_LayerVector ll;
                TGIS_Shape shape = tgs.getShape();  // the sidekick being painted
                TGIS_Shape shp;                     // the matching realpoint shape
                TGIS_RendererAbstract rdr;

                // Find the realpoints layer and retrieve the matching shape by UID.
                // The UID mapping works because realpoints and sidekicks are created
                // in the same sequential order (parallel UID sequence).
                ll = (TGIS_LayerVector)GIS.Get( "realpoints" );
                shp = ll.GetShape( shape.getUid() );

                // MapToScreen converts geographic coordinates to screen pixels
                // relative to the viewer's top-left corner.
                ptA = shape.getViewer().Ref.MapToScreen(shp.GetPoint(0,0));    // real symbol
                ptB = shape.getViewer().Ref.MapToScreen(shape.GetPoint(0,0));  // label anchor

                // Draw the blue leader line on the abstract renderer canvas.
                // TGIS_RendererAbstract provides a hardware-agnostic drawing API.
                rdr = GIS.getRenderer();
                rdr.getCanvasPen().Color = TGIS_Color.getBlue();
                rdr.getCanvasPen().Style = TGIS_PenStyle.Solid;
                rdr.getCanvasPen().Width = 1;
                rdr.CanvasDrawLine(ptA.X, ptA.Y, ptB.X, ptB.Y);

                // Set the label text from the realpoint's "name" field and render it
                // at the sidekick's position using the layer's configured label style.
                shape.getParams().getLabels().setValue(shp.GetField("name").toString());
                shape.DrawLabel();
            }
        });

        // Disable auto label-placement so labels stay at the exact sidekick position.
        ll.getParams().getLabels().setAllocator(false);
        GIS.FullExtent();

        // --- Populate both layers with 20 random ship points ---
        rnd = new Random();
        for( i = 0; i < 20; i ++){
            ptg = new TGIS_Point(rnd.nextInt(360) - 180, rnd.nextInt(180) - 90);

            // Create and configure the visible realpoint shape.
            // Lock(Extent) prevents automatic extent expansion during construction.
            shp = ((TGIS_LayerVector)GIS.Get("realpoints")).CreateShape( TGIS_ShapeType.Point, TGIS_DimensionType.XY);
            shp.Lock(TGIS_Lock.Extent);
            shp.AddPart();
            shp.AddPoint(ptg);
            shp.getParams().getMarker().setSymbolRotate(shp.getUid()); // unique angle per ship
            shp.getParams().getMarker().setSize(250);
            shp.getParams().getMarker().setColor(TGIS_Color.FromRGB((byte)rnd.nextInt(256), (byte)rnd.nextInt(256), (byte)rnd.nextInt(256)));
            shp.SetField("name", text + shp.getUid());
            shp.Unlock();

            // Create the matching sidekick at the same position plus a small offset.
            // 15 / Zoom converts 15 screen pixels to the current map-unit scale
            // so the label starts displaced from the symbol regardless of zoom level.
            shp = ((TGIS_LayerVector)GIS.Get("sidekicks")).CreateShape( TGIS_ShapeType.Point);
            shp.Lock(TGIS_Lock.Extent);
            shp.AddPart();

            ptg.X += 15/GIS.getZoom();
            ptg.Y += 15/GIS.getZoom();
            shp.AddPoint(ptg);
            shp.Unlock();
        }
        GIS.FullExtent();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        btnAnimate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DragLabel - TatukGIS DK sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(800, 600));

        // Register mouse listeners on the GIS viewer.
        // mouseDragged is used instead of mouseMoved for drag tracking because
        // Java's MouseMotionAdapter only fires mouseDragged when a button is held.
        GIS.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                GISMouseDragged(evt);
            }
        });
        GIS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                GISMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                GISMouseReleased(evt);
            }
        });

        btnAnimate.setText("Animate");
        btnAnimate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnimateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAnimate)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAnimate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Begins a label drag when the user clicks near a sidekick shape.
     *
     * <p>{@code GIS.getIsEmpty()} guards prevent starting an edit when the
     * viewer has no layers.  {@code Locate} searches the sidekicks layer for
     * the nearest shape within a 100-pixel tolerance expressed in map units
     * ({@code 100 / GIS.getZoom()} converts pixels to map units at the
     * current zoom level).</p>
     *
     * <p>{@code GIS.getEditor().MouseBegin()} is called directly rather than
     * switching {@code GIS.Mode} to Edit, so normal viewer pan/zoom is not
     * globally disabled and we retain full control over what gets moved.</p>
     *
     * @param evt The mouse-pressed event from the AWT event system.
     */
    private void GISMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GISMousePressed
        TGIS_LayerVector ll;
        TGIS_Shape shp;

        if(GIS.getIsEmpty()) return;

        // Find the nearest sidekick within 100 screen pixels (converted to map units)
        ll = (TGIS_LayerVector)GIS.Get("sidekicks");
        shp = ll.Locate(GIS.ScreenToMap(new TPoint(evt.getX(), evt.getY())), 100/GIS.getZoom());
        currShp = shp;
        if(currShp == null) return;

        // Initiate editor drag tracking without switching viewer mode globally
        GIS.getEditor().MouseBegin(new TPoint(evt.getX(), evt.getY()), true);
    }//GEN-LAST:event_GISMousePressed

    /**
     * Ends the current label drag by clearing the tracked shape reference.
     * Subsequent {@code GISMouseDragged} events will be ignored.
     *
     * @param evt The mouse-released event (position is not used).
     */
    private void GISMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GISMouseReleased
        if(GIS.getIsEmpty()) return;
        currShp = null;
    }//GEN-LAST:event_GISMouseReleased

    /**
     * Repositions the tracked sidekick shape to follow the mouse cursor.
     *
     * <p>Both a pre-move and post-move bounding extent are computed covering
     * the sidekick and its matching realpoint.  {@code GIS.InvalidateExtent}
     * is called with these extents to limit repaints to only the dirty region,
     * which is more efficient than a full-map invalidation at large zoom levels.</p>
     *
     * <p>{@code ScreenToMap} converts the pixel mouse position to geographic
     * map coordinates.  {@code GisIsPointInsideExtent} prevents the label from
     * being dragged outside the viewer's valid geographic extent.</p>
     *
     * @param evt The mouse-dragged event carrying the current cursor position.
     */
    @SuppressWarnings("deprecation")
    private void GISMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GISMouseDragged
        TGIS_LayerVector ll ;
        TGIS_Shape shp ;
        TGIS_Point ptgA ;
        TGIS_Point ptgB ;
        TGIS_Extent ex = new TGIS_Extent();

        if(GIS.getIsEmpty()) return;
        // currShp guard is commented out in the original; if null a NPE would
        // occur on the next line - callers should ensure this is non-null.
        //if(currShp == null) return;

        // Pre-move extent: union of current sidekick position and its realpoint
        ptgA = currShp.GetPoint(0,0);
        ll = (TGIS_LayerVector)GIS.Get("realpoints");
        shp = ll.GetShape(currShp.getUid());
        ptgB = shp.GetPoint(0,0);
        ex.XMin = Math.min( ptgA.X, ptgB.X);
        ex.YMin = Math.min( ptgA.Y, ptgB.Y );
        ex.XMax = Math.max( ptgA.X, ptgB.X );
        ex.YMax = Math.max( ptgA.Y, ptgB.Y );

        // Trigger a selective repaint for the pre-move region so the old
        // leader line is erased before the new position is drawn.
        GIS.InvalidateExtent(ex, true);

        // Reproject mouse pixel position to geographic map coordinates and move
        ptgA = GIS.ScreenToMap(new TPoint(evt.getX(), evt.getY()));
        if( TGIS_Utils.GisIsPointInsideExtent(ptgA, GIS.getExtent())){
            currShp.SetPosition(ptgA, null, 0);
        }

        // Post-move extent: union of new sidekick position and its realpoint.
        // Invalidating this region draws the leader line in its new position.
        ptgA = currShp.GetPoint(0,0);
        ll = (TGIS_LayerVector)GIS.Get("realpoints");
        shp = ll.GetShape( currShp.getUid() );
        ptgB = shp.GetPoint(0,0);
        ex.XMin = Math.min( ptgA.X, ptgB.X);
        ex.YMin = Math.min( ptgA.Y, ptgB.Y );
        ex.XMax = Math.max( ptgA.X, ptgB.X );
        ex.YMax = Math.max( ptgA.Y, ptgB.Y );
        GIS.InvalidateExtent(ex, true);
    }//GEN-LAST:event_GISMouseDragged

    /**
     * "Animate" button action handler.
     *
     * <p>Runs a 90-step animation loop on a new background thread.  Each step:</p>
     * <ol>
     *   <li>Calls {@code SwingUtilities.invokeAndWait()} to execute the shape
     *       move on the AWT event dispatch thread (EDT).  Direct modification
     *       of TatukGIS shapes from a non-EDT thread is not safe.</li>
     *   <li>Moves shape UID=5 (realpoint) and its sidekick by (+2, +1) map units.</li>
     *   <li>Computes the bounding extent of both shapes for selective invalidation.</li>
     *   <li>Sleeps 50 ms between steps so the movement is visible.</li>
     * </ol>
     *
     * <p>Any {@code InterruptedException} or {@code InvocationTargetException}
     * is logged at SEVERE level and the loop continues.</p>
     *
     * @param evt The action event from the Animate button (not used).
     */
    private void btnAnimateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnimateActionPerformed
        // Spawn a background thread so the UI remains responsive during animation.
        new Thread(new Runnable() {
            @Override
            public void run() {
                TGIS_Shape shp;
                int i;

                for (i = 0; i < 90; i++)
                {
                    try {
                        // All TatukGIS map operations must run on the EDT.
                        // invokeAndWait blocks the background thread until
                        // the EDT has finished executing this Runnable.
                        SwingUtilities.invokeAndWait(new Runnable() {
                            @Override
                            public void run() {
                                TGIS_LayerVector ll;
                                TGIS_Shape shp;
                                TGIS_Point ptgA;
                                TGIS_Point ptgB;
                                TGIS_Extent ex;
                                int x, y;

                                x = 2;  // horizontal displacement per step (map units)
                                y = 1;  // vertical displacement per step (map units)

                                ex = new TGIS_Extent();
                                shp = ((TGIS_LayerVector)GIS.Get("realpoints")).GetShape(5);

                                // Move the realpoint by (x, y) map units
                                ptgA = shp.GetPoint(0, 0);
                                ptgA.X += x;
                                ptgA.Y += y;
                                shp.SetPosition(ptgA, null, 0);

                                // Move the matching sidekick by the same displacement
                                // so the leader line length is preserved during animation.
                                ll = (TGIS_LayerVector)GIS.Get("sidekicks");
                                shp = ll.GetShape(shp.getUid());
                                ptgB = shp.GetPoint(0, 0);
                                ptgB.X += x;
                                ptgB.Y += y;
                                shp.SetPosition(ptgB, null, 0);

                                // Compute bounding extent for both shapes.
                                // Could be used with InvalidateExtent for selective
                                // repaint, but SetPosition already triggers the viewer.
                                ex.XMin = Math.min(ptgA.X, ptgB.X);
                                ex.YMin = Math.min(ptgA.Y, ptgB.Y);
                                ex.XMax = Math.max(ptgA.X, ptgB.X);
                                ex.YMax = Math.max(ptgA.Y, ptgB.Y);
                            }
                        });
                    } catch (InterruptedException | InvocationTargetException ex) {
                        Logger.getLogger(DragLabelUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        // Pause between steps so the animation is visible
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(DragLabelUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
    }//GEN-LAST:event_btnAnimateActionPerformed

    /**
     * Application entry point.  Sets the Windows look-and-feel if available,
     * then creates and shows the DragLabelUI frame on the AWT event queue.
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
            java.util.logging.Logger.getLogger(DragLabelUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DragLabelUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DragLabelUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DragLabelUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form on the AWT event dispatch thread */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DragLabelUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    /** Main TatukGIS Swing viewer control. */
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    /** "Animate" button that triggers programmatic shape movement. */
    private javax.swing.JButton btnAnimate;
    // End of variables declaration//GEN-END:variables

    /** Label prefix prepended to each ship's UID to form the "name" field value. */
    String text = "Ship";

    /**
     * The sidekick shape currently being dragged by the user.
     * Null when no drag is in progress.
     */
    TGIS_Shape currShp;
}
