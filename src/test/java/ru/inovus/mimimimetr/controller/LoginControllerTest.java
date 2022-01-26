package ru.inovus.mimimimetr.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private LoginController loginController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void loginControllerMustBeNotNull() {
        assertNotNull(loginController);
    }

    @Test
    public void loginControllerMustReturnLoginPage() throws Exception {
       this.mockMvc.perform(MockMvcRequestBuilders.get("/login"))
               .andDo(MockMvcResultHandlers.print())
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Зарегистрироваться")))
               .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Пароль")))
               .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Адрес эл.почты")));
    }

    @Test
    public void authorizationRequestTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/voting"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/voting/leaderboard"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound());
    }

    @Test
    public void badCredentialsTest() throws Exception {
        this.mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin().user("badcredentialstest").password("pass"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login?error"));
    }

    @Test
    public void correctCredentialsTest() throws Exception {
        this.mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin().user("test@test.test").password("password"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/voting"));
    }

    @Test
    @WithUserDetails("test@test.test")
    public void redirectForAlreadyLoggedTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.redirectedUrl("voting"));
    }
}
