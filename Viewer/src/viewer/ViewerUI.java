package viewer;

import java.awt.Color;
import java.awt.Toolkit;
import java.io.File;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import tatukgis.jdk.PaintEvent;
import tatukgis.jdk.PaintListener;
import tatukgis.jdk.RenderEvent;
import tatukgis.jdk.TGIS_CSCoordinateSystem;
import tatukgis.jdk.TGIS_CSFactory;
import tatukgis.jdk.TGIS_CompressionType;
import tatukgis.jdk.TGIS_ControlCSSystem;
import tatukgis.jdk.TGIS_ControlLegendMode;
import tatukgis.jdk.TGIS_Extent;
import tatukgis.jdk.TGIS_FileType;
import tatukgis.jdk.TGIS_Layer;
import tatukgis.jdk.TGIS_LayerPixel;
import tatukgis.jdk.TGIS_LayerPixelSubFormat;
import tatukgis.jdk.TGIS_PixelExportManager;
import tatukgis.jdk.TGIS_PixelFormat;
import tatukgis.jdk.TGIS_PixelSubFormat;
import tatukgis.jdk.TGIS_Point;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.jdk.TGIS_ViewerBmp;
import tatukgis.jdk.TGIS_ViewerMode;
import tatukgis.rtl.TPoint;

public class ViewerUI extends javax.swing.JFrame {
    private String nameOfFile = "No file opened";
    private String filePath ;
    private DetailsUI attr;
    private EditUI edit;
    private ExportUI export;
    private SearchUI search;

    public ViewerUI() {
        initComponents();
        initOwn();
    }
    private void initOwn(){
        String supportedFiles;
        String supportedLayers;
        String supportedExports;
                
        supportedFiles = TGIS_Utils.GisSupportedFiles(TGIS_FileType.All, false) ;
        supportedLayers = TGIS_Utils.GisSupportedFiles(TGIS_FileType.All, false) ;
        supportedExports = "BMP_.bmp-JPG_.jpg-PNG_.png-TIF_.tif";
        
        ccColor = new JColorChooser(Color.white);
        
        for(String tmp : (supportedFiles.split("_"))){
            fcOpen.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
                @Override
                public boolean accept(File file) {
                    String tmp2;
                    tmp2 = "";
                    if(file.isFile())
                        tmp2 = getFileExtension(file);
                    if(tmp.contains(tmp2))
                        return true ;
                    else
                        return false;
                }
                @Override
                public String getDescription() {
                    return tmp.split("-")[0];
                }
            });
        }
        for(String tmp : (supportedLayers.split("_"))){
            fcAddLayer.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
                @Override
                public boolean accept(File file) {
                    String tmp2;
                    tmp2 = "";
                    if(file.isFile())
                        tmp2 = getFileExtension(file);
                    if(tmp.contains(tmp2))
                        return true ;
                    else
                        return false;
                }
                @Override
                public String getDescription() {
                    return tmp.split("-")[0];
                }
            });
        }
        for(String tmp : (supportedExports.split("-"))){
            fcExport.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
                @Override
                public boolean accept(File file) {
                    if((tmp.split("_")[1]).contains(getFileExtension(file)))
                        return true;
                    else
                        return false;
                }

                @Override
                public String getDescription() {
                    return tmp.split("_")[0];
                }
            });                
        }
        
        ccColor.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                GIS.setBackground(ccColor.getSelectionModel().getSelectedColor());
            }
        });

    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fcAddLayer = new javax.swing.JFileChooser();
        fcOpen = new javax.swing.JFileChooser();
        ccColor = new javax.swing.JColorChooser();
        fcExport = new javax.swing.JFileChooser();
        infoLabel = new javax.swing.JPanel();
        info = new javax.swing.JTextArea();
        jToolBar1 = new javax.swing.JToolBar();
        btnOpen = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        btnFileEdit = new javax.swing.JButton();
        btnSaveToImage = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnFullExtent = new javax.swing.JButton();
        btnZoomMode = new javax.swing.JButton();
        btnDragMode = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btnSelect = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        btnSaveAll = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        btnLegendMode = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        btnCS = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        GIS_Legend = new tatukgis.jdk.TGIS_ControlLegend();
        jMenuBar1 = new javax.swing.JMenuBar();
        file = new javax.swing.JMenu();
        miOpen = new javax.swing.JMenuItem();
        miAdd = new javax.swing.JMenuItem();
        miEditConfig = new javax.swing.JMenuItem();
        miExport = new javax.swing.JMenuItem();
        miPrint = new javax.swing.JMenuItem();
        miClose = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        miExit = new javax.swing.JMenuItem();
        view = new javax.swing.JMenu();
        miFullExtent = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        miInfo = new javax.swing.JMenuItem();
        miZoom = new javax.swing.JMenuItem();
        miDrag = new javax.swing.JMenuItem();
        options = new javax.swing.JMenu();
        miUseRTree = new javax.swing.JCheckBoxMenuItem();
        miColor = new javax.swing.JMenuItem();
        Searchmenu = new javax.swing.JMenu();
        miSearch = new javax.swing.JMenuItem();

        fcAddLayer.setDialogTitle("Add layer");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Viewer - TatukGIS DK11 sample");
        setBackground(new java.awt.Color(255, 255, 255));
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(1000, 800));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        info.setBackground(new java.awt.Color(240, 240, 240));
        info.setColumns(20);
        info.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        info.setRows(5);

        javax.swing.GroupLayout infoLabelLayout = new javax.swing.GroupLayout(infoLabel);
        infoLabel.setLayout(infoLabelLayout);
        infoLabelLayout.setHorizontalGroup(
            infoLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(info)
        );
        infoLabelLayout.setVerticalGroup(
            infoLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoLabelLayout.createSequentialGroup()
                .addComponent(info, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Open.png"))); // NOI18N
        btnOpen.setToolTipText("Open");
        btnOpen.setFocusable(false);
        btnOpen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOpen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });
        jToolBar1.add(btnOpen);

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/LayerAdd.png"))); // NOI18N
        btnAdd.setToolTipText("Add Layer");
        btnAdd.setFocusable(false);
        btnAdd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAdd.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAdd);

        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Close.png"))); // NOI18N
        btnClose.setToolTipText("Close");
        btnClose.setFocusable(false);
        btnClose.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnClose.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        jToolBar1.add(btnClose);

        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Print.png"))); // NOI18N
        btnPrint.setToolTipText("Not implemented yet");
        btnPrint.setEnabled(false);
        btnPrint.setFocusable(false);
        btnPrint.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPrint.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnPrint);

        btnFileEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Edit.png"))); // NOI18N
        btnFileEdit.setToolTipText("Edit");
        btnFileEdit.setFocusable(false);
        btnFileEdit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFileEdit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnFileEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFileEditActionPerformed(evt);
            }
        });
        jToolBar1.add(btnFileEdit);

        btnSaveToImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Export.png"))); // NOI18N
        btnSaveToImage.setToolTipText("Save image");
        btnSaveToImage.setFocusable(false);
        btnSaveToImage.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSaveToImage.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSaveToImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveToImageActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSaveToImage);
        jToolBar1.add(jSeparator3);

        btnFullExtent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/FullExtent.png"))); // NOI18N
        btnFullExtent.setToolTipText("Full extent");
        btnFullExtent.setFocusable(false);
        btnFullExtent.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFullExtent.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnFullExtent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFullExtentActionPerformed(evt);
            }
        });
        jToolBar1.add(btnFullExtent);

        btnZoomMode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Zoom.png"))); // NOI18N
        btnZoomMode.setToolTipText("Zoom");
        btnZoomMode.setFocusable(false);
        btnZoomMode.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnZoomMode.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnZoomMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZoomModeActionPerformed(evt);
            }
        });
        jToolBar1.add(btnZoomMode);

        btnDragMode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Drag.png"))); // NOI18N
        btnDragMode.setToolTipText("Drag");
        btnDragMode.setFocusable(false);
        btnDragMode.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDragMode.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDragMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDragModeActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDragMode);
        jToolBar1.add(jSeparator4);

        btnSelect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/SelectLocate.png"))); // NOI18N
        btnSelect.setToolTipText("Select");
        btnSelect.setFocusable(false);
        btnSelect.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSelect.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSelect);

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Search.png"))); // NOI18N
        btnSearch.setToolTipText("Search");
        btnSearch.setFocusable(false);
        btnSearch.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSearch.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnSearch);

        btnSaveAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/SaveAll.png"))); // NOI18N
        btnSaveAll.setToolTipText("Save all");
        btnSaveAll.setFocusable(false);
        btnSaveAll.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSaveAll.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSaveAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveAllActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSaveAll);
        jToolBar1.add(jSeparator5);

        btnLegendMode.setText("Layers Mode");
        btnLegendMode.setToolTipText("Not implemented yet");
        btnLegendMode.setFocusable(false);
        btnLegendMode.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLegendMode.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLegendMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLegendModeActionPerformed(evt);
            }
        });
        jToolBar1.add(btnLegendMode);
        jToolBar1.add(jSeparator6);

        btnCS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/ExtentLayer.png"))); // NOI18N
        btnCS.setToolTipText("CS");
        btnCS.setFocusable(false);
        btnCS.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCS.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCSActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCS);
        jToolBar1.add(jSeparator7);

        GIS.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                GISMouseMoved(evt);
            }
        });
        GIS.addPaintListener(new tatukgis.jdk.PaintListener() {
            public void BeforePaint(tatukgis.jdk.PaintEvent evt) {
            }
            public void AfterPaint(tatukgis.jdk.PaintEvent evt) {
            }
            public void BeforePaintRenderer(tatukgis.jdk.RenderEvent evt) {
            }
            public void AfterPaintRenderer(tatukgis.jdk.RenderEvent evt) {
            }
            public void PaintExtra(tatukgis.jdk.RenderEvent evt) {
                GISPaintExtra(evt);
            }
        });
        GIS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                GISMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                GISMouseReleased(evt);
            }
        });

        GIS_Legend.setGIS_Viewer(GIS);

        file.setText("File");

        miOpen.setText("Open");
        miOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miOpenActionPerformed(evt);
            }
        });
        file.add(miOpen);

        miAdd.setText("Add");
        miAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miAddActionPerformed(evt);
            }
        });
        file.add(miAdd);

        miEditConfig.setText("Edit project/config file");
        miEditConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miEditConfigActionPerformed(evt);
            }
        });
        file.add(miEditConfig);

        miExport.setText("Export");
        miExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miExportActionPerformed(evt);
            }
        });
        file.add(miExport);

        miPrint.setText("Print");
        miPrint.setToolTipText("Not implemented yet");
        miPrint.setEnabled(false);
        file.add(miPrint);

        miClose.setText("Close");
        miClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miCloseActionPerformed(evt);
            }
        });
        file.add(miClose);
        file.add(jSeparator1);

        miExit.setText("Exit");
        miExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miExitActionPerformed(evt);
            }
        });
        file.add(miExit);

        jMenuBar1.add(file);

        view.setText("View");

        miFullExtent.setText("Full Extent");
        view.add(miFullExtent);
        view.add(jSeparator2);

        miInfo.setText("Info");
        miInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miInfoActionPerformed(evt);
            }
        });
        view.add(miInfo);

        miZoom.setText("Zoom");
        miZoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miZoomActionPerformed(evt);
            }
        });
        view.add(miZoom);

        miDrag.setText("Drag");
        miDrag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDragActionPerformed(evt);
            }
        });
        view.add(miDrag);

        jMenuBar1.add(view);

        options.setText("Options");

        miUseRTree.setSelected(true);
        miUseRTree.setText("Use R-tree");
        miUseRTree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miUseRTreeActionPerformed(evt);
            }
        });
        options.add(miUseRTree);

        miColor.setText("Color");
        miColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miColorActionPerformed(evt);
            }
        });
        options.add(miColor);

        jMenuBar1.add(options);

        Searchmenu.setText("Search");

        miSearch.setText("Find shape");
        miSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miSearchActionPerformed(evt);
            }
        });
        Searchmenu.add(miSearch);

        jMenuBar1.add(Searchmenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(infoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(GIS_Legend, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 748, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GIS_Legend, javax.swing.GroupLayout.DEFAULT_SIZE, 842, Short.MAX_VALUE)
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(infoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
    
    private void open(){
        int returnVal = fcOpen.showOpenDialog(GIS);
        if(returnVal == JFileChooser.APPROVE_OPTION){
        filePath = fcOpen.getSelectedFile().toString();

        GIS.Open(filePath);
        GIS.InvalidateWholeMap();
        GIS.FullExtent();
        nameOfFile = fcOpen.getName(new File(filePath));
        }
    }
    
    private void close(){
        if(GIS.getIsEmpty()) return;
        GIS.Close();        
    }
    
    private void selectMode(){
        if(GIS.getIsEmpty()) return;
        GIS.setMode(TGIS_ViewerMode.Select);        
    }
    
    private void dragMode(){
        if(GIS.getIsEmpty()) return;
        GIS.setMode(TGIS_ViewerMode.Drag);        
    }
    
    private void zoomMode(){
        if(GIS.getIsEmpty()) return;
        GIS.setMode(TGIS_ViewerMode.Zoom);
    }
    
    private void fullExtent(){
        if(GIS.getIsEmpty()) return;
        GIS.FullExtent();        
    }
    
    private void addLayer(){
        int i;
        String name;
        TGIS_Layer ll;
        
        int returnVal = fcAddLayer.showOpenDialog(GIS);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            String filePath = fcAddLayer.getSelectedFile().toString();

            ll = TGIS_Utils.GisCreateLayer(fcAddLayer.getName(new File(fcAddLayer.getSelectedFile().toString())),
                    fcAddLayer.getSelectedFile().toString());
            if(ll != null){
                ll.ReadConfig();
                GIS.Add(ll);
                GIS.InvalidateWholeMap();
            }
            GIS.setUseRTree(miUseRTree.isSelected());
        }
    }
    
    private void editFile(){
        if(edit == null){
            edit = new EditUI(filePath); 
            edit.setVisible(true);
            edit.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        }else{
            edit.setPath(filePath);
        }
        edit.setVisible(true);
    }
    
    private void exportToImage(){
        TGIS_PixelExportManager pem;
        TGIS_LayerPixel lp;
        int w,h;
        int dpi;
        TGIS_LayerPixelSubFormat sf;
        TGIS_ViewerBmp vbmp;
        String path, ext;
        int sub;
        int comp;
        TGIS_Extent ex;
        
        ex = GIS.getExtent();
        w = 1024;
        h = (int)Math.round((ex.YMax - ex.YMin) / (ex.XMax - ex.XMin) * w);
        ex = TGIS_Utils.GisExtent(ex.XMin, ex.YMin, ex.XMax, ex.YMin + ((ex.XMax - ex.XMin) / w) * h);        
        dpi = 96;
     
        int returnVal = fcExport.showSaveDialog(GIS);

        if(returnVal == JFileChooser.APPROVE_OPTION){
            path = fcExport.getSelectedFile().toString();
            switch(fcExport.getFileFilter().getDescription().toUpperCase()){
                case "BMP": ext = ".bmp"; sub = TGIS_PixelSubFormat.BMP ; comp = TGIS_CompressionType.None ; break;
                case "JPG": ext = ".jpg"; sub = TGIS_PixelSubFormat.JPEG ; comp = TGIS_CompressionType.JPEG ; break;
                case "TIF": ext = ".tif"; sub = TGIS_PixelSubFormat.None ; comp = TGIS_CompressionType.PNG ; break;
                case "PNG": ext = ".png"; sub = TGIS_PixelSubFormat.PNG ; comp = TGIS_CompressionType.None ; break;
                default: ext = ""; sub= TGIS_PixelSubFormat.None ; comp = TGIS_CompressionType.None ;
            }
            
            if(path.lastIndexOf(".") != -1 && path.lastIndexOf(".") != 0)
                path += ext;            
            
            lp = (TGIS_LayerPixel)TGIS_Utils.GisCreateLayer(path, path);
            sf = new TGIS_LayerPixelSubFormat(TGIS_PixelFormat.RGB, false, sub, comp, 90);
            lp.Build(lp.getPath(), false, GIS.getCS(), ex, w, h, sf);

            pem = new TGIS_PixelExportManager(lp);
            vbmp = new TGIS_ViewerBmp();
            vbmp.addPaintListener(new PaintListener() {
                @Override
                public void BeforePaint(PaintEvent pe) {

                }

                @Override
                public void BeforePaintRenderer(RenderEvent re) {

                }

                @Override
                public void AfterPaint(PaintEvent pe) {

                }

                @Override
                public void AfterPaintRenderer(RenderEvent re) {

                }

                @Override
                public void PaintExtra(RenderEvent re) {
                    GISPaintExtra( re );
                }
            });
            
            pem.ExportFrom(GIS, vbmp, ex, dpi);

            vbmp = null;
            pem = null;
            lp.SaveData();
            lp = null;        
        }
    }
    
    private void CS(){
        TGIS_ControlCSSystem dlg;
        
        dlg = new TGIS_ControlCSSystem();
        dlg.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        
        dlg.Execute(GIS.getCS());
        GIS.setCS(dlg.getCS());
        GIS.InvalidateWholeMap();
    }
    
    private void miOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miOpenActionPerformed
        if(evt.getSource() == miOpen)
            open();
    }//GEN-LAST:event_miOpenActionPerformed

    private void miCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miCloseActionPerformed
        if(evt.getSource() == miClose){
            close();
        }
    }//GEN-LAST:event_miCloseActionPerformed

    private void miInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miInfoActionPerformed
        if(evt.getSource() == miInfo){
            selectMode();
        }
    }//GEN-LAST:event_miInfoActionPerformed

    private void miDragActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDragActionPerformed
        if(evt.getSource() == miDrag){
            dragMode();
        }
    }//GEN-LAST:event_miDragActionPerformed

    private void miZoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miZoomActionPerformed
        if(evt.getSource() == miZoom){
            zoomMode();
        }
    }//GEN-LAST:event_miZoomActionPerformed
    //own rounding function
    public static double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    long factor = (long) Math.pow(10, places);
    value = value * factor;
    long tmp = Math.round(value);
    return (double) tmp / factor;
    }
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int answer ;
        boolean canClose;
        canClose = true;

        if(GIS.MustSave()){
            canClose = false;
            answer = JOptionPane.showOptionDialog(  null, 
                                                    "Save changes before exit?", 
                                                    "Save changes?", 
                                                    JOptionPane.YES_NO_CANCEL_OPTION, 
                                                    JOptionPane.QUESTION_MESSAGE, 
                                                    null, 
                                                    null, 
                                                    null
                                                  );
            //"Yes" - save all changes
            if(answer == 0){
                GIS.SaveAll();
                canClose = true ;
            //"No" - close operation
            }else if(answer == 1){
                canClose = true ;
            //"Cancel" - Cancel close operation   
            }else if(answer == 2){
                canClose = false ;
            }          
        }else{
            canClose = true;
        }
        if(!canClose)
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        else
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }//GEN-LAST:event_formWindowClosing

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        open();
    }//GEN-LAST:event_btnOpenActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        addLayer();
    }//GEN-LAST:event_btnAddActionPerformed

    private void miAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miAddActionPerformed
        addLayer();
    }//GEN-LAST:event_miAddActionPerformed

    private void miEditConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miEditConfigActionPerformed
        editFile();
    }//GEN-LAST:event_miEditConfigActionPerformed

    private void btnFileEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFileEditActionPerformed
        editFile();
    }//GEN-LAST:event_btnFileEditActionPerformed

    private void miExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miExportActionPerformed
       
        if(export == null){
            export = new ExportUI(GIS); 
            export.setVisible(true);
            export.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        }
        export.setGIS(GIS);
        export.setVisible(true);
        
    }//GEN-LAST:event_miExportActionPerformed

    private void btnSaveToImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveToImageActionPerformed
        exportToImage();
    }//GEN-LAST:event_btnSaveToImageActionPerformed

    private void btnFullExtentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFullExtentActionPerformed
        if(GIS.getIsEmpty()) return;
        fullExtent();
    }//GEN-LAST:event_btnFullExtentActionPerformed

    private void btnZoomModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZoomModeActionPerformed
        if(GIS.getIsEmpty()) return;
        zoomMode();
    }//GEN-LAST:event_btnZoomModeActionPerformed

    private void btnDragModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDragModeActionPerformed
        if(GIS.getIsEmpty()) return;
        dragMode();
    }//GEN-LAST:event_btnDragModeActionPerformed

    private void btnSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectActionPerformed
        if(GIS.getIsEmpty()) return;
        selectMode();
    }//GEN-LAST:event_btnSelectActionPerformed

    private void miUseRTreeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miUseRTreeActionPerformed
        GIS.setUseRTree(miUseRTree.isSelected());
        GIS.InvalidateWholeMap();
    }//GEN-LAST:event_miUseRTreeActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        if(GIS.getIsEmpty())return;
        close();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnSaveAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveAllActionPerformed
        GIS.SaveAll();
    }//GEN-LAST:event_btnSaveAllActionPerformed

    private void btnCSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCSActionPerformed
        CS();
    }//GEN-LAST:event_btnCSActionPerformed

    private void miColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miColorActionPerformed
        Color c = ccColor.showDialog(GIS, "Choose background color", Color.white);
        GIS.setBackground(c);
        GIS.InvalidateWholeMap();
    }//GEN-LAST:event_miColorActionPerformed

    private void miExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_miExitActionPerformed

    private void miSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miSearchActionPerformed
        if(search == null){
            search = new SearchUI(GIS); 
            search.setVisible(true);
            search.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        }
        search.setVisible(true);
    }//GEN-LAST:event_miSearchActionPerformed

    private void GISMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GISMouseReleased
        TGIS_Shape shp;

        if(GIS.getIsEmpty()) return;
        if(GIS.getMode() != TGIS_ViewerMode.Select) return;

        shp = (TGIS_Shape) GIS.Locate(GIS.ScreenToMap(new TPoint(evt.getX(), evt.getY())), 5/GIS.getZoom());

        shp.setIsSelected(false);
    }//GEN-LAST:event_GISMouseReleased

    private void GISMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GISMousePressed
        TGIS_Shape shp;

        if(GIS.getIsEmpty()) return;
        if(GIS.getMode() != TGIS_ViewerMode.Select) return;

        shp = (TGIS_Shape) GIS.Locate(GIS.ScreenToMap(new TPoint(evt.getX(), evt.getY())), 5/GIS.getZoom());

        shp.setIsSelected(true);
        if(attr == null){
            attr = new DetailsUI();
            attr.setVisible(true);
            attr.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        }
        attr.setVisible(true);
        attr.getAttr().ShowShape(shp);
    }//GEN-LAST:event_GISMousePressed

    private void GISMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GISMouseMoved
        TGIS_Point ptg;
        if(GIS.getIsEmpty()) return;

        ptg = GIS.ScreenToMap(new TPoint(evt.getX(), evt.getY()));
        GIS.getCS().ToWGS(ptg);
        TGIS_CSCoordinateSystem coords_in = TGIS_CSFactory.ByEPSG(GIS.fget_CS().getEPSG());
        TGIS_CSCoordinateSystem coord_out = TGIS_CSFactory.ByEPSG(4326);
        ptg = coord_out.FromCS(coords_in, ptg);

        String unitsX = TGIS_Utils.GisLatitudeToStr(ptg.X * Math.PI / 180);
        String unitsY = TGIS_Utils.GisLatitudeToStr(ptg.Y * Math.PI / 180);

        String zoom = String.valueOf(round(GIS.fget_Zoom(), 2));
        String exZoom = String.valueOf(round(GIS.fget_ZoomEx(), 2));
        info.setText("File: "+ nameOfFile +" | X: " + unitsX + " | Y: " + unitsY + " | Zoom: " + zoom + " | ZoomEx: " + exZoom);
    }//GEN-LAST:event_GISMouseMoved

    private void btnLegendModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLegendModeActionPerformed

        if(GIS.getIsEmpty()) return ;
        
        if(GIS_Legend.getMode() == TGIS_ControlLegendMode.Layers){
            btnLegendMode.setText("Group Mode");
            GIS_Legend.setMode(TGIS_ControlLegendMode.Groups);
        }
        else if(GIS_Legend.getMode() == TGIS_ControlLegendMode.Groups){
            btnLegendMode.setText("Layers Mode");
            GIS_Legend.setMode(TGIS_ControlLegendMode.Layers);
        }
    }//GEN-LAST:event_btnLegendModeActionPerformed

    private void GISPaintExtra(tatukgis.jdk.RenderEvent evt) {//GEN-FIRST:event_GISPaintExtra
        // TODO add your handling code here:
    }//GEN-LAST:event_GISPaintExtra

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ViewerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewerUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }       
                ViewerUI viewer = new ViewerUI();
                viewer.setVisible(true);

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private tatukgis.jdk.TGIS_ControlLegend GIS_Legend;
    private javax.swing.JMenu Searchmenu;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCS;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDragMode;
    private javax.swing.JButton btnFileEdit;
    private javax.swing.JButton btnFullExtent;
    private javax.swing.JButton btnLegendMode;
    private javax.swing.JButton btnOpen;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnSaveAll;
    private javax.swing.JButton btnSaveToImage;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSelect;
    private javax.swing.JButton btnZoomMode;
    private javax.swing.JColorChooser ccColor;
    private javax.swing.JFileChooser fcAddLayer;
    private javax.swing.JFileChooser fcExport;
    private javax.swing.JFileChooser fcOpen;
    private javax.swing.JMenu file;
    private javax.swing.JTextArea info;
    private javax.swing.JPanel infoLabel;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem miAdd;
    private javax.swing.JMenuItem miClose;
    private javax.swing.JMenuItem miColor;
    private javax.swing.JMenuItem miDrag;
    private javax.swing.JMenuItem miEditConfig;
    private javax.swing.JMenuItem miExit;
    private javax.swing.JMenuItem miExport;
    private javax.swing.JMenuItem miFullExtent;
    private javax.swing.JMenuItem miInfo;
    private javax.swing.JMenuItem miOpen;
    private javax.swing.JMenuItem miPrint;
    private javax.swing.JMenuItem miSearch;
    private javax.swing.JCheckBoxMenuItem miUseRTree;
    private javax.swing.JMenuItem miZoom;
    private javax.swing.JMenu options;
    private javax.swing.JMenu view;
    // End of variables declaration//GEN-END:variables
    
}
