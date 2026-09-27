package learn.manish.creditCard.ctrl;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.manish.creditCard.exceptions.CCAlreadyExistsException;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CCCrudControllerTest {

    private static final String CARD_NUMBER = "1234567890123456";

    @Mock
    private CCService ccService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new CCCrudController(ccService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createCCReturnsCreatedWhenServiceSavesCard() throws Exception {
        CreditCard card = card(CARD_NUMBER, "Visa");
        when(ccService.addCC(org.mockito.ArgumentMatchers.any(CreditCard.class))).thenReturn(1);

        mockMvc.perform(post("/CrudCC/createCC")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(card)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ccNumber").value(CARD_NUMBER))
                .andExpect(jsonPath("$.message").value("Credit Card Number successfully added to database"));

        verify(ccService).addCC(org.mockito.ArgumentMatchers.any(CreditCard.class));
    }

    @Test
    void createCCReturnsConflictWhenCardAlreadyExists() throws Exception {
        when(ccService.addCC(org.mockito.ArgumentMatchers.any(CreditCard.class))).thenReturn(0);

        mockMvc.perform(post("/CrudCC/createCC")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(card(CARD_NUMBER, "Visa"))))
                .andExpect(status().isConflict())
                .andExpect(content().string("Credit card already exists in the system"));
    }

    @Test
    void createCCReturnsBadRequestWhenServiceRejectsCard() throws Exception {
        when(ccService.addCC(org.mockito.ArgumentMatchers.any(CreditCard.class)))
                .thenThrow(new CCInvalidException("Credit Card number has to be 16 digits"));

        mockMvc.perform(post("/CrudCC/createCC")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(card("123", "Visa"))))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Credit Card number has to be 16 digits"));
    }

    @Test
    void getCCReturnsCard() throws Exception {
        CreditCard card = card(CARD_NUMBER, "Visa");
        when(ccService.getCreditCardDetails(CARD_NUMBER)).thenReturn(card);

        mockMvc.perform(get("/CrudCC/getCCbyId/{ccNumber}", CARD_NUMBER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ccNumber").value(CARD_NUMBER))
                .andExpect(jsonPath("$.ccType").value("Visa"));

        verify(ccService).getCreditCardDetails(CARD_NUMBER);
    }

    @Test
    void getCCReturnsNotFoundWhenServiceCannotFindCard() throws Exception {
        when(ccService.getCreditCardDetails(CARD_NUMBER)).thenThrow(new CCNotFoundException());

        mockMvc.perform(get("/CrudCC/getCCbyId/{ccNumber}", CARD_NUMBER))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Credit card not found"));
    }

    @Test
    void listAllCCReturnsEveryCard() throws Exception {
        when(ccService.getAllCC()).thenReturn(List.of(
                card(CARD_NUMBER, "Visa"),
                card("2345678901234567", "MasterCard")));

        mockMvc.perform(get("/CrudCC/listAllCC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].ccType").value("MasterCard"));

        verify(ccService).getAllCC();
    }

    @Test
    void updateCCReturnsUpdatedCard() throws Exception {
        CreditCard updated = card(CARD_NUMBER, "MasterCard");
        when(ccService.modifyCC(org.mockito.ArgumentMatchers.any(CreditCard.class))).thenReturn(updated);

        mockMvc.perform(put("/CrudCC/updateCC")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ccType").value("MasterCard"))
                .andExpect(jsonPath("$.message").value("Credit Card Details Updated Successfully.."));

        verify(ccService).modifyCC(org.mockito.ArgumentMatchers.any(CreditCard.class));
    }

    @Test
    void updateCCReturnsNotFoundWhenServiceCannotFindCard() throws Exception {
        when(ccService.modifyCC(org.mockito.ArgumentMatchers.any(CreditCard.class)))
                .thenThrow(new CCNotFoundException());

        mockMvc.perform(put("/CrudCC/updateCC")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(card(CARD_NUMBER, "Visa"))))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Credit card not found"));
    }

    @Test
    void removeCCReturnsSuccessWhenOneRowIsDeleted() throws Exception {
        when(ccService.removeCC(CARD_NUMBER)).thenReturn(1);

        mockMvc.perform(delete("/CrudCC/delCC/{ccNumber}", CARD_NUMBER))
                .andExpect(status().isOk())
                .andExpect(content().string("Credit Card Number successfully deleted"));

        verify(ccService).removeCC(CARD_NUMBER);
    }

    @Test
    void removeCCReturnsNotFoundWhenNoRowIsDeleted() throws Exception {
        when(ccService.removeCC(CARD_NUMBER)).thenReturn(0);

        mockMvc.perform(delete("/CrudCC/delCC/{ccNumber}", CARD_NUMBER))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Credit card not found"));
    }

    @Test
    void clearCacheDelegatesToService() throws Exception {
        mockMvc.perform(delete("/CrudCC/clearCache"))
                .andExpect(status().isOk())
                .andExpect(content().string("Cache cleared successfully"));

        verify(ccService).clearCache();
        verifyNoMoreInteractions(ccService);
    }

    private static CreditCard card(String number, String type) {
        CreditCard card = new CreditCard();
        card.setCcNumber(number);
        card.setCcName("Test Bank");
        card.setCcType(type);
        return card;
    }
}
