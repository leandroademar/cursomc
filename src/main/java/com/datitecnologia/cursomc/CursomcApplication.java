package com.datitecnologia.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.datitecnologia.cursomc.domain.Categoria;
import com.datitecnologia.cursomc.domain.Cidade;
import com.datitecnologia.cursomc.domain.Cliente;
import com.datitecnologia.cursomc.domain.Endereco;
import com.datitecnologia.cursomc.domain.Estado;
import com.datitecnologia.cursomc.domain.Produto;
import com.datitecnologia.cursomc.domain.enums.TipoCliente;
import com.datitecnologia.cursomc.repositories.CategoriaRepository;
import com.datitecnologia.cursomc.repositories.CidadeRepository;
import com.datitecnologia.cursomc.repositories.ClienteRepository;
import com.datitecnologia.cursomc.repositories.EnderecoRepository;
import com.datitecnologia.cursomc.repositories.EstadoRepository;
import com.datitecnologia.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	CategoriaRepository repo;
	@Autowired 
	ProdutoRepository produto;
	@Autowired
	EstadoRepository estado;
	@Autowired
	CidadeRepository cidade;
	@Autowired
	ClienteRepository cliente;
	@Autowired
	EnderecoRepository endereco;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	
		Categoria cat1 = new Categoria(null,"Informatica");
		Categoria cat2 = new Categoria(null,"Escritorio");

		
		Produto p1 = new Produto(null,"Computador",2000.00);
		Produto p2 = new Produto(null,"Notebook",2500.00);
		Produto p3 = new Produto(null,"Tablet A7",1400.00);

		Estado est1 = new Estado(null,"Minas Gerais");
		Estado est2 = new Estado(null,"São Paulo");
		
		Cidade c1 = new Cidade(null,"Uberlandia",est1);
		Cidade c2 = new Cidade(null,"São Paulo",est2);
		Cidade c3 = new Cidade(null,"Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
	
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		repo.saveAll(Arrays.asList(cat1,cat2));
		produto.saveAll(Arrays.asList(p1,p2,p3));
		estado.saveAll(Arrays.asList(est1,est2));
		cidade.saveAll(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);

		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));

		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		cliente.saveAll(Arrays.asList(cli1));
		endereco.saveAll(Arrays.asList(e1, e2));
		
	}

}
