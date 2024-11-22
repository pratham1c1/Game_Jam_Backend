package PCGamesGroup.PCGamesBackend.Services;

import PCGamesGroup.PCGamesBackend.Model.VideoFiles;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class VideoService {

    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private GridFsOperations gridFsOperations;


    public String addVideo(String id, String title, MultipartFile file) throws IOException{
        DBObject metaData = new BasicDBObject();
        metaData.put("type", "video");
        metaData.put("title", title);
        metaData.put("id",id);
        ObjectId objectId = gridFsTemplate.store(
                file.getInputStream(), file.getName(), file.getContentType(), metaData);
        return objectId.toString();
    }

    public VideoFiles getVideo(String id) throws IllegalStateException, IOException{
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        VideoFiles videoFiles = new VideoFiles();
        videoFiles.setId(file.getMetadata().get("id").toString());
        videoFiles.setTitle(file.getMetadata().get("title").toString());
        videoFiles.setStream(gridFsOperations.getResource(file).getInputStream());
        return videoFiles;
    }
}
