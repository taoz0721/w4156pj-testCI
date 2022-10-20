package com.insomnia_studio.w4156pj.service;

import com.insomnia_studio.w4156pj.controller.ClientController;
import com.insomnia_studio.w4156pj.entity.ClientEntity;
import com.insomnia_studio.w4156pj.model.Client;
import com.insomnia_studio.w4156pj.repository.ClientRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client createClient(Client client) {
        ClientEntity clientEntity = new ClientEntity();

        BeanUtils.copyProperties(client, clientEntity);
        clientRepository.save(clientEntity);
        return client;
    }
}
