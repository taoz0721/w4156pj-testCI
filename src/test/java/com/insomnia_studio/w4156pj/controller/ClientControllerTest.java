package com.insomnia_studio.w4156pj.controller;

import com.insomnia_studio.w4156pj.model.Client;
import com.insomnia_studio.w4156pj.repository.CommentRepository;
import com.insomnia_studio.w4156pj.repository.PostEntityRepository;
import com.insomnia_studio.w4156pj.service.ClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {

    @Autowired
    private ClientController clientController;

    private ClientService clientService;

    @BeforeEach
    void setUp() {
        clientService = Mockito.mock(ClientService.class);

        clientController = new ClientController(clientService);
    }

    @Test
    void testRegister() {
        Client client = new Client("a");

        Mockito.when(clientService.createClient(client)).thenReturn(client);

        Client addedClient = clientController.createClient(client);
        Assertions.assertEquals(client, addedClient);
    }

}
