package interpolation;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import tatukgis.jdk.TGIS_BusyEvent;
import tatukgis.jdk.TGIS_BusyEventArgs;
import tatukgis.jdk.TGIS_CSFactory;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_Extent;
import tatukgis.jdk.TGIS_GaussianHeatmap;
import tatukgis.jdk.TGIS_InterpolationIDW;
import tatukgis.jdk.TGIS_InterpolationKriging;
import tatukgis.jdk.TGIS_InterpolationSplines;
import tatukgis.jdk.TGIS_LayerPixel;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_SemivarianceCircular;
import tatukgis.jdk.TGIS_SemivarianceExponential;
import tatukgis.jdk.TGIS_SemivarianceGaussian;
import tatukgis.jdk.TGIS_SemivarianceLinear;
import tatukgis.jdk.TGIS_SemivariancePowerLaw;
import tatukgis.jdk.TGIS_SemivarianceSpherical;
import tatukgis.jdk.TGIS_ShapePolygon;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.jdk.TGIS_VectorToGridCoordinate;

public class InterpolationUI extends javax.swing.JFrame {

    public InterpolationUI() {
        initComponents();
        initOwn();
    }

    private void initOwn(){
        cbSemivariance.setVisible(false);
        lblSemivariance.setVisible(false);
        
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/Samples/Interpolation/Interpolation.ttkproject");
        GIS.setCS(TGIS_CSFactory.ByEPSG(3395));

        GIS.FullExtent();

        rbIDW.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                doRbAnyClick();
            }
        });
        rbKriging.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                doRbAnyClick();
            }
        });
        rbSplines.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                doRbAnyClick();
            }
        });
        rbHeatMap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                doRbAnyClick();
            }
        });
        rbConcentration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                doRbAnyClick();
            }
        });

        rbIDW.setSelected(true);
        cbSemivariance.setSelectedIndex(0);
    }
    
    private void doRbAnyClick(){
        if (rbKriging.isSelected())
        {
         lblSemivariance.setVisible(true);
         cbSemivariance.setVisible(true);
        }
        else
        {
         lblSemivariance.setVisible(false);
         cbSemivariance.setVisible(false);
        } 
    } 

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgMethod = new javax.swing.ButtonGroup();
        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        progressBar1 = new javax.swing.JProgressBar();
        jPanel1 = new javax.swing.JPanel();
        lblSemivariance = new javax.swing.JLabel();
        btnGenerate = new javax.swing.JButton();
        rbKriging = new javax.swing.JRadioButton();
        rbIDW = new javax.swing.JRadioButton();
        lblMethod = new javax.swing.JLabel();
        rbConcentration = new javax.swing.JRadioButton();
        cbSemivariance = new javax.swing.JComboBox<>();
        rbHeatMap = new javax.swing.JRadioButton();
        rbSplines = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Interpolation - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setPreferredSize(new java.awt.Dimension(600, 450));
        setSize(new java.awt.Dimension(600, 450));

        GIS.setMode(tatukgis.jdk.TGIS_ViewerMode.Zoom);

        progressBar1.setStringPainted(true);

        lblSemivariance.setText("Semivariance");

        btnGenerate.setText("Generate");
        btnGenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateActionPerformed(evt);
            }
        });

        bgMethod.add(rbKriging);
        rbKriging.setText("Kriging Interpolation");

        bgMethod.add(rbIDW);
        rbIDW.setText("IDW Interpolation");

        lblMethod.setText("Method");

        bgMethod.add(rbConcentration);
        rbConcentration.setText("Concentration Map");

        cbSemivariance.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Power Law", "Exponential", "Gaussian", "Spherical", "Circular", "Linear", " " }));

        bgMethod.add(rbHeatMap);
        rbHeatMap.setText("Heat Map");

        bgMethod.add(rbSplines);
        rbSplines.setText("Splines Interpolation");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(23, 23, 23)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnGenerate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbSemivariance, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblMethod)
                        .addComponent(rbIDW)
                        .addComponent(rbKriging)
                        .addComponent(rbSplines)
                        .addComponent(rbHeatMap)
                        .addComponent(rbConcentration)
                        .addComponent(lblSemivariance))
                    .addGap(24, 24, 24)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblMethod)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(rbIDW)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(rbKriging)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(rbSplines)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(rbHeatMap)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(rbConcentration)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(lblSemivariance)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(cbSemivariance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 232, Short.MAX_VALUE)
                    .addComponent(btnGenerate)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnGenerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateActionPerformed
        btnGenerate.setEnabled(false);

        // obtain a handle to the source layer
        src = (TGIS_LayerVector)GIS.Get("temperatures");
        // obtain a handle to the polygonal layer (largest extent)
        plg = (TGIS_LayerVector)GIS.Get("country");

        // remove any previously created grid layer
        if (GIS.Get("grid") != null) {
            GIS.Delete("Grid");
        }

        // get the source layer extent
        ext = plg.getExtent();

        // calculate the height/width ratio of the extent to properly set the grid
        // resolution
        rat = (ext.YMax - ext.YMin) / (ext.XMax - ext.XMin);

        // create and initialize the destination layer
        dst = new TGIS_LayerPixel();
                              
        dst.setName("grid");
        dst.Build(true, src.getCS(), ext, GRID_RESOLUTION, (int)(Math.round(rat * GRID_RESOLUTION)));
        dst.getParams().getPixel().setGridShadow(false);
        
        // new thread for code in order to leave gui thread free for updates
        new Thread(new Runnable() {
            @Override
            public void run() {
                // choose the start color of the grid ramp
                clr = TGIS_Color.getBlue();

                // find out which vector-to-grid has beeno chosen
                if (rbIDW.isSelected()) {
                    // perform Inverse Distance Squared (IDW) interpolation
                    doIDW();
                } else
                if (rbKriging.isSelected()) {
                    // perform Kriging interpolation
                    doKriging();
                } else
                if (rbSplines.isSelected()) {
                    // perform Completely Regularized Splines interpolation
                    doSplines();
                } else 
                if (rbHeatMap.isSelected())
                {
                    // perform Heat Map generation
                    doHeatmap(false);
                    // choose the start color for the grid ramp with ALPHA=0 to make it
                    // semitransparent
                    clr = TGIS_Color.FromARGB((byte)0, (byte)0, (byte)0, (byte)255);
                }
                else
                if (rbConcentration.isSelected())
                {
                    // perform Concentration Map generation
                    doHeatmap(true);
                    // choose the start color for the grid ramp with ALPHA=0 to make it
                    // semitransparent
                    clr = TGIS_Color.FromARGB((byte)0, (byte)0, (byte)0, (byte)255);
               }

                // apply color ramp to the grid layer         
                dst.GenerateRamp(clr, TGIS_Color.getLime(), TGIS_Color.getRed(),
                        dst.getMinHeight(), (dst.getMaxHeight() - dst.getMinHeight()) / 2,
                        dst.getMaxHeight(), false,
                        (dst.getMaxHeight() - dst.getMinHeight()) / 100.0,
                        (dst.getMaxHeight() - dst.getMinHeight()) / 10.0,
                        null, false
                );

                // limit the grid visibility only to the pixels contained within a polygon
                dst.setCuttingPolygon((TGIS_ShapePolygon)plg.GetShape(6).CreateCopy());
                dst.setZOrder(1);
                // add the grid layer to the viewer
                GIS.Add(dst);

                // update the viewer to show the grid layer
                GIS.FullExtent();

                // reset the progress bar
                //progressBar1.setValue(0);
                btnGenerate.setEnabled(true);
            }
        }).start();

    }//GEN-LAST:event_btnGenerateActionPerformed

    private void doBusyEvent(Object _sender, TGIS_BusyEventArgs _e){
        if (_e.getPos() < 0) {
            progressBar1.setValue(progressBar1.getMaximum());
        } else {
            if (_e.getPos() == 0) {
                progressBar1.setMinimum(0);
                progressBar1.setMaximum((int)_e.getEndPos());
                progressBar1.setValue(0);
            }
            else {
                if (_e.getPos() > 0) {
                    progressBar1.setValue((int)_e.getPos());
                }
            }
        }
    }
    
    private void doIDW(){
        TGIS_InterpolationIDW vtg;

        vtg = new TGIS_InterpolationIDW();

        // for windowed version of this method you need to set Windowed=True
        // and at least the Radius, e.g.
        // vtg.Windowed := True ;
        // vtg.Radius := ( ext.XMax - ext.XMin )/5.0 ;

        // attach the event to automatically update the progress bar
        vtg.setBusyEvent(new TGIS_BusyEvent() {
            @Override
            public void Invoke(Object o, TGIS_BusyEventArgs tgsb) {
                doBusyEvent(o, tgsb);
            }
        });

        // set the exponent parameter of the IDW formula (default is 2.0,
        // 3.0 gives nice results in most cases)
        vtg.setExponent(3.0);
        // generate the Inverse Distance Squared (IDW) interpolation grid
        vtg.Generate(src, src.getExtent(), FIELD_VALUE, dst, dst.getExtent());
    }
    
    private void doKriging(){
        TGIS_InterpolationKriging vtg;

        vtg = new TGIS_InterpolationKriging();

        // for windowed version of this method you need to set Windowed=True
        // and at least the Radius, e.g.
        // vtg.Windowed := True ;
        // vtg.Radius := ( ext.XMax - ext.XMin )/5.0 ;

        // attach the event to automatically update the progress bar
        vtg.setBusyEvent(new TGIS_BusyEvent() {
            @Override
            public void Invoke(Object o, TGIS_BusyEventArgs tgsb) {
                doBusyEvent(o, tgsb);
            }
        });

        // set Semivarinace; default is Power Law (code for case 0 is not needed)
        switch (cbSemivariance.getSelectedIndex())
        {
            case 0: vtg.setSemivariance(new TGIS_SemivariancePowerLaw()); break;
            case 1: vtg.setSemivariance(new TGIS_SemivarianceExponential()); break;
            case 2: vtg.setSemivariance(new TGIS_SemivarianceGaussian()); break;
            case 3: vtg.setSemivariance(new TGIS_SemivarianceSpherical()); break;
            case 4: vtg.setSemivariance(new TGIS_SemivarianceCircular()); break;
            case 5: vtg.setSemivariance(new TGIS_SemivarianceLinear()); break;

        }

        // generate the Kriging interpolation grid
        vtg.Generate(src, src.getExtent(), FIELD_VALUE, dst, dst.getExtent());    
    }
    
    private void doSplines(){
        TGIS_InterpolationSplines vtg;

        vtg = new TGIS_InterpolationSplines();

        // for windowed version of this method you need to set Windowed=True
        // and at least the Radius, e.g.
        // vtg.Windowed := True ;
        // vtg.Radius := ( ext.XMax - ext.XMin )/5.0 ;

        // attach the event to automatically update the progress bar
        // attach the event to automatically update the progress bar
        vtg.setBusyEvent(new TGIS_BusyEvent() {
            @Override
            public void Invoke(Object o, TGIS_BusyEventArgs tgsb) {
                doBusyEvent(o, tgsb);
            }
        });
        
        // set the tension parameter of Splines (value need to be adjusted for
        // the data)
        vtg.setTension(1e-9);
        // generate the Completely Regularized Splines interpolation grid
        vtg.Generate(src, src.getExtent(), FIELD_VALUE, dst, dst.getExtent());
    }
    
    private void doHeatmap(boolean _concentration){
        TGIS_GaussianHeatmap vtg;
        String fld;

        vtg = new TGIS_GaussianHeatmap();

        // for Concentration Map the coordinate must be None and source field
        // must be empty
        vtg.setCoordinate(TGIS_VectorToGridCoordinate.None);
        if (_concentration)
         fld = "";
        else
         fld = FIELD_VALUE;

        // attach the event to automatically update the progress bar
        vtg.setBusyEvent(new TGIS_BusyEvent() {
            @Override
            public void Invoke(Object o, TGIS_BusyEventArgs tgsb) {
                doBusyEvent(o, tgsb);
            }
        });
        
        // estimate the 3-sigma for Gaussian (can be set manually with Radius)
        vtg.EstimateRadius(src, src.getExtent(), dst);
        // correct the Radius after estimation (if needed)
        vtg.setRadius(vtg.getRadius() / 2.0);
        // generate the Heat/Concentaration Map grid
        vtg.Generate(src, src.getExtent(), fld, dst, dst.getExtent()); 
    }
    
    
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
            java.util.logging.Logger.getLogger(InterpolationUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterpolationUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterpolationUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterpolationUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterpolationUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.ButtonGroup bgMethod;
    private javax.swing.JButton btnGenerate;
    private javax.swing.JComboBox<String> cbSemivariance;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblMethod;
    private javax.swing.JLabel lblSemivariance;
    private javax.swing.JProgressBar progressBar1;
    private javax.swing.JRadioButton rbConcentration;
    private javax.swing.JRadioButton rbHeatMap;
    private javax.swing.JRadioButton rbIDW;
    private javax.swing.JRadioButton rbKriging;
    private javax.swing.JRadioButton rbSplines;
    // End of variables declaration//GEN-END:variables
    private final String FIELD_VALUE = "TEMP" ;
    private final int GRID_RESOLUTION = 400 ;
    private TGIS_LayerVector src;
    private TGIS_LayerPixel dst;
    private TGIS_LayerVector plg;
    private TGIS_Extent ext;
    private double rat;
    private TGIS_Color clr;
}
