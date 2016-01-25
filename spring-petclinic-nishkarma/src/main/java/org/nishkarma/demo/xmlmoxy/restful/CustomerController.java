package org.nishkarma.demo.xmlmoxy.restful;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.nishkarma.demo.xmlmoxy.model.Address;
import org.nishkarma.demo.xmlmoxy.model.Customer;
import org.nishkarma.demo.xmlmoxy.model.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Path("/customer")
@Controller
public class CustomerController {

	Logger logger = LoggerFactory.getLogger(CustomerController.class);

	private static Customer customer = createInitialCustomer();

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Customer getCustomer() {
		return customer;
	}

	@PUT
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public void setCustomer(Customer c) {
		if (c == null) {
			logger.debug("[Nishkarma]-setCustomer= c is null");
		}

		logger.debug("[Nishkarma]-setCustomer=" + c.getName());
		customer = c;
	}

	private static Customer createInitialCustomer() {
		Customer result = new Customer();

		result.setName("Jane Doe");
		result.setAddress(new Address("123 Any Street", "My Town"));
		result.getPhoneNumbers().add(new PhoneNumber("work", "613-555-1111"));
		result.getPhoneNumbers().add(new PhoneNumber("cell", "613-555-2222"));

		return result;
	}
}
