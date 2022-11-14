package com.twms.wms.services;

import com.twms.wms.entities.Category;
import com.twms.wms.repositories.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

    
}
