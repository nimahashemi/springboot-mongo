package com.tutorials.springbootmongo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Price(int id, int price, String tutorialId) {}
