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

public class TriangulationUI extends javax.swing.JFrame {

    public TriangulationUI() {
        initComponents();
        initOwn();
    }

    private void initOwn(){
        TGIS_LayerVector ll;
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/Poland/DCW/city.shp");
        
        ll = (TGIS_LayerVector)GIS.getItems().getItems(0);
        
        ll.getParams().getMarker().setColor(TGIS_Color.getGray());
        ll.getParams().getMarker().setOutlineWidth(2);
        ll.getParams().getMarker().setStyle(TGIS_MarkerStyle.Circle);
        
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
        setTitle("Triangulation - TatukGIS DK11 sample");
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

    private void rdbtnDelaunayActionPerformed(java.awt.event.ActionEvent evt) {
        rdbtnVoronoi.setSelected(!rdbtnDelaunay.isSelected());
        txtLayerName.setText("Delaunay");
    }

    private void rdbtnVoronoiActionPerformed(java.awt.event.ActionEvent evt) {
        rdbtnDelaunay.setSelected(!rdbtnVoronoi.isSelected());
        txtLayerName.setText("Voronoi");
    }

    private void btnFullExtentActionPerformed(java.awt.event.ActionEvent evt) {
        if(GIS.getIsEmpty()) return;
        GIS.FullExtent();
    }

    private void btnZoomInActionPerformed(java.awt.event.ActionEvent evt) {
        if(GIS.getIsEmpty()) return;
        GIS.setZoom(GIS.getZoom()*2);
    }

    private void btnZoomOutActionPerformed(java.awt.event.ActionEvent evt) {
        if(GIS.getIsEmpty()) return;
        GIS.setZoom(GIS.getZoom()/2);
    }

    private void GISMousePressed(java.awt.event.MouseEvent evt) {
        TGIS_Shape shp;
        TGIS_Point ptg;
        String info;
        
        if(GIS.getIsEmpty()) return;
        
        ptg = GIS.ScreenToMap(new TPoint(evt.getX(), evt.getY()));
        shp = (TGIS_Shape)GIS.Locate(ptg, 5/GIS.getZoom());
        
        if(shp != null)
            GIS_ControlAttributes1.ShowShape(shp);
    }

    private void btnGenerateActionPerformed(java.awt.event.ActionEvent evt) {
        TGIS_LayerVector lv;
        
        if(rdbtnDelaunay.isSelected())
            lv = new TGIS_LayerDelaunay();
        else
            lv = new TGIS_LayerVoronoi();
        
        lv.setName(txtLayerName.getText());
        lv.ImportLayer((TGIS_LayerVector)GIS.getItems().getItems(0), GIS.getExtent(), TGIS_ShapeType.Unknown, "", false);
        lv.setTransparency(60);
        
        lv.getParams().getRender().setExpression("GIS_AREA");
        lv.getParams().getRender().setMinVal(10000000);
        lv.getParams().getRender().setMaxVal(1300000000);
        lv.getParams().getRender().setStartColor(TGIS_Color.getWhite());
        if(rdbtnVoronoi.isSelected())
            lv.getParams().getRender().setEndColor(TGIS_Color.getRed());
        else
            lv.getParams().getRender().setEndColor(TGIS_Color.getBlue());
        lv.getParams().getRender().setZones(10);
        lv.getParams().getArea().setColor(TGIS_Color.getRenderColor());
        lv.setCS(GIS.getCS());
        GIS.Add(lv);
        GIS.InvalidateWholeMap();
    }
    
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


        /* Create and display the form */
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
