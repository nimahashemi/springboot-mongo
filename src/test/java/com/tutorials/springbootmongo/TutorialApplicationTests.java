package com.tutorials.springbootmongo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tutorials.springbootmongo.controller.v1.TutorialController;
import com.tutorials.springbootmongo.dto.TutorialDTO;
import com.tutorials.springbootmongo.service.TutorialService;

import com.tutorials.springbootmongo.service.impl.TutorialServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@Import(TutorialController.class)
@WebMvcTest(TutorialController.class)
public class TutorialApplicationTests {

    @MockBean
    TutorialServiceImpl tutorialService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void testfindAll() throws Exception {
        TutorialDTO tutorial = new TutorialDTO("6412c3e9211d6a1d42befb52", "Spring boot 3", "Tutorial 3", false, 3000);
        List<TutorialDTO> tutorials = Arrays.asList(tutorial);

        Mockito.when(tutorialService.find("")).thenReturn(tutorials);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tutorials"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
