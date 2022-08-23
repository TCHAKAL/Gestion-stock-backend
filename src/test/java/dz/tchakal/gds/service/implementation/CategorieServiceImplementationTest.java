package dz.tchakal.gds.service.implementation;

import dz.tchakal.gds.dto.CategorieDto;
import dz.tchakal.gds.exception.ErrorCode;
import dz.tchakal.gds.exception.InvalidEntityException;
import dz.tchakal.gds.service.CategorieService;
import dz.tchakal.gds.util.StaticUtil;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@NoArgsConstructor
public class CategorieServiceImplementationTest {
    @Autowired
    private CategorieService categorieService;

    @Test
    public void shouldSaveCategoryWithSuccess() {
        CategorieDto expectedCategorie = CategorieDto.builder()
                .code("Cat Test")
                .designation("Categorie de test")
                .idEntreprise(1)
                .build();

        CategorieDto savedCategorie = categorieService.save(expectedCategorie);

        CategorieDto categorieToUpdate = savedCategorie;
        categorieToUpdate.setCode("CAT 1");
        savedCategorie = categorieService.save(categorieToUpdate);

        assertNotNull(categorieToUpdate);
        assertNotNull(categorieToUpdate.getId());
        assertEquals(categorieToUpdate.getCode(), savedCategorie.getCode());
        assertEquals(categorieToUpdate.getDesignation(), savedCategorie.getDesignation());
        assertEquals(categorieToUpdate.getIdEntreprise(), savedCategorie.getIdEntreprise());
    }

    @Test
    public void shouldUpdateCategoryWithSuccess() {
        CategorieDto expectedCategorie = CategorieDto.builder()
                .code("Cat Test")
                .designation("Categorie de test")
                .idEntreprise(1)
                .build();

        CategorieDto savedCategorie = categorieService.save(expectedCategorie);

        assertNotNull(savedCategorie);
        assertNotNull(savedCategorie.getId());
        assertEquals(expectedCategorie.getCode(), savedCategorie.getCode());
        assertEquals(expectedCategorie.getDesignation(), savedCategorie.getDesignation());
        assertEquals(expectedCategorie.getIdEntreprise(), savedCategorie.getIdEntreprise());
    }

    @Test
    public void shouldUpdateCategoryShouldThrowInvalidEntityException() {
        CategorieDto expectedCategorie = CategorieDto.builder()
                .build();

        InvalidEntityException expectedException = assertThrows(InvalidEntityException.class, () -> categorieService.save(expectedCategorie));
        assertEquals(ErrorCode.CATEGORIE_NOT_VALIDE, expectedException.getErrorCode());
        assertEquals(1, expectedException.getErrors().size());
        assertEquals(StaticUtil.CODE_OBLIGATOIRE, expectedException.getErrors().get(0));
    }

    @Test(expected= NoSuchElementException.class)
    public void shouldUpdateCategoryShouldThrowEntityNotFoundException(){
        categorieService.findById(0);
    }

}