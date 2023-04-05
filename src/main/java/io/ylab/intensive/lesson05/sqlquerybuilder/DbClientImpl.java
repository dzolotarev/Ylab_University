package io.ylab.intensive.lesson05.sqlquerybuilder;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Denis Zolotarev
 */
@Component
public class DbClientImpl implements DbClient {
    private DataSource dataSource;

    public DbClientImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean isTableAvailable(String tableName) throws SQLException {
        try (Connection connection = dataSource.getConnection();) {
            DatabaseMetaData dbMetadata = connection.getMetaData();
            ResultSet resultSet = dbMetadata.getTables(null, null, tableName, null);
            return resultSet.next();
        }
    }

    @Override
    public List<String> getTableColumns(String tableName) throws SQLException {
        List<String> columnsName = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData dbMetadata = connection.getMetaData();
            ResultSet resultSet = dbMetadata.getColumns(null, null, tableName, null);
            while (resultSet.next()) {
                columnsName.add(resultSet.getString("COLUMN_NAME"));
            }
        }
        return columnsName;
    }

    @Override
    public List<String> getAllTables() throws SQLException {
        List<String> tablesName = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData dbMetadata = connection.getMetaData();
            ResultSet resultSet = dbMetadata.getTables(null, null, null, new String[]{"TABLE", "SYSTEM TABLE"});
            while (resultSet.next()) {
                tablesName.add(resultSet.getString("TABLE_NAME"));
            }
        }
        return tablesName;
    }
}
