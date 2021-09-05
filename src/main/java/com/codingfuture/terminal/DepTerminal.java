package com.codingfuture.terminal;

import com.codingfuture.entity.Dept;
import com.codingfuture.service.DepService;
import com.codingfuture.util.IsNumericUtils;

import java.util.Scanner;

public class DepTerminal {
    private static final String ADD = "1";
    private static final String UPDATE = "2";
    private static final String DELETE = "3";
    private static final String SELECT = "4";
    private static final String EXIT = "5";
    public static void depOperation(){
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            System.out.println("***************************************");
            System.out.println("*********** 部门数据管理系统 ************");
            System.out.println("** 1. 部门新增 *************************");
            System.out.println("** 2. 部门修改 *************************");
            System.out.println("** 3. 部门删除 *************************");
            System.out.println("** 4. 部门查询 *************************");
            System.out.println("** 5. 退出模块 *************************");
            System.out.println("***************************************");
            System.out.print("请输入执行的功能序号:");
            input = scanner.next();
            while (!IsNumericUtils.isNumeric(input)){
                System.out.println("** 您输入的序号不对请重新输入:");
                input = scanner.next();
            }
            switch (input){
                case ADD:
                    System.out.println("** 请输入部门名称：");
                    Dept dept = new Dept();
                    String deptName = scanner.next();
                    dept.setName(deptName);
                    DepService.addDataService(dept);
                    break;
                case UPDATE:
                    System.out.println("***************************************");
                    System.out.println("*********** 部门数据修改 ****************");
                    System.out.println("*********** 部门数据查询 ****************");
                    DepService.selectDeptService();
                    System.out.println("*********** 请输入修改的数据序号 **********");
                    String updateNum = scanner.next();
                    System.out.println("*********** 请重新输入部门名称 ************");
                    String updateName = scanner.next();
                    Dept deptUpdate = new Dept(updateNum,updateName);
                    DepService.updateDeptService(deptUpdate);
                    break;
                case DELETE:
                    System.out.println("***************************************");
                    System.out.println("*********** 部门数据删除 ****************");
                    System.out.println("*********** 部门数据查询 ****************");
                    DepService.selectDeptService();
                    System.out.println("*********** 请输入删除数据序号 **********");
                    String deleteNum = scanner.next();
                    Dept deleteDept = new Dept();
                    deleteDept.setId(deleteNum);
                    DepService.deleteDeptService(deleteDept);
                    break;
                case SELECT:
                    DepService.selectDeptService();
                    break;
                case EXIT:
                    input = "exist";
                    break;
                default:
                    System.out.println("输入的执行序号不存在请重新输入");
            }
        }while (!input.equals("exist"));
    }
}
