package languages;

import java.awt.Toolkit;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_FieldType;
import tatukgis.jdk.TGIS_LabelAlignment;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_Point;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_ShapeType;
import tatukgis.jdk.TGIS_Utils;

public class LanguagesUI extends javax.swing.JFrame {

    public LanguagesUI() {
        initComponents();
        initOwn();   
    }

    private void initOwn(){
        TGIS_LayerVector ll;
        TGIS_Shape shp;
        
        
        ll = new TGIS_LayerVector();
        ll.setName("points");
        ll.getParams().getLabels().setFontName("Arial Unicode MS");
        ll.getParams().getLabels().setAllocator(false);
        
        GIS.Add(ll);
        
        ll.setExtent(TGIS_Utils.GisExtent(-180, -90, 180, 90));
        
        shp = ll.CreateShape(TGIS_ShapeType.Point);
        shp.AddPart();
        shp.AddPoint(new TGIS_Point(-45, -45));
        
        ll = new TGIS_LayerVector();
        ll.setName("lines");
        ll.AddField("name", TGIS_FieldType.String,256,0);
        ll.getParams().getLabels().setAlignment(TGIS_LabelAlignment.Center);
        ll.getParams().getLabels().setColor(TGIS_Color.getBlack());
        ll.getParams().getLabels().getFont().setSize(12);
        ll.getParams().getLabels().getFont().setColor(TGIS_Color.getBlack());
        ll.getParams().getLabels().getFont().getStyle();
        ll.getParams().getLabels().getFont().getName();
        ll.getParams().getLabels().setFontName("Arial Unicode MS");
        ll.getParams().getLabels().setAllocator(false);
        
        GIS.Add(ll);
        
        ll.setExtent(TGIS_Utils.GisExtent(-180, -90, 180, 90));
        
        shp = ll.CreateShape(TGIS_ShapeType.Arc);
        shp.AddPart();
        shp.AddPoint(new TGIS_Point(-90, 90));
        shp.AddPoint(new TGIS_Point(180, -90));
        
        GIS.FullExtent();
        cbLanguage.setSelectedItem("English");
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        cbLanguage = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Languages - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setSize(new java.awt.Dimension(800, 600));

        cbLanguage.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "English", "Chinese", "japanese", "hebrew", "greek", "arabic" }));
        cbLanguage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbLanguageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(cbLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 700, Short.MAX_VALUE))
            .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(cbLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbLanguageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbLanguageActionPerformed
        TGIS_LayerVector ll;
        String text;
        switch(cbLanguage.getSelectedIndex()){
            case 1: //Chinese
                text = TXT_CHINESE;
                break;
            case 2: //Japanese
                text = TXT_JAPANESE;
                break;
            case 3: //hebrew
                text = TXT_HEBREW;
                break;
            case 4: //greek
                text = TXT_GREEK;
                break;
            case 5: //Arabic
                text = TXT_ARABIC;
                break;
            default: //engilsh
                text = TXT_ENGLISH;
                break;
        }
        ll = (TGIS_LayerVector)GIS.Get("points");
        ll.getParams().getLabels().setValue(text + "1");
        
        ll = (TGIS_LayerVector)GIS.Get("lines");
        ll.getParams().getLabels().setValue(text + "2");

        GIS.InvalidateWholeMap();
    }//GEN-LAST:event_cbLanguageActionPerformed

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
            java.util.logging.Logger.getLogger(LanguagesUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LanguagesUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LanguagesUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LanguagesUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LanguagesUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private javax.swing.JComboBox cbLanguage;
    // End of variables declaration//GEN-END:variables

    private String TXT_ENGLISH  = "Welcome" ;
    private String TXT_CHINESE  = "欢迎" ;
    private String TXT_JAPANESE = "ようこそ" ;
    private String TXT_HEBREW   = "ברוך הבא" ;
    private String TXT_GREEK    = "Καλώς ήλθατε" ;
    private String TXT_ARABIC   = "أهلا بك" ;
}
