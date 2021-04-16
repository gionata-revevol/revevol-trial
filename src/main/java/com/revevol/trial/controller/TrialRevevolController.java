package com.revevol.trial.controller;

import com.revevol.trial.model.User;
import com.revevol.trial.service.TrialRevevolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/api/v1/email")
public class TrialRevevolController {

    @Autowired
    TrialRevevolService service;

    @PostMapping(value = "/upload", produces = "application/json", consumes = "multipart/form-data")
    public ResponseEntity<List<User>> upload(@RequestParam(value = "file", required = true) MultipartFile file) throws Exception {
        service.upload(file);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/retrieve/{fileName}", produces = "application/json")
    public ResponseEntity<List<String>> retrieve(@PathVariable("fileName") String fileName) throws Exception {
        return ResponseEntity.ok(service.retriveEmail(fileName));
    }

}
