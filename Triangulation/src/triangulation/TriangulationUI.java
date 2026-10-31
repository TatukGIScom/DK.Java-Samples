/*
 * =============================================================================
 * This source code is a part of TatukGIS Developer Kernel.
 * =============================================================================
 *
 * Triangulation Sample – Demonstrates Delaunay triangulation and Voronoi diagrams.
 *
 * This sample opens a point shapefile (Polish cities) and uses two specialised
 * TGIS_LayerVector subclasses from the TatukGIS JDK to compute geometric structures
 * derived from the point distribution:
 *
 *   Delaunay Triangulation
 *     Connects input points into triangles such that no point lies inside the
 *     circumscribed circle of any triangle.  Produces a TIN (Triangulated
 *     Irregular Network) suitable for surface modelling and proximity analysis.
 *
 *   Voronoi Diagram
 *     Partitions the plane into one region per input point, where each region
 *     contains all locations closer to that point than to any other input point.
 *     The Voronoi diagram is the geometric dual of the Delaunay triangulation.
 *
 * After generation the result layer is colour-graduated by polygon area
 * (GIS_AREA attribute) over a white-to-red (Voronoi) or white-to-blue (Delaunay)
 * gradient rendered across 10 equal-interval zones.  This highlights relative
 * cell / triangle area variation across the study region.
 *
 * User interaction:
 *   - Click a point on the map to display its attributes in the Attributes panel
 *   - Radio buttons select Delaunay vs Voronoi mode before generation
 *   - "Generate" adds the result layer (duplicate layer names are not guarded here)
 *   - Toolbar buttons provide Full Extent, Zoom In, and Zoom Out navigation
 *
 * Key TatukGIS JDK classes used:
 *   TGIS_LayerDelaunay       – generates and stores a Delaunay triangulation
 *   TGIS_LayerVoronoi        – generates and stores a Voronoi diagram
 *   TGIS_LayerVector         – base class; ImportLayer() copies source point data
 *   TGIS_ControlAttributes   – side panel that shows shape attribute data on click
 *   TGIS_ControlLegend       – layer legend / style control panel
 *   TGIS_ViewerWnd           – Swing-embedded map viewer component
 * =============================================================================
 */
package triangulation;

import java.awt.Toolkit;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_LayerDelaunay;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_LayerVoronoi;
import tatukgis.jdk.TGIS_MarkerStyle;
import tatukgis.jdk.TGIS_Point;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_ShapeType;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.rtl.TPoint;

/**
 * Main frame for the Triangulation sample application.
 *
 * <p>Demonstrates Delaunay triangulation and Voronoi diagram generation using
 * {@link TGIS_LayerDelaunay} and {@link TGIS_LayerVoronoi}.  The result layer
 * is colour-graduated by polygon area (GIS_AREA attribute) to give an immediate
 * visual impression of size variation across the plane.</p>
 *
 * <p>The UI presents a toolbar for map navigation, radio buttons to choose the
 * generation mode, a text field for the result layer name, and side panels for
 * layer attributes and the legend.</p>
 */
public class TriangulationUI extends javax.swing.JFrame {

    /**
     * Constructs the frame, initialises designer components, then loads map data.
     */
    public TriangulationUI() {
        initComponents();
        initOwn();
    }

    /**
     * Custom initialisation: opens the Polish city point shapefile and
     * configures the city marker style.
     *
     * <p>A second "selected" param set is registered so that shapes shown in the
     * Attributes panel receive a distinct blue area fill, making the selected
     * shape easy to identify on the map.</p>
     */
    private void initOwn(){
        TGIS_LayerVector ll;

        // Open the Polish city point data – this layer provides the seed
        // point set for both the Delaunay and Voronoi algorithms.
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/Poland/DCW/city.shp");

        // Retrieve the first (and only) vector layer just loaded
        ll = (TGIS_LayerVector)GIS.getItems().getItems(0);

        // Customise the default marker style for city points
        ll.getParams().getMarker().setColor(TGIS_Color.getGray());
        ll.getParams().getMarker().setOutlineWidth(2);
        ll.getParams().getMarker().setStyle(TGIS_MarkerStyle.Circle);

        // Add a second named param set; shapes displayed in the attribute panel
        // use the "selected" style so they stand out on the map.
        ll.getParamsList().Add();
        ll.getParams().setStyle("selected");
        ll.getParams().getArea().setOutlineWidth(1);
        ll.getParams().getArea().setColor(TGIS_Color.getBlue());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        rdbtnVoronoi = new javax.swing.JRadioButton();
        rdbtnDelaunay = new javax.swing.JRadioButton();
        btnGenerate = new javax.swing.JButton();
        txtLayerName = new javax.swing.JTextField();
        GIS_ControlAttributes1 = new tatukgis.jdk.TGIS_ControlAttributes();
        jToolBar1 = new javax.swing.JToolBar();
        btnFullExtent = new javax.swing.JButton();
        btnZoomIn = new javax.swing.JButton();
        btnZoomOut = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tGIS_ControlLegend1 = new tatukgis.jdk.TGIS_ControlLegend();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Triangulation - TatukGIS DK sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(800, 600));

        GIS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                GISMousePressed(evt);
            }
        });

        rdbtnVoronoi.setText("Voronoi");
        rdbtnVoronoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbtnVoronoiActionPerformed(evt);
            }
        });

        rdbtnDelaunay.setText("Delaunay");
        rdbtnDelaunay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbtnDelaunayActionPerformed(evt);
            }
        });

        btnGenerate.setText("Generate");
        btnGenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateActionPerformed(evt);
            }
        });

        txtLayerName.setText("RESULT");

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

        btnZoomIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/ZoomIn.png"))); // NOI18N
        btnZoomIn.setFocusable(false);
        btnZoomIn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnZoomIn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnZoomIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZoomInActionPerformed(evt);
            }
        });
        jToolBar1.add(btnZoomIn);

        btnZoomOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/ZoomOut.png"))); // NOI18N
        btnZoomOut.setFocusable(false);
        btnZoomOut.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnZoomOut.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnZoomOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZoomOutActionPerformed(evt);
            }
        });
        jToolBar1.add(btnZoomOut);

        tGIS_ControlLegend1.setGIS_Viewer(GIS);
        jScrollPane1.setViewportView(tGIS_ControlLegend1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtLayerName, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(rdbtnVoronoi)
                            .addComponent(rdbtnDelaunay)
                            .addComponent(GIS_ControlAttributes1, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnGenerate)
                        .addGap(50, 50, 50))))
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(GIS_ControlAttributes1, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdbtnVoronoi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdbtnDelaunay)
                        .addGap(18, 18, 18)
                        .addComponent(txtLayerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGenerate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Selects Delaunay mode: deselects Voronoi radio button and pre-fills the
     * output layer name with "Delaunay".
     */
    private void rdbtnDelaunayActionPerformed(java.awt.event.ActionEvent evt) {
        rdbtnVoronoi.setSelected(!rdbtnDelaunay.isSelected());
        txtLayerName.setText("Delaunay");
    }

    /**
     * Selects Voronoi mode: deselects Delaunay radio button and pre-fills the
     * output layer name with "Voronoi".
     */
    private void rdbtnVoronoiActionPerformed(java.awt.event.ActionEvent evt) {
        rdbtnDelaunay.setSelected(!rdbtnVoronoi.isSelected());
        txtLayerName.setText("Voronoi");
    }

    /**
     * Zooms the viewer to fit all loaded layers within the visible area.
     * Guarded by an IsEmpty check to avoid calling FullExtent on an empty viewer.
     */
    private void btnFullExtentActionPerformed(java.awt.event.ActionEvent evt) {
        if(GIS.getIsEmpty()) return;
        GIS.FullExtent();
    }

    /**
     * Doubles the current zoom factor, zooming into the centre of the map.
     * Guarded by an IsEmpty check to avoid touching an empty viewer.
     */
    private void btnZoomInActionPerformed(java.awt.event.ActionEvent evt) {
        if(GIS.getIsEmpty()) return;
        GIS.setZoom(GIS.getZoom()*2);
    }

    /**
     * Halves the current zoom factor, zooming out from the centre of the map.
     * Guarded by an IsEmpty check to avoid touching an empty viewer.
     */
    private void btnZoomOutActionPerformed(java.awt.event.ActionEvent evt) {
        if(GIS.getIsEmpty()) return;
        GIS.setZoom(GIS.getZoom()/2);
    }

    /**
     * Hit-tests the map on mouse press to find the nearest shape and displays
     * its attribute data in the GIS_ControlAttributes1 side panel.
     *
     * <p>ScreenToMap converts pixel coordinates to geographic (map) coordinates.
     * Locate searches all layers for the nearest shape within a tolerance of
     * 5 screen pixels (converted to map units by dividing by the zoom factor).</p>
     *
     * @param evt the AWT mouse event carrying the click position in screen pixels
     */
    private void GISMousePressed(java.awt.event.MouseEvent evt) {
        TGIS_Shape shp;
        TGIS_Point ptg;

        if(GIS.getIsEmpty()) return;

        // Convert screen pixel position to geographic map coordinates
        ptg = GIS.ScreenToMap(new TPoint(evt.getX(), evt.getY()));
        // Locate the nearest shape within a 5-pixel screen tolerance
        shp = (TGIS_Shape)GIS.Locate(ptg, 5/GIS.getZoom());

        if(shp != null)
            GIS_ControlAttributes1.ShowShape(shp);  // Populate the attribute panel
    }

    /**
     * Creates either a {@link TGIS_LayerVoronoi} or {@link TGIS_LayerDelaunay}
     * layer, imports the city point data as input, configures a graduated colour
     * render style, and adds the result to the viewer.
     *
     * <p>ImportLayer copies all features from the source layer into the new
     * triangulation layer and runs the algorithm immediately; after the call the
     * layer contains fully formed polygon shapes ready for display.</p>
     *
     * <p>The GIS_AREA attribute (computed automatically by the triangulation engine)
     * drives a white-to-colour gradient over 10 equal-interval zones.
     * Voronoi uses red as the end colour; Delaunay uses blue.</p>
     *
     * @param evt the action event (not used)
     */
    private void btnGenerateActionPerformed(java.awt.event.ActionEvent evt) {
        TGIS_LayerVector lv;

        // Instantiate the correct layer type based on the radio button selection
        if(rdbtnDelaunay.isSelected())
            lv = new TGIS_LayerDelaunay();   // Delaunay triangulation
        else
            lv = new TGIS_LayerVoronoi();    // Voronoi diagram

        lv.setName(txtLayerName.getText());

        // ImportLayer reads all point features from the source layer (index 0) and uses
        // them as the input seed set for the triangulation / diagram computation.
        // TGIS_ShapeType.Unknown lets the engine infer the output shape type automatically.
        lv.ImportLayer((TGIS_LayerVector)GIS.getItems().getItems(0),
                        GIS.getExtent(), TGIS_ShapeType.Unknown, "", false);
        lv.setTransparency(60);  // Semi-transparent so the source city layer shows through

        // Configure graduated colour rendering keyed on the built-in GIS_AREA attribute.
        // GIS_AREA holds the area of each output polygon in map coordinate units squared.
        lv.getParams().getRender().setExpression("GIS_AREA");
        lv.getParams().getRender().setMinVal(10000000);    // ~10 km² lower bound
        lv.getParams().getRender().setMaxVal(1300000000);  // ~1300 km² upper bound
        lv.getParams().getRender().setStartColor(TGIS_Color.getWhite());
        // Differentiate Voronoi (red) from Delaunay (blue) visually
        if(rdbtnVoronoi.isSelected())
            lv.getParams().getRender().setEndColor(TGIS_Color.getRed());
        else
            lv.getParams().getRender().setEndColor(TGIS_Color.getBlue());
        lv.getParams().getRender().setZones(10);  // Divide the colour range into 10 equal steps
        // getRenderColor() instructs the renderer to substitute the computed gradient
        // colour for the polygon fill during painting.
        lv.getParams().getArea().setColor(TGIS_Color.getRenderColor());

        // Inherit the coordinate system from the source layer so all layers align
        lv.setCS(GIS.getCS());
        GIS.Add(lv);
        GIS.InvalidateWholeMap();  // Trigger a full repaint
    }

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
            java.util.logging.Logger.getLogger(TriangulationUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TriangulationUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TriangulationUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TriangulationUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form on the Event Dispatch Thread */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TriangulationUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private tatukgis.jdk.TGIS_ControlAttributes GIS_ControlAttributes1;
    private javax.swing.JButton btnFullExtent;
    private javax.swing.JButton btnGenerate;
    private javax.swing.JButton btnZoomIn;
    private javax.swing.JButton btnZoomOut;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JRadioButton rdbtnDelaunay;
    private javax.swing.JRadioButton rdbtnVoronoi;
    private tatukgis.jdk.TGIS_ControlLegend tGIS_ControlLegend1;
    private javax.swing.JTextField txtLayerName;
    // End of variables declaration//GEN-END:variables
}
