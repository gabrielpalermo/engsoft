<%-- 
    Document   : cartoes
    Created on : 10/05/2019, 10:28:42
    Author     : Gabriel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="servlets.cartoes"%>
<% String documento = (String)request.getAttribute("documento"); %>
<% String entidade = (String)request.getAttribute("entidade"); %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Controle Financeiro</title>
        <link rel="stylesheet" href="styles.css">
    </head>
    <body>
        <h2 style="color:#fff">Editar / Excluir</h2>
        <h3 style="color:#fff">Selecione o item a ser editado ou excluido</h3>
        
        <!--- Formulário de dados para edição da entidade construida dinamicamente com opção de excluir --->
        <% if(entidade.equals("cartoes")){ %>
            <%= servlets.cartoes.GetCartoesEdit(documento)%>
        <% } else if(entidade.equals("movimentacoes")){ %>
            <%= servlets.movimentacoes.GetMovimentacoesDelete(documento)%>
        <% } %>
        
        <form autocomplete="off" action="main" method="get">
            <input type="hidden" name="documento" value=<%=documento%>>
            <button name="redirect-path" type="submit" value="<%=entidade%>.jsp">Voltar</button>
        </form>
        
    </body>
</html>
