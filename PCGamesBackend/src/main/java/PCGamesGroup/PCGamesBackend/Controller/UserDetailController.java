package PCGamesGroup.PCGamesBackend.Controller;


import PCGamesGroup.PCGamesBackend.Model.UserDetails;
import PCGamesGroup.PCGamesBackend.Repository.UserDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserDetailController {

    @Autowired
    UserDetailRepo userDetailRepo;

    @PostMapping("/addUser")
    public void addUser(@RequestBody UserDetails userDetails){
        userDetailRepo.save(userDetails);
    }
}