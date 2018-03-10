package br.com.valhala.agenda.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import br.com.valhala.agenda.erro.AppException;

public class FabricaConexoes {

    private static final FabricaConexoes instance = new FabricaConexoes();

    public static FabricaConexoes getIntance() {
        return instance;
    }

    private DataSource dataSource;

    protected FabricaConexoes() {
        try {
            Context contextInicial = new InitialContext();
            Context contextoAmbiente = (Context) contextInicial.lookup("java:/comp/env");
            dataSource = (DataSource) contextoAmbiente.lookup("jdbc/agenda");
        } catch (NamingException e) {
            throw new AppException(e.getMessage(), e);
        }
    }

    public Connection getConexao() {
        try {
            Connection conexao = dataSource.getConnection();
            conexao.setAutoCommit(false);
            return conexao;
        } catch (SQLException e) {
            throw new AppException(e.getMessage(), e);
        }
    }

}
