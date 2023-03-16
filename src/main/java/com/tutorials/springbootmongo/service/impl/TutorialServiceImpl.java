package com.tutorials.springbootmongo.service.impl;

import com.tutorials.springbootmongo.dto.Price;
import com.tutorials.springbootmongo.dto.SaveDTO;
import com.tutorials.springbootmongo.dto.TutorialDTO;
import com.tutorials.springbootmongo.exception.ApplicationException;
import com.tutorials.springbootmongo.exception.BadRequestException;
import com.tutorials.springbootmongo.model.Tutorial;
import com.tutorials.springbootmongo.repository.TutorialRepository;
import com.tutorials.springbootmongo.service.TutorialService;
import com.tutorials.springbootmongo.thirdparties.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TutorialServiceImpl implements TutorialService {

    private final TutorialRepository tutorialRepository;
    private final PriceService priceService;

    @Autowired
    public TutorialServiceImpl(TutorialRepository tutorialRepository,
                               PriceService priceService) {
        this.tutorialRepository = tutorialRepository;
        this.priceService = priceService;
    }


    @Override
    public List<TutorialDTO> find(String title) {
        List<TutorialDTO> list = new ArrayList<>();
        List<Tutorial> tutorials = new ArrayList<Tutorial>();

        try {
            if (title == null) {
                tutorialRepository.findAll().forEach(tutorials::add);
            } else {
                tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);
            }

            if (!tutorials.isEmpty()) {
                List<Price> prices = priceService.getAllPrices();
                tutorials.parallelStream().forEach(tutorial -> {
                    Optional<Price> p = prices.stream().filter(price -> price.tutorialId().equals(tutorial.getId())).findFirst();
                    if (p.isPresent()) {
                        Price obj = p.get();
                        TutorialDTO dto = new TutorialDTO(tutorial.getId(), tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished(), obj.price());
                        list.add(dto);
                    }
                });
            }
        } catch (Exception e) {
            throw new ApplicationException(1103, "An Exception occurred!");
        }

        return list;
    }

    @Override
    public TutorialDTO findById(String id) {
        TutorialDTO tutorialDTO = null;
        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

        if (!tutorialData.isPresent()) throw new BadRequestException(1104, "Tutorial not found.");
        else {
            Tutorial tutorial = tutorialData.get();
            Price price = priceService.getById(tutorial.getId());
            if (price == null) throw new BadRequestException(1105, "Price not found");
            tutorialDTO = new TutorialDTO(tutorial.getId(), tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished(), price.price());
        }

        return tutorialDTO;
    }

    @Override
    public List<TutorialDTO> findByPublished(boolean isPublished) {
        List<TutorialDTO> list = new ArrayList<>();

        try {
            List<Tutorial> tutorials = tutorialRepository.findByPublished(isPublished);
            if (tutorials.isEmpty()) throw new BadRequestException(1104, "Tutorial not found");
            else {
                List<Price> prices = priceService.getAllPrices();
                tutorials.parallelStream().forEach(tutorial -> {
                    Optional<Price> p = prices.stream().filter(price -> price.tutorialId().equals(tutorial.getId())).findFirst();
                    if (p.isPresent()) {
                        Price obj = p.get();
                        TutorialDTO dto = new TutorialDTO(tutorial.getId(), tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished(), obj.price());
                        list.add(dto);
                    }
                });
            }
        } catch (Exception e) {
            throw new ApplicationException(1103, "An Exception occurred!");
        }
        return list;
    }

    @Override
    public SaveDTO save(TutorialDTO tutorialDTO) {
        SaveDTO saveDTO = new SaveDTO();
        try {
            Tutorial tutorial = new Tutorial(tutorialDTO.title(), tutorialDTO.description(), false);
            Tutorial _tutorial = tutorialRepository.save(tutorial);
            priceService.savePrice(tutorialDTO.price(), _tutorial.getId());
            saveDTO.setId(_tutorial.getId());
        } catch (Exception e) {
            throw new ApplicationException(1103, "An Exception occurred!");
        }
        return saveDTO;
    }

    @Override
    public void update(Tutorial tutorial, Tutorial modifiedTutorial, int price) {
        try {
            tutorial.setTitle(tutorial.getTitle());
            tutorial.setDescription(tutorial.getDescription());
            tutorial.setPublished(tutorial.isPublished());
            tutorialRepository.save(tutorial);
            priceService.updatePrice(price, tutorial.getId());
        } catch (Exception e) {
            throw new ApplicationException(1103, "An Exception occurred!");
        }
    }

    @Override
    public void delete(String id) {
        try {
            tutorialRepository.deleteById(id);
        } catch (Exception e) {
            throw new ApplicationException(1103, "An Exception occurred!");
        }
    }

    @Override
    public void deleteAll() {
        try {
            tutorialRepository.deleteAll();
        } catch (Exception e) {
            throw new ApplicationException(1103, "An Exception occurred!");
        }
    }
}
