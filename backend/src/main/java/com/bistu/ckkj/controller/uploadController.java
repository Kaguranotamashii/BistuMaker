package com.bistu.ckkj.controller;

import com.bistu.ckkj.aop.MyLog;
import com.bistu.ckkj.config.MinIOConfigProperties;
import com.bistu.ckkj.pojo.Result;

import com.bistu.ckkj.service.ImagesService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


@RequestMapping("/upload")
@RestController//支持自动将接口返回的数据类型转换为JSON格式
@Validated//它可以在保存实体类之前对其进行验证，并根据指定的验证规则进行校验。该注解可用于字段、集合、实体类等。
@RequiredArgsConstructor
public class uploadController {

    /**
     * 时间格式化
     */
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd/");
    private final ImagesService imagesService;

    @Value("${upload.location.os}")
    String path;
    @Value("${upload.location.os1}")
    String os;
    @Value("${method}")
    String method;
    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinIOConfigProperties minIOConfigProperties;


    // 假设的辅助方法，用于根据文件类型获取contentType
    private String getContentType(String imageType) {
        // 根据 imageType 返回正确的MIME类型，这里简化处理
        if ("jpg".equalsIgnoreCase(imageType) || "jpeg".equalsIgnoreCase(imageType)) {
            return "image/jpeg";
        } else if ("png".equalsIgnoreCase(imageType)) {
            return "image/png";
        }
        // 其他类型请按需添加
        return "application/octet-stream"; // 默认二进制流
    }

    @MyLog(value = "添加照片")
    @PostMapping("/img")
    public Result upload(@RequestParam("file") MultipartFile file, String imageType, String imageName, HttpServletRequest request) throws IOException {


        if(method.equals("minio")){
            try {
                // 确保 filePath 和 inputStream 被正确初始化
                String filePath = generateFilePath(imageName); // 假设这是一个生成文件路径的方法
                InputStream inputStream = file.getInputStream(); // 使用 MultipartFile 的 getInputStream 获取输入流

                PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                        .object(filePath)
                        // 根据实际上传的文件类型动态设置contentType
                        .contentType(getContentType(imageType))
                        .bucket(minIOConfigProperties.getBucket())
                        .stream(inputStream, file.getSize(), -1) // 使用文件大小而不是available()
                        .build();

                MinioClient minioClient = MinioClient.builder()
                        .endpoint(minIOConfigProperties.getEndpoint())
                        .credentials(minIOConfigProperties.getAccessKey(), minIOConfigProperties.getSecretKey())
                        .build();
                    minioClient.putObject(putObjectArgs);
                // 构建访问URL，确保 separator 是正确的路径分隔符
                String separator = "/";
                StringBuilder urlPath = new StringBuilder(minIOConfigProperties.getReadPath());
                urlPath.append(separator).append(minIOConfigProperties.getBucket())
                        .append(separator).append(filePath);

                // Step 3: 服务端上传逻辑
                if (imageName != null && imageType != null) {
                    imagesService.upload(imageName, String.valueOf(urlPath), imageType);
                } else {
                    imagesService.upload(imageName, String.valueOf(urlPath), "封面");
                }
                return Result.success(String.valueOf(urlPath));
            }catch (Exception ex){
                return Result.error("上传文件失败");
            }



        }

        else{
            // Step 1: 检查文件是否为空 以及文件大小
            if (file == null) {
                return Result.error("上传失败");
            }

            if (file.isEmpty() || file.getSize() > 20 * 1024 * 1024) {
                return Result.error("文件太大了");
            }
            float scale = 1.0F;
            float quality = 0.75F;

            if (file.getSize() < 10 * 1024 * 1024 && file.getSize() > 5 *1024 * 1024) {
                scale = 0.8F;
                quality = 0.6F;
            }

            // Step 2: 图片压缩（使用thumbnailator）
            String directory = simpleDateFormat.format(new Date());
            String fileName = file.getOriginalFilename();
            UUID uuid = UUID.randomUUID();
            String extension = null;
            if (fileName != null) {
                extension = fileName.substring(fileName.lastIndexOf("."));
            }
            fileName = uuid + extension;

            // 压缩后的图片文件路径
            String compressedFilePath = path + "/" + directory + fileName;
            File compressedFile = new File(compressedFilePath);

            // 创建父目录（如果不存在）
            compressedFile.getParentFile().mkdirs();

            try (InputStream inputStream = file.getInputStream(); FileOutputStream outputStream = new FileOutputStream(compressedFile)) {
                Thumbnails.of(inputStream)
                        .scale(scale) // 默认情况下，不缩放图片
                        .outputQuality(quality) // 设置压缩质量（例如，0.7表示70%质量，可按需调整）
                        .toOutputStream(outputStream);
            }

            // Step 3: 服务端上传逻辑
            if (imageName != null && imageType != null) {
                imagesService.upload(imageName, "/images/" + directory + fileName, imageType);
            } else {
                imagesService.upload(fileName, "/images/" + directory + fileName, "封面");
            }
            return Result.success("/images/" + directory + fileName);
        }
    }

    private String generateFilePath(String imageName) {
        return imageName;
    }
}
