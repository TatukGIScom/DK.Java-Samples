package bitmapfill;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import tatukgis.jdk.TGIS_Bitmap;
import tatukgis.jdk.TGIS_BrushStyle;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_LayerSHP;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_ShapeEvent;
import tatukgis.jdk.TGIS_ShapeEventArgs;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.jdk.TGIS_ViewerMode;

public class BitmapFillUI extends javax.swing.JFrame {

    public BitmapFillUI() {
        initComponents();
        try {
            initImagePanels();
        } catch (IOException ex) {
            Logger.getLogger(BitmapFillUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        initOwn();
    }

    private void initImagePanels() throws IOException {
        imagePanel2 = new ImagePanel("/resources/2.png");
        imagePanel3 = new ImagePanel("/resources/3.png");
        imagePanel4 = new ImagePanel("/resources/4.png");
        imagePanel1 = new ImagePanel("/resources/1.png");
        imagePanel5 = new ImagePanel("/resources/5.png");

        imagePanel2.setMaximumSize(new java.awt.Dimension(100, 100));

        javax.swing.GroupLayout imagePanel2Layout = new javax.swing.GroupLayout(imagePanel2);
        imagePanel2.setLayout(imagePanel2Layout);
        imagePanel2Layout.setHorizontalGroup(
                imagePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 100, Short.MAX_VALUE)
        );
        imagePanel2Layout.setVerticalGroup(
                imagePanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 100, Short.MAX_VALUE)
        );

        imagePanel3.setMaximumSize(new java.awt.Dimension(100, 100));
        imagePanel3.setMinimumSize(new java.awt.Dimension(100, 100));

        javax.swing.GroupLayout imagePanel3Layout = new javax.swing.GroupLayout(imagePanel3);
        imagePanel3.setLayout(imagePanel3Layout);
        imagePanel3Layout.setHorizontalGroup(
                imagePanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 100, Short.MAX_VALUE)
        );
        imagePanel3Layout.setVerticalGroup(
                imagePanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 100, Short.MAX_VALUE)
        );

        imagePanel4.setMaximumSize(new java.awt.Dimension(100, 100));
        imagePanel4.setMinimumSize(new java.awt.Dimension(100, 100));

        javax.swing.GroupLayout imagePanel4Layout = new javax.swing.GroupLayout(imagePanel4);
        imagePanel4.setLayout(imagePanel4Layout);
        imagePanel4Layout.setHorizontalGroup(
                imagePanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 100, Short.MAX_VALUE)
        );
        imagePanel4Layout.setVerticalGroup(
                imagePanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 100, Short.MAX_VALUE)
        );

        imagePanel1.setMaximumSize(new java.awt.Dimension(100, 100));
        imagePanel1.setMinimumSize(new java.awt.Dimension(100, 100));

        javax.swing.GroupLayout imagePanel1Layout = new javax.swing.GroupLayout(imagePanel1);
        imagePanel1.setLayout(imagePanel1Layout);
        imagePanel1Layout.setHorizontalGroup(
                imagePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 100, Short.MAX_VALUE)
        );
        imagePanel1Layout.setVerticalGroup(
                imagePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 100, Short.MAX_VALUE)
        );

        imagePanel5.setMaximumSize(new java.awt.Dimension(100, 100));
        imagePanel5.setMinimumSize(new java.awt.Dimension(100, 100));

        javax.swing.GroupLayout imagePanel5Layout = new javax.swing.GroupLayout(imagePanel5);
        imagePanel5.setLayout(imagePanel5Layout);
        imagePanel5Layout.setHorizontalGroup(
                imagePanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 100, Short.MAX_VALUE)
        );
        imagePanel5Layout.setVerticalGroup(
                imagePanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(imagePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(imagePanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(imagePanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(imagePanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(imagePanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(imagePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(imagePanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(imagePanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(imagePanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(imagePanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 245, Short.MAX_VALUE))
        );

    }

    private void initOwn() {
        TGIS_LayerSHP ll;
        TGIS_Bitmap bmp;

        bmp = new TGIS_Bitmap();

        ll = new TGIS_LayerSHP();
        ll.setPath(TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/USA/States/California/Counties.shp");
        ll.setUseConfig(false);
        ll.setName("counties");

        cbLabels.setSelectedIndex(0);
        cbStats.setSelectedIndex(0);

        
        ll.addPaintShapeEvent(new TGIS_ShapeEvent() {
            @Override
            public void Invoke(Object o, TGIS_ShapeEventArgs tgs) {
                double population;
                double area;
                double factor;
                BufferedImage oldBitmap;
                TGIS_Shape shp = tgs.getShape();

                //calculate paremeters
                population = Double.parseDouble(shp.GetField("population").toString());
                area = Double.parseDouble(shp.GetField("area").toString());

                if (area == 0) {
                    return;
                }
                
                
                oldBitmap = null;
                if(shp.getParams().getArea().getBitmap() != null) {
                    if (!shp.getParams().getArea().getBitmap().getIsEmpty()) {
                        oldBitmap = (BufferedImage) shp.getParams().getArea().getBitmap().getNativeBitmap();
                    }
                }

                shp.getParams().getArea().setBitmap(new TGIS_Bitmap());
                shp.getParams().getArea().setPattern(TGIS_BrushStyle.Solid);
                shp.getParams().getArea().setColor(TGIS_Color.getRed());
                shp.getParams().getArea().setOutlineColor(TGIS_Color.getDimGray());
                shp.getParams().getArea().setOutlineWidth(20);

                if (cbStats.getSelectedIndex() == 1) {
                    factor = population / area;
                    if (factor < 1) {
                        shp.getParams().getArea().getBitmap().setNativeBitmap(imagePanel1.getImage());
                    } else if (factor < 7) {
                        shp.getParams().getArea().getBitmap().setNativeBitmap(imagePanel2.getImage());
                    } else if (factor < 52) {
                        shp.getParams().getArea().getBitmap().setNativeBitmap(imagePanel3.getImage());
                    } else if (factor < 380) {
                        shp.getParams().getArea().getBitmap().setNativeBitmap(imagePanel4.getImage());
                    } else if (factor < 3000) {
                        shp.getParams().getArea().getBitmap().setNativeBitmap(imagePanel5.getImage());
                    }
                } else {
                    factor = population;
                    // assing different bitmaps for factor value
                    if (factor < 5000) {
                        shp.getParams().getArea().getBitmap().setNativeBitmap(imagePanel1.getImage());
                    } else if (factor < 22000) {
                        shp.getParams().getArea().getBitmap().setNativeBitmap(imagePanel2.getImage());
                    } else if (factor < 104000) {
                        shp.getParams().getArea().getBitmap().setNativeBitmap(imagePanel3.getImage());
                    } else if (factor < 478000) {
                        shp.getParams().getArea().getBitmap().setNativeBitmap(imagePanel4.getImage());
                    } else if (factor < 2186000) {
                        shp.getParams().getArea().getBitmap().setNativeBitmap(imagePanel5.getImage());
                    }
                }

                shp.Draw();

                shp.getParams().getArea().getBitmap().setNativeBitmap(oldBitmap);
            }
        });

        GIS.Add(ll);
        GIS.setMode(TGIS_ViewerMode.Drag);
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

        jToolBar1 = new javax.swing.JToolBar();
        btnFullExtent = new javax.swing.JButton();
        btnZoomIn = new javax.swing.JButton();
        btnZoomOut = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        cbStats = new javax.swing.JComboBox<>();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        cbLabels = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JToolBar.Separator();

        jPanel1 = new javax.swing.JPanel();
        GIS = new tatukgis.jdk.TGIS_ViewerWnd();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("BitmapFill - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));

        jToolBar1.setFloatable(false);
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

        cbStats.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "By population", "By density" }));
        cbStats.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStatsActionPerformed(evt);
            }
        });
        jToolBar1.add(cbStats);
        jToolBar1.add(jSeparator3);

        cbLabels.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No Labels", "By FIPS", "By NAME" }));
        cbLabels.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbLabelsActionPerformed(evt);
            }
        });
        jToolBar1.add(cbLabels);
        jToolBar1.add(jSeparator2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 122, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 779, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 814, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbLabelsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbLabelsActionPerformed
        TGIS_LayerVector lv;

        lv = (TGIS_LayerVector) GIS.Get("counties");

        if (lv != null) {
            switch (cbLabels.getSelectedIndex()) {
                default:
                    lv.getParams().getLabels().setField("");
                    break;
                case 1:
                    lv.getParams().getLabels().setField("CNTYIDFP");
                    break;
                case 2:
                    lv.getParams().getLabels().setField("NAME");
                    break;
            }
        }

        GIS.InvalidateWholeMap();

    }//GEN-LAST:event_cbLabelsActionPerformed

    private void cbStatsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStatsActionPerformed
        GIS.InvalidateWholeMap();
    }//GEN-LAST:event_cbStatsActionPerformed

    private void btnFullExtentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFullExtentActionPerformed
        if (GIS.getIsEmpty()) {
            return;
        }
        GIS.FullExtent();
    }//GEN-LAST:event_btnFullExtentActionPerformed

    private void btnZoomInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZoomInActionPerformed
        if (GIS.getIsEmpty()) {
            return;
        }
        GIS.setZoom(GIS.getZoom() * 2);
    }//GEN-LAST:event_btnZoomInActionPerformed

    private void btnZoomOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZoomOutActionPerformed
        if (GIS.getIsEmpty()) {
            return;
        }
        GIS.setZoom(GIS.getZoom() / 2);
    }//GEN-LAST:event_btnZoomOutActionPerformed

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
            java.util.logging.Logger.getLogger(BitmapFillUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BitmapFillUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BitmapFillUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BitmapFillUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BitmapFillUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.JButton btnFullExtent;
    private javax.swing.JButton btnZoomIn;
    private javax.swing.JButton btnZoomOut;
    private javax.swing.JComboBox<String> cbLabels;
    private javax.swing.JComboBox<String> cbStats;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
    private bitmapfill.ImagePanel imagePanel1;
    private bitmapfill.ImagePanel imagePanel2;
    private bitmapfill.ImagePanel imagePanel3;
    private bitmapfill.ImagePanel imagePanel4;
    private bitmapfill.ImagePanel imagePanel5;
}
