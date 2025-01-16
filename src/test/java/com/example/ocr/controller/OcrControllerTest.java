package com.example.ocr.controller;

import com.example.ocr.service.OcrService;
import com.example.ocr.controller.OcrController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OcrController.class)
public class OcrControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OcrService ocrService;

  @Test
  public void testTextRecognition() throws Exception {
    MockMultipartFile file = new MockMultipartFile(
        "file",
        "test.png",
        "image/png",
        "test image content".getBytes());

    when(ocrService.processTextRecognition(anyString())).thenReturn("test result");

    mockMvc.perform(multipart("/api/ocr/text-recognition").file(file))
        .andExpect(status().isOk())
        .andExpect(content().string("test result"));
  }

  @Test
  public void testLayoutAnalysis() throws Exception {
    MockMultipartFile file = new MockMultipartFile(
        "file",
        "test.png",
        "image/png",
        "test image content".getBytes());

    when(ocrService.processLayoutAnalysis(anyString())).thenReturn("test result");

    mockMvc.perform(multipart("/api/ocr/layout-analysis").file(file))
        .andExpect(status().isOk())
        .andExpect(content().string("test result"));
  }

  @Test
  public void testReadingOrder() throws Exception {
    MockMultipartFile file = new MockMultipartFile(
        "file",
        "test.png",
        "image/png",
        "test image content".getBytes());

    when(ocrService.processReadingOrder(anyString())).thenReturn("test result");

    mockMvc.perform(multipart("/api/ocr/reading-order").file(file))
        .andExpect(status().isOk())
        .andExpect(content().string("test result"));
  }

  @Test
  public void testTextDetection() throws Exception {
    MockMultipartFile file = new MockMultipartFile(
        "file",
        "test.png",
        "image/png",
        "test image content".getBytes());

    when(ocrService.processTextDetection(anyString())).thenReturn("test result");

    mockMvc.perform(multipart("/api/ocr/text-detection").file(file))
        .andExpect(status().isOk())
        .andExpect(content().string("test result"));
  }

  @Test
  public void testInvalidFileType() throws Exception {
    MockMultipartFile file = new MockMultipartFile(
        "file",
        "test.txt",
        "text/plain",
        "invalid content".getBytes());

    mockMvc.perform(multipart("/api/ocr/text-recognition").file(file))
        .andExpect(status().isBadRequest());
  }
}
