package com.tutorials.springbootmongo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveDTO {
    private String id;

    @Override
    public String toString() {
        return "{\"id\":\"" + id + "\"}";
    }
}