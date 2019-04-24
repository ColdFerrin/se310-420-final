package org.erau.icarus.detect.Services.HumanReview;

import org.erau.icarus.detect.ES.Service.DroneStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HumanReviewService {

    private DroneStorageService droneStorageService;

    @Autowired
    public HumanReviewService(DroneStorageService droneStorageService) {
        this.droneStorageService = droneStorageService;
    }
}
