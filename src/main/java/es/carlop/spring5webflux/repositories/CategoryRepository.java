package es.carlop.spring5webflux.repositories;

import es.carlop.spring5webflux.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {}
