package com.codingfuture.dao;

import com.codingfuture.entity.Employee;
import com.codingfuture.util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EmployeeDao {
    //查询当日创建的员工数
    public static int currentDateAddEmployee() {
        int employeeCount = 0;
        try (Connection connection = JDBCUtils.getConnection()) {
            String sql = "SELECT COUNT(*) count FROM ims_employee WHERE date(create_time) = curdate()";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                employeeCount = resultSet.getInt("count");
            }
        } catch (SQLException e) {
            System.out.println("数据库连接异常" + e.getMessage());
        }
        return employeeCount;
    }

    //添加员工信息
    public static void addEmployee(Employee addEmployee, String empCode) {
        try (Connection connection = JDBCUtils.getConnection()) {
            String sql = "INSERT INTO ims_employee (emp_id,dpt_id,emp_name,emp_code,emp_sex,dpt_name,is_deleted,create_time,update_time) VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, UUID.randomUUID().toString());
            preparedStatement.setString(2, addEmployee.getDptId());
            preparedStatement.setString(3, addEmployee.getEmpName());
            preparedStatement.setString(4, empCode);
            preparedStatement.setString(5, addEmployee.getEmpSex());
            preparedStatement.setString(6, addEmployee.getDptName());
            preparedStatement.setInt(7, 0);
            preparedStatement.setString(8, addEmployee.getCreateTime());
            preparedStatement.setString(9, addEmployee.getUpdateTime());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                System.out.println("************ 数据添加成功 ***************");
            } else {
                System.out.println("************ 添加数据失败 ***************");
            }
        } catch (SQLException e) {
            System.out.println("数据库连接异常" + e.getMessage());
        }
    }

    //查询员工信息
    public static List<Employee> selectEmployee() {
        List<Employee> selectedList = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection()) {
            String sql = "SELECT ROW_NUMBER() OVER(ORDER BY dpt_id) AS rowNumber,emp_sex,emp_code,dpt_name,emp_name FROM ims_employee WHERE is_deleted = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 0);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String empRowNumber = resultSet.getString("rowNumber");
                String empSex = resultSet.getString("emp_sex");
                String empCode = resultSet.getString("emp_code");
                String dptName = resultSet.getString("dpt_name");
                String empName = resultSet.getString("emp_name");
                //String emp_id = resultSet.getString("emp_id");
                selectedList.add(new Employee(empRowNumber, empName, empCode, empSex, dptName));
            }
        } catch (SQLException e) {
            System.out.println("数据库连接异常" + e.getMessage());
        }
        return selectedList;
    }

    //查询一个员工的信息
    public static List<Employee> selectOneEmployee(String updateNum) {
        List<Employee> selectedOneList = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection()) {
            String sql = "SELECT a.dpt_name,a.emp_name,a.emp_sex,a.emp_id FROM ims_employee a LEFT JOIN (SELECT ROW_NUMBER() OVER(ORDER BY dpt_id) AS rowNumber,emp_code,emp_sex,dpt_name,emp_name FROM ims_employee WHERE is_deleted = 0) b ON a.emp_code = b.emp_code WHERE a.is_deleted = 0 AND b.rowNumber = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, updateNum);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String empId = resultSet.getString("emp_id");
                String dptName = resultSet.getString("dpt_name");
                String empName = resultSet.getString("emp_name");
                String empSex = resultSet.getString("emp_sex");
                selectedOneList.add(new Employee(empId,empName, dptName, empSex));
            }
        } catch (SQLException e) {
            System.out.println("数据库连接异常" + e.getMessage());
        }
        return selectedOneList;
    }

    //修改员工信息
    public static void updateEmployee(Employee updateEmployee) {
        try (Connection connection = JDBCUtils.getConnection()) {
            String sql = "UPDATE ims_employee a LEFT JOIN " +
                    "(SELECT ROW_NUMBER() OVER(ORDER BY dpt_id) AS rowNumber,emp_code,emp_sex,dpt_name,emp_name FROM ims_employee WHERE is_deleted = 0) b ON a.emp_code = b.emp_code" +
                    " SET a.emp_sex =?,a.emp_name =?  " +
                    "WHERE a.is_deleted = ? AND b.rowNumber = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, updateEmployee.getEmpSex());
            preparedStatement.setString(2, updateEmployee.getEmpName());
            preparedStatement.setInt(3, 0);
            preparedStatement.setString(4, updateEmployee.getEmpId());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                System.out.println("************ 数据修改成功 **************");
            } else {
                System.out.println("************ 数据修改失败 **************");
            }
        } catch (Exception e) {
            System.out.println("数据库连接异常" + e.getMessage());
        }
    }

    //删除员工信息
    public static void deleteEmployee(Employee updateEmployee) {
        try (Connection connection = JDBCUtils.getConnection()) {
            String sql = "UPDATE ims_employee a LEFT JOIN (SELECT ROW_NUMBER() OVER(ORDER BY dpt_id) AS rowNumber,emp_code,emp_sex,dpt_name,emp_name FROM ims_employee WHERE is_deleted = 0) b ON a.emp_code = b.emp_code " +
                    "SET a.is_deleted =?  WHERE a.is_deleted = ? AND b.rowNumber = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, 0);
            preparedStatement.setString(3, updateEmployee.getEmpId());
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                System.out.println("************ 数据删除成功 **************");
            } else {
                System.out.println("************ 数据删除失败 **************");
            }
        } catch (Exception e) {
            System.out.println("数据库连接异常" + e.getMessage());
        }
    }
}

