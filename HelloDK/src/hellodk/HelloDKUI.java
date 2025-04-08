package hellodk;

import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import tatukgis.jdk.TGIS_BrushStyle;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_Lock;
import tatukgis.jdk.TGIS_Point;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_ShapeType;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.jdk.TGIS_ViewerMode;
import tatukgis.jdk.TMouseEvent;
import tatukgis.rtl.TPoint;

public class HelloDKUI extends javax.swing.JFrame {

    public HelloDKUI() {
        initComponents();
        
        GIS.setOnTapSimple(new TMouseEvent() {
            @Override
            public void Invoke(MouseEvent me) {
                TGIS_Shape shp;
                TGIS_Point ptg;
                TGIS_LayerVector lv;
                double precision;


                // ignore clicking if mode is other then select
                if(GIS.getMode() != TGIS_ViewerMode.Select) return ;

                // convert screen coordinates to map coordinates
                ptg = GIS.ScreenToMap(new TPoint(me.getX(), me.getY()));

                //get layer from the viewer
                lv = (TGIS_LayerVector)GIS.getItems().getItems(0);

                // calculate precision of location as 5 pixels
                precision = 5/GIS.getZoom() ;

                // let's try to locate a selected shape on the map
                shp = (TGIS_Shape) GIS.Locate(ptg, precision) ;

                // not found?
                if(shp == null) return ;

                //deselect selected shapes
                lv.DeselectAll();

                // mark shape as selected
                shp.setIsSelected(!shp.getIsSelected()) ;

                // and refresh a map
                GIS.InvalidateWholeMap() ;
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        btnOpen = new javax.swing.JButton();
        btnCreate = new javax.swing.JButton();
        btnFind = new javax.swing.JButton();
        btnZoom = new javax.swing.JButton();
        btnDrag = new javax.swing.JButton();
        btnSelect = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HelloDK - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(900, 600));

        btnOpen.setText("Open project");
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });

        btnCreate.setText("Create shape");
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });

        btnFind.setText("Find shapes");
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });

        btnZoom.setText("Zooming");
        btnZoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZoomActionPerformed(evt);
            }
        });

        btnDrag.setText("Dragging");
        btnDrag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDragActionPerformed(evt);
            }
        });

        btnSelect.setText("Selecting");
        btnSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnOpen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnZoom)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDrag)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSelect)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCreate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFind)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 901, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOpen)
                    .addComponent(btnCreate)
                    .addComponent(btnFind)
                    .addComponent(btnZoom)
                    .addComponent(btnDrag)
                    .addComponent(btnSelect))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        //Open a project
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/World/WorldDCW/world.shp");
        GIS.setMode(TGIS_ViewerMode.Select);
    }//GEN-LAST:event_btnOpenActionPerformed

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
        TGIS_LayerVector ll;
        TGIS_Shape shp;
        
        // lets find if such layer already exists
        ll = (TGIS_LayerVector)GIS.Get( "edit layer" ) ;
        if( ll != null ) return;
            
        
        // create a new layer and add it to the viewer
        ll = new TGIS_LayerVector() ;
        ll.setName( "edit layer" ) ;
        ll.setCS( GIS.getCS() ) ; // same coordinate system as a viewer

        // making inside of polygon transparent with blue border
        ll.getParams().getArea().setOutlineColor(TGIS_Color.getBlue());
        ll.getParams().getArea().setPattern(TGIS_BrushStyle.Clear);        
        
        GIS.Add( ll ) ;


        // create a shape and add it to polygon
        shp = ll.CreateShape( TGIS_ShapeType.Polygon ) ;


        // we group operation together 
        shp.Lock( TGIS_Lock.Extent ) ;
        shp.AddPart() ;
        
        // add some veritices
        shp.AddPoint( new TGIS_Point( 10, 10 ) );
        shp.AddPoint( new TGIS_Point( 10, 80 ) );
        shp.AddPoint( new TGIS_Point( 80, 90 ) );
        shp.AddPoint( new TGIS_Point( 90, 10 ) );
        
        // unlock operation, close polygon if necessary
        shp.Unlock() ; 

        // and now refresh map
        GIS.InvalidateWholeMap();
    }//GEN-LAST:event_btnCreateActionPerformed

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        TGIS_LayerVector ll;
        TGIS_LayerVector lv;
        TGIS_Shape selShp, tmpShp;
        
        //get layer from the viewer
        ll = (TGIS_LayerVector) GIS.Get( "edit layer" );
        if (ll == null) return ;
        
        // lets get a layer with world shape
        // names are constructed based on layer name
        lv = (TGIS_LayerVector)GIS.Get("world");
        
        //deselect selected shapes
        lv.DeselectAll();
        
        // and we need a created shape, with we want   
        // to use as selection shape
        selShp = ll.GetShape(1) ;  // just a first shape form the layer

        // for file based layer we should pin shape to memory
        // otherwise it should be discarded 
        selShp = selShp.MakeEditable();

        // Group all future screen updates into one drawing operatiiom
        GIS.Lock();
            
        // so now we search for all shapes with DE9-IM relationship
        // which labels starts with 's' (with use of SQL syntax)
        // in this case we find “T*****FF*” contains relationship
        // which means that we will find only shapes inside the polygon
        for( Object tmp : lv.Loop( selShp.getExtent(), "label LIKE 's%'", selShp, "T*****FF*" )){
            tmpShp = (TGIS_Shape)tmp ;  
            tmpShp.setIsSelected(true) ;
          }
        
        //unlock updating of the viewer
        GIS.Unlock();
            
        // and now refresh map
        GIS.InvalidateWholeMap();
    }//GEN-LAST:event_btnFindActionPerformed

    private void btnZoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZoomActionPerformed
        // if the viewer is empty then do nothing
        if(GIS.getIsEmpty()) return;
        
        // if the viewer is not empty set it mode to Zoom mode
        GIS.setMode(TGIS_ViewerMode.Zoom);
    }//GEN-LAST:event_btnZoomActionPerformed

    private void btnDragActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDragActionPerformed
        // if the viewer is empty then do nothing
        if(GIS.getIsEmpty()) return;
        
        // if the viewer is not empty set it mode to Drag mode
        GIS.setMode(TGIS_ViewerMode.Drag);
    }//GEN-LAST:event_btnDragActionPerformed

    private void btnSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectActionPerformed
        // if the viewer is empty then do nothing
        if(GIS.getIsEmpty()) return;
        
        // if the viewer is not empty set it mode to Select mode
        GIS.setMode(TGIS_ViewerMode.Select);
    }//GEN-LAST:event_btnSelectActionPerformed

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
            java.util.logging.Logger.getLogger(HelloDKUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HelloDKUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HelloDKUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HelloDKUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HelloDKUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnDrag;
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnOpen;
    private javax.swing.JButton btnSelect;
    private javax.swing.JButton btnZoom;
    // End of variables declaration//GEN-END:variables
}
