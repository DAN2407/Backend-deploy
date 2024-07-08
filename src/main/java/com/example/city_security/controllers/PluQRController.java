package com.example.city_security.controllers;

import com.example.city_security.models.entities.CodigoQR;
import com.example.city_security.services.CodigoqrService;
import com.example.city_security.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class PluQRController {

    private boolean qrState = false;

    @Autowired
    private CodigoqrService codigoqrService;

    @PostMapping("/state/activate")
    public void activateQrState() {
        qrState = true;
    }

    @GetMapping("/state/activate")
    public String activateStateGet() {
        if (qrState) {
            qrState = false; // Reset the state after reading
            return "1";
        }
        return "0";
    }

    @PostMapping("/qr-data")
    public String handleQrData(@RequestBody CodigoQR qrData) {
        System.out.println("QR Data received: " + qrData.getId());
        boolean isValid = codigoqrService.IsCodeQRValid(qrData.getId()) ;
        if (isValid) {
            activateQrState(); // Activar el estado QR
            return "1";
        }
        return "0";
    }


}
