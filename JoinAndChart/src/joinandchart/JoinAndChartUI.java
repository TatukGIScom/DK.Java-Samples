package joinandchart;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import tatukgis.jdk.TGIS_BrushStyle;
import tatukgis.jdk.TGIS_ChartStyle;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_LabelPosition;
import tatukgis.jdk.TGIS_LayerSHP;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_Utils;
import static tatukgis.jdk.TGIS_Utils.ParamChart;
import tatukgis.jdk.TGIS_ViewerMode;

public class JoinAndChartUI extends javax.swing.JFrame {

    public JoinAndChartUI() throws SQLException {
        initComponents();
        initOwn();

    }

    private void initOwn() throws SQLException{
        TGIS_LayerSHP ll;
        conn = DriverManager.getConnection("jdbc:ucanaccess://" + TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/USA/Statistical/Stats.mdb;lockmdb=true;ignorecase=true", "","");
        s = conn.createStatement();
        
        ll = new TGIS_LayerSHP();
        ll.setPath(TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/USA/States/California/tl_2008_06_county.shp");
        ll.setName("tl_2008_06_county");
        
        ll.setUseConfig(false);
        ll.getParams().getLabels().setField("name");
        ll.getParams().getLabels().setPattern(TGIS_BrushStyle.Clear);
        ll.getParams().getLabels().setOutlineWidth(0);
        ll.getParams().getLabels().getFont().setColor(TGIS_Color.getWhite());
        ll.getParams().getLabels().setColor(TGIS_Color.getBlack());
        ll.getParams().getLabels().setPosition(TGIS_LabelPosition.MiddleCenter);
        
        GIS.Add(ll);
        GIS.FullExtent();
        GIS.setMode(TGIS_ViewerMode.Drag);
        cbChange(null);
   }
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        jToolBar1 = new javax.swing.JToolBar();
        btnFullExtent = new javax.swing.JButton();
        btnZoomIn = new javax.swing.JButton();
        btnZoomOut = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        cbSize = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        cbValue = new javax.swing.JComboBox<>();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        cbStyle = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JoinAndChart - TatukGIS DK11 sample");
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
        jToolBar1.add(jSeparator4);

        cbSize.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "pop2000", "male2000", "female2000", "under18", "asia", "black", "white", "hisp_lat" }));
        cbSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbChange(evt);
            }
        });
        jToolBar1.add(cbSize);
        jToolBar1.add(jSeparator2);

        cbValue.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "black:white", "pop2000:square_mil", "male2000:female2000" }));
        cbValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbChange(evt);
            }
        });
        jToolBar1.add(cbValue);
        jToolBar1.add(jSeparator3);

        cbStyle.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pie", "Bar" }));
        cbStyle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbChange(evt);
            }
        });
        jToolBar1.add(cbStyle);

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

    private void btnFullExtentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFullExtentActionPerformed
        if(GIS.getIsEmpty()) return;
        
        GIS.FullExtent();
    }//GEN-LAST:event_btnFullExtentActionPerformed

    private void btnZoomInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZoomInActionPerformed
        if(GIS.getIsEmpty()) return;
        
        GIS.setZoom(GIS.getZoom()*2);
    }//GEN-LAST:event_btnZoomInActionPerformed

    private void btnZoomOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZoomOutActionPerformed
        if(GIS.getIsEmpty()) return;
        
        GIS.setZoom(GIS.getZoom()/2);    }//GEN-LAST:event_btnZoomOutActionPerformed

    private void cbChange(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbChange
        TGIS_LayerVector    ll;
        String              vsize,
                            vvalues,
                            vstyle;
        Double              vmax,
                            vmin;
        ResultSet           rs ;
        PreparedStatement   ps;
        Statement           statement;
        try{
            ll = (TGIS_LayerVector)GIS.getItems().getItems(0);
            if(ll == null) return;

            vsize = cbSize.getSelectedItem().toString();
            vvalues = cbValue.getSelectedItem().toString();
            vstyle = cbStyle.getSelectedItem().toString();
            
            // find min, max values for shapes
            ps = conn.prepareStatement("SELECT min(" + vsize +") AS mini, max(" + vsize + ") AS maxi FROM ce2000t");
            rs = ps.executeQuery();
            rs.next();
            vmin = Double.parseDouble(rs.getString("mini"));
            vmax = Double.parseDouble(rs.getString("maxi"));
            rs.close();
            
            // let's load data to recordset
            ll.setJoinJDBCQuery("SELECT * FROM ce2000t ORDER BY fips");
            statement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery(ll.getJoinJDBCQuery());
            rs.next();

            // connect layer with query results
            ll.setJoinJDBC(rs);
            
            // set primary and foreign keys
            ll.setJoinPrimary("cntyidfp");
            ll.setJoinForeign("fips");
            
            // Set the chart style: "Pie" or "Bar"
            ll.getParams().getChart().setStyle(ParamChart(vstyle, TGIS_ChartStyle.Pie));
            
            // The chart size will be set by Render in the range of 350 to 1000
            // depending on the value of the "vsize" field
            ll.getParams().getChart().setSize(TGIS_Utils.GIS_RENDER_SIZE());
            ll.getParams().getRender().setStartSize(350);
            ll.getParams().getRender().setEndSize(1000);
            ll.getParams().getRender().setExpression(vsize);
            
            // The Renderer will create 10 zones to group field values,
            // starting from "vmin" and edning with "vmax"
            ll.getParams().getRender().setZones(10);
            ll.getParams().getRender().setMaxVal(vmax);
            ll.getParams().getRender().setMinVal(vmin);

            // For 'Bar' chart you can replace '0:0' by 'min:max' to set custom Y-axis limits.
            // 'vvalues' contains list of values displayed on the chart.
            // In this sample field names are used, e.g. 'male2000:female2000'.
            // Values need to be divided by a colon ':'.
            ll.getParams().getRender().setChart("0:0:" + vvalues);

            // If necessary, the chart can also be included in the legend
            ll.getParams().getChart().setLegend(ll.getParams().getRender().getChart());
            ll.getParams().getChart().setShowLegend(true);
                        
            GIS.InvalidateWholeMap();
        } catch (SQLException ex) {
            Logger.getLogger(JoinAndChartUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cbChange

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
            java.util.logging.Logger.getLogger(JoinAndChartUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JoinAndChartUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JoinAndChartUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JoinAndChartUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new JoinAndChartUI().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(JoinAndChartUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.JButton btnFullExtent;
    private javax.swing.JButton btnZoomIn;
    private javax.swing.JButton btnZoomOut;
    private javax.swing.JComboBox<String> cbSize;
    private javax.swing.JComboBox<String> cbStyle;
    private javax.swing.JComboBox<String> cbValue;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
    private Connection conn;
    private Statement s;
}
