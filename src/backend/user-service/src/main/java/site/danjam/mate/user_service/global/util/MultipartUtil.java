package site.danjam.mate.user_service.global.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import site.danjam.mate.user_service.global.common.annotation.MethodDescription;
import site.danjam.mate.user_service.global.exception.MultipartException;

public class MultipartUtil {
    @Value("${minio.allowed.extension}")
    ArrayList<String> allowedFileExtensions;


    @MethodDescription(description = "파일 확장자 추출")
    public static String getFileExtension(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        if (StringUtils.hasText(originalFileName)) {
            return originalFileName.substring(originalFileName.lastIndexOf('/') + 1);
        }
        return "";
    }

    @MethodDescription(description = "지정된 형식으로 파일명 생성")
    public String generateFileName(String username, String suffix, String extension) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss");
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        return username + "_" + now.format(formatter) + "_" + suffix + "." + extension;
    }

    @MethodDescription(description = "파일 확장자를 확인합니다.")
    public void uploadFileExtensionCheck(String extension) {
        if (!allowedFileExtensions.contains(extension)) {
            throw new MultipartException();
        }
    }
}
