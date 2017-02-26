<%-- 
    Document   : login
    Created on : 2016-8-13, 23:00:22
    Author     : Meteor
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

        <script src="//cdn.bootcss.com/jquery/1.12.4/jquery.js"></script>
        <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

        <title>登陆</title>

        <style>
            body
            {
                font-family: "微软雅黑";
                background-color: #353580;
            }
            header{
                font-family: "华文新魏";
                text-align:center;
                color: white;
            }
            #login_form
            {
                background-color:gainsboro;
                height:auto;
                width: 300px;
                padding: 20px;
                border-radius: 5px;
                margin: 0 auto;
                font-size: 1em;
            }
            #login_link {
                margin: 0 auto;
                width: 300px;
                font-size: 0.9em;
            }
            .label_text {
                font-weight: 500;
                font-size: 1em;
            }
        </style>

    </head>
    <body>
        <div id="login_div">
            <header>
                <br/>
                <img src="http://meteorx.cn:8080/xwfxDev/img/CN_sifa_lim[limit].png" height="200px" width="200px" alt="海门市司法社区矫正"/>
                <br/><br/>
                <span style="font-weight: 500;font-size: 2.8em;letter-spacing: 2px;">
                    PBA System 司法社区矫正管理系统
                </span>
                <br/><br/>
            </header>
            <div id="login_form">
                <form method="post" action="loginAction" id="login_from" role="form" style="position: relative;top: 8px;">
                    <div class="form-group" class="control-label">
                        <label class="label_text" for="username">用户名：</label>
                        <input type="text" class="form-control input-sm" id="username" name="userName" placeholder="Your Username">
                    </div>
                    <div class="form-group">
                        <label class="label_text" for="password" class="control-label">密码：</label>
                        <input type="password" class="form-control input-sm" id="password" name="passWord" placeholder="Your Password">
                    </div>
                    <br/>
                    <div class="form-group">
                        <label class="label_text">
                            <input type="checkbox"> 记住我？
                        </label>
                        <button type="submit" class="btn btn-default btn-sm" style="float: right">确定</button>
                    </div>
                </form>
            </div>
            <div id="login_link">
                <br/>
                <a>忘记密码？</a>
                <br/><br/>
                <a>转到系统支持页面</a>
            </div>
        </div>
    </body>
</html>

