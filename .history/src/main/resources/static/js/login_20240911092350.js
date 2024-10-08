window.onload = function () {
    var input = document.getElementById('usernameForm');
    input.addEventListener('input', function (e) {
        // Loại bỏ tất cả các ký tự không phải là số từ giá trị nhập
        this.value = this.value.replace(/\D/g, '');
    });
};