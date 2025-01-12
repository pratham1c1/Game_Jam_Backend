package PCGamesGroup.PCGamesBackend.Controller;

import PCGamesGroup.PCGamesBackend.Model.UserDetails;
import PCGamesGroup.PCGamesBackend.Services.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserDetailController {

    @Autowired
    UserDetailService userDetailService;

    @PostMapping("/addUser")
    public Object addUser(@RequestPart(value = "userDetailsJson") String userDetailsJson,@RequestPart(value="userProfileImage", required = false) MultipartFile userProfileImage,@RequestPart(value="userProfileBgImage", required = false) MultipartFile userProfileBgImage) throws IOException {
        return userDetailService.addUserDetails(userDetailsJson,userProfileImage,userProfileBgImage);
    }

    @GetMapping("/getUserByName/{userName}")
    public Object getUserByName(@PathVariable String userName){
        return userDetailService.getUserDetailsByName(userName);
    }
    @GetMapping("/getUserByUserId/{userId}")
    public Object getUserByUserId(@PathVariable String userId){
        return userDetailService.getUserDetailsByUserId(userId);
    }
    @GetMapping("/getAllUsers")
    public List<UserDetails> getAllUser(){
        return userDetailService.getAllUserDetails();
    }
    @GetMapping("/getUserProfileImageByName/{userName}")
    public Object getUserProfileImageByName(@PathVariable String userName) throws IOException{
        return userDetailService.getUserProfileImageByName(userName);
    }
    @GetMapping("/getUserProfileBgImageByName/{userName}")
    public Object getUserProfileBgImageByName(@PathVariable String userName) throws IOException{
        return userDetailService.getUserProfileBgImageByName(userName);
    }


    @PutMapping("/updateUserPasswordByName/{userName}")
    public Object updateUserPasswordByName(@PathVariable String userName,@RequestBody String userPassword){
        return userDetailService.updateUserPasswordByName(userName,userPassword);
    }
    @PutMapping("/updateUserInfoByName/{userName}")
    public Object updateUserInfoByName(@PathVariable String userName,@RequestBody UserDetails userDetails){
        return userDetailService.updateUserInfoByName(userName,userDetails);
    }
    @PutMapping("/updateUserProfileImageByName/{userName}")
    public Object updateUserProfileImageByName(@PathVariable String userName,@RequestPart(value="userProfileImage", required = false) MultipartFile userProfileImage) throws IOException {
        return userDetailService.updateUserProfileImageByName(userName,userProfileImage);
    }
    @PutMapping("/updateUserProfileBgImageByName/{userName}")
    public Object updateUserProfileBgImageByName(@PathVariable String userName,@RequestPart(value="userProfileBgImage", required = false) MultipartFile userProfileBgImage) throws IOException {
        return userDetailService.updateUserProfileBgImageByName(userName,userProfileBgImage);
    }



    @DeleteMapping("/deleteUserByName/{userName}")
    public Object deleteUserByName(@PathVariable String userName){
        return userDetailService.deleteUserDetailsByName(userName);
    }
}