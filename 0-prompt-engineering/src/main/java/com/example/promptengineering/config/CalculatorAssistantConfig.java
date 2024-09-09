package com.example.promptengineering.config;

import com.example.promptengineering.services.CalculatorService;
import com.example.promptengineering.assistants.CalculatorAssistant;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.example.promptengineering.urils.Utils.createContentRetriever;
import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocuments;

@Configuration
public class CalculatorAssistantConfig {

    /**
     * Creates bean of {@link CalculatorAssistant}. Under the hood will be created a proxy that will have all AI components.
     * If you acknowledged with 'chains' from langchain - its alternative of them
     *
     * @see LangchaingConfig
     */
    @Bean
    CalculatorAssistant calculatorAssistant(ChatLanguageModel chatLanguageModel, CalculatorService calculatorService)  {
        return AiServices.builder(CalculatorAssistant.class)
                .chatLanguageModel(chatLanguageModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10)) //maximum amount of previous questions from user and answers that will be passed with request to AI
                .tools(calculatorService)// AI will use methods with 'Tool' annotation for calculations
                .build();
    }

}
