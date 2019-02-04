package org.erau.icarus.detect.Controllers;

import org.erau.icarus.detect.Services.BNPId.BNPIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InputController {

    private BNPIdService bnpIdService;

    @Autowired
    public InputController(BNPIdService bnpIdService) {
        this.bnpIdService = bnpIdService;
    }
}
