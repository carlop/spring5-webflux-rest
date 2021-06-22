package es.carlop.spring5webflux.bootstrap;

import es.carlop.spring5webflux.domain.Category;
import es.carlop.spring5webflux.domain.Vendor;
import es.carlop.spring5webflux.repositories.CategoryRepository;
import es.carlop.spring5webflux.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository,
                     VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadVendors();
    }

    private void loadCategories() {
        System.out.println("### LOADING CATEGORIES DATA ###");

        categoryRepository.save(Category.builder()
                .description("Fruits").build()).block();

        categoryRepository.save(Category.builder()
                .description("Nuts").build()).block();

        categoryRepository.save(Category.builder()
                .description("Breads").build()).block();

        categoryRepository.save(Category.builder()
                .description("Meats").build()).block();

        categoryRepository.save(Category.builder()
                .description("Eggs").build()).block();

        System.out.println("Category Data loaded = "
                + categoryRepository.count().block());
    }

    private void loadVendors() {
        System.out.println("### LOADING VENDORS DATA ###");

        vendorRepository.save(Vendor.builder()
                .firstName("Jimmy").lastName("McGill").build()).block();

        vendorRepository.save(Vendor.builder()
                .firstName("Charles").lastName("McGill").build()).block();

        vendorRepository.save(Vendor.builder()
                .firstName("Kim").lastName("Wexler").build()).block();

        vendorRepository.save(Vendor.builder()
                .firstName("Mike").lastName("Ehrmantraut").build()).block();

        vendorRepository.save(Vendor.builder()
                .firstName("Nacho").lastName("Varga").build()).block();

        vendorRepository.save(Vendor.builder()
                .firstName("Gus").lastName("Fring").build()).block();

        vendorRepository.save(Vendor.builder()
                .firstName("Howard").lastName("Hamlin").build()).block();

        System.out.println("Vendor Data loaded = "
                + vendorRepository.count().block());
    }
}
