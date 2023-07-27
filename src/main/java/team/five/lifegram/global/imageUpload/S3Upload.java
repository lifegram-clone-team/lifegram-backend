package team.five.lifegram.global.imageUpload;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
@Service
public class S3Upload {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFiles(MultipartFile multipartFile, String path) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
        return upload(uploadFile, path);
    }

    private String upload(File uploadFile, String path) {
        String fileName = uploadFile.getName();
        putS3(uploadFile, path + "/" + fileName);
        uploadFile.delete();
        return fileName;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)    // PublicRead 권한으로 업로드 됨
        );
        return fileName;
    }

    //TODO 메서드 명과 변수 명은 읽었을 때 용도를 바로 알 수 있도록 명확하게 작성하기
    //TODO 하나의 함수에는 한 가지 기능만을 해야 합니다. Side Effect 줄입시다. 코드 분리를 해야 합니다.
    //TODO 파일을 아무데나 저장을 하는 것이 아니라, tmp 폴더를 따로 설정해주는 게 좋을 것 같아요. tmp/images/
    private Optional<File> convert(MultipartFile file) throws  IOException {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        File convertFile = new File(System.getProperty("user.dir") + "/" + now + ".jpg");
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}
