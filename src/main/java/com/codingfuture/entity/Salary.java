package com.codingfuture.entity;

public class Salary {
    private String saId;
    private String empId;
    private String saDate;
    private double saBase;
    private double saPerformance;
    private double saInsurance;
    private double saActual;
    private int isDeleted;
    private String createTime;
    private String updateTime;
    private String empName;

    public Salary() {

    }

    public Salary(String saId, String saDate, double saBase, double saPerformance, double saInsurance, double saActual, String empName) {
        this.saId = saId;
        this.saDate = saDate;
        this.saBase = saBase;
        this.saPerformance = saPerformance;
        this.saInsurance = saInsurance;
        this.saActual = saActual;
        this.empName = empName;
    }

    public Salary(String saId, String empId, String saDate, double saBase, double saPerformance, double saInsurance, double saActual, int isDeleted, String createTime, String updateTime,String empName) {
        this.saId = saId;
        this.empId = empId;
        this.saDate = saDate;
        this.saBase = saBase;
        this.saPerformance = saPerformance;
        this.saInsurance = saInsurance;
        this.saActual = saActual;
        this.isDeleted = isDeleted;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.empName = empName;
    }

    public String getSaId() {
        return saId;
    }

    public void setSaId(String saId) {
        this.saId = saId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getSaDate() {
        return saDate;
    }

    public void setSaDate(String saDate) {
        this.saDate = saDate;
    }

    public double getSaBase() {
        return saBase;
    }

    public void setSaBase(double saBase) {
        if (saBase < 0) {
            this.saBase = -1;
        } else {
            this.saBase = saBase;
        }

    }

    public double getSaPerformance() {
        return saPerformance;
    }

    public void setSaPerformance(double saPerformance) {
        if (saPerformance < 0) {
            this.saPerformance = -1;
        } else {
            this.saPerformance = saPerformance;
        }

    }

    public double getSaInsurance() {
        return saInsurance;
    }

    public void setSaInsurance(double saInsurance) {
        if (saInsurance < 0) {
            this.saInsurance = -1;
        } else {
            this.saInsurance = saInsurance;
        }
    }

    public double getSaActual() {
        return saActual;
    }

    public void setSaActual(double saActual) {
        this.saActual = saActual;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "saId='" + saId + '\'' +
                ", empId='" + empId + '\'' +
                ", saDate='" + saDate + '\'' +
                ", saBase='" + saBase + '\'' +
                ", saPerformance='" + saPerformance + '\'' +
                ", saInsurance='" + saInsurance + '\'' +
                ", saActual='" + saActual + '\'' +
                ", isDeleted=" + isDeleted +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
