package io.samples.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.MinioException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: baoxin.zhao
 * @date: 2024/7/28
 */
@Slf4j
@Service
public class MinioService {

    @Value("${minio.host:http://192.168.1.16:9000}")
    private String endpoint;
    @Value("${minio.access:minioadmin}")
    private String access;
    @Value("${minio.secret:minioadmin}")
    private String secret;
    @Value("${minio.bucket:bucket-json}")
    private String bucket;

    private MinioClient minioClient;

    @PostConstruct
    private void init() {
        minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(access, secret)
                .build();

        boolean found = false;
        try {
            found = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucket).build());
            if (!found) {
                log.info("create bucket: {}", bucket);
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(bucket)
                        .build());
            }
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        } catch (InsufficientDataException e) {
            throw new RuntimeException(e);
        } catch (InternalException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        } catch (XmlParserException e) {
            throw new RuntimeException(e);
        }
    }

    public ObjectWriteResponse upload(String name, String json) {
        try {
            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(name)
                    .contentType("text/plain")
                    .stream(inputStream, bytes.length, -1)
                    .build();
            ObjectWriteResponse response = minioClient.putObject(putObjectArgs);
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        } catch (InsufficientDataException e) {
            throw new RuntimeException(e);
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidResponseException e) {
            throw new RuntimeException(e);
        } catch (XmlParserException e) {
            throw new RuntimeException(e);
        } catch (InternalException e) {
            throw new RuntimeException(e);
        }
    }

    public String getObject(String name) {
        try {
            InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucket)
                            .object(name)
                            .build());
            byte[] buf = new byte[16384];
            int bytesRead;
            StringBuilder sb = new StringBuilder("");
            while ((bytesRead = stream.read(buf, 0, buf.length)) >= 0) {
                sb.append(new String(buf, 0, bytesRead, StandardCharsets.UTF_8));
            }
            stream.close();
            return sb.toString();
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return "";
    }
}
