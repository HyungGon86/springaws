window.onload = function () {

    document.getElementById('deleteBtn').addEventListener('click', function () {

        const check = confirm('해당 글을 삭제하시겠습니까?');

        if (check) {

            $.ajax({
                type: "delete",
                url: `/post/delete/${this.value}`,
                contentType: false,
                processData: false,
                success: function () {
                    history.back();
                }
            });
        }
    });

};