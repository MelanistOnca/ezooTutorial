package com.examples.ezoo.dao;

import java.util.List;

import com.examples.ezoo.model.Animal;
//import com.examples.ezoo.model.FeedingSchedule;

/**
 * Main interface used to execute CRUD methods on Animal class
 * @author anon
 *
 */
public interface AnimalDAO {

	/**
	 * Used to retrieve a list of all Animals 
	 * @return
	 */
	List<Animal> getAllAnimals();

	/**
	 * Used to persist the animal to the datastore
	 * @param animalToSave
	 */
	void saveAnimal(Animal animalToSave) throws Exception;
	

	/**
	 * Used to get an Animal from the datastore by providing an animal_id
	 * @param animalToSave
	 */
	Animal getAnimalByID(long animal_id) throws Exception;
	
	
}
