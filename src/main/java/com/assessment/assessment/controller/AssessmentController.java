package com.assessment.assessment.controller;

import com.assessment.assessment.components.AssessmentProducer;
import com.assessment.assessment.exceptions.IncorrectFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/assessment")
public class AssessmentController {

    @Autowired
    private AssessmentProducer assessmentProducer;

    @GetMapping(value = {"", "/"})
    public ResponseEntity newEvent(@RequestParam String[] events) {
        try {
            assessmentProducer.send(events);
            return ResponseEntity.ok(events);
        } catch (IncorrectFormatException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
