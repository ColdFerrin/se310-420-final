package org.erau.icarus.detect.ES.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;

public class DroneInfo {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String status;

    @JsonProperty(value = "@timestamp")
    private Date timestamp;

    private String image;

    private ArrayList<Identification> identifications;

    private Camera camera;

    private String test;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<Identification> getIdentifications() {
        return identifications;
    }

    public void setIdentifications(ArrayList<Identification> identifications) {
        this.identifications = identifications;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
