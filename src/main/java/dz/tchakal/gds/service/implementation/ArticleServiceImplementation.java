package dz.tchakal.gds.service.implementation;

import dz.tchakal.gds.dto.ArticleDto;
import dz.tchakal.gds.exception.EntityNotFoundException;
import dz.tchakal.gds.exception.ErrorCode;
import dz.tchakal.gds.exception.InvalidEntityException;
import dz.tchakal.gds.model.Article;
import dz.tchakal.gds.repository.ArticleRepository;
import dz.tchakal.gds.service.ArticleService;
import dz.tchakal.gds.validator.ArticleValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("ArticleServiceImplementation")
@Slf4j
public class ArticleServiceImplementation implements ArticleService {

    private ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImplementation(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public ArticleDto save(ArticleDto articleDto) {
        List<String> errors = ArticleValidator.validate(articleDto);
        if(!errors.isEmpty()){
            log.error("L'article n'est pas valide");
            throw new InvalidEntityException("L'article n'est pas valide", ErrorCode.ARTICLE_NOT_VALIDE,errors);
        }
        return ArticleDto.fromEntity(
                articleRepository.save(ArticleDto.toEntity(articleDto))
        );
    }

    @Override
    public ArticleDto findById(Integer id) {
        if(id==null){
            log.error("Article ID est null");
            return null;
        }
        Optional<Article> article = articleRepository.findById(id);
        ArticleDto articleDto = ArticleDto.fromEntity(article.get());
        return Optional.of(articleDto)
                .orElseThrow(()->new EntityNotFoundException("Aucun article avec l'id = "+id+" n'est trouvé dans la BDD",ErrorCode.ARTICLE_NOT_FOUND));
    }

    @Override
    public ArticleDto findByCode(String code) {
        if(code==null){
            log.error("Article code est null");
            return null;
        }
        Article article = articleRepository.findByCode(code);
        ArticleDto articleDto = ArticleDto.fromEntity(article);
        return Optional.of(articleDto)
                .orElseThrow(()->new EntityNotFoundException("Aucun article avec le code = "+code+" n'est trouvé dans la BDD",ErrorCode.ARTICLE_NOT_FOUND));
    }

    @Override
    public List<ArticleDto> findAll() {
        return articleRepository.findAll()
                .stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id==null){
            throw new InvalidEntityException("L'article avec l'id "+id+" n'est présent dans la BDD",ErrorCode.ARTICLE_NOT_FOUND);
        }else{
            articleRepository.deleteById(id);
        }
    }
}
