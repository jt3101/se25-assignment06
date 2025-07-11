package de.unibayreuth.se.campuscoffee.api.controller;

import de.unibayreuth.se.campuscoffee.api.dtos.PosDto;
import de.unibayreuth.se.campuscoffee.api.dtos.ReviewDto;
import de.unibayreuth.se.campuscoffee.api.dtos.UserDto;
import de.unibayreuth.se.campuscoffee.api.mapper.PosDtoMapper;
import de.unibayreuth.se.campuscoffee.api.mapper.ReviewDtoMapper;
import de.unibayreuth.se.campuscoffee.api.mapper.UserDtoMapper;
import de.unibayreuth.se.campuscoffee.domain.exceptions.PosNotFoundException;
import de.unibayreuth.se.campuscoffee.domain.exceptions.ReviewNotFoundException;
import de.unibayreuth.se.campuscoffee.domain.exceptions.UserNotFoundException;
import de.unibayreuth.se.campuscoffee.domain.model.Pos;
import de.unibayreuth.se.campuscoffee.domain.model.Review;
import de.unibayreuth.se.campuscoffee.domain.model.User;
import de.unibayreuth.se.campuscoffee.domain.ports.PosService;
import de.unibayreuth.se.campuscoffee.domain.ports.ReviewService;
import de.unibayreuth.se.campuscoffee.domain.ports.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@OpenAPIDefinition(info = @Info(title = "CampusCoffee", version = "0.0.1"))
@Tag(name = "Reviews")
@Controller
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final UserDtoMapper userDtoMapper;
    private final ReviewService reviewService;
    private final ReviewDtoMapper reviewDtoMapper;
    private final PosService posService;

    @GetMapping("")
    public ResponseEntity<List<ReviewDto>> getAll() {
        return ResponseEntity.ok(
                reviewService.getAll().stream()
                        .map(reviewDtoMapper::fromDomain)
                        .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDto> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(
                    reviewDtoMapper.fromDomain(reviewService.getById(id)));
        } catch (ReviewNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ReviewDto>> getApprovedByPosId(@RequestParam("pos_id") Long posId) {
        Pos pos = posService.getById(posId);
        return ResponseEntity.ok(
                reviewService.getApprovedByPos(pos).stream()
                        .map(reviewDtoMapper::fromDomain)
                        .toList());
    }

    @PostMapping("")
    public ResponseEntity<ReviewDto> create(@RequestBody @Valid ReviewDto reviewDto) {
        Review review = reviewDtoMapper.toDomain(reviewDto);
        return ResponseEntity.ok(
                reviewDtoMapper.fromDomain(reviewService.create(review)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDto> approve(@PathVariable Long id, @RequestBody @Valid UserDto userDto) {
        Review review = reviewService.getById(id);
        User user = userDtoMapper.toDomain(userDto);
        return ResponseEntity.ok(
                reviewDtoMapper.fromDomain(reviewService.approve(review, user)));
    }
}