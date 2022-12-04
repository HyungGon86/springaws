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
        let i = document.createElement('i');
        let li = document.createElement('li');
        li.setAttribute('class', 'fileList')
        li.setAttribute('data-id', file.fileId);
        button.setAttribute('value', file.fileId);
        button.setAttribute('class', 'fileDeleteBtn');
        i.setAttribute('class', 'fa-regular fa-square-minus delete');
        i.style.pointerEvents = 'none';
        button.setAttribute('type', 'button');
        span.innerText = file.originalName;

        button.appendChild(i);
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
        fontNames: ['맑은 고딕', '궁서', '굴림체', '굴림', '돋움체', '바탕체', 'Arial', 'Arial Black', 'Comic Sans MS', 'Courier New', 'NanumSquareNeo-Variable'],
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
    $('#summernote').summernote('fontName', 'NanumSquareNeo-Variable');

    if (!document.getElementById('title').value) {
        const placeholder = document.querySelector(".note-placeholder");
        placeholder.style.display = 'block';
    }



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
                        alert('이미지 파일만 삽입 가능합니다.');
                    }
                }
            }
        });
    }

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

            if (document.getElementById('thumbnailId').value === e.target.value) {

                document.getElementById('thumbBox').style.display = 'none';
                document.getElementById('thumbnailUrl').value = '';

            } else {
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

        }
    });

    document.addEventListener('click', function (e) {

        if (e.target.id === 'registerBtn' || e.target.id === 'modifyBtn') {
            const image = document.querySelectorAll(".note-editable img"); // 게시물에 찐막으로 남아잇는 이미지태그
            const category = document.getElementById('category');
            const title = document.getElementById('title');

            const content = document.querySelector(".note-editable");
            let str = '';

            for (const child of content.children) {
                str += child.innerText.replace(/\s/g, '');

            }
            if (!str && !image.length) {
                alert('내용을 입력해주세요');
                return;
            } else if (category.value === '0') {
                alert('카테고리를 선택해주세요.');
                category.focus();
                return;
            } else if (!title.value) {
                alert('제목을 입력해주세요.');
                title.focus();
                return;
            }

            if (e.target.id === 'registerBtn') {
                if (!confirm('글을 작성하시겠습니까?')) {
                    return;
                }
            } else if (e.target.id === 'modifyBtn') {
                if (!confirm('글을 수정하시겠습니까?')) {
                    return;
                }
            }
            document.getElementById('postForm').submit();
        }


    });

    document.getElementById('thumbnail').addEventListener("change", e => {
        uploadImg(e.target);
    })

    const uploadImg = function (input) {

        if (input.files && input.files[0]) {

            let formData = new FormData();
            formData.append('thumbnailFile', input.files[0]);

            $.ajax({
                data: formData,
                type: "POST",
                url: "/files/thumbnail",
                contentType: false,
                processData: false,
                success: function (data) {
                    const thumbnailUrl = document.getElementById('thumbnailUrl');
                    const thumbnailPreView = document.getElementById('thumbnailPreView');
                    const thumbBox = document.getElementById('thumbBox');
                    const thumbnailId = document.getElementById('thumbnailId');
                    if (thumbnailUrl.value) {
                        document.querySelector(`li[data-id="${thumbnailId.value}"]`).remove();
                        let fileInput = document.getElementById(thumbnailId.value);
                        fileInput.setAttribute('name', 'fileDeleteList');
                    }
                    thumbnailUrl.value = String(data.filePath);
                    thumbnailPreView.src = data.filePath;
                    thumbBox.style.display = '';
                    thumbnailId.value = String(data.fileId);
                    createFileList(data);
                }
            });

        }
    }


};