package com.insomnia_studio.w4156pj.service;

import com.insomnia_studio.w4156pj.entity.ClientEntity;
import com.insomnia_studio.w4156pj.model.Client;
import com.insomnia_studio.w4156pj.repository.ClientEntityRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientEntityRepository clientEntityRepository;

    public ClientServiceImpl(ClientEntityRepository clientEntityRepository) {
        this.clientEntityRepository = clientEntityRepository;
    }

    @Override
    public Client createClient(Client client) {
        ClientEntity clientEntity = new ClientEntity();

        BeanUtils.copyProperties(client, clientEntity);
        clientEntityRepository.save(clientEntity);

        client.setClientId(clientEntity.getClientId());
        return client;
    }
}
