package com.tutorials.springbootmongo;

import com.tutorials.springbootmongo.dto.TutorialDTO;
import com.tutorials.springbootmongo.model.Tutorial;
import com.tutorials.springbootmongo.repository.TutorialRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DaoTests {

//    @Autowired
//    TutorialRepository tutorialRepository;
//
//    @Test
//    public void testCreateReadDelete() {
//        Tutorial tutorial = new Tutorial("6412c3e9211d6a1d42befb52", "Spring boot 3", "Tutorial 3", false);
//
//        tutorialRepository.save(tutorial);
//
//        Iterable<Tutorial> employees = tutorialRepository.findAll();
//        Assertions.assertThat(employees).extracting(Tutorial::getTitle).containsOnly("Spring boot 3");
//
//        tutorialRepository.deleteAll();
//        Assertions.assertThat(tutorialRepository.findAll()).isEmpty();
//    }
}
