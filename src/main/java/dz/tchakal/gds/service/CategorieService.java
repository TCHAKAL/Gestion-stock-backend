package dz.tchakal.gds.service;

import dz.tchakal.gds.dto.CategorieDto;

import java.util.List;

public interface CategorieService {
    CategorieDto save(CategorieDto articleDto);

    CategorieDto findById(Integer id);

    CategorieDto findByDesignation(String designation);

    List<CategorieDto> findAll();

    void delete(Integer id);
}
