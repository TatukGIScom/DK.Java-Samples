package pixeledit;

import RemObjects.Elements.System.VarParameter;
import java.awt.Toolkit;
import java.util.Random;
import tatukgis.jdk.TGIS_CSFactory;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_LayerGRD;
import tatukgis.jdk.TGIS_LayerJPG;
import tatukgis.jdk.TGIS_LayerPixel;
import tatukgis.jdk.TGIS_LayerPixelLock;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_MarkerStyle;
import tatukgis.jdk.TGIS_PixelItem;
import tatukgis.jdk.TGIS_Point;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_ShapeType;
import tatukgis.jdk.TGIS_Utils;

public class PixelEditUI extends javax.swing.JFrame {
    
    public PixelEditUI() {
        //still missing legend control
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        jScrollPane1 = new javax.swing.JScrollPane();
        taInfo = new javax.swing.JTextArea();
        jToolBar1 = new javax.swing.JToolBar();
        btnProfile = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnMinMax = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnAvarageColor = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnNewJPG = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btnNewGRD = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        jScrollPane2 = new javax.swing.JScrollPane();
        tGIS_ControlLegend1 = new tatukgis.jdk.TGIS_ControlLegend();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PixelEdit - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(800, 600));

        taInfo.setColumns(20);
        taInfo.setRows(5);
        jScrollPane1.setViewportView(taInfo);

        jToolBar1.setRollover(true);

        btnProfile.setText("Terrain profile");
        btnProfile.setFocusable(false);
        btnProfile.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProfile.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProfileActionPerformed(evt);
            }
        });
        jToolBar1.add(btnProfile);
        jToolBar1.add(jSeparator3);

        btnMinMax.setText("Grid Min/Max");
        btnMinMax.setFocusable(false);
        btnMinMax.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMinMax.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMinMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMinMaxActionPerformed(evt);
            }
        });
        jToolBar1.add(btnMinMax);
        jToolBar1.add(jSeparator2);

        btnAvarageColor.setText("Bitmap average color");
        btnAvarageColor.setFocusable(false);
        btnAvarageColor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAvarageColor.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAvarageColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAvarageColorActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAvarageColor);
        jToolBar1.add(jSeparator1);

        btnNewJPG.setText("Create new JPG");
        btnNewJPG.setFocusable(false);
        btnNewJPG.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNewJPG.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNewJPG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewJPGActionPerformed(evt);
            }
        });
        jToolBar1.add(btnNewJPG);
        jToolBar1.add(jSeparator4);

        btnNewGRD.setText("Create new GRD");
        btnNewGRD.setFocusable(false);
        btnNewGRD.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNewGRD.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNewGRD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewGRDActionPerformed(evt);
            }
        });
        jToolBar1.add(btnNewGRD);
        jToolBar1.add(jSeparator5);

        tGIS_ControlLegend1.setGIS_Viewer(GIS);
        jScrollPane2.setViewportView(tGIS_ControlLegend1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfileActionPerformed
        TGIS_LayerPixel lp;
        TGIS_LayerVector lv;
        TGIS_Shape shp;
        TGIS_PixelItem px;
        String info;
        
        info = "";
        taInfo.setText(info);
        
        GIS.Lock();
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/Samples/PixelEdit/grid.ttkproject");
        
        lp = (TGIS_LayerPixel) GIS.Get("elevation");
        lv = (TGIS_LayerVector) GIS.Get("line");
        shp = lv.GetShape(1).MakeEditable();
        shp.setIsSelected(true);
        
        for(Object tmp : lp.Loop(0, shp, false)){
            px = (TGIS_PixelItem)tmp;
            info = info + "Distance: " + px.getDistance() + " Height: " + px.Value + "\n" ;
        }
        taInfo.setText(info);
        GIS.Unlock();
    }//GEN-LAST:event_btnProfileActionPerformed

    private void btnMinMaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMinMaxActionPerformed
        TGIS_LayerPixel lp;
        TGIS_LayerVector lv;
        TGIS_LayerVector lvtmp;
        TGIS_Shape shp;
        TGIS_Shape shptmp;
        TGIS_PixelItem px;
        double dmin, dmax;
        TGIS_Point ptmax, ptmin;
        String info;
        
        info = "";
        taInfo.setText(info);
        
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/Samples/PixelEdit/grid.ttkproject");
        
        lp = (TGIS_LayerPixel)GIS.Get("elevation");
        lv = (TGIS_LayerVector)GIS.Get("polygon");
        shp = lv.GetShape(1).MakeEditable();
        shp.setIsSelected(true);
        
        dmax = -1e38;
        dmin = 1e38;
        
        ptmin = new TGIS_Point();
        ptmax = new TGIS_Point();
        
        for(Object tmp : lp.Loop(shp.getExtent(), 0, shp, "T", false)){
            px = (TGIS_PixelItem)tmp;
            if(px.Value < dmin){
                dmin = px.Value;
                ptmin = px.getCenter();
            }
            if(px.Value > dmax){
                dmax = px.Value;
                ptmax = px.getCenter();
            }            
        }
        
        lvtmp = new TGIS_LayerVector();
        lvtmp.setCS(lp.getCS());
        GIS.Add(lvtmp);
        
        lvtmp.getParams().getMarker().setStyle(TGIS_MarkerStyle.Cross);
        lvtmp.getParams().getMarker().setSize(-10);
        lvtmp.getParams().getMarker().setColor(TGIS_Color.getBlack());
        
        shptmp = lvtmp.CreateShape(TGIS_ShapeType.Point);
        shptmp.AddPart();
        shptmp.AddPoint(ptmin);
                
        shptmp = lvtmp.CreateShape(TGIS_ShapeType.Point);
        shptmp.AddPart();
        shptmp.AddPoint(ptmax);        
        
        GIS.InvalidateWholeMap();
        
        info = info + "Min: " + dmin + " Location: " + ptmin.X + " , " + ptmin.Y + "\n";
        info = info + "Max: " + dmax + " Location: " + ptmax.X + " , " + ptmax.Y + "\n";
        taInfo.setText(info);
    }//GEN-LAST:event_btnMinMaxActionPerformed

    private void btnAvarageColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvarageColorActionPerformed
        TGIS_LayerPixel lp;
        TGIS_LayerVector lv;
        TGIS_Shape shp;
        TGIS_PixelItem px;
        double r,g,b;
        int cnt;
        TGIS_Color cl;
        String info;
        
        info = "";
        taInfo.setText(info);
        
        GIS.Lock();
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/Samples/PixelEdit/bitmap.ttkproject");
        
        lp = (TGIS_LayerPixel) GIS.Get("bluemarble");
        lv = (TGIS_LayerVector) GIS.Get("countries");
        
        shp = lv.GetShape(679).MakeEditable();
        
        GIS.setVisibleExtent(shp.getProjectedExtent());
        GIS.setZoom(GIS.getZoom() / 2);
        
        cnt = 0;
        r = 0;
        g = 0;
        b = 0;
        
        for(Object tmp : lp.Loop(shp.getExtent(), 0, shp, "T", false)){
            px = (TGIS_PixelItem) tmp;
            r += px.Color.getR();
            g += px.Color.getG();
            b += px.Color.getB();
            cnt++;
        }
        
        if( cnt > 0 ){
            cl = TGIS_Color.FromRGB((byte)(r/cnt), 
                    (byte)(g/cnt), 
                    (byte)(b/cnt)
            );
            for(Object tmp : lp.Loop(shp.getExtent(), 0, shp, "T", true)){
                px = (TGIS_PixelItem)tmp;
                px.Color = cl;
            }
        }
        
        GIS.InvalidateWholeMap();
        GIS.Unlock();
    }//GEN-LAST:event_btnAvarageColorActionPerformed

    private void btnNewJPGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewJPGActionPerformed
        TGIS_LayerJPG lp;
        TGIS_LayerPixelLock lock ;
        int x,y;
        
        taInfo.setText("");
        
        lp = new TGIS_LayerJPG();
        
        lp.Build("test.jpg", TGIS_CSFactory.ByEPSG(4326), TGIS_Utils.GisExtent(-180, -90, 180, 90), 360, 180);
        
        lock = lp.LockPixels(TGIS_Utils.GisExtent(-45, -45, 45, 45), lp.getCS(), true);
        
        try{
            for(x = lock.getBounds().Left; x < lock.getBounds().Right; x++){
                for(y = lock.getBounds().Top; y < lock.getBounds().Bottom; y++){
                    lock.getBitmap()[lock.BitmapPos(x, y)] = TGIS_Color.getRed().ARGB;
                }
            }
        }finally{
            lp.UnlockPixels(new VarParameter<TGIS_LayerPixelLock>(lock));
        }
        
        lp.SaveData();

        GIS.Open( "test.jpg" ) ;
    }//GEN-LAST:event_btnNewJPGActionPerformed

    private void btnNewGRDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewGRDActionPerformed
        TGIS_LayerGRD lp;
        TGIS_LayerPixelLock lock;
        int x,y;
        Random rnd;
        
        taInfo.setText("");
        rnd = new Random();
        
        lp = new TGIS_LayerGRD();
        
        lp.Build("test.grd", TGIS_CSFactory.ByEPSG(4326), TGIS_Utils.GisExtent(-180, -90, 180, 90), 360, 180);
        
        lock = lp.LockPixels(TGIS_Utils.GisExtent(-45, -45, 45, 45), lp.getCS(), true);
        
        try{
            for(x = lock.getBounds().Left; x < lock.getBounds().Right; x++){
                for(y = lock.getBounds().Top; y < lock.getBounds().Bottom; y++){
                    lock.getGrid()[y][x] = rnd.nextInt(100);
                }
            }
        }finally{
            lp.UnlockPixels(new VarParameter<TGIS_LayerPixelLock>(lock));
        }
        
        lp.SaveData(); 
        
        GIS.Open( "test.grd" ) ;
    }//GEN-LAST:event_btnNewGRDActionPerformed

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
            java.util.logging.Logger.getLogger(PixelEditUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PixelEditUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PixelEditUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PixelEditUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PixelEditUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.JButton btnAvarageColor;
    private javax.swing.JButton btnMinMax;
    private javax.swing.JButton btnNewGRD;
    private javax.swing.JButton btnNewJPG;
    private javax.swing.JButton btnProfile;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar jToolBar1;
    private tatukgis.jdk.TGIS_ControlLegend tGIS_ControlLegend1;
    private javax.swing.JTextArea taInfo;
    // End of variables declaration//GEN-END:variables
}
