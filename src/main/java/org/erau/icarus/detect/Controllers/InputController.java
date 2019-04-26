package org.erau.icarus.detect.Controllers;

import org.erau.icarus.detect.ES.Model.DroneInfo;
import org.erau.icarus.detect.ES.Service.DroneStorageService;
import org.erau.icarus.detect.Services.BNPDId.BNPDIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.util.Optional;

@RestController
@RequestMapping("/input")
public class InputController {

    private BNPDIdService BNPDIdService;

    private DroneStorageService droneStorageService;

    @Autowired
    public InputController(BNPDIdService BNPDIdService, DroneStorageService droneStorageService) {
        this.BNPDIdService = BNPDIdService;
        this.droneStorageService = droneStorageService;
    }

    //Define mapping as /input/image and http method as put
    @RequestMapping(value = "/image", method = RequestMethod.PUT)
    // Input to the function is the body of the http request mapped to drone info object
    ResponseEntity<?> inputImage(@RequestBody DroneInfo droneInfo) throws IOException{
        //generate timestamp for image
        droneInfo.setTimestamp(Date.from(Instant.now()));
        //input the drone info to bnpdid service
        DroneInfo toReturn = BNPDIdService.input(droneInfo);
        //set status
        toReturn.setStatus("Image uploaded");
        //return response entity with body of droneinfo.
        return new ResponseEntity<>(toReturn, HttpStatus.OK);
    }

    //define mapping as /input/update and http method as put
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    ResponseEntity<?> updateMetadata(@RequestBody DroneInfo droneInfo) throws IOException {
        // find requested drone from database
        Optional<DroneInfo> optionalDroneInfo = droneStorageService.findOne(droneInfo.getId());
        //check if requested drone is in database
        if(optionalDroneInfo.isPresent()){
            //get the drone info from database out of optional
            DroneInfo droneToBeUpdated = optionalDroneInfo.get();
            //check if the camera is the same as what is in the database
            if(!droneInfo.getCamera().equals(droneToBeUpdated.getCamera())){
                //Set the camera with new data if it is different
                optionalDroneInfo.get().setCamera(droneInfo.getCamera());
            }
            else{
                //send an error if nothing has changed
                droneToBeUpdated.setError("Redundant camera info modification");
                return new ResponseEntity<>(droneToBeUpdated, HttpStatus.CONFLICT);
            }

            //save the newest drone info to database
            droneStorageService.save(droneToBeUpdated);
        }
        else{
            //if the drone requested is not fround send an error
            droneInfo.setError("Drone not found");
            return new ResponseEntity<>(droneInfo, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("You have updated metadata", HttpStatus.OK);
    }
}
