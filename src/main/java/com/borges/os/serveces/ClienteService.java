package com.borges.os.serveces;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.borges.os.domain.Cliente;
import com.borges.os.domain.Pessoa;
import com.borges.os.dtos.ClienteDTO;
import com.borges.os.repository.ClienteRepository;
import com.borges.os.repository.PessoaRepository;
import com.borges.os.serveces.exceptions.DataIntegratyViolationException;
import com.borges.os.serveces.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	@Autowired
	private PessoaRepository pessoaRepository;

	/*
	 * Busca Cliente pelo ID
	 */
	public Cliente findbyId(Integer id) {
		
		Optional<Cliente> obj = repository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id:" + id + ", Tipo:" + Cliente.class.getName()));
	}

	/*
	 * Busca todos os clientes da base de dados
	 */
	public List<Cliente> findAll() {
		return repository.findAll();
	}

	/*
	 * Cria um cliente
	 */
	public Cliente create(ClienteDTO objDTO) {

		if (findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}

		return repository.save(new Cliente(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()));

	}

	/*
	 * Atualizar um Cliente
	 */
	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		
		Cliente oldObj = findbyId(id);

		if (findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
			
		}

		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());
		return repository.save(oldObj);
	}

	/*
	 * Delete um Cliente pelo ID
	 */
	public void delete(Integer id) {
		
		Cliente obj = findbyId(id);
		
		if (obj.getList().size() > 0) {
			
			throw new DataIntegratyViolationException("Pessoa possui Ordem de Serviço, não pode ser deletado!");
			
		}

		repository.deleteById(id);
	}

	/*
	 * Busca o cliente pelo CPF
	 */
	private Pessoa findByCPF(ClienteDTO objDTO) {
		Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());

		if (obj != null) {
			return obj;
		}
		return null;
	}

}
