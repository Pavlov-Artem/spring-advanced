package com.epam.esm.service.impl;

import com.epam.esm.data.Tag;
import com.epam.esm.service.DAOException;
import com.epam.esm.service.TagDAO;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exceptions.EntityNotFoundDaoException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private TagDAO tagDAO;

    public TagServiceImpl(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    @Override
    public List<Tag> getAllTags(Long page, Long pageSize) {
        return tagDAO.findAll(pageSize,page);
    }

    @Override
    public Tag findTagById(Long id) throws DAOException {

        Optional<Tag> tag = tagDAO.findById(id);
        if (tag.isPresent()) {
            return tag.get();
        } else {
            throw new EntityNotFoundDaoException("tag not found", id);
        }
    }

    @Override
    public Tag findByName(String name) {
        return tagDAO.findByName(name);
    }

    @Override
    public void createTag(Tag tag) throws DAOException {
        tagDAO.createEntity(tag);
    }

    @Override
    public void removeTag(Long id) throws DAOException {

//        Optional<Tag> tag = tagDAO.findById(id);
//        if (tag.isPresent()) {
//            tagDAO.deleteCertificate(tag.get());
//        } else {
//            throw new EntityNotFoundDaoException("cannot remove tag because tag with such id doen't exist", id);
//        }
    }

    @Override
    public Tag findMostWidelyUsedTag() {
        return tagDAO.findMostWidelyUsedTag();
    }
}
