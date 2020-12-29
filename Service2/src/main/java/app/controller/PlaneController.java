package app.controller;

import static app.security.SecurityConstants.HEADER_STRING;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.entities.Plane;
import app.forms.PlaneForm;
import app.repository.PlaneRepository;
import app.utils.UtilsMethods;

@RestController
@RequestMapping("/plane")
public class PlaneController {

	private PlaneRepository planeRepo;

	@Autowired
	public PlaneController(PlaneRepository planeRepo) {
		this.planeRepo = planeRepo;
	}

	@PostMapping("/addNew")
	public ResponseEntity<String> addNewPlane(@RequestBody PlaneForm planeForm,
			@RequestHeader(value = HEADER_STRING) String token) {
		try {

			ResponseEntity<Integer> response = UtilsMethods.sendGet("http://localhost:8080/user/isAdmin/", token);
			if (response.getBody() == 1) {

				Plane plane = new Plane(planeForm.getName(), planeForm.getCapacity());

				planeRepo.saveAndFlush(plane);

				return new ResponseEntity<String>("success", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/delete/{x}")
	public ResponseEntity<Long> deletePlane(@PathVariable Long x, @RequestHeader(value = HEADER_STRING) String token) {

		try {
			ResponseEntity<Integer> response = UtilsMethods.sendGet("http://localhost:8080/user/isAdmin/", token);
			if (response.getBody() == 1) {

				planeRepo.deleteById(x);
				return new ResponseEntity<Long>(x, HttpStatus.ACCEPTED);

			} else {
				return new ResponseEntity<Long>(HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}

	}

}
