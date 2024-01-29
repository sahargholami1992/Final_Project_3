package com.example.final_project_3.impl;


import com.example.final_project_3.entity.Expert;
import com.example.final_project_3.service.AdminService;
import com.example.final_project_3.service.ExpertService;
import com.example.final_project_3.service.dto.ExpertRegisterDto;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ExpertServiceImplTest {
    @Autowired
    private ExpertService expertService;
    @Autowired
    private AdminService adminService;
    private ExpertRegisterDto expertRegisterDto;

    @Test
    @Order(1)
    void registerExpert_ShouldSaveExpert() throws IOException {
        // Arrange
        expertRegisterDto = new ExpertRegisterDto("John","Doe","john.doe@example.com","Passwor1", Files.readAllBytes(Paths.get("C:\\Users\\ZAITOON.iR\\IdeaProjects\\Final_Project_2\\src\\main\\resources\\3.jpg")));

        // Act
        Expert expert = expertService.registerExpert(expertRegisterDto);
        // Assert

       assertTrue(expertService.existByEmail(expertRegisterDto.getEmail()));
    }


    @Test
    @Order(2)
    void logIn_WhenValidCredentials_ShouldReturnExpert() throws IOException {
        // Arrange
        expertRegisterDto = new ExpertRegisterDto("John","Doe","john.doe@example123.com","Passwor1", Files.readAllBytes(Paths.get("C:\\Users\\ZAITOON.iR\\IdeaProjects\\Final_Project_2\\src\\main\\resources\\3.jpg")));
        Expert expert1 = expertService.registerExpert(expertRegisterDto);
        adminService.changeExpertStatus(expert1);
        String email = "john.doe@example123.com";
        String password = "Passwor1";

        // Act
        expertService.logIn(email, password);

        // Assert
        assertNotNull(expert1);
    }

    @Test
    @Order(3)
    void logIn_WhenInvalidCredentials_ShouldThrowException() {
        // Arrange
        String email = "john.doe@example1.com";
        String password = "password";

        // Act & Assert
        assertThrows(NoResultException.class, () -> expertService.logIn(email, password));
    }

    @Test
    @Order(4)
    void logIn_WhenInvalidPassword_ShouldThrowException() {
        // Arrange
        String email = "john.doe@example.com";
        String password = "password";
        // Act & Assert
        assertThrows(RuntimeException.class, () -> expertService.logIn(email, password));
    }

    @Test
    @Order(5)
    void readsImage_WhenImageExists_ShouldReturnImageData() {
        // Arrange
        String imageName = "3.jpg";
        InputStream inputStream = new ByteArrayInputStream(new byte[1024]);

        // Act
        byte[] result = expertService.readsImage(imageName);

        // Assert
        assertNotNull(result);
        Assertions.assertEquals(5154, result.length);
    }

    @Test
    @Order(6)
    void readsImage_WhenImageDoesNotExist_ShouldReturnNull() {
        // Arrange
        String imageName = "nonexistent.jpg";

        // Act
        byte[] result = expertService.readsImage(imageName);

        // Assert
        assertNull(result);
    }

    @Test
    @Order(7)
    void saveImageToFile_WhenSuccessfulSave_ShouldReturnTrue() throws IOException {
        // Arrange
        expertRegisterDto = new ExpertRegisterDto("John","Doe","john.doe@example123321.com","Passwor1", Files.readAllBytes(Paths.get("C:\\Users\\ZAITOON.iR\\IdeaProjects\\Final_Project_2\\src\\main\\resources\\3.jpg")));
        Expert expert2 = expertService.registerExpert(expertRegisterDto);
        byte[] imageData = expert2.getProfileImage();
        String outputPath = "output.jpg";

        // Act
        boolean result = expertService.saveImageToFile(imageData, outputPath);

        // Assert
        assertTrue(result);
    }

    @Test
    @Order(8)
    void saveImageToFile_WhenFailedSave_ShouldReturnFalse() {
        // Arrange
        byte[] imageData = new byte[]{1, 2, 3};
        String outputPath = "nonexistent/folder/output.jpg";

        // Act
        boolean result = expertService.saveImageToFile(imageData, outputPath);

        // Assert
        assertFalse(result);
    }
    @Test
    @Order(9)
    public void testChangePassword() throws IOException {
        // Create a user
        // Arrange
        expertRegisterDto = new ExpertRegisterDto("John","Doe","john1.doe@example.com","oldPass1", Files.readAllBytes(Paths.get("C:\\Users\\ZAITOON.iR\\IdeaProjects\\Final_Project_2\\src\\main\\resources\\3.jpg")));

        // Act
        expertService.registerExpert(expertRegisterDto);
        Expert expert = expertService.changePassword("john1.doe@example.com", "newPass1");
        // Assert
        Assertions.assertEquals("newPass1", expert.getPassword());
    }
}
