package twowindows;

import java.awt.Toolkit;
import tatukgis.jdk.PaintEvent;
import tatukgis.jdk.PaintListener;
import tatukgis.jdk.RenderEvent;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.rtl.TNotifyEvent;

public class TwoWindowsUI extends javax.swing.JFrame {
    boolean bSentinel = false ;
    
    public TwoWindowsUI() {
        initComponents();
        initOwn();
    }

    private void initOwn(){
        GIS1.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/Poland/DCW/poland.ttkproject", true);
        GIS2.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/Poland/DCW/poland.ttkproject", true);
        GIS1.setZoom(GIS1.getZoom() * 3);
        GIS2.setZoom(GIS2.getZoom() * 4);
        
        TNotifyEvent GIS1AfterVisibleExtentChange = new TNotifyEvent() {
            @Override
            public void Invoke(Object o) {
                if( bSentinel ) 
                    return ;
                bSentinel = true ;
                
                GIS2.Lock();
                GIS2.setCenter(GIS1.getCenter());
                
                if(cbxZoom.isSelected())
                    GIS2.setZoom(GIS1.getZoom());
                
                GIS2.Unlock();
                
                bSentinel =  false ;
            }
        };
        
        TNotifyEvent GIS2AfterVisibleExtentChange = new TNotifyEvent() {
            @Override
            public void Invoke(Object o) {
                if( bSentinel ) 
                    return ;
                bSentinel = true ;
                
                GIS1.Lock();
                GIS1.setCenter(GIS2.getCenter());
                
                if(cbxZoom.isSelected())
                    GIS1.setZoom(GIS2.getZoom());
                
                GIS1.Unlock();
                
                bSentinel =  false ;
            }
        };
        
        GIS2.setVisibleExtentChangeEvent(GIS2AfterVisibleExtentChange);
        GIS1.setVisibleExtentChangeEvent(GIS1AfterVisibleExtentChange);

    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbxZoom = new javax.swing.JCheckBox();
        jSplitPane2 = new javax.swing.JSplitPane();
        GIS1 = new tatukgis.jdk.TGIS_ViewerWnd();
        GIS2 = new tatukgis.jdk.TGIS_ViewerWnd();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TwoWindows - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(800, 600));

        cbxZoom.setText("Keep Zoom");

        jSplitPane2.setBorder(null);
        jSplitPane2.setDividerLocation(400);
        jSplitPane2.setDividerSize(10);
        jSplitPane2.setResizeWeight(0.5);

        GIS1.setMode(tatukgis.jdk.TGIS_ViewerMode.Zoom);
        GIS1.setPreferredSize(new java.awt.Dimension(390, 570));
        jSplitPane2.setLeftComponent(GIS1);

        GIS2.setMode(tatukgis.jdk.TGIS_ViewerMode.Zoom);
        jSplitPane2.setRightComponent(GIS2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(cbxZoom)
                .addGap(0, 728, Short.MAX_VALUE))
            .addComponent(jSplitPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(cbxZoom)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 572, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


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
            java.util.logging.Logger.getLogger(TwoWindowsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TwoWindowsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TwoWindowsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TwoWindowsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TwoWindowsUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS1;
    private tatukgis.jdk.TGIS_ViewerWnd GIS2;
    private javax.swing.JCheckBox cbxZoom;
    private javax.swing.JSplitPane jSplitPane2;
    // End of variables declaration//GEN-END:variables
}
