package com.bookstore.ai.controller;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/bookstore")
public class BookstoreAssistentController {

    @Autowired
    private OpenAiChatClient chatClient;


    // metodo com retorno do chat, contendo diversas infos que podem ser tratadas de formas especificas
    @GetMapping("/info/chat")
    public ChatResponse bookstoreChat(@RequestParam(value = "message",
            defaultValue = "Quais são os livros best sellers do ultimos tempos?") String message) {
        return chatClient.call(new Prompt(message));
    }

    //     metodo retorno uma spring como message
    @GetMapping("/informations")
    public String bookstoreChatString(@RequestParam(value = "message",
            defaultValue = "Quais são os livros best sellers do ultimos tempos?") String message) {
        return chatClient.call(message);
    }

    // metodo com um prompt pronto para retorno do conteudo
    @GetMapping("/reviews")
    public String bookstoreReviews(@RequestParam(value = "book",
            defaultValue = "A hora da estrela") String book) {
        PromptTemplate template = new PromptTemplate("""
                Por favor me forneça
                um breve resumo do {book}
                e também a biografia de seu autor.
                """);
        template.add(book, "book");
        return this.chatClient.call(template.create()).getResult().getOutput().getContent();
    }

    // metodo com retorno tipo flux
    @GetMapping("/stream/informations")
    public Flux<String> bookstoreChatStream(@RequestParam(value = "message",
            defaultValue = "Quais são os livros best sellers do ultimos tempos?") String message) {
        return chatClient.stream(message);
    }

}
