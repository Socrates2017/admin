

/**
 *输入框失去焦点时触发登录验证码检查方法
 */
$("#login-authCode").blur(function () {
    checkloginauthCode();
});

/**
 * 检查登录验证码是否正确
 * @returns
 */
function checkloginauthCode() {
    var authCode = $("#login-authCode").val();
    if (authCode != undefined && authCode != "") {
        var dataObj = {};
        dataObj.authCode = authCode;
        $.ajax({
            url: "/api/v1/authCode/check",
            type: "POST",
            cache: false,
            contentType: "application/json;charset=utf-8",
            datatype: "json",
            data: JSON.stringify(dataObj),
            success: function (result) {
                if (result.code == 1) {
                    $("#div-authCode-login").addClass("has-success has-feedback");
                    $("#login-authCode").after("<span class='glyphicon glyphicon-ok form-control-feedback' aria-hidden='true'></span>");
                    /*
                     * 如果第一次重复了 换了一个账号之后成功了
                     * 要把第一次加的class和span取消掉
                     */
                    $("#div-authCode-login").removeClass("has-error");
                    $("#div-authCode-login span").remove(".glyphicon-remove");
                } else if (result.code == 10005) {
                    $("#div-authCode-login").addClass("has-error has-feedback");
                    $("#login-authCode").after("<span class='glyphicon glyphicon-remove form-control-feedback' aria-hidden='true'></span>");
                    $("#div-authCode-login").removeClass("has-success");
                    $("#div-authCode-login span").remove(".glyphicon-ok");
                }else{
                    console.log(result)
                }
            }
        });
    } else {
        $("#div-authCode-login").addClass("has-error has-feedback");
        $("#login-authCode").after("<span class='glyphicon glyphicon-remove form-control-feedback' aria-hidden='true'></span>");
        $("#div-authCode-login").removeClass("has-success");
        $("#div-authCode-login span").remove(".glyphicon-ok");
    }
}



/**
 * 提交登录表单
 * @returns
 */
function login() {
    var authCode_class = $("#div-authCode-login").attr("class");
    if (authCode_class.indexOf("glyphicon-remove") > 0) {
        //alert("验证码不正确");
        return false;
    }
    var name = $("#login-name").val();
    var pwd = $("#login-password").val();
    var authCode = $("#login-authCode").val();

    var reqBody = {};
    reqBody.name = name;
    reqBody.pwd = pwd;
    reqBody.authCode = authCode;

    showReflesh();
    $.ajax({
        url: "/api/v1/admin/user/login",
        type: "post",
        contentType: "application/json;charset=utf-8",
        datatype: "json",
        data: JSON.stringify(reqBody),
        success: function (result) {
            removeReflesh();
            let code = result.code;
            if (code == 1) {
                authCodeImg("checkLogin");
                $('#login').modal('hide');

                var pathName = location.pathname;
                if(pathName != '/admin'){
                    location.href =location.href;
                }

            } else if (code == 10001 ||code == 10002) {
                 console.log("token缺失");
                 authCodeImg();
                 $.MsgBox.Confirm("提示", "请重新登录", function () {
                     console.log("登录失败")
                 });
            } else {
                $.MsgBox.Confirm("提示", result.msg, function () {
                    console.log("登录失败")
                });
            }
        },
         error: function (result) {
             removeReflesh();
             $.MsgBox.Confirm("提示", result.msg, function () {});
         }
    });
}

/**
 * 监听 enter事件，按下enter触发登录事件

 document.onkeydown = function(e) {
	var ev = document.all ? window.event : e;
	if (ev.keyCode == 13) {
		login();
	}
}*/

/**
 * 退出登录
 * @returns
 */
function logout() {
    var dataObj = {};
    $.ajax({
        url: "/api/v1/admin/user/logout",
        type: "post",
        contentType: "application/json;charset=utf-8",
        datatype: "json",
        data: JSON.stringify(dataObj),
        success: function (result) {
            let code = result.code;
            if (code == 1) {
                location.reload(true);
            }  else if (code == 10001 ||code == 10002) {
                location.reload(true);
            }else {
                $.MsgBox.Confirm("提示", result.msg, function () {});
            }
        },
      error: function (result) {
          removeReflesh();
          $.MsgBox.Confirm("提示", result.msg, function () {});
      }
    });
}

/**
 * 刷新验证码
 * @param callback
 */
function authCodeImg(callback) {
    var url = '/api/v1/authCode/img';

    showReflesh();

    $.get(url, function (result) {
        $("#img-authCode-login").attr("src", url);
        $("#img-authCode-register").attr("src", url);

        if ($.isBlank(callback)) {
        removeReflesh();
        } else {
            window[callback].call(this);
        }

    });
}

/**
 * 检测用户是否已经登录
 * @returns
 */
function checkLogin() {

    $.ajax({
        url: "/api/v1/admin/user/login/check",
        type: "GET",
        datatype: "json",
        async: true,
        success: function (result) {
            var code = result.code;

             if (code == 1) {
                //获取cookie中的用户id
                var user = result.data;
                var ulHtml = '';
                ulHtml += '<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"> <img id="sm-name" src="';
                if ($.isBlank(user.headImg)) {
                    ulHtml += '/img/default_headImg.jpg"';
                } else {
                    ulHtml += user.headImg;
                }
                ulHtml += 'class="center-block img-circle" style="height: 29px;" alt="图片无效"></a><ul class="dropdown-menu"><li><a href="';
                ulHtml += '/user/center/' + user.id + '" target="_blank">';
                ulHtml += user.name + '<span class="glyphicon glyphicon-cog"></span></a> <a onclick="logout()"> 退出登录 <span class="glyphicon glyphicon-off"></span></a></li></ul></li>';
                /**ulHtml += '<li> <a class="btn btn-default" href="/download/zrzhen2.0.0.apk" download="zrzhen_Android_2.0.0.apk" id="contact_weixin">下载APP</a></li>';*/

                $('#loginNav').html(ulHtml);

                var menu ='<button type="button" class="btn-default" onclick="window.open(\'/admin/article/center\')"><span class="glyphicon glyphicon-book"></span>&ensp;文章管理中心</button>';

                $('#articleCenter').html(menu);

            } else if (code == 10001 ||code == 10002) {
               console.log("登录失效");
               authCodeImg();
               $('#login').modal('show');
            } else {
                $.MsgBox.Confirm("提示", result.msg, function () {});

            }
            removeReflesh();
        },
          error: function (result) {
              removeReflesh();
              $.MsgBox.Confirm("提示", result.msg, function () {});
          }
    });
}

/**
 * 文档加载完毕即执行
 */
$(function () {
    authCodeImg("checkLogin");

});

