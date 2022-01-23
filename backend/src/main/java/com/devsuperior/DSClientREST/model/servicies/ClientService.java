package com.devsuperior.DSClientREST.model.servicies;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.DSClientREST.dto.ClientDTO;
import com.devsuperior.DSClientREST.model.entities.Client;
import com.devsuperior.DSClientREST.model.repositories.ClientRepository;
import com.devsuperior.DSClientREST.model.servicies.exception.DatabaseException;
import com.devsuperior.DSClientREST.model.servicies.exception.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {

		Page<Client> client = repository.findAll(pageRequest);

		return client.map(x -> new ClientDTO(x));
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {

		Client client = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));

		return new ClientDTO(client);
	}

	@Transactional
	public ClientDTO save(ClientDTO dto) {

		Client client = new Client();
		copyDtoToEntity(dto, client);

		client = repository.save(client);

		return new ClientDTO(client);
	}

	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {

		try {
			Client client = repository.getOne(id);
			copyDtoToEntity(dto, client);

			client = repository.save(client);
			return new ClientDTO(client);
		}
		catch(EntityNotFoundException e){
			throw new ResourceNotFoundException("Id not found "+ id);
		}
	}
	
	public void deleteById(Long id) {

		try {
			repository.deleteById(id);
			
		}
		catch(EmptyResultDataAccessException e){
			throw new ResourceNotFoundException("Id not found "+ id);
		}
		catch(DataIntegrityViolationException e){
			throw new DatabaseException("Integrity Violation");
		}
	}

	private void copyDtoToEntity(ClientDTO dto, Client client) {
		client.setName(dto.getName());
		client.setCpf(dto.getCpf());
		client.setIncome(dto.getIncome());
		client.setBirthDate(dto.getBirthDate());
		client.setChildren(dto.getChildren());
	}

}
