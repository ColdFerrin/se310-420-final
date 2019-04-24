package org.erau.icarus.detect.Services.DroneId;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.erau.icarus.detect.ES.Model.DroneInfo;
import org.erau.icarus.detect.ES.Model.Identification;
import org.erau.icarus.detect.ES.Service.DroneStorageService;
import org.erau.icarus.detect.Services.HumanReview.HumanReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DroneIdService {

    private DroneStorageService droneStorageService;

    private HumanReviewService humanReviewService;

    private Random rand;

    @Autowired
    public DroneIdService(DroneStorageService droneStorageService, HumanReviewService humanReviewService) {
        this.droneStorageService = droneStorageService;
        this.humanReviewService = humanReviewService;
        this.rand = new Random();
    }

    public void setRandSeed(Long seed) {
        rand = new Random(seed);
    }

    public void input(DroneInfo droneInfo) throws IOException {
        Identification input = new Identification();
        input.setIdType("DroneID");
        ArrayList<Float> scores = new ArrayList<>();

        for(int i = 0; i < 128; i++){
            scores.add(rand.nextFloat());
        }

        input.setScore(scores);
        float genData = rand.nextFloat();
        if(genData > .9){
            input.setHumanReviewNeeded(true);
        }

        droneInfo.getIdentifications().add(input);

        droneStorageService.save(droneInfo);
    }
}
