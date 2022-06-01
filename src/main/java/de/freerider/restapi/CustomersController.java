package de.freerider.restapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

@RestController
public class CustomersController implements CustomersAPI {
	@Autowired
	private final ObjectMapper objectMapper;
	//
	private final HttpServletRequest request;

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
		System.out.println("+++++++ Jetzt in Customer mit der ID: " + id + "++++++++");
		ArrayNode arrayNode = customersAsJSON();

			if (arrayNode.size() < id) {
				return new ResponseEntity<JsonNode>(HttpStatus.NOT_FOUND);
			}
			if (!arrayNode.isEmpty()) {
				for (int i = 0; i < arrayNode.size(); i++) {
					JsonNode jsonObject = arrayNode.get(i);
					System.out.println("#### DAS IST ARRAYNODE #######: " + arrayNode.get(i));
					System.out.println("Das ist JSONOBJECT>GET : " + jsonObject.get("id"));
					long num = jsonObject.get("id").asLong();
					System.out.println("### DAS IST NUM: " + num);
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
	class Person {
		String firstName = "";
		String lastName = "";
		long id = 0;
		final List<String> contacts = new ArrayList<String>();

		Person setName(String firstName, String lastName) {
			this.firstName = firstName;
			this.lastName = lastName;
			return this;
		}

		Person addContact(String contact) {
			this.contacts.add(contact);
			return this;
		}

		Person setId(long id) {
			this.id = id;
			return this;
		}
	}

	private final Person eric = new Person()
			.setName("Eric", "Meyer")
			.addContact("eric98@yahoo.com")
			.addContact("(030) 3945-642298")
			.setId(1);
	//
	private final Person anne = new Person()
			.setName("Anne", "Bayer")
			.addContact("anne24@yahoo.de")
			.addContact("(030) 3481-23352")
			.setId(2);
	//
	private final Person tim = new Person()
			.setName("Tim", "Schulz-Mueller")
			.addContact("tim2346@gmx.de")
			.setId(3);

	private final List<Person> people = Arrays.asList(eric, anne, tim);

	private ArrayNode customersAsJSON() {
		//
		ArrayNode arrayNode = objectMapper.createArrayNode();
		//
		people.forEach(c -> {
			StringBuffer sb = new StringBuffer();
			c.contacts.forEach(contact -> sb.append(sb.length() == 0 ? "" : "; ").append(contact));
			arrayNode.add(
					objectMapper.createObjectNode()
							.put("name", c.lastName)
							.put("first", c.firstName)
							.put("id", c.id)
							.put("contacts", sb.toString()));
		});
		return arrayNode;
	}
}
