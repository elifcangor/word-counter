import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;



class WordCounterTest {

    Translator translator = Mockito.mock(Translator.class);

    WordValidator validValidator = Mockito.mock(WordValidator.class);



    @BeforeEach
     void setup(){
        Mockito.when(translator.translate("elma")).thenReturn("apple");
        Mockito.when(translator.translate("apple")).thenReturn("apple");
        Mockito.when(translator.translate("muz")).thenReturn("banana");
        Mockito.when(translator.translate("banana")).thenReturn("banana");
        Mockito.when(translator.translate("flower")).thenReturn("flower");
        Mockito.when(translator.translate("flor")).thenReturn("flower");
        Mockito.when(translator.translate("blume")).thenReturn("flower");

        Mockito.when(validValidator.isValid(Mockito.anyString())).thenReturn(true);
    }

    @Test
    void when_addWordCalledWithSameTranslation_Should_CountAsSameWord() {
        WordCounter wordCounter = new WordCounter(translator, validValidator);

        wordCounter.addWord("elma");
        wordCounter.addWord("apple");
        wordCounter.addWord("muz");
        wordCounter.addWord("blume");
        wordCounter.addWord("flor");

        Assertions.assertEquals(wordCounter.getCountOfAdditions("elma"), 2);
        Assertions.assertEquals(wordCounter.getCountOfAdditions("apple"), 2);
        Assertions.assertEquals(wordCounter.getCountOfAdditions("flower"), 2);
        Assertions.assertEquals(wordCounter.getCountOfAdditions("banana"), 1);

    }

    @Test
    void when_addWordCalledListOfWords_Should_CountIncrementForSameTranslation() {
        WordCounter wordCounter = new WordCounter(translator, validValidator);

        List<String> wordList = List.of("elma", "apple", "muz", "blume", "flor");
        wordCounter.addWord(wordList);

        Assertions.assertEquals(wordCounter.getCountOfAdditions("elma"), 2);
        Assertions.assertEquals(wordCounter.getCountOfAdditions("apple"), 2);
        Assertions.assertEquals(wordCounter.getCountOfAdditions("flower"), 2);
        Assertions.assertEquals(wordCounter.getCountOfAdditions("banana"), 1);

    }

    @Test
    void when_addWordCalledWithSingleAndListOfWords_Should_CountIncrementForSameTranslation() {
        WordCounter wordCounter = new WordCounter(translator, validValidator);

        List<String> wordList = List.of("elma", "apple", "muz", "blume", "flor");
        wordCounter.addWord(wordList);

        wordCounter.addWord("elma");
        wordCounter.addWord("apple");
        wordCounter.addWord("muz");
        wordCounter.addWord("blume");
        wordCounter.addWord("flor");

        Assertions.assertEquals(wordCounter.getCountOfAdditions("elma"), 4);
        Assertions.assertEquals(wordCounter.getCountOfAdditions("apple"), 4);
        Assertions.assertEquals(wordCounter.getCountOfAdditions("flower"), 4);
        Assertions.assertEquals(wordCounter.getCountOfAdditions("banana"), 2);

    }


    @Test
    void when_addWordCalledWithInvalidWord_Should_ThrowWordNotValidException() {
        WordValidator invalidValidator = Mockito.mock(WordValidator.class);
        WordCounter wordCounter = new WordCounter(translator, invalidValidator);

        Mockito.when(translator.translate("apple123")).thenReturn("apple123");

        Assertions.assertThrows(WordNotValidException.class, () -> wordCounter.addWord("apple123"));
        Assertions.assertEquals(wordCounter.getCountOfAdditions("apple123"), 0);
    }


    @Test
    void when_addWordCalledWithListIncludesInvalidWord_Should_ThrowWordNotValidExceptionAnsNotAddAnyInTheList() {
        WordValidator validator = Mockito.mock(WordValidator.class);
        Mockito.when(validator.isValid("1123Invalid")).thenReturn(false);
        Mockito.when(validator.isValid("apple")).thenReturn(true);
        Mockito.when(validator.isValid("flower")).thenReturn(true);

        WordCounter wordCounter = new WordCounter(translator, validator);
        Mockito.when(translator.translate("1123Invalid")).thenReturn("1123Invalid");

        List<String> words = List.of("apple", "1123Invalid", "flower");
        Assertions.assertThrows(WordNotValidException.class, () -> wordCounter.addWord(words));
        Assertions.assertEquals(wordCounter.getCountOfAdditions("apple"), 0);
        Assertions.assertEquals(wordCounter.getCountOfAdditions("1123Invalid"), 0);
        Assertions.assertEquals(wordCounter.getCountOfAdditions("flower"), 0);

    }

    @Test
    void when_getCountOfAdditionsCallWithNullTranslate_Should_ReturnZero() {
        WordCounter wordCounter = new WordCounter(translator, validValidator);
        Mockito.when(translator.translate("null")).thenReturn(null);

        Assertions.assertEquals(wordCounter.getCountOfAdditions("null"), 0);
    }

}