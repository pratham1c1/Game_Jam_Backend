package PCGamesGroup.PCGamesBackend.Repository;

import PCGamesGroup.PCGamesBackend.Model.UserDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserDetailRepo extends MongoRepository<UserDetails,Integer> {
}
