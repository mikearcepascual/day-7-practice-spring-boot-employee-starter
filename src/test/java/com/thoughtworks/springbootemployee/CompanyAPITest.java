package com.thoughtworks.springbootemployee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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
    @Test
    void should_return_company_when_perform_post_companies_given_a_new_company_with_JSON_format() throws Exception{
        //given

        Company newCompany = new Company("Google");
        //when, then
        mockMvcClient.perform(MockMvcRequestBuilders.post("/companies/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newCompany)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.companyId").value(notNullValue()))
                .andExpect(jsonPath("$.companyName").value("Google"));

    }

    @Test
    void should_return_company_when_perform_put_companies_given_an_company_id() throws Exception {
        //given
        Company company = companyRepository.addCompany(new Company("Google"));
        Company newCompany = new Company("Alphabet");
        //when, then
        mockMvcClient.perform(MockMvcRequestBuilders.put("/companies/" + company.getCompanyId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newCompany)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName").value(newCompany.getCompanyName()));
    }

    @Test
    void should_return_204_no_content_when_perform_delete_employee_given_an_employee_id() throws Exception{
        //given
        Company google = companyRepository.addCompany(new Company("Google"));
        //when, then
        mockMvcClient.perform(MockMvcRequestBuilders.delete("/companies/" + google.getCompanyId()))
                .andExpect(status().isNoContent());
    }
    @Test
    void should_return_list_of_employees_when_get_employees_given_pageNumber_and_pageSize() throws Exception {
        //given
        long pageNumber = 1L;
        long pageSize = 2L;
        Company google = companyRepository.addCompany(new Company("Google"));
        Company facebook = companyRepository.addCompany(new Company("Facebook"));
        MultiValueMap<String,String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("pageNumber", Long.toString(pageNumber));
        paramsMap.add("pageSize", Long.toString(pageSize));

        //when
        mockMvcClient.perform(MockMvcRequestBuilders.get("/companies/").params(paramsMap))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].companyName").value(google.getCompanyName()))
                .andExpect(jsonPath("$[1].companyName").value(facebook.getCompanyName()));

        //then
    }
}
