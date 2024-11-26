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

    @DeleteMapping("/deleteAllGames/{userName}")
    public Object deleteAllUserGames(@PathVariable String userName) throws IOException{
        return userGameService.deleteAllGamesByUserName(userName);
    }
}