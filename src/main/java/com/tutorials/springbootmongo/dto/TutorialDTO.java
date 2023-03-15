package com.tutorials.springbootmongo.dto;

public record TutorialDTO(String id, String title, String description, boolean published, int price) {
}
