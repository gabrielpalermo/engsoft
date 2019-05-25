<%-- 
    Document   : cartoes
    Created on : 10/05/2019, 10:28:42
    Author     : Gabriel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="servlets.cartoes"%>
<% String message = (String)request.getAttribute("message"); %>
<% String path = (String)request.getAttribute("redirect-path"); %>
<!DOCTYPE html>
<html>
    <head>
        <title>Controle Financeiro</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="styles.css">
    </head>
    <body>
        <h3 style="color:#fff"><%=message%></h3>
        <form autocomplete="off" action="main" method="get">
            <button name="redirect-path" type="submit" value=<%=path%>>Voltar</button>
        </form>
    </body>
</html>
