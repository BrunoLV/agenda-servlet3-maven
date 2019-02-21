package br.com.valhala.agenda.db.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.valhala.agenda.db.FabricaConexoesTeste;
import br.com.valhala.agenda.db.FlywayTestUtils;
import br.com.valhala.agenda.modelo.Contato;
import br.com.valhala.agenda.modelo.Telefone;
import br.com.valhala.agenda.modelo.enums.EnumTipoTelefone;

@DisplayName("Testes de integração com banco de dados para minupulação de Contato")
public class ContatoDaoTest {

	private ContatoDao dao;
	private Connection conexao;

	@BeforeAll
	public static void constroiBanco() {
		new FlywayTestUtils().migrarBancoTeste();
	}

	@AfterAll
	public static void destroiBanco() {
		new FlywayTestUtils().limparBancoTeste();
	}

	@BeforeEach
	public void inicializa() {
		conexao = new FabricaConexoesTeste().getConexao();
		dao = new ContatoDao(conexao);
	}

	@AfterEach
	public void limpa() {
		deletaRegistrosDasTabelas();
	}

	@Test
	@DisplayName("Inserção de contato sem telefones.")
	public void deveInserirContatoNoBancoDeDados() throws SQLException {

		Contato contato = new Contato.Builder().nome("Pedro Henrique Renan Enrico Drumond").build();

		Long idGerado = dao.insere(contato);

		assertNotNull(idGerado);

	}

	@Test
	@DisplayName("Atualização apenas nos dados do contato, não dos telefones")
	public void deveAtualizarContatoNoBancoDeDados() throws SQLException {

		Contato contato = new Contato.Builder().nome("Pedro Henrique Renan Enrico Drumond").build();

		Long idGerado = dao.insere(contato);

		assertNotNull(idGerado);

		Contato contatoBanco = dao.buscaPorId(idGerado);

		assertNotNull(contatoBanco);
		assertThat(contatoBanco.getNome(), equalTo("Pedro Henrique Renan Enrico Drumond"));

		Contato contatoAtualiza = new Contato.Builder()
				.id(contatoBanco.getId())
				.nome("Geraldo Renan Campos")
				.telefones(contatoBanco.getTelefones())
				.build();

		dao.atualiza(contatoAtualiza);

		contatoBanco = dao.buscaPorId(idGerado);

		assertNotNull(contatoBanco);
		assertThat(contatoBanco.getNome(), equalTo("Geraldo Renan Campos"));
	}

	@Test
	@DisplayName("Inserção de contato completo com telefones.")
	public void deveInserirContatoComTelefonesNoBancoDeDados() throws SQLException {

		Telefone telefone = new Telefone.Builder().ddd("011").numero("3764-7751").tipo(EnumTipoTelefone.RESIDENCIAL)
				.build();

		Contato contato = new Contato.Builder().nome("Pedro Henrique Renan Enrico Drumond").comTelefone(telefone)
				.build();

		Long idGerado = dao.insere(contato);

		assertNotNull(idGerado);

		Contato contatoPersistido = dao.buscaPorId(idGerado);

		assertNotNull(contatoPersistido);
		assertNotNull(contatoPersistido.getTelefones());
		assertThat(contatoPersistido.getTelefones().size(), equalTo(1));
		assertThat(contatoPersistido.getTelefones(), hasItem(telefone));

	}

	@Test
	@DisplayName("Exclusão de contato no banco de dados.")
	public void deveRemoverContatoDoBancoDeDados() throws SQLException {

		Telefone telefone = new Telefone.Builder().ddd("011").numero("3764-7751").tipo(EnumTipoTelefone.RESIDENCIAL)
				.build();

		Contato contato = new Contato.Builder().nome("Pedro Henrique Renan Enrico Drumond").comTelefone(telefone)
				.build();

		Long idGerado = dao.insere(contato);

		assertNotNull(idGerado);

		Contato contatoPersistido = dao.buscaPorId(idGerado);

		assertNotNull(contatoPersistido);

		dao.excluir(idGerado);

		contatoPersistido = dao.buscaPorId(idGerado);

		assertNull(contatoPersistido);
	}

	private void deletaRegistrosDasTabelas() {
		try {
			conexao.createStatement().execute("DELETE FROM telefone");
			conexao.createStatement().execute("DELETE FROM contato");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
