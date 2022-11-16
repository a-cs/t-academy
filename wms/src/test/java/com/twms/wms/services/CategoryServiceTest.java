package com.twms.wms.services;

import com.twms.wms.entities.Branch;
import com.twms.wms.entities.Category;
import com.twms.wms.repositories.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService service;

    @Mock
    private CategoryRepository repository;

    @Test
    public void returnCategoryWhenCreateCategory(){
        Category category = new Category();

        Mockito.when(repository.save(category)).thenReturn(category);
        Assertions.assertNotNull(service.createGategory(category));

        Mockito.verify(repository,Mockito.times(1)).save(category);
    }

    @Test
    public void VoidResponseWhenACategoryIsDeleted(){
        Category category = new Category();
        category.setId(1L);
        category.setName("NewCategory");

        Mockito.when(repository.findById(category.getId())).thenReturn(Optional.of(category));
        Mockito.doNothing().when(repository).delete(category);

        Assertions.assertDoesNotThrow(()->service.delete(category.getId()));
        Mockito.verify(repository, Mockito.times(1)).delete(category);
    }
}
