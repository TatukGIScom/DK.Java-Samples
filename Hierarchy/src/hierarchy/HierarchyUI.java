package hierarchy;

import java.awt.Toolkit;
import tatukgis.jdk.IGIS_HierarchyGroup;
import tatukgis.jdk.TGIS_ConfigFormat;
import tatukgis.jdk.TGIS_ControlLegendMode;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.rtl.TStrings;

public class HierarchyUI extends javax.swing.JFrame {

    public HierarchyUI() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnBuildHierarchy = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        GIS_Legend = new tatukgis.jdk.TGIS_ControlLegend();
        GIS = new tatukgis.jdk.TGIS_ViewerWnd();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hierarchy - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));

        btnBuildHierarchy.setText("Build Hierarchy");
        btnBuildHierarchy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuildHierarchyActionPerformed(evt);
            }
        });

        GIS_Legend.setGIS_Viewer(GIS);
        jScrollPane1.setViewportView(GIS_Legend);

        GIS.setMode(tatukgis.jdk.TGIS_ViewerMode.Zoom);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBuildHierarchy)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBuildHierarchy)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuildHierarchyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuildHierarchyActionPerformed
        IGIS_HierarchyGroup group;
        int i;
        TStrings list;
        
        GIS.Close();
        GIS_Legend.setMode(TGIS_ControlLegendMode.Groups);
        
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/Poland/DCW/poland.ttkproject");
        
        GIS.getHierarchy().ClearGroups();
        
        group = GIS.getHierarchy().CreateGroup("My group");
        
        for(i = 0; i < GIS.getItems().getCount() / 2; i++){
            group.AddLayer(GIS.getItems().getItems(i));
        }
        
        for(i = 0; i < GIS.getItems().getCount() / 2; i++){
            group.DeleteLayer(GIS.getItems().getItems(i));
        }
        
        group = GIS.getHierarchy().CreateGroup("Root");
        group.CreateGroup("Leaf");
        
        GIS.getHierarchy().getGroups("Leaf").CreateGroup("node").AddLayer(GIS.Get("city1"));
        GIS.getHierarchy().MoveGroup("Root", "My group");
        
        group = GIS.getHierarchy().CreateGroup("Poland");
        group = group.CreateGroup("Waters");
        group.AddLayer(GIS.Get("Lakes"));
        group.AddLayer(GIS.Get("Rivers"));

        group = GIS.getHierarchy().getGroups("Poland");
        group = group.CreateGroup("Areas");
        group.AddLayer(GIS.Get("city"));
        group.AddLayer(GIS.Get("Country area"));

        GIS.getHierarchy().AddOtherLayers();

        list = new TStrings();

        list.Add("Poland/Waters=Lakes;Rivers");
        list.Add("Poland/Areas=city;Country area");

        GIS.getHierarchy().ClearGroups();
        GIS.getHierarchy().ParseHierarchy(list, TGIS_ConfigFormat.Ini);

        GIS.FullExtent();        
    }//GEN-LAST:event_btnBuildHierarchyActionPerformed

    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HierarchyUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HierarchyUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HierarchyUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HierarchyUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HierarchyUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private tatukgis.jdk.TGIS_ControlLegend GIS_Legend;
    private javax.swing.JButton btnBuildHierarchy;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
