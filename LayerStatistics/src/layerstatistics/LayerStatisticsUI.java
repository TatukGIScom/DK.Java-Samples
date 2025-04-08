package layerstatistics;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import RemObjects.Elements.System.VarParameter;
import tatukgis.jdk.TGIS_BusyEvent;
import tatukgis.jdk.TGIS_BusyEventArgs;
import tatukgis.jdk.TGIS_FieldInfo;
import tatukgis.jdk.TGIS_Layer;
import tatukgis.jdk.TGIS_LayerPixel;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_StatisticalFunctions;
import tatukgis.jdk.TGIS_StatisticsAbstract;
import tatukgis.jdk.TGIS_StatisticsItem;
import tatukgis.jdk.TGIS_StatisticsPixel;
import tatukgis.jdk.TGIS_StatisticsResult;
import tatukgis.jdk.TGIS_Utils;

public class LayerStatisticsUI extends javax.swing.JFrame {

    public LayerStatisticsUI() {
        initModels();
        initComponents();
        initCheckBoxList();
        initOwn();
    }
    
    @SuppressWarnings("unchecked")
    private void initModels(){
        mStats = new DefaultListModel();
        mStats.addElement(new CheckListItem("Count"));
        mStats.addElement(new CheckListItem("CountMissings"));
        mStats.addElement(new CheckListItem("Sum"));
        mStats.addElement(new CheckListItem("Average"));
        mStats.addElement(new CheckListItem("StandardDeviation"));
        mStats.addElement(new CheckListItem("Sample"));
        mStats.addElement(new CheckListItem("Variance"));
        mStats.addElement(new CheckListItem("Median"));
        mStats.addElement(new CheckListItem("Min"));
        mStats.addElement(new CheckListItem("Max"));
        mStats.addElement(new CheckListItem("Range"));
        mStats.addElement(new CheckListItem("Minority"));
        mStats.addElement(new CheckListItem("Majority"));
        mStats.addElement(new CheckListItem("Variety"));
        mStats.addElement(new CheckListItem("Unique"));
        
        mDefs = new DefaultListModel();        
    }
    
    @SuppressWarnings("unchecked")
    private void initCheckBoxList(){
        
        cblDefs.setCellRenderer(new CheckListRenderer());
        cblDefs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cblDefs.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                JList list;
                list = (JList) e.getSource();
                int index = list.locationToIndex(e.getPoint());// Get index of item
                                                                   // clicked
                CheckListItem item = (CheckListItem) list.getModel()
                    .getElementAt(index);
                item.setSelected(!item.isSelected()); // Toggle selected state
                list.repaint(list.getCellBounds(index, index));// Repaint cell 
            }
        });
                
        cblStats.setCellRenderer(new CheckListRenderer());
        cblStats.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cblStats.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                JList list;
                list = (JList) e.getSource();
                int index = list.locationToIndex(e.getPoint());// Get index of item
                                                                   // clicked
                CheckListItem item = (CheckListItem) list.getModel()
                    .getElementAt(index);
                item.setSelected(!item.isSelected()); // Toggle selected state
                list.repaint(list.getCellBounds(index, index));// Repaint cell 
            }
        });
    }
    
    private void initOwn(){
        String sample_dir;

        abrt = false;
        btnCalculate.setText(BUTTON_CALCULATE);


        //openDialog.Filter = TGIS_Utils.GisSupportedFiles( TGIS_FileType.Grid | TGIS_FileType.Pixel | TGIS_FileType.Vector , false );

        // set file paths
        sample_dir = TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/USA/States/California";
        sample_vector = sample_dir + "/Counties.shp";
        sample_grid = sample_dir + "/San Bernardino/NED/w001001.adf";
        sample_pixel = sample_dir + "/San Bernardino/DOQ/37134877.jpg";
        custom_path = "";

        // open sample vector layer
        rbVector.setSelected(true);
        rbVector.doClick();

        // set common functions
        checkPredefined(STATISTICS_STANDARD);    
        
        GIS.setBusyEvent(new TGIS_BusyEvent() {
            @Override
            public void Invoke(Object o, TGIS_BusyEventArgs tgsb) {
                doBusyEvent(o, tgsb);            }
        });
    }
    
    private void checkPredefined( String[] _predefined)
    {
        int id;
        CheckListItem itm;

        id = -1;
        
        for (int i = 0; i < mStats.getSize(); ++i)
        {
            itm = ((CheckListItem)mStats.getElementAt(i));
            itm.setSelected(false);
        }

        for(String stat_fun : _predefined)
        {
            Object[] itms ;
            itms = mStats.toArray();
            for(int j = 0 ; j < itms.length ; j++){
                if(((CheckListItem)itms[j]).label.equals(stat_fun)) {
                    id = j;
                }
            }
            if (id >= 0){
                itm = ((CheckListItem)mStats.getElementAt(id));
                itm.setSelected(true);
            }
        }
        cblStats.updateUI();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rbgLayer = new javax.swing.ButtonGroup();
        openDialog = new javax.swing.JFileChooser();
        pLayer = new javax.swing.JPanel();
        rbVector = new javax.swing.JRadioButton();
        rbGrid = new javax.swing.JRadioButton();
        rbPixel = new javax.swing.JRadioButton();
        rbCustom = new javax.swing.JRadioButton();
        btnOpen = new javax.swing.JButton();
        pStats = new javax.swing.JPanel();
        btnBasic = new javax.swing.JButton();
        btnStandard = new javax.swing.JButton();
        btnAll = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        cblStats = new javax.swing.JList();
        pDefs = new javax.swing.JPanel();
        btnSelect = new javax.swing.JButton();
        btnDeselect = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        cblDefs = new javax.swing.JList();
        pResults = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        taResult = new javax.swing.JTextArea();
        btnLoadStats = new javax.swing.JButton();
        btnSaveStats = new javax.swing.JButton();
        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        pProgress = new javax.swing.JProgressBar();
        btnCalculate = new javax.swing.JButton();
        chkFast = new javax.swing.JCheckBox();
        chkBessel = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LayerStatistics - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        pLayer.setBorder(javax.swing.BorderFactory.createTitledBorder("Select layer"));

        rbgLayer.add(rbVector);
        rbVector.setSelected(true);
        rbVector.setText("Vector");
        rbVector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbVectorActionPerformed(evt);
            }
        });

        rbgLayer.add(rbGrid);
        rbGrid.setText("Grid");
        rbGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbGridActionPerformed(evt);
            }
        });

        rbgLayer.add(rbPixel);
        rbPixel.setText("Pixel");
        rbPixel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbPixelActionPerformed(evt);
            }
        });

        rbgLayer.add(rbCustom);
        rbCustom.setText("Custom");
        rbCustom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbCustomActionPerformed(evt);
            }
        });

        btnOpen.setText("Open file...");
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pLayerLayout = new javax.swing.GroupLayout(pLayer);
        pLayer.setLayout(pLayerLayout);
        pLayerLayout.setHorizontalGroup(
            pLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pLayerLayout.createSequentialGroup()
                .addGroup(pLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pLayerLayout.createSequentialGroup()
                        .addComponent(rbCustom)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOpen))
                    .addComponent(rbVector)
                    .addComponent(rbGrid)
                    .addComponent(rbPixel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pLayerLayout.setVerticalGroup(
            pLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pLayerLayout.createSequentialGroup()
                .addComponent(rbVector)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbGrid)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbPixel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbCustom)
                    .addComponent(btnOpen))
                .addContainerGap())
        );

        pStats.setBorder(javax.swing.BorderFactory.createTitledBorder("Select statistics"));

        btnBasic.setText("Basic");
        btnBasic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBasicActionPerformed(evt);
            }
        });

        btnStandard.setText("Standard");
        btnStandard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStandardActionPerformed(evt);
            }
        });

        btnAll.setText("All");
        btnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAllActionPerformed(evt);
            }
        });

        cblStats.setModel(mStats);
        jScrollPane1.setViewportView(cblStats);

        javax.swing.GroupLayout pStatsLayout = new javax.swing.GroupLayout(pStats);
        pStats.setLayout(pStatsLayout);
        pStatsLayout.setHorizontalGroup(
            pStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pStatsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(pStatsLayout.createSequentialGroup()
                        .addComponent(btnBasic, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnStandard)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAll)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pStatsLayout.setVerticalGroup(
            pStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pStatsLayout.createSequentialGroup()
                .addGroup(pStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBasic)
                    .addComponent(btnStandard)
                    .addComponent(btnAll))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                .addContainerGap())
        );

        pDefs.setBorder(javax.swing.BorderFactory.createTitledBorder("Select band"));

        btnSelect.setText("Select all");
        btnSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectActionPerformed(evt);
            }
        });

        btnDeselect.setText("Deselect all");
        btnDeselect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeselectActionPerformed(evt);
            }
        });

        cblDefs.setModel(mDefs
        );
        jScrollPane2.setViewportView(cblDefs);

        javax.swing.GroupLayout pDefsLayout = new javax.swing.GroupLayout(pDefs);
        pDefs.setLayout(pDefsLayout);
        pDefsLayout.setHorizontalGroup(
            pDefsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pDefsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pDefsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(pDefsLayout.createSequentialGroup()
                        .addComponent(btnSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDeselect, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pDefsLayout.setVerticalGroup(
            pDefsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pDefsLayout.createSequentialGroup()
                .addGroup(pDefsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSelect)
                    .addComponent(btnDeselect))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                .addContainerGap())
        );

        pResults.setBorder(javax.swing.BorderFactory.createTitledBorder("Results"));

        taResult.setColumns(20);
        taResult.setRows(5);
        jScrollPane3.setViewportView(taResult);

        btnLoadStats.setText("Load *.ttkstats");
        btnLoadStats.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadStatsActionPerformed(evt);
            }
        });

        btnSaveStats.setText("Save *.ttkstats");
        btnSaveStats.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveStatsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pResultsLayout = new javax.swing.GroupLayout(pResults);
        pResults.setLayout(pResultsLayout);
        pResultsLayout.setHorizontalGroup(
            pResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pResultsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                    .addGroup(pResultsLayout.createSequentialGroup()
                        .addComponent(btnLoadStats)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSaveStats)))
                .addContainerGap())
        );
        pResultsLayout.setVerticalGroup(
            pResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pResultsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLoadStats)
                    .addComponent(btnSaveStats))
                .addContainerGap())
        );

        GIS.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        GIS.setMode(tatukgis.jdk.TGIS_ViewerMode.Zoom);

        btnCalculate.setText("Calculate statistics");
        btnCalculate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalculateActionPerformed(evt);
            }
        });

        chkFast.setSelected(true);
        chkFast.setText("Fast statistics");

        chkBessel.setText("Use Bessel's correction");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(pDefs, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pStats, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pLayer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCalculate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chkFast, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(chkBessel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pProgress, javax.swing.GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE)
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pResults, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pResults, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(pLayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pStats, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pDefs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(chkBessel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(chkFast))
                            .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCalculate)
                            .addComponent(pProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void disableOpenButton()
    {
        btnOpen.setEnabled( false );
    }

    private void enableOpenButton()
    {
        btnOpen.setEnabled( true );
    }

    private void rbVectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbVectorActionPerformed
        disableOpenButton();
        openLayerAndStats(sample_vector);
    }//GEN-LAST:event_rbVectorActionPerformed

    private void rbGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbGridActionPerformed
        disableOpenButton();
        openLayerAndStats(sample_grid);
    }//GEN-LAST:event_rbGridActionPerformed

    private void rbPixelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbPixelActionPerformed
        disableOpenButton();
        openLayerAndStats(sample_pixel);
    }//GEN-LAST:event_rbPixelActionPerformed

    private void rbCustomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCustomActionPerformed
        enableOpenButton();

        if( !custom_path.isEmpty())
        {
            openLayerAndStats(custom_path);
        }
        else
        {
            openFile();
        }
    }//GEN-LAST:event_rbCustomActionPerformed

    private void openFile()
    {
        if( openDialog.showOpenDialog(this)== JFileChooser.APPROVE_OPTION)
        {
            custom_path = openDialog.getSelectedFile().getAbsolutePath();
            openLayerAndStats(custom_path);
        }else
        {
            if(custom_path.isEmpty())
            {
                rbVector.setSelected(true);
            }
        }
    }
    
    private void btnBasicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBasicActionPerformed
        checkPredefined(STATISTICS_BASIC);
    }//GEN-LAST:event_btnBasicActionPerformed

    private void btnStandardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStandardActionPerformed
        checkPredefined(STATISTICS_STANDARD);
    }//GEN-LAST:event_btnStandardActionPerformed

    private void btnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAllActionPerformed
        for (int i = 0; i < mStats.getSize(); i++){
            ((CheckListItem)mStats.getElementAt(i)).setSelected(true);
        }
        cblStats.updateUI();
    }//GEN-LAST:event_btnAllActionPerformed

    private void btnSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectActionPerformed
        for (int i = 0; i < mDefs.getSize(); i++){
            ((CheckListItem)mDefs.getElementAt(i)).setSelected(true);
        }
        cblDefs.updateUI();
    }//GEN-LAST:event_btnSelectActionPerformed

    private void btnDeselectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeselectActionPerformed
        for (int i = 0; i < mDefs.getSize(); i++){
            ((CheckListItem)mDefs.getElementAt(i)).setSelected(false);
        }
        cblDefs.updateUI();
    }//GEN-LAST:event_btnDeselectActionPerformed

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        openFile();
    }//GEN-LAST:event_btnOpenActionPerformed

    private void btnLoadStatsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadStatsActionPerformed
        TGIS_Layer ll;
        
        ll = getLayer();
        if(! ll.getStatistics().LoadFromFile()){
            JOptionPane.showMessageDialog(this, "Loading failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        showResults(ll.getStatistics(), true);
    }//GEN-LAST:event_btnLoadStatsActionPerformed

    private void btnSaveStatsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveStatsActionPerformed
        TGIS_Layer ll;
        
        ll = getLayer();
        ll.getStatistics().SaveToFile();
    }//GEN-LAST:event_btnSaveStatsActionPerformed

    private Boolean prepareFunctions(VarParameter<TGIS_StatisticalFunctions> _funcs)
    {
        Boolean res;

        res = false;
        _funcs.Value = TGIS_StatisticalFunctions.EmptyStatistics();

        for(int i = 0; i < mStats.getSize() - 1; i++)
        {
            if (((CheckListItem)mStats.getElementAt(i)).isSelected)
            {
                res = true;
                if ("Average".equals(((CheckListItem)mStats.getElementAt(i)).label)) {
                    _funcs.Value.Average = true;
                } else if ("Count".equals(((CheckListItem)mStats.getElementAt(i)).label)) {
                    _funcs.Value.Count = true;
                } else if ("CountMissings".equals(((CheckListItem)mStats.getElementAt(i)).label)) {
                    _funcs.Value.CountMissings = true;
                } else if ("Max".equals(((CheckListItem)mStats.getElementAt(i)).label)) {
                    _funcs.Value.Max = true;
                } else if ("Majority".equals(((CheckListItem)mStats.getElementAt(i)).label)) {
                    _funcs.Value.Majority = true;
                } else if ("Median".equals(((CheckListItem)mStats.getElementAt(i)).label)) {
                    _funcs.Value.Median = true;
                } else if ("Min".equals(((CheckListItem)mStats.getElementAt(i)).label)) {
                    _funcs.Value.Min = true;
                } else if ("Minority".equals(((CheckListItem)mStats.getElementAt(i)).label)) {
                    _funcs.Value.Minority = true;
                } else if ("Range".equals(((CheckListItem)mStats.getElementAt(i)).label)) {
                    _funcs.Value.Range = true;
                } else if ("StandardDeviation".equals(((CheckListItem)mStats.getElementAt(i)).label)) {
                    _funcs.Value.StandardDeviation = true;   
                } else if ("Sample".equals(((CheckListItem)mStats.getElementAt(i)).label)){
                    _funcs.Value.Sample = true ;
                } else if ("Sum".equals(((CheckListItem)mStats.getElementAt(i)).label)) {
                    _funcs.Value.Sum = true;
                } else if ("Variance".equals(((CheckListItem)mStats.getElementAt(i)).label)) {
                    _funcs.Value.Variance = true;
                } else if ("Variety".equals(((CheckListItem)mStats.getElementAt(i)).label)) {
                    _funcs.Value.Variety = true;
                } else if ("Unique".equals(((CheckListItem)mStats.getElementAt(i)).label)) {
                    _funcs.Value.Unique = true;
                }
            }
        }

        return res;
    }
    
    private void btnCalculateActionPerformed(java.awt.event.ActionEvent evt) {                                             
        TGIS_Layer ll;
        VarParameter<TGIS_StatisticalFunctions> funcs;
        TGIS_StatisticsAbstract stats;

        //cancel calculation
        if( btnCalculate.getText().equals(BUTTON_CANCEL))
        {
            abrt = true;
            btnCalculate.setText( BUTTON_CALCULATE );
            return;
        }

        btnCalculate.setText( BUTTON_CANCEL );
        btnCalculate.updateUI();
        abrt = false;

        // statistical functions
        funcs = new VarParameter<>();
        funcs.Value = new TGIS_StatisticalFunctions(false);
        if (!prepareFunctions(funcs))
        {
            JOptionPane.showMessageDialog(this, "Select at least one statistical function.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //creating statistical engine
        ll = (TGIS_Layer)GIS.getItems().getItems(0);
        ll.getStatistics().Reset();

        if (ll.getStatistics() == null)
            return;


        // use Bessel's correction
        // if True, calculate "sample" standard devation and variance;
        // if False, calculate "population" version (this is default)
        ll.getStatistics().setUseBesselCorrection( chkBessel.isSelected() );

        // collect statistics definitions (fields or bands)
        for(int i = 0; i < mDefs.getSize(); i++)
        {
            if (((CheckListItem)mDefs.getElementAt(i)).isSelected) {
                ll.getStatistics().Add( ((CheckListItem)mDefs.getElementAt(i)).label, funcs.Value );
            }
        }

        if( ll.getStatistics().getDefinedResults().isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Select at least one field for vector layer or band for pixel layer.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // here the calculations starts
        new Thread(new Runnable() {
            @Override
            public void run() {
                // statistics class can calaculate statistics for a given extent;
                // for filtering data: "_extent", "_shape", "_de9im" and only for vector layers:
                // "_query", "_useSelected" parameters can be used
                
                // "Fast Statistics" works only for pixel layers (bu default);
                // big pixel layers are resampled to avoid long calculation;
                // the results are approximate with high accuracy
                ll.getStatistics().Calculate(GIS.getVisibleExtent(), null, "", chkFast.isSelected());
                
                btnCalculate.setText( BUTTON_CALCULATE );

                // print results on TMemo control
                showResults( ll.getStatistics(), true );
            }
        }).start();
    }                                            

    private void rbVectorItemStateChanged(java.awt.event.ItemEvent evt) {                                          
        disableOpenButton();
        openLayerAndStats(sample_vector);
    }                                         

    @SuppressWarnings("unchecked")
    private void prepareStatisticsDefinitions( TGIS_Layer layer ){
        TGIS_LayerVector lv;
        TGIS_LayerPixel lp;
        CheckListItem itm;

        mDefs.removeAllElements();

        if(layer instanceof TGIS_LayerVector)
        {
            lv = (TGIS_LayerVector)layer;
            ((TitledBorder)pDefs.getBorder()).setTitle("Select fields");
            
            
            // fill with virtual field names
            itm = new CheckListItem("GIS_UID" );
            mDefs.addElement( itm ) ;
            itm.setSelected(true);
            itm = new CheckListItem("GIS_AREA" );
            mDefs.addElement( itm ) ;
            itm.setSelected(true);
            itm = new CheckListItem("GIS_LENGTH" );
            mDefs.addElement( itm ) ;
            itm.setSelected(true);
            itm = new CheckListItem("GIS_COORD_Z" );
            mDefs.addElement( itm ) ;
            itm.setSelected(true);

            // fill with layer field names
            for( TGIS_FieldInfo field : lv.getFields())
            {
                itm = new CheckListItem(field.getName());
                mDefs.addElement(itm);
                itm.setSelected(true);
            }
        }
        else if(layer instanceof TGIS_LayerPixel)
        {
            lp = (TGIS_LayerPixel)layer;
            ((TitledBorder)pDefs.getBorder()).setTitle("Select bands");

            // fill with appropriate band names
            if (lp.IsGrid())
            {
                itm = new CheckListItem(GIS_BAND_DEFAULT);
                mDefs.addElement(itm);
            }
            
            for(int i = 1;  i <= lp.getBandsCount(); i++){
                itm = new CheckListItem(i);
                mDefs.addElement(itm);
            }
                
            
            if (lp.IsGrid())
            {
                itm = new CheckListItem(GIS_BAND_GRID);
                mDefs.addElement(itm);
                itm.setSelected(true);
            }
            else
            {
                itm = new CheckListItem(GIS_BAND_A) ;
                mDefs.addElement(itm);
                itm = new CheckListItem(GIS_BAND_R) ;
                mDefs.addElement(itm);
                itm.setSelected(true);
                itm = new CheckListItem(GIS_BAND_G) ;
                mDefs.addElement(itm);
                itm.setSelected(true);
                itm = new CheckListItem(GIS_BAND_B) ;
                mDefs.addElement(itm);
                itm.setSelected(true);
                itm = new CheckListItem(GIS_BAND_H) ;
                mDefs.addElement(itm);
                itm = new CheckListItem(GIS_BAND_S) ;
                mDefs.addElement(itm);
                itm = new CheckListItem(GIS_BAND_L) ;
                mDefs.addElement(itm);
            }
        }
    }
    
    private void showResults(TGIS_StatisticsAbstract _stats_engine, boolean _clear )
    {
        final String DASHED_LINE = "----------------------------------------";

        TGIS_StatisticsResult stats_result;
        ArrayList<TGIS_StatisticsItem> stats_available;
        String node_string;
        String txt;
        
        if(_clear){
            taResult.removeAll();
        }
        
        txt = "";
        
        for(String stats_name : _stats_engine.getAvailableResults())
        {
            txt += DASHED_LINE + "\r\n";
            txt += stats_name + "\r\n";
            txt += DASHED_LINE + "\r\n";

            stats_result = _stats_engine.Get(stats_name);
            stats_available = stats_result.getAvailableStatistics();
            for (TGIS_StatisticsItem stats_item : stats_available)
            {
                node_string = ("    + " + stats_item.getName() + " = " + stats_item.ToString());
                txt += node_string + "\r\n";
            }
        }
        
        taResult.setText(txt);
        
        if(_stats_engine.getObsolete() && 
                (_stats_engine.getAge().compareTo(new GregorianCalendar(1899, 12, 30)) > 0 )){
            JOptionPane.showMessageDialog(this, "Statistics are outdated.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void doBusyEvent(Object o, TGIS_BusyEventArgs tgsb){
        if (tgsb.getPos() < 0) {
            pProgress.setValue(0);
        } else
        if (tgsb.getPos() == 0)
        {
            pProgress.setMinimum(0);
            pProgress.setMaximum(100);
            pProgress.setValue(0);
        }
        else
        {
            if (tgsb.getPos() > 0) {
                pProgress.setValue((int)tgsb.getPos());
            }
        }
        
        tgsb.setAbort(abrt);
    }
    
    private TGIS_Layer getLayer(){
        return (TGIS_Layer)GIS.getItems().getItems(0);
    }
    
    private void openLayerAndStats( String path )
    {
        TGIS_Layer ll;

        GIS.Open(path);
        ll = getLayer();
        prepareStatisticsDefinitions(ll);
        showResults(ll.getStatistics(), true);
    }    
    
    //Own implementation of checkboxlist
    class CheckListItem {

        private String label;
        private boolean isSelected = false;

        public CheckListItem(String label) {
          this.label = label;
        }

        private CheckListItem(char c) {
          this.label = Character.toString(c) ;
        }

        private CheckListItem(int i) {
          this.label = String.valueOf(i);
        }

        public boolean isSelected() {
          return isSelected;
        }

        public void setSelected(boolean isSelected) {
          this.isSelected = isSelected;
        }

        @Override
        public String toString() {
          return label;
        }
    }

    class CheckListRenderer extends JCheckBox implements ListCellRenderer {
      public Component getListCellRendererComponent(JList list, Object value,
          int index, boolean isSelected, boolean hasFocus) {
        setEnabled(list.isEnabled());
        setSelected(((CheckListItem) value).isSelected());
        setFont(list.getFont());
        setBackground(list.getBackground());
        setForeground(list.getForeground());
        setText(value.toString());
        return this;
      }
    }

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
            java.util.logging.Logger.getLogger(LayerStatisticsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LayerStatisticsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LayerStatisticsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LayerStatisticsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LayerStatisticsUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.JButton btnAll;
    private javax.swing.JButton btnBasic;
    private javax.swing.JButton btnCalculate;
    private javax.swing.JButton btnDeselect;
    private javax.swing.JButton btnLoadStats;
    private javax.swing.JButton btnOpen;
    private javax.swing.JButton btnSaveStats;
    private javax.swing.JButton btnSelect;
    private javax.swing.JButton btnStandard;
    private javax.swing.JList cblDefs;
    private javax.swing.JList cblStats;
    private javax.swing.JCheckBox chkBessel;
    private javax.swing.JCheckBox chkFast;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JFileChooser openDialog;
    private javax.swing.JPanel pDefs;
    private javax.swing.JPanel pLayer;
    private javax.swing.JProgressBar pProgress;
    private javax.swing.JPanel pResults;
    private javax.swing.JPanel pStats;
    private javax.swing.JRadioButton rbCustom;
    private javax.swing.JRadioButton rbGrid;
    private javax.swing.JRadioButton rbPixel;
    private javax.swing.JRadioButton rbVector;
    private javax.swing.ButtonGroup rbgLayer;
    private javax.swing.JTextArea taResult;
    // End of variables declaration//GEN-END:variables
    private Boolean abrt;
    private String sample_vector;
    private String sample_grid;
    private String sample_pixel;
    private String custom_path;
    // list models 
    private DefaultListModel mStats;
    private DefaultListModel mDefs;
    // band names
    private static final String GIS_BAND_DEFAULT = "0";
    private static final String GIS_BAND_GRID = "Value";
    private static final String GIS_BAND_A = "A";
    private static final String GIS_BAND_R = "R";
    private static final String GIS_BAND_G = "G";
    private static final String GIS_BAND_B = "B";
    private static final String GIS_BAND_H = "H";
    private static final String GIS_BAND_S = "S";
    private static final String GIS_BAND_L = "L";
    // buttons captions
    private static final String BUTTON_CALCULATE = "Calcualte statistics";
    private static final String BUTTON_CANCEL = "Cancel";
    // predefined sets of statistical functions
    // find in documentation: GisStatisticsAll, GisStatisticsBasic, GisStatisticsStandard
    private static final String[] STATISTICS_BASIC = {
        "Average",
        "Count",
        "Max",
        "Min",
        "Sum"
    };
    private static final String[] STATISTICS_STANDARD = {
        "Average",
        "Count",
        "CountMissings",
        "Max",
        "Median",
        "Min",
        "Range",
        "StandardDeviation",
        "Sum",
        "Variance"
    };        
}
