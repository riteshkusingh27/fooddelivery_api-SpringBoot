package com.food.delivery.service;

import com.food.delivery.entity.FoodEnity;
import com.food.delivery.io.FoodRequest;
import com.food.delivery.io.FoodResponse;
import com.food.delivery.repository.FoodRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectAclRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service

public class FoodServiceImpl implements FoodService {
    @Autowired
    private S3Client s3client;
    // inject repository
    @Autowired
    private FoodRepo foodrepo;

    @Value("${aws.s3.bucketname}")
    private String bucketName;

    @Override
    public String uploadFile(MultipartFile file) {
        String filenameExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

//                      random uuid and the file extension
        String key = UUID.randomUUID().toString() + "." + filenameExtension;

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder().
                    bucket(bucketName)
                    .key(key)
                    .acl("public-read")
                    .contentType(file.getContentType())
                    .build();
            PutObjectResponse response = s3client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
            if (response.sdkHttpResponse().isSuccessful()) {
                return "https://" + bucketName + ".s3.amazonaws.com/" + key;
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File upload failed");
            }
        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occured while uploading the file ");

        }

    }

    @Override
    public FoodResponse addFood(FoodRequest request, MultipartFile file) {
        FoodEnity newFoodEntity = convertToEntity(request);
        ///  gets the image url and upload file to the s3 bucket
        String imageurl = uploadFile(file);
        newFoodEntity.setImageUrl(imageurl);
        foodrepo.save(newFoodEntity);
        // converting the saved Enity into FoodResponse DTO
        return convertToResponse(newFoodEntity);


    }

    @Override
    public List<FoodResponse> readFoods() {
        ///  Food entity from the database response
        List<FoodEnity> databaseEntries = foodrepo.findAll();
        return databaseEntries.stream().map(this::convertToResponse).collect(Collectors.toList());

    }

    @Override
    public FoodResponse readFood(String id) {
        FoodEnity foodById = foodrepo.findById(id).orElseThrow(() -> new RuntimeException("Food Not found for the id : "));
        return convertToResponse(foodById);

    }

    @Override
    public boolean deleteFilename(String filename) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(filename)
                .build();

        s3client.deleteObject(deleteObjectRequest);
        return true;
    }

    @Override
    public void deleteFood(String id) {
        FoodResponse fr = readFood(id);
        String imageUrl = fr.getImageUrl();
      String filename =   imageUrl.substring(imageUrl.lastIndexOf("/")+1);
      // if the file is deleted the aws s3 bucket
     boolean isFileDeleted =  deleteFilename(filename);   //delete file from s3 bucket
     if(isFileDeleted){
         foodrepo.deleteById(fr.getId());
     }



    }

    // as we get the food request object convert into entity object

    private FoodEnity convertToEntity(FoodRequest request) {

        return FoodEnity.builder()
                .name(request.getName())
                .price(request.getPrice())
                .description(request.getDescription())
                .category(request.getCategory())
                .build();
    }


    private FoodResponse convertToResponse(FoodEnity entity) {
        return FoodResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .category(entity.getCategory())
                .price(entity.getPrice())
                .imageUrl(entity.getImageUrl())
                .build();

    }

}
