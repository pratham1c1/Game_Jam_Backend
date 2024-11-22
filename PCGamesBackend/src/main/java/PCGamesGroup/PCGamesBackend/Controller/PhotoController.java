package PCGamesGroup.PCGamesBackend.Controller;


import PCGamesGroup.PCGamesBackend.Model.Photo;
import PCGamesGroup.PCGamesBackend.Repository.PhotoRepo;
import PCGamesGroup.PCGamesBackend.Services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@RestController
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @PostMapping("/add/photo")
    public String addPhoto(@RequestParam("id") String id,@RequestParam("title") String title, @RequestParam("image")MultipartFile image, Model model) throws IOException {
        String Id = photoService.addPhoto(id,title,image);
        return "Photo "+id + " : " + Id;
    }

    @GetMapping("/photo/{id}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable String id) {
        Photo photo = photoService.getPhoto(id);

        // Set the Content-Type header to the appropriate image type (e.g., image/jpeg)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        // Return the image data as a byte array
        return new ResponseEntity<>(photo.getImage().getData(), headers, HttpStatus.OK);
    }

}