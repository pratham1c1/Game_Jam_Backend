package PCGamesGroup.PCGamesBackend.Services;

import PCGamesGroup.PCGamesBackend.ErrorResponse.ErrorMessage;
import PCGamesGroup.PCGamesBackend.Model.UserDetails;
import PCGamesGroup.PCGamesBackend.Repository.UserDetailRepo;
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

    public Object addUserDetails(UserDetails details){

        if (details.getUserName() == null || details.getUserName().isEmpty() || userRepo.findByUserName(details.getUserName()) != null) {
            return new ErrorMessage("Validation Error", "User name cannot be null or empty or Username is already used");
        }

        if (details.getUserEmail() == null || details.getUserEmail().isEmpty()) {
            return new ErrorMessage("Validation Error", "User email cannot be null or empty");
        }

        userRepo.save(details);
        // Fetch the saved user from MongoDB to ensure the ID is populated
        UserDetails fetchedUser = userRepo.findByUserName(details.getUserName());
        System.out.println("Fetched User from MongoDB: " + fetchedUser);

        return fetchedUser;
    }

    public Object getUserDetailsByName(String userName){
//        System.out.println("getUserDetailsByName Service method called ....");
        return userRepo.findByUserName(userName);
    }

    public List<UserDetails> getAllUserDetails(){
        return userRepo.findAll();
    }


    public Object updateUserDetailsByName(String userName, UserDetails details){
        Query query = new Query(Criteria.where("userName").is(userName));
        UserDetails existingUser = mongoTemplate.findOne(query, UserDetails.class);
        if (existingUser == null) {
            return new ErrorMessage("Validation Error", "User is not available with the name: " + userName);
        }
//        System.out.println("Existing user from MongoDB: " + existingUser);
        Update update = new Update()
                .set("userName", details.getUserName())
                .set("userEmail", details.getUserEmail());

        // Perform the update and return the modified document
        UserDetails fetchUser = mongoTemplate.findAndModify(query, update, UserDetails.class);

        return userRepo.findByUserName(details.getUserName());
    }


    public Object deleteUserDetailsByName(String userName){
        if (userRepo.findByUserName(userName) == null) {
            return new ErrorMessage("Validation Error", "User is not available");
        }
        UserDetails user = userRepo.findByUserName(userName);
        userRepo.deleteByUserName(userName);
        return user;
    }

}