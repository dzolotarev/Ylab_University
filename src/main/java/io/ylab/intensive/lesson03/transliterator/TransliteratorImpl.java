package io.ylab.intensive.lesson03.transliterator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Denis Zolotarev
 */
public class TransliteratorImpl implements Transliterator {
    private static final Map<Character, String> cyrillicLatinCharacterMap = new HashMap<>() {{
        put('А', "A");
        put('Б', "B");
        put('В', "V");
        put('Г', "G");
        put('Д', "D");
        put('Е', "E");
        put('Ё', "E");
        put('Ж', "ZH");
        put('З', "Z");
        put('И', "I");
        put('Й', "I");
        put('К', "K");
        put('Л', "L");
        put('М', "M");
        put('Н', "N");
        put('О', "O");
        put('П', "P");
        put('Р', "R");
        put('С', "S");
        put('Т', "T");
        put('У', "U");
        put('Ф', "F");
        put('Х', "KH");
        put('Ц', "TS");
        put('Ч', "CH");
        put('Ш', "SH");
        put('Щ', "SHCH");
        put('Ы', "Y");
        put('Ь', "");
        put('Ъ', "IE");
        put('Э', "E");
        put('Ю', "IU");
        put('Я', "IA");
    }};

    @Override
    public String transliterate(String source) {
        if (source == null) {
            return "Ошибка: передана пустая строка!";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < source.length(); i++) {
            Character character = source.charAt(i);
            String transliteratedCharacter = cyrillicLatinCharacterMap.get(character);
            if (transliteratedCharacter != null) {
                sb.append(transliteratedCharacter);
            } else {
                sb.append(character);
            }
        }
        return sb.toString();
    }
}
