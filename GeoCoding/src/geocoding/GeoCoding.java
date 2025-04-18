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

public class GeoCoding extends javax.swing.JFrame {

    public GeoCoding() {
        initComponents();
        initOwn();
    }

    private void initOwn(){
        GIS.Lock();
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/Samples/Projects/California.ttkproject");
        layerSrc = (TGIS_LayerVector) (GIS.Get("streets"));

        layerRoute = new TGIS_LayerVector();
        layerRoute.setUseConfig(false);
        layerRoute.getParams().getLine().setColor(TGIS_Color.getRed());
        layerRoute.getParams().getLine().setWidth(-2);
        layerRoute.getParams().getMarker().setOutlineWidth(1);
        layerRoute.setName("RouteDisplay");
        layerRoute.setCS(GIS.getCS());
        GIS.Add(layerRoute);
        geoObj = new TGIS_Geocoding(layerSrc);
        geoObj.setOffset(0.0001);
        geoObj.setRoadName("FULLNAME");
        geoObj.setLFrom("LFROMADD");
        geoObj.setLTo("LTOADD");
        geoObj.setRFrom("RFROMADD");
        geoObj.setRTo("RTOADD");
        
        rtrObj = new TGIS_ShortestPath(GIS);
        rtrObj.setLinkTypeEvent(new TGIS_LinkTypeEvent() {
            @Override
            public void Invoke(Object o, TGIS_LinkTypeEventArgs tgslt) {
                if((tgslt.getShape().GetField("MTFCC").toString().compareTo("S1400")) != 0) {
                    tgslt.setLinkType(1);
                } else {
                    tgslt.setLinkType(0);
                }
            }
        });
        
        rtrObj.LoadTheData(layerSrc);
        rtrObj.setRoadName("FULLNAME");
        
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

        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        GIS_ControlScale = new tatukgis.jdk.TGIS_ControlScale();
        jPanel1 = new javax.swing.JPanel();
        sldrLocal = new javax.swing.JSlider();
        prefLocalLabel = new javax.swing.JLabel();
        PrefHighwayLabel = new javax.swing.JLabel();
        sldrHighway = new javax.swing.JSlider();
        fromLabel = new javax.swing.JLabel();
        tfAddrFrom = new javax.swing.JTextField();
        btnResolve = new javax.swing.JButton();
        toLabel = new javax.swing.JLabel();
        tfAddrTo = new javax.swing.JTextField();
        btnRoute = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        taRoute = new javax.swing.JEditorPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GeoCoding - TatukGIS DK11 sample");
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

    private void btnRouteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRouteActionPerformed
        int i;
        TGIS_Shape shp;
        int res;
        TGIS_Point pt_a;
        TGIS_Point pt_b;
        String ang = "";
        String oldnam = "";   
        
        // calculate wages
        rtrObj.setCostModifiers(0, 1.0 - 1.0 / 11.0 * sldrHighway.getValue());
        rtrObj.setCostModifiers(1, 1.0 - 1.0 / 11.0 * sldrLocal.getValue());
        
        // locate shapes meeting query
        res = geoObj.Parse(tfAddrFrom.getText());
        // if not found, ask for more details
        if (res > 0) {
            tfAddrFrom.setText(geoObj.getQuery(0));
        } else {
            tfAddrFrom.setText(tfAddrFrom.getText() + " ???");
        }

        // remember starting point
        if (res <= 0) return;
        pt_a = geoObj.getPoint(0);

        res = geoObj.Parse(tfAddrTo.getText());
        if (res > 0) { 
            tfAddrTo.setText(geoObj.getQuery(0));
        } else {
            tfAddrTo.setText(tfAddrTo.getText() + " ???");
        }

        // remember ending point
        if (res <= 0) return;
        pt_b = geoObj.getPoint(0);

        // set starting and ending position
        rtrObj.UpdateTheData();
        rtrObj.Find(layerRoute.Unproject(pt_a), layerRoute.Unproject(pt_b));

        taRoute.setText("");
        oldnam = "#$@3eqewe";
        for (i = 0; i < rtrObj.getItemsCount(); i++)
        {
            switch (rtrObj.getItems(i).getCompass())
            {
                case 0:
                    ang = "FWD  ";
                    break;
                case 1:
                    ang = "RIGHT";
                    break;
                case 2:
                    ang = "RIGHT";
                    break;
                case 3:
                    ang = "RIGHT";
                    break;
                case 4:
                    ang = "BACK ";
                    break;
                case -1:
                    ang = "LEFT ";
                    break;
                case -2:
                    ang = "LEFT ";
                    break;
                case -3:
                    ang = "LEFT ";
                    break;
                case -4:
                    ang = "BACK ";
                    break;
            }

            if (oldnam.compareTo(rtrObj.getItems(i).getName()) == 0 ) continue;
            oldnam = rtrObj.getItems(i).getName();

            taRoute.setText(taRoute.getText() + ang + " " + rtrObj.getItems(i).getName() + "\r\n");
        }    
        
        layerRoute.RevertShapes();

        // add shapes of our path to route layer (red)
        for (i = 0; i < rtrObj.getItemsCount(); i++)
        {
            shp = rtrObj.getItems(i).getLayer().GetShape(rtrObj.getItems(i).getUid());
            if (shp == null) continue;
            layerRoute.AddShape(shp);
            if (i == 0) {
                layerRoute.setExtent(shp.getExtent());
            }
        }

        // mark starting point as green squere
        shp = layerRoute.CreateShape(TGIS_ShapeType.Point);
        shp.Lock(TGIS_Lock.Extent);
        shp.AddPart();
        shp.AddPoint(pt_a);
        shp.getParams().getMarker().setColor(TGIS_Color.getGreen());
        shp.Unlock();

        // mark starting point as red squere
        shp = layerRoute.CreateShape(TGIS_ShapeType.Point);
        shp.Lock(TGIS_Lock.Extent);
        shp.AddPart();
        shp.AddPoint(pt_b);
        shp.Unlock();

        GIS.Lock();
        GIS.setVisibleExtent(layerRoute.getExtent());
        GIS.setZoom(0.7 * GIS.getZoom());
        GIS.Unlock();
    }//GEN-LAST:event_btnRouteActionPerformed

    private void btnResolveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResolveActionPerformed
        int i;
        int r;
        TGIS_Shape shp;

        if (geoObj == null) return;

        layerRoute.RevertShapes();

        // locate shapes meeting query
        r = geoObj.Parse(tfAddrFrom.getText()) - 1;
        if (r <= 0) {
            tfAddrFrom.setText(tfAddrFrom.getText() + " ???");
        }

        for (i = 0; i <= r; i++)
        {
            tfAddrFrom.setText(geoObj.getQuery(i));
            //Application.DoEvents(); ???

            // add found shape to route layer (red color)
            shp = layerSrc.GetShape(geoObj.getUid(i));
            layerRoute.AddShape(shp);

            if (i == 0) {
                layerRoute.setExtent(shp.getProjectedExtent());
            }

            shp = layerSrc.GetShape(geoObj.getUidEx(i));
            
            if (shp != null) {
                layerRoute.AddShape(shp);
            }

            // mark address as green squere
            shp = layerRoute.CreateShape(TGIS_ShapeType.Point);
            shp.Lock(TGIS_Lock.Extent);
            shp.AddPart();
            shp.AddPoint(layerRoute.getCS().FromCS(layerSrc.getCS(), geoObj.getPoint(i)));
            shp.getParams().getMarker().setColor(TGIS_Color.getGreen());
            shp.Unlock();
        }
        GIS.Lock();
        GIS.setVisibleExtent(layerRoute.getProjectedExtent());
        GIS.setZoom(0.7 * GIS.getZoom());
        GIS.Unlock();

    }//GEN-LAST:event_btnResolveActionPerformed

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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GeoCoding().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private tatukgis.jdk.TGIS_ControlScale GIS_ControlScale;
    private javax.swing.JLabel PrefHighwayLabel;
    private javax.swing.JButton btnResolve;
    private javax.swing.JButton btnRoute;
    private javax.swing.JLabel fromLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel prefLocalLabel;
    private javax.swing.JSlider sldrHighway;
    private javax.swing.JSlider sldrLocal;
    private javax.swing.JEditorPane taRoute;
    private javax.swing.JTextField tfAddrFrom;
    private javax.swing.JTextField tfAddrTo;
    private javax.swing.JLabel toLabel;
    // End of variables declaration//GEN-END:variables
    private tatukgis.jdk.TGIS_LayerVector layerSrc;
    private tatukgis.jdk.TGIS_LayerVector layerRoute;
    private tatukgis.jdk.TGIS_Geocoding geoObj;
    private tatukgis.jdk.TGIS_ShortestPath rtrObj;
}
