package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.entities.Plane;
import app.forms.PlaneForm;
import app.repository.PlaneRepository;

@RestController
@RequestMapping("/plane")
public class PlaneController {

	private PlaneRepository planeRepo;

	@Autowired
	public PlaneController(PlaneRepository planeRepo) {
		this.planeRepo = planeRepo;
	}

	@PostMapping("/addNew")
	public ResponseEntity<String> addNewPlane(@RequestBody PlaneForm planeForm) {
		try {

			Plane plane = new Plane(planeForm.getName(), planeForm.getCapacity());

			planeRepo.saveAndFlush(plane);

			return new ResponseEntity<String>("success", HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}

}
