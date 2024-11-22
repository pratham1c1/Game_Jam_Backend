package PCGamesGroup.PCGamesBackend.Model;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {
    @Id
    private Integer userId;
    private String name;
    private String email;
    private Integer noOfGame;
}
