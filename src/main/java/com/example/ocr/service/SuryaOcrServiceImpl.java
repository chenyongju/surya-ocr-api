package com.example.ocr.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

@Service
public class SuryaOcrServiceImpl implements OcrService {
  private static final Logger logger = LoggerFactory.getLogger(SuryaOcrServiceImpl.class);

  @Value("${surya.ocr.command.ocr}")
  private String ocrCommand;

  @Value("${surya.ocr.command.layout}")
  private String layoutCommand;

  @Value("${surya.ocr.command.order}")
  private String orderCommand;

  @Value("${surya.ocr.command.detect}")
  private String detectCommand;

  @Value("${surya.ocr.path}")
  private String suryaOcrPath;

  @Override
  public String processTextRecognition(String filePath) throws IOException, InterruptedException {
    logger.info("Starting text recognition using command: {}", ocrCommand);
    return executeCommand(ocrCommand, filePath);
  }

  @Override
  public String processLayoutAnalysis(String filePath) throws IOException, InterruptedException {
    logger.info("Starting layout analysis using command: {}", layoutCommand);
    return executeCommand(layoutCommand, filePath);
  }

  @Override
  public String processReadingOrder(String filePath) throws IOException, InterruptedException {
    logger.info("Starting reading order analysis using command: {}", orderCommand);
    return executeCommand(orderCommand, filePath);
  }

  @Override
  public String processTextDetection(String filePath) throws IOException, InterruptedException {
    logger.info("Starting text detection using command: {}", detectCommand);
    return executeCommand(detectCommand, filePath);
  }

  private String executeCommand(String command, String filePath) throws IOException, InterruptedException {
    logger.info("Executing command: {} for file: {}", command, filePath);

    // 检查命令是否存在
    ProcessBuilder checkBuilder = new ProcessBuilder("which", command);
    Process checkProcess = checkBuilder.start();
    if (checkProcess.waitFor() != 0) {
      throw new IOException("Command not found: " + command);
    }

    ProcessBuilder processBuilder = new ProcessBuilder();
    // 使用完整路径执行命令
    processBuilder.command(suryaOcrPath + "/" + command, filePath);

    Process process = processBuilder.start();
    process.waitFor();

    StringBuilder output = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(process.getInputStream()))) {
      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line).append("\n");
      }
    }

    int exitCode = process.exitValue();
    if (exitCode != 0) {
      throw new IOException("Process failed with exit code: " + exitCode);
    }

    return output.toString();
  }
}
