package PCGamesGroup.PCGamesBackend.Services;

import PCGamesGroup.PCGamesBackend.Model.UserDetails;
import PCGamesGroup.PCGamesBackend.Repository.UserDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDetailService {

    @Autowired
    private UserDetailRepo userRepo;


    public String returnName(){
        UserDetails user = new UserDetails();
        String name = user.getName();
        return "abc";
    }
}