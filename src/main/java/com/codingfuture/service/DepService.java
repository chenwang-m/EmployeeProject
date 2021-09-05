package com.codingfuture.service;

import com.codingfuture.dao.DeptDao;
import com.codingfuture.entity.Dept;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class DepService {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Date date = new Date();
    private static final String nowDate = sdf.format(date);

    public static void addDataService(Dept dept) {
        if (!checkName(dept.getName())) {
            System.out.println("输入部门名称有错误，请输入符合要求的部门名称(部门名称为中文，不包含空格，字母，数字)");
            System.out.println("** 添加数据失败 *************************");
            return;
        } else if (!isExistDept(dept.getName())) {
            System.out.println("输入的部门名称已经存在,请更换其他部门名称");
            System.out.println("** 添加数据失败 *************************");
            return;
        } else {
            dept.setCreateTime(nowDate);
            dept.setUpdateTime(nowDate);
            DeptDao.addData(dept);
        }
    }
    //查询所有部门信息
    public static void selectDeptService(){
        List<Dept> resultListSelect;
        resultListSelect = DeptDao.selectDept();
        Iterator<Dept> iteratorSelect = resultListSelect.iterator();
        System.out.println("+------+----------+");
        System.out.println(" | "+"序号"+"| "+"部门名称"+" | ");
        System.out.println("+------+----------+");
        while (iteratorSelect.hasNext()){
            Dept deptSelect = iteratorSelect.next();
            String deptId = deptSelect.getId();
            String deptDname = deptSelect.getName();
            System.out.println(" | "+deptId+"  | "+deptDname+"   | ");
        }
        System.out.println("+------+----------+");
    }
    //修改部门信息
    public static void updateDeptService(Dept dept){
        if (!checkName(dept.getName())) {
            System.out.println("输入部门名称有错误，请输入符合要求的部门名称(部门名称为中文，不包含空格，字母，数字)");
            System.out.println("** 添加数据失败 *************************");
            return;
        } else if (!isExistDept(dept.getName())) {
            System.out.println("输入的部门名称已经存在,请更换其他部门名称");
            System.out.println("** 添加数据失败 *************************");
            return;
        }else {
            DeptDao.updateDeptData(dept);
        }
    }
    //删除部门信息
    public static void deleteDeptService(Dept dept){
        DeptDao.deleteDeptData(dept);
    }
    //查询一个部门的信息
    public static List<Dept> selectOneDeptService(String deptNum){
        List<Dept> resultDept = DeptDao.selectOneDept(deptNum);
        return resultDept;
    }
    //判断输入的内容是否都是汉字
    public static boolean checkName(String name) {
        String regExp = "[\u4e00-\u9fa5]+";
        if (name.matches(regExp)) {
            return true;
        } else {
            return false;
        }
    }

    //判断输入的部门是否存在
    public static boolean isExistDept(String deptName) {
        boolean flag = true;
        List<String> deptList = DeptDao.selectExistDept();
        Iterator<String> iterable = deptList.iterator();
        while (iterable.hasNext()) {
            String iter = iterable.next();
            if (iter.equals(deptName)) {
                flag = false;
            }
        }
        return flag;
    }
}
