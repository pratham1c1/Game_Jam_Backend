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

    /**
     * @param gameName
     * @param gameDescription
     * @param gameInstallInstruction
     * @param userName
     * @param gameVideoLink
     * @param gameCoverImage
     * @param gameFirstSs
     * @param gameSecondSs
     * @param gameFile
     * @param gameBackgroundImage
     * @return
     * @throws IOException
     */
    @PostMapping("/addGame")
    public Object addGameDetails(@RequestPart("gameName") String gameName, @RequestPart(value = "userName" , required = true) String userName,@RequestPart(value = "gameVideoLink" , required = false) String gameVideoLink,@RequestPart(value = "gameDescription", required = false) String gameDescription,@RequestPart(value = "gameInstallInstruc" , required = false) String gameInstallInstruction  , @RequestPart(value="gameCoverImage", required = false) MultipartFile gameCoverImage, @RequestPart(value="gameFirstSs", required = false) MultipartFile gameFirstSs, @RequestPart(value="gameSecondSs", required = false) MultipartFile gameSecondSs,@RequestPart(value="gameFile", required = false) MultipartFile gameFile,@RequestPart(value="gameBackgroundImage", required = false) MultipartFile gameBackgroundImage) throws IOException {
        return gameDetailsService.addGameDetails(gameName,userName,gameVideoLink,gameDescription,gameInstallInstruction,gameCoverImage,gameFirstSs,gameSecondSs,gameFile,gameBackgroundImage);
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
        return gameDetailsService.getGameCoverImageByName(gameName);
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
    public Object updateGameDetails(@RequestPart("details") GameDetails details, @PathVariable("gameName") String gameName , @RequestPart("gameCoverImage") MultipartFile gameCoverImage, @RequestPart("gameFirstSs") MultipartFile gameFirstSs, @RequestPart("gameSecondSs") MultipartFile gameSecondSs) throws IOException {
        return gameDetailsService.updateGameDetailsByName(details,gameName,gameCoverImage,gameFirstSs,gameSecondSs);
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