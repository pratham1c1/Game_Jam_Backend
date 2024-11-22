package PCGamesGroup.PCGamesBackend.Repository;

import PCGamesGroup.PCGamesBackend.Model.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PhotoRepo extends MongoRepository<Photo,String> {

}
