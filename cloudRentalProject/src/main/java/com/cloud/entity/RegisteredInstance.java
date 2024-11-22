package com.cloud.entity;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "registered_instances")
public class RegisteredInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long instanceId;

    @Column(nullable = false)
    private String instanceName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InstanceStatus instanceStatus;

    @ManyToOne
    @JoinColumn(name = "allocated_user_id", referencedColumnName = "id", nullable = true)
    private User allocatedUser;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    // Configuration fields
    @Column(nullable = false)
    private int cpuCores;

    @Column(nullable = false)
    private float memoryGb;

    @Column(nullable = false)
    private float storageGb;

    @Column(nullable = false)
    private String osType;
    
    @Column(nullable = true)
    private String InstanceUsername;

    @Column(nullable = false, unique = true)
    private String ipAddress;

    @Column(nullable = false)
    private String password;

    // Enum for status
    public enum InstanceStatus {
        FREE, ALLOCATED
    }

    // Constructors
    public RegisteredInstance() {
        super();
    }

  

    // Getters and Setters
    public Long getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Long instanceId) {
        this.instanceId = instanceId;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public InstanceStatus getInstanceStatus() {
        return instanceStatus;
    }

    public void setInstanceStatus(InstanceStatus instanceStatus) {
        this.instanceStatus = instanceStatus;
    }

    public User getAllocatedUser() {
        return allocatedUser;
    }

    public void setAllocatedUser(User allocatedUser) {
        this.allocatedUser = allocatedUser;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getCpuCores() {
        return cpuCores;
    }

    public void setCpuCores(int cpuCores) {
        this.cpuCores = cpuCores;
    }

    public float getMemoryGb() {
        return memoryGb;
    }

    public void setMemoryGb(float memoryGb) {
        this.memoryGb = memoryGb;
    }

    public float getStorageGb() {
        return storageGb;
    }

    public void setStorageGb(float storageGb) {
        this.storageGb = storageGb;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getInstanceUsername() {
		return InstanceUsername;
	}



	public void setInstanceUsername(String instanceUsername) {
		InstanceUsername = instanceUsername;
	}



	public RegisteredInstance(Long instanceId, String instanceName, InstanceStatus instanceStatus, User allocatedUser,
			Date createdAt, int cpuCores, float memoryGb, float storageGb, String osType, String instanceUsername,
			String ipAddress, String password) {
		super();
		this.instanceId = instanceId;
		this.instanceName = instanceName;
		this.instanceStatus = instanceStatus;
		this.allocatedUser = allocatedUser;
		this.createdAt = createdAt;
		this.cpuCores = cpuCores;
		this.memoryGb = memoryGb;
		this.storageGb = storageGb;
		this.osType = osType;
		InstanceUsername = instanceUsername;
		this.ipAddress = ipAddress;
		this.password = password;
	}



	@Override
    public String toString() {
        return "RegisteredInstance [instanceId=" + instanceId + ", instanceName=" + instanceName + ", instanceStatus=" + instanceStatus
                + ", allocatedUser=" + allocatedUser + ", createdAt=" + createdAt + ", cpuCores=" + cpuCores
                + ", memoryGb=" + memoryGb + ", storageGb=" + storageGb + ", osType=" + osType
                + ", ipAddress=" + ipAddress + ", password=" + password + "]";
    }
}
