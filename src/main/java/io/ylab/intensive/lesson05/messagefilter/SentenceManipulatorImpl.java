package io.ylab.intensive.lesson05.messagefilter;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Denis Zolotarev
 */
@Component
public class SentenceManipulatorImpl implements SentenceManipulator {

    private static final String REGEX = "[.,;?!\\s]";

    private final DbClient dbClient;

    public SentenceManipulatorImpl(DbClient dbClient) {
        this.dbClient = dbClient;
    }

    @Override
    public String sentenceProcess(String sentence) throws SQLException {
        StringBuilder newSentence = new StringBuilder();
        StringBuilder word = new StringBuilder();
        String newWord;
        try (Connection connection = dbClient.openConnection()) {
            for (int i = 0; i < sentence.length(); i++) {
                char ch = sentence.charAt(i);
                if (Character.toString(ch).matches(REGEX)) {
                    if (dbClient.isBadWord(connection, word.toString())) {
                        newWord = replaceCharsWithAsterisks(word.toString());
                    } else {
                        newWord = word.toString();
                    }
                    newSentence.append(newWord).append(ch);
                    word = new StringBuilder();
                } else {
                    word.append(ch);
                }
            }
            if (dbClient.isBadWord(connection, word.toString())) {
                newWord = replaceCharsWithAsterisks(word.toString());
            } else {
                newWord = word.toString();
            }
            newSentence.append(newWord);
        }
        return newSentence.toString();
    }

    @Override
    public String replaceCharsWithAsterisks(String word) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if (i == 0 || i == word.length() - 1) {
                result.append(word.charAt(i));
            } else {
                result.append('*');
            }
        }
        return result.toString();
    }
}
