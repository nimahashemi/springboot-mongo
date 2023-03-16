package com.tutorials.springbootmongo.service;

import com.tutorials.springbootmongo.dto.SaveDTO;
import com.tutorials.springbootmongo.dto.TutorialDTO;
import com.tutorials.springbootmongo.model.Tutorial;

import java.util.List;

public interface TutorialService {

    List<TutorialDTO> find(String title);
    TutorialDTO findById(String id);
    List<TutorialDTO> findByPublished(boolean isPublished);
    SaveDTO save(TutorialDTO tutorialDTO);
    void update(Tutorial tutorial, Tutorial modifiedTutorial, int price);
    void delete(String id);
    void deleteAll();



}
