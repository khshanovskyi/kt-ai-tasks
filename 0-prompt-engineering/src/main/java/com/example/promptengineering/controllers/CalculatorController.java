package com.example.promptengineering.controllers;

import com.example.promptengineering.assistants.CalculatorAssistant;
import com.example.promptengineering.assistants.UkrLawsAssistant;
import dev.langchain4j.service.TokenStream;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@RestController
@RequestMapping("v1/calculator")
@RequiredArgsConstructor
public class CalculatorController {

    private final CalculatorAssistant assistant;

    /**
     * Regular REST endpoint that produces the answer when AI fully collected it.
     * You can freely test it with Postman
     */
    @GetMapping("/chat")
    public String chat(@RequestParam("question") String question) {
        return assistant.chat(question);
    }
}
