package br.com.gestao.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import br.com.gestao.config.S3Properties;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3Service {

    @Autowired
    private AmazonS3 amazonS3;
    
    @Autowired
    private S3Properties s3Properties;


    public S3Service(S3Properties s3Properties) {
        this.s3Properties = s3Properties;
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(s3Properties.getAccessKeyId(), s3Properties.getSecretAccessKey());
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(s3Properties.getRegion())
                .build();
    }

    public String uploadFile(MultipartFile file) throws AmazonServiceException, SdkClientException, IOException {
        String fileName = file.getOriginalFilename();
        amazonS3.putObject("seu-bucket", fileName, file.getInputStream(), null);
        return amazonS3.getUrl("seu-bucket", fileName).toString();
    }
}