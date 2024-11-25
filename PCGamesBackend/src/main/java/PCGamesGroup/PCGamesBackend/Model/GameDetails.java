package PCGamesGroup.PCGamesBackend.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.awt.*;

@Document(collection = "gameDetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameDetails {
    @Id
    @Field("_id")
    private String gameId;
    private String userId;
    @NonNull
    @Indexed(unique = true)
    private String gameName;
    private Binary gameImage;
    private Binary gameFirstScreenshot;
    private Binary gameSecondScreenshot;
    private String gameVideoLink;
    private Integer gameRating;
}