package com.codingfuture.service;

import com.codingfuture.dao.SalaryDao;
import com.codingfuture.entity.Employee;
import com.codingfuture.entity.Salary;
import com.codingfuture.util.TimeUtils;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SalaryService {
    private static final EmployeeService employeeService = EmployeeService.getInstace();
    private static SalaryService instace = null;
    private DecimalFormat df = new DecimalFormat("#.00");
    private SalaryService() {
    }

    public static SalaryService getInstace() {
        if (instace == null) {
            synchronized (EmployeeService.class) {
                if (instace == null) {
                    instace = new SalaryService();
                }
            }
        }
        return instace;
    }

    //添加薪资方法
    public void addSalaryService(Salary salaryAdd) {
        double saActual = salaryAdd.getSaBase() + salaryAdd.getSaPerformance() - salaryAdd.getSaInsurance();
        salaryAdd.setSaActual(saActual);
        SalaryDao.addSalary(salaryAdd);
    }

    //判断员工当月工资是否已经存在
    public boolean isExistSalaryService(Salary salaryAdd) {
        List<String> list = SalaryDao.getSalaryDate(salaryAdd);
        return list.contains(salaryAdd.getSaDate());
    }

    //判断薪资表中是否存在该该员工的薪资
    public boolean isExistEmpSalary(String empId) {
        List<String> list = SalaryDao.isExistEmpSalary(empId);
        return list.contains(empId);
    }

    //返回员工姓名
    public String resultEmpName(String empNum) {
        String empName = null;
        List<Employee> resultOneListSelect = employeeService.selectOneEmployeeService(empNum);
        Iterator<Employee> iteratorSelect = resultOneListSelect.iterator();
        while (iteratorSelect.hasNext()) {
            Employee empOneSelect = iteratorSelect.next();
            empName = empOneSelect.getEmpName();
        }
        return empName;
    }

    //返回员工ID
    public String resultEmpId(String empNum) {
        String empId = null;
        List<Employee> resultOneListSelect = employeeService.selectOneEmployeeService(empNum);
        Iterator<Employee> iteratorSelect = resultOneListSelect.iterator();
        while (iteratorSelect.hasNext()) {
            Employee empOneSelect = iteratorSelect.next();
            empId = empOneSelect.getEmpId();
        }
        return empId;
    }

    //查询某员工薪资历史明细
    public void selectEmpSalaryDetailService(String empId) {
        List<Salary> getSalaryDetailList = SalaryDao.selectEmpSalaryDetail(empId);
        Iterator<Salary> iterator = getSalaryDetailList.iterator();
        System.out.println("+------+----------+--------------+----------+----------+----------+----------+");
        System.out.println(" | " + "序号" + "| " + "保险扣除" + "  | " + "实发薪资" + "     | " + "员工姓名" + " | "
                + "薪资月份" + "   | " + "绩效薪资" + "  | " + "基础薪资" + "  | ");
        System.out.println("+------+----------+--------------+----------+----------+----------+----------+");
        while (iterator.hasNext()) {
            Salary salaryDetailList = iterator.next();
            String rowNumber = salaryDetailList.getSaId();
            //double saInsurance = salaryDetailList.getSaInsurance();
            String saInsurance = df.format(salaryDetailList.getSaInsurance());
            //double saActual = salaryDetailList.getSaActual();
            String saActual = df.format(salaryDetailList.getSaActual());
            String empName = salaryDetailList.getEmpName();
            String saDate = salaryDetailList.getSaDate();
            //double saPerformance = salaryDetailList.getSaPerformance();
            String saPerformance = df.format(salaryDetailList.getSaPerformance());
            //double saBase = salaryDetailList.getSaBase();
            String saBase = df.format(salaryDetailList.getSaBase());
            System.out.println(" | " + rowNumber + "   | " + saInsurance + "   | " + saActual + "    | "
                    + empName + "    | " + saDate + "   | " + saPerformance + "  | " + saBase+ "  | ");
        }
        System.out.println("+------+----------+--------------+----------+----------+----------+----------+");
    }

    //查询某年月薪资历史明细
    public void selectDateSalaryDetailService(String dateInput) {
        List<Salary> getSalaryDetailList = SalaryDao.selectDateSalaryDetail(dateInput);
        if (!getSalaryDetailList.isEmpty()) {
            Iterator<Salary> iterator = getSalaryDetailList.iterator();
            System.out.println("+------+----------+--------------+----------+----------+----------+----------+");
            System.out.println(" | " + "序号" + "| " + "保险扣除" + "  | " + "实发薪资" + "      | " + "员工姓名" + " | "
                    + "薪资月份" + "   | " + "绩效薪资" + "  | " + "基础薪资" + "  | ");
            System.out.println("+------+----------+--------------+----------+----------+----------+----------+");
            while (iterator.hasNext()) {
                Salary salaryDetailList = iterator.next();
                String rowNumber = salaryDetailList.getSaId();
                //double saInsurance = salaryDetailList.getSaInsurance();
                String saInsurance = df.format(salaryDetailList.getSaInsurance());
                //double saActual = salaryDetailList.getSaActual();
                String saActual = df.format(salaryDetailList.getSaActual());
                String empName = salaryDetailList.getEmpName();
                String saDate = salaryDetailList.getSaDate();
                //double saPerformance = salaryDetailList.getSaPerformance();
                String saPerformance = df.format(salaryDetailList.getSaPerformance());
                //double saBase = salaryDetailList.getSaBase();
                String saBase = df.format(salaryDetailList.getSaBase());
                System.out.println(" | " + rowNumber + "   | " + saInsurance + "   | " + saActual + "    | "
                        + empName + "    | " + saDate + "   | " + saPerformance + "  | " + saBase+ "  | ");
            }
            System.out.println("+------+----------+--------------+----------+----------+----------+----------+");
        } else {
            System.out.println("******* " + dateInput + "月没有员工薪资信息" + "********");
        }
    }
    //获取基础薪资
    public String getSaBaseService(String empId){
        String  sa_base=null;
        List<Salary> getSalaryDetailList = SalaryDao.selectEmpSalaryDetail(empId);
        Iterator<Salary> iterator = getSalaryDetailList.iterator();
        while (iterator.hasNext()) {
            Salary salaryDetailList = iterator.next();
            if (salaryDetailList.getSaDate().equals(TimeUtils.timeYM())){
                //sa_base = salaryDetailList.getSaBase();
                sa_base = df.format(salaryDetailList.getSaBase());
            }
        }
        return sa_base;
    }
    //获取绩效薪资
    public String getSaPerformanceService(String empId){
        String sa_performance=null;
        List<Salary> getSalaryDetailList = SalaryDao.selectEmpSalaryDetail(empId);
        Iterator<Salary> iterator = getSalaryDetailList.iterator();
        while (iterator.hasNext()) {
            Salary salaryDetailList = iterator.next();
            if (salaryDetailList.getSaDate().equals(TimeUtils.timeYM())){
                //sa_performance = salaryDetailList.getSaPerformance();
                sa_performance = df.format(salaryDetailList.getSaPerformance());
            }
        }
        return sa_performance;
    }
    //获取绩效薪资
    public String getSaInsuranceService(String empId){
        String sa_insurance = null;
        List<Salary> getSalaryDetailList = SalaryDao.selectEmpSalaryDetail(empId);
        Iterator<Salary> iterator = getSalaryDetailList.iterator();
        while (iterator.hasNext()) {
            Salary salaryDetailList = iterator.next();
            if (salaryDetailList.getSaDate().equals(TimeUtils.timeYM())){
                //sa_insurance = salaryDetailList.getSaInsurance();
                sa_insurance = df.format(salaryDetailList.getSaInsurance());
            }
        }
        return sa_insurance;
    }
    //修改薪资
    public void updateSalaryService(Salary salaryUpdate){
        double newSaActual = salaryUpdate.getSaBase() + salaryUpdate.getSaPerformance() - salaryUpdate.getSaInsurance();
        salaryUpdate.setSaActual(newSaActual);
        SalaryDao.updateEmpSalary(salaryUpdate);
    }
    //删除薪资
    public void deleteSalaryService(Salary salaryDelete){
        SalaryDao.deleteEmpSalary(salaryDelete);
    }
    //    判断输入的日期格式是否正确
    public  boolean isDate(String date) {
        String eL = "[0-9]{4}-[0-9]{2}";
        Pattern p = Pattern.compile(eL);
        Matcher m = p.matcher(date);
        boolean dateFlag = m.matches();
        return dateFlag;
    }
}
