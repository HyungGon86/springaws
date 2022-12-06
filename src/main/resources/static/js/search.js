function searchKeyword() {

    const sKeyword = document.getElementById("searchInput");
    const keyword = sKeyword.value.replace(/\s/g, '');

    if (!keyword) {
        alert('검색어를 입력해주세요.');
        return;
    }

    location.href = `/post/list/search/?keyword=${keyword}&page=0`;
}

document.getElementById('searchInput').addEventListener('keyup', event => {

    if (event.key === 'Enter') {
        searchKeyword();
    }
});