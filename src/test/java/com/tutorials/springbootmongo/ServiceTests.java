package com.tutorials.springbootmongo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.tutorials.springbootmongo.dto.TutorialDTO;
import com.tutorials.springbootmongo.model.Tutorial;
import com.tutorials.springbootmongo.repository.TutorialRepository;
import com.tutorials.springbootmongo.service.impl.TutorialServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ServiceTests
{
    @InjectMocks
    TutorialServiceImpl tutorialService;

    @Mock
    TutorialRepository dao;

    @Test
    public void testFindAllTutorials()
    {
        List<Tutorial> list = new ArrayList<Tutorial>();
        Tutorial tutorialOne = new Tutorial("6412c3e9211d6a1d42befb52", "Spring boot 3", "Tutorial 3", false);

        list.add(tutorialOne);
        when(dao.findAll()).thenReturn(list);

        //test
        List<TutorialDTO> empList = tutorialService.find("");

        assertEquals(1, empList.size());
        verify(dao, times(1)).findAll();
    }

    @Test
    public void testCreateOrSaveTutorial()
    {
        Tutorial tutorial = new Tutorial("6412c3e9211d6a1d42befb52", "Spring boot 3", "Tutorial 3", false);
        TutorialDTO tutorialDto = new TutorialDTO("6412c3e9211d6a1d42befb52", "Spring boot 3", "Tutorial 3", false, 3000);

        tutorialService.save(tutorialDto);
        verify(dao, times(1)).save(tutorial);
    }
}
