package com.kodilla.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * User: Z6PWA
 * Date: 24.10.2023
 */
@RestController
public class FileController
{
  private final IntegrationFlow fileIntegrationFlow;
  @Value("${inputDirectory}")
  private String inputDirectory;
  @Value("${outputFilePath}")
  private String outputFilePath;

  public FileController(IntegrationFlow fileIntegrationFlow)
  {
    this.fileIntegrationFlow = fileIntegrationFlow;
  }

  @PostMapping("/create")
  public ResponseEntity<String> createFile(@RequestParam String fileName)
  {
    try
    {
      File file = new File(inputDirectory + fileName).getAbsoluteFile();
      file.createNewFile();
      return ResponseEntity.ok("File: " + fileName + " created successfully.");
    }
    catch (IOException e)
    {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create file.");
    }
  }

  @GetMapping("/read")
  public ResponseEntity<List<String>> readFiles()
  {
    try
    {
      List<String> lines = Files.readAllLines(Paths.get(outputFilePath + "results.txt"));
      return ResponseEntity.ok(lines);
    }
    catch (Exception e)
    {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());

    }
  }
}
