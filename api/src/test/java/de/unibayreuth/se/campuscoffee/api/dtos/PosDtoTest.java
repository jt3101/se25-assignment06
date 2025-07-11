/*
 * package de.unibayreuth.se.campuscoffee.api.dtos;
 * 
 * import de.unibayreuth.se.campuscoffee.api.mapper.PosDtoMapper;
 * import de.unibayreuth.se.campuscoffee.domain.tests.TestFixtures;
 * import jakarta.validation.ConstraintViolation;
 * import jakarta.validation.Validation;
 * import jakarta.validation.Validator;
 * import jakarta.validation.ValidatorFactory;
 * import org.junit.jupiter.api.BeforeAll;
 * import org.junit.jupiter.api.Test;
 * import org.mapstruct.factory.Mappers;
 * 
 * import java.util.Set;
 * 
 * import static org.junit.jupiter.api.Assertions.assertFalse;
 * import static org.junit.jupiter.api.Assertions.assertTrue;
 * 
 * public class PosDtoTest {
 * private static Validator validator;
 * private static PosDtoMapper posDtoMapper;
 * 
 * @BeforeAll
 * static void beforeAll() {
 * try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
 * validator = factory.getValidator();
 * }
 * posDtoMapper = Mappers.getMapper(PosDtoMapper.class);
 * }
 * 
 * @Test
 * void validPosDto() {
 * PosDto validPosDto =
 * posDtoMapper.fromDomain(TestFixtures.getPosList().getFirst());
 * Set<ConstraintViolation<PosDto>> violations =
 * validator.validate(validPosDto);
 * assertTrue(violations.isEmpty());
 * }
 * 
 * @Test
 * void invalidCity_empty() {
 * PosDto validPosDto =
 * posDtoMapper.fromDomain(TestFixtures.getPosList().getFirst());
 * PosDto invalidPosDto = validPosDto.toBuilder().city("").build();
 * Set<ConstraintViolation<PosDto>> violations =
 * validator.validate(invalidPosDto);
 * assertFalse(violations.isEmpty());
 * }
 * }
 */