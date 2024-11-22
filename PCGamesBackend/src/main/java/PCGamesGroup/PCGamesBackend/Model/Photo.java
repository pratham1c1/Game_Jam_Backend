package PCGamesGroup.PCGamesBackend.Model;

import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection = "Photos")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Photo {

    @Id
    private String id;
    @NonNull
    private String title;
    private Binary image;

}
