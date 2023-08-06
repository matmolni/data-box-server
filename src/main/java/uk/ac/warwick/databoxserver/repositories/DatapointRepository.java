package uk.ac.warwick.databoxserver.repositories;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uk.ac.warwick.databoxserver.models.Datapoint;

@Repository
public class DatapointRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_UPDATE_STATEMENT = "INSERT INTO display_data.datapoints (session_foreign_key, " +
            "absolute_timestamp, " +
            "relative_timestamp, lap, gps_position, gps_speed, sas, apps, brake_pressure, " +
            "wheel_speed_fl, wheel_speed_fr, wheel_speed_rl, wheel_speed_rr," +
            "tyre_pressure_fl, tyre_pressure_fr, tyre_pressure_rl, tyre_pressure_rr," +
            "tyre_temp_fl, tyre_temp_fr, tyre_temp_rl, tyre_temp_rr," +
            "tyre_temp_surface_fl, tyre_temp_surface_fr, tyre_temp_surface_rl, tyre_temp_surface_rr," +
            "suspension_travel_fl, suspension_travel_fr, suspension_travel_rl, suspension_travel_rr," +
            "ssc_err, imd_err, inv_err, bat_err) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
            " ?, ?)";

    @Autowired
    public DatapointRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(Datapoint datapoint) {

        return jdbcTemplate.update(SQL_UPDATE_STATEMENT, datapoint.getSessionForeignKey(), datapoint.getAbsoluteTimestamp(),
                datapoint.getRelativeTimestamp(), datapoint.getLap(), datapoint.getGpsPosition(),
                datapoint.getGpsSpeed(), datapoint.getSAS(), datapoint.getAPPS(), datapoint.getBrakePressure(),
                datapoint.getWheelSpeedFL(), datapoint.getWheelSpeedFR(), datapoint.getWheelSpeedRL(),
                datapoint.getWheelSpeedRR(), datapoint.getTyrePressureFL(), datapoint.getTyrePressureFR(),
                datapoint.getTyrePressureRL(), datapoint.getTyrePressureRR(), datapoint.getTyreTempFL(),
                datapoint.getTyreTempFR(), datapoint.getTyreTempRL(), datapoint.getTyreTempRR(),
                datapoint.getTyreTempSurfaceFL(), datapoint.getTyreTempSurfaceFR(), datapoint.getTyreTempSurfaceRL(),
                datapoint.getTyreTempSurfaceRR(), datapoint.getSuspensionTravelFL(),
                datapoint.getSuspensionTravelFR(), datapoint.getSuspensionTravelRL(),
                datapoint.getSuspensionTravelRR(), datapoint.getSscErr(), datapoint.getImdErr(),
                datapoint.getImdErr(), datapoint.getBatErr());
    }
}
