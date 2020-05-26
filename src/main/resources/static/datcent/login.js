GetCookie();
$(function() {
    $(".i-checks").iCheck({checkboxClass:"icheckbox_square-green-login"});
	$('.imgcode').click(function() {
		var url = ctx + "captcha/captchaImage?type=" + captchaType + "&s=" + Math.random();
		$(".imgcode").attr("src", url);
	});
});

$("#signupForm").validate({
    rules: {
        username: {
            required: true
        },
        password: {
            required: true
        }
    },
    submitHandler: function() {
        login();
    }
});

saveInfo = function () {
        var isSave = document.getElementById('rememberme').checked;   //保存按键是否选中
        if (isSave) {
            var username = document.getElementById('username').value;
            var password = document.getElementById('password').value;
                SetCookie(username, password);
        } else {
            SetCookie("", "");
        }
}
function SetCookie(username, password) {
    var Then = new Date();
    Then.setTime(Then.getTime() + 1866240000000);
    document.cookie = "username=" + username + "%%" + password + ";expires=" + Then.toGMTString();
}
function GetCookie() {
    var nmpsd;
    var nm;
    var psd;
    var cookieString = new String(document.cookie);
    var cookieHeader = "username=";
    var beginPosition = cookieString.indexOf(cookieHeader);
        cookieString = cookieString.substring(beginPosition);
    var ends = cookieString.indexOf(";");
    if (ends != -1) {
        cookieString = cookieString.substring(0, ends);
    }
    if (beginPosition > -1) {
        nmpsd = cookieString.substring(cookieHeader.length);
        if (nmpsd != "") {
            beginPosition = nmpsd.indexOf("%%");
            nm = nmpsd.substring(0, beginPosition);
            psd = nmpsd.substring(beginPosition + 2);
            document.getElementById('username').value = nm;
            document.getElementById('password').value = psd;
            if (nm != "" && psd != "") {
                // document.forms[0].checkbox.checked = true;
                document.getElementById('rememberme').checked = true;
            }
        }
    }
}
function login() {
    $.modal.loading($("#btnSubmit").data("loading"));
    var username = $.common.trim($(".login-user input[name='username']").val());
    var password = $.common.trim($(".login-pwd input[name='password']").val());
    var validateCode = $(".login-code input[name='validateCode']").val();
    var rememberMe = $(".login-rem input[name='rememberme']").is(':checked');
    saveInfo();

    $.ajax({
        type: "post",
        url: ctx + "login",
        data: {
            "username": username,
            "password": password,
            "validateCode" : validateCode,
            "rememberMe": rememberMe
        },
        success: function(r) {
            if (r.code == 0) {
                location.href = ctx + 'index';
            } else {
                $.modal.closeLoading();
                $('.imgcode').click();
                $.modal.msg(r.msg);
            }
        }
    });
}




