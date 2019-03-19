package org.erau.icarus.detect.ES.Model;

import org.elasticsearch.common.geo.GeoPoint;

public class Camera {
    private String cameraID;

    private GeoPoint cameraLocation;

    private CameraModel cameraModel;

    public String getCameraID() {
        return cameraID;
    }

    public void setCameraID(String cameraID) {
        this.cameraID = cameraID;
    }

    public GeoPoint getCameraLocation() {
        return cameraLocation;
    }

    public void setCameraLocation(GeoPoint cameraLocation) {
        this.cameraLocation = cameraLocation;
    }

    public CameraModel getCameraModel() {
        return cameraModel;
    }

    public void setCameraModel(CameraModel cameraModel) {
        this.cameraModel = cameraModel;
    }
}
