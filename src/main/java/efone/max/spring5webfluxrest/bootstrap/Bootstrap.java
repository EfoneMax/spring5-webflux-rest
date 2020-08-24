package efone.max.spring5webfluxrest.bootstrap;

import efone.max.spring5webfluxrest.domain.Category;
import efone.max.spring5webfluxrest.domain.Vendor;
import efone.max.spring5webfluxrest.repositories.CategoryRepository;
import efone.max.spring5webfluxrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    private final VendorRepository vendorRepository;
    private final CategoryRepository categoryRepository;

    public Bootstrap(VendorRepository vendorRepository, CategoryRepository categoryRepository) {
        this.vendorRepository = vendorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count().block() == 0) {
            //load data
            System.out.println("#### LOADING DATA ON BOOTSTRAP #####");

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

            System.out.println("Loaded Categories: " + categoryRepository.count().block());

            Vendor firstVendor = Vendor.builder().firstname("firstname_firstVendor").lastname("lastname_firstVendor").build();
            Vendor secondVendor = Vendor.builder().firstname("firstname_secondVendor").lastname("lastname_secondVendor").build();
            Vendor thirdVendor = Vendor.builder().firstname("firstname_thirdVendor").lastname("lastname_thirdVendor").build();

            vendorRepository.save(firstVendor).block();
            vendorRepository.save(secondVendor).block();
            vendorRepository.save(thirdVendor).block();

            System.out.println("Loaded Vendors: " + vendorRepository.count().block());
        }
    }
}
