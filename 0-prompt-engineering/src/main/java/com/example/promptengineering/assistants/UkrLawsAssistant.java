package com.example.promptengineering.assistants;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;

public interface UkrLawsAssistant {

    String SYSTEM_PROMPT = """
            You are an expert in Ukrainian Constitution and laws, Ukrainian Criminal code and Code of Civil Protection of Ukraine,
             and your role is consultant in the Ukrainian constitution and its laws.
            Your main goal is to provide SHORT and CONCISE answers with users questions related to Ukrainian constitution and its laws.
            You are strictly prohibited to answer to questions that are not relayed to the Ukrainian constitution and laws. In case if
             question do not related to Ukrainian constitution and its laws you need to respond that you are not eligible to answers
             that is not related to Ukrainian constitution and its laws.
            Make you answer not more than 2000 tokens.
            """;

    /**
     * Non-streaming
     */
    @SystemMessage(SYSTEM_PROMPT)
    String chat(String userMessage);

    /**
     * Streaming
     */
    @SystemMessage(SYSTEM_PROMPT)
    TokenStream chatStream(String userMessage);

}
