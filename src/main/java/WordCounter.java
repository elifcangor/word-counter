import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;


public class WordCounter {

    private final ConcurrentMap<String, Integer> wordCountMap = new ConcurrentHashMap<>();

    private final Translator translator;
    private final WordValidator wordValidator;

    public WordCounter(Translator translator, WordValidator wordValidator) {
        this.translator = translator;
        this.wordValidator = wordValidator;
    }

    // 1.  Method that allows you to add one word
    // Method overloading
    public void addWord(String word) {
        if (wordValidator.isValid(word)) {
            String englishTranslation = translator.translate(word);
            wordCountMap.put(englishTranslation, wordCountMap.getOrDefault(englishTranslation, 0) +1);
        } else {
            throw new WordNotValidException(Collections.singleton(word));
        }
    }

    // 1.  Method that allows you to add words
    // Method overloading
    public void addWord(Collection<String> words) {
        Set<String> invalidWords = words.stream()
                .filter(word -> !wordValidator.isValid(word))
                .collect(Collectors.toSet());

        // Think as a batch process
        if (!invalidWords.isEmpty()) {
            throw new WordNotValidException(invalidWords);
        }

        words.stream()
                .map(translator::translate)
                .forEach(englishTranslation -> wordCountMap.put(englishTranslation,
                        wordCountMap.getOrDefault(englishTranslation, 0) +1));
    }

    // 2.	Method that returns the count of how many times a given word was added to the word counter
    public int getCountOfAdditions(String word) {
        String translate = translator.translate(word);
        if (translate==null) {
            return 0;
        }
        return wordCountMap.getOrDefault(translate, 0);
    }
}
