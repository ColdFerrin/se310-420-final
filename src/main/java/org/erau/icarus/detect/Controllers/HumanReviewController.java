package org.erau.icarus.detect.Controllers;

import org.erau.icarus.detect.Services.HumanReview.HumanReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HumanReviewController {

    private HumanReviewService humanReviewService;

    @Autowired
    public HumanReviewController(HumanReviewService humanReviewService) {
        this.humanReviewService = humanReviewService;
    }
}
