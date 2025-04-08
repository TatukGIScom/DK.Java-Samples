package pixellocate;

import RemObjects.Elements.System.VarParameter;
import java.awt.Color;
import java.awt.Toolkit;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_LayerPixel;
import tatukgis.jdk.TGIS_Point;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.jdk.TGIS_ViewerMode;
import tatukgis.rtl.TPoint;

public class PixelLocateUI extends javax.swing.JFrame {

    public PixelLocateUI() {    
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        sBrightness = new javax.swing.JSlider();
        titleBrightness = new javax.swing.JLabel();
        titleChanelsValue = new javax.swing.JLabel();
        pChannelsColor = new javax.swing.JPanel();
        lRGB = new javax.swing.JLabel();
        titleOrginalColor = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taOrginalColor = new javax.swing.JTextArea();
        btnImage = new javax.swing.JButton();
        btnGrid = new javax.swing.JButton();
        GIS = new tatukgis.jdk.TGIS_ViewerWnd();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PixelLocate - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(800, 600));

        sBrightness.setMinimum(-100);
        sBrightness.setValue(0);
        sBrightness.setEnabled(false);
        sBrightness.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sBrightnessStateChanged(evt);
            }
        });

        titleBrightness.setText("Brightness");

        titleChanelsValue.setText("Channels value");

        pChannelsColor.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pChannelsColorLayout = new javax.swing.GroupLayout(pChannelsColor);
        pChannelsColor.setLayout(pChannelsColorLayout);
        pChannelsColorLayout.setHorizontalGroup(
            pChannelsColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        pChannelsColorLayout.setVerticalGroup(
            pChannelsColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        lRGB.setText("RGB: 255, 255, 255");

        titleOrginalColor.setText("Original color value");

        taOrginalColor.setEditable(false);
        taOrginalColor.setBackground(new java.awt.Color(240, 240, 240));
        taOrginalColor.setColumns(20);
        taOrginalColor.setRows(5);
        jScrollPane1.setViewportView(taOrginalColor);

        btnImage.setText("Open image");
        btnImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImageActionPerformed(evt);
            }
        });

        btnGrid.setText("Open grid");
        btnGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGridActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(sBrightness, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pChannelsColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(titleBrightness)
                            .addComponent(titleChanelsValue)
                            .addComponent(lRGB)
                            .addComponent(titleOrginalColor))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(btnImage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGrid, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnImage)
                    .addComponent(btnGrid))
                .addGap(28, 28, 28)
                .addComponent(titleBrightness)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sBrightness, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(titleChanelsValue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pChannelsColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lRGB)
                .addGap(18, 18, 18)
                .addComponent(titleOrginalColor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(113, Short.MAX_VALUE))
        );

        GIS.setMode(tatukgis.jdk.TGIS_ViewerMode.Select);
        GIS.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                GISMouseMoved(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void GISMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GISMouseMoved
        TGIS_Point ptg;
        TGIS_LayerPixel lp;
        TGIS_Color rgbMapped;
        VarParameter<TGIS_Color> rgbMappedVar;
        rgbMapped = new TGIS_Color();
        double[] nativeVals;
        VarParameter<double[]> nativeValsVar;
        boolean bT;
        VarParameter<Boolean> bTVar;
        int i;
        String temp;
        
        if(GIS.getIsEmpty()) return;
        if(GIS.getMode() != TGIS_ViewerMode.Select) return;
        
        ptg = GIS.ScreenToMap(new TPoint(evt.getX(), evt.getY()));
        lp = (TGIS_LayerPixel)GIS.getItems().getItems(0);
        nativeVals = new double[50];
        bT = false;
        
        if(lp == null) return;
        
        rgbMappedVar = new VarParameter<>(rgbMapped);
        nativeValsVar = new VarParameter<>(nativeVals);
        bTVar = new VarParameter<>(bT);
        if(lp.Locate(ptg, rgbMappedVar, nativeValsVar, bTVar)){
            rgbMapped = rgbMappedVar.Value;
            nativeVals = nativeValsVar.Value;

            pChannelsColor.setBackground(new Color(rgbMapped.getR(), rgbMapped.getG() , rgbMapped.getB() ));
            lRGB.setText("RGB: " + rgbMapped.getR() + ", " + rgbMapped.getG() + ", " + rgbMapped.getB());
            temp = "";
            taOrginalColor.setText(temp);
            for(i = 0 ; i < nativeVals.length ; i++){
                temp = temp + "\nCH"+ i + " = " + nativeVals[i]; 
            }
            taOrginalColor.setText(temp);
        }
    }//GEN-LAST:event_GISMouseMoved

    private void sBrightnessStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sBrightnessStateChanged
        TGIS_LayerPixel lp;
        
        lp = (TGIS_LayerPixel)GIS.getItems().getItems(0);
        if(lp == null) return;
        
        lp.getParams().getPixel().setBrightness(sBrightness.getValue());
        GIS.InvalidateWholeMap();
    }//GEN-LAST:event_sBrightnessStateChanged

    private void btnImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImageActionPerformed
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/USA/States/California/San Bernardino/DOQ/37134877.jpg");
        sBrightness.setEnabled(true);
        sBrightness.setValue(0);
    }//GEN-LAST:event_btnImageActionPerformed

    private void btnGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGridActionPerformed
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/USA/States/California/San Bernardino/NED/w001001.adf");
        sBrightness.setEnabled(false);
        sBrightness.setValue(0);
    }//GEN-LAST:event_btnGridActionPerformed

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
            java.util.logging.Logger.getLogger(PixelLocateUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PixelLocateUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PixelLocateUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PixelLocateUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PixelLocateUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.JButton btnGrid;
    private javax.swing.JButton btnImage;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lRGB;
    private javax.swing.JPanel pChannelsColor;
    private javax.swing.JSlider sBrightness;
    private javax.swing.JTextArea taOrginalColor;
    private javax.swing.JLabel titleBrightness;
    private javax.swing.JLabel titleChanelsValue;
    private javax.swing.JLabel titleOrginalColor;
    // End of variables declaration//GEN-END:variables
}
