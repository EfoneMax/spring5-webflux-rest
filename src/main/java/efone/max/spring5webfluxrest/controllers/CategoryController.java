package efone.max.spring5webfluxrest.controllers;

import efone.max.spring5webfluxrest.domain.Category;
import efone.max.spring5webfluxrest.repositories.CategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(CategoryController.URL)
public class CategoryController {
    static final String URL = "/api/v1/categories";
    private final CategoryRepository repository;

    public CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    Flux<Category> list() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    Mono<Category> getById(@PathVariable String id) {
        return repository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Mono<Void> createCategory(@RequestBody Publisher<Category> categoryPublish) {
        return repository.saveAll(categoryPublish).then();
    }
}
