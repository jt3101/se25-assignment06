package de.unibayreuth.se.campuscoffee.domain.model;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Domain class that stores the review metadata.
 */
@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class Review implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L; // required to clone objects (see TestFixtures class).
    private Long id; // id is null when creating a new task
    private LocalDateTime createdAt; // is null when using DTO to create a new Review
    @NonNull
    private Pos pos;
    @NonNull
    private User author;
    @NonNull
    private String review;
    @Builder.Default
    private boolean approved = false;
    @Builder.Default
    private int approvalCount = 0;
}
