package kz.unm.tusupkalimiraszhaugashnurzhan;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanAuthRequestDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.dto.TusupkaliMirasZhaugashNurzhanRegisterRequestDto;
import kz.unm.tusupkalimiraszhaugashnurzhan.entity.TusupkaliMirasZhaugashNurzhanRoleName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TusupkaliMirasZhaugashNurzhanSecurityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void studentsEndpointRequiresJwtAndAcceptsValidToken() throws Exception {
        mockMvc.perform(get("/api/students"))
                .andExpect(result -> assertThat(result.getResponse().getStatus()).isIn(401, 403));

        String username = "mockmvc-user";
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new TusupkaliMirasZhaugashNurzhanRegisterRequestDto(
                                username,
                                username + "@unm.test",
                                "Password123",
                                "MockMvc User",
                                TusupkaliMirasZhaugashNurzhanRoleName.STUDENT
                        ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(username));

        String loginBody = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new TusupkaliMirasZhaugashNurzhanAuthRequestDto(username, "Password123"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        @SuppressWarnings("unchecked")
        Map<String, Object> loginResponse = objectMapper.readValue(loginBody, Map.class);
        String token = loginResponse.get("token").toString();

        mockMvc.perform(get("/api/students")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }
}
