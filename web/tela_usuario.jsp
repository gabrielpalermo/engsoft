<%-- 
    Document   : tela_usuario
    Created on : 11/05/2019, 9:12:42
    Author     : Gabriel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% String documento = (String)request.getAttribute("documento"); %>
<!DOCTYPE html>

<html>
    <head>
        <title>Controle Financeiro</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="styles.css">
    </head>
    <body>
        <h1 style="color:#fff">Página inicial</h1>
        <h3 style="color:#fff;">Login realizado com sucesso</h3>
        <form autocomplete="off" action="main" method="get">
            <input type="hidden" name="documento" value=<%=documento%>>
            <button name="redirect-path" type="submit" value="cartoes.jsp">Cartões</button>
            <!-- TO DO: desenvolvimento de tela de movimentações e acompanhamento -->
            <button name="redirect-path" type="submit" value="movimentacoes.html">Movimentações</button>
            <br/>
            <br/>
            <button name="redirect-path" type="submit" value="index.html">Sair</button>
        </form>
    </body>
</html>
