package com.kodilla.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.*;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.dsl.FileWritingMessageHandlerSpec;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageHandler;

import java.io.File;

/**
 * User: Z6PWA
 * Date: 22.10.2023
 */
@Configuration
public class FileIntegrationConfiguration
{
  @Value("${inputDirectory}")
  private String inputDirectory;
  @Value("${outputFilePath}")
  private String outputFilePath;

  @Bean
  IntegrationFlow fileIntegrationFlow(
    FileReadingMessageSource fileAdapter,
    FileTransformer transformer,
    MessageHandler outputFileHandler) {

    return IntegrationFlows.from(fileAdapter, config -> config.poller(Pollers.fixedDelay(1000)))
      .transform(transformer, "transformFile")
      .handle(outputFileHandler)
      .get();
  }

  @Bean
  FileReadingMessageSource fileAdapter() {
    FileReadingMessageSource fileSource = new FileReadingMessageSource();
    fileSource.setDirectory(new File(inputDirectory));

    return fileSource;
  }

  @Bean
  FileTransformer transformer() {
    return new FileTransformer();
  }


  @Bean
  MessageHandler outputFileHandler() {
    return message -> {
      // ta metoda nic nie robi
    };
  }
}
