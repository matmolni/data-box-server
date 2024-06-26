package uk.ac.warwick.databoxserver;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

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
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * This controller is used to parse incoming CSV files and save their data to the database.
 * It is used by the frontend to upload CSV files.
 *
 * The frontend is hosted on localhost:3000, so the response entity headers are set to allow CORS from that origin.
 */
@RestController
public class UploadController {

    //add logging
    private static final Logger LOGGER = Logger.getLogger(UploadController.class.getName());
    private final DatasetRepository datasetRepo;
    private final DatapointRepository datapointRepo;

    @Autowired
    public UploadController(DatasetRepository datasetRepo, DatapointRepository datapointRepo) {
        this.datasetRepo = datasetRepo;
        this.datapointRepo = datapointRepo;
    }

    /**
     * Receives a CSV file from the frontend and parses it into the database.
     *
     * @param file the CSV file to be parsed and saved
     * @return a response entity with the HTTP status code signifying the outcome of the request
     */
    @PostMapping("/upload-csv")
    public ResponseEntity<String> uploadCSVFile(@RequestParam("dataset-csv") MultipartFile file) {

        LOGGER.info("Received request to upload CSV file");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            //read the first 2 lines of the CSV file metadata
            String sessionName = reader.readLine();
            String dateString = reader.readLine();

            //parse dateString
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date parsedDate = dateFormat.parse(dateString);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());

            //compile dataset object using the CSV metadata (first 2 rows)
            Dataset dataset = new Dataset();
            dataset.setSessionName(sessionName);
            dataset.setTimestamp(timestamp);

            //saves the dataset and returns generated key of the dataset to be used as a foreign key in the datapoint
            // table
            int datasetId = datasetRepo.save(dataset);

            long duration = 0;

            // create a CSVFormat object with the required parameters
            CSVFormat format = CSVFormat.Builder
                    .create()
                    .setDelimiter(',')
                    .setHeader()
                    .build();

            //parse the rest of the CSV file using the CSVParser with the input as the reader and the defined format
            for (CSVRecord record : CSVParser.parse(reader, format)) {

                //log contents of the record
                LOGGER.info(record.toString());

                //create a new Datapoint object for each line
                Datapoint datapoint = new Datapoint();

                //load each value into the Datapoint object

                //id of the dataset this point belongs to
                datapoint.setSessionForeignKey(datasetId);

                //absolute and relative time calculated using the dataset metadata recording start time
                try {
                    datapoint.setAbsoluteTimestamp(new Timestamp(Long.parseLong(record.get("relativeTimestamp")) + timestamp.getTime()));
                    datapoint.setRelativeTimestamp(Long.parseLong(record.get("relativeTimestamp")));
                }
                catch (IllegalArgumentException e) {
                    LOGGER.warning("Timestamp not found in CSV file");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CSV file does not contain a timestamp");
                }

                //duration of the session
                duration = Long.parseLong(record.get("relativeTimestamp"));

                //recorded data points parsed from the CSV
                if (record.isMapped("lap"))
                    datapoint.setLap(Integer.parseInt(record.get("lap")));
                if (record.isMapped("gpsPositionX") && record.isMapped("gpsPositionY"))
                    datapoint.setGpsPosition(new PGpoint(Double.parseDouble(record.get("gpsPositionX")), Double.parseDouble(record.get("gpsPositionY"))));
                if (record.isMapped("gpsSpeed"))
                    datapoint.setGpsSpeed(Float.parseFloat(record.get("gpsSpeed")));
                if (record.isMapped("sas"))
                    datapoint.setSAS(Float.parseFloat(record.get("sas")));
                if (record.isMapped("apps"))
                    datapoint.setAPPS(Float.parseFloat(record.get("apps")));
                if (record.isMapped("brakePressure"))
                    datapoint.setBrakePressure(Float.parseFloat(record.get("brakePressure")));
                if (record.isMapped("wheelSpeedFL"))
                    datapoint.setWheelSpeedFL(Float.parseFloat(record.get("wheelSpeedFL")));
                if (record.isMapped("wheelSpeedFR"))
                    datapoint.setWheelSpeedFR(Float.parseFloat(record.get("wheelSpeedFR")));
                if (record.isMapped("wheelSpeedRL"))
                    datapoint.setWheelSpeedRL(Float.parseFloat(record.get("wheelSpeedRL")));
                if (record.isMapped("wheelSpeedRR"))
                    datapoint.setWheelSpeedRR(Float.parseFloat(record.get("wheelSpeedRR")));
                if (record.isMapped("tyrePressureFL"))
                    datapoint.setTyrePressureFL(Float.parseFloat(record.get("tyrePressureFL")));
                if (record.isMapped("tyrePressureRL"))
                    datapoint.setTyrePressureRL(Float.parseFloat(record.get("tyrePressureRL")));
                if (record.isMapped("tyrePressureFR"))
                    datapoint.setTyrePressureFR(Float.parseFloat(record.get("tyrePressureFR")));
                if (record.isMapped("tyrePressureRR"))
                    datapoint.setTyrePressureRR(Float.parseFloat(record.get("tyrePressureRR")));
                if (record.isMapped("tyreTempFL"))
                    datapoint.setTyreTempFL(Float.parseFloat(record.get("tyreTempFL")));
                if (record.isMapped("tyreTempFR"))
                    datapoint.setTyreTempFR(Float.parseFloat(record.get("tyreTempFR")));
                if (record.isMapped("tyreTempRL"))
                    datapoint.setTyreTempRL(Float.parseFloat(record.get("tyreTempRL")));
                if (record.isMapped("tyreTempRR"))
                    datapoint.setTyreTempRR(Float.parseFloat(record.get("tyreTempRR")));
                if (record.isMapped("tyreTempSurfaceFL"))
                    datapoint.setTyreTempSurfaceFL(Float.parseFloat(record.get("tyreTempSurfaceFL")));
                if (record.isMapped("tyreTempSurfaceFR"))
                    datapoint.setTyreTempSurfaceFR(Float.parseFloat(record.get("tyreTempSurfaceFR")));
                if (record.isMapped("tyreTempSurfaceRL"))
                    datapoint.setTyreTempSurfaceRL(Float.parseFloat(record.get("tyreTempSurfaceRL")));
                if (record.isMapped("tyreTempSurfaceRR"))
                    datapoint.setTyreTempSurfaceRR(Float.parseFloat(record.get("tyreTempSurfaceRR")));
                if (record.isMapped("suspensionTravelFL"))
                    datapoint.setSuspensionTravelFL(Float.parseFloat(record.get("suspensionTravelFL")));
                if (record.isMapped("suspensionTravelFR"))
                    datapoint.setSuspensionTravelFR(Float.parseFloat(record.get("suspensionTravelFR")));
                if (record.isMapped("suspensionTravelRL"))
                    datapoint.setSuspensionTravelRL(Float.parseFloat(record.get("suspensionTravelRL")));
                if (record.isMapped("suspensionTravelRR"))
                    datapoint.setSuspensionTravelRR(Float.parseFloat(record.get("suspensionTravelRR")));
                if (record.isMapped("sscErr"))
                    datapoint.setSscErr(Boolean.valueOf(record.get("sscErr")));
                if (record.isMapped("imdErr"))
                    datapoint.setImdErr(Boolean.valueOf(record.get("imdErr")));
                if (record.isMapped("invErr"))
                    datapoint.setInvErr(Boolean.valueOf(record.get("invErr")));
                if (record.isMapped("batErr"))
                    datapoint.setBatErr(Boolean.valueOf(record.get("batErr")));

                datapointRepo.save(datapoint);
            }

            //update the dataset metadata with the duration of the session
            datasetRepo.saveDuration(datasetId, duration);

            //log successful save
            LOGGER.info("Saved dataset to database");

            return ResponseEntity.ok("CSV file processed successfully");
        } catch (Exception e) {
            //log stacktrace for specific class
            Logger.getLogger(UploadController.class.getName()).log(java.util.logging.Level.SEVERE, e.getMessage(), e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process CSV file");
        }
    }
}
