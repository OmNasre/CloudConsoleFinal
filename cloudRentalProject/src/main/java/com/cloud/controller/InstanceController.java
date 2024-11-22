package com.cloud.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.entity.RegisteredInstance;
import com.cloud.entity.User;
import com.cloud.user.InstanceService;

@RestController
@RequestMapping("/api/instances")
public class InstanceController {

    @Autowired
    private InstanceService instanceService;
    
	@Autowired
	private JavaMailSender emailSender;

    // Get all registered instances
    @GetMapping("/all")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<RegisteredInstance> getAllInstances() {
        return instanceService.getAllInstances();
    }

    // Get a specific registered instance by ID
    @GetMapping("/{instanceId}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<RegisteredInstance> getInstanceById(@PathVariable("instanceId") Long instanceId) {
        return instanceService.getInstanceById(instanceId)
                              .map(ResponseEntity::ok)
                              .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Save or update a registered instance
    @PostMapping("/save")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<RegisteredInstance> saveInstance(@RequestBody RegisteredInstance instance) {
        try {
            RegisteredInstance savedInstance = instanceService.saveInstance(instance);
            return ResponseEntity.ok(savedInstance);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Delete a registered instance
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteInstance(@PathVariable Long id) {
        instanceService.deleteInstance(id);
        return ResponseEntity.ok().build();
    }

    // Get all free registered instances
    @GetMapping("/free")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<RegisteredInstance>> getFreeInstances() {
        return ResponseEntity.ok(instanceService.getFreeInstances());
    }

    // Get instances allocated to a specific user
    @GetMapping("/allocate/{userId}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<RegisteredInstance>> getInstancesByUserId(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(instanceService.getInstancesByUserId(userId));
    }

    // Update the status of a registered instance (Allocated/Free)
 // Update the status of a registered instance and assign it to a user
    @PutMapping("/status/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<RegisteredInstance> updateInstanceStatus(
            @PathVariable Long id, 
            @RequestParam String status, 
            @RequestParam Long userId) {
        RegisteredInstance updatedInstance = instanceService.updateInstanceStatus(id, status, userId);
        return updatedInstance != null ? ResponseEntity.ok(updatedInstance) : ResponseEntity.notFound().build();
    }


    // Generate OTP for a registered instance
 // Generate OTP for a registered instance and send it to the user's email
 // Updated generateOtp method in InstanceController
    @GetMapping("/generate-otp/{instanceId}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Map<String, String>> generateOtp(@PathVariable("instanceId") Long instanceId) {
        // Retrieve instance and check if user is allocated
        RegisteredInstance instance = instanceService.getInstanceById(instanceId)
                                                     .orElse(null);
        if (instance == null || instance.getAllocatedUser() == null) {
            return ResponseEntity.notFound().build(); // No instance or user found
        }

        User allocatedUser = instance.getAllocatedUser();
        String userEmail = allocatedUser.getEmail();
        String userName = allocatedUser.getUsername();

        // Generate OTP
        int otp = new Random().nextInt(999999);
        String otpString = String.format("%06d", otp);

        // Email message details
        final String subjectOfEmail = "Your OTP for VM Access";
        final String msgOfEmail = String.format("Hello %s,\n\nYour OTP for accessing instance %s is: %s\n\nThank you for using our platform!",
                                                userName, instance.getInstanceName(), otpString);

        // Send email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("omnasre45@gmail.com"); // Set your email
        message.setTo(userEmail);
        message.setSubject(subjectOfEmail);
        message.setText(msgOfEmail);

        emailSender.send(message);

        // Response
        Map<String, String> response = new HashMap<>();
        response.put("otp", otpString);
        return ResponseEntity.ok(response);
    }

 // Add to InstanceController.java

 // Send credentials to the allocated user's email
    @GetMapping("/send-credentials/{instanceId}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Map<String, String>> sendCredentialsToUser(@PathVariable("instanceId") Long instanceId) {
        // Retrieve the instance by ID
        RegisteredInstance instance = instanceService.getInstanceById(instanceId).orElse(null);
        if (instance == null || instance.getAllocatedUser() == null) {
            return ResponseEntity.notFound().build(); // No instance or user found
        }

        // Get the allocated user's information
        User allocatedUser = instance.getAllocatedUser();
        String userEmail = allocatedUser.getEmail();
        String userName = allocatedUser.getUsername();

        // Extract instance credentials
        String ipAddress = instance.getIpAddress();
        String password = instance.getPassword();
        String instanceName = instance.getInstanceName();
        String instanceUsername = instance.getInstanceUsername();

        // Email message details
        final String subjectOfEmail = "Your Virtual Machine Access Credentials";
        final String msgOfEmail = String.format(
            "Dear %s,\n\n" +
            "Congratulations! Your virtual machine instance '%s' has been successfully allocated. Below are the details to access your instance:\n\n" +
            "Instance Name: %s\n" +
            "IP Address: %s\n" +
            "Username: %s\n" +
            "Password: %s\n\n" +
            "### Steps to Connect to Your Instance ###\n" +
            "1. Open a terminal on your system.\n" +
            "2. Use the following SSH command to connect to your instance:\n" +
            "\n" +
            "   ssh %s@%s\n" +
            "\n" +
            "3. Enter the password provided above when prompted.\n\n" +
            "Note:\n" +
            "- Ensure you replace 'user' with the username provided above.\n" +
            "- Make sure your firewall allows SSH access to this IP address.\n" +
            "- If this is your first time connecting, you may need to accept the host key fingerprint.\n\n" +
            "If you encounter any issues, please reach out to our support team for assistance.\n\n" +
            "Thank you for choosing our platform to manage your virtual machine needs.\n\n" +
            "Best regards,\n" +
            "The QuickCloud Team",
            userName, instanceName, instanceName, ipAddress, instanceUsername, password, instanceUsername, ipAddress
        );

        // Prepare the email message
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("omnasre45@gmail.com"); // Set your email here
        message.setTo(userEmail);
        message.setSubject(subjectOfEmail);
        message.setText(msgOfEmail);

        // Send the email
        emailSender.send(message);

        // Response
        Map<String, String> response = new HashMap<>();
        response.put("message", "Credentials sent to the user’s email successfully.");
        return ResponseEntity.ok(response);
    }


    // Generate OTP without an instance ID
    @GetMapping("/generate-otp")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Map<String, String>> generateOtp() {
        int otp = new Random().nextInt(999999);
        Map<String, String> response = new HashMap<>();
        response.put("otp", String.format("%06d", otp));
        return ResponseEntity.ok(response);
    }
}
