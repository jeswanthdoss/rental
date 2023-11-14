package com.rental.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.rental.entity.Rental;

public interface RentalRepository extends CrudRepository<Rental, Integer> {
	
	List<Rental> findAll();
	
	Optional<Rental> findByRentalId(String rentalId);
	
	Optional<Rental> findByUserId(String userId);
	
}
