package learn.manish.creditCard.ctrl;

import learn.manish.creditCard.exceptions.CCInvalidException;
import learn.manish.creditCard.exceptions.CCNotFoundException;
import learn.manish.creditCard.exceptions.GlobalExceptionHandler;
import learn.manish.creditCard.model.CreditCard;
import learn.manish.creditCard.service.CCService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CCValidationControllerTest {

    private static final String CARD_NUMBER = "1234567890123456";

    @Mock
    private CCService ccService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new CCValidationController(ccService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void validateCCReturnsValidatedCard() throws Exception {
        CreditCard card = new CreditCard();
        card.setCcNumber(CARD_NUMBER);
        card.setMessage("Credit Card number is valid & in database");
        when(ccService.validateCC(CARD_NUMBER)).thenReturn(card);

        mockMvc.perform(post("/validate/creditCard/{ccNumber}", CARD_NUMBER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ccNumber").value(CARD_NUMBER))
                .andExpect(jsonPath("$.message").value("Credit Card number is valid & in database"));

        verify(ccService).validateCC(CARD_NUMBER);
    }

    @Test
    void validateCCReturnsNotFoundForUnknownCard() throws Exception {
        when(ccService.validateCC(CARD_NUMBER)).thenThrow(new CCNotFoundException());

        mockMvc.perform(post("/validate/creditCard/{ccNumber}", CARD_NUMBER))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Credit card not found"));
    }

    @Test
    void validateCCReturnsBadRequestForMalformedNumber() throws Exception {
        when(ccService.validateCC("abc"))
                .thenThrow(new CCInvalidException("Credit Card number has to be 16 digits"));

        mockMvc.perform(post("/validate/creditCard/{ccNumber}", "abc"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Credit Card number has to be 16 digits"));
    }
}
