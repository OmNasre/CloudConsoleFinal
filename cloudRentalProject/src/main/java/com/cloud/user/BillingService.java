//package com.cloud.user;
//
//import java.util.List;
//
////package com.example.cloudconsole.service;
////
////import com.example.cloudconsole.entity.Billing;
////import com.example.cloudconsole.repository.BillingRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.cloud.entity.Billing;
//import com.cloud.entity.BillingDto;
//import com.cloud.entity.RegisteredInstance;
//import com.cloud.entity.User;
//import com.cloud.repo.BillingRepository;
//import com.cloud.repo.RegisteredInstanceRepository;
//import com.cloud.repo.UserRepository;
//
//@Service
//public class BillingService {
//
//    @Autowired
//    private BillingRepository billingRepository;
//    
//    @Autowired
//    private RegisteredInstanceRepository rip;
//    @Autowired
//    private UserRepository userrepo;
//
//    public List<Billing> getBillingByUserId(Long userId) {
//        return billingRepository.findByUserId(userId);
//    }
//
//    public Billing createBilling(BillingDto bill) {
//    	RegisteredInstance registeredInstance = rip.findById(bill.getInstanceid())
//                .orElseThrow(() -> new RuntimeException("Instance not found"));
//    	
//    	User user = userrepo.findById(bill.getUserid())
//                .orElseThrow(() -> new RuntimeException("Instance not found"));
//    	Billing billing = new Billing();
//    	billing.setInstance(registeredInstance);
//    	billing.setUser(user);
//    	billing.setBillingDate(bill.getBillingDate());
//    	billing.setTotalCost(bill.getTotalCost());
//    	billing.setUsageTimeHours(bill.getUsageTimeHours());
//        return billingRepository.save(billing);
//    }
//}
//
