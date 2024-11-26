package PCGamesGroup.PCGamesBackend.Controller;

import PCGamesGroup.PCGamesBackend.Model.GameDetails;
import PCGamesGroup.PCGamesBackend.Services.GameDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/games")
public class GameDetailsController {
    @Autowired
    private GameDetailsService gameDetailsService;

    @PostMapping("/addGame")
    public Object addGameDetails(@RequestPart("details") GameDetails details, @RequestPart("userName") String userName , @RequestPart("gameImage") MultipartFile gameImage, @RequestPart("gameFirstSs") MultipartFile gameFirstSs, @RequestPart("gameSecondSs") MultipartFile gameSecondSs) throws IOException {
        return gameDetailsService.addGameDetails(details,userName,gameImage,gameFirstSs,gameSecondSs);
    }
    @PostMapping("/addGameFile")
    public Object addGameFile(@RequestPart("gameName") String gameName , @RequestPart("gameFile") MultipartFile gameFile) throws IOException{
        return gameDetailsService.addGameFile(gameName,gameFile);
    }


    @GetMapping("/getGameFileByGameName/{gameName}")
    public Object getGameFileByGameName(@PathVariable String gameName) throws IOException{
        return gameDetailsService.getGameFile(gameName);
    }
    @GetMapping("/getAllGames")
    public Object getAllGames() throws IOException{
        return gameDetailsService.getAllGames();
    }
    @GetMapping("/getGameImageByName/{gameName}")
    public Object getGameImageByName(@PathVariable String gameName) throws IOException {
        return gameDetailsService.getGameImageByName(gameName);
    }
    @GetMapping("/getGameFirstSsByName/{gameName}")
    public Object getGameFirstSsByName(@PathVariable String gameName) throws IOException {
        return gameDetailsService.getGameFirstSsByName(gameName);
    }
    @GetMapping("/getGameSecondSsByName/{gameName}")
    public Object getGameSecondSsByName(@PathVariable String gameName) throws IOException {
        return gameDetailsService.getGameSecondSsByName(gameName);
    }

    @GetMapping("/getGameDetailsByName/{gameName}")
    public Object getGameDetailsByName(@PathVariable String gameName) throws IOException {
        return gameDetailsService.getGameDetailsByName(gameName);
    }

    @PutMapping("/updateGameDetailsByName/{gameName}")
    public Object updateGameDetails(@RequestPart("details") GameDetails details, @PathVariable("gameName") String gameName , @RequestPart("gameImage") MultipartFile gameImage, @RequestPart("gameFirstSs") MultipartFile gameFirstSs, @RequestPart("gameSecondSs") MultipartFile gameSecondSs) throws IOException {
        return gameDetailsService.updateGameDetailsByName(details,gameName,gameImage,gameFirstSs,gameSecondSs);
    }

    @DeleteMapping("/deleteGameDetailsByName/{gameName}")
    public Object deleteGameDetailsByName(@PathVariable String gameName) throws IOException{
        return gameDetailsService.deleteGameDetailsByName(gameName);
    }
    @DeleteMapping("/deleteGameFileByGameName/{gameName}")
    public Object deleteGameFileByGameName(@PathVariable String gameName) throws IOException{
        return gameDetailsService.deleteGameFile(gameName);
    }

}