package fieldrules;

import java.awt.Toolkit;
import tatukgis.jdk.TGIS_FieldInfo;
import tatukgis.jdk.TGIS_FieldRule;
import tatukgis.jdk.TGIS_FieldRulesOperations;
import tatukgis.jdk.TGIS_FieldType;
import tatukgis.jdk.TGIS_FieldValueAlias;
import tatukgis.jdk.TGIS_FieldValueCheck;
import tatukgis.jdk.TGIS_FieldValueCheckFormula;
import tatukgis.jdk.TGIS_FieldValueCheckMode;
import tatukgis.jdk.TGIS_FieldValuesMode;
import tatukgis.jdk.TGIS_LayerVector;
import tatukgis.jdk.TGIS_Shape;
import tatukgis.jdk.TGIS_ShapeType;
import tatukgis.jdk.TGIS_Utils;

public class FieldRulesUI extends javax.swing.JFrame {

    public FieldRulesUI() {
        initComponents();
        initOwn();
    }

    private void initOwn(){
        TGIS_Shape shp;
        
        lv = new TGIS_LayerVector();
        
        lv.setName("test_rules");
        lv.Open();
        
        lv.AddField("name", TGIS_FieldType.String, 1, 0);
        
        shp = lv.CreateShape(TGIS_ShapeType.Point);
        shp.AddPart();
        shp.AddPoint(TGIS_Utils.GisPoint(20, 20));
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        attr = new tatukgis.jdk.TGIS_ControlAttributes();
        btnAddField = new javax.swing.JButton();
        btnAddAlias = new javax.swing.JButton();
        btnAddCheck = new javax.swing.JButton();
        btnAddList = new javax.swing.JButton();
        btnAddDefault = new javax.swing.JButton();
        btnAddValidate = new javax.swing.JButton();
        btnSaveRules = new javax.swing.JButton();
        btnReadrules = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FieldRules - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));

            btnAddField.setText("Add field");
            btnAddField.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnAddFieldActionPerformed(evt);
                }
            });

            btnAddAlias.setText("Add alias");
            btnAddAlias.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnAddAliasActionPerformed(evt);
                }
            });

            btnAddCheck.setText("Add check");
            btnAddCheck.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnAddCheckActionPerformed(evt);
                }
            });

            btnAddList.setText("Add list");
            btnAddList.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnAddListActionPerformed(evt);
                }
            });

            btnAddDefault.setText("Add default");
            btnAddDefault.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnAddDefaultActionPerformed(evt);
                }
            });

            btnAddValidate.setText("Add validate");
            btnAddValidate.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnAddValidateActionPerformed(evt);
                }
            });

            btnSaveRules.setText("Save rules");
            btnSaveRules.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnSaveRulesActionPerformed(evt);
                }
            });

            btnReadrules.setText("Read rules");
            btnReadrules.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnReadrulesActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(attr, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnAddDefault, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAddList, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAddCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAddAlias, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAddField, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSaveRules, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnReadrules, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAddValidate, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addContainerGap())
            );

            layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAddAlias, btnAddCheck, btnAddDefault, btnAddField, btnAddList, btnAddValidate});

            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(attr, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(26, 26, 26)
                            .addComponent(btnAddField)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnAddAlias)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnAddCheck)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnAddList)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnAddDefault)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnAddValidate)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 184, Short.MAX_VALUE)
                            .addComponent(btnSaveRules)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnReadrules)))
                    .addContainerGap())
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void btnAddFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddFieldActionPerformed
        TGIS_Shape shp;
        
        shp = lv.GetShape(1);
        shp.SetField("name", "Tom");
        attr.ShowShape(shp);
    }//GEN-LAST:event_btnAddFieldActionPerformed

    private void btnAddAliasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAliasActionPerformed
        TGIS_Shape shp;
        TGIS_FieldRule r;
        TGIS_FieldInfo fld;
        
        fld = lv.FieldInfo(0);
        r = new TGIS_FieldRule();
        r.getValueAliases().getAliases().Add(new TGIS_FieldValueAlias("Tommy", "Tom"));

        fld.setRules(r);
        
        shp = lv.GetShape(1);
        shp.SetField("name", "Tom");

        attr.ShowShape(shp);
    }//GEN-LAST:event_btnAddAliasActionPerformed

    private void btnAddCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCheckActionPerformed
        TGIS_FieldRule r;
        TGIS_FieldInfo fld;
        TGIS_Shape shp;

        fld = lv.FieldInfo(0);
        r = new TGIS_FieldRule();
        r.getValueChecks().getChecks().add(new TGIS_FieldValueCheck(TGIS_FieldValueCheckMode.AfterEdit,
                                                          TGIS_FieldValueCheckFormula.Required,
                                                          "",
                                                          "Cannot be null"));
        fld.setRules(r);

        shp = lv.GetShape(1);
        shp.SetField("name", "");

        attr.ShowShape(shp);

    }//GEN-LAST:event_btnAddCheckActionPerformed

    private void btnAddListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddListActionPerformed
        TGIS_FieldRule r;
        TGIS_FieldInfo fld;
        TGIS_Shape shp;

        fld = lv.FieldInfo(0);
        r = new TGIS_FieldRule();
        r.getValues().setMode(TGIS_FieldValuesMode.SelectList);
        r.getValues().getItems().add("Ala");
        r.getValues().getItems().add("Tom");
        r.getValues().getItems().add("Bobby");

        fld.setRules(r);

        shp = lv.GetShape(1);
        shp.SetField("name", "Tom");

        attr.ShowShape(shp);
    }//GEN-LAST:event_btnAddListActionPerformed

    private void btnAddDefaultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDefaultActionPerformed
        TGIS_FieldRule r;
        TGIS_FieldInfo fld;
        TGIS_Shape shp;

        fld = lv.FieldInfo(0);
        r = new TGIS_FieldRule();
        r.getValues().setDefaultValue("Diana");

        fld.setRules(r);

        shp = lv.CreateShape(TGIS_ShapeType.Point);
        shp.AddPart();
        shp.AddPoint(TGIS_Utils.GisPoint(30, 20));
        shp.SetFieldsDefaulRuleValue();

        attr.ShowShape(shp);
    }//GEN-LAST:event_btnAddDefaultActionPerformed

    private void btnAddValidateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddValidateActionPerformed
        TGIS_FieldRule r;
        TGIS_FieldInfo fld;
        TGIS_Shape shp;

        if (lv.FindField("email") == -1)
            lv.AddField("email", TGIS_FieldType.String, 1, 0);

        fld = lv.FieldInfo(1);
        r = new TGIS_FieldRule();
        r.getValueChecks().getChecks().add(new TGIS_FieldValueCheck(TGIS_FieldValueCheckMode.AfterEdit,
                                                          TGIS_FieldValueCheckFormula.Regex,
                                                          EMAIL_REGEX,
                                                          "Invalid email"));

        fld.setRules(r);

        shp = lv.GetShape(1);
        shp.SetField("email", "xyz@gmail.com");

        attr.ShowShape(shp);        
    }//GEN-LAST:event_btnAddValidateActionPerformed

    private void btnSaveRulesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveRulesActionPerformed
        TGIS_FieldRulesOperations.SaveFldx("myrules" + GIS_FLDX_EXT, lv);
    }//GEN-LAST:event_btnSaveRulesActionPerformed

    private void btnReadrulesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReadrulesActionPerformed
            TGIS_Shape shp;

            TGIS_FieldRulesOperations.ParseFldx("myrules" + GIS_FLDX_EXT, lv);

            shp = lv.GetShape(1);

            attr.ShowShape(shp);
    }//GEN-LAST:event_btnReadrulesActionPerformed

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
            java.util.logging.Logger.getLogger(FieldRulesUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FieldRulesUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FieldRulesUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FieldRulesUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FieldRulesUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ControlAttributes attr;
    private javax.swing.JButton btnAddAlias;
    private javax.swing.JButton btnAddCheck;
    private javax.swing.JButton btnAddDefault;
    private javax.swing.JButton btnAddField;
    private javax.swing.JButton btnAddList;
    private javax.swing.JButton btnAddValidate;
    private javax.swing.JButton btnReadrules;
    private javax.swing.JButton btnSaveRules;
    // End of variables declaration//GEN-END:variables
    private TGIS_LayerVector lv;
    private static final String EMAIL_REGEX = "[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+";
    private static final String GIS_FLDX_EXT = ".fldx";
}
