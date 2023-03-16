package com.tutorials.springbootmongo;

import com.tutorials.springbootmongo.dto.SaveDTO;
import com.tutorials.springbootmongo.dto.TutorialDTO;
import com.tutorials.springbootmongo.model.Tutorial;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SystemTests {

    @Test
    public void testCreateReadDelete() {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8080/api/v1/tutorials";

        Tutorial tutorial = new Tutorial("6412c3e9211d6a1d42befb52", "Spring boot 3", "Tutorial 3", false);
        TutorialDTO tutorialDto = new TutorialDTO("", "Spring boot 3", "Tutorial 3", false, 3000);

        ResponseEntity<SaveDTO> entity = restTemplate.postForEntity(url, tutorialDto, SaveDTO.class);

        TutorialDTO[] tutorials = restTemplate.getForObject(url, TutorialDTO[].class);
        assertNotNull("Entity should not be null.", entity.toString());
        // Assertions.assertThat(tutorials).extracting(TutorialDTO::title).containsOnly("Spring");

        restTemplate.delete(url + "/" + entity.getBody().getId());
        Assertions.assertThat(restTemplate.getForObject(url, TutorialDTO[].class)).isEmpty();
    }
}
