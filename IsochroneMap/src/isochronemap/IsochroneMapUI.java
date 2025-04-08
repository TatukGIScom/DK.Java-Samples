package isochronemap;

import java.awt.Toolkit;
import javax.swing.JOptionPane;
import tatukgis.jdk.TGIS_CSUnitsList;
import tatukgis.jdk.TGIS_CSUnknownCoordinateSystem;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_Geocoding;
import tatukgis.jdk.TGIS_IsochroneMap;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_LinkCostEvent;
import tatukgis.jdk.TGIS_LinkCostEventArgs;
import tatukgis.jdk.TGIS_LinkDynamicEvent;
import tatukgis.jdk.TGIS_LinkDynamicEventArgs;
import tatukgis.jdk.TGIS_LinkTypeEvent;
import tatukgis.jdk.TGIS_LinkTypeEventArgs;
import tatukgis.jdk.TGIS_Lock;
import tatukgis.jdk.TGIS_PenStyle;
import tatukgis.jdk.TGIS_Point;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_ShapeType;
import tatukgis.jdk.TGIS_ShortestPath;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.jdk.TGIS_ViewerMode;
import tatukgis.rtl.TPoint;

public class IsochroneMapUI extends javax.swing.JFrame {

    public IsochroneMapUI() {
        initComponents();
        initOwn();
    }
    
    private void initOwn(){
        GIS.Lock();
        
        try{
            GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/USA/States/California/San Bernardino/TIGER/tl_2008_06071_edges_trunc.shp");
            layerSrc = (TGIS_LayerVector)(GIS.Get("tl_2008_06071_edges_trunc"));

            if (layerSrc == null) return;
            if (!(layerSrc instanceof TGIS_LayerVector)) return;

            // update the layer parameters to show roads types
            layerSrc.getParamsList().Add();
            layerSrc.getParams().getLine().setWidth( -2 );
            layerSrc.getParams().setQuery( "MTFCC<'S1400'" );
            layerSrc.getParamsList().Add();
            layerSrc.getParams().getLine().setWidth( 1 );
            layerSrc.getParams().getLine().setStyle( TGIS_PenStyle.Dash);
            layerSrc.getParams().setQuery( "MTFCC='S1400'" );

            GIS.setVisibleExtent( layerSrc.getExtent() );
            GIS_Scale.setUnits(new TGIS_CSUnitsList().ByEPSG(9035));

            // initial traversing cost
            costFactor = 5000.0;
            numZones = 5;

            // create route layer for result isochrone map
            layerRoute = new TGIS_LayerVector();
            layerRoute.setUseConfig( false );
            layerRoute.setName( "Isochrone map for route" );
            layerRoute.setCS( GIS.getCS() );
            layerRoute.getParams().getRender().setExpression( "GIS_COST" );
            layerRoute.getParams().getRender().setMinVal( 0 );
            layerRoute.getParams().getRender().setMaxVal( costFactor );
            layerRoute.getParams().getRender().setZones( numZones );
            layerRoute.getParams().getArea().setColor( TGIS_Color.getRenderColor() );
            layerRoute.getParams().getArea().setShowLegend( true );
            layerRoute.setTransparency( 50 );
            GIS.Add(layerRoute);

            // create marker layer to show position
            layerMarker = new TGIS_LayerVector();
            layerMarker.setUseConfig( false );
            layerMarker.setName( "Current Position" );
            layerMarker.setCS( GIS.getCS() );
            layerMarker.getParams().getMarker().setColor( TGIS_Color.getRed() );
            GIS.Add(layerMarker);

            markerShp = null;

            // initialize isochrone map generator
            rtrObj = new TGIS_IsochroneMap(GIS);

            // initialize shortest path and attach events
            srtpObj = new TGIS_ShortestPath(GIS);
            srtpObj.setLinkCostEvent(new TGIS_LinkCostEvent() {
                @Override
                public void Invoke(Object o, TGIS_LinkCostEventArgs tgslc) {
                    if (tgslc.getShape().getLayer().getCS() instanceof TGIS_CSUnknownCoordinateSystem) {
                        tgslc.setCost(tgslc.getShape().Length());
                    } else {
                        tgslc.setCost(tgslc.getShape().LengthCS());
                    }

                    tgslc.setRevCost( tgslc.getCost() );                    
                }
            });
            srtpObj.setLinkTypeEvent(new TGIS_LinkTypeEvent() {
                @Override
                public void Invoke(Object o, TGIS_LinkTypeEventArgs tgslt) {
                    if (!(tgslt.getShape().GetField("MTFCC").toString().contains("S1400") == false)) {
                        tgslt.setLinkType( 1 );
                    } else {
                        tgslt.setLinkType( 0 );
                    }                    
                }
            });
            srtpObj.setLinkDynamicEvent(new TGIS_LinkDynamicEvent() {
                @Override
                public void Invoke(Object o, TGIS_LinkDynamicEventArgs tgsld) {
                    TGIS_Shape shp;

                    if (sldrHighway.getValue() == 1)
                    {
                        shp = layerSrc.GetShape(tgsld.getUid());
                        if(shp.GetField("MTFCC").toString().contains("S1400") == false )
                        {
                            tgsld.setCost( -1 );
                            tgsld.setRevCost( -1 );
                        }    
                    }                    
                }
            });
        }finally{
            GIS.Unlock();
        }
                
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        GIS_Scale = new tatukgis.jdk.TGIS_ControlScale();
        pSide = new javax.swing.JPanel();
        pRouting = new javax.swing.JPanel();
        sldrLocal = new javax.swing.JSlider();
        sldrHighway = new javax.swing.JSlider();
        lblLocal = new javax.swing.JLabel();
        lblHighways = new javax.swing.JLabel();
        edtDistance = new javax.swing.JTextField();
        lblDistance = new javax.swing.JLabel();
        lblZones = new javax.swing.JLabel();
        edtZones = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tGIS_ControlLegend1 = new tatukgis.jdk.TGIS_ControlLegend();
        jToolBar1 = new javax.swing.JToolBar();
        btnFullExtent = new javax.swing.JButton();
        btnZoomIn = new javax.swing.JButton();
        btnZoomout = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("IsochroneMap - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(800, 600));

        GIS.setMode(tatukgis.jdk.TGIS_ViewerMode.Select);
        GIS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                GISMouseClicked(evt);
            }
        });

        GIS_Scale.setGIS_Viewer(GIS);
        GIS.add(GIS_Scale);
        GIS_Scale.setBounds(10, 10, 185, 40);

        pRouting.setBorder(javax.swing.BorderFactory.createTitledBorder("Routing parameters"));

        sldrLocal.setMaximum(10);
        sldrLocal.setMinimum(1);

        sldrHighway.setMaximum(10);
        sldrHighway.setMinimum(1);
        sldrHighway.setValue(5);

        lblLocal.setText("Prefer local roads");

        lblHighways.setText("Prefer highway");

        edtDistance.setText("4000");

        lblDistance.setText("Distance");

        lblZones.setText("Zones");

        edtZones.setText("3");

        jLabel1.setText("<html>Click on the map to set start<br> point and generate isochrone");

        javax.swing.GroupLayout pRoutingLayout = new javax.swing.GroupLayout(pRouting);
        pRouting.setLayout(pRoutingLayout);
        pRoutingLayout.setHorizontalGroup(
            pRoutingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pRoutingLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(pRoutingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblDistance)
                    .addComponent(sldrHighway, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sldrLocal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblLocal)
                    .addComponent(lblHighways)
                    .addComponent(edtDistance)
                    .addComponent(lblZones)
                    .addComponent(edtZones)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18))
        );
        pRoutingLayout.setVerticalGroup(
            pRoutingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pRoutingLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(lblLocal)
                .addGap(3, 3, 3)
                .addComponent(sldrLocal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblHighways)
                .addGap(3, 3, 3)
                .addComponent(sldrHighway, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDistance)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(edtDistance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblZones)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(edtZones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        tGIS_ControlLegend1.setGIS_Viewer(GIS);
        jScrollPane1.setViewportView(tGIS_ControlLegend1);

        javax.swing.GroupLayout pSideLayout = new javax.swing.GroupLayout(pSide);
        pSide.setLayout(pSideLayout);
        pSideLayout.setHorizontalGroup(
            pSideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pSideLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pSideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pRouting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pSideLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jScrollPane1, pRouting});

        pSideLayout.setVerticalGroup(
            pSideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pSideLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pRouting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jToolBar1.setRollover(true);

        btnFullExtent.setFocusable(false);
        btnFullExtent.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFullExtent.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnFullExtent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFullExtentActionPerformed(evt);
            }
        });
        jToolBar1.add(btnFullExtent);

        btnZoomIn.setFocusable(false);
        btnZoomIn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnZoomIn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnZoomIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZoomInActionPerformed(evt);
            }
        });
        jToolBar1.add(btnZoomIn);

        btnZoomout.setFocusable(false);
        btnZoomout.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnZoomout.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnZoomout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZoomoutActionPerformed(evt);
            }
        });
        jToolBar1.add(btnZoomout);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 264, Short.MAX_VALUE))
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pSide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(pSide, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFullExtentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFullExtentActionPerformed
        if (GIS.getIsEmpty()) return;
            GIS.FullExtent();
    }//GEN-LAST:event_btnFullExtentActionPerformed

    private void btnZoomInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZoomInActionPerformed
        if (GIS.getIsEmpty()) return;
            GIS.setZoom(GIS.getZoom() * 2);
    }//GEN-LAST:event_btnZoomInActionPerformed

    private void btnZoomoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZoomoutActionPerformed
        if (GIS.getIsEmpty()) return;
            GIS.setZoom(GIS.getZoom() / 2);
    }//GEN-LAST:event_btnZoomoutActionPerformed

    private void GISMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GISMouseClicked
        TGIS_Point ptg;

        if (GIS.getIsEmpty()) return;
        if (GIS.getMode() != TGIS_ViewerMode.Select) return;

        ptg = GIS.ScreenToMap(new TPoint(evt.getX(), evt.getY()));

        if (markerShp == null)
        {
            markerShp = layerMarker.CreateShape(TGIS_ShapeType.Point);
            markerShp.Lock(TGIS_Lock.Extent);
            markerShp.AddPart();
            markerShp.AddPoint(ptg);
            markerShp.Unlock();
            markerShp.Invalidate();
        }
        else
        {
            markerShp.SetPosition(ptg, null, 0);
        }

        generateIsochrone();
    }//GEN-LAST:event_GISMouseClicked

    private void generateIsochrone(){
        int i;
        TGIS_Shape shp = null;

        if (markerShp == null)
        {
            JOptionPane.showMessageDialog(this, "Please select a start point on the map");
            return;
        }

        layerRoute.RevertShapes();

        // maximum traversing cost for the isochrone map
        numZones = Integer.parseInt(edtZones.getText());
        costFactor = Integer.parseInt(edtDistance.getText());

        // update the renderer range
        layerRoute.getParams().getRender().setMaxVal( costFactor );
        layerRoute.getParams().getRender().setZones( numZones );

        // calculate wages
        srtpObj.setCostModifiers(0, 1.0 - 1.0 / 11.0 * sldrHighway.getValue());
        srtpObj.setCostModifiers(1, 1.0 - 1.0 / 11.0 * sldrLocal.getValue());

        // generate the isochrone maps
        for( i = 1; i <= numZones; i++) {
            rtrObj.Generate(layerSrc, srtpObj, layerRoute, TGIS_ShapeType.Polygon,
                    markerShp.Centroid(), costFactor / i, 0
            );
        }

        // smooth the result polygons shapes
        for(Object tmp : layerRoute.Loop() ){
            shp = (TGIS_Shape)tmp ;
            shp.Smooth(10, false);
        }

        layerRoute.RecalcExtent() ;
        GIS.Lock();
        GIS.setVisibleExtent( layerRoute.getProjectedExtent() );
        GIS.setZoom( 0.7 * GIS.getZoom() );
        GIS.Unlock();
    }
    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IsochroneMapUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IsochroneMapUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private tatukgis.jdk.TGIS_ControlScale GIS_Scale;
    private javax.swing.JButton btnFullExtent;
    private javax.swing.JButton btnZoomIn;
    private javax.swing.JButton btnZoomout;
    private javax.swing.JTextField edtDistance;
    private javax.swing.JTextField edtZones;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblDistance;
    private javax.swing.JLabel lblHighways;
    private javax.swing.JLabel lblLocal;
    private javax.swing.JLabel lblZones;
    private javax.swing.JPanel pRouting;
    private javax.swing.JPanel pSide;
    private javax.swing.JSlider sldrHighway;
    private javax.swing.JSlider sldrLocal;
    private tatukgis.jdk.TGIS_ControlLegend tGIS_ControlLegend1;
    // End of variables declaration//GEN-END:variables
    private TGIS_LayerVector layerSrc;
    private TGIS_LayerVector layerMarker;
    private TGIS_Shape markerShp;
    private double costFactor;
    private int numZones;
    private TGIS_LayerVector layerRoute;
    private TGIS_Geocoding geoObj; 
    private TGIS_IsochroneMap rtrObj;
    private TGIS_ShortestPath srtpObj;
}
