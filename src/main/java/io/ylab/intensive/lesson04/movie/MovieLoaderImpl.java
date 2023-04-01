package io.ylab.intensive.lesson04.movie;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class MovieLoaderImpl implements MovieLoader {
    private static final String QUERY = "INSERT INTO movie (year, length, title, subject, actors, actress, director, popularity, awards)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private DataSource dataSource;

    public MovieLoaderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadData(File file) {
        // РЕАЛИЗАЦИЮ ПИШЕМ ТУТ
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
             PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(QUERY)) {
            bufferedReader.readLine(); //пропускаем первую строку
            bufferedReader.readLine(); //пропускаем вторую строку )
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                if (!line.isEmpty()) {
                    Movie movie = parseStringToObject(line);
                    writeMovieToDB(preparedStatement, movie);
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Movie parseStringToObject(String line) {
        String[] fields = line.trim().split(";");
        Movie movie = new Movie();
        movie.setYear(fields[0].isBlank() ? null : Integer.parseInt(fields[0]));
        movie.setLength(fields[1].isBlank() ? null : Integer.parseInt(fields[1]));
        movie.setTitle(fields[2]);
        movie.setSubject(fields[3]);
        movie.setActors(fields[4]);
        movie.setActress(fields[5]);
        movie.setDirector(fields[6]);
        movie.setPopularity(fields[7].isBlank() ? null : Integer.parseInt(fields[7]));
        movie.setAwards(fields[8].equalsIgnoreCase("yes"));
        return movie;
    }

    private void writeMovieToDB(PreparedStatement statement, Movie movie) throws SQLException {
        setIntParam(statement, 1, movie.getYear());
        setIntParam(statement, 2, movie.getLength());
        statement.setString(3, movie.getTitle());
        statement.setString(4, movie.getSubject());
        statement.setString(5, movie.getActors());
        statement.setString(6, movie.getActress());
        statement.setString(7, movie.getDirector());
        setIntParam(statement, 8, movie.getPopularity());
        statement.setBoolean(9, movie.getAwards());
        statement.executeUpdate();
    }

    private void setIntParam(PreparedStatement statement, int paramIndex, Integer value) throws SQLException {
        if (value == null) {
            statement.setNull(paramIndex, Types.INTEGER);
        } else {
            statement.setInt(paramIndex, value);
        }
    }
}
