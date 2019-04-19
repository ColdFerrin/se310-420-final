package org.erau.icarus.detect.Services.BNPId;

import java.util.Random;
import java.util.ArrayList;

import org.erau.icarus.detect.ES.Service.DroneStorageService;
import org.erau.icarus.detect.Services.Classification.ClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BNPIdService {

    private DroneStorageService droneStorageService;

    private ClassificationService classificationService;

    private float scoreGenerator(){
        Random rand = new Random();
        float score;
        score = rand.nextFloat();
        return score;
    }

    private Identification identify(){
        DroneInfo drone = new DroneInfo();
        Identification filler =  new Identification();

        for(int i = 0; i < 4; i++){
            float tempScore = scoreGenerator();
            filler.add(tempScore);
        }

        compareScores(filler);
        drone.add(filler);

    }

    private float compareScores(Identification src){
      float high;
      float smDiff = 1;
      float tempDiff = 0;
      high = src.get(0);

      for(int i = 0; i < 4; i++){
         if(high <= src.get(i)){
           high = src.get(i);
         }
         else if(high == src.get(i)){}

         else{
           tempDiff = (high - src.get(i)) * 100;
           if(tempDiff < smDiff){
             smDiff = tempDiff;
             if(smDiff >= 85){
                if(src.indexOf(high) == 0)
                 src.idType = "B";
               else if(src.indexOf(high) == 1)
                 src.idType = "N";
               else if(src.indexOf(high) == 2)
                 src.idType = "P";
               else if(src.indexOf(high) == 3)
                 src.idType = "D";
             }
           }
         }

      }
    }

    @Autowired
    public BNPIdService(DroneStorageService droneStorageService, ClassificationService classificationService) {
        this.droneStorageService = droneStorageService;
        this.classificationService = classificationService;
    }
}
