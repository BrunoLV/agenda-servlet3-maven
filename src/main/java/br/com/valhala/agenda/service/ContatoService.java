package br.com.valhala.agenda.service;

import br.com.valhala.agenda.db.dao.ContatoDao;
import br.com.valhala.agenda.modelo.Contato;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public class ContatoService {

    private final ContatoDao dao;
    private Connection conexao;

    public ContatoService(final Connection conexao) {
        this.conexao = conexao;
        this.dao = new ContatoDao(conexao);
    }

    public Contato buscaPorId(final Long id) throws SQLException {
        Contato contato = dao.buscaPorId(id);
        return contato;
    }

    public boolean salva(Contato contato) throws SQLException {
        boolean operacaoSucesso = false;
        try {
            if (contato.getId() == null) {
                dao.insere(contato);
            } else {
                dao.atualiza(contato);
            }
            operacaoSucesso = true;
            conexao.commit();
        } catch (SQLException e) {
            conexao.rollback();
            throw e;
        }
        return operacaoSucesso;
    }

    public Collection<Contato> lista() throws SQLException {
        Collection<Contato> lista = dao.lista();
        return lista;
    }

    public boolean deleta(final Long id) throws SQLException {
        boolean operacaoSucesso = false;
        try {
            dao.excluir(id);
            conexao.commit();
            operacaoSucesso = true;
        } catch (SQLException e) {
            conexao.rollback();
            throw e;
        }
        return operacaoSucesso;
    }

}
