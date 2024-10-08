const hamBurger = document.querySelector(".toggle-btn");
hamBurger.addEventListener("click", function () {
  document.querySelector("#sidebar").classList.toggle("expand");
});
$(document).ready(function () {
  $(".sidebar-item .sidebar-link").on("click", function (e) {
    // Remove 'active' class from all sidebar links
    $(".sidebar-link").removeClass("active");

    // Add 'active' class to the clicked sidebar link
    $(this).addClass("active");
  });
  var path = window.location.pathname;
  // Loop through each <a> tag in the sidebar
  $(".sidebar-item .sidebar-link").each(function () {
    var href = $(this).attr("href");
    console.log(href);
    // Check if the href attribute matches the current path
    if (href == path) {
      // Add the 'active' class to the parent <li> of the matching <a> tag
      $(this).addClass("active");
    }
  });
});

const updateTableDevice = (data) => {
  const tbody = $("#tableDevice tbody");
  tbody.empty(); // Xóa nội dung hiện tại của tbody
  if (data && data.device && data.device.length > 0) {
    // Lặp qua mảng các đối tượng trong thuộc tính "device"
    data.device.forEach((item, index) => {
      const row = $("<tr>");
      row.append($("<td>").text(index + 1));
      row.append($("<td>").text(item.deviceID)); // Truy cập thuộc tính "deviceID"
      row.append($("<td>").text(item.deviceName)); // Truy cập thuộc tính "deviceName"
      row.append($("<td>").text(item.deviceDescription)); // Truy cập thuộc tính "deviceDescription"
      row.append($("<td>").text(item.borrowedTime)); // Truy cập thuộc tính "borrowedTime"
      tbody.append(row); // Thêm dòng mới vào tbody
    });
  } else {
    tbody.append(
      $("<tr>").append($('<td colspan="5">').text("No data available"))
    );
  }
};

const updateTotalDevices = (data) => {
  $("#totalDevices").text(data.total);
};

const getStatisticDevice = () => {
  const searchDeviceVal = $("#inputSearchDevice").val();
  const selectDeviceVal = $("#selectDevice").val();
  const startDateDevice = $("#inputDateStartDevice").val();
  const endDateDevice = $("#inputDateEndDevice").val();

  // call ajax
  $.ajax({
    url: "/admin/dashboard/statisticDevice",
    type: "POST",
    data: {
      search: searchDeviceVal,
      select: selectDeviceVal,
      startDate: startDateDevice,
      endDate: endDateDevice,
    },
    success: function (data) {
      dataJSON = JSON.parse(data);
      updateTableDevice(dataJSON);
      updateTotalDevices(dataJSON);
    },
    error: function (xhr, status, error) {
      // Xử lý lỗi nếu có
      console.error(error);
      console.error(xhr.responseText);
    },
  });
};

function CheckFineReadonly() {
  var isBoiThuong =
    $("#hinhThucXuLy2").val() === "Bồi thường" ||
    $("#hinhThucXuLy2").val() === "Khóa thẻ 1 tháng và bồi thường";
  $("#soTien2")
    .prop("readonly", !isBoiThuong)
    .val(!isBoiThuong ? "" : $("#soTien2").val());
}

$(document).ready(CheckFineReadonly);

$(document).on("change", "#hinhThucXuLy2", CheckFineReadonly);

function handleDeleteDiscipline(id) {
  window.location.href = `/deleteDiscipline?id=${id}`;
}

//document.getElementById('soTien2').addEventListener('input', function (e) {
//    e.target.value = e.target.value.replace(/[^0-9]/g, '');
//});

$(document).ready(function () {
  $("#inputSearchDevice").on("keypress", function (e) {
    if (e.which === 13) {
      getStatisticDevice();
    }
  });
});

const getStatisticMember = () => {
  const selectedDepartment = $("#departmentForm").val();
  const selectedMajor = $("#majorForm").val();
  const startDateInput = $("#startDateInput").val();
  const endDateInput = $("#endDateInput").val();

  // call ajax
  $.ajax({
    url: "/admin/dashboard/statisticMember",
    type: "POST",
    data: {
      department: selectedDepartment,
      major: selectedMajor,
      startDate: startDateInput,
      endDate: endDateInput,
    },
    success: function (data) {
      dataJSON = JSON.parse(data);
      updateTableMember(dataJSON);
      updateTotalMembers(dataJSON);
    },
    error: function (xhr, status, error) {
      // Xử lý lỗi nếu có
      console.error(error);
      console.error(xhr.responseText);
    },
  });
};

const updateTableMember = (data) => {
  const tbody = $("#tableMember tbody");
  tbody.empty(); // Xóa nội dung hiện tại của tbody
  if (data && data.members && data.members.length > 0) {
    $.each(data.members, function (index, item) {
      const row = $("<tr>");
      row.append($("<td>").text(index + 1));
      row.append($("<td>").text(item.memberID));
      row.append($("<td>").text(item.memberName));
      row.append($("<td>").text(item.memberDepartment));
      row.append($("<td>").text(item.memberMajor));
      row.append($("<td>").text(item.enteredTime));
      tbody.append(row);
    });
  } else {
    tbody.append(
      $("<tr>").append($('<td colspan="6">').text("No data available"))
    );
  }
};

const updateTotalMembers = (data) => {
  $("#totalMembers").text(data.total);
};
