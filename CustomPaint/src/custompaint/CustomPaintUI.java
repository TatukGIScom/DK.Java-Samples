/**
 * CustomPaint sample - demonstrates how to perform custom drawing on top of a
 * TatukGIS map viewer using per-shape and per-frame paint-hook callbacks
 * in the TatukGIS Java Developer Kernel (JDK).
 *
 * <p>Key concepts demonstrated:</p>
 *
 * <dl>
 *   <dt>PaintShapeEvent (addPaintShapeEvent)</dt>
 *   <dd>A per-shape callback registered on a {@link TGIS_LayerVector}.  It fires
 *       once for every shape in that layer during each repaint cycle, replacing
 *       the default rendering.  The anonymous {@link TGIS_ShapeEvent} implementation
 *       in {@code initOwn()} calls {@code GIS.MapToScreen(shape.PointOnShape())} to
 *       convert the geographic position to screen pixels, then uses the viewer's
 *       renderer ({@link tatukgis.jdk.TGIS_RendererSwing}) Canvas* methods to draw
 *       primitives: rectangles, ellipses, bitmaps from file, and raw pixel arrays.</dd>
 *
 *   <dt>GISPaintExtra (RenderEvent)</dt>
 *   <dd>A viewer-level callback (wired in the Netbeans form editor) fired once after
 *       all layers have rendered.  Demonstrates accessing the renderer's native
 *       {@link Graphics2D} canvas handle to call Java2D drawing APIs directly.
 *       Using the native handle is NOT recommended for portable code; prefer the
 *       Canvas* methods on {@link TGIS_RendererSwing} instead.</dd>
 * </dl>
 *
 * <p>Layer layout:</p>
 * <pre>
 *   "CustomPaint" TGIS_LayerVector with four invisible point shapes (Marker.Size=0):
 *     "Rectangle" at (-25,  25)  -> CanvasDrawRectangle (red border, yellow fill)
 *     "Ellipse"   at ( 25,  25)  -> CanvasDrawEllipse   (black border, red fill)
 *     "Image1"    at (-25, -25)  -> CanvasDrawBitmap(TGIS_Bitmap from police.bmp)
 *     "Image2"    at ( 25, -25)  -> CanvasDrawBitmap(int[] raw ARGB pixel array)
 * </pre>
 *
 * <p>The raw pixel array {@code px} is a 5x5 image in row-major ARGB order:
 *    row 0 = opaque red, diagonal column 2 = opaque blue, remainder transparent.</p>
 */
package custompaint;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import tatukgis.jdk.TGIS_Bitmap;
import tatukgis.jdk.TGIS_BitmapFormat;
import tatukgis.jdk.TGIS_BitmapLinesOrder;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_FieldType;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_Lock;
import tatukgis.jdk.TGIS_RendererSwing;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_ShapeEvent;
import tatukgis.jdk.TGIS_ShapeEventArgs;
import tatukgis.jdk.TGIS_ShapeType;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.rtl.TPoint;
import tatukgis.rtl.TRect;

public class CustomPaintUI extends javax.swing.JFrame {

    /**
     * Constructs the form, initialises the Netbeans designer components, and
     * then calls {@link #initOwn()} to build the TatukGIS layers and register
     * the paint callbacks.
     */
    public CustomPaintUI() {
        initComponents();
        initOwn();
    }

    /**
     * Initialises the raw ARGB pixel array used for the Image2 drawing case.
     *
     * <p>The array is a 5x5 image in row-major order (5 ints per row):</p>
     * <ul>
     *   <li>Row 0 (indices 0-4): opaque red (0xFFFF0000)</li>
     *   <li>Diagonal column 2 pixels [7],[12],[17],[22]: opaque blue (0xFF0000FF)</li>
     *   <li>All other pixels: fully transparent (0x00000000)</li>
     * </ul>
     *
     * <p>The format is ARGB: 0xAARRGGBB per int element, matching
     * {@link TGIS_BitmapFormat#ARGB}.</p>
     */
    private void initialize_pixels()
    {
        px = new int[25];

        // Row 0: five opaque red pixels
        px[0] = 0xFFFF0000;
        px[1] = 0xFFFF0000;
        px[2] = 0xFFFF0000;
        px[3] = 0xFFFF0000;
        px[4] = 0xFFFF0000;
        // Row 1: transparent, blue at column 2
        px[5] = 0x00000000;
        px[6] = 0x00000000;
        px[7] = 0xFF0000FF;  // opaque blue
        px[8] = 0x00000000;
        px[9] = 0x00000000;
        // Row 2
        px[10] = 0x00000000;
        px[11] = 0x00000000;
        px[12] = 0xFF0000FF;
        // note: px[13] is implicitly 0 (default int)
        px[14] = 0x00000000;
        px[15] = 0x00000000;
        px[16] = 0x00000000;
        px[17] = 0xFF0000FF;
        // Row 3
        px[18] = 0x00000000;
        px[19] = 0x00000000;
        px[20] = 0x00000000;
        px[21] = 0x00000000;
        // Row 4: blue at column 2
        px[22] = 0xFF0000FF;
        px[23] = 0x00000000;
        px[23] = 0x00000000; // note: index 23 assigned twice
    }

    /**
     * Builds the demo layer, registers the PaintShapeEvent callback, creates
     * the four invisible point shapes, and loads the file-based bitmap.
     *
     * <p>The layer has a single {@code "type"} string field.  The anonymous
     * {@link TGIS_ShapeEvent} registered via {@code ll.addPaintShapeEvent()}
     * reads this field on each repaint to choose the drawing primitive.</p>
     *
     * <p>Note that in Java the PaintShapeEvent callback is registered using an
     * anonymous inner class (lambda-style pre-Java-8 pattern) rather than a
     * named method, because the JDK COM-bridge does not use .NET delegates.</p>
     */
    private void initOwn(){
        TGIS_Shape shp;

        initialize_pixels();

        // Create the demo layer; no file backing - all geometry is in memory.
        ll = new TGIS_LayerVector();
        ll.setName("CustomPaint");

        GIS.Add(ll);

        // Register the per-shape paint callback as an anonymous TGIS_ShapeEvent.
        // The Invoke method fires once per shape per repaint cycle.
        ll.addPaintShapeEvent(new TGIS_ShapeEvent() {
            @Override
            public void Invoke(Object o, TGIS_ShapeEventArgs tgs) {
                TPoint pt;    // screen-pixel position of the shape
                TRect rect;   // destination rect for the current primitive
                TRect rect2;  // destination rect for the label (20 pixels above)

                rect = new TRect();
                rect2 = new TRect();

                // MapToScreen converts geographic coordinates to screen pixels.
                // PointOnShape() returns the representative geometric point.
                pt = GIS.MapToScreen(tgs.getShape().PointOnShape());

                // Build the 20x20 destination rectangle for the primitive
                rect.Left = pt.X;
                rect.Top = pt.Y;
                rect.Right = pt.X + 20;
                rect.Bottom = pt.Y + 20;

                // Build the label rectangle 20 pixels above the primitive
                rect2.Left = pt.X;
                rect2.Top = pt.Y - 20;
                rect2.Right = pt.X + 50;
                rect2.Bottom = pt.Y - 20;

                // Ensure the renderer reference is current for this shape
                tgs.getShape().getLayer().getRenderer();

                // --- Select drawing primitive by "type" field ---

                if ("Rectangle".equals(tgs.getShape().GetField("type")))
                {
                    // Yellow-filled, red-bordered rectangle
                    GIS.getRenderer().getCanvasPen().Color = TGIS_Color.getRed();
                    GIS.getRenderer().getCanvasBrush().Color = TGIS_Color.getYellow();
                    GIS.getRenderer().CanvasDrawRectangle(rect);
                    pt.Y -= 20;
                    GIS.getRenderer().CanvasDrawText(rect2, "Rectangle");
                }
                else if ("Ellipse".equals(tgs.getShape().GetField("type")))
                {
                    // Red-filled, black-bordered ellipse; width=20, height=20
                    GIS.getRenderer().getCanvasPen().Color = TGIS_Color.getBlack();
                    GIS.getRenderer().getCanvasBrush().Color = TGIS_Color.getRed();
                    GIS.getRenderer().CanvasDrawEllipse(pt.X, pt.Y, 20, 20);
                    pt.Y -= 20;
                    GIS.getRenderer().CanvasDrawText(rect2, "Ellipse");
                }
                else if ("Image1".equals(tgs.getShape().GetField("type")))
                {
                    // Stretch the TGIS_Bitmap (police.bmp) into the 20x20 rect.
                    // TGIS_Bitmap is TatukGIS's cross-renderer bitmap wrapper.
                    GIS.getRenderer().CanvasDrawBitmap(
                    bmp,
                    rect);
                    pt.Y -= 20;
                    GIS.getRenderer().CanvasDrawText(rect2, "Image1");
                }
                else if ("Image2".equals(tgs.getShape().GetField("type")))
                {
                    // Draw using a raw ARGB pixel array.
                    // new TPoint(5,5) = source offset: skip the first 5 pixels
                    //   (the solid red row) and start reading from pixel (5,5).
                    // TGIS_BitmapFormat.ARGB = 0xAARRGGBB layout per int.
                    // TGIS_BitmapLinesOrder.Down = scanlines run top-to-bottom.
                    GIS.getRenderer().CanvasDrawBitmap(
                      px,
                      new TPoint(5, 5),
                      rect,
                      TGIS_BitmapFormat.ARGB,
                      TGIS_BitmapLinesOrder.Down
                    );
                    pt.Y -= 20;
                    GIS.getRenderer().CanvasDrawText(rect2, "Image2");
                }
            }
        });

        // Add the "type" attribute field that the paint callback reads.
        ll.AddField("type", TGIS_FieldType.String, 100, 0);

        // --- Create the four demo point shapes ---
        // Marker.Size = 0 means no default symbol is drawn; all visuals come
        // from the PaintShapeEvent callback registered above.
        // Lock(Extent) prevents the layer from auto-expanding its extent
        // during AddPoint; Unlock() releases it after shape construction.

        shp = ll.CreateShape(TGIS_ShapeType.Point);
        shp.Lock(TGIS_Lock.Extent);
        shp.AddPart();
        shp.AddPoint(TGIS_Utils.GisPoint(-25, 25));  // NW quadrant
        shp.getParams().getMarker().setSize(0);
        shp.SetField("type", "Rectangle");
        shp.Unlock();

        shp = ll.CreateShape(TGIS_ShapeType.Point);
        shp.Lock(TGIS_Lock.Extent);
        shp.AddPart();
        shp.AddPoint(TGIS_Utils.GisPoint(25, 25));   // NE quadrant
        shp.getParams().getMarker().setSize(0);
        shp.SetField("type", "Ellipse");
        shp.Unlock();

        shp = ll.CreateShape(TGIS_ShapeType.Point);
        shp.Lock(TGIS_Lock.Extent);
        shp.AddPart();
        shp.AddPoint(TGIS_Utils.GisPoint(-25, -25)); // SW quadrant
        shp.getParams().getMarker().setSize(0);
        shp.SetField("type", "Image1");
        shp.Unlock();

        shp = ll.CreateShape(TGIS_ShapeType.Point);
        shp.Lock(TGIS_Lock.Extent);
        shp.AddPart();
        shp.AddPoint(TGIS_Utils.GisPoint(25, -25));  // SE quadrant
        shp.getParams().getMarker().setSize(0);
        shp.SetField("type", "Image2");
        shp.Unlock();

        // Set the layer extent to enclose all four shapes
        ll.setExtent(TGIS_Utils.GisExtent(-100, -100, 100, 100)) ;

        // Load the bitmap used by the "Image1" drawing case.
        bmp = new TGIS_Bitmap();
        bmp.LoadFromFile(TGIS_Utils.GisSamplesDataDirDownload() + "/Symbols/police.bmp");

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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CustomPaint - TatukGIS DK sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(800, 600));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * PaintExtraEvent callback - fired once per frame after all layers have
     * finished rendering.  This is the right place for full-frame overlays
     * that must appear on top of all layer content.
     *
     * <p>This handler demonstrates accessing the renderer's native
     * {@link Graphics2D} canvas handle to call Java2D drawing APIs.
     * Using the native handle is NOT recommended for portable code; prefer
     * {@link tatukgis.jdk.TGIS_RendererAbstract} Canvas* methods instead,
     * which work regardless of the underlying backend.</p>
     *
     * @param evt The render event carrying the renderer and draw-mode hint.
     */
    private void GISPaintExtra(tatukgis.jdk.RenderEvent evt) {//GEN-FIRST:event_GISPaintExtra
        Graphics2D grp;

        // drawing with native objects, not recommended for portable code.
        // Check the renderer type before casting CanvasNative() to ensure
        // the correct backend is active.
        if( evt.getRenderer() instanceof TGIS_RendererSwing){
            // CanvasNative() returns a java.awt.Graphics2D for the Swing renderer.
            grp = (Graphics2D)GIS.getRenderer().CanvasNative();

            // Draw a blue "Hello" text directly on the Java2D canvas
            grp.setColor(Color.BLUE);
            grp.setFont(new Font("Arial", Font.PLAIN, 12));
            grp.drawString("Hello from Graphics2D", 50, 50);
        }else{
            // Other renderer backends: extend with appropriate native cast here
        }
    }//GEN-LAST:event_GISPaintExtra

    /**
     * Application entry point.  Sets the Windows look-and-feel if available,
     * then creates and shows the CustomPaintUI frame on the AWT event queue.
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
            java.util.logging.Logger.getLogger(CustomPaintUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CustomPaintUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CustomPaintUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CustomPaintUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CustomPaintUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    /** Main TatukGIS Swing viewer control. */
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    // End of variables declaration//GEN-END:variables
    /** The "CustomPaint" in-memory vector layer. */
    private TGIS_LayerVector ll;
    /** Bitmap loaded from police.bmp for the Image1 drawing case. */
    private TGIS_Bitmap bmp;
    /** Raw ARGB pixel array (5x5) for the Image2 drawing case. */
    private int[] px;

}
