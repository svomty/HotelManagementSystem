function openMenu() {
    let navWrapper = document.querySelector('nav');
    let bodyWrapper = document.querySelector('body');
    if (navWrapper.className.indexOf('active') === -1) {
        navWrapper.classList.add('active');
        bodyWrapper.classList.remove('overflow-hidden');
    } else {
        navWrapper.classList.remove('active');
        bodyWrapper.classList.add('overflow-hidden');
    }
}

function openSubMenu() {
    var subMenuWrapper = document.querySelector('.submenu');
    var className = 'active__submenu';
    if (subMenuWrapper.className.indexOf(className ) === -1) {
        subMenuWrapper.classList.add(className );
    } else {
        subMenuWrapper.classList.remove(className );
    }
}