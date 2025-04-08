package topology;

import java.awt.Toolkit;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_ShapePolygon;
import tatukgis.jdk.TGIS_Topology;
import tatukgis.jdk.TGIS_TopologyCombineType;
import tatukgis.jdk.TGIS_Utils;

public class TopologyUI extends javax.swing.JFrame {

    public TopologyUI() {
        initComponents();
        initOwn();
    }

    private void initOwn(){
        TGIS_LayerVector ll;
        GIS.Lock();
        topologyObj = new TGIS_Topology();
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/Samples//Topology//topology.shp");
        ll = (TGIS_LayerVector)GIS.getItems().getItems(0);
        if(ll == null) return;
        shpA = (TGIS_ShapePolygon)ll.GetShape(1).MakeEditable();
        if(shpA == null) return;
        shpB = (TGIS_ShapePolygon)ll.GetShape(2).MakeEditable();
        if(shpB == null) return;
        layerObj = new TGIS_LayerVector();
        layerObj.setName("output");
        layerObj.setTransparency(50);
        layerObj.getParams().getArea().setColor(TGIS_Color.getRed());
        
        GIS.Add(layerObj);
        GIS.Unlock();
        GIS.FullExtent();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        jToolBar1 = new javax.swing.JToolBar();
        btnAplusB = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnAmultiplyB = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnAminusB = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnBminusA = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btnAxorB = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Topology - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(800, 600));

        jToolBar1.setRollover(true);

        btnAplusB.setText("A + B");
        btnAplusB.setFocusable(false);
        btnAplusB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAplusB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAplusB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAplusBActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAplusB);
        jToolBar1.add(jSeparator1);

        btnAmultiplyB.setText("A * B");
        btnAmultiplyB.setFocusable(false);
        btnAmultiplyB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAmultiplyB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAmultiplyB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmultiplyBActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAmultiplyB);
        jToolBar1.add(jSeparator2);

        btnAminusB.setText("A - B");
        btnAminusB.setFocusable(false);
        btnAminusB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAminusB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAminusB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAminusBActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAminusB);
        jToolBar1.add(jSeparator3);

        btnBminusA.setText("B - A");
        btnBminusA.setFocusable(false);
        btnBminusA.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBminusA.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBminusA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBminusAActionPerformed(evt);
            }
        });
        jToolBar1.add(btnBminusA);
        jToolBar1.add(jSeparator4);

        btnAxorB.setText("A xor B");
        btnAxorB.setFocusable(false);
        btnAxorB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAxorB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAxorB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAxorBActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAxorB);
        jToolBar1.add(jSeparator5);

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

    private void btnAplusBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAplusBActionPerformed
        TGIS_Shape tmp;
        
        layerObj.RevertShapes();
        tmp = topologyObj.Combine(shpA, shpB, TGIS_TopologyCombineType.Union);
        if(tmp == null) return;
        layerObj.AddShape(tmp);
        tmp = null;
        GIS.InvalidateWholeMap();
    }//GEN-LAST:event_btnAplusBActionPerformed

    private void btnAmultiplyBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmultiplyBActionPerformed
        TGIS_Shape tmp;
        
        layerObj.RevertShapes();
        tmp = topologyObj.Combine(shpA, shpB, TGIS_TopologyCombineType.Intersection);
        if(tmp == null) return;
        layerObj.AddShape(tmp);
        tmp = null;
        GIS.InvalidateWholeMap();    }//GEN-LAST:event_btnAmultiplyBActionPerformed

    private void btnAminusBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAminusBActionPerformed
        TGIS_Shape tmp;
        
        layerObj.RevertShapes();
        tmp = topologyObj.Combine(shpA, shpB, TGIS_TopologyCombineType.Difference);
        if(tmp == null) return;
        layerObj.AddShape(tmp);
        tmp = null;
        GIS.InvalidateWholeMap();    }//GEN-LAST:event_btnAminusBActionPerformed

    private void btnBminusAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBminusAActionPerformed
        TGIS_Shape tmp;
        
        layerObj.RevertShapes();
        tmp = topologyObj.Combine(shpB, shpA, TGIS_TopologyCombineType.Difference);
        if(tmp == null) return;
        layerObj.AddShape(tmp);
        tmp = null;
        GIS.InvalidateWholeMap();    }//GEN-LAST:event_btnBminusAActionPerformed

    private void btnAxorBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAxorBActionPerformed
        TGIS_Shape tmp;
        
        layerObj.RevertShapes();
        tmp = topologyObj.Combine(shpA, shpB, TGIS_TopologyCombineType.SymmetricalDifference);
        if(tmp == null) return;
        layerObj.AddShape(tmp);
        tmp = null;
        GIS.InvalidateWholeMap();    }//GEN-LAST:event_btnAxorBActionPerformed

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
            java.util.logging.Logger.getLogger(TopologyUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TopologyUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TopologyUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TopologyUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TopologyUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.JButton btnAminusB;
    private javax.swing.JButton btnAmultiplyB;
    private javax.swing.JButton btnAplusB;
    private javax.swing.JButton btnAxorB;
    private javax.swing.JButton btnBminusA;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
    private TGIS_Topology topologyObj;
    private TGIS_LayerVector layerObj;
    private TGIS_ShapePolygon shpA, shpB;
}
