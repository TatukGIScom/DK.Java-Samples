package wkt;

import java.awt.Toolkit;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_Utils;


public class WKTUI extends javax.swing.JFrame {

    public WKTUI() {
        importantInit();
        initComponents();
        initOwn();
    }
    private void importantInit(){
        layerObj = new TGIS_LayerVector();
    }
    private void initOwn(){
        layerObj.setName("output");
        layerObj.getParams().getArea().setColor(TGIS_Color.getRed());
        
        GIS.Add(layerObj);
        GIS.FullExtent();
        wkt.setText("POLYGON( ( 5 5, 25 5, 25 25, 5 25, 5 5), ( 10 10, 10 20, 20 20, 20 10, 10 10))");
        shapeChooser.setSelectedIndex(4);
        System.out.println(shapeChooser.getSelectedIndex());
        pareClick();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        shapeChooser = new javax.swing.JComboBox<>();
        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        wkt = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("WKT - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(800, 600));
        setSize(new java.awt.Dimension(800, 600));

        shapeChooser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "POINT", "MULTIPOINT", "LINESTRING", "MULTILINESTRING", "POLYGON", "POINT3D", "MULTIPOINT3D", "LINESTRING3D", "MULTILINESTRING3D", "POLYGON3D", "GEOMETRYCOLLECTION" }));
        shapeChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shapeChooserActionPerformed(evt);
            }
        });

        wkt.setColumns(20);
        wkt.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        wkt.setRows(5);
        wkt.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                wktPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(shapeChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 668, Short.MAX_VALUE))
            .addComponent(wkt)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(shapeChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(wkt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void shapeChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shapeChooserActionPerformed
        switch(shapeChooser.getSelectedIndex()){
            case 0: wktHelper = "POINT (30 30)"; break;
            case 1: wktHelper = "MULTIPOINT (4 4, 5 5, 6 6 ,7 7)"; break;
            case 2: wktHelper = "LINESTRING (3 3, 10 10)"; break;
            case 3: wktHelper = "MULTILINESTRING ((5 5, 25 5, 25 25, 5 25, 5 5), (10 10, 10 20, 20 20, 20 10, 10 10))"; break;
            case 4: wktHelper = "POLYGON ((5 5, 25 5, 25 25, 5 25, 5 5), (10 10, 10 20, 20 20, 20 10, 10 10))"; break;
            case 5: wktHelper = "POINTZ (30 30 100)"; break;
            case 6: wktHelper = "MULTIPOINTZ (4 4 100, 5 5 100, 6 6 100, 7 7 100)"; break;
            case 7: wktHelper = "LINESTRINGZ (3 3 100, 10 10 100)"; break;
            case 8: wktHelper = "MULTILINESTRINGZ ((5 5 100, 25 5 100, 25 25 100, 5 25 100, 5 5 100), (10 10 100, 10 20 100, 20 20 100, 20 10 100, 10 10 100))"; break;
            case 9: wktHelper = "POLYGONZ ((5 5 100, 25 5 100, 25 25 100, 5 25 100, 5 5 100), (10 10 100, 10 20 100, 20 20 100, 20 10 100, 10 10 100))"; break;
            case 10: wktHelper = "GEOMETRYCOLLECTION (POINT (30 30), LINESTRING (3 3, 10 10) )"; break;
            }
        wkt.setText(wktHelper);
        pareClick();
    }//GEN-LAST:event_shapeChooserActionPerformed

    private void wktPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_wktPropertyChange
        TGIS_Shape shp;
        
        layerObj.RevertShapes();
        shp = TGIS_Utils.GisCreateShapeFromWKT(wkt.getText());
        if(shp != null)
            layerObj.AddShape(shp);
        else 
            return;
        layerObj.RecalcExtent();
        GIS.RecalcExtent();
        GIS.FullExtent();
    }//GEN-LAST:event_wktPropertyChange


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
            java.util.logging.Logger.getLogger(WKTUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WKTUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WKTUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WKTUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WKTUI().setVisible(true);
            }
        });
    }
    
    private void pareClick(){
        TGIS_Shape shp;
        
        layerObj.RevertShapes();
        shp = TGIS_Utils.GisCreateShapeFromWKT(wkt.getText());
        layerObj.AddShape(shp);
        GIS.RecalcExtent();
        GIS.FullExtent();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.JComboBox<String> shapeChooser;
    private javax.swing.JTextArea wkt;
    // End of variables declaration//GEN-END:variables
    private TGIS_LayerVector layerObj;
    private String wktHelper;
}
