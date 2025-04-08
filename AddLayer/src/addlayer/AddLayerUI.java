package addlayer;

import java.awt.Toolkit;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_LayerSHP;
import static tatukgis.jdk.TGIS_RegistredLayers.GisCreateLayer;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.jdk.TGIS_ViewerMode;

public class AddLayerUI extends javax.swing.JFrame {

    public AddLayerUI() {        
        initComponents();
        initOwn();
    }
    
    private void initOwn(){
        TGIS_LayerSHP layer = new TGIS_LayerSHP();
        //add country layer
        layer.setPath(TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/Poland/DCW/country.shp") ;
        layer.setName("Country");
        GIS.Add(layer);
        
        //add rivers layer
        layer = (TGIS_LayerSHP) (GisCreateLayer( "rivers",TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/Poland/DCW/lwaters.shp")) ;               
        layer.setUseConfig(false);
        layer.getParams().getLine().setOutlineWidth(0);
        layer.getParams().getLine().setWidth(3);
        layer.getParams().getLine().setColor(TGIS_Color.getBlue());
        GIS.Add(layer);
        GIS.FullExtent();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("AddLayer - TatukGIS DK11 sample");
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(AddLayerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddLayerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddLayerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddLayerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddLayerUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.JButton btnFullExtent;
    private javax.swing.JButton btnZoomIn;
    private javax.swing.JButton btnZoomOut;
    private javax.swing.JCheckBox cbxDragging;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
