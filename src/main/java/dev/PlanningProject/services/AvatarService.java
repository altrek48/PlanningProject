package dev.PlanningProject.services;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class AvatarService {

    private final MinioClient minioClient;
    private final UserService userService;

    @Value("${minio.bucket}")
    private String bucket;

    @Value("${minio.endpoint}")
    private String minioUrl;

    public String uploadFile(MultipartFile file, String username) {
        try {
            String fileName = file.getOriginalFilename();
            String newFileName = System.currentTimeMillis() + "." + StringUtils.substringAfterLast(fileName, ".");
            InputStream inputStream = file.getInputStream();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(newFileName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            String avatarUrl =  "http://localhost:9000/" + bucket + "/" + newFileName;
            userService.updateAvatar(username, avatarUrl);
            return newFileName;
        }
        catch (Exception e) {
            throw new RuntimeException("upload fail", e);
        }
    }

    public String getFileUrl(String fileName) {
        return minioUrl + "/" + bucket + "/" + fileName;
    }

}
