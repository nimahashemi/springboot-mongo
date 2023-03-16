package com.tutorials.springbootmongo;

import com.tutorials.springbootmongo.controller.v1.TutorialController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SpringbootMongoApplicationTests {

    @Autowired
    TutorialController tutorialController;

    @Test
    void contextLoads() {
        Assertions.assertThat(tutorialController).isNotEqualTo(null);
    }

}
