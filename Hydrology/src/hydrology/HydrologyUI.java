package hydrology;

import java.awt.Toolkit;
import tatukgis.jdk.TGIS_BusyEvent;
import tatukgis.jdk.TGIS_BusyEventArgs;
import tatukgis.jdk.TGIS_CSCoordinateSystem;
import tatukgis.jdk.TGIS_ClassificationMethod;
import tatukgis.jdk.TGIS_ClassificationPixel;
import tatukgis.jdk.TGIS_ClassificationVector;
import tatukgis.jdk.TGIS_ColorMap;
import tatukgis.jdk.TGIS_ColorMapMode;
import tatukgis.jdk.TGIS_Extent;
import tatukgis.jdk.TGIS_FieldType;
import tatukgis.jdk.TGIS_GradientMap;
import tatukgis.jdk.TGIS_GridToPolygon;
import tatukgis.jdk.TGIS_Hydrology;
import tatukgis.jdk.TGIS_HydrologyStreamOrderMethod;
import tatukgis.jdk.TGIS_LabelAlignment;
import tatukgis.jdk.TGIS_LayerPixel;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_Lock;
import tatukgis.jdk.TGIS_MarkerStyle;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_ShapeType;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.jdk.TGIS_ViewerMode;
import tatukgis.jdk.__Global;

/**
 * Hydrology sample — TatukGIS DK Java/Swing.
 *
 * <p>Demonstrates the complete DEM-based hydrological analysis pipeline provided
 * by {@link TGIS_Hydrology}, executed step by step via UI buttons.
 *
 * <p>Workflow (10 steps):
 * <ol>
 *   <li>Sink             — detect depressions/flat areas in the raw DEM</li>
 *   <li>Fill             — condition the DEM by raising sinks</li>
 *   <li>FlowDirection    — D8 flow-direction codes (power-of-two, 1–128)</li>
 *   <li>FlowAccumulation — upstream-cell count per cell</li>
 *   <li>AddOutlets       — place two hardcoded pour points on high-acc cells</li>
 *   <li>Watershed        — label cells by the outlet they drain toward</li>
 *   <li>Basin            — partition DEM into auto-detected drainage basins</li>
 *   <li>StreamOrder      — Strahler hierarchical ordering of stream cells</li>
 *   <li>Vectorize        — GridToPolygon (basins) + StreamToPolyline (streams)</li>
 *   <li>3D              — (button present but unavailable in the Java DK)</li>
 * </ol>
 *
 * <p>Data: World/Countries/Poland/DEM/Bytowski_County.tif
 *
 * <p>Each pipeline step runs on a background thread to keep the UI responsive.
 */
public class HydrologyUI extends javax.swing.JFrame {

    final String HYDRO_LAYER_SINK         = "Sinks and flats" ;
    final String HYDRO_LAYER_DEM          = "Hydrologically conditioned DEM" ;
    final String HYDRO_LAYER_DIRECTION    = "Flow direction" ;
    final String HYDRO_LAYER_ACCUMULATION = "Flow accumulation" ;
    final String HYDRO_LAYER_STREAM_ORDER = "Stream order (Strahler)" ;
    final String HYDRO_LAYER_OUTLETS      = "Outlets (pour points)" ;
    final String HYDRO_LAYER_WATERSHED    = "Watersheds" ;
    final String HYDRO_LAYER_BASIN        = "Basins" ;
    final String HYDRO_LAYER_STREAM_VEC   = "Streams (vectorized)" ;
    final String HYDRO_LAYER_BASIN_VEC    = "Basins (vectorized)" ;
    final String HYDRO_FIELD_ORDER        = "ORDER" ;
    final String HYDRO_FIELD_BASIN        = "BASIN_ID" ;
    
    private TGIS_LayerPixel dem;
    private TGIS_Extent ext;
    private TGIS_Hydrology hydrologyToolset;
  
    /** Constructs the frame, lays out all Swing components, then calls {@link #initOwn()}. */
    public HydrologyUI() {
        initComponents();
        initOwn();
    }

    /**
     * Initialises the GIS viewer: loads the Bytowski County DEM, stores a
     * reference and its full extent as the analysis region, disables antialias
     * and hillshade on the raw layer, then creates the shared
     * {@link TGIS_Hydrology} toolset and wires up the BusyEvent progress callback.
     */
    private void initOwn(){
        GIS.setMode(TGIS_ViewerMode.Zoom);
        GIS.setRestrictedDrag(false);
        GIS.Open(
            TGIS_Utils.GisSamplesDataDirDownload() +
            "/World/Countries/Poland/DEM/Bytowski_County.tif"
        );
        
        dem = (TGIS_LayerPixel)(GIS.getItems().getItems(0));
        ext = dem.getExtent();
        dem.getParams().getPixel().setAntialias(false);
        dem.getParams().getPixel().setGridShadow(false);
        GIS.InvalidateWholeMap();
        
        hydrologyToolset = new TGIS_Hydrology();
        hydrologyToolset.setBusyEvent(new TGIS_BusyEvent() {
            @Override
            public void Invoke(Object o, TGIS_BusyEventArgs tgsb) {
                doBusyEvent(o, tgsb);
            }
        });
    };
    
    /**
     * Creates an in-memory grid layer that matches the given DEM's extent,
     * coordinate system, and cell dimensions. Antialias and hillshade are
     * disabled so intermediate results render as plain coloured grids.
     *
     * @param dem  source layer whose spatial properties are copied
     * @param name display name to assign to the new layer
     * @return new {@link TGIS_LayerPixel} ready to receive algorithm output
     */
    private TGIS_LayerPixel createLayerPix(TGIS_LayerPixel dem, String name){
        TGIS_LayerPixel res;
        
        res = new TGIS_LayerPixel();
        res.Build(true, dem.getCS(), dem.getExtent(), dem.getBitWidth(), dem.getBitHeight());
        res.setName(name);
        res.getParams().getPixel().setAntialias(false);
        res.getParams().getPixel().setGridShadow(false);
        
        return res;
    }
    
    /**
     * Creates an empty in-memory vector layer, opens it immediately, and
     * assigns the given coordinate system and default shape type.
     *
     * @param name display name for the layer
     * @param cs   coordinate system (typically copied from the DEM)
     * @param type shape type constant from {@link TGIS_ShapeType}
     * @return new, open {@link TGIS_LayerVector}
     */
    private TGIS_LayerVector createLayerVec(String name, TGIS_CSCoordinateSystem cs, int type){
        TGIS_LayerVector res;
        
        res = new TGIS_LayerVector();
        res.setName(name);
        res.Open();
        res.setCS(cs);
        res.setDefaultShapeType(type);
        
        return res;
    }
    
    /** Returns the named layer cast to {@link TGIS_LayerPixel}. */
    private TGIS_LayerPixel getLayerGrd(String name){
        return (TGIS_LayerPixel) GIS.Get(name);
    }

    /** Returns the named layer cast to {@link TGIS_LayerVector}. */
    private TGIS_LayerVector getLayerVec(String name){
        return (TGIS_LayerVector) GIS.Get(name);
    }

    /**
     * Progress callback used by {@link TGIS_Hydrology} and {@link TGIS_GridToPolygon}.
     * {@code e.getPos() == 0}: initialise the progress bar (range 0–endPos).
     * {@code e.getPos() < 0}: reset bar to zero (operation finished or aborted).
     * {@code e.getPos() > 0}: advance bar to the current position.
     */
    private void doBusyEvent(Object o, TGIS_BusyEventArgs e){
        if (e.getPos() < 0) {
            prgBussy.setValue(0);
        } else {
            if (e.getPos() == 0) {
                prgBussy.setMinimum(0);
                prgBussy.setMaximum((int)e.getEndPos());
                prgBussy.setValue(0);
            }
            else {
                if (e.getPos() > 0) {
                    prgBussy.setValue((int)e.getPos());
                }
            }
        }

    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnSink = new javax.swing.JButton();
        btnFillSink = new javax.swing.JButton();
        btnFlowDirection = new javax.swing.JButton();
        btnFlowAccumulation = new javax.swing.JButton();
        btnAddOutlets = new javax.swing.JButton();
        btnWatershed = new javax.swing.JButton();
        btnBasin = new javax.swing.JButton();
        btnStreamOrderStrahler = new javax.swing.JButton();
        btnVectorize = new javax.swing.JButton();
        btn3D = new javax.swing.JButton();
        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        GIS_Legend = new tatukgis.jdk.TGIS_ControlLegend();
        prgBussy = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hydrology - TatukGIS DK sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(800, 600));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("This sample application is a step-by-step tutorial on how to perform common hydrological analyzes.");

        btnSink.setText("Identify DEM problems");
        btnSink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSinkActionPerformed(evt);
            }
        });

        btnFillSink.setText("Fill sinks");
        btnFillSink.setEnabled(false);
        btnFillSink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFillSinkActionPerformed(evt);
            }
        });

        btnFlowDirection.setText("Flow Direction");
        btnFlowDirection.setEnabled(false);
        btnFlowDirection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFlowDirectionActionPerformed(evt);
            }
        });

        btnFlowAccumulation.setText("Flow Accumulation");
        btnFlowAccumulation.setEnabled(false);
        btnFlowAccumulation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFlowAccumulationActionPerformed(evt);
            }
        });

        btnAddOutlets.setText("Add outlets for Watershed");
        btnAddOutlets.setEnabled(false);
        btnAddOutlets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddOutletsActionPerformed(evt);
            }
        });

        btnWatershed.setText("Watershed");
        btnWatershed.setEnabled(false);
        btnWatershed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWatershedActionPerformed(evt);
            }
        });

        btnBasin.setText("Basin");
        btnBasin.setEnabled(false);
        btnBasin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBasinActionPerformed(evt);
            }
        });

        btnStreamOrderStrahler.setText("Stream Order (Strahler)");
        btnStreamOrderStrahler.setEnabled(false);
        btnStreamOrderStrahler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStreamOrderStrahlerActionPerformed(evt);
            }
        });

        btnVectorize.setText("Convert to vector");
        btnVectorize.setEnabled(false);
        btnVectorize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVectorizeActionPerformed(evt);
            }
        });

        btn3D.setText("View in 3D ( unavailable in Java )");
        btn3D.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSink, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFillSink, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFlowDirection, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFlowAccumulation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAddOutlets, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnWatershed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBasin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnStreamOrderStrahler, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnVectorize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn3D, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSink)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFillSink)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFlowDirection)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFlowAccumulation)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddOutlets)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnWatershed)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBasin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnStreamOrderStrahler)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVectorize)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn3D)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        GIS.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                GISMouseWheelMoved(evt);
            }
        });

        GIS_Legend.setGIS_Viewer(GIS);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1070, Short.MAX_VALUE)
                    .addComponent(prgBussy, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GIS_Legend, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GIS_Legend, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(prgBussy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void GISMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_GISMouseWheelMoved
            if (GIS.getIsEmpty()) {
                return;
            }

            if (evt.getWheelRotation() < 0) {
                GIS.ZoomBy(3 / 4, evt.getX(), evt.getY());
            } else {
                GIS.ZoomBy(4 / 3, evt.getX(), evt.getY());
            }
    }//GEN-LAST:event_GISMouseWheelMoved

    /**
     * Step 1 — Identify DEM problems (runs on a background thread).
     * Calls {@link TGIS_Hydrology#Sink} on the raw DEM to produce a grid where
     * non-zero cells mark sinks and flat areas. Colours the result in red.
     */
    private void btnSinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSinkActionPerformed
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                String mn, mx;
                TGIS_LayerPixel sinks;

                btnSink.setEnabled(false);

                // creating a grid layer for sinks
                sinks = createLayerPix(dem, HYDRO_LAYER_SINK);

                // the Sink a algorithm requires only a grid layer with DEM
                hydrologyToolset.Sink(dem, ext, sinks);

                GIS.Add(sinks);

                // coloring pixels with sinks (pits) and flats
                mn = Float.toString(sinks.getMinHeight());
                mx = Float.toString(sinks.getMaxHeight());
                sinks.getParams().getPixel().getAltitudeMapZones().Add(
                        String.format("%s,%s,165:15:21:255,%s-%s", mn, mx, mn, mx)
                );

                GIS.InvalidateWholeMap();

                btnFillSink.setEnabled(true);
            }
        }).start();
    }//GEN-LAST:event_btnSinkActionPerformed

    /**
     * Step 2 — Condition the DEM for flow routing (background thread).
     * Calls {@link TGIS_Hydrology#Fill} to raise depressions, then applies a
     * YellowGreen colour ramp with hillshade for terrain context.
     */
    private void btnFillSinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFillSinkActionPerformed
        new Thread(new Runnable() {
            @Override
            public void run() {
                TGIS_LayerPixel hydro_dem;
                TGIS_GradientMap color_ramp;
                TGIS_ColorMap[] color_map;

                btnFillSink.setEnabled(false);

                // turning off layers
                dem.setActive(false);
                getLayerGrd(HYDRO_LAYER_SINK).setActive(false);

                // creating a grid layer for a hydrologically conditioned DEM
                hydro_dem = createLayerPix(dem, HYDRO_LAYER_DEM);

                // the Fill algorithm requires a grid layer with DEM
                hydrologyToolset.Fill(dem, ext, hydro_dem, true); 

                GIS.Add(hydro_dem);

                // applying the layer symbology
                color_ramp = TGIS_Utils.getGisColorRampList().ByName("YellowGreen");
                color_map = color_ramp.RealizeColorMap(TGIS_ColorMapMode.Continuous, 0, true);
                hydro_dem.GenerateRampEx(hydro_dem.getMinHeight(), hydro_dem.getMaxHeight(), color_map, null);
                hydro_dem.getParams().getPixel().setGridShadow(true);
                hydro_dem.getParams().getPixel().setAntialias(true);
                hydro_dem.getParams().getPixel().setShowLegend(false);

                GIS.InvalidateWholeMap();

                btnFlowDirection.setEnabled(true);
            }
        }).start();
        
    }//GEN-LAST:event_btnFillSinkActionPerformed

    /**
     * Step 3 — Compute D8 flow directions (background thread).
     * Calls {@link TGIS_Hydrology#FlowDirection} on the conditioned DEM to assign
     * each cell a power-of-two code (1=E, 2=SE, 4=S … 128=NE). Uses a turbo
     * colour ramp with one zone per code for visual clarity.
     */
    private void btnFlowDirectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFlowDirectionActionPerformed
        new Thread(new Runnable() {
            @Override
            public void run() {
                TGIS_LayerPixel flowdir, hydro_dem;

                btnFlowDirection.setEnabled(false);

                hydro_dem = getLayerGrd(HYDRO_LAYER_DEM);
                hydro_dem.setActive(false);

                // creating a grid layer for flow directions
                flowdir = createLayerPix(dem, HYDRO_LAYER_DIRECTION);

                // the FlowDirection algorithm requires a hydrologically conditioned DEM
                hydrologyToolset.FlowDirection( hydro_dem, ext, flowdir, false ) ;

                // applying a turbo color ramp for direction codes
                flowdir.getParams().getPixel().getAltitudeMapZones().Add( "1,1,48:18:59:255,1" ) ;
                flowdir.getParams().getPixel().getAltitudeMapZones().Add( "2,2,71:117:237:255,2" ) ;
                flowdir.getParams().getPixel().getAltitudeMapZones().Add( "4,4,29:206:214:255,4" ) ;
                flowdir.getParams().getPixel().getAltitudeMapZones().Add( "8,8,98:252:108:255,8" ) ;
                flowdir.getParams().getPixel().getAltitudeMapZones().Add( "16,16,210:232:53:255,16" ) ;
                flowdir.getParams().getPixel().getAltitudeMapZones().Add( "32,32,254:154:45:255,32" ) ;
                flowdir.getParams().getPixel().getAltitudeMapZones().Add( "64,64,217:56:6:255,64" ) ;
                flowdir.getParams().getPixel().getAltitudeMapZones().Add( "128,128,122:4:3:255,128" ) ;
                flowdir.getParams().getPixel().setShowLegend(true);

                GIS.Add(flowdir);
                GIS.InvalidateWholeMap();

                btnFlowAccumulation.setEnabled(true);
            }
        }).start();
    }//GEN-LAST:event_btnFlowDirectionActionPerformed

    /**
     * Step 4 — Accumulate upstream drainage area (background thread).
     * Calls {@link TGIS_Hydrology#FlowAccumulation} to count upstream cells per
     * cell. Applies a geometric-interval classification with the Bathymetry2
     * colour ramp (reversed) to reveal the river-network hierarchy.
     */
    private void btnFlowAccumulationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFlowAccumulationActionPerformed
        new Thread(new Runnable() {
            @Override
            public void run() {
                TGIS_LayerPixel flowdir, flowacc;
                TGIS_ClassificationPixel classifier;

                btnFlowAccumulation.setEnabled(false);

                flowdir = getLayerGrd(HYDRO_LAYER_DIRECTION);
                flowdir.setActive(false);

                // creating a grid layer for flow accumulation
                flowacc = createLayerPix( dem, HYDRO_LAYER_ACCUMULATION) ;

                // the FlowAccumulation algorithm requires a flow accumulation grid
                hydrologyToolset.FlowAccumulation( flowdir, ext, flowacc ) ;

                GIS.Add( flowacc ) ;

                // performing a geometric classification for a better result visualization
                classifier = new TGIS_ClassificationPixel(flowacc);
                classifier.setMethod(TGIS_ClassificationMethod.GeometricalInterval);
                classifier.setBand("1");
                classifier.setNumClasses(5);
                classifier.setColorRamp(TGIS_Utils.getGisColorRampList().ByName("Bathymetry2"));
                classifier.getColorRamp().setDefaultReverse(true);

                classifier.Classify();
                flowacc.getParams().getPixel().setShowLegend(true);

                GIS.InvalidateWholeMap();

                btnAddOutlets.setEnabled(true);
            }
        }).start();
    }//GEN-LAST:event_btnFlowAccumulationActionPerformed

    /**
     * Step 5 — Place watershed outlet points (background thread).
     * Creates a point vector layer and adds two hardcoded coordinates that fall
     * on high-accumulation cells. These pour points are used by the Watershed step.
     */
    private void btnAddOutletsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddOutletsActionPerformed
        new Thread(new Runnable() {
            @Override
            public void run() {
                TGIS_LayerVector outlets;
                TGIS_Shape shp;

                btnAddOutlets.setEnabled(false);

                // creating a grid layer for outlets (pour points)
                outlets = createLayerVec( HYDRO_LAYER_OUTLETS, dem.getCS(), TGIS_ShapeType.Point );

                // adding point symbology
                outlets.getParams().getMarker().setStyle(TGIS_MarkerStyle.TriangleUp);
                outlets.getParams().getMarker().setSizeAsText("SIZE:8pt");

                // adding two sample pour points
                // outlets should be located to cells of high accumulated flow
                TGIS_LayerVector tmp = new TGIS_LayerVector();
                shp = outlets.CreateShape(TGIS_ShapeType.Point);
                shp.Lock(TGIS_Lock.Projection);
                shp.AddPart();
                shp.AddPoint(TGIS_Utils.GisPoint(375007.548333318, 696503.13358447));
                shp.Unlock();

                shp = outlets.CreateShape(TGIS_ShapeType.Point);
                shp.Lock(TGIS_Lock.Projection);
                shp.AddPart();
                shp.AddPoint(TGIS_Utils.GisPoint(399612.055851588, 706196.55502031 ));
                shp.Unlock();

                GIS.Add( outlets );
                GIS.InvalidateWholeMap();

                btnWatershed.setEnabled(true);
            }
        }).start();
    }//GEN-LAST:event_btnAddOutletsActionPerformed

    /**
     * Step 6 — Delineate catchment areas per outlet (background thread).
     * Calls {@link TGIS_Hydrology#Watershed} with the flow-direction grid and
     * vector outlet layer to label every DEM cell with the ID of the outlet it
     * drains toward. Each catchment is coloured distinctly.
     */
    private void btnWatershedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWatershedActionPerformed
        new Thread(new Runnable() {
            @Override
            public void run() {
                TGIS_LayerVector outlets;
                TGIS_LayerPixel flowdir, watershed;

                btnWatershed.setEnabled(false);

                flowdir = getLayerGrd(HYDRO_LAYER_DIRECTION);
                outlets = getLayerVec(HYDRO_LAYER_OUTLETS);

                // creating a grid layer for watershed
                watershed = createLayerPix(dem, HYDRO_LAYER_WATERSHED);

                // applying symbology
                watershed.getParams().getPixel().getAltitudeMapZones().Add("1,1,62:138:86:255,1");
                watershed.getParams().getPixel().getAltitudeMapZones().Add("2,2,108:3:174:255,2");
                watershed.setTransparency(50);

                watershed.getParams().getPixel().setShowLegend(true);

                // the Watershed algorithm requires Flow Direction grid and outlets
                // (may be vector, or grid)
                hydrologyToolset.Watershed(flowdir, outlets, __Global.GIS_FIELD_UID, ext, watershed);

                GIS.Add(watershed);
                GIS.InvalidateWholeMap();

                btnBasin.setEnabled(true);
            }
        }).start();
    }//GEN-LAST:event_btnWatershedActionPerformed

    /**
     * Step 7 — Partition the DEM into topographic basins (background thread).
     * Calls {@link TGIS_Hydrology#Basin} with a threshold of 1 % of the maximum
     * accumulated flow. Applies a unique-value classification with the UniquePastel
     * colour ramp so each basin has a distinct fill colour.
     */
    private void btnBasinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBasinActionPerformed
        new Thread(new Runnable() {
            @Override
            public void run() {
                TGIS_LayerPixel flowdir, flowacc;
                TGIS_LayerPixel basins;
                TGIS_ClassificationPixel classifier;

                btnBasin.setEnabled(false);

                flowdir = getLayerGrd(HYDRO_LAYER_DIRECTION);
                flowacc = getLayerGrd(HYDRO_LAYER_ACCUMULATION);
                flowacc.setActive(false);
                getLayerGrd(HYDRO_LAYER_DEM).setActive(false);
                getLayerGrd(HYDRO_LAYER_WATERSHED).setActive(false);
                getLayerVec(HYDRO_LAYER_OUTLETS).setActive(false);

                // creating a grid layer for Basin
                basins = createLayerPix(dem, HYDRO_LAYER_BASIN);

                // the Basin algorithm only requires a Flow Direction grid
                hydrologyToolset.Basin(flowdir, ext, basins, Math.round(flowacc.getMaxHeight()/100));

                GIS.Add(basins) ;

                // classifying basin grid by unique values
                classifier = new TGIS_ClassificationPixel(basins);
                classifier.setMethod(TGIS_ClassificationMethod.Unique);
                classifier.setBand(__Global.GIS_BAND_GRID);
                classifier.setShowLegend(false);
                classifier.setColorRampName("UniquePastel");
                classifier.getColorRamp().setDefaultColorMapMode(TGIS_ColorMapMode.Discrete);

                classifier.Classify();

                GIS.InvalidateWholeMap();

                btnStreamOrderStrahler.setEnabled(true);            }
        }).start();
    }//GEN-LAST:event_btnBasinActionPerformed

    /**
     * Step 8 — Compute Strahler stream order (background thread).
     * Calls {@link TGIS_Hydrology#StreamOrder} with the Strahler method to classify
     * each stream cell by its hierarchical order (1 = headwater, higher = trunk).
     * Styled with a Blues colour ramp so higher-order streams appear darker.
     */
    private void btnStreamOrderStrahlerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStreamOrderStrahlerActionPerformed
        new Thread(new Runnable() {
            @Override
            public void run() {
                TGIS_LayerPixel flowdir, flowacc, stream_order;
        
                btnStreamOrderStrahler.setEnabled(false);

                flowdir = getLayerGrd(HYDRO_LAYER_DIRECTION);
                flowacc = getLayerGrd(HYDRO_LAYER_ACCUMULATION);

                // creating a grid layer for stream order
                stream_order = createLayerPix(dem, HYDRO_LAYER_STREAM_ORDER);

                // applying a symbology from "blues" color ramp
                stream_order.getParams().getPixel().getAltitudeMapZones().Add("1,1,78:179:211:255,1");
                stream_order.getParams().getPixel().getAltitudeMapZones().Add("2,2,43:140:190:255,2");
                stream_order.getParams().getPixel().getAltitudeMapZones().Add("3,3,8:104:172:255,3");
                stream_order.getParams().getPixel().getAltitudeMapZones().Add("4,4,8:64:129:255,4");
                stream_order.getParams().getPixel().setShowLegend(true);

                // the StreamOrder algorithm requires Flow direction and acumulation grids
                hydrologyToolset.StreamOrder(flowdir, flowacc, ext, stream_order, TGIS_HydrologyStreamOrderMethod.Strahler, -1);

                GIS.Add(stream_order);
                GIS.InvalidateWholeMap();

                btnVectorize.setEnabled(true);
            }
        }).start();
    }//GEN-LAST:event_btnStreamOrderStrahlerActionPerformed

    /**
     * Step 9 — Convert raster results to vector layers (background thread).
     * Two vectorisation operations run in sequence:
     * <ol>
     *   <li>{@link TGIS_GridToPolygon} converts the basin raster to polygon shapes,
     *       classified by unique basin ID with the Unique colour ramp.</li>
     *   <li>{@link TGIS_Hydrology#StreamToPolyline} converts the stream-order raster
     *       to polylines; width and colour are scaled by Strahler order via RENDERER.</li>
     * </ol>
     */
    private void btnVectorizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVectorizeActionPerformed
        new Thread(new Runnable() {
            @Override
            public void run() {
                TGIS_LayerPixel flowdir;
                TGIS_LayerPixel basins, streams;
                TGIS_LayerVector streams_vec, basins_vec;
                TGIS_GridToPolygon vectorizator;
                TGIS_ClassificationVector classifier;

                btnVectorize.setEnabled(false);

                flowdir = getLayerGrd(HYDRO_LAYER_DIRECTION);
                streams = getLayerGrd(HYDRO_LAYER_STREAM_ORDER);
                basins  = getLayerGrd(HYDRO_LAYER_BASIN);

                streams.setActive(false);
                basins.setActive(false);

                // 1. Converting basins to polygon

                // creating a vector polygon layer for basins
                basins_vec = createLayerVec(HYDRO_LAYER_BASIN_VEC, dem.getCS(), TGIS_ShapeType.Polygon);
                basins_vec.AddField(HYDRO_FIELD_BASIN, TGIS_FieldType.Number, 10, 0);

                // using GridToPolygon vectorization tool
                vectorizator = new TGIS_GridToPolygon();
                vectorizator.setBusyEvent(new TGIS_BusyEvent() {
                    @Override
                    public void Invoke(Object o, TGIS_BusyEventArgs tgsb) {
                        doBusyEvent(o, tgsb);
                    }
                });
                vectorizator.Generate(basins, basins_vec, HYDRO_FIELD_BASIN);

                GIS.Add(basins_vec);

                // classifying a basins vector layer by unique values
                classifier = new TGIS_ClassificationVector(basins_vec);
                classifier.setMethod(TGIS_ClassificationMethod.Unique);
                classifier.setField(HYDRO_FIELD_BASIN);
                classifier.setShowLegend(false);
                classifier.setColorRampName("Unique");
                classifier.getColorRamp().setDefaultColorMapMode(TGIS_ColorMapMode.Discrete);

                
                classifier.Classify();

                // 2. Converting streams to polylines

                // creating a vector layer for streams from Stream Order grid
                streams_vec = createLayerVec(HYDRO_LAYER_STREAM_VEC, dem.getCS(), TGIS_ShapeType.Arc);
                streams_vec.AddField(HYDRO_FIELD_ORDER, TGIS_FieldType.Number, 10, 0);

                // applying a symbology and width based on a stream order value, and labeling
                streams_vec.getParams().getLine().setWidthAsText("RENDERER");
                streams_vec.getParams().getLine().setColorAsText("ARGB:FF045A8D");
                streams_vec.getParams().getRender().setExpression(HYDRO_FIELD_ORDER);
                streams_vec.getParams().getRender().setZones(4);
                streams_vec.getParams().getRender().setMinVal(1);
                streams_vec.getParams().getRender().setMaxVal(5);
                streams_vec.getParams().getRender().setStartSizeAsText("SIZE:1pt");
                streams_vec.getParams().getRender().setEndSizeAsText("SIZE:4pt");
                streams_vec.getParams().getLabels().setValue(String.format("{%s}", HYDRO_FIELD_ORDER));
                streams_vec.getParams().getLabels().setFontSizeAsText("SIZE:7pt");
                streams_vec.getParams().getLabels().setFontColorAsText("ARGB:FF045A8D");
                streams_vec.getParams().getLabels().setColorAsText("ARGB:FFBDC9E1");
                streams_vec.getParams().getLabels().setAlignment(TGIS_LabelAlignment.Follow);

                hydrologyToolset.StreamToPolyline(flowdir, streams, ext, streams_vec, HYDRO_FIELD_ORDER, 0);

                GIS.Add(streams_vec);
                GIS.InvalidateWholeMap();
            }
        }).start();
    }//GEN-LAST:event_btnVectorizeActionPerformed
    
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
            java.util.logging.Logger.getLogger(HydrologyUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HydrologyUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HydrologyUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HydrologyUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HydrologyUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private tatukgis.jdk.TGIS_ControlLegend GIS_Legend;
    private javax.swing.JButton btn3D;
    private javax.swing.JButton btnAddOutlets;
    private javax.swing.JButton btnBasin;
    private javax.swing.JButton btnFillSink;
    private javax.swing.JButton btnFlowAccumulation;
    private javax.swing.JButton btnFlowDirection;
    private javax.swing.JButton btnSink;
    private javax.swing.JButton btnStreamOrderStrahler;
    private javax.swing.JButton btnVectorize;
    private javax.swing.JButton btnWatershed;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar prgBussy;
    // End of variables declaration//GEN-END:variables
}
