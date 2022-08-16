package dz.tchakal.gds.service.implementation;

import dz.tchakal.gds.dto.ClientDto;
import dz.tchakal.gds.exception.EntityNotFoundException;
import dz.tchakal.gds.exception.ErrorCode;
import dz.tchakal.gds.exception.InvalidEntityException;
import dz.tchakal.gds.model.Client;
import dz.tchakal.gds.repository.ClientRepository;
import dz.tchakal.gds.service.ClientService;
import dz.tchakal.gds.util.StaticUtil;
import dz.tchakal.gds.validator.ClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service("ClientServiceImplimentation")
@Slf4j
public class ClientServiceImplimentation implements ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImplimentation(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientDto save(ClientDto clientDto) {
        List<String> errors = ClientValidator.validate(clientDto);
        if (!errors.isEmpty()) {
            log.error("Le client est invalide");
            throw new InvalidEntityException("Le client est invalide", ErrorCode.CLIENT_NOT_VALIDE, errors);
        }
        return ClientDto.fromEntity(clientRepository.save(ClientDto.toEntity(clientDto)));
    }

    @Override
    public ClientDto findById(Integer id) {
        if (id == null) {
            log.error("Le client avec l'id " + id + " n'est pas présent dans la BDD");
            return null;
        }
        Optional<Client> client = clientRepository.findById(id);
        return Optional.of(ClientDto.fromEntity(client.get()))
                .orElseThrow(() -> new EntityNotFoundException(StaticUtil.AUCUN_ELEMENT_TROUVE, ErrorCode.CLIENT_NOT_FOUND));
    }

    @Override
    public ClientDto findByMail(String mail) {
        if (mail == null) {
            log.error("Le client avec l'email " + mail + " n'est pas présent dans la BDD");
            return null;
        }
        Optional<Client> client = clientRepository.findByMail(mail);
        return Optional.of(ClientDto.fromEntity(client.get()))
                .orElseThrow(() -> new EntityNotFoundException(StaticUtil.AUCUN_ELEMENT_TROUVE, ErrorCode.CLIENT_NOT_FOUND));

    }

    @Override
    public List<ClientDto> findAll() {
        return clientRepository.findAll()
                .stream()
                .map(ClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if (id == null) {
            throw new InvalidEntityException(StaticUtil.AUCUN_ELEMENT_TROUVE, ErrorCode.ARTICLE_NOT_FOUND);
        } else {
            clientRepository.deleteById(id);
        }
    }
}
