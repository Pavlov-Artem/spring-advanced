package com.epam.esm.api.rest;

import com.epam.esm.data.Tag;
import com.epam.esm.data.UserOrder;
import com.epam.esm.service.DAOException;
import com.epam.esm.service.TagService;
import com.epam.esm.service.data.CreateOrderDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class TagRestController {

    private TagService tagService;

    public TagRestController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tags")
    public ResponseEntity<List<Tag>> getAll(@RequestParam("size") Long pageSize, @RequestParam("page") Long page) throws DAOException {
        List<Tag> tags = tagService.getAllTags(page, pageSize);
        for (Tag tag : tags) {
            addLinks(tag);
        }
        return ResponseEntity.status(HttpStatus.OK).body(tags);
    }

    @GetMapping("/tags/name/{name}")
    public ResponseEntity<Tag> getByName(@PathVariable String name) throws DAOException {
        Tag tag = tagService.findByName(name);
        addLinks(tag);
        return ResponseEntity.status(HttpStatus.OK).body(tag);
    }

    @GetMapping("/tags/{id}")
    public ResponseEntity<Tag> getById(@PathVariable Long id) throws DAOException {
        Tag tag = tagService.findTagById(id);
        addLinks(tag);
        return ResponseEntity.status(HttpStatus.OK).body(tag);
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

    @GetMapping("/most-widely-used-tag")
    public ResponseEntity<Tag> findMostWidelyUsedTag(){
        return ResponseEntity.status(HttpStatus.OK).body(tagService.findMostWidelyUsedTag());
    }

    private void addLinks(Tag tag) throws DAOException {
        tag.add(linkTo(methodOn(TagRestController.class).getById(tag.getId())).withSelfRel());
        tag.add(linkTo(methodOn(TagRestController.class).createTag(tag)).withRel("create"));
        tag.add(linkTo(methodOn(TagRestController.class).removeTag(tag.getId())).withRel("remove"));
    }


}
