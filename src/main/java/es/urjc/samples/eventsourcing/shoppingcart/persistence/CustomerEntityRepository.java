package es.urjc.samples.eventsourcing.shoppingcart.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerEntityRepository extends JpaRepository<CustomerEntity, String> {
}
