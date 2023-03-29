package io.ylab.intensive.lesson04.filesort;

import javax.sql.DataSource;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FileSortImplNoBatch implements FileSorter {

    private static final String ADD_QUERY = "INSERT INTO numbers (val) VALUES (?)";
    private static final String SORT_QUERY = "SELECT val FROM numbers ORDER BY val DESC";
    private DataSource dataSource;

    public FileSortImplNoBatch(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public File sort(File data) throws SQLException, IOException {
        // ТУТ ПИШЕМ РЕАЛИЗАЦИЮ
        readAndWriteToDB(data);
        File sortedFile = createSortedFile(data);
        sortAndWriteToFile(sortedFile);
        return sortedFile;
    }

    private File createSortedFile(File file) {
        String fileName = file.getName().substring(0, file.getName().length() - 4);
        return new File(file.getParent() + File.separator + fileName + "_sorted_" + System.currentTimeMillis() + ".txt");
    }

    private void readAndWriteToDB(File file) throws IOException, SQLException {
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(ADD_QUERY);
             BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                preparedStatement.setLong(1, Long.parseLong(line));
                preparedStatement.executeUpdate();
            }
        }
    }

    private void sortAndWriteToFile(File outFile) throws IOException, SQLException {
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(SORT_QUERY);
             BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long number = resultSet.getLong("val");
                writer.write(number + "\n");
            }
        }

    }
}
