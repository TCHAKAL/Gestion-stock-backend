package dz.tchakal.gds.service.strategy;

import com.flickr4java.flickr.FlickrException;
import dz.tchakal.gds.dto.ArticleDto;
import dz.tchakal.gds.exception.ErrorCode;
import dz.tchakal.gds.exception.InvalidOperationException;
import dz.tchakal.gds.service.ArticleService;
import dz.tchakal.gds.service.FlickrPhotoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("articleStrategy")
@Slf4j
public class SaveArticlePhoto implements Strategy<ArticleDto> {

    private FlickrPhotoService flickrPhotoService;
    private ArticleService articleService;

    @Autowired
    public SaveArticlePhoto(FlickrPhotoService flickrPhotoService, ArticleService articleService) {
        this.flickrPhotoService = flickrPhotoService;
        this.articleService = articleService;
    }

    @Override
    public ArticleDto savePhoto(Integer id,InputStream photo, String titre) throws FlickrException {
        ArticleDto articleDto = articleService.findById(id);
        final String urlPhoto = flickrPhotoService.savePhoto(photo, titre);
        if(!StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("URL de la photo est invalide", ErrorCode.URL_PHOTO_INVALIDE);
        }
        articleDto.setPhoto(urlPhoto);
        return articleService.save(articleDto);
    }
}
