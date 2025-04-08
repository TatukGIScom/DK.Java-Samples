package custompaint;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import tatukgis.jdk.TGIS_Bitmap;
import tatukgis.jdk.TGIS_BitmapFormat;
import tatukgis.jdk.TGIS_BitmapLinesOrder;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_FieldType;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_Lock;
import tatukgis.jdk.TGIS_RendererSwing;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_ShapeEvent;
import tatukgis.jdk.TGIS_ShapeEventArgs;
import tatukgis.jdk.TGIS_ShapeType;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.rtl.TPoint;
import tatukgis.rtl.TRect;

public class CustomPaintUI extends javax.swing.JFrame {

    public CustomPaintUI() {
        initComponents();
        initOwn();
    }
    private void initialize_pixels()
    {
        px = new int[25];

        px[0] = 0xFFFF0000;
        px[1] = 0xFFFF0000;
        px[2] = 0xFFFF0000;
        px[3] = 0xFFFF0000;
        px[4] = 0xFFFF0000;
        px[5] = 0x00000000;
        px[6] = 0x00000000;
        px[7] = 0xFF0000FF;
        px[8] = 0x00000000;
        px[9] = 0x00000000;
        px[10] = 0x00000000;
        px[11] = 0x00000000;
        px[12] = 0xFF0000FF;
        px[14] = 0x00000000;
        px[15] = 0x00000000;
        px[16] = 0x00000000;
        px[17] = 0xFF0000FF;
        px[18] = 0x00000000;
        px[19] = 0x00000000;
        px[20] = 0x00000000;
        px[21] = 0x00000000;
        px[22] = 0xFF0000FF;
        px[23] = 0x00000000;
        px[23] = 0x00000000;
    }
        
    private void initOwn(){
        TGIS_Shape shp;

        initialize_pixels();
        
        ll = new TGIS_LayerVector();
        ll.setName("CustomPaint");

        GIS.Add(ll);

        ll.addPaintShapeEvent(new TGIS_ShapeEvent() {
            @Override
            public void Invoke(Object o, TGIS_ShapeEventArgs tgs) {
                TPoint pt;
                TRect rect;
                TRect rect2;

                rect = new TRect();
                rect2 = new TRect();
                pt = GIS.MapToScreen(tgs.getShape().PointOnShape());
                rect.Left = pt.X;
                rect.Top = pt.Y;
                rect.Right = pt.X + 20;
                rect.Bottom = pt.Y + 20;

                rect2.Left = pt.X;
                rect2.Top = pt.Y - 20;
                rect2.Right = pt.X + 50;
                rect2.Bottom = pt.Y - 20;
                tgs.getShape().getLayer().getRenderer();

                //Drawing with our renderer
                if ("Rectangle".equals(tgs.getShape().GetField("type")))
                {
                    GIS.getRenderer().getCanvasPen().Color = TGIS_Color.getRed();
                    GIS.getRenderer().getCanvasBrush().Color = TGIS_Color.getYellow();
                    GIS.getRenderer().CanvasDrawRectangle(rect);
                    pt.Y -= 20;
                    GIS.getRenderer().CanvasDrawText(rect2, "Rectangle");
                }
                else if ("Ellipse".equals(tgs.getShape().GetField("type")))
                {
                    GIS.getRenderer().getCanvasPen().Color = TGIS_Color.getBlack();
                    GIS.getRenderer().getCanvasBrush().Color = TGIS_Color.getRed();                   
                    GIS.getRenderer().CanvasDrawEllipse(pt.X, pt.Y, 20, 20);
                    pt.Y -= 20;
                    GIS.getRenderer().CanvasDrawText(rect2, "Ellipse");
                }
                else if ("Image1".equals(tgs.getShape().GetField("type")))
                {
                    GIS.getRenderer().CanvasDrawBitmap(
                    bmp,
                    rect);
                    pt.Y -= 20;
                    GIS.getRenderer().CanvasDrawText(rect2, "Image1");
                }
                else if ("Image2".equals(tgs.getShape().GetField("type")))
                {
                    GIS.getRenderer().CanvasDrawBitmap(
                      px,
                      new TPoint(5, 5),
                      rect,
                      TGIS_BitmapFormat.ARGB,
                      TGIS_BitmapLinesOrder.Down
                    );
                    pt.Y -= 20;
                    GIS.getRenderer().CanvasDrawText(rect2, "Image2");
                }
            }
        });

        
        ll.AddField("type", TGIS_FieldType.String, 100, 0);

        shp = ll.CreateShape(TGIS_ShapeType.Point);
        shp.Lock(TGIS_Lock.Extent);
        shp.AddPart();
        shp.AddPoint(TGIS_Utils.GisPoint(-25, 25));
        shp.getParams().getMarker().setSize(0);
        shp.SetField("type", "Rectangle");
        shp.Unlock();

        shp = ll.CreateShape(TGIS_ShapeType.Point);
        shp.Lock(TGIS_Lock.Extent);
        shp.AddPart();
        shp.AddPoint(TGIS_Utils.GisPoint(25, 25));
        shp.getParams().getMarker().setSize(0);
        shp.SetField("type", "Ellipse");
        shp.Unlock();

        shp = ll.CreateShape(TGIS_ShapeType.Point);
        shp.Lock(TGIS_Lock.Extent);
        shp.AddPart();
        shp.AddPoint(TGIS_Utils.GisPoint(-25, -25));
        shp.getParams().getMarker().setSize(0);
        shp.SetField("type", "Image1");
        shp.Unlock();

        shp = ll.CreateShape(TGIS_ShapeType.Point);
        shp.Lock(TGIS_Lock.Extent);
        shp.AddPart();
        shp.AddPoint(TGIS_Utils.GisPoint(25, -25));
        shp.getParams().getMarker().setSize(0);
        shp.SetField("type", "Image2");
        shp.Unlock();

        ll.setExtent(TGIS_Utils.GisExtent(-100, -100, 100, 100)) ;

        bmp = new TGIS_Bitmap();
        bmp.LoadFromFile(TGIS_Utils.GisSamplesDataDirDownload() + "/Symbols/police.bmp");

        GIS.FullExtent();        
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CustomPaint - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(800, 600));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void GISPaintExtra(tatukgis.jdk.RenderEvent evt) {//GEN-FIRST:event_GISPaintExtra
        Graphics2D grp;
      
        // drawing with native objects, not recommended        
        if( evt.getRenderer() instanceof TGIS_RendererSwing){
            grp = (Graphics2D)GIS.getRenderer().CanvasNative();

            grp.setColor(Color.BLUE);
            grp.setFont(new Font("Arial", Font.PLAIN, 12));
            grp.drawString("Hello from Graphics2D", 50, 50);
        }else{
            //other renderer
        }
    }//GEN-LAST:event_GISPaintExtra

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
            java.util.logging.Logger.getLogger(CustomPaintUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CustomPaintUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CustomPaintUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CustomPaintUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CustomPaintUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    // End of variables declaration//GEN-END:variables
    private TGIS_LayerVector ll;
    private TGIS_Bitmap bmp;
    private int[] px;
    
}
