package PCGamesGroup.PCGamesBackend.Repository;

import PCGamesGroup.PCGamesBackend.Model.UserDetails;
import org.bson.types.ObjectId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserDetailRepo extends MongoRepository<UserDetails, String> {

    @Query("{ 'userName' : ?0 }")
    UserDetails findByUserName(String userName);

    void deleteByUserName(String userName);
}
