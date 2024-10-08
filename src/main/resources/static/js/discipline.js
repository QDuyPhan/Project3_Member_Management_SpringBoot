$("#soTien").prop('readonly', true);
$("#soTien2").prop('readonly', true);
$(document).on("change","#hinhThucXuLy, #hinhThucXuLy2",function (){
    if($(this).val()==="Bồi thường" || $(this).val()==="Khóa thẻ 1 tháng và bồi thường"){
        $("#soTien").prop('readonly', false);
        $("#soTien2").prop('readonly', false);
    }else{
        $("#soTien").prop('readonly', true);
        $("#soTien2").prop('readonly', true);
    }
})