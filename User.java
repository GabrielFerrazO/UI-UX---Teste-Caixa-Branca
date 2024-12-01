package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe responsável por gerenciar a autenticação de usuários no sistema.
 */
public class User {

    /**
     * Nome do usuário autenticado.
     */
    private String nome = "";

    /**
     * Retorna o nome do usuário autenticado.
     *
     * @return o nome do usuário autenticado.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Estabelece uma conexão com o banco de dados.
     *
     * @return um objeto {@link Connection} se a conexão for bem-sucedida, ou {@code null} caso contrário.
     */
    public Connection conectarBD() {
        Connection conn = null;
        try {
            // Carrega o driver do MySQL
            Class.forName("com.mysql.Driver").newInstance();
            // URL de conexão com o banco de dados
            String url = "jdbc:mysql://127.0.0.1/test?user=lopes&password=123";
            // Estabelece a conexão
            conn = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erro ao conectar ao banco: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
        return conn;
    }

    /**
     * Verifica as credenciais do usuário no banco de dados.
     *
     * @param login o login fornecido pelo usuário.
     * @param senha a senha fornecida pelo usuário.
     * @return {@code true} se as credenciais forem válidas e o usuário for autenticado; {@code false} caso contrário.
     */
    public boolean verificarUsuario(String login, String senha) {
        String sql = "SELECT nome FROM usuarios WHERE login = ? AND senha = ?";
        try (Connection conn = conectarBD();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Define os parâmetros da consulta
            stmt.setString(1, login);
            stmt.setString(2, senha);
            // Executa a consulta
            ResultSet rs = stmt.executeQuery();
            // Verifica se houve resultado
            if (rs.next()) {
                // Obtém o nome do usuário autenticado
                nome = rs.getString("nome");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar usuário: " + e.getMessage());
        }
        return false;
    }
}
