package com.cloud.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cloud.entity.InstanceAllocationHistory;

public interface InstanceAllocationHistoryRepository extends JpaRepository<InstanceAllocationHistory, Long> {
    Optional<InstanceAllocationHistory> findByInstance_InstanceId(Long instanceId);
}
