<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en" xmlns:c="http://www.w3.org/1999/XSL/Transform">
<head>
    <meta charset="UTF-8">
    <title>Customer Sign Up</title>
</head>
<body>
<h1>Customer Sign Up</h1>

<c:if test="${violations != null}">
    <c:forEach items="${violations}" var="violation">
        <p>${violation}.</p>
    </c:forEach>
</c:if>

<form action="${pageContext.request.contextPath}/processsignup" method="post">
    <p><label for="firstname">First Name : </label>
        <input type="text" name="firstname" id="firstname" value="${firstname}">
        <span id="status1"></span>
    </p>
    <p><label for="lastname">Last Name:</label>
        <input type="text" name="lastname" id="lastname" value="${lastname}">
        <span id="status2"></span>
    </p>
    <p><label for="email">Email: </label>
        <input type="text" name="email" id="email" value="${email}">
        <span id="status3"></span></p>
    <input type="submit" name="signup" value="Sign Up">
</form>
</body>
</html>
