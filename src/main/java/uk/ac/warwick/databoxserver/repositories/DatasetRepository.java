package uk.ac.warwick.databoxserver.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import uk.ac.warwick.databoxserver.models.Dataset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DatasetRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatasetRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Saves a dataset to the database.
     *
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
                                            new String[]{"identifier"}
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

    /**
     * Returns a list of all datasets stored in the database.
     * return list of all datasets stored in the database
     */
    public List<Dataset> findDatasets() {

        //prepared statement to query every dataset
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(
                        "SELECT * FROM logger_data.dataset"
                );
                return ps;
            }
        };

        //row mapper to map the result set to a list of datasets
        RowMapper<Dataset> rm = new RowMapper<Dataset>() {
            @Override
            public Dataset mapRow(ResultSet rs, int rowNum) throws SQLException {
                Dataset dataset = new Dataset();

                dataset.setIdentifier(rs.getInt("identifier"));
                dataset.setSessionName(rs.getString("session_name"));
                dataset.setDescription(rs.getString("description"));
                dataset.setTimestamp(rs.getTimestamp("timestamp"));

                return dataset;
            }
        };

        //execute the query and return the resulting list of datasets
        return jdbcTemplate.query(psc, rm);
    }
}
