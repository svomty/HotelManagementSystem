function btnActive(sort) {
    let divTableHeadingWrapper = document.querySelector('.divTableHeading');
    let aWrapper = divTableHeadingWrapper.querySelectorAll("a");
    for (let i = 0; i < aWrapper.length; ++i) {
        if (aWrapper[i].id === sort) {
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

function popup_active(id) {
    let popupBgWrapper = document.querySelector('.answer-popup');
    let aWrapper = popupBgWrapper.querySelectorAll("a");
    for (let i = 0; i < aWrapper.length; ++i) {
        if (aWrapper[i].id === id) {
            aWrapper[i].classList.add('answer-popup__active');
        }
    }
}

function foreign_active() {
    let checkWrapper = document.querySelector('#check');
    let foreignWrapper = document.querySelector('.foreign');
    if (!checkWrapper.checked) {
        foreignWrapper.classList.add('display-none');
    } else {
        foreignWrapper.classList.remove('display-none');
    }
}

function toDefault(selectId, filterId) {
    document.getElementById(filterId).value = "";
    filtering(selectId, filterId);
}

function filtering(selectId, filterId) {
    var keyword = document.getElementById(filterId).value;
    var select = document.getElementById(selectId);

    var find = false;

    for (var i = 0; i < select.length; i++) {

        var value = select.options[i].value;
        var txt = select.options[i].text;
        var include = txt.toLowerCase().includes(keyword.toLowerCase());

        if (!include) {
            select.options[i].classList.add('display-none');
        } else {

            if (!find) {
                document.getElementById(selectId).value = value;
                find = true;
            }
            select.options[i].classList.remove('display-none');

        }

    }
}
