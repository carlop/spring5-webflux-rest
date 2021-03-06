package es.carlop.spring5webflux.controllers;

import es.carlop.spring5webflux.config.BaseURLs;
import es.carlop.spring5webflux.domain.Category;
import es.carlop.spring5webflux.repositories.CategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(BaseURLs.CATEGORIES_URL)
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping({"", "/"})
    @ResponseStatus(HttpStatus.OK)
    Flux<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<Category> getCategoryById(@PathVariable String id) {
        return categoryRepository.findById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Void> createCategory(@RequestBody Publisher<Category> categoryStream) {
        return categoryRepository.saveAll(categoryStream).then();
    }

    @PutMapping ("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Category> updateCategory(@PathVariable String id, @RequestBody Category category) {
        category.setId(id);
        return categoryRepository.save(category);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Category> patchCategory(@PathVariable String id, @RequestBody Category category) {

        Category foundCategory = categoryRepository.findById(id).block();

        if (foundCategory.getDescription() != category.getDescription()) {
            foundCategory.setDescription(category.getDescription());
            return categoryRepository.save(foundCategory);
        }

        return Mono.just(foundCategory);
    }
}
