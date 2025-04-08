package ShapeOperations;

import java.awt.Toolkit;
import tatukgis.jdk.TGIS_BrushStyle;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_Point;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.jdk.TGIS_ViewerMode;
import tatukgis.rtl.TPoint;

public class ShapeOperationsUI extends javax.swing.JFrame {

    public ShapeOperationsUI() {
        initComponents();
        initOwn();
    }

    private void initOwn(){
        currShape = null;
        edtShape = null;
        
        handleMouseMove = false;
        GIS.Lock();
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/Samples/3D/buildings.shp");
        
        edtLayer = new TGIS_LayerVector() ;
        edtLayer.setCS(GIS.getCS()) ;
        edtLayer.setCachedPaint( false ) ; // make tracking layer
        edtLayer.getParams().getArea().setPattern(TGIS_BrushStyle.Clear) ;
        edtLayer.getParams().getArea().setOutlineColor(TGIS_Color.getRed()) ;
        GIS.Add( edtLayer );
            
        GIS.Unlock();
        GIS.setZoom(GIS.getZoom() * 4);
        GIS.setMode(TGIS_ViewerMode.Select);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        jToolBar1 = new javax.swing.JToolBar();
        rbRotate = new javax.swing.JRadioButton();
        rbScale = new javax.swing.JRadioButton();
        rbMove = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        lblSelected = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ShapeTransform - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(800, 600));

        GIS.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                GISMouseMoved(evt);
            }
        });
        GIS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                GISMouseReleased(evt);
            }
        });

        jToolBar1.setRollover(true);

        buttonGroup1.add(rbRotate);
        rbRotate.setSelected(true);
        rbRotate.setText("Rotate");
        rbRotate.setFocusable(false);
        rbRotate.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rbRotate.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rbRotate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbRotateActionPerformed(evt);
            }
        });
        jToolBar1.add(rbRotate);

        buttonGroup1.add(rbScale);
        rbScale.setText("Scale");
        rbScale.setFocusable(false);
        rbScale.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rbScale.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rbScale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbScaleActionPerformed(evt);
            }
        });
        jToolBar1.add(rbScale);

        buttonGroup1.add(rbMove);
        rbMove.setText("Move");
        rbMove.setFocusable(false);
        rbMove.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rbMove.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rbMove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbMoveActionPerformed(evt);
            }
        });
        jToolBar1.add(rbMove);
        jToolBar1.add(jSeparator1);

        lblSelected.setText("Select shape to start rotating");
        jToolBar1.add(lblSelected);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void GISMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GISMouseMoved
        TGIS_Point ptg;
        
        if(edtShape == null) return;
        
        if(handleMouseMove){
            ptg = GIS.ScreenToMap(new TPoint(evt.getX(), evt.getY()));
            
            if(rbRotate.isSelected())
                RotateSelectedShape(edtShape, Math.toRadians(evt.getX() - prevX));
            else if (rbScale.isSelected())
                ScaleSelectedShape(edtShape, evt.getX() / (double)prevX, evt.getY() / (double)prevY);
            else if(rbMove.isSelected())
                TranslateSelectedShape(edtShape, (ptg.X - prevPtg.X), ptg.Y - prevPtg.Y);
            
        prevPtg = ptg;
        prevX = evt.getX();
        prevY = evt.getY();
        }
    }//GEN-LAST:event_GISMouseMoved

    private void GISMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GISMouseReleased
        TGIS_Point ptg;
        TGIS_Shape shp;
        
        lblSelected.setText("No selected shape. Select shape");
        
        if (currShape != null)
        {
            currShape.CopyGeometry( edtShape );
            edtLayer.RevertAll() ;
            currShape = null ;
            edtShape = null;
            GIS.InvalidateWholeMap() ;                
            handleMouseMove = false;
            return;
        }
        
        if( GIS.getIsEmpty() ) return;
        if( GIS.getMode() != TGIS_ViewerMode.Select) return;
        
        if ( handleMouseMove ){
            GIS.InvalidateWholeMap();
            currShape = null;
            handleMouseMove = false;
            
            if (rbRotate.isSelected())
                lblSelected.setText("Select shape to start rotating");
            else if (rbScale.isSelected())
                lblSelected.setText("Select shape to start scaling");
            else if (rbMove.isSelected())
                lblSelected.setText("Select shape to start moving");
            
            return;
        }
        
        ptg = GIS.ScreenToMap(new TPoint(evt.getX(), evt.getY()));
        
        shp = (TGIS_Shape) GIS.Locate(ptg,  5 / GIS.getZoom());
        
        if ( shp == null ) return;
        
        currShape = shp.MakeEditable();
        edtShape = edtLayer.AddShape( currShape.CreateCopy() ) ;
          
        lblSelected.setText("Selected shape : " + currShape.getUid() + ". Click to commit changes");
        
        prevPtg = ptg;
        prevX = evt.getX();
        prevY = evt.getY();
        
        handleMouseMove = !handleMouseMove;
    }//GEN-LAST:event_GISMouseReleased

    private void rbRotateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbRotateActionPerformed
        lblSelected.setText("Select shape to start rotating");

        if (currShape != null)
        {
            GIS.InvalidateTopmost();
            currShape = null;
            handleMouseMove = false;
        }
    }//GEN-LAST:event_rbRotateActionPerformed

    private void rbScaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbScaleActionPerformed
        lblSelected.setText("Select shape to start scaling");

        if (currShape != null)
        {
            GIS.InvalidateTopmost();
            currShape = null;
            handleMouseMove = false;
        }
    }//GEN-LAST:event_rbScaleActionPerformed

    private void rbMoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbMoveActionPerformed
        lblSelected.setText("Select shape to start moving");
        
        if (currShape != null)
        {
            GIS.InvalidateTopmost();
            currShape = null;
            handleMouseMove = false;
        }
    }//GEN-LAST:event_rbMoveActionPerformed

    private void TransformSelectedShape(TGIS_Shape shape, double xx, double yx, double xy, double yy, double dx, double dy){
        TGIS_Point centroid;
        
        if(shape == null) return;
        
        centroid = shape.Centroid();
        
        shape.Transform(    TGIS_Utils.GisPoint3DFrom2D(centroid),
                            xx, yx, 0,
                            xy, yy, 0,
                            0 , 0 , 1,
                            dx, dy, 0,
                            false                                   
                        );
        GIS.InvalidateWholeMap();
    }
    
    private void RotateSelectedShape(TGIS_Shape shape, double angle){
        TransformSelectedShape(shape, Math.cos(angle), Math.sin(angle), -Math.sin(angle), Math.cos(angle), 0, 0);
    }
    
    private void ScaleSelectedShape(TGIS_Shape shape, double x, double y){
        TransformSelectedShape(shape, x, 0, 0, y, 0, 0);
    }
    
    private void TranslateSelectedShape(TGIS_Shape shape, double x, double y){
        TransformSelectedShape(shape, 1, 0, 0, 1, x, y);
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
            java.util.logging.Logger.getLogger(ShapeOperationsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ShapeOperationsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ShapeOperationsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ShapeOperationsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        java.awt.EventQueue.invokeLater(new Runnable() {      
        public void run() {
                new ShapeOperationsUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblSelected;
    private javax.swing.JRadioButton rbMove;
    private javax.swing.JRadioButton rbRotate;
    private javax.swing.JRadioButton rbScale;
    // End of variables declaration//GEN-END:variables
    private TGIS_LayerVector edtLayer;
    private TGIS_Shape currShape;
    private TGIS_Shape edtShape;
    private int prevX, prevY;
    private TGIS_Point prevPtg;
    private boolean handleMouseMove;
    
    
}
