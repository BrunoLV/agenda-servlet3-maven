package br.com.valhala.agenda.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import br.com.valhala.agenda.modelo.Contato;
import br.com.valhala.agenda.modelo.Telefone;
import br.com.valhala.agenda.modelo.enums.EnumTipoTelefone;

public class ContatoDao {

	private static final String SQL_ATUALIZA_CONTATO = "UPDATE contato SET nome = ? WHERE id = ?";
	private static final String SQL_ATUALIZA_TELEFONE_CONTATO = "UPDATE telefone SET ddd = ?, numero = ?, tipo = ? WHERE id = ?";
	private static final String SQL_BUSCA_ID = "SELECT * FROM contato WHERE id = ?";
	private static final String SQL_BUSCA_TELEFONE_CONTATO = "SELECT * FROM telefone WHERE id_contato = ?";
	private static final String SQL_EXCLUI_CONTATO = "DELETE FROM contato WHERE id = ?";
	private static final String SQL_EXCLUI_TELEFONE = "DELETE FROM telefone WHERE id = ?";
	private static final String SQL_EXCLUI_TELEFONES_CONTATO = "DELETE FROM telefone WHERE id_contato = ?";
	private static final String SQL_INSERE_CONTATO = "INSERT INTO contato (nome) VALUES (?)";
	private static final String SQL_INSERE_TELEFONE_CONTATO = "INSERT INTO telefone (ddd, numero, tipo, id_contato) VALUES (?, ?, ?, ?)";
	private static final String SQL_LISTA = "SELECT * FROM contato";
	private static final String SQL_PESQUISA_IDS_TELEFONES_CONTATO = "SELECT id FROM telefone WHERE id_contato = ?";

	private Connection conexao;

	public ContatoDao(Connection conexao) {
		super();
		this.conexao = conexao;
	}

	public void atualiza(Contato contato) throws SQLException {
		try (PreparedStatement stmt = conexao.prepareStatement(SQL_ATUALIZA_CONTATO)) {
			stmt.setString(1, contato.getNome());
			stmt.setLong(2, contato.getId());
			stmt.execute();
		}
		if ((contato.getTelefones() != null)) {
			Collection<Long> idsDaOperacao = new HashSet<>();
			for (Telefone telefone : contato.getTelefones()) {
				if (telefone.getId() != null) {
					idsDaOperacao.add(telefone.getId());
					atualizaTelefoneContato(telefone);
				} else {
					Long idTelefone = insereTelefoneContato(telefone, contato);
					idsDaOperacao.add(idTelefone);
				}
			}
			excluiTelefonesNaoMaisNecessarios(idsDaOperacao, contato);
		}
	}

	private void atualizaTelefoneContato(Telefone telefone) throws SQLException {
		try (PreparedStatement stmt = conexao.prepareStatement(SQL_ATUALIZA_TELEFONE_CONTATO)) {
			stmt.setString(1, telefone.getDdd());
			stmt.setString(2, telefone.getNumero());
			stmt.setString(3, telefone.getTipo().name());
			stmt.setLong(4, telefone.getId());
			stmt.execute();
		}
	}

	private void bucaTelefoneContato(Contato contato) throws SQLException {
		try (PreparedStatement stmtTelefone = conexao.prepareStatement(SQL_BUSCA_TELEFONE_CONTATO)) {
			stmtTelefone.setLong(1, contato.getId());
			try (ResultSet rsTelefone = stmtTelefone.executeQuery()) {
				while (rsTelefone.next()) {
					Telefone telefone = new Telefone.Builder().id(rsTelefone.getLong("id"))
							.ddd(rsTelefone.getString("ddd")).numero(rsTelefone.getString("numero"))
							.tipo(EnumTipoTelefone.valueOf(rsTelefone.getString("tipo"))).build();
					contato.adicionaTelefone(telefone);
				}
			}
		}
	}

	public Contato buscaPorId(Long id) throws SQLException {
		Contato contato = null;
		try (PreparedStatement stmt = conexao.prepareStatement(SQL_BUSCA_ID)) {
			stmt.setLong(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					contato = new Contato.Builder().id(rs.getLong("id")).nome(rs.getString("nome")).build();
				}
			}
		}
		if (contato != null) {
			bucaTelefoneContato(contato);
		}
		return contato;
	}

	private void excluiContato(Long idContato) throws SQLException {
		try (PreparedStatement stmt = conexao.prepareStatement(SQL_EXCLUI_CONTATO)) {
			stmt.setLong(1, idContato);
			stmt.execute();
		}
	}

	public void excluir(Long id) throws SQLException {
		excluiTelefones(id);
		excluiContato(id);
	}

	private void excluiTelefones(Long idContato) throws SQLException {
		try (PreparedStatement stmt = conexao.prepareStatement(SQL_EXCLUI_TELEFONES_CONTATO)) {
			stmt.setLong(1, idContato);
			stmt.execute();
		}
	}

	private void excluiTelefonesNaoMaisNecessarios(Collection<Long> idsManipuladosNaOperacao, Contato contato)
			throws SQLException {
		Set<Long> idsRecuperadosBanco = new HashSet<>();
		try (PreparedStatement stmt = conexao.prepareStatement(SQL_PESQUISA_IDS_TELEFONES_CONTATO)) {
			stmt.setLong(1, contato.getId());
			stmt.executeQuery();
			try (ResultSet rs = stmt.getResultSet()) {
				while (rs.next()) {
					idsRecuperadosBanco.add(rs.getLong(1));
				}
			}
		}
		for (Long idTelefone : idsRecuperadosBanco) {
			if (!idsManipuladosNaOperacao.contains(idTelefone)) {
				try (PreparedStatement stmtDelete = conexao.prepareStatement(SQL_EXCLUI_TELEFONE)) {
					stmtDelete.setLong(1, idTelefone);
					stmtDelete.execute();
				}
			}
		}
	}

	public Long insere(Contato contato) throws SQLException {
		Long idGerado = null;
		try (PreparedStatement stmt = conexao.prepareStatement(SQL_INSERE_CONTATO,
				java.sql.Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, contato.getNome());
			stmt.execute();
			try (ResultSet rs = stmt.getGeneratedKeys()) {
				if (rs.next()) {
					idGerado = rs.getLong(1);
				}
			}
		}
		if (idGerado != null) {
			if ((contato.getTelefones() != null) && !contato.getTelefones().isEmpty()) {
				for (Telefone telefone : contato.getTelefones()) {
					insereTelefone(idGerado, telefone);
				}
			}
		}
		return idGerado;
	}

	private void insereTelefone(Long idGerado, Telefone telefone) throws SQLException {
		try (PreparedStatement stmtTelefone = conexao.prepareStatement(SQL_INSERE_TELEFONE_CONTATO)) {
			stmtTelefone.setString(1, telefone.getDdd());
			stmtTelefone.setString(2, telefone.getNumero());
			stmtTelefone.setString(3, telefone.getTipo().name());
			stmtTelefone.setLong(4, idGerado);
			stmtTelefone.execute();
		}
	}

	private Long insereTelefoneContato(Telefone telefone, Contato contato) throws SQLException {
		Long idCriado = null;
		try (PreparedStatement stmt = conexao.prepareStatement(SQL_INSERE_TELEFONE_CONTATO,
				java.sql.Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, telefone.getDdd());
			stmt.setString(2, telefone.getNumero());
			stmt.setString(3, telefone.getTipo().name());
			stmt.setLong(4, contato.getId());
			stmt.execute();
			try (ResultSet rs = stmt.getGeneratedKeys()) {
				if (rs.next()) {
					idCriado = rs.getLong(1);
				}
			}
		}
		return idCriado;
	}

	public Collection<Contato> lista() throws SQLException {
		Collection<Contato> contatos = new ArrayList<>();
		try (PreparedStatement stmt = conexao.prepareStatement(SQL_LISTA)) {
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Contato contato = new Contato.Builder().id(rs.getLong("id")).nome(rs.getString("nome")).build();
					contatos.add(contato);
				}
			}
		}
		return contatos;
	}

}
