package PCGamesGroup.PCGamesBackend.Repository;

import PCGamesGroup.PCGamesBackend.Model.GameDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GameDetailsRepo extends MongoRepository<GameDetails,String> {

    GameDetails findByGameName (String gameName);

    List<GameDetails> findByUserId (String userId);

    void deleteByGameName (String gameName);
    void deleteGamesByUserId (String userId);
}