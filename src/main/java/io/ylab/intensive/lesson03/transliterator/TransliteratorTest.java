package io.ylab.intensive.lesson03.transliterator;

/**
 * @author Denis Zolotarev
 */
public class TransliteratorTest {
    public static void main(String[] args) {
        Transliterator transliterator = new TransliteratorImpl();
        String res = transliterator.transliterate("HELLO! ПРИВЕТ НОЧЬ ночь! Go, boy!"); //HELLO! PRIVET NOCH ночь! Go, boy!
        System.out.println(res);
    }
}
