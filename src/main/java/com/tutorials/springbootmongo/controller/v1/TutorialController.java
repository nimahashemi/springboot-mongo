package com.tutorials.springbootmongo.controller.v1;

import com.tutorials.springbootmongo.dto.Price;
import com.tutorials.springbootmongo.dto.TutorialDTO;
import com.tutorials.springbootmongo.model.Tutorial;
import com.tutorials.springbootmongo.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class TutorialController {

    private final TutorialRepository tutorialRepository;

    @Autowired
    public TutorialController(TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    @PostMapping("/tutorials")
    public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
        try {
            // Tutorial _tutorial = tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
            Tutorial _tutorial = tutorialRepository.save(tutorial);
            return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tutorials")
    public ResponseEntity<List<TutorialDTO>> getAllTutorials(@RequestParam(required = false) String title) {
        try {
            WebClient webClient = WebClient.create("http://localhost:3000");

            List<Price> priceList = new ArrayList<>();
            Flux<Price> prices = webClient.get()
                    .uri("/prices")
                    .retrieve()
                    .bodyToFlux(Price.class);
            // prices.subscribe();
            prices.toStream().forEach(priceList::add);

            List<Tutorial> tutorials = new ArrayList<Tutorial>();
            List<TutorialDTO> list = new ArrayList<>();

            if (title == null) {
                tutorialRepository.findAll().forEach(tutorials::add);
                tutorials.parallelStream().forEach(tutorial -> {
                    Optional<Price> p = prices.toStream().filter(price -> price.tutorialId().equals(tutorial.getId())).findFirst();
                    if (p.isPresent()) {
                        Price obj = p.get();
                        TutorialDTO t = new TutorialDTO(tutorial.getId(), tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished(), obj.price());
                        list.add(t);
                    }
                });
            }
            else {
                tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);
                tutorials.parallelStream().forEach(tutorial -> {
                    Optional<Price> p = prices.toStream().filter(price -> price.tutorialId().equals(tutorial.getId())).findFirst();
                    if (p.isPresent()) {
                        Price obj = p.get();
                        TutorialDTO t = new TutorialDTO(tutorial.getId(), tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished(), obj.price());
                        list.add(t);
                    }
                });
            }

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") String id) {
        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

        if (tutorialData.isPresent()) {
            return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/tutorials/published")
    public ResponseEntity<List<Tutorial>> findByPublished() {
        try {
            List<Tutorial> tutorials = tutorialRepository.findByPublished(true);

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") String id, @RequestBody Tutorial tutorial) {
        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

        if (tutorialData.isPresent()) {
            Tutorial _tutorial = tutorialData.get();
            _tutorial.setTitle(tutorial.getTitle());
            _tutorial.setDescription(tutorial.getDescription());
            _tutorial.setPublished(tutorial.isPublished());
            return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") String id) {
        try {
            tutorialRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/tutorials")
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        try {
            tutorialRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}