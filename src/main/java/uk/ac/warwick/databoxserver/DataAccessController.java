package uk.ac.warwick.databoxserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.warwick.databoxserver.models.DatalogRecord;
import uk.ac.warwick.databoxserver.models.Dataset;
import uk.ac.warwick.databoxserver.repositories.DatalogRepository;
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
    private final DatalogRepository datalogRepository;

    @Autowired
    public DataAccessController(DatasetRepository datasetRepository, DatapointRepository datapointRepository, DatalogRepository datalogRepository) {
        this.datasetRepository = datasetRepository;
        this.datapointRepository = datapointRepository;
        this.datalogRepository = datalogRepository;
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

    /**
     * Endpoint that returns a list corresponding to one datasource of each datapoint in the specified dataset.
     * @param datasetId id of the dataset to get datapoints from
     * @param dataSource datasource to get datapoints from
     * @return datalog list of records
     */
    @GetMapping("/datalog")
    public ResponseEntity<?> getDatalog(@RequestParam @NonNull int datasetId,
                                                               @RequestParam @NonNull String dataSource) {

        ArrayList<DatalogRecord> datalogRecord;

        //query the database for the list of datasets using the DatasetRepository
        try {
            datalogRecord = (ArrayList<DatalogRecord>)
                    datalogRepository.findDatalogOfDataSourceOfDataset(datasetId, dataSource);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid dataSource: '" + dataSource + "' " +
                    "Please check that the requested dataSource is a valid column of the database");
        }

        return ResponseEntity.ok()
                .body(datalogRecord);
    }
}