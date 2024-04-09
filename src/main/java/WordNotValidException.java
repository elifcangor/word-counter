import java.util.Collection;
import java.util.Optional;

public class WordNotValidException extends RuntimeException {

    private static final String MESSAGE = "Word/Words will not be able to add. Invalid Word/Words: ";

    public WordNotValidException(Collection<String> invalidWords) {
        super(generateMessage(invalidWords));
    }

    private static String generateMessage(Collection<String> invalidWords) {
        return String.format("%s%s", MESSAGE, Optional.ofNullable(invalidWords)
                .map(strings -> String.join(",", invalidWords))
                .orElse(""));
    }
}
