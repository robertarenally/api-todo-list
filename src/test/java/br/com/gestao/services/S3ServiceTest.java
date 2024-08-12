package br.com.gestao.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class S3ServiceTest {

    @Mock
    private AmazonS3 amazonS3;

    @InjectMocks
    private S3Service s3Service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUploadFileSuccess() throws AmazonServiceException, SdkClientException, IOException {
        // Arrange
        String fileName = "test-file.txt";
        String bucketName = "seu-bucket";
        String fileUrl = "http://s3.amazonaws.com/" + bucketName + "/" + fileName;

        MultipartFile file = new MultipartFile() {
            @Override
            public String getName() {
                return fileName;
            }

            @Override
            public String getOriginalFilename() {
                return fileName;
            }

            @Override
            public String getContentType() {
                return "text/plain";
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() {
                return new ByteArrayInputStream("test".getBytes());
            }

            @Override
            public void transferTo(java.io.File file) throws IOException, IllegalStateException {
                // Not needed for this test
            }
        };

        when(amazonS3.putObject(eq(bucketName), eq(fileName), any(InputStream.class), isNull())).thenReturn(null);
        when(amazonS3.getUrl(bucketName, fileName)).thenReturn(new java.net.URL(fileUrl));

        // Act
        Mono<String> result = Mono.fromCallable(() -> s3Service.uploadFile(file));

        // Assert
        StepVerifier.create(result)
                .expectNext(fileUrl)
                .verifyComplete();
    }

    @Test
    void testUploadFileFailure() throws IOException {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("test-file.txt");
        when(file.getInputStream()).thenThrow(new IOException("Failed to get input stream"));

        // Act
        Mono<String> result = Mono.fromCallable(() -> s3Service.uploadFile(file));

        // Assert
        StepVerifier.create(result)
                .expectError(IOException.class)
                .verify();
    }
}