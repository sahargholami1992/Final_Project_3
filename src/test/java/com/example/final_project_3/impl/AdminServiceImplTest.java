package com.example.final_project_3.impl;


import com.example.final_project_3.entity.Expert;
import com.example.final_project_3.entity.SubService;
import com.example.final_project_3.entity.enumaration.ExpertStatus;
import com.example.final_project_3.entity.enumaration.Permissions;
import com.example.final_project_3.service.AdminService;
import com.example.final_project_3.service.ExpertService;
import com.example.final_project_3.service.dto.ExpertRegisterDto;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceImplTest {

    @Autowired
    private ExpertService expertService;

    @Autowired
    private AdminService adminService;

    @Test
    @Order(1)
    void saveService_WhenServiceDoesNotExist_ShouldSaveService() {
        // Arrange
        String serviceName = "existingService";
        adminService.saveService(serviceName);
        String newServiceName = "newService";
        adminService.saveService(newServiceName);
        // Act and Assert
        assertTrue(adminService.existByServiceName(newServiceName));
    }

    @Test
    @Order(2)
    void saveService_WhenServiceExists_ShouldThrowIllegalArgumentException() {
        // Arrange
        String serviceName = "existingService";
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> adminService.saveService(serviceName));
    }

    @Test
    @Order(3)
    void saveSubService_WhenSubServiceDoNotExist_ShouldSaveSubService() {
        // Arrange
        String serviceName = "existService";
        adminService.saveService(serviceName);
        SubService subService = new SubService();
        subService.setSubServiceName("newSubService");
        subService.setBasePrice(100);
        subService.setDescription("Description");
        // Act
        adminService.saveSubService(serviceName, subService);
        // Assert
        assertEquals(1,adminService.ShowAllSubService().size());
    }

    @Test
    @Order(4)
    void saveSubService_WhenServiceDoesNotExist_ShouldThrowIllegalArgumentException() {
        // Arrange
        String serviceName = "nonExistingService";
        SubService subService = new SubService();
        subService.setSubServiceName("newSubService");
        subService.setBasePrice(100);
        subService.setDescription("Description");
        // Act and Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> adminService.saveSubService(serviceName, subService));
        assertEquals(" this service not exist or duplicate subService name ", exception.getMessage());
    }

    @Test
    @Order(5)
    void saveSubService_WhenSubServiceExists_ShouldThrowIllegalArgumentException() {
        // Arrange
        String serviceName = "existingService";
        SubService subService = new SubService();
        subService.setSubServiceName("subService");
        subService.setBasePrice(500);
        subService.setDescription("Descriptions");
        adminService.saveSubService(serviceName, subService);
        SubService newSubService = new SubService();
        newSubService.setSubServiceName("subService");
        // Act and Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> adminService.saveSubService(serviceName, newSubService));
        assertEquals(" this service not exist or duplicate subService name ", exception.getMessage());
    }

    @Test
    @Order(6)
    void deleteExpertFromSubService_WhenExpertAndSubServiceExistAndExpertStatusIsAccepted_ShouldDeleteExpert() throws IOException {
        // Arrange
        String serviceName = "newService";
        SubService subService = new SubService();
        subService.setSubServiceName("anotherSubService");
        subService.setBasePrice(100);
        subService.setDescription("Description");
        // Act
        adminService.saveSubService(serviceName, subService);
        ExpertRegisterDto expertRegisterDto = new ExpertRegisterDto("John", "Doe", "john.doe@exampleeee.com", "Passwor1", Files.readAllBytes(Paths.get("C:\\Users\\ZAITOON.iR\\IdeaProjects\\Final_Project_2\\src\\main\\resources\\3.jpg")));
        Expert expert = expertService.registerExpert(expertRegisterDto);
        adminService.changeExpertStatus(expert);
        adminService.saveExpertForSubService(subService,expert);
        // Act
        adminService.deleteExpertFromSubService(subService, expert);
        assertEquals(0,subService.getExperts().size());

    }

    @Test
    @Order(7)
    void deleteExpertFromSubService_WhenExpertIsNull_ShouldThrowNullPointerException() {
        // Arrange
        SubService subService = new SubService();
        Expert expert = null;
        // Act and Assert
        NullPointerException exception = assertThrows(NullPointerException.class, () -> adminService.deleteExpertFromSubService(subService, expert));
        assertEquals(" Expert or SubService not found in the database or ExpertStatus is not equals ACCEPTED  ", exception.getMessage());
    }

    @Test
    @Order(8)
    void deleteExpertFromSubService_WhenExpertStatusIsNotAccepted_ShouldThrowNullPointerException() {
        // Arrange
        Expert expert = new Expert();
        expert.setExpertStatus(ExpertStatus.AWAITING_CONFIRMATION);
        SubService subService = new SubService();
        // Act and Assert
        NullPointerException exception = assertThrows(NullPointerException.class, () -> adminService.deleteExpertFromSubService(subService, expert));
        assertEquals(" Expert or SubService not found in the database or ExpertStatus is not equals ACCEPTED  ", exception.getMessage());
    }

    @Test
    @Order(9)
    void saveExpertForSubService_WhenExpertAndSubServiceExistAndExpertStatusIsAccepted_ShouldSaveExpert() throws IOException {
        // Arrange
        ExpertRegisterDto expertRegisterDto = new ExpertRegisterDto("John", "Doe", "john.doe@example.com", "Passwor1", Files.readAllBytes(Paths.get("C:\\Users\\ZAITOON.iR\\IdeaProjects\\Final_Project_2\\src\\main\\resources\\3.jpg")));
        Expert expert = expertService.registerExpert(expertRegisterDto);
        adminService.changeExpertStatus(expert);
        String serviceName = "existingService";
        String saveSubService = "saveSubService";
        SubService subService = new SubService();
        subService.setSubServiceName(saveSubService);
        adminService.saveSubService(serviceName,subService);
        // Act
        adminService.saveExpertForSubService(subService, expert);
        // Assert
        assertEquals(1,subService.getExperts().size());
    }

    @Test
    @Order(10)
    void saveExpertForSubService_WhenExpertIsNull_ShouldThrowNullPointerException() {
        // Arrange
        SubService subService = new SubService();
        Expert expert = null;
        // Act and Assert
        NullPointerException exception = assertThrows(NullPointerException.class, () -> adminService.saveExpertForSubService(subService, expert));
        assertEquals(" Expert or SubService not found in the database or ExpertStatus is not equals ACCEPTED  ", exception.getMessage());
    }

    @Test
    @Order(11)
    void saveExpertForSubService_WhenExpertStatusIsNotAccepted_ShouldThrowNullPointerException() {
        // Arrange
        Expert expert = new Expert();
        expert.setExpertStatus(ExpertStatus.AWAITING_CONFIRMATION);
        SubService subService = new SubService();
        // Act and Assert
        NullPointerException exception = assertThrows(NullPointerException.class, () -> adminService.saveExpertForSubService(subService, expert));
        assertEquals(" Expert or SubService not found in the database or ExpertStatus is not equals ACCEPTED  ", exception.getMessage());

    }

    @Test
    @Order(12)
    void changeExpertStatus_ShouldChangeExpertStatusAndPermission() throws IOException {
        // Arrange
        ExpertRegisterDto expertRegisterDto = new ExpertRegisterDto("John", "Doe", "john.doe@example1.com", "Passwor1", Files.readAllBytes(Paths.get("C:\\Users\\ZAITOON.iR\\IdeaProjects\\Final_Project_2\\src\\main\\resources\\3.jpg")));
        Expert expert = expertService.registerExpert(expertRegisterDto);
        // Act
        adminService.changeExpertStatus(expert);

        // Assert
        assertEquals(ExpertStatus.ACCEPTED, expert.getExpertStatus());
        assertEquals(Permissions.ACCEPTED, expert.getPermissions());
    }
}