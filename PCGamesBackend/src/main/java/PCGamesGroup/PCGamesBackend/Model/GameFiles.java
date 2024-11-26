package PCGamesGroup.PCGamesBackend.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.InputStream;

@Document(collection = "GameFiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameFiles {
    @Id
    @Field("_id")
    private String fileId;
    @NonNull
    private String fileTitle;
    private InputStream fileStream;
}
