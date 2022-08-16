package dz.tchakal.gds.service;

import dz.tchakal.gds.dto.MvtStockDto;

import java.util.List;

public interface MvtStockService {
    MvtStockDto save(MvtStockDto MvtStockDto);

    MvtStockDto findById(Integer id);

    List<MvtStockDto> findAll();

    void delete(Integer id);
}
