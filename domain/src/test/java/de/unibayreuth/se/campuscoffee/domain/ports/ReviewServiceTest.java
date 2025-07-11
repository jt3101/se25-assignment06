/*
 * package de.unibayreuth.se.campuscoffee.domain.ports;
 * 
 * import static org.assertj.core.api.Assertions.assertThat;
 * import static org.junit.jupiter.api.Assertions.assertEquals;
 * import static org.junit.jupiter.api.Assertions.assertThrows;
 * import static org.mockito.ArgumentMatchers.anyLong;
 * import static org.mockito.Mockito.doThrow;
 * import static org.mockito.Mockito.verify;
 * import static org.mockito.Mockito.when;
 * 
 * import java.util.List;
 * 
 * import org.junit.jupiter.api.Test;
 * import org.junit.jupiter.api.extension.ExtendWith;
 * import org.mockito.InjectMocks;
 * import org.mockito.Mock;
 * import org.mockito.junit.jupiter.MockitoExtension;
 * 
 * import
 * de.unibayreuth.se.campuscoffee.domain.exceptions.ReviewNotFoundException;
 * import de.unibayreuth.se.campuscoffee.domain.impl.ReviewServiceImpl;
 * import de.unibayreuth.se.campuscoffee.domain.model.Review;
 * import de.unibayreuth.se.campuscoffee.domain.tests.TestFixtures;
 * import de.unibayreuth.se.campuscoffee.domain.util.AddressValidator;
 * 
 * @ExtendWith(MockitoExtension.class)
 * public class ReviewServiceTest {
 * 
 * @Mock
 * private ReviewDataService ReviewDataService;
 * 
 * @Mock
 * private AddressValidator addressValidator;
 * 
 * @InjectMocks
 * private ReviewServiceImpl ReviewService;
 * 
 * @Test
 * void getAllReviewRetrievesExpectedReview() {
 * // given
 * List<Review> testFixtures = TestFixtures.getReviewList();
 * when(ReviewDataService.getAll()).thenReturn(testFixtures);
 * 
 * // when
 * List<Review> retrievedReview = ReviewService.getAll();
 * 
 * // then
 * verify(ReviewDataService).getAll();
 * assertEquals(testFixtures.size(), retrievedReview.size());
 * assertThat(retrievedReview)
 * .usingRecursiveFieldByFieldElementComparator()
 * .containsExactlyInAnyOrderElementsOf(testFixtures);
 * }
 * 
 * @Test
 * void getReviewByIdNotFound() {
 * // given
 * when(ReviewDataService.getById(anyLong())).thenThrow(new
 * ReviewNotFoundException("Review not found."));
 * 
 * // when, then
 * assertThrows(ReviewNotFoundException.class, () ->
 * ReviewService.getById(anyLong()));
 * verify(ReviewDataService).getById(anyLong());
 * 
 * }
 * 
 * @Test
 * void getReviewByIdFound() {
 * // given
 * Review Review = TestFixtures.getReviewList().getFirst();
 * when(ReviewDataService.getById(Review.getId())).thenReturn(Review);
 * 
 * // when
 * Review retrievedReview = ReviewService.getById(Review.getId());
 * 
 * // then
 * verify(ReviewDataService).getById(Review.getId());
 * assertThat(retrievedReview)
 * .usingRecursiveComparison()
 * .isEqualTo(Review);
 * }
 * }
 */
