package PCGamesGroup.PCGamesBackend.Controller;


import PCGamesGroup.PCGamesBackend.Model.VideoFiles;
import PCGamesGroup.PCGamesBackend.Services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class VideoController {
    @Autowired
    private VideoService videoService;

    @PostMapping("/add/video")
    public ResponseEntity<String> addVideo(
            @RequestParam("id") String id,
            @RequestParam("title") String title,
            @RequestParam("file") MultipartFile file) {
        try {
            // Save the video file
            String videoId = videoService.addVideo(id, title, file);
            return ResponseEntity.ok("Video uploaded successfully with ID: " + videoId);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload video: " + e.getMessage());
        }
    }


    @GetMapping("/get/video/{id}")
    public ResponseEntity<InputStreamResource> getVideo(@PathVariable("id") String id) throws IllegalStateException, IOException{
        VideoFiles videoFiles = videoService.getVideo(id);

        // Set headers for the video response
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("inline", videoFiles.getTitle());

        // Return the video stream as the response
        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(videoFiles.getStream()));
    }
}