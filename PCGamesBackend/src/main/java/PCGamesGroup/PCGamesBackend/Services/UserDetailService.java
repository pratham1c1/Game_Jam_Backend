package PCGamesGroup.PCGamesBackend.Services;

import PCGamesGroup.PCGamesBackend.Response.ErrorMessage;
import PCGamesGroup.PCGamesBackend.Model.UserDetails;
import PCGamesGroup.PCGamesBackend.Repository.UserDetailRepo;
import PCGamesGroup.PCGamesBackend.Response.SuccessMessage;
import PCGamesGroup.PCGamesBackend.Utility.UserValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailService {

    @Autowired
    private UserDetailRepo userRepo;
    @Autowired
    private MongoTemplate mongoTemplate;

    public Object addUserDetails(UserDetails details) {
        // Validate userName
        if (details.getUserName() == null || details.getUserName().isEmpty()) {
            return new ErrorMessage("Validation Error", "User name cannot be null or empty.");
        }

        // Check if userName is unique
        if (userRepo.findByUserName(details.getUserName()) != null) {
            return new ErrorMessage("Validation Error", "User name '" + details.getUserName() + "' is already in use.");
        }

        // Validate userEmail
        if (details.getUserEmail() == null || details.getUserEmail().isEmpty()) {
            return new ErrorMessage("Validation Error", "User email cannot be null or empty.");
        }
        if (!UserValidations.isValidEmail(details.getUserEmail())) {
            return new ErrorMessage("Validation Error", "Invalid email format.");
        }

        // Save the user details
        userRepo.save(details);

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

    public List<UserDetails> getAllUserDetails(){
        return userRepo.findAll();
    }


    public Object updateUserDetailsByName(String userName, UserDetails details) {
        // Check if userName exists
        Query query = new Query(Criteria.where("userName").is(userName));
        UserDetails existingUser = mongoTemplate.findOne(query, UserDetails.class);
        if (existingUser == null) {
            return new ErrorMessage("Validation Error", "User not found with name: " + userName);
        }

        // Check if the new userName (if changed) is unique
        if (!userName.equals(details.getUserName()) && userRepo.findByUserName(details.getUserName()) != null) {
            return new ErrorMessage("Validation Error", "User name '" + details.getUserName() + "' is already in use.");
        }

        // Validate userEmail
        if (details.getUserEmail() != null && details.getUserEmail().isEmpty()) {
            return new ErrorMessage("Validation Error", "User email cannot be empty.");
        }
        if (!UserValidations.isValidEmail(details.getUserEmail())) {
            return new ErrorMessage("Validation Error", "Invalid email format.");
        }

        // Create the update object
        Update update = new Update()
                .set("userName", details.getUserName())
                .set("userEmail", details.getUserEmail());

        // Perform the update
        UserDetails updatedUser = mongoTemplate.findAndModify(query, update, UserDetails.class);

        return updatedUser;
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