package com.codingfuture.service;

import com.codingfuture.dao.EmployeeDao;
import com.codingfuture.entity.Employee;
import com.codingfuture.util.ChineseToFirstLetterUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class EmployeeService {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Date date = new Date();
    private static final String nowDate = sdf.format(date);
    private static EmployeeService instace = null;

    private EmployeeService() {
    }

    public static EmployeeService getInstace() {
        if (instace == null) {
            synchronized (EmployeeService.class) {
                if (instace == null) {
                    instace = new EmployeeService();
                }
            }
        }
        return instace;
    }

    //添加员工信息
    public void addEmployeeService(Employee addEmployee) {
        StringBuilder stringBuilder = new StringBuilder();
        //获取部门大写首字母
        String deptNameFirstUpperCase = ChineseToFirstLetterUtil.ChineseToFirstLetter(addEmployee.getDptName());
        //获取年月日(210804)
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        Date date = new Date();
        String empNowDate = sdf.format(date);
        //获取序列号
        int currentDateEmployeeNum = EmployeeDao.currentDateAddEmployee() + 1;
        String newcurrentDateEmployeeNum = Integer.toString(currentDateEmployeeNum);
        if (currentDateEmployeeNum < 10) {
            newcurrentDateEmployeeNum = "0" + newcurrentDateEmployeeNum;
        }
        String empCode = stringBuilder.append(deptNameFirstUpperCase).append(empNowDate).append(newcurrentDateEmployeeNum).toString();
        addEmployee.setCreateTime(nowDate);
        addEmployee.setUpdateTime(nowDate);
        EmployeeDao.addEmployee(addEmployee, empCode);
    }

    //查询员工信息
    public void selectEmployeeService() {
        List<Employee> resultListSelect;
        resultListSelect = EmployeeDao.selectEmployee();
        Iterator<Employee> iteratorSelect = resultListSelect.iterator();
        System.out.println("+------+----------+--------------+----------+----------+");
        System.out.println(" | " + "序号" + "| " + "员工性别" + " | " + "员工工号" + "     | " + "部门名称" + " | " + "员工名称" + " | ");
        System.out.println("+------+----------+--------------+----------+----------+");
        while (iteratorSelect.hasNext()) {
            Employee empSelect = iteratorSelect.next();
            String empRowNumber = empSelect.getEmpId();
            String empSex = empSelect.getEmpSex();
            if (empSex.equals("MALE")) {
                empSex = "男";
            }
            if (empSex.equals("FEMALE")) {
                empSex = "女";
            }
            String empCode = empSelect.getEmpCode();
            String dptName = empSelect.getDptName();
            String empName = empSelect.getEmpName();
            //System.out.println("+------+----------+--------------+----------+----------+");
            System.out.println(" | " + empRowNumber + "   | " + empSex + "      | " + empCode + " | " + dptName + "   | " + empName + "    | ");
        }
        System.out.println("+------+----------+--------------+----------+----------+");
    }

    //查询一个员工的信息
    public List<Employee> selectOneEmployeeService(String updateNum) {
        List<Employee> resultOneListSelect = EmployeeDao.selectOneEmployee(updateNum);
        return resultOneListSelect;
    }
    //更新员工信息
    public void updateEmployeeService(Employee updateEmployee) {
        EmployeeDao.updateEmployee(updateEmployee);
    }
    //删除员工信息
    public void deleteEmployeeService(Employee deleteEmployee){
        EmployeeDao.deleteEmployee(deleteEmployee);
    }

}
