package learn.manish.creditCard.config;

import learn.manish.creditCard.ctrl.CCCrudController;
import learn.manish.creditCard.service.CCService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CCCrudController.class)
@Import(SecurityConfig.class)
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CCService ccService;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Test
    void crudEndpointRejectsAnonymousRequest() throws Exception {
        mockMvc.perform(get("/CrudCC/listAllCC"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void crudEndpointAllowsJwtAuthenticatedRequest() throws Exception {
        when(ccService.getAllCC()).thenReturn(List.of());

        mockMvc.perform(get("/CrudCC/listAllCC").with(jwt()))
                .andExpect(status().isOk());
    }
}
