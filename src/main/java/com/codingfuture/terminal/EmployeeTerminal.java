package com.codingfuture.terminal;

import com.codingfuture.entity.Dept;
import com.codingfuture.entity.Employee;
import com.codingfuture.service.DepService;
import com.codingfuture.service.EmployeeService;
import com.codingfuture.util.IsNumericUtils;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class EmployeeTerminal {
    private static final String ADD = "1";
    private static final String UPDATE = "2";
    private static final String DELETE = "3";
    private static final String SELECT = "4";
    private static final String EXIT = "5";
    private static final int MALE_NUM = 1;
    private static final int FEMALE_NUM = 2;
    private static final EmployeeService employeeService = EmployeeService.getInstace();

    public static void empOperation() {
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            System.out.println("***************************************");
            System.out.println("*********** 员工数据管理系统 ************");
            System.out.println("** 1. 员工新增 *************************");
            System.out.println("** 2. 员工修改 *************************");
            System.out.println("** 3. 员工删除 *************************");
            System.out.println("** 4. 员工查询 *************************");
            System.out.println("** 5. 退出模块 *************************");
            System.out.println("***************************************");
            System.out.print("请输入执行的功能序号:");
            input = scanner.next();
            while (!IsNumericUtils.isNumeric(input)){
                System.out.println("** 您输入的序号不对请重新输入:");
                input = scanner.next();
            }
            switch (input) {
                case ADD:
                    System.out.println("**************************************");
                    System.out.println("*********** 员工数据新增 ***************");
                    System.out.println("*********** 部门数据查询 ***************");
                    String resultDeptName = null;
                    String employeeSex = null;
                    String resultDeptId = null;
                    DepService.selectDeptService();
                    System.out.print("** 请选择一个部门进行员工数据添加:");
                    String deptNum = scanner.next();
                    List<Dept> resultDeptList = DepService.selectOneDeptService(deptNum);
                    Iterator<Dept> iterator = resultDeptList.iterator();
                    while (iterator.hasNext()) {
                        Dept resultDept = iterator.next();
                        resultDeptName = resultDept.getName();
                        resultDeptId = resultDept.getId();
                    }
                    System.out.println("*********** 已选择的部门:" + resultDeptName + " **********");
                    System.out.println("*********** 请输入新员工姓名 *************");
                    String employeeName = scanner.next();
                    System.out.println("*********** 请输入新员工性别(1:男;2:女) **********");
                    int sexNum = scanner.nextInt();
                    switch (sexNum) {
                        case MALE_NUM:
                            employeeSex = "MALE";
                            break;
                        case FEMALE_NUM:
                            employeeSex = "FEMALE";
                            break;
                        default:
                            System.out.println("请输入正确的性别编号");
                    }
                    Employee addEmployee = new Employee();
                    addEmployee.setDptId(resultDeptId);
                    addEmployee.setDptName(resultDeptName);
                    addEmployee.setEmpSex(employeeSex);
                    addEmployee.setEmpName(employeeName);
                    employeeService.addEmployeeService(addEmployee);
                    break;
                case UPDATE:
                    System.out.println("**************************************");
                    System.out.println("*********** 员工数据修改 ***************");
                    System.out.println("*********** 员工数据查询 ***************");
                    String selectDepName = null;
                    String selectEmpName = null;
                    String selectEmpSex = null;
                    employeeService.selectEmployeeService();
                    System.out.println("*********** 请输入修改的数据序号 ********");
                    String updateNum = scanner.next();
                    List<Employee> resultOneListSelect = employeeService.selectOneEmployeeService(updateNum);
                    Iterator<Employee> iteratorSelect = resultOneListSelect.iterator();
                    while (iteratorSelect.hasNext()) {
                        Employee empOneSelect = iteratorSelect.next();
                        selectDepName = empOneSelect.getDptName();
                        selectEmpName = empOneSelect.getEmpName();
                        selectEmpSex = empOneSelect.getEmpSex();
                    }
                    if (selectEmpSex.equals("MALE")) {
                        selectEmpSex = "男";
                    }
                    if (selectEmpSex.equals("FEMALE")) {
                        selectEmpSex = "女";
                    }
                    System.out.println("***********" + " 已选择[" + selectDepName + "]部门" + "  员工:[" + selectEmpName + "]"+ " **************");
                    System.out.println("***********" + " 员工名字原数据为:" + selectEmpName + "  请输入员工的名字:" + "*******");
                    String newEmpName = scanner.next();
                    System.out.println("***********" + " 员工性别原数据为:" + selectEmpSex + "  请选择员工性别(1:男;2:女): " + "*******");
                    int newEmpSex = scanner.nextInt();
                    switch (newEmpSex) {
                        case MALE_NUM:
                            selectEmpSex = "MALE";
                            break;
                        case FEMALE_NUM:
                            selectEmpSex = "FEMALE";
                            break;
                        default:
                            System.out.println("请输入正确的性别编号");
                    }
                    Employee updateEmployee = new Employee();
                    updateEmployee.setEmpId(updateNum);
                    updateEmployee.setEmpName(newEmpName);
                    updateEmployee.setEmpSex(selectEmpSex);
                    employeeService.updateEmployeeService(updateEmployee);
                    break;
                case DELETE:
                    System.out.println("**************************************");
                    System.out.println("*********** 员工数据删除 ***************");
                    System.out.println("*********** 员工数据查询 ***************");
                    employeeService.selectEmployeeService();
                    System.out.println("*********** 请输入删除的数据序号 ********");
                    String deleteNum = scanner.next();
                    Employee deleteEmployee = new Employee();
                    deleteEmployee.setEmpId(deleteNum);
                    employeeService.deleteEmployeeService(deleteEmployee);
                    break;
                case SELECT:
                    System.out.println("*********** 员工数据查询 ***************");
                    employeeService.selectEmployeeService();
                    break;
                case EXIT:
                    input = "exist";
                    break;
                default:
                    System.out.println("输入的执行序号不存在请重新输入");
            }
        } while (!input.equals("exist"));
    }
}