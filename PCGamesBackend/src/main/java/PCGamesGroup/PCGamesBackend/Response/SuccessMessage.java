package PCGamesGroup.PCGamesBackend.Response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessMessage {
    private String status;
    private String message;
}
