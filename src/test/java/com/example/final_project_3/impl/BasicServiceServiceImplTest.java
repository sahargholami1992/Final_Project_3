package com.example.final_project_3.impl;


import com.example.final_project_3.entity.BasicService;
import com.example.final_project_3.service.BasicServiceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class BasicServiceServiceImplTest {
    @Autowired
    private BasicServiceService basicServiceService;

    @Test
    void existByServiceName_WhenServiceExists_ShouldReturnTrue() {
        // Arrange
        String serviceName = "building decoration";
        BasicService basicService = new BasicService();
        basicService.setServiceName(serviceName);
        basicServiceService.saveOrUpdate(basicService);

        // Act
        boolean result = basicServiceService.existByServiceName(serviceName);

        // Assert
        assertTrue(result);
    }

    @Test
    void existByServiceName_WhenServiceDoesNotExist_ShouldReturnFalse() {
        // Arrange
        String serviceName = "NonExistentService";
        // Act
        boolean result = basicServiceService.existByServiceName(serviceName);
        // Assert
        assertFalse(result);
    }

    @Test
    void findByServiceName_WhenServiceExists_ShouldReturnBasicService() {
        // Arrange
        String serviceName = "new basic serive";
        BasicService expectedService = new BasicService();
        expectedService.setServiceName(serviceName);
        basicServiceService.saveOrUpdate(expectedService);
        // Act
        BasicService result = basicServiceService.findByServiceName(serviceName);
        // Assert
        assertNotNull(result);
    }

    @Test
    void findByServiceName_WhenServiceDoesNotExist_ShouldThrowNullPointerException() {
        // Arrange
        String serviceName = "NonExistentService";

        // Act and Assert
        assertThrows(NullPointerException.class, () -> basicServiceService.findByServiceName(serviceName));
    }

}
