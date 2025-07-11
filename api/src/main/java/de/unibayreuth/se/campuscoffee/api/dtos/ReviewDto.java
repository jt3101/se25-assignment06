package de.unibayreuth.se.campuscoffee.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder(toBuilder = true)
public class ReviewDto {
    private Long id; // id is null when creating a new task
    private LocalDateTime createdAt; // is null when using DTO to create a new Review
    @NotNull
    private Long posId;
    @NotNull
    private Long authorId;
    @NotBlank(message = "It can't be empty")
    private String review;
    @Builder.Default
    private boolean approved = false; // missing when creating a new review
}
