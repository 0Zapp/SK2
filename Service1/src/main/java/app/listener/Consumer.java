package app.listener;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import app.entities.User;
import app.repository.UserRepository;
import app.utils.UtilsMethods;

@Component
public class Consumer {

	private UserRepository userRepo;

	@Autowired
	public Consumer(UserRepository userRepo) {

		this.userRepo = userRepo;
	}

	@JmsListener(destination = "user.queue")
	public void consume(Long flightID) {

		System.out.println("vrati pare svim korisnicima koji imaju ticket na " + flightID);

		ResponseEntity<Integer> response = UtilsMethods.sendGet("http://localhost:8080/findUserIDs/" + flightID);
		ArrayList<Long> userIDs = new ArrayList<>();
		for (long id : userIDs) {
			User user = userRepo.findById(id);

			String Subject = "Your filght has been canceled";
			String Text = "Your flight was just cancelled";

			// Mail.sendEmail(user.getEmail(), Subject, Text);

		}

	}

}
