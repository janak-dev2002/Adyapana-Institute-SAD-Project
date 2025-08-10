/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import com.formdev.flatlaf.IntelliJTheme;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.ResultSet;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.MySQL;
import model.Student_Attend;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author 94762
 */
public class Student_Attendence extends javax.swing.JFrame {

    private static HashMap<Integer, String> dayMap = new HashMap<>();
    private static HashMap<String, Integer> currentClassMap = new HashMap<>();
    public static Vector<Student_Attend> stuAtend_vec = new Vector<>();

    /**
     * Creates new form Student_Attendence
     */
    public Student_Attendence() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        loadClasses();
        loadDays();
        loadAllClasses();
        loadAllClassesInCombo();

    }

    private void loadDays() {

        try {
            ResultSet rs = MySQL.execute("SELECT * FROM `day`");

            while (rs.next()) {
                dayMap.put(rs.getInt("id"), rs.getString("day_name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadClasses() {

        LocalDate currentDate = LocalDate.now();
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();

        String todayDay = String.valueOf(dayOfWeek);

        try {

            ResultSet rs = MySQL.execute("SELECT * FROM `class` INNER JOIN `timelots` ON `class`.`timeLots_id` = `timelots`.`id`");

            Vector vec = new Vector();
            vec.add("Select Class");

            while (rs.next()) {

                ResultSet rs_days = MySQL.execute("SELECT * FROM `day` WHERE `id` = '" + rs.getInt("timelots.day_id") + "'");
                if (rs_days.next()) {

                    if (todayDay.equals(rs_days.getString("day_name"))) {
                        vec.add(rs.getString("class_name"));
                        currentClassMap.put(rs.getString("class_name"), rs.getInt("classNo"));

                    }
                }
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vec);
            classCom.setModel(model);

            DefaultComboBoxModel model1 = new DefaultComboBoxModel(vec);
            classCom_sort.setModel(model1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAllClassesInCombo() {

        try {

            ResultSet rs = MySQL.execute("SELECT * FROM `class`");

            Vector vec = new Vector();
            vec.add("Select Class");

            while (rs.next()) {

                vec.add(rs.getString("class_name"));

            }

            DefaultComboBoxModel model1 = new DefaultComboBoxModel(vec);
            classCom_sort.setModel(model1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAllClasses() {

        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);

        try {

            ResultSet rs = MySQL.execute("SELECT * FROM `class` INNER JOIN `subjects` ON `class`.`subjects_Subno` = `subjects`.`Subno` "
                    + "INNER JOIN `timelots` ON `class`.`timeLots_id` = `timelots`.`id` INNER JOIN `class_type` ON `class`.`class_type_id` = `class_type`.`id`"
                    + "INNER JOIN `teachers` ON `class`.`teachers_Tno` = `teachers`.`Tno`");

            while (rs.next()) {

                Vector<String> vec = new Vector<>();
                vec.add(rs.getString("class_name"));
                vec.add(rs.getString("teachers.teFname") + " " + rs.getString("teachers.teLname"));
                vec.add(rs.getString("teachers.teMobile"));
                vec.add(rs.getString("subjects.name"));
                vec.add(dayMap.get(rs.getInt("timelots.day_id")) + "--" + rs.getTime("time"));
                vec.add(rs.getString("class_type.type"));

                model.addRow(vec);
                jTable2.setModel(model);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadAttendence() {

        int Class_Id = currentClassMap.get(String.valueOf(classCom.getSelectedItem()));

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        try {

            ResultSet rs = MySQL.execute("SELECT * FROM `attendence_record` INNER JOIN `st_attendance` ON `attendence_record`.`id` = `st_attendance`.`attendence_record_id`"
                    + "INNER JOIN `class` ON `attendence_record`.`class_classNo` = `class`.`classNo` INNER JOIN `subjects` ON `class`.`subjects_Subno` = `subjects`.`Subno`"
                    + "INNER JOIN `timelots` ON `class`.`timeLots_id` = `timelots`.`id` INNER JOIN `day` ON `timelots`.`day_id` = `day`.`id` "
                    + "INNER JOIN `teachers` ON `class`.`teachers_Tno` = `teachers`.`Tno` INNER JOIN `students` ON `st_attendance`.`students_Sno` = `students`.`Sno`"
                    + "WHERE `attendence_record`.`class_classNo` = '" + Class_Id + "'");

            while (rs.next()) {

                Vector<String> vec = new Vector<>();
                vec.add(rs.getString("class.class_name"));
                vec.add(rs.getString("subjects.name"));
                vec.add(rs.getString("day.day_name") + "--" + rs.getString("timelots.time"));
                vec.add(rs.getString("teachers.teFname") + " " + rs.getString("teachers.teLname"));
                vec.add(rs.getString("students.Sno"));
                vec.add(rs.getString("students.stFname") + " " + rs.getString("students.stLname"));
                vec.add(rs.getString("date"));
                vec.add(rs.getString("st_attendance.status"));

                model.addRow(vec);
                jTable1.setModel(model);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        stNic_txt = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        classCom = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        stNo_txt = new javax.swing.JTextField();
        attendBtn = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        stMobLbl = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        stGenLbl = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        stEmailLbl = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        stNameLbl = new javax.swing.JLabel();
        jCalendar1 = new com.toedter.calendar.JCalendar();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        antTxt = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        classCom_sort = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        clrBtn = new javax.swing.JButton();
        printBtn = new javax.swing.JButton();
        findBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Student Attandance");

        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 153, 153)));

        jLabel2.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        jLabel2.setText("Student NIC");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Class Name", "Subject", "TimeLot", "Teacher", "ST_No", "ST_Name", "Date", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel4.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        jLabel4.setText("Class Name");

        classCom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        classCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                classComActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        jLabel1.setText("Student No");

        attendBtn.setText("Attend");
        attendBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attendBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(classCom, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(stNo_txt)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(stNic_txt)
                        .addGap(18, 18, 18)
                        .addComponent(attendBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(stNic_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(classCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(stNo_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(attendBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 153, 153)));

        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assetes/img/nopreview.png"))); // NOI18N
        jPanel6.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, -1, -1));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assetes/img/imagframe1.png"))); // NOI18N
        jPanel6.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, -1, -1));

        jPanel7.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 153, 153)));

        jLabel12.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        jLabel12.setText("Mobile");

        stMobLbl.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        stMobLbl.setText("---");

        jLabel14.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        jLabel14.setText("Gender");

        stGenLbl.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        stGenLbl.setText("---");

        jLabel16.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        jLabel16.setText("Email");

        stEmailLbl.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        stEmailLbl.setText("----");

        jLabel18.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        jLabel18.setText("Name");

        stNameLbl.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        stNameLbl.setText("---");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel18)
                    .addComponent(jLabel12)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(stEmailLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(stMobLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(stNameLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(stGenLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(stNameLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(stGenLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(stMobLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(stEmailLbl))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(25, 25, 25))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCalendar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCalendar1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 153, 153)));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Class Name", "Teacher", "Mobile", "Subject", "TimeLot", "Class Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jLabel3.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        jLabel3.setText("Type any things related to this table");

        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 0, new java.awt.Color(153, 153, 153)));

        jLabel5.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        jLabel5.setText("Sorting Options For Attendence Table");

        jLabel6.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        jLabel6.setText("Class Name");

        classCom_sort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel10.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        jLabel10.setText("Date");

        jPanel3.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        clrBtn.setText("CLEAR");
        jPanel3.add(clrBtn);

        printBtn.setText("PRINT");
        printBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printBtnActionPerformed(evt);
            }
        });
        jPanel3.add(printBtn);

        findBtn.setText("FIND");
        findBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(8, 8, 8)
                        .addComponent(classCom_sort, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(53, 53, 53)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(findBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(classCom_sort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(findBtn))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(146, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(antTxt)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(antTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void classComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_classComActionPerformed
        // TODO add your handling code here:

        loadAttendence();
    }//GEN-LAST:event_classComActionPerformed

    private void attendBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attendBtnActionPerformed
        // TODO add your handling code here:

        if (stNo_txt.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Inavlid Student Number", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (classCom.getSelectedItem().equals("Select Class")) {
            JOptionPane.showMessageDialog(this, "Select a class", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            String Stu_No = stNo_txt.getText();
            int Class_Id = currentClassMap.get(String.valueOf(classCom.getSelectedItem()));

            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
            String todayDate = format.format(date);
            String currentTime = formatTime.format(date);

            // System.out.println(currentTime);
            try {

                ResultSet st_rs = MySQL.execute("SELECT * FROM `student_enrolment_class` WHERE `students_Sno` = '" + Stu_No + "' AND `class_classNo` = '" + Class_Id + "'");

                if (st_rs.next()) {
                    ResultSet rs = MySQL.execute("SELECT * FROM `attendence_record` WHERE `date` = '" + todayDate + "' AND `class_classNo` = '" + Class_Id + "'");

                    if (rs.next()) {

                        MySQL.execute("INSERT INTO `st_attendance` (`students_Sno`,`marked_time`,`attendence_record_id`,`status`)"
                                + "VALUES ('" + Stu_No + "','" + currentTime + "','" + rs.getInt("id") + "','1')");

                        loadAttendence();

                    } else {

                        MySQL.execute("INSERT INTO `attendence_record` (`date`,`class_classNo`) VALUES ('" + todayDate + "','" + Class_Id + "'");
                        ResultSet rs1 = MySQL.execute("SELECT * FROM `attendence_record` WHERE `date` = '" + todayDate + "' AND `class_classNo` = '" + Class_Id + "'");

                        MySQL.execute("INSERT INTO `st_attendance` (`students_Sno`,`marked_time`,`attendence_record_id`,`status`)"
                                + "VALUES ('" + Stu_No + "','" + currentTime + "','" + rs1.getInt("id") + "','1')");

                        loadAttendence();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "This student not at this class", "Warning", JOptionPane.WARNING_MESSAGE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }//GEN-LAST:event_attendBtnActionPerformed

    private void findBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findBtnActionPerformed
        // TODO add your handling code here:

        Date date = jDateChooser1.getDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        if (classCom_sort.getSelectedItem().equals("Select Class")) {
            JOptionPane.showMessageDialog(this, "Select a class", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (date == null) {
            JOptionPane.showMessageDialog(this, "Select a Date", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            //  int Class_Id = currentClassMap.get(String.valueOf(classCom_sort.getSelectedItem()));
            String WhichDate = format.format(date);

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            try {

                ResultSet rs = MySQL.execute("SELECT * FROM `attendence_record` INNER JOIN `st_attendance` ON `attendence_record`.`id` = `st_attendance`.`attendence_record_id`"
                        + "INNER JOIN `class` ON `attendence_record`.`class_classNo` = `class`.`classNo` INNER JOIN `subjects` ON `class`.`subjects_Subno` = `subjects`.`Subno`"
                        + "INNER JOIN `timelots` ON `class`.`timeLots_id` = `timelots`.`id` INNER JOIN `day` ON `timelots`.`day_id` = `day`.`id` "
                        + "INNER JOIN `teachers` ON `class`.`teachers_Tno` = `teachers`.`Tno` INNER JOIN `students` ON `st_attendance`.`students_Sno` = `students`.`Sno`"
                        + "WHERE `class`.`class_name` = '" + String.valueOf(classCom_sort.getSelectedItem()) + "' AND `attendence_record`.`date` = '" + WhichDate + "'");

                while (rs.next()) {

                    Vector<String> vec = new Vector<>();
                    vec.add(rs.getString("class.class_name"));
                    vec.add(rs.getString("subjects.name"));
                    vec.add(rs.getString("day.day_name") + "--" + rs.getString("timelots.time"));
                    vec.add(rs.getString("teachers.teFname") + " " + rs.getString("teachers.teLname"));
                    vec.add(rs.getString("students.Sno"));
                    vec.add(rs.getString("students.stFname") + " " + rs.getString("students.stLname"));
                    vec.add(rs.getString("date"));
                    vec.add(rs.getString("st_attendance.status"));

                    model.addRow(vec);
                    jTable1.setModel(model);

                    Student_Attend stu_A = new Student_Attend();
                    stu_A.setStnu(rs.getString("students.Sno"));
                    stu_A.setStname(rs.getString("students.stFname") + " " + rs.getString("students.stLname"));
                    stu_A.setMobile(rs.getString("students.stMobile"));
                    stu_A.setEmail(rs.getString("students.stEmail"));

                    stuAtend_vec.add(stu_A);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }//GEN-LAST:event_findBtnActionPerformed

    private void printBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printBtnActionPerformed
        // TODO add your handling code here:
        
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {

            ResultSet rs = MySQL.execute("SELECT * FROM `class` INNER JOIN `teachers` ON `class`.`teachers_Tno` = `teachers`.`Tno`"
                    + "WHERE `class_name` = '" + classCom_sort.getSelectedItem() + "'");

            if (rs.next()) {

                HashMap<String, Object> parameters = new HashMap<>();
                parameters.put("Parameter1", rs.getString("class_name"));
                parameters.put("Parameter2", rs.getString("teachers.teFname")+" "+rs.getString("teachers.teLname"));
                parameters.put("Parameter3", format.format(date));

                JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(stuAtend_vec);
                JasperPrint report = JasperFillManager.fillReport("src/reports/Stu_Attendance.jasper", parameters, datasource);
                JasperViewer.viewReport(report, false);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_printBtnActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//
//        /* Set the Theam look and feel */
//        IntelliJTheme.setup(Student_Registration.class.getResourceAsStream(
//                "/themes/one_dark.theme.json"));
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Student_Attendence().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField antTxt;
    private javax.swing.JButton attendBtn;
    private javax.swing.JComboBox<String> classCom;
    private javax.swing.JComboBox<String> classCom_sort;
    private javax.swing.JButton clrBtn;
    private javax.swing.JButton findBtn;
    private com.toedter.calendar.JCalendar jCalendar1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JButton printBtn;
    private javax.swing.JLabel stEmailLbl;
    private javax.swing.JLabel stGenLbl;
    private javax.swing.JLabel stMobLbl;
    private javax.swing.JLabel stNameLbl;
    private javax.swing.JTextField stNic_txt;
    private javax.swing.JTextField stNo_txt;
    // End of variables declaration//GEN-END:variables
}
