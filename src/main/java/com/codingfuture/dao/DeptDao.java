package com.codingfuture.dao;

import com.codingfuture.entity.Dept;
import com.codingfuture.util.JDBCUtils;
import com.codingfuture.util.TimeUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DeptDao {
    //增加部门
    public static void addData(Dept dept){
        try(Connection connection = JDBCUtils.getConnection()) {
            String sql = "INSERT INTO ims_department (dpt_id,dpt_name,is_deleted,create_time,update_time) VALUES(?,?,0,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, UUID.randomUUID().toString());
            preparedStatement.setString(2,dept.getName());
            preparedStatement.setString(3,TimeUtils.timeymdhms());
            preparedStatement.setString(4,TimeUtils.timeymdhms());
            int i = preparedStatement.executeUpdate();
            if (i>0){
                System.out.println("************ 数据添加成功 ***************");
            }else {
                System.out.println("************ 添加数据失败 ***************");
            }
        }catch (SQLException e){
            System.out.println("数据库连接异常"+e.getMessage());
        }
    }
    //查询已有部门名称
    public static List<String> selectExistDept(){
        List<String> list = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection()) {
            String sql = "SELECT dpt_name FROM ims_department WHERE is_deleted = 0";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String deptName = resultSet.getString("dpt_name");
                list.add(deptName);
            }
        }catch (SQLException e){
            System.out.println("数据库连接异常"+e.getMessage());
        }
        return list;
    }
    //查询所有部门信息
    public static List<Dept> selectDept(){
        List<Dept> selectedList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection()) {
            String sql = "SELECT ROW_NUMBER() OVER(ORDER BY dpt_id) AS rowNumber,dpt_name FROM ims_department WHERE is_deleted = 0";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String deptId = resultSet.getString("rowNumber");
                String deptName = resultSet.getString("dpt_name");
                selectedList.add(new Dept(deptId,deptName));
            }
        }catch (SQLException e){
            System.out.println("数据库连接异常"+e.getMessage());
        }
        return selectedList;
    }
    //部门信息修改
    public static void updateDeptData(Dept dept) {
        try(Connection connection = JDBCUtils.getConnection()){
            String sql = "UPDATE ims_department a LEFT JOIN (SELECT ROW_NUMBER() OVER(ORDER BY dpt_id) AS rowNumber,dpt_name FROM ims_department WHERE is_deleted = 0) b ON a.dpt_name = b.dpt_name SET\n" +
                    "a.dpt_name = ?,a.update_time = ? WHERE b.rowNumber = ? AND a.is_deleted = 0";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,dept.getName());
            preparedStatement.setString(2, TimeUtils.timeymdhms());
            preparedStatement.setString(3,dept.getId());
            int i = preparedStatement.executeUpdate();
            if (i>0){
                System.out.println("************ 数据修改成功 **************");
            }else {
                System.out.println("************ 数据修改失败 **************");
            }
        }catch (Exception e){
            System.out.println("数据库连接异常"+e.getMessage());
        }
    }
    //删除部门表中的信息
    public static void deleteDeptData(Dept dept){
        try(Connection connection = JDBCUtils.getConnection()){
            String sql = "UPDATE ims_department a LEFT JOIN (SELECT ROW_NUMBER() OVER(ORDER BY dpt_id) AS rowNumber,dpt_name FROM ims_department WHERE is_deleted = 0) b ON a.dpt_name = b.dpt_name SET\n" +
                    "a.is_deleted = 1 ,a.update_time = ? WHERE b.rowNumber = ? AND a.is_deleted = 0;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,TimeUtils.timeymdhms());
            preparedStatement.setString(2,dept.getId());
            int i = preparedStatement.executeUpdate();
            if (i>0){
                System.out.println("************ 数据删除成功 **************");
            }else {
                System.out.println("************ 数据删除失败 **************");
            }
        }catch (Exception e){
            System.out.println("数据库连接异常"+e.getMessage());
        }
    }
    //根据序号查询部门
    public static List<Dept> selectOneDept(String deptNum){
        List<Dept> oneDeptList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection()){
            String sql = "SELECT a.dpt_name,a.dpt_id FROM ims_department a LEFT JOIN (SELECT ROW_NUMBER() OVER(ORDER BY dpt_id) AS rowNumber,dpt_name FROM ims_department WHERE is_deleted = 0) b ON a.dpt_name = b.dpt_name WHERE a.is_deleted = 0 AND b.rowNumber = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,deptNum);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String deptName = resultSet.getString("dpt_name");
                String deptId = resultSet.getString("dpt_id");
                oneDeptList.add(new Dept(deptId,deptName));
            }
        }catch (SQLException e){
            System.out.println("数据库连接异常"+e.getMessage());
        }
        return oneDeptList;
    }
}
