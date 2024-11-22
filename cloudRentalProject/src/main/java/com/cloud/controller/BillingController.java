package com.cloud.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.entity.Billing;
import com.cloud.entity.BillingUpdateRequest;
import com.cloud.entity.InstanceAllocationHistory;
import com.cloud.entity.RegisteredInstance;
import com.cloud.entity.User;
import com.cloud.repo.BillingRepository;
import com.cloud.repo.InstanceAllocationHistoryRepository;
import com.cloud.repo.RegisteredInstanceRepository;
import com.cloud.repo.UserRepository;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/allocation-history")
@CrossOrigin(origins = "http://localhost:4200")
public class BillingController {

    private final InstanceAllocationHistoryRepository allocationHistoryRepository;
    private final BillingRepository billingRepository;
    private final UserRepository userRepository;
    private final RegisteredInstanceRepository instanceRepository;

    @Autowired
    public BillingController(
        InstanceAllocationHistoryRepository allocationHistoryRepository,
        BillingRepository billingRepository,
        UserRepository userRepository,
        RegisteredInstanceRepository instanceRepository
    ) {
        this.allocationHistoryRepository = allocationHistoryRepository;
        this.billingRepository = billingRepository;
        this.userRepository = userRepository;
        this.instanceRepository = instanceRepository;
    }

    @PutMapping("/update-billing")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public String updateBillingAndTerminate(@RequestBody BillingUpdateRequest request) {
        Long userId = request.getUserId();
        Long instanceId = request.getInstanceId();
        double billAmount = request.getBillAmount();
        float usageTimeHours = request.getUsageTimeHours();

        // Fetch user and instance
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found for ID: " + userId));
        RegisteredInstance instance = instanceRepository.findById(instanceId)
                .orElseThrow(() -> new RuntimeException("Instance not found for ID: " + instanceId));

        // Fetch allocation history
        InstanceAllocationHistory allocationHistory = allocationHistoryRepository.findByInstance_InstanceId(instanceId)
                .orElseThrow(() -> new RuntimeException("Allocation history not found for Instance ID: " + instanceId));

        // Update termination time and billing amount in allocation history
        allocationHistory.setTerminatedAt(new Date());
        allocationHistory.setBillAmount(billAmount);
        allocationHistoryRepository.save(allocationHistory);

        // Create or update billing record
        Billing billing = allocationHistory.getBilling();
        if (billing == null) {
            billing = new Billing();
        }
        billing.setUser(user);
        billing.setInstance(instance);
        billing.setUsageTimeHours(usageTimeHours);
        billing.setTotalCost(billAmount);
        billing.setBillingDate(new Date());

        // Save billing and link to allocation history
        billing = billingRepository.save(billing);
        allocationHistory.setBilling(billing);
        allocationHistoryRepository.save(allocationHistory);

        // Nullify allocatedUser and update the status to FREE
        instance.setAllocatedUser(null);
        instance.setInstanceStatus(RegisteredInstance.InstanceStatus.FREE);
        instanceRepository.save(instance);  // Save the updated instance

        return "Billing and termination updated successfully for Instance ID: " + instanceId;
    }


    @GetMapping("/allocation-time/{instanceId}")
    @ResponseStatus(HttpStatus.OK)
    public Date findAllocationTimeByInstanceId(@PathVariable Long instanceId) {
        System.out.println("In Billing Controller");
        return allocationHistoryRepository.findByInstance_InstanceId(instanceId)
                .map(InstanceAllocationHistory::getAllocatedAt)
                .orElseThrow(() -> new RuntimeException("Allocation history not found for Instance ID: " + instanceId));
    }

    
}
