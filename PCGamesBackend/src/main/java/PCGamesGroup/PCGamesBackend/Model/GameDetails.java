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

@Document(collection = "gameDetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameDetails {
    @Id
    @Field("_id")
    private String gameId;
    private String userId;
    private String userName;
    private String gameFileId;
    @NonNull
    @Indexed(unique = true)
    private String gameName;
    private String gameDescription;
    private String gameInstallInstruction;
    private Binary gameCoverImage;
    private Binary gameFirstScreenshot;
    private Binary gameSecondScreenshot;
    private Binary gameBackgroundImage;
    private String gameVideoLink;
    private Integer gameRating;
    private boolean publishStatus;

}