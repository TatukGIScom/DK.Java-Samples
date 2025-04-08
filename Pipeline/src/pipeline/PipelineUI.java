package pipeline;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.BadLocationException;
import tatukgis.jdk.DialogResult;
import tatukgis.jdk.TGIS_BusyEvent;
import tatukgis.jdk.TGIS_BusyEventArgs;
import tatukgis.jdk.TGIS_BusyEventManager;
import tatukgis.jdk.TGIS_HelpEvent;
import tatukgis.jdk.TGIS_HelpEventArgs;
import tatukgis.jdk.TGIS_Pipeline;
import tatukgis.jdk.TGIS_PipelineMessageEvent;
import tatukgis.jdk.TGIS_PipelineOperationAbstract;
import tatukgis.jdk.TGIS_PipelineOperationEvent;
import tatukgis.jdk.TGIS_PipelineParamsEditor;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.rtl.TStringList;

public class PipelineUI extends javax.swing.JFrame {

    public PipelineUI() {
        initComponents();
        initOwn();
    }
    
    
    
    private void doBusyEvent(Object sender, TGIS_BusyEventArgs e) throws InterruptedException{
        TGIS_BusyEventManager eventManager;
        JProgressBar progressBar;
        JLabel nameLabel;
        JLabel etlLabel;
        long etl;
        String name;
        int i, pos, maxValue;

        
        if(sender.getClass() == TGIS_BusyEventManager.class){
            eventManager = (TGIS_BusyEventManager)sender;
            int asd = eventManager.getCount();
            for( i = 0; i < asd; i++ ){
                //dynamically add or remove progress controls
                if( progressBarList.size() <= i ){
                  addInfoLabel();
                  addEtlLabel(); 
                  addProgressBar();
                }

                if(progressBarList.size() > eventManager.getCount()){
                  deleteInfoLabel() ;
                  deleteEtlLabel() ;
                  deleteProgressBar() ;
                }
                
                progressBar = progressBarList.get(i);
                etlLabel = etlLabelList.get(i);
                nameLabel = nameLabelList.get(i);
                
                name = eventManager.getName(i);
                pos = eventManager.getPosition(i);
                                
                switch(pos){
                    case -1 : 
                        progressBar.setValue(0);
                        nameLabel.setText("");
                        etlLabel.setText("");
                        break;
                    case 0 :
                        nameLabel.setText(name);
                        
                        maxValue = eventManager.getMax(i);
                        progressBar.setMinimum(0);
                        progressBar.setMaximum(maxValue);
                        progressBar.setValue(pos);
                        break;
                    default :
                        nameLabel.setText(name);
                        progressBar.setValue(pos);
                        
                        etl = eventManager.getEstimatedTimeLeft(i);
                        etlLabel.setText("End time: " + LocalTime.now().plusSeconds(etl/1000));
                }
            }
        }else{          
            progressBar = progressBarList.get(0);
            pos = (int) e.getPos();
            switch (pos) {
                case 0:
                    progressBar.setMinimum(0);
                    progressBar.setMaximum(100);
                    progressBar.setValue(0);
                    break;
                case -1:
                    progressBar.setValue(0);
                    break;
                default:
                    progressBar.setValue(pos);
                    break;
            }
        }
    }
    
    private void addInfoLabel(){
        JLabel infoLabel;
        
        infoLabel = new JLabel();
        infoLabel.setVisible(true);
        pnlDynamicProgress.add(infoLabel);
        nameLabelList.add(infoLabel);  
    }
    
    private void addEtlLabel(){
        JLabel etlLabel;
        
        etlLabel = new JLabel();
        etlLabel.setVisible(true);
        pnlDynamicProgress.add(etlLabel);
        etlLabelList.add(etlLabel);
    }
    
    private void addProgressBar(){
        JProgressBar progressBar;
        
        progressBar = new JProgressBar();
        progressBar.setVisible(true);
        pnlDynamicProgress.add(progressBar);
        progressBarList.add(progressBar);
    }
    
    private void deleteInfoLabel(){
        pnlDynamicProgress.remove(nameLabelList.get(nameLabelList.size() - 1));
        nameLabelList.remove(nameLabelList.size() - 1);
    }
    
    private void deleteEtlLabel(){
        pnlDynamicProgress.remove(etlLabelList.get(etlLabelList.size() - 1));
        etlLabelList.remove(etlLabelList.size() - 1);
    }
    
    private void deleteProgressBar(){
        pnlDynamicProgress.remove(progressBarList.get(progressBarList.size() - 1));
        progressBarList.remove(progressBarList.size() - 1);
    }

    
    private void doPipelineHelp( Object _sender, String _name ) throws URISyntaxException, IOException{
        if( Desktop.isDesktopSupported() ){
            String url = "https://docs.tatukgis.com/EDT5/guide:help:dkbuiltin:" +
                   _name ;  
            Desktop.getDesktop().browse(new URI(url));
        }
    }
    
    private void doPipelineMessage( String _message ){
        JOptionPane.showMessageDialog(this, _message);
    }
    
    private void doPipelineForm( TGIS_PipelineOperationAbstract _operation ){
        // alternative ways of bringing up the TGIS_PipelineParamsEditor
        // if TGIS_PipelineParamsEditor.ShowPipelineOperationParams( Self, _operation, doPipelineHelp ) = DialogResult.OK then
        // if TGIS_PipelineParamsEditor.ShowPipelineOperationParams( _operation, doPipelineHelp ) = DialogResult.OK then  
        TGIS_PipelineParamsEditor frm;
        
        frm = new TGIS_PipelineParamsEditor() ;
        if( frm.Execute( _operation, new TGIS_HelpEvent() {
            @Override
            public void Invoke(Object o, TGIS_HelpEventArgs tgsh) {
                try {
                    doPipelineHelp(o, tgsh.getName());
                } catch (URISyntaxException | IOException ex) {
                    Logger.getLogger(PipelineUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } ) == DialogResult.Ok ){ 
            try {
                taCode.replaceRange(_operation.AsText() + "\r\n", taCode.getLineStartOffset(oPipelineLine - 1), taCode.getLineEndOffset(oPipelineLine - 1));
            } catch (BadLocationException ex) {
                Logger.getLogger(PipelineUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void readFromFile( File _f ){
        File f;
        BufferedReader br;
        String line;
        
        taCode.setText("");

        try {
            f = _f;
            br = new BufferedReader( new FileReader(f) );
            line = br.readLine();

            while( line != null ){
                taCode.append(line + "\r\n");
                line = br.readLine();
            }
            br.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(PipelineUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PipelineUI.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    private void initOwn(){
        int i;
        
        readFromFile(new File( TGIS_Utils.GisSamplesDataDirDownload() + "/Samples/Pipeline/contouring.ttkpipeline" ));
        
        lstModel = new DefaultListModel<>();
        lstCommands.setModel(lstModel);
        
        oPipeline = new TGIS_Pipeline();
        oPipeline.setGIS(GIS);
        
        progressBarList = new ArrayList<>();
        nameLabelList = new ArrayList<>();
        etlLabelList = new ArrayList<>();
        
        oPipeline.setBusyEvent(new TGIS_BusyEvent() {
            @Override
            public void Invoke(Object o, TGIS_BusyEventArgs tgsb) {
                try {
                    doBusyEvent(o, tgsb);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PipelineUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        oPipeline.setShowMessageEvent(new TGIS_PipelineMessageEvent() {
            @Override
            public void Invoke(String string) {
                doPipelineMessage(string);
            }
        });
        oPipeline.setShowFormEvent(new TGIS_PipelineOperationEvent() {
            @Override
            public void Invoke(TGIS_PipelineOperationAbstract tgsp) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        doPipelineForm(tgsp);
                    }
                });
            }
        });
        oPipeline.setLogErrorEvent(new TGIS_PipelineMessageEvent() {
            @Override
            public void Invoke(String string) {
                doPipelineMessage(string);
            }
        });
        oPipeline.setLogWarningEvent(new TGIS_PipelineMessageEvent() {
            @Override
            public void Invoke(String string) {
                doPipelineMessage(string);
            }
        });
        
        prepareCommands();
        for( i = 0; i < oPipelineCommands.getCount()- 1; i++ ){
            lstModel.addElement(oPipelineCommands.getStrings(i));
        }
        
        filter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(FILE_EXT) ;
            }

            @Override
            public String getDescription() {
                return ".ttkpipeline";
            }
        };
        
        fcOpen.setFileFilter(filter);
        fcSave.setFileFilter(filter);
    }
    
    private void prepareCommands(){
        oPipelineCommands = TGIS_Utils.getPipelineOperations().getNames();     
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fcSave = new javax.swing.JFileChooser();
        fcOpen = new javax.swing.JFileChooser();
        lblCommands = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstCommands = new javax.swing.JList<>();
        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        lblCode = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        taCode = new javax.swing.JTextArea();
        btnHelp = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        btnExecute = new javax.swing.JButton();
        btnOpen = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        GIS_Legend = new tatukgis.jdk.TGIS_ControlLegend();
        pnlDynamicProgress = new javax.swing.JPanel();

        fcSave.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        fcSave.setFileFilter(null);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pipeline - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));

        lblCommands.setText("Commands");

        lstCommands.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstCommandsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lstCommands);

        GIS.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        lblCode.setText("Code");

        taCode.setColumns(20);
        taCode.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        taCode.setRows(5);
        taCode.setText("Say Text=\"Hello!\"\nLayer.Open Result=$layer Path=C:\\Users\\Public\\Documents\\TatukGIS\\Data\\Samples11\\World\\VisibleEarth\\world_8km.jpg\nMap.FullExtent\nSay Text=\"Done!\"\n");
        taCode.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        taCode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                taCodeMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(taCode);

        btnHelp.setText("Help");
        btnHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHelpActionPerformed(evt);
            }
        });

        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        btnExecute.setText("Execute");
        btnExecute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExecuteActionPerformed(evt);
            }
        });

        btnOpen.setText("Open...");
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });

        btnSave.setText("Save...");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        GIS_Legend.setGIS_Viewer(GIS);

        pnlDynamicProgress.setLayout(new javax.swing.BoxLayout(pnlDynamicProgress, javax.swing.BoxLayout.PAGE_AXIS));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnHelp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOpen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExecute)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExit))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCode)
                            .addComponent(lblCommands)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlDynamicProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(GIS_Legend, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCommands)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                    .addComponent(GIS_Legend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCode)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlDynamicProgress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHelp)
                    .addComponent(btnExit)
                    .addComponent(btnExecute)
                    .addComponent(btnOpen)
                    .addComponent(btnSave))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lstCommandsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstCommandsMouseClicked
        if( evt.getClickCount() == 2 ){          
            try {
                oPipelineLine = taCode.getLineOfOffset(taCode.getCaretPosition()) + 1 ;
                oPipeline.ShowForm(lstModel.getElementAt(lstCommands.getSelectedIndex()), oPipelineLine);
            } catch (BadLocationException ex) {
                Logger.getLogger(PipelineUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_lstCommandsMouseClicked

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
      System.exit(0);
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnExecuteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExecuteActionPerformed
        oPipeline.setSourceCode(taCode.getText());
        new Thread(() -> oPipeline.Execute()).start();    
    }//GEN-LAST:event_btnExecuteActionPerformed

    private void taCodeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taCodeMouseClicked
        if( evt.getClickCount() == 2 ){ 
            try {      
                oPipelineLine = taCode.getLineOfOffset(taCode.getCaretPosition() - 1) + 1 ;
                int start = taCode.getLineStartOffset(oPipelineLine - 1) ;
                int end = taCode.getLineEndOffset(oPipelineLine - 1) ;
                taCode.setSelectionStart(start);
                taCode.setSelectionEnd(end);
                oPipeline.setSourceCode(taCode.getText());
                if((end - start) > 0)
                    oPipeline.ShowForm( oPipelineLine );
            } catch (BadLocationException ex) {
                Logger.getLogger(PipelineUI.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_taCodeMouseClicked

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        File file;
        BufferedWriter bw;
        
        if( fcSave.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
            try {
                if( fcSave.getSelectedFile().getName().endsWith(FILE_EXT) )
                    file = fcSave.getSelectedFile();
                else
                    file = new File( fcSave.getSelectedFile().getAbsoluteFile()+ "." + FILE_EXT );
                bw = new BufferedWriter(new FileWriter(file));
                bw.write(taCode.getText());
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(PipelineUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        if( fcOpen.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            readFromFile(fcOpen.getSelectedFile());
        }
    }//GEN-LAST:event_btnOpenActionPerformed

    private void btnHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHelpActionPerformed
        
        if( Desktop.isDesktopSupported() ){
            String url = "https://docs.tatukgis.com/DK11/doc:pipeline" ;  
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (URISyntaxException ex) {
                Logger.getLogger(PipelineUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PipelineUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnHelpActionPerformed

    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PipelineUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PipelineUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PipelineUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PipelineUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PipelineUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private tatukgis.jdk.TGIS_ControlLegend GIS_Legend;
    private javax.swing.JButton btnExecute;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnHelp;
    private javax.swing.JButton btnOpen;
    private javax.swing.JButton btnSave;
    private javax.swing.JFileChooser fcOpen;
    private javax.swing.JFileChooser fcSave;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCode;
    private javax.swing.JLabel lblCommands;
    private javax.swing.JList<String> lstCommands;
    private javax.swing.JPanel pnlDynamicProgress;
    private javax.swing.JTextArea taCode;
    // End of variables declaration//GEN-END:variables
    private TGIS_Pipeline oPipeline;
    private TStringList oPipelineCommands;
    private DefaultListModel<String> lstModel;
    private Integer oPipelineLine;
    private FileFilter filter;
    private String FILE_EXT = "ttkpipeline";
    private List<JProgressBar> progressBarList;
    private List<JLabel> etlLabelList;
    private List<JLabel> nameLabelList;
}
