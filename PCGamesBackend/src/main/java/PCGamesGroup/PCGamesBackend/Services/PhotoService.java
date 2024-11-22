package PCGamesGroup.PCGamesBackend.Services;


import PCGamesGroup.PCGamesBackend.Model.Photo;
import PCGamesGroup.PCGamesBackend.Repository.PhotoRepo;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepo photoRepo;


    public String addPhoto(String id, String title, MultipartFile file) throws IOException {
        Photo photo = new Photo();
        photo.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        photo.setId(id);
        photo.setTitle(title);
        photo = photoRepo.insert(photo);
        return photo.getId();
    }

    public Photo getPhoto(String id){
        return photoRepo.findById(id).get();
    }
}
