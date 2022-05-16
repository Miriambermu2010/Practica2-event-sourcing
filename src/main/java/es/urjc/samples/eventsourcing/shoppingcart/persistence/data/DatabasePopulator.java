package es.urjc.samples.eventsourcing.shoppingcart.persistence.data;

import com.github.javafaker.Faker;

import es.urjc.samples.eventsourcing.shoppingcart.persistence.CustomerEntity;
import es.urjc.samples.eventsourcing.shoppingcart.persistence.Product;
import es.urjc.samples.eventsourcing.shoppingcart.query.customer.CustomerHandler;
import es.urjc.samples.eventsourcing.shoppingcart.query.customer.GetAllCustomerQuery;
import es.urjc.samples.eventsourcing.shoppingcart.rest.CustomerDto;
import es.urjc.samples.eventsourcing.shoppingcart.rest.CustomerRestController;
import es.urjc.samples.eventsourcing.shoppingcart.rest.ProductRestController;
import es.urjc.samples.eventsourcing.shoppingcart.rest.ShoppingCartController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Component
public class DatabasePopulator {

    private final CustomerRestController customerController;
    private final ProductRestController productController;
    private final ShoppingCartController cartController;
    private final CustomerHandler customerHandler;

    public DatabasePopulator(CustomerRestController customerController, 
                    ProductRestController productController, 
                    ShoppingCartController cartController,
                    CustomerHandler customerHandler) {
        this.customerController = customerController;
        this.productController = productController;
        this.cartController = cartController;
        this.customerHandler = customerHandler;
    }

    @PostConstruct
    public void simulateHistory() {
        createCustomers();
        createProducts(50);
        simulateCarts(1, 10, 8);
    }

    private void createCustomers() {
        Stream.of(
                new CustomerDto("dgomezg", "David Gómez G.", "Unknown"),
                new CustomerDto("patxi", "Patxi Gortazar", "Unknown"),
                new CustomerDto("mica", "Micael Gallego", "Unknown")
        ).forEach(customerController::createCustomer);
    }

    private void createProducts(int limit) {
        Faker faker = new Faker();
        AtomicInteger count = new AtomicInteger(1);
        Stream.generate(
                () -> new Product(
                        "Product-" + count.incrementAndGet(),
                        faker.commerce().productName(),
                        StringUtils.truncate(faker.hitchhikersGuideToTheGalaxy().quote(), 250),
                        new BigDecimal(ThreadLocalRandom.current().nextDouble(5,150)))
            ).limit(limit)
            .forEach(productController::addProduct);
    }

    private void simulateCarts(int cartsPerCustomer, int additionsPerCart, int removalsPerCart) {

        for(CustomerEntity customer: customerHandler.handleListQuery(new GetAllCustomerQuery())) {
            for (int i = 0; i < cartsPerCustomer; i++) {
                String cartId = customerController.createCart(customer.getCustomerId());
                List<String> productIds = new ArrayList<>();

                for (int j = 0; j < additionsPerCart; j++) {
                    String productId = getRandomProductId();
                    productIds.add(productId);
                    cartController.addItem(cartId, productId, ThreadLocalRandom.current().nextInt(1, 4));
                    System.out.println("Added " + productId + " to " + cartId);
                }

                ThreadLocalRandom.current().ints(removalsPerCart, 0, productIds.size() -1)
                        .forEach(n -> {
                            System.out.println("Removing product " + productIds.get(n) + " from " + cartId);
                            try {
                            cartController.removeItem(cartId, productIds.get(n), 0);
                            } catch (ResponseStatusException e) {
                            	// We just ignore that the element doesn't exist
                            }
                        });


            }
        }
    }

    private String getRandomProductId() {
        return "Product-" + ThreadLocalRandom.current().nextInt(1,50);
    }

}
