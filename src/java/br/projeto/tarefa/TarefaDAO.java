package br.projeto.tarefa;

import br.root.config.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {

    private final ConnectionPool con;

    public TarefaDAO() {
        this.con = new ConnectionPool();
    }

    public TarefaDAO(ConnectionPool con) {
        this.con = con;
    }

    public List<TarefaBean> listarTarefas() throws SQLException {
        List<TarefaBean> lista = new ArrayList<>();
        String sql = "SELECT * FROM tarefas";

        try (
            Connection conn = con.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                TarefaBean tarefa = new TarefaBean();
                tarefa.setId_tarefa(rs.getInt("id_tarefa"));
                tarefa.setTitulo(rs.getString("titulo"));
                tarefa.setPrioridade(rs.getString("prioridade"));
                tarefa.setResponsavel(rs.getString("responsavel"));
                tarefa.setStatus(rs.getInt("status"));
                tarefa.setDescricao(rs.getString("descricao"));
                lista.add(tarefa);
            }
        }
        return lista;
    }
    
    public void excluirTarefa(Integer id) throws SQLException {
        String sql = "DELETE FROM tarefas WHERE id_tarefa = ?";

        try (
            Connection conn = con.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql);
            ) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

