<%@ page language="java" contentType="multipart/form-data; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/mystyle.css"/>
    <script src="/webjars/jquery/3.6.0/jquery.min.js"></script>
    <script src="/js/semantic_aside.js" charset="utf-8"></script>
    <script src="/js/semantic_header.js" charset="utf-8"></script>
    <script src="/js/elements.js" charset="utf-8"></script>
    <script src="/js/ckeditor.js"></script><!--ckeditor 경로-->
    <script src="/js/UploadAdapter.js"></script>
    <title>글 작성</title>
    <script>

        var writedPosting;

        $(function(){
             ClassicEditor
                .create( document.querySelector('#postContent'),{
                    extraPlugins:[MyCustomUploadAdapterPlugin],
                    language: 'ko',
                })
                .then(editor =>{  
                    writedPosting = editor ;
                })
                .catch( error => {
                    console.error( error );
                });
        });

        function MyCustomUploadAdapterPlugin(editor){
            var boardNumber = "${boardNumber}";
            editor.plugins.get('FileRepository').createUploadAdapter = (loader) =>{
                return new UploadAdapter(loader, boardNumber);
            }
        }

        function writePosting(){
            // 작성한 글 데이터베이스에 저장
            var title = document.getElementById("postTitle").value;
            if(title === '') {
                alert("제목은 필수 입니다.");
            }
            else if(postContent === ''){
                alert("내용을 입력해 주십시오");
            }
            else{

                let today = new Date();

                let year = today.getFullYear();
                let month = today.getMonth() + 1;
                let date = today.getDate();
                let time = today.getTime();

                var hour = today.getHours();
                var minutes = today.getMinutes();
                var seconds = today.getSeconds();

                var postDate = year + "." + month +"." + date;
                var postTime = hour + ":" + minutes + ":" + seconds;

                var post = {
                    title : title,
                    postDate : postDate,
                    postTime : postTime,
                    writedPosting : writedPosting.getData(),
                    boardNumber : '${boardNumber}'
                }
                
                // 참고 : https://stackoverflow.com/questions/20960582/html-string-nbsp-breaking-json
                // enter 키를 통해 개행이 발생했을 경우 데이터를 제대로 받아오지 못하는 에러 발생
                // 확인 결과 개행은 &nbsp; 로 처리 되고 있었다
                // 위의 주소를 보면 알 수 있듯 URL 에서 & 문자는 breaking the data, 즉 URL에 데이터 입력을 멈춘다는 뜻이다.
                // 그렇기에 &nbsp; 에서 & 기호로 인해 데이터가 정상적으로 모두 전달되지 않았고, 그 결과 컨트롤러에서 데이터를 정상적으로 받아오지 못하는 에러가 발생되었다.
                // 이를 해결하기 위해 위의 참조에서 알 수 있듯 encodeURL 메소드가 아닌 encodeURIComponent 메소드를 활용하였다. 
                $.ajax({
                    type:'get',
                    url:'${pageContext.request.contextPath}/postWriting.do?post='+encodeURIComponent(JSON.stringify(post)),
                    data:'',
                    dataType:'',
                    success:function(data){
                        window.location.reload();
                    }
                })
            }
        }
    </script>
</head>
<body>
    <header></header>
    <aside></aside>
    <article>
        <div class="wrapper">
            <div class="content-wrapper">
                <div class="col-sm-12">
                    <div class="card">
                        <form class="card-block" method="POST">
                            <c:if test="${boardNumber == 1}">
                                <label for="boardName">게시판</label>
                                <input type="text" placeholder="자유게시판" id="boardName" disabled>
                            </c:if>
                            <c:if test="${boardNumber == 2}">
                                <label for="boardName">게시판</label>
                                <input type="text" placeholder="팀원 모집 게시판" id="boardName" disabled>
                            </c:if>
                            <c:if test="${boardNumber == 3}">
                                <label for="boardName">게시판</label>
                                <input type="text" placeholder="팁 게시판" id="boardName" disabled>
                            </c:if>
                            <br>

                            <label for="postTitle">제목</label>&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="text" placeholder="제목을 입력하세요" size=70 id="postTitle">
                            <br>
                            <hr>
                            <!-- <label for="postContent">내용</label><br> -->
                            <textarea name="postContent" id="postContent"></textarea><input type="button" value="작성하기" onclick="writePosting()">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </article>
</body>
</html>