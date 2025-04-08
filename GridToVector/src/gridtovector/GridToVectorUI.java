package GridToVector;

import java.awt.Toolkit;
import tatukgis.jdk.TGIS_BusyEvent;
import tatukgis.jdk.TGIS_BusyEventArgs;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_ColorInterpolationMode;
import tatukgis.jdk.TGIS_FieldType;
import tatukgis.jdk.TGIS_GridToPoint;
import tatukgis.jdk.TGIS_GridToPolygon;
import tatukgis.jdk.TGIS_LayerPixel;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_MarkerStyle;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_ShapeType;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.jdk.TGIS_ViewerMode;
import tatukgis.rtl.TPoint;

public class GridToVectorUI extends javax.swing.JFrame {

    public GridToVectorUI() {       
        initComponents();
        initOwn();
    }
    
    private void initOwn(){
        btnLandCover.doClick();
        GIS.setMode(TGIS_ViewerMode.Select);
    };
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GIS_Attr = new tatukgis.jdk.TGIS_ControlAttributes();
        gbLoadData = new javax.swing.JPanel();
        btnLandCover = new javax.swing.JButton();
        btnDEM = new javax.swing.JButton();
        gbCommon = new javax.swing.JPanel();
        lblTolerance = new javax.swing.JLabel();
        tfTolerance = new javax.swing.JTextField();
        chbIgnoreNoData = new javax.swing.JCheckBox();
        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        pProgressBar = new javax.swing.JProgressBar();
        gbPolygon = new javax.swing.JPanel();
        chkSplitShapes = new javax.swing.JCheckBox();
        btnGridToPolygon = new javax.swing.JButton();
        gbPoint = new javax.swing.JPanel();
        btnGridToPoint = new javax.swing.JButton();
        lblPointSpacing = new javax.swing.JLabel();
        tfPointSpacing = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GridToVector - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(800, 600));

        GIS_Attr.setReadOnly(true);

        gbLoadData.setBorder(javax.swing.BorderFactory.createTitledBorder("Load data"));

        btnLandCover.setText("Land Cover");
        btnLandCover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLandCoverActionPerformed(evt);
            }
        });

        btnDEM.setText("DEM");
        btnDEM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDEMActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout gbLoadDataLayout = new javax.swing.GroupLayout(gbLoadData);
        gbLoadData.setLayout(gbLoadDataLayout);
        gbLoadDataLayout.setHorizontalGroup(
            gbLoadDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gbLoadDataLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(gbLoadDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLandCover, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDEM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        gbLoadDataLayout.setVerticalGroup(
            gbLoadDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gbLoadDataLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnLandCover)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDEM)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gbCommon.setBorder(javax.swing.BorderFactory.createTitledBorder("Common parameters"));

        lblTolerance.setText("Tolerance:");

        chbIgnoreNoData.setText("Ignore NoData");

        javax.swing.GroupLayout gbCommonLayout = new javax.swing.GroupLayout(gbCommon);
        gbCommon.setLayout(gbCommonLayout);
        gbCommonLayout.setHorizontalGroup(
            gbCommonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gbCommonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(gbCommonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gbCommonLayout.createSequentialGroup()
                        .addComponent(lblTolerance)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfTolerance))
                    .addGroup(gbCommonLayout.createSequentialGroup()
                        .addComponent(chbIgnoreNoData)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        gbCommonLayout.setVerticalGroup(
            gbCommonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gbCommonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(gbCommonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTolerance)
                    .addComponent(tfTolerance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chbIgnoreNoData)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        GIS.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                GISMouseWheelMoved(evt);
            }
        });
        GIS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                GISMouseClicked(evt);
            }
        });

        gbPolygon.setBorder(javax.swing.BorderFactory.createTitledBorder("Grid to polygon"));

        chkSplitShapes.setSelected(true);
        chkSplitShapes.setText("Split shapes");

        btnGridToPolygon.setText("Generate");
        btnGridToPolygon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGridToPolygonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout gbPolygonLayout = new javax.swing.GroupLayout(gbPolygon);
        gbPolygon.setLayout(gbPolygonLayout);
        gbPolygonLayout.setHorizontalGroup(
            gbPolygonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gbPolygonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(gbPolygonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gbPolygonLayout.createSequentialGroup()
                        .addComponent(chkSplitShapes)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnGridToPolygon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        gbPolygonLayout.setVerticalGroup(
            gbPolygonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gbPolygonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chkSplitShapes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGridToPolygon)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gbPoint.setBorder(javax.swing.BorderFactory.createTitledBorder("Grid to point"));

        btnGridToPoint.setText("Generate");
        btnGridToPoint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGridToPointActionPerformed(evt);
            }
        });

        lblPointSpacing.setText("Point spacing:");

        javax.swing.GroupLayout gbPointLayout = new javax.swing.GroupLayout(gbPoint);
        gbPoint.setLayout(gbPointLayout);
        gbPointLayout.setHorizontalGroup(
            gbPointLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gbPointLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(gbPointLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnGridToPoint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(gbPointLayout.createSequentialGroup()
                        .addComponent(lblPointSpacing)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfPointSpacing)))
                .addContainerGap())
        );
        gbPointLayout.setVerticalGroup(
            gbPointLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gbPointLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(gbPointLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPointSpacing)
                    .addComponent(tfPointSpacing, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGridToPoint)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(gbPolygon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(gbCommon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(gbLoadData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(gbPoint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(GIS_Attr, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(gbLoadData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(gbCommon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(gbPolygon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(gbPoint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GIS_Attr, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLandCoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLandCoverActionPerformed
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "\\World\\Countries\\Luxembourg\\CLC2018_CLC2018_V2018_20_Luxembourg.tif");
        tfTolerance.setText("1");
        tfPointSpacing.setText("1000");
    }//GEN-LAST:event_btnLandCoverActionPerformed

    private void btnDEMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDEMActionPerformed
        TGIS_LayerPixel lp;
        
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "\\Samples\\3D\\elevation.grd");
        lp = (TGIS_LayerPixel)GIS.Get("elevation");
        lp.GenerateRamp(
          TGIS_Color.getBlue(),
          TGIS_Color.getLime(),
          TGIS_Color.getRed(),
          lp.getMinHeight(),
          (lp.getMinHeight() + lp.getMaxHeight()) / 2,
          lp.getMaxHeight(),
          true,
          (lp.getMaxHeight() - lp.getMinHeight()) / 100,
          (lp.getMaxHeight() - lp.getMinHeight()) / 10,
          null,
          true,
          TGIS_ColorInterpolationMode.HSL
        );
        GIS.InvalidateWholeMap();
            
        tfTolerance.setText("10");
        tfPointSpacing.setText("200");
    }//GEN-LAST:event_btnDEMActionPerformed

    private void GISMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GISMouseClicked
        TGIS_Shape shp;

        if (GIS.getIsEmpty())
        {
            return;
        }

        shp = (TGIS_Shape)GIS.Locate(GIS.ScreenToMap(new TPoint(evt.getX(), evt.getY())), 5 / GIS.getZoom());

        if(shp == null)
        {
            return;
        }

        shp.getLayer().DeselectAll();
        shp.setIsSelected(!shp.getIsSelected());

        GIS_Attr.ShowShape(shp);
    }//GEN-LAST:event_GISMouseClicked

    private void GISMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_GISMouseWheelMoved
            if (GIS.getIsEmpty()) return;

            if (evt.getWheelRotation() < 0) {
                GIS.ZoomBy(2 / 1.0, evt.getX(), evt.getY());
            } else {
                GIS.ZoomBy(1 / 2.0, evt.getX(), evt.getY());
            }
    }//GEN-LAST:event_GISMouseWheelMoved

    private void btnGridToPolygonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGridToPolygonActionPerformed
        TGIS_LayerPixel lp;
        TGIS_LayerVector lv;
        TGIS_GridToPolygon polygonizer;

        lp = (TGIS_LayerPixel)GIS.getItems().getItems(0);

        if(GIS.Get(LV_NAME) != null)
        {
            GIS.Delete(LV_NAME);
        }

        lv = new TGIS_LayerVector();
        lv.setName(LV_NAME);
        lv.Open();
        lv.setCS(lp.getCS());
        lv.setDefaultShapeType(TGIS_ShapeType.Polygon);
        lv.AddField(LV_FIELD, TGIS_FieldType.Float, 0, 0);
        lv.setTransparency(50);
        lv.getParams().getArea().setOutlineColor(TGIS_Color.getBlack());

        polygonizer = new TGIS_GridToPolygon();
        polygonizer.setTolerance(Float.parseFloat(tfTolerance.getText() ));
        polygonizer.setIgnoreNoData(chbIgnoreNoData.isSelected());
        
        polygonizer.setSplitShapes(chkSplitShapes.isSelected());
        polygonizer.setBusyEvent(new TGIS_BusyEvent() {
            @Override
            public void Invoke(Object o, TGIS_BusyEventArgs e) {
                switch ((int)e.getPos())
                {
                    case 0:
                        pProgressBar.setMinimum(0);
                        pProgressBar.setMaximum((int)e.getEndPos());
                        pProgressBar.setValue((int)e.getPos());
                        break;
                    case -1:
                        pProgressBar.setValue(0);
                        break;
                    default:
                        pProgressBar.setValue((int)e.getPos());
                        break;

                }
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                polygonizer.Generate(lp, lv, LV_FIELD);

                GIS.Add(lv);
                GIS.InvalidateWholeMap();
            }
        }).start();
    }//GEN-LAST:event_btnGridToPolygonActionPerformed

    private void btnGridToPointActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGridToPointActionPerformed
        TGIS_LayerPixel lp;
        TGIS_LayerVector lv;
        TGIS_GridToPoint grid_to_point;

        lp = (TGIS_LayerPixel)GIS.getItems().getItems(0);

        if(GIS.Get(LV_NAME) != null)
        {
            GIS.Delete(LV_NAME);
        }

        lv = new TGIS_LayerVector();
        lv.setName(LV_NAME);
        lv.Open();
        lv.setCS(lp.getCS());
        lv.setDefaultShapeType(TGIS_ShapeType.Point);
        lv.AddField(LV_FIELD, TGIS_FieldType.Float, 0, 0);
        lv.getParams().getMarker().setColor(TGIS_Color.getBlack());
        lv.getParams().getMarker().setStyle(TGIS_MarkerStyle.Circle);
        lv.getParams().getMarker().setSizeAsText("SIZE:4pt");

        grid_to_point = new TGIS_GridToPoint();
        grid_to_point.setTolerance(Float.parseFloat(tfTolerance.getText() ));
        grid_to_point.setIgnoreNoData(chbIgnoreNoData.isSelected());
        grid_to_point.setPointSpacing(Float.parseFloat(tfPointSpacing.getText()));
        grid_to_point.setBusyEvent(new TGIS_BusyEvent() {
            @Override
            public void Invoke(Object o, TGIS_BusyEventArgs e) {
                switch ((int)e.getPos())
                {
                    case 0:
                        pProgressBar.setMinimum(0);
                        pProgressBar.setMaximum((int)e.getEndPos());
                        pProgressBar.setValue((int)e.getPos());
                        break;
                    case -1:
                        pProgressBar.setValue(0);
                        break;
                    default:
                        pProgressBar.setValue((int)e.getPos());
                        break;

                }
            }
        });
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                grid_to_point.Generate(lp, lv, LV_FIELD);

                GIS.Add(lv);
                GIS.InvalidateWholeMap();
            }
        }).start();
    }//GEN-LAST:event_btnGridToPointActionPerformed
    
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
            java.util.logging.Logger.getLogger(GridToVectorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GridToVectorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GridToVectorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GridToVectorUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GridToVectorUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private tatukgis.jdk.TGIS_ControlAttributes GIS_Attr;
    private javax.swing.JButton btnDEM;
    private javax.swing.JButton btnGridToPoint;
    private javax.swing.JButton btnGridToPolygon;
    private javax.swing.JButton btnLandCover;
    private javax.swing.JCheckBox chbIgnoreNoData;
    private javax.swing.JCheckBox chkSplitShapes;
    private javax.swing.JPanel gbCommon;
    private javax.swing.JPanel gbLoadData;
    private javax.swing.JPanel gbPoint;
    private javax.swing.JPanel gbPolygon;
    private javax.swing.JLabel lblPointSpacing;
    private javax.swing.JLabel lblTolerance;
    private javax.swing.JProgressBar pProgressBar;
    private javax.swing.JTextField tfPointSpacing;
    private javax.swing.JTextField tfTolerance;
    // End of variables declaration//GEN-END:variables
    private final String LV_FIELD = "value";
    private final String LV_NAME = "polygons";
}
