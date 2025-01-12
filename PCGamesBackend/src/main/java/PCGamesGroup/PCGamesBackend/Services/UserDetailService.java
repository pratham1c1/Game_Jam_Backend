package PCGamesGroup.PCGamesBackend.Services;

import PCGamesGroup.PCGamesBackend.Response.ErrorMessage;
import PCGamesGroup.PCGamesBackend.Model.UserDetails;
import PCGamesGroup.PCGamesBackend.Repository.UserDetailRepo;
import PCGamesGroup.PCGamesBackend.Response.SuccessMessage;
import PCGamesGroup.PCGamesBackend.Utility.UserValidations;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.User;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailService {

    @Autowired
    private UserDetailRepo userRepo;
    @Autowired
    private MongoTemplate mongoTemplate;

    public Object addUserDetails(String userDetailsJson , MultipartFile userProfileImage, MultipartFile userProfileBgImage) throws IOException {
        // Validate userName
        ObjectMapper objectMapper = new ObjectMapper();
        UserDetails details = objectMapper.readValue(userDetailsJson, UserDetails.class);
        UserDetails existingUser = new UserDetails();
        if (details.getUserName() == null || details.getUserName().isEmpty()) {
            return new ErrorMessage("Validation Error", "User name cannot be null or empty.");
        }

        // Check if userName is unique
        if (userRepo.findByUserName(details.getUserName()) != null) {
            return new ErrorMessage("Validation Error", "User name '" + details.getUserName() + "' is already in use.");
        }

        // Validate userPassword
        if (details.getUserPassword() == null || details.getUserPassword().isEmpty()) {
            return new ErrorMessage("Validation Error", "User Password cannot be null or empty.");
        }

        if ((details.getUserEmail() != null) && (!UserValidations.isValidEmail(details.getUserEmail()))) {
            return new ErrorMessage("Validation Error", "Invalid email format.");
        }


        existingUser.setUserFullName(details.getUserFullName());
        existingUser.setUserName(details.getUserName());
        existingUser.setUserEmail(details.getUserEmail());
        existingUser.setUserPassword(details.getUserPassword());
        existingUser.setUserLikedGames(new ArrayList<>());
        existingUser.setUserProfileImage(userProfileImage != null ? new Binary(BsonBinarySubType.BINARY , userProfileImage.getBytes()) : null);
        existingUser.setUserProfileBgImage(userProfileBgImage != null ? new Binary(BsonBinarySubType.BINARY , userProfileBgImage.getBytes()) : null);
        // Save the user details
        userRepo.save(existingUser);

        // Fetch and return the saved user
        UserDetails fetchedUser = userRepo.findByUserName(details.getUserName());
        return fetchedUser;
    }


    public Object getUserDetailsByName(String userName) {
        // Check if userName exists
        UserDetails user = userRepo.findByUserName(userName);
        if (user == null) {
            return new ErrorMessage("Validation Error", "User not found with name: " + userName);
        }

        return user;
    }
    public Object getUserDetailsByUserId(String userId){

        // Check if userName exists
        UserDetails user = userRepo.findByUserId(userId);
        if (user == null) {
            return new ErrorMessage("Validation Error", "User not found with Id: " + userId);
        }

        return user;
    }
    public Object getUserProfileImageByName(String userName) throws IOException{
        // Check if userName exists
        UserDetails existingUser = userRepo.findByUserName(userName);
        if (existingUser == null) {
            return new ErrorMessage("Validation Error", "User not found with name: " + userName);
        }
        if(existingUser.getUserProfileImage() == null){
            return new ErrorMessage("Message", "User Profile Image is null with name: " + userName);
        }
        // Return the image data
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(existingUser.getUserProfileImage().getData(), headers, HttpStatus.OK);
    }
    public Object getUserProfileBgImageByName(String userName) throws IOException{
        // Check if userName exists
        UserDetails existingUser = userRepo.findByUserName(userName);
        if (existingUser == null) {
            return new ErrorMessage("Validation Error", "User not found with name: " + userName);
        }

        if(existingUser.getUserProfileBgImage() == null){
            return new ErrorMessage("Message" , "User Profile Image is null with the user name : " + userName);
        }

        // Return the image data
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(existingUser.getUserProfileBgImage().getData(), headers, HttpStatus.OK);
    }

    public List<UserDetails> getAllUserDetails(){
        return userRepo.findAll();
    }

    public Object updateUserPasswordByName(String userName, String userPassword){
        Query query = new Query(Criteria.where("userName").is(userName));
        UserDetails existingUser = mongoTemplate.findOne(query, UserDetails.class);
        if (existingUser == null) {
            return new ErrorMessage("Validation Error", "User not found with name: " + userName);
        }

        Update update = new Update().set("userPassword" , userPassword);
        UserDetails updatedUser = mongoTemplate.findAndModify(query,update,UserDetails.class);
        return "User Password updated successfully";
    }
    public Object updateUserInfoByName(String userName, UserDetails userDetails){
        Query query = new Query(Criteria.where("userName").is(userName));
        UserDetails existingUser = mongoTemplate.findOne(query, UserDetails.class);
        if (existingUser == null) {
            return new ErrorMessage("Validation Error", "User not found with name: " + userName);
        }

        Update update = new Update()
                .set("userName" , userDetails.getUserName())
                .set("userFullName" , userDetails.getUserFullName())
                .set("userEmail" , userDetails.getUserEmail());
        UserDetails updatedUser = mongoTemplate.findAndModify(query,update,UserDetails.class);
        return "User Info updated successfully";
    }
    public Object updateUserProfileImageByName(String userName, MultipartFile userProfileImage) throws IOException {
        Query query = new Query(Criteria.where("userName").is(userName));
        UserDetails existingUser = mongoTemplate.findOne(query, UserDetails.class);
        if (existingUser == null) {
            return new ErrorMessage("Validation Error", "User not found with name: " + userName);
        }
        Update update = new Update()
                .set("userProfileImage" , userProfileImage != null ? new Binary(BsonBinarySubType.BINARY , userProfileImage.getBytes()) : null);
        UserDetails updatedUser = mongoTemplate.findAndModify(query,update,UserDetails.class);
        if(userProfileImage == null){
            return "UserProfileImage updated successfully with the value :  null ";
        }
        return "User Images updated successfully";
    }
    public Object updateUserProfileBgImageByName(String userName, MultipartFile userProfileBgImage) throws IOException {
        Query query = new Query(Criteria.where("userName").is(userName));
        UserDetails existingUser = mongoTemplate.findOne(query, UserDetails.class);
        if (existingUser == null) {
            return new ErrorMessage("Validation Error", "User not found with name: " + userName);
        }
        Update update = new Update()
                .set("userProfileBgImage" , userProfileBgImage != null ? new Binary(BsonBinarySubType.BINARY , userProfileBgImage.getBytes()) : null);
        UserDetails updatedUser = mongoTemplate.findAndModify(query,update,UserDetails.class);
        if(userProfileBgImage == null){
            return "UserProfileBgImage updated successfully with the value :  null ";
        }
        return "User Images updated successfully";
    }
    
    public Object deleteUserDetailsByName(String userName) {
        // Check if userName exists
        UserDetails user = userRepo.findByUserName(userName);
        if (user == null) {
            return new ErrorMessage("Validation Error", "User not found with name: " + userName);
        }

        // Delete the user
        userRepo.deleteByUserName(userName);

        return new SuccessMessage("Success", "User '" + userName + "' deleted successfully.");
    }


}