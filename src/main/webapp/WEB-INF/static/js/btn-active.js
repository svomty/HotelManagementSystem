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

//function setDate(object, page, size, sort) {
function setDate(object) {
    var params = window
        .location
        .search
        .replace('?', '')
        .split('&')
        .reduce(
            function (p, e) {
                var a = e.split('=');
                p[decodeURIComponent(a[0])] = decodeURIComponent(a[1]);
                return p;
            },
            {}
        );

    document.getElementById(object).classList.remove('display-none');

    var arrival_date = params['arrival_date_filter'];
    var departure_date = params['departure_date_filter'];
    var arrival_date1 = document.getElementById("arrival_date").value;
    var departure_date1 = document.getElementById("departure_date").value;

    var page;
    var size;
    var sort;

    if (!page || !size || !sort) {
        page = params['page'];
        size = params['size'];
        sort = params['sort'];
        document.getElementById("page").value = page;
        document.getElementById("size").value = size;
        document.getElementById("sort").value = sort;
    }

    if (departure_date && 0 !== departure_date.length && arrival_date && 0 !== arrival_date.length) {
        //alert(2);
        if (arrival_date > departure_date) {
            //alert(3);
            var temp = arrival_date;
            arrival_date = departure_date;
            departure_date = temp;
        }

        if (arrival_date === departure_date) {
            //alert(4);
            document.getElementById("error_filter").classList.remove('display-none');
            document.getElementById(object).classList.add('display-none');
        } else {
            //alert(5);
            document.getElementById(object).classList.remove('display-none');
            document.getElementById("arrival_date").value = arrival_date;
            document.getElementById("arrival_date_filter").value = arrival_date;
            document.getElementById("departure_date_filter").value = departure_date;
            document.getElementById("departure_date").value = departure_date;
        }


    } else {
        //alert(6);
        if (!departure_date1 || 0 === departure_date1.length || !arrival_date1 || 0 === arrival_date1.length
            || "" === departure_date1 || "" === arrival_date1) {
            //alert(7);
            document.getElementById(object).classList.add('display-none');
        } else {
            //alert(8);
            if (departure_date1 === arrival_date1) {
                document.getElementById(object).classList.add('display-none');
                //alert(9);
            } else {
                //alert(10);
                window
                    .location
                    .search = "?arrival_date_filter=" + arrival_date1 + "&departure_date_filter=" + departure_date1 +
                    "&page=" + page + "&size=" + size + "&sort=" + sort.value();
                /*window
                    .location
                    .search = "?arrival_date_filter=" + arrival_date1 + "&departure_date_filter=" + departure_date1;*/
            }

        }

    }

}

function setDate2(object) {
    var params = window
        .location
        .search
        .replace('?', '')
        .split('&')
        .reduce(
            function (p, e) {
                var a = e.split('=');
                p[decodeURIComponent(a[0])] = decodeURIComponent(a[1]);
                return p;
            },
            {}
        );

    document.getElementById(object).classList.add('display-none');

    var arrival_date = params['arrival_date_filter'];
    var departure_date = params['departure_date_filter'];

    if (arrival_date === undefined && departure_date === undefined) {

        let arrival_date = new Date().toISOString();
        let newDate = new Date();
        newDate.setDate(newDate.getDate() + 1);
        let departure_date = newDate.toISOString();

        window
            .location
            .search = "?arrival_date_filter=" + arrival_date.slice(0, 10) + "&departure_date_filter=" + departure_date.slice(0, 10);
    }

    if (departure_date && 0 !== departure_date.length && arrival_date && 0 !== arrival_date.length) {
        if (arrival_date > departure_date) {
            var temp = arrival_date;
            arrival_date = departure_date;
            departure_date = temp;
        }

        if (arrival_date === departure_date) {
            document.getElementById("error_filter").classList.remove('display-none');

            document.getElementById(object).classList.add('display-none');
        } else {
            document.getElementById(object).classList.remove('display-none');
            document.getElementById("arrival_date_filter").value = arrival_date;
            document.getElementById("departure_date_filter").value = departure_date;
        }

    } else {
        if (arrival_date.length !== 0 && departure_date.length !== 0) {
            window
                .location
                .search = "?arrival_date_filter=" + arrival_date + "&departure_date_filter=" + departure_date;
        } else {
            document.getElementById(object).classList.add('display-none');
            document.getElementById("error_filter2").classList.remove('display-none');
        }

    }

}

function setDate3() {

    var arrival_date = document.getElementById("arrival_date_filter").value;
    var departure_date = document.getElementById("departure_date_filter").value;

    document.location.href = "/reservation/?arrival_date_filter=" + arrival_date + "&departure_date_filter=" + departure_date;

    alert();
}

function setView() {
    var params = window
        .location
        .search
        .replace('?', '')
        .split('&')
        .reduce(
            function (p, e) {
                var a = e.split('=');
                p[decodeURIComponent(a[0])] = decodeURIComponent(a[1]);
                return p;
            },
            {}
        );

    var page = params['page'];
    var size = params['size'];
    var sort = params['sort'];

    document.getElementById("page").value = page;
    document.getElementById("size").value = size;
    document.getElementById("sort").value = sort;
}

function findButtonActive() {
    let filter = document.querySelector('.poisk-filter');

    if (filter.style.visibility === 'hidden' || filter.style.display === 'none') {
        filter.style.display = 'block';
        filter.style.visibility = 'visible';
    } else {
        filter.style.visibility = 'hidden';
        filter.style.display = 'none';
    }
}