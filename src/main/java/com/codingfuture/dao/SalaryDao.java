package com.codingfuture.dao;

import com.codingfuture.entity.Employee;
import com.codingfuture.entity.Salary;
import com.codingfuture.util.JDBCUtils;
import com.codingfuture.util.TimeUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SalaryDao {

    public static void addSalary(Salary salary) {
        try (Connection connection = JDBCUtils.getConnection()) {
            String sql = "INSERT INTO ims_salary (sa_id,emp_id,sa_date,sa_base,sa_performance,sa_insurance,sa_actual," +
                    "is_deleted,create_time,update_time) " +
                    "VALUES(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, UUID.randomUUID().toString());
            preparedStatement.setString(2, salary.getEmpId());
            preparedStatement.setString(3, TimeUtils.timeYM());
            preparedStatement.setDouble(4, salary.getSaBase());
            preparedStatement.setDouble(5, salary.getSaPerformance());
            preparedStatement.setDouble(6, salary.getSaInsurance());
            preparedStatement.setDouble(7, salary.getSaActual());
            preparedStatement.setInt(8, 0);
            preparedStatement.setString(9, TimeUtils.timeymdhms());
            preparedStatement.setString(10, TimeUtils.timeymdhms());
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

    //获取某个人工资日期
    public static List<String> getSalaryDate(Salary salaryAdd) {
        List<String> getSalaryDateList = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection()) {
            String sql = "SELECT sa_date FROM  ims_salary s LEFT JOIN ims_employee e ON e.emp_id = s.emp_id " +
                    "WHERE s.emp_id = ? AND s.is_deleted = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, salaryAdd.getEmpId());
            preparedStatement.setInt(2, 0);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String resultDate = resultSet.getString("sa_date");
                getSalaryDateList.add(resultDate);
            }
        } catch (SQLException e) {
            System.out.println("数据库连接异常" + e.getMessage());
        }
        return getSalaryDateList;
    }

    //查询某员工薪资历史明细
    public static List<Salary> selectEmpSalaryDetail(String empId) {
        List<Salary> getSalaryDetailList = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection()) {
            String sql = "SELECT ROW_NUMBER() OVER(ORDER BY sa_id) AS rowNumber,s.sa_insurance,s.sa_actual,e.emp_name," +
                    "s.sa_date,s.sa_performance,s.sa_base FROM  ims_salary s LEFT JOIN  ims_employee e " +
                    "ON e.emp_id = s.emp_id WHERE s.emp_id = ? AND s.is_deleted = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, empId);
            preparedStatement.setInt(2, 0);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String rowNumber = resultSet.getString("rowNumber");
                double saInsurance = resultSet.getDouble("sa_insurance");
                double saActual = resultSet.getDouble("sa_actual");
                String empName = resultSet.getString("emp_name");
                String saDate = resultSet.getString("sa_date");
                double saPerformance = resultSet.getDouble("sa_performance");
                double saBase = resultSet.getDouble("sa_base");
                getSalaryDetailList.add(new Salary(rowNumber, saDate, saBase, saPerformance, saInsurance, saActual, empName));
            }
        } catch (SQLException e) {
            System.out.println("数据库连接异常" + e.getMessage());
        }
        return getSalaryDetailList;
    }

    //查询某年月薪资历史明细
    public static List<Salary> selectDateSalaryDetail(String dateInput) {
        List<Salary> getSalaryDetailList = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection()) {
            String sql = "SELECT ROW_NUMBER() OVER(ORDER BY sa_id) AS rowNumber,s.sa_insurance,s.sa_actual,e.emp_name," +
                    "s.sa_date,s.sa_performance,s.sa_base FROM  ims_salary s LEFT JOIN  ims_employee e " +
                    "ON e.emp_id = s.emp_id WHERE sa_date = ? AND s.is_deleted = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, dateInput);
            preparedStatement.setInt(2, 0);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String rowNumber = resultSet.getString("rowNumber");
                double saInsurance = resultSet.getDouble("sa_insurance");
                double saActual = resultSet.getDouble("sa_actual");
                String empName = resultSet.getString("emp_name");
                String saDate = resultSet.getString("sa_date");
                double saPerformance = resultSet.getDouble("sa_performance");
                double saBase = resultSet.getDouble("sa_base");
                getSalaryDetailList.add(new Salary(rowNumber, saDate, saBase, saPerformance, saInsurance, saActual, empName));
            }
        } catch (SQLException e) {
            System.out.println("数据库连接异常" + e.getMessage());
        }
        return getSalaryDetailList;
    }

    //判断薪资表中是否存在该该员工的薪资
    public static List<String> isExistEmpSalary(String empId) {
        List<String> getSalaryIdList = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection()) {
            String sql = "SELECT emp_id FROM ims_salary WHERE  is_deleted = ? AND emp_id =?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, empId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String resultId = resultSet.getString("emp_id");
                getSalaryIdList.add(resultId);
            }
        } catch (SQLException e) {
            System.out.println("数据库连接异常" + e.getMessage());
        }
        return getSalaryIdList;
    }

    //修改员工的薪资
    public static void updateEmpSalary(Salary salaryUpdate) {
        try (Connection connection = JDBCUtils.getConnection()) {
            String sql = "UPDATE ims_salary SET sa_base=?,sa_performance = ?,sa_insurance=?,sa_actual=? WHERE emp_id = ? AND sa_date = ? AND is_deleted = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1,salaryUpdate.getSaBase());
            preparedStatement.setDouble(2,salaryUpdate.getSaPerformance());
            preparedStatement.setDouble(3,salaryUpdate.getSaInsurance());
            preparedStatement.setDouble(4,salaryUpdate.getSaActual());
            preparedStatement.setString(5,salaryUpdate.getEmpId());
            preparedStatement.setString(6,TimeUtils.timeYM());
            preparedStatement.setInt(7,0);
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                System.out.println("************ 数据修改成功 ***************");
            } else {
                System.out.println("************ 数据修改失败 ***************");
            }
        } catch (SQLException e) {
            System.out.println("数据库连接异常" + e.getMessage());
        }
    }
    //删除员工薪资薪资
    public static void deleteEmpSalary(Salary salaryDelete) {
        try (Connection connection = JDBCUtils.getConnection()) {
            String sql = "UPDATE ims_salary SET is_deleted = ? WHERE emp_id = ? AND sa_date = ? AND is_deleted = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,1);
            preparedStatement.setString(2,salaryDelete.getEmpId());
            preparedStatement.setString(3,TimeUtils.timeYM());
            preparedStatement.setInt(4,0);
            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                System.out.println("************ 数据修改成功 ***************");
            } else {
                System.out.println("************ 数据修改失败 ***************");
            }
        } catch (SQLException e) {
            System.out.println("数据库连接异常" + e.getMessage());
        }
    }
}

