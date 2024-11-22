package com.cloud.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "billing")
public class Billing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billingId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "instance_id", nullable = false)
    private RegisteredInstance instance;

    @Column(nullable = false)
    private float usageTimeHours;

    @Column(nullable = false)
    private double totalCost;
    

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date billingDate;

	public Long getBillingId() {
		return billingId;
	}

	public void setBillingId(Long billingId) {
		this.billingId = billingId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public RegisteredInstance getInstance() {
		return instance;
	}

	public void setInstance(RegisteredInstance instance) {
		this.instance = instance;
	}

	public float getUsageTimeHours() {
		return usageTimeHours;
	}

	public void setUsageTimeHours(float usageTimeHours) {
		this.usageTimeHours = usageTimeHours;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public Date getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(Date billingDate) {
		this.billingDate = billingDate;
	}

	public Billing(Long billingId, User user, RegisteredInstance instance, float usageTimeHours, double totalCost,
			Date billingDate) {
		super();
		this.billingId = billingId;
		this.user = user;
		this.instance = instance;
		this.usageTimeHours = usageTimeHours;
		this.totalCost = totalCost;
		this.billingDate = billingDate;
	}

	public Billing() {
		super();
		// TODO Auto-generated constructor stub
	}

    // Getters and Setters
    
}
