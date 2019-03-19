package org.erau.icarus.detect.ES.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;

public class DroneInfo {
    @JsonIgnore
    private String id;

    @JsonProperty(value = "@timestamp")
    private Date timestamp;

    private String image;

    private boolean humanReview;

    private int hasBeenReviewed;

    private ArrayList<Identification> identifications;

    private Camera camera;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isHumanReview() {
        return humanReview;
    }

    public void setHumanReview(boolean humanReview) {
        this.humanReview = humanReview;
    }

    public int getHasBeenReviewed() {
        return hasBeenReviewed;
    }

    public void setHasBeenReviewed(int hasBeenReviewed) {
        this.hasBeenReviewed = hasBeenReviewed;
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
}
