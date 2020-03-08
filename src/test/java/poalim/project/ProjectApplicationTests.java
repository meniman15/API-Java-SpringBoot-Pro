package poalim.project;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import poalim.project.controller.APIController;
import poalim.project.model.Address;
import poalim.project.model.Employee;
import poalim.project.model.GeneralDetails;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Sql(executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD,scripts="classpath:/dataForTests.sql")
class ProjectApplicationTests {

	MockMvc mockMvc;

	@InjectMocks
	@Autowired
	APIController apiController;

	@BeforeEach
	public void setup(){
		mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void postEmployee_receivePostedEmployee() throws Exception{
		GeneralDetails details = new GeneralDetails();
		details.setDetailsId(6);
		details.setName("another_employee");
		details.setAge(45);
		details.setGender(GeneralDetails.Gender.MALE);

		Address address = new Address();
		address.setAddressId(3);
		address.setCity("Tel-Aviv");
		address.setStreet("Hashmonaim");
		address.setApartmentNumber(80);
		Set<Address> addresses = new HashSet<>();
		addresses.add(address);

		Employee employee = new Employee();
		employee.setEmployeeId(2);
		employee.setDetails(details);
		employee.setAddresses(addresses);

		String json = mapToJson(employee);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/employee").accept(MediaType.APPLICATION_JSON)
				.content(json).contentType(MediaType.APPLICATION_JSON)).andReturn();

		//result is checked by employee object, because a link is added in POST operation, therefore- JSONs are not equal.
		Employee empRes = stringToEmployee(result.getResponse().getContentAsString());
		assertThat(empRes.getEmployeeId()).isEqualTo(employee.getEmployeeId());
		assertThat(empRes.getDetails()).isEqualTo(employee.getDetails());
		assertThat(empRes.getAddresses()).isEqualTo(employee.getAddresses());
	}

	@Test
	void getEmployees_receiveOneEmployee() throws Exception{
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/employees").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();
		assertThat(result.getResponse().getContentAsString()).isNotEmpty();
		List<Employee> employees = stringToEmployees(result.getResponse().getContentAsString());
		assertThat(employees.get(0).getDetails().getName()).isEqualTo("Bob_employee");

	}

	@Test
	void getEmployeeASpecificEmployee_receiveOneEmployee() throws Exception{
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/employee/1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();
		assertThat(result.getResponse().getContentAsString()).isNotEmpty();
		Employee employee = stringToEmployee(result.getResponse().getContentAsString());
		assertThat(employee.getDetails().getName()).isEqualTo("Bob_employee");
	}

	@Test
	void updateEmployeeASpecificEmployee_receiveAnUpdatedEmployee() throws Exception{
		GeneralDetails details = new GeneralDetails();
		details.setDetailsId(5);
		details.setName("first_employee");
		details.setAge(35);
		details.setGender(GeneralDetails.Gender.MALE);

		Address address = new Address();
		address.setAddressId(2);
		address.setCity("Tel-Aviv");
		address.setStreet("Hashmonaim");
		address.setApartmentNumber(2);
		Set<Address> addresses = new HashSet<>();
		addresses.add(address);

		Employee employee = new Employee();
		employee.setEmployeeId(1);
		employee.setDetails(details);
		employee.setAddresses(addresses);

		String json = mapToJson(employee);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/employee").content(json).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();
		assertThat(result.getResponse().getContentAsString()).isNotEmpty();
		Employee resEmployee = stringToEmployee(result.getResponse().getContentAsString());
		assertThat(resEmployee.getDetails().getName()).isEqualTo("first_employee");

		//verify that GET request with the given ID, gives the new object
		result = mockMvc.perform(MockMvcRequestBuilders.get("/employee/1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();
		resEmployee = stringToEmployee(result.getResponse().getContentAsString());
		assertThat(resEmployee.getDetails().getName()).isEqualTo("first_employee");
	}

	@Test
	void patchEmployeeASpecificEmployee_receiveAPatchedEmployee() throws Exception{
		GeneralDetails details = new GeneralDetails();
		details.setDetailsId(3);
		details.setName("first_employee");
		details.setAge(35);
		details.setGender(GeneralDetails.Gender.MALE);

		Address address = new Address();
		address.setAddressId(2);
		address.setCity("Tel-Aviv");
		address.setStreet("Hashmonaim");
		address.setApartmentNumber(2);
		Set<Address> addresses = new HashSet<>();
		addresses.add(address);

		Employee employee = new Employee();
		employee.setEmployeeId(1);
		employee.setDetails(details);
		employee.setAddresses(addresses);

		String json = mapToJson(employee);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/employee").content(json).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();
		assertThat(result.getResponse().getContentAsString()).isNotEmpty();
		Employee resEmployee = stringToEmployee(result.getResponse().getContentAsString());
		assertThat(resEmployee.getDetails().getName()).isEqualTo("first_employee");
		assertThat(resEmployee.getSpouse()).isNotNull();

		//verify that GET request with the given ID, gives the new object
		result = mockMvc.perform(MockMvcRequestBuilders.get("/employee/1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();
		resEmployee = stringToEmployee(result.getResponse().getContentAsString());
		assertThat(resEmployee.getDetails().getName()).isEqualTo("first_employee");
		assertThat(resEmployee.getSpouse()).isNotNull();
	}

	@Test
	void deleteEmployeeASpecificEmployee_receiveNoEmployees() throws Exception{
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/employee/1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();
		result = mockMvc.perform(MockMvcRequestBuilders.get("/employee/1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();
		assertThat(result.getResponse().getErrorMessage()).isEqualTo("employee {1} does not exist");
		result = mockMvc.perform(MockMvcRequestBuilders.get("/employees").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();
		assertThat(result.getResponse().getContentAsString()).isEqualTo("[]");
	}

	private String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	private Employee stringToEmployee(String str) throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(str,Employee.class);
	}

	private List<Employee> stringToEmployees(String str) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(str, objectMapper.getTypeFactory().constructCollectionType(List.class, Employee.class));
	}

}
