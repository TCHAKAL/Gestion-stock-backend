package dz.tchakal.gds.controller.api;

import com.flickr4java.flickr.FlickrException;
import dz.tchakal.gds.util.StaticRoot;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(StaticRoot.APP_ROOT+"/photos")
@CrossOrigin(origins = "http://localhost:4200")
public interface PhotoControllerApi {

    @PostMapping(value = StaticRoot.APP_ROOT+"/photos/{id}/{titre}/{context}")
    Object savePhoto(String context, Integer id, @RequestPart("photo") MultipartFile photo, String titre) throws IOException, FlickrException;
}
