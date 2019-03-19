package org.erau.icarus.detect.ES.Model;

import java.util.ArrayList;

public class Identification {
    private String idType;

    private ArrayList<Float> score;

    private String PotentialID;

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public ArrayList<Float> getScore() {
        return score;
    }

    public void setScore(ArrayList<Float> score) {
        this.score = score;
    }

    public String getPotentialID() {
        return PotentialID;
    }

    public void setPotentialID(String potentialID) {
        PotentialID = potentialID;
    }
}
