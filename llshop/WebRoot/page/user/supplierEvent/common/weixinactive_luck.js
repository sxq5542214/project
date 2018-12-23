$(function () {

    var needpaysign = $("#needpaysign").val();
    var payprice = $("#payprice").val();
    var returnprice = $("#returnprice").val();

    //验证码操作
    $(".vCodeBtn").click(function () {
        var userPhone = $("#regtell").val();
        if (!isMobile(userPhone)) {
            alert('请输入有效的手机号');
            return;
        }

        verify.send(userPhone, function (lock) {
            $(".vCodeBtn").toggleClass('disabled', lock).prop('disabled', lock);
            if (!lock) {
                $(".vCodeBtn").text('重发验证码');
            }
        }, function (seconds) {
            $(".vCodeBtn").text(seconds + '秒后可重发');
        });
    });

    //window.httpsWebUrl = "/";
    var verifyUrl = window.httpsWebUrl + 'Account/Verify';
    var verifyNewUserUrl = window.httpsWebUrl + 'Account/VerifyNewUser';
    var verify = {
        send: function (number, lock, update) {
            var self = this;
            if (self._timer) {
                return;
            }
            self._startTimer(lock, update);
            $.post(verifyUrl, {
                action: 'send',
                number: number
            }, 'json').then(function (r) {
                if (!r.ok) {
                    self._stopTimer(lock);
                    alert('系统故障，请稍后重试或联系技术支持');
                }
            }).fail(function () {
                self._stopTimer(lock);
                alert('网络请求失败');
            })
        },
        check: function (number, code) {
            return $.post(verifyNewUserUrl, {
                action: 'check',
                number: number,
                code: code
            }, 'json').then(function (r) {
                return r.ok || true ? contacts._store(number) : $.Deferred().reject(new Error('短信校验码有误'));
            });
        },
        _startTimer: function (lock, update) {
            var self = this;
            self._dest = new Date().getTime() + 60 * 1000;
            self._timer = setInterval(function () {
                var seconds = Math.max(0, ((self._dest - new Date) / 1000) | 0);
                seconds > 0 ? update(seconds) : self._stopTimer(lock);
            }, 1000);
            lock(true);
        },
        _stopTimer: function (lock) {
            clearInterval(this._timer);
            this._timer = null;
            lock(false);
        }
    };

    var isMobile = function (val) {
        return phoneNumReg.test(val);
    };

    function regXmas()
    {
        var activeid = $("#activeid").val();
        var partnerid = $("#partnerid").val();
        var openid = $("#openid").val();
        var unionid = $("#unid").val();
        var username = $("#username").val();
        var phone = $("#regtell").val();

        if (phone == "") {
            alert("请输入您的手机号");
            return;
        }

        if (phone.length != 11) {
            alert("手机号码有误");
            return;
        }

        var dic = {};
        dic["openid"] = openid;
        dic["unionid"] = unionid;
        dic["username"] = username;
        dic["phone"] = phone;
        dic["activeid"] = activeid;
        dic["partnerid"] = partnerid;
        dic["needpaysign"] = needpaysign;
        dic["payprice"] = payprice;
        dic["returnprice"] = returnprice;

        $.get('/Active/SignUpWeixinLuckActive', dic, function (dic) {
            if (dic.state == "-1")
            {
                //强制关注
                $(".mustfollow-md").show();
                $(".mustfollow-panel").show();
            }
            else if (dic.state == "1")
            {
                //成功
                location.href = "/Active/Weixin_LuckActive_RegDone/" + activeid + "?openid=" + openid;
            }
            else
            {
                //失败
                alert("报名失败，请重试！");
            }
        });
    }
    $(".submit").click(regXmas);

    //立即支付
    function subpay()
    {
        var activeid = $("#activeid").val();
        var partnerid = $("#partnerid").val();
        var openid = $("#openid").val();
        var unionid = $("#unid").val();
        var regName = $("#regName").val();
        var phone = $("#regtell").val();
        var vCode = $("#vCode").val();

        //if (regName == "") {
        //    alert("请输入您的姓名");
        //    return;
        //}

        if (phone == "") {
            alert("请输入您的手机号");
            return;
        }

        if (phone.length != 11) {
            alert("手机号码有误");
            return;
        }

        if (vCode == "") {
            alert("请输入短信验证码");
            return;
        }

        $.post(verifyNewUserUrl, {
            action: 'check',
            number: phone,
            code: vCode
        }, 'json').then(function (r) {
            if (r.ok == "1") {
                
                var dic = {};
                dic["openid"] = openid;
                dic["unionid"] = unionid;
                dic["username"] = regName;
                dic["phone"] = phone;
                dic["activeid"] = activeid;
                dic["partnerid"] = partnerid;
                dic["needpaysign"] = needpaysign;
                dic["payprice"] = payprice;
                dic["returnprice"] = returnprice;

                $.get('/Active/SignUpWeixinLuckActive', dic, function (dic) {
                    //强制关注
                    if (dic.state == "-1") {

                        $(".mustfollow-md").show();
                        $(".mustfollow-panel").show();
                    }
                        //成功
                    else if (dic.state == "1") {
                        location.href = "/Active/Weixin_LuckActive_RegDone/" + activeid + "?openid=" + openid;
                    }
                        //去支付
                    else if (dic.state == "2") {
                        location.href = dic.payurl;
                    }
                    else {
                        //失败
                        alert("报名失败，请重试！");
                    }
                });

            }
            else {
                alert("短信验证码输入有误");
            }
        });
    }
    $(".subpay").click(subpay);

    //分享给好友
    $("#sharefd-btn").click(function ()
    {
        $(".sharetofriend-panel").fadeIn(300);
        $(".sharetofriend-md").fadeIn(300);
    });
    $(".sharetofriend-md").click(function ()
    {
        $(".sharetofriend-md").fadeOut(300);
        $(".sharetofriend-panel").fadeOut(300);
    });
    $(".sharetofriend-panel").click(function () {
        $(".sharetofriend-md").fadeOut(300);
        $(".sharetofriend-panel").fadeOut(300);
    });
    $("#close-fundalter").click(function () {
        $(".fundalter-md").fadeOut(300);
        $(".fundalter-panel").fadeOut(300);
    });
});