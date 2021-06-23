package es.carlop.spring5webflux.controllers;

import es.carlop.spring5webflux.config.BaseURLs;
import es.carlop.spring5webflux.domain.Vendor;
import es.carlop.spring5webflux.repositories.VendorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(BaseURLs.VENDORS_URL)
public class VendorController {

    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping({"", "/"})
    @ResponseStatus(HttpStatus.OK)
    Flux<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<Vendor> getVendorById(@PathVariable String id) {
        return vendorRepository.findById(id);
    }
}
