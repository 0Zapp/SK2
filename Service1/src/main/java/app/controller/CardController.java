package app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.forms.RegistrationForm;

@RestController
@RequestMapping("/card")
public class CardController {

	@PostMapping("/addNew")
	public ResponseEntity<String> register(@RequestBody RegistrationForm registrationForm) {

		try {

			// iscitavamo entitet iz registracione forme
			//User user = new User(registrationForm.getIme(), registrationForm.getPrezime(), registrationForm.getEmail(),
			//		registrationForm.getPassportNumber(), encoder.encode(registrationForm.getPassword()));

			// cuvamo u nasoj bazi ovaj entitet
			//userRepo.saveAndFlush(user);

			return new ResponseEntity<>("success", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
	
}
