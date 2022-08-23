package dz.tchakal.gds.service.strategy;

import com.flickr4java.flickr.FlickrException;
import dz.tchakal.gds.exception.ContextNotFoundException;
import dz.tchakal.gds.exception.ErrorCode;
import lombok.Setter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class StrategyPhotoContext {

    private BeanFactory beanFactory;
    private Strategy strategy;
    @Setter
    private String context;

    @Autowired
    public StrategyPhotoContext(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object savePhoto(String context, Integer id, InputStream photo, String titre) throws FlickrException {
        determinateContext(context);
        return strategy.savePhoto(id,photo,titre);
    }
    private void determinateContext(String context) {
        final String beanName = context + "Strategy";
        switch (context) {
            case "article":
                strategy = beanFactory.getBean("beanName", SaveArticlePhoto.class);
                break;
            case "client":
                strategy = beanFactory.getBean("beanName", SaveClientPhoto.class);
                break;
            case "fournisseur":
                strategy = beanFactory.getBean("beanName", SaveFournisseurPhoto.class);
                break;
            case "utilisateur":
                strategy = beanFactory.getBean("beanName", SaveUtilisateurPhoto.class);
                break;
            case "entreprise":
                strategy = beanFactory.getBean("beanName", SaveEntreprisePhoto.class);
                break;
            default:
                throw new ContextNotFoundException("Context inconnu pour l'enregistrement de la photo", ErrorCode.CONTEXT_NOT_FOUND);
        }
    }
}
