package com.tutorials.springbootmongo.controller.v1;

import com.tutorials.springbootmongo.dto.SaveDTO;
import com.tutorials.springbootmongo.dto.TutorialDTO;
import com.tutorials.springbootmongo.model.Tutorial;
import com.tutorials.springbootmongo.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TutorialController {

    private final TutorialService tutorialService;

    @Autowired
    public TutorialController(TutorialService tutorialService) {
        this.tutorialService = tutorialService;
    }


    @PostMapping("/tutorials")
    public ResponseEntity<SaveDTO> createTutorial(@RequestBody TutorialDTO tutorial) {
        return new ResponseEntity<>(tutorialService.save(tutorial), HttpStatus.CREATED);
    }

    @GetMapping("/tutorials")
    public ResponseEntity<List<TutorialDTO>> getAllTutorials(@RequestParam(required = false) String title) {
        List<TutorialDTO> list = tutorialService.find(title);
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<TutorialDTO> getTutorialById(@PathVariable("id") String id) {
        TutorialDTO tutorialDTO = tutorialService.findById(id);

        if (tutorialDTO != null) {
            return new ResponseEntity<>(tutorialDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/tutorials/published")
    public ResponseEntity<List<TutorialDTO>> findByPublished() {
        List<TutorialDTO> tutorials = tutorialService.findByPublished(true);

        if (tutorials.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<TutorialDTO> updateTutorial(@PathVariable("id") String id, @RequestBody Tutorial tutorial) {
        TutorialDTO tutorialDTO = tutorialService.findById(id);

        if (tutorialDTO != null) {
            tutorialService.update(new Tutorial(tutorialDTO.id(), tutorialDTO.title(), tutorialDTO.description(), tutorialDTO.published()), tutorial, tutorialDTO.price());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") String id) {
        tutorialService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/tutorials")
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        tutorialService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}