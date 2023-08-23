package com.thoughtworks.springbootemployee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CompanyAPITest {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private MockMvc mockMvcClient;

    @BeforeEach
    void cleanUpCompanyData(){
        companyRepository.cleanAll();
    }
    @Test
    void should_return_all_given_all_companies_when_perform_get_companies() throws Exception{
        //given
        Company google = companyRepository.addCompany(new Company("Google"));

        //when, then
        mockMvcClient.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].companyId").value(google.getCompanyId()))
                .andExpect(jsonPath("$[0].companyName").value(google.getCompanyName()));

    }
    @Test
    void should_return_the_company_when_perform_get_companies_given_a_company_id() throws Exception{
        //given
        Company google = companyRepository.addCompany(new Company("Google"));
        //when
        mockMvcClient.perform(MockMvcRequestBuilders.get("/companies/" + google.getCompanyId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyId").value(google.getCompanyId()))
                .andExpect(jsonPath("$.companyName").value(google.getCompanyName()));
    }
    @Test
    void should_return_404_not_found_when_perform_get_companies_given_a_not_existing_id() throws Exception{
        //given
        long notExistingEmployeeId = 99L;
        //when
        mockMvcClient.perform(MockMvcRequestBuilders.get("/companies/" + notExistingEmployeeId))
                .andExpect(status().isNotFound());
        //then
    }
//    @Test
//    void should_return_the_employees_by_given_gender_when_perform_get_employees() throws Exception{
//        //given
//        Employee alice = employeeRepository.addEmployee(
//                new Employee("Alice",24,"Female",9000,1L));
//        employeeRepository.addEmployee(new Employee("Bob",28,"Male",8000,2L));
//        //when, then
//        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees").param("gender","Female"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].id").value(alice.getId()))
//                .andExpect(jsonPath("$[0].name").value(alice.getName()))
//                .andExpect(jsonPath("$[0].age").value(alice.getAge()))
//                .andExpect(jsonPath("$[0].gender").value(alice.getGender()))
//                .andExpect(jsonPath("$[0].salary").value(alice.getSalary()))
//                .andExpect(jsonPath("$[0].companyId").value(alice.getCompanyId()));
//    }
//
//    @Test
//    void should_return_employee_when_perform_post_employees_given_a_new_employee_with_JSON_format() throws Exception{
//        //given
//        Employee newEmployee = new Employee("Alice",23,"Female",9000,1L);
//        newEmployee.setEmployeeActive(Boolean.TRUE);
//        //when, then
//        mockMvcClient.perform(MockMvcRequestBuilders.post("/employees/")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(newEmployee)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(notNullValue()))
//                .andExpect(jsonPath("$.name").value("Alice"))
//                .andExpect(jsonPath("$.age").value(23))
//                .andExpect(jsonPath("$.gender").value("Female"))
//                .andExpect(jsonPath("$.salary").value(9000))
//                .andExpect(jsonPath("$.companyId").value(1));
//    }
//
//    @Test
//    void should_return_employee_when_perform_put_employees_given_an_employee_id() throws Exception {
//        //given
//        Employee alice = employeeRepository.addEmployee(
//                new Employee("Alice",23,"Female",9000,1L));
//        alice.setEmployeeActive(Boolean.TRUE);
//        Employee newAlice = new Employee("Alice",24,"Female",10000,1L);
//        newAlice.setEmployeeActive(Boolean.TRUE);
//        //when, then
//        mockMvcClient.perform(MockMvcRequestBuilders.put("/employees/" + alice.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(newAlice)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.age").value(newAlice.getAge()))
//                .andExpect(jsonPath("$.salary").value(newAlice.getSalary()));
//    }
//
//    @Test
//    void should_return_204_no_content_when_perform_delete_employee_given_an_employee_id() throws Exception{
//        //given
//        Employee alice = employeeRepository.addEmployee(
//                new Employee("Alice",23,"Female",9000,1L));
//        //when, then
//        mockMvcClient.perform(MockMvcRequestBuilders.delete("/employees/" + alice.getId()))
//                .andExpect(status().isNoContent());
//    }
}
