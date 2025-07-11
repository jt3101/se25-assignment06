
package de.unibayreuth.se.campuscoffee.domain.ports;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.unibayreuth.se.campuscoffee.domain.exceptions.PosNotFoundException;
import de.unibayreuth.se.campuscoffee.domain.exceptions.ReviewNotFoundException;
import de.unibayreuth.se.campuscoffee.domain.impl.ReviewServiceImpl;
import de.unibayreuth.se.campuscoffee.domain.model.Pos;
import de.unibayreuth.se.campuscoffee.domain.model.User;
import de.unibayreuth.se.campuscoffee.domain.model.Review;
import de.unibayreuth.se.campuscoffee.domain.tests.TestFixtures;
import de.unibayreuth.se.campuscoffee.domain.util.AddressValidator;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewDataService ReviewDataService;

    @InjectMocks
    private ReviewServiceImpl ReviewService;

    @Test
    void getAllReviewRetrievesExpectedReview() {
        // given
        List<Review> testFixtures = TestFixtures.getReviewList();
        when(ReviewDataService.getAll()).thenReturn(testFixtures);

        // when
        List<Review> retrievedReview = ReviewService.getAll();

        // then
        verify(ReviewDataService).getAll();
        assertEquals(testFixtures.size(), retrievedReview.size());
        assertThat(retrievedReview)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(testFixtures);
    }

    @Test
    void getReviewByIdNotFound() {
        // given
        when(ReviewDataService.getById(anyLong())).thenThrow(new ReviewNotFoundException("Review not found."));

        // when, then
        assertThrows(ReviewNotFoundException.class, () -> ReviewService.getById(anyLong()));
        verify(ReviewDataService).getById(anyLong());

    }

    @Test
    void getReviewByIdFound() {
        // given
        Review Review = TestFixtures.getReviewList().getFirst();
        when(ReviewDataService.getById(Review.getId())).thenReturn(Review);

        // when
        Review retrievedReview = ReviewService.getById(Review.getId());

        // then
        verify(ReviewDataService).getById(Review.getId());
        assertThat(retrievedReview)
                .usingRecursiveComparison()
                .isEqualTo(Review);
    }

    @Test
    void createReviewSuccess() {
        // given
        Review review = TestFixtures.getReviewList().getFirst();

        // when
        ReviewService.create(review);

        // then
        verify(ReviewDataService).create(review);
    }

    @Test
    void createNewReview() {
        // given
        Review review = TestFixtures.getReviewList().getFirst();
        review.setId(null);

        // when, then
        ReviewService.create(review);
    }

    @Test
    void getAllReviewApprovedByPosRetrievesExpectedReview() {
        // given
        Review approvedReview = TestFixtures.getReviewList().getFirst(); // approvalCount = 3
        Pos pos = approvedReview.getPos();
        when(ReviewDataService.getByPos(pos)).thenReturn(List.of(approvedReview));

        // when
        List<Review> retrievedApprovedReview = ReviewService.getApprovedByPos(pos);

        // then
        assertEquals(1, retrievedApprovedReview.size());
        assertThat(retrievedApprovedReview)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactly(approvedReview);
    }

    @Test
    void approveReviewNotFound() {
        // given
        Review review = TestFixtures.getReviewList().getFirst();
        User user = TestFixtures.getUserList().getFirst();
        when(ReviewDataService.approve(any())).thenThrow(new ReviewNotFoundException("Review not found"));

        // when, then
        assertThrows(ReviewNotFoundException.class, () -> ReviewService.approve(review, user));
    }

    void approveReviewSuccess() {
        // given
        Review review = TestFixtures.getReviewList().getFirst();
        User user = TestFixtures.getUserList().getFirst();

        // when
        ReviewService.approve(review, user);

        // then
        assertEquals(review.getApprovalCount(), ReviewDataService.getById(review.getId()).getApprovalCount());
        verify(ReviewDataService).getById(review.getId());
    }

    @Test
    void getApprovedByPosWithNoReviewsReturnsEmptyList() {
        when(ReviewDataService.getByPos(any())).thenReturn(List.of());

        List<Review> result = ReviewService.getApprovedByPos(TestFixtures.getPosList().getFirst());

        assertTrue(result.isEmpty());
    }

    @Test
    void approveReviewByAuthorDoesNothing() {
        Review review = TestFixtures.getReviewList().getFirst();
        User author = review.getAuthor();

        Review result = ReviewService.approve(review, author);

        assertEquals(review.getApprovalCount(), result.getApprovalCount());
        verifyNoInteractions(ReviewDataService); // no DB call expected
    }

}
