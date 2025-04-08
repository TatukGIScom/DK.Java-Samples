package inmemory;

import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_Lock;
import tatukgis.jdk.TGIS_Point;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_ShapeType;
import tatukgis.jdk.TGIS_Utils;
import static javax.swing.JOptionPane.showMessageDialog;


public class InMemoryUI extends javax.swing.JFrame {


    public InMemoryUI() {
      initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        jToolBar1 = new javax.swing.JToolBar();
        btnCreateLayer = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnAddLines = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnAddPoints = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnAnimate = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("InMemory - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(800, 600));

        jToolBar1.setRollover(true);

        btnCreateLayer.setText("Create layer");
        btnCreateLayer.setFocusable(false);
        btnCreateLayer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCreateLayer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCreateLayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateLayerActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCreateLayer);
        jToolBar1.add(jSeparator2);

        btnAddLines.setText("Add lines");
        btnAddLines.setFocusable(false);
        btnAddLines.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAddLines.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAddLines.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddLinesActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAddLines);
        jToolBar1.add(jSeparator1);

        btnAddPoints.setText("Add points");
        btnAddPoints.setFocusable(false);
        btnAddPoints.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAddPoints.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAddPoints.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPointsActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAddPoints);
        jToolBar1.add(jSeparator3);

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
        jToolBar1.add(jSeparator4);

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
    
    private void btnCreateLayerActionPerformed(java.awt.event.ActionEvent evt) {
        TGIS_LayerVector ll;
        
        ll = new TGIS_LayerVector();
        ll.getParams().getMarker().setSymbol(TGIS_Utils.getSymbolList().Prepare(TGIS_Utils.GisSamplesDataDirDownload() + "/Symbols/2267.cgm"));
        ll.getParams().getMarker().setSymbolRotate(Math.PI/2);
        ll.getParams().getMarker().setSize(-20);
        ll.getParams().getLine().setSymbol(TGIS_Utils.getSymbolList().Prepare(TGIS_Utils.GisSamplesDataDirDownload() + "/Symbols/1301.cgm"));
        ll.getParams().getLine().setWidth(-5);
        GIS.Add(ll);
        ll.setExtent(TGIS_Utils.GisExtent(-180, -90, 180, 90));
        GIS.FullExtent();
        btnCreateLayer.setEnabled(false);
        rnd = new Random();
    }

    private void btnAddPointsActionPerformed(java.awt.event.ActionEvent evt) {
        int i;
        TGIS_Shape shp;
        Random rng;
        if(GIS.getIsEmpty()){
            showMessageDialog(null, "Please create layer first");
            return;
        }
        rng = new Random();
        for(i = 0 ; i < 100 ; i++){
            shp = ((TGIS_LayerVector)GIS.getItems().getItems(0)).CreateShape(TGIS_ShapeType.Point);
            shp.getParams().getMarker().setSymbolRotate(rng.nextInt(360)*(Math.PI/180));
            shp.getParams().getMarker().setColor(TGIS_Color.FromRGB((byte)rng.nextInt(256), (byte)rng.nextInt(256), (byte)rng.nextInt(256)));
            shp.getParams().getMarker().setOutlineColor(TGIS_Color.FromRGB((byte)rng.nextInt(256), (byte)rng.nextInt(256), (byte)rng.nextInt(256)));
            shp.Lock(TGIS_Lock.Extent);
            shp.AddPart();
            shp.AddPoint(new TGIS_Point(rng.nextInt(360)-180, rng.nextInt(180)-90));
            shp.Unlock();         
        }
        GIS.InvalidateWholeMap();
    }

    private void btnAddLinesActionPerformed(java.awt.event.ActionEvent evt) {
        int i;
        TGIS_Shape shp;
        
        if(GIS.getIsEmpty()){
            showMessageDialog(null, "Please create layer first");
            return;
        }
        shp = ((TGIS_LayerVector)GIS.getItems().getItems(0)).CreateShape(TGIS_ShapeType.Arc);
        shp.Lock(TGIS_Lock.Extent);
        shp.AddPart();
        for(i = 0 ; i < 20 ; i++){
            shp.AddPoint(new TGIS_Point(rnd.nextInt(360)-180, rnd.nextInt(180)-90));
        }
        shp.Unlock();
        GIS.InvalidateWholeMap();
    }

    private void btnAnimateActionPerformed(java.awt.event.ActionEvent evt) {
        TGIS_Shape shp, shp2;
        
        if(GIS.getIsEmpty()){
            showMessageDialog(null, "Please create layer first");
            return;
        }
        
        shp = ((TGIS_LayerVector)GIS.getItems().getItems(0)).CreateShape(TGIS_ShapeType.Point);
        shp.Lock(TGIS_Lock.Extent);
        shp.AddPart();
        shp.AddPoint(TGIS_Utils.GisPoint(0, 0));
        shp.getParams().getMarker().setColor(TGIS_Color.FromRGB((byte)rnd.nextInt(256), (byte)rnd.nextInt(256), (byte)rnd.nextInt(256)));
        shp.getParams().getMarker().setOutlineColor(TGIS_Color.FromRGB((byte)rnd.nextInt(256), (byte)rnd.nextInt(256), (byte)rnd.nextInt(256)));
        shp.getParams().getMarker().setSize(-20);
        shp.Unlock();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0 ; i < 90 ; i++){
                    final int x = i;
                    try {
                        SwingUtilities.invokeAndWait(new Runnable() {
                            @Override
                            public void run() {
                                shp.SetPosition(TGIS_Utils.GisPoint(x * 2, x), null, 0);
                                try {
                                    Thread.sleep(10);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(InMemoryUI.class.getName()).log(Level.SEVERE, null, ex);
                                }                        
                            }
                        });
                    } catch (InterruptedException | InvocationTargetException ex) {
                        Logger.getLogger(InMemoryUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();        
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InMemoryUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InMemoryUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.JButton btnAddLines;
    private javax.swing.JButton btnAddPoints;
    private javax.swing.JButton btnAnimate;
    private javax.swing.JButton btnCreateLayer;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
    private Random rnd;
}
