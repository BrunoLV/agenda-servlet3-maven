package br.com.valhala.agenda.web.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import br.com.valhala.agenda.web.commands.Command;
import br.com.valhala.agenda.web.commands.utils.CommandUtils;

/**
 * Servlet implementation class FrontController
 */
@WebServlet(urlPatterns = { "/mvc" })
public class MvcServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(MvcServlet.class);

    private static final long   serialVersionUID = 1L;
    private static final String COMMAND          = "command";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MvcServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        trataRequisicao(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        trataRequisicao(request, response);
    }

    /**
     * @see Servlet#init(ServletConfig)
     */
    @Override
    public void init() throws ServletException {
        super.init();

    }

    private void trataRequisicao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String command = request.getParameter(COMMAND);
        LOGGER.info("Comando enviado: " + command);
        if (CommandUtils.existeComando(command)) {
            try {
                Command comando = CommandUtils.getComando(command);
                comando.execute(request, response);
            } catch (InstantiationException e) {
                LOGGER.error("Ocorreu um erro na execucao do Servlet. Erro: " + e.getMessage(), e);
                throw new ServletException(e);
            } catch (IllegalAccessException e) {
                LOGGER.error("Ocorreu um erro na execucao do Servlet. Erro: " + e.getMessage(), e);
                throw new ServletException(e);
            } catch (ClassNotFoundException e) {
                LOGGER.error("Ocorreu um erro na execucao do Servlet. Erro: " + e.getMessage(), e);
                throw new ServletException(e);
            }
        } else {
            LOGGER.error("Comando enviado nao e valido.");
            response.sendError(404);
        }
    }

}