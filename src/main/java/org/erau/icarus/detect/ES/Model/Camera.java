package org.erau.icarus.detect.ES.Model;

public class Camera {
    private String cameraID;

    private CameraLocation cameraLocation;

    private CameraModel cameraModel;

    public String getCameraID() {
        return cameraID;
    }

    public void setCameraID(String cameraID) {
        this.cameraID = cameraID;
    }

    public CameraLocation getCameraLocation() {
        return cameraLocation;
    }

    public void setCameraLocation(CameraLocation cameraLocation) {
        this.cameraLocation = cameraLocation;
    }

    public CameraModel getCameraModel() {
        return cameraModel;
    }

    public void setCameraModel(CameraModel cameraModel) {
        this.cameraModel = cameraModel;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Camera camera = (Camera) o;

        if (!cameraID.equals(camera.cameraID)) return false;
        if (!cameraLocation.equals(camera.cameraLocation)) return false;
        return cameraModel.equals(camera.cameraModel);

    }

    @Override
    public int hashCode() {
        int result = cameraID.hashCode();
        result = 31 * result + cameraLocation.hashCode();
        result = 31 * result + cameraModel.hashCode();
        return result;
    }
}
