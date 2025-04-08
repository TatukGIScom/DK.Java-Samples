package viewer;

import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class EditUI extends javax.swing.JFrame {
   
    public EditUI(String _path){
        initComponents();
        initOwn();
        loadFromFile(_path);
    }

    private EditUI() {
        initComponents();
        initOwn();
    }
    
    private void initOwn(){
        taEdit.getDocument().addDocumentListener(new DocumentListener() {

                @Override
                public void removeUpdate(DocumentEvent e) {
                    btnSave.setEnabled(true);
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    btnSave.setEnabled(true);
                }

                @Override
                public void changedUpdate(DocumentEvent arg0) {

                }
            });
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        taEdit = new javax.swing.JTextArea();
        btnSave = new javax.swing.JButton();
        jTextPane1 = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Edit configuration");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
            setPreferredSize(new java.awt.Dimension(400, 600));
            addComponentListener(new java.awt.event.ComponentAdapter() {
                public void componentShown(java.awt.event.ComponentEvent evt) {
                    formComponentShown(evt);
                }
            });

            jScrollPane1.setMaximumSize(new java.awt.Dimension(400, 550));
            jScrollPane1.setPreferredSize(new java.awt.Dimension(400, 550));

            taEdit.setColumns(20);
            taEdit.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
            taEdit.setRows(5);
            jScrollPane1.setViewportView(taEdit);

            btnSave.setText("Save");
            btnSave.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnSaveActionPerformed(evt);
                }
            });

            jTextPane1.setEditable(false);

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(162, 162, 162)
                    .addComponent(btnSave)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE))
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 592, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnSave)
                    .addGap(29, 29, 29))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 630, Short.MAX_VALUE)
                        .addComponent(jTextPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if(jTextPane1.getText() != ""){
            saveToFile(jTextPane1.getText());
            btnSave.setEnabled(false);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        loadFromFile(path);
    }//GEN-LAST:event_formComponentShown

    public void setPath( String _path ){
        path = _path ;
    }
    
    private void loadFromFile(String _path){
        try{
            taEdit.setText(new String(Files.readAllBytes(Paths.get(_path))));
            jTextPane1.setText(_path);
            btnSave.setEnabled(false);
        }        
        catch (IOException e)
        {
            System.out.println("Exception ");

        }
    }
    
    private void saveToFile(String _path){
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(_path));
            out.write(taEdit.getText());     
            out.close();
        }
        catch (IOException e)
        {
            System.out.println("Exception ");

        }
    }
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextArea taEdit;
    // End of variables declaration//GEN-END:variables
    private String path;
}
