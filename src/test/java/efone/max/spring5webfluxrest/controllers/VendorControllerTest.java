package efone.max.spring5webfluxrest.controllers;

import efone.max.spring5webfluxrest.domain.Vendor;
import efone.max.spring5webfluxrest.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class VendorControllerTest {
    @Mock
    VendorRepository repository;
    VendorController controller;

    WebTestClient webTestClient;

    @Before
    public void setUp() throws Exception {
        controller = new VendorController(repository);
        webTestClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    public void testListAll() {
        BDDMockito.given(repository.findAll()).willReturn(Flux.just(Vendor.builder().firstname("FirstFirstName").build(),
                Vendor.builder().firstname("SecondFirstNameBuild").build()));

        webTestClient.get().uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    public void testGetById() {
        BDDMockito.given(repository.findById(anyString())).willReturn(
                Mono.just(Vendor.builder().firstname("FirstFirstName").build()));

        webTestClient.get().uri("/api/v1/vendors/bla")
                .exchange()
                .expectBody(Vendor.class);
    }

    @Test
    public void testCreate() {
        Flux<Vendor> vendors = Flux.just(Vendor.builder().firstname("FirstFirstName").build(),
                Vendor.builder().firstname("SecondFirstName").build());

        BDDMockito.given(repository.saveAll(any(Publisher.class))).willReturn(
                vendors);

        webTestClient.post().uri(VendorController.URL)
                .body(vendors, Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void testUpdate() {
        Mono<Vendor> firstVendor = Mono.just(Vendor.builder().firstname("FirstFirstName").build());

        BDDMockito.given(repository.save(any(Vendor.class))).willReturn(
                firstVendor);

        webTestClient.put().uri(VendorController.URL + "/324234d")
                .body(firstVendor, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();
    }
}