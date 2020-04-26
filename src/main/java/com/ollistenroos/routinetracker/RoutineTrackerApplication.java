package com.ollistenroos.routinetracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ollistenroos.routinetracker.domain.Entry;
import com.ollistenroos.routinetracker.domain.EntryRepository;
import com.ollistenroos.routinetracker.domain.EntryType;
import com.ollistenroos.routinetracker.domain.EntryTypeRepository;
import com.ollistenroos.routinetracker.domain.Routine;
import com.ollistenroos.routinetracker.domain.RoutineRepository;
import com.ollistenroos.routinetracker.domain.User;
import com.ollistenroos.routinetracker.domain.UserRepository;

@SpringBootApplication
public class RoutineTrackerApplication {
	
	private static final Logger log = LoggerFactory.getLogger(RoutineTrackerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RoutineTrackerApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner routineStart(RoutineRepository routineRepo, UserRepository userRepo, EntryRepository entryRepo, EntryTypeRepository entryTypeRepo) {
		return (args) -> {
			if (userRepo.findByUsername("admin") == null) {
				log.info("Create new users");
				//User user1 = new User("user", "$2a$10$prBUdL6vSjeVy.wfTbToxuZ8nQ/.uyJzop1FK204r7gyYGjOGEYb2", "USER");
				User user2 = new User("admin", "$2a$10$or9UI2zbhXieoYib.N86UO.4vgz9O//A8Oxwe/jzaq8TqiBcCUVqK", "ADMIN");
				//userRepo.save(user1);
				userRepo.save(user2);
			}
			/*
			log.info("Save a couple of routines");
			
			Routine routine1 = new Routine("Exercise", 10, "Place holder description for exercise", "Exercise type", user1.getUsername());
			Routine routine2 = new Routine("Meditation", 12, "Place holder description for exercise", "Meditation type", user1.getUsername());
			Routine routine3 = new Routine("Exercise", 16, "Place holder description for admin exercise", "Exercise type", user2.getUsername());
			
			routineRepo.save(routine1);
			routineRepo.save(routine2);
			routineRepo.save(routine3);
			
			EntryType entryType1 = new EntryType("German Fencing", routine1);
			EntryType entryType2 = new EntryType("Bolognese Fencing", routine1);
			EntryType entryType3 = new EntryType("Meditation", routine2);
			
			entryTypeRepo.save(entryType1);
			entryTypeRepo.save(entryType2);
			entryTypeRepo.save(entryType3);
			
			entryRepo.save(new Entry(90, "2020-04-20", 3, 1, "Testing", routine1, entryType1));
			entryRepo.save(new Entry(90, "2020-04-18", 3, 1, "Testing2", routine1, entryType2));
			entryRepo.save(new Entry(90, "2020-04-17", 2, 1, "Meditation test", routine2, entryType3));
			*/
		};
	}

}
