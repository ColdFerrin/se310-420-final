package org.erau.icarus.detect.Services.DroneId;

import org.erau.icarus.detect.ES.Model.DroneInfo;
import org.erau.icarus.detect.ES.Model.Identification;
import org.erau.icarus.detect.ES.Service.DroneStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

@Service
public class DroneIdService {

    private DroneStorageService droneStorageService;

    //This will be removed when switching to AI
    private Random rand;

    @Autowired
    public DroneIdService(DroneStorageService droneStorageService) {
        this.droneStorageService = droneStorageService;
        //This will be removed when switching to AI
        this.rand = new Random();
    }

    //This will be removed when switching to AI
    public void setRandSeed(Long seed) {
        rand = new Random(seed);
    }
    //Creates DroneInfo input function for DroneIdService
    public DroneInfo input(DroneInfo droneInfo) throws IOException {
        //Creates an Idenfifications input named input
        Identification input = new Identification();
        //Calls setIdType from input and passes DroneID as the string to be set
        input.setIdType("DroneID");
        //Creates an ArrayList of floats named scores
        ArrayList<Float> scores = new ArrayList<>();

        //This will be removed when switching to AI
        //Generated 128 random floats to emulate the 128 dimension analysis
        for(int i = 0; i < 128; i++){
            scores.add(rand.nextFloat());
        }

        //Calls setScore from input and passes the ArrayList scores
        input.setScore(scores);
        //Creates a float for genData and sets it equal to a random number
        float genData = rand.nextFloat();
        //This will be removed when switching to AI
        //Needed to emulate that some results need to be reviewed
        if(genData > .9){
            input.setHumanReviewNeeded(true);
        }
        //Calls DroneInfo's getIdentifications() to get the Identifications ArrayList and uses the add function of ArrayLists
        //to add the Identifications object input to the Identifications ArrayList
        droneInfo.getIdentifications().add(input);
        //Returns the result of the function save from the droneStorageService passing the droneInfo
        return droneStorageService.save(droneInfo);
    }
}
