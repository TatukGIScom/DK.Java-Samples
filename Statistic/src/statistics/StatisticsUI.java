package statistics;

import java.awt.Toolkit;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_LayerSHP;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_ShapeEvent;
import tatukgis.jdk.TGIS_ShapeEventArgs;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.jdk.TGIS_ViewerMode;

public class StatisticsUI extends javax.swing.JFrame {

    public StatisticsUI() {
        initComponents();
        initOwn();
    }
    private void initOwn(){
        TGIS_LayerSHP ll;
        ll = new TGIS_LayerSHP();
        ll.setPath(TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/USA/States/California/Counties.shp");
        ll.setName("counties");
        GIS.setMode(TGIS_ViewerMode.Drag);
        
        ll.addPaintShapeEvent(new TGIS_ShapeEvent() {
            @Override
            public void Invoke(Object o, TGIS_ShapeEventArgs tgs) {
                double population, area, factor;
                TGIS_Shape shp;
                shp = tgs.getShape();
                population = Float.parseFloat( shp.GetField("population").toString());
                area = Float.parseFloat( shp.GetField("area").toString());
                if( cbStatistic.getSelectedIndex() == 1){
                    factor = area;
                    if(factor < 40) shp.getParams().getArea().setColor(TGIS_Color.FromRGB(61452));
                    else if(factor < 130) shp.getParams().getArea().setColor(TGIS_Color.FromRGB(11468723));
                    else if (factor < 480) shp.getParams().getArea().setColor(TGIS_Color.FromRGB(13421823));
                    else if (factor < 2000) shp.getParams().getArea().setColor(TGIS_Color.FromRGB(3487231));
                    else if (factor < 10000) shp.getParams().getArea().setColor(TGIS_Color.FromRGB(179));
                    else shp.getParams().getArea().setColor(TGIS_Color.FromRGB(3145907));
                }else{
                    factor = population;
                    if(factor < 5000) shp.getParams().getArea().setColor(TGIS_Color.FromRGB(61452));
                    else if(factor < 22000) shp.getParams().getArea().setColor(TGIS_Color.FromRGB(11468723));
                    else if (factor < 104000) shp.getParams().getArea().setColor(TGIS_Color.FromRGB(13421823));
                    else if (factor < 478000) shp.getParams().getArea().setColor(TGIS_Color.FromRGB(3487231));
                    else if (factor < 2186000) shp.getParams().getArea().setColor(TGIS_Color.FromRGB(179));
                    else shp.getParams().getArea().setColor(TGIS_Color.FromRGB(3145907));
                }
                shp.Draw();
            }
        });
        GIS.Add(ll);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jToolBar1 = new javax.swing.JToolBar();
        btnFullExtent = new javax.swing.JButton();
        btnZoomIN = new javax.swing.JButton();
        btnZoomOut = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        cbStatistic = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        cbLabel = new javax.swing.JComboBox<>();
        jSeparator3 = new javax.swing.JToolBar.Separator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Statistics - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
            setSize(new java.awt.Dimension(800, 600));

            jScrollPane1.setViewportView(jTree1);

            GIS.add(jScrollPane1);
            jScrollPane1.setBounds(490, 150, 77, 363);

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

            btnZoomIN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/ZoomIn.png"))); // NOI18N
            btnZoomIN.setFocusable(false);
            btnZoomIN.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            btnZoomIN.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            btnZoomIN.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnZoomINActionPerformed(evt);
                }
            });
            jToolBar1.add(btnZoomIN);

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

            cbStatistic.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "By population", "By area" }));
            cbStatistic.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    cbStatisticActionPerformed(evt);
                }
            });
            jToolBar1.add(cbStatistic);
            jToolBar1.add(jSeparator2);

            cbLabel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No labels", "By FIPS", "By NAME" }));
            cbLabel.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    cbLabelActionPerformed(evt);
                }
            });
            jToolBar1.add(cbLabel);
            jToolBar1.add(jSeparator3);

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

    private void cbLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbLabelActionPerformed
        TGIS_LayerVector ll;
        ll = (TGIS_LayerVector)GIS.Get("counties");
        if(ll != null){
            switch(cbLabel.getSelectedIndex()){
                case 1 : ll.getParams().getLabels().setField("CNTYIDFP"); break;
                case 2 : ll.getParams().getLabels().setField("NAME"); break;
                default: ll.getParams().getLabels().setField(""); break;
            }
        }
        GIS.InvalidateWholeMap();
    }//GEN-LAST:event_cbLabelActionPerformed

    private void cbStatisticActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStatisticActionPerformed
        TGIS_LayerVector ll;
        ll = (TGIS_LayerVector)GIS.Get("counties");
        
        GIS.InvalidateWholeMap();
    }//GEN-LAST:event_cbStatisticActionPerformed

    private void btnFullExtentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFullExtentActionPerformed
        if(GIS.getIsEmpty()) return;
        
        GIS.FullExtent();
    }//GEN-LAST:event_btnFullExtentActionPerformed

    private void btnZoomINActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZoomINActionPerformed
        if(GIS.getIsEmpty()) return;
        
        GIS.setZoom(GIS.getZoom()*2);
    }//GEN-LAST:event_btnZoomINActionPerformed

    private void btnZoomOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZoomOutActionPerformed
        if(GIS.getIsEmpty()) return;
        
        GIS.setZoom(GIS.getZoom()/2);    }//GEN-LAST:event_btnZoomOutActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(StatisticsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StatisticsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StatisticsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StatisticsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StatisticsUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.JButton btnFullExtent;
    private javax.swing.JButton btnZoomIN;
    private javax.swing.JButton btnZoomOut;
    private javax.swing.JComboBox<String> cbLabel;
    private javax.swing.JComboBox<String> cbStatistic;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables
}
