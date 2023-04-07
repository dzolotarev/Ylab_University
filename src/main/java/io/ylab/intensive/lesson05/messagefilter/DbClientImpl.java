package io.ylab.intensive.lesson05.messagefilter;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

/**
 * @author Denis Zolotarev
 */
@Component
public class DbClientImpl implements DbClient {
    private static final String TABLE_NAME = "badwords";
    private static final String COLUMN_NAME = "word";
    private static final String INSERT_QUERY = "INSERT INTO %s (%s) VALUES (?)";
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE %s (%s varchar);";
    private static final String TRUNCATE_TABLE_QUERY = "TRUNCATE %s;";
    private static final String IS_BAD_WORD_QUERY = "SELECT %s FROM %s WHERE %s=?";

    private DataSource dataSource;


    public DbClientImpl(DataSource dataSource) throws SQLException {
        this.dataSource = dataSource;
    }


    @Override
    public void uploadBadWordsToDb(File file) throws SQLException, IOException {
        if (checkIfAvailable(TABLE_NAME)) {
            dropTable(TABLE_NAME);
        } else {
            createTable(TABLE_NAME, COLUMN_NAME);
        }
        String query = String.format(INSERT_QUERY, TABLE_NAME, COLUMN_NAME);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file)); PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(query)) {
            int count = 0;
            String word;
            while (bufferedReader.ready()) {
                if ((word = bufferedReader.readLine()) != null) {
                    preparedStatement.setString(1, word.toLowerCase());
                    preparedStatement.addBatch();
                    count++;
                    if (count >= 100) {
                        preparedStatement.executeBatch();
                        count = 0;
                    }
                }
            }
            if (count > 0) {
                preparedStatement.executeBatch();
            }
        }

    }

    private void createTable(String tableName, String columnName) throws SQLException {
        String query = String.format(CREATE_TABLE_QUERY, tableName, columnName);
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(query)) {
            preparedStatement.execute();
        }
    }

    private void dropTable(String tableName) throws SQLException {
        String query = String.format(TRUNCATE_TABLE_QUERY, tableName);
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(query)) {
            preparedStatement.execute();
        }

    }

    @Override
    public boolean checkIfAvailable(String tableName) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData dbMetadata = connection.getMetaData();
            ResultSet resultSet = dbMetadata.getTables(null, null, tableName, null);
            if (resultSet.next()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Connection openConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public boolean isBadWord(Connection connection, String word) throws SQLException {
        if (word.length() < 3) {
            return false;
        }
        String query = String.format(IS_BAD_WORD_QUERY, COLUMN_NAME, TABLE_NAME, COLUMN_NAME);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, word.toLowerCase());
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }
}