package io.ylab.intensive.lesson05.messagefilter;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Denis Zolotarev
 */
public interface DbClient {
    void uploadBadWordsToDb(File file) throws SQLException, IOException;

    boolean checkIfAvailable(String tableName) throws SQLException;

    Connection openConnection() throws SQLException;

    boolean isBadWord(Connection connection, String word) throws SQLException;
}
