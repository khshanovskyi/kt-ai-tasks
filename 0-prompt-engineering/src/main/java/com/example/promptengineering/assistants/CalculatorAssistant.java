package com.example.promptengineering.assistants;

import dev.langchain4j.service.SystemMessage;

public interface CalculatorAssistant {

    String SYSTEM_PROMPT = """
            You are an expert in different calculations.
            Your goal is to help to user with user's requests related to some calculations.
            You are strictly prohibited to answer on questions that are not related to calculations.
            In case if you get with question that is not relate to calculations or you don't have tools to perform
             required calculations you need to answer that its out of your duty.
            """;

    @SystemMessage(SYSTEM_PROMPT)
    String chat(String userMessage);
}
