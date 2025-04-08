package buffers2;

import java.awt.Toolkit;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_LayerAbstract;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_Topology;
import tatukgis.jdk.TGIS_Utils;

public class Buffers2UI extends javax.swing.JFrame {

    public Buffers2UI() {
        initComponents();
        initOwn();
    }
    
    private void initOwn(){
        TGIS_LayerAbstract la;
        TGIS_LayerVector lb;
        
        la = TGIS_Utils.GisCreateLayer("counties", TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/USA/States/California/Counties.shp");
        GIS.Lock();
        GIS.Add(la);
        
        lb = new TGIS_LayerVector();
        lb.setName("buffer");
        lb.setTransparency(70);
        lb.getParams().getArea().setColor(TGIS_Color.getYellow());
        lb.setCS(GIS.getCS());
        GIS.Add(lb);
        GIS.Unlock();
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
        scroll = new javax.swing.JScrollPane();
        info = new javax.swing.JTextArea();
        jToolBar1 = new javax.swing.JToolBar();
        btnMinus = new javax.swing.JButton();
        slider = new javax.swing.JSlider();
        btnPlus = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Buffer2 - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(800, 600));

        info.setColumns(20);
        info.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        info.setRows(5);
        scroll.setViewportView(info);

        jToolBar1.setRollover(true);

        btnMinus.setText("-");
        btnMinus.setFocusable(false);
        btnMinus.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMinus.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMinus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMinusActionPerformed(evt);
            }
        });
        jToolBar1.add(btnMinus);

        slider.setMaximum(200);
        slider.setToolTipText("");
        slider.setValue(0);
        slider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderStateChanged(evt);
            }
        });
        jToolBar1.add(slider);

        btnPlus.setText("+");
        btnPlus.setFocusable(false);
        btnPlus.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPlus.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPlus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlusActionPerformed(evt);
            }
        });
        jToolBar1.add(btnPlus);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 600, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scroll)
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMinusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMinusActionPerformed
    if(evt.getSource() == btnMinus){
            if(slider.getValue() > slider.getMinimum()){
               slider.setValue(slider.getValue()-5);
            }
        }
    }//GEN-LAST:event_btnMinusActionPerformed

    private void sliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderStateChanged
        TGIS_LayerVector ll,
                         lb;
        TGIS_Shape       shp,
                         tmp,
                         buf;
        TGIS_Topology    tpl;
        ll = (TGIS_LayerVector)GIS.Get("counties");
        if(ll == null) return;
        lb = (TGIS_LayerVector)GIS.Get("buffer");
        if(lb == null) return;
        
        shp = ll.FindFirst(TGIS_Utils.GisWholeWorld(), "NAME='Merced'");
        if(shp == null) return;
        
        tpl = new TGIS_Topology();
        try{
            lb.RevertShapes();
            tmp = tpl.MakeBuffer(shp, (double)slider.getValue()/100);
            if(tmp != null){
                buf = lb.AddShape(tmp);
                tmp = null;
            }else 
                buf = null;
        }finally{
            tpl = null;
        }
    
        if(buf == null) return;
        
        ll = (TGIS_LayerVector)GIS.Get("counties");
        
        if(ll == null) return;

        ll.setIgnoreShapeParams(false);

        infoText = null;
        info.setText(infoText);
        
        tmp = ll.FindFirst(buf.getExtent());
        while(tmp != null){
            if(buf.IsCommonPoint(tmp)){
                tmp = tmp.MakeEditable();
                if(infoText == null) { 
                    infoText = tmp.GetField("name") + "\n";
                } else {
                    infoText += tmp.GetField("name") + "\n";
                }
                info.setText(infoText);
                tmp.getParams().getArea().setColor(TGIS_Color.getBlue());
            }
            tmp = ll.FindNext();
        }
        GIS.InvalidateWholeMap();
    }//GEN-LAST:event_sliderStateChanged

    private void btnPlusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlusActionPerformed
    if(evt.getSource() == btnPlus){
            if(slider.getValue() < slider.getMaximum()){
                slider.setValue(slider.getValue()+5);
            }
        }
    }//GEN-LAST:event_btnPlusActionPerformed

    public static void main(String args[]) {
        /* Set the Windows look and feel */
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
            java.util.logging.Logger.getLogger(Buffers2UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Buffers2UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Buffers2UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Buffers2UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
                new Buffers2UI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.JButton btnMinus;
    private javax.swing.JButton btnPlus;
    private javax.swing.JTextArea info;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JScrollPane scroll;
    private javax.swing.JSlider slider;
    // End of variables declaration//GEN-END:variables
    private String infoText;
}
