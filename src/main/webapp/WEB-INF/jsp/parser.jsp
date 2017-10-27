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
    <link rel="stylesheet" href="/css/styleForParser.css"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.17.1/moment.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.6.4/css/bootstrap-datepicker3.css"/>
</head>
<body>

<script>
    function load() {
        var a=document.getElementsByClassName("loading")[0];
        a.style.display = "block";

        var b = document.getElementById('main');
        b.style.display = "none";
    }
</script>

<form:form method="POST" commandName="parserDate" action="result" enctype="multipart/form-data">
    <div class="container">
        <div class="col-md-4" id="main">
            <form:label path="nameForBD">Name:</form:label>
            <form:input cssClass="form-control" path="nameForBD"/>

            <label for="parserConf">ParserConf:</label>
            <form:select path="parserConf" class="form-control" id="parserConf">
                <option>sdng</option>
                <option>gc</option>
                <option>top</option>
            </form:select>


            <form:label path="filePath">Select a file to upload</form:label>
            <input type="file" name="filePath" />

            <label for="timeZone">TimeZone:</label>
            <form:select path="timeZone" class="form-control" id="timeZone">
                <option>UTC</option>
                <option>GMT+1</option>
                <option>GMT+2</option>
                <option>GMT+3</option>
                <option>GMT+4</option>
                <option>GMT+5</option>
                <option>GMT+6</option>
                <option>GMT+7</option>
                <option>GMT+8</option>
                <option>GMT+9</option>
                <option>GMT+10</option>
                <option>GMT+11</option>
                <option>GMT+12</option>
                <option>GMT+13</option>
                <option>GMT+14</option>
                <option>GMT-1</option>
                <option>GMT-2</option>
                <option>GMT-3</option>
                <option>GMT-4</option>
                <option>GMT-5</option>
                <option>GMT-6</option>
                <option>GMT-7</option>
                <option>GMT-8</option>
                <option>GMT-9</option>
                <option>GMT-10</option>
                <option>GMT-11</option>
                <option>GMT-12</option>
            </form:select>
        <form:label path="traceResult">TraceResult:</form:label>
        <form:checkbox class="checkbox" path="traceResult"/><br><br>
        <button class="btn btn-primary" onclick="load()">Pars</button>
        </div>
    </div>

    <div class="loading" id="loading">
        <svg width="300" height="120" id="clackers">
            <!-- Left arc path -->
            <svg>
                <path id="arc-left-up" fill="none" d="M 90 90 A 90 90 0 0 1 0 0"/>
            </svg>
            <!-- Right arc path -->
            <svg>
                <path id="arc-right-up" fill="none" d="M 100 90 A 90 90 0 0 0 190 0"/>
            </svg>

            <text x="150" y="50" fill="#ffffff" font-family="Helvetica Neue,Helvetica,Arial" font-size="18"
                  text-anchor="middle">
                L O A D I N G
            </text>
            <circle cx="15" cy="15" r="15">
                <!-- I used a python script to calculate the keyPoints and keyTimes based on a quadratic function. -->
                <animateMotion dur="1.5s" repeatCount="indefinite"
                               calcMode="linear"
                               keyPoints="0.0;0.19;0.36;0.51;0.64;0.75;0.84;0.91;0.96;0.99;1.0;0.99;0.96;0.91;0.84;0.75;0.64;0.51;0.36;0.19;0.0;0.0;0.05;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0"
                               keyTimes="0.0;0.025;0.05;0.075;0.1;0.125;0.15;0.175;0.2;0.225;0.25;0.275;0.3;0.325;0.35;0.375;0.4;0.425;0.45;0.475;0.5;0.525;0.55;0.575;0.6;0.625;0.65;0.675;0.7;0.725;0.75;0.775;0.8;0.825;0.85;0.875;0.9;0.925;0.95;0.975;1.0">
                    <mpath xlink:href="#arc-left-up"/>
                </animateMotion>
            </circle>
            <circle cx="135" cy="105" r="15" />
            <circle cx="165" cy="105" r="15" />
            <circle cx="95" cy="15" r="15">
                <animateMotion dur="1.5s" repeatCount="indefinite"
                               calcMode="linear"
                               keyPoints="0.0;0.0;0.05;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0.0;0.19;0.36;0.51;0.64;0.75;0.84;0.91;0.96;0.99;1.0;0.99;0.96;0.91;0.84;0.75;0.64;0.51;0.36;0.19;0.0"
                               keyTimes="0.0;0.025;0.05;0.075;0.1;0.125;0.15;0.175;0.2;0.225;0.25;0.275;0.3;0.325;0.35;0.375;0.4;0.425;0.45;0.475;0.5;0.525;0.55;0.575;0.6;0.625;0.65;0.675;0.7;0.725;0.75;0.775;0.8;0.825;0.85;0.875;0.9;0.925;0.95;0.975;1.0">
                    <mpath xlink:href="#arc-right-up"/>
                </animateMotion>
            </circle>
        </svg>
    </div>


</form:form>
</body>
</html>
