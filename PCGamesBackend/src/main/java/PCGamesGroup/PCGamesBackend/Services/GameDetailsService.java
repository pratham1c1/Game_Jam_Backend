package PCGamesGroup.PCGamesBackend.Services;

import PCGamesGroup.PCGamesBackend.Response.ErrorMessage;
import PCGamesGroup.PCGamesBackend.Model.GameDetails;
import PCGamesGroup.PCGamesBackend.Model.UserDetails;
import PCGamesGroup.PCGamesBackend.Repository.GameDetailsRepo;
import PCGamesGroup.PCGamesBackend.Repository.UserDetailRepo;
import PCGamesGroup.PCGamesBackend.Response.SuccessMessage;
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

@Service
public class GameDetailsService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private GameDetailsRepo gameDetailsRepo;
    @Autowired
    private UserDetailRepo userDetailRepo;

    public Object addGameDetails(GameDetails details, String userName, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
        // Validate mandatory fields
        if (details.getGameName() == null || details.getGameName().isEmpty()) {
            return new ErrorMessage("Validation Error", "Game name is mandatory and cannot be null or empty.");
        }

        // Check if gameName is unique
        if (gameDetailsRepo.findByGameName(details.getGameName()) != null) {
            return new ErrorMessage("Validation Error", "A game with the name '" + details.getGameName() + "' already exists.");
        }

        // Check if the user exists
        UserDetails user = userDetailRepo.findByUserName(userName);
        if (user == null) {
            return new ErrorMessage("Validation Error", "User not found with name: " + userName);
        }

        // Set binary files
        details.setGameImage(file1 != null ? new Binary(BsonBinarySubType.BINARY, file1.getBytes()) : null);
        details.setGameFirstScreenshot(file2 != null ? new Binary(BsonBinarySubType.BINARY, file2.getBytes()) : null);
        details.setGameSecondScreenshot(file3 != null ? new Binary(BsonBinarySubType.BINARY, file3.getBytes()) : null);

        // Set userId
        details.setUserId(user.getUserId());

        // Save the game details
        gameDetailsRepo.save(details);

        return gameDetailsRepo.findByGameName(details.getGameName());
    }


    public Object getGameImageByName(String gameName) throws IOException {
        // Validate that the game exists
        GameDetails gameDetails = gameDetailsRepo.findByGameName(gameName);
        if (gameDetails == null) {
            return new ErrorMessage("Validation Error", "Game not found with name: " + gameName);
        }

        // Validate that the image exists
        if (gameDetails.getGameImage() == null) {
            return new ErrorMessage("Validation Error", "No game image available for the game: " + gameName);
        }

        // Return the image data
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(gameDetails.getGameImage().getData(), headers, HttpStatus.OK);
    }
    public Object getGameFirstSsByName(String gameName) throws IOException {
        // Validate that the game exists
        GameDetails gameDetails = gameDetailsRepo.findByGameName(gameName);
        if (gameDetails == null) {
            return new ErrorMessage("Validation Error", "Game not found with name: " + gameName);
        }

        // Validate that the image exists
        if (gameDetails.getGameFirstScreenshot() == null) {
            return new ErrorMessage("Validation Error", "No game image available for the game: " + gameName);
        }

        // Return the image data
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(gameDetails.getGameFirstScreenshot().getData(), headers, HttpStatus.OK);
    }
    public Object getGameSecondSsByName(String gameName) throws IOException {
        // Validate that the game exists
        GameDetails gameDetails = gameDetailsRepo.findByGameName(gameName);
        if (gameDetails == null) {
            return new ErrorMessage("Validation Error", "Game not found with name: " + gameName);
        }

        // Validate that the image exists
        if (gameDetails.getGameSecondScreenshot() == null) {
            return new ErrorMessage("Validation Error", "No game image available for the game: " + gameName);
        }

        // Return the image data
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(gameDetails.getGameSecondScreenshot().getData(), headers, HttpStatus.OK);
    }


    public Object getGameDetailsByName(String gameName) {
        // Validate that the game exists
        GameDetails gameDetails = gameDetailsRepo.findByGameName(gameName);
        if (gameDetails == null) {
            return new ErrorMessage("Validation Error", "Game not found with name: " + gameName);
        }

        return gameDetails;
    }

    public Object updateGameDetailsByName(GameDetails details, String gameName, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
        // Validate that the game exists
        Query query = new Query(Criteria.where("gameName").is(gameName));
        GameDetails existingGame = mongoTemplate.findOne(query, GameDetails.class);
        if (existingGame == null) {
            return new ErrorMessage("Validation Error", "Game not found with name: " + gameName);
        }

        // Check if the new gameName is unique
        if (!gameName.equals(details.getGameName()) && gameDetailsRepo.findByGameName(details.getGameName()) != null) {
            return new ErrorMessage("Validation Error", "A game with the name '" + details.getGameName() + "' already exists.");
        }

        // Create the update object
        Update update = new Update()
                .set("gameName", details.getGameName())
                .set("gameImage", file1 != null ? new Binary(BsonBinarySubType.BINARY, file1.getBytes()) : existingGame.getGameImage())
                .set("gameFirstScreenshot", file2 != null ? new Binary(BsonBinarySubType.BINARY, file2.getBytes()) : existingGame.getGameFirstScreenshot())
                .set("gameSecondScreenshot", file3 != null ? new Binary(BsonBinarySubType.BINARY, file3.getBytes()) : existingGame.getGameSecondScreenshot())
                .set("gameVideoLink", details.getGameVideoLink())
                .set("gameRating", details.getGameRating());

        // Perform the update
        GameDetails updatedGame = mongoTemplate.findAndModify(query, update, GameDetails.class);

        return updatedGame;
    }


    public Object deleteGameDetailsByName(String gameName) {
        // Validate that the game exists
        GameDetails existingGame = gameDetailsRepo.findByGameName(gameName);
        if (existingGame == null) {
            return new ErrorMessage("Validation Error", "Game not found with name: " + gameName);
        }

        // Delete the game
        gameDetailsRepo.deleteByGameName(gameName);
        return new SuccessMessage("Success", "Game '" + gameName + "' deleted successfully.");
    }

}