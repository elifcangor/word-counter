public class WordValidator {

    public boolean isValid(String word){
        return word!=null && word.matches("[a-zA-Z]+");
    }
}
