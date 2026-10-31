// =============================================================================
// ExportToImage sample - Java (Swing / JDK)
//
// Demonstrates how to export a GIS map layer to a raster image or a numeric
// elevation grid using the TatukGIS Developer Kernel Java API.
//
// Key concepts shown:
//   - Loading raster imagery (JPEG) and DEM/elevation grids (ADF) into a
//     TGIS_ViewerWnd Swing component.
//   - Creating a new output TGIS_LayerPixel via TGIS_Utils.GisCreateLayer,
//     which selects the appropriate driver from the file extension.
//   - Querying getCapabilities() to discover the TGIS_LayerPixelSubFormat
//     options (pixel depth, compression, etc.) supported by the target format.
//   - Controlling export resolution via three strategies:
//       * Best quality  - pixel size matched to the highest-density source layer.
//       * For document  - fixed physical paper width at 300 DPI.
//       * For Web       - fixed pixel width (640 px) at 96 DPI.
//   - Controlling export spatial coverage through full or visible extent.
//   - Performing the raster conversion with TGIS_LayerPixel.ImportLayer, which
//     resamples the source layer into the target at the requested pixel size.
// =============================================================================

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

/**
 * Main Swing frame for the ExportToImage sample.
 *
 * <p>Lets the user choose between a satellite image and an elevation grid,
 * pick an output file path and sub-format, select resolution and extent
 * options, then export the raster data to disk.</p>
 */
public class ExportToImageUI extends javax.swing.JFrame {

    /**
     * Constructs the form, initialises Swing components, and loads the
     * default satellite image into the viewer.
     */
    public ExportToImageUI() {
        initComponents();
        initOwn();
    }

    /**
     * Post-constructor initialisation: sets up file-chooser filters,
     * triggers the default "Image" selection, and captures the initial
     * map extent for later use.
     */
    private void initOwn(){
        //rbGrid.setEnabled(false);

        // Start in Image mode so the viewer shows a sample layer immediately
        rbImageActionPerformed(null);

        // Register file extension filters for the image save chooser
        FileNameExtensionFilter jpg   = new FileNameExtensionFilter("JPEG File Interchange Format (*.jpg)", ".jpg");
        FileNameExtensionFilter png   = new FileNameExtensionFilter("Portable Network Graphic (*.png)", ".png");
        FileNameExtensionFilter tiff  = new FileNameExtensionFilter("Tag Image File Format (*.tiff)", ".tiff");
        FileNameExtensionFilter bmp   = new FileNameExtensionFilter("Window Bitmap (*.bmp)", ".bmp");
        // TTKPS is the TatukGIS native PixelStore format - lossless and supports metadata
        FileNameExtensionFilter ttkps = new FileNameExtensionFilter("TatukGIS PixelStore (*.ttkps)", "ttkps");

        // Register file extension filters for the elevation grid save chooser
        FileNameExtensionFilter flt   = new FileNameExtensionFilter("Arc/Info Binary Grid (*.flt)", ".flt");
        FileNameExtensionFilter grd   = new FileNameExtensionFilter("Arc/Info ASCII Grid (*.grd)", ".grd");

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

        // Capture the initial full map extent so it is available before the
        // user chooses an extent option (avoids a null-check in export logic)
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
        setTitle("ExportToImage - TatukGIS DK sample");
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

    /**
     * Switches the viewer to the sample satellite image and resets all export
     * controls to their disabled / empty state.
     *
     * <p>The world_8km.jpg is a global RGB mosaic at approximately 8 km per pixel.
     * It demonstrates image-format export (JPEG, PNG, TIFF, BMP, TTKPS).</p>
     */
    private void rbImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbImageActionPerformed
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/World/VisibleEarth/world_8km.jpg");
        tfDestination.setText("");
        cbxFormat.removeAllItems();
        // Disable all export controls until a valid destination file is chosen
        btnExport.setEnabled(false);
        rbBest.setEnabled(false);
        rbDoc.setEnabled(false);
        rbFullExtent.setEnabled(false);
        rbVisibleExtent.setEnabled(false);
        rbWeb.setEnabled(false);
        cbxFormat.setEnabled(false);
    }//GEN-LAST:event_rbImageActionPerformed

    /**
     * Switches the viewer to a DEM elevation grid (ADF format) and resets all
     * export controls.
     *
     * <p>The hdr.adf is an ESRI Arc/Info Binary Grid header; DK opens the entire
     * dataset by pointing at that file. Grid export supports formats such as
     * Arc/Info FLT, ASCII GRD, and TatukGIS PixelStore (TTKPS).</p>
     */
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

    /**
     * Opens the file chooser, creates the output pixel layer via
     * {@link TGIS_Utils#GisCreateLayer}, and populates the format combobox with
     * the sub-formats supported by the chosen file format.
     *
     * <p>{@code TGIS_LayerPixelSubFormat} describes properties like bit depth and
     * compression that vary by format (e.g. TIFF supports LZW, DEFLATE; JPEG
     * supports quality levels). Each combobox item represents one valid combination
     * for the selected output format.</p>
     */
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
                    // Append the chosen extension to the selected file path so
                    // the DK driver can identify the correct format
                    String[] exts = ((FileNameExtensionFilter)fcImage.getFileFilter()).getExtensions();
                    fcImage.setSelectedFile(new File(file.getPath() ));
                    tfDestination.setText(fcImage.getSelectedFile().toString() + exts[0]);
                }
            }
            // GIS.getItems().getItems(0) is the primary (bottom-most) layer
            lstp = (TGIS_LayerPixel)GIS.getItems().getItems(0);
            // GisCreateLayer selects the correct DK driver from the extension
            // and returns an empty TGIS_LayerPixel ready to receive raster data
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

        // getCapabilities() returns all valid TGIS_LayerPixelSubFormat options.
        // Build the combobox and a parallel caps[] array of deep copies.
        clst = lpx.getCapabilities();
        i = 0;
        caps = new T_Capability[clst.size()];
        for(TGIS_LayerPixelSubFormat c : clst){
            cbxFormat.addItem(c.toString());
            caps[i] = new T_Capability(c);  // Deep copy to survive list disposal
            i++;
        }

        cbxFormat.setSelectedIndex(0);

        // Enable export controls now that a valid destination and layer exist
        btnExport.setEnabled(true);
        rbBest.setEnabled(true);
        rbDoc.setEnabled(true);
        rbFullExtent.setEnabled(true);
        rbVisibleExtent.setEnabled(true);
        rbWeb.setEnabled(true);
        cbxFormat.setEnabled(true);
    }//GEN-LAST:event_btnDestinationActionPerformed

    /**
     * Performs the raster export.
     *
     * <p>Steps:
     * <ol>
     *   <li>Resolve the chosen {@link TGIS_LayerPixelSubFormat} from the combobox,
     *       or fall back to the layer's natural default.</li>
     *   <li>Set {@code FExtent} from the user's spatial coverage choice.</li>
     *   <li>Calculate {@code PixWidth} / {@code PixHeight} according to the
     *       selected resolution preset.</li>
     *   <li>Call {@code lpx.ImportLayer} to resample the source into the output
     *       file at the requested pixel size.</li>
     * </ol>
     * </p>
     */
    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        TGIS_LayerPixelSubFormat c;

        // Retrieve the sub-format selected by the user (bit depth, compression, etc.)
        if( cbxFormat.getSelectedIndex() >= 0)
            c = caps[cbxFormat.getSelectedIndex()].C;
        else
            c = lpx.getDefaultSubFormat();  // Fall back to the format's natural default

        // Determine the geographic area to export
        if(rbFullExtent.isSelected()){
            FExtent = GIS.getExtent();         // Full bounding box of all loaded layers
        }else if(rbVisibleExtent.isSelected()){
            FExtent = GIS.getVisibleExtent();  // Current viewport in map coordinates
        }

        // --- Resolution strategy ---
        if(rbBest.isSelected()){
            // Match pixel density of the highest-resolution source layer
            ValuesInit();
        }else if(rbWeb.isSelected()){
            // Web preset: fixed pixel width (640 px), 96 DPI
            Ppi = DEFAULT_PPI_WEB;
            PixWidth = DEFAULT_WIDTHPIX_WEB;

            if ((FExtent.XMax - FExtent.XMin) != 0)
                // Height derived from geographic aspect ratio
                PixHeight = (FExtent.YMax - FExtent.YMin) / (FExtent.XMax - FExtent.XMin) * PixWidth;
            else
            {
                PixWidth = 2;
                PixHeight = 2;
            }
            // Derive physical dimensions from pixel count / DPI (stored for reference)
            ValuesWH();
        }else if(rbDoc.isSelected()){
            // Document preset: fixed physical width (6.3 inches), 300 DPI
            Ppi = DEFAULT_PPI_DOC;
            expWidth = DEFAULT_WIDTH_DOC_INCH;
            if ((FExtent.XMax - FExtent.XMin) != 0)
                // Aspect ratio preserved from the geographic extent
                expHeight = (FExtent.YMax - FExtent.YMin) / (FExtent.XMax - FExtent.XMin) * expWidth;
            else
            {
                expWidth = 2;
                expHeight = 2;
            }
            // Convert physical size (inches) * DPI -> pixel dimensions
            ValuesWHpix();
        }

        // ImportLayer resamples lstp (source layer) into lpx (output file):
        //   lstp              - source TGIS_LayerPixel to read pixel data from
        //   lstp.getExtent()  - full geographic coverage of the source
        //   lstp.getCS()      - coordinate system, ensuring correct spatial referencing
        //   PixWidth/PixHeight - desired output raster dimensions in pixels (rounded)
        //   c                 - sub-format descriptor (bit depth, compression, etc.)
        lpx.ImportLayer(lstp, lstp.getExtent(), lstp.getCS(), Math.round((float) PixWidth), Math.round((float) PixHeight ), c);
        JOptionPane.showMessageDialog(null, "Done!", "File exported", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnExportActionPerformed

    /**
     * Application entry point. Sets the Windows look and feel for a native
     * appearance, then creates and shows the main frame on the Swing event thread.
     *
     * @param args Command-line arguments (not used).
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
            java.util.logging.Logger.getLogger(ExportToImageUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExportToImageUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExportToImageUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExportToImageUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form on the EDT to satisfy Swing's thread safety requirements */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExportToImageUI().setVisible(true);
            }
        });
    }

    /**
     * Calculates the optimal output pixel dimensions for the "Best quality" preset.
     *
     * <p>Scans all pixel layers in the viewer to find the one with the highest pixel
     * density ({@code BitWidth} / geographic extent width). The export pixel width is
     * then scaled so the chosen export extent is rendered at that same native density,
     * preserving the maximum detail available in the source data.  Falls back to
     * {@code DEFAULT_WIDTHPIX} (4200 px) when no raster layer is loaded.</p>
     */
    private void ValuesInit(){
        int i, j;
        TGIS_Layer la;
        double density;   // Pixel density (px/map-unit) of the best layer found
        double density0;  // Density of the previously examined layer
        double density1;  // Density of the current layer being examined
        int widthpix;     // Computed output width in pixels
        double ext_delta; // Ratio: export extent width / best-layer extent width
        double ext_width; // Geographic width of the layer under examination

        density0 = 0;
        density = density0;
        Ppi = DEFAULT_PPI ;
        j = 0;

        // Iterate from the top layer downwards; keep the highest-density raster layer
        for ( i = GIS.getItems().getCount() - 1 ; i >= 0 ; i-- ){
            la = (TGIS_Layer)GIS.getItems().getItems(i);

            if (la instanceof TGIS_LayerPixel)
            {
                ext_width = la.getExtent().XMax - la.getExtent().XMin;

                // getBitWidth() is the layer's native pixel width over its full extent;
                // dividing by geographic width gives pixels per map unit.
                density1 = ((TGIS_LayerPixel)la).getBitWidth() / ext_width;
                if (density1 > density0)
                {
                    density = density1;
                    j = i;  // Remember the index of the highest-density layer
                }
                density0 = density1;
            }
        }

        if (density == 0)
            // No raster layers found; use the predefined fallback pixel width
            widthpix = 4200;
        else
        {
            la = (TGIS_Layer)GIS.getItems().getItems(j);
            ext_width = la.getExtent().XMax - la.getExtent().XMin;

            // ext_delta is the fraction of the best layer's extent covered by FExtent.
            // Multiplying by getBitWidth() gives source pixels in that region, which is
            // the ideal export width to preserve native resolution without upscaling.
            ext_delta = (FExtent.XMax - FExtent.XMin) / ext_width;
            widthpix = (int)Math.round(ext_delta * ((TGIS_LayerPixel)GIS.getItems().getItems(j)).getBitWidth());
        }

        PixWidth = widthpix;

        // Preserve geographic aspect ratio so the exported image is not distorted
        if ((FExtent.XMax - FExtent.XMin) != 0)
            PixHeight = ((FExtent.YMax - FExtent.YMin) / (FExtent.XMax - FExtent.XMin) * PixWidth);
        else
        {
            // Zero-width extent guard: produce a minimal valid raster
            PixWidth = 2;
            PixHeight = 2;
        }
    }

    /**
     * Converts pixel dimensions to physical (inch) dimensions and preserves the
     * geographic aspect ratio.
     *
     * <p>{@code expWidth = PixWidth / Ppi} (inches wide at the chosen DPI setting);
     * {@code expHeight} is derived from {@code expWidth} via the geographic aspect
     * ratio of {@code FExtent}.</p>
     */
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

    /**
     * Converts physical (inch) dimensions to pixel dimensions and preserves the
     * geographic aspect ratio.
     *
     * <p>{@code PixWidth = expWidth * Ppi} (pixels wide given physical width and DPI);
     * {@code PixHeight} is derived from {@code PixWidth} via the geographic aspect
     * ratio of {@code FExtent}.</p>
     */
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

    /** Source pixel layer loaded in the viewer; resampled into lpx during export. */
    private TGIS_LayerPixel lstp;

    /** Target pixel layer that writes to the chosen output file. */
    private TGIS_LayerPixel lpx;

    /** Geographic bounding box for the export operation. */
    private TGIS_Extent FExtent;

    /** Physical output size in inches (document preset only). */
    private double expWidth, expHeight;

    /** Output image dimensions in pixels; passed to ImportLayer. */
    private double PixWidth, PixHeight;

    /** Pixels per inch for the current export mode. */
    private int Ppi;

    /** Holds a reference to the last file selected in a file chooser. */
    private File file;

    /** Parallel array to cbxFormat items; each entry owns a deep-copied sub-format. */
    private T_Capability[] caps;

    /** File chooser pre-configured with image format filters. */
    final JFileChooser fcImage = new JFileChooser();

    /** File chooser pre-configured with grid format filters. */
    final JFileChooser fcGrid = new JFileChooser();

    // --- Resolution / dimension constants (same values across all language variants) ---

    /** Default PPI for best-quality and document export modes (300 DPI = print quality). */
    final int DEFAULT_PPI = 300;

    /** Screen/web resolution in DPI (96 PPI = standard Windows screen DPI). */
    final int DEFAULT_PPI_WEB = 96;

    /** Document print resolution in DPI. */
    final int DEFAULT_PPI_DOC = 300;

    /** Fallback output width in pixels when no raster layer is present. */
    final int DEFAULT_WIDTHPIX = 4200;

    /** Default pixel width for the web export profile. */
    final int DEFAULT_WIDTHPIX_WEB = 640;

    /** Document page-width references (160 mm / 16 cm / 6.3 in = typical A4 text area). */
    final int DEFAULT_WIDTH_DOC_MM = 160;
    final int DEFAULT_WIDTH_DOC_CM = 16;
    final double DEFAULT_WIDTH_DOC_INCH = 6.3;
}
