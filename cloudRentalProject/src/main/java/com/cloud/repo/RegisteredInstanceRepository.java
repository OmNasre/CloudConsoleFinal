package com.cloud.repo;

import java.util.List;

//package com.example.cloudconsole.repository;

//import com.example.cloudconsole.entity.RegisteredInstance;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cloud.entity.RegisteredInstance;

public interface RegisteredInstanceRepository extends JpaRepository<RegisteredInstance, Long> {
    List<RegisteredInstance> findByInstanceStatus(RegisteredInstance.InstanceStatus status);



	List<RegisteredInstance> findByAllocatedUserId(Long userId);
//	List<RegisteredInstance> findByInstanceStatus(RegisteredInstance.InstanceStatus status);


}