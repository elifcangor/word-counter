import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordValidatorTest {

    @Test
    void when_isValidCalledWithAlphabetic_Should_ReturnTrueElseReturnFalse() {

        WordValidator wordValidator = new WordValidator();

        Assertions.assertTrue(wordValidator.isValid("apple"));
        Assertions.assertTrue(wordValidator.isValid("Xqlm"));

        Assertions.assertFalse(wordValidator.isValid(""));
        Assertions.assertFalse(wordValidator.isValid(null));
        Assertions.assertFalse(wordValidator.isValid("!apple"));
        Assertions.assertFalse(wordValidator.isValid("1234"));

    }
}