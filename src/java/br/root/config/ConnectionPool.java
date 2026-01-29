package br.root.config;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionPool {

    private static final String JNDI_NAME = "java:comp/env/jdbc/PoolConexoes";
    private static DataSource dataSource;

    static {
        try {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup(JNDI_NAME);
        } catch (NamingException e) {
            throw new ExceptionInInitializerError("Erro ao configurar o DataSource via JNDI: " + e.getMessage());
        }
    }

    public static Connection getConexao() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("DataSource não inicializado");
        }
        return dataSource.getConnection();
    }
    
}