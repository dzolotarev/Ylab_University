package io.ylab.intensive.lesson04.persistentmap;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, методы которого надо реализовать
 */
public class PersistentMapImpl implements PersistentMap {

    private static final String GET_QUERY = "SELECT value FROM persistent_map WHERE map_name=? and KEY=?";
    private static final String GET_KEYS_QUERY = "SELECT key FROM persistent_map WHERE map_name=?";
    private static final String INSERT_QUERY = "INSERT INTO persistent_map (map_name, key, value) VALUES (?, ?, ?)";
    private static final String DELETE_MAP_QUERY = "DELETE FROM persistent_map WHERE map_name=?";
    private static final String DELETE_ROW_QUERY = "DELETE FROM persistent_map WHERE map_name=? and KEY=?";

    private DataSource dataSource;
    private String name;

    public PersistentMapImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private void checkInitialized() {
        if (this.name == null) {
            throw new IllegalStateException("Map is not initialized");
        }
    }

    private void checkIFKeyIsNull(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Key can not be NULL");
        }
    }

    @Override
    public void init(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name can not be empty");
        } else if (this.name != null) {
            throw new IllegalStateException("This Map is already initialized");
        }
        this.name = name;
    }

    @Override
    public boolean containsKey(String key) throws SQLException {
        checkInitialized();
        checkIFKeyIsNull(key);
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(GET_QUERY)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, key);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
    }

    @Override
    public List<String> getKeys() throws SQLException {
        checkInitialized();
        List<String> keys = new ArrayList<>();
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(GET_KEYS_QUERY)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                keys.add(resultSet.getString("key"));
            }
        }
        return keys;
    }

    @Override
    public String get(String key) throws SQLException {
        checkInitialized();
        checkIFKeyIsNull(key);
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(GET_QUERY)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, key);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("value");
            }
        }
        return null;
    }

    @Override
    public void remove(String key) throws SQLException {
        checkInitialized();
        checkIFKeyIsNull(key);
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(DELETE_ROW_QUERY)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, key);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void put(String key, String value) throws SQLException {
        checkInitialized();
        checkIFKeyIsNull(key);
        if (this.containsKey(key)) {
            this.remove(key);
        }
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(INSERT_QUERY)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, key);
            if (value == null) {
                preparedStatement.setNull(3, Types.VARCHAR);
            } else {
                preparedStatement.setString(3, value);
            }
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void clear() throws SQLException {
        checkInitialized();
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(DELETE_MAP_QUERY)) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        }
    }
}
