package PCGamesGroup.PCGamesBackend.Repository;


import PCGamesGroup.PCGamesBackend.Model.GameFiles;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameFileRepo extends MongoRepository<GameFiles,String> {
}
