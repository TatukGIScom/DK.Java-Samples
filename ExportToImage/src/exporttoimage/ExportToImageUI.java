package exporttoimage;

import java.awt.Toolkit;
import java.io.File;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import tatukgis.jdk.TGIS_Extent;
import tatukgis.jdk.TGIS_Layer;
import tatukgis.jdk.TGIS_LayerPixel;
import tatukgis.jdk.TGIS_LayerPixelSubFormat;
import tatukgis.jdk.TGIS_Utils;

public class ExportToImageUI extends javax.swing.JFrame {

    public ExportToImageUI() {
        initComponents();
        initOwn();
    }

    private void initOwn(){
        //rbGrid.setEnabled(false);
        
        rbImageActionPerformed(null);
        FileNameExtensionFilter jpg = new FileNameExtensionFilter("JPEG File Interchange Format (*.jpg)", ".jpg");
        FileNameExtensionFilter png = new FileNameExtensionFilter("Portable Network Graphic (*.png)", ".png");
        FileNameExtensionFilter tiff = new FileNameExtensionFilter("Tag Image File Format (*.tiff)", ".tiff");
        FileNameExtensionFilter bmp = new FileNameExtensionFilter("Window Bitmap (*.bmp)", ".bmp");
        FileNameExtensionFilter ttkps = new FileNameExtensionFilter("TatukGIS PixelStore (*.ttkps)", "ttkps");
        FileNameExtensionFilter flt = new FileNameExtensionFilter("Arc/Info Binary Grid (*.flt)", ".flt");
        FileNameExtensionFilter grd = new FileNameExtensionFilter("Arc/Info ASCII Grid (*.grd)", ".grd");
        fcImage.setAcceptAllFileFilterUsed(false);
        fcImage.addChoosableFileFilter(jpg);
        fcImage.addChoosableFileFilter(png);        
        fcImage.addChoosableFileFilter(tiff);        
        fcImage.addChoosableFileFilter(bmp);        
        fcImage.addChoosableFileFilter(ttkps);  
        fcGrid.setAcceptAllFileFilterUsed(false);
        fcGrid.addChoosableFileFilter(flt);
        fcGrid.addChoosableFileFilter(grd);
        fcGrid.addChoosableFileFilter(ttkps);
        FExtent = GIS.getExtent().MakeCopy();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgExtent = new javax.swing.ButtonGroup();
        bgResolution = new javax.swing.ButtonGroup();
        bgType = new javax.swing.ButtonGroup();
        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        lDestination = new javax.swing.JLabel();
        tfDestination = new javax.swing.JTextField();
        btnDestination = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        rbBest = new javax.swing.JRadioButton();
        rbDoc = new javax.swing.JRadioButton();
        rbWeb = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        lblFormat = new javax.swing.JLabel();
        cbxFormat = new javax.swing.JComboBox<>();
        rbFullExtent = new javax.swing.JRadioButton();
        lblExtent = new javax.swing.JLabel();
        rbVisibleExtent = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        rbImage = new javax.swing.JRadioButton();
        rbGrid = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ExportToImage - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setPreferredSize(new java.awt.Dimension(600, 500));
        setSize(new java.awt.Dimension(600, 500));

        lDestination.setText("Destination");

        tfDestination.setEnabled(false);

        btnDestination.setText("...");
        btnDestination.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDestinationActionPerformed(evt);
            }
        });

        btnExport.setText("Export");
        btnExport.setEnabled(false);
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Resolution"));

        bgResolution.add(rbBest);
        rbBest.setSelected(true);
        rbBest.setText("Best quality");
        rbBest.setEnabled(false);

        bgResolution.add(rbDoc);
        rbDoc.setText("For document");
        rbDoc.setEnabled(false);

        bgResolution.add(rbWeb);
        rbWeb.setText("For Web");
        rbWeb.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbWeb)
                    .addComponent(rbDoc)
                    .addComponent(rbBest))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbBest)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbDoc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbWeb)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Options:"));

        lblFormat.setText("Format");

        cbxFormat.setEnabled(false);

        bgExtent.add(rbFullExtent);
        rbFullExtent.setSelected(true);
        rbFullExtent.setText("Full");
        rbFullExtent.setEnabled(false);

        lblExtent.setText("Extent");

        bgExtent.add(rbVisibleExtent);
        rbVisibleExtent.setText("Visible");
        rbVisibleExtent.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblFormat)
                        .addComponent(lblExtent))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(rbFullExtent)
                        .addComponent(rbVisibleExtent)
                        .addComponent(cbxFormat, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(120, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 130, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(13, 13, 13)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblFormat)
                        .addComponent(cbxFormat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblExtent)
                        .addComponent(rbFullExtent))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(rbVisibleExtent)
                    .addContainerGap(14, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jPanel2, jPanel3});

        bgType.add(rbImage);
        rbImage.setSelected(true);
        rbImage.setText("Image");
        rbImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbImageActionPerformed(evt);
            }
        });

        bgType.add(rbGrid);
        rbGrid.setText("Grid");
        rbGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbGridActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 97, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(rbImage)
                        .addComponent(rbGrid))
                    .addContainerGap(14, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(51, 51, 51)
                    .addComponent(rbImage)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(rbGrid)
                    .addContainerGap(57, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(tfDestination)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDestination))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lDestination)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(263, 263, 263)
                .addComponent(btnExport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(241, 241, 241))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lDestination)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfDestination, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDestination))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExport)
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rbImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbImageActionPerformed
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/World/VisibleEarth/world_8km.jpg"); 
        tfDestination.setText("");
        cbxFormat.removeAllItems();
        btnExport.setEnabled(false);
        rbBest.setEnabled(false);
        rbDoc.setEnabled(false);
        rbFullExtent.setEnabled(false);
        rbVisibleExtent.setEnabled(false);
        rbWeb.setEnabled(false);
        cbxFormat.setEnabled(false);
    }//GEN-LAST:event_rbImageActionPerformed

    private void rbGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbGridActionPerformed
        GIS.Open( TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/USA/States/California/San Bernardino/NED/hdr.adf" );
        tfDestination.setText("");
        cbxFormat.removeAllItems();
        btnExport.setEnabled(false);
        rbBest.setEnabled(false);
        rbDoc.setEnabled(false);
        rbFullExtent.setEnabled(false);
        rbVisibleExtent.setEnabled(false);
        rbWeb.setEnabled(false);
        cbxFormat.setEnabled(false);
    }//GEN-LAST:event_rbGridActionPerformed

    private void btnDestinationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDestinationActionPerformed
        List<TGIS_LayerPixelSubFormat> clst;
        int i;
        
        if(cbxFormat.getItemCount() != 0)
            cbxFormat.removeAllItems();
        
        if(rbImage.isSelected()){
            int returnVal = fcImage.showSaveDialog(GIS);
            if(returnVal == JFileChooser.APPROVE_OPTION){
                file = fcImage.getSelectedFile();
                if (fcImage.getFileFilter() instanceof FileNameExtensionFilter) {
                    String[] exts = ((FileNameExtensionFilter)fcImage.getFileFilter()).getExtensions();
                    fcImage.setSelectedFile(new File(file.getPath() ));
                    tfDestination.setText(fcImage.getSelectedFile().toString() + exts[0]);
                }
            }
            lstp = (TGIS_LayerPixel)GIS.getItems().getItems(0);
            lpx = (TGIS_LayerPixel)TGIS_Utils.GisCreateLayer(fcImage.getSelectedFile().getName(), tfDestination.getText() );
            
        }else if(rbGrid.isSelected()){
            int returnVal = fcGrid.showSaveDialog(GIS);
            if(returnVal == JFileChooser.APPROVE_OPTION){
                file = fcGrid.getSelectedFile();
                if (fcGrid.getFileFilter() instanceof FileNameExtensionFilter) {
                    String[] exts = ((FileNameExtensionFilter)fcGrid.getFileFilter()).getExtensions();
                    fcGrid.setSelectedFile(new File(file.getPath() ));
                    tfDestination.setText(fcGrid.getSelectedFile().toString() + exts[0]);
                }
            }
            lstp = (TGIS_LayerPixel)GIS.getItems().getItems(0);        
            lpx = (TGIS_LayerPixel)TGIS_Utils.GisCreateLayer(fcGrid.getSelectedFile().getName(), tfDestination.getText() );     
            
        }
        
        clst = lpx.getCapabilities();
        i = 0;
        caps = new T_Capability[clst.size()];
        for(TGIS_LayerPixelSubFormat c : clst){
            cbxFormat.addItem(c.toString());
            caps[i] = new T_Capability(c);
            i++;
        }
        
        cbxFormat.setSelectedIndex(0);
        
        btnExport.setEnabled(true);
        rbBest.setEnabled(true);
        rbDoc.setEnabled(true);
        rbFullExtent.setEnabled(true);
        rbVisibleExtent.setEnabled(true);
        rbWeb.setEnabled(true);
        cbxFormat.setEnabled(true);
    }//GEN-LAST:event_btnDestinationActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        TGIS_LayerPixelSubFormat c;
        
        if( cbxFormat.getSelectedIndex() >= 0)
            c = caps[cbxFormat.getSelectedIndex()].C;
        else
            c = lpx.getDefaultSubFormat();
        
        if(rbFullExtent.isSelected()){
            FExtent = GIS.getExtent();
        }else if(rbVisibleExtent.isSelected()){
            FExtent = GIS.getVisibleExtent();
        }
        
        if(rbBest.isSelected()){
            ValuesInit();
        }else if(rbWeb.isSelected()){
            Ppi = DEFAULT_PPI_WEB;
            PixWidth = DEFAULT_WIDTHPIX_WEB;

            if ((FExtent.XMax - FExtent.XMin) != 0)
                PixHeight = (FExtent.YMax - FExtent.YMin) / (FExtent.XMax - FExtent.XMin) * PixWidth;
            else
            {
                PixWidth = 2;
                PixHeight = 2;
            }
            ValuesWH();
        }else if(rbDoc.isSelected()){
            Ppi = DEFAULT_PPI_DOC;
            expWidth = DEFAULT_WIDTH_DOC_INCH;
            if ((FExtent.XMax - FExtent.XMin) != 0)
                expHeight = (FExtent.YMax - FExtent.YMin) / (FExtent.XMax - FExtent.XMin) * expWidth;
            else
            {
                expWidth = 2;
                expHeight = 2;
            }

            ValuesWHpix();
        }
        
        lpx.ImportLayer(lstp, lstp.getExtent(), lstp.getCS(), Math.round((float) PixWidth), Math.round((float) PixHeight ), c);
        JOptionPane.showMessageDialog(null, "Done!", "File exported", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnExportActionPerformed

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
            java.util.logging.Logger.getLogger(ExportToImageUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExportToImageUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExportToImageUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExportToImageUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExportToImageUI().setVisible(true);
            }
        });
    }

    private void ValuesInit(){
        int i,j;
        TGIS_Layer la;
        double density, density0, density1;
        int widthpix;
        double ext_delta;
        double ext_width;
        
        density0 = 0;
        density = density0;
        Ppi = DEFAULT_PPI ;
        j = 0;
        for ( i = GIS.getItems().getCount() - 1 ; i >= 0 ; i-- ){
            la = (TGIS_Layer)GIS.getItems().getItems(i);

            if (la instanceof TGIS_LayerPixel)
            {
                ext_width = la.getExtent().XMax - la.getExtent().XMin;

                density1 = ((TGIS_LayerPixel)la).getBitWidth() / ext_width;
                if (density1 > density0)
                {
                    density = density1;
                    j = i;
                }
                density0 = density1;
            }
        }

        if (density == 0)
            widthpix = 4200;
        else
        {
            la = (TGIS_Layer)GIS.getItems().getItems(j);
            ext_width = la.getExtent().XMax - la.getExtent().XMin;
            ext_delta = (FExtent.XMax - FExtent.XMin) / ext_width;

            widthpix = (int)Math.round(ext_delta * ((TGIS_LayerPixel)GIS.getItems().getItems(j)).getBitWidth());
        }

        PixWidth = widthpix;

        if ((FExtent.XMax - FExtent.XMin) != 0)
            PixHeight = ((FExtent.YMax - FExtent.YMin) / (FExtent.XMax - FExtent.XMin) * PixWidth);
        else
        {
            PixWidth = 2;
            PixHeight = 2;
        }     
    }
    private void ValuesWH(){
        expWidth = PixWidth / Ppi;

        if ((FExtent.XMax - FExtent.XMin) != 0)
            expHeight = (FExtent.YMax - FExtent.YMin) / (FExtent.XMax - FExtent.XMin) * expWidth;
        else
        {
            expWidth = 2;
            expHeight = 2;
        }
    }
    private void ValuesWHpix(){
        PixWidth = expWidth * Ppi;

        if ((FExtent.XMax - FExtent.XMin) != 0)
            PixHeight = (FExtent.YMax - FExtent.YMin) / (FExtent.XMax - FExtent.XMin) * PixWidth;
        else
        {
            PixWidth = 2;
            PixHeight = 2;
        }
    }
            
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.ButtonGroup bgExtent;
    private javax.swing.ButtonGroup bgResolution;
    private javax.swing.ButtonGroup bgType;
    private javax.swing.JButton btnDestination;
    private javax.swing.JButton btnExport;
    private javax.swing.JComboBox<String> cbxFormat;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lDestination;
    private javax.swing.JLabel lblExtent;
    private javax.swing.JLabel lblFormat;
    private javax.swing.JRadioButton rbBest;
    private javax.swing.JRadioButton rbDoc;
    private javax.swing.JRadioButton rbFullExtent;
    private javax.swing.JRadioButton rbGrid;
    private javax.swing.JRadioButton rbImage;
    private javax.swing.JRadioButton rbVisibleExtent;
    private javax.swing.JRadioButton rbWeb;
    private javax.swing.JTextField tfDestination;
    // End of variables declaration//GEN-END:variables
    private TGIS_LayerPixel lstp;
    private TGIS_LayerPixel lpx;
    private TGIS_Extent FExtent;
    private double expWidth,
                    expHeight,
                    PixWidth,
                    PixHeight;
    private int Ppi;
    private File file;
    private T_Capability[] caps;
    final JFileChooser fcImage = new JFileChooser();
    final JFileChooser fcGrid = new JFileChooser();
    final int DEFAULT_PPI = 300;
    final int DEFAULT_PPI_WEB = 96;
    final int DEFAULT_PPI_DOC = 300;
    final int DEFAULT_WIDTHPIX = 4200;
    final int DEFAULT_WIDTHPIX_WEB = 640;
    final int DEFAULT_WIDTH_DOC_MM = 160;
    final int DEFAULT_WIDTH_DOC_CM = 16;
    final double DEFAULT_WIDTH_DOC_INCH = 6.3;
}
