# UI-UX---Teste-Caixa-Branca
UI/UX - Teste Caixa Branca. Facens, Professor Daniel Ohata.


# Teste de Caixa Branca - Projeto `User`

## Descrição
Este projeto contém um código básico para estudo de testes de caixa branca. Ele se conecta a um banco de dados e verifica usuários por meio de um login e senha.

## Erros encontrados

1. **Vulnerabilidade a SQL Injection**:
   - O código utiliza concatenação direta de strings para formar as consultas SQL, como no trecho:
     ```java
     sql = "select nome from usuarios where login = '" + login + "' and senha = '" + senha + "';";
     ```
   - **Problema**: Isso permite que um invasor insira código malicioso no campo de login ou senha para acessar ou manipular o banco de dados.
   - **Solução sugerida**: Usar `PreparedStatement` para evitar injeções de SQL.

2. **Exceções sem tratamento adequado**:
   - Em várias partes do código, as exceções são capturadas, mas não são registradas nem tratadas de forma informativa:
     ```java
     catch (Exception e) {
         // Nenhuma ação é realizada com a exceção.
     }
     ```
   - **Problema**: Isso dificulta a depuração de erros durante o desenvolvimento ou operação.
   - **Solução sugerida**: Adicionar mensagens de log, como `e.printStackTrace()` ou utilizar uma biblioteca de log, como Log4j ou SLF4J.

3. **Recursos não fechados**:
   - Os objetos `Connection`, `Statement` e `ResultSet` não são fechados após o uso:
     ```java
     Connection conn = conectarBD();
     Statement st = conn.createStatement();
     ResultSet rs = st.executeQuery(sql);
     ```
   - **Problema**: Isso pode causar vazamento de memória e problemas de desempenho no banco de dados.
   - **Solução sugerida**: Usar `try-with-resources` para fechar automaticamente os recursos após o uso.

4. **Credenciais sensíveis no código**:
   - A URL de conexão contém usuário e senha:
     ```java
     String url = "jdbc:mysql://127.0.0.1/test?user=lopes&password=123";
     ```
   - **Problema**: É uma má prática de segurança, pois credenciais podem ser expostas se o código for compartilhado.
   - **Solução sugerida**: Armazenar as credenciais em variáveis de ambiente ou arquivos de configuração externos e seguros.

5. **Encapsulamento inadequado**:
   - As variáveis `nome` e `result` são públicas:
     ```java
     public String nome = "";
     public boolean result = false;
     ```
   - **Problema**: Permite que outros componentes do sistema modifiquem esses valores diretamente.
   - **Solução sugerida**: Tornar as variáveis privadas e implementar métodos de acesso (`getters` e `setters`).

---

## Como executar o código
1. Certifique-se de que o JDK está instalado no sistema.
2. Compile o código com:
   ```bash
   javac User.java
