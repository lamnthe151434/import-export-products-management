/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal.partner;

import dal.DBContext;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.partner.Customer;

/**
 *
 * @author ADMIN
 */
public class CustomerDBContext extends DBContext {

    public ArrayList<Customer> getCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            String sql = "SELECT [Customer_ID]\n"
                    + "      ,[Customer_Name]\n"
                    + "      ,[Address]\n"
                    + "      ,[Phone]\n"
                    + "      ,[DOB]\n"
                    + "      ,[Gender]\n"
                    + "      ,[Description]\n"
                    + "  FROM [dbo].[Customer]";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String phone = rs.getString("Phone");
                Date dob = rs.getDate("DOB");
                boolean gender = rs.getBoolean("Gender");
                String description = rs.getString("Description");
                Customer c = new Customer(customerID, customerName, gender, dob,
                        phone, address, description);
                customers.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return customers;
    }

    public ArrayList<Customer> getCustomers(int pageIndex, int pageSize, String searchKey, String orderBy) {
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            String sql = "SELECT [Customer_ID]\n"
                    + "      ,[Customer_Name]\n"
                    + "      ,[Address]\n"
                    + "      ,[Phone]\n"
                    + "      ,[DOB]\n"
                    + "      ,[Gender]\n"
                    + "      ,[Description] FROM\n"
                    + " (SELECT *, ROW_NUMBER() OVER (" + orderBy+ ") as row_index FROM [Customer]\n"
                    + " WHERE [Customer_Name] like '%" + searchKey
                    + "%' OR Cast(Customer_ID as NVARCHAR(50)) like '%" + searchKey
                    + "%') tb\n"
                    + "  WHERE row_index >=(?-1)*?+1 AND row_index <= ?*?";
            System.out.println(sql);
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, pageIndex);
            stm.setInt(2, pageSize);
            stm.setInt(3, pageIndex);
            stm.setInt(4, pageSize);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String phone = rs.getString("Phone");
                Date dob = rs.getDate("DOB");
                boolean gender = rs.getBoolean("Gender");
                String description = rs.getString("Description");
                Customer c = new Customer(customerID, customerName, gender, dob,
                        phone, address, description);
                customers.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(customers.size());
        return customers;
    }

    public Customer getCustomer(int customerID) {
        try {
            String sql = "SELECT [Customer_ID]\n"
                    + "      ,[Customer_Name]\n"
                    + "      ,[Address]\n"
                    + "      ,[Phone]\n"
                    + "      ,[DOB]\n"
                    + "      ,[Gender]\n"
                    + "      ,[Description]\n"
                    + "  FROM [dbo].[Customer] WHERE [Customer_ID] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, customerID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String phone = rs.getString("Phone");
                Date dob = rs.getDate("DOB");
                boolean gender = rs.getBoolean("Gender");
                String description = rs.getString("Description");
                Customer customer = new Customer(customerID, customerName, gender,
                        dob, phone, address, description);
                return customer;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void insertCustomer(Customer customer) {
        PreparedStatement stm = null;
        try {
            String sql = "INSERT INTO [dbo].[Customer]\n"
                    + "           ([Customer_Name]\n"
                    + "           ,[Address]\n"
                    + "           ,[Phone]\n"
                    + "           ,[DOB]\n"
                    + "           ,[Gender]\n"
                    + "           ,[Description])\n"
                    + "     VALUES\n"
                    + "           (?,?,?,?,?,?)";
//            connection.setAutoCommit(false);
            stm = connection.prepareStatement(sql);
            stm.setString(1, customer.getCustomerName());
            stm.setString(2, customer.getAddress());
            stm.setString(3, customer.getPhone());
            stm.setDate(4, customer.getDob());
            stm.setBoolean(5, customer.isGender());
            stm.setString(6, customer.getDescription());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
//            try {
//                if (stm != null) {
//                    stm.close();
//                }
//                connection.setAutoCommit(true);
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
//            }

        }
    }

    public void deleteCustomer(int customerID) {
        try {
            String sql = "DELETE FROM [dbo].[Customer]\n"
                    + "WHERE [Customer_ID] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, customerID);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateCustomer(Customer customer) {
        PreparedStatement stm = null;
        try {
            String sql = "UPDATE [dbo].[Customer]\n"
                    + "   SET [Customer_Name] = ?\n"
                    + "      ,[Address] = ?\n"
                    + "      ,[Phone] = ?\n"
                    + "      ,[DOB] = ?\n"
                    + "      ,[Gender] = ?\n"
                    + "      ,[Description] = ?\n"
                    + " WHERE [Customer_ID] = ?";
            connection.setAutoCommit(false);
            stm = connection.prepareStatement(sql);
            stm.setString(1, customer.getCustomerName());
            stm.setString(2, customer.getAddress());
            stm.setString(3, customer.getPhone());
            stm.setDate(4, customer.getDob());
            stm.setBoolean(5, customer.isGender());
            stm.setString(6, customer.getDescription());
            stm.setInt(7, customer.getCustomerID());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                connection.setAutoCommit(true);
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public int getTotalRecord() {
        String sql = "SELECT COUNT(*) AS [Total] FROM [Customer]";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
}
