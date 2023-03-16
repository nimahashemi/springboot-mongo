package com.tutorials.springbootmongo;

import com.tutorials.springbootmongo.controller.v1.TutorialController;
import com.tutorials.springbootmongo.dto.SaveDTO;
import com.tutorials.springbootmongo.dto.TutorialDTO;
import com.tutorials.springbootmongo.model.Tutorial;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class IntegrationTests {

    @Autowired
    TutorialController tutorialController;

    @Test
    public void testCreateReadDelete() {
        Tutorial tutorial = new Tutorial("6412c3e9211d6a1d42befb52", "Spring boot 3", "Tutorial 3", false);
        TutorialDTO tutorialDto = new TutorialDTO("6412c3e9211d6a1d42befb52", "Spring boot 3", "Tutorial 3", false, 3000);


        ResponseEntity<SaveDTO> tutorialResult = tutorialController.createTutorial(tutorialDto);

        Iterable<TutorialDTO> tutorials = (Iterable<TutorialDTO>) tutorialController.getAllTutorials("");
        Assertions.assertThat(tutorials).first().hasFieldOrPropertyWithValue("title", "Spring boot 3");

        tutorialController.deleteTutorial(tutorialResult.getBody().getId());
        Assertions.assertThat(tutorialController.getTutorialById(tutorialResult.getBody().getId())).isEqualTo(null);
    }
}