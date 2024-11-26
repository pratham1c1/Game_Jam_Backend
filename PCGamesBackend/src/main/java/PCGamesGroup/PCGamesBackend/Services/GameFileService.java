package PCGamesGroup.PCGamesBackend.Services;

import PCGamesGroup.PCGamesBackend.Model.GameFiles;
import PCGamesGroup.PCGamesBackend.Response.ErrorMessage;
import PCGamesGroup.PCGamesBackend.Response.SuccessMessage;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class GameFileService {

    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private GridFsOperations gridFsOperations;


    public Object addGameFile(String fileName, MultipartFile file) throws IOException{
        DBObject metaData = new BasicDBObject();
        metaData.put("fileTitle", fileName);
        ObjectId objectId = gridFsTemplate.store(
                file.getInputStream(), fileName , file.getContentType(), metaData);
        return ResponseEntity.ok("Game File uploaded successfully with ID: " + objectId.toString());
    }

    public Object getGameFile(String title) throws IllegalStateException, IOException{
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("filename").is(title)));
        if(file == null){
            return new ErrorMessage("Validation Failed" , "No such Game File with name : " + title);
        }
//        GameFiles gameFiles = new GameFiles();
//        assert file.getMetadata() != null;
//        gameFiles.setFileId(file.getMetadata().get("id").toString());
//        gameFiles.setFileTitle(file.getMetadata().get("title").toString());
//        gameFiles.setFileStream(gridFsOperations.getResource(file).getInputStream());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("inline", title);

        // Return the video stream as the response
        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(gridFsOperations.getResource(file).getInputStream()));
    }

    public Object deleteGameFile(String fileName) throws IOException, IllegalStateException{
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("filename").is(fileName)));
        if(file == null){
            return (new ErrorMessage("Validation Failed" , "No such file is available with the name : "+fileName));
        }
        gridFsTemplate.delete(new Query(Criteria.where("filename").is(fileName)));
        return (new SuccessMessage("Validation Successful","Successfully deleted the file with file name : " + fileName));
    }
}