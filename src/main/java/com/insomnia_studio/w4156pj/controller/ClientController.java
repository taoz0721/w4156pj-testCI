package com.insomnia_studio.w4156pj.controller;

import com.insomnia_studio.w4156pj.model.Client;
import com.insomnia_studio.w4156pj.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/register")
    public Client createClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }
}
