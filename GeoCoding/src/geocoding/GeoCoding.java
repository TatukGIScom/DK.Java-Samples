/*
 * =============================================================================
 * This source code is a part of TatukGIS Developer Kernel.
 * =============================================================================
 *
 * GeoCoding Sample - Java (TatukGIS JDK)
 * =======================================
 * Demonstrates address geocoding and shortest-path routing using TatukGIS DK.
 *
 * Geocoding translates a human-readable address string (e.g. "Chrysler 1345")
 * into geographic coordinates by matching it against street-range attribute
 * fields stored in a vector layer.  The TGIS_Geocoding class performs this
 * matching entirely on the client side using the loaded shapefile — no
 * external service is needed.
 *
 * Routing finds the shortest path between two geocoded points using
 * TGIS_ShortestPath, which builds an in-memory Dijkstra graph from the road
 * network.  The resulting path segments are visualised by copying them into a
 * separate in-memory "RouteDisplay" layer rendered in red, with green/red
 * point markers for the start and end positions.
 *
 * Key concepts shown:
 *   - Opening a TatukGIS project (.ttkproject) and retrieving a named layer
 *   - Constructing TGIS_Geocoding with TIGER/Line address-range field mappings
 *   - Using TGIS_Geocoding.Parse to resolve typed addresses to coordinates
 *   - Building a TGIS_ShortestPath graph with per-link-type cost modifiers
 *   - Providing a TGIS_LinkTypeEvent lambda to classify road segments by MTFCC code
 *   - Drawing route results and address markers into a TGIS_LayerVector overlay
 *   - Displaying a TGIS_ControlScale bar linked to the viewer
 *
 * Data used:
 *   California.ttkproject — a TatukGIS project referencing US Census TIGER/Line
 *   street data.  Relevant attribute fields:
 *     FULLNAME  — full street name
 *     LFROMADD  — address range start, left side of street
 *     LTOADD    — address range end, left side of street
 *     RFROMADD  — address range start, right side of street
 *     RTOADD    — address range end, right side of street
 *     MTFCC     — MAF/TIGER Feature Class Code; "S1400"+ = local/residential road
 * =============================================================================
 */
package geocoding;

import java.awt.Toolkit;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_Geocoding;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_LinkTypeEvent;
import tatukgis.jdk.TGIS_LinkTypeEventArgs;
import tatukgis.jdk.TGIS_Lock;
import tatukgis.jdk.TGIS_Point;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_ShapeType;
import tatukgis.jdk.TGIS_ShortestPath;
import tatukgis.jdk.TGIS_Utils;

/**
 * Main application window for the GeoCoding &amp; Routing sample.
 * Hosts a {@code TGIS_ViewerWnd} Swing component displaying California street
 * data and provides address resolution (geocoding) and shortest-path routing.
 */
public class GeoCoding extends javax.swing.JFrame {

    /**
     * Creates the form, initialises designer components, then opens the
     * California street project and configures the geocoding and routing engines.
     */
    public GeoCoding() {
        initComponents();
        initOwn();
    }

    /**
     * Performs application-specific initialisation after the Swing designer
     * components have been created.
     *
     * <p>Opens the California TatukGIS project and retrieves the "streets" layer.
     * Creates an in-memory route-display layer, configures the geocoding engine
     * with TIGER/Line field mappings, and builds the routing graph.
     *
     * <p>The viewer is locked during setup to suppress intermediate redraws —
     * the standard DK pattern for multi-step viewer modifications.
     */
    private void initOwn(){
        GIS.Lock();

        // Open the TatukGIS project which contains the California street data.
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/Samples/Projects/California.ttkproject");

        // Retrieve the streets vector layer by name so the geocoding and
        // routing engines can operate on its shape and attribute data.
        layerSrc = (TGIS_LayerVector) (GIS.Get("streets"));

        // ------------------------------------------------------------------
        // Create an in-memory overlay layer for route visualisation.
        // setUseConfig(false) prevents the DK from persisting a style config.
        // Red line (Width = -2, negative = device pixels) and 1-px marker outline.
        // The layer's CS must match the viewer's CS.
        // ------------------------------------------------------------------
        layerRoute = new TGIS_LayerVector();
        layerRoute.setUseConfig(false);
        layerRoute.getParams().getLine().setColor(TGIS_Color.getRed());
        layerRoute.getParams().getLine().setWidth(-2);
        layerRoute.getParams().getMarker().setOutlineWidth(1);
        layerRoute.setName("RouteDisplay");
        layerRoute.setCS(GIS.getCS());
        GIS.Add(layerRoute);

        // ------------------------------------------------------------------
        // Configure the geocoding engine with TIGER/Line field mappings.
        //   setOffset    — distance along the segment to place the geocoded point.
        //   setRoadName  — attribute field with the full street name.
        //   setLFrom/LTo — address number range, left side of the street.
        //   setRFrom/RTo — address number range, right side of the street.
        //                  TIGER/Line uses these four fields to interpolate the
        //                  house number position along the segment.
        // ------------------------------------------------------------------
        geoObj = new TGIS_Geocoding(layerSrc);
        geoObj.setOffset(0.0001);
        geoObj.setRoadName("FULLNAME");
        geoObj.setLFrom("LFROMADD");
        geoObj.setLTo("LTOADD");
        geoObj.setRFrom("RFROMADD");
        geoObj.setRTo("RTOADD");

        // ------------------------------------------------------------------
        // Configure the routing engine.
        // An anonymous TGIS_LinkTypeEvent is used to classify each road segment:
        //   MTFCC != "S1400" → local/residential road (link type 1)
        //   MTFCC == "S1400" → highway (link type 0)
        // LoadTheData builds the Dijkstra graph from all arcs in layerSrc.
        // setRoadName labels each route step in the directions area.
        // ------------------------------------------------------------------
        rtrObj = new TGIS_ShortestPath(GIS);
        rtrObj.setLinkTypeEvent(new TGIS_LinkTypeEvent() {
            @Override
            public void Invoke(Object o, TGIS_LinkTypeEventArgs tgslt) {
                // Classify based on the US Census MTFCC feature class code.
                // Any code != "S1400" is treated as local (type 1); "S1400"
                // exact match is treated as highway (type 0).
                if((tgslt.getShape().GetField("MTFCC").toString().compareTo("S1400")) != 0) {
                    tgslt.setLinkType(1);
                } else {
                    tgslt.setLinkType(0);
                }
            }
        });

        rtrObj.LoadTheData(layerSrc);
        rtrObj.setRoadName("FULLNAME");

        // Display the scale bar in miles (EPSG 9035 = statute mile).
        GIS_ControlScale.setUnits(TGIS_Utils.getCSUnitsList().ByEPSG(9035));

        GIS.Unlock();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GIS = new tatukgis.jdk.TGIS_ViewerWnd();      // TatukGIS map viewer Swing component
        GIS_ControlScale = new tatukgis.jdk.TGIS_ControlScale(); // Scale-bar overlay linked to GIS
        jPanel1 = new javax.swing.JPanel();            // Right-hand controls panel
        sldrLocal = new javax.swing.JSlider();         // Local-road preference slider (1–10)
        prefLocalLabel = new javax.swing.JLabel();     // Label for sldrLocal
        PrefHighwayLabel = new javax.swing.JLabel();   // Label for sldrHighway
        sldrHighway = new javax.swing.JSlider();       // Highway preference slider (1–10)
        fromLabel = new javax.swing.JLabel();          // Label for tfAddrFrom
        tfAddrFrom = new javax.swing.JTextField();     // Start address input
        btnResolve = new javax.swing.JButton();        // Resolve start address on map
        toLabel = new javax.swing.JLabel();            // Label for tfAddrTo
        tfAddrTo = new javax.swing.JTextField();       // End address input
        btnRoute = new javax.swing.JButton();          // Find shortest route
        jScrollPane1 = new javax.swing.JScrollPane();  // Scrollable container for taRoute
        taRoute = new javax.swing.JEditorPane();       // Read-only turn-by-turn directions

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GeoCoding - TatukGIS DK sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(800, 600));

        GIS_ControlScale.setGIS_Viewer(GIS);
        GIS.add(GIS_ControlScale);
        GIS_ControlScale.setBounds(20, 20, 185, 40);

        sldrLocal.setMaximum(10);
        sldrLocal.setMinimum(1);
        sldrLocal.setToolTipText("");
        sldrLocal.setValue(1);

        prefLocalLabel.setText("Prefer local roads");

        PrefHighwayLabel.setText("Prefer highway");

        sldrHighway.setMaximum(10);
        sldrHighway.setMinimum(1);
        sldrHighway.setValue(5);

        fromLabel.setText("From");

        tfAddrFrom.setText("Chrys 1345");

        btnResolve.setText("Find address");
        btnResolve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResolveActionPerformed(evt);
            }
        });

        toLabel.setText("To");

        tfAddrTo.setText("wash");

        btnRoute.setText("Find route");
        btnRoute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRouteActionPerformed(evt);
            }
        });

        taRoute.setEditable(false);
        jScrollPane1.setViewportView(taRoute);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnRoute)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(btnResolve)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(toLabel)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(fromLabel)
                                        .addComponent(sldrHighway, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(PrefHighwayLabel)
                                        .addComponent(prefLocalLabel)
                                        .addComponent(sldrLocal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(tfAddrFrom)))
                                .addComponent(tfAddrTo, javax.swing.GroupLayout.Alignment.LEADING)))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(prefLocalLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sldrLocal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PrefHighwayLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sldrHighway, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fromLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfAddrFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnResolve)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(toLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfAddrTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRoute)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Handles the Find Route button click.
     * Geocodes both addresses, computes the shortest path between them,
     * and displays the route as a red line with turn-by-turn directions.
     *
     * <p>Cost modifiers derived from the highway/local-road sliders are applied
     * before routing so the user can tune road-type preference at runtime.
     * The formula maps slider values 1–10 to cost multipliers 1.0–~0.09
     * (lower multiplier = cheaper = preferred by the router).
     *
     * <p>The {@code Compass} property of each route item encodes turn direction:
     * 0 = straight ahead, 1..3 = right (slight/right/sharp), 4 = U-turn,
     * -1..-3 = left (slight/left/sharp), -4 = left U-turn.
     * Consecutive steps on the same named street are suppressed.
     */
    private void btnRouteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRouteActionPerformed
        int i;
        TGIS_Shape shp;
        int res;
        TGIS_Point pt_a;
        TGIS_Point pt_b;
        String ang = "";
        String oldnam = "";

        // Apply slider-derived cost modifiers.
        // setCostModifiers(0, x) sets the highway cost multiplier.
        // setCostModifiers(1, x) sets the local-road cost multiplier.
        rtrObj.setCostModifiers(0, 1.0 - 1.0 / 11.0 * sldrHighway.getValue());
        rtrObj.setCostModifiers(1, 1.0 - 1.0 / 11.0 * sldrLocal.getValue());

        // Geocode the start address.
        // Parse() returns the number of matching candidates found.
        // If found, refine the text field to the best canonical match.
        res = geoObj.Parse(tfAddrFrom.getText());
        if (res > 0) {
            tfAddrFrom.setText(geoObj.getQuery(0));
        } else {
            tfAddrFrom.setText(tfAddrFrom.getText() + " ???");
        }

        // Abort if the start address could not be resolved.
        if (res <= 0) return;
        pt_a = geoObj.getPoint(0);   // Geocoded start coordinate

        // Geocode the end address.
        res = geoObj.Parse(tfAddrTo.getText());
        if (res > 0) {
            tfAddrTo.setText(geoObj.getQuery(0));
        } else {
            tfAddrTo.setText(tfAddrTo.getText() + " ???");
        }

        // Abort if the end address could not be resolved.
        if (res <= 0) return;
        pt_b = geoObj.getPoint(0);   // Geocoded end coordinate

        // UpdateTheData refreshes the routing graph (e.g. after data edits).
        // Find() computes the shortest path; Unproject converts viewer display
        // coordinates to the layer's native coordinate system.
        rtrObj.UpdateTheData();
        rtrObj.Find(layerRoute.Unproject(pt_a), layerRoute.Unproject(pt_b));

        taRoute.setText("");
        oldnam = "#$@3eqewe";   // Sentinel to detect the first direction step

        // Build turn-by-turn directions text.
        for (i = 0; i < rtrObj.getItemsCount(); i++)
        {
            // Map the numeric Compass value to a readable direction abbreviation.
            switch (rtrObj.getItems(i).getCompass())
            {
                case 0:  ang = "FWD  "; break;
                case 1:  ang = "RIGHT"; break;   // Slight right
                case 2:  ang = "RIGHT"; break;   // Right
                case 3:  ang = "RIGHT"; break;   // Sharp right
                case 4:  ang = "BACK "; break;   // U-turn
                case -1: ang = "LEFT "; break;   // Slight left
                case -2: ang = "LEFT "; break;   // Left
                case -3: ang = "LEFT "; break;   // Sharp left
                case -4: ang = "BACK "; break;   // Left U-turn
            }

            // Suppress consecutive items on the same named street.
            if (oldnam.compareTo(rtrObj.getItems(i).getName()) == 0 ) continue;
            oldnam = rtrObj.getItems(i).getName();

            taRoute.setText(taRoute.getText() + ang + " " + rtrObj.getItems(i).getName() + "\r\n");
        }

        // Clear the route display layer and repopulate with the new path.
        layerRoute.RevertShapes();

        // Copy each route segment from the source layer into the display layer.
        for (i = 0; i < rtrObj.getItemsCount(); i++)
        {
            shp = rtrObj.getItems(i).getLayer().GetShape(rtrObj.getItems(i).getUid());
            if (shp == null) continue;
            layerRoute.AddShape(shp);
            if (i == 0) {
                layerRoute.setExtent(shp.getExtent());  // Seed the bounding extent
            }
        }

        // Place a green point marker at the start position (pt_a).
        // Lock/Unlock brackets shape modification to maintain internal extent.
        shp = layerRoute.CreateShape(TGIS_ShapeType.Point);
        shp.Lock(TGIS_Lock.Extent);
        shp.AddPart();
        shp.AddPoint(pt_a);
        shp.getParams().getMarker().setColor(TGIS_Color.getGreen());
        shp.Unlock();

        // Place a red (default colour) point marker at the end position (pt_b).
        shp = layerRoute.CreateShape(TGIS_ShapeType.Point);
        shp.Lock(TGIS_Lock.Extent);
        shp.AddPart();
        shp.AddPoint(pt_b);
        shp.Unlock();

        // Zoom to the route bounding box, pulling back slightly (0.7×) for context.
        GIS.Lock();
        GIS.setVisibleExtent(layerRoute.getExtent());
        GIS.setZoom(0.7 * GIS.getZoom());
        GIS.Unlock();
    }//GEN-LAST:event_btnRouteActionPerformed

    /**
     * Handles the Find Address button click.
     * Geocodes the address typed in {@code tfAddrFrom} and highlights all
     * matching street segments on the map with a green point marker at each
     * geocoded coordinate.
     *
     * <p>{@code Parse()} returns the number of matching candidates found in the
     * street data.  Each candidate provides a {@code getQuery()} (canonical name),
     * a {@code getUid()} (shape ID of the primary segment), and a {@code getUidEx()}
     * (shape ID of the complementary opposite-side segment).
     * A green marker is placed at {@code getPoint(i)}, the interpolated coordinate
     * for the house number along the matched segment.
     */
    private void btnResolveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResolveActionPerformed
        int i;
        int r;
        TGIS_Shape shp;

        if (geoObj == null) return;

        // Clear any previously displayed route or address highlights.
        layerRoute.RevertShapes();

        // Parse returns the number of matches; r is the last valid array index.
        // Append "???" if no match was found.
        r = geoObj.Parse(tfAddrFrom.getText()) - 1;
        if (r <= 0) {
            tfAddrFrom.setText(tfAddrFrom.getText() + " ???");
        }

        for (i = 0; i <= r; i++)
        {
            // Refine the text field to the canonical name of each matched street.
            tfAddrFrom.setText(geoObj.getQuery(i));

            // Add the primary matching segment to the route display layer.
            shp = layerSrc.GetShape(geoObj.getUid(i));
            layerRoute.AddShape(shp);

            // Seed the bounding extent from the first matched segment.
            if (i == 0) {
                layerRoute.setExtent(shp.getProjectedExtent());
            }

            // Add the complementary (opposite-side) segment if available.
            // getUidEx returns the ID of the adjacent segment for the same address range.
            shp = layerSrc.GetShape(geoObj.getUidEx(i));
            if (shp != null) {
                layerRoute.AddShape(shp);
            }

            // Place a green marker at the interpolated geocoded coordinate.
            // getCS().FromCS() converts from the source layer's CS to the route layer's CS.
            shp = layerRoute.CreateShape(TGIS_ShapeType.Point);
            shp.Lock(TGIS_Lock.Extent);
            shp.AddPart();
            shp.AddPoint(layerRoute.getCS().FromCS(layerSrc.getCS(), geoObj.getPoint(i)));
            shp.getParams().getMarker().setColor(TGIS_Color.getGreen());
            shp.Unlock();
        }

        // Zoom to the matched results with a slight zoom-out factor for context.
        GIS.Lock();
        GIS.setVisibleExtent(layerRoute.getProjectedExtent());
        GIS.setZoom(0.7 * GIS.getZoom());
        GIS.Unlock();

    }//GEN-LAST:event_btnResolveActionPerformed

    /**
     * Application entry point.
     * Applies the Windows Look and Feel (if available) and shows the main window
     * on the Swing Event Dispatch Thread.
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
            java.util.logging.Logger.getLogger(GeoCoding.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GeoCoding.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GeoCoding.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GeoCoding.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form on the Swing Event Dispatch Thread */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GeoCoding().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;           // TatukGIS Swing map viewer
    private tatukgis.jdk.TGIS_ControlScale GIS_ControlScale; // Scale-bar overlay linked to GIS
    private javax.swing.JLabel PrefHighwayLabel;        // "Prefer highway" label
    private javax.swing.JButton btnResolve;             // Resolve start address button
    private javax.swing.JButton btnRoute;               // Find route button
    private javax.swing.JLabel fromLabel;               // "From" label
    private javax.swing.JPanel jPanel1;                 // Right-hand controls panel
    private javax.swing.JScrollPane jScrollPane1;       // Scrollable wrapper for taRoute
    private javax.swing.JLabel prefLocalLabel;          // "Prefer local roads" label
    private javax.swing.JSlider sldrHighway;            // Highway preference slider
    private javax.swing.JSlider sldrLocal;              // Local-road preference slider
    private javax.swing.JEditorPane taRoute;            // Read-only turn-by-turn directions
    private javax.swing.JTextField tfAddrFrom;          // Start address text field
    private javax.swing.JTextField tfAddrTo;            // End address text field
    private javax.swing.JLabel toLabel;                 // "To" label
    // End of variables declaration//GEN-END:variables

    private tatukgis.jdk.TGIS_LayerVector layerSrc;    // Source streets layer from the project
    private tatukgis.jdk.TGIS_LayerVector layerRoute;  // In-memory layer for route visualisation
    private tatukgis.jdk.TGIS_Geocoding geoObj;        // Geocoding engine (address → coordinates)
    private tatukgis.jdk.TGIS_ShortestPath rtrObj;     // Routing engine (Dijkstra shortest path)
}
