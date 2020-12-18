package com.epam.esm.service;

import com.epam.esm.data.Tag;

import java.util.List;

public interface TagService {

    List<Tag> getAllTags();

    Tag findTagById(Long id) throws DAOException;

    void createTag(Tag tag) throws DAOException;

    void removeTag(Long id) throws DAOException;

}
