package app.controller;

import static app.security.SecurityConstants.HEADER_STRING;
import static app.security.SecurityConstants.SECRET;
import static app.security.SecurityConstants.TOKEN_PREFIX;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import app.entities.User;
import app.forms.RegistrationForm;
import app.forms.UserInfo_Form;
import app.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

	private BCryptPasswordEncoder encoder;
	private UserRepository userRepo;

	@Autowired
	public UserController(BCryptPasswordEncoder encoder, UserRepository userRepo) {
		this.encoder = encoder;
		this.userRepo = userRepo;
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegistrationForm registrationForm) {

		try {
			User user = userRepo.findByEmail(registrationForm.getEmail());
			if (user == null) {
				user = new User(registrationForm.getName(), registrationForm.getSurname(), registrationForm.getEmail(),
						registrationForm.getPassportNumber(), encoder.encode(registrationForm.getPassword()));

				sendEmail(registrationForm.getEmail());

				userRepo.saveAndFlush(user);

				return new ResponseEntity<>("success", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@PatchMapping("/update")
	public ResponseEntity<String> update(@RequestBody RegistrationForm registrationForm,
			@RequestHeader(value = HEADER_STRING) String token) {

		try {

			String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
					.verify(token.replace(TOKEN_PREFIX, "")).getSubject();

			User user = userRepo.findByEmail(email);
			user.setName(registrationForm.getName());
			user.setSurrname(registrationForm.getSurname());

			if (!user.getEmail().equals(registrationForm.getEmail())) {
				sendEmail(registrationForm.getEmail());
			}

			user.setEmail(registrationForm.getEmail());
			user.setPassportNumber(registrationForm.getPassportNumber());
			user.setPassword(registrationForm.getPassword());

			userRepo.saveAndFlush(user);

			return new ResponseEntity<>("success", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/setup")
	public ResponseEntity<String> setup() {
		try {

			User admin = userRepo.findByEmail("admin@admin.com");

			if (admin == null) {

				admin = new User("admin", "admin", "admin@admin.com", 0, encoder.encode("admin"));
				admin.setAdmin(true);
				userRepo.saveAndFlush(admin);

			}

			return new ResponseEntity<>("success", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/isAdmin")
	public ResponseEntity<Integer> isAdmin(@RequestHeader(value = HEADER_STRING) String token) {
		try {

			String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
					.verify(token.replace(TOKEN_PREFIX, "")).getSubject();

			User user = userRepo.findByEmail(email);

			if (user != null && user.isAdmin()) {
				return new ResponseEntity<>(1, HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>(-1, HttpStatus.ACCEPTED);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(-1, HttpStatus.BAD_REQUEST);
		}
	}

	public void sendEmail(String email) {
		System.out.println("Sending email to:" + email);

		Random rand = new Random();

		for (int i = 0; i < rand.nextInt(10); i++) {
			try {
				Thread.sleep(1000);
				System.out.println("Sending...");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println("Email sent to:" + email);
	}

}
