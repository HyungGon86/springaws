window.onload = function () {

    const imgs = ['gif', 'jpg', 'jpeg', 'png', 'bmp', 'ico', 'apng'];

    const createFileList = function (file) {
        let input = document.createElement('input');
        input.setAttribute('id', file.fileId);
        input.setAttribute('name', 'fileList');
        input.setAttribute('data-path', file.filePath);
        input.setAttribute('value', file.fileId);
        input.setAttribute('type', 'hidden');
        document.getElementById('postForm').append(input);

        let span = document.createElement('span');
        let button = document.createElement('button');
        let li = document.createElement('li');
        li.setAttribute('class', 'fileList')
        li.setAttribute('data-id', file.fileId);
        button.setAttribute('value', file.fileId);
        button.setAttribute('class', 'fileDeleteBtn');
        button.setAttribute('type', 'button');
        button.innerText = '삭제';
        span.innerText = file.originalName;

        li.append(span, button);
        document.getElementById('fileUl').append(li);
    }

    $('#summernote').summernote({
        // height: 600,                 // 에디터 높이
        minHeight: 800,             // 최소 높이
        maxHeight: 1300,             // 최대 높이
        focus: true,                  // 에디터 로딩후 포커스를 맞출지 여부
        lang: "ko-KR",					// 한글 설정
        placeholder: '내용을 입력해주세요.',	//placeholder 설정
        toolbar: [
            // [groupName, [list of button]]
            ['fontname', ['fontname']],
            ['fontsize', ['fontsize']],
            ['style', ['bold', 'italic', 'underline', 'strikethrough', 'clear']],
            ['color', ['forecolor', 'color']],
            ['table', ['table']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['height', ['height']],
            ['insert', ['picture', 'link', 'video']],
            ['view', ['help']]
        ],
        fontNames: ['맑은 고딕', '궁서', '굴림체', '굴림', '돋움체', '바탕체', 'Arial', 'Arial Black', 'Comic Sans MS', 'Courier New'],
        fontSizes: ['8', '9', '10', '11', '12', '14', '16', '18', '20', '22', '24', '28', '30', '36', '50', '72'],
        callbacks: {	//여기 부분이 이미지를 첨부하는 부분
            onImageUpload: function (files) {
                uploadSummernoteImageFile(files, this);
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
    // $('#summernote').summernote('fontName', '맑은 고딕');


    // 이미지 파일 업로드
    function uploadSummernoteImageFile(files, editor) {

        let formData = new FormData();

        for (let file of files) {
            formData.append('attachFiles', file);
        }

        $.ajax({
            data: formData,
            type: "POST",
            url: "/files/upload",
            contentType: false,
            processData: false,
            success: function (data) {

                for (const file of data) {
                    const fileDot = String(file.originalName).lastIndexOf('.');
                    const ext = String(file.originalName).substring(fileDot + 1).toLocaleLowerCase();

                    if (imgs.indexOf(ext) >= 0) {
                        createFileList(file);
                        $(editor).summernote('insertImage', file.filePath);
                    } else {
                        alert('이미지를 제외한 파일은 파일 첨부 버튼을 이용해 주세요.');
                    }
                }
            }
        });
    }

    // 파일 첨부버튼 클릭시 인풋파일 클릭 이벤트 실행
    document.getElementById('attachBtn').addEventListener('click', () => {
        document.getElementById('attachFiles').click();
    });

    document.getElementById('attachFiles').addEventListener('change', function (e) {
        let files = e.target.files;
        let formData = new FormData();

        for (let file of files) {
            formData.append('attachFiles', file);
        }

        $.ajax({
            data: formData,
            type: "POST",
            url: "/files/upload",
            contentType: false,
            processData: false,
            success: function (data) {

                for (const file of data) {
                    createFileList(file);
                }
            },
            error: function (error) {
                console.log(error);
            },
        });

        this.value = '';
    });

    document.addEventListener('click', function (e) {

        if (e.target.className === 'fileDeleteBtn') {
            document.querySelector(`li[data-id="${e.target.value}"]`).remove();
            let fileInput = document.getElementById(e.target.value);
            fileInput.setAttribute('name', 'fileDeleteList');

            const image = document.querySelectorAll(".note-editable img");

            const pathDot = fileInput.dataset.path.lastIndexOf('\\');
            const fileName = fileInput.dataset.path.substring(pathDot + 1);

            image.forEach(img => {

                const imgDot = img.src.lastIndexOf('/');
                const imgName = img.src.substring(imgDot + 1);

                if (fileName === imgName) {
                    img.remove();
                }
            });


        }
    });

    document.getElementById('registerBtn').addEventListener('click', () => {

        const image = document.querySelectorAll(".note-editable img"); // 게시물에 찐막으로 남아잇는 이미지태그
        // const fileList = document.querySelectorAll('input[name="fileList"]');
        //
        // for (const list of fileList) {
        //
        //     for (const img of image) {
        //         const pathDot = list.dataset.path.lastIndexOf('\\');
        //         const imgDot = img.src.lastIndexOf('/');
        //
        //         const listName = list.dataset.path.substring(pathDot + 1);
        //         const imgName = img.src.substring(imgDot + 1);
        //
        //         if (listName === imgName) {
        //             list.setAttribute('name', 'fileList');
        //             break;
        //         } else {
        //             list.setAttribute('name', 'fileDeleteList');
        //         }
        //     }
        // }

        const content = document.querySelector(".note-editable");
        let str = '';

        console.log('이너텍스트 =' + content.innerText);
        for (const child of content.children) {
            str += child.innerText.replace(/\s/g, '');

        }
        if (!str && !image.length) {
            alert('내용을 입력해주세요');
            content.focus();
            return;
        }


        document.getElementById('postForm').submit();
    });


};