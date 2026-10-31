package pixeledit;

/**
 * PixelEditUI - main window for the PixelEdit sample application (Java / JDK edition).
 *
 * <p>Demonstrates how to read and modify raster (pixel) layer data programmatically
 * using TatukGIS.  Five operations are shown, each triggered by a dedicated button:</p>
 *
 * <ol>
 *   <li><b>Terrain profile</b> ({@code btnProfile})<br>
 *       Iterates over every pixel that lies under a polyline shape, reading the
 *       grid elevation value ({@code px.Value}) and the cumulative distance along
 *       the line ({@code px.Distance}).  Results are shown in the text area.
 *       <br>API: {@code TGIS_LayerPixel.Loop(distanceStep, shape, editable)}</li>
 *
 *   <li><b>Grid min/max</b> ({@code btnMinMax})<br>
 *       Finds the pixels with the lowest and highest elevation values inside a
 *       polygon area.  A temporary vector layer with cross markers is added at
 *       the two extreme locations so they are visible in the viewer.
 *       <br>API: {@code TGIS_LayerPixel.Loop(extent, step, shape, mode, editable)}</li>
 *
 *   <li><b>Bitmap average colour</b> ({@code btnAvarageColor})<br>
 *       Reads every pixel of a satellite image that falls inside a country polygon
 *       (Spain, shape #679) to compute the average R, G, B colour, then writes that
 *       flat colour back into every pixel of the same region.
 *       <br>API: {@code TGIS_PixelItem.Color}</li>
 *
 *   <li><b>Create new JPG</b> ({@code btnNewJPG})<br>
 *       Creates a brand-new JPEG raster layer spanning the entire world in WGS-84
 *       (EPSG:4326), then uses LockPixels/UnlockPixels to paint a square region red
 *       by writing directly into the raw bitmap buffer.
 *       <br>API: {@code TGIS_LayerJPG.Build()}, {@code LockPixels()},
 *       {@code TGIS_LayerPixelLock.getBitmap()[]}, {@code BitmapPos()}</li>
 *
 *   <li><b>Create new GRD</b> ({@code btnNewGRD})<br>
 *       Creates a brand-new ESRI Grid raster layer, then fills a region with random
 *       elevation values using the low-level {@code Grid[][]} accessor.
 *       <br>API: {@code TGIS_LayerGRD.Build()}, {@code TGIS_LayerPixelLock.getGrid()[][]}</li>
 * </ol>
 *
 * <p>Key TatukGIS API concepts shown here:</p>
 * <ul>
 *   <li>{@code TGIS_LayerPixel}  - base class for all raster layers; source of
 *       pixel iteration and lock operations</li>
 *   <li>{@code TGIS_LayerJPG}    - JPEG raster layer; supports Build() and SaveData()</li>
 *   <li>{@code TGIS_LayerGRD}    - ESRI Grid raster layer; continuous float values</li>
 *   <li>{@code TGIS_PixelItem}   - a single raster cell during Loop() iteration;
 *       exposes {@code Value} (elevation/float), {@code Color} (TGIS_Color),
 *       {@code getCenter()} (geographic point), {@code getDistance()} (profile offset)</li>
 *   <li>{@code Loop()}           - enumerator-based iteration over raster cells;
 *       the last boolean parameter controls whether values can be written back</li>
 *   <li>{@code LockPixels()}     - locks a rectangular region for direct raw-buffer
 *       access; must be paired with {@code UnlockPixels()}</li>
 *   <li>{@code TGIS_LayerPixelLock} - holds the locked pixel buffer and its bounds</li>
 *   <li>{@code TGIS_CSFactory.ByEPSG()} - creates a coordinate system from an EPSG code</li>
 *   <li>{@code GIS.Lock()/Unlock()} - suspends/resumes map repainting during batch updates</li>
 * </ul>
 */

import RemObjects.Elements.System.VarParameter;
import java.awt.Toolkit;
import java.util.Random;
import tatukgis.jdk.TGIS_CSFactory;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_LayerGRD;
import tatukgis.jdk.TGIS_LayerJPG;
import tatukgis.jdk.TGIS_LayerPixel;
import tatukgis.jdk.TGIS_LayerPixelLock;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_MarkerStyle;
import tatukgis.jdk.TGIS_PixelItem;
import tatukgis.jdk.TGIS_Point;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_ShapeType;
import tatukgis.jdk.TGIS_Utils;

public class PixelEditUI extends javax.swing.JFrame {

    /**
     * Constructs the frame, initialises all Swing components, and sets up the
     * GIS viewer and legend.  The legend control is not yet wired up (see comment).
     */
    public PixelEditUI() {
        //still missing legend control
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        jScrollPane1 = new javax.swing.JScrollPane();
        taInfo = new javax.swing.JTextArea();
        jToolBar1 = new javax.swing.JToolBar();
        btnProfile = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnMinMax = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnAvarageColor = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnNewJPG = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btnNewGRD = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        jScrollPane2 = new javax.swing.JScrollPane();
        tGIS_ControlLegend1 = new tatukgis.jdk.TGIS_ControlLegend();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PixelEdit - TatukGIS DK sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(800, 600));

        taInfo.setColumns(20);
        taInfo.setRows(5);
        jScrollPane1.setViewportView(taInfo);

        jToolBar1.setRollover(true);

        btnProfile.setText("Terrain profile");
        btnProfile.setFocusable(false);
        btnProfile.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProfile.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProfileActionPerformed(evt);
            }
        });
        jToolBar1.add(btnProfile);
        jToolBar1.add(jSeparator3);

        btnMinMax.setText("Grid Min/Max");
        btnMinMax.setFocusable(false);
        btnMinMax.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMinMax.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMinMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMinMaxActionPerformed(evt);
            }
        });
        jToolBar1.add(btnMinMax);
        jToolBar1.add(jSeparator2);

        btnAvarageColor.setText("Bitmap average color");
        btnAvarageColor.setFocusable(false);
        btnAvarageColor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAvarageColor.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAvarageColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAvarageColorActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAvarageColor);
        jToolBar1.add(jSeparator1);

        btnNewJPG.setText("Create new JPG");
        btnNewJPG.setFocusable(false);
        btnNewJPG.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNewJPG.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNewJPG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewJPGActionPerformed(evt);
            }
        });
        jToolBar1.add(btnNewJPG);
        jToolBar1.add(jSeparator4);

        btnNewGRD.setText("Create new GRD");
        btnNewGRD.setFocusable(false);
        btnNewGRD.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNewGRD.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNewGRD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewGRDActionPerformed(evt);
            }
        });
        jToolBar1.add(btnNewGRD);
        jToolBar1.add(jSeparator5);

        tGIS_ControlLegend1.setGIS_Viewer(GIS);
        jScrollPane2.setViewportView(tGIS_ControlLegend1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Reads a terrain elevation profile along a polyline shape.
     * <p>
     * Opens the grid project, retrieves the elevation layer and the polyline
     * from the "line" layer, then calls {@code Loop(step, shape, editable)} to
     * iterate over every raster cell that the line crosses.
     * {@code px.getDistance()} provides the cumulative distance along the line;
     * {@code px.Value} is the elevation at that point.
     * GIS.Lock()/Unlock() suppress intermediate redraws for performance.
     * </p>
     */
    private void btnProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfileActionPerformed
        TGIS_LayerPixel lp;
        TGIS_LayerVector lv;
        TGIS_Shape shp;
        TGIS_PixelItem px;
        String info;

        info = "";
        taInfo.setText(info);

        GIS.Lock();  // Suppress repaints during project load
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/Samples/PixelEdit/grid.ttkproject");

        // Retrieve the elevation grid and the profile-line vector layer by name
        lp = (TGIS_LayerPixel) GIS.Get("elevation");
        lv = (TGIS_LayerVector) GIS.Get("line");
        shp = lv.GetShape(1).MakeEditable();
        shp.setIsSelected(true);  // Highlight the line in the viewer

        // Loop(step=0, shape, editable=false): step=0 visits every raster cell the line crosses.
        // Casting to TGIS_PixelItem is required in Java because Loop() returns Object.
        for(Object tmp : lp.Loop(0, shp, false)){
            px = (TGIS_PixelItem)tmp;
            info = info + "Distance: " + px.getDistance() + " Height: " + px.Value + "\n" ;
        }
        taInfo.setText(info);
        GIS.Unlock();  // Resume repainting
    }//GEN-LAST:event_btnProfileActionPerformed

    /**
     * Finds the minimum and maximum elevation pixels inside a polygon and marks
     * those locations on the map with cross symbols.
     * <p>
     * The "T" parameter in {@code Loop()} causes iteration to be clipped to the
     * exact polygon boundary rather than its bounding box.  After the scan, a
     * temporary in-memory vector layer is created in the same CRS as the raster
     * and point shapes are added at the extreme locations.
     * </p>
     */
    private void btnMinMaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMinMaxActionPerformed
        TGIS_LayerPixel lp;
        TGIS_LayerVector lv;
        TGIS_LayerVector lvtmp;
        TGIS_Shape shp;
        TGIS_Shape shptmp;
        TGIS_PixelItem px;
        double dmin, dmax;
        TGIS_Point ptmax, ptmin;
        String info;

        info = "";
        taInfo.setText(info);

        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/Samples/PixelEdit/grid.ttkproject");

        lp = (TGIS_LayerPixel)GIS.Get("elevation");
        lv = (TGIS_LayerVector)GIS.Get("polygon");
        shp = lv.GetShape(1).MakeEditable();
        shp.setIsSelected(true);  // Highlight the polygon to show the search area

        // Initialise sentinels so the first real pixel value always wins
        dmax = -1e38;
        dmin = 1e38;

        ptmin = new TGIS_Point();
        ptmax = new TGIS_Point();

        // Loop over pixels clipped to the polygon shape; "T" = use polygon as mask
        for(Object tmp : lp.Loop(shp.getExtent(), 0, shp, "T", false)){
            px = (TGIS_PixelItem)tmp;
            if(px.Value < dmin){
                dmin = px.Value;
                ptmin = px.getCenter();  // Geographic centre of this raster cell
            }
            if(px.Value > dmax){
                dmax = px.Value;
                ptmax = px.getCenter();
            }
        }

        // Create a temporary in-memory vector layer in the raster's CRS for markers
        lvtmp = new TGIS_LayerVector();
        lvtmp.setCS(lp.getCS());
        GIS.Add(lvtmp);

        // Style the temporary layer with cross markers sized in screen pixels
        lvtmp.getParams().getMarker().setStyle(TGIS_MarkerStyle.Cross);
        lvtmp.getParams().getMarker().setSize(-10);  // Negative = screen pixels, not map units
        lvtmp.getParams().getMarker().setColor(TGIS_Color.getBlack());

        // Add a point at the lowest elevation location
        shptmp = lvtmp.CreateShape(TGIS_ShapeType.Point);
        shptmp.AddPart();
        shptmp.AddPoint(ptmin);

        // Add a point at the highest elevation location
        shptmp = lvtmp.CreateShape(TGIS_ShapeType.Point);
        shptmp.AddPart();
        shptmp.AddPoint(ptmax);

        GIS.InvalidateWholeMap();  // Force repaint to show the new markers

        info = info + "Min: " + dmin + " Location: " + ptmin.X + " , " + ptmin.Y + "\n";
        info = info + "Max: " + dmax + " Location: " + ptmax.X + " , " + ptmax.Y + "\n";
        taInfo.setText(info);
    }//GEN-LAST:event_btnMinMaxActionPerformed

    /**
     * Computes the average pixel colour inside Spain and paints the whole region
     * with that average colour, demonstrating read-then-write pixel iteration.
     * <p>
     * Two passes over the pixels of the Blue Marble satellite image are made:
     * </p>
     * <ol>
     *   <li>Read pass (editable=false): accumulate R, G, B values for all pixels
     *   inside the Spain polygon (shape #679).</li>
     *   <li>Write pass (editable=true): replace every pixel inside Spain with the
     *   computed average colour.  Assigning {@code px.Color = cl} writes the value
     *   back into the raster immediately.</li>
     * </ol>
     * <p>
     * GIS.Lock()/Unlock() are used around the open-and-zoom sequence to prevent
     * partial screen updates while the view is being repositioned.
     * </p>
     */
    private void btnAvarageColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvarageColorActionPerformed
        TGIS_LayerPixel lp;
        TGIS_LayerVector lv;
        TGIS_Shape shp;
        TGIS_PixelItem px;
        double r,g,b;
        int cnt;
        TGIS_Color cl;
        String info;

        info = "";
        taInfo.setText(info);

        GIS.Lock();  // Suppress repaints during load and viewport change
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/Samples/PixelEdit/bitmap.ttkproject");

        // Retrieve the raster and the country polygon layers
        lp = (TGIS_LayerPixel) GIS.Get("bluemarble");
        lv = (TGIS_LayerVector) GIS.Get("countries");

        // Shape 679 is Spain; MakeEditable() returns a writable copy
        shp = lv.GetShape(679).MakeEditable();

        // Pan and zoom to Spain
        GIS.setVisibleExtent(shp.getProjectedExtent());
        GIS.setZoom(GIS.getZoom() / 2);  // Zoom out slightly to show surrounding area

        cnt = 0;
        r = 0;
        g = 0;
        b = 0;

        // First pass: accumulate channel sums for all pixels inside Spain
        // "T" = clip iteration to the polygon boundary
        for(Object tmp : lp.Loop(shp.getExtent(), 0, shp, "T", false)){
            px = (TGIS_PixelItem) tmp;
            r += px.Color.getR();  // Red channel (0-255)
            g += px.Color.getG();  // Green channel (0-255)
            b += px.Color.getB();  // Blue channel (0-255)
            cnt++;
        }

        if( cnt > 0 ){
            // Compute the per-channel average and build the replacement colour
            cl = TGIS_Color.FromRGB((byte)(r/cnt),
                    (byte)(g/cnt),
                    (byte)(b/cnt)
            );
            // Second pass: overwrite every pixel inside Spain with the average colour.
            // editable=true enables px.Color assignment to flush changes to the layer.
            for(Object tmp : lp.Loop(shp.getExtent(), 0, shp, "T", true)){
                px = (TGIS_PixelItem)tmp;
                px.Color = cl;
            }
        }

        GIS.InvalidateWholeMap();  // Repaint to show the modified satellite imagery
        GIS.Unlock();
    }//GEN-LAST:event_btnAvarageColorActionPerformed

    /**
     * Creates a new JPEG raster layer from scratch and paints a rectangular
     * region red using the low-level {@code LockPixels} interface.
     * <p>
     * {@code Build()} defines the file name, coordinate system (EPSG:4326 = WGS-84),
     * geographic extent (whole world), and pixel dimensions (360x180).
     * {@code LockPixels()} returns a {@code TGIS_LayerPixelLock} whose
     * {@code getBitmap()[]} is a flat ARGB integer array; {@code BitmapPos(x,y)}
     * converts 2-D pixel coordinates to the correct flat array index.
     * {@code UnlockPixels()} flushes the writes back into the layer.
     * {@code SaveData()} then writes the modified data to the JPEG file on disk.
     * </p>
     */
    private void btnNewJPGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewJPGActionPerformed
        TGIS_LayerJPG lp;
        TGIS_LayerPixelLock lock ;
        int x,y;

        taInfo.setText("");

        lp = new TGIS_LayerJPG();

        // Build() creates the JPEG file with the given georeferencing and dimensions
        lp.Build("test.jpg", TGIS_CSFactory.ByEPSG(4326), TGIS_Utils.GisExtent(-180, -90, 180, 90), 360, 180);

        // Lock a 90x90-degree sub-region for write access
        lock = lp.LockPixels(TGIS_Utils.GisExtent(-45, -45, 45, 45), lp.getCS(), true);

        try{
            // Iterate every pixel in the locked bounds and write solid red.
            // BitmapPos(x,y) maps 2-D coordinates to the flat array index.
            // TGIS_Color.getRed().ARGB is the 32-bit ARGB value for opaque red.
            for(x = lock.getBounds().Left; x < lock.getBounds().Right; x++){
                for(y = lock.getBounds().Top; y < lock.getBounds().Bottom; y++){
                    lock.getBitmap()[lock.BitmapPos(x, y)] = TGIS_Color.getRed().ARGB;
                }
            }
        }finally{
            // VarParameter wraps the lock for the by-ref UnlockPixels() call.
            // Always unlock in a finally block to avoid buffer leaks.
            lp.UnlockPixels(new VarParameter<TGIS_LayerPixelLock>(lock));
        }

        lp.SaveData();  // Persist changes to the JPEG file on disk

        GIS.Open( "test.jpg" );  // Display the newly created image
    }//GEN-LAST:event_btnNewJPGActionPerformed

    /**
     * Creates a new ESRI Grid raster layer from scratch and fills a region with
     * random elevation values using the {@code Grid[][]} accessor.
     * <p>
     * The ESRI Grid format stores continuous floating-point values (e.g. elevations)
     * rather than RGB colour.  The locked region's data is accessed via
     * {@code getGrid()[row][col]} (row-major order: first index is y, second is x).
     * </p>
     */
    private void btnNewGRDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewGRDActionPerformed
        TGIS_LayerGRD lp;
        TGIS_LayerPixelLock lock;
        int x,y;
        Random rnd;

        taInfo.setText("");
        rnd = new Random();

        lp = new TGIS_LayerGRD();

        // Build the GRD file with the same world-extent georeferencing as the JPG above
        lp.Build("test.grd", TGIS_CSFactory.ByEPSG(4326), TGIS_Utils.GisExtent(-180, -90, 180, 90), 360, 180);

        // Lock the central sub-region for write access
        lock = lp.LockPixels(TGIS_Utils.GisExtent(-45, -45, 45, 45), lp.getCS(), true);

        try{
            for(x = lock.getBounds().Left; x < lock.getBounds().Right; x++){
                for(y = lock.getBounds().Top; y < lock.getBounds().Bottom; y++){
                    // Grid[y][x]: row-major storage; y is the row, x is the column.
                    // nextInt(100) generates a random integer in [0, 100) as the elevation.
                    lock.getGrid()[y][x] = rnd.nextInt(100);
                }
            }
        }finally{
            lp.UnlockPixels(new VarParameter<TGIS_LayerPixelLock>(lock));  // Flush and release
        }

        lp.SaveData();  // Write the grid data to disk

        GIS.Open( "test.grd" );  // Display the newly created grid
    }//GEN-LAST:event_btnNewGRDActionPerformed

    /**
     * Application entry point.
     * Attempts to apply the Windows look-and-feel for visual consistency,
     * then creates and shows the main form on the Event Dispatch Thread.
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
            java.util.logging.Logger.getLogger(PixelEditUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PixelEditUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PixelEditUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PixelEditUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form on the Event Dispatch Thread */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PixelEditUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;              // TatukGIS map viewer component
    private javax.swing.JButton btnAvarageColor;           // Compute and apply average bitmap colour
    private javax.swing.JButton btnMinMax;                 // Find min/max grid elevation in polygon
    private javax.swing.JButton btnNewGRD;                 // Create a new ESRI Grid raster layer
    private javax.swing.JButton btnNewJPG;                 // Create a new JPEG raster layer
    private javax.swing.JButton btnProfile;                // Read elevation profile along a line
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar jToolBar1;
    private tatukgis.jdk.TGIS_ControlLegend tGIS_ControlLegend1;  // Layer legend panel
    private javax.swing.JTextArea taInfo;                  // Output text area for results
    // End of variables declaration//GEN-END:variables
}
