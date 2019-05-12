<%-- 
    Document   : cartoes
    Created on : 10/05/2019, 10:28:42
    Author     : Gabriel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="servlets.cartoes"%>
<% String documento = (String)request.getAttribute("documento"); %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Controle Financeiro</title>
        <link rel="stylesheet" href="styles.css">
    </head>
    <body>
        <h2 style="color:#fff">Cartões de Crédito</h2>
        
        <!--- Formulário para cadastro de cartões --->
        <form autocomplete="off" action="cartoes" method="get">
            <h3>
                <input type="text" name="codigo" required placeholder="Código do cartão">
            </h3>
            <h3>
                <input type="number" name="limite" required placeholder="Limite">
            </h3>
            <h3>
                <input type="text" name="bandeira" required placeholder="Bandeira">
            </h3>
            <input type="hidden" name="documento" value=<%=documento%>>
            <button name="action_cartoes" value="cadastrar" type="submit">Cadastrar</button>
        </form>
        
        <!--- Tabela de cartões construída dinamicamente --->
        <%= servlets.cartoes.GetCartoesTable(documento)%>
        
        <!--- Formulário para botão de voltar à tela do usuário --->
        <form autocomplete="off" action="main" method="get">
            <input type="hidden" name="documento" value=<%=documento%>>
            <button name="redirect-path" type="submit" value="tela_usuario.jsp">Voltar</button>
            <input type="hidden" name="entidade" value="cartoes">
            <button name="redirect-path" type="submit" value="edit.jsp">Editar / Excluir</button>
        </form>
        
    </body>
</html>
