<%-- 
    Document   : welcome
    Created on : 07.11.2018, 11:11:11
    Author     : gsedelnikov
--%>
<!DOCTYPE html>

<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en">
<head>
    <link rel="stylesheet" type="text/css"
            href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
    <c:url value="/css/main.css" var="jstlCss" />
    <link href="${jstlCss}" rel="stylesheet" />
</head>
<body>
    
    <div class="starter-template">
	<h1>Spring Boot Web JSP Example</h1>
	<h2>Message: ${message}</h2>
    </div>    
    
    <form:form method="post" commandName="request">  
        input delay 
        <input type="delay" class="quantity" name="delay" min="0" max="5000" value="20">        
        <br>
        
        <c:forEach items="${queue_types}" var="obj">
            Enter the number of applications with the type = ${obj}:
            <input type="number" class="quantity" name="${obj}" min="0" max="5000" value="3">
            <br>
        </c:forEach>           
        
        <button id ="btnGENERATE" type="submit"> GENERATE </button>        
    </form:form>      
</body>

</html>
