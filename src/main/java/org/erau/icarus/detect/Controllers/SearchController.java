package org.erau.icarus.detect.Controllers;

import org.erau.icarus.detect.ES.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {
    private DroneRepository droneRepository;

    @Autowired
    public SearchController(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
        System.out.println("Search Controller Created!!");
    }
}
