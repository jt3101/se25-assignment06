package de.unibayreuth.se.campuscoffee.domain.impl;

import de.unibayreuth.se.campuscoffee.domain.exceptions.PosNotFoundException;
import de.unibayreuth.se.campuscoffee.domain.exceptions.ReviewNotFoundException;
import de.unibayreuth.se.campuscoffee.domain.exceptions.UserNotFoundException;
import de.unibayreuth.se.campuscoffee.domain.model.Pos;
import de.unibayreuth.se.campuscoffee.domain.model.Review;
import de.unibayreuth.se.campuscoffee.domain.model.User;
import de.unibayreuth.se.campuscoffee.domain.ports.ReviewService;
import de.unibayreuth.se.campuscoffee.domain.ports.ReviewDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewDataService reviewDataService;

    @Override
    public void clear() {
        reviewDataService.clear();
    }

    @Override
    @NonNull
    public List<Review> getAll() {
        return reviewDataService.getAll();
    }

    @Override
    @NonNull
    public Review getById(@NonNull Long id) throws ReviewNotFoundException {
        return reviewDataService.getById(id);
    }

    @Override
    @NonNull
    public List<Review> getApprovedByPos(@NonNull Pos pos) throws PosNotFoundException {
        List<Review> reviews = reviewDataService.getByPos(pos);
        List<Review> approvedList = reviews.stream()
                .filter(review -> review.getApprovalCount() >= 2)
                .collect(Collectors.toList());
        return approvedList;
    }

    @Override
    @NonNull
    public Review create(@NonNull Review review) throws PosNotFoundException, UserNotFoundException {
        return reviewDataService.create(review);
    }

    @Override
    @NonNull
    public Review approve(@NonNull Review review, @NonNull User user)
            throws ReviewNotFoundException, UserNotFoundException, IllegalArgumentException {
        if (user.getId() != review.getAuthor().getId()) {
            return reviewDataService.approve(review);
        }
        return review;
    }
}