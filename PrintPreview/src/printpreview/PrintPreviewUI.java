package printpreview;

import RemObjects.Elements.System.VarParameter;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.print.PrinterJob;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import tatukgis.jdk.TGIS_Color;
import tatukgis.jdk.TGIS_FontStyle;
import tatukgis.jdk.TGIS_FrameworkUtils;
import tatukgis.jdk.TGIS_PrintManager;
import tatukgis.jdk.TGIS_PrintPageEvent;
import tatukgis.jdk.TGIS_PrintPageEventArgs;
import tatukgis.jdk.TGIS_Printer;
import tatukgis.jdk.TGIS_PrinterPreview;
import tatukgis.jdk.TGIS_Utils;
import tatukgis.rtl.TRect;

public class PrintPreviewUI extends javax.swing.JFrame {

    public PrintPreviewUI() {
        initComponents();
        initOwn() ;
    }

    private void initOwn(){
        GIS.Open(TGIS_Utils.GisSamplesDataDirDownload() + "/World/Countries/Poland/DCW/poland.ttkproject");
        
        job = PrinterJob.getPrinterJob() ;
        
        printManager = new TGIS_PrintManager();
        printManager.setTitle(tfTitle.getText()) ;
        printManager.setSubtitle(tfSubtitle.getText()) ;
        printManager.setFooter("Footer");

        printManager.getTitleFont().Name = "Arial";
        printManager.getTitleFont().Size = 18;
        printManager.getTitleFont().Style = to_gis_fontstyle(Font.BOLD);
        printManager.getTitleFont().Color = to_gis_color(SystemColor.windowText);

        printManager.getSubtitleFont().Name = "Arial";
        printManager.getSubtitleFont().Size = 14;
        printManager.getSubtitleFont().Style = to_gis_fontstyle(Font.BOLD);
        printManager.getSubtitleFont().Color = to_gis_color(SystemColor.windowText);

        printManager.getFooterFont().Name = "Arial";
        printManager.getFooterFont().Size = 10;
        printManager.getFooterFont().Style = to_gis_fontstyle(Font.PLAIN);
        printManager.getFooterFont().Color = to_gis_color(SystemColor.windowText);

        printManager.setPrintPageEvent(new TGIS_PrintPageEvent() {
            @Override
            public void Invoke(Object o, TGIS_PrintPageEventArgs tgsp) {
                GIS_PrintPage(o, tgsp) ;
            }
        });
        GIS_ControlPrintPreview1.Preview(1, job, printManager, aset);     
        
        tfTitle.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                printManager.setTitle(tfTitle.getText()) ;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                printManager.setTitle(tfTitle.getText()) ;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                printManager.setTitle(tfTitle.getText()) ;
            }
        }); 
        
        tfSubtitle.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                printManager.setSubtitle(tfSubtitle.getText()) ;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                printManager.setSubtitle(tfSubtitle.getText()) ;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                printManager.setSubtitle(tfSubtitle.getText()) ;
            }
        });
    }
    
    private Color to_native_color( TGIS_Color _cl ){
        return TGIS_FrameworkUtils.ToPlatformColor(_cl);
    }
    
    private TGIS_Color to_gis_color(Color _cl){
        return TGIS_FrameworkUtils.FromPlatformColor(_cl);
    }
//    Currently theres no option to change fonts    
    private int to_native_fontstyle(int _st)
    {
        int style = Font.PLAIN;
        if (_st == TGIS_FontStyle.Bold)
            style += Font.BOLD;
        if (_st == TGIS_FontStyle.Italic)
            style |= Font.ITALIC;
//        TO BE REVIEWED
//        if ((_st & TGIS_FontStyle.StrikeOut) != 0)
//            style = style | FontStyle.Strikeout;
//        if ((_st & TGIS_FontStyle.Underline) != 0)
//            style = style | FontStyle.Underline;
        return style;
    }

    private int to_gis_fontstyle(int _st)
    {
        int style = TGIS_FontStyle.Bold;
        if ((_st & Font.BOLD) != 0)
            style |= TGIS_FontStyle.Bold;
        if ((_st & Font.ITALIC) != 0)
            style |= TGIS_FontStyle.Italic;
//        TO BE REVIEWED
//        if ((_st & FontStyle.Strikeout) != 0)
//            style = style | TGIS_FontStyle.StrikeOut;
//        if ((_st & FontStyle.Underline) != 0)
//            style = style | TGIS_FontStyle.Underline;
        return style;
    }    

    private int inch(double _value, int _dpi)
    {
        return (int)Math.round(_value * _dpi);
    }

    private int xy(double _value, double _factor)
    {
        return (int)Math.round(_value * _factor);
    }    
    
    private void GIS_PrintPage(Object _sender, TGIS_PrintPageEventArgs _e){
        // IMPORTANT : if you are using java graphics to draw anything you need
        //             to calculate units by yourself, on the other hand if you 
        //             use our API to do so then you do not need to.
        VarParameter<Double> scale;
        Rectangle r;
        Rectangle mr;
        int x1, y1, x2, y2;
        int h;
        int sz;
        Graphics canvas = _e.getCanvas();
        TGIS_PrintManager pm;
        TGIS_Printer pr;
        int dpi;
        double fx, fy;
        TRect rect ;
        Font ft;

        scale = new VarParameter<>();
        scale.Value = 0.0 ;
        pm = _e.getPrintManager();
        pr = pm.getPrinter();
        if( pr instanceof TGIS_PrinterPreview )
            dpi = 72;
        else
            dpi = pr.getPpiX() ;
        fx = (double)pr.getPrintArea().getWidth() / pr.getPageWidth();
        fy = (double)pr.getPrintArea().getHeight() / pr.getPageHeight();

        // left panel
        x1 = xy(inch(0.1, dpi), fx);
        y1 = xy(inch(0.5, dpi), fy);
        x2 = pr.getPrintArea().getWidth();
        y2 = pr.getPrintArea().getHeight();
        
        // left panel: grey rectangle
        x2 = x2 - xy(pr.TwipsToX(2 * 1440), fx) - xy(inch(0.2, dpi), fx);
        r = new Rectangle(x1, y1, x2 - x1, y2 - y1);
        canvas.setColor(Color.GRAY);
        canvas.fillRect(r.x * 72 / dpi, r.y * 72 / dpi, r.width * 72 / dpi, r.height * 72 / dpi);
        canvas.drawRect(r.x * 72 / dpi, r.y * 72 / dpi, r.width * 72 / dpi, r.height * 72 / dpi);

        // left panel: white rectangle
        r = new Rectangle(r.x - xy(inch(0.1, dpi), fx),
                           r.y - xy(inch(0.1, dpi), fy),
                           r.width, r.height
                         );
        canvas.setColor(Color.white);
        canvas.fillRect(r.x * 72 / dpi, r.y * 72 / dpi, r.width * 72 / dpi, r.height * 72 / dpi);
        Rectangle fr = r;

        // left panel: map
        x1 = r.x + xy(inch(0.1, dpi), fx);
        y1 = r.y + xy(inch(0.1, dpi), fy);
        x2 = (r.width + r.x) - xy(inch(0.1, dpi), fx);
        y2 = (r.height + r.y) - xy(inch(0.1, dpi), fy);
        r = new Rectangle(x1, y1, x2 - x1, y2 - y1);
        
        rect = new TRect();
        rect.Left = r.x ;
        rect.Top = r.y ;
        rect.Right = (r.width + r.x) ;
        rect.Bottom = (r.height + r.y) ;
        
        // 'scale' returned by the function is the real map scale used during printing.
        // It should be passed to the following DrawControl methods.
        // If legend or scale controls have to be printed before map (for some reason)
        // use the following frame:
        //    scale := 0 ;
        //    pm.GetDrawingParams( GIS, GIS.Extent, r, scale ) ; ...
        //    pm.DrawControl( GIS_ControlLegend1, r1, scale ) ;  ...
        //    pm.DrawMap( GIS, GIS.Extent, r, scale ) ;
        pm.DrawMap( GIS, GIS.getExtent(), rect, scale);
        mr = r ;

        // left panel: black frame
        canvas.setColor(Color.BLACK);
        canvas.drawRect(fr.x * 72 / dpi, fr.y * 72 / dpi, fr.width * 72 / dpi, fr.height * 72 / dpi);
        
        // right panel
        x1 = 0;
        y1 = xy(inch(0.5, dpi), fy);
        x2 = pr.getPrintArea().getWidth();
        y2 = pr.getPrintArea().getHeight();
        
        // right panel: grey rectangle
        x1 = x2 - xy(pr.TwipsToX(2 * 1440), fx);
        r = new Rectangle(x1, y1, x2 - x1, y2 - y1);
        canvas.setColor(Color.gray);
        canvas.fillRect(r.x * 72 / dpi, r.y * 72 / dpi, r.width * 72 / dpi, r.height * 72 / dpi);
        canvas.drawRect(r.x * 72 / dpi, r.y * 72 / dpi, r.width * 72 / dpi, r.height * 72 / dpi);

        // right panel: white rectangle
        r = new Rectangle(r.x - xy(inch(0.1, dpi), fx),
                           r.y - xy(inch(0.1, dpi), fy),
                           r.width, r.height
                         );
        canvas.setColor(Color.white);
        canvas.fillRect(r.x * 72 / dpi, r.y * 72 / dpi, r.width * 72 / dpi, r.height * 72 / dpi);
        
        fr = r;

        // right panel: legend
        
        x1 = r.x + xy(inch(0.1, dpi), fx);
        y1 = r.y + xy(inch(0.1, dpi), fy);
        x2 = (r.width + r.x) - xy(inch(0.1, dpi), fx);
        y2 = (r.height + r.y)- xy(inch(0.1, dpi), fy);
        r = new Rectangle(x1 + 1 * 72 / dpi , y1 + 1 * 72 / dpi, x2 - x1 - 2* 72 / dpi , y2 - y1 - 2* 72 / dpi );
        rect.Left = r.x ;
        rect.Top = r.y ;
        rect.Right = (r.width + r.x) ;
        rect.Bottom = (r.height + r.y - 1) ;
        pm.DrawControl(GIS_ControlLegend1, rect, scale.Value);

        // right panel: black frame   
        canvas.setColor(Color.BLACK);
        canvas.drawRect(fr.x * 72 / dpi, fr.y * 72 / dpi, fr.width * 72 / dpi, fr.height * 72 / dpi);
        
        // page number
        ft = new Font("Arial", Font.PLAIN, 12 );
        canvas.setColor(Color.BLACK);
        canvas.setFont(ft) ;
        canvas.drawString("Page " + pr.getPageNumber(),
                           xy(pr.TwipsToX(720), fx) * 72 / dpi,
                           xy(pr.TwipsToY(720), fy) * 72 / dpi + 
                           canvas.getFontMetrics().getHeight() - 
                           canvas.getFontMetrics().getDescent()
                         );

        r = mr ;
        // title
        ft = new Font(pm.getTitleFont().Name, 
                        to_native_fontstyle(pm.getTitleFont().Style), 
                        pm.getTitleFont().Size
                     );
        canvas.setColor(to_native_color(pm.getTitleFont().Color));
        canvas.setFont(ft);
        sz = canvas.getFontMetrics().stringWidth(pm.getTitle());
        canvas.drawString(pm.getTitle(),
                           (int)(Math.round((double)(((r.width* 72 / dpi + r.x* 72 / dpi)) - r.x* 72 / dpi) / 2) -
                                 Math.round(sz / 2)) ,
                           xy(pr.TwipsToY(720), fy) * 72 / dpi + canvas.getFontMetrics().getHeight() -  canvas.getFontMetrics().getDescent()
                         );

        // subtitle
        h = (Math.round(canvas.getFontMetrics().getHeight()) + canvas.getFontMetrics().getDescent() );
        ft = new Font(  pm.getSubtitleFont().Name, 
                        to_native_fontstyle(pm.getSubtitleFont().Style), 
                        pm.getSubtitleFont().Size
        );
        canvas.setColor(to_native_color(pm.getSubtitleFont().Color));
        canvas.setFont(ft);
        canvas.drawString(pm.getSubtitle(),
                           (int)(Math.round((double)(((r.width* 72 / dpi + r.x*  72 / dpi)) - r.x* 72 / dpi) / 2) -
                                 Math.round(canvas.getFontMetrics().stringWidth(pm.getSubtitle()) / 2)) ,
                           (xy(pr.TwipsToY(720 + 200), fy) ) * 72 / dpi + h
                         );

        _e.setLastPage(true);                
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GIS_ControlPrintPreviewSimple1 = new tatukgis.jdk.TGIS_ControlPrintPreviewSimple();
        btnSimplePreview = new javax.swing.JButton();
        btnPreview = new javax.swing.JButton();
        GIS_ControlLegend1 = new tatukgis.jdk.TGIS_ControlLegend();
        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        jLabel1 = new javax.swing.JLabel();
        tfTitle = new javax.swing.JTextField();
        btnTitleFont = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        tfSubtitle = new javax.swing.JTextField();
        btnSubtitleFont = new javax.swing.JButton();
        chkStandard = new javax.swing.JCheckBox();
        GIS_ControlPrintPreview1 = new tatukgis.jdk.TGIS_ControlPrintPreview();

        GIS_ControlPrintPreviewSimple1.setGIS_Viewer(GIS);

        setTitle("PrintPreview - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnSimplePreview.setText("TGIS_ControlPrintPreviewSimple");
        btnSimplePreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimplePreviewActionPerformed(evt);
            }
        });

        btnPreview.setText("TGIS_ControlPrintPreview");
        btnPreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviewActionPerformed(evt);
            }
        });

        GIS_ControlLegend1.setGIS_Viewer(GIS);

        jLabel1.setText("Print title");

        tfTitle.setText("Title");

        btnTitleFont.setText("Font");
        btnTitleFont.setEnabled(false);

        jLabel2.setText("Print Subtitle");

        tfSubtitle.setText("Subtitle");

        btnSubtitleFont.setText("Font");
        btnSubtitleFont.setEnabled(false);

        chkStandard.setText("Standard print");
        chkStandard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkStandardActionPerformed(evt);
            }
        });

        GIS_ControlPrintPreview1.setGIS_Viewer(GIS);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(GIS_ControlPrintPreview1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(GIS_ControlLegend1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSimplePreview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnPreview)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnTitleFont)
                                    .addComponent(jLabel2)
                                    .addComponent(tfSubtitle, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnSubtitleFont)
                                    .addComponent(chkStandard))
                                .addGap(30, 30, 30))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimplePreview)
                    .addComponent(btnPreview))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(GIS_ControlLegend1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTitleFont)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfSubtitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSubtitleFont)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chkStandard)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GIS_ControlPrintPreview1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void chkStandardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkStandardActionPerformed
        if(!chkStandard.isSelected())
            printManager.setPrintPageEvent(new TGIS_PrintPageEvent() {
            @Override
            public void Invoke(Object o, TGIS_PrintPageEventArgs tgsp) {
                GIS_PrintPage(o, tgsp) ;
            }
        });
        else
            printManager.setPrintPageEvent(null);
    }//GEN-LAST:event_chkStandardActionPerformed

    private void btnSimplePreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimplePreviewActionPerformed
        GIS_ControlPrintPreviewSimple1.Preview(printManager) ;
    }//GEN-LAST:event_btnSimplePreviewActionPerformed

    private void btnPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviewActionPerformed
        GIS_ControlPrintPreview1.Preview(1, job, printManager, aset) ;
    }//GEN-LAST:event_btnPreviewActionPerformed

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PrintPreviewUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrintPreviewUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrintPreviewUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrintPreviewUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrintPreviewUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private tatukgis.jdk.TGIS_ControlLegend GIS_ControlLegend1;
    private tatukgis.jdk.TGIS_ControlPrintPreview GIS_ControlPrintPreview1;
    private tatukgis.jdk.TGIS_ControlPrintPreviewSimple GIS_ControlPrintPreviewSimple1;
    private javax.swing.JButton btnPreview;
    private javax.swing.JButton btnSimplePreview;
    private javax.swing.JButton btnSubtitleFont;
    private javax.swing.JButton btnTitleFont;
    private javax.swing.JCheckBox chkStandard;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField tfSubtitle;
    private javax.swing.JTextField tfTitle;
    // End of variables declaration//GEN-END:variables
    private TGIS_PrintManager printManager;
    private PrintRequestAttributeSet aset ;
    private PrinterJob job ;
}
