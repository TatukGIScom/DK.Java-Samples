/*
 * =============================================================================
 * This source code is a part of TatukGIS Developer Kernel.
 * =============================================================================
 *
 * SelectByShape sample — demonstrates how to select vector features by drawing
 * an arbitrary shape (circle or rectangle) interactively on the map.
 *
 * Key concepts shown:
 *   - Interactive rubber-band drawing of a selection shape on the GIS viewer
 *     using AWT MouseListener / MouseMotionListener and the PaintListener hook.
 *   - Converting screen pixel coordinates to geographic map coordinates via
 *     ScreenToMap so the drawn shape is in the layer's coordinate system.
 *   - Building a circular selection area using TGIS_Topology.MakeBuffer, which
 *     approximates a circle as a polygon around a point shape using a given
 *     radius in map units.
 *   - Building a rectangular selection polygon by manually adding four corner
 *     points to a new polygon shape.
 *   - Spatial query via TGIS_LayerVector.FindFirst / FindNext using the
 *     GIS_RELATE_INTERSECT DE-9IM predicate to find all features that share
 *     at least one point with the selection shape.
 *   - Visually highlighting matched features with setIsSelected and listing
 *     their "name" attribute in a JTextArea.
 */
package selectbyshape;

import java.awt.Toolkit;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import tatukgis.jdk.TGIS_BrushStyle;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_Lock;
import tatukgis.jdk.TGIS_PenStyle;
import tatukgis.jdk.TGIS_Point;
import tatukgis.jdk.TGIS_RendererAbstract;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_ShapeType;
import tatukgis.jdk.TGIS_Topology;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.jdk.TGIS_ViewerMode;
import tatukgis.rtl.TPoint;
import tatukgis.rtl.TRect;

/**
 * Main application frame for the SelectByShape sample.
 * Hosts the TatukGIS viewer panel, two mutually-exclusive toggle buttons
 * (circle / rectangle), and a scrollable text area that lists the names of
 * the features found to intersect the drawn selection shape.
 */
public class SelectByShapeUI extends javax.swing.JFrame {

    /**
     * Constructs the form, wires all Swing components through the
     * NetBeans-generated {@link #initComponents()}, then calls
     * {@link #initOwn()} to load geospatial data and configure the viewer.
     */
    public SelectByShapeUI() {
        initComponents();
        initOwn();
    }

    /**
     * Initialises the map viewer after the Swing component tree is built.
     * Opens the base Counties shapefile and adds two empty in-memory vector
     * layers that will hold visual indicators during selection:
     * <ul>
     *   <li><b>"Points"</b>  — invisible marker shapes (size&nbsp;0) used only
     *       as geometry containers for the MakeBuffer radius calculation.</li>
     *   <li><b>"Buffers"</b> — the rendered selection polygon (circle or
     *       rectangle) drawn over the map.</li>
     * </ul>
     * The viewer is locked during setup so that only one repaint fires on
     * {@code Unlock}.  Select mode is activated immediately so the mouse
     * handlers receive control from the start.
     */
    private void initOwn(){
        GIS.Lock();
        // Put the viewer into Select mode immediately so the custom mouse handlers
        // receive events; the default mode after construction is typically Pan/Zoom
        GIS.setMode(TGIS_ViewerMode.Select);
        // Open the base shapefile; Counties.shp supplies the features to select from
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/USA/States/California/Counties.shp");

        // "Points" layer — invisible markers (size 0) that act as geometry placeholders
        ll = new TGIS_LayerVector();
        ll.getParams().getArea().setColor(TGIS_Color.getBlue());
        ll.setTransparency(50);
        ll.setName("Points");
        // Inherit the coordinate system from the viewer so all geometry is in the
        // same spatial reference and ScreenToMap conversions are correct
        ll.setCS(GIS.getCS());
        GIS.Add(ll);

        // "Buffers" layer — holds the selection shape drawn by the user
        ll = new TGIS_LayerVector();
        ll.getParams().getArea().setColor(TGIS_Color.getBlue());
        ll.getParams().getArea().setOutlineColor(TGIS_Color.getBlue());
        ll.setTransparency(60);   // Semi-transparent so the map beneath shows through
        ll.setName("Buffers");
        ll.setCS(GIS.getCS());
        GIS.Add(ll);
        GIS.Unlock();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        btnCircle = new javax.swing.JToggleButton();
        btnRect = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SelectByShape - TatukGIS DK sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(800, 600));

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        GIS.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                GISMouseDragged(evt);
            }
        });
        GIS.addPaintListener(new tatukgis.jdk.PaintListener() {
            public void PaintExtra(tatukgis.jdk.RenderEvent evt) {
                GISPaintExtra(evt);
            }
            public void BeforePaint(tatukgis.jdk.PaintEvent evt) {
            }
            public void BeforePaintRenderer(tatukgis.jdk.RenderEvent evt) {
            }
            public void AfterPaint(tatukgis.jdk.PaintEvent evt) {
            }
            public void AfterPaintRenderer(tatukgis.jdk.RenderEvent evt) {
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

        btnCircle.setText("By circle");
        btnCircle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCircleActionPerformed(evt);
            }
        });

        btnRect.setText("By rectangle");
        btnRect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCircle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRect)
                        .addGap(0, 424, Short.MAX_VALUE))
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCircle)
                    .addComponent(btnRect))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Records the anchor point when the user presses the mouse button.
     * Prompts the user if no selection mode is active yet.
     * Right-click switches to the viewer's built-in Zoom mode so the user
     * can pan or zoom without permanently leaving the custom Select mode.
     *
     * @param evt AWT mouse event containing the click coordinates.
     */
    private void GISMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GISMousePressed
        if(GIS.getIsEmpty()) return;

        // Guard: require the user to have chosen a mode before starting a drag
        if(!(btnCircle.isSelected() || btnRect.isSelected())){
            JOptionPane.showMessageDialog(this,
                "Choose selection method first");
            return;
        }

        if(SwingUtilities.isRightMouseButton(evt)){
            // Right-click: hand control back to the viewer's built-in zoom/pan behaviour
            GIS.setMode(TGIS_ViewerMode.Zoom);
            return;
        }
        // Initialise both anchor and drag-end to the same pixel so the shape starts
        // with zero size (prevents stale values from a previous selection)
        oldpos = new TPoint(evt.getX(), evt.getY());
        oldpos2 = new TPoint(evt.getX(), evt.getY());
        oldRadius = 0 ;
    }//GEN-LAST:event_GISMousePressed

    /**
     * Finalises the selection when the mouse button is released.
     *
     * <p>Steps:
     * <ol>
     *   <li>Guard against empty map, no mode selected, zero-size drag, or right-click.</li>
     *   <li>Record invisible point markers in the "Points" layer as geometry
     *       containers for the buffer / corner calculations.</li>
     *   <li>Clear the "Buffers" layer and add the new selection polygon:
     *       <ul>
     *         <li><b>Circle</b> — {@code TGIS_Topology.MakeBuffer} approximates a circle
     *             as a 32-vertex polygon; the radius is derived by converting
     *             {@code oldRadius} screen pixels into map units via {@code ScreenToMap}.</li>
     *         <li><b>Rectangle</b> — four corner points are assembled in map coordinates.</li>
     *       </ul>
     *   </li>
     *   <li>Iterate the base Counties layer ({@code getItems().getItems(0)}) with
     *       {@code FindFirst}/{@code FindNext} using {@code GIS_RELATE_INTERSECT}
     *       (a DE-9IM predicate) which returns true when two geometries share at
     *       least one point (boundary or interior).</li>
     *   <li>Mark each matched feature as selected and append its name to the text area.</li>
     * </ol>
     *
     * @param evt AWT mouse event containing the release coordinates.
     */
    private void GISMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GISMouseReleased
        TGIS_Topology tpl;
        TGIS_LayerVector ll;
        TGIS_Shape tmp;
        TGIS_Shape buf;
        TGIS_Point ptg  = new TGIS_Point();   // Centre (circle) or first corner (rectangle) in map coords
        TGIS_Point ptg1 = new TGIS_Point();   // Edge point used to compute the circle radius in map units
        TGIS_Point ptg2 = new TGIS_Point();   // Opposite corner for rectangle in map coords
        double distance;
        String info;

        if(GIS.getIsEmpty()) return;

        // Guard: require the user to have chosen a mode
        if(!(btnCircle.isSelected() || btnRect.isSelected())){
            JOptionPane.showMessageDialog(this,
                "Choose selection method first");
            return;
        }

        if(SwingUtilities.isRightMouseButton(evt)){
            // Right-click: restore Select mode (reversed the right-click zoom set in MousePressed)
            GIS.setMode(TGIS_ViewerMode.Select);
            return;
        }
        // Guard: if the drag produced no movement, ignore (avoids zero-size shapes)
        if(btnRect.isSelected())
            if((oldpos2.X == oldpos.X) && (oldpos2.Y == oldpos.Y))
                return;
        if(btnCircle.isSelected())
            if(oldRadius == 0)
                return;

        // --- Add invisible marker(s) to the "Points" layer ---
        // Marker size 0 hides them from the renderer; they exist only to
        // supply geometry for MakeBuffer (circle) or as placeholders (rectangle).
        ll = (TGIS_LayerVector)GIS.Get("Points");
        ll.Lock();

        tmp = ll.CreateShape(TGIS_ShapeType.Point);

        if(btnCircle.isSelected()){
            // Convert the anchor screen pixel to a map coordinate (centre of circle)
            ptg = GIS.ScreenToMap(oldpos);
            tmp = ll.CreateShape(TGIS_ShapeType.Point);
            tmp.getParams().getMarker().setSize(0);
            // Lock with Extent so the layer's spatial extent is updated automatically
            tmp.Lock(TGIS_Lock.Extent);
            tmp.AddPart();
            tmp.AddPoint(ptg);
            tmp.Unlock();
            ll.Unlock();
            // Convert a point that is exactly oldRadius pixels to the right of centre;
            // the map-unit X difference between ptg1 and ptg is the circle radius
            ptg1 = GIS.ScreenToMap(new TPoint(oldpos.X + (int)oldRadius, evt.getY()));
        }
        if(btnRect.isSelected()){
            // Record the first corner as an invisible marker
            ptg = GIS.ScreenToMap(oldpos);
            tmp = ll.CreateShape(TGIS_ShapeType.Point);
            tmp.getParams().getMarker().setSize(0);
            tmp.Lock(TGIS_Lock.Extent);
            tmp.AddPart();
            tmp.AddPoint(ptg);
            tmp.Unlock();
            // Record the second corner (drag endpoint) as an invisible marker
            ptg = GIS.ScreenToMap(oldpos2);
            tmp.AddPoint(ptg);
            ll.Unlock();
            // Re-convert the first corner for use during rectangle construction below
            ptg1 = GIS.ScreenToMap(oldpos);
        }
        buf = ll.CreateShape(TGIS_ShapeType.Unknown);

        // --- Rebuild the "Buffers" layer with the new selection shape ---
        ll = (TGIS_LayerVector)GIS.Get("Buffers");
        // RevertShapes clears all shapes added since the last commit, effectively
        // resetting the layer to empty so previous selections are discarded
        ll.RevertShapes();

        if(btnCircle.isSelected()){
            // The map-unit diameter spans ptg1.X - ptg.X (right edge minus centre).
            // Dividing by 2 converts diameter to radius as expected by MakeBuffer.
            distance = ptg1.X - ptg.X;
            tpl = new TGIS_Topology();
            // MakeBuffer approximates a circle as a 32-vertex polygon around tmp.
            // The last parameter (true) closes the ring automatically.
            buf = tpl.MakeBuffer(tmp, distance / 2, 32, true);
            // AddShape transfers ownership of the geometry to the Buffers layer
            buf = ll.AddShape(buf);
        }

        if(btnRect.isSelected()){
            // Build a closed rectangle polygon from the two diagonally opposite corners
            ptg2 = GIS.ScreenToMap(oldpos2);
            buf = ll.CreateShape(TGIS_ShapeType.Polygon);
            buf.AddPart();
            // Wind the four corners in order (top-left, top-right, bottom-right, bottom-left)
            buf.AddPoint(ptg1);
            buf.AddPoint(TGIS_Utils.GisPoint(ptg1.X, ptg2.Y));
            buf.AddPoint(ptg2);
            buf.AddPoint(TGIS_Utils.GisPoint(ptg2.X, ptg1.Y));
        }

        // --- Perform the spatial query on the base Counties layer ---
        ll = (TGIS_LayerVector)GIS.getItems().getItems(0);

        if(ll == null){
            GIS.InvalidateWholeMap();
            return;
        }

        // Clear any previously selected features before the new selection pass
        ll.DeselectAll();

        info = "";

        GIS.InvalidateWholeMap();
        GIS.Lock();
        // FindFirst / FindNext iterate over shapes whose bounding box overlaps buf.getExtent(),
        // then applies GIS_RELATE_INTERSECT (a DE-9IM test) to confirm the geometries
        // actually share at least one point — not just overlapping bounding boxes.
        tmp = ll.FindFirst(buf.getExtent(), "", buf, TGIS_Utils.GIS_RELATE_INTERSECT());
        while(tmp != null){
            // This feature intersects the selection polygon: highlight it and record its name
            info += tmp.GetField("name").toString() + "\n";
            tmp.setIsSelected(true);
            tmp = ll.FindNext();
        }
        jTextArea1.setText(info);
        GIS.Unlock();
    }//GEN-LAST:event_GISMouseReleased

    /**
     * Called by the viewer's paint pipeline after all map layers have been drawn.
     * Renders the rubber-band selection shape directly on the renderer canvas in
     * screen space using the abstract renderer API.
     *
     * <p>Using the {@code PaintListener.PaintExtra} callback (rather than an AWT
     * overlay panel) ensures the feedback is composited correctly on top of the
     * map without flickering and works regardless of hardware acceleration.
     *
     * <p>The pen colour is randomised each call to create a flickering "marching
     * ants" visual effect on the outline during a drag operation.
     *
     * @param evt Render event providing the {@link TGIS_RendererAbstract} instance.
     */
    private void GISPaintExtra(tatukgis.jdk.RenderEvent evt) {//GEN-FIRST:event_GISPaintExtra
        TGIS_RendererAbstract rdr;
        Random rand;
        TRect rect;
        rand = new Random();
        rdr = evt.getRenderer();
        rdr.getCanvasPen().Width = 1;
        // Random colour creates the animated "marching ants" outline effect
        rdr.getCanvasPen().Color = TGIS_Color.FromBGR(rand.nextInt(16581375));
        rdr.getCanvasPen().Style = TGIS_PenStyle.Solid;
        // Clear brush so only the outline is drawn (no fill obscuring the map)
        rdr.getCanvasBrush().Style = TGIS_BrushStyle.Clear;

        if(btnRect.isSelected()){
            // Do not draw until the user has actually moved the cursor (zero-size guard)
            if((oldpos.X == oldpos2.X) && (oldpos.Y == oldpos2.Y)) return;
            // TRect uses Top/Bottom/Left/Right; note the vertical axis: Bottom = anchor Y
            rect = new TRect();
            rect.Bottom = oldpos.Y;
            rect.Top = oldpos2.Y;
            rect.Left = oldpos.X;
            rect.Right = oldpos2.X;
            rdr.CanvasDrawRectangle(rect);
        }

        if(btnCircle.isSelected()){
            // CanvasDrawEllipse takes top-left corner X/Y, width, height in screen pixels.
            // This variant halves oldRadius for top-left offset because oldRadius here
            // represents the full pixel span (diameter) captured during the drag.
            rdr.CanvasDrawEllipse(oldpos.X - (int)Math.round(oldRadius / 2), oldpos.Y - (int)Math.round(oldRadius / 2), (int)oldRadius, (int)oldRadius);
        }
    }//GEN-LAST:event_GISPaintExtra

    /**
     * Updates the rubber-band dimensions while the user drags the mouse.
     * Java's AWT distinguishes dragging (button held) from moving (no button)
     * through {@code MouseMotionAdapter.mouseDragged} rather than a shift-state
     * flag, so no explicit button check is needed here.
     *
     * <p>For rectangle mode the second corner follows the cursor; for circle mode
     * the radius is the Euclidean pixel distance from the anchor to the cursor.
     * Calling {@code GIS.invalidate()} triggers {@code PaintExtra} on the next
     * repaint so the rubber-band is redrawn every frame during the drag.
     *
     * @param evt AWT mouse event containing the current cursor position.
     */
    private void GISMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GISMouseDragged
        if(GIS.getIsEmpty()) return;

        if(GIS.getMode() != TGIS_ViewerMode.Select) return;
        if(!SwingUtilities.isLeftMouseButton(evt)) return;

        if(btnRect.isSelected())
            // Track the second corner of the bounding rectangle
            oldpos2 = new TPoint(evt.getX(), evt.getY());
        if(btnCircle.isSelected())
            // Pythagorean distance from the anchor to the current cursor position
            oldRadius = (int)Math.round(Math.sqrt(Math.pow(oldpos.X - evt.getX(), 2) + Math.pow(oldpos.Y - evt.getY(), 2)));
        // Trigger a repaint so PaintExtra draws the updated rubber-band shape
        GIS.invalidate();
    }//GEN-LAST:event_GISMouseDragged

    /**
     * Keeps the two mode buttons mutually exclusive: selecting circle
     * automatically deselects rectangle.
     *
     * @param evt Action event from the button.
     */
    private void btnCircleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCircleActionPerformed
        if(btnCircle.isSelected())
            btnRect.setSelected(!btnCircle.isSelected()) ;
    }//GEN-LAST:event_btnCircleActionPerformed

    /**
     * Keeps the two mode buttons mutually exclusive: selecting rectangle
     * automatically deselects circle.
     *
     * @param evt Action event from the button.
     */
    private void btnRectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRectActionPerformed
        if(btnRect.isSelected())
            btnCircle.setSelected(!btnRect.isSelected()) ;
    }//GEN-LAST:event_btnRectActionPerformed

    /**
     * Application entry point.
     * Applies the Windows look-and-feel (if available) then shows the form on
     * the AWT Event Dispatch Thread to comply with Swing's thread-safety rules.
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
            java.util.logging.Logger.getLogger(SelectByShapeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SelectByShapeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SelectByShapeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SelectByShapeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        // Show the form on the Event Dispatch Thread as required by Swing
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SelectByShapeUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;  // Main map viewer panel
    private javax.swing.JToggleButton btnCircle;
    private javax.swing.JToggleButton btnRect;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;  // Displays names of selected features
    // End of variables declaration//GEN-END:variables

    // Screen-space anchor point recorded when the mouse button is pressed
    private double oldRadius;
    private int oldZoom;
    private TGIS_Color oldColor;
    // Persistent reference to the most recently used in-memory layer
    private TGIS_LayerVector ll;
    // Anchor point where the user first pressed the mouse button (screen pixels)
    private TPoint oldpos;
    // Current drag-end point updated on every mouseDragged event
    private TPoint oldpos2;
}
