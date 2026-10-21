package com.autorepairservicesystem.dao;

import com.autorepairservicesystem.model.ServiceJobEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for service jobs.
 */
public class ServiceJobDao {

    public boolean insert(ServiceJobEntity job) {
        String sql = "INSERT INTO service_jobs (customer_name, service_type, estimated_cost, status) "
                + "VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, job.getCustomerName());
            ps.setString(2, job.getServiceType());
            ps.setDouble(3, job.getEstimatedCost());
            ps.setString(4, job.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("ERROR: Could not save record - " + e.getMessage());
            return false;
        }
    }

    public List<ServiceJobEntity> findAll() {
        List<ServiceJobEntity> jobs = new ArrayList<>();
        String sql = "SELECT id, customer_name, service_type, estimated_cost, status "
                + "FROM service_jobs ORDER BY id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                jobs.add(new ServiceJobEntity(
                        rs.getInt("id"),
                        rs.getString("customer_name"),
                        rs.getString("service_type"),
                        rs.getDouble("estimated_cost"),
                        rs.getString("status")));
            }
        } catch (SQLException e) {
            System.out.println("ERROR: Could not fetch records - " + e.getMessage());
        }
        return jobs;
    }
}
