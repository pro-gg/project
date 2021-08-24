<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pro.gg</title>
<script src="/pro.gg/resources/webjars/jquery/3.6.0/jquery.min.js"></script>
<script src="/pro.gg/resources/js/semantic_aside.js" charset="utf-8"></script>
<script src="/pro.gg/resources/js/semantic_header.js" charset="utf-8"></script>
<script>
    if('${jsonMember}' != null){
        $(function(){
            var jsonMember = JSON.parse('${jsonMember}');

            window.onload = function(){
                var selectMember = $('#Member');
                var table = $("<table>").appendTo(selectMember);
                table.css({"border-collapse" : "collapse", "border" : "1px gray solid"});
                var tr = $("<tr>").appendTo(table);

                $.each(jsonMember, function(key, value){
                    var td = $("<td>").appendTo(tr);
                   
                    td.html(key+" : " + value);
                    td.css({"width":"100px", "border":"1px gray solid"});
                });
            }  
        })
    }
        
    
    
</script>
<style>
    article, form{
        position:absolute;
        width: 920px; height: 50px;
        left: 0; right: 0;
        margin-left: 300px; margin-right:auto;
        top: 0; bottom: 0;
        margin-top: 100px; margin-bottom: auto;
    }

    #Member{
        position:absoulte;
        left: 0; right: 0;
        width: 920px; height: 50px;
        margin-left: 100px; margin-right:auto;
        top: 0; bottom: 0;
        margin-top: 150px; margin-bottom: auto;
    }

</style>
</head>
<body>
    <header></header>
    <aside></aside>
    <article>
        <form action="${pageContext.request.contextPath}/memberSelect.do" method="GET">
            <input type="text" name="selectMember" id="selectMember">
            <input type="submit" >
        </form>
        <div id="Member">

        </div>
    </article>
</body>
</html>