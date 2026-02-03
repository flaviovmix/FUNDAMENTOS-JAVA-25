package br.projeto.tarefa;

import br.root.config.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {

    public List<TarefaBean> listarTarefas() throws SQLException {
        List<TarefaBean> lista = new ArrayList<>();
        String sql = "SELECT * FROM tarefas";

        try (
            Connection conn = ConnectionPool.getConexao();
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

    public void inserirTarefa(TarefaBean tarefa) throws SQLException {

        String sql = """
            INSERT INTO tarefas
                (titulo, prioridade, responsavel, status, descricao)
            VALUES
                (?, ?, ?, ?, ?)
        """;

        try (
            Connection conn = ConnectionPool.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, tarefa.getTitulo());
            ps.setString(2, tarefa.getPrioridade());
            ps.setString(3, tarefa.getResponsavel());
            ps.setInt(4, tarefa.getStatus());
            ps.setString(5, tarefa.getDescricao());

            ps.executeUpdate();
        }
    }

    public TarefaBean buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM tarefas WHERE id_tarefa = ?";

        try (
            Connection conn = ConnectionPool.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                TarefaBean tarefa = new TarefaBean();
                tarefa.setId_tarefa(rs.getInt("id_tarefa"));
                tarefa.setTitulo(rs.getString("titulo"));
                tarefa.setPrioridade(rs.getString("prioridade"));
                tarefa.setResponsavel(rs.getString("responsavel"));
                tarefa.setStatus(rs.getInt("status"));
                tarefa.setDescricao(rs.getString("descricao"));
                return tarefa;
            }
        }
    }

    public void atualizarTarefa(TarefaBean tarefa) throws SQLException {
        String sql = """
            UPDATE tarefas
            SET titulo = ?, prioridade = ?, responsavel = ?, status = ?, descricao = ?
            WHERE id_tarefa = ?
        """;

        try (
            Connection conn = ConnectionPool.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, tarefa.getTitulo());
            ps.setString(2, tarefa.getPrioridade());
            ps.setString(3, tarefa.getResponsavel());
            ps.setInt(4, tarefa.getStatus());
            ps.setString(5, tarefa.getDescricao());
            ps.setInt(6, tarefa.getId_tarefa());

            ps.executeUpdate();
        }
    }



    public void excluirTarefa(Integer id) throws SQLException {
        String sql = "DELETE FROM tarefas WHERE id_tarefa = ?";

        try (
            Connection conn = ConnectionPool.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
