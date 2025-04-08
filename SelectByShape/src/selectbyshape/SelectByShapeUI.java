package selectbyshape;

import java.awt.Toolkit;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import tatukgis.jdk.TGIS_BrushStyle;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_Lock;
import tatukgis.jdk.TGIS_PenStyle;
import tatukgis.jdk.TGIS_Point;
import tatukgis.jdk.TGIS_RendererAbstract;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_ShapeType;
import tatukgis.jdk.TGIS_Topology;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.jdk.TGIS_ViewerMode;
import tatukgis.rtl.TPoint;
import tatukgis.rtl.TRect;

public class SelectByShapeUI extends javax.swing.JFrame {

    public SelectByShapeUI() {
        initComponents();
        initOwn();
    }

    private void initOwn(){
        GIS.Lock();
        GIS.setMode(TGIS_ViewerMode.Select);
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/USA/States/California/Counties.shp");
        ll = new TGIS_LayerVector();
        ll.getParams().getArea().setColor(TGIS_Color.getBlue());
        ll.setTransparency(50);
        ll.setName("Points");
        ll.setCS(GIS.getCS());
        GIS.Add(ll);

        ll = new TGIS_LayerVector();
        ll.getParams().getArea().setColor(TGIS_Color.getBlue());
        ll.getParams().getArea().setOutlineColor(TGIS_Color.getBlue());
        ll.setTransparency(60);
        ll.setName("Buffers");
        ll.setCS(GIS.getCS());
        GIS.Add(ll);
        GIS.Unlock();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        btnCircle = new javax.swing.JToggleButton();
        btnRect = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SelectByShape - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(800, 600));

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        GIS.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                GISMouseDragged(evt);
            }
        });
        GIS.addPaintListener(new tatukgis.jdk.PaintListener() {
            public void PaintExtra(tatukgis.jdk.RenderEvent evt) {
                GISPaintExtra(evt);
            }
            public void BeforePaint(tatukgis.jdk.PaintEvent evt) {
            }
            public void BeforePaintRenderer(tatukgis.jdk.RenderEvent evt) {
            }
            public void AfterPaint(tatukgis.jdk.PaintEvent evt) {
            }
            public void AfterPaintRenderer(tatukgis.jdk.RenderEvent evt) {
            }
        });
        GIS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                GISMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                GISMouseReleased(evt);
            }
        });

        btnCircle.setText("By circle");
        btnCircle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCircleActionPerformed(evt);
            }
        });

        btnRect.setText("By rectangle");
        btnRect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCircle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRect)
                        .addGap(0, 424, Short.MAX_VALUE))
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCircle)
                    .addComponent(btnRect))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void GISMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GISMousePressed
        if(GIS.getIsEmpty()) return;
        
        if(!(btnCircle.isSelected() || btnRect.isSelected())){
            JOptionPane.showMessageDialog(this,
                "Choose selection method first");
            return;
        }
        
        if(SwingUtilities.isRightMouseButton(evt)){
            GIS.setMode(TGIS_ViewerMode.Zoom);
            return;
        }
        oldpos = new TPoint(evt.getX(), evt.getY());
        oldpos2 = new TPoint(evt.getX(), evt.getY());
        oldRadius = 0 ;
    }//GEN-LAST:event_GISMousePressed

    private void GISMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GISMouseReleased
        TGIS_Topology tpl;
        TGIS_LayerVector ll;
        TGIS_Shape tmp;
        TGIS_Shape buf;
        TGIS_Point ptg  = new TGIS_Point();
        TGIS_Point ptg1 = new TGIS_Point();
        TGIS_Point ptg2 = new TGIS_Point();
        double distance;
        String info;
        
        if(GIS.getIsEmpty()) return;
        
        if(!(btnCircle.isSelected() || btnRect.isSelected())){
            JOptionPane.showMessageDialog(this,
                "Choose selection method first");
            return;
        }
                
        if(SwingUtilities.isRightMouseButton(evt)){
            GIS.setMode(TGIS_ViewerMode.Select);
            return;        
        }
        if(btnRect.isSelected())
            if((oldpos2.X == oldpos.X) && (oldpos2.Y == oldpos.Y))
                return;
        if(btnCircle.isSelected())
            if(oldRadius == 0)
                return;
        
        ll = (TGIS_LayerVector)GIS.Get("Points");
        ll.Lock();
        
        tmp = ll.CreateShape(TGIS_ShapeType.Point);

        
        if(btnCircle.isSelected()){
            ptg = GIS.ScreenToMap(oldpos);
            tmp = ll.CreateShape(TGIS_ShapeType.Point);
            tmp.getParams().getMarker().setSize(0);
            tmp.Lock(TGIS_Lock.Extent);
            tmp.AddPart();
            tmp.AddPoint(ptg);
            tmp.Unlock();
            ll.Unlock();
            ptg1 = GIS.ScreenToMap(new TPoint(oldpos.X + (int)oldRadius, evt.getY()));

        }
        if(btnRect.isSelected()){
            ptg = GIS.ScreenToMap(oldpos);
            tmp = ll.CreateShape(TGIS_ShapeType.Point);
            tmp.getParams().getMarker().setSize(0);
            tmp.Lock(TGIS_Lock.Extent);
            tmp.AddPart();
            tmp.AddPoint(ptg);
            tmp.Unlock();
            ptg = GIS.ScreenToMap(oldpos2);
            tmp.AddPoint(ptg);
            ll.Unlock();
            ptg1 = GIS.ScreenToMap(oldpos);            
        }
        buf = ll.CreateShape(TGIS_ShapeType.Unknown);
        
        ll = (TGIS_LayerVector)GIS.Get("Buffers");
        ll.RevertShapes();
        
        if(btnCircle.isSelected()){
            distance = ptg1.X - ptg.X;
            tpl = new TGIS_Topology();
            buf = tpl.MakeBuffer(tmp, distance / 2, 32, true);
            buf = ll.AddShape(buf);
        }
        
        if(btnRect.isSelected()){
            ptg2 = GIS.ScreenToMap(oldpos2);
            buf = ll.CreateShape(TGIS_ShapeType.Polygon);
            buf.AddPart();
            buf.AddPoint(ptg1);
            buf.AddPoint(TGIS_Utils.GisPoint(ptg1.X, ptg2.Y));
            buf.AddPoint(ptg2);
            buf.AddPoint(TGIS_Utils.GisPoint(ptg2.X, ptg1.Y));
        }
        
        ll = (TGIS_LayerVector)GIS.getItems().getItems(0);
        
        if(ll == null){
            GIS.InvalidateWholeMap();
            return;
        }
        
        ll.DeselectAll();
        
        info = "";
        
        GIS.InvalidateWholeMap();
        GIS.Lock();
        tmp = ll.FindFirst(buf.getExtent(), "", buf, TGIS_Utils.GIS_RELATE_INTERSECT());
        while(tmp != null){
            info += tmp.GetField("name").toString() + "\n";
            tmp.setIsSelected(true);
            tmp = ll.FindNext();
        }
        jTextArea1.setText(info);
        GIS.Unlock();
    }//GEN-LAST:event_GISMouseReleased

    private void GISPaintExtra(tatukgis.jdk.RenderEvent evt) {//GEN-FIRST:event_GISPaintExtra
        TGIS_RendererAbstract rdr;
        Random rand;
        TRect rect;
        rand = new Random();
        rdr = evt.getRenderer();
        rdr.getCanvasPen().Width = 1;
        rdr.getCanvasPen().Color = TGIS_Color.FromBGR(rand.nextInt(16581375));
        rdr.getCanvasPen().Style = TGIS_PenStyle.Solid;
        rdr.getCanvasBrush().Style = TGIS_BrushStyle.Clear;
        
        if(btnRect.isSelected()){
            if((oldpos.X == oldpos2.X) && (oldpos.Y == oldpos2.Y)) return;
            rect = new TRect();
            rect.Bottom = oldpos.Y;
            rect.Top = oldpos2.Y;
            rect.Left = oldpos.X;
            rect.Right = oldpos2.X;
            rdr.CanvasDrawRectangle(rect);
        }
        
        if(btnCircle.isSelected()){
            rdr.CanvasDrawEllipse(oldpos.X - (int)Math.round(oldRadius / 2), oldpos.Y - (int)Math.round(oldRadius / 2), (int)oldRadius, (int)oldRadius);
        }
    }//GEN-LAST:event_GISPaintExtra

    private void GISMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GISMouseDragged
        if(GIS.getIsEmpty()) return;
        
        if(GIS.getMode() != TGIS_ViewerMode.Select) return;
        if(!SwingUtilities.isLeftMouseButton(evt)) return;
        
        if(btnRect.isSelected())
            oldpos2 = new TPoint(evt.getX(), evt.getY());
        if(btnCircle.isSelected())
            oldRadius = (int)Math.round(Math.sqrt(Math.pow(oldpos.X - evt.getX(), 2) + Math.pow(oldpos.Y - evt.getY(), 2)));
        GIS.invalidate();
    }//GEN-LAST:event_GISMouseDragged

    private void btnCircleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCircleActionPerformed
        if(btnCircle.isSelected())
            btnRect.setSelected(!btnCircle.isSelected()) ;
    }//GEN-LAST:event_btnCircleActionPerformed

    private void btnRectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRectActionPerformed
        if(btnRect.isSelected())
            btnCircle.setSelected(!btnRect.isSelected()) ;
    }//GEN-LAST:event_btnRectActionPerformed

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
            java.util.logging.Logger.getLogger(SelectByShapeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SelectByShapeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SelectByShapeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SelectByShapeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SelectByShapeUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.JToggleButton btnCircle;
    private javax.swing.JToggleButton btnRect;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
    private double oldRadius;
    private int oldZoom;
    private TGIS_Color oldColor;
    private TGIS_LayerVector ll;
    private TPoint oldpos;
    private TPoint oldpos2;
}
