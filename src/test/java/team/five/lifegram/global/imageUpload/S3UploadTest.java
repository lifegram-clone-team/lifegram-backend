package team.five.lifegram.global.imageUpload;

import com.amazonaws.services.s3.AmazonS3Client;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class S3UploadTest {

    @Mock
    AmazonS3Client amazonS3Client;

    String bucket = "mockBucket";

    @Nested
    @DisplayName("uploadFilesTest")
    class UploadFiles {

        @Test
        @DisplayName("uploadFiles 성공")
        void uploadFilesSuccessTest() throws IOException {
            // given
            String fileName = "testImage";
            String contentType = "JPG";
            String filePath = "src/test/resources/testImages/"+fileName+"."+contentType;
            FileInputStream fileInputStream = new FileInputStream(filePath);
            MockMultipartFile multipartFile = new MockMultipartFile("images",
                    fileName + "." + contentType,
                    contentType,
                    fileInputStream);
            String path = "path";
            S3Upload s3Upload = new S3Upload(amazonS3Client);

            s3Upload.uploadFiles(multipartFile, path);
        }

    }

}