package br.com.bersch.controller;

import br.com.bersch.model.Book;
import br.com.bersch.proxy.CambioProxy;
import br.com.bersch.repository.BookRepository;
import br.com.bersch.response.Cambio;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Tag(name = "Book Endpoint")
@RequiredArgsConstructor
@RestController
@RequestMapping("/book-service")
public class BookController {

    private final Environment environment;

    private final BookRepository repository;

    private final CambioProxy proxy;

    @Operation(summary = "Find book by id and convert amount from cambio")
    @GetMapping(value = "/{id}/{currency}")
    @Retry(name = "default")
    public Book findBook(
            @PathVariable("id") Long id,
            @PathVariable("currency") String currency
    ) {

        Book book = repository.findById(id).orElseThrow(RuntimeException::new);
        if (book == null) throw new RuntimeException("Book not Found");

        Cambio cambio = proxy.getCambio(book.getPrice(), "USD", currency);

        var port = environment.getProperty("local.server.port");
        book.setEnvironment("Book port: " + port + " FEIGN \n Cambio port: " + cambio.getEnvironment());
        book.setPrice(cambio.getConvertedValue());
        return book;
    }
}
