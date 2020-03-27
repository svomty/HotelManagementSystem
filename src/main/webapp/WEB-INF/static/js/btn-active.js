function btnActive(sort) {
    let divTableHeadingWrapper = document.querySelector('.divTableHeading');
    let aWrapper = divTableHeadingWrapper.querySelectorAll("a");
    for (let i = 0; i < aWrapper.length; ++i) {
        if (aWrapper[i].id === sort){
            aWrapper[i].classList.add('btn-active');
        } else {
            aWrapper[i].classList.add('btn');
        }
    }
}

function resizePage(href) {
    let page_size = document.getElementById("page_size").value;
    document.location.href = href + "&size=" + page_size;
}

function goToPage(href) {
    let page = document.getElementById("pageNo").value;
    document.location.href = href + "&page=" + page;
}