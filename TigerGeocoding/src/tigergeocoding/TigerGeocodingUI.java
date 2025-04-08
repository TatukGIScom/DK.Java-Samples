package tigergeocoding;

import RemObjects.Elements.System.VarParameter;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import tatukgis.jdk.TGIS_BusyEvent;
import tatukgis.jdk.TGIS_BusyEventArgs;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_Geocoding;
import tatukgis.jdk.TGIS_LabelAlignment;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_Lock;
import tatukgis.jdk.TGIS_PenStyle;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_ShapeType;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.rtl.TObjectList;

public class TigerGeocodingUI extends javax.swing.JFrame {

    public TigerGeocodingUI() {
        initComponents();
        initOwn();
    }

    @SuppressWarnings("unchecked")
    private void initOwn(){
        GIS_Scale.setVisible(false);
        infoFields = new ArrayList<>();
        infoFields.add("HOUSENUMBER");
        infoFields.add("DIRPREFIX");
        infoFields.add("STREETNAME");
        infoFields.add("DIRSUFFIX");
        infoFields.add("STREETTYPE");
        fieldNames = new ArrayList<>();
        fieldNames.add("FULLNAME");
        fieldNames.add("LFROMADD");
        fieldNames.add("LTOADD");
        fieldNames.add("RFROMADD");
        fieldNames.add("RTOADD");
        fieldNames.add("ZIPL");
        fieldNames.add("ZIPR");  
        
        selectedRow = -1;
        state = -1;
        frmMatches = new MatchesUI();
        
        lstModel = new DefaultListModel();
        lstMemo.setModel(lstModel);
    }
    
    private void openCovarage(String _path){
        busy = new TGIS_BusyEvent() {
            @Override
            public void Invoke(Object o, TGIS_BusyEventArgs tgsb) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        
        if(layerResult != null){
            GIS.Delete(layerResult.getName());
            layerResult = null;
        }
        if(geoObj != null){
            geoObj.Dispose();
            geoObj = null;
        }
        if(layerSrc != null){
            GIS.Close();
        }
        
        btnFirst.setEnabled(false);
        btnAll.setEnabled(false);
        btnHelp.setEnabled(false);
        btnMatches.setEnabled(false);
        
        GIS.Lock();
        GIS.setBusyEvent(busy);
        GIS.Open((_path));
        GIS.setBusyEvent(null);
        
        layerSrc = (TGIS_LayerVector)GIS.getItems().getItems(0);
        if(layerSrc == null) return;
        layerSrc.getParams().getLine().setSmartSize(-1);
        layerSrc.getParams().getLabels().setField("FULLNAME");
        layerSrc.getParams().getLabels().setAlignment(TGIS_LabelAlignment.Follow);
        layerSrc.getParams().getLabels().setColor(TGIS_Color.getBlack());
        
        layerSrc.getParamsList().Add();
        layerSrc.getParams().setQuery("MTFCC < 'S1400'");
        layerSrc.getParams().getLine().setWidth(-2);
        layerSrc.getParams().getLine().setStyle(TGIS_PenStyle.Solid);
        layerSrc.setUseConfig(false);
        layerSrc.setCS(GIS.getCS());
        
        layerResult = new TGIS_LayerVector();
        layerResult.setUseConfig(false);
        layerResult.getParams().getLine().setColor(TGIS_Color.getRed());
        layerResult.getParams().getLine().setWidth(-2);
        layerResult.getParams().getMarker().setOutlineWidth(1);
        layerResult.setName("RouteDisplay");
        layerResult.setCS(GIS.getCS());
        
        GIS.Add(layerResult);
        
        geoObj = new TGIS_Geocoding(layerSrc);
        geoObj.setOffset(0.0001);
        geoObj.LoadFormulas(TGIS_Utils.GisSamplesDataDirDownload() + "/samples/Geocoding/us_addresses.geo", 
                TGIS_Utils.GisSamplesDataDirDownload() + "/samples/Geocoding/tiger2008.geo",
                "");
        
        GIS.Unlock();
        GIS.FullExtent();
        
        GIS_Scale.setVisible(true);
        
        btnFirst.setEnabled(true);
        btnAll.setEnabled(true);
        btnHelp.setEnabled(true);
        
        lstModel.clear();
        state = -1;
        selectedRow = -1;
    }
    
    @SuppressWarnings("unchecked")
    private void findAddress(boolean _findFirst, boolean _extentScope){
        int i,j;
        int r = -1;
        TGIS_Shape shp;
        String s;
        
        if(geoObj == null){
            JOptionPane.showMessageDialog(this, "Open a TIGER/Line file.");
            return;
        }
        
        layerResult.RevertAll();
        lstModel.clear();
        state = -1;
        selectedRow = -1;
        btnMatches.setEnabled(false);
        
        try{
            r = parse(_findFirst, _extentScope) - 1;
        }catch(Exception _e){
            JOptionPane.showMessageDialog(this, "Exception: " + _e.getMessage());
        }
        
        if(r < 0){
            tfAddress.setText(tfAddress.getText() + " ???");
        }else{
            tfAddress.setText(geoObj.getQuery(0));
            if(_findFirst){
                state = 0;
            }else{
                state = 1;
            }
        }
        
        for(i = 0; i <= r; i++){
            shp = layerSrc.GetShape(geoObj.getUid(i));
            layerResult.AddShape(shp);
            
            if(i == 0)
                layerResult.setExtent(shp.getExtent());
            
            if(_findFirst){
                if(i == 0){
                    for(j = 0; j < fieldNames.size(); j++){
                        s = shp.GetField(fieldNames.get(j)).toString();
                        lstModel.addElement(fieldNames.get(j) + " = " + s);
                    }
                }
            }else{
                lstModel.addElement(geoObj.getQuery(i));
            }
            
            shp = layerSrc.GetShape(geoObj.getUidEx(i));
            
            if(shp != null){
                layerResult.AddShape(shp);
                if(_findFirst){
                    if(i == 0){
                        lstModel.addElement("---------------------------");                                
                        for(j = 0; j < fieldNames.size(); j++){
                            s = shp.GetField(fieldNames.get(j).toString()).toString();
                            lstModel.addElement(fieldNames.get(j) + s);                           
                        }
                    }
                }
            }
            
            shp = layerResult.CreateShape(TGIS_ShapeType.Point);
            shp.Lock(TGIS_Lock.Extent);
            shp.AddPart();
            shp.AddPoint(geoObj.getPoint(i));
            shp.getParams().getMarker().setColor(TGIS_Color.getYellow());
            shp.Unlock();
        }
        
        GIS.Lock();
        GIS.setVisibleExtent(layerResult.getExtent());
        GIS.setZoom(0.7 * GIS.getZoom());
        GIS.Unlock();
    }
    
    private int parse(boolean _findFirst, boolean _extendedScope){
        int res = 0;
        VarParameter<TObjectList<Object>> resolvedAddresses = new VarParameter<>();
        VarParameter<TObjectList<Object>> resolvedAddresses2 = new VarParameter<>(); 
        
        try{
            if(geoObj.Match(tfAddress.getText(), resolvedAddresses, resolvedAddresses2)){
                frmMatches.ShowMatches(resolvedAddresses.Value,
                        resolvedAddresses2.Value);
                res = geoObj.ParseEx(resolvedAddresses.Value, resolvedAddresses2.Value, 
                        _findFirst, _extendedScope, true);
                btnMatches.setEnabled(true);
            }
        }finally{
            resolvedAddresses  = null;
            resolvedAddresses2 = null;
        }
        
        return res;
    }
    
    private void showInfo(){
        TGIS_Shape shp;
        
        if(layerSrc == null) return;
        if(selectedRow == -1) return;
        
        shp = layerSrc.GetShape(geoObj.getUid(selectedRow));
        GIS.setVisibleExtent(shp.getExtent());
        GIS.setZoom(0.7 * GIS.getZoom());
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fcOpen = new javax.swing.JFileChooser();
        jToolBar1 = new javax.swing.JToolBar();
        btnDefault = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnOpen = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tfAddress = new javax.swing.JTextField();
        chkExtended = new javax.swing.JCheckBox();
        btnHelp = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnAll = new javax.swing.JButton();
        btnMatches = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstMemo = new javax.swing.JList<>();
        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        GIS_Scale = new tatukgis.jdk.TGIS_ControlScale();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TigerGeocoding - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));

        jToolBar1.setRollover(true);

        btnDefault.setText("Open default");
        btnDefault.setFocusable(false);
        btnDefault.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDefault.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDefault.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDefaultActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDefault);
        jToolBar1.add(jSeparator1);

        btnOpen.setText("Open");
        btnOpen.setFocusable(false);
        btnOpen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOpen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });
        jToolBar1.add(btnOpen);
        jToolBar1.add(jSeparator2);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Find Address(es):");

        chkExtended.setText("Exact street- a. city names");

        btnHelp.setText("Help");
        btnHelp.setEnabled(false);
        btnHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHelpActionPerformed(evt);
            }
        });

        btnFirst.setText("Find First");
        btnFirst.setEnabled(false);
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnAll.setText("Find All");
        btnAll.setEnabled(false);
        btnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAllActionPerformed(evt);
            }
        });

        btnMatches.setText("Matches");
        btnMatches.setEnabled(false);
        btnMatches.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMatchesActionPerformed(evt);
            }
        });

        lstMemo.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstMemoValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(lstMemo);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(tfAddress)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(chkExtended)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(btnHelp))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnFirst)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMatches, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkExtended)
                    .addComponent(btnHelp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst)
                    .addComponent(btnAll)
                    .addComponent(btnMatches))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                .addContainerGap())
        );

        GIS.add(GIS_Scale);
        GIS_Scale.setBounds(20, 10, 185, 40);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDefaultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDefaultActionPerformed
        openCovarage(TGIS_Utils.GisSamplesDataDirDownload() + 
                "/World/Countries/USA/States/California/San Bernardino/TIGER/tl_2008_06071_edges_trunc.shp");
    }//GEN-LAST:event_btnDefaultActionPerformed

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        if(fcOpen.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            GIS.Open(fcOpen.getSelectedFile().toString());        
    }//GEN-LAST:event_btnOpenActionPerformed

    private void btnHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHelpActionPerformed
        if(help == null){
            help = new HelpUI();
            help.setVisible(true);
        }else{
            help.setVisible(true);
        }
    }//GEN-LAST:event_btnHelpActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        findAddress(true, !chkExtended.isSelected());
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAllActionPerformed
        findAddress(false, !chkExtended.isSelected());
    }//GEN-LAST:event_btnAllActionPerformed

    private void btnMatchesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMatchesActionPerformed
        frmMatches.setVisible(true);
    }//GEN-LAST:event_btnMatchesActionPerformed

    private void lstMemoValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstMemoValueChanged
        boolean canSelect;
        
        canSelect = (lstMemo.getSelectedIndex() < lstModel.getSize()) && 
                !((lstModel.getSize() == 1) &&
                ("".equals(lstModel.get(lstMemo.getSelectedIndex()).toString())));
        
        if(canSelect && (state == 1)){
            selectedRow = lstMemo.getSelectedIndex();
            showInfo();
        }
    }//GEN-LAST:event_lstMemoValueChanged

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
            java.util.logging.Logger.getLogger(TigerGeocodingUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TigerGeocodingUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TigerGeocodingUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TigerGeocodingUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TigerGeocodingUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private tatukgis.jdk.TGIS_ControlScale GIS_Scale;
    private javax.swing.JButton btnAll;
    private javax.swing.JButton btnDefault;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnHelp;
    private javax.swing.JButton btnMatches;
    private javax.swing.JButton btnOpen;
    private javax.swing.JCheckBox chkExtended;
    private javax.swing.JFileChooser fcOpen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JList<String> lstMemo;
    private javax.swing.JTextField tfAddress;
    // End of variables declaration//GEN-END:variables
    private ArrayList<String> infoFields;
    private ArrayList<String> fieldNames;
    private int selectedRow;
    private int state;
    private boolean doAbort;
    private boolean fShown;
    private MatchesUI frmMatches;
    private TGIS_LayerVector layerSrc;
    private TGIS_LayerVector layerResult;
    private TGIS_Geocoding geoObj;
    private TGIS_BusyEvent busy;
    private HelpUI help;
    private DefaultListModel lstModel;

}
