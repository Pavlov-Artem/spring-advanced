package com.epam.esm.api.rest;

import com.epam.esm.data.Tag;
import com.epam.esm.service.DAOException;
import com.epam.esm.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TagRestController {

    private TagService tagService;

    public TagRestController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tags")
    public ResponseEntity<List<Tag>> getAll() {

        return ResponseEntity.status(HttpStatus.OK).body(tagService.getAllTags());

    }

    @GetMapping("/tags/{id}")
    public ResponseEntity<Tag> getById(@PathVariable Long id) throws DAOException {

        return ResponseEntity.status(HttpStatus.OK).body(tagService.findTagById(id));

    }

    @PostMapping("/tags")
    public ResponseEntity<String> createTag(@RequestBody Tag tag) throws DAOException {

        tagService.createTag(tag);
        return ResponseEntity.status(HttpStatus.CREATED).body("tag was created");

    }

    @DeleteMapping("/tags/{id}")
    public ResponseEntity<String> removeTag(@PathVariable Long id) throws DAOException {

        tagService.removeTag(id);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("tag with id:%s was removed", id));

    }


}
