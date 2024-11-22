package com.cloud.user;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.entity.InstanceAllocationHistory;
import com.cloud.entity.RegisteredInstance;
import com.cloud.entity.User;
import com.cloud.repo.InstanceAllocationHistoryRepository;
import com.cloud.repo.RegisteredInstanceRepository;
import com.cloud.repo.UserRepository;

@Service
public class InstanceService {

    @Autowired
    private RegisteredInstanceRepository registeredInstanceRepository;

    @Autowired
    private InstanceAllocationHistoryRepository instanceAllocationHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    // Save or update a registered instance
    public RegisteredInstance saveInstance(RegisteredInstance instance) {
        // Set default status if none is provided
        if (instance.getInstanceStatus() == null) {
            instance.setInstanceStatus(RegisteredInstance.InstanceStatus.FREE);
        }
        return registeredInstanceRepository.save(instance);
    }

    // Allocate an instance to a user
    public RegisteredInstance allocateInstance(Long instanceId, Long userId) {
        RegisteredInstance instance = registeredInstanceRepository.findById(instanceId)
                .orElseThrow(() -> new RuntimeException("Instance not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        instance.setAllocatedUser(user);
        instance.setInstanceStatus(RegisteredInstance.InstanceStatus.ALLOCATED);
        instance = registeredInstanceRepository.save(instance);

        // Record allocation history
        InstanceAllocationHistory history = new InstanceAllocationHistory();
        history.setInstance(instance);
        history.setAllocatedAt(new Date());  // Set allocation time to current time
        history.setTerminatedAt(null);  // No termination yet
        history.setBillAmount(0.0);  // Set default bill amount (can be updated later)
        history.setBilling(null);  // Bill ID will be set later after billing
        history.setAllocatedUser(user); // Set the user for allocation history

        instanceAllocationHistoryRepository.save(history);  // Save allocation history

        return instance;
    }

    // Get all free instances
    public List<RegisteredInstance> getFreeInstances() {
        return registeredInstanceRepository.findByInstanceStatus(RegisteredInstance.InstanceStatus.FREE);
    }

    // Get instances by user ID
    public List<RegisteredInstance> getInstancesByUserId(Long userId) {
        return registeredInstanceRepository.findByAllocatedUserId(userId);
    }

    // Update the status of an instance
    public RegisteredInstance updateInstanceStatus(Long id, String status, Long userId) {
        Optional<RegisteredInstance> instanceOpt = registeredInstanceRepository.findById(id);
        
        if (instanceOpt.isPresent()) {
            RegisteredInstance instance = instanceOpt.get();
            RegisteredInstance.InstanceStatus newStatus = RegisteredInstance.InstanceStatus.valueOf(status.toUpperCase());
            instance.setInstanceStatus(newStatus);
            
            // Record allocation history
            InstanceAllocationHistory history = new InstanceAllocationHistory();
            history.setInstance(instance);
            history.setAllocatedAt(null);  // No allocation time as it's terminated
            history.setTerminatedAt(null);  // No termination yet
            history.setBillAmount(0.0);  // Set default bill amount (can be updated later)
            history.setBilling(null);  // Bill ID will be set later after billing

            if ("ALLOCATED".equalsIgnoreCase(status)) {
                // Allocate the instance
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found"));
                instance.setAllocatedUser(user);
                history.setAllocatedUser(user); // Set allocated user in history

                history.setAllocatedAt(new Date());  // Set allocation time to current time
                instanceAllocationHistoryRepository.save(history);  // Save allocation history
            } else {
                // Clear allocation if status is not ALLOCATED
                instance.setAllocatedUser(null);
                history.setTerminatedAt(new Date());  // Set termination time to current time
                instanceAllocationHistoryRepository.save(history);  // Save termination history
            }
            
            return registeredInstanceRepository.save(instance);
        }
        
        return null;  // If the instance is not found
    }

    // Get a specific instance by ID
    public Optional<RegisteredInstance> getInstanceById(Long instanceId) {
        return registeredInstanceRepository.findById(instanceId);
    }

    // Get all instances
    public List<RegisteredInstance> getAllInstances() {
        return registeredInstanceRepository.findAll();
    }

    // Delete an instance
    public void deleteInstance(Long id) {
        registeredInstanceRepository.deleteById(id);
    }
}
