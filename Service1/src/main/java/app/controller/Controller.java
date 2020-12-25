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
import app.forms.TwoNumbers;
import app.forms.UserInfo_Form;
import app.repository.UserRepository;
import app.utils.UtilsMethods;

@RestController
@RequestMapping("")
public class Controller {

	private BCryptPasswordEncoder encoder;
	private UserRepository userRepo;

	@Autowired
	public Controller(BCryptPasswordEncoder encoder, UserRepository userRepo) {
		this.encoder = encoder;
		this.userRepo = userRepo;
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegistrationForm registrationForm) {

		try {

			// iscitavamo entitet iz registracione forme
			User user = new User(registrationForm.getIme(), registrationForm.getPrezime(), registrationForm.getEmail(),
					registrationForm.getPassportNumber(), encoder.encode(registrationForm.getPassword()));

			// cuvamo u nasoj bazi ovaj entitet
			userRepo.saveAndFlush(user);

			return new ResponseEntity<>("success", HttpStatus.ACCEPTED);
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
			user.setIme(registrationForm.getIme());
			user.setPrezime(registrationForm.getPrezime());
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

	@GetMapping("/whoAmI")
	public ResponseEntity<UserInfo_Form> whoAmI(@RequestHeader(value = HEADER_STRING) String token) {
		try {

			// izvlacimo iz tokena subject koj je postavljen da bude email
			String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
					.verify(token.replace(TOKEN_PREFIX, "")).getSubject();

			User user = userRepo.findByEmail(email);

			return new ResponseEntity<>(new UserInfo_Form(user.getIme(), user.getPrezime()), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/addition/{x}/{y}")
	public ResponseEntity<Integer> addition(@PathVariable int x, @PathVariable int y) {
		try {

			ResponseEntity<Integer> response = UtilsMethods
					.sendGet("http://localhost:8081/calculate/addition/" + x + "/" + y);

			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/subtraction")
	public ResponseEntity<Integer> subtraction(@RequestBody TwoNumbers dvaBroja) {
		try {

			ResponseEntity<Integer> response = UtilsMethods.sendPost("http://localhost:8081/calculate/subtractionPost",
					dvaBroja);

			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
