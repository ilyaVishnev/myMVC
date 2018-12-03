<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 12.09.2018
  Time: 23:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>description</title>
    <style>
        .price {
            background-color: #3370FF;
        }
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script>
        $(document).ready(function () {
            $.ajax({
                type: 'GET',
                url: '/desc',
                dataType:'json',
                success:function (data) {
                    var content = " <tr>\n" +
                        "        <td>Описание</td>\n" +
                        "        <td>Корпус</td>\n" +
                        "        <td>Коробка скоростей</td>\n" +
                        "        <td>Мотор</td>\n" +
                        "        <td>Статус</td>\n" +
                        "        <td>Фото</td>\n" +
                        "    </tr>";
                    var id_holder = data.idHolder;
                    var id = '${user.getId()}';
                    var status = data.status;
                    if (id == id_holder) {
                        if (status) {
                            var option = "<option value=\"true\">Не продано</option>\n<option value=\"false\">Продано</option>";
                        } else {
                            var option = "<option value=\"false\">Продано</option>\n<option value=\"true\">Не продано</option>";
                        }
                    } else {
                        if (status) {
                            var option = "<option value=\"true\">Не продано</option>";
                        } else {
                            var option = "<option value=\"false\">Продано</option>";
                        }
                    }
                    content += "<tr><td>" + data.desc + "</td><td>" + data.carbody + "</td><td>" +
                        data.gearbox + "</td><td>" + data.engine + "</td><td>" +
                        "<select id=\"status\" name=\"status\" onchange=\"document.forms['descForm'].submit()\" class=\"form-control\">" + option +
                        "</select>" + "</td><td><img src=\"" + data.photo
                        + "\"  width=\"45%\" height=\"45%\"></td></tr><input type=\"hidden\" id=\"id\" name=\"id\" value=\""
                        + data.id + "\" >";
                    $('#table').html(content);
                    $('#price').each(function () {
                        this.value = data.price;
                    })
                }
            })
        })
    </script>
</head>
<body>
<form action="${pageContext.servletContext.contextPath}/status" method="post" name="descForm" id="descForm">
    <table id="table" class="table">
    </table>
    </br>
    Цена : <input type="text" disabled class="price" id="price" name="price">
</form>
</body>
</html>
