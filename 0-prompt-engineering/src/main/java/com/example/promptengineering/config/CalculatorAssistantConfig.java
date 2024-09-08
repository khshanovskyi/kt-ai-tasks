package com.example.promptengineering.config;

import com.example.promptengineering.Services.CalculatorService;
import com.example.promptengineering.assistants.CalculatorAssistant;
import com.example.promptengineering.assistants.UkrLawsAssistant;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URISyntaxException;
import java.util.List;

import static com.example.promptengineering.urils.Utils.createContentRetriever;
import static com.example.promptengineering.urils.Utils.embeddingStore;
import static com.example.promptengineering.urils.Utils.glob;
import static com.example.promptengineering.urils.Utils.toPath;
import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocuments;

@Configuration
public class CalculatorAssistantConfig {

    @Bean
    CalculatorAssistant calculatorAssistant(ChatLanguageModel chatLanguageModel, CalculatorService calculatorService)  {
        return AiServices.builder(CalculatorAssistant.class)
                .chatLanguageModel(chatLanguageModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10)) //maximum amount of previous questions from user and answers that will be passed with request to AI
                .tools(calculatorService)// AI will use methods with 'Tool' annotation for calculations
                .build();
    }

}
