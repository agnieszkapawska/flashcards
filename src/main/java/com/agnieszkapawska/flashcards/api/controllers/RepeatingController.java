package com.agnieszkapawska.flashcards.api.controllers;
//
//import com.agnieszkapawska.flashcards.domain.facades.RepeatingFacade;
//import com.agnieszkapawska.flashcards.domain.utils.Answer;
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@AllArgsConstructor
//@RequestMapping("/repeating")
//public class RepeatingController {
//    private RepeatingFacade repeatingFacade;
//
//    @PutMapping
//    public ResponseEntity<Void> markAnswer(@RequestBody Answer answer) {
//        repeatingFacade.markAnswer(answer);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//}
