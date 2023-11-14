package com.rental.repository.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rental.entity.Rental;
import com.rental.repository.RentalRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RentalRepositoryHelper {

	@Autowired
	private RentalRepository rentalRepository;
	
	public List<Rental> findAllRentals(){
		return rentalRepository.findAll();
	}
	
	public Optional<Rental> findByRentalId(String rentalId){
		return rentalRepository.findByRentalId(rentalId);
	}
	
	public Optional<Rental> findByUserId(String userId){
		return rentalRepository.findByUserId(userId);
	}

	public Rental saveRental(Rental rental) {
		return rentalRepository.save(rental);
	}
	
	public void deleteRental(Rental rental) {
		 rentalRepository.delete(rental);
	}
}