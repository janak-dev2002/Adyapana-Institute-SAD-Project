/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import com.formdev.flatlaf.IntelliJTheme;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Invoice;
import model.MySQL;
import model.Payment;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author 94762
 */
public class Student_payments extends javax.swing.JFrame {

    private static HashMap<String, Integer> subMap = new HashMap<>();
    private static HashMap<String, Integer> classMap = new HashMap<>();
    public static Vector<Payment> paymentVector = new Vector<>();
    public static Vector<Invoice> invoiceVector = new Vector<>();
    private int count;
    private int itmNum;
    private double total;

    /**
     * Creates new form Student_payments
     */
    public Student_payments() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        loadSubjects();
        loadMonths();
        loadAllPaymentHistory();
    }

    private void loadSubjects() {

        try {
            ResultSet rs = MySQL.execute("SELECT * FROM `subjects`");

            Vector subjectVector = new Vector();
            subjectVector.add("Select Subject");

            while (rs.next()) {
                subjectVector.add(rs.getString("name"));
                subMap.put(rs.getString("name"), rs.getInt("Subno"));

            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(subjectVector);
            subCom_sort.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTeachers(int subId) {

        try {

            ResultSet rs = MySQL.execute("SELECT * FROM `teachers` "
                    + "INNER JOIN `subjects` ON `teachers`.`subjects_Subno` = `subjects`.`Subno` WHERE `subjects_Subno` = '" + subId + "'");

            Vector<String> vec = new Vector<>();
            vec.add("Select Teacher");

            while (rs.next()) {
                vec.add(rs.getString("Tno"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vec);
            teaComsort.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadClasses(String te_ID) {

        try {

            ResultSet rs = MySQL.execute("SELECT * FROM `class` "
                    + "WHERE `teachers_Tno` = '" + te_ID + "'");

            Vector<String> vec = new Vector<>();
            vec.add("Select Class");

            while (rs.next()) {
                vec.add(rs.getString("class_name"));

            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vec);
            classCom_sort.setModel(model);

//            DefaultComboBoxModel modelSt = new DefaultComboBoxModel(vec);
//            st_class_com.setModel(modelSt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadStudentClasses(String stu_No) {

        try {

            ResultSet rs = MySQL.execute("SELECT * FROM `student_enrolment_class` "
                    + "INNER JOIN `class` ON `student_enrolment_class`.`class_classNo` = `class`.`classNo` WHERE `students_Sno` = '" + stu_No + "'");

            Vector<String> vec = new Vector<>();
            vec.add("Select Teacher");

            while (rs.next()) {
                vec.add(rs.getString("class.class_name"));
                classMap.put(rs.getString("class_name"), rs.getInt("classNo"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vec);
            stu_classCom.setModel(model);

            DefaultComboBoxModel model1 = new DefaultComboBoxModel(vec);
            upd_classCom.setModel(model1);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadMonths() {

        Vector<String> vec = new Vector<>();
        vec.add("JANUARY");
        vec.add("FEBRUARY");
        vec.add("MARCH");
        vec.add("APRIL");
        vec.add("MAY");
        vec.add("JUNE");
        vec.add("JULY");
        vec.add("AUGUST");
        vec.add("SEPTEMBER");
        vec.add("OCTOBER");
        vec.add("NOVEMBER");
        vec.add("DECEMBER");

        DefaultComboBoxModel model1 = new DefaultComboBoxModel(vec);
        upd_monthCom.setModel(model1);

    }

    private void loadPaymentHistory(String Stu_No) {

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        try {

            ResultSet rs = MySQL.execute("SELECT * FROM `invoice` INNER JOIN `teachers` ON `invoice`.`teachers_Tno` = `teachers`.`Tno`"
                    + "INNER JOIN `status` ON `invoice`.`status_id` = `status`.`id`"
                    + "INNER JOIN `class` ON `invoice`.`class_classNo` = `class`.`classNo` WHERE `students_Sno` = '" + Stu_No + "'");

            while (rs.next()) {

                Vector<String> vec = new Vector<>();
                vec.add(rs.getString("invoice.id"));
                vec.add(rs.getString("teachers.teFname") + " " + rs.getString("teachers.teLname"));
                vec.add(rs.getString("class.class_name"));
                vec.add(rs.getString("class.fee"));
                vec.add(rs.getString("month"));
                vec.add(rs.getString("paid_date"));
                vec.add(rs.getString("status.type"));

                model.addRow(vec);
                jTable1.setModel(model);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadAllPaymentHistory() {

        DefaultTableModel model = (DefaultTableModel) jTable4.getModel();
        model.setRowCount(0);

        try {

            ResultSet rs = MySQL.execute("SELECT * FROM `invoice` INNER JOIN `teachers` ON `invoice`.`teachers_Tno` = `teachers`.`Tno`"
                    + "INNER JOIN `status` ON `invoice`.`status_id` = `status`.`id`"
                    + "INNER JOIN `class` ON `invoice`.`class_classNo` = `class`.`classNo`"
                    + "INNER JOIN `students` ON `invoice`.`students_Sno` = `students`.`Sno`"
                    + "INNER JOIN `subjects` ON `class`.`subjects_Subno` = `subjects`.`Subno`");

            while (rs.next()) {

                Vector<String> vec = new Vector<>();
                vec.add(rs.getString("students.stFname") + " " + rs.getString("students.stLname"));
                vec.add(rs.getString("students.stMobile"));
                vec.add(rs.getString("teachers.teFname") + " " + rs.getString("teachers.teLname"));
                vec.add(rs.getString("subjects.name"));
                vec.add(rs.getString("class.class_name"));
                vec.add(rs.getString("invoice.month"));
                vec.add(rs.getString("class.fee"));
                vec.add(rs.getString("invoice.paid_date"));
                vec.add(rs.getString("status.type"));

                model.addRow(vec);
                jTable4.setModel(model);

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
        jPanel2 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        stu_classCom = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        stu_statusCom = new javax.swing.JComboBox<>();
        stuClrBtn = new javax.swing.JButton();
        stu_prntBtn = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        stu_teaCom = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jCalendar1 = new com.toedter.calendar.JCalendar();
        jPanel6 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        updBtn = new javax.swing.JButton();
        updStatusCom = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        upd_monthCom = new javax.swing.JComboBox<>();
        upd_classCom = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        inIdLBL = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        stuNo_txt = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel10 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        totalTxt = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        discounTxt = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        recevTxt = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        savTxt = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        payBtn = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        genInvoiceBtn = new javax.swing.JButton();
        payMonthCom = new javax.swing.JComboBox<>();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        stuFindBtn = new javax.swing.JButton();
        getTotalBtn = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        subCom_sort = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        teaComsort = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        classCom_sort = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        monthCom_sort = new javax.swing.JComboBox<>();
        jPanel14 = new javax.swing.JPanel();
        findBtn = new javax.swing.JButton();
        undoBtn = new javax.swing.JButton();
        printBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Students Payments");

        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 153, 153)));

        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(153, 153, 153)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assetes/img/nopreview.png"))); // NOI18N
        jPanel2.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 250, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assetes/img/imagframe1.png"))); // NOI18N
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, -1, -1));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assetes/img/nopreview.png"))); // NOI18N
        jPanel3.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assetes/img/imagframe1.png"))); // NOI18N
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, -1, -1));

        jLabel3.setFont(new java.awt.Font("Californian FB", 0, 18)); // NOI18N
        jLabel3.setText("Student");

        jLabel4.setFont(new java.awt.Font("Californian FB", 0, 18)); // NOI18N
        jLabel4.setText("Teacher");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Invoice-ID", "Teacher", "Class Name", "Fee", "Month", "Pay-Date", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel6.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        jLabel6.setText("Class Name");

        stu_classCom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        stu_classCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stu_classComActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        jLabel7.setText("Status");

        stu_statusCom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Paid", "Unpaid" }));

        stuClrBtn.setText("CLEAR");

        stu_prntBtn.setText("PRINT");

        jLabel8.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        jLabel8.setText("Teacher");

        stu_teaCom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(stu_classCom, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(stu_statusCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(stu_teaCom, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(stuClrBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(stu_prntBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(stu_classCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(stu_statusCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stuClrBtn)
                    .addComponent(stu_prntBtn)
                    .addComponent(jLabel8)
                    .addComponent(stu_teaCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 153, 153)));
        jPanel4.setLayout(new java.awt.GridLayout(1, 0));

        jPanel5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(153, 153, 153)));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCalendar1, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCalendar1, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.add(jPanel5);

        jLabel23.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        jLabel23.setText("Update Payments");

        jPanel15.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(153, 153, 153)));

        updBtn.setText("UPDATE");
        updBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updBtnActionPerformed(evt);
            }
        });

        updStatusCom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Paid", "Unpaid" }));

        jLabel26.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        jLabel26.setText("Status");

        jLabel25.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        jLabel25.setText("Month");

        upd_monthCom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        upd_classCom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel24.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        jLabel24.setText("Class Name");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26))
                .addGap(18, 18, 18)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(upd_classCom, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(upd_monthCom, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(updStatusCom, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(updBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(upd_classCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(upd_monthCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(updStatusCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updBtn))
                .addGap(12, 12, 12))
        );

        jLabel30.setFont(new java.awt.Font("Californian FB", 0, 14)); // NOI18N
        jLabel30.setText("Invoice ID  :");

        inIdLBL.setFont(new java.awt.Font("Californian FB", 0, 14)); // NOI18N
        inIdLBL.setText("  ");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inIdLBL, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel30)
                    .addComponent(inIdLBL))
                .addGap(18, 18, 18)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(86, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel6);

        jPanel7.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 153, 153)));

        jLabel5.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        jLabel5.setText("Student No");

        jLabel9.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        jLabel9.setText("Student NIC");

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane3.setViewportView(jTextArea1);

        jPanel10.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 1, new java.awt.Color(153, 153, 153)));

        jPanel9.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(153, 153, 153)));

        jLabel12.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        jLabel12.setText(" Other Payments");

        jLabel13.setFont(new java.awt.Font("Californian FB", 0, 14)); // NOI18N
        jLabel13.setText("Description");

        jLabel14.setFont(new java.awt.Font("Californian FB", 0, 14)); // NOI18N
        jLabel14.setText("Amount");

        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton3.setText("+");

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "No", "Description", "Amount"
            }
        ));
        jScrollPane4.setViewportView(jTable3);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel12)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel11.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        jLabel11.setText("Payment Process");

        jLabel15.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        jLabel15.setText("Total");

        totalTxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        jLabel16.setText("Discount");

        discounTxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        jLabel17.setText("Recived");

        recevTxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        jLabel18.setText("Save");

        savTxt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jPanel13.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        payBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        payBtn.setText("PAY");
        payBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payBtnActionPerformed(evt);
            }
        });
        jPanel13.add(payBtn);

        jButton6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton6.setText("TO EMAIL");
        jPanel13.add(jButton6);

        genInvoiceBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        genInvoiceBtn.setText("INVOICE");
        jPanel13.add(genInvoiceBtn);

        payMonthCom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER" }));

        jLabel29.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        jLabel29.setText("Month");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(30, 30, 30)
                                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(payMonthCom, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel17))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addComponent(recevTxt)
                                        .addGap(24, 24, 24)
                                        .addComponent(jLabel18)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(savTxt))
                                    .addComponent(discounTxt)
                                    .addComponent(totalTxt))))
                        .addContainerGap())))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(payMonthCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(totalTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(discounTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(recevTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(savTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "St-Name", "Te-No", "Teacher", "Subject", "Class Typpe", "Class Name", "Fee"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        stuFindBtn.setText("FIND");
        stuFindBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stuFindBtnActionPerformed(evt);
            }
        });

        getTotalBtn.setText("GET TOTAL");
        getTotalBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getTotalBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(stuNo_txt, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(stuFindBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(getTotalBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(stuNo_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stuFindBtn)
                    .addComponent(getTotalBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3)))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 153, 153)));

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Student", "St_Mobile", "Teacher", "Subject", "Class Name", "Month", "Fee", "Pay_Date", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(jTable4);

        jLabel19.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        jLabel19.setText("Subject");

        subCom_sort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        subCom_sort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subCom_sortActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        jLabel20.setText("Teacher");

        teaComsort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        teaComsort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                teaComsortActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        jLabel21.setText("Class");

        classCom_sort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));

        jLabel22.setFont(new java.awt.Font("Californian FB", 0, 16)); // NOI18N
        jLabel22.setText("Month");

        monthCom_sort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Month", "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER", " " }));

        jPanel14.setLayout(new java.awt.GridLayout(1, 0, 5, 0));

        findBtn.setText("FIND");
        findBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findBtnActionPerformed(evt);
            }
        });
        jPanel14.add(findBtn);

        undoBtn.setText("UNDO");
        undoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoBtnActionPerformed(evt);
            }
        });
        jPanel14.add(undoBtn);

        printBtn.setText("PRINT");
        jPanel14.add(printBtn);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(subCom_sort, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(teaComsort, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(classCom_sort, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(monthCom_sort, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel19)
                        .addComponent(subCom_sort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel20)
                        .addComponent(teaComsort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel21)
                        .addComponent(classCom_sort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel22)
                        .addComponent(monthCom_sort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void subCom_sortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subCom_sortActionPerformed
        // TODO add your handling code here:
        int subId = subCom_sort.getSelectedIndex();
        loadTeachers(subId);
    }//GEN-LAST:event_subCom_sortActionPerformed

    private void teaComsortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_teaComsortActionPerformed
        // TODO add your handling code here:

        String te_ID = String.valueOf(teaComsort.getSelectedItem());
        loadClasses(te_ID);
    }//GEN-LAST:event_teaComsortActionPerformed

    private void stuFindBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stuFindBtnActionPerformed
        // TODO add your handling code here:

        String stu_No = stuNo_txt.getText();
        loadStudentClasses(stu_No);
        loadPaymentHistory(stu_No);

        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);

        try {

            ResultSet rs = MySQL.execute("SELECT * FROM `student_enrolment_class` INNER JOIN `students` ON `student_enrolment_class`.`students_Sno` = `students`.`Sno`"
                    + "INNER JOIN `class` ON `student_enrolment_class`.`class_classNo` = `class`.`classNo`"
                    + "INNER JOIN `teachers` ON `class`.`teachers_Tno` = `teachers`.`Tno`"
                    + "INNER JOIN `subjects` ON `class`.`subjects_Subno` = `subjects`.`Subno`"
                    + "INNER JOIN `class_type` ON `class`.`class_type_id` = `class_type`.`id` WHERE `student_enrolment_class`.`students_Sno` = '" + stu_No + "'");

            while (rs.next()) {

                Vector<String> vec = new Vector<>();
                vec.add(rs.getString("stFname") + " " + rs.getString("stLname"));
                vec.add(rs.getString("teachers.Tno"));
                vec.add(rs.getString("teachers.teFname") + " " + rs.getString("teachers.teLname"));
                vec.add(rs.getString("subjects.name"));
                vec.add(rs.getString("class_type.type"));
                vec.add(rs.getString("class.class_name"));
                vec.add(rs.getString("class.fee"));

                model.addRow(vec);
                jTable2.setModel(model);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_stuFindBtnActionPerformed

    private void stu_classComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stu_classComActionPerformed
        // TODO add your handling code here:
        int Te_id = classMap.get(String.valueOf(stu_classCom.getSelectedItem()));
        loadTeachers(Te_id);
        System.out.println(Te_id);
    }//GEN-LAST:event_stu_classComActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:

        if (evt.getClickCount() == 2) {

            int slectedRow = jTable1.getSelectedRow();
            String InvoiceId = String.valueOf(jTable1.getValueAt(slectedRow, 0));
            inIdLBL.setText(InvoiceId);

        }

    }//GEN-LAST:event_jTable1MouseClicked

    private void updBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updBtnActionPerformed
        // TODO add your handling code here:

        int classNo = classMap.get(String.valueOf(upd_classCom.getSelectedItem()));

        try {

            MySQL.execute("UPDATE `invoice` SET `class_classNo` = '" + classNo + "', `month` = '" + upd_monthCom.getSelectedItem() + "', `status_id` = '" + updStatusCom.getSelectedIndex() + "'"
                    + "WHERE `id` = '" + inIdLBL.getText() + "'");
            JOptionPane.showMessageDialog(this, "Update Done", "Warning", JOptionPane.INFORMATION_MESSAGE);
            loadPaymentHistory(stuNo_txt.getText());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_updBtnActionPerformed

    private void invoiceGenarate() {

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String Billdate = format.format(date);

        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss a");
        String Billtime = formatTime.format(date);

        jTextArea1.setText("********************Adyapana Institute**********************\n"
                + "Time: " + Billtime + "                                 Date: " + Billdate + "\n"
                + "*************************************************************\n"
                + "Payment Name:\t\t" + "Amount(Rs)\n"
                + "\n");

    }

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        count++;

        if (count == 1) {
            invoiceGenarate();
        }

        if (evt.getClickCount() == 2) {
            
            itmNum++;

            int slectedRow = jTable2.getSelectedRow();

            String Selected_sub = String.valueOf(jTable2.getValueAt(slectedRow, 3));
            String Selected_Ctype = "(" + String.valueOf(jTable2.getValueAt(slectedRow, 4)) + ")";
            String Selected_fee = String.valueOf(jTable2.getValueAt(slectedRow, 6));

            total += Double.parseDouble(Selected_fee);

            jTextArea1.setText(jTextArea1.getText() + "* " + Selected_sub + " " + Selected_Ctype + "\t\t" + Selected_fee + "\n");

            Payment payment = new Payment();
            payment.setStu_no(stuNo_txt.getText());
//            payment.setTea_no(String.valueOf(jTable2.getValueAt(slectedRow, 1)));
//            payment.setClass_no(classMap.get(String.valueOf(jTable2.getValueAt(slectedRow, 5))));
//            payment.setAmount(Double.parseDouble(Selected_fee));
//            payment.setMonth(String.valueOf(payMonthCom.getSelectedItem()));

stuNo_txt.setText(payment.getStu_no());
            
            Invoice invoice = new Invoice();
            invoice.setX(String.valueOf(itmNum));
            invoice.setY(Selected_sub);
            invoice.setZ(Selected_Ctype);
            invoice.setP(Selected_fee);

            paymentVector.add(payment);
            invoiceVector.add(invoice);

        }

    }//GEN-LAST:event_jTable2MouseClicked

    private void genarateInvoicePrint() {

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format1 = new SimpleDateFormat("HH:mm:ss");
        
        String curentDate = format.format(date);
        String currentTime = format1.format(date);
        
        try {

            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("Parameter1", curentDate);
            parameters.put("Parameter2", currentTime);
            parameters.put("Parameter3", totalTxt.getText());

            JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(invoiceVector);

            JasperPrint report = JasperFillManager.fillReport("src/reports/feesInvoice.jasper", parameters, datasource);

            JasperViewer.viewReport(report, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getTotalBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getTotalBtnActionPerformed
        // TODO add your handling code here:

        if (total == 0.0) {
            JOptionPane.showMessageDialog(this, "Please Select a Payment", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            jTextArea1.setText(jTextArea1.getText() + "\n*************************************************************\n"
                    + "Sub Total: \t\t\t" + total + "\n"
                    + "*************************Thank You*************************");

            totalTxt.setText(String.valueOf(total));
        }


    }//GEN-LAST:event_getTotalBtnActionPerformed

    private void payBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payBtnActionPerformed
        // TODO add your handling code here:

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String Billdate = format.format(date);

        if (String.valueOf(payMonthCom.getSelectedItem()).equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please selecat the month", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (recevTxt.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please Enter Receive Payment", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            for (Payment payment : paymentVector) {

                try {

                    MySQL.execute("INSERT INTO `invoice` (`students_Sno`,`teachers_Tno`,`class_classNo`,`month`,`value`,`paid_date`,`status_id`)"
                            + "VALUES ('" + payment.getStu_no() + "','" + payment.getTea_no() + "','" + payment.getClass_no() + "','" + payment.getMonth() + "',"
                                    + "'" + payment.getAmount() + "','" + Billdate + "','1')");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            JOptionPane.showMessageDialog(this, "Done", "Warning", JOptionPane.INFORMATION_MESSAGE);

            double save = Double.parseDouble(recevTxt.getText()) - total;

            savTxt.setText(String.valueOf(save));
            loadPaymentHistory(stuNo_txt.getText());
            genarateInvoicePrint();

        }

    }//GEN-LAST:event_payBtnActionPerformed

    private void findBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findBtnActionPerformed
        // TODO add your handling code here:
        if (String.valueOf(classCom_sort.getSelectedItem()).equals("Select")) {
            JOptionPane.showMessageDialog(this, "Please a class", "Warning", JOptionPane.WARNING_MESSAGE);
        } else if (String.valueOf(monthCom_sort.getSelectedItem()).equals("Select Month")) {
            JOptionPane.showMessageDialog(this, "Please a montht", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {

            String ClassNAme = String.valueOf(classCom_sort.getSelectedItem());
            String Month = String.valueOf(monthCom_sort.getSelectedItem());

            DefaultTableModel model = (DefaultTableModel) jTable4.getModel();
            model.setRowCount(0);

            try {

                ResultSet rs = MySQL.execute("SELECT * FROM `invoice` INNER JOIN `teachers` ON `invoice`.`teachers_Tno` = `teachers`.`Tno`"
                        + "INNER JOIN `status` ON `invoice`.`status_id` = `status`.`id`"
                        + "INNER JOIN `class` ON `invoice`.`class_classNo` = `class`.`classNo`"
                        + "INNER JOIN `students` ON `invoice`.`students_Sno` = `students`.`Sno`"
                        + "INNER JOIN `subjects` ON `class`.`subjects_Subno` = `subjects`.`Subno` WHERE `class`.`class_name` = '" + ClassNAme + "' AND `invoice`.`month` = '" + Month + "'");

                while (rs.next()) {

                    Vector<String> vec = new Vector<>();
                    vec.add(rs.getString("students.stFname") + " " + rs.getString("students.stLname"));
                    vec.add(rs.getString("students.stMobile"));
                    vec.add(rs.getString("teachers.teFname") + " " + rs.getString("teachers.teLname"));
                    vec.add(rs.getString("subjects.name"));
                    vec.add(rs.getString("class.class_name"));
                    vec.add(rs.getString("invoice.month"));
                    vec.add(rs.getString("class.fee"));
                    vec.add(rs.getString("invoice.paid_date"));
                    vec.add(rs.getString("status.type"));

                    model.addRow(vec);
                    jTable4.setModel(model);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }//GEN-LAST:event_findBtnActionPerformed

    private void undoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoBtnActionPerformed
        // TODO add your handling code here:
        loadAllPaymentHistory();
    }//GEN-LAST:event_undoBtnActionPerformed

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
//                new Student_payments().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> classCom_sort;
    private javax.swing.JTextField discounTxt;
    private javax.swing.JButton findBtn;
    private javax.swing.JButton genInvoiceBtn;
    private javax.swing.JButton getTotalBtn;
    private javax.swing.JLabel inIdLBL;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private com.toedter.calendar.JCalendar jCalendar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JComboBox<String> monthCom_sort;
    private javax.swing.JButton payBtn;
    private javax.swing.JComboBox<String> payMonthCom;
    private javax.swing.JButton printBtn;
    private javax.swing.JTextField recevTxt;
    private javax.swing.JTextField savTxt;
    private javax.swing.JButton stuClrBtn;
    private javax.swing.JButton stuFindBtn;
    private javax.swing.JTextField stuNo_txt;
    private javax.swing.JComboBox<String> stu_classCom;
    private javax.swing.JButton stu_prntBtn;
    private javax.swing.JComboBox<String> stu_statusCom;
    private javax.swing.JComboBox<String> stu_teaCom;
    private javax.swing.JComboBox<String> subCom_sort;
    private javax.swing.JComboBox<String> teaComsort;
    private javax.swing.JTextField totalTxt;
    private javax.swing.JButton undoBtn;
    private javax.swing.JButton updBtn;
    private javax.swing.JComboBox<String> updStatusCom;
    private javax.swing.JComboBox<String> upd_classCom;
    private javax.swing.JComboBox<String> upd_monthCom;
    // End of variables declaration//GEN-END:variables
}
