package app.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

	@JmsListener(destination = "ticket.queue")
	public void consume(Long flightID) {

		System.out.println("canceluj sve karte sa flightIdijem:" + flightID);

	}

}
