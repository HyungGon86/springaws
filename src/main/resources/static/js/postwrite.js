window.onload = function () {

    $('#summernote').summernote({
        // height: 600,                 // 에디터 높이
        minHeight: 700,             // 최소 높이
        maxHeight: 1000,             // 최대 높이
        focus: true,                  // 에디터 로딩후 포커스를 맞출지 여부
        lang: "ko-KR",					// 한글 설정
        placeholder: '내용을 입력해주세요.',	//placeholder 설정
        callbacks: {	//여기 부분이 이미지를 첨부하는 부분
            onImageUpload : function(files) {
                uploadSummernoteImageFile(files[0],this);
            },
            onPaste: function (e) {
                let clipboardData = e.originalEvent.clipboardData;
                if (clipboardData && clipboardData.items && clipboardData.items.length) {
                    let item = clipboardData.items[0];
                    if (item.kind === 'file' && item.type.indexOf('image/') !== -1) {
                        e.preventDefault();
                    }
                }
            }
        }
    });

    /* 썸머노트 툴바 설정하는거 */
    $('.summernote').summernote({
        toolbar: [
            // [groupName, [list of button]]
            ['fontname', ['fontname']],
            ['fontsize', ['fontsize']],
            ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
            ['color', ['forecolor','color']],
            ['table', ['table']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['height', ['height']],
            ['insert',['picture','link','video']],
            ['view', ['fullscreen', 'help']]
        ],
        fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New','맑은 고딕','궁서','굴림체','굴림','돋움체','바탕체'],
        fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72']
    });


    // 이미지 파일 업로드
    function uploadSummernoteImageFile(file, editor) {

        let formData = new FormData();
        formData.append("attachFiles", file);

        $.ajax({
            data : formData,
            type : "POST",
            url : "/files/upload",
            contentType : false,
            processData : false,
            success : function(data) {

                for (const file of data) {
                    $(editor).summernote('insertImage', file.filePath);
                    console.log('filePath = ' + file.filePath);
                }
            }
        });
    }

    // 파일 첨부버튼 클릭시 인풋파일 클릭 이벤트 실행
    document.getElementById('attachBtn').addEventListener('click', () => {
        document.getElementById('attachFiles').click();
    });


};