package org.erau.icarus.detect.Services.BNPDId;

import org.erau.icarus.detect.ES.Model.DroneInfo;
import org.erau.icarus.detect.ES.Model.Identification;
import org.erau.icarus.detect.ES.Service.DroneStorageService;
import org.erau.icarus.detect.Services.DroneId.DroneIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

@Service
public class BNPDIdService {

    private DroneStorageService droneStorageService;

    private DroneIdService droneIdService;

    //This will be removed when switching to AI
    private Random rand;

    @Autowired
    public BNPDIdService(DroneStorageService droneStorageService, DroneIdService droneIdService) {
        this.droneStorageService = droneStorageService;
        this.droneIdService = droneIdService;
        //This will be removed when switching to AI
        this.rand = new Random();
    }

    public DroneInfo input(DroneInfo drone) throws IOException {
        DroneInfo toReturn;
        ArrayList<Identification> identifications = new ArrayList<>();
        Identification filler = new Identification();
        ArrayList<Float> scores = new ArrayList<>();
        filler.setIdType("BNPDId");

        //This will be removed when switching to AI
        for (int i = 0; i < 4; i++) {
            float tempScore = scoreGenerator();
            scores.add(tempScore);
        }

        filler.setScore(scores);
        compareScores(filler);
        identifications.add(filler);
        drone.setIdentifications(identifications);

        if(!filler.isHumanReviewNeeded() && filler.getPotentialID().equalsIgnoreCase("D")){
            toReturn = droneIdService.input(drone);
        }
        else{
            toReturn = droneStorageService.save(drone);
        }

        return toReturn;
    }

    //This will be removed when switching to AI
    public void setRandSeed(long seed) {
        rand = new Random(seed);
    }

    private void compareScores(Identification src) {
        float max;
        HashMap<Integer, Float> goodScores = new HashMap<>();
        ArrayList<Float> scores = src.getScore();
        max = scores.get(0);

        for (int i = 0; i < 4; i++) {
            float currentScore = scores.get(i);
            if (max <= currentScore) {
                max = currentScore;
            }

            if (currentScore > .85) {
                goodScores.put(i, currentScore);
            }
        }

        if (goodScores.size() == 1) {
            if (scores.indexOf(max) == 0)
                src.setPotentialID("B");
            else if (scores.indexOf(max) == 1)
                src.setPotentialID("N");
            else if (scores.indexOf(max) == 2)
                src.setPotentialID("P");
            else if (scores.indexOf(max) == 3)
                src.setPotentialID("D");
        }
        else {
            src.setHumanReviewNeeded(true);
        }
    }

    private float scoreGenerator() {
        return rand.nextFloat();
    }
}
