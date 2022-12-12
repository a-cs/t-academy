package com.twms.wms.services;

import com.twms.wms.entities.Address;
import com.twms.wms.entities.Branch;
import com.twms.wms.entities.Category;
import com.twms.wms.repositories.CategoryRepository;
import com.twms.wms.repositories.SKURepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CategoryServiceTest {

    @InjectMocks
    @Spy
    private CategoryService service;

    @Mock
    private CategoryRepository repository;

    @Mock
    private SKURepository skuRepository;

    @Test
    public void returnCategoryWhenCreateCategory(){
        Category category = new Category();

        Mockito.when(repository.save(category)).thenReturn(category);
        Assertions.assertNotNull(service.createGategory(category));

        Mockito.verify(repository,Mockito.times(1)).save(category);
    }
    @Test
    public void returnExceptionWhenCreateCategoryThatAlreadyExists(){
        Category category = new Category();
        category.setId(1L);
        category.setName("NewCategory");

        Mockito.when(repository.findByName(category.getName())).thenReturn(category);
        Mockito.when(repository.save(category)).thenReturn(category);
        Assertions.assertThrows(SQLIntegrityConstraintViolationException.class,() -> service.createGategory(category));

        //Mockito.verify(repository,Mockito.times(1)).save(category);
    }

    @Test
    public void VoidResponseWhenACategoryIsDeleted(){
        Category category = new Category();
        category.setId(1L);
        category.setName("NewCategory");


        Mockito.when(skuRepository.existsByCategoryId(any())).thenReturn(false);
        Mockito.doNothing().when(repository).deleteById(category.getId());

        Assertions.assertDoesNotThrow(()->service.delete(category.getId()));
        Mockito.verify(repository, Mockito.times(1)).deleteById(any());
    }
    @Test
    public void ThrowExceptionWhenACategoryShouldNotBeDeleted(){
        Category category = new Category();
        category.setId(1L);
        category.setName("NewCategory");


        Mockito.when(skuRepository.existsByCategoryId(any())).thenReturn(true);
        Mockito.doNothing().when(repository).deleteById(category.getId());

        Assertions.assertThrows(SQLIntegrityConstraintViolationException.class,()->service.delete(category.getId()));
        //Mockito.verify(repository, Mockito.times(1)).deleteById(any());
    }

    @Test
    public void PageResponseWhenReadingCategories(){
        List<Category> categories = new ArrayList<>();
        Mockito.when(repository.findAll(PageRequest.of(0,1))).thenReturn(new PageImpl(categories));

        Assertions.assertNotNull(service.readCategories(PageRequest.of(0,1)));
    }

    @Test
    public void shouldGetOneCategoryWhenReadById(){
        Category category = new Category();
        category.setName("NewCategory");

        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.of(category));

        Category response = service.readById(anyLong());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(category, response);
        Mockito.verify(repository, Mockito.times(1)).findById(anyLong());
    }

    @Test
    void shouldGetAllCategories() {
        List<Category> categories = new ArrayList<>();
        Mockito.when(repository.findAll()).thenReturn(categories);

        Assertions.assertNotNull(service.readCategories());
        Mockito.verify(repository).findAll();
    }

    @Test
    void shouldNotPutByInvalidId() {
        Category category = new Category();
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> service.update(1L, category)
        );

        Mockito.verify(repository, times(0)).save(category);
    }

    @Test
    public void shouldReturnCategoryWhenUpdated(){
        Category category = new Category();
        category.setId(1L);
        category.setName("NewCategory");

        Mockito.doReturn(category).when(service).readById(1L);
        Mockito.doReturn(category).when(service).createGategory(category);

        Assertions.assertNotNull(service.update(1L,category));
    }

    @Test
    public void shouldReturnPageWhenSearchedByTerm(){
        List<Category> categories = new ArrayList<>();

        Mockito.when(repository.findByNameContainingIgnoreCase(any(String.class),any(Pageable.class))).thenReturn(new PageImpl(categories));

        Assertions.assertNotNull(service.searchTerm("any",PageRequest.of(0,1)));
    }

}
