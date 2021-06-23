package es.carlop.spring5webflux.controllers;

import es.carlop.spring5webflux.config.BaseURLs;
import es.carlop.spring5webflux.domain.Category;
import es.carlop.spring5webflux.repositories.CategoryRepository;
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
import static org.mockito.ArgumentMatchers.anyString;

class CategoryControllerTest {

    WebTestClient webTestClient;
    CategoryRepository categoryRepository;
    CategoryController categoryController;

    @BeforeEach
    void setUp() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    void getAllCategories() {
        BDDMockito.given(categoryRepository.findAll())
                .willReturn(Flux.just(Category.builder().description("Cat1").build(),
                        Category.builder().description("Cat2").build()));

        webTestClient.get()
                .uri(BaseURLs.CATEGORIES_URL)
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    void getCategoryById() {
        BDDMockito.given(categoryRepository.findById("someid"))
                .willReturn(Mono.just(Category.builder().description("Fruit").build()));

        webTestClient.get()
                .uri(BaseURLs.CATEGORIES_URL + "/someid")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    void createCategory() {
        BDDMockito.given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Category.builder().description("Desc").build()));

        Mono<Category> categoryToSaveMono = Mono.just(Category.builder().description("Desc").build());

        webTestClient.post()
                .uri(BaseURLs.CATEGORIES_URL)
                .body(categoryToSaveMono, Category.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void updateCategory() {
        BDDMockito.given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().description("Desc").build()));

        Mono<Category> categoryToUpdateMono = Mono.just(Category.builder().description("Desc").build());

        webTestClient.put()
                .uri(BaseURLs.CATEGORIES_URL + "/someid")
                .body(categoryToUpdateMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void patchCategory() {
        BDDMockito.given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().build()));

        BDDMockito.given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> categoryToPatchMono = Mono.just(Category.builder().description("Desc").build());

        webTestClient.patch()
                .uri(BaseURLs.CATEGORIES_URL + "/someid")
                .body(categoryToPatchMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();

        BDDMockito.verify(categoryRepository).save(any());
    }

    @Test
    void patchCategoryNoChanges() {
        BDDMockito.given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().build()));

        BDDMockito.given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> categoryToPatchMono = Mono.just(Category.builder().build());

        webTestClient.patch()
                .uri(BaseURLs.CATEGORIES_URL + "/someid")
                .body(categoryToPatchMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();

        BDDMockito.verify(categoryRepository, Mockito.never()).save(any());
    }
}
