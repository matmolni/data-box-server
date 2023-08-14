package uk.ac.warwick.databoxserver.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import uk.ac.warwick.databoxserver.models.DatalogRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DatalogRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatalogRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Repository method that returns the list of all datalog records for a given dataset and data source. In
     * practice, this is all the data recorded by a given sensor for a given dataset.
     * @param datasetId the id of the dataset to retrieve data from
     * @param dataSource the name of the data source to retrieve data from
     * @return the list of all datalog records for a given dataset and data source
     */
    public List<DatalogRecord> findDatalogOfDataSourceOfDataset(int datasetId, String dataSource) {
        //TODO: Check dataSource String for SQL injection. Current implementation with string concatenation is very
        // unsafe. Need to verify that the string contained in dataSource is a valid column name of the database.

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(
                        "SELECT relative_timestamp, absolute_timestamp, " + dataSource +
                                " FROM logger_data.datapoint " +
                                "WHERE session_foreign_key = ? " +
                                "ORDER BY relative_timestamp"
                );

                ps.setInt(1, datasetId);

                return ps;
            }
        };

        RowMapper<DatalogRecord> rowMapper = new RowMapper<DatalogRecord>() {
            @Override
            public DatalogRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
                DatalogRecord datalogRecord = new DatalogRecord();

                datalogRecord.setRelativeTimestamp(rs.getLong("relative_timestamp"));
                datalogRecord.setAbsoluteTimestamp(rs.getTimestamp("absolute_timestamp"));
                datalogRecord.setData(rs.getObject(dataSource));

                return datalogRecord;
            }
        };

        return jdbcTemplate.query(psc, rowMapper);
    }
}
