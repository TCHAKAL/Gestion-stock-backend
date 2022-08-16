package dz.tchakal.gds.service.implementation;

import dz.tchakal.gds.dto.MvtStockDto;
import dz.tchakal.gds.exception.EntityNotFoundException;
import dz.tchakal.gds.exception.ErrorCode;
import dz.tchakal.gds.exception.InvalidEntityException;
import dz.tchakal.gds.model.MvtStock;
import dz.tchakal.gds.repository.MvtStockRepository;
import dz.tchakal.gds.service.MvtStockService;
import dz.tchakal.gds.util.StaticUtil;
import dz.tchakal.gds.validator.MvtStockValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("MvtStockServiceImplimentation")
@Slf4j
public class MvtStockServiceImplimentation implements MvtStockService {

    private final MvtStockRepository mvtStockRepository;

    @Autowired
    public MvtStockServiceImplimentation(MvtStockRepository mvtStockRepository) {
        this.mvtStockRepository = mvtStockRepository;
    }

    @Override
    public MvtStockDto save(MvtStockDto mvtStockDto) {
        List<String> errors = MvtStockValidator.validate(mvtStockDto);
        if (!errors.isEmpty()) {
            log.error("Le  mouvement du stock est invalide");
            throw new InvalidEntityException("Le mouvement du stock est invalide", ErrorCode.CLIENT_NOT_VALIDE, errors);
        }
        return MvtStockDto.fromEntity(mvtStockRepository.save(MvtStockDto.toEntity(mvtStockDto)));
    }

    @Override
    public MvtStockDto findById(Integer id) {
        if (id == null) {
            log.error("Le  mouvement du stock avec l'id " + id + " n'est pas présent dans la BDD");
            return null;
        }
        Optional<MvtStock> mvtStock = mvtStockRepository.findById(id);
        return Optional.of(MvtStockDto.fromEntity(mvtStock.get()))
                .orElseThrow(() -> new EntityNotFoundException(StaticUtil.AUCUN_ELEMENT_TROUVE, ErrorCode.CLIENT_NOT_FOUND));
    }

    @Override
    public List<MvtStockDto> findAll() {
        return mvtStockRepository.findAll()
                .stream()
                .map(MvtStockDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            throw new InvalidEntityException(StaticUtil.AUCUN_ELEMENT_TROUVE, ErrorCode.ARTICLE_NOT_FOUND);
        } else {
            mvtStockRepository.deleteById(id);
        }
    }
}
