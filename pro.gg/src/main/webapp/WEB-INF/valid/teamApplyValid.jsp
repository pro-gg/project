<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title></title>
    <script src="/webjars/jquery/3.6.0/jquery.min.js"></script>
    <script>
        function existLiner(){
            alert("해당 라인은 이미 지정된 소환사가 있습니다.");
            $.ajax({
                type:'get',
                url:'${pageContext.request.contextPath}/teamdetail.do?teamName=${teamName}',
                data:'',
                dataType:'',
                success:function(data){
                    $("body").html(data);
                }
            })
        }

        function blockToTierLimit(){
            var tierLimit = "${tierLimit}";

            console.log("디버그");
            alert(tierLimit + " 이하는 신청 할 수 없습니다.");
            $.ajax({
                type:'get',
                url:'${pageContext.request.contextPath}/teamdetail.do?teamName=${teamName}',
                data:'',
                dataType:'',
                success:function(data){
                    $("body").html(data);
                }
            })
        }

        function teamApplySuccess(){
            alert("신청이 완료 되었습니다.");
            $.ajax({
                type:'get',
                url:'${pageContext.request.contextPath}/teamdetail.do?teamName=${teamName}',
                data:'',
                dataType:'',
                success:function(data){
                    $("body").html(data);
                }
            })
        }
    </script>
</head>
<body>
    <c:if test="${exist_liner != null && teamName != null}">
        <script>existLiner()</script>
    </c:if>

    <c:if test="${tierLimit != null && teamName != null}">
        <script>blockToTierLimit()</script>
    </c:if>

    <c:if test="${applySuccess != null && teamName != null}">
        <script>teamApplySuccess()</script>
    </c:if>
</body>
</html>