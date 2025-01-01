package PCGamesGroup.PCGamesBackend.Services;

import PCGamesGroup.PCGamesBackend.Model.GameDetails;
import PCGamesGroup.PCGamesBackend.Model.UserDetails;
import PCGamesGroup.PCGamesBackend.Repository.GameDetailsRepo;
import PCGamesGroup.PCGamesBackend.Repository.UserDetailRepo;
import PCGamesGroup.PCGamesBackend.Response.ErrorMessage;
import PCGamesGroup.PCGamesBackend.Response.SuccessMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class User_Games_DetailsService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private UserDetailRepo userDetailRepo;
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private GameDetailsRepo gameDetailsRepo;
    @Autowired
    private GameDetailsService gameDetailsService;


    public Object addGameToUserLikedGames(String userName, String gameName){
        UserDetails user = userDetailRepo.findByUserName(userName);
        Query query = new Query(Criteria.where("userName").is(userName));
        if(user == null){
            return (new ErrorMessage("Validation Error" , "User is not available with the name : "+userName));
        }
        List<String> existingLikedGamesList = user.getUserLikedGames();
        existingLikedGamesList.remove(gameName);
        existingLikedGamesList.add(gameName);
        Update update = new Update()
                .set("userLikedGames" , existingLikedGamesList);
        UserDetails oldUser = mongoTemplate.findAndModify(query,update,UserDetails.class);
        return "Game Added Successfully";
    }

    public Object removeGameFromUserLikedGames(String userName, String gameName){
        UserDetails user = userDetailRepo.findByUserName(userName);
        Query userQuery = new Query(Criteria.where("userName").is(userName));
        if(user == null){
            return (new ErrorMessage("Validation Error" , "User is not available with the name : "+userName));
        }
        Query query = new Query(Criteria.where("gameName").is(gameName));
        GameDetails existingGame = mongoTemplate.findOne(query, GameDetails.class);
        if (existingGame == null) {
            return new ErrorMessage("Validation Error", "Game not found with name: " + gameName);
        }
        List<String> existingLikedGamesList = user.getUserLikedGames();
        existingLikedGamesList.remove(gameName);
        Update update = new Update()
                .set("userLikedGames" , existingLikedGamesList);
        UserDetails oldUser = mongoTemplate.findAndModify(userQuery,update,UserDetails.class);
        return "Game Removed Successfully";
    }

    public Object getAllGamesByUserName(String userName){
        UserDetails user = userDetailRepo.findByUserName(userName);

        if(user == null){
            return (new ErrorMessage("Validation Error" , "User is not available with the name : "+userName));
        }
        return gameDetailsRepo.findByUserId(user.getUserId());
    }
    public Object getUserByGameName(String gameName){
        GameDetails game = gameDetailsRepo.findByGameName(gameName);
        if(game == null){
            return (new ErrorMessage("Validation Error" , "Game is not available with the name : "+gameName));
        }
        return userDetailRepo.findByUserId(game.getUserId());
    }
    public Object getUserLikedGame(String userName){
        UserDetails user = userDetailRepo.findByUserName(userName);
        Query query = new Query(Criteria.where("userName").is(userName));
        if(user == null){
            return (new ErrorMessage("Validation Error" , "User is not available with the name : "+userName));
        }
        return user.getUserLikedGames();
    }

    public Object deleteAllGamesByUserName(String userName){
        UserDetails user = userDetailRepo.findByUserName(userName);

        if(user == null){
            return (new ErrorMessage("Validation Error" , "User is not available with the name : "+userName));
        }
        gameDetailsRepo.deleteGamesByUserId(user.getUserId());
        return (new SuccessMessage("Deleted Successfully" , "All games of user: "+ userName + " are deleted successfully"));
    }
}