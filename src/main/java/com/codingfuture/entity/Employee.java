package com.codingfuture.entity;

public class Employee {
    private String empId;
    private String dptId;
    private String empName;
    private String empCode;
    private String empSex;
    private String dptName;
    private String createTime;
    private String updateTime;

    public Employee(){

    }

    public Employee(String empId,String empName, String empCode, String empSex, String dptName) {
        this.empId = empId;
        this.empName = empName;
        this.empCode = empCode;
        this.empSex = empSex;
        this.dptName = dptName;
    }

    public Employee(String empId,String empName, String dptName,String empSex) {
        this.empId = empId;
        this.empName = empName;
        this.dptName = dptName;
        this.empSex = empSex;
    }
    public Employee(String empId, String dptId, String empName, String empCode, String empSex, String dptName, String createTime, String updateTime) {
        this.empId = empId;
        this.dptId = dptId;
        this.empName = empName;
        this.empCode = empCode;
        this.empSex = empSex;
        this.dptName = dptName;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getDptId() {
        return dptId;
    }

    public void setDptId(String dptId) {
        this.dptId = dptId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getEmpSex() {
        return empSex;
    }

    public void setEmpSex(String empSex) {
        this.empSex = empSex;
    }

    public String getDptName() {
        return dptName;
    }

    public void setDptName(String dptName) {
        this.dptName = dptName;
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

    @Override
    public String toString() {
        return "Employee{" +
                "empId='" + empId + '\'' +
                ", dptId='" + dptId + '\'' +
                ", empName='" + empName + '\'' +
                ", empCode='" + empCode + '\'' +
                ", empSex='" + empSex + '\'' +
                ", dptName='" + dptName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
