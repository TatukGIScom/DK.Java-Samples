package Measure;

import java.awt.Toolkit;
import tatukgis.jdk.TGIS_CSUnits;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_EditorSnapType;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_Point;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_ShapeType;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.jdk.TGIS_ViewerMode;
import tatukgis.rtl.TNotifyEvent;
import tatukgis.rtl.TPoint;

public class MeasureUI extends javax.swing.JFrame {

    public MeasureUI() {
        initComponents();
        initOwn();
    }

    private void initOwn(){
        GIS.Lock();
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "\\World\\WorldDCW\\world.shp");

        ll = new TGIS_LayerVector();
        ll.getParams().getLine().setColor(TGIS_Color.getRed());
        ll.getParams().getLine().setWidth(25);
        ll.SetCSByEPSG( 4326 );

        GIS.Add(ll);
        GIS.setRestrictedExtent(GIS.getExtent());

        GIS.Unlock();

        unit = TGIS_Utils.getCSUnitsList().ByEPSG( 904201 );

        isLine = false;
        isPolygon = false;

        GIS.getEditor().getEditingLinesStyle().setPenWidth(10);
        GIS.getEditor().setSnapType(TGIS_EditorSnapType.Line);
        
        //Manually set event for editor change
        GIS.setEditorChangeEvent( new TNotifyEvent() {
            @Override
            public void Invoke(Object o) {
                if (GIS.getEditor().getCurrentShape() != null)
                {
                    if (isLine)
                    {
                        tfLength.setText(unit.AsLinear(((TGIS_Shape)GIS.getEditor().getCurrentShape()).LengthCS(), true));
                    }else if (isPolygon)
                    {
                        tfLength.setText(unit.AsLinear(((TGIS_Shape)GIS.getEditor().getCurrentShape()).LengthCS(), true));
                        tfArea.setText(unit.AsAreal(((TGIS_Shape)GIS.getEditor().getCurrentShape()).LengthCS(), true, "%s²"));
                    }

                }
            }
        });
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        btnLine = new javax.swing.JButton();
        btnPolygon = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblLength = new javax.swing.JLabel();
        tfLength = new javax.swing.JTextField();
        lblArea = new javax.swing.JLabel();
        tfArea = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SelectByShape - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
            setSize(new java.awt.Dimension(800, 600));

            GIS.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    GISMouseClicked(evt);
                }
            });

            btnLine.setText("By line");
            btnLine.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnLineActionPerformed(evt);
                }
            });

            btnPolygon.setText("By polygon");
            btnPolygon.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnPolygonActionPerformed(evt);
                }
            });

            btnClear.setText("Clear");
            btnClear.setName(""); // NOI18N
            btnClear.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnClearActionPerformed(evt);
                }
            });

            lblLength.setText("Length:");

            lblArea.setText("Area:");

            javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
            jPanel2.setLayout(jPanel2Layout);
            jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(tfLength, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblLength)
                                .addComponent(lblArea))
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addComponent(tfArea))
                    .addContainerGap())
            );
            jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblLength)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(tfLength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(lblArea)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(tfArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(296, Short.MAX_VALUE))
            );

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(btnLine)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnPolygon)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnClear)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnLine)
                        .addComponent(btnPolygon)
                        .addComponent(btnClear))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE))))
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void btnLineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLineActionPerformed
            GIS.getEditor().DeleteShape();
            GIS.getEditor().EndEdit();

            tfArea.setText("");
            tfLength.setText("");

            isPolygon = false;
            isLine = true;

            GIS.setMode(TGIS_ViewerMode.Select);
    }//GEN-LAST:event_btnLineActionPerformed

    private void btnPolygonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPolygonActionPerformed
            GIS.getEditor().DeleteShape();
            GIS.getEditor().EndEdit();

            tfArea.setText("");
            tfLength.setText("");

            isPolygon = true;
            isLine = false;

            GIS.setMode(TGIS_ViewerMode.Select);
    }//GEN-LAST:event_btnPolygonActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
            GIS.getEditor().DeleteShape();
            GIS.getEditor().EndEdit();

            tfArea.setText("");
            tfLength.setText("");

            GIS.setMode(TGIS_ViewerMode.Drag);
    }//GEN-LAST:event_btnClearActionPerformed

    private void GISMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GISMouseClicked
            TGIS_Point ptg;

            if (GIS.getMode() == TGIS_ViewerMode.Edit)
                return;

            ptg = GIS.ScreenToMap(new TPoint(evt.getX(), evt.getY()));

            if (isLine)
            {
                GIS.getEditor().CreateShape(ll, ptg, TGIS_ShapeType.Arc);
            }else if (isPolygon)
            {
                GIS.getEditor().CreateShape(ll, ptg, TGIS_ShapeType.Polygon);
            }

            GIS.setMode(TGIS_ViewerMode.Edit);
    }//GEN-LAST:event_GISMouseClicked
    
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
            java.util.logging.Logger.getLogger(MeasureUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MeasureUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MeasureUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MeasureUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MeasureUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnLine;
    private javax.swing.JButton btnPolygon;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblArea;
    private javax.swing.JLabel lblLength;
    private javax.swing.JTextField tfArea;
    private javax.swing.JTextField tfLength;
    // End of variables declaration//GEN-END:variables

    private TGIS_LayerVector ll;
    private TGIS_CSUnits unit;
    private boolean isLine;
    private boolean isPolygon;
}
