
package model;


public class Payment {
    
    private String stu_no;
    private String tea_no;
    private int class_no;
    private String month;
    private double amount;

    /**
     * @return the stu_no
     */
    public String getStu_no() {
        return stu_no;
    }

    /**
     * @param stu_no the stu_no to set
     */
    public void setStu_no(String stu_no) {
        this.stu_no = stu_no;
    }

    /**
     * @return the tea_no
     */
    public String getTea_no() {
        return tea_no;
    }

    /**
     * @param tea_no the tea_no to set
     */
    public void setTea_no(String tea_no) {
        this.tea_no = tea_no;
    }

    /**
     * @return the class_no
     */
    public int getClass_no() {
        return class_no;
    }

    /**
     * @param class_no the class_no to set
     */
    public void setClass_no(int class_no) {
        this.class_no = class_no;
    }

    /**
     * @return the month
     */
    public String getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }



}
