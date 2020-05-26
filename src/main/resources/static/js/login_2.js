$(function () {
    $('#switch_qlogin').click(function () {
        $(".cue").css("display","none");
        $('#switch_login').removeClass("switch_btn_focus").addClass('switch_btn');
        $('#switch_qlogin').removeClass("switch_btn").addClass('switch_btn_focus');
        $('#switch_bottom').animate({left: '-16px', width: '70px'});
        $('#qlogin').css('display', 'none');
        $('#web_qr_login').css('display', 'block');
    });
    $('#switch_login').click(function () {
        $(".cue").css("display","none");
        $('#switch_login').removeClass("switch_btn").addClass('switch_btn_focus');
        $('#switch_qlogin').removeClass("switch_btn_focus").addClass('switch_btn');
        $('#switch_bottom').animate({left: '105px', width: '70px'});
        $('#qlogin').css('display', 'block');
        $('#web_qr_login').css('display', 'none');
    });
    if (getParam("a") == '0') {
        $('#switch_login').trigger('click');
    }
});
function logintab() {
    scrollTo(0);
    $('#switch_qlogin').removeClass("switch_btn_focus").addClass('switch_btn');
    $('#switch_login').removeClass("switch_btn").addClass('switch_btn_focus');
    $('#switch_bottom').animate({left: '154px', width: '96px'});
    $('#qlogin').css('display', 'none');
    $('#web_qr_login').css('display', 'block');
}
// 验证身份证
function isCardNo(card) {
    var pattern = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    return pattern.test(card);
}


//根据参数名获得该参数 pname等于想要的参数名
function getParam(pname) {
    var params = location.search.substr(1); // 获取参数 平且去掉？
    var ArrParam = params.split('&');
    if (ArrParam.length == 1) {
        //只有一个参数的情况
        return params.split('=')[1];
    }
    else {
        //多个参数参数的情况
        for (var i = 0; i < ArrParam.length; i++) {
            if (ArrParam[i].split('=')[0] == pname) {
                return ArrParam[i].split('=')[1];
            }
        }
    }
}

var reMethod = "GET",
    pwdmin = 6;

$(document).ready(function () {

    $('#reg').click(function () {
        $(".cue").css("display","block");
        $('.inputstyle2').focus().css({
            border: "1px solid #D7D7D7",
            boxShadow: "0 0 2px #D7D7D7"
        });
        if ($('#jbqkXm').val() == "") {
            $('#user').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#userCue').html("<font color='red'><b>×姓名不能为空</b></font>");
            return false;
        }

        if ($('#jbqkSfzh').val() == "") {
            $('#jbqkSfzh').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#userCue').html("<font color='red'><b>×身份证号码不能为空</b></font>");
            return false;
        }else{
            $('#userCue').html("");
            if(!isCardNo($('#jbqkSfzh').val())){
                $('#jbqkSfzh').focus().css({
                    border: "1px solid red",
                    boxShadow: "0 0 2px red"
                });
                $('#userCue').html("<font color='red'><b>×身份证号码格式有误</b></font>");
                return false;
            }
        }

        if ($('#jbqkBm').val() == "") {
            $('#jbqkBm').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#userCue').html("<font color='red'><b>×工作部门不能为空</b></font>");
            return false;
        }

        if ($('#jbqkZw').val() == "") {
            $('#jbqkZw').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#userCue').html("<font color='red'><b>×职务不能为空</b></font>");
            return false;
        }

        if($("#qpassWord_one").val() == ""){
            $('#qpassWord_one').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#userCue').html("<font color='red'><b>×口令不能为空</b></font>");
            return false;
        }
        if($("#qpassWord_two").val() == ""){
            $('#qpassWord_two').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#userCue').html("<font color='red'><b>×确认口令不能为空</b></font>");
            return false;
        }
        if($("#qpassWord_one").val() != $("#qpassWord_two").val()){
            $('#qpassWord_one').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#userCue').html("<font color='red'><b>×口令不能为空</b></font>");
            $('#qpassWord_two').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#userCue').html("<font color='red'><b>×两次口令不一致</b></font>");
            return false;
        }else {
            $(".cue").css("display","none");
            $('#userCue').html("");
        }
        // if ($('#passwd').val().length < pwdmin) {
        //     $('#passwd').focus();
        //     $('#userCue').html("<font color='red'><b>×密码不能小于" + pwdmin + "位</b></font>");
        //     return false;
        // }
        // if ($('#passwd2').val() != $('#passwd').val()) {
        //     $('#passwd2').focus();
        //     $('#userCue').html("<font color='red'><b>×两次密码不一致！</b></font>");
        //     return false;
        // }
        // var idcardReg = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;
        //
        // if ($('#idcard').val() != idcardReg) {
        //     $('#idcard').focus();
        //     $('#userCue').html("<font color='red'><b>×请输入正确的身份证号码！</b></font>");
        //     return false;
        // }
        var config = {
            url:ctx+'module/basicinformation/reg',
            type:'post',
            data:$("#regUser").serialize(),
            success:function(result){
                if(result.code==web_status.SUCCESS){
                    $.modal.msgSuccess(result.msg)
                    $('#switch_login').removeClass("switch_btn_focus").addClass('switch_btn');
                    $('#switch_qlogin').removeClass("switch_btn").addClass('switch_btn_focus');
                    $('#switch_bottom').animate({left: '-16px', width: '70px'});
                    $('#qlogin').css('display', 'none');
                    $('#web_qr_login').css('display', 'block');
                }else{
                    $.modal.msgError(result.msg)
                }
            },
            error:function(data){
                $.modal.msgError(data)
            }
        }
        $.ajax(config);
    });

    // $.ajax({
    //     type: reMethod,
    //     url: "",
    //     data: "uid=" + $("#user").val() + '&temp=' + new Date(),
    //     dataType: 'html',
    //     success: function (result) {
    //
    //         if (result.length > 2) {
    //             $('#user').focus().css({
    //                 border: "1px solid red",
    //                 boxShadow: "0 0 2px red"
    //             });
    //             $("#userCue").html(result);
    //             return false;
    //         } else {
    //             $('#user').css({
    //                 border: "1px solid #D7D7D7",
    //                 boxShadow: "none"
    //             });
    //         }
    //
    //     }
    // });
    $("#loginFrm").click(function(){
        $(".cue").css("display","block");
        $('.inputstyle').focus().css({
            border: "1px solid #D7D7D7",
            boxShadow: "0 0 2px #D7D7D7"
        });
        var loginName = $("#loginName").val();
        var loginCardNo = $("#loginCardNo").val();
        var passWord = $("#passWord").val();
        if ($('#loginName').val() == "") {
            $('#loginName').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#loginCue').html("<font color='red'><b>×姓名不能为空</b></font>");
            return false;
        }
        if ($('#loginCardNo').val() == "") {
            $('#loginCardNo').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#loginCue').html("<font color='red'><b>×身份证号码不能为空</b></font>");
            return false;
        }
        if ($('#passWord').val() == "") {
            $('#passWord').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#loginCue').html("<font color='red'><b>×口令不能为空</b></font>");
            return false;
        }else {
            $('#loginCue').html("");
            $(".cue").css("display","none");
        }
        var config = {
            url:ctx+'module/basicinformation/login',
            type:'post',
            data:$("#login_form").serialize(),
            success:function(result){
                if(result.code==web_status.SUCCESS){
                    window.location.href=ctx+"module/basicinformation/loginView?sfz="+result.sfz+"&id="+result.id;
                }else{
                    $.modal.msgError(result.msg)
                }
            },
            error:function(data){
                $.modal.msgError(data)
            }
        }
        $.ajax(config);
    });
    $("#update_btn").click(function(){
        $(".cue").css("display","block");
        $('.inputstyle').focus().css({
            border: "1px solid #D7D7D7",
            boxShadow: "0 0 2px #D7D7D7"
        });
        var sfz = $("#f3_jbqkSfzh").val();
        var pwd = $("#f3_password").val();
        var passWord_one = $("#passWord_one").val();
        var passWord_two = $("#passWord_two").val();
        if(sfz == ''){
            $('#f3_jbqkSfzh').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#frm3Cue').html("<font color='red'><b>×身份证号码不能为空</b></font>");
            return false;
        }
        if(pwd == ''){
            $('#f3_password').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#frm3Cue').html("<font color='red'><b>×旧口令不能为空</b></font>");
            return false;
        }
        if(passWord_one == ''){
            $('#passWord_one').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#frm3Cue').html("<font color='red'><b>×新口令不能为空</b></font>");
            return false;
        }
        if(passWord_two == ''){
            $('#passWord_two').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#frm3Cue').html("<font color='red'><b>×确认口令不能为空</b></font>");
            return false;
        }
        if(passWord_two != passWord_one){
            $('#passWord_one').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#passWord_two').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#frm3Cue').html("<font color='red'><b>×两次输入口令不一致</b></font>");
            return false;
        }else {
            $(".cue").css("display","none");
            $('#frm3Cue').html("");
        }
        var config = {
            url:ctx+'module/basicinformation/password_edit',
            type:'post',
            data:$("#form3").serialize(),
            success:function(result){
                if(result.code == web_status.SUCCESS){
                    $.modal.msgSuccess(result.msg);
                    $("#flogin").css("display","none");
                    $("#web_qr_login").css("display","block");
                    $("#login").css("display","block");
                }else{
                    $.modal.msgError(result.msg)
                }
            },
            error:function(data){
                $.modal.msgSuccess(data)
            }
        };
        $.ajax(config);
    });
});