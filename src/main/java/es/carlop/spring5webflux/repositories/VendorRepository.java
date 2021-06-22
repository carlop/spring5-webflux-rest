package es.carlop.spring5webflux.repositories;

import es.carlop.spring5webflux.domain.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {}
