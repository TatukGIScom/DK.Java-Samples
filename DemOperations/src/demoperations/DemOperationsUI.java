package demoperations;

import java.awt.Toolkit;
import javax.swing.JFileChooser;
import RemObjects.Elements.System.VarParameter;
import tatukgis.jdk.TGIS_BusyEvent;
import tatukgis.jdk.TGIS_BusyEventArgs;
import tatukgis.jdk.TGIS_DemGenerator;
import tatukgis.jdk.TGIS_DemOperation;
import tatukgis.jdk.TGIS_DemOperationAspect;
import tatukgis.jdk.TGIS_DemOperationFlowDir;
import tatukgis.jdk.TGIS_DemOperationHillShade;
import tatukgis.jdk.TGIS_DemOperationMatrixGain;
import tatukgis.jdk.TGIS_DemOperationRoughness;
import tatukgis.jdk.TGIS_DemOperationSlope;
import tatukgis.jdk.TGIS_DemOperationSlopeHydro;
import tatukgis.jdk.TGIS_DemOperationTPI;
import tatukgis.jdk.TGIS_DemOperationTRI;
import tatukgis.jdk.TGIS_DemOperationTotalCurvature;
import tatukgis.jdk.TGIS_DemSlopeMode;
import tatukgis.jdk.TGIS_DemTotalCurvatureMode;
import tatukgis.jdk.TGIS_Extent;
import tatukgis.jdk.TGIS_GridOperation;
import tatukgis.jdk.TGIS_LayerPixel;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.jdk.TGIS_ViewerMode;

public class DemOperationsUI extends javax.swing.JFrame {

    public DemOperationsUI() {
        initComponents();
        initOwn();
    }
    
    private void initOwn(){
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/USA/States/California/San Bernardino/NED/w001001.adf");
        GIS.FullExtent();
        
        cbOperationActionPerformed(null);
        
        changeDEM = new TGIS_GridOperation() {
            @Override
            public boolean Invoke(Object _layer, 
                    TGIS_Extent _extent, 
                    float[][] _source, 
                    VarParameter<float[][]> _output, 
                    int _width, 
                    int _height, 
                    VarParameter<Float> _minz, 
                    VarParameter<Float> _maxz) 
            {
                final Double DEG_TO_RAD = Math.PI / 180.0 ;
                int i, j;
                Double sin_alt_rad;
                Double cos_alt_zsf;
                Double az_rad;
                Double square_z_sf;
                Double z_scale_factor;
                float minz, maxz;
                Double ZFactor;
                Double Azimuth;
                Double Altitude;
                Double XRes;
                Double YRes;
                Double Scale;
                float[] AWindow;
                int k;
                int xsize;
                int ysize;
                Double xscale;
                Double yscale;
                float val;
                Boolean usealg;
                int l1, l2, l3;
                float inodata;
                Double x, y, aspect, xx_plus_yy, cang;
                Boolean final_result;

                final_result = true ;
                AWindow = new float[9];

                xsize = _width;
                ysize = _height;
                xscale = (_extent.XMax - _extent.XMin) / xsize;
                yscale = (_extent.YMax - _extent.YMin) / ysize;
                inodata = ((TGIS_LayerPixel)_layer).getNoDataValue();

                XRes = xscale;
                YRes = yscale;

                Scale = 1.0;
                minz = GIS_MAX_SINGLE;
                maxz = -GIS_MAX_SINGLE;

                ZFactor = 2.0;
                Azimuth = (double)trbShadow.getValue();
                Altitude = 15.0;

                sin_alt_rad = Math.sin(Altitude * DEG_TO_RAD);
                az_rad = Azimuth * DEG_TO_RAD;
                z_scale_factor = ZFactor / (2 * Scale);
                cos_alt_zsf = Math.cos(Altitude * DEG_TO_RAD);
                square_z_sf = z_scale_factor * z_scale_factor;

                for (i = 2; i < _height - 1; i++)
                {
                    l1 = i - 2;
                    l2 = i - 1;
                    l3 = i;

                    for (j = 1; j < _width - 2; j++)
                    {
                        AWindow[0] = _source[l1][j - 1];
                        AWindow[1] = _source[l1][j];
                        AWindow[2] = _source[l1][j + 1];
                        AWindow[3] = _source[l2][j - 1];
                        AWindow[4] = _source[l2][j];
                        AWindow[5] = _source[l2][j + 1];
                        AWindow[6] = _source[l3][j - 1];
                        AWindow[7] = _source[l3][j];
                        AWindow[8] = _source[l3][j + 1];

                        usealg = true;
                        val = inodata;
                        if (Math.abs(AWindow[4] - inodata) < 1e-10)
                        {
                            val = inodata;
                            usealg = false;
                        }
                        else
                        {
                            for (k = 0; k < 8; k++)
                            {
                                if (Math.abs(AWindow[k] - inodata) < 1e-10)
                                {
                                    val = inodata;
                                    usealg = false;
                                    break;
                                }
                            }
                        }

                        if (usealg)
                        {
                            x = (AWindow[3] - AWindow[5]) / XRes;
                            y = (AWindow[7] - AWindow[1]) / YRes;

                            xx_plus_yy = x * x + y * y;
                            aspect = Math.atan2(y, x);
                            cang = (sin_alt_rad - cos_alt_zsf * Math.sqrt(xx_plus_yy) * Math.sin(aspect - az_rad)) / Math.sqrt(1 + square_z_sf * xx_plus_yy);

                            if (cang <= 0.0)
                                cang = 1.0;
                            else
                                cang = 1.0 + (254.0 * cang);
                            val = Float.parseFloat(cang.toString());
                        }

                        if (_source[l1][j] != inodata)
                            _output.Value[l1][j] = val;

                        if ((val < minz) && (val != inodata))
                            minz = val;

                        if ((val > maxz) && (val != inodata))
                            maxz = val;
                    }
                }
                _minz.Value = minz;
                _maxz.Value = maxz;        

                return final_result;
            }
        };
        GIS_BusyEvent = new TGIS_BusyEvent() {
            @Override
            public void Invoke(Object _sender, TGIS_BusyEventArgs _e) {
                if (_e.getPos() < 0)
                    pbProgress.setValue(pbProgress.getMaximum());
                else
                if (_e.getPos() == 0)
                {
                    pbProgress.setMinimum(0);
                    pbProgress.setMaximum((int)_e.getEndPos());
                    pbProgress.setValue(0);
                }
                else
                {
                    if (_e.getPos() > 0)
                        pbProgress.setValue((int)_e.getPos());
                }
            }
        };
        
        GIS.setBusyEvent(GIS_BusyEvent);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fcOpen = new javax.swing.JFileChooser();
        jToolBar1 = new javax.swing.JToolBar();
        btnOpen = new javax.swing.JButton();
        btnFullExtent = new javax.swing.JButton();
        btnZoom = new javax.swing.JButton();
        btnDrag = new javax.swing.JButton();
        btn3D = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        GIS_Legend = new tatukgis.jdk.TGIS_ControlLegend();
        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        trbShadow = new javax.swing.JSlider();
        cbxGrid = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        cbOperation = new javax.swing.JComboBox<>();
        pHillShadeParams = new javax.swing.JPanel();
        lAzimuth = new javax.swing.JLabel();
        lAltitude = new javax.swing.JLabel();
        lZFactor = new javax.swing.JLabel();
        tfAzimuth = new javax.swing.JTextField();
        tfAltitude = new javax.swing.JTextField();
        tfZFactor = new javax.swing.JTextField();
        cbxCombined = new javax.swing.JCheckBox();
        pSlopeParams = new javax.swing.JPanel();
        lMode = new javax.swing.JLabel();
        lScale = new javax.swing.JLabel();
        cbMode = new javax.swing.JComboBox<>();
        tfScale = new javax.swing.JTextField();
        pCurvature = new javax.swing.JPanel();
        lCurvMode = new javax.swing.JLabel();
        cbCurveMode = new javax.swing.JComboBox<>();
        cbxAngle = new javax.swing.JCheckBox();
        btnRun = new javax.swing.JButton();
        pbProgress = new javax.swing.JProgressBar();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DemOperations - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Open.png"))); // NOI18N
        btnOpen.setFocusable(false);
        btnOpen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOpen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });
        jToolBar1.add(btnOpen);

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

        btnZoom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Zoom.png"))); // NOI18N
        btnZoom.setFocusable(false);
        btnZoom.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnZoom.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnZoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZoomActionPerformed(evt);
            }
        });
        jToolBar1.add(btnZoom);

        btnDrag.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Drag.png"))); // NOI18N
        btnDrag.setFocusable(false);
        btnDrag.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDrag.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDrag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDragActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDrag);

        btn3D.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/3DRotate.png"))); // NOI18N
        btn3D.setEnabled(false);
        btn3D.setFocusable(false);
        btn3D.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn3D.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn3D.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn3DActionPerformed(evt);
            }
        });
        jToolBar1.add(btn3D);

        GIS_Legend.setGIS_Viewer(GIS);
        jScrollPane1.setViewportView(GIS_Legend);

        GIS.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Shadow angle"));

        trbShadow.setMaximum(360);
        trbShadow.setMinorTickSpacing(30);
        trbShadow.setPaintTicks(true);
        trbShadow.setValue(90);
        trbShadow.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                trbShadowStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(trbShadow, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(trbShadow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cbxGrid.setText("Attach custom grid operation");
        cbxGrid.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cbxGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxGridActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Operation:"));

        cbOperation.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hillshade", "Slope", "Slope Hydro", "Aspect", "TRI", "TPI", "Roughness", "Total Curvature", "Matrix Gain", "Flow dir" }));
        cbOperation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbOperationActionPerformed(evt);
            }
        });

        pHillShadeParams.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lAzimuth.setText("Azimuth:");

        lAltitude.setText("Altitude:");

        lZFactor.setText("Z factor:");

        tfAzimuth.setText("315");

        tfAltitude.setText("45");

        tfZFactor.setText("1");

        cbxCombined.setText("Combined");

        javax.swing.GroupLayout pHillShadeParamsLayout = new javax.swing.GroupLayout(pHillShadeParams);
        pHillShadeParams.setLayout(pHillShadeParamsLayout);
        pHillShadeParamsLayout.setHorizontalGroup(
            pHillShadeParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pHillShadeParamsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pHillShadeParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lZFactor)
                    .addComponent(lAltitude)
                    .addComponent(lAzimuth))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pHillShadeParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pHillShadeParamsLayout.createSequentialGroup()
                        .addComponent(cbxCombined)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(tfAzimuth)
                    .addComponent(tfAltitude)
                    .addComponent(tfZFactor))
                .addContainerGap())
        );
        pHillShadeParamsLayout.setVerticalGroup(
            pHillShadeParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pHillShadeParamsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pHillShadeParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lAzimuth)
                    .addComponent(tfAzimuth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pHillShadeParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lAltitude)
                    .addComponent(tfAltitude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pHillShadeParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lZFactor)
                    .addComponent(tfZFactor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxCombined)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pSlopeParams.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lMode.setText("Mode:");

        lScale.setText("Scale:");

        cbMode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Degrees", "Percent" }));
        cbMode.setSelectedIndex(1);

        tfScale.setText("1");

        javax.swing.GroupLayout pSlopeParamsLayout = new javax.swing.GroupLayout(pSlopeParams);
        pSlopeParams.setLayout(pSlopeParamsLayout);
        pSlopeParamsLayout.setHorizontalGroup(
            pSlopeParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pSlopeParamsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pSlopeParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lScale)
                    .addComponent(lMode))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pSlopeParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbMode, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tfScale))
                .addContainerGap())
        );
        pSlopeParamsLayout.setVerticalGroup(
            pSlopeParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pSlopeParamsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pSlopeParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lMode)
                    .addComponent(cbMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pSlopeParamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lScale)
                    .addComponent(tfScale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pCurvature.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lCurvMode.setText("Mode:");

        cbCurveMode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Profile", "Plan" }));

        javax.swing.GroupLayout pCurvatureLayout = new javax.swing.GroupLayout(pCurvature);
        pCurvature.setLayout(pCurvatureLayout);
        pCurvatureLayout.setHorizontalGroup(
            pCurvatureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCurvatureLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lCurvMode)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbCurveMode, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pCurvatureLayout.setVerticalGroup(
            pCurvatureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCurvatureLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pCurvatureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lCurvMode)
                    .addComponent(cbCurveMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cbxAngle.setText("Angle instead of azimuth");
        cbxAngle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pHillShadeParams, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbOperation, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pSlopeParams, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pCurvature, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxAngle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbOperation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pHillShadeParams, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pSlopeParams, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pCurvature, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxAngle)
                .addGap(13, 13, 13))
        );

        btnRun.setText("Run operation");
        btnRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxGrid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pbProgress, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(btnRun)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jPanel4, pbProgress});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxGrid)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRun)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pbProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void btnFullExtentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFullExtentActionPerformed
        if(GIS.getIsEmpty())
            return;
        GIS.FullExtent();
    }//GEN-LAST:event_btnFullExtentActionPerformed

    private void btnZoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZoomActionPerformed
        GIS.setMode(TGIS_ViewerMode.Zoom);
    }//GEN-LAST:event_btnZoomActionPerformed

    private void btnDragActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDragActionPerformed
        GIS.setMode(TGIS_ViewerMode.Drag);
    }//GEN-LAST:event_btnDragActionPerformed

    private void btn3DActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn3DActionPerformed
        //No 3D yet
    }//GEN-LAST:event_btn3DActionPerformed

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        int returnVal = fcOpen.showOpenDialog(this);
        String path ;
        
        if(returnVal == JFileChooser.APPROVE_OPTION){
        path = fcOpen.getSelectedFile().toString();

        GIS.Open(path);
        }        
    }//GEN-LAST:event_btnOpenActionPerformed

    private void cbOperationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbOperationActionPerformed
        pHillShadeParams.setVisible(false);
        pSlopeParams.setVisible(false);
        pCurvature.setVisible(false);
        cbxAngle.setVisible(false);
        
        switch(cbOperation.getSelectedIndex()){
            case 0: pHillShadeParams.setVisible(true); break ;
            case 1: pSlopeParams.setVisible(true); break ;
            case 2: pSlopeParams.setVisible(true); break ;
            case 3: cbxAngle.setVisible(true); break ;
            case 7: pCurvature.setVisible(true); break ;
        }
    }//GEN-LAST:event_cbOperationActionPerformed

    private void trbShadowStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_trbShadowStateChanged
        TGIS_LayerPixel lp;

        lp = (TGIS_LayerPixel)GIS.getItems().getItems(0);

        if (lp == null) return;

        lp.getParams().getPixel().setGridShadowAngle(trbShadow.getValue());

        if (!GIS.getInPaint())
            GIS.InvalidateWholeMap();        
    }//GEN-LAST:event_trbShadowStateChanged

    private void cbxGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxGridActionPerformed
        TGIS_LayerPixel lp;

        lp = (TGIS_LayerPixel)GIS.getItems().getItems(0);

        if (lp == null) return;

        if (cbxGrid.isSelected()){
            lp.getParams().getPixel().getAltitudeMapZones().Clear();
            lp.getParams().getPixel().setGridShadow(false);
            lp.setGridOperationEvent(changeDEM);
        }
        else{
            lp.setGridOperationEvent(null);
            lp.getParams().getPixel().setGridShadow(true);
        }        
    }//GEN-LAST:event_cbxGridActionPerformed

    private void btnRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunActionPerformed
        TGIS_LayerPixel lp;
        TGIS_LayerPixel ld;
        TGIS_DemGenerator dem;
        TGIS_DemOperation dop;
        int sm;
        int cm;

        lp = (TGIS_LayerPixel)GIS.getItems().getItems(0);

        ld = new TGIS_LayerPixel();
        ld.setName("out_");
        ld.setCS(lp.getCS());
        ld.Build(true, lp.getCS(), lp.getExtent(), lp.getBitWidth(), lp.getBitHeight());

        dem = new TGIS_DemGenerator();

        switch (cbOperation.getSelectedIndex())
        {
            // HillShade
            case 0:
                dop = new TGIS_DemOperationHillShade(Float.parseFloat(tfZFactor.getText()),
                                                     Float.parseFloat(tfAzimuth.getText()),
                                                     Float.parseFloat(tfAltitude.getText()),
                                                     cbxCombined.isSelected());
                break;
            // Slope
            case 1:
                switch (cbMode.getSelectedIndex())
                {
                    case 0: sm = TGIS_DemSlopeMode.Degrees; break;
                    case 1: sm = TGIS_DemSlopeMode.Percent; break;
                    default: sm = TGIS_DemSlopeMode.Degrees; break;
                }
                dop = new TGIS_DemOperationSlope(sm, Float.parseFloat(tfScale.getText()));
                break;
            // SlopeHydro
            case 2:
                switch (cbMode.getSelectedIndex())
                {
                    case 0: sm = TGIS_DemSlopeMode.Degrees; break;
                    case 1: sm = TGIS_DemSlopeMode.Percent; break;
                    default: sm = TGIS_DemSlopeMode.Degrees; break;
                }
                dop = new TGIS_DemOperationSlopeHydro(sm, Float.parseFloat(tfScale.getText()));
                break;

            // Aspect
            case 3: dop = new TGIS_DemOperationAspect(cbxAngle.isSelected()); break;

            // TRI
            case 4: dop = new TGIS_DemOperationTRI(); break;

            // TPI
            case 5: dop = new TGIS_DemOperationTPI(); break;

            // Roughness
            case 6: dop = new TGIS_DemOperationRoughness(); break;

            // TotalCurvature
            case 7:
                switch (cbCurveMode.getSelectedIndex())
                {
                    case 0: cm = TGIS_DemTotalCurvatureMode.Profile; break;
                    case 1: cm = TGIS_DemTotalCurvatureMode.Plan; break;
                    default: cm = TGIS_DemTotalCurvatureMode.Profile; break;
                }
                dop = new TGIS_DemOperationTotalCurvature(cm); break;

            // MatrixGain
            case 8: dop = new TGIS_DemOperationMatrixGain(); break;

            // Flow dir
            case 9: dop = new TGIS_DemOperationFlowDir(); break;

            // default
            default: dop = new TGIS_DemOperation(); break;
        }

        ld.setName("out_" + dop.Description());

        if (GIS.Get(ld.getName()) != null)
            GIS.Delete(ld.getName());

        ld.getParams().getPixel().setGridShadow(false);
        GIS.Add(ld);

        new Thread(new Runnable() {
            @Override
            public void run() {
                dem.Process(lp, lp.getExtent(), ld, dop, GIS_BusyEvent);
                GIS.InvalidateWholeMap(); 
            }
        }).start();
      
    }//GEN-LAST:event_btnRunActionPerformed

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
            java.util.logging.Logger.getLogger(DemOperationsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DemOperationsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DemOperationsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DemOperationsUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DemOperationsUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private tatukgis.jdk.TGIS_ControlLegend GIS_Legend;
    private javax.swing.JButton btn3D;
    private javax.swing.JButton btnDrag;
    private javax.swing.JButton btnFullExtent;
    private javax.swing.JButton btnOpen;
    private javax.swing.JButton btnRun;
    private javax.swing.JButton btnZoom;
    private javax.swing.JComboBox<String> cbCurveMode;
    private javax.swing.JComboBox<String> cbMode;
    private javax.swing.JComboBox<String> cbOperation;
    private javax.swing.JCheckBox cbxAngle;
    private javax.swing.JCheckBox cbxCombined;
    private javax.swing.JCheckBox cbxGrid;
    private javax.swing.JFileChooser fcOpen;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lAltitude;
    private javax.swing.JLabel lAzimuth;
    private javax.swing.JLabel lCurvMode;
    private javax.swing.JLabel lMode;
    private javax.swing.JLabel lScale;
    private javax.swing.JLabel lZFactor;
    private javax.swing.JPanel pCurvature;
    private javax.swing.JPanel pHillShadeParams;
    private javax.swing.JPanel pSlopeParams;
    private javax.swing.JProgressBar pbProgress;
    private javax.swing.JTextField tfAltitude;
    private javax.swing.JTextField tfAzimuth;
    private javax.swing.JTextField tfScale;
    private javax.swing.JTextField tfZFactor;
    private javax.swing.JSlider trbShadow;
    // End of variables declaration//GEN-END:variables
    final int GIS_MAX_SINGLE = (int)3.4e38;
    private TGIS_GridOperation changeDEM;
    private TGIS_BusyEvent GIS_BusyEvent;
}
