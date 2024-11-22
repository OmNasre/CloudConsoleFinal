package com.cloud.entity;

public class BillingUpdateRequest {
    private Long userId;
    private Long instanceId;
    private double billAmount;
    private float usageTimeHours;
    // Getters and setters
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(Long instanceId) {
		this.instanceId = instanceId;
	}
	public double getBillAmount() {
		return billAmount;
	}
	public void setBillAmount(double billAmount) {
		this.billAmount = billAmount;
	}
	public float getUsageTimeHours() {
		return usageTimeHours;
	}
	public void setUsageTimeHours(float usageTimeHours) {
		this.usageTimeHours = usageTimeHours;
	}
}
