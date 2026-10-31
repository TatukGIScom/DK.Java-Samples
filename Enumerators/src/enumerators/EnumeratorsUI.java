package enumerators;

import java.awt.Toolkit;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_FieldType;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_RegistredLayers;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.jdk.TGIS_ViewerMode;

/**
 * Enumerators sample — demonstrates using {@code TGIS_LayerVector.Loop()} enumerators to compute
 * the neighbor count for every shape in a polygon layer.
 *
 * Loads the California Counties shapefile.  Clicking the enumerate button runs
 * {@link #btnEnumeratorActionPerformed}: an outer {@code Loop()} visits every county while an
 * inner {@code Loop(extent, "", shape, "****T", true)} counts topologically adjacent neighbors
 * using the DE-9IM "T" (touches/intersects) filter.  Each county's neighbor count is written to a
 * "COUNT" field via {@code MakeEditable}, then the layer is rendered as a 5-zone white-to-red
 * color ramp keyed on that field.
 */
public class EnumeratorsUI extends javax.swing.JFrame {

    /** Constructs the frame, initialises UI components, and loads the counties shapefile. */
    public EnumeratorsUI() {
        initComponents();
        initOwn();
    }

    /** Loads the California Counties shapefile and fits the viewer to its full extent. */
    private void initOwn(){
        GIS.Add(TGIS_RegistredLayers.GisCreateLayer("world", TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/USA/States/California/tl_2008_06_county.shp"));
        GIS.FullExtent();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        jToolBar1 = new javax.swing.JToolBar();
        btnFullExtent = new javax.swing.JButton();
        btnZoomIn = new javax.swing.JButton();
        btnZoomOut = new javax.swing.JButton();
        btnDrag = new javax.swing.JButton();
        btnEnumerator = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Enumerators - TatukGIS DK sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/"
            + "icon.png")));
setSize(new java.awt.Dimension(800, 600));

jToolBar1.setRollover(true);

btnFullExtent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/FullExtent.png"))); // NOI18N
jToolBar1.add(btnFullExtent);

btnZoomIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/ZoomIn.png"))); // NOI18N
jToolBar1.add(btnZoomIn);

btnZoomOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/ZoomOut.png"))); // NOI18N
jToolBar1.add(btnZoomOut);

btnDrag.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Drag.png"))); // NOI18N
btnDrag.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnDragActionPerformed(evt);
    }
    });
    jToolBar1.add(btnDrag);

    btnEnumerator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/LayerProperties.png"))); // NOI18N
    btnEnumerator.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnEnumeratorActionPerformed(evt);
        }
    });
    jToolBar1.add(btnEnumerator);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    /** Toggles the viewer between Drag and Select mode, updating the button label accordingly. */
    private void btnDragActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDragActionPerformed
        if(GIS.getIsEmpty()) return;
        if(GIS.getMode() == TGIS_ViewerMode.Drag){ 
            GIS.setMode(TGIS_ViewerMode.Select);
            btnDrag.setText("Drag");
        }
        else{
            GIS.setMode(TGIS_ViewerMode.Drag);
            btnDrag.setText("Stop dragging");
        }
    }//GEN-LAST:event_btnDragActionPerformed

    /**
     * Counts topological neighbors for every county and renders the result as a 5-zone
     * white-to-red color ramp.  The COUNT field is added if it does not yet exist.
     */
    private void btnEnumeratorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnumeratorActionPerformed
        int cnt, max_cnt    ;
        TGIS_LayerVector lv ;
        TGIS_Shape tmpshp   ;
        
        max_cnt = 0 ;
        
        lv = (TGIS_LayerVector)GIS.getItems().getItems(0) ;
        
        if( lv.FindField("COUNT") < 0 ) {
            lv.AddField("COUNT", TGIS_FieldType.Number, 10, 0) ;
        }
        
        GIS.HourglassPrepare();
        try{
            for(Object shp : lv.Loop()){
                cnt = -1 ;
                for(Object shpNbr : lv.Loop(((TGIS_Shape)shp).getProjectedExtent(), "", (TGIS_Shape)shp, "****T", true)){
                    cnt += 1 ;
                    tmpshp = (TGIS_Shape)shpNbr;
                    GIS.HourglassShake();
                }
                tmpshp = ((TGIS_Shape)shp).MakeEditable();
                tmpshp.SetField("COUNT", cnt);
                if( cnt > max_cnt) {
                    max_cnt = cnt ;
                }
            }
        }finally{
            GIS.HourglassRelease();
            lv.getParams().getLabels().setField("COUNT");
            lv.getParams().getRender().setExpression("COUNT");
            lv.getParams().getRender().setMinVal(1);
            lv.getParams().getRender().setMaxVal(max_cnt);
            lv.getParams().getRender().setStartColor(TGIS_Color.getWhite());
            lv.getParams().getRender().setEndColor(TGIS_Color.getRed());
            lv.getParams().getRender().setZones(5);
            lv.getParams().getArea().setColor(TGIS_Color.getRenderColor());
            GIS.InvalidateWholeMap();            
        }
    }//GEN-LAST:event_btnEnumeratorActionPerformed

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
            java.util.logging.Logger.getLogger(EnumeratorsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EnumeratorsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EnumeratorsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EnumeratorsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EnumeratorsUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.JButton btnDrag;
    private javax.swing.JButton btnEnumerator;
    private javax.swing.JButton btnFullExtent;
    private javax.swing.JButton btnZoomIn;
    private javax.swing.JButton btnZoomOut;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
