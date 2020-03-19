var images = ["apart.jpg", "hotel.jpg", "parking.jpg"];
var tagline = ["Чистые и недорогие апартаменты",
"Возможность онлайн бронирования",
"Охраняемая стоянка"];
var slider = document.querySelector("#slider");
var img = slider.querySelector("img");
var span = slider.querySelector("span");

var i = 1;
img.src = "img/slider/" + images[0];
span.textContent = tagline[0];

window.setInterval(function() {

    img.src = "img/slider/" + images[i];
    span.textContent = tagline[i];

    i++;
    if (i === images.length) {
        i = 0;
    }
}, 3000);