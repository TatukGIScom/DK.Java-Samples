package trackingtest;

import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_FieldType;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_Lock;
import tatukgis.jdk.TGIS_Point;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_ShapeType;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.rtl.__Global;


public class TrackingTestUI extends javax.swing.JFrame {

    public TrackingTestUI() {
        initComponents();
        initOwn();
    }

    private void initOwn(){
        TGIS_LayerVector ll;
        TGIS_Shape shp;
        int i;
        Random generator;
        
        generator = new Random();
        GIS.Lock();
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/World/VisibleEarth/world_8km.jpg");
        GIS.setZoom(GIS.getZoom() * 2);

        ll = new TGIS_LayerVector();
        ll.getParams().getMarker().setSymbol(tatukgis.jdk.__Global.SymbolList().Prepare(TGIS_Utils.GisSamplesDataDirDownload() + "/Symbols/2267.cgm"));
        ll.getParams().getMarker().setSymbolRotate(Math.PI/2);
        ll.getParams().getMarker().setSize(-20);
        ll.getParams().getLine().setSymbol(tatukgis.jdk.__Global.SymbolList().Prepare(TGIS_Utils.GisSamplesDataDirDownload() + "/Symbols/1301.cgm"));
        ll.getParams().getLine().setWidth(-5);
        ll.setCachedPaint(false);
        ll.setCS(GIS.getCS());
        ll.AddField("Name", TGIS_FieldType.String, 255, 0);
        ll.getParams().getLabels().setField("Name");
        GIS.Add(ll);

        GIS.Unlock();
        
        for( i = 0 ; i < 100 ; i++ ){
            shp = ll.CreateShape(TGIS_ShapeType.Point);
            shp.SetField("Name", __Global.IntToStr(i + 1));
            shp.getParams().getMarker().setSymbolRotate(__Global.DegToRad(generator.nextInt()*360));
            shp.getParams().getMarker().setColor(TGIS_Color.FromRGB(generator.nextInt()*255));
            shp.getParams().getMarker().setOutlineColor(shp.getParams().getMarker().getColor());
            shp.Lock(TGIS_Lock.Extent);
            shp.AddPart();
            shp.AddPoint(TGIS_Utils.GisPoint(-180 + generator.nextInt(360), 90 - generator.nextInt(180)));
            shp.Unlock();
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        jToolBar1 = new javax.swing.JToolBar();
        cbLock = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnAnimate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TrackingTest - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(800, 600));

        jToolBar1.setRollover(true);

        cbLock.setText("Use Lock");
        cbLock.setFocusable(false);
        cbLock.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        cbLock.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(cbLock);
        jToolBar1.add(jSeparator1);

        btnAnimate.setText("Animate");
        btnAnimate.setFocusable(false);
        btnAnimate.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAnimate.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAnimate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnimateActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAnimate);

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
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAnimateActionPerformed(java.awt.event.ActionEvent evt) {
        btnAnimate.setEnabled(false); 
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int i,j;
                    for(i = 0; i < 90; i++){
                        if(cbLock.isSelected()){
                            GIS.Lock();
                        }
                        for(j = 1 ; j < 90 ; j++){
                            final TGIS_Shape shp = ((TGIS_LayerVector)GIS.getItems().getItems(1)).GetShape(j);
                            final TGIS_Point ptg = shp.Centroid();
                            final int delta = (j % 3) - 1;
                            try {
                            SwingUtilities.invokeAndWait(new Runnable() {
                                @Override
                                public void run() {
                                    shp.SetPosition(TGIS_Utils.GisPoint(ptg.X + delta, ptg.Y), null, 0);
                                }
                            });
                            } catch (InterruptedException | InvocationTargetException ex) {
                                Logger.getLogger(TrackingTestUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        if(cbLock.isSelected()){
                            GIS.Unlock();
                            GIS.invalidate();
                        }
                    }
                    btnAnimate.setEnabled(true);
                }
            }).start();
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
            java.util.logging.Logger.getLogger(TrackingTestUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TrackingTestUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TrackingTestUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TrackingTestUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TrackingTestUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.JButton btnAnimate;
    private javax.swing.JCheckBox cbLock;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
