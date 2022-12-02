window.onload = function () {
    const replyBox = document.getElementById("commentBox");


    /* 댓글 등록 api */
    document.getElementById('commentInsertBtn').addEventListener('click', () => {
        let content = document.getElementById("commentContent").value;
        let isSecret = document.getElementById("isCommentSecret").checked;

        let urlSearch = new URLSearchParams(location.search);
        let postId = urlSearch.get("postId");

        /*댓글 내용 유효성 검사*/
        if (!content.replace(/\s/g, '')) {
            alert("댓글을 작성해주세요");
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
            dataType: "text",
            success: function (data) {
                if (String(data) === 'false') {
                    alert('인증된 유저 정보가 존재하지 않습니다.');
                    return;
                }
                // makeCommentBox(xhr, replyBox);
                document.getElementById("commentContent").value = '';
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


    /* 댓글 작성 */
    const commentWrite = () => {


        /*const xhr = new XMLHttpRequest();
        xhr.open("POST", "/comment/write?articleId=" + [[${article.getId()}]]);
        xhr.setRequestHeader("content-type", "application/json");
        xhr.setRequestHeader("X-XSRF-TOKEN", token);
        xhr.send(JSON.stringify(commentForm));

        xhr.onload = () => {
            if (xhr.status === 200 || xhr.status === 201 || xhr.status === 202) {
                makeCommentBox(xhr, replyBox);
                document.getElementById("commentContent").value = '';
            } else {
                alert(xhr.response);
            }
        }*/


    }

    /*function deleteCommentConfirm(commentId) {

        if (confirm("댓글을 정말 삭제하시겠습니까?") === true) {
            deleteComment(commentId);
        } else {
            return false;
        }
    }

    function deleteComment(commentId) {
        let token = getCsrfToken();

        const xhr = new XMLHttpRequest();
        xhr.open("POST", "/comment/delete?commentId=" + commentId + "&articleId=" + [[${article.getId()}]]);
        xhr.setRequestHeader("content-type", "application/json");
        xhr.setRequestHeader("X-XSRF-TOKEN", token);
        xhr.send();

        xhr.onload = () => {
            makeCommentBox(xhr, replyBox);
        }
    }*/

    /*function makeCommentBox(xhr, replyBox) {

        const roleArr = [[${#authentication.authorities}]];

        if (xhr.status === 200 || xhr.status === 201 || xhr.status === 202) {

            let commentList = JSON.parse(xhr.response);

            let member = null;
            if ([[${member}]] != null) {
                member = [[${member}]];
            }

            // 초기화
            replyBox.innerHTML = ' ';

            // 부모 댓글 리스트
            let idx = 1;
            for (const parentComment of commentList) {
                let replyHtmlSource = ' ';

                parentComment.createdDate[1]--;
                let date = moment(parentComment.createdDate).format('YY-MM-DD HH:mm:ss');

                replyHtmlSource +=
                    `<div class="mt-2 comment-box" id="commentBox">
    <div class="d-flex flex-row p-2">
        <div>
            <img src="${parentComment.picUrl}" width="40" height="40"
                 class="rounded-circle me-2" alt="">
        </div>
        <div class="w-100">
            <div class="d-flex justify-content-between align-items-center">
                <span class="me-2">${parentComment.username}</span>
                <div class="d-flex flex-row align-items-center">
                    <small>${date}</small>`

                if (member != null && member.id === parentComment.memberId) {
                    replyHtmlSource +=
                        `<button class="btn p-0 m-0 fas fa-trash ms-3"
                            onclick="deleteCommentConfirm(${parentComment.id});return false">
                         </button>`
                }
                replyHtmlSource +=
                    `</div>
            </div>`

                // 부모 댓글 비밀댓글 처리
                if (parentComment.secret === true) {
                    if (roleArr[0].authority === "ROLE_ADMIN") {
                        replyHtmlSource += `<p class="text-justify comment-text mb-0" style="white-space:pre-wrap;">${parentComment.content}(비밀댓글입니다)</p>`
                    } else if (member != null && member.id === parentComment.memberId) {
                        replyHtmlSource += `<p class="text-justify comment-text mb-0" style="white-space:pre-wrap;">${parentComment.content}(내가 쓴 비밀댓글입니다)</p>`;
                    } else {
                        replyHtmlSource += `<p class="text-justify comment-text mb-0" style="white-space:pre-wrap;">비밀댓글입니다.</p>`;
                    }
                } else {
                    replyHtmlSource += `<p class="text-justify comment-text mb-0" style="white-space:pre-wrap;">${parentComment.content}</p>`
                }

                <!-- comment-reply-post -->
                replyHtmlSource +=
                    `<div class="comment-reply">
                <div style="background-color: #ebebeb;">
                    <button class="btn align-items-center rounded collapsed" data-bs-toggle="collapse"
                            data-bs-target="#comment-reply-${parentComment.id}" aria-expanded="false">
                        <i class="fa fa-comments-o me-2"></i> 대댓글
                    </button>
                </div>`


                if (member != null) {
                    replyHtmlSource +=
                        `<div class="collapse" id="comment-reply-${parentComment.id}">
                    <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                        <li>
                            <div class="mt-3 d-flex flex-row align-items-center p-2 form-color">
                                <div>
                                </div>
                                <textarea id="child-content-${parentComment.id}" name="child-content-${parentComment.id}" class="form-control" placeholder="대댓글을 작성해주세요..."></textarea>
                            </div>
                            <div class="d-flex">
                <div class="ms-1 mt-2 secretBox">
                    <label class="lock">
                        <input type="checkbox" id="isCommentSecret-${parentComment.id}" value="true"/>
                        <span></span>
                    </label>
                </div>
                                <div class="ms-auto ms-1 me-2">
                                    <button class="btn btn-sm btn-secondary" onclick="cCommentWrite(${parentComment.id})">등 록</button>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>`
                }

                // 자식 댓글 리스트
                for (const childComment of parentComment.commentDtoList) {

                    childComment.createdDate[1]--;
                    let date = moment(childComment.createdDate).format('YY-MM-DD HH:mm:ss');

                    replyHtmlSource +=
                        `<div class="d-flex flex-row p-2">
                <div>
                    <img src="${childComment.picUrl}" width="40"
                         height="40" class="rounded-circle me-2" alt="">
                </div>
                <div class="w-100">
                    <div class="d-flex justify-content-between align-items-center">
                        <span class="me-2">${childComment.username}</span>
                        <div class="d-flex flex-row align-items-center">
                            <small>${date}</small>`

                    if (member != null && member.id === childComment.memberId) {
                        replyHtmlSource +=
                            `<button class="btn p-0 m-0 fas fa-trash ms-3"
                                        onclick="deleteCommentConfirm(${childComment.id});return false">
                                     </button>`
                    }
                    replyHtmlSource +=
                        `</div>
                    </div>`

                    // 자식댓글 비밀댓글 처리
                    if (childComment.secret === true) {
                        if (roleArr[0].authority === "ROLE_ADMIN" || member != null && member.id === parentComment.memberId) {
                            replyHtmlSource += `<p class="text-justify comment-text mb-0" style="white-space:pre-wrap;">${childComment.content}(비밀댓글입니다)</p>`
                        } else if (member != null && member.id === childComment.memberId) {
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
//

                replyBox.innerHTML += replyHtmlSource;
            }
        }
    }*/

    /*function cCommentWrite(parentCommentId) {
        let token = getCsrfToken();
        let content = document.getElementById("child-content-" + parentCommentId).value;
        let isSecret = document.getElementById("isCommentSecret-" + parentCommentId).checked;


        let commentForm = {};
        commentForm.content = content;
        commentForm.secret = isSecret;

        const xhr = new XMLHttpRequest();
        xhr.open("POST", "/comment/write?parentId=" + parentCommentId + "&articleId=" + [[${article.getId()}]]);
        xhr.setRequestHeader("content-type", "application/json");
        xhr.setRequestHeader("X-XSRF-TOKEN", token);
        xhr.send(JSON.stringify(commentForm));

        xhr.onload = () => {
            makeCommentBox(xhr, replyBox);
            document.getElementById("commentContent").value = '';
        }
    }*/


};

/* 댓글 목록 불러오는거 개발해야함 */
document.addEventListener("DOMContentLoaded", () => {
        let urlSearch = new URLSearchParams(location.search);
        let postId = urlSearch.get("postId");

        $.ajax({
            type: "get",
            url: `/comment/list/${postId}`,
            contentType: false,
            processData: false,
            success: function (data) {
                console.log(data);
                for (const dto of data) {
                    console.log(dto);
                }
                // makeCommentBox(xhr, replyBox);
            }
        });
    }
);


