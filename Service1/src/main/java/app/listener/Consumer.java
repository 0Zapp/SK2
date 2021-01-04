package app.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

	@JmsListener(destination = "user.queue")
	public void consume(Long flightID) {

		System.out.println("vrati pare svim korisnicima koji imaju ticket na " + flightID);

	}

}
