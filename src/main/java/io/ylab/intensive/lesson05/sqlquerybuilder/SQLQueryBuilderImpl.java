package io.ylab.intensive.lesson05.sqlquerybuilder;

import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Denis Zolotarev
 */
@Component
public class SQLQueryBuilderImpl implements SQLQueryBuilder {
    DbClient dbClient;

    public SQLQueryBuilderImpl(DbClient dbClient) {
        this.dbClient = dbClient;
    }

    @Override
    public String queryForTable(String tableName) throws SQLException {
        if (dbClient.isTableAvailable(tableName)) {
            List<String> columnsName = dbClient.getTableColumns(tableName);
            return buildQuery(tableName, columnsName);
        }
        return null;
    }

    @Override
    public List<String> getTables() throws SQLException {
        return dbClient.getAllTables();
    }

    private String buildQuery(String tableName, List<String> columnsName) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT ");
        for (int i = 0; i < columnsName.size(); i++) {
            if (i == columnsName.size() - 1) {
                query.append(columnsName.get(i));
            } else {
                query.append(columnsName.get(i)).append(", ");
            }
        }
        query.append(" FROM ").append(tableName);
        return query.toString();
    }
}
