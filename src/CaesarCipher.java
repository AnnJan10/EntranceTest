import java.util.HashMap;
import java.util.Map;

public class CaesarCipher {

    private static final String ENGLISH_ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final String RUSSIAN_ALPHABET= "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";

    public static String encryptTextWithShift(String text, int shift) {
        String alphabet = defineAlphabet(text);
        return encryptText(text, shift, alphabet);
    }

    public static String decryptTextWithShift(String text, int shift) {
        String alphabet = defineAlphabet(text);
        return decryptText(text, shift, alphabet);
    }

    /*
    Decryption without specifying a shift
    Provides all possible variants depending on the alphabet
    */
    public static Map<Integer, String> brutoForce(String text) {
        String alphabet = defineAlphabet(text);
        Map<Integer, String> allVariants = new HashMap<>();
        for (int shift = 1; shift < alphabet.length(); shift++) {
            allVariants.put(shift, decryptTextWithShift(text, shift));
        }
        return allVariants;
    }

    private static String defineAlphabet(String text) {
        String alphabet = ENGLISH_ALPHABET;
        for (char character: text.toCharArray()) {
            if (RUSSIAN_ALPHABET.contains(String.valueOf(Character.toLowerCase(character)))) {
                alphabet = RUSSIAN_ALPHABET;
                break;
            }
        }
        return alphabet;
    }

    private static String encryptText(String text, int shift, String alphabet) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char symbol = text.charAt(i);
            if (Character.isLetter(symbol)) {
                if (Character.isUpperCase(symbol))
                    symbol = encryptSymbol(alphabet.toUpperCase(), symbol, shift);
                else
                    symbol = encryptSymbol(alphabet, symbol, shift);
            }
            result.append(symbol);
        }
        return result.toString();
    }

    private static String decryptText(String text, int shift, String alphabet) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char symbol = text.charAt(i);
            if (Character.isLetter(symbol)) {
                if (Character.isUpperCase(symbol))
                    symbol = decryptSymbol(alphabet.toUpperCase(), symbol, shift);
                else
                    symbol = decryptSymbol(alphabet, symbol, shift);
            }
            result.append(symbol);
        }
        return result.toString();
    }

    private static char encryptSymbol(String alphabet, int symbol, int shift) {
        int index = (alphabet.indexOf(symbol) + shift) % alphabet.length();
        if (index < 0)
            index = alphabet.length() + index;
        return alphabet.charAt(index);
    }

    private static char decryptSymbol(String alphabet, int symbol, int shift) {
        int index = (alphabet.indexOf(symbol) - shift) % alphabet.length();
        if (index < 0)
            index = alphabet.length() + index;
        return alphabet.charAt(index);
    }
}