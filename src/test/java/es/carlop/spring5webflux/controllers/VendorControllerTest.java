package es.carlop.spring5webflux.controllers;

import es.carlop.spring5webflux.config.BaseURLs;
import es.carlop.spring5webflux.domain.Vendor;
import es.carlop.spring5webflux.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class VendorControllerTest {

    WebTestClient webTestClient;
    VendorRepository vendorRepository;
    VendorController vendorController;

    @BeforeEach
    void setUp() {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    void getAllVendors() {
        BDDMockito.given(vendorRepository.findAll())
                .willReturn(Flux.just(Vendor.builder().firstName("Name 1").lastName("LastName 1").build(),
                        Vendor.builder().firstName("Name 2").lastName("Last name 2").build()));

        webTestClient.get()
                .uri(BaseURLs.VENDORS_URL)
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    void getVendorById() {
        BDDMockito.given(vendorRepository.findById("someid"))
                .willReturn(Mono.just(Vendor.builder().firstName("Name").lastName("Last name").build()));

        webTestClient.get()
                .uri(BaseURLs.VENDORS_URL + "/someid")
                .exchange()
                .expectBody(Vendor.class);
    }


    @Test
    void createVendor() {
        BDDMockito.given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().firstName("Name").lastName("Last name").build()));

        Mono<Vendor> vendorToSaveMono = Mono.just(Vendor.builder().firstName("Name").build());

        webTestClient.post()
                .uri(BaseURLs.VENDORS_URL)
                .body(vendorToSaveMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void updateVendor() {
        BDDMockito.given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().firstName("Name").lastName("Last name").build()));

        Mono<Vendor> vendorToUpdateMono = Mono.just(Vendor.builder().firstName("Desc").lastName("Last name").build());

        webTestClient.put()
                .uri(BaseURLs.VENDORS_URL + "/someid")
                .body(vendorToUpdateMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();
    }
}
