package simpleedit;

import RemObjects.Elements.System.VarParameter;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import tatukgis.jdk.TGIS_BrushStyle;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_Layer;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_PenStyle;
import tatukgis.jdk.TGIS_Point;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_ShapeType;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.jdk.TGIS_ViewerMode;
import tatukgis.rtl.TNotifyEvent;
import tatukgis.rtl.TPoint;

public class SimpleEditUI extends javax.swing.JFrame {

    public SimpleEditUI() {
        initComponents();
        initOwn();
        initButtons();
    }
    
    private void initButtons(){
        btnEdit.setEnabled(false);
        btnRevert.setEnabled(false);
        btnDelete.setEnabled(false);
        btnWinding.setEnabled(false);
        btnAddShape.setEnabled(false);
        btnUndo.setEnabled(false);
        btnRedo.setEnabled(false);
    }
    
    private void initOwn(){
        TGIS_Layer ll;
        int i;
        
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/Samples/SimpleEdit/SimpleEdit.ttkproject");
        for ( i = 0 ; i < GIS.getItems().getCount() ; i++){
            ll = (TGIS_Layer)GIS.getItems().getItems(i);
            if ( ll instanceof TGIS_LayerVector){
                cbLayer.addItem(ll.getName());
            }
        }
        
        if ( GIS.getItems().getCount() > 0){
            cbLayer.setSelectedIndex(0);
        }
        
        for ( i = 0 ; i < GIS.getItems().getCount() - 1 ; i++){
            ll = (TGIS_Layer)GIS.getItems().getItems(i);
            if ( ll instanceof TGIS_LayerVector){
                cbSnapping.addItem(ll.getName());
            }
        }
        
        if ( GIS.getItems().getCount() > 0){
            cbSnapping.setSelectedIndex(0);
        }
        GIS.setOnEditorChange(new TNotifyEvent() {
                    @Override
                    public void Invoke(Object o) {
                        btnRedo.setEnabled(GIS.getEditor().getCanRedo());
                        btnUndo.setEnabled(GIS.getEditor().getCanUndo());
                    }
                });
        
        cbLayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                TGIS_Layer ll;
                int i;

                endEdit();
                //wnd.setFocus();//?
                selectClickSelf();

                for(i = 1 ; i < cbLayer.getItemCount() ; i++){
                    ll = (TGIS_Layer)GIS.Get(cbLayer.getItemAt(i));
                    if(cbLayer.getSelectedIndex() == 0)
                        ll.setActive(true);
                    else
                        ll.setActive(i == cbLayer.getSelectedIndex());
                }

                btnAddShape.setEnabled(cbLayer.getSelectedIndex() != 0);

                GIS.InvalidateWholeMap();            
            }
        });
        btnPrint.setToolTipText("Currently not working");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popUpMnu = new javax.swing.JPopupMenu();
        addPartMnu = new javax.swing.JMenuItem();
        deletePartMnu = new javax.swing.JMenuItem();
        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        zoom = new javax.swing.JLabel();
        x = new javax.swing.JLabel();
        y = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        btnSave = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnFullExtent = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnZoom = new javax.swing.JButton();
        btnDrag = new javax.swing.JButton();
        btnSelect = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        cbLayer = new javax.swing.JComboBox<>();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btnAddShape = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        btnUndo = new javax.swing.JButton();
        btnRedo = new javax.swing.JButton();
        btnRevert = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnWinding = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        btnInfo = new javax.swing.JButton();
        btnAutocenter = new javax.swing.JToggleButton();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        btnNewStyle = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JToolBar.Separator();
        cbSnapping = new javax.swing.JComboBox<>();

        addPartMnu.setName("Add part"); // NOI18N
        addPartMnu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPartMnuActionPerformed(evt);
            }
        });
        popUpMnu.add(addPartMnu);

        deletePartMnu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletePartMnuActionPerformed(evt);
            }
        });
        popUpMnu.add(deletePartMnu);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SimpleEdit - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
            setSize(new java.awt.Dimension(1000, 600));
            addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosing(java.awt.event.WindowEvent evt) {
                    formWindowClosing(evt);
                }
            });

            GIS.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                public void mouseMoved(java.awt.event.MouseEvent evt) {
                    GISMouseMoved(evt);
                }
            });
            GIS.addPaintListener(new tatukgis.jdk.PaintListener() {
                public void AfterPaintRenderer(tatukgis.jdk.RenderEvent evt) {
                }
                public void BeforePaintRenderer(tatukgis.jdk.RenderEvent evt) {
                }
                public void BeforePaint(tatukgis.jdk.PaintEvent evt) {
                }
                public void AfterPaint(tatukgis.jdk.PaintEvent evt) {
                    GISAfterPaint(evt);
                }
                public void PaintExtra(tatukgis.jdk.RenderEvent evt) {
                }
            });
            GIS.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseReleased(java.awt.event.MouseEvent evt) {
                    GISMouseReleased(evt);
                }
            });
            GIS.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    GISKeyPressed(evt);
                }
                public void keyReleased(java.awt.event.KeyEvent evt) {
                    GISKeyReleased(evt);
                }
            });

            zoom.setText("zoom: ");

            x.setText("x:");

            y.setText("y:");

            jToolBar1.setRollover(true);

            btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Save.png"))); // NOI18N
            btnSave.setToolTipText("Save");
            btnSave.setFocusable(false);
            btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            btnSave.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnSaveActionPerformed(evt);
                }
            });
            jToolBar1.add(btnSave);

            btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Print.png"))); // NOI18N
            btnPrint.setToolTipText("Print");
            btnPrint.setEnabled(false);
            btnPrint.setFocusable(false);
            btnPrint.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            btnPrint.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            jToolBar1.add(btnPrint);
            jToolBar1.add(jSeparator1);

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
            jToolBar1.add(jSeparator2);

            btnZoom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Zoom.png"))); // NOI18N
            btnZoom.setToolTipText("Zoom mode");
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
            btnDrag.setToolTipText("Drag mode");
            btnDrag.setFocusable(false);
            btnDrag.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            btnDrag.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            btnDrag.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnDragActionPerformed(evt);
                }
            });
            jToolBar1.add(btnDrag);

            btnSelect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/SelectLocate.png"))); // NOI18N
            btnSelect.setToolTipText("Select mode");
            btnSelect.setFocusable(false);
            btnSelect.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            btnSelect.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            btnSelect.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnSelectActionPerformed(evt);
                }
            });
            jToolBar1.add(btnSelect);

            btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Edit.png"))); // NOI18N
            btnEdit.setToolTipText("Edit mode");
            btnEdit.setFocusable(false);
            btnEdit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            btnEdit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            btnEdit.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnEditActionPerformed(evt);
                }
            });
            jToolBar1.add(btnEdit);
            jToolBar1.add(jSeparator3);

            cbLayer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Show all" }));
            jToolBar1.add(cbLayer);
            jToolBar1.add(jSeparator4);

            btnAddShape.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Add.png"))); // NOI18N
            btnAddShape.setToolTipText("Add shape");
            btnAddShape.setFocusable(false);
            btnAddShape.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            btnAddShape.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            btnAddShape.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnAddShapeActionPerformed(evt);
                }
            });
            jToolBar1.add(btnAddShape);
            jToolBar1.add(jSeparator5);

            btnUndo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Undo.png"))); // NOI18N
            btnUndo.setToolTipText("Undo");
            btnUndo.setFocusable(false);
            btnUndo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            btnUndo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            btnUndo.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnUndoActionPerformed(evt);
                }
            });
            jToolBar1.add(btnUndo);

            btnRedo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Redo.png"))); // NOI18N
            btnRedo.setToolTipText("Redo");
            btnRedo.setFocusable(false);
            btnRedo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            btnRedo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            btnRedo.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnRedoActionPerformed(evt);
                }
            });
            jToolBar1.add(btnRedo);

            btnRevert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Revert.png"))); // NOI18N
            btnRevert.setToolTipText("Revert shape");
            btnRevert.setFocusable(false);
            btnRevert.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            btnRevert.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            btnRevert.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnRevertActionPerformed(evt);
                }
            });
            jToolBar1.add(btnRevert);

            btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Delete.png"))); // NOI18N
            btnDelete.setToolTipText("Delete shape");
            btnDelete.setFocusable(false);
            btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            btnDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            btnDelete.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnDeleteActionPerformed(evt);
                }
            });
            jToolBar1.add(btnDelete);

            btnWinding.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/ChangeWinding.png"))); // NOI18N
            btnWinding.setToolTipText("Change winding");
            btnWinding.setFocusable(false);
            btnWinding.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            btnWinding.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            btnWinding.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnWindingActionPerformed(evt);
                }
            });
            jToolBar1.add(btnWinding);
            jToolBar1.add(jSeparator6);

            btnInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/DataTable.png"))); // NOI18N
            btnInfo.setToolTipText("Info");
            btnInfo.setFocusable(false);
            btnInfo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            btnInfo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            btnInfo.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnInfoActionPerformed(evt);
                }
            });
            jToolBar1.add(btnInfo);

            btnAutocenter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Center.png"))); // NOI18N
            btnAutocenter.setToolTipText("Autocenter");
            btnAutocenter.setFocusable(false);
            btnAutocenter.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            btnAutocenter.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            btnAutocenter.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnAutocenterActionPerformed(evt);
                }
            });
            jToolBar1.add(btnAutocenter);
            jToolBar1.add(jSeparator7);

            btnNewStyle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/FullScreen.png"))); // NOI18N
            btnNewStyle.setToolTipText("Editor new style");
            btnNewStyle.setFocusable(false);
            btnNewStyle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            btnNewStyle.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
            btnNewStyle.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnNewStyleActionPerformed(evt);
                }
            });
            jToolBar1.add(btnNewStyle);
            jToolBar1.add(jSeparator8);

            cbSnapping.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No snapping" }));
            jToolBar1.add(cbSnapping);

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(zoom)
                    .addGap(62, 62, 62)
                    .addComponent(x)
                    .addGap(30, 30, 30)
                    .addComponent(y)
                    .addContainerGap(948, Short.MAX_VALUE))
                .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(zoom)
                        .addComponent(x)
                        .addComponent(y))
                    .addContainerGap())
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int dialogResult;
        int dialogButton = JOptionPane.YES_NO_OPTION;
  
        endEdit();
        selectClickSelf();
        
        if ( !GIS.MustSave()) return;
        
        dialogResult = JOptionPane.showConfirmDialog (null, "Would You Like to Save your Previous Note First?","Warning",dialogButton);
        if(dialogResult == JOptionPane.YES_OPTION){
            GIS.SaveAll();
        }
    }//GEN-LAST:event_formWindowClosing

    private void btnSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectActionPerformed
        initButtons();
        btnSelect.setSelected(true);
        endEdit();
        GIS.setMode(TGIS_ViewerMode.Select);

    }//GEN-LAST:event_btnSelectActionPerformed

    private void GISMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GISMouseReleased
        VarParameter<TGIS_Point> proj;
        TGIS_Point ptg;
        TPoint pt;
        TGIS_Shape shp;
        VarParameter<Double> dist;
        VarParameter<Integer> part;
        
        proj = new VarParameter<>(new TGIS_Point());
        dist = new VarParameter<>(0.0);
        part = new VarParameter<>(0);
        
        if ( GIS.getIsEmpty() ) return;
        
        ptg = GIS.ScreenToMap( new TPoint(evt.getX(), evt.getY()));
        if (SwingUtilities.isLeftMouseButton(evt)){
            
        }
        if(SwingUtilities.isRightMouseButton(evt)){
            if ( GIS.getMode() == TGIS_ViewerMode.Edit ){
                menuPos = ptg;
                pt = new TPoint(evt.getX(), evt.getY());
                popUpMnu.show(GIS, pt.X, pt.Y);
            }
        }
        
        if(SwingUtilities.isLeftMouseButton(evt)){
            if(GIS.getMode() == TGIS_ViewerMode.Edit){
                if (editLayer == null) return;
                
                GIS.getEditor().CreateShape(editLayer, ptg, TGIS_ShapeType.Unknown);
                if(cbSnapping.getSelectedIndex() > 0){
                    GIS.getEditor().setSnapLayer(GIS.Get(((TGIS_Layer)cbSnapping.getSelectedItem()).getName()));
                }else{
                    GIS.getEditor().setSnapLayer(null);
                }
                if(!btnInfo.isSelected()){
                    info.setVisible(true);
                    info.showInfo((TGIS_Shape)GIS.getEditor().getCurrentShape());
                }
                editLayer = null;
                btnEditActionPerformed(null);
            }else{
                if(GIS.getMode() == TGIS_ViewerMode.Select){
                    endEdit();
                    if(!btnInfo.isSelected()){
                        if( info != null ){
                            info.setVisible(true);
                            info.showInfo((TGIS_Shape)GIS.getEditor().getCurrentShape());
                        }
                    }
                    
                    shp = (TGIS_Shape)GIS.Locate(ptg, 5/GIS.getZoom());
                    if(shp == null) return;
                    
                    if(cbLayer.getSelectedIndex() != 0)
                        if(shp.getLayer() != GIS.Get(cbLayer.getSelectedItem().toString())) return;

                    shp = shp.getLayer().LocateEx(ptg, 5/GIS.getZoom(), -1, dist, part, proj);
                    if(shp == null) return;

                    GIS.getEditor().EditShape(shp, part.Value);
                    if(cbSnapping.getSelectedIndex() > 0)
                        GIS.getEditor().setSnapLayer(GIS.Get(cbSnapping.getSelectedItem().toString()));
                    else
                        GIS.getEditor().setSnapLayer(null);

                    GIS.setMode(TGIS_ViewerMode.Edit);

                        if( info != null ){
                            info.setVisible(true);
                            info.showInfo(shp);
                        }
                btnEditActionPerformed(null);
                }
            }
        }
    }//GEN-LAST:event_GISMouseReleased

    private void GISKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GISKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_CONTROL){
            if(!vkControll){
                GIS.setMode(TGIS_ViewerMode.Select);
                vkControll = true;
            }
        }
        if(evt.getKeyCode() == KeyEvent.VK_DELETE){
            if(GIS.getMode() == TGIS_ViewerMode.Edit){
                GIS.getEditor().DeleteShape();
                GIS.setMode(TGIS_ViewerMode.Select);
            }
        }
    }//GEN-LAST:event_GISKeyPressed

    private void GISKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GISKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_CONTROL){
            if(btnEdit.isSelected())
                GIS.setMode(TGIS_ViewerMode.Edit);
            else if(btnDrag.isSelected())
                GIS.setMode(TGIS_ViewerMode.Drag);
            vkControll = false;
        }
    }//GEN-LAST:event_GISKeyReleased

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        endEdit();
        selectClickSelf();
        GIS.SaveAll();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnFullExtentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFullExtentActionPerformed
        GIS.FullExtent();
    }//GEN-LAST:event_btnFullExtentActionPerformed

    private void btnZoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZoomActionPerformed
        initButtons();
        btnZoom.setSelected(true);
        GIS.setMode(TGIS_ViewerMode.Zoom);
    }//GEN-LAST:event_btnZoomActionPerformed

    private void btnDragActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDragActionPerformed
        initButtons();
        btnDrag.setSelected(true);
        GIS.setMode(TGIS_ViewerMode.Drag);
    }//GEN-LAST:event_btnDragActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        initButtons();
        btnEdit.setEnabled(true);
        btnRevert.setEnabled(true);
        btnDelete.setEnabled(true);
        btnWinding.setEnabled(true);

        btnEdit.setSelected(true);
        btnEdit.requestFocus();
        if(GIS.getMode() == TGIS_ViewerMode.Edit) return;
        endEdit();
        GIS.setMode(TGIS_ViewerMode.Select);
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnAddShapeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddShapeActionPerformed
        endEdit();
        GIS.setMode(TGIS_ViewerMode.Edit);
        editLayer = (TGIS_Layer)GIS.Get(cbLayer.getSelectedItem().toString());
    }//GEN-LAST:event_btnAddShapeActionPerformed

    private void btnUndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUndoActionPerformed
        GIS.getEditor().Undo();
        if(GIS.getAutoCenter())
            GIS.setZoom(GIS.getZoom());
    }//GEN-LAST:event_btnUndoActionPerformed

    private void btnRedoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRedoActionPerformed
        GIS.getEditor().Redo();
        if(GIS.getAutoCenter())
            GIS.setZoom(GIS.getZoom());
    }//GEN-LAST:event_btnRedoActionPerformed

    private void btnRevertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRevertActionPerformed
        GIS.getEditor().RevertShape();
        if(!btnInfo.isSelected()){
            info.setVisible(true);
            info.showInfo((TGIS_Shape)GIS.getEditor().getCurrentShape());
        }
    }//GEN-LAST:event_btnRevertActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        GIS.getEditor().DeleteShape();
        selectClickSelf();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInfoActionPerformed
        if(!btnInfo.isSelected()){
            if(info == null){
                info = new InfoUI();
                info.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                info.setVisible(true);
            }else{
                info.setVisible(true);
                info.showInfo((TGIS_Shape)GIS.getEditor().getCurrentShape());
            }
        }else{
            info.dispose();
        }
    }//GEN-LAST:event_btnInfoActionPerformed

    private void GISMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GISMouseMoved
        TGIS_Point ptg;
        
        if(GIS.getIsEmpty()) return;
        
        ptg = GIS.ScreenToMap(new TPoint(evt.getX(), evt.getY()));
        x.setText(String.format("x: %.4f", ptg.X));
        y.setText(String.format("y: %.4f", ptg.Y));
    }//GEN-LAST:event_GISMouseMoved

    private void btnWindingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWindingActionPerformed
        GIS.getEditor().ChangeWinding();
    }//GEN-LAST:event_btnWindingActionPerformed

    private void deletePartMnuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletePartMnuActionPerformed
        GIS.getEditor().DeletePart();
    }//GEN-LAST:event_deletePartMnuActionPerformed

    private void addPartMnuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPartMnuActionPerformed
        GIS.getEditor().CreatePart(TGIS_Utils.GisPoint3DFrom2D(menuPos));
    }//GEN-LAST:event_addPartMnuActionPerformed

    private void btnAutocenterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAutocenterActionPerformed
        GIS.setAutoCenter(btnAutocenter.isSelected());
    }//GEN-LAST:event_btnAutocenterActionPerformed

    private void btnNewStyleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewStyleActionPerformed
        GIS.getEditor().getEditingLinesStyle().setBrushStyle(TGIS_BrushStyle.Solid);
        GIS.getEditor().getEditingLinesStyle().setBrushColor(TGIS_Color.getGreen());
        GIS.getEditor().getEditingLinesStyle().setPenStyle(TGIS_PenStyle.Dot);
        GIS.getEditor().getEditingLinesStyle().setPenColor(TGIS_Color.getLime());
        
        GIS.getEditor().getEditingPointsStyle().getPointsFont().Name = "Verdana";
        GIS.getEditor().getEditingPointsStyle().getPointsFont().Size = 8;
        GIS.getEditor().getEditingPointsStyle().getPointsFont().Color = TGIS_Color.getWhite();
        
        GIS.getEditor().getEditingPointsStyle().getActivePoints().setBrushStyle(TGIS_BrushStyle.Solid);
        GIS.getEditor().getEditingPointsStyle().getActivePoints().setBrushColor(TGIS_Color.getGreen());
        GIS.getEditor().getEditingPointsStyle().getActivePoints().setPenStyle(TGIS_PenStyle.Solid);
        GIS.getEditor().getEditingPointsStyle().getActivePoints().setPenColor(TGIS_Color.getBlack());
        
        GIS.getEditor().getEditingPointsStyle().getSelectedPoints().setBrushStyle(TGIS_BrushStyle.Solid);
        GIS.getEditor().getEditingPointsStyle().getSelectedPoints().setBrushColor(TGIS_Color.getBlue());
        GIS.getEditor().getEditingPointsStyle().getSelectedPoints().setPenStyle(TGIS_PenStyle.Solid);
        GIS.getEditor().getEditingPointsStyle().getSelectedPoints().setPenColor(TGIS_Color.getBlack());
        
        GIS.getEditor().getEditingPointsStyle().getPoints3D().setBrushStyle(TGIS_BrushStyle.Solid);
        GIS.getEditor().getEditingPointsStyle().getPoints3D().setBrushColor(TGIS_Color.getPurple());
        GIS.getEditor().getEditingPointsStyle().getPoints3D().setPenStyle(TGIS_PenStyle.Solid);
        GIS.getEditor().getEditingPointsStyle().getPoints3D().setPenColor(TGIS_Color.getOlive());
        
        GIS.getEditor().getEditingPointsStyle().getSnappingPoints().setBrushStyle(TGIS_BrushStyle.Solid);
        GIS.getEditor().getEditingPointsStyle().getSnappingPoints().setBrushColor(TGIS_Color.getRed());
        GIS.getEditor().getEditingPointsStyle().getSnappingPoints().setPenStyle(TGIS_PenStyle.Solid);
        GIS.getEditor().getEditingPointsStyle().getSnappingPoints().setPenColor(TGIS_Color.getYellow());
        
        if(GIS.getEditor().getInEdit())
            GIS.getEditor().RefreshShape();
    }//GEN-LAST:event_btnNewStyleActionPerformed

    private void GISAfterPaint(tatukgis.jdk.PaintEvent evt) {//GEN-FIRST:event_GISAfterPaint
        zoom.setText(String.format("Zoom: %.4f", GIS.getZoomEx()));
    }//GEN-LAST:event_GISAfterPaint

    private void endEdit(){
        btnEdit.setEnabled(false);
        btnRevert.setEnabled(false);
        btnDelete.setEnabled(false);
        btnWinding.setEnabled(false);
        
        editLayer = null;
        
        GIS.getEditor().EndEdit();
        
    }
    
    private void selectClickSelf(){
        endEdit();
        GIS.setMode(TGIS_ViewerMode.Select);
    }
    
    public static void main(String args[]) {
        /* Set the Windows look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SimpleEditUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SimpleEditUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SimpleEditUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SimpleEditUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SimpleEditUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.JMenuItem addPartMnu;
    private javax.swing.JButton btnAddShape;
    private javax.swing.JToggleButton btnAutocenter;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDrag;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnFullExtent;
    private javax.swing.JButton btnInfo;
    private javax.swing.JButton btnNewStyle;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnRedo;
    private javax.swing.JButton btnRevert;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSelect;
    private javax.swing.JButton btnUndo;
    private javax.swing.JButton btnWinding;
    private javax.swing.JButton btnZoom;
    private javax.swing.JComboBox<String> cbLayer;
    private javax.swing.JComboBox<String> cbSnapping;
    private javax.swing.JMenuItem deletePartMnu;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JToolBar.Separator jSeparator8;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPopupMenu popUpMnu;
    private javax.swing.JLabel x;
    private javax.swing.JLabel y;
    private javax.swing.JLabel zoom;
    // End of variables declaration//GEN-END:variables
    private TGIS_Layer editLayer;
    private TGIS_Point menuPos;
    private Boolean vkControll;
    private InfoUI info;
}
