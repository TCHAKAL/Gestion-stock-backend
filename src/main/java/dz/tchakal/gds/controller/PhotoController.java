package dz.tchakal.gds.controller;

import com.flickr4java.flickr.FlickrException;
import dz.tchakal.gds.controller.api.PhotoControllerApi;
import dz.tchakal.gds.service.strategy.StrategyPhotoContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class PhotoController implements PhotoControllerApi {

    private StrategyPhotoContext strategyPhotoContext;

    @Autowired
    public PhotoController(StrategyPhotoContext strategyPhotoContext) {
        this.strategyPhotoContext = strategyPhotoContext;
    }

    @Override
    public Object savePhoto(String context, Integer id, MultipartFile photo, String titre) throws IOException, FlickrException {
        return strategyPhotoContext.savePhoto(context,id,photo.getInputStream(),titre);
    }
}
