window.onload = function () {

    document.getElementById('deleteBtn')?.addEventListener('click', function () {

        const check = confirm('해당 글을 삭제하시겠습니까?');

        if (check) {

            $.ajax({
                type: "delete",
                url: `/post/delete/${this.value}`,
                contentType: false,
                processData: false,
                success: function () {
                    location.href = '/';
                }
            });
        }
    });

    const replyBox = document.getElementById("commentBox");
    const roleArr = document.getElementById('roleArr').value;
    const userId = document.getElementById('userId')?.value;
    let urlSearch = new URLSearchParams(location.search);
    let postId = urlSearch.get("postId");

    const commentList = () => {
        $.ajax({
            type: "get",
            url: `/comment/list/${postId}`,
            contentType: false,
            processData: false,
            success: function (data) {
                makeCommentBox(data, replyBox);
            }
        });
    }

    /*댓글 내용 유효성 검사*/
    const commentValidation = content => {
        if (!content.replace(/\s/g, '')) {
            alert("댓글을 작성해주세요");
            return true;
        }
    }

    commentList();

    /* 댓글 등록 api */
    document.getElementById('commentInsertBtn').addEventListener('click', () => {
        if (userId == null) {
            alert('댓글 작성 하시려면 로그인 해주세요.')
            if (confirm('로그인 하시겠습니까?')) location.href = '/login';
        }

        let content = document.getElementById("commentContent")?.value;
        let isSecret = document.getElementById("isCommentSecret").checked;

        if (commentValidation(content)) {
            return;
        }

        let commentForm = {};
        commentForm.content = content;
        commentForm.secret = isSecret;
        commentForm.postId = postId;

        $.ajax({
            url: "/comment/write",
            type: 'post',
            traditional: true,
            data: JSON.stringify(commentForm),
            contentType: 'application/json',
            success: function () {
                commentList();
                document.getElementById("commentContent").value = '';
                document.getElementById('isCommentSecret').checked = false
            },
            error: function (e) {
                console.log(e);
            }
        });
    });

    document.getElementById('unauthenticatedArea')?.addEventListener('click', () => {
        if (confirm('로그인 하시겠습니까?')) {
            location.href = '/login';
        }
    });

    document.addEventListener('click', function (e) {
        if (e.target.className.includes('fa-trash')) { // 댓글 삭제 api
            let check = confirm('댓글을 정말 삭제하시겠습니까?');
            if (check) {
                $.ajax({
                    url: `/comment/delete/${e.target.value}`,
                    type: 'delete',
                    success: function () {
                        commentList();
                    },
                    error: function (e) {
                        console.log(e);
                    }
                });
            }
        } else if (e.target.className.includes('child-insertBtn')) { // 대댓글 등록 api
            let content = document.getElementById(`child-content-${e.target.value}`).value;
            let isSecret = document.getElementById(`isCommentSecret-${e.target.value}`).checked;

            if (commentValidation(content)) {
                return;
            }

            let commentForm = {};
            commentForm.content = content;
            commentForm.secret = isSecret;
            commentForm.postId = postId;
            commentForm.parentId = e.target.value;

            $.ajax({
                url: "/comment/write",
                type: 'post',
                traditional: true,
                data: JSON.stringify(commentForm),
                contentType: 'application/json',
                success: function () {
                    commentList();
                    document.getElementById("commentContent").value = '';
                    document.getElementById('isCommentSecret').checked = false
                },
                error: function (e) {
                    console.log(e);
                }
            });
        }
    });

    function makeCommentBox(dtoList, replyBox) {

        // 초기화
        replyBox.innerHTML = ' ';
        const TIME_ZONE = 3240 * 10000;

        // 부모 댓글 리스트
        for (const parentComment of dtoList) {
            let replyHtmlSource = ' ';

            const createdDate = new Date(parentComment.createdDate);
            let date = new Date(+createdDate + TIME_ZONE).toISOString().replace('T', ' ').replace(/\..*/, '');

            replyHtmlSource +=
                `<div class="mt-2 comment-box" id="commentBox">
    <div class="d-flex flex-row p-2">
        <div>
            <img src="${parentComment.imgUrl}" width="40" height="40"
                 class="rounded-circle me-2" alt="">
        </div>
        <div class="w-100">
            <div class="d-flex justify-content-between align-items-center">
                <span class="me-2">${parentComment.nickname}</span>
                <div class="d-flex flex-row align-items-center">
                    <small>${date}</small>`

            if (userId === String(parentComment.userId) || roleArr === '[ROLE_MANAGER]') {
                replyHtmlSource +=
                    `<button class="btn p-0 m-0 fas fa-trash ms-3" value="${parentComment.commentId}"></button>`
            }
            replyHtmlSource +=
                `</div>
            </div>`

            // 자식댓글을 가지고 있는 부모댓글 상태변경처리
            if (parentComment.deleteStatus === true) {
                replyHtmlSource += `<p class="text-justify comment-text mb-0" style="white-space:pre-wrap;">삭제된 댓글입니다.</p>`;
            } else if (parentComment.secret === true) { // 부모 댓글 비밀댓글 처리
                if (roleArr === '[ROLE_MANAGER]') {
                    replyHtmlSource += `<p class="text-justify comment-text mb-0" style="white-space:pre-wrap;">${parentComment.content}(비밀댓글입니다)</p>`
                } else if (userId === String(parentComment.userId)) {
                    replyHtmlSource += `<p class="text-justify comment-text mb-0" style="white-space:pre-wrap;">${parentComment.content}(내가 쓴 비밀댓글입니다)</p>`;
                } else {
                    replyHtmlSource += `<p class="text-justify comment-text mb-0" style="white-space:pre-wrap;">비밀댓글입니다.</p>`;
                }
            } else {
                replyHtmlSource += `<p class="text-justify comment-text mb-0" style="white-space:pre-wrap;">${parentComment.content}</p>`;
            }

            <!-- comment-reply-post -->
            replyHtmlSource +=
                `<div class="comment-reply">
                <div style="background-color: #ebebeb;">
                    <button class="btn align-items-center rounded collapsed" data-bs-toggle="collapse"
                            data-bs-target="#comment-reply-${parentComment.commentId}" aria-expanded="false">
                        <i class="fa fa-comments-o me-2"></i> 대댓글
                    </button>
                </div>`


            if (userId != null) {
                replyHtmlSource +=
                    `<div class="collapse" id="comment-reply-${parentComment.commentId}">
                    <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                        <li>
                            <div class="mt-3 d-flex flex-row align-items-center p-2 form-color">
                                <div>
                                </div>
                                <textarea id="child-content-${parentComment.commentId}" name="child-content-${parentComment.commentId}" class="form-control" placeholder="대댓글을 작성해주세요..."></textarea>
                            </div>
                            <div class="d-flex">
                <div class="ms-1 mt-2 secretBox">
                    <label class="lock">
                        <input type="checkbox" id="isCommentSecret-${parentComment.commentId}" value="true"/>
                        <span></span>
                    </label>
                </div>
                                <div class="ms-auto ms-1 me-2">
                                    <button class="btn btn-sm btn-secondary child-insertBtn" value="${parentComment.commentId}">등 록</button>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>`
            }

            // 자식 댓글 리스트
            for (const childComment of parentComment.child) {

                const createdDate = new Date(childComment.createdDate);
                let date = new Date(+createdDate + TIME_ZONE).toISOString().replace('T', ' ').replace(/\..*/, '');

                replyHtmlSource +=
                    `<div class="d-flex flex-row p-2">
                <div>
                    <img src="${childComment.imgUrl}" width="40"
                         height="40" class="rounded-circle me-2" alt="">
                </div>
                <div class="w-100">
                    <div class="d-flex justify-content-between align-items-center">
                        <span class="me-2">${childComment.nickname}</span>
                        <div class="d-flex flex-row align-items-center">
                            <small>${date}</small>`

                if (userId === String(childComment.userId) || roleArr === '[ROLE_MANAGER]') {
                    replyHtmlSource +=
                        `<button class="btn p-0 m-0 fas fa-trash ms-3" value="${childComment.commentId}"></button>`
                }
                replyHtmlSource +=
                    `</div>
                    </div>`

                // 자식댓글 비밀댓글 처리
                if (childComment.secret === true) {
                    if (roleArr === '[ROLE_MANAGER]' || userId === String(childComment.userId)) {
                        replyHtmlSource += `<p class="text-justify comment-text mb-0" style="white-space:pre-wrap;">${childComment.content}(비밀댓글입니다)</p>`
                    } else if (userId === String(childComment.userId)) {
                        replyHtmlSource += `<p class="text-justify comment-text mb-0" style="white-space:pre-wrap;">${childComment.content}(내가 쓴 비밀댓글입니다)</p>`;
                    } else {
                        replyHtmlSource += `<p class="text-justify comment-text mb-0" style="white-space:pre-wrap;">비밀댓글입니다.</p>`;
                    }
                } else {
                    replyHtmlSource += `<p class="text-justify comment-text mb-0" style="white-space:pre-wrap;">${childComment.content}</p>`
                }

                replyHtmlSource +=
                    `</div>
            </div>`
            }

            // 부모댓글 리스트 마무리
            replyHtmlSource +=
                `</div>
            </div>
        </div>
    </div> `

            replyBox.innerHTML += replyHtmlSource;
        }
    }

};