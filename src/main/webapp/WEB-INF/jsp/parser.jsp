<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Parser</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <script src="/js/jquery-3.1.1.min.js"></script>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.5/css/bootstrap.min.css"
          integrity="sha384-AysaV+vQoT3kOAXZkl02PThvDr8HYKPZhNT5h/CXfBThSRXQ6jW5DO2ekP5ViFdi" crossorigin="anonymous"/>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.5/js/bootstrap.min.js"
            integrity="sha384-BLiI7JTZm+JWlgKa0M0kGRpJbF2J8q+qreVrKBC47e3K6BW78kGLrCkeRX6I9RoK"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.2.0/js/tether.min.js"
            integrity="sha384-Plbmg8JY28KFelvJVai01l8WyZzrYWG825m+cZ0eDDS1f7d/js6ikvy1+X+guPIB"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.17.1/moment.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker3.css"/>
</head>
<body>

<form:form method="POST" commandName="parserDate" action="result">

    <fieldset class="boxBody">

        <form:label path="nameForBD">NameBD:</form:label>
        <form:input path="nameForBD" />

        <form:label path="parserConf">ParserConf:</form:label>
        <form:input path="parserConf"/>

        <form:label path="filePath">FilePath:</form:label>
        <form:input path="filePath"/>

        <form:label path="timeZone">TimeZone:</form:label>
        <form:input path="timeZone"/>

        <form:label path="traceResult">TraceResult:</form:label>
        <form:checkbox path="traceResult"/>

    </fieldset>

    <footer> <label><input type="checkbox" tabindex="3">Keep me logged in</label>
        <input type="submit" class="btnLogin" value="Login" tabindex="4">
    </footer>

</form:form>
</body>
</html>
