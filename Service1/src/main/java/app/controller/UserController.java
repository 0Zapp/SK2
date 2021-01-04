package app.controller;

import static app.security.SecurityConstants.HEADER_STRING;
import static app.security.SecurityConstants.SECRET;
import static app.security.SecurityConstants.TOKEN_PREFIX;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import app.entities.User;
import app.forms.RegistrationForm;
import app.mailing.Mail;
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

				String Subject = "Your have registered";
				String Text = "Your registered recently using this email";

				//Mail.sendEmail(registrationForm.getEmail(), Subject, Text);

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
			user.setSurname(registrationForm.getSurname());

			if (!user.getEmail().equals(registrationForm.getEmail())) {
				String Subject = "Your email was changed";
				String Text = "Your email was changed recently";

				//Mail.sendEmail(registrationForm.getEmail(), Subject, Text);
			}

			user.setEmail(registrationForm.getEmail());
			user.setPassportNumber(registrationForm.getPassportNumber());
			if (registrationForm.getPassword() != null)
				user.setPassword(encoder.encode(registrationForm.getPassword()));

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

	@GetMapping("/get")
	public ResponseEntity<User> getUser(@RequestHeader(value = HEADER_STRING) String token) {
		try {

			String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
					.verify(token.replace(TOKEN_PREFIX, "")).getSubject();

			User user = userRepo.findByEmail(email);
			return new ResponseEntity<>(user, HttpStatus.ACCEPTED);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/addMiles/{miles}")
	public ResponseEntity<String> addmiles(@PathVariable Integer miles,
			@RequestHeader(value = HEADER_STRING) String token) {
		try {

			String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
					.verify(token.replace(TOKEN_PREFIX, "")).getSubject();

			User user = userRepo.findByEmail(email);
			user.setMiles(user.getMiles() + miles);
			userRepo.saveAndFlush(user);
			return new ResponseEntity<>("success", HttpStatus.ACCEPTED);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/removeMiles/{userId}/{miles}")
	public ResponseEntity<String> addmiles(@PathVariable Long userId, @PathVariable Integer miles,
			@RequestHeader(value = HEADER_STRING) String token) {
		try {
			User user = userRepo.findById(userId).get();
			user.setMiles(user.getMiles() - miles);
			userRepo.saveAndFlush(user);
			return new ResponseEntity<>("success", HttpStatus.ACCEPTED);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
		}
	}

}
