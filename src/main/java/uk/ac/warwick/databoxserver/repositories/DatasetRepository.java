package uk.ac.warwick.databoxserver.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import uk.ac.warwick.databoxserver.models.Dataset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class DatasetRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatasetRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Saves a dataset to the database.
     * @param dataset Dataset object to save
     * @return the generated id of the dataset as an int
     */
    public int save(Dataset dataset) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            @NonNull
            public PreparedStatement createPreparedStatement(@NonNull Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO logger_data.dataset(session_name, description, timestamp) VALUES(?, ?, ?)",
                        new String[]{"session_id"}
                );
                ps.setString(1, dataset.getSessionName());
                ps.setString(2, dataset.getDescription());
                ps.setTimestamp(3, dataset.getTimestamp());
                return ps;
            }
        },
                keyHolder
        );

        return keyHolder.getKey().intValue();
    }
}
