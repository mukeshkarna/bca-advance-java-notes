<!DOCTYPE html>
<html>
<head>
    <meta http-equiv=“Content-Type” content=“text/htm; charset=UTF-8”>
    <title> JSP program to display “Tribhuvan University” 10 times</title>
</head>
<body>
<h1> Displaying “Tribhuvan University” 10 times!!</h1>
<table>
    <% for(int i=1; i<=10; i++){ %>
        <tr>
            <td>Tribhuvan University</td>
        </tr>
    <% } %>
</table>
</body>
</html>