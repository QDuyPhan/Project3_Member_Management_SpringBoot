
let buttons = document.querySelectorAll('button#reservationButton');
buttons.forEach((button, _) => {
    button.addEventListener('click', function () {
        alert('hello world');
        console.log('run')
    });
});