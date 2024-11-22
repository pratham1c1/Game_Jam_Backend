package PCGamesGroup.PCGamesBackend.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.InputStream;

@Document(collection = "VideoFile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoFiles {
    @Id
    private String id;
    @NonNull
    private String title;
    private InputStream stream;
}
