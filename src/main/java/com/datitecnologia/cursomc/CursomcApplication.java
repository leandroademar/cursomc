package com.datitecnologia.cursomc;

import java.text.SimpleDateFormat;
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
import com.datitecnologia.cursomc.domain.ItemPedido;
import com.datitecnologia.cursomc.domain.Pagamento;
import com.datitecnologia.cursomc.domain.PagamentoComBoleto;
import com.datitecnologia.cursomc.domain.PagamentoComCartao;
import com.datitecnologia.cursomc.domain.Pedido;
import com.datitecnologia.cursomc.domain.Produto;
import com.datitecnologia.cursomc.domain.enums.EstadoPagamento;
import com.datitecnologia.cursomc.domain.enums.TipoCliente;
import com.datitecnologia.cursomc.repositories.CategoriaRepository;
import com.datitecnologia.cursomc.repositories.CidadeRepository;
import com.datitecnologia.cursomc.repositories.ClienteRepository;
import com.datitecnologia.cursomc.repositories.EnderecoRepository;
import com.datitecnologia.cursomc.repositories.EstadoRepository;
import com.datitecnologia.cursomc.repositories.ItemPedidoRepository;
import com.datitecnologia.cursomc.repositories.PagamentoRepository;
import com.datitecnologia.cursomc.repositories.PedidoRepository;
import com.datitecnologia.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository repo;
	@Autowired 
	private ProdutoRepository produto;
	@Autowired
	private EstadoRepository estado;
	@Autowired
	private CidadeRepository cidade;
	@Autowired
	private ClienteRepository cliente;
	@Autowired
	private EnderecoRepository endereco;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
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
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));

		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));	
	}

}
