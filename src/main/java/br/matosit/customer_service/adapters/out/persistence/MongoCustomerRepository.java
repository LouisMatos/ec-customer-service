package br.matosit.customer_service.adapters.out.persistence;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoCustomerRepository extends MongoRepository<CustomerDocument, String> {

  Optional<CustomerDocument> findByEmail(String email);
}
