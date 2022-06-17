package de.freerider.restapi;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import de.freerider.datamodel.Customer;
import de.freerider.datamodel.Customer.Status;
import de.freerider.repository.CustomerRepository;

@RestController
public class CustomersController implements CustomersAPI {
	@Autowired
	private final ObjectMapper objectMapper;
	//
	private final HttpServletRequest request;
	@Autowired
	private CustomerRepository customerRepository;

	/**
	 * Constructor.
	 * 
	 * @param objectMapper entry point to JSON tree for the Jackson library
	 * @param request      HTTP request object
	 */
	public CustomersController(ObjectMapper objectMapper, HttpServletRequest request) {
		this.objectMapper = objectMapper;
		this.request = request;
	}

	/**
	 * GET /customers
	 * 
	 * Return JSON Array of customers (compact).
	 * 
	 * @return JSON Array of customers
	 */
	@Override
	public ResponseEntity<List<?>> getCustomers() {
		//
		ResponseEntity<List<?>> re = null;
		System.err.println(request.getMethod() + " " + request.getRequestURI());
		try {
			ArrayNode arrayNode = customersAsJSON();
			ObjectReader reader = objectMapper.readerFor(new TypeReference<List<ObjectNode>>() {
			});
			List<String> list = reader.readValue(arrayNode);
			//
			re = new ResponseEntity<List<?>>(list, HttpStatus.OK);

		} catch (IOException e) {
			re = new ResponseEntity<List<?>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return re;
	}

	/**
	 * GET /customers/{id}
	 * 
	 * Return JSON Array of customers (compact).
	 * 
	 * @return JSON Array of customers
	 */
	@Override
	public ResponseEntity<?> getCustomer(long id) {
		//
		ResponseEntity<?> re = null;
		System.err.println(request.getMethod() + " " + request.getRequestURI());
		ArrayNode arrayNode = customersAsJSON();
		if (customerRepository.count() < id) {
			return new ResponseEntity<JsonNode>(HttpStatus.NOT_FOUND);
		}
		if (!arrayNode.isEmpty()) {
			for (int i = 0; i < arrayNode.size(); i++) {
				JsonNode jsonObject = arrayNode.get(i);
				long num = jsonObject.get("id").asLong();
				if (num == id) {
					re = new ResponseEntity<JsonNode>(jsonObject, HttpStatus.OK);
					return re;
				}
			}
		}
		re = new ResponseEntity<JsonNode>(HttpStatus.NOT_FOUND);
		return re;
	}

	/*
	 * Quick Person class
	 */
	// class Person {
	// String firstName = "";
	// String lastName = "";
	// long id = 0;
	// final List<String> contacts = new ArrayList<String>();

	// Person setName(String firstName, String lastName) {
	// this.firstName = firstName;
	// this.lastName = lastName;
	// return this;
	// }

	// Person addContact(String contact) {
	// this.contacts.add(contact);
	// return this;
	// }

	// Person setId(long id) {
	// this.id = id;
	// return this;
	// }
	// }

	// private final Person eric = new Person()
	// .setName("Eric", "Meyer")
	// .addContact("eric98@yahoo.com")
	// .addContact("(030) 3945-642298")
	// .setId(1);
	// //
	// private final Person anne = new Person()
	// .setName("Anne", "Bayer")
	// .addContact("anne24@yahoo.de")
	// .addContact("(030) 3481-23352")
	// .setId(2);
	// //
	// private final Person tim = new Person()
	// .setName("Tim", "Schulz-Mueller")
	// .addContact("tim2346@gmx.de")
	// .setId(3);

	// private final List<Person> people = Arrays.asList(eric, anne, tim);

	private ArrayNode customersAsJSON() {
		//
		ArrayNode arrayNode = objectMapper.createArrayNode();
		//
		customerRepository.findAll().forEach(c -> {
			StringBuffer sb = new StringBuffer();
			c.getContacts().forEach(contact -> sb.append(sb.length() == 0 ? "" : "; ").append(contact));
			arrayNode.add(
					objectMapper.createObjectNode()
							.put("name", c.getLastName())
							.put("first", c.getFirstName())
							.put("id", c.getId())
							.put("contacts", sb.toString()));
		});
		return arrayNode;
	}

	@Override
	public ResponseEntity<List<?>> postCustomers(Map<String, Object>[] jsonMap) {
		System.err.println("POST /customers");
		if (jsonMap == null)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		//
		System.out.println("[{");
		boolean accept = false;
		Customer customer = new Customer();
		for (Map<String, Object> kvpairs : jsonMap) {
			kvpairs.keySet().forEach(key -> {
				Object value = kvpairs.get(key);
				System.out.println(" [ " + key + ", " + value + " ] ");
			});
			accept = false;
			System.out.println(accept(kvpairs).isEmpty());
			if (!accept(kvpairs).isEmpty()) {
				accept = true;
				customer = accept(kvpairs).get();

			}
		}
		System.out.println("}]");
		if (accept) {
			customerRepository.save(customer);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.CONFLICT);
		}
	}

	private Optional<Customer> accept(Map<String, Object> kvpairs) {
		if (kvpairs.get("id") == null) {
			kvpairs.put("id", customerRepository.count() + 1);
		}
		if (Integer.parseInt(kvpairs.get("id").toString()) < 1) {
			return Optional.empty();
		}

		if (kvpairs.get("name") == null || kvpairs.get("first") == null) {
			return Optional.empty();
		}

		Customer newCustomer = new Customer();
		newCustomer.setId(Long.parseLong(kvpairs.get("id").toString()));
		newCustomer.setName(kvpairs.get("first").toString(), kvpairs.get("name").toString());
		if (kvpairs.get("contacts") != null) {
			String[] contacts = kvpairs.get("contacts").toString().split(";");
			for (String contact : contacts) {
				newCustomer.addContact(contact);
			};
		}
		if (customerRepository.findById(newCustomer.getId()).isEmpty()) {
			newCustomer.setStatus(Status.Active);
			return Optional.of(newCustomer);
		} else {
			return Optional.empty();
		}

	}

	@Override
	public ResponseEntity<List<?>> putCustomers(Map<String, Object>[] jsonMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> deleteCustomer(long id) {
		if (customerRepository.existsById(id)) {
			System.err.println("DELETE /customers/" + id);
			Optional<Customer> toDelete = customerRepository.findById(id);
			customerRepository.deleteById(id);
			return new ResponseEntity<>(toDelete, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
}
