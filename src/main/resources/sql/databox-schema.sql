CREATE SCHEMA display_data;

ALTER SCHEMA display_data OWNER TO postgres;

CREATE TABLE display_data.datasets
(
    identifier   SERIAL PRIMARY KEY,
    session_name TEXT,
    description  TEXT,
    timestamp    TIMESTAMP
);

CREATE TABLE display_data.datapoints
(
    session_foreign_key  INTEGER NOT NULL,
    absolute_timestamp   TIMESTAMP,
    relative_timestamp   BIGINT NOT NULL,
    lap                  INTEGER,
    gps_position         POINT,
    gps_speed            REAL,
    sas                  REAL,
    apps                 REAL,
    brake_pressure       REAL,
    wheel_speed_fl       REAL,
    wheel_speed_fr       REAL,
    wheel_speed_rl       REAL,
    wheel_speed_rr       REAL,
    tyre_pressure_fl     REAL,
    tyre_pressure_fr     REAL,
    tyre_pressure_rl     REAL,
    tyre_pressure_rr     REAL,
    tyre_temp_fl         REAL,
    tyre_temp_fr         REAL,
    tyre_temp_rl         REAL,
    tyre_temp_rr         REAL,
    tyre_temp_surface_fl REAL,
    tyre_temp_surface_fr REAL,
    tyre_temp_surface_rl REAL,
    tyre_temp_surface_rr REAL,
    suspension_travel_fl REAL,
    suspension_travel_fr REAL,
    suspension_travel_rl REAL,
    suspension_travel_rr REAL,
    ssc_err              BOOLEAN,
    imd_err              BOOLEAN,
    inv_err              BOOLEAN,
    bat_err              BOOLEAN,
    FOREIGN KEY (session_foreign_key) REFERENCES display_data.datasets (identifier),
    PRIMARY KEY (session_foreign_key, relative_timestamp)
);


