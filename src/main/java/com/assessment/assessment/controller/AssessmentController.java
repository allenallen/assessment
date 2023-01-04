package com.assessment.assessment.controller;

import com.assessment.assessment.components.AssessmentProducer;
import com.assessment.assessment.exceptions.IncorrectFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
public class AssessmentController {

    @Autowired
    private AssessmentProducer assessmentProducer;

    @GetMapping(value = "/newTopic/{event}")
    public ResponseEntity newTopic(@PathVariable String event) {
        try {
            assessmentProducer.send(event);
            return ResponseEntity.ok(event);
        } catch (IncorrectFormatException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
