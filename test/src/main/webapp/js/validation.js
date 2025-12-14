// 表单验证函数

function validateLoginForm() {
    var username = document.getElementById("username").value.trim();
    var password = document.getElementById("password").value.trim();

    if (username === "") {
        alert("请输入用户名！");
        return false;
    }

    if (password === "") {
        alert("请输入密码！");
        return false;
    }

    if (password.length < 6) {
        alert("密码长度至少6位！");
        return false;
    }

    return true;
}

function validateRegisterForm() {
    var username = document.getElementById("username").value.trim();
    var password = document.getElementById("password").value.trim();
    var confirmPassword = document.getElementById("confirm_password").value.trim();
    var name = document.getElementById("name").value.trim();
    var studentId = document.getElementById("student_id").value.trim();
    var phone = document.getElementById("phone").value.trim();
    var email = document.getElementById("email").value.trim();

    if (username === "") {
        alert("请输入用户名！");
        return false;
    }

    if (username.length < 3) {
        alert("用户名长度至少3位！");
        return false;
    }

    if (password === "") {
        alert("请输入密码！");
        return false;
    }

    if (password.length < 6) {
        alert("密码长度至少6位！");
        return false;
    }

    if (password !== confirmPassword) {
        alert("两次输入的密码不一致！");
        return false;
    }

    if (name === "") {
        alert("请输入姓名！");
        return false;
    }

    if (studentId === "") {
        alert("请输入学号！");
        return false;
    }

    if (phone === "") {
        alert("请输入手机号！");
        return false;
    }

    var phoneRegex = /^1[3-9]\d{9}$/;
    if (!phoneRegex.test(phone)) {
        alert("请输入正确的手机号！");
        return false;
    }

    if (email === "") {
        alert("请输入邮箱！");
        return false;
    }

    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        alert("请输入正确的邮箱地址！");
        return false;
    }

    return true;
}

function validateReservationForm() {
    var roomId = document.getElementById("room_id").value;
    var reservationDate = document.getElementById("reservation_date").value;
    var startTime = document.getElementById("start_time").value;
    var endTime = document.getElementById("end_time").value;

    if (roomId === "" || roomId === "0") {
        alert("请选择自习室！");
        return false;
    }

    if (reservationDate === "") {
        alert("请选择预约日期！");
        return false;
    }

    // 检查日期不能是过去
    var today = new Date();
    today.setHours(0, 0, 0, 0);
    var selectedDate = new Date(reservationDate);
    if (selectedDate < today) {
        alert("不能预约过去的日期！");
        return false;
    }

    if (startTime === "") {
        alert("请选择开始时间！");
        return false;
    }

    if (endTime === "") {
        alert("请选择结束时间！");
        return false;
    }

    if (startTime >= endTime) {
        alert("结束时间必须晚于开始时间！");
        return false;
    }

    return true;
}

function validateRoomForm() {
    var roomName = document.getElementById("room_name").value.trim();
    var location = document.getElementById("location").value.trim();
    var capacity = document.getElementById("capacity").value;

    if (roomName === "") {
        alert("请输入自习室名称！");
        return false;
    }

    if (location === "") {
        alert("请输入位置！");
        return false;
    }

    if (capacity === "" || capacity <= 0) {
        alert("请输入正确的容量！");
        return false;
    }

    return true;
}

// 加载座位信息
function loadSeats(roomId) {
    var seatSelect = document.getElementById("seat_id");
    
    if (!roomId || roomId === "0" || roomId === "") {
        seatSelect.innerHTML = "<option value=''>请先选择自习室</option>";
        return;
    }

    // 显示加载中
    seatSelect.innerHTML = "<option value=''>加载中...</option>";
    seatSelect.disabled = true;

    // 使用AJAX加载座位信息
    var xhr = new XMLHttpRequest();
    // 获取当前页面的context path（从当前页面路径推断）
    var pathname = window.location.pathname;
    var contextPath = '';
    // 如果路径包含项目名（如 /test1/reservation/add），提取项目名
    var parts = pathname.split('/');
    if (parts.length > 1 && parts[1]) {
        contextPath = '/' + parts[1];
    }
    var url = contextPath + "/reservation/getSeats?room_id=" + roomId;
    console.log("请求URL:", url); // 调试用
    xhr.open("GET", url, true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            seatSelect.disabled = false;
            if (xhr.status === 200) {
                try {
                    var responseText = xhr.responseText.trim();
                    if (!responseText || responseText === '') {
                        seatSelect.innerHTML = "<option value=''>该自习室暂无可用座位</option>";
                        return;
                    }
                    var seats = JSON.parse(responseText);
                    seatSelect.innerHTML = "<option value=''>不指定座位（可选）</option>";
                    
                    if (!seats || seats.length === 0) {
                        seatSelect.innerHTML += "<option value=''>该自习室暂无可用座位</option>";
                    } else {
                        for (var i = 0; i < seats.length; i++) {
                            var seat = seats[i];
                            var option = document.createElement("option");
                            option.value = seat.id;
                            option.textContent = seat.seatNumber + (seat.status === 'available' ? ' (可用)' : ' (已占用)');
                            if (seat.status !== 'available') {
                                option.disabled = true;
                            }
                            seatSelect.appendChild(option);
                        }
                    }
                } catch (e) {
                    console.error("加载座位失败:", e, xhr.responseText);
                    seatSelect.innerHTML = "<option value=''>加载失败，请重试</option>";
                }
            } else {
                console.error("AJAX请求失败，状态码:", xhr.status);
                seatSelect.innerHTML = "<option value=''>加载失败，请重试</option>";
            }
        }
    };
    xhr.onerror = function() {
        seatSelect.disabled = false;
        console.error("网络错误");
        seatSelect.innerHTML = "<option value=''>网络错误，请检查连接</option>";
    };
    xhr.send();
}



