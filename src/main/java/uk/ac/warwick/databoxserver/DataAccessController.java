package uk.ac.warwick.databoxserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.warwick.databoxserver.models.Dataset;
import uk.ac.warwick.databoxserver.repositories.DatapointRepository;
import uk.ac.warwick.databoxserver.repositories.DatasetRepository;

import java.util.ArrayList;

/**
 * This controller is used to access the data files stored in the /resources/data directory.
 * It is used by the frontend to download the data files.
 *
 * The frontend is hosted on localhost:3000, so the response entity headers are set to allow CORS from that origin.
 */
@RestController
@RequestMapping("/data")
public class DataAccessController {

    private final DatasetRepository datasetRepository;
    private final DatapointRepository datapointRepository;

    @Autowired
    public DataAccessController(DatasetRepository datasetRepository, DatapointRepository datapointRepository) {
        this.datasetRepository = datasetRepository;
        this.datapointRepository = datapointRepository;
    }

    /**
     * Returns a list of all datasets stored in the database.
     * @return list of all datasets stored in the database
     */
    @GetMapping("/datasets")
    public ResponseEntity<ArrayList<Dataset>> getDatasetNames() {
        ArrayList<Dataset> datasets;

        //query the database for the list of datasets using the DatasetRepository
        datasets = (ArrayList<Dataset>) datasetRepository.findDatasets();

        return ResponseEntity.ok()
                .body(datasets);
    }
}