/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appthoitiet;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class FromGiaoDien extends javax.swing.JFrame {

    /**
     * Creates new form FromGiaoDien
     */
    /// KHai bao, khong khai bao thi truyen tu ngoai vao cung duoc
    static String t="",time = "";
    static float temp = 0,humi = 0;
    static boolean stt,auto,t1_h0;
    static float ont,offt,onh,offh;
    static boolean focus_setting=false;
    static DefaultListModel md1=new DefaultListModel<>() ;
    private static int gethistory(){
      try {
            // TODO add your handling code here:
            URL url_key;
            url_key = new URL("http://doandieukhien.000webhostapp.com/history.php");
            BufferedReader br = new BufferedReader(new InputStreamReader(url_key.openStream()));
            String line,temp;
            String sb = new String();
            while ((line = br.readLine()) != null) {
                sb=sb+line;                //sb=sb+System.lineSeparator();
            }
            System.out.println(sb);
            while (sb.length() >= 1) {
                temp="";
                temp = sb.substring(0, sb.indexOf("#"));
                md1.addElement(temp);
                sb = sb.substring(sb.indexOf("#") + 1, sb.length());
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(FromGiaoDien.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FromGiaoDien.class.getName()).log(Level.SEVERE, null, ex);
        }  
        return 0;
    
    }
    private static int getnow(){
        //http://doandieukhien.000webhostapp.com/now.php
        URL url_key;          
        try {
            url_key = new URL("http://doandieukhien.000webhostapp.com/now.php");
            BufferedReader br = new BufferedReader(new InputStreamReader(url_key.openStream()));
            String line;
            String sb = new String();
            sb="";
            while ((line = br.readLine()) != null) {
                sb=sb+line;                //sb=sb+System.lineSeparator();
            }
            System.out.println(sb);
            //////
            byte count=1;
            while(sb.length()>=5) {
                //temp
                if(sb.contains("<br>")){
                t=sb.substring(0,sb.indexOf("<br>"));
                sb=sb.substring(sb.indexOf("<br>")+4,sb.length());
                }else{
                    t=sb.substring(0,sb.length());
                    sb="";
                }
                    
                switch(count){
                    case 1:
                        temp = Float.parseFloat(t);
                        break;
                    case 2:
                        humi = Float.parseFloat(t);
                        break;
                    case 3:
                        stt = Integer.parseInt(t)> 0 ;
                        break;
                    case 4:
                        auto = Integer.parseInt(t)> 0 ;
                        break;
                    case 5:
                        t1_h0 = Integer.parseInt(t)> 0 ;
                        break;
                    case 6:
                        ont = Float.parseFloat(t);
                        break;
                    case 7:
                        offt = Float.parseFloat(t);
                        break;
                    case 8:
                        onh = Float.parseFloat(t);
                        break;
                    case 9:
                        offh = Float.parseFloat(t);
                        break;
                    case 10:
                        if(!"\0".equals(t)){
                            time="";
                            time+=t;
                        }
                        break;    
                    default: return 0;
                }
                count++;
                System.out.println(t);
                t="";
            }
            System.out.println(time);
        } catch (MalformedURLException ex) {
            Logger.getLogger(FromGiaoDien.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e1) {
            Logger.getLogger(FromGiaoDien.class.getName()).log(Level.SEVERE, null, e1);
        }    
        return 1;
    }
    CardLayout card = new CardLayout();
    User FGDU = new User();
    public FromGiaoDien(User thisUser) {
        initComponents();
        this.setLocationRelativeTo(null);
        card = (CardLayout) (jPanel_GiaoDienChinh.getLayout());
        card.show(jPanel_GiaoDienChinh, "card2");
        FGDU = thisUser;
        if (FGDU.Guest != true) {
        jLabel_NAME.setText(FGDU.NAME);
        jLabel_NAME1.setText(FGDU.NAME);
        }
        /////
        TimerTask myTask = new TimerTask() {
            @Override
            public void run() {
                /////Update lại trong đây, cái này tạo random làm ví dụ thôi !
                //updateto hear
                getnow();
                md1.clear();
                gethistory();
                jList2.setModel(md1);
                lb_nhietdo.setText(temp+" °C     ");
                lb_doam.setText(humi+" %RH");
                lb_updatetime.setText("Updated at: "+time);
                if(focus_setting==false){
                    jSlider_TatCuaNhietDo.setValue((int)(10.0*offt));
                    jSlider_BatCuaNhietDo.setValue((int)(10.0*ont));
                    jSlider_TatCuaDoAm.setValue((int)(10.0*offh));
                    jSlider_BatCuaDoAm.setValue((int)(10.0*onh));
                    jTextField_BatNhietDo.setText(String.valueOf(ont));
                    jTextField_TatNhietDo.setText(String.valueOf(offt));
                    jTextField_BatDoAm.setText(String.valueOf(onh));
                    jTextField_TatDoAm.setText(String.valueOf(offh));
                    System.out.println("Notthing changed with settings");
                }
                System.out.println("Auto ="+auto);
                    if (auto == true)
                        jButton_NutBatTatTuDong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appthoitiet/mode.png")));
                    else 
                        jButton_NutBatTatTuDong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appthoitiet/mode1.png")));    
                    if (t1_h0 == true) {
                        jRadioButton_NhietDo.setSelected(true);
                        jRadioButton_DoAm.setSelected(false);
                    } else {
                        jRadioButton_DoAm.setSelected(true);
                        jRadioButton_NhietDo.setSelected(false);
                    };
                    if (stt == true) 
                        jButton_NutBatTatThietBi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appthoitiet/on.png")));
                    else 
                        jButton_NutBatTatThietBi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appthoitiet/off.png")));
                }
            };
            Timer timer = new Timer();
            timer.schedule(myTask,2000, 9000);
        /////
        if (FGDU.Guest == true) {
            jButton5_admin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appthoitiet/guest.png")));
            jButton2_Fan.setEnabled(false);
            //jButton2_Fan.setVisible(false);
            jButton1_settings.setEnabled(false);
            //jButton1_settings.setVisible(false);
        }
        if (FGDU.Ad == false) {
            jButton5_admin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appthoitiet/standar.png")));
            //         Them ngdung
            jButton_ThemNguoiDung.setEnabled(false);
            jButton_ThemNguoiDung.setVisible(false);
//          field code
            jPasswordField_ĐoiMaDangNhap.setEnabled(false);
            jPasswordField_ĐoiMaDangNhap.setVisible(false);
//            button save code
            jButton_NutSaveDoiMaDangNhap.setEnabled(false);
            jButton_NutSaveDoiMaDangNhap.setVisible(false);
            jLabel3.setVisible(false);
            jLabel7.setVisible(false);
            jLabel26.setVisible(false);
            
        }else
            jButton5_admin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appthoitiet/admin.png")));
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel_fromTieuDe = new javax.swing.JPanel();
        jLabel1_FormLogin1 = new javax.swing.JLabel();
        jLabel1_Min = new javax.swing.JLabel();
        jLabel1_FormLogin = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton1_settings = new javax.swing.JButton();
        jButton2_Fan = new javax.swing.JButton();
        jButton3_Home = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        RightPanel = new javax.swing.JPanel();
        jButton5_admin = new javax.swing.JButton();
        jLabel_NAME = new javax.swing.JLabel();
        jButton4_exit = new javax.swing.JButton();
        jLabel_dangXuat1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jPanel_GiaoDienChinh = new javax.swing.JPanel();
        jPanel_GiaoDienSetting = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPasswordField_MatKhauCu = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        jPasswordField_MaKhauMoi = new javax.swing.JPasswordField();
        jPasswordField_ĐoiMaDangNhap = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();
        jButton_NutSaveDoiMatKhau = new javax.swing.JButton();
        jButton_NutSaveDoiMaDangNhap = new javax.swing.JButton();
        jButton_ThemNguoiDung = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jLabel_NAME1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel_BieuDo = new javax.swing.JPanel();
        jLabel_HienThiBieuDo = new javax.swing.JLabel();
        jLabel_HienThiDoAm = new javax.swing.JLabel();
        jLabel_Reload = new javax.swing.JLabel();
        jPane_ThemNguoiDung = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTextField_TênNguoiDungMoi = new javax.swing.JTextField();
        jPasswordField_MatKhauMoi = new javax.swing.JPasswordField();
        jLabel18 = new javax.swing.JLabel();
        jPasswordField_XacNhanMoi = new javax.swing.JPasswordField();
        jButton_NutSaveThemNguoiDung = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jTextField_UsernameMoi = new javax.swing.JTextField();
        jPanel_1GiaoDienHome = new javax.swing.JPanel();
        jButton_BieuDo = new javax.swing.JButton();
        lb_doam = new javax.swing.JLabel();
        lb_nhietdo = new javax.swing.JLabel();
        lb_updatetime = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jPanel_2GiaoDienFan = new javax.swing.JPanel();
        jSlider_BatCuaNhietDo = new javax.swing.JSlider();
        jLabel8 = new javax.swing.JLabel();
        jLabel_ThietBi = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jSlider_TatCuaNhietDo = new javax.swing.JSlider();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jSlider_BatCuaDoAm = new javax.swing.JSlider();
        jSlider_TatCuaDoAm = new javax.swing.JSlider();
        jPanel7 = new javax.swing.JPanel(){
            ImageIcon icon = new ImageIcon("src/appthoitiet/humidity setting.png");
            public void paintComponent(Graphics g){
                Dimension d = getSize();
                g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
                setOpaque(false);
                super.paintComponent(g);
            }
        };
        jTextField_BatNhietDo = new javax.swing.JTextField();
        jTextField_TatNhietDo = new javax.swing.JTextField();
        jTextField_BatDoAm = new javax.swing.JTextField();
        jTextField_TatDoAm = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jRadioButton_NhietDo = new javax.swing.JRadioButton();
        jRadioButton_DoAm = new javax.swing.JRadioButton();
        jButton_NutSaveNhietDo = new javax.swing.JButton();
        jButton_NutBatTatThietBi = new javax.swing.JButton();
        jButton_NutBatTatTuDong = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ENVIROMENT CONTROLER");
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setUndecorated(true);

        jPanel_fromTieuDe.setBackground(new java.awt.Color(217, 30, 24));

        jLabel1_FormLogin1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel1_FormLogin1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1_FormLogin1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1_FormLogin1.setText("X");
        jLabel1_FormLogin1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1_FormLogin1MouseClicked(evt);
            }
        });

        jLabel1_Min.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel1_Min.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1_Min.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1_Min.setText("-");
        jLabel1_Min.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1_MinMouseClicked(evt);
            }
        });

        jLabel1_FormLogin.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1_FormLogin.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1_FormLogin.setText("Enviroment Controler");

        javax.swing.GroupLayout jPanel_fromTieuDeLayout = new javax.swing.GroupLayout(jPanel_fromTieuDe);
        jPanel_fromTieuDe.setLayout(jPanel_fromTieuDeLayout);
        jPanel_fromTieuDeLayout.setHorizontalGroup(
            jPanel_fromTieuDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_fromTieuDeLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1_FormLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(602, 602, 602)
                .addComponent(jLabel1_Min, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1_FormLogin1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel_fromTieuDeLayout.setVerticalGroup(
            jPanel_fromTieuDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_fromTieuDeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_fromTieuDeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1_FormLogin1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1_Min, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1_FormLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(1, 50, 67));
        jPanel2.setPreferredSize(new java.awt.Dimension(600, 600));

        jButton1_settings.setBackground(new java.awt.Color(1, 50, 67));
        jButton1_settings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appthoitiet/settings.png"))); // NOI18N
        jButton1_settings.setBorder(null);
        jButton1_settings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1_settingsActionPerformed(evt);
            }
        });

        jButton2_Fan.setBackground(new java.awt.Color(1, 50, 67));
        jButton2_Fan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appthoitiet/fan.png"))); // NOI18N
        jButton2_Fan.setBorder(null);
        jButton2_Fan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2_FanActionPerformed(evt);
            }
        });

        jButton3_Home.setBackground(new java.awt.Color(1, 50, 67));
        jButton3_Home.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appthoitiet/home.png"))); // NOI18N
        jButton3_Home.setBorder(null);
        jButton3_Home.setBorderPainted(false);
        jButton3_Home.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton3_Home.setDefaultCapable(false);
        jButton3_Home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3_HomeMouseClicked(evt);
            }
        });
        jButton3_Home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3_HomeActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 255, 255));
        jLabel2.setText("By Võ Phước Nguyễn & Đàm Quang Ân");

        RightPanel.setBackground(new java.awt.Color(1, 50, 67));
        RightPanel.setForeground(new java.awt.Color(1, 50, 67));

        jButton5_admin.setBackground(new java.awt.Color(1, 50, 67));
        jButton5_admin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appthoitiet/guest.png"))); // NOI18N
        jButton5_admin.setBorder(null);
        jButton5_admin.setBorderPainted(false);
        jButton5_admin.setContentAreaFilled(false);
        jButton5_admin.setDefaultCapable(false);
        jButton5_admin.setFocusPainted(false);
        jButton5_admin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5_adminActionPerformed(evt);
            }
        });

        jLabel_NAME.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel_NAME.setForeground(new java.awt.Color(204, 255, 255));
        jLabel_NAME.setText("Khách");
        jLabel_NAME.setPreferredSize(new java.awt.Dimension(63, 80));

        jButton4_exit.setBackground(new java.awt.Color(1, 50, 67));
        jButton4_exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appthoitiet/exit (1).png"))); // NOI18N
        jButton4_exit.setAutoscrolls(true);
        jButton4_exit.setBorder(null);
        jButton4_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4_exitActionPerformed(evt);
            }
        });

        jLabel_dangXuat1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel_dangXuat1.setForeground(new java.awt.Color(204, 255, 255));
        jLabel_dangXuat1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel_dangXuat1.setText(" Đăng xuất ");
        jLabel_dangXuat1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_dangXuat1MouseClicked(evt);
            }
        });

        jList2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jList2.setForeground(new java.awt.Color(1, 50, 67));
        jList2.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "116", "17", "18", "19", "20", "21" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList2);

        javax.swing.GroupLayout RightPanelLayout = new javax.swing.GroupLayout(RightPanel);
        RightPanel.setLayout(RightPanelLayout);
        RightPanelLayout.setHorizontalGroup(
            RightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RightPanelLayout.createSequentialGroup()
                .addComponent(jButton5_admin, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_NAME, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(RightPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(RightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(RightPanelLayout.createSequentialGroup()
                        .addComponent(jButton4_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_dangXuat1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        RightPanelLayout.setVerticalGroup(
            RightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RightPanelLayout.createSequentialGroup()
                .addGroup(RightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5_admin, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_NAME, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(RightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_dangXuat1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel_GiaoDienChinh.setLayout(new java.awt.CardLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(1, 50, 67));
        jLabel3.setText("Thêm người dùng");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(1, 50, 67));
        jLabel5.setText("Mật khẩu cũ:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(1, 50, 67));
        jLabel6.setText("Mật khẩu mới:");

        jPasswordField_ĐoiMaDangNhap.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPasswordField_ĐoiMaDangNhap.setText("Mã đăng nhập hiện tại:");
        jPasswordField_ĐoiMaDangNhap.setActionCommand("null");
        jPasswordField_ĐoiMaDangNhap.setEchoChar((char) 0);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(1, 50, 67));
        jLabel7.setText("Đổi mã đăng nhập: ");
        jLabel7.setFocusTraversalPolicyProvider(true);

        jButton_NutSaveDoiMatKhau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appthoitiet/save.png"))); // NOI18N
        jButton_NutSaveDoiMatKhau.setBorder(null);
        jButton_NutSaveDoiMatKhau.setBorderPainted(false);
        jButton_NutSaveDoiMatKhau.setContentAreaFilled(false);
        jButton_NutSaveDoiMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_NutSaveDoiMatKhauActionPerformed(evt);
            }
        });

        jButton_NutSaveDoiMaDangNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appthoitiet/save.png"))); // NOI18N
        jButton_NutSaveDoiMaDangNhap.setBorder(null);
        jButton_NutSaveDoiMaDangNhap.setBorderPainted(false);
        jButton_NutSaveDoiMaDangNhap.setContentAreaFilled(false);
        jButton_NutSaveDoiMaDangNhap.setDefaultCapable(false);
        jButton_NutSaveDoiMaDangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_NutSaveDoiMaDangNhapActionPerformed(evt);
            }
        });

        jButton_ThemNguoiDung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appthoitiet/add.png"))); // NOI18N
        jButton_ThemNguoiDung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ThemNguoiDungActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(1, 50, 67));
        jLabel24.setText("Đổi mật khẩu");

        jLabel_NAME1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel_NAME1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel_NAME1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel_NAME1.setText("Namee Name Name");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appthoitiet/password.png"))); // NOI18N

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appthoitiet/code.png"))); // NOI18N

        javax.swing.GroupLayout jPanel_GiaoDienSettingLayout = new javax.swing.GroupLayout(jPanel_GiaoDienSetting);
        jPanel_GiaoDienSetting.setLayout(jPanel_GiaoDienSettingLayout);
        jPanel_GiaoDienSettingLayout.setHorizontalGroup(
            jPanel_GiaoDienSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_GiaoDienSettingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_GiaoDienSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_GiaoDienSettingLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton_NutSaveDoiMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_GiaoDienSettingLayout.createSequentialGroup()
                        .addGroup(jPanel_GiaoDienSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel_GiaoDienSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_GiaoDienSettingLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton_NutSaveDoiMaDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPasswordField_ĐoiMaDangNhap)))
                    .addGroup(jPanel_GiaoDienSettingLayout.createSequentialGroup()
                        .addComponent(jButton_ThemNguoiDung, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel_GiaoDienSettingLayout.createSequentialGroup()
                        .addGroup(jPanel_GiaoDienSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel_GiaoDienSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPasswordField_MaKhauMoi)
                            .addComponent(jPasswordField_MatKhauCu)))
                    .addGroup(jPanel_GiaoDienSettingLayout.createSequentialGroup()
                        .addGroup(jPanel_GiaoDienSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(jLabel_NAME1, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)))
                .addContainerGap())
        );
        jPanel_GiaoDienSettingLayout.setVerticalGroup(
            jPanel_GiaoDienSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_GiaoDienSettingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_GiaoDienSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton_ThemNguoiDung, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel_GiaoDienSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel_GiaoDienSettingLayout.createSequentialGroup()
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel_NAME1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_GiaoDienSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordField_MatKhauCu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel_GiaoDienSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordField_MaKhauMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_NutSaveDoiMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel_GiaoDienSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordField_ĐoiMaDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_GiaoDienSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton_NutSaveDoiMaDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addContainerGap())
        );

        jPanel_GiaoDienChinh.add(jPanel_GiaoDienSetting, "card4");

        jPanel_BieuDo.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jPanel_BieuDoComponentResized(evt);
            }
        });

        jLabel_Reload.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_Reload.setText("Reload");
        jLabel_Reload.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel_Reload.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel_ReloadMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel_BieuDoLayout = new javax.swing.GroupLayout(jPanel_BieuDo);
        jPanel_BieuDo.setLayout(jPanel_BieuDoLayout);
        jPanel_BieuDoLayout.setHorizontalGroup(
            jPanel_BieuDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_BieuDoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_BieuDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_HienThiBieuDo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel_HienThiDoAm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel_Reload, javax.swing.GroupLayout.DEFAULT_SIZE, 818, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel_BieuDoLayout.setVerticalGroup(
            jPanel_BieuDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_BieuDoLayout.createSequentialGroup()
                .addComponent(jLabel_HienThiBieuDo, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_HienThiDoAm, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_Reload, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel_GiaoDienChinh.add(jPanel_BieuDo, "card5");

        jPane_ThemNguoiDung.setPreferredSize(new java.awt.Dimension(763, 604));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel16.setText("Username: ");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel17.setText("Mật khẩu:");

        jPasswordField_MatKhauMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordField_MatKhauMoiActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel18.setText("Xác nhận:");

        jButton_NutSaveThemNguoiDung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appthoitiet/save.png"))); // NOI18N
        jButton_NutSaveThemNguoiDung.setBorderPainted(false);
        jButton_NutSaveThemNguoiDung.setContentAreaFilled(false);
        jButton_NutSaveThemNguoiDung.setDefaultCapable(false);
        jButton_NutSaveThemNguoiDung.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jButton_NutSaveThemNguoiDungComponentResized(evt);
            }
        });
        jButton_NutSaveThemNguoiDung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_NutSaveThemNguoiDungActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Thêm người dùng mới");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel23.setText("Tên người dùng:");

        jTextField_UsernameMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_UsernameMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPane_ThemNguoiDungLayout = new javax.swing.GroupLayout(jPane_ThemNguoiDung);
        jPane_ThemNguoiDung.setLayout(jPane_ThemNguoiDungLayout);
        jPane_ThemNguoiDungLayout.setHorizontalGroup(
            jPane_ThemNguoiDungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPane_ThemNguoiDungLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPane_ThemNguoiDungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 818, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPane_ThemNguoiDungLayout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPane_ThemNguoiDungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton_NutSaveThemNguoiDung, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_TênNguoiDungMoi)))
                    .addGroup(jPane_ThemNguoiDungLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_UsernameMoi))
                    .addGroup(jPane_ThemNguoiDungLayout.createSequentialGroup()
                        .addGroup(jPane_ThemNguoiDungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPane_ThemNguoiDungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPasswordField_XacNhanMoi)
                            .addComponent(jPasswordField_MatKhauMoi))))
                .addContainerGap())
        );
        jPane_ThemNguoiDungLayout.setVerticalGroup(
            jPane_ThemNguoiDungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPane_ThemNguoiDungLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addGroup(jPane_ThemNguoiDungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jTextField_TênNguoiDungMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPane_ThemNguoiDungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_UsernameMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(50, 50, 50)
                .addGroup(jPane_ThemNguoiDungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordField_MatKhauMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20)
                .addGroup(jPane_ThemNguoiDungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordField_XacNhanMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_NutSaveThemNguoiDung, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel_GiaoDienChinh.add(jPane_ThemNguoiDung, "card6");

        jPanel_1GiaoDienHome.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPanel_1GiaoDienHomeFocusGained(evt);
            }
        });

        jButton_BieuDo.setBackground(new java.awt.Color(1, 50, 67));
        jButton_BieuDo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton_BieuDo.setForeground(new java.awt.Color(255, 255, 255));
        jButton_BieuDo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appthoitiet/chart.png"))); // NOI18N
        jButton_BieuDo.setText("Hiển thị biểu đồ môi trường");
        jButton_BieuDo.setToolTipText("");
        jButton_BieuDo.setAutoscrolls(true);
        jButton_BieuDo.setContentAreaFilled(false);
        jButton_BieuDo.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton_BieuDo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_BieuDoActionPerformed(evt);
            }
        });

        lb_doam.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        lb_doam.setForeground(new java.awt.Color(1, 50, 67));
        lb_doam.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lb_doam.setText("0 %RH");

        lb_nhietdo.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        lb_nhietdo.setForeground(new java.awt.Color(1, 50, 67));
        lb_nhietdo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lb_nhietdo.setText("0 °C     ");

        lb_updatetime.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_updatetime.setForeground(new java.awt.Color(1, 50, 67));
        lb_updatetime.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lb_updatetime.setText("Loading, please wait... !");
        lb_updatetime.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb_updatetimeMouseClicked(evt);
            }
        });

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appthoitiet/humidity.png"))); // NOI18N

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appthoitiet/hot.png"))); // NOI18N

        javax.swing.GroupLayout jPanel_1GiaoDienHomeLayout = new javax.swing.GroupLayout(jPanel_1GiaoDienHome);
        jPanel_1GiaoDienHome.setLayout(jPanel_1GiaoDienHomeLayout);
        jPanel_1GiaoDienHomeLayout.setHorizontalGroup(
            jPanel_1GiaoDienHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_1GiaoDienHomeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_BieuDo)
                .addContainerGap())
            .addGroup(jPanel_1GiaoDienHomeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_1GiaoDienHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_updatetime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_1GiaoDienHomeLayout.createSequentialGroup()
                        .addGroup(jPanel_1GiaoDienHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lb_nhietdo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lb_doam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel_1GiaoDienHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50))))
        );
        jPanel_1GiaoDienHomeLayout.setVerticalGroup(
            jPanel_1GiaoDienHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_1GiaoDienHomeLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jButton_BieuDo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
                .addGroup(jPanel_1GiaoDienHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_nhietdo, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel_1GiaoDienHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lb_doam, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 116, Short.MAX_VALUE)
                .addComponent(lb_updatetime)
                .addContainerGap())
        );

        jPanel_GiaoDienChinh.add(jPanel_1GiaoDienHome, "card2");

        jPanel_2GiaoDienFan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel_2GiaoDienFanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel_2GiaoDienFanMouseExited(evt);
            }
        });

        jSlider_BatCuaNhietDo.setMaximum(1000);
        jSlider_BatCuaNhietDo.setMinorTickSpacing(100);
        jSlider_BatCuaNhietDo.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider_BatCuaNhietDoStateChanged(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel8.setText("Tự động");

        jLabel_ThietBi.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel_ThietBi.setText("Thiết bị");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel9.setText("Nhiệt độ:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel10.setText("Bật:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel11.setText("Tắt:");

        jSlider_TatCuaNhietDo.setMaximum(1000);
        jSlider_TatCuaNhietDo.setMinorTickSpacing(100);
        jSlider_TatCuaNhietDo.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider_TatCuaNhietDoStateChanged(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel12.setText("Độ ẩm:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel13.setText("Bật:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel14.setText("Tắt:");

        jSlider_BatCuaDoAm.setMaximum(1000);
        jSlider_BatCuaDoAm.setMinorTickSpacing(100);
        jSlider_BatCuaDoAm.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider_BatCuaDoAmStateChanged(evt);
            }
        });

        jSlider_TatCuaDoAm.setMaximum(1000);
        jSlider_TatCuaDoAm.setMinorTickSpacing(100);
        jSlider_TatCuaDoAm.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider_TatCuaDoAmStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jTextField_BatNhietDo.setEnabled(false);
        jTextField_BatNhietDo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_BatNhietDoKeyTyped(evt);
            }
        });

        jTextField_TatNhietDo.setEnabled(false);
        jTextField_TatNhietDo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_TatNhietDoKeyTyped(evt);
            }
        });

        jTextField_BatDoAm.setEnabled(false);
        jTextField_BatDoAm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_BatDoAmKeyTyped(evt);
            }
        });

        jTextField_TatDoAm.setEnabled(false);
        jTextField_TatDoAm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_TatDoAmKeyTyped(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel15.setText("°C");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel19.setText("°C");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel20.setText("%");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel21.setText("%");

        jRadioButton_NhietDo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jRadioButton_NhietDo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jRadioButton_NhietDo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_NhietDoActionPerformed(evt);
            }
        });

        jRadioButton_DoAm.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jRadioButton_DoAm.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jRadioButton_DoAm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_DoAmActionPerformed(evt);
            }
        });

        jButton_NutSaveNhietDo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appthoitiet/save.png"))); // NOI18N
        jButton_NutSaveNhietDo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_NutSaveNhietDoActionPerformed(evt);
            }
        });

        jButton_NutBatTatThietBi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appthoitiet/off.png"))); // NOI18N
        jButton_NutBatTatThietBi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton_NutBatTatThietBi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_NutBatTatThietBiActionPerformed(evt);
            }
        });

        jButton_NutBatTatTuDong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appthoitiet/mode.png"))); // NOI18N
        jButton_NutBatTatTuDong.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton_NutBatTatTuDong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_NutBatTatTuDongActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_2GiaoDienFanLayout = new javax.swing.GroupLayout(jPanel_2GiaoDienFan);
        jPanel_2GiaoDienFan.setLayout(jPanel_2GiaoDienFanLayout);
        jPanel_2GiaoDienFanLayout.setHorizontalGroup(
            jPanel_2GiaoDienFanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_2GiaoDienFanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_2GiaoDienFanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_2GiaoDienFanLayout.createSequentialGroup()
                        .addGroup(jPanel_2GiaoDienFanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton_NutBatTatThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_ThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 415, Short.MAX_VALUE)
                        .addComponent(jButton_NutBatTatTuDong, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_2GiaoDienFanLayout.createSequentialGroup()
                        .addGroup(jPanel_2GiaoDienFanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_2GiaoDienFanLayout.createSequentialGroup()
                                .addGroup(jPanel_2GiaoDienFanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel_2GiaoDienFanLayout.createSequentialGroup()
                                        .addGroup(jPanel_2GiaoDienFanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel13)
                                            .addComponent(jLabel14))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel_2GiaoDienFanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jSlider_BatCuaDoAm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jSlider_TatCuaDoAm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(jPanel_2GiaoDienFanLayout.createSequentialGroup()
                                        .addGroup(jPanel_2GiaoDienFanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel11)
                                            .addComponent(jLabel10))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel_2GiaoDienFanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jSlider_BatCuaNhietDo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jSlider_TatCuaNhietDo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel_2GiaoDienFanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField_BatNhietDo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField_TatNhietDo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_2GiaoDienFanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jButton_NutSaveNhietDo, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel_2GiaoDienFanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jTextField_TatDoAm, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField_BatDoAm, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel_2GiaoDienFanLayout.createSequentialGroup()
                                .addGroup(jPanel_2GiaoDienFanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel_2GiaoDienFanLayout.createSequentialGroup()
                                        .addComponent(jRadioButton_NhietDo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel9))
                                    .addGroup(jPanel_2GiaoDienFanLayout.createSequentialGroup()
                                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jRadioButton_DoAm, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_2GiaoDienFanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        jPanel_2GiaoDienFanLayout.setVerticalGroup(
            jPanel_2GiaoDienFanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_2GiaoDienFanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_2GiaoDienFanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton_NutBatTatTuDong, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel_2GiaoDienFanLayout.createSequentialGroup()
                        .addComponent(jLabel_ThietBi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_NutBatTatThietBi, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                .addGroup(jPanel_2GiaoDienFanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton_NhietDo, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addGap(20, 20, 20)
                .addGroup(jPanel_2GiaoDienFanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSlider_BatCuaNhietDo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addGroup(jPanel_2GiaoDienFanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField_BatNhietDo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_2GiaoDienFanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_2GiaoDienFanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField_TatNhietDo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel19))
                    .addComponent(jLabel11)
                    .addComponent(jSlider_TatCuaNhietDo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                .addGroup(jPanel_2GiaoDienFanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jRadioButton_DoAm, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel_2GiaoDienFanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_2GiaoDienFanLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_2GiaoDienFanLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel_2GiaoDienFanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_2GiaoDienFanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel20)
                                .addComponent(jTextField_BatDoAm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel_2GiaoDienFanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jSlider_BatCuaDoAm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel13)))))
                .addGroup(jPanel_2GiaoDienFanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_2GiaoDienFanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jSlider_TatCuaDoAm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14))
                    .addGroup(jPanel_2GiaoDienFanLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel_2GiaoDienFanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(jTextField_TatDoAm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_NutSaveNhietDo, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(90, Short.MAX_VALUE))
        );

        jPanel_GiaoDienChinh.add(jPanel_2GiaoDienFan, "card3");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2_Fan, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3_Home, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1_settings, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jPanel_GiaoDienChinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(RightPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(RightPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jButton3_Home, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jButton2_Fan, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1_settings, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel_GiaoDienChinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jLabel2))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_fromTieuDe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1366, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel_fromTieuDe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void jLabel1_FormLogin1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1_FormLogin1MouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jLabel1_FormLogin1MouseClicked

    private void jLabel1_MinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1_MinMouseClicked
        // TODO add your handling code here:
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_jLabel1_MinMouseClicked

    private void jButton5_adminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5_adminActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5_adminActionPerformed

    private void jButton4_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4_exitActionPerformed
        // TODO add your handling code here:
        FromDangNhap fd = new FromDangNhap();
        fd.setVisible(true);
        fd.setVisible(true);
        fd.pack();
        fd.setLocationRelativeTo(null);
        fd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_jButton4_exitActionPerformed

    private void jButton3_HomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3_HomeActionPerformed
        // TODO add your handling code here:
        card.show(jPanel_GiaoDienChinh, "card2");
    }//GEN-LAST:event_jButton3_HomeActionPerformed

    private void jButton3_HomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3_HomeMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3_HomeMouseClicked

    private void jButton2_FanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2_FanActionPerformed
        // TODO add your handling code here:
        card.show(jPanel_GiaoDienChinh, "card3");
    }//GEN-LAST:event_jButton2_FanActionPerformed

    private void jButton1_settingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1_settingsActionPerformed
        // TODO add your handling code here:
        card.show(jPanel_GiaoDienChinh, "card4");
    }//GEN-LAST:event_jButton1_settingsActionPerformed

    private void jButton_BieuDoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_BieuDoActionPerformed
        // TODO add your handling code here:
        card.show(jPanel_GiaoDienChinh, "card5");
    }//GEN-LAST:event_jButton_BieuDoActionPerformed


    private void jButton_NutSaveDoiMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_NutSaveDoiMatKhauActionPerformed
        // TODO add your handling code here: 
        if (jPasswordField_MatKhauCu.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Mật khẩu cũ không được bỏ trống");
        } else if (jPasswordField_MaKhauMoi.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Mật khẩu mới không được bỏ trống");
        } else {
            boolean tonTai;
            try {
                tonTai = kiemTraDoiMatKhau();
                if (tonTai == true) {
                    JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công");
                } else {
                    JOptionPane.showMessageDialog(this, "Sai mật khẩu cũ!!!");
                }
            } catch (IOException ex) {
                Logger.getLogger(FromGiaoDien.class.getName()).log(Level.SEVERE, null, ex);
            }

        }


    }//GEN-LAST:event_jButton_NutSaveDoiMatKhauActionPerformed
    public class User_DoiMatKhau {

        final private String NewPW;
        final private String OldPW;

        User_DoiMatKhau(String PasswordNew, String PasswordOld) {

            NewPW = PasswordNew;
            OldPW = PasswordOld;
        }

        User_DoiMatKhau() {

            NewPW = "";
            OldPW = "";
        }

        public URL get_URLDoiMatKhau() throws MalformedURLException {
            String link = "https://doandieukhien.000webhostapp.com/user.php?field=changepass";
            link = link + "&id=" + FGDU.ID + "&newpw=" + NewPW + "&oldpw=" + OldPW;
            URL url = new URL(link);
            return url;
        }

        public void show_URLDoiMatKhau() throws MalformedURLException {
            URL url;
            url = get_URLDoiMatKhau();
            System.out.println(url.toString());
        }
    }

    public boolean kiemTraDoiMatKhau() throws MalformedURLException, IOException {
        User_DoiMatKhau us = new User_DoiMatKhau(jPasswordField_MaKhauMoi.getText(), jPasswordField_MatKhauCu.getText());
        us.show_URLDoiMatKhau();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(us.get_URLDoiMatKhau().openStream()))) {

            String line;

            String sb = new String();

            while ((line = br.readLine()) != null) {

                sb = sb + line;                //sb=sb+System.lineSeparator();
            }
            System.out.println(sb);
            System.out.println(sb.startsWith("1")); //sử dụng .startsWith của String để kiểm tra 
            if (sb.startsWith("1")) {
                return true;
            }

        } catch (IOException ex) {
            Logger.getLogger(FromDangNhap.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    private void jButton_ThemNguoiDungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ThemNguoiDungActionPerformed
        // TODO add your handling code here:
        card.show(jPanel_GiaoDienChinh, "card6");
    }//GEN-LAST:event_jButton_ThemNguoiDungActionPerformed

    private void jButton_NutSaveThemNguoiDungComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jButton_NutSaveThemNguoiDungComponentResized
        // TODO add your handling code here:
        ImageIcon ii = new ImageIcon(getClass().getResource("/appthoitiet/save.png"));
        Image image = ii.getImage().getScaledInstance(jButton_NutSaveThemNguoiDung.getHeight() * 7 / 8, jButton_NutSaveThemNguoiDung.getWidth() * 7 / 8, Image.SCALE_SMOOTH);
        jButton_NutSaveThemNguoiDung.setIcon(new ImageIcon(image));
    }//GEN-LAST:event_jButton_NutSaveThemNguoiDungComponentResized

    private void jButton_NutSaveThemNguoiDungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_NutSaveThemNguoiDungActionPerformed
        // TODO add your handling code here:
        String NguoiDungMoi = jTextField_TênNguoiDungMoi.getText();
        String MatKhau = jPasswordField_MatKhauMoi.getText();
        String XacNhan = jPasswordField_XacNhanMoi.getText();

        if (jPasswordField_MatKhauMoi.getText() == null ? jPasswordField_XacNhanMoi.getText() != null : !jPasswordField_MatKhauMoi.getText().equals(jPasswordField_XacNhanMoi.getText())) {
            JOptionPane.showMessageDialog(this, "Mã xác nhận không trùng với mật khẩu");
        } else if (jTextField_TênNguoiDungMoi.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Tên người dùng không được bỏ trống");
        } else if (jTextField_UsernameMoi.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Username không được bỏ trống");
        } else if (jPasswordField_MatKhauMoi.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được bỏ trống");
        } else {
            boolean tonTai;
            try {
                tonTai = kiemTraThemNguoiDung();
                if (tonTai == true) {
                    JOptionPane.showMessageDialog(this, "Thêm thành công");
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm thất bại do User trùng hoặc Tên người dùng trùng");
                }
            } catch (IOException ex) {
                Logger.getLogger(FromGiaoDien.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton_NutSaveThemNguoiDungActionPerformed

    private void jButton_NutSaveDoiMaDangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_NutSaveDoiMaDangNhapActionPerformed
        // TODO add your handling code here:
        if (jPasswordField_ĐoiMaDangNhap.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền mã mới");
        } else {
            boolean tonTai;
            try {
                tonTai = KiemTraThayDoiCode();
                if (tonTai == true) {
                    JOptionPane.showMessageDialog(this, "Đổi mã thành công");
                }
            } catch (IOException ex) {
                Logger.getLogger(FromGiaoDien.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton_NutSaveDoiMaDangNhapActionPerformed

    private void jPanel_BieuDoComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel_BieuDoComponentResized
        // TODO add your handling code here:
        
        URL url_key = null;
        try {
            url_key = new URL("https://doandieukhien.000webhostapp.com/chart.php?field=temp&num=10");
        } catch (MalformedURLException ex) {
            Logger.getLogger(FromGiaoDien.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url_key.openStream()))) {
            String line;
            String sb = new String();
            while ((line = br.readLine()) != null) {
                sb = sb + line;                //sb=sb+System.lineSeparator();
            }
            int mode = 0;
            String idx = new String();
            String time = new String();
            System.out.println(sb);
            while (sb.length() >= 1) {
                idx = sb.substring(0, sb.indexOf("*")) + idx;
                sb = sb.substring(sb.indexOf("*") + 1, sb.length());
                idx = "%2C" + idx; //VD: %2A
                time = sb.substring(0, sb.indexOf("#")) + time;
                sb = sb.substring(sb.indexOf("#") + 1, sb.length());
                time = "|" + time;//VD: %2A
            }
            //Xoa 2 ky tu cuoi
            idx = idx.substring("%2A".length(), idx.length());
            time = time.substring("|".length(), time.length());
            System.out.println(idx);
            System.out.println(time);

            URL url = new URL("https://image-charts.com/chart?chco=E74C3C&chd=t%3A" + idx + "&chf=bg%2Cs%2CFFFFFF&chg=10%2C10&chls=3&chm=B%2Cfdb45c%2C0%2C0%2C0&chma=0%2C50%2C0&chs=739x295&cht=lc&chtt=Th%E1%BB%91ng%20k%C3%AA%20nhi%E1%BB%87t%20%C4%91%E1%BB%99&chxt=x%2Cy&chxl=0%3A|" + time);

            BufferedImage c = ImageIO.read(url);
            ImageIcon image2 = new ImageIcon(c);
            Image image22 = image2.getImage().getScaledInstance(739, 278, Image.SCALE_SMOOTH);
            jLabel_HienThiBieuDo.setIcon(new ImageIcon(image22));

        } catch (IOException ex) {
            Logger.getLogger(FromGiaoDien.class.getName()).log(Level.SEVERE, null, ex);
        }

        URL url_key1 = null;
        try {
            url_key1 = new URL("https://doandieukhien.000webhostapp.com/chart.php?field=humi&num=10");
        } catch (MalformedURLException ex) {
            Logger.getLogger(FromGiaoDien.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url_key1.openStream()))) {
            String line;
            String sb = new String();
            while ((line = br.readLine()) != null) {
                sb = sb + line;                //sb=sb+System.lineSeparator();
            }
            int mode = 0;
            String idx = new String();
            String time = new String();
            System.out.println(sb);
            while (sb.length() >= 1) {
                idx = sb.substring(0, sb.indexOf("*")) + idx;
                sb = sb.substring(sb.indexOf("*") + 1, sb.length());
                idx = "%2C" + idx; //VD: %2A
                time = sb.substring(0, sb.indexOf("#")) + time;
                sb = sb.substring(sb.indexOf("#") + 1, sb.length());
                time = "|" + time;//VD: %2A
            }
            //Xoa 2 ky tu cuoi
            idx = idx.substring("%2A".length(), idx.length());
            time = time.substring("|".length(), time.length());
            System.out.println(idx);
            System.out.println(time);

            URL url = new URL("https://image-charts.com/chart?chco=087D66&chd=t%3A" + idx + "&chf=bg%2Cs%2CFFFFFF&chg=10%2C10&chls=3&chm=B%2C76D7C4%2C0%2C0%2C0&chma=0%2C50%2C0&chs=739x295&cht=lc&chtt=Th%E1%BB%91ng%20k%C3%AA%20%C4%91%E1%BB%99%20%E1%BA%A9m&chxt=x%2Cy&chxl=0%3A|" + time);

            BufferedImage c = ImageIO.read(url);
            ImageIcon image2 = new ImageIcon(c);
            Image image22 = image2.getImage().getScaledInstance(739, 295, Image.SCALE_SMOOTH);
            jLabel_HienThiDoAm.setIcon(new ImageIcon(image22));

        } catch (IOException ex) {
            Logger.getLogger(FromGiaoDien.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
        
    }//GEN-LAST:event_jPanel_BieuDoComponentResized
   
    private void jLabel_ReloadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_ReloadMouseClicked
        // TODO add your handling code here:
        
        URL url_key = null;
        try {
            url_key = new URL("https://doandieukhien.000webhostapp.com/chart.php?field=temp&num=10");
        } catch (MalformedURLException ex) {
            Logger.getLogger(FromGiaoDien.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url_key.openStream()))) {
            String line;
            String sb = new String();
            while ((line = br.readLine()) != null) {
                sb = sb + line;                //sb=sb+System.lineSeparator();
            }
            int mode = 0;
            String idx = new String();
            String time = new String();
            System.out.println(sb);
            while (sb.length() >= 1) {
                idx = sb.substring(0, sb.indexOf("*")) + idx;
                sb = sb.substring(sb.indexOf("*") + 1, sb.length());
                idx = "%2C" + idx; //VD: %2A
                time = sb.substring(0, sb.indexOf("#")) + time;
                sb = sb.substring(sb.indexOf("#") + 1, sb.length());
                time = "|" + time;//VD: %2A
            }
            //Xoa 2 ky tu cuoi
            idx = idx.substring("%2A".length(), idx.length());
            time = time.substring("|".length(), time.length());
            System.out.println(idx);
            System.out.println(time);

            URL url = new URL("https://image-charts.com/chart?chco=E74C3C&chd=t%3A" + idx + "&chf=bg%2Cs%2CFFFFFF&chg=10%2C10&chls=3&chm=B%2Cfdb45c%2C0%2C0%2C0&chma=0%2C50%2C0&chs=739x295&cht=lc&chtt=Th%E1%BB%91ng%20k%C3%AA%20nhi%E1%BB%87t%20%C4%91%E1%BB%99&chxt=x%2Cy&chxl=0%3A|" + time);
            BufferedImage c = ImageIO.read(url);
            ImageIcon image2 = new ImageIcon(c);
            Image image22 = image2.getImage().getScaledInstance(739, 278, Image.SCALE_SMOOTH);
            jLabel_HienThiBieuDo.setIcon(new ImageIcon(image22));

        } catch (IOException ex) {
            Logger.getLogger(FromGiaoDien.class.getName()).log(Level.SEVERE, null, ex);
        }

        URL url_key1 = null;
        try {
            url_key1 = new URL("https://doandieukhien.000webhostapp.com/chart.php?field=humi&num=10");
        } catch (MalformedURLException ex) {
            Logger.getLogger(FromGiaoDien.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url_key1.openStream()))) {
            String line;
            String sb = new String();
            while ((line = br.readLine()) != null) {
                sb = sb + line;                //sb=sb+System.lineSeparator();
            }
            int mode = 0;
            String idx = new String();
            String time = new String();
            System.out.println(sb);
            while (sb.length() >= 1) {
                idx = sb.substring(0, sb.indexOf("*")) + idx;
                sb = sb.substring(sb.indexOf("*") + 1, sb.length());
                idx = "%2C" + idx; //VD: %2A
                time = sb.substring(0, sb.indexOf("#")) + time;
                sb = sb.substring(sb.indexOf("#") + 1, sb.length());
                time = "|" + time;//VD: %2A
            }
            //Xoa 2 ky tu cuoi
            idx = idx.substring("%2A".length(), idx.length());
            time = time.substring("|".length(), time.length());
            System.out.println(idx);
            System.out.println(time);

            URL url = new URL("https://image-charts.com/chart?chco=087D66&chd=t%3A" + idx + "&chf=bg%2Cs%2CFFFFFF&chg=10%2C10&chls=3&chm=B%2C76D7C4%2C0%2C0%2C0&chma=0%2C50%2C0&chs=739x295&cht=lc&chtt=Th%E1%BB%91ng%20k%C3%AA%20%C4%91%E1%BB%99%20%E1%BA%A9m&chxt=x%2Cy&chxl=0%3A|" + time);
            BufferedImage c = ImageIO.read(url);
            ImageIcon image2 = new ImageIcon(c);
            Image image22 = image2.getImage().getScaledInstance(739, 295, Image.SCALE_SMOOTH);
            jLabel_HienThiDoAm.setIcon(new ImageIcon(image22));

        } catch (IOException ex) {
            Logger.getLogger(FromGiaoDien.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jLabel_ReloadMouseClicked

    private void jButton_NutSaveNhietDoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_NutSaveNhietDoActionPerformed
        // TODO add your handling code here:
        if (jRadioButton_NhietDo.isSelected()) {
            try {
                URL url = new URL("https://doandieukhien.000webhostapp.com/t1h0.php?t1h0=1&on=" + jTextField_BatNhietDo.getText() + "&off=" + jTextField_TatNhietDo.getText());
                try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {

                    String line;

                    String sb = new String();

                    while ((line = br.readLine()) != null) {

                        sb = sb + line;                //sb=sb+System.lineSeparator();
                    }
                    System.out.println(url);

                } catch (IOException ex) {
                    Logger.getLogger(FromDangNhap.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(FromGiaoDien.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else {
            try {
                URL url = new URL("https://doandieukhien.000webhostapp.com/t1h0.php?t1h0=0&on=" + jTextField_BatDoAm.getText() + "&off=" + jTextField_TatDoAm.getText());
                try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {

                    String line;

                    String sb = new String();

                    while ((line = br.readLine()) != null) {

                        sb = sb + line;                //sb=sb+System.lineSeparator();
                    }
                    System.out.println(url);

                } catch (IOException ex) {
                    Logger.getLogger(FromDangNhap.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (MalformedURLException ex) {
                Logger.getLogger(FromGiaoDien.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton_NutSaveNhietDoActionPerformed

    private void jRadioButton_NhietDoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_NhietDoActionPerformed
        // TODO add your handling code here:
        // create ButtonGroup for radBoy and radGirl
        if (jRadioButton_NhietDo.isSelected())
            jRadioButton_DoAm.setSelected(false);
    }//GEN-LAST:event_jRadioButton_NhietDoActionPerformed

    private void jSlider_TatCuaDoAmStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider_TatCuaDoAmStateChanged
        // TODO add your handling code here:
        jTextField_TatDoAm.setText(Float.toString((float)(jSlider_TatCuaDoAm.getValue()/10.0)));
    }//GEN-LAST:event_jSlider_TatCuaDoAmStateChanged

    private void jSlider_BatCuaDoAmStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider_BatCuaDoAmStateChanged
        // TODO add your handling code here:
        jTextField_BatDoAm.setText(Float.toString((float)(jSlider_BatCuaDoAm.getValue()/10.0)));
    }//GEN-LAST:event_jSlider_BatCuaDoAmStateChanged

    private void jSlider_TatCuaNhietDoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider_TatCuaNhietDoStateChanged
        // TODO add your handling code here:
        jTextField_TatNhietDo.setText(Float.toString((float) (jSlider_TatCuaNhietDo.getValue()/10.0)));
    }//GEN-LAST:event_jSlider_TatCuaNhietDoStateChanged

    private void jSlider_BatCuaNhietDoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider_BatCuaNhietDoStateChanged
        // TODO add your handling code here:
        jTextField_BatNhietDo.setText(Float.toString((float) (jSlider_BatCuaNhietDo.getValue()/10.0)));
    }//GEN-LAST:event_jSlider_BatCuaNhietDoStateChanged

    private void jRadioButton_DoAmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_DoAmActionPerformed
        // TODO add your handling code here:
        if (jRadioButton_DoAm.isSelected())
            jRadioButton_NhietDo.setSelected(false);
    }//GEN-LAST:event_jRadioButton_DoAmActionPerformed

    private void jPanel_1GiaoDienHomeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPanel_1GiaoDienHomeFocusGained
        // TODO add your handling code here:
            
    }//GEN-LAST:event_jPanel_1GiaoDienHomeFocusGained

    private void jLabel_dangXuat1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel_dangXuat1MouseClicked
        // TODO add your handling code here:
        FromDangNhap fd = new FromDangNhap();
        fd.setVisible(true);
        fd.setVisible(true);
        fd.pack();
        fd.setLocationRelativeTo(null);
        fd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_jLabel_dangXuat1MouseClicked

    private void lb_updatetimeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb_updatetimeMouseClicked
        // TODO add your handling code here:
        getnow();
    }//GEN-LAST:event_lb_updatetimeMouseClicked

    private void jPanel_2GiaoDienFanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_2GiaoDienFanMouseEntered
        // TODO add your handling code here:
        focus_setting=true;
        System.err.println( "Entered Setting" );
    }//GEN-LAST:event_jPanel_2GiaoDienFanMouseEntered

    private void jPanel_2GiaoDienFanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_2GiaoDienFanMouseExited
        // TODO add your handling code here:
        focus_setting=false;
        System.err.println( "Exited Setting" );
    }//GEN-LAST:event_jPanel_2GiaoDienFanMouseExited

    private void jPasswordField_MatKhauMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordField_MatKhauMoiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordField_MatKhauMoiActionPerformed

    private void jTextField_UsernameMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_UsernameMoiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_UsernameMoiActionPerformed

    private void jButton_NutBatTatThietBiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_NutBatTatThietBiActionPerformed
        try {
            // TODO add your handling code here:
            URL url_key;
            stt=(!stt);
            url_key = new URL("http://doandieukhien.000webhostapp.com/onofff.php?stt="+(stt?1:0)+"&id="+FGDU.ID);
            BufferedReader br = new BufferedReader(new InputStreamReader(url_key.openStream()));
            String line;
            String sb = new String();
            while ((line = br.readLine()) != null) {
                sb=sb+line;                //sb=sb+System.lineSeparator();
            }
            System.out.println(sb);
        } catch (MalformedURLException ex) {
            Logger.getLogger(FromGiaoDien.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FromGiaoDien.class.getName()).log(Level.SEVERE, null, ex);
        }
        getnow();
        if (stt == true) 
            jButton_NutBatTatThietBi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appthoitiet/on.png")));
        else 
            jButton_NutBatTatThietBi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appthoitiet/off.png")));
        if (auto == true)
            jButton_NutBatTatTuDong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appthoitiet/mode.png")));
        else 
            jButton_NutBatTatTuDong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appthoitiet/mode1.png")));
    }//GEN-LAST:event_jButton_NutBatTatThietBiActionPerformed

    private void jButton_NutBatTatTuDongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_NutBatTatTuDongActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            URL url_key;
            auto=(!auto);
            url_key = new URL("http://doandieukhien.000webhostapp.com/onofff.php?auto="+ (auto?1:0));
            BufferedReader br = new BufferedReader(new InputStreamReader(url_key.openStream()));
            String line;
            String sb = new String();
            while ((line = br.readLine()) != null) {
                sb=sb+line;                //sb=sb+System.lineSeparator();
            }
            System.out.println(sb);
        } catch (MalformedURLException ex) {
            Logger.getLogger(FromGiaoDien.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FromGiaoDien.class.getName()).log(Level.SEVERE, null, ex);
        }
        getnow();
        if (auto == true)
            jButton_NutBatTatTuDong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appthoitiet/mode.png")));
        else 
            jButton_NutBatTatTuDong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/appthoitiet/mode1.png")));

    }//GEN-LAST:event_jButton_NutBatTatTuDongActionPerformed

    private void jTextField_BatNhietDoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_BatNhietDoKeyTyped
        // TODO add your handling code here:
        jSlider_BatCuaNhietDo.setValue((int)(10.0*Float.valueOf(jTextField_BatNhietDo.getText())));
    }//GEN-LAST:event_jTextField_BatNhietDoKeyTyped

    private void jTextField_TatNhietDoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_TatNhietDoKeyTyped
        // TODO add your handling code here:ư
        jSlider_TatCuaNhietDo.setValue((int)(10.0*Float.valueOf(jTextField_TatNhietDo.getText())));
    }//GEN-LAST:event_jTextField_TatNhietDoKeyTyped

    private void jTextField_BatDoAmKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_BatDoAmKeyTyped
        // TODO add your handling code here:
        jSlider_BatCuaDoAm.setValue((int)(10.0*Float.valueOf(jTextField_BatDoAm.getText())));
    }//GEN-LAST:event_jTextField_BatDoAmKeyTyped

    private void jTextField_TatDoAmKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_TatDoAmKeyTyped
        // TODO add your handling code here:
        jSlider_TatCuaDoAm.setValue((int)(10.0*Float.valueOf(jTextField_TatDoAm.getText())));
    }//GEN-LAST:event_jTextField_TatDoAmKeyTyped

    public class ThayDoiCode {

        final private String NewCode;

        ThayDoiCode(String NewCoder) {

            NewCode = NewCoder;
        }

        ThayDoiCode() {
            NewCode = "";
        }

        public URL get_URLThayDoiCode() throws MalformedURLException {
            String link = "http://doandieukhien.000webhostapp.com/code.php?field=set&newcode=";
            link = link + NewCode;
            URL url = new URL(link);
            return url;
        }

        public void show_URLThayDoiCode() throws MalformedURLException {
            URL url;
            url = get_URLThayDoiCode();
            System.out.println(url.toString());
        }
    }

    public boolean KiemTraThayDoiCode() throws MalformedURLException, IOException {
        ThayDoiCode us = new ThayDoiCode(jPasswordField_ĐoiMaDangNhap.getText());
        us.show_URLThayDoiCode();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(us.get_URLThayDoiCode().openStream()))) {

            String line;

            String sb = new String();

            while ((line = br.readLine()) != null) {

                sb = sb + line;                //sb=sb+System.lineSeparator();
            }
            System.out.println(sb);
            System.out.println(sb.startsWith("1")); //sử dụng .startsWith của String để kiểm tra 
            if (sb.startsWith("1")) {
                return true;
            }

        } catch (IOException ex) {
            Logger.getLogger(FromDangNhap.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public class User_new {

        final private String UN;
        final private String PN;
        final private String TM;

        User_new(String UsernameNew, String PasswordNew, String TenNguoiDungMoi) {
            UN = UsernameNew;
            PN = PasswordNew;
            TM = TenNguoiDungMoi;
        }

        User_new() {
            UN = "";
            PN = "";
            TM = "";
        }

        public URL get_URL() throws MalformedURLException {
            String link = "https://doandieukhien.000webhostapp.com/user.php?field=add";
            link = link + "&id=" + FGDU.ID + "&pw=" + FGDU.PW + "&newid=" + UN + "&newpw=" + PN + "&newname=" + TM;
            URL url = new URL(link);
            return url;
        }
        public void show_URL() throws MalformedURLException {
            URL url;
            url = get_URL();
            System.out.println(url.toString());
        }
    }

    public boolean kiemTraThemNguoiDung() throws MalformedURLException, IOException {
        User_new us = new User_new(jTextField_UsernameMoi.getText(), String.valueOf(jPasswordField_MatKhauMoi.getPassword()), jTextField_TênNguoiDungMoi.getText());
        us.show_URL();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(us.get_URL().openStream()))) {

            String line;

            String sb = new String();

            while ((line = br.readLine()) != null) {

                sb = sb + line;                //sb=sb+System.lineSeparator();
            }
            System.out.println(sb);
            System.out.println(sb.startsWith("1")); //sử dụng .startsWith của String để kiểm tra 
            if (sb.startsWith("1")) {
                return true;
            }

        } catch (IOException ex) {
            Logger.getLogger(FromDangNhap.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        User thisUser = new User();
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FromGiaoDien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FromGiaoDien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FromGiaoDien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FromGiaoDien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FromGiaoDien(thisUser).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel RightPanel;
    private javax.swing.JButton jButton1_settings;
    private javax.swing.JButton jButton2_Fan;
    private javax.swing.JButton jButton3_Home;
    private javax.swing.JButton jButton4_exit;
    private javax.swing.JButton jButton5_admin;
    private javax.swing.JButton jButton_BieuDo;
    private javax.swing.JButton jButton_NutBatTatThietBi;
    private javax.swing.JButton jButton_NutBatTatTuDong;
    private javax.swing.JButton jButton_NutSaveDoiMaDangNhap;
    private javax.swing.JButton jButton_NutSaveDoiMatKhau;
    private javax.swing.JButton jButton_NutSaveNhietDo;
    private javax.swing.JButton jButton_NutSaveThemNguoiDung;
    private javax.swing.JButton jButton_ThemNguoiDung;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel1_FormLogin;
    private javax.swing.JLabel jLabel1_FormLogin1;
    private javax.swing.JLabel jLabel1_Min;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_HienThiBieuDo;
    private javax.swing.JLabel jLabel_HienThiDoAm;
    private javax.swing.JLabel jLabel_NAME;
    private javax.swing.JLabel jLabel_NAME1;
    private javax.swing.JLabel jLabel_Reload;
    private javax.swing.JLabel jLabel_ThietBi;
    private javax.swing.JLabel jLabel_dangXuat1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JPanel jPane_ThemNguoiDung;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel_1GiaoDienHome;
    private javax.swing.JPanel jPanel_2GiaoDienFan;
    private javax.swing.JPanel jPanel_BieuDo;
    private javax.swing.JPanel jPanel_GiaoDienChinh;
    private javax.swing.JPanel jPanel_GiaoDienSetting;
    private javax.swing.JPanel jPanel_fromTieuDe;
    private javax.swing.JPasswordField jPasswordField_MaKhauMoi;
    private javax.swing.JPasswordField jPasswordField_MatKhauCu;
    private javax.swing.JPasswordField jPasswordField_MatKhauMoi;
    private javax.swing.JPasswordField jPasswordField_XacNhanMoi;
    private javax.swing.JPasswordField jPasswordField_ĐoiMaDangNhap;
    private javax.swing.JRadioButton jRadioButton_DoAm;
    private javax.swing.JRadioButton jRadioButton_NhietDo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSlider jSlider_BatCuaDoAm;
    private javax.swing.JSlider jSlider_BatCuaNhietDo;
    private javax.swing.JSlider jSlider_TatCuaDoAm;
    private javax.swing.JSlider jSlider_TatCuaNhietDo;
    private javax.swing.JTextField jTextField_BatDoAm;
    private javax.swing.JTextField jTextField_BatNhietDo;
    private javax.swing.JTextField jTextField_TatDoAm;
    private javax.swing.JTextField jTextField_TatNhietDo;
    private javax.swing.JTextField jTextField_TênNguoiDungMoi;
    private javax.swing.JTextField jTextField_UsernameMoi;
    private javax.swing.JLabel lb_doam;
    private javax.swing.JLabel lb_nhietdo;
    private javax.swing.JLabel lb_updatetime;
    // End of variables declaration//GEN-END:variables
}
