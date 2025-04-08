package onlineservices;

import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.*;
import tatukgis.rtl.*;
import tatukgis.jdk.*;

public class OnlineServices extends javax.swing.JFrame {
    
    final String LOCAL_LAYER_TILES = "tiles";
    
    ArrayList<Long> shpList = new ArrayList<>();
    
    private enum MapStyle {
        International,
        English,
        InternationalHillshade,
        EnglishHillshade        
    } 

    public OnlineServices() {
        initComponents();
        initOwn();
    }
    
    public TGIS_Extent growExtent(TGIS_Extent _ext, double _fac)
    {
        TGIS_Point ctr= TGIS_Utils.GisPoint(0.5d * (_ext.XMin + _ext.XMax),
                                            0.5d * (_ext.YMin + _ext.YMax));

        double xsiz = 0.5d * _fac * (_ext.XMax - _ext.XMin);
        double ysiz = 0.5d * _fac * (_ext.YMax - _ext.YMin);

        return TGIS_Utils.GisExtent(ctr.X - xsiz, ctr.Y - ysiz,
                                    ctr.X + xsiz, ctr.Y + ysiz);        
    }
    
    private TGIS_Extent resizeExtent(TGIS_Extent _ext, double _siz)
    {
        double xsiz = _ext.XMax - _ext.XMin;
        double ysiz = _ext.YMax - _ext.YMin;

        if ((xsiz > _siz) || (ysiz > _siz)) return _ext;

        TGIS_Point ctr = TGIS_Utils.GisPoint(0.5 * (_ext.XMin + _ext.XMax),
                                             0.5 * (_ext.YMin + _ext.YMax));

        return TGIS_Utils.GisExtent(ctr.X - 0.5 * _siz, ctr.Y - 0.5 * _siz,
                                    ctr.X + 0.5 * _siz, ctr.Y + 0.5 * _siz);
    }      
    
    private void resetLayers()
    {
        if (GIS.Get("fgeocoding") != null) GIS.Delete("fgeocoding");
        if (GIS.Get("route") != null) GIS.Delete("route");
        if (GIS.Get("isochrone") != null) GIS.Delete("isochrone");
    }    
   
    private void loadTiles(MapStyle _style)
    {
        final TGIS_LayerWebTiles lwt = new TGIS_LayerWebTiles();
        boolean b = GIS.Get(LOCAL_LAYER_TILES) != null;
        if (b) GIS.Delete(LOCAL_LAYER_TILES);
        
        new Thread(new Runnable() {
            @Override
            public void run() {           
                try {
                    SwingUtilities.invokeAndWait(new Runnable() {
                        @Override
                        public void run() {
                            String path = TGIS_Utils.GisSamplesDataDirDownload() + 
                                          "\\Samples\\WebServices\\";
                            switch (_style)
                            {
                                case International :
                                    path += "TatukGIS OpenStreetMap Tiles.ttkwp";
                                    break;
                                case English :
                                    path += "TatukGIS OpenStreetMap Tiles (English).ttkwp";
                                    break;
                                case InternationalHillshade :
                                    path += "TatukGIS OpenStreetMap Hillshade Tiles.ttkwp";
                                    break;
                                case EnglishHillshade :
                                    path += "TatukGIS OpenStreetMap Hillshade Tiles (English).ttkwp";
                                    break;
                            }
                            lwt.setPath(path);
                            lwt.Open();
                            lwt.setName(LOCAL_LAYER_TILES);
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    GIS.Add(lwt);
                                    lwt.Move(999);
                                    if (b) GIS.InvalidateWholeMap();
                                    else GIS.setVisibleExtent(lwt.getExtent());
                                }
                            });
                        }
                    }); 
                } catch (InterruptedException ex) {
                    Logger.getLogger(OnlineServices.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(OnlineServices.class.getName()).log(Level.SEVERE, null, ex);
                }           
            }
        }).start(); 
    }
    
    
    private String sign2dir(int _sign)
    {
        String res = "";
        switch (_sign)
        {
            case -99: res = "[unknown]"; break;
            case -98: res = "Make a u-turn"; break;
            case  -8: res = "Make a left u-turn"; break;
            case  -7: res = "Keep left"; break;
            case  -6: res = "Exit roundabout"; break;
            case  -3: res = "Sharp turn left"; break;
            case  -2: res = "Turn left"; break;
            case  -1: res = "Slight turn left"; break;
            case   0: res = "Continue"; break;
            case   1: res = "Slight turn right"; break;
            case   2: res = "Turn right"; break;
            case   3: res = "Sharp turn right"; break;
            case   4: res = "Finish"; break;
            case   5: res = "Reach the intermediate destination"; break;
            case   6: res = "Enter roundabout and take the "; break;
            case   7: res = "Keep right"; break;
            case   8: res = "Make a right u-turn"; break;
            case 101: res = "Start trip"; break;
            case 102: res = "Transfer"; break;
            case 103: res = "End trip"; break;
            default : res = "Ignore"; break;
        }
        return res;
    }

    private String exitNumber(String _s)
    {
        String res;        
        switch (_s.charAt(_s.length() - 1))
        {
           case '1': res = _s + "st"; break;
           case '2': res = _s + "nd"; break;
           case '3': res = _s + "rd"; break;
           default : res = _s + "th"; break;
        }
        return res;
    }    
    
    private void initOwn(){

        // move frame to the center of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        
        GIS.setMode(TGIS_ViewerMode.Zoom);
            
        DefaultTableModel mdl = (DefaultTableModel)tblRouting.getModel();
        mdl.addRow(new Object[]{"From", "Gdynia"});
        mdl.addRow(new Object[]{"Through", "Czestochowa"});
        mdl.addRow(new Object[]{"To", "Wroclaw"});  
        
        TableColumnModel tcm = tblRouting.getColumnModel();
        tcm.getColumn(0).setPreferredWidth(64) ;
        tcm.getColumn(1).setPreferredWidth(256) ;
        
        tcm = tblRoutingDir.getColumnModel();
        tcm.getColumn(0).setPreferredWidth(384) ;

        loadTiles(MapStyle.English);

        cmbbxMap.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent evt) {
                    switch (cmbbxMap.getSelectedIndex())
                    {
                        case 0 : loadTiles(MapStyle.International); break;
                        case 1 : loadTiles(MapStyle.English); break;
                        case 2 : loadTiles(MapStyle.InternationalHillshade); break;
                        case 3 : loadTiles(MapStyle.EnglishHillshade); break;
                    }
                }
            }
        );
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rgrpIsochroneProfile = new javax.swing.ButtonGroup();
        rgrpRoutingProfile = new javax.swing.ButtonGroup();
        GIS = new tatukgis.jdk.TGIS_ViewerWnd();
        GIS_Scale = new tatukgis.jdk.TGIS_ControlScale();
        pnlMap = new javax.swing.JPanel();
        cmbbxMap = new javax.swing.JComboBox<>();
        pnlGeocoding = new javax.swing.JPanel();
        lblGeocodingAddress = new javax.swing.JLabel();
        edtGeocodingAddress = new javax.swing.JTextField();
        lblGeocodingLimit = new javax.swing.JLabel();
        cmbbxGeocodingLimit = new javax.swing.JComboBox<>();
        btnGeocoding = new javax.swing.JButton();
        pnlRouting = new javax.swing.JPanel();
        lblRoutingProfile = new javax.swing.JLabel();
        rbtnRoutingProfileCar = new javax.swing.JRadioButton();
        rbtnRoutingProfileBike = new javax.swing.JRadioButton();
        rbtnRoutingProfileFoot = new javax.swing.JRadioButton();
        spnRouting = new javax.swing.JScrollPane();
        tblRouting = new javax.swing.JTable();
        btnRoutingAdd = new javax.swing.JButton();
        btnRoutingDelete = new javax.swing.JButton();
        btnRouting = new javax.swing.JButton();
        pnlIsochrone = new javax.swing.JPanel();
        lblIsochroneProfile = new javax.swing.JLabel();
        rbtnIsochroneProfileCar = new javax.swing.JRadioButton();
        rbtnIsochroneProfileBike = new javax.swing.JRadioButton();
        rbtnIsochroneProfileFoot = new javax.swing.JRadioButton();
        lblIsochroneTime = new javax.swing.JLabel();
        edtIsochroneTime = new javax.swing.JTextField();
        lblIsochroneBuckets = new javax.swing.JLabel();
        cmbbxIsochroneBuckets = new javax.swing.JComboBox<>();
        lblIsochroneAddress = new javax.swing.JLabel();
        edtIsochroneAddress = new javax.swing.JTextField();
        btnIsochrone = new javax.swing.JButton();
        pnlRoutingDir = new javax.swing.JPanel();
        lblRoutingDirDist = new javax.swing.JLabel();
        lblRoutingDirTime = new javax.swing.JLabel();
        lblRoutingDirInfo = new javax.swing.JLabel();
        spnRoutingDir = new javax.swing.JScrollPane();
        tblRoutingDir = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("OnlineServices - TatukGIS DK11 sample");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/icon.png")));
        setPreferredSize(new java.awt.Dimension(1113, 640));
        setSize(new java.awt.Dimension(800, 600));

        GIS_Scale.setGIS_Viewer(GIS);
        GIS.add(GIS_Scale);
        GIS_Scale.setBounds(10, 10, 185, 40);

        pnlMap.setBorder(javax.swing.BorderFactory.createTitledBorder("Map style"));

        cmbbxMap.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "International", "English", "International with hillshade", "English with hillshade" }));
        cmbbxMap.setSelectedIndex(1);
        cmbbxMap.setToolTipText("");

        javax.swing.GroupLayout pnlMapLayout = new javax.swing.GroupLayout(pnlMap);
        pnlMap.setLayout(pnlMapLayout);
        pnlMapLayout.setHorizontalGroup(
            pnlMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMapLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmbbxMap, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlMapLayout.setVerticalGroup(
            pnlMapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMapLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cmbbxMap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlGeocoding.setBorder(javax.swing.BorderFactory.createTitledBorder("Geocoding"));

        lblGeocodingAddress.setText("Address");

        edtGeocodingAddress.setText("Gdynia, Plac Kaszubski 8");

        lblGeocodingLimit.setText("Limit");

        cmbbxGeocodingLimit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "5", "10" }));
        cmbbxGeocodingLimit.setSelectedIndex(0);

        btnGeocoding.setText("Find");
        btnGeocoding.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGeocodingMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlGeocodingLayout = new javax.swing.GroupLayout(pnlGeocoding);
        pnlGeocoding.setLayout(pnlGeocodingLayout);
        pnlGeocodingLayout.setHorizontalGroup(
            pnlGeocodingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGeocodingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlGeocodingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlGeocodingLayout.createSequentialGroup()
                        .addComponent(lblGeocodingAddress)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(edtGeocodingAddress))
                    .addGroup(pnlGeocodingLayout.createSequentialGroup()
                        .addComponent(lblGeocodingLimit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbbxGeocodingLimit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGeocoding)))
                .addContainerGap())
        );
        pnlGeocodingLayout.setVerticalGroup(
            pnlGeocodingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGeocodingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlGeocodingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblGeocodingAddress)
                    .addComponent(edtGeocodingAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlGeocodingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbbxGeocodingLimit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGeocodingLimit)
                    .addComponent(btnGeocoding))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlRouting.setBorder(javax.swing.BorderFactory.createTitledBorder("Routing"));
        pnlRouting.setToolTipText("");
        pnlRouting.setPreferredSize(new java.awt.Dimension(256, 59));

        lblRoutingProfile.setText("Profile:");

        rgrpRoutingProfile.add(rbtnRoutingProfileCar);
        rbtnRoutingProfileCar.setSelected(true);
        rbtnRoutingProfileCar.setText("Car");

        rgrpRoutingProfile.add(rbtnRoutingProfileBike);
        rbtnRoutingProfileBike.setText("Bike");

        rgrpRoutingProfile.add(rbtnRoutingProfileFoot);
        rbtnRoutingProfileFoot.setText("Foot");

        tblRouting.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblRouting.setCellSelectionEnabled(true);
        tblRouting.setName(""); // NOI18N
        tblRouting.setRowHeight(24);
        tblRouting.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblRouting.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblRouting.setShowGrid(true);
        tblRouting.getTableHeader().setReorderingAllowed(false);
        spnRouting.setViewportView(tblRouting);
        tblRouting.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        btnRoutingAdd.setText("+");
        btnRoutingAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRoutingAddMouseClicked(evt);
            }
        });

        btnRoutingDelete.setText("-");
        btnRoutingDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRoutingDeleteMouseClicked(evt);
            }
        });

        btnRouting.setText("Find");
        btnRouting.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRoutingMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlRoutingLayout = new javax.swing.GroupLayout(pnlRouting);
        pnlRouting.setLayout(pnlRoutingLayout);
        pnlRoutingLayout.setHorizontalGroup(
            pnlRoutingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRoutingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlRoutingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spnRouting, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(pnlRoutingLayout.createSequentialGroup()
                        .addComponent(lblRoutingProfile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbtnRoutingProfileCar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbtnRoutingProfileBike)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbtnRoutingProfileFoot)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlRoutingLayout.createSequentialGroup()
                        .addComponent(btnRoutingAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRoutingDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRouting)))
                .addContainerGap())
        );
        pnlRoutingLayout.setVerticalGroup(
            pnlRoutingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRoutingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlRoutingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRoutingProfile)
                    .addComponent(rbtnRoutingProfileCar)
                    .addComponent(rbtnRoutingProfileBike)
                    .addComponent(rbtnRoutingProfileFoot))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spnRouting, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlRoutingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRoutingAdd)
                    .addComponent(btnRoutingDelete)
                    .addComponent(btnRouting))
                .addContainerGap())
        );

        pnlIsochrone.setBorder(javax.swing.BorderFactory.createTitledBorder("Isochrone"));

        lblIsochroneProfile.setText("Profile:");

        rgrpIsochroneProfile.add(rbtnIsochroneProfileCar);
        rbtnIsochroneProfileCar.setSelected(true);
        rbtnIsochroneProfileCar.setText("Car");

        rgrpIsochroneProfile.add(rbtnIsochroneProfileBike);
        rbtnIsochroneProfileBike.setText("Bike");

        rgrpIsochroneProfile.add(rbtnIsochroneProfileFoot);
        rbtnIsochroneProfileFoot.setText("Foot");

        lblIsochroneTime.setText("Time limit (seconds)");

        edtIsochroneTime.setText("600");

        lblIsochroneBuckets.setText("Number of buckets");

        cmbbxIsochroneBuckets.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "5", "10" }));
        cmbbxIsochroneBuckets.setSelectedIndex(1);

        lblIsochroneAddress.setText("Address");

        edtIsochroneAddress.setText("Gdynia, Plac Kaszubski 8");

        btnIsochrone.setText("Find");
        btnIsochrone.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnIsochroneMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlIsochroneLayout = new javax.swing.GroupLayout(pnlIsochrone);
        pnlIsochrone.setLayout(pnlIsochroneLayout);
        pnlIsochroneLayout.setHorizontalGroup(
            pnlIsochroneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlIsochroneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlIsochroneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlIsochroneLayout.createSequentialGroup()
                        .addGroup(pnlIsochroneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIsochroneTime)
                            .addComponent(lblIsochroneBuckets))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlIsochroneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbbxIsochroneBuckets, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(edtIsochroneTime)))
                    .addGroup(pnlIsochroneLayout.createSequentialGroup()
                        .addComponent(lblIsochroneProfile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbtnIsochroneProfileCar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbtnIsochroneProfileBike)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbtnIsochroneProfileFoot)
                        .addGap(0, 49, Short.MAX_VALUE))
                    .addGroup(pnlIsochroneLayout.createSequentialGroup()
                        .addComponent(lblIsochroneAddress)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(edtIsochroneAddress))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlIsochroneLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnIsochrone)))
                .addContainerGap())
        );
        pnlIsochroneLayout.setVerticalGroup(
            pnlIsochroneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlIsochroneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlIsochroneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIsochroneProfile)
                    .addComponent(rbtnIsochroneProfileCar)
                    .addComponent(rbtnIsochroneProfileBike)
                    .addComponent(rbtnIsochroneProfileFoot))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlIsochroneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edtIsochroneTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIsochroneTime))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlIsochroneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbbxIsochroneBuckets, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIsochroneBuckets))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlIsochroneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edtIsochroneAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIsochroneAddress))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnIsochrone)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlRoutingDir.setBorder(javax.swing.BorderFactory.createTitledBorder("Routing directions"));

        lblRoutingDirDist.setText("Total distance: ?");

        lblRoutingDirTime.setText("Totoal time: ?");

        lblRoutingDirInfo.setText("Double-click to zoom:");

        tblRoutingDir.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblRoutingDir.setCellSelectionEnabled(true);
        tblRoutingDir.setRowHeight(24);
        tblRoutingDir.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblRoutingDir.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblRoutingDir.setShowGrid(true);
        tblRoutingDir.getTableHeader().setResizingAllowed(false);
        tblRoutingDir.getTableHeader().setReorderingAllowed(false);
        tblRoutingDir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRoutingDirMouseClicked(evt);
            }
        });
        spnRoutingDir.setViewportView(tblRoutingDir);

        javax.swing.GroupLayout pnlRoutingDirLayout = new javax.swing.GroupLayout(pnlRoutingDir);
        pnlRoutingDir.setLayout(pnlRoutingDirLayout);
        pnlRoutingDirLayout.setHorizontalGroup(
            pnlRoutingDirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRoutingDirLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlRoutingDirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spnRoutingDir, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(pnlRoutingDirLayout.createSequentialGroup()
                        .addGroup(pnlRoutingDirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblRoutingDirDist)
                            .addComponent(lblRoutingDirTime)
                            .addComponent(lblRoutingDirInfo))
                        .addGap(0, 58, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlRoutingDirLayout.setVerticalGroup(
            pnlRoutingDirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRoutingDirLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblRoutingDirDist)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRoutingDirTime)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRoutingDirInfo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spnRoutingDir)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlIsochrone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlGeocoding, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlMap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlRouting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GIS, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlRoutingDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(GIS, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlRoutingDir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlMap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlGeocoding, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlRouting, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlIsochrone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGeocodingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGeocodingMouseClicked
        resetLayers();

        if (edtGeocodingAddress.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Address not specified.");
            return;
        }

        TGIS_OSMGeocoding ogeo = new TGIS_OSMGeocoding();

        ogeo.setLimit(Integer.parseInt((String)cmbbxGeocodingLimit.getSelectedItem()));
        TGIS_LayerVector lgeo = ogeo.Forward(edtGeocodingAddress.getText());
        if (lgeo.GetLastUid() > 0)
        {
            lblRoutingDirDist.setText("Total distance: ?");
            lblRoutingDirTime.setText("Total time: ?");
            DefaultTableModel mdl = (DefaultTableModel)tblRoutingDir.getModel();
            mdl.setRowCount(0);

            GIS.Add(lgeo);

            TGIS_Extent ext = resizeExtent(lgeo.getProjectedExtent(), 500.0d);
            ext = growExtent(ext, 1.2d);

            GIS.setVisibleExtent(ext);
        }
        else
            JOptionPane.showMessageDialog(this, "Address not found.");
    }//GEN-LAST:event_btnGeocodingMouseClicked

    private void btnRoutingAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRoutingAddMouseClicked
        DefaultTableModel mdl = (DefaultTableModel)tblRouting.getModel();
        int i = mdl.getRowCount() - 1;
        mdl.setValueAt("Through", i, 0);
        mdl.addRow(new Object[]{"To", ""});        
    }//GEN-LAST:event_btnRoutingAddMouseClicked

    private void btnRoutingDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRoutingDeleteMouseClicked
        DefaultTableModel mdl = (DefaultTableModel)tblRouting.getModel();
        int i = mdl.getRowCount()-1;
        if (i == 1) return ;
        
        mdl.removeRow(i);        
        i--;
        mdl.setValueAt("To", i, 0);   
    }//GEN-LAST:event_btnRoutingDeleteMouseClicked
    
    private void btnRoutingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRoutingMouseClicked
            resetLayers();

            for (int i = 0; i < tblRouting.getRowCount(); i++)
            {
                if (((String)tblRouting.getValueAt(i, 1)).isEmpty())
                {
                    JOptionPane.showMessageDialog(this, "Address not specified.");
                    return;
                }
            }

            TStringList names = new TStringList();
            TGIS_OSMRouting ortg = new TGIS_OSMRouting();

            if (rbtnRoutingProfileCar.isSelected())
                ortg.setProfile(TGIS_OSMRoutingProfile.Car);
            else
            if (rbtnRoutingProfileBike.isSelected())
                ortg.setProfile(TGIS_OSMRoutingProfile.Bike);
            else
            if (rbtnRoutingProfileFoot.isSelected())
                ortg.setProfile(TGIS_OSMRoutingProfile.Foot);

            for (int i = 0; i < tblRouting.getRowCount(); i++)
                names.Add((String)tblRouting.getValueAt(i, 1));

            TGIS_LayerVector lrtg = ortg.Route(names);

            if (lrtg != null)
            {
                boolean b = false;
                int dist = 0;
                int time = 0;
                for (Object obj: lrtg.Loop(lrtg.getExtent(), "( type = 'route' )"))
                {
                    TGIS_Shape shp = (TGIS_Shape)obj;
                    dist += Integer.parseInt((String)shp.GetField("distance"));
                    time += Integer.parseInt((String)shp.GetField("time"));
                    b = true;
                }

                GIS.Lock();
                GIS.Add(lrtg);
                TGIS_Extent ext = resizeExtent(lrtg.getProjectedExtent(), 500.0d);
                GIS.setVisibleExtent(growExtent(ext, 1.2d));

                if (b)
                {
                    long uid = 0;
                    String str;
                    int i;

                    if (dist < 1000)
                        str = String.valueOf(dist) + " m";
                    else
                        str = String.format("%.2f km", dist / 1000.0d);

                    lblRoutingDirDist.setText("Total distance: " + str);

                    int hrs = time / 3600;
                    int mns = (time / 60) - hrs * 60;
                    if (hrs == 0)
                        str = String.valueOf(mns) + " min";
                    else
                        str = String.valueOf(hrs) + " h " + String.valueOf(mns) + " min";
                    lblRoutingDirTime.setText("Total time: " + str);

                    boolean bfin = false;
                    
                    DefaultTableModel mdl = (DefaultTableModel)tblRoutingDir.getModel();
                    mdl.setRowCount(0);
                    shpList.clear();
                    

                    for (Object obj: lrtg.Loop(lrtg.getExtent(), "( type = 'route' )"))
                    {                       
                        TGIS_Shape shp = (TGIS_Shape)obj;
                        uid = shp.getUid();
                        i = Integer.parseInt((String)shp.GetField("sign"));
                        str = (String)shp.GetField("name");

                        String dir;
                        switch (i)
                        {
                            case -98:
                            case -8:
                            case 8:
                            case 5: dir = sign2dir(i); break;
                            case 6: dir = sign2dir(i) +
                                exitNumber((String)shp.GetField("exit")) +
                                " exit"; break;
                            default:
                                dir = sign2dir(i);
                                if (!str.isEmpty())
                                {
                                    if (i == 0)
                                        dir += " on " + str;
                                    else
                                        dir += " onto " + str;
                                }
                                break;
                        }

                        if (i == 5)
                        {
                            mdl.addRow(new Object[]{dir});
                            shpList.add(uid);                            
                            bfin = true;
                            continue;
                        }

                        dist = Integer.parseInt((String)shp.GetField("distance"));
                        if (dist < 1000)
                            dir += " (" + String.valueOf(dist) + " m, ";
                        else
                            dir += String.format(" (%.2f km, ", dist / 1000.0d);

                        time = Integer.parseInt((String)shp.GetField("time"));
                        hrs = time / 3600;
                        mns = (time / 60) - hrs * 60;
                        if (hrs == 0)
                        {
                            if (mns == 0)
                                dir += "<1 min)";
                            else
                                dir += String.valueOf(mns) + " min)";
                        }
                        else
                            dir += String.valueOf(hrs) + " h " + String.valueOf(mns) + " min)";

                        mdl.addRow(new Object[]{dir});
                        shpList.add(uid);  
                    }

                    if (bfin)
                    {
                        mdl.addRow(new Object[]{"Reach the final destination"});
                        shpList.add(uid);                          
                    }
                    else
                    {
                        mdl.addRow(new Object[]{"Reach the destination"});
                        shpList.add(uid);                            
                    }

                }
                else
                    JOptionPane.showMessageDialog(this, "Route not found.");
                
                GIS.Unlock();
            }
    }//GEN-LAST:event_btnRoutingMouseClicked

    private boolean tryConvertToInt32(String _str, int[] _val)
    {
        boolean res = true;
        try
        {
            _val[0] = Integer.parseInt(_str);
        }
        catch(Exception e)
        {
            res = false;
        }                
        return res;
    }
    
    private void btnIsochroneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIsochroneMouseClicked
        resetLayers();

        if (edtIsochroneAddress.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Address not specified.");
            return;
        }

        int[] time = new int[1];
        if (!tryConvertToInt32(edtIsochroneTime.getText(), time))
        {
            JOptionPane.showMessageDialog(this, "'" + edtIsochroneTime.getText() +
                                                "' is not a positive number.");
            return;
        }

        TGIS_OSMIsochrone oiso = new TGIS_OSMIsochrone();

        if (rbtnIsochroneProfileCar.isSelected())
            oiso.setProfile(TGIS_OSMRoutingProfile.Car);
        else
        if (rbtnIsochroneProfileBike.isSelected())
            oiso.setProfile(TGIS_OSMRoutingProfile.Bike);
        else
        if (rbtnIsochroneProfileFoot.isSelected())
            oiso.setProfile(TGIS_OSMRoutingProfile.Foot);

        oiso.setBuckets(Integer.parseInt((String)cmbbxIsochroneBuckets.getSelectedItem()));
        oiso.setTimeLimit(time[0]);
        TGIS_LayerVector liso = oiso.Isochrone(edtIsochroneAddress.getText());

        if (liso.GetLastUid() > 0)
        {
            lblRoutingDirDist.setText("Total distance: ?");
            lblRoutingDirTime.setText("Total time: ?");
            DefaultTableModel mdl = (DefaultTableModel)tblRoutingDir.getModel();
            mdl.setRowCount(0);

            GIS.Add(liso);

            TGIS_Extent ext = resizeExtent(liso.getProjectedExtent(), 500.0d);
            ext = growExtent(ext, 1.2d);

            GIS.setVisibleExtent(ext);            
        }
        else
            JOptionPane.showMessageDialog(this, "Address not found.");
    }//GEN-LAST:event_btnIsochroneMouseClicked

    private void tblRoutingDirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRoutingDirMouseClicked
        if (!(evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1))
            return;

        TGIS_LayerVector lrtg = (TGIS_LayerVector)GIS.Get("route");

        if (lrtg == null) return;
        
        TGIS_Shape shp = lrtg.GetShape(shpList.get(tblRoutingDir.getSelectedRow()));
        GIS.setVisibleExtent(resizeExtent(shp.getProjectedExtent(), 500.0d));      
    }//GEN-LAST:event_tblRoutingDirMouseClicked

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
            java.util.logging.Logger.getLogger(OnlineServices.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OnlineServices.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OnlineServices.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OnlineServices.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OnlineServices().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private tatukgis.jdk.TGIS_ViewerWnd GIS;
    private tatukgis.jdk.TGIS_ControlScale GIS_Scale;
    private javax.swing.JButton btnGeocoding;
    private javax.swing.JButton btnIsochrone;
    private javax.swing.JButton btnRouting;
    private javax.swing.JButton btnRoutingAdd;
    private javax.swing.JButton btnRoutingDelete;
    private javax.swing.JComboBox<String> cmbbxGeocodingLimit;
    private javax.swing.JComboBox<String> cmbbxIsochroneBuckets;
    private javax.swing.JComboBox<String> cmbbxMap;
    private javax.swing.JTextField edtGeocodingAddress;
    private javax.swing.JTextField edtIsochroneAddress;
    private javax.swing.JTextField edtIsochroneTime;
    private javax.swing.JLabel lblGeocodingAddress;
    private javax.swing.JLabel lblGeocodingLimit;
    private javax.swing.JLabel lblIsochroneAddress;
    private javax.swing.JLabel lblIsochroneBuckets;
    private javax.swing.JLabel lblIsochroneProfile;
    private javax.swing.JLabel lblIsochroneTime;
    private javax.swing.JLabel lblRoutingDirDist;
    private javax.swing.JLabel lblRoutingDirInfo;
    private javax.swing.JLabel lblRoutingDirTime;
    private javax.swing.JLabel lblRoutingProfile;
    private javax.swing.JPanel pnlGeocoding;
    private javax.swing.JPanel pnlIsochrone;
    private javax.swing.JPanel pnlMap;
    private javax.swing.JPanel pnlRouting;
    private javax.swing.JPanel pnlRoutingDir;
    private javax.swing.JRadioButton rbtnIsochroneProfileBike;
    private javax.swing.JRadioButton rbtnIsochroneProfileCar;
    private javax.swing.JRadioButton rbtnIsochroneProfileFoot;
    private javax.swing.JRadioButton rbtnRoutingProfileBike;
    private javax.swing.JRadioButton rbtnRoutingProfileCar;
    private javax.swing.JRadioButton rbtnRoutingProfileFoot;
    private javax.swing.ButtonGroup rgrpIsochroneProfile;
    private javax.swing.ButtonGroup rgrpRoutingProfile;
    private javax.swing.JScrollPane spnRouting;
    private javax.swing.JScrollPane spnRoutingDir;
    private javax.swing.JTable tblRouting;
    private javax.swing.JTable tblRoutingDir;
    // End of variables declaration//GEN-END:variables

}
