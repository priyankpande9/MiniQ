package com.pp.miniq.core;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pp.miniq.utility.InflateDeflateUtility;

public class Message {

    @JsonProperty
    private String id;

    @JsonProperty
    @NotEmpty
    private String description;

    @JsonIgnore
    private byte[] descInBytes;

    /**
     * @return the desc
     */
    public byte[] getDescInBytes() {
        return descInBytes;
    }

    /**
     * @param desc the desc to set
     */
    public void setDescInBytes(byte[] desc) {
        this.descInBytes = desc;
    }

    private Date timestamp;

    public Message() {
    }

    public Message(String id, String description, Date ts, byte[] descInBytes) {
        this.id = id;
        this.description = description;
        this.timestamp = ts;
        this.descInBytes = descInBytes;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Message other = (Message) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (timestamp == null) {
            if (other.timestamp != null)
                return false;
        } else if (!timestamp.equals(other.timestamp))
            return false;
        return true;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the timeStamp
     */
    public Date getTimeStamp() {
        return timestamp;
    }

    /**
     * @param timeStamp the timeStamp to set
     */
    public void setTimeStamp(Date timeStamp) {
        this.timestamp = timeStamp;
    }

    public void setDescInBytes() {
        InflateDeflateUtility zipper = new InflateDeflateUtility();
        try {
            this.descInBytes = zipper.compress(description);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
