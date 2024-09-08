package com.example.promptengineering.config;

import com.example.promptengineering.assistants.UkrLawsAssistant;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2q.AllMiniLmL6V2QuantizedEmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.List;

import static com.example.promptengineering.urils.Utils.createContentRetriever;
import static com.example.promptengineering.urils.Utils.embeddingStore;
import static com.example.promptengineering.urils.Utils.glob;
import static com.example.promptengineering.urils.Utils.toPath;
import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocuments;

@Configuration
public class UkrLawsAssistantConfig {

    @Bean
    UkrLawsAssistant ukrLawsAssistant(StreamingChatLanguageModel streamingChatLanguageModel,
                                          ChatLanguageModel chatLanguageModel,
                                          EmbeddingModel embeddingModel) throws URISyntaxException {
        // Loads PDF documents from 'documents/laws/' and parse its binary content into text representation
        List<Document> documents = loadDocuments(toPath("documents/laws/"), glob("*.pdf"), new ApachePdfBoxDocumentParser());

        return AiServices.builder(UkrLawsAssistant.class)
                .streamingChatLanguageModel(streamingChatLanguageModel)
                .chatLanguageModel(chatLanguageModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(1)) //maximum amount of previous questions from user and answers that will be passed with request to AI
                .contentRetriever(
                        createContentRetriever(
                                documents,
                                embeddingStore(),
                                embeddingModel,
                                DocumentSplitters.recursive(300, 50) //splits Document with chunks with 300 characters and with overlap 50 character in each chunk
                        )
                )
                .build();
    }

}
