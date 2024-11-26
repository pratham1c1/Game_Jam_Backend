package PCGamesGroup.PCGamesBackend.Controller;


import PCGamesGroup.PCGamesBackend.Model.GameFiles;
import PCGamesGroup.PCGamesBackend.Services.GameFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/gameFile")
public class GameFileController {
    @Autowired
    private GameFileService gameFileService;

    @PostMapping("/addGameFile")
    public Object addGameFile(@RequestParam("fileName") String fileName,@RequestParam("file") MultipartFile file) throws IOException{
        return gameFileService.addGameFile(fileName,file);
    }


    @GetMapping("/getGameFile/{fileTitle}")
    public Object getGameFile(@PathVariable String fileTitle) throws IllegalStateException, IOException{
       return gameFileService.getGameFile(fileTitle);
        // Set headers for the video response
    }

    @DeleteMapping("/deleteGameFile/{fileTitle}")
    public Object deleteGameFile(@PathVariable String fileTitle) throws IllegalStateException,IOException{
        return gameFileService.deleteGameFile(fileTitle);
    }

}