package com.borges.os.serveces;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.borges.os.domain.Pessoa;
import com.borges.os.domain.Tecnico;
import com.borges.os.dtos.TecnicoDTO;
import com.borges.os.repository.PessoaRepository;
import com.borges.os.repository.TecnicoRepository;
import com.borges.os.serveces.exceptions.DataIntegratyViolationException;
import com.borges.os.serveces.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	/*
	 * Busca Tecnico pelo ID
	 */
	public Tecnico findbyId(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id:" + id + ", Tipo: " + Tecnico.class.getName()));
	}
	
	/*
	 * 	Busca todos os tecnicos da base de dados
	 */
	public List<Tecnico> findAll() {
		return repository.findAll();
	}
	
	/*
	 * 	Cria um tecnico
	 */
	public Tecnico create(TecnicoDTO objDTO) {
		
		if (findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		
		return repository.save(new Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()));
		
	}
/*
 * 	Atualiza um Tecnico
 */
	public Tecnico update(Integer id, @Valid TecnicoDTO objDto) {
		Tecnico oldObj = findbyId(id);
		
		if(findByCPF(objDto) != null && findByCPF(objDto).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		
		oldObj.setNome(objDto.getNome());
		oldObj.setCpf(objDto.getCpf());
		oldObj.setTelefone(objDto.getTelefone());
		return repository.save(oldObj);
		
	}
	
/*
 *  Deleta um Tecnico pelo ID
 */
	public void delete(Integer id) {
		
		Tecnico obj = findbyId(id);
		
		if(obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Técnico possui Ordem de Serviço, não pode ser deletado!");
		}
		
		repository.deleteById(id);
		
	}

/*
 * 	Busca o Tecnico pelo CPF	
 */
	private Pessoa findByCPF(TecnicoDTO objDTO) {
		Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());

		if (obj != null) {
			return obj;
		}
		return null;
	}

}
