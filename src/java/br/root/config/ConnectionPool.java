package br.root.config;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public final class ConnectionPool {

    private static final String JNDI_NAME = "java:comp/env/jdbc/PoolConexoes";
    private static DataSource dataSource;

    private static NamingException erroJndi;

    static {
        try {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup(JNDI_NAME);
        } catch (NamingException e) {
            erroJndi = e;     
            dataSource = null; 
        }
    }

    public static Connection getConexao() throws SQLException {
        if (dataSource == null) {
            SQLException ex = new SQLException(
                "Erro ao conectar ao banco (JNDI nao disponivel): " +
                (erroJndi != null ? erroJndi.getMessage() : "DataSource nao inicializado")
            );
            if (erroJndi != null) ex.initCause(erroJndi);
            throw ex;
        }
        return dataSource.getConnection();
    }

}
