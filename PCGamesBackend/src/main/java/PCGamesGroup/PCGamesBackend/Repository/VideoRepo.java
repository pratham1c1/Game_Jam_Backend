package PCGamesGroup.PCGamesBackend.Repository;


import PCGamesGroup.PCGamesBackend.Model.VideoFiles;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepo extends MongoRepository<VideoFiles,String> {
}
