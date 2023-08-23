package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SpringBootEmployeeApplicationTests {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private MockMvc mockMvcClient;

	@BeforeEach
	void cleanUpEmployeeData(){
		employeeRepository.cleanAll();
	}
	@Test
	void should_return_all_given_all_employees_when_perform_get_employees() throws Exception{
		//given
		Employee alice = employeeRepository.addEmployee(
				new Employee("Alice",24,"Female",9000,1L));

		//when, then
		mockMvcClient.perform(MockMvcRequestBuilders.get("/employees"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id").value(alice.getId()))
				.andExpect(jsonPath("$[0].name").value(alice.getName()))
				.andExpect(jsonPath("$[0].age").value(alice.getAge()))
				.andExpect(jsonPath("$[0].gender").value(alice.getGender()))
				.andExpect(jsonPath("$[0].salary").value(alice.getSalary()))
				.andExpect(jsonPath("$[0].companyId").value(alice.getCompanyId()));
	}
}
