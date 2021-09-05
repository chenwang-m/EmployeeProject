package com.codingfuture.terminal;

import com.codingfuture.entity.Salary;
import com.codingfuture.service.EmployeeService;
import com.codingfuture.service.SalaryService;
import com.codingfuture.util.IsNumericUtils;
import com.codingfuture.util.TimeUtils;

import java.util.Scanner;

public class SalaryTerminal {
    private static final String ADD = "1";
    private static final String UPDATE = "2";
    private static final String DELETE = "3";
    private static final String SELECT = "4";
    private static final String EXIT = "5";
    private static final String selectOne = "1";
    private static final String selectTwo = "2";
    private static final EmployeeService employeeService = EmployeeService.getInstace();
    private static final SalaryService salaryService = SalaryService.getInstace();

    public static void salaryOperation() {
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            System.out.println("***************************************");
            System.out.println("*********** 薪资数据管理 ****************");
            System.out.println("** 1. 薪资新增 *************************");
            System.out.println("** 2. 薪资修改 *************************");
            System.out.println("** 3. 薪资删除 *************************");
            System.out.println("** 4. 薪资查询 *************************");
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
                    String empName = null;
                    String empId = null;
                    double salaryBase = 0.00;
                    double salaryPerformance = 0.00;
                    double salaryInsurance = 0.00;
                    Salary salaryAdd = new Salary();
                    System.out.println("**************************************");
                    System.out.println("*********** 薪资数据新增 ***************");
                    System.out.println("*********** 员工数据查询 ***************");
                    employeeService.selectEmployeeService();
                    System.out.print("** 请选择一个员工进行薪资数据添加:");
                    String empNum = scanner.next();
                    /*List<Employee> resultOneListSelect = employeeService.selectOneEmployeeService(empNum);
                    Iterator<Employee> iteratorSelect = resultOneListSelect.iterator();
                    while (iteratorSelect.hasNext()) {
                        Employee empOneSelect = iteratorSelect.next();
                        empName = empOneSelect.getEmpName();
                        empId = empOneSelect.getEmpId();
                    }*/
                    empName = salaryService.resultEmpName(empNum);
                    empId = salaryService.resultEmpId(empNum);
                    System.out.println("** 当前操作[" + empName + "]" + "员工的[" + TimeUtils.timeYM() + "]" + "薪资信息 **");
                    salaryAdd.setEmpId(empId);
                    salaryAdd.setSaDate(TimeUtils.timeYM());
                    if (salaryService.isExistSalaryService(salaryAdd)) {
                        System.out.println("** 该员工当月薪资信息已添加，数据添加失败 **");
                        break;
                    }
                    System.out.println("*********** 请输入基础薪资 ***************");
                    salaryBase = scanner.nextDouble();
                    salaryAdd.setSaBase(salaryBase);
                    while (salaryAdd.getSaBase() == -1) {
                        System.out.println("*********** 基础薪资不能小于0 **************");
                        System.out.println("*********** 请重新输入基础薪资 *************");
                        salaryBase = scanner.nextDouble();
                        salaryAdd.setSaBase(salaryBase);
                    }
                    System.out.println("*********** 请输入绩效薪资 ***************");
                    salaryPerformance = scanner.nextDouble();
                    salaryAdd.setSaPerformance(salaryPerformance);
                    while (salaryAdd.getSaPerformance() == -1) {
                        System.out.println("*********** 绩效薪资不能小于0 **************");
                        System.out.println("*********** 请重新输入绩效薪资 *************");
                        salaryPerformance = scanner.nextDouble();
                        salaryAdd.setSaPerformance(salaryPerformance);
                    }
                    System.out.println("*********** 请输入保险扣除薪资 ************");
                    salaryInsurance = scanner.nextDouble();
                    salaryAdd.setSaInsurance(salaryInsurance);
                    while (salaryAdd.getSaInsurance() == -1) {
                        System.out.println("*********** 保险扣除薪资不能小于0 ************");
                        System.out.println("*********** 请重新输入保险扣除薪资 ***********");
                        salaryInsurance = scanner.nextDouble();
                        salaryAdd.setSaInsurance(salaryInsurance);
                    }
                    salaryService.addSalaryService(salaryAdd);
                    break;
                case UPDATE:
                    double newSaBase = 0.00;
                    double newSaPerformance = 0.00;
                    double newSaInsuranceService = 0.00;
                    System.out.println("******** 薪资数据修改 **********");
                    System.out.println("******** 员工数据查询 **********");
                    employeeService.selectEmployeeService();
                    System.out.println("**** 请输入修改的员工数据序号 ****");
                    String updateEmpNum = scanner.next();
                    empId = salaryService.resultEmpId(updateEmpNum);
                    Salary salaryUpdate = new Salary();
                    salaryUpdate.setEmpId(empId);
                    salaryUpdate.setSaDate(TimeUtils.timeYM());
                    if (salaryService.isExistSalaryService(salaryUpdate)) {
                        System.out.println("**** 请输入基础薪资,原数据为:" + salaryService.getSaBaseService(empId) + "****");
                        newSaBase = scanner.nextDouble();
                        salaryUpdate.setSaBase(newSaBase);
                        while (salaryUpdate.getSaBase() == -1) {
                            System.out.println("*********** 基础薪资不能小于0 **************");
                            System.out.println("*********** 请重新输入基础薪资 *************");
                            newSaBase = scanner.nextDouble();
                            salaryUpdate.setSaBase(newSaBase);
                        }
                        System.out.println("**** 请输入绩效薪资,原数据为:" + salaryService.getSaPerformanceService(empId) + "****");
                        newSaPerformance = scanner.nextDouble();
                        salaryUpdate.setSaPerformance(newSaPerformance);
                        while (salaryUpdate.getSaPerformance() == -1) {
                            System.out.println("*********** 绩效薪资不能小于0 **************");
                            System.out.println("*********** 请重新输入绩效薪资 *************");
                            newSaPerformance = scanner.nextDouble();
                            salaryUpdate.setSaPerformance(newSaPerformance);
                        }
                        System.out.println("**** 请输入保险扣除薪资,原数据为:" + salaryService.getSaInsuranceService(empId) + "****");
                        newSaInsuranceService = scanner.nextDouble();
                        salaryUpdate.setSaInsurance(newSaInsuranceService);
                        while (salaryUpdate.getSaInsurance() == -1) {
                            System.out.println("*********** 保险扣除薪资不能小于0 ************");
                            System.out.println("*********** 请重新输入保险扣除薪资 ***********");
                            newSaInsuranceService = scanner.nextDouble();
                            salaryUpdate.setSaInsurance(newSaInsuranceService);
                        }
                        salaryService.updateSalaryService(salaryUpdate);
                    } else {
                        System.out.println("**** 该员工当月薪资未填写,数据修改失败 ****");
                    }
                    break;
                case DELETE:
                    System.out.println("******** 薪资数据删除 **********");
                    System.out.println("******** 员工数据查询 **********");
                    employeeService.selectEmployeeService();
                    System.out.println("**** 请输入删除的员工数据序号 ****");
                    String deleteEmpNum = scanner.next();
                    empId = salaryService.resultEmpId(deleteEmpNum);
                    Salary salaryDelete = new Salary();
                    salaryDelete.setEmpId(empId);
                    salaryDelete.setSaDate(TimeUtils.timeYM());
                    if (salaryService.isExistSalaryService(salaryDelete)) {
                        salaryService.deleteSalaryService(salaryDelete);
                    } else {
                        System.out.println("**** 该员工当月薪资未填写,数据删除失败 ****");
                    }
                    break;
                case SELECT:
                    System.out.println("**************************************");
                    System.out.println("*********** 薪资数据查询 ***************");
                    System.out.println("** 1. 查询某员工薪资历史明细 ************");
                    System.out.println("** 2. 查询某年月薪资历史明细 ************");
                    System.out.print("** 请输入查询的方式序号:");
                    input = scanner.next();
                    while (!IsNumericUtils.isNumeric(input)){
                        System.out.println("** 您输入的序号不对请重新输入:");
                        input = scanner.next();
                    }
                    switch (input) {
                        case selectOne:
                            System.out.println("***** 查询某员工薪资历史明细 ****");
                            System.out.println("******** 员工数据查询 **********");
                            employeeService.selectEmployeeService();
                            System.out.print("** 请输入一个想要查询的员工序号:");
                            String deptNum = scanner.next();
                            empName = salaryService.resultEmpName(deptNum);
                            empId = salaryService.resultEmpId(deptNum);
                            System.out.println("********** 员工[" + empName + "]" + "薪资历史信息 *******");
                            if (!salaryService.isExistEmpSalary(empId)) {
                                System.out.println("**** 薪资表中没有员工[" + empName + "]" + "薪资历史信息 ****");
                            } else {
                                salaryService.selectEmpSalaryDetailService(empId);
                            }
                            break;
                        case selectTwo:
                            String dateInput=null;
                            System.out.println("***** 查询某年月薪资历史明细 ****");
                            System.out.println("** 请按照标准格式输入想要查询的年月(例:2021-03)");
                            dateInput = scanner.next();
                            while (!salaryService.isDate(dateInput)){
                                System.out.println("***** 您输入的日期格式不对 ****");
                                System.out.println("** 请按照标准格式输入想要查询的年月(例:2021-03)");
                                dateInput = scanner.next();
                            }
                            System.out.println("******* " + dateInput + "月员工薪资信息" + "********");
                            salaryService.selectDateSalaryDetailService(dateInput);
                            break;
                    }
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
