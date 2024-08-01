<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta name="_csrf" content="${_csrf.token}" />
    <meta name="_csrf_header" content="${_csrf.headerName}" />

    <script src="http://code.jquery.com/jquery-1.11.1.js" type="text/javascript"></script>
    <script type="text/javascript">

        $(function() {
            $('#submit').click(function () {
                $.ajax({
                    url :"/hello",
                    type :"post",
                    beforeSend : function(xhr){
                        xhr.setRequestHeader($("meta[name='_csrf_header']").attr("content"),
                                             $("meta[name='_csrf']").attr("content"));
                        xhr.setRequestHeader("X-IDENTIFIER",
                                             $('#username').val());
                    },
                    success : function(msg){
                        alert("성공");
                    }
                });
                return false;
            });
        });

    </script>
</head>
<body>
<form>
    <span>username : </span>
    <span><input type="text" id="username" name="username" value="${username}" /></span>
    <span><input type="button" id="submit" value="POST 테스트"/></span>
</form>
</body>
</html>