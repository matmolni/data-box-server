package uk.ac.warwick.databoxserver.repositories;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import uk.ac.warwick.databoxserver.models.Datapoint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class DatapointRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatapointRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Saves a datapoint to the database.
     * @param datapoint Datapoint object to save
     * @return number of rows affected
     */
    public int save(Datapoint datapoint) {

        return jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            @NonNull
            public PreparedStatement createPreparedStatement(@NonNull Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement("INSERT INTO logger_data.datapoint (session_foreign_key, " +
                        "absolute_timestamp, " +
                        "relative_timestamp, lap, gps_position, gps_speed, sas, apps, brake_pressure, " +
                        "wheel_speed_fl, wheel_speed_fr, wheel_speed_rl, wheel_speed_rr," +
                        "tyre_pressure_fl, tyre_pressure_fr, tyre_pressure_rl, tyre_pressure_rr," +
                        "tyre_temp_fl, tyre_temp_fr, tyre_temp_rl, tyre_temp_rr," +
                        "tyre_temp_surface_fl, tyre_temp_surface_fr, tyre_temp_surface_rl, tyre_temp_surface_rr," +
                        "suspension_travel_fl, suspension_travel_fr, suspension_travel_rl, suspension_travel_rr," +
                        "ssc_err, imd_err, inv_err, bat_err) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                        " ?, ?)");

                ps.setInt(1, datapoint.getSessionForeignKey());
                ps.setTimestamp(2, datapoint.getAbsoluteTimestamp());
                ps.setDouble(3, datapoint.getRelativeTimestamp());
                ps.setInt(4, datapoint.getLap());
                ps.setObject(5, datapoint.getGpsPosition());
                ps.setDouble(6, datapoint.getGpsSpeed());
                ps.setDouble(7, datapoint.getSAS());
                ps.setDouble(8, datapoint.getAPPS());
                ps.setDouble(9, datapoint.getBrakePressure());
                ps.setDouble(10, datapoint.getWheelSpeedFL());
                ps.setDouble(11, datapoint.getWheelSpeedFR());
                ps.setDouble(12, datapoint.getWheelSpeedRL());
                ps.setDouble(13, datapoint.getWheelSpeedRR());
                ps.setDouble(14, datapoint.getTyrePressureFL());
                ps.setDouble(15, datapoint.getTyrePressureFR());
                ps.setDouble(16, datapoint.getTyrePressureRL());
                ps.setDouble(17, datapoint.getTyrePressureRR());
                ps.setDouble(18, datapoint.getTyreTempFL());
                ps.setDouble(19, datapoint.getTyreTempFR());
                ps.setDouble(20, datapoint.getTyreTempRL());
                ps.setDouble(21, datapoint.getTyreTempRR());
                ps.setDouble(22, datapoint.getTyreTempSurfaceFL());
                ps.setDouble(23, datapoint.getTyreTempSurfaceFR());
                ps.setDouble(24, datapoint.getTyreTempSurfaceRL());
                ps.setDouble(25, datapoint.getTyreTempSurfaceRR());
                ps.setDouble(26, datapoint.getSuspensionTravelFL());
                ps.setDouble(27, datapoint.getSuspensionTravelFR());
                ps.setDouble(28, datapoint.getSuspensionTravelRL());
                ps.setDouble(29, datapoint.getSuspensionTravelRR());
                ps.setBoolean(30, datapoint.getSscErr());
                ps.setBoolean(31, datapoint.getImdErr());
                ps.setBoolean(32, datapoint.getInvErr());
                ps.setBoolean(33, datapoint.getBatErr());

                return ps;
            }
        });
    }
}
