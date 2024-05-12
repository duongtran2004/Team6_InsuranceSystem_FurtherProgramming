package org.example.insurancemanagementapplication.Controller.CreationAndUpdatePageController;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class CreationAndUpdatePageControllerClaimTest {

    @Test
    void createMode() throws IOException {
        //check upload file
        File shrekFile = new File("src/main/resources/org/example/insurancemanagementapplication/SampleImage/shrek.jpg");

        Files.readAllBytes(Path.of(shrekFile.getPath()));
    }
}