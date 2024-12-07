package PCGamesGroup.PCGamesBackend.Services;

import PCGamesGroup.PCGamesBackend.Model.GameDetails;
import PCGamesGroup.PCGamesBackend.Model.UserDetails;
import PCGamesGroup.PCGamesBackend.Repository.GameDetailsRepo;
import PCGamesGroup.PCGamesBackend.Repository.UserDetailRepo;
import PCGamesGroup.PCGamesBackend.Response.ErrorMessage;
import PCGamesGroup.PCGamesBackend.Response.SuccessMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class User_Games_DetailsService {
    @Autowired
    private UserDetailRepo userDetailRepo;
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private GameDetailsRepo gameDetailsRepo;
    @Autowired
    private GameDetailsService gameDetailsService;


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

    public Object deleteAllGamesByUserName(String userName){
        UserDetails user = userDetailRepo.findByUserName(userName);

        if(user == null){
            return (new ErrorMessage("Validation Error" , "User is not available with the name : "+userName));
        }
        gameDetailsRepo.deleteGamesByUserId(user.getUserId());
        return (new SuccessMessage("Deleted Successfully" , "All games of user: "+ userName + " are deleted successfully"));
    }
}