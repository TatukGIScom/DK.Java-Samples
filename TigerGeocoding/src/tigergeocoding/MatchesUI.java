package tigergeocoding;

import java.awt.Toolkit;
import tatukgis.rtl.TObjectList;
import tatukgis.rtl.TStrings;

public class MatchesUI extends javax.swing.JFrame {

    public MatchesUI() {
        initComponents();
    }
    
    public void ShowMatches(TObjectList<Object> _resolvedAddresses, 
            TObjectList<Object> _resolvedAddresses2){
        int i,j;
        TStrings strings;
        
        taMatches.setText("");
        
        if(_resolvedAddresses != null){
            for(i = 0; i < _resolvedAddresses.getCount(); i++){
                if(i != 0)
                    taMatches.append("------------------------\r\n");
                strings = (TStrings)_resolvedAddresses.get(i);
                for(j = 0; j < strings.getCount(); j++)
                    taMatches.append(strings.getNames(j) + " = " + 
                            strings.getValues(strings.getNames(j)) + "\r\n");
            }
        }
        if(_resolvedAddresses2 != null){
            for(i = 0; i < _resolvedAddresses.getCount(); i++){
                if(i == 0)
                    taMatches.append("========================\r\n");
                else
                    taMatches.append("------------------------\r\n");
                strings = (TStrings)_resolvedAddresses.get(i);
                for(j = 0; j < strings.getCount(); j++)
                    taMatches.append(strings.getNames(j) + " = " + 
                            strings.getValues(strings.getNames(j)) + "\r\n");
            }
        }        
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        taMatches = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Found matches");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));

        taMatches.setColumns(20);
        taMatches.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        taMatches.setRows(5);
        jScrollPane1.setViewportView(taMatches);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(MatchesUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MatchesUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MatchesUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MatchesUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MatchesUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea taMatches;
    // End of variables declaration//GEN-END:variables
}
