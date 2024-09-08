package com.example.promptengineering.config;

import com.example.promptengineering.assistants.InterviewAssistant;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
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
public class InterviewAssistantConfig {

    /**
     * Creates bean of {@link InterviewAssistant}. Under the hood will be created a proxy that will have all AI components.
     * If you acknowledged with 'chains' from langchain - its alternative of them
     *
     * @see LangchaingConfig
     */
    @Bean
    InterviewAssistant interviewAssistant(ChatLanguageModel chatLanguageModel,
                                        EmbeddingModel embeddingModel) throws URISyntaxException {
        // Loads TXT documents from 'documents/interview/'
        List<Document> documents = loadDocuments(toPath("documents/interview/"), glob("*.txt"));

        return AiServices.builder(InterviewAssistant.class)
                .chatLanguageModel(chatLanguageModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10)) //maximum amount of previous questions from user and answers that will be passed with request to AI
                .contentRetriever(
                        createContentRetriever(
                                documents,
                                embeddingStore(),
                                embeddingModel
                                //no splitters provided, so, the document will be fully stored as embedding
                        )
                )
                .build();
    }

}
