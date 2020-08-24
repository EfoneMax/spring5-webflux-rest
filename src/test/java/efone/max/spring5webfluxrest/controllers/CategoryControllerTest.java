package efone.max.spring5webfluxrest.controllers;

import efone.max.spring5webfluxrest.domain.Category;
import efone.max.spring5webfluxrest.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class CategoryControllerTest {

    WebTestClient webTestClient;
    CategoryRepository repository;
    CategoryController controller;

    @Before
    public void setUp() throws Exception {
        repository = Mockito.mock(CategoryRepository.class);
        controller = new CategoryController(repository);
        webTestClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    public void list() {
        BDDMockito.given(repository.findAll())
                .willReturn(Flux.just(Category.builder().description("Cat").build(),
                        Category.builder().description("Dog").build()));

        webTestClient.get().uri("/api/v1/categories")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    public void getById() {
        BDDMockito.given(repository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().description("Cat").build()));

        webTestClient.get().uri("/api/v1/categories/btw")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    public void testCreate() {
        BDDMockito.given(repository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Category.builder().build()));

        Mono<Category> catToSaveMono = Mono.just(Category.builder().description("Some cattt").build());

        webTestClient.post().uri(CategoryController.URL)
                .body(catToSaveMono, Category.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void testUpdate() {
        BDDMockito.given(repository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> catToSaveMono = Mono.just(Category.builder().description("Some cattt").build());

        webTestClient.put().uri(CategoryController.URL + "/112fds")
                .body(catToSaveMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();
    }
}