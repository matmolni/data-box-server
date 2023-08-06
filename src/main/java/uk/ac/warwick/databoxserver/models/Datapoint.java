package uk.ac.warwick.databoxserver.models;

import org.postgresql.geometric.PGpoint;

import java.sql.Timestamp;
import java.time.Duration;

public class Datapoint {
    private Integer sessionForeignKey;
    private Timestamp absoluteTimestamp;
    private Long relativeTimestamp;
    private Integer lap;
    private PGpoint gpsPosition;
    private Float gpsSpeed;
    private Float sas;
    private Float apps;
    private Float brakePressure;
    private Float wheelSpeedFL;
    private Float wheelSpeedFR;
    private Float wheelSpeedRL;
    private Float wheelSpeedRR;
    private Float tyrePressureFL;
    private Float tyrePressureFR;
    private Float tyrePressureRL;
    private Float tyrePressureRR;
    private Float tyreTempFL;
    private Float tyreTempFR;
    private Float tyreTempRL;
    private Float tyreTempRR;
    private Float tyreTempSurfaceFL;
    private Float tyreTempSurfaceFR;
    private Float tyreTempSurfaceRL;
    private Float tyreTempSurfaceRR;
    private Float suspensionTravelFL;
    private Float suspensionTravelFR;
    private Float suspensionTravelRL;
    private Float suspensionTravelRR;
    private Boolean sscErr;
    private Boolean imdErr;
    private Boolean invErr;
    private Boolean batErr;

    public Datapoint() {

    }

    public Integer getSessionForeignKey() {
        return sessionForeignKey;
    }

    public void setSessionForeignKey(Integer sessionForeignKey) {
        this.sessionForeignKey = sessionForeignKey;
    }

    public Timestamp getAbsoluteTimestamp() {
        return absoluteTimestamp;
    }

    public void setAbsoluteTimestamp(Timestamp absoluteTimestamp) {
        this.absoluteTimestamp = absoluteTimestamp;
    }

    public Long getRelativeTimestamp() {
        return relativeTimestamp;
    }

    public void setRelativeTimestamp(Long relativeTimestamp) {
        this.relativeTimestamp = relativeTimestamp;
    }

    public Integer getLap() {
        return lap;
    }

    public void setLap(Integer lap) {
        this.lap = lap;
    }

    public PGpoint getGpsPosition() {
        return gpsPosition;
    }

    public void setGpsPosition(PGpoint gpsPosition) {
        this.gpsPosition = gpsPosition;
    }

    public Float getGpsSpeed() {
        return gpsSpeed;
    }

    public void setGpsSpeed(Float gpsSpeed) {
        this.gpsSpeed = gpsSpeed;
    }

    public Float getSAS() {
        return sas;
    }

    public void setSAS(Float sas) {
        this.sas = sas;
    }

    public Float getAPPS() {
        return apps;
    }

    public void setAPPS(Float apps) {
        this.apps = apps;
    }

    public Float getBrakePressure() {
        return brakePressure;
    }

    public void setBrakePressure(Float brakePressure) {
        this.brakePressure = brakePressure;
    }

    public Float getWheelSpeedFL() {
        return wheelSpeedFL;
    }

    public void setWheelSpeedFL(Float wheelSpeedFL) {
        this.wheelSpeedFL = wheelSpeedFL;
    }

    public Float getWheelSpeedFR() {
        return wheelSpeedFR;
    }

    public void setWheelSpeedFR(Float wheelSpeedFR) {
        this.wheelSpeedFR = wheelSpeedFR;
    }

    public Float getWheelSpeedRL() {
        return wheelSpeedRL;
    }

    public void setWheelSpeedRL(Float wheelSpeedRL) {
        this.wheelSpeedRL = wheelSpeedRL;
    }

    public Float getWheelSpeedRR() {
        return wheelSpeedRR;
    }

    public void setWheelSpeedRR(Float wheelSpeedRR) {
        this.wheelSpeedRR = wheelSpeedRR;
    }

    public Float getTyrePressureFL() {
        return tyrePressureFL;
    }

    public void setTyrePressureFL(Float tyrePressureFL) {
        this.tyrePressureFL = tyrePressureFL;
    }

    public Float getTyrePressureFR() {
        return tyrePressureFR;
    }

    public void setTyrePressureFR(Float tyrePressureFR) {
        this.tyrePressureFR = tyrePressureFR;
    }

    public Float getTyrePressureRL() {
        return tyrePressureRL;
    }

    public void setTyrePressureRL(Float tyrePressureRL) {
        this.tyrePressureRL = tyrePressureRL;
    }

    public Float getTyrePressureRR() {
        return tyrePressureRR;
    }

    public void setTyrePressureRR(Float tyrePressureRR) {
        this.tyrePressureRR = tyrePressureRR;
    }

    public Float getTyreTempFL() {
        return tyreTempFL;
    }

    public void setTyreTempFL(Float tyreTempFL) {
        this.tyreTempFL = tyreTempFL;
    }

    public Float getTyreTempFR() {
        return tyreTempFR;
    }

    public void setTyreTempFR(Float tyreTempFR) {
        this.tyreTempFR = tyreTempFR;
    }

    public Float getTyreTempRL() {
        return tyreTempRL;
    }

    public void setTyreTempRL(Float tyreTempRL) {
        this.tyreTempRL = tyreTempRL;
    }

    public Float getTyreTempRR() {
        return tyreTempRR;
    }

    public void setTyreTempRR(Float tyreTempRR) {
        this.tyreTempRR = tyreTempRR;
    }

    public Float getTyreTempSurfaceFL() {
        return tyreTempSurfaceFL;
    }

    public void setTyreTempSurfaceFL(Float tyreTempSurfaceFL) {
        this.tyreTempSurfaceFL = tyreTempSurfaceFL;
    }

    public Float getTyreTempSurfaceFR() {
        return tyreTempSurfaceFR;
    }

    public void setTyreTempSurfaceFR(Float tyreTempSurfaceFR) {
        this.tyreTempSurfaceFR = tyreTempSurfaceFR;
    }

    public Float getTyreTempSurfaceRL() {
        return tyreTempSurfaceRL;
    }

    public void setTyreTempSurfaceRL(Float tyreTempSurfaceRL) {
        this.tyreTempSurfaceRL = tyreTempSurfaceRL;
    }

    public Float getTyreTempSurfaceRR() {
        return tyreTempSurfaceRR;
    }

    public void setTyreTempSurfaceRR(Float tyreTempSurfaceRR) {
        this.tyreTempSurfaceRR = tyreTempSurfaceRR;
    }

    public Float getSuspensionTravelFL() {
        return suspensionTravelFL;
    }

    public void setSuspensionTravelFL(Float suspensionTravelFL) {
        this.suspensionTravelFL = suspensionTravelFL;
    }

    public Float getSuspensionTravelFR() {
        return suspensionTravelFR;
    }

    public void setSuspensionTravelFR(Float suspensionTravelFR) {
        this.suspensionTravelFR = suspensionTravelFR;
    }

    public Float getSuspensionTravelRL() {
        return suspensionTravelRL;
    }

    public void setSuspensionTravelRL(Float suspensionTravelRL) {
        this.suspensionTravelRL = suspensionTravelRL;
    }

    public Float getSuspensionTravelRR() {
        return suspensionTravelRR;
    }

    public void setSuspensionTravelRR(Float suspensionTravelRR) {
        this.suspensionTravelRR = suspensionTravelRR;
    }

    public Boolean getSscErr() {
        return sscErr;
    }

    public void setSscErr(Boolean sscErr) {
        this.sscErr = sscErr;
    }

    public Boolean getImdErr() {
        return imdErr;
    }

    public void setImdErr(Boolean imdErr) {
        this.imdErr = imdErr;
    }

    public Boolean getInvErr() {
        return invErr;
    }

    public void setInvErr(Boolean invErr) {
        this.invErr = invErr;
    }

    public Boolean getBatErr() {
        return batErr;
    }

    public void setBatErr(Boolean batErr) {
        this.batErr = batErr;
    }
}

