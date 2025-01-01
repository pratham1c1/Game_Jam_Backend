package PCGamesGroup.PCGamesBackend.Controller;

import PCGamesGroup.PCGamesBackend.Services.User_Games_DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/userGames/")
public class User_Games_DetailsController {

    @Autowired
    private User_Games_DetailsService userGameService;

    @GetMapping("/getAllGames/{userName}")
    public Object getAllUserGames(@PathVariable String userName) throws IOException{
        return userGameService.getAllGamesByUserName(userName);
    }
    @GetMapping("/getGameUserDetails/{gameName}")
    public Object getUserByGameName(@PathVariable String gameName) throws IOException{
        return userGameService.getUserByGameName(gameName);
    }
    @GetMapping("/getUserLikedGame/{userName}")
    public Object getUserLikedGame(@PathVariable String userName) throws IOException{
        return userGameService.getUserLikedGame(userName);
    }

    @PutMapping("/addGameToUserLikedGames/{userName}/{gameName}")
    public Object addGameToUserLikedGames(@PathVariable String userName,@PathVariable String gameName) throws IOException{
        return userGameService.addGameToUserLikedGames(userName, gameName);
    }
    @PutMapping("/removeGameFromUserLikedGames/{userName}/{gameName}")
    public Object removeGameFromUserLikedGames(@PathVariable String userName,@PathVariable String gameName) throws IOException{
        return userGameService.removeGameFromUserLikedGames(userName, gameName);
    }

    @DeleteMapping("/deleteAllGames/{userName}")
    public Object deleteAllUserGames(@PathVariable String userName) throws IOException{
        return userGameService.deleteAllGamesByUserName(userName);
    }
}