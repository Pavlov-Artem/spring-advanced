package com.epam.esm.service.impl;

import com.epam.esm.data.Tag;
import com.epam.esm.data.User;
import com.epam.esm.service.TagDAO;
import com.epam.esm.service.TagService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagDAO tagDAO;

    @InjectMocks
    private TagService tagService = new TagServiceImpl(tagDAO);

    @BeforeEach
    void setup(){
        tagService = new TagServiceImpl(tagDAO);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTags() {
        Long page = 1L;
        Long pageSize = 10L;
        List<Tag> expectedTags = new ArrayList<>();
        for (int i = 0; i < 10 ; i++) {
            expectedTags.add(new Tag());
        }
        Mockito.when(tagDAO.findAll(pageSize, page)).thenReturn(expectedTags);
        List<Tag> actualTags = tagService.getAllTags(page, pageSize);

        Assertions.assertEquals(expectedTags.size(), actualTags.size());
    }

    @Test
    void findTagById() {
    }

    @Test
    void findByName() {
    }

    @Test
    void createTag() {
    }

    @Test
    void removeTag() {
    }

    @Test
    void findMostWidelyUsedTag() {
    }
}