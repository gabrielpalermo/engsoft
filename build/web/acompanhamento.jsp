<%-- 
    Document   : acompanhamento
    Created on : 25/05/2019, 13:59:47
    Author     : Gabriel
--%>

<%@page import="modelos.Categoria"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="servlets.acompanhamento"%>
<% String documento = (String)request.getAttribute("documento"); %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Acompanhamento</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="styles.css">
        <!--Load the AJAX API-->
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        <script type="text/javascript">
            // Load the Visualization API and the corechart package.
            google.charts.load('current', {'packages':['corechart']});

            // Set a callback to run when the Google Visualization API is loaded.
            google.charts.setOnLoadCallback(drawChartA);
            google.charts.setOnLoadCallback(drawChartB);
            google.charts.setOnLoadCallback(drawChartC);
    
            function drawChartA(myData) {
                // Create the data table.            
                var data = new google.visualization.DataTable();
                data.addColumn('string', 'Categoria');
                data.addColumn('number', 'Valor');
                data.addRows(myData);

                var options = {'title':'Receitas e despesas categorizados por mês',
                               'width':1000,
                               'height':300};

                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.BarChart(document.getElementById('chart_div_A'));
                chart.draw(data, options);
            }
    
            function drawChartB(myData) {
                // Create the data table.            
                var data = new google.visualization.DataTable();
                data.addColumn('string', 'Categoria');
                data.addColumn('number', 'Valor');
                data.addRows(myData);

                var options = {'title':'Categoria de receita e de despesa nos últimos seis meses',
                               'width':1000,
                               'height':300};

                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.BarChart(document.getElementById('chart_div_B'));
                chart.draw(data, options);
            }
            
            function drawChartC(myData) {
                // Create the data table.            
                var data = new google.visualization.DataTable();
                data.addColumn('string', 'Tipo');
                data.addColumn('number', 'Valor');
                data.addRows(myData);

                var options = {'title':'Líquido mensal nos últimos seis meses',
                               'width':1000,
                               'height':300};

                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.BarChart(document.getElementById('chart_div_C'));
                chart.draw(data, options);
            }
        </script>  
    </head>
    <!--- Grafico de acompanhamento construído dinamicamente --->
    
    <body onload="drawChartA(<%= servlets.acompanhamento.GetValoresCategoriaMes(documento)%>);drawChartB(<%= servlets.acompanhamento.GetValoresCategorias(documento)%>);drawChartC(<%= servlets.acompanhamento.GetLiquidoSeisMeses(documento)%>);">
        <div id="chart_div_A"></div>
        <div id="chart_div_B"></div>
        <div id="chart_div_C"></div>
        <form autocomplete="off" action="main" method="get">
            <input type="hidden" name="documento" value=<%=documento%>>
            <br/>
            <button name="redirect-path" type="submit" value="tela_usuario.jsp">Voltar</button>
        </form>
    </body>
</html>
