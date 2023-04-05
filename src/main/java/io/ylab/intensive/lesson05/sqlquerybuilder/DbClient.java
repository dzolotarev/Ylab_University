package io.ylab.intensive.lesson05.sqlquerybuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Denis Zolotarev
 */
public interface DbClient {

    boolean isTableAvailable(String tableName) throws SQLException;

    List<String> getTableColumns(String tableName) throws SQLException;

    List<String> getAllTables() throws SQLException;
}
