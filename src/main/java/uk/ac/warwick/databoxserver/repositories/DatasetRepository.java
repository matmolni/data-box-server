package uk.ac.warwick.databoxserver.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uk.ac.warwick.databoxserver.models.Dataset;

@Repository
public class DatasetRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(Dataset dataset) {
        return jdbcTemplate.update(
                "INSERT INTO display_data.datasets(session_name, description, timestamp) VALUES(?, ?, ?)",
                dataset.getSessionName(), dataset.getDescription(), dataset.getTimestamp()
        );
    }
}
