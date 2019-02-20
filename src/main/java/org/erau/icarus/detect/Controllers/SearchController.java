package org.erau.icarus.detect.Controllers;

import org.erau.icarus.detect.ES.Service.DroneStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {
    private DroneStorageService droneStorageService;

    @Autowired
    public SearchController(DroneStorageService droneStorageService) {
        this.droneStorageService = droneStorageService;
        System.out.println("Search Controller Created!!");
    }

    @RequestMapping("/id")
    ResponseEntity<?> getEntryByID(){
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
