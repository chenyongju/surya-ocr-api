# Surya OCR Spring Boot 集成

本项目实现了在Spring Boot应用中集成Surya OCR功能，提供以下OCR操作接口：

## 功能特性

- 文字识别 (Text Recognition)
- 版面分析 (Layout Analysis)  
- 阅读顺序识别 (Reading Order)
- 文字区域检测 (Text Detection)

## 安装步骤

1. 克隆项目
2. 安装依赖：

   ```bash
   mvn clean install
   ```

3. 配置Surya OCR命令，编辑`application.properties`：

   ```properties
   surya.ocr.command.ocr=surya_ocr
   surya.ocr.command.layout=surya_layout
   surya.ocr.command.order=surya_order
   surya.ocr.command.detect=surya_detect
   surya.ocr.timeout=30000
   ```

## API 接口

### 1. 文字识别

- **URL**: POST /api/ocr/text-recognition
- **请求**: 图片文件 (multipart/form-data)
- **响应**: 识别出的文字内容

### 2. 版面分析

- **URL**: POST /api/ocr/layout-analysis
- **请求**: 图片文件 (multipart/form-data)
- **响应**: 版面分析结果

### 3. 阅读顺序识别

- **URL**: POST /api/ocr/reading-order
- **请求**: 图片文件 (multipart/form-data)
- **响应**: 文字阅读顺序

### 4. 文字区域检测

- **URL**: POST /api/ocr/text-detection
- **请求**: 图片文件 (multipart/form-data)
- **响应**: 文字区域坐标

## 使用示例

1. 启动应用：

   ```bash
   mvn spring-boot:run
   ```

2. 访问Swagger UI：<http://localhost:8080/swagger-ui.html>

3. 上传图片文件到任意OCR接口

4. 示例cURL命令：

   ```bash
   curl -X POST -F "file=@test.png" http://localhost:8080/api/ocr/text-recognition
   ```

## 开发环境

- Java 8
- Spring Boot 3.x
- Maven

## 测试

运行单元测试：

```bash
mvn test
```

## 贡献指南

欢迎提交PR和改进建议！
