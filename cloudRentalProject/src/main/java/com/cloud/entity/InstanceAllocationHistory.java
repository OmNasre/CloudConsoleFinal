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
@Table(name = "instance_allocation_history")
public class InstanceAllocationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    @ManyToOne
    @JoinColumn(name = "instance_id", referencedColumnName = "instanceId", nullable = false)
    private RegisteredInstance instance;

    @ManyToOne
    @JoinColumn(name = "allocated_user_id", referencedColumnName = "id", nullable = false)
    private User allocatedUser;

    @Column(name = "allocated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date allocatedAt;

    @Column(name = "terminated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date terminatedAt;

    @Column(name = "bill_amount")
    private double billAmount;

    @ManyToOne
    @JoinColumn(name = "billing_id", referencedColumnName = "billingId", nullable = true)
    private Billing billing;

    // Constructors
    public InstanceAllocationHistory() {
        super();
    }

    public InstanceAllocationHistory(Long historyId, RegisteredInstance instance, User allocatedUser, Date allocatedAt,
                                     Date terminatedAt, double billAmount, Billing billing) {
        this.historyId = historyId;
        this.instance = instance;
        this.allocatedUser = allocatedUser;
        this.allocatedAt = allocatedAt;
        this.terminatedAt = terminatedAt;
        this.billAmount = billAmount;
        this.billing = billing;
    }

    // Getters and Setters
    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public RegisteredInstance getInstance() {
        return instance;
    }

    public void setInstance(RegisteredInstance instance) {
        this.instance = instance;
    }

    public User getAllocatedUser() {
        return allocatedUser;
    }

    public void setAllocatedUser(User allocatedUser) {
        this.allocatedUser = allocatedUser;
    }

    public Date getAllocatedAt() {
        return allocatedAt;
    }

    public void setAllocatedAt(Date allocatedAt) {
        this.allocatedAt = allocatedAt;
    }

    public Date getTerminatedAt() {
        return terminatedAt;
    }

    public void setTerminatedAt(Date terminatedAt) {
        this.terminatedAt = terminatedAt;
    }

    public double getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(double billAmount) {
        this.billAmount = billAmount;
    }

    public Billing getBilling() {
        return billing;
    }

    public void setBilling(Billing billing) {
        this.billing = billing;
    }

    @Override
    public String toString() {
        return "InstanceAllocationHistory [historyId=" + historyId + ", instance=" + instance + ", allocatedUser=" + allocatedUser
                + ", allocatedAt=" + allocatedAt + ", terminatedAt=" + terminatedAt + ", billAmount=" + billAmount
                + ", billing=" + billing + "]";
    }
}
