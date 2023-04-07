package io.ylab.intensive.lesson05.messagefilter;

import java.sql.SQLException;

/**
 * @author Denis Zolotarev
 */
public interface SentenceManipulator {

    String sentenceProcess(String sentence) throws SQLException;

    String replaceCharsWithAsterisks(String word);

}
