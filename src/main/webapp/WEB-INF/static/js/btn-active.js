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
