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
import java.util.Arrays;
import java.util.Date;

@Service
public class GameDetailsService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private GameDetailsRepo gameDetailsRepo;
    @Autowired
    private UserDetailRepo userDetailRepo;
    @Autowired
    private GameFileService gameFileService;

    public Object addGameDetails(String gameName, String userName, String gameVideoLink, String gameDescription, String gameInstallInstruction, String gameGenre, String gamePlatform, MultipartFile file1, MultipartFile file2, MultipartFile file3, MultipartFile file4, MultipartFile file5) throws IOException {
        // Validate mandatory fields
        if (gameName == null || gameName.equals("")) {
            return new ErrorMessage("Validation Error", "Game name is mandatory and cannot be null or empty.");
        }

        // Check if gameName is unique
        if (gameDetailsRepo.findByGameName(gameName) != null) {
            return new ErrorMessage("Validation Error", "A game with the name '" + gameName + "' already exists.");
        }

        // Check if the user exists
        UserDetails user = userDetailRepo.findByUserName(userName);
        if (user == null) {
            return new ErrorMessage("Validation Error", "User not found with name: " + userName);
        }
        GameDetails details = new GameDetails();
        // Set binary files
        details.setUserName(userName);
        details.setGameName(gameName);
        details.setGameDescription(gameDescription);
        details.setGameInstallInstruction(gameInstallInstruction);
        details.setGameVideoLink(gameVideoLink);
        details.setGameGenre(Arrays.asList(gameGenre.split(",")));
        details.setGamePlatform(Arrays.asList(gamePlatform.split(",")));
        details.setGameCoverImage(file1 != null ? new Binary(BsonBinarySubType.BINARY, file1.getBytes()) : null);
        details.setGameFirstScreenshot(file2 != null ? new Binary(BsonBinarySubType.BINARY, file2.getBytes()) : null);
        details.setGameSecondScreenshot(file3 != null ? new Binary(BsonBinarySubType.BINARY, file3.getBytes()) : null);
        details.setGameBackgroundImage(file5 != null ? new Binary(BsonBinarySubType.BINARY , file4.getBytes()): null);
        // Set userId
        details.setUserId(user.getUserId());
        details.setGameRating(0.0);
        details.setGameRatingCount(0.0);
        details.setGameRaters(0.0);
        details.setGameDownloadCount(0);
        details.setGameViewCount(0);
        details.setGameIncome(0.0);
        details.setGamePrice(0.0);
        details.setGameLikeCount(0);
        details.setGameCreateDate((new Date()));
//         Save the game file details
        if(file4!=null){
            details.setGameFileId(gameFileService.addGameFile(gameName,file4));
        }
        gameDetailsRepo.save(details);
        return gameDetailsRepo.findByGameName(details.getGameName());
    }
    public String addGameFile(String gameName, MultipartFile gameFile) throws IOException, IllegalStateException{
        System.out.println("This is in Detail service addGame....");
        return "Game File uploaded successfully with ID : " + gameFileService.addGameFile(gameName,gameFile);
    }



    public Object getGameFile(String gameName) throws IOException {
        return gameFileService.getGameFile(gameName);
    }
    public Object getAllGames() throws IOException{
        return gameDetailsRepo.findAll();
    }
    public Object getGameCoverImageByName(String gameName) throws IOException {
        // Validate that the game exists
        GameDetails gameDetails = gameDetailsRepo.findByGameName(gameName);
        if (gameDetails == null) {
            return new ErrorMessage("Validation Error", "Game not found with name: " + gameName);
        }

        // Validate that the image exists
        if (gameDetails.getGameCoverImage() == null) {
            return new ErrorMessage("Validation Error", "No game image available for the game: " + gameName);
        }

        // Return the image data
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(gameDetails.getGameCoverImage().getData(), headers, HttpStatus.OK);
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
                .set("gameCoverImage", file1 != null ? new Binary(BsonBinarySubType.BINARY, file1.getBytes()) : existingGame.getGameCoverImage())
                .set("gameFirstScreenshot", file2 != null ? new Binary(BsonBinarySubType.BINARY, file2.getBytes()) : existingGame.getGameFirstScreenshot())
                .set("gameSecondScreenshot", file3 != null ? new Binary(BsonBinarySubType.BINARY, file3.getBytes()) : existingGame.getGameSecondScreenshot())
                .set("gameVideoLink", details.getGameVideoLink())
                .set("gameRating", details.getGameRatingCount());

        // Perform the update
        GameDetails updatedGame = mongoTemplate.findAndModify(query, update, GameDetails.class);
        return mongoTemplate.findOne(query, GameDetails.class);
    }

    public Object updatePublishStatue(String gameName, boolean publishStatus){
        Query query = new Query(Criteria.where("gameName").is(gameName));
        GameDetails existingGame = mongoTemplate.findOne(query, GameDetails.class);
        if (existingGame == null) {
            return new ErrorMessage("Validation Error", "Game not found with name: " + gameName);
        }
        Update update = new Update()
                .set("publishStatus" , publishStatus);
        // not redundant, used to get the updated data
        GameDetails updatedGame =  mongoTemplate.findAndModify(query, update, GameDetails.class);

        return mongoTemplate.findOne(query, GameDetails.class);
    }
    public Object updateGameRating(String gameName, Double newGameRating){
        Query query = new Query(Criteria.where("gameName").is(gameName));
        GameDetails existingGame = mongoTemplate.findOne(query, GameDetails.class);
        if (existingGame == null) {
            return new ErrorMessage("Validation Error", "Game not found with name: " + gameName);
        }
        double currentRatingSum = existingGame.getGameRatingCount();
        double currentRaters = existingGame.getGameRaters();
        double updatedRaters = currentRaters + 1;
        double updatedRatingSum = currentRatingSum + newGameRating;


//         Calculate the new average rating with precision
        double updatedAverageRating = Math.round((updatedRatingSum / updatedRaters) * 10.0) / 10.0;
        // Update the game details in MongoDB
        Update update = new Update()
                .set("gameRating", updatedAverageRating)
                .set("gameRatingCount", updatedRatingSum)
                .set("gameRaters", updatedRaters);



        GameDetails updatedGame = mongoTemplate.findAndModify(query , update , GameDetails.class);
        return "Game Rating updated successfully";
    }
    public Object updateGameDownloadCount(String gameName){
        Query query = new Query(Criteria.where("gameName").is(gameName));
        GameDetails existingGame = mongoTemplate.findOne(query, GameDetails.class);
        if (existingGame == null) {
            return new ErrorMessage("Validation Error", "Game not found with name: " + gameName);
        }
        Update update = new Update()
                .set("gameDownloadCount" , existingGame.getGameDownloadCount()+1);

        GameDetails updatedGame = mongoTemplate.findAndModify(query , update , GameDetails.class);
        return "Game Download Count updated successfully";
    }
    public Object updateGameViewCount(String gameName){
        Query query = new Query(Criteria.where("gameName").is(gameName));
        GameDetails existingGame = mongoTemplate.findOne(query, GameDetails.class);
        if (existingGame == null) {
            return new ErrorMessage("Validation Error", "Game not found with name: " + gameName);
        }
        Update update = new Update()
                .set("gameViewCount" , existingGame.getGameViewCount()+1);

        GameDetails updatedGame = mongoTemplate.findAndModify(query , update , GameDetails.class);
        return "Game View Count updated successfully";
    }
    public Object updateGameIncome(String gameName){
        Query query = new Query(Criteria.where("gameName").is(gameName));
        GameDetails existingGame = mongoTemplate.findOne(query, GameDetails.class);
        if (existingGame == null) {
            return new ErrorMessage("Validation Error", "Game not found with name: " + gameName);
        }
        Update update = new Update()
                .set("gameIncome" , existingGame.getGameIncome()+existingGame.getGamePrice());

        GameDetails updatedGame = mongoTemplate.findAndModify(query , update , GameDetails.class);
        return "Game Income updated successfully";
    }
    public Object updateGamePrice(String gameName , Double newGamePrice){
        Query query = new Query(Criteria.where("gameName").is(gameName));
        GameDetails existingGame = mongoTemplate.findOne(query, GameDetails.class);
        if (existingGame == null) {
            return new ErrorMessage("Validation Error", "Game not found with name: " + gameName);
        }
        Update update = new Update()
                .set("gamePrice" , newGamePrice);

        GameDetails updatedGame = mongoTemplate.findAndModify(query , update , GameDetails.class);
        return "Game Price updated successfully";
    }

    public Object deleteGameDetailsByName(String gameName) throws IOException {
        // Validate that the game exists
        GameDetails existingGame = gameDetailsRepo.findByGameName(gameName);
        if (existingGame == null) {
            return new ErrorMessage("Validation Error", "Game not found with name: " + gameName);
        }

        // Delete the game
        gameDetailsRepo.deleteByGameName(gameName);
        gameFileService.deleteGameFile(gameName);
        return new SuccessMessage("Success", "Game '" + gameName + "' deleted successfully.");
    }
    public Object deleteGameFile(String gameName) throws IOException{
        return gameFileService.deleteGameFile(gameName);
    }
}