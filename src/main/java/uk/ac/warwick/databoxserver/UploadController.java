package uk.ac.warwick.databoxserver;

import org.postgresql.geometric.PGpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.warwick.databoxserver.models.Datapoint;
import uk.ac.warwick.databoxserver.models.Dataset;
import uk.ac.warwick.databoxserver.repositories.DatapointRepository;
import uk.ac.warwick.databoxserver.repositories.DatasetRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLDataException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.logging.Logger;

@RestController
public class UploadController {

    //add logging
    private static final Logger LOGGER = Logger.getLogger(UploadController.class.getName());


    private JdbcTemplate jdbcTemplate;
    private DatasetRepository datasetRepo;
    private DatapointRepository datapointRepo;

    @Autowired
    public UploadController(JdbcTemplate jdbcTemplate, DatasetRepository datasetRepo, DatapointRepository datapointRepo) {
        this.jdbcTemplate = jdbcTemplate;
        this.datasetRepo = datasetRepo;
        this.datapointRepo = datapointRepo;
    }

    @PostMapping("/upload-csv")
    public ResponseEntity<String> uploadCSVFile(@RequestParam("dataset-csv") MultipartFile file) {
        // Parse CSV file
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String sessionName = reader.readLine();
            String dateString = reader.readLine();
            reader.readLine(); // skip headers

            //parse dateString
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date parsedDate = dateFormat.parse(dateString);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

            //compile dataset object using the CSV metadata (first few rows)
            Dataset dataset = new Dataset();
            dataset.setSessionName(sessionName);
            dataset.setTimestamp(timestamp);
            int datasetInsert = datasetRepo.save(dataset); //returns number of rows inserted

            //find the id of the most recently inserted dataset to be used as the foreign key of each datapoint
            Integer datasetId;
            if (datasetInsert != 0) {
                String query = "SELECT identifier FROM display_data.datasets ORDER BY identifier DESC LIMIT 1";
                datasetId = jdbcTemplate.queryForObject( query, Integer.class);
            }
            else {
                throw new SQLDataException();
            }

            String line;
            while ((line = reader.readLine()) != null) {

                //log contents of line
                LOGGER.info(line);

                String[] data = line.split(","); // split CSV row into individual elements

                //log contents of data
                LOGGER.info(Arrays.toString(data));

                Datapoint datapoint = new Datapoint();

                //load each value into the Datapoint object

                //id of the dataset this point belongs to
                datapoint.setSessionForeignKey(datasetId);

                //absolute and relative time calculated using the dataset metadata recording start time
                datapoint.setAbsoluteTimestamp(new Timestamp(Long.parseLong(data[0]) + timestamp.getTime()));
                datapoint.setRelativeTimestamp(Long.parseLong(data[0]));

                //recorded data points parsed from the CSV
                datapoint.setLap(Integer.parseInt(data[1]));
                datapoint.setGpsPosition(new PGpoint(Double.parseDouble(data[2]), Double.parseDouble(data[3])));
                datapoint.setGpsSpeed(Float.parseFloat(data[4]));
                datapoint.setSAS(Float.parseFloat(data[5]));
                datapoint.setAPPS(Float.parseFloat(data[6]));
                datapoint.setBrakePressure(Float.parseFloat(data[7]));
                datapoint.setWheelSpeedFL(Float.parseFloat(data[8]));
                datapoint.setWheelSpeedFR(Float.parseFloat(data[9]));
                datapoint.setWheelSpeedRL(Float.parseFloat(data[10]));
                datapoint.setWheelSpeedRR(Float.parseFloat(data[11]));
                datapoint.setTyrePressureFL(Float.parseFloat(data[12]));
                datapoint.setTyrePressureFR(Float.parseFloat(data[13]));
                datapoint.setTyrePressureRL(Float.parseFloat(data[14]));
                datapoint.setTyrePressureRR(Float.parseFloat(data[15]));
                datapoint.setTyreTempFL(Float.parseFloat(data[16]));
                datapoint.setTyreTempFR(Float.parseFloat(data[17]));
                datapoint.setTyreTempRL(Float.parseFloat(data[18]));
                datapoint.setTyreTempRR(Float.parseFloat(data[19]));
                datapoint.setTyreTempSurfaceFL(Float.parseFloat(data[20]));
                datapoint.setTyreTempSurfaceFR(Float.parseFloat(data[21]));
                datapoint.setTyreTempSurfaceRL(Float.parseFloat(data[22]));
                datapoint.setTyreTempSurfaceRR(Float.parseFloat(data[23]));
                datapoint.setSuspensionTravelFL(Float.parseFloat(data[24]));
                datapoint.setSuspensionTravelFR(Float.parseFloat(data[25]));
                datapoint.setSuspensionTravelRL(Float.parseFloat(data[26]));
                datapoint.setSuspensionTravelRR(Float.parseFloat(data[27]));
                datapoint.setSscErr(Boolean.valueOf(data[28]));
                datapoint.setImdErr(Boolean.valueOf(data[29]));
                datapoint.setInvErr(Boolean.valueOf(data[30]));
                datapoint.setBatErr(Boolean.valueOf(data[31]));

                datapointRepo.save(datapoint);

                //log successful save
                LOGGER.info("Saved datapoint to database");
            }
            return ResponseEntity.ok("CSV file processed successfully");
        } catch (Exception e) {
            //log stacktrace for specific class
            Logger.getLogger(UploadController.class.getName()).log(java.util.logging.Level.SEVERE, e.getMessage(), e);


            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process CSV file");
        }
    }

}
