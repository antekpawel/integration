package com.kodilla.integration;

import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * User: Z6PWA
 * Date: 22.10.2023
 */
public class FileTransformer
{
  @Value("${inputDirectory}")
  private String inputDirectory;
  @Value("${outputFilePath}")
  private String outputFilePath;

  public String transformFile() {
    List<String> collect = Stream.of(new File(inputDirectory).listFiles())
      .filter(file -> !file.isDirectory())
      .map(File::getName)
      .toList();

    try {
      Files.write(Paths.get(outputFilePath + "results.txt"), collect);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return collect.toString();
  }
}
