package com.insomnia_studio.w4156pj.service;

import com.insomnia_studio.w4156pj.model.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientServiceTest {
    @Autowired
    private ClientService clientService;

    @Test
    void testRegister() {
        Client client = new Client("a");

        Client response = clientService.createClient(client);
        Assertions.assertEquals(response.getClientName(), client.getClientName());
    }
}
