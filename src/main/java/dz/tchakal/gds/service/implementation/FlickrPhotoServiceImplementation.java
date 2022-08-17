package dz.tchakal.gds.service.implementation;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.uploader.UploadMetaData;
import dz.tchakal.gds.service.FlickrPhotoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@Slf4j
public class FlickrPhotoServiceImplementation implements FlickrPhotoService {

    @Value("${flickr.api.key}")
    private String apiKey;
    @Value("${flickr.api.secret}")
    private String apiSecret;
    @Value("${flickr.api.appSecret}")
    private String appKey;
    @Value("${flickr.api.appSecret}")
    private String appSecret;
    private Flickr flickr;

    @Autowired
    public FlickrPhotoServiceImplementation(Flickr flickr){
        this.flickr=flickr;
    }

    @Override
    public String savePhoto(InputStream photo , String titre) throws FlickrException {
        UploadMetaData uploadMetaData = new UploadMetaData();
        uploadMetaData.setTitle(titre);

        String photoId = flickr.getUploader().upload(photo,uploadMetaData);
        return flickr.getPhotosInterface().getPhoto(photoId).getMedium640Url();
    }

    public void connect(){

    }
}
