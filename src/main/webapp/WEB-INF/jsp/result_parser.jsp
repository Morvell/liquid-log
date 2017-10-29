<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.naumen.sd40.log.parser.ActionDoneParser" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="ru.naumen.sd40.log.parser.ErrorParser" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 25.10.2017
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.5/css/bootstrap.min.css"
          integrity="sha384-AysaV+vQoT3kOAXZkl02PThvDr8HYKPZhNT5h/CXfBThSRXQ6jW5DO2ekP5ViFdi" crossorigin="anonymous"/>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.5/js/bootstrap.min.js"
            integrity="sha384-BLiI7JTZm+JWlgKa0M0kGRpJbF2J8q+qreVrKBC47e3K6BW78kGLrCkeRX6I9RoK"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="/css/style.css"/>
</head>
<body>



<div class="container">
    <div id="actions-chart-container" style="height: 600px"></div>
    <div class="scroll-container">
        <table class="table table-fixed header-fixed">
            <thead class="thead-inverse">
            <th class="col-xs-2">Timestamp</th>
            <th class="col-xs-1">Actions</th>
            <th class="col-xs-1">Min</th>
            <th class="col-xs-1">Mean</th>
            <th class="col-xs-1">Stdev</th>
            <th class="col-xs-1">50%</th>
            <th class="col-xs-1">95%</th>
            <th class="col-xs-1">99%</th>
            <th class="col-xs-1">99.9%</th>
            <th class="col-xs-1">Max</th>
            <th class="col-xs-1">Errors</th>
            </thead>
            <tbody >


            <c:forEach var="num" items="${date}">
                <tr class="row">
                    <td class="col-xs-2">
                            ${num.getK()}
                    </td>
                    <td class="col-xs-1">
                            ${num.getCount()}
                    </td>
                    <td class="col-xs-1">
                            ${num.getMin()}
                    </td>
                    <td class="col-xs-1">
                            ${num.getMean()}
                    </td>
                    <td class="col-xs-1" >
                            ${num.getStddev()}
                    </td>
                    <td class="col-xs-1" >
                            ${num.getPercent50()}
                    </td>
                    <td class="col-xs-1">
                            ${num.getPercent95()}
                    </td>
                    <td class="col-xs-1">
                            ${num.getPercent99()}
                    </td>
                    <td class="col-xs-1">
                            ${num.getPercent999()}
                    </td>
                    <td class="col-xs-1">
                            ${num.getMax()}
                    </td>

                </tr>
            </c:forEach>

            </tbody>
        </table>
    </div>

</div>
</body>
</html>
