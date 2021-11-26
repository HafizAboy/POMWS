package io.trampoline.currencyconverter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import com.rabbitmq.api.ConversionController;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConversionController.class)
public class ConversionRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void convertValidInputReturnsConvertedAmountInJson() throws Exception {
        mockMvc.perform(get("/convert")
                                .param("from", "USD")
                                .param("to", "DOGE")
                                .param("amount", "20"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.currency", is("DOGE")))
               .andExpect(jsonPath("$.converted", is(43.0)))
               .andReturn();
    }
}