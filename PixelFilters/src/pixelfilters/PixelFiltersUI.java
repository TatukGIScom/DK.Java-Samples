package pixelfilters;

import java.awt.Toolkit;
import tatukgis.jdk.TGIS_BusyEvent;
import tatukgis.jdk.TGIS_BusyEventArgs;
import tatukgis.jdk.TGIS_LayerPixel;
import tatukgis.jdk.TGIS_PixelFilterAbstract;
import tatukgis.jdk.TGIS_PixelFilterAlphaTrimmedMean;
import tatukgis.jdk.TGIS_PixelFilterArithmeticMean;
import tatukgis.jdk.TGIS_PixelFilterBottomHat;
import tatukgis.jdk.TGIS_PixelFilterClosing;
import tatukgis.jdk.TGIS_PixelFilterColorSpace;
import tatukgis.jdk.TGIS_PixelFilterContraHarmonicMean;
import tatukgis.jdk.TGIS_PixelFilterConvolution;
import tatukgis.jdk.TGIS_PixelFilterDilation;
import tatukgis.jdk.TGIS_PixelFilterErosion;
import tatukgis.jdk.TGIS_PixelFilterGeometricMean;
import tatukgis.jdk.TGIS_PixelFilterHarmonicMean;
import tatukgis.jdk.TGIS_PixelFilterMajority;
import tatukgis.jdk.TGIS_PixelFilterMaskType;
import tatukgis.jdk.TGIS_PixelFilterMaximum;
import tatukgis.jdk.TGIS_PixelFilterMedian;
import tatukgis.jdk.TGIS_PixelFilterMidpoint;
import tatukgis.jdk.TGIS_PixelFilterMinimum;
import tatukgis.jdk.TGIS_PixelFilterMinority;
import tatukgis.jdk.TGIS_PixelFilterNoiseGaussian;
import tatukgis.jdk.TGIS_PixelFilterNoiseSaltPepper;
import tatukgis.jdk.TGIS_PixelFilterOpening;
import tatukgis.jdk.TGIS_PixelFilterRange;
import tatukgis.jdk.TGIS_PixelFilterSobelMagnitude;
import tatukgis.jdk.TGIS_PixelFilterStandardDeviation;
import tatukgis.jdk.TGIS_PixelFilterStructuringElementType;
import tatukgis.jdk.TGIS_PixelFilterSum;
import tatukgis.jdk.TGIS_PixelFilterThreshold;
import tatukgis.jdk.TGIS_PixelFilterTopHat;
import tatukgis.jdk.TGIS_PixelFilterUniqueCount;
import tatukgis.jdk.TGIS_PixelFilterWeightedMean;
import tatukgis.jdk.TGIS_PixelFilterWeightedMedian;
import tatukgis.jdk.TGIS_PixelFilterYpMean;
import tatukgis.jdk.TGIS_Utils;

public class PixelFiltersUI extends javax.swing.JFrame {

    public PixelFiltersUI() {
        initComponents();
        initOwn();
    }

    private void open(){
        TGIS_LayerPixel ll ;
        
        GIS.Close(); ;
        ll = (TGIS_LayerPixel)(
        TGIS_Utils.GisCreateLayer( "", TGIS_Utils.GisSamplesDataDirDownload() +
          "\\World\\Countries\\USA\\States\\California\\San Bernardino\\NED\\w001001.adf" )
        ) ;
        ll.Open() ;
        ll.getParams().getPixel().getAltitudeMapZones().Clear() ;
        ll.getParams().getPixel().setGridShadow( false ) ;

        GIS.Add( ll );
        GIS.FullExtent() ;

        bFirst = true ;
    }
    
    private void initOwn(){
        lFilters.setSelectedIndex(0);
        cbStructuring.setSelectedIndex(0);
        cbMask.setSelectedIndex(0);
        
        onChange();
        open();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        lblFilter = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lFilters = new javax.swing.JList<>();
        lblMaskSize = new javax.swing.JLabel();
        lblMask = new javax.swing.JLabel();
        sMaskSize = new javax.swing.JSlider();
        lblStructuring = new javax.swing.JLabel();
        cbStructuring = new javax.swing.JComboBox<>();
        btnExecute = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        GIS_Legend = new tatukgis.jdk.TGIS_ControlLegend();
        cbMask = new javax.swing.JComboBox<>();
        pProgress = new javax.swing.JProgressBar();
        lblMaskSizeValue = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PixelFilters - TatukGIS DK11 Sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(788, 394));

        GIS.setMode(tatukgis.jdk.TGIS_ViewerMode.Zoom);

        lblFilter.setLabelFor(lFilters);
        lblFilter.setText("Filter");

        lFilters.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Threshold", "Salt-And-Pepper Noise", "Gaussian Noise", "Convolution", "Sobel Magnitude", "Range", "Midpoint", "Minimum", "Maximum", "Arithmetic Mean", "Alpha-Trimmed Mean", "Contra-Harmonic Mean", "Geometric Mean", "Harmonic Mean", "Wieghted Mean", "Yp Mean", "Majority", "Minority", "Median", "Wieghted Median", "Sum", "Standard Deviation", "Unique Count", "Erosion", "Dilatation", "Opening", "Closing", "Top-Hat", "Bottom-Hat" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lFilters.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lFiltersValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lFilters);

        lblMaskSize.setLabelFor(sMaskSize);
        lblMaskSize.setText("Mask Size");

        lblMask.setLabelFor(cbMask);
        lblMask.setText("Mask");

        sMaskSize.setMaximum(17);
        sMaskSize.setMinimum(1);
        sMaskSize.setValue(1);
        sMaskSize.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sMaskSizeStateChanged(evt);
            }
        });

        lblStructuring.setLabelFor(cbStructuring);
        lblStructuring.setText("Structuring Elements");

        cbStructuring.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Square", "Diamond", "Disk", "Horizontal Line", "Vertical Line", "Left Diagonal Line", "Right Diagonal Line" }));

        btnExecute.setText("Execute");
        btnExecute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExecuteActionPerformed(evt);
            }
        });

        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        GIS_Legend.setGIS_Viewer(GIS);
        GIS_Legend.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N

        cbMask.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Low-Pass 3x3", "Low-Pass 5x5", "Low-Pass 7x7", "High-Pass 3x3", "High-Pass 5x5", "High-Pass 7x7", "Gaussian 3x3", "Gaussian 5x5", "Gaussian 7x7", "Laplacian 3x3", "Laplacian 5x5", "GradientNorth", "GradientEast", "GradientSouth", "GradientWest", "GradientNorthwest", "GradientNortheast", "GradientSouthwest", "GradientSoutheast", "PointDetector", "LineDetectorHorizontal", "LineDetectorVertical", "LineDetectorLeftDiagonal", "LineDetectorRightDiagonal" }));

        lblMaskSizeValue.setText(" ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addComponent(lblFilter))
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(pProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(GIS_Legend, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(btnExecute)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(cbStructuring, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(sMaskSize, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblMaskSizeValue, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblStructuring)
                            .addComponent(lblMaskSize)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbMask, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMask))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblFilter)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMaskSize)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sMaskSize, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMaskSizeValue, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMask)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbMask, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblStructuring)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbStructuring, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnExecute)
                            .addComponent(btnReset))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GIS_Legend, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void onChange(){
        if ( ( lFilters.getSelectedIndex() == 0 ) ||
             ( lFilters.getSelectedIndex() == 1 ) ||
             ( lFilters.getSelectedIndex() == 2 )
            ){
           cbStructuring.setVisible(false);
           lblStructuring.setVisible(false);
           lblMask.setVisible(false);
           cbMask.setVisible(false);
           lblMaskSize.setVisible(false);
           lblMaskSizeValue.setVisible(false);
           sMaskSize.setVisible(false);
        }else{
            if ( ( lFilters.getSelectedIndex() == 23 ) ||
                 ( lFilters.getSelectedIndex() == 24 ) ||
                 ( lFilters.getSelectedIndex() == 25 ) ||
                 ( lFilters.getSelectedIndex() == 26 ) ||
                 ( lFilters.getSelectedIndex() == 27 ) ||
                 ( lFilters.getSelectedIndex() == 28 )
                ){
                cbStructuring.setVisible(true);
                lblStructuring.setVisible(true);
                lblMask.setVisible(true);
                cbMask.setVisible(true);
                lblMaskSize.setVisible(true);
                lblMaskSizeValue.setVisible(true);
                sMaskSize.setVisible(true);
            }else{
                cbStructuring.setVisible(false);
                lblStructuring.setVisible(false);
                lblMask.setVisible(false);
                cbMask.setVisible(false);
                lblMaskSize.setVisible(false);
                lblMaskSizeValue.setVisible(false);
                sMaskSize.setVisible(false);
            }   
            
            if ( lFilters.getSelectedIndex() == 3 ){
                cbMask.setVisible(true);
                lblMask.setVisible(true);
                lblMaskSize.setVisible(false);
                lblMaskSizeValue.setVisible(false);
                sMaskSize.setVisible(false);
            }else{
                cbMask.setVisible(false);
                lblMask.setVisible(false);
                lblMaskSize.setVisible(true);
                lblMaskSizeValue.setVisible(true);
                sMaskSize.setVisible(true);                
            }
        }
    }
    
    private void lFiltersValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lFiltersValueChanged
        onChange() ;
    }//GEN-LAST:event_lFiltersValueChanged

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        open();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnExecuteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExecuteActionPerformed
        final TGIS_PixelFilterAbstract flrt;
        TGIS_LayerPixel input;
        TGIS_LayerPixel output;
        int mask;
        int struct;
        int block;
        
        input = (TGIS_LayerPixel)(GIS.getItems().get(0));
        
        if( bFirst ){
            output = new TGIS_LayerPixel() ;
            output.setName( "Result" );
            output.Build( true, input.getCS(), input.getExtent(), input.getBitWidth(), input.getBitHeight() ) ;
            output.Open();
        }else{
            output = input ;
        }
        
        block = 2 * sMaskSize.getValue() + 1;
        
        switch( lFilters.getSelectedIndex() ){
            case 0:{
                flrt = new TGIS_PixelFilterThreshold();
                ((TGIS_PixelFilterThreshold)flrt).setThreshold((float) ((input.getMinHeight() + input.getMaxHeight())*0.3));
                break;
            }
            case 1:{
                flrt = new TGIS_PixelFilterNoiseSaltPepper();
                ((TGIS_PixelFilterNoiseSaltPepper)flrt).setAmount(10);
                break;
            }
            case 2:{
                flrt = new TGIS_PixelFilterNoiseGaussian();
                ((TGIS_PixelFilterNoiseGaussian)flrt).setAmount(10);  
                break; 
            }
            case 3:{
                flrt = new TGIS_PixelFilterConvolution();
                switch( cbMask.getSelectedIndex() ){
                    case 0  :
                      mask = TGIS_PixelFilterMaskType.LowPass3x3 ;
                      break;
                    case 1  :
                      mask = TGIS_PixelFilterMaskType.LowPass5x5 ;
                      break;
                    case 2  :
                      mask = TGIS_PixelFilterMaskType.LowPass7x7 ;
                      break;
                    case 3  :
                      mask = TGIS_PixelFilterMaskType.HighPass3x3 ;
                      break;
                    case 4  :
                      mask = TGIS_PixelFilterMaskType.HighPass5x5 ;
                      break;
                    case 5  :
                      mask = TGIS_PixelFilterMaskType.HighPass7x7 ;
                      break;
                    case 6  :
                      mask = TGIS_PixelFilterMaskType.Gaussian3x3 ;
                      break;
                    case 7  :
                      mask = TGIS_PixelFilterMaskType.Gaussian5x5 ;
                      break;
                    case 8  :
                      mask = TGIS_PixelFilterMaskType.Gaussian7x7 ;
                      break;
                    case 9  :
                      mask = TGIS_PixelFilterMaskType.Laplacian3x3 ;
                      break;
                    case 10 :
                      mask = TGIS_PixelFilterMaskType.Laplacian5x5 ;
                      break;
                    case 11 :
                      mask = TGIS_PixelFilterMaskType.GradientNorth ;
                      break;
                    case 12 :
                      mask = TGIS_PixelFilterMaskType.GradientEast ;
                      break;
                    case 13 :
                      mask = TGIS_PixelFilterMaskType.GradientSouth ;
                      break;
                    case 14 :
                      mask = TGIS_PixelFilterMaskType.GradientWest ;
                      break;
                    case 15 :
                      mask = TGIS_PixelFilterMaskType.GradientNorthwest ;
                      break;
                    case 16 :
                      mask = TGIS_PixelFilterMaskType.GradientNortheast ;
                      break;
                    case 17 :
                      mask = TGIS_PixelFilterMaskType.GradientSouthwest ;
                      break;
                    case 18 :
                      mask = TGIS_PixelFilterMaskType.GradientSoutheast ;
                      break;
                    case 19 :
                      mask = TGIS_PixelFilterMaskType.PointDetector ;
                      break;
                    case 20 :
                      mask = TGIS_PixelFilterMaskType.LineDetectorHorizontal ;
                      break;
                    case 21 :
                      mask = TGIS_PixelFilterMaskType.LineDetectorVertical ;
                      break;
                    case 22 :
                      mask = TGIS_PixelFilterMaskType.LineDetectorLeftDiagonal ;
                      break;
                    case 23 :
                      mask = TGIS_PixelFilterMaskType.LineDetectorHorizontal ;
                      break;
                    default : 
                      mask = 0;
                }
                ((TGIS_PixelFilterConvolution)flrt).setMaskType(mask);
                break;
            }
            case 4:{
                flrt = new TGIS_PixelFilterSobelMagnitude();
                ((TGIS_PixelFilterSobelMagnitude)flrt).setBlockSize(block);
                break;
            }
            case 5:{
                flrt = new TGIS_PixelFilterRange();
                ((TGIS_PixelFilterRange)flrt).setBlockSize(block);
                break;
            }
            case 6:{
                flrt = new TGIS_PixelFilterMidpoint();
                ((TGIS_PixelFilterMidpoint)flrt).setBlockSize(block);
                break;
            }
            case 7:{
                flrt = new TGIS_PixelFilterMinimum();
                ((TGIS_PixelFilterMinimum)flrt).setBlockSize(block);
                break;
            }
            case 8:{
                flrt = new TGIS_PixelFilterMaximum();
                ((TGIS_PixelFilterMaximum)flrt).setBlockSize(block);
                break;
            }
            case 9:{
                flrt = new TGIS_PixelFilterArithmeticMean();
                ((TGIS_PixelFilterArithmeticMean)flrt).setBlockSize(block);
                break;
            }
            case 10:{
                flrt = new TGIS_PixelFilterAlphaTrimmedMean();
                ((TGIS_PixelFilterAlphaTrimmedMean)flrt).setBlockSize(block);
                break;
            }
            case 11:{
                flrt = new TGIS_PixelFilterContraHarmonicMean();
                ((TGIS_PixelFilterContraHarmonicMean)flrt).setBlockSize(block);
                break;
            }
            case 12:{
                flrt = new TGIS_PixelFilterGeometricMean();
                ((TGIS_PixelFilterGeometricMean)flrt).setBlockSize(block);
                break;
            }
            case 13:{
                flrt = new TGIS_PixelFilterHarmonicMean();
                ((TGIS_PixelFilterHarmonicMean)flrt).setBlockSize(block);
                break;
            }
            case 14:{
                flrt = new TGIS_PixelFilterWeightedMean();
                ((TGIS_PixelFilterWeightedMean)flrt).setBlockSize(block);
                break;
            }
            case 15:{
                flrt = new TGIS_PixelFilterYpMean();
                ((TGIS_PixelFilterYpMean)flrt).setBlockSize(block);
                break;
            }
            case 16:{
                flrt = new TGIS_PixelFilterMajority();
                ((TGIS_PixelFilterMajority)flrt).setBlockSize(block);
                break;
            }
            case 17:{
                flrt = new TGIS_PixelFilterMinority();
                ((TGIS_PixelFilterMinority)flrt).setBlockSize(block);
                break;
            }
            case 18:{
                flrt = new TGIS_PixelFilterMedian();
                ((TGIS_PixelFilterMedian)flrt).setBlockSize(block);
                break;
            }
            case 19:{
                flrt = new TGIS_PixelFilterWeightedMedian();
                ((TGIS_PixelFilterWeightedMedian)flrt).setBlockSize(block);
                break;
            }
            case 20:{
                flrt = new TGIS_PixelFilterSum();
                ((TGIS_PixelFilterSum)flrt).setBlockSize(block);
                break;
            }
            case 21:{
                flrt = new TGIS_PixelFilterStandardDeviation();
                ((TGIS_PixelFilterStandardDeviation)flrt).setBlockSize(block);
                break;
            }
            case 22:{
                flrt = new TGIS_PixelFilterUniqueCount();
                ((TGIS_PixelFilterUniqueCount)flrt).setBlockSize(block);
                break;
            }
            case 23:{
                flrt = new TGIS_PixelFilterErosion();
                switch( cbStructuring.getSelectedIndex() ){
                    case 0 :
                      struct = TGIS_PixelFilterStructuringElementType.Square             ;
                      break;
                    case 1 :
                      struct = TGIS_PixelFilterStructuringElementType.Diamond            ;
                      break;
                    case 2 :
                      struct = TGIS_PixelFilterStructuringElementType.Disk               ;
                      break;
                    case 3 :
                      struct = TGIS_PixelFilterStructuringElementType.LineHorizontal     ;
                      break;
                    case 4 :
                      struct = TGIS_PixelFilterStructuringElementType.LineVertical       ;
                      break;
                    case 5 :
                      struct = TGIS_PixelFilterStructuringElementType.LineLeftDiagonal   ;
                      break;
                    case 6 :
                      struct = TGIS_PixelFilterStructuringElementType.LineRightDiagonal  ;
                      break;
                    default:
                      struct = 0 ;  
                }
                ((TGIS_PixelFilterErosion)flrt ).setStructuringElementType( struct ) ;
                ((TGIS_PixelFilterErosion)flrt ).setBlockSize( block );
                break;
            }
            case 24:{
                flrt = new TGIS_PixelFilterDilation();
                switch( cbStructuring.getSelectedIndex() ){
                    case 0 :
                      struct = TGIS_PixelFilterStructuringElementType.Square             ;
                      break;
                    case 1 :
                      struct = TGIS_PixelFilterStructuringElementType.Diamond            ;
                      break;
                    case 2 :
                      struct = TGIS_PixelFilterStructuringElementType.Disk               ;
                      break;
                    case 3 :
                      struct = TGIS_PixelFilterStructuringElementType.LineHorizontal     ;
                      break;
                    case 4 :
                      struct = TGIS_PixelFilterStructuringElementType.LineVertical       ;
                      break;
                    case 5 :
                      struct = TGIS_PixelFilterStructuringElementType.LineLeftDiagonal   ;
                      break;
                    case 6 :
                      struct = TGIS_PixelFilterStructuringElementType.LineRightDiagonal  ;
                      break;
                    default:
                      struct = 0 ;  
                }
                ((TGIS_PixelFilterDilation)flrt ).setStructuringElementType( struct ) ;
                ((TGIS_PixelFilterDilation)flrt ).setBlockSize( block );
                break;
            }
            case 25:{
                flrt = new TGIS_PixelFilterOpening();
                switch( cbStructuring.getSelectedIndex() ){
                    case 0 :
                      struct = TGIS_PixelFilterStructuringElementType.Square             ;
                      break;
                    case 1 :
                      struct = TGIS_PixelFilterStructuringElementType.Diamond            ;
                      break;
                    case 2 :
                      struct = TGIS_PixelFilterStructuringElementType.Disk               ;
                      break;
                    case 3 :
                      struct = TGIS_PixelFilterStructuringElementType.LineHorizontal     ;
                      break;
                    case 4 :
                      struct = TGIS_PixelFilterStructuringElementType.LineVertical       ;
                      break;
                    case 5 :
                      struct = TGIS_PixelFilterStructuringElementType.LineLeftDiagonal   ;
                      break;
                    case 6 :
                      struct = TGIS_PixelFilterStructuringElementType.LineRightDiagonal  ;
                      break;
                    default:
                      struct = 0 ;  
                }
                ((TGIS_PixelFilterOpening)flrt ).setStructuringElementType( struct ) ;
                ((TGIS_PixelFilterOpening)flrt ).setBlockSize( block );
                break;
            }
            case 26:{
                flrt = new TGIS_PixelFilterClosing();
                switch( cbStructuring.getSelectedIndex() ){
                    case 0 :
                      struct = TGIS_PixelFilterStructuringElementType.Square             ;
                      break;
                    case 1 :
                      struct = TGIS_PixelFilterStructuringElementType.Diamond            ;
                      break;
                    case 2 :
                      struct = TGIS_PixelFilterStructuringElementType.Disk               ;
                      break;
                    case 3 :
                      struct = TGIS_PixelFilterStructuringElementType.LineHorizontal     ;
                      break;
                    case 4 :
                      struct = TGIS_PixelFilterStructuringElementType.LineVertical       ;
                      break;
                    case 5 :
                      struct = TGIS_PixelFilterStructuringElementType.LineLeftDiagonal   ;
                      break;
                    case 6 :
                      struct = TGIS_PixelFilterStructuringElementType.LineRightDiagonal  ;
                      break;
                    default:
                      struct = 0 ;  
                }
                ((TGIS_PixelFilterClosing)flrt ).setStructuringElementType( struct ) ;
                ((TGIS_PixelFilterClosing)flrt ).setBlockSize( block );
                break;
            }
            case 27:{
                flrt = new TGIS_PixelFilterTopHat();
                switch( cbStructuring.getSelectedIndex() ){
                    case 0 :
                      struct = TGIS_PixelFilterStructuringElementType.Square             ;
                      break;
                    case 1 :
                      struct = TGIS_PixelFilterStructuringElementType.Diamond            ;
                      break;
                    case 2 :
                      struct = TGIS_PixelFilterStructuringElementType.Disk               ;
                      break;
                    case 3 :
                      struct = TGIS_PixelFilterStructuringElementType.LineHorizontal     ;
                      break;
                    case 4 :
                      struct = TGIS_PixelFilterStructuringElementType.LineVertical       ;
                      break;
                    case 5 :
                      struct = TGIS_PixelFilterStructuringElementType.LineLeftDiagonal   ;
                      break;
                    case 6 :
                      struct = TGIS_PixelFilterStructuringElementType.LineRightDiagonal  ;
                      break;
                    default:
                      struct = 0 ;  
                }
                ((TGIS_PixelFilterTopHat)flrt ).setStructuringElementType( struct ) ;
                ((TGIS_PixelFilterTopHat)flrt ).setBlockSize( block );
                break;
            }
            case 28:{
                flrt = new TGIS_PixelFilterBottomHat();
                switch( cbStructuring.getSelectedIndex() ){
                    case 0 :
                      struct = TGIS_PixelFilterStructuringElementType.Square             ;
                      break;
                    case 1 :
                      struct = TGIS_PixelFilterStructuringElementType.Diamond            ;
                      break;
                    case 2 :
                      struct = TGIS_PixelFilterStructuringElementType.Disk               ;
                      break;
                    case 3 :
                      struct = TGIS_PixelFilterStructuringElementType.LineHorizontal     ;
                      break;
                    case 4 :
                      struct = TGIS_PixelFilterStructuringElementType.LineVertical       ;
                      break;
                    case 5 :
                      struct = TGIS_PixelFilterStructuringElementType.LineLeftDiagonal   ;
                      break;
                    case 6 :
                      struct = TGIS_PixelFilterStructuringElementType.LineRightDiagonal  ;
                      break;
                    default:
                      struct = 0 ;  
                }
                ((TGIS_PixelFilterBottomHat)flrt ).setStructuringElementType( struct ) ;
                ((TGIS_PixelFilterBottomHat)flrt ).setBlockSize( block );
                break;
            }
            default: return;
        }
        
        flrt.setSourceLayer(input);
        flrt.setDestinationLayer(output);
        flrt.setBand(1);
        flrt.setColorSpace(TGIS_PixelFilterColorSpace.HSL);
        flrt.setBusyEvent(new TGIS_BusyEvent() {
            @Override
            public void Invoke(Object o, TGIS_BusyEventArgs _e) {
                if (_e.getPos() < 0) {
                    pProgress.setValue(pProgress.getMaximum());
                } else {
                    if (_e.getPos() == 0)
                    {
                        pProgress.setMinimum(0);
                        pProgress.setMaximum((int)_e.getEndPos());
                        pProgress.setValue(0);
                    }
                    else
                    {
                        if (_e.getPos() > 0) {
                            pProgress.setValue((int)_e.getPos());
                        }
                    }
                }
            }
        });
        
        new Thread(new Runnable() {
            @Override
            public void run() {
            flrt.Execute();

            output.getParams().getPixel().setGridShadow(false);
            output.ApplyAntialiasSettings(true);
            if ( bFirst ) {
                GIS.Delete( input.getName() );
                GIS.Add( output ) ;
                bFirst = false ;
            }
            GIS.InvalidateWholeMap() ;            }
        }).start();
    }//GEN-LAST:event_btnExecuteActionPerformed

    private void sMaskSizeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sMaskSizeStateChanged
        int i;
        i = 2 * sMaskSize.getValue() + 1;
        lblMaskSizeValue.setText(i + "x" + i);
    }//GEN-LAST:event_sMaskSizeStateChanged

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
            java.util.logging.Logger.getLogger(PixelFiltersUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PixelFiltersUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PixelFiltersUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PixelFiltersUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PixelFiltersUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private tatukgis.jdk.TGIS_ControlLegend GIS_Legend;
    private javax.swing.JButton btnExecute;
    private javax.swing.JButton btnReset;
    private javax.swing.JComboBox<String> cbMask;
    private javax.swing.JComboBox<String> cbStructuring;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> lFilters;
    private javax.swing.JLabel lblFilter;
    private javax.swing.JLabel lblMask;
    private javax.swing.JLabel lblMaskSize;
    private javax.swing.JLabel lblMaskSizeValue;
    private javax.swing.JLabel lblStructuring;
    private javax.swing.JProgressBar pProgress;
    private javax.swing.JSlider sMaskSize;
    // End of variables declaration//GEN-END:variables
    private boolean bFirst;
}
