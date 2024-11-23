package PCGamesGroup.PCGamesBackend.Controller;

import PCGamesGroup.PCGamesBackend.Model.UserDetails;
import PCGamesGroup.PCGamesBackend.Services.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserDetailController {

    @Autowired
    UserDetailService userDetailService;

    @PostMapping("/addUser")
    public Object addUser(@RequestBody UserDetails userDetails){
        return userDetailService.addUserDetails(userDetails);
    }

    @GetMapping("/getUserByName/{userName}")
    public Object getUserByName(@PathVariable String userName){
        return userDetailService.getUserDetailsByName(userName);
    }

    @GetMapping("/getAllUsers")
    public List<UserDetails> getAllUser(){
        return userDetailService.getAllUserDetails();
    }

    @PutMapping("/updateUserByName/{userName}")
    public Object updateUser(@PathVariable String userName , @RequestBody UserDetails userDetails){
        return userDetailService.updateUserDetailsByName(userName,userDetails);
    }

    @DeleteMapping("/deleteUserByName/{userName}")
    public Object deleteUserByName(@PathVariable String userName){
        return userDetailService.deleteUserDetailsByName(userName);
    }
}