package com.autorepairservicesystem.model;

/**
 * Entity representing a vehicle service job (5 attributes).
 */
public class ServiceJobEntity {

    private int id;
    private String customerName;
    private String serviceType;
    private double estimatedCost;
    private String status;

    public ServiceJobEntity(int id, String customerName, String serviceType,
                            double estimatedCost, String status) {
        this.id = id;
        this.customerName = customerName;
        this.serviceType = serviceType;
        this.estimatedCost = estimatedCost;
        this.status = status;
    }

    // Used when creating a new job; the ID is assigned automatically by the database.
    public ServiceJobEntity(String customerName, String serviceType,
                            double estimatedCost, String status) {
        this(0, customerName, serviceType, estimatedCost, status);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public double getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
