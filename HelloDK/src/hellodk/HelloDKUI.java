// HelloDK - TatukGIS Developer Kernel introductory sample (Java / Swing).
//
// Demonstrates the core DK workflow:
//   1. Opening a vector Shapefile into the map viewer.
//   2. Switching the viewer interaction mode: Zoom / Drag / Select.
//   3. Creating an in-memory editable vector layer with a transparent polygon style.
//   4. Building a polygon shape programmatically by adding explicit vertices.
//   5. Click-to-select a feature using screen-to-map coordinate conversion
//      and spatial proximity search (GIS.Locate).
//   6. Spatial containment query using the DE-9IM matrix string "T*****FF*"
//      combined with a SQL WHERE filter to find world features whose label
//      starts with 's' and that are geometrically contained within the
//      user-created polygon.
//
// Java API notes:
//   - Properties are accessed via getters/setters: getName(), setName(), etc.
//   - TapSimple is wired up via GIS.setOnTapSimple(new TMouseEvent(){...}).
//   - Collection items use GIS.getItems().getItems(index).
//   - Static-style color constants are accessed as TGIS_Color.getBlue().

package hellodk;

import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import tatukgis.jdk.TGIS_BrushStyle;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_Lock;
import tatukgis.jdk.TGIS_Point;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_ShapeType;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.jdk.TGIS_ViewerMode;
import tatukgis.jdk.TMouseEvent;
import tatukgis.rtl.TPoint;

/**
 * HelloDK sample — introductory demonstration of core TatukGIS DK workflows.
 * Opens a world shapefile, creates an in-memory vector layer with a polygon, performs spatial containment
 * queries using DE-9IM topology predicates, and switches between interaction modes (Zoom, Drag, Select).
 * Demonstrates screen-to-map coordinate conversion, feature location via GIS.Locate(), and SQL filtering.
 */
public class HelloDKUI extends javax.swing.JFrame {

    /**
     * Constructs the frame and wires up the TapSimple mouse event handler.
     * The TapSimple handler is registered inline here rather than via a
     * NetBeans-generated method because the Swing GUI builder does not
     * recognise custom DK event types.
     */
    public HelloDKUI() {
        initComponents();

        // Wire up the map click handler using an anonymous TMouseEvent.
        // TapSimple fires on every single (non-double) mouse button press.
        GIS.setOnTapSimple(new TMouseEvent() {
            @Override
            public void Invoke(MouseEvent me) {
                TGIS_Shape shp;
                TGIS_Point ptg;
                TGIS_LayerVector lv;
                double precision;

                // Ignore taps when the viewer is not in Select mode
                if(GIS.getMode() != TGIS_ViewerMode.Select) return ;

                // Convert screen pixel coordinates to geographic map coordinates.
                // ScreenToMap accounts for the current zoom level and pan offset.
                ptg = GIS.ScreenToMap(new TPoint(me.getX(), me.getY()));

                // Get the first (world) layer to manage its selection state.
                // Java uses getItems().getItems(index) to access the layer collection.
                lv = (TGIS_LayerVector)GIS.getItems().getItems(0);

                // Compute the hit-test tolerance: 5 screen pixels expressed in map units.
                // Dividing by getZoom() converts pixels to the map's coordinate unit.
                precision = 5/GIS.getZoom() ;

                // Search all layers for the topmost shape within 'precision' of the click point
                shp = (TGIS_Shape) GIS.Locate(ptg, precision) ;

                // If no shape was found near the click point, do nothing
                if(shp == null) return ;

                // Clear any previously selected shapes before applying the new selection
                lv.DeselectAll();

                // Toggle selection: clicking a selected shape deselects it, and vice versa
                shp.setIsSelected(!shp.getIsSelected()) ;

                // Repaint the map to reflect the updated selection highlight
                GIS.InvalidateWholeMap() ;
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        btnOpen = new javax.swing.JButton();
        btnCreate = new javax.swing.JButton();
        btnFind = new javax.swing.JButton();
        btnZoom = new javax.swing.JButton();
        btnDrag = new javax.swing.JButton();
        btnSelect = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HelloDK - TatukGIS DK sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(900, 600));

        btnOpen.setText("Open project");
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });

        btnCreate.setText("Create shape");
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });

        btnFind.setText("Find shapes");
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });

        btnZoom.setText("Zooming");
        btnZoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZoomActionPerformed(evt);
            }
        });

        btnDrag.setText("Dragging");
        btnDrag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDragActionPerformed(evt);
            }
        });

        btnSelect.setText("Selecting");
        btnSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnOpen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnZoom)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDrag)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSelect)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCreate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFind)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 901, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOpen)
                    .addComponent(btnCreate)
                    .addComponent(btnFind)
                    .addComponent(btnZoom)
                    .addComponent(btnDrag)
                    .addComponent(btnSelect))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * "Open project" button action.
     * Opens the WorldDCW world Shapefile from the DK sample data directory and
     * loads it into the viewer. After opening, the mode is set to Select so
     * the user can immediately click on features.
     */
    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/World/WorldDCW/world.shp");
        GIS.setMode(TGIS_ViewerMode.Select);
    }//GEN-LAST:event_btnOpenActionPerformed

    /**
     * "Create Shape" button action.
     * Creates a new in-memory TGIS_LayerVector named "edit layer", gives it a
     * transparent fill with a blue outline, then adds a single quadrilateral
     * polygon to it. This layer is not backed by a file.
     *
     * Java note: properties use setters (setName, setCS, setOutlineColor, setPattern).
     */
    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
        TGIS_LayerVector ll;
        TGIS_Shape shp;

        // Guard: if the edit layer already exists, do nothing (idempotent)
        ll = (TGIS_LayerVector)GIS.Get( "edit layer" ) ;
        if( ll != null ) return;

        // Create a new in-memory vector layer and register it with the viewer
        ll = new TGIS_LayerVector() ;
        ll.setName( "edit layer" ) ;
        // Inherit the viewer's coordinate system so coordinates are interpreted correctly
        ll.setCS( GIS.getCS() ) ;

        // Style: transparent fill (Clear pattern) with a solid blue outline,
        // so the underlying world layer remains visible through the polygon
        ll.getParams().getArea().setOutlineColor(TGIS_Color.getBlue());
        ll.getParams().getArea().setPattern(TGIS_BrushStyle.Clear);

        // Register the layer with the viewer; it will appear on top of existing layers
        GIS.Add( ll ) ;

        // Create a new Polygon shape inside the layer
        shp = ll.CreateShape( TGIS_ShapeType.Polygon ) ;

        // Lock(Extent) batches vertex additions so the bounding box is recalculated
        // only once when Unlock is called, improving performance for bulk edits
        shp.Lock( TGIS_Lock.Extent ) ;

        // AddPart starts the first ring of the polygon; a shape can have multiple
        // parts (e.g., islands or holes in a multi-polygon)
        shp.AddPart() ;

        // Add the four corner vertices of the polygon (coordinates in the map's CS)
        shp.AddPoint( new TGIS_Point( 10, 10 ) );
        shp.AddPoint( new TGIS_Point( 10, 80 ) );
        shp.AddPoint( new TGIS_Point( 80, 90 ) );
        shp.AddPoint( new TGIS_Point( 90, 10 ) );

        // Unlock finalises the shape geometry: recalculates extents and closes
        // the polygon ring automatically if the first and last points differ
        shp.Unlock() ;

        // Redraw the entire map canvas to show the newly added polygon
        GIS.InvalidateWholeMap();
    }//GEN-LAST:event_btnCreateActionPerformed

    /**
     * "Find Shape" button action.
     * Uses DE-9IM (Dimensionally Extended 9-Intersection Model) spatial
     * relationship to find all world features that are fully contained inside
     * the polygon created by btnCreateActionPerformed.
     *
     * The DE-9IM matrix "T*****FF*" encodes the "contains" relationship:
     *   - 'T' at position [0]: interiors must intersect (non-empty)
     *   - "FF" at positions [6,7]: the query shape's boundary and exterior
     *     must NOT intersect the target shape's interior - i.e. the target
     *     lies entirely within the query polygon.
     *
     * An additional SQL LIKE filter restricts results to features whose
     * 'label' field starts with the letter 's'.
     */
    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        TGIS_LayerVector ll;
        TGIS_LayerVector lv;
        TGIS_Shape selShp, tmpShp;

        // The edit layer must exist (created by btnCreateActionPerformed) to provide
        // the selection polygon; exit early if it has not been created yet
        ll = (TGIS_LayerVector) GIS.Get( "edit layer" );
        if (ll == null) return ;

        // Retrieve the world layer - its name is derived from the filename ('world')
        lv = (TGIS_LayerVector)GIS.Get("world");

        // Clear any previous selection on the world layer before applying the new one
        lv.DeselectAll();

        // Retrieve the first (and only) shape from the edit layer to use as
        // the spatial query boundary
        selShp = ll.GetShape(1) ;  // just the first shape from the layer

        // MakeEditable pins the shape into memory so it survives the subsequent
        // iteration; file-backed shapes are otherwise evicted from cache
        selShp = selShp.MakeEditable();

        // Lock the viewer to batch all selection redraws into a single repaint
        GIS.Lock();

        // Loop over all shapes in the world layer whose bounding box overlaps
        // selShp.getExtent(), whose 'label' field matches the SQL pattern 's%', AND
        // whose DE-9IM relationship with selShp satisfies "T*****FF*" (Contains)
        for( Object tmp : lv.Loop( selShp.getExtent(), "label LIKE 's%'", selShp, "T*****FF*" )){
            tmpShp = (TGIS_Shape)tmp ;
            tmpShp.setIsSelected(true) ;
          }

        // Unlock releases the batched repaint and triggers a single screen refresh
        GIS.Unlock();

        // Force a full map redraw to show the newly selected shapes highlighted
        GIS.InvalidateWholeMap();
    }//GEN-LAST:event_btnFindActionPerformed

    /**
     * "Zooming" button action.
     * Switches the viewer to Zoom mode. In this mode the left mouse button
     * draws a rubber-band rectangle to zoom into a region; the right button
     * zooms out.
     */
    private void btnZoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZoomActionPerformed
        // Do nothing if no layers are loaded
        if(GIS.getIsEmpty()) return;
        GIS.setMode(TGIS_ViewerMode.Zoom);
    }//GEN-LAST:event_btnZoomActionPerformed

    /**
     * "Dragging" button action.
     * Switches the viewer to Drag mode, allowing the user to pan the map
     * by clicking and dragging with the mouse.
     */
    private void btnDragActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDragActionPerformed
        // Do nothing if no layers are loaded
        if(GIS.getIsEmpty()) return;
        GIS.setMode(TGIS_ViewerMode.Drag);
    }//GEN-LAST:event_btnDragActionPerformed

    /**
     * "Selecting" button action.
     * Switches the viewer to Select mode. In this mode, mouse clicks on the map
     * are handled by the TapSimple event to toggle shape selection.
     */
    private void btnSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectActionPerformed
        // Do nothing if no layers are loaded
        if(GIS.getIsEmpty()) return;
        GIS.setMode(TGIS_ViewerMode.Select);
    }//GEN-LAST:event_btnSelectActionPerformed

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
            java.util.logging.Logger.getLogger(HelloDKUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HelloDKUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HelloDKUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HelloDKUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        // Show the frame on the Swing Event Dispatch Thread
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HelloDKUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnDrag;
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnOpen;
    private javax.swing.JButton btnSelect;
    private javax.swing.JButton btnZoom;
    // End of variables declaration//GEN-END:variables
}
