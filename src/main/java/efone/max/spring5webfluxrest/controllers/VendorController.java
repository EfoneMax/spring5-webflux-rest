package efone.max.spring5webfluxrest.controllers;

import efone.max.spring5webfluxrest.domain.Vendor;
import efone.max.spring5webfluxrest.repositories.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping(VendorController.URL)
public class VendorController {
    public static final String URL = "/api/v1/vendors";
    private final VendorRepository repository;

    public VendorController(VendorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Flux<Vendor> listAll() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    public Mono<Vendor> getById(@PathVariable String id) {
        return repository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Mono<Void> create(@RequestBody Publisher<Vendor> vendorPublisher) {
        return repository.saveAll(vendorPublisher).then();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("{id}")
    Mono<Vendor> update(@PathVariable String id, @RequestBody Vendor vendor) {
        vendor.setId(id);
        return repository.save(vendor);
    }
}
