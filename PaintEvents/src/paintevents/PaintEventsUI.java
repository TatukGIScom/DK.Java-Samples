package paintevents;

import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import tatukgis.jdk.PaintEvent;
import tatukgis.jdk.PaintListener;
import tatukgis.jdk.RenderEvent;
import tatukgis.jdk.TGIS_BrushStyle;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_FrameworkUtils;
import tatukgis.jdk.TGIS_LayerSHP;
import tatukgis.jdk.TGIS_Point;
import tatukgis.jdk.TGIS_RendererAbstract;
import tatukgis.jdk.TGIS_ViewerMode;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.rtl.TPoint;
import tatukgis.rtl.TRect;

public class PaintEventsUI extends javax.swing.JFrame {

    public PaintEventsUI() {        
        initComponents();
        initOwn();
    }
    
    private void initOwn(){
        TGIS_LayerSHP ll = new TGIS_LayerSHP();
        
        ll.setPath(TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/USA/States/California/Counties.shp") ;
        ll.getParams().getArea().setColor(TGIS_Color.getLightGray());
        GIS.Add(ll);
        GIS.FullExtent();
        center_ptg = GIS.getCenterPtg();
        
        GIS.addPaintListener(new PaintListener() {
            @Override
            public void BeforePaint(PaintEvent pe) {
                if(chkBeforePaint.isSelected()){
                    GIS_BeforePaint(pe);
                }
            }

            @Override
            public void BeforePaintRenderer(RenderEvent re) {
                if(chkBeforePaintRenderer.isSelected()){
                    GIS_BeforePaintRenderer(re);
                }
            }

            @Override
            public void AfterPaint(PaintEvent pe) {
                if(chkAfterPaint.isSelected()){
                    GIS_AfterPaint(pe);
                }
            }

            @Override
            public void AfterPaintRenderer(RenderEvent re) {
                if(chkAfterPaintRenderer.isSelected()){
                    GIS_AfterPaintRenderer(re);
                }
            }

            @Override
            public void PaintExtra(RenderEvent re) {
                if(chkPaintExtra.isSelected()){
                    GIS_PaintExtra(re);
                }
            }
        });
    };
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        jToolBar1 = new javax.swing.JToolBar();
        btnFullExtent = new javax.swing.JButton();
        btnZoomIn = new javax.swing.JButton();
        btnZoomOut = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        cbxDragging = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        chkBeforePaintRenderer = new javax.swing.JCheckBox();
        chkBeforePaint = new javax.swing.JCheckBox();
        chkPaintExtra = new javax.swing.JCheckBox();
        chkAfterPaint = new javax.swing.JCheckBox();
        btnPrintBmp = new javax.swing.JButton();
        chkPrintBmp = new javax.swing.JCheckBox();
        chkAfterPaintRenderer = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PaintEvents - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(800, 600));

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
        jToolBar1.add(jSeparator1);

        cbxDragging.setText("Dragging");
        cbxDragging.setFocusable(false);
        cbxDragging.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        cbxDragging.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cbxDragging.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxDraggingActionPerformed(evt);
            }
        });
        jToolBar1.add(cbxDragging);

        chkBeforePaintRenderer.setSelected(true);
        chkBeforePaintRenderer.setText("BeforePaintRendererEvent");
        chkBeforePaintRenderer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkChange(evt);
            }
        });

        chkBeforePaint.setSelected(true);
        chkBeforePaint.setText("BeforePaintEvent");
        chkBeforePaint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkChange(evt);
            }
        });

        chkPaintExtra.setSelected(true);
        chkPaintExtra.setText("PaintExtraEvent");
        chkPaintExtra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkChange(evt);
            }
        });

        chkAfterPaint.setSelected(true);
        chkAfterPaint.setText("AfterPaintEvent");
        chkAfterPaint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkChange(evt);
            }
        });

        btnPrintBmp.setText("PrintBmp");
        btnPrintBmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintBmpActionPerformed(evt);
            }
        });

        chkPrintBmp.setSelected(true);
        chkPrintBmp.setText("PrintBmp with events");

        chkAfterPaintRenderer.setSelected(true);
        chkAfterPaintRenderer.setText("AfterPaintRendererEvent");
        chkAfterPaintRenderer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkChange(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPrintBmp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkBeforePaintRenderer)
                            .addComponent(chkBeforePaint)
                            .addComponent(chkPaintExtra)
                            .addComponent(chkAfterPaint)
                            .addComponent(chkPrintBmp)
                            .addComponent(chkAfterPaintRenderer))
                        .addGap(0, 7, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chkBeforePaintRenderer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkBeforePaint)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkPaintExtra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkAfterPaint)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkAfterPaintRenderer)
                .addGap(33, 33, 33)
                .addComponent(btnPrintBmp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkPrintBmp)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbxDraggingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxDraggingActionPerformed
        if(cbxDragging.isSelected()) {
            GIS.setMode(TGIS_ViewerMode.Drag);
        } else {
            GIS.setMode(TGIS_ViewerMode.Select);
        }
    }//GEN-LAST:event_cbxDraggingActionPerformed

    private void chkChange(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkChange
        GIS.InvalidateWholeMap();
    }//GEN-LAST:event_chkChange

    private void btnPrintBmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintBmpActionPerformed
        JFileChooser fc = new JFileChooser();
        File file;
        
        int res = fc.showSaveDialog(this);
        if(res == JFileChooser.APPROVE_OPTION){
            BufferedImage bmp = GIS.PrintBmp(chkPrintBmp.isSelected());
            try{
                if(fc.getSelectedFile().getName().split(".")[1].equalsIgnoreCase("png")) {
                    file = fc.getSelectedFile();
                } else {
                    file = new File(fc.getSelectedFile().toString() + ".png");
                }
            }catch(ArrayIndexOutOfBoundsException ex){
                file = new File(fc.getSelectedFile().toString() + ".png");
            }
            try {
                ImageIO.write(bmp, "png", file);
            } catch (IOException ex) {
                Logger.getLogger(PaintEventsUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnPrintBmpActionPerformed

    
    private void GIS_BeforePaint(PaintEvent pe) {
        TGIS_Color bcl;
        bcl = TGIS_Color.getBlue();
        Graphics2D g = ((Graphics2D)pe.getCanvas());
        g.setColor(TGIS_FrameworkUtils.ToPlatformColor(bcl));
        g.drawRect(40, 40, GIS.getWidth() - 2 * 40, GIS.getHeight()- 2 * 40);
    }
   
    private void GIS_BeforePaintRenderer(RenderEvent re) {
        Object cn;
        TGIS_RendererAbstract rdr;
        TRect rect;
        TGIS_Color bcl;
        
        rdr = re.getRenderer();
        cn = rdr.CanvasNative();
        bcl = TGIS_Color.FromRGB((byte)0xEE, (byte)0xE8, (byte)0xAA);
        
        
        if (cn instanceof Graphics2D){
            ((Graphics2D)cn).setBackground(TGIS_FrameworkUtils.ToPlatformColor(bcl));
            ((Graphics2D)cn).clearRect(0,0, GIS.getWidth(), GIS.getHeight());
        }
        
        rdr.getCanvasPen().Color = TGIS_Color.getBlue();
        rdr.getCanvasBrush().Color = TGIS_Color.getWhite();
        rdr.getCanvasBrush().Style = TGIS_BrushStyle.Clear;
        rdr.getCanvasPen().Width = 1;
        rdr.getCanvasPen().Style = TGIS_BrushStyle.Clear;
        rect = new TRect();
        rect.Left = 10;
        rect.Top = 10;
        rect.Right = GIS.getWidth() - 2*10;
        rect.Bottom = GIS.getHeight()- 2*10;
        
        rdr.CanvasDrawRectangle(rect);
    }    
    
    private void GIS_PaintExtra(RenderEvent re) {
        String txt;
        TPoint pt;
        TPoint ptc;
        TRect rect;

        txt = "PaintExtra";
        re.getRenderer().getCanvasFont().Name = "Courier New";
        re.getRenderer().getCanvasFont().Size = 24;
        re.getRenderer().getCanvasFont().Color = TGIS_Color.getBlue();

        pt = re.getRenderer().CanvasTextExtent(txt);
        ptc = GIS.MapToScreen(center_ptg);
        rect = new TRect();

        rect.Left = ptc.X - pt.X / 2;
        rect.Top = ptc.Y - pt.Y / 2;
        rect.Right = ptc.X + pt.X / 2;
        rect.Bottom = ptc.Y + pt.Y / 2;

        re.getRenderer().CanvasDrawText(rect, txt);
    }

    
    private void GIS_AfterPaint(PaintEvent pe) {
        TGIS_Color bcl;

        bcl = TGIS_Color.getBlue();
        Graphics2D g = ((Graphics2D)pe.getCanvas());
        g.setColor(TGIS_FrameworkUtils.ToPlatformColor(bcl));
        g.drawRect(70, 70, GIS.getWidth() - 2 * 70, GIS.getHeight() - 2 * 70);
    }

    private void GIS_AfterPaintRenderer(RenderEvent re) {
        TGIS_RendererAbstract rdr;
        TRect rect = new TRect();

        rdr = re.getRenderer();
        rdr.getCanvasPen().Color = TGIS_Color.getBlue();
        rdr.getCanvasPen().Width = 1;
        rdr.getCanvasBrush().Style = TGIS_BrushStyle.Clear;
        
        rect.Left = 100;
        rect.Top = 100;
        rect.Right = GIS.getWidth() - 2 * 50;
        rect.Bottom = GIS.getHeight() - 2 * 50;
        
        rdr.CanvasDrawRectangle(rect);
    }
    
    private void btnFullExtentActionPerformed(java.awt.event.ActionEvent evt) {
        if(GIS.getIsEmpty())return;
        GIS.FullExtent();
    }

    private void btnZoomInActionPerformed(java.awt.event.ActionEvent evt) {
        if(GIS.getIsEmpty()) return;
        GIS.setZoom(GIS.getZoom() * 2);
    }

    private void btnZoomOutActionPerformed(java.awt.event.ActionEvent evt) {
        if(GIS.getIsEmpty()) return;
        GIS.setZoom(GIS.getZoom() / 2);
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
            java.util.logging.Logger.getLogger(PaintEventsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PaintEventsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PaintEventsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PaintEventsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PaintEventsUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.JButton btnFullExtent;
    private javax.swing.JButton btnPrintBmp;
    private javax.swing.JButton btnZoomIn;
    private javax.swing.JButton btnZoomOut;
    private javax.swing.JCheckBox cbxDragging;
    private javax.swing.JCheckBox chkAfterPaint;
    private javax.swing.JCheckBox chkAfterPaintRenderer;
    private javax.swing.JCheckBox chkBeforePaint;
    private javax.swing.JCheckBox chkBeforePaintRenderer;
    private javax.swing.JCheckBox chkPaintExtra;
    private javax.swing.JCheckBox chkPrintBmp;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
    
    private TGIS_Point center_ptg;
}
