package br.com.gestao.controllers;

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
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;

import br.com.gestao.services.S3Service;

@WebFluxTest(S3Controller.class)
public class S3ControllerTest {

    private WebTestClient webTestClient;

    @Mock
    private S3Service s3Service;

    @InjectMocks
    private S3Controller s3Controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(s3Controller).build();
    }

    @Test
    void testUploadFileSuccess() throws AmazonServiceException, SdkClientException, IOException {
        // Arrange
        String fileName = "test-file.txt";
        String fileUrl = "http://s3.amazonaws.com/seu-bucket/" + fileName;

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

        when(s3Service.uploadFile(file)).thenReturn(fileUrl);

        // Act & Assert
        webTestClient.post()
                .uri("/api/files/upload")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(file.getBytes())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(fileUrl);
    }

    @Test
    void testUploadFileFailure() throws AmazonServiceException, SdkClientException, IOException {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("test-file.txt");
        when(s3Service.uploadFile(file)).thenThrow(new IOException("IO Exception"));

        // Act & Assert
        webTestClient.post()
                .uri("/api/files/upload")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(new byte[0])
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
