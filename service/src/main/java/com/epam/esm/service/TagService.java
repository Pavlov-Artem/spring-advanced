package com.epam.esm.service;

import com.epam.esm.data.Tag;

import java.util.List;

public interface TagService {

    List<Tag> getAllTags(Long page, Long pageSize);

    Tag findTagById(Long id) throws DAOException;

    Tag findByName(String name) throws DAOException;

    void createTag(Tag tag) throws DAOException;

    void removeTag(Long id) throws DAOException;

    Tag findMostWidelyUsedTag();
}
