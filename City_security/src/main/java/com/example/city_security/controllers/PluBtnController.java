package com.example.city_security.controllers;

import com.example.city_security.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/control-pluma")
@CrossOrigin("*")
public class PluBtnController {
    @Autowired
    private StateService stateService;

    @PostMapping
    public ResponseEntity<Map<String, String>> controlPluma(@RequestBody Map<String, String> request) {
        String action = request.get("action");

        if ("abrir".equals(action)) {
            stateService.activateState();
            return ResponseEntity.ok(Collections.singletonMap("message", "Pluma abierta"));
        }

        return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Acción no válida"));
    }
}
