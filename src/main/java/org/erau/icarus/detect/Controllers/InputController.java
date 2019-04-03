package org.erau.icarus.detect.Controllers;

import org.erau.icarus.detect.ES.Model.DroneInfo;
import org.erau.icarus.detect.ES.Service.DroneStorageService;
import org.erau.icarus.detect.Services.BNPId.BNPIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/input")
public class InputController {

    private BNPIdService bnpIdService;

    private DroneStorageService droneStorageService;

    @Autowired
    public InputController(BNPIdService bnpIdService, DroneStorageService droneStorageService) {
        this.bnpIdService = bnpIdService;
        this.droneStorageService = droneStorageService;
    }

    @RequestMapping(value = "/image", method = RequestMethod.PUT)
    ResponseEntity<?> inputImage(@RequestBody DroneInfo droneInfo){
        bnpIdService.input(droneInfo);
        return new ResponseEntity<>("You have uploaded an image", HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    ResponseEntity<?> updateMetadata(@RequestBody DroneInfo droneInfo) throws IOException {
        Optional<DroneInfo> optionalDroneInfo = droneStorageService.findByID(droneInfo.getId());
        if(optionalDroneInfo.isPresent()){
            DroneInfo droneToBeUpdated = optionalDroneInfo.get();
            if(!droneInfo.getCamera().equals(droneToBeUpdated.getCamera())){
                optionalDroneInfo.get().setCamera(droneInfo.getCamera());
            }
            else{
                droneToBeUpdated.setError("Redundant camera info modification");
                return new ResponseEntity<>(droneToBeUpdated, HttpStatus.CONFLICT);
            }

            droneStorageService.save(droneToBeUpdated);
        }
        else{
            droneInfo.setError("Drone not found");
            return new ResponseEntity<>(droneInfo, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("You have updated metadata", HttpStatus.OK);
    }
}
