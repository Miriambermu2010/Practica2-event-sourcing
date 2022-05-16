package es.urjc.samples.eventsourcing.shoppingcart.rest;

import es.urjc.samples.eventsourcing.shoppingcart.persistence.Product;
import es.urjc.samples.eventsourcing.shoppingcart.persistence.ProductRepository;
import es.urjc.samples.eventsourcing.shoppingcart.query.products.AddProductCommand;
import es.urjc.samples.eventsourcing.shoppingcart.query.products.GetAllProductQuery;
import es.urjc.samples.eventsourcing.shoppingcart.query.products.GetProductQuery;

import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/products")
public class ProductRestController {


    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;
    private final ProductRepository repository;

    public ProductRestController(ProductRepository repository, CommandGateway commandGateway, QueryGateway queryGateway) {
        this.repository = repository;
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }



    @PostMapping
    public CompletableFuture<Void> addProduct(@RequestBody Product product) {

        return commandGateway.send(new AddProductCommand(product.getProductId()));

    }


    @GetMapping
    public CompletableFuture<List<Product>> listProducts(){

        return queryGateway.query(new GetAllProductQuery(), ResponseTypes.multipleInstancesOf(Product.class));
    }

    @GetMapping("/{productId}")
    public CompletableFuture<Product> getProduct(@PathVariable String productId) {
        return queryGateway.query(new GetProductQuery(productId), ResponseTypes.instanceOf(Product.class));
    }


}
