package com.example.ocr.service;

import java.io.IOException;

public interface OcrService {
  String processTextRecognition(String filePath) throws IOException, InterruptedException;

  String processLayoutAnalysis(String filePath) throws IOException, InterruptedException;

  String processReadingOrder(String filePath) throws IOException, InterruptedException;

  String processTextDetection(String filePath) throws IOException, InterruptedException;
}
