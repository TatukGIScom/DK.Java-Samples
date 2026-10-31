// =============================================================================
// This source code is a part of TatukGIS Developer Kernel.
// =============================================================================
//
// Measure Sample - TatukGIS DK Java (JDK)
//
// Demonstrates how to interactively measure distance (polyline length) and
// area (polygon area) on a map using the built-in shape editor.
//
// Key concepts shown:
//   - Creating an in-memory TGIS_LayerVector to hold the temporary measurement
//     shape, rendered on top of the background basemap.
//   - Using GIS.getEditor() to create and track Arc (polyline) and Polygon
//     shapes as the user clicks to place vertices.
//   - Providing the EditorChangeEvent callback via an anonymous TNotifyEvent
//     class to recompute measurements live after every vertex placed.
//   - Using TGIS_CSUnits.AsLinear / AsAreal to format raw metre /
//     square-metre values into human-readable strings (e.g. "12.3 km").
//   - EPSG 904201 is TatukGIS's internal code for the metric unit set.
//     Assigning EPSG 4326 (WGS-84 geographic) to the drawing layer ensures
//     that LengthCS() / AreaCS() return geodetically correct values.
//   - GIS viewer mode transitions:
//       Select  - waiting for the first click that anchors the shape
//       Edit    - the editor is collecting additional vertices
//       Drag    - idle / panning (default)
// =============================================================================

package Measure;

import java.awt.Toolkit;
import tatukgis.jdk.TGIS_CSUnits;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_EditorSnapType;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_Point;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_ShapeType;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.jdk.TGIS_ViewerMode;
import tatukgis.rtl.TNotifyEvent;
import tatukgis.rtl.TPoint;

/**
 * Main application window for the Measure sample.
 * <p>
 * Hosts the TatukGIS viewer (TGIS_ViewerWnd) and provides buttons to start
 * polyline-distance or polygon-area measurement directly on the map.  The
 * results are updated live as the user places each vertex.
 */
public class MeasureUI extends javax.swing.JFrame {

    /**
     * Constructs the frame: builds the UI (Swing designer code) then runs
     * the custom initialisation that sets up the map and event handlers.
     */
    public MeasureUI() {
        initComponents();
        initOwn();
    }

    /**
     * Custom initialisation that runs after the Swing designer code.
     * <p>
     * Loads the background basemap, creates an in-memory measurement layer,
     * sets up the unit formatter, and wires up the EditorChangeEvent callback
     * using an anonymous inner class (Java's equivalent of a delegate/lambda).
     */
    private void initOwn(){
        // Lock suspends rendering while multiple map changes are made,
        // preventing partial redraws during initialisation.
        GIS.Lock();

        // Load a world outline shapefile as the background basemap.
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "\\World\\WorldDCW\\world.shp");

        // Create an in-memory vector layer to hold the measurement shape.
        // No file is written; the shape exists only in memory.
        ll = new TGIS_LayerVector();
        ll.getParams().getLine().setColor(TGIS_Color.getRed()); // Red for high visibility
        ll.getParams().getLine().setWidth(25);                   // Thick line for easy reading

        // Assign WGS-84 geographic CS (EPSG 4326).
        // With a geographic CS, LengthCS() and AreaCS() perform geodetic
        // calculations that account for the curvature of the Earth.
        ll.SetCSByEPSG( 4326 );

        GIS.Add(ll);

        // Restrict scrolling to the initial world extent.
        GIS.setRestrictedExtent(GIS.getExtent());

        GIS.Unlock();

        // EPSG 904201 selects the TatukGIS standard metric unit formatter
        // (km for linear, km² for areal measurements).
        unit = TGIS_Utils.getCSUnitsList().ByEPSG( 904201 );

        isLine = false;
        isPolygon = false;

        // Widen the rubber-band preview line for better visibility.
        GIS.getEditor().getEditingLinesStyle().setPenWidth(10);

        // Snap to existing line geometry so the user can trace features exactly.
        GIS.getEditor().setSnapType(TGIS_EditorSnapType.Line);

        // Wire up the EditorChangeEvent using an anonymous TNotifyEvent
        // implementation.  This callback fires every time a vertex is placed
        // or the mouse moves while a shape is being edited.
        GIS.setEditorChangeEvent( new TNotifyEvent() {
            @Override
            public void Invoke(Object o) {
                if (GIS.getEditor().getCurrentShape() != null)
                {
                    if (isLine)
                    {
                        // LengthCS() returns the geodetic cumulative length in metres.
                        // AsLinear formats it to a labelled string (e.g. "5.3 km").
                        tfLength.setText(unit.AsLinear(((TGIS_Shape)GIS.getEditor().getCurrentShape()).LengthCS(), true));
                    }
                    else if (isPolygon)
                    {
                        // For a polygon, LengthCS() is the perimeter.
                        tfLength.setText(unit.AsLinear(((TGIS_Shape)GIS.getEditor().getCurrentShape()).LengthCS(), true));
                        // AsAreal formats the geodetic enclosed area with a unit² label.
                        tfArea.setText(unit.AsAreal(((TGIS_Shape)GIS.getEditor().getCurrentShape()).LengthCS(), true, "%s²"));
                    }
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        btnLine = new javax.swing.JButton();
        btnPolygon = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblLength = new javax.swing.JLabel();
        tfLength = new javax.swing.JTextField();
        lblArea = new javax.swing.JLabel();
        tfArea = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SelectByShape - TatukGIS DK sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
            setSize(new java.awt.Dimension(800, 600));

            GIS.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    GISMouseClicked(evt);
                }
            });

            btnLine.setText("By line");
            btnLine.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnLineActionPerformed(evt);
                }
            });

            btnPolygon.setText("By polygon");
            btnPolygon.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnPolygonActionPerformed(evt);
                }
            });

            btnClear.setText("Clear");
            btnClear.setName(""); // NOI18N
            btnClear.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnClearActionPerformed(evt);
                }
            });

            lblLength.setText("Length:");

            lblArea.setText("Area:");

            javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
            jPanel2.setLayout(jPanel2Layout);
            jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(tfLength, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblLength)
                                .addComponent(lblArea))
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addComponent(tfArea))
                    .addContainerGap())
            );
            jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblLength)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(tfLength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(lblArea)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(tfArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(296, Short.MAX_VALUE))
            );

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(btnLine)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnPolygon)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnClear)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnLine)
                        .addComponent(btnPolygon)
                        .addComponent(btnClear))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE))))
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    /**
     * Prepares the viewer for polyline distance measurement.
     * Discards any existing shape, clears result fields, and sets the viewer
     * to Select mode so the next click starts a new Arc (polyline) shape.
     */
    private void btnLineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLineActionPerformed
            // Remove any in-progress measurement shape.
            GIS.getEditor().DeleteShape();
            GIS.getEditor().EndEdit();

            tfArea.setText("");
            tfLength.setText("");

            // Only the length field will be updated; area stays empty.
            isPolygon = false;
            isLine = true;

            // Waiting for the first click that anchors the polyline's first vertex.
            GIS.setMode(TGIS_ViewerMode.Select);
    }//GEN-LAST:event_btnLineActionPerformed

    /**
     * Prepares the viewer for polygon area measurement.
     * Discards any existing shape, clears result fields, and sets the viewer
     * to Select mode so the next click starts a new Polygon shape.
     */
    private void btnPolygonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPolygonActionPerformed
            // Remove any in-progress measurement shape.
            GIS.getEditor().DeleteShape();
            GIS.getEditor().EndEdit();

            tfArea.setText("");
            tfLength.setText("");

            // Both perimeter and area will be displayed in this mode.
            isPolygon = true;
            isLine = false;

            // Waiting for the first click that anchors the polygon's first vertex.
            GIS.setMode(TGIS_ViewerMode.Select);
    }//GEN-LAST:event_btnPolygonActionPerformed

    /**
     * Clears the current measurement shape, resets result fields, and
     * returns the viewer to drag/pan mode.
     */
    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
            GIS.getEditor().DeleteShape();
            GIS.getEditor().EndEdit();

            tfArea.setText("");
            tfLength.setText("");

            // Return to normal pan/zoom interaction.
            GIS.setMode(TGIS_ViewerMode.Drag);
    }//GEN-LAST:event_btnClearActionPerformed

    /**
     * Handles the first mouse click on the map that creates a new measurement
     * shape.  Once created, the viewer mode switches to Edit and the editor
     * manages all subsequent vertex placements internally.
     *
     * @param evt  The AWT mouse event carrying the click coordinates in screen pixels.
     */
    private void GISMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GISMouseClicked
            TGIS_Point ptg;

            // If the editor is already active, additional clicks are handled
            // internally by the editor — do not create a second shape.
            if (GIS.getMode() == TGIS_ViewerMode.Edit)
                return;

            // Convert the screen pixel position to geographic map coordinates.
            ptg = GIS.ScreenToMap(new TPoint(evt.getX(), evt.getY()));

            if (isLine)
            {
                // Arc = polyline; LengthCS() sums all segment lengths geodetically.
                GIS.getEditor().CreateShape(ll, ptg, TGIS_ShapeType.Arc);
            }
            else if (isPolygon)
            {
                // Polygon; both AreaCS() and LengthCS() (perimeter) are available.
                GIS.getEditor().CreateShape(ll, ptg, TGIS_ShapeType.Polygon);
            }

            // Hand vertex control to the editor for all subsequent mouse clicks.
            GIS.setMode(TGIS_ViewerMode.Edit);
    }//GEN-LAST:event_GISMouseClicked

    /**
     * Application entry point.  Sets the Windows Look-and-Feel if available,
     * then opens the main window on the AWT event dispatch thread.
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
            java.util.logging.Logger.getLogger(MeasureUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MeasureUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MeasureUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MeasureUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        // Show the window on the event dispatch thread to comply with Swing's
        // single-threaded UI model.
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MeasureUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS; // The main TatukGIS map viewer component
    private javax.swing.JButton btnClear;     // Clears measurement and returns to drag mode
    private javax.swing.JButton btnLine;      // Activates polyline distance measurement
    private javax.swing.JButton btnPolygon;   // Activates polygon area measurement
    private javax.swing.JPanel jPanel2;       // Right-hand panel holding result labels/fields
    private javax.swing.JLabel lblArea;
    private javax.swing.JLabel lblLength;
    private javax.swing.JTextField tfArea;    // Displays the measured area
    private javax.swing.JTextField tfLength;  // Displays the measured length / perimeter
    // End of variables declaration//GEN-END:variables

    /** In-memory vector layer holding the current measurement shape. */
    private TGIS_LayerVector ll;

    /** Unit formatter (metric by default, EPSG 904201). */
    private TGIS_CSUnits unit;

    private boolean isLine;    // True when measuring polyline distance
    private boolean isPolygon; // True when measuring polygon area
}
