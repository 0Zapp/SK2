package app.controller;

import static app.security.SecurityConstants.HEADER_STRING;
import static app.security.SecurityConstants.SECRET;
import static app.security.SecurityConstants.TOKEN_PREFIX;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import app.entities.Card;
import app.entities.User;
import app.forms.CardForm;
import app.repository.CardRepository;
import app.repository.UserRepository;

@RestController
@RequestMapping("/card")
public class CardController {
	
	private UserRepository userRepo;
	private CardRepository cardRepo;
	
	
	@Autowired
	public CardController(UserRepository userRepo, CardRepository cardRepo) {

		this.userRepo = userRepo;
		this.cardRepo = cardRepo;
	}



	@PostMapping("/addNew")
	public ResponseEntity<String> register(@RequestBody CardForm cardForm,
			@RequestHeader(value = HEADER_STRING) String token) {

		try {

			String email = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
					.verify(token.replace(TOKEN_PREFIX, "")).getSubject();

			User user = userRepo.findByEmail(email);

			Card card = new Card(user.getName(), user.getSurrname(), cardForm.getCardNumber(),
					cardForm.getSecurityNumber(), user.getId());

			cardRepo.saveAndFlush(card);

			return new ResponseEntity<>("success", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

}
