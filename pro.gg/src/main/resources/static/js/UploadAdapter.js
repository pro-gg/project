class UploadAdapter {
    constructor(loader, boardNumber) {
        this.loader = loader;
        this.boardNumber = boardNumber;
    }

    upload() {
        return this.loader.file.then( file => new Promise(((resolve, reject) => {
            this._initRequest();
            this._initListeners( resolve, reject, file );
            this._sendRequest( file );
        })))
    }

    _initRequest() {
        const xhr = this.xhr = new XMLHttpRequest();
        xhr.open('POST', 'https://localhost:8120/image.do?boardNumber='+this.boardNumber, true);
        // xhr.open('POST', 'https://localhost:8120/freeUploadImage', true);
        xhr.responseType = '';
    }

    _initListeners(resolve, reject, file) {
        const xhr = this.xhr;
        const loader = this.loader;
        const genericErrorText = '파일을 업로드 할 수 없습니다.'

        xhr.addEventListener('error', () => {reject(genericErrorText)})
        xhr.addEventListener('abort', () => reject())
        xhr.addEventListener('load', () => {

        encodeURI(xhr.response);
        const response = xhr.response;
        var responseText = xhr.responseText;
            // response 가 왜 null 인 것이며, Content-type 은 왜 '' 가 나오는지 도저히 모르겠다
            // 그냥 에러 발생은 없다고 가정하고 로직을 제외하자.

            // console.log(response);
            // console.log(responseText);
            
            // responseText = btoa(unescape(encodeURIComponent(responseText)));
            // responseText = btoa(responseText);
            // console.log(responseText);

            if(!response || response.error) {
                return reject( response && response.error ? response.error.message : genericErrorText );
            }

            resolve({
                default: responseText //업로드된 파일 주소
            })
            
        })

        if(xhr.upload){
            xhr.upload.addEventListener('progress', evt =>{
                if(evt.lengthComputable){
                    loader.uploadTotal = evt.total;
                    loader.uploaded = evt.loaded;
                }
            })
        }
    }

    _sendRequest(file) {
        const data = new FormData()
        data.append('upload',file)
        this.xhr.send(data);
    }
}