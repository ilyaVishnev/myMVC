<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 13.09.2018
  Time: 0:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script>
        var gearboxes;
        var engines;
        var carBodies;
        $(document).ready(function () {
            $.ajax({
                url: '/image',
                type: 'GET',
                dataType: 'json',
                success: function (data) {
                    $('#image').each(function () {
                        $(this).attr('src', data.image);
                    })
                    $('#myimage').each(function () {
                        this.value = data.image;
                    })
                }
            })
            $.ajax({
                type: 'GET',
                url: '/cars',
                dataType: 'json',
                success: function (data) {
                    var brands = data.brandArray;
                    var models = data.modelArray;
                    gearboxes = data.gearboxArray;
                    engines = data.engineArray;
                    carBodies = data.carbodyArray;
                    var years = data.yearsArray;
                    var content = '<option disabled selected>Выберите марку</option>';
                    for (var i = 0; i < brands.length; i++) {
                        content += '<option value="' + brands[i].id + '">' + brands[i].name + '</option>';
                    }
                    $('#brand').html(content);
                    $('#model').empty().html('<option disabled selected>Выберите модель</option>');
                    $('#gearbox').html('<option disabled selected>Выберите коробку скоростей</option>');
                    $('#engine').html('<option disabled selected>Выберите мотор</option>');
                    $('#carBody').html('<option disabled selected>Выберите корпус</option>');
                    var brandSel = document.getElementById('brand');
                    $('#brand').change(function () {
                        for (var i = 0; i < models.length; i++) {
                            if (models[i].IdBrand == brandSel.options[brandSel.selectedIndex].value) {
                                content += '<option value="' + models[i].id + '">' + models[i].name + '</option>';
                            }
                        }
                        $('#model').empty().html(content);
                    })
                    var modelSel = document.getElementById('model');
                    content = '<option disabled selected>Выберите год</option>';
                    years.forEach(function (year, index, years) {
                        content += '<option value="' + year.year + '">' + year.year + '</option>';
                    })
                    $('#year').html(content);
                    content = '';
                    var yearSel = document.getElementById('year');
                    var modelSel = document.getElementById('model');
                    $('#model').change(function () {
                        fullFields(yearSel, modelSel, gearboxes, engines, carBodies);
                    })
                    $('#year').change(function () {
                        fullFields(yearSel, modelSel, gearboxes, engines, carBodies);
                    })
                }
            })
        })
        function fullFields(yearSel, modelSel, gearboxes, engines, carBodies) {
            var content = '';
            for (var i = 0; i < gearboxes.length; i++) {
                if (gearboxes[i].IdM == modelSel.options[modelSel.selectedIndex].value && gearboxes[i].year >= yearSel.options[yearSel.selectedIndex].value) {
                    content += '<option value="' + gearboxes[i].id + '">' + gearboxes[i].desc + '</option>';
                }
            }
            $('#gearbox').html(content);
            content = '';
            for (var i = 0; i < engines.length; i++) {
                if (engines[i].IdM == modelSel.options[modelSel.selectedIndex].value && engines[i].year >= yearSel.options[yearSel.selectedIndex].value) {
                    content += '<option value="' + engines[i].id + '">' + engines[i].desc + '</option>';
                }
            }
            $('#engine').html(content);
            content = '';
            for (var i = 0; i < carBodies.length; i++) {
                if (carBodies[i].IdM == modelSel.options[modelSel.selectedIndex].value && carBodies[i].year >= yearSel.options[yearSel.selectedIndex].value) {
                    content += '<option value="' + carBodies[i].id + '">' + carBodies[i].desc + '</option>';
                }
            }
            $('#carBody').html(content);
        }
        function checkEmptyPlaces() {
            var data = {
                empty: false
            };
            var elem = $("#createForm").find('input:not(input.image), select').each(
                function () {
                    if ($(this).val() == '' || $(this).val() == null) {
                        data.empty = true;
                    }
                }
            );
            if (data.empty) {
                alert("some of the fields are empty");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
    <p>Введите марку авто : <select id="brand" name="brand" class="form-control">
    </select></p>
    <p>Введите модель авто : <select id="model" name="model" class="form-control">
    </select></p>
    <form name="createForm" action="${pageContext.servletContext.contextPath}/create" method="post" id="createForm">
    <p>Выберите год производства : <select id="year" name="year" class="form-control">
    </select></p>
    <p>Коробка скоростей : <select id="gearbox" name="gearbox" class="form-control">
    </select></p>
    <p>Выберите корпус : <select id="carBody" name="carBody" class="form-control">
    </select></p>
    <p>Выберите мотор : <select id="engine" name="engine" class="form-control">
    </select></p>
    <p>Введите описание : <textarea name="description" class="input-group-prepend"></textarea></p>
    <p>Введите цену : <input type="text" name="price" class="input-group-prepend"></p>
    <p>Выберите статус : <select id="status" name="status" class="form-control">
        <option value="true">Не продано</option>
        <option value="false">Продано</option>
    </select></p>
    <c:if test="${error !=''}">
        <div style="background-color: antiquewhite">
            <c:out value="${error}"></c:out>
        </div>
    </c:if>
    <br/>
    <p><img id="image" src="pictures/emptyPhoto.JPG" width="15%" height="15%"></p>
    <input type="hidden" id="myimage" name="photo" class="image">
    <input type="submit" value="Добавить объявление" name="create" onclick="return checkEmptyPlaces();"
           class="btn btn-default">
</form>
<h3> Выберите изображение: </h3>
<form action="${pageContext.servletContext.contextPath}/image" id="formIm" method="post" name="formIm"
      enctype="multipart/form-data">
    <input type="file" name="file" id="file">
    <br/>
    <input type="button" value="Отправить изображение" onclick="submit()">
</form>
<br/>

</body>
</html>