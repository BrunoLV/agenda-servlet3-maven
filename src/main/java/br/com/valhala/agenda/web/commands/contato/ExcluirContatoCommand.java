/**
 *
 */
package br.com.valhala.agenda.web.commands.contato;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import br.com.valhala.agenda.db.FabricaConexoes;
import br.com.valhala.agenda.db.dao.ContatoDao;
import br.com.valhala.agenda.erro.AppException;
import br.com.valhala.agenda.web.commands.Command;

/**
 * @author bruno
 */
public class ExcluirContatoCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(ExcluirContatoCommand.class);

    private static final String URL_ACAO_LISTAGEM = "/mvc?command=listarContatos";

    /*
     * (non-Javadoc)
     * @see br.com.valhala.agenda.web.commands.Command#execute(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws ServletException {
        try (Connection conexao = FabricaConexoes.getIntance().getConexao()) {
            LOGGER.info("Executando comando para exclusao de cadastro de contato.");
            Long id = Long.parseLong(requisicao.getParameter("id"));
            try {
                ContatoDao contatoDao = new ContatoDao(conexao);
                contatoDao.excluir(id);
                conexao.commit();
                resposta.sendRedirect(requisicao.getContextPath() + URL_ACAO_LISTAGEM);
            } catch (Exception e) {
                conexao.rollback();
            }
        } catch (SQLException e) {
            LOGGER.error("Ocorreu um erro na acao de exclusao de cadastro de contato. Erro: " + e.getMessage(), e);
            throw new AppException(e.getMessage(), e);
        }
    }

}
