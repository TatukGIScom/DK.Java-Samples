package viewshed;

import RemObjects.Elements.System.VarParameter;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_DimensionType;
import tatukgis.jdk.TGIS_LayerPixel;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_Point;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_ShapeType;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.jdk.TGIS_ViewerMode;
import tatukgis.jdk.TGIS_Viewshed;
import tatukgis.jdk.TGIS_ViewshedObserverElevation;
import tatukgis.rtl.TPoint;

public class ViewshedUI extends javax.swing.JFrame {

    public ViewshedUI() {
        initComponents();
        initOwn();
    }
    
    //private void 
    
    private void initOwn(){
        GIS.Lock();
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/USA/States/California/San Bernardino/NED/w001001.adf");
        
        // obtain the DEM layer
        lTerrain = (TGIS_LayerPixel)GIS.Get("w001001");
        lTerrain.getParams().getPixel().getAltitudeMapZones().Clear();
        
        // create a layer for storing the observer locations
        lObservers = new TGIS_LayerVector();
        lObservers.setName("Observers");
        lObservers.setCS(lTerrain.getCS());
        lObservers.Open();
        
        // add a symbol to represent observers
        lObservers.getParams().getMarker().setSymbol(TGIS_Utils.getSymbolList().Prepare("LIBSVG:std:TowerCommunication01"));
        lObservers.getParams().getMarker().setColor(TGIS_Color.getWhite());
        lObservers.getParams().getMarker().setOutlineColor(TGIS_Color.getWhite());
        lObservers.getParams().getMarker().setSize(-32);
        
        GIS.Add(lObservers);
        GIS.Unlock();
        GIS.FullExtent();
    }
    
    private void setLayerActive(){
       GIS.Lock();
       makeViewshedRamp();
       if(GIS.Get(SAMPLE_VIEWSHED_NAME) != null){
           lViewshed.setActive(!rbtnAGL.isSelected());
           lAGL.setActive(rbtnAGL.isSelected());
           GIS.InvalidateWholeMap();
       }
       GIS.Unlock();
       
       showComment() ;
    }
    
    private void showComment(){
        if(rbtnBinary.isSelected()) {
            lDesc.setText("Green - area of visibility.");
        } else if (rbtnFreq.isSelected()) {
            lDesc.setText("Visibility frequency; " +
                    "Red - one  observer is visible; " +
                    "Green - all observers are visible.");
        } else if(rbtnAGL.isSelected()) {
            lDesc.setText("Minimum height that must be added to a nonvisible cell " +
                    "to make it visible by at least one observer; "+
                    "Red = " + Math.round(lAGL.getMaxHeight()) + " m");
        }
    }
    
    private void makeViewshedRamp(){
        if(GIS.Get(SAMPLE_VIEWSHED_NAME) == null)
            return;
        
        lViewshed.setTransparency(50);
        lViewshed.getParams().getPixel().setGridShadow(false);
        lViewshed.getParams().getPixel().getAltitudeMapZones().Clear();
        
        // create color ramp for binary viewshed
        if(rbtnBinary.isSelected()){
        // make default value transparent
        lViewshed.GenerateRamp(
          TGIS_Color.FromARGB( (byte)127, (byte)0, (byte)255, (byte)0),
          TGIS_Color.getNone(),
          TGIS_Color.FromARGB( (byte)127, (byte)0, (byte)255, (byte)0),
          lViewshed.getMinHeight(), 0.01,
          lViewshed.getMaxHeight(), false,
          ( lViewshed.getMaxHeight() - lViewshed.getMinHeight() )/100,
          ( lViewshed.getMaxHeight() - lViewshed.getMinHeight() )/10,
          null, false
        ) ;
        // create color ramp for frequency viewshed    
        }else if(rbtnFreq.isSelected()){
            lViewshed.GenerateRamp(
              TGIS_Color.FromARGB( (byte)127, (byte)255, (byte)0, (byte)0),
              TGIS_Color.getNone(),
              TGIS_Color.FromARGB( (byte)127, (byte)0, (byte)255, (byte)0),
              0, 0,
              lViewshed.getMaxHeight(), false,
              ( lViewshed.getMaxHeight() - lViewshed.getMinHeight() )/100,
              ( lViewshed.getMaxHeight() - lViewshed.getMinHeight() )/10,
              null, false
            ) ;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mapmode = new javax.swing.ButtonGroup();
        visiblelayer = new javax.swing.ButtonGroup();
        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        btnFullExtent = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        lDesc = new javax.swing.JLabel();
        pMapMode = new javax.swing.JPanel();
        rbtnZoom = new javax.swing.JRadioButton();
        rbtnAddObserver = new javax.swing.JRadioButton();
        pVisibleLayer = new javax.swing.JPanel();
        rbtnBinary = new javax.swing.JRadioButton();
        rbtnFreq = new javax.swing.JRadioButton();
        rbtnAGL = new javax.swing.JRadioButton();
        lblObserverElevation = new javax.swing.JLabel();
        tfElevation = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        lInfo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Viewshed - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));

        GIS.setMode(tatukgis.jdk.TGIS_ViewerMode.UserDefined);
        GIS.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                GISMouseMoved(evt);
            }
        });
        GIS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                GISMousePressed(evt);
            }
        });

        btnFullExtent.setText("Full Extent");
        btnFullExtent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFullExtentActionPerformed(evt);
            }
        });

        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        lDesc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lDesc.setText("Click on the map to add an observer");

        pMapMode.setBorder(javax.swing.BorderFactory.createTitledBorder("Map Mode"));

        mapmode.add(rbtnZoom);
        rbtnZoom.setText("Zoom");
        rbtnZoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnZoomActionPerformed(evt);
            }
        });

        mapmode.add(rbtnAddObserver);
        rbtnAddObserver.setSelected(true);
        rbtnAddObserver.setText("Add Observer");
        rbtnAddObserver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnAddObserverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pMapModeLayout = new javax.swing.GroupLayout(pMapMode);
        pMapMode.setLayout(pMapModeLayout);
        pMapModeLayout.setHorizontalGroup(
            pMapModeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pMapModeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pMapModeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbtnAddObserver)
                    .addComponent(rbtnZoom))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pMapModeLayout.setVerticalGroup(
            pMapModeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pMapModeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbtnZoom)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbtnAddObserver)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pVisibleLayer.setBorder(javax.swing.BorderFactory.createTitledBorder("Visible Layer"));

        visiblelayer.add(rbtnBinary);
        rbtnBinary.setSelected(true);
        rbtnBinary.setText("Viewshed (binary)");
        rbtnBinary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnBinaryActionPerformed(evt);
            }
        });

        visiblelayer.add(rbtnFreq);
        rbtnFreq.setText("Viewshed (frequency)");
        rbtnFreq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnFreqActionPerformed(evt);
            }
        });

        visiblelayer.add(rbtnAGL);
        rbtnAGL.setText("Above-Ground-Level");
        rbtnAGL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnAGLActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pVisibleLayerLayout = new javax.swing.GroupLayout(pVisibleLayer);
        pVisibleLayer.setLayout(pVisibleLayerLayout);
        pVisibleLayerLayout.setHorizontalGroup(
            pVisibleLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pVisibleLayerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pVisibleLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbtnAGL)
                    .addComponent(rbtnFreq)
                    .addComponent(rbtnBinary))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pVisibleLayerLayout.setVerticalGroup(
            pVisibleLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pVisibleLayerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbtnBinary)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbtnFreq)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbtnAGL)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblObserverElevation.setText("Observer Elevation (meters)");

        tfElevation.setText("30");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lInfo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lInfo, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnFullExtent, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pMapMode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pVisibleLayer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblObserverElevation)
                            .addComponent(tfElevation))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lDesc, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFullExtent)
                    .addComponent(lDesc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnReset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pMapMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pVisibleLayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblObserverElevation)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfElevation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 30, Short.MAX_VALUE))
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void GISMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GISMousePressed
        TGIS_Point ptg;
        TGIS_Shape shp;
        TGIS_Viewshed vs;
        Float elev;
        
        if(GIS.getMode() == TGIS_ViewerMode.UserDefined){
            try {
                elev = Float.parseFloat(tfElevation.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "'" + tfElevation.getText() + "' is not a valid floating point value.");
                return;
            }
            
            GIS.Lock();
            try{
                // check if the point lays within the DEM area
                ptg = GIS.ScreenToMap(new TPoint(evt.getX(), evt.getY()));
                if(!TGIS_Utils.GisIsPointInsideExtent(ptg, lTerrain.getExtent()))
                    return;
                
                // add observer to the observer layer
                shp = lObservers.CreateShape(TGIS_ShapeType.Point, TGIS_DimensionType.XY);
                shp.AddPart();
                shp.AddPoint(ptg);
                
                // remove previous viewshed/AGL layers
                if(GIS.Get(SAMPLE_VIEWSHED_NAME) != null){
                    GIS.Delete(lAGL.getName());
                    lAGL = null;
                    GIS.Delete(lViewshed.getName());
                    lViewshed = null;
                }
                
                // create and set up the layer to host viewshed
                lViewshed = new TGIS_LayerPixel();
                lViewshed.Build(true, lTerrain.getCS(), lTerrain.getExtent(),
                        lTerrain.getBitWidth(), lTerrain.getBitHeight()
                );
                lViewshed.setName(SAMPLE_VIEWSHED_NAME);
                lViewshed.Open();
                
                // create and set up the layer to host above-ground-level
                lAGL = new TGIS_LayerPixel();
                lAGL.Build(true, lTerrain.getCS(), lTerrain.getExtent(),
                        lTerrain.getBitWidth(), lTerrain.getBitHeight()
                );
                lAGL.setName(SAMPLE_AGL_NAME);
                lAGL.Open();      
                
                // create viewshed tool
                vs = new TGIS_Viewshed();
                // set the base observer elevation to be read from the DEM layer
                vs.setObserverElevation(TGIS_ViewshedObserverElevation.OnDem);
                // turn on the correction for earth curvature and refractivity
                vs.setCurvedEarth(true);
                
                // initiate the viewshed/AGL generation process
                vs.Generate(lTerrain, lObservers, lViewshed, lAGL, Float.parseFloat("0.0"), "", elev);
                
                lViewshed.setActive(!rbtnAGL.isSelected());
                lAGL.setActive(rbtnAGL.isSelected());
                
                GIS.Add(lAGL);
                GIS.Add(lViewshed);
                lAGL.setTransparency(50);
                lViewshed.setTransparency(50);
                lObservers.Move(-2);
                
                // apply (binary or frequency) color ramp to the viewshed layer
                makeViewshedRamp();
                
                // apply color ramp to the AGL layer
                lAGL.getParams().getPixel().setGridShadow(false);
                lAGL.GenerateRamp(
                  TGIS_Color.FromARGB( (byte)127, (byte)0, (byte)255, (byte)0),
                  TGIS_Color.getNone(),
                  TGIS_Color.FromARGB( (byte)127, (byte)255, (byte)0, (byte)0),
                  0, 1,
                  lAGL.getMaxHeight(), false,
                  ( lAGL.getMaxHeight() - lAGL.getMinHeight() )/100,
                  ( lAGL.getMaxHeight() - lAGL.getMinHeight() )/10,
                  null, false
                ) ;           
                GIS.InvalidateWholeMap();
            }finally{
                GIS.Unlock();
            }
            showComment();
        }
    }//GEN-LAST:event_GISMousePressed

    private void btnFullExtentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFullExtentActionPerformed
        GIS.FullExtent();
    }//GEN-LAST:event_btnFullExtentActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        GIS.Lock();
        if(GIS.Get(SAMPLE_VIEWSHED_NAME) != null){
            GIS.Delete(lAGL.getName());
            lAGL = null;
            GIS.Delete(lViewshed.getName());
            lViewshed = null;
        }
        lObservers.RevertAll();
        GIS.FullExtent();
        GIS.Unlock();
    }//GEN-LAST:event_btnResetActionPerformed

    private void rbtnAddObserverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnAddObserverActionPerformed
        GIS.setMode(TGIS_ViewerMode.UserDefined);
    }//GEN-LAST:event_rbtnAddObserverActionPerformed

    private void rbtnZoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnZoomActionPerformed
        GIS.setMode(TGIS_ViewerMode.Zoom);
    }//GEN-LAST:event_rbtnZoomActionPerformed

    private void rbtnBinaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnBinaryActionPerformed
        setLayerActive();
    }//GEN-LAST:event_rbtnBinaryActionPerformed

    private void rbtnFreqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnFreqActionPerformed
        setLayerActive();
    }//GEN-LAST:event_rbtnFreqActionPerformed

    private void rbtnAGLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnAGLActionPerformed
        setLayerActive();
    }//GEN-LAST:event_rbtnAGLActionPerformed

    private void GISMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GISMouseMoved
        TGIS_Point ptg;
        VarParameter<TGIS_Color> cl;
        VarParameter<double[]> vals;
        VarParameter<Boolean> transp;
        String str;
        
        cl = new VarParameter<>();
        vals = new VarParameter<>();
        transp = new VarParameter<>();
        
        ptg = GIS.ScreenToMap(new TPoint(evt.getX(), evt.getY()));
        
        str = "" ;
        
        if(lViewshed != null && lViewshed.Locate(ptg, cl, vals, transp)) {
            if(vals.Value[0] != lViewshed.getNoDataValue()) {
                str = str + "Frequancy: " + vals.Value[0] ;
            }
        }
        if(lAGL != null && lAGL.Locate(ptg, cl, vals, transp)) {
            if(vals.Value[0] != lAGL.getNoDataValue()) {
                str = str + "Above-Ground-Level: " + vals.Value[0] ;
            }
        }
        
        lInfo.setText(str);
    }//GEN-LAST:event_GISMouseMoved

    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ViewshedUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewshedUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewshedUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewshedUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewshedUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.JButton btnFullExtent;
    private javax.swing.JButton btnReset;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lDesc;
    private javax.swing.JLabel lInfo;
    private javax.swing.JLabel lblObserverElevation;
    private javax.swing.ButtonGroup mapmode;
    private javax.swing.JPanel pMapMode;
    private javax.swing.JPanel pVisibleLayer;
    private javax.swing.JRadioButton rbtnAGL;
    private javax.swing.JRadioButton rbtnAddObserver;
    private javax.swing.JRadioButton rbtnBinary;
    private javax.swing.JRadioButton rbtnFreq;
    private javax.swing.JRadioButton rbtnZoom;
    private javax.swing.JTextField tfElevation;
    private javax.swing.ButtonGroup visiblelayer;
    // End of variables declaration//GEN-END:variables
    private TGIS_LayerPixel lTerrain;
    private TGIS_LayerVector lObservers;
    private TGIS_LayerPixel lViewshed;
    private TGIS_LayerPixel lAGL;
    private String SAMPLE_VIEWSHED_NAME = "Viewshed";
    private String SAMPLE_AGL_NAME = "Above-Ground-Level";
}
