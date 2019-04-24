package org.erau.icarus.detect.Services.BNPDId;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;

import org.erau.icarus.detect.ES.Model.DroneInfo;
import org.erau.icarus.detect.ES.Model.Identification;
import org.erau.icarus.detect.ES.Service.DroneStorageService;
import org.erau.icarus.detect.Services.DroneId.DroneIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BNPDIdService {

    private DroneStorageService droneStorageService;

    private DroneIdService droneIdService;

    private Random rand;

    @Autowired
    public BNPDIdService(DroneStorageService droneStorageService, DroneIdService droneIdService) {
        this.droneStorageService = droneStorageService;
        this.droneIdService = droneIdService;
        this.rand = new Random();
    }

    public void input(DroneInfo drone) throws IOException {
        ArrayList<Identification> identifications = new ArrayList<>();
        Identification filler = new Identification();
        ArrayList<Float> scores = new ArrayList<>();
        filler.setIdType("BNPDId");

        for (int i = 0; i < 4; i++) {
            float tempScore = scoreGenerator();
            scores.add(tempScore);
        }

        filler.setScore(scores);
        compareScores(filler);
        identifications.add(filler);
        drone.setIdentifications(identifications);

        if(!filler.isHumanReviewNeeded() && filler.getPotentialID().equalsIgnoreCase("D")){
            droneIdService.input(drone);
        }
        else{
            droneStorageService.save(drone);
        }
    }

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
        float score;
        score = rand.nextFloat();
        return score;
    }
}
