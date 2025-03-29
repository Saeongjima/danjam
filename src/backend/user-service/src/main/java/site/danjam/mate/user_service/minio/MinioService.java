package site.danjam.mate.user_service.minio;

import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.danjam.mate.common.annotation.MethodDescription;
import site.danjam.mate.common.exception.MinioException;
import site.danjam.mate.user_service.global.util.MultipartUtil;

@Service
@Slf4j
@RequiredArgsConstructor
public class MinioService {
    private final MinioClient minioClient;
    private final MultipartUtil multipartUtil;

    @MethodDescription(description = "minio 서버에 파일 업로드")
    public String uploadFileMinio(String bucketName, String fileName, MultipartFile file) throws Exception {
        // 업로드 진행 전 파일 확장자 확인
        uploadFileCheck(file);

        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build());

        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucketName)
                        .object(fileName)
                        .expiry(7, TimeUnit.DAYS)
                        .build());
    }

    @MethodDescription(description = "파일의 확장자를 확인합니다.")
    public void uploadFileCheck(MultipartFile file) {
        String fileExtension = multipartUtil.getFileExtension(file);
        multipartUtil.uploadFileExtensionCheck(fileExtension);
    }

    @MethodDescription(description = "파일 이름과 버킷 이름을 통해 파일을 다운로드 받습니다.")
    public ResponseEntity<byte[]> downloadFile(String fileName, String bucketName) throws Exception {
        InputStream fileData = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build());
        byte[] bytes = IOUtils.toByteArray(fileData);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);

        log.info("[+] HttpHeaders = [{}] ", headers);

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    @MethodDescription(description = "파일 이름과 버킷 이름을 통해 파일을 삭제합나디.")
    public String deleteFile(String bucketName, String fileName) throws Exception {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build()
            );
        } catch (IOException e) {
            e.printStackTrace();
            throw new MinioException("파일 삭제를 실패하였습니다.");
        }
        return "파일 삭제가 정상적으로 진행되었습니다.";
    }
}
