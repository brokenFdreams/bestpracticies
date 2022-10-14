package com.brokenfdreams.bestpractices.dto;


import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.annotation.Id;
import org.springframework.lang.NonNull;

@Data
@Builder
@Jacksonized
public class RoleDTO {
    @Id
    @NonNull
    private final String name;

    @NonNull
    private final String description;
}
