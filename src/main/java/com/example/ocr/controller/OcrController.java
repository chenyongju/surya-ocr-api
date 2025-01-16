package com.example.ocr.controller;

import com.example.ocr.service.OcrService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/api/ocr")
@Tag(name = "OCR API", description = "OCR processing operations")
public class OcrController {
  private static final Logger logger = LoggerFactory.getLogger(OcrController.class);

  @Autowired
  private OcrService ocrService;

  @PostMapping("/text-recognition")
  @Operation(summary = "Text recognition", description = "Extract text from image")
  public ResponseEntity<String> processTextRecognition(@RequestParam("file") MultipartFile file) {
    return processOcrOperation(file, "text-recognition");
  }

  @PostMapping("/layout-analysis")
  @Operation(summary = "Layout analysis", description = "Analyze document layout")
  public ResponseEntity<String> processLayoutAnalysis(@RequestParam("file") MultipartFile file) {
    return processOcrOperation(file, "layout-analysis");
  }

  @PostMapping("/reading-order")
  @Operation(summary = "Reading order", description = "Determine reading order of text elements")
  public ResponseEntity<String> processReadingOrder(@RequestParam("file") MultipartFile file) {
    return processOcrOperation(file, "reading-order");
  }

  @PostMapping("/text-detection")
  @Operation(summary = "Text detection", description = "Detect text regions in image")
  public ResponseEntity<String> processTextDetection(@RequestParam("file") MultipartFile file) {
    return processOcrOperation(file, "text-detection");
  }

  private ResponseEntity<String> processOcrOperation(MultipartFile file, String operation) {
    // Validate file type
    String contentType = file.getContentType();
    if (contentType == null || !contentType.startsWith("image/")) {
      return ResponseEntity.badRequest().body("Invalid file type. Only image files are allowed.");
    }

    try {
      Path tempFile = Files.createTempFile("ocr-", ".tmp");
      Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

      String result;
      switch (operation) {
        case "text-recognition":
          result = ocrService.processTextRecognition(tempFile.toString());
          break;
        case "layout-analysis":
          result = ocrService.processLayoutAnalysis(tempFile.toString());
          break;
        case "reading-order":
          result = ocrService.processReadingOrder(tempFile.toString());
          break;
        case "text-detection":
          result = ocrService.processTextDetection(tempFile.toString());
          break;
        default:
          throw new IllegalArgumentException("Invalid OCR operation");
      }

      Files.deleteIfExists(tempFile);
      return ResponseEntity.ok(result);
    } catch (IOException | InterruptedException e) {
      logger.error("{} failed", operation, e);
      return ResponseEntity.internalServerError()
          .body(operation + " failed: " + e.getMessage());
    }
  }
}
