/**
 *
 */
package br.com.valhala.agenda.web.commands.contato;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import br.com.valhala.agenda.erro.AppException;
import br.com.valhala.agenda.modelo.Contato;
import br.com.valhala.agenda.web.commands.Command;

/**
 * @author bruno
 */
public class NovoContatoCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(NovoContatoCommand.class);

    private static final String URL_ATRIBUTO_CONTATO = "contato";
    private static final String URL_PAGINA_INCLUSAO  = "/WEB-INF/paginas/contato/novo.jsp";

    /*
     * (non-Javadoc)
     * @see br.com.valhala.agenda.web.commands.Command#execute(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws ServletException {
        try {
            LOGGER.info("Executando comando de criacao de contato novo.");
            Contato contato = new Contato.Builder().build();
            requisicao.setAttribute(URL_ATRIBUTO_CONTATO, contato);
            requisicao.getRequestDispatcher(URL_PAGINA_INCLUSAO).forward(requisicao, resposta);
        } catch (IOException e) {
            LOGGER.error("Ocorreu um erro na acao de criacao de contato novo. Erro: " + e.getMessage(), e);
            throw new AppException(e.getMessage(), e);
        }
    }

}
