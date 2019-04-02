package org.erau.icarus.detect.Controllers;

import org.erau.icarus.detect.ES.Model.DroneInfo;
import org.erau.icarus.detect.Services.BNPId.BNPIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/input")
public class InputController {

    private BNPIdService bnpIdService;

    @Autowired
    public InputController(BNPIdService bnpIdService) {
        this.bnpIdService = bnpIdService;
    }

    @RequestMapping(value = "/image", method = RequestMethod.PUT)
    ResponseEntity<?> inputImage(@RequestBody DroneInfo droneInfo){
        System.out.println("You have uploaded an image");
        return new ResponseEntity<>("You have uploaded an image", HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    ResponseEntity<?> updateMetadata(){
        System.out.println("You have updated metadata");
        return new ResponseEntity<>("You have updated metadata", HttpStatus.OK);
    }
}
