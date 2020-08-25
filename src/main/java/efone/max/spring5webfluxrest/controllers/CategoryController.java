package efone.max.spring5webfluxrest.controllers;

import efone.max.spring5webfluxrest.domain.Category;
import efone.max.spring5webfluxrest.repositories.CategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    Mono<Void> create(@RequestBody Publisher<Category> categoryPublish) {
        return repository.saveAll(categoryPublish).then();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("{id}")
    Mono<Category> update(@PathVariable String id, @RequestBody Category category) {
        category.setId(id);
        return repository.save(category);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("{id}")
    Mono<Category> patch(@PathVariable String id, @RequestBody Category category) {
        Category foundCategory = repository.findById(id).block();
        category.setId(id);
        if (foundCategory != null && !foundCategory.equals(category)) {
            foundCategory.setDescription(category.getDescription());
            return repository.save(foundCategory);
        }
        return Mono.just(foundCategory);
    }
}
