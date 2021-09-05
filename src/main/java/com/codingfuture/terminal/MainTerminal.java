package com.codingfuture.terminal;

import com.codingfuture.util.IsNumericUtils;
import com.codingfuture.util.JDBCUtils;

import java.util.Scanner;

public class MainTerminal {
    private static final String DEPT = "1";
    private static final String EMPLOYEE = "2";
    private static final String SALARY = "3";
    private static final String EXIST = "4";

    public static void main(String[] args) {
//        JDBCUtils.getConnection();
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            System.out.println("***************************************");
            System.out.println("************* 员工管理系统 **************");
            System.out.println("** 1. 部门信息管理 **********************");
            System.out.println("** 2. 员工信息管理 **********************");
            System.out.println("** 3. 薪资信息管理 **********************");
            System.out.println("** 4. 退出 *****************************");
            System.out.println("***************************************");
            System.out.print("请输入执行的功能序号:");
            input = scanner.next();
            while (!IsNumericUtils.isNumeric(input)){
                System.out.println("** 您输入的序号不对请重新输入:");
                input = scanner.next();
            }
            switch (input) {
                case DEPT:
                    DepTerminal.depOperation();
                    break;
                case EMPLOYEE:
                    EmployeeTerminal.empOperation();
                    break;
                case SALARY:
                    SalaryTerminal.salaryOperation();
                    break;
                case EXIST:
                    input = "exist";
                    break;
                default:
                    System.out.println("输入的执行序号不存在请您重新输入");
            }
        } while (!input.equals("exist"));
    }

}
