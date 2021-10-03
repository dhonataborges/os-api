package com.borges.os.serveces;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.borges.os.domain.Cliente;
import com.borges.os.domain.OS;
import com.borges.os.domain.Tecnico;
import com.borges.os.domain.enuns.Prioridade;
import com.borges.os.domain.enuns.Status;
import com.borges.os.repository.ClienteRepository;
import com.borges.os.repository.OSRepository;
import com.borges.os.repository.TecnicoRepository;

@Service
public class DBService {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired 
	private ClienteRepository clienteRepository;
	@Autowired
	private OSRepository osRepository;
	
	public void instanciaDB() {

		Tecnico t1 = new Tecnico(null, "Valdir Cezar", "878.647.690-47", "(38)998736845");
		Tecnico t2 = new Tecnico(null, "Dhonata", "878.647.690-47", "(38)998736845");
		Cliente c1 = new Cliente(null, "Barbara Oliveira", "376.816.820-40", "(38)998736799");
		OS os1 = new OS(null, Prioridade.ALTA, "Teste create OS", Status.ANDAMENTO, t1, c1);

		t1.getList().add(os1);
		c1.getList().add(os1);

		tecnicoRepository.saveAll(Arrays.asList(t1,t2));
		clienteRepository.saveAll(Arrays.asList(c1));
		osRepository.saveAll(Arrays.asList(os1));

	}
}
