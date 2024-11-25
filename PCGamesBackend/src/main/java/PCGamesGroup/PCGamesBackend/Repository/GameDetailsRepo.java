package PCGamesGroup.PCGamesBackend.Repository;

import PCGamesGroup.PCGamesBackend.Model.GameDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GameDetailsRepo extends MongoRepository<GameDetails,String> {

    GameDetails findByGameName (String gameName);

    void deleteByGameName (String gameName);
}
