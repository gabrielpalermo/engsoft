<%-- 
    Document   : cartoes
    Created on : 10/05/2019, 10:28:42
    Author     : Gabriel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% String documento = (String)request.getAttribute("documento"); %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Controle Financeiro</title>
        <link rel="stylesheet" href="styles.css">
    </head>
    <body>
        <h2 style="color:#fff">Movimentacoes financeiras</h2>
        
        <!--- Formulário para cadastro de movimentações --->
        <form autocomplete="off" action="movimentacoes" method="get">
            <h3>
                <input type="number" name="valor" required placeholder="Valor">
            </h3>
            <h3>
                <input type="date" name="data" required placeholder="Data">
            </h3>
            <h3
                <!--- Select de cartões + conta corrente construído dinamicamente--->
                <%= servlets.cartoes.GetContaCartoesSelect(documento)%>
            </h3>
            <h3>
                <select name="categoria" required placeholder="Categoria">
                    <option value="Salario">Salario</option>
                    <option value="Alimetacao">Alimentacao</option>
                    <option value="Transporte">Transporte</option>
                    <option value="Lazer">Lazer</option>
                    <option value="Outros">Outros</option>
                </select>
            </h3>
            <h3>
                <select name="io" required placeholder="Tipo">
                    <option value="1">Receita</option>
                    <option value="-1">Despesa</option>
                </select>
            </h3>
            <input type="hidden" name="documento" value=<%=documento%>>
            <button name="action_movimentacoes" value="cadastrar" type="submit">Cadastrar</button>
        </form>
        <h2 style="color:#fff">Balanco mensal: <%= servlets.movimentacoes.GetBalancoMensal(documento) %></h2>
        <!--- Tabela de movimentações construída dinamicamente --->
        <%= servlets.movimentacoes.GetMovimentacoesTable(documento)%>
        
        <!--- Formulário para botão de voltar à tela do usuário --->
        <form autocomplete="off" action="main" method="get">
            <input type="hidden" name="documento" value=<%=documento%>>
            <button name="redirect-path" type="submit" value="tela_usuario.jsp">Voltar</button>
            <input type="hidden" name="entidade" value="movimentacoes">
            <button name="redirect-path" type="submit" value="edit.jsp">Excluir</button>
        </form>
        
    </body>
</html>
