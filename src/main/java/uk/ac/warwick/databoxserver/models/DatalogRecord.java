package uk.ac.warwick.databoxserver.models;

import java.sql.Timestamp;

public class DatalogRecord {

    private Long relativeTimestamp;

    private Timestamp absoluteTimestamp;

    private Object data;

    public Long getRelativeTimestamp() {
        return relativeTimestamp;
    }

    public void setRelativeTimestamp(Long relativeTimestamp) {
        this.relativeTimestamp = relativeTimestamp;
    }

    public Timestamp getAbsoluteTimestamp() {
        return absoluteTimestamp;
    }

    public void setAbsoluteTimestamp(Timestamp absoluteTimestamp) {
        this.absoluteTimestamp = absoluteTimestamp;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
