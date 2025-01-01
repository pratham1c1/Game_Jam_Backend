package PCGamesGroup.PCGamesBackend.Model;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Document(collection = "userDetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {
    @Id
    @Field("_id")
    private String userId;

    @NonNull
    @Indexed(unique = true)
    private String userName;

    @NonNull
    private String userEmail;
    private String deleteThisField;

    private List<String> userLikedGames = new ArrayList<>();
}