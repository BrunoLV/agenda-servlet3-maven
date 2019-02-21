package br.com.valhala.agenda.web.commands.contato;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import br.com.valhala.agenda.db.FabricaConexoesDataSouce;
import br.com.valhala.agenda.db.dao.ContatoDao;
import br.com.valhala.agenda.erro.AppException;
import br.com.valhala.agenda.modelo.Contato;
import br.com.valhala.agenda.web.commands.Command;

public class AtualizarContatoCommand implements Command {

	private static final Logger LOGGER = Logger.getLogger(AtualizarContatoCommand.class);

	private static final String ATRIBUTO_CONTATO = "contato";
	private static final String PARAMETRO_ID = "id";
	private static final String URL_PAGINA_EDICAO = "/WEB-INF/paginas/contato/atualiza.jsp";

	@Override
	public void execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws ServletException {
		try (Connection conexao = FabricaConexoesDataSouce.getIntance().getConexao()) {
			LOGGER.info("Executando comando para atualizacao de cadastro de contato.");
			Long id = Long.parseLong(requisicao.getParameter(PARAMETRO_ID));
			ContatoDao contatoDao = new ContatoDao(conexao);
			Contato contato = contatoDao.buscaPorId(id);
			requisicao.setAttribute(ATRIBUTO_CONTATO, contato);
			requisicao.getRequestDispatcher(URL_PAGINA_EDICAO).forward(requisicao, resposta);
		} catch (SQLException e) {
			LOGGER.error("Ocorreu um erro na acao de atualizacao de cadastro de contato. Erro: " + e.getMessage(), e);
			throw new AppException(e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error("Ocorreu um erro na acao de atualizacao de cadastro de contato. Erro: " + e.getMessage(), e);
			throw new AppException(e.getMessage(), e);
		}
	}

}
