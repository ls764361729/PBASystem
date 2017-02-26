<%--
    Document   : frame
    Created on : 2016-8-19, 15:28:45
    Author     : Meteor
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html;charset=utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="shortcut icon" type="image/x-icon" href="http://meteorx.cn:8080/xwfxDev/img/CN_sifa_head.ico" media="screen" />

        <link rel="stylesheet" href="http://meteorx.cn/arcgis_js_api/library/3.17/3.17/esri/css/esri.css">
        <link rel="stylesheet" href="http://meteorx.cn/arcgis_js_api/library/3.17/3.17/dijit/themes/claro/claro.css">
        <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

        <link href="http://meteorx.cn:8080/xwfxDev/css/body.css" rel="stylesheet" type="text/css"/>
        <link href="http://meteorx.cn:8080/xwfxDev/css/floatDiv.css" rel="stylesheet" type="text/css"/>
        <link href="http://meteorx.cn:8080/xwfxDev/css/frame.css" rel="stylesheet" type="text/css"/>
        <link href="http://meteorx.cn:8080/xwfxDev/css/iconfont.css" rel="stylesheet" type="text/css"/>
        <link href="http://meteorx.cn:8080/xwfxDev/css/loader.css" rel="stylesheet" type="text/css"/>


        <!-- jQuery old version
        <script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.0.js"></script>
        -->
        <script src="http://meteorx.cn/arcgis_js_api/library/3.17/3.17/init.js"></script>
        <script src="//cdn.bootcss.com/jquery/1.12.4/jquery.js"></script>
        <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

        <script src="http://meteorx.cn:8080/xwfxDev/js/divAct.js" type="text/javascript"></script>
        <script src="http://meteorx.cn:8080/xwfxDev/js/loading.js" type="text/javascript"></script>
        <script src="http://meteorx.cn:8080/xwfxDev/js/mapLoad.js" type="text/javascript"></script>
        <script src="http://meteorx.cn:8080/xwfxDev/js/menuAct.js" type="text/javascript"></script>
        <script src="http://meteorx.cn:8080/xwfxDev/js/mapSpatialConv.js" type="text/javascript"></script>


        <script>
            window.onload = function () {
                $("#LoadCover").hide();
            };
            <%--
            <%if (session.getAttribute("userName") != null && (Boolean) session.getAttribute("userActivity")) {%>
            window.onload = function () {
                $("#LoadCover").hide();
            };
            
            <%} else {%>
            $("#Loading_Notice_Error").show("fast");
            alert("您未登录，即将跳转至登录页面")
            setTimeout(function () {
                location.href = "login/login.jsp";
            }, 5000);
           
            <%}%>
           --%>

            function LogOut() {
                $.ajax({
                    type: 'post',
                    url: "action/divDataPushAction",
                    data: {
                        pushType: "LogOut"
                    },
                    datatype: "text",
                    success: function () {
                        $("#LoadCover").show("fast");
                        $("#Loading_Notice_Success").show();
                        setTimeout(function () {
                            location.href = "login/login.jsp";
                        }, 5000);
                    },
                    error: function () {
                        alert("退出失败。")
                    }
                });
            }
        </script>

        <title>PBA System 司法社区矫正管理系统</title>
    </head>
    <body>
        <div id="LoadCover" class="container-fluid">
            <div class="alert alert-danger Loading_Notice" role="alert" id="Loading_Notice_Error">
                您未登录，5秒后将进入登录页面。
            </div>
            <div class="alert alert-success Loading_Notice" role="alert" id="Loading_Notice_Success">
                退出成功，即将进入登录页面。
            </div>
            <div id="loadCenter">
                <div id="loadLeft">
                    <h1 style="font-weight: 700;">正在进入</h1>
                    <p style="font-size: 1.3em;font-weight: 400;">PBA System 司法社区矫正管理系统</p>
                    <br />
                    <h3 calss="h_weight">系统状态：<span id="sysState" class="label_load label label-warning">未知</span></h3>
                    <h3 calss="h_weight">在库人数：<span id="databasePep" class="label_load label label-warning">X人</span></h3>
                    <h3 calss="h_weight">在线人数：<span id="onlinePep" class="label_load label label-warning">X人</span></h3>
                </div>
                <div id="loadRight">
                    <img id="loadMap_Haimen" src="http://meteorx.cn:8080/xwfxDev/img/Himen_SimpleMap_White_lim[limit].png" height="240px" width="260px" />
                </div>
            </div>
            <div class="loader">
                <div class="loader-inner line-scale-pulse-out">
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                    <div></div>
                </div>
            </div>
        </div>

        <div id="frame">
            <header>
                <div id="headImg">
                    <img src="http://meteorx.cn:8080/xwfxDev/img/CN_sifa_lim[limit].png" width="50" height="50" >
                </div>
                <div id="headText">
                    <p><span style="font-family: 'Times New Roman';font-size: 0.8em">PBA System</span> 司法社区矫正管理系统</p>
                </div>
                <div id="headICO">
                    <a href="#" role="button" class="headicon" onclick="$('#basemapDiv').toggle('fast')" >&#xe620;</a>
                    <a href="#" role="button" class="headicon" onclick="$('#userInfDiv').toggle('fast')" >&#xe63b;</a>
                    <button class="btn btn-sm btn-primary" id="messageBtn" type="button" onclick="$('#messageDiv').toggle('fast')" >
                        Message<i id="messageBtn_ICO">&#xe62f;</i><span class="badge" id="messageBtn_Badge">0</span>
                    </button>
                </div>
            </header>
            <div id="display">

                <nav id="toolBar">
                    <ul class="toolBarItem">
                        <li id="MessageTool">
                            <a href="#" role="button" class="toolicon side_menu_message">&#xe60f;</a>
                            <span class="nav-text">&nbsp;&nbsp;信息功能</span>
                        </li>
                        <li id="ArchiveTool">
                            <a href="#" role="button" class="toolicon">&#xe622;</a>
                            <span class="nav-text">&nbsp;&nbsp;档案管理</span>
                        </li>
                        <li id="ObjMonitTool">
                            <a href="#" role="button" class="toolicon">&#xe621;</a>
                            <span class="nav-text">&nbsp;&nbsp;对象监测</span>
                        </li>
                        <li id="SysSetTool">
                            <a href="#" role="button" class="toolicon">&#xe624;</a>
                            <span class="nav-text">&nbsp;&nbsp;系统设置</span>
                        </li>
                        <li id="FunHelpTool">
                            <a href="#" role="button" class="toolicon">&#xe610;</a>
                            <span class="nav-text">&nbsp;&nbsp;功能帮助</span>
                        </li>
                    </ul>
                </nav>

                <div class="secMenu" id="MesToolMenu">
                    <ul>
                        <li><a href="#" role="button" onclick="$('#MesDiv_Send').show('fast')" >&nbsp;&nbsp;发送消息</a></li>
                        <li><a href="#" role="button" onclick="$('#MesDiv_History').show('fast')">&nbsp;&nbsp;历史信息</a></li>
                    </ul>
                </div>
                <div class="secMenu" id="ArchToolMenu">
                    <ul>
                        <li><a href="#" role="button" onclick="ArchListLoad()" >&nbsp;&nbsp;矫正人员列表</a></li>
                        <li><a href="#" role="button" onclick="$('#ArchDiv_Query_Update').show('fast')" >&nbsp;&nbsp;查询与修改</a></li>
                        <li><a href="#" role="button" onclick="$('#ArchDiv_Add').show('fast')" >&nbsp;&nbsp;添加矫正人员</a></li>
                    </ul>
                </div>
                <div class="secMenu" id="ObjMonToolMenu">
                    <ul>
                        <li><a href="#" role="button" onclick="$('#ObjMonDiv_Identify').show('fast')" >&nbsp;&nbsp;用户鉴定</a></li>
                        <li><a href="#" role="button" onclick="AtAreaLoad()" >&nbsp;&nbsp;区域报警</a></li>
                        <li><a href="#" role="button" onclick="SenScoreLoad()" >&nbsp;&nbsp;行为分析</a></li>
                    </ul>
                </div>
                <div class="secMenu" id="SysSetToolMenu">
                    <ul>
                        <li><a href="#" role="button" onclick="$('#SysSetDiv_UIset').show('fast')" >&nbsp;&nbsp;界面设置</a></li>
                        <li><a href="#" role="button" onclick="$('#SysSetDiv_Password').show('fast')" >&nbsp;&nbsp;密码修改</a></li>
                        <li><a href="#" role="button" onclick="LoadUserList()" >&nbsp;&nbsp;用户管理</a></li>
                    </ul>
                </div>
                <div class="secMenu" id="FunHelpToolMenu">
                    <ul>
                        <li><a href="#" role="button" onclick="$('#FunHelpDiv_Qusetion').show('fast')" >&nbsp;&nbsp;常见问题</a></li>
                        <li><a href="#" role="button" onclick="$('#FunHelpDiv_About').show('fast')" >&nbsp;&nbsp;关于</a></li>
                    </ul>
                </div>

                <div id="mapDiv"></div>

                <div id="basemapDiv" class="panel panel-primary rightHeadFloatDiv">
                    <div class="divTitle panel-heading">切换图层<a class="divicon" href="#" role="button" onclick="$('#basemapDiv').hide('fast')" style="float: right;color: white !important;">&#xe634;</a></div>
                    <div id="basemapGalleryDiv" class="panel-body"></div>
                </div>

                <div id="userInfDiv" class="panel panel-default rightHeadFloatDiv">
                    <a class="divicon divButton_closeDiv" href="#" role="button" onclick="$('#userInfDiv').hide('fast')">&#xe634;</a>
                    <div class="panel-body">
                        <img src="http://meteorx.cn:8080/xwfxDev/img/DefaultUserPhoto_lim[limit].png" class="img-circle" height="120px" width="120px" >
                        <table class="table table-condensed" style="position: relative;top: 15px">
                            <tr>
                                <td>用户：<span><%=(String) session.getAttribute("userName")%></span></td>
                                <td>姓名：<span><%=(String) session.getAttribute("userRealName")%></span></td>
                            </tr>
                            <tr>
                                <td>类型：<span><%=(String) session.getAttribute("userType")%></span></td>
                                <td>单位：<span><%=(String) session.getAttribute("userFrom")%></span></td>
                            </tr>
                        </table>
                    </div>
                    <div class="panel-footer">
                        <button type="button" class="btn btn-default btn-sm" onclick="location.href = 'login/login.jsp'">切换用户</button>
                        <button type="button" class="btn btn-primary btn-sm" onclick="LogOut()">登出</button>
                    </div>

                </div>
                <div id="messageDiv" class="panel panel-primary">
                    <div class="panel-heading">
                        信息列表
                        <a class="divicon" href="#" role="button" onclick="$('#messageDiv').hide('fast')" style="float: right;color: white !important;">&#xe634;</a>
                        <a class="divicon" href="#" role="button" onclick="$('#messageDiv_table').empty();$('#messageBtn_Badge').text(0);$('#messageDiv').hide('fast');" style="float: right;color: white !important;">&#xe606;</a>
                    </div>
                    <table id="messageDiv_table" style="color: black" class="divTable table table-striped" >
                        <tr><td>监控对象：郝建豪 敏感评分数据超限，请及时处理</td></tr>
                    </table>
                </div>

                <div class="floatDiv panel panel-default" id="MesDiv_Send">
                    <a class="divicon divButton_closeDiv" href="#" role="button" onclick="$('#MesDiv_Send').hide('fast')">&#xe61e;</a>
                    <div class="divTitle panel-heading">信息发送</div>
                    <div class="panel-body">
                        需要通信技术支持
                    </div>
                </div>
                <div class="floatDiv panel panel-default" id="MesDiv_History">
                    <a class="divicon divButton_closeDiv" href="#" role="button" onclick="$('#MesDiv_History').hide('fast')">&#xe61e;</a>
                    <div class="divTitle panel-heading">历史信息</div>
                    <div class="panel-body">
                        需要通信技术支持
                    </div>
                </div>

                <div class="floatDiv panel panel-default" id="ArchDiv_List">
                    <a class="divicon divButton_closeDiv" href="#" role="button" onclick="$('#ArchDiv_List').hide('fast')">&#xe61e;</a>
                    <div class="divTitle panel-heading">矫正人员列表</div>
                    <table id="ArchDiv_List_Table" class="divTable table table-striped">
                        <tr><th>ID</th><th>姓名</th><th>设备代码</th><th>身份证号</th><th>性别</th><th>年龄</th><th>服刑开始时间</th><th>服刑期限</th></tr>
                    </table>
                </div>
                <div class="floatDiv panel panel-default" id="ArchDiv_Query_Update">
                    <a class="divicon divButton_closeDiv" href="#" role="button" onclick="$('#ArchDiv_Query_Update').hide('fast')">&#xe61e;</a>
                    <div class="divTitle panel-heading">查询与修改</div>
                    <div class="panel-body">
                        <p>请选择查询字段并输入数据</p>
                        <br/>
                        <div class="row">
                            <div class="col-lg-6">
                                <div class="input-group">
                                    <select class="form-control" id="ArchDiv_Query_Type">
                                        <option value="pepName" selected="selected">姓名</option>
                                        <option value="deviceID">设备ID</option>
                                        <option value="pepID">序号</option>
                                        <option value="pepIDcardNum">身份证号</option>
                                        <option value="pepSex">性别</option>
                                        <option value="pepAge">年龄</option>
                                        <option value="startDate">服刑时间</option>
                                        <option value="manaDayLimit">服刑期限</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-lg-6">
                                <div class="input-group">
                                    <input id="ArchDiv_Query_Input" type="text" class="form-control" placeholder="Search for...">
                                    <span class="input-group-btn">
                                        <button id="ArchDiv_Query_Submit" onclick="ArchListQuery()" class="btn btn-default" type="button">查询</button>
                                    </span>
                                </div><!-- /input-group -->
                            </div><!-- /.col-lg-6 -->
                        </div><!-- /.row -->
                    </div>
                    <br/>
                    <table id="ArchDiv_Query_Table" class="divTable table table-striped">
                        <tr><th>ID</th><th>姓名</th><th>设备代码</th><th>身份证号</th><th>性别</th><th>年龄</th><th>服刑开始时间</th><th>服刑期限</th></tr>
                    </table>
                </div>
                <div class="floatDiv panel panel-default" id="ArchDiv_Add">
                    <a class="divicon divButton_closeDiv" href="#" role="button" onclick="$('#ArchDiv_Add').hide('fast')">&#xe61e;</a>
                    <div class="divTitle panel-heading">添加矫正人员</div>
                    <br/>
                    <div class="alert alert-success ArchDiv_Add_Notice" role="alert" id="ArchDiv_Add_Notice_Success">
                        添加矫正人员成功
                        <a class="divicon divButton_closeDiv" href="#" role="button" style="color: gray" onclick="$('#ArchDiv_Add_Notice_Success').hide('fast')">&#xe61e;</a>
                    </div>
                    <div class="alert alert-danger ArchDiv_Add_Notice" role="alert" id="ArchDiv_Add_Notice_Error">
                        发生错误，添加矫正人员失败，请检查您的输入
                        <a class="divicon divButton_closeDiv" href="#" role="button" style="color: gray" onclick="$('#ArchDiv_Add_Notice_Error').hide('fast')">&#xe61e;</a>
                    </div>
                    <div class="panel-body" style="width: 95%">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label for="name" class="col-sm-2 control-label">姓名</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="input_pep_name" placeholder="Name" required="required">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="deviceID" class="col-sm-2 control-label">设备ID</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="input_pep_deviceID" placeholder="Device ID" required="required">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="IDcardNum" class="col-sm-2 control-label">身份证号</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="input_pep_IDcardNumber" placeholder="IDcard Number" required="required">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="sex" class="col-sm-2 control-label">性别</label>
                                <div class="col-sm-10">
                                    <select class="form-control" id="input_pep_sex">
                                        <option value="男" selected="selected">男</option>
                                        <option value="女">女</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="age" class="col-sm-2 control-label">年龄</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="input_pep_age" placeholder="Age">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="startDate" class="col-sm-2 control-label">开始日期</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="input_pep_startDate" placeholder="YYYYMMDD">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="dayLimit" class="col-sm-2 control-label">服刑期限（天）</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="input_pep_dayLimit" placeholder="Day Limit">
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="panel-footer" style="text-align: right">
                        <button type="button" onclick="ManaPepAdd()" class="btn btn-primary" style="position: relative;right: 50px">添加</button>
                    </div>
                </div>

                <div class="floatDiv panel panel-default" id="ObjMonDiv_Identify">
                    <a class="divicon divButton_closeDiv" href="#" role="button" onclick="$('#ObjMonDiv_Identify').hide('fast')">&#xe61e;</a>
                    <div class="divTitle panel-heading">目标鉴定</div>
                    <div class="panel-body">
                        需要 “语音识别技术” 或 “面部识别技术” 支持
                    </div>
                </div>
                <div class="floatDiv panel panel-default" id="ObjMonDiv_AreaMon">
                    <a class="divicon divButton_closeDiv" href="#" role="button" onclick="$('#ObjMonDiv_AreaMon').hide('fast')">&#xe61e;</a>
                    <div class="divTitle panel-heading">区域监控</div>
                    <div class="panel-body">
                        区域监控功能正在开发中
                        <div class="btn-group" style="float: right;">
                            <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                                功能设置 <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="#" role="button" onclick="AtAreaAdd()" >监控区域添加</a></li>
                                <li><a href="#">监控区域删改</a></li>
                                <li class="divider"></li>
                                <li><a href="#">区域监控历史</a></li>
                            </ul>
                        </div>
                    </div>
                    <table id="ObjMonDiv_AreaMon_Table" class="divTable  table table-striped">
                        <tr><th>ID</th><th>区域名称</th><th>区域ID</th><th>区域点序</th><th>坐标经度</th><th>坐标纬度</th></tr>
                    </table>
                </div>
                <div class="floatDiv panel panel-default" id="ObjMonDiv_BehAny">
                    <a class="divicon divButton_closeDiv" href="#" role="button" onclick="$('#ObjMonDiv_BehAny').hide('fast')">&#xe61e;</a>
                    <div class="divTitle panel-heading">行为分析</div>
                    <div class="panel-body">
                        默认显示为前一日的自动行为分析计算分数
                        <div class="btn-group" style="float: right;">
                            <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                                功能设置 <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="#" role="button" onclick="BehAnlSet()" >敏感区域添加</a></li>
                                <li><a href="#">敏感区域删改</a></li>
                                <li><a href="#">计算阈值自定义</a></li>
                                <li class="divider"></li>
                                <li><a href="#">敏感分数统计</a></li>
                            </ul>
                        </div>
                    </div>
                    <table id="ObjMonDiv_BehAny_Table" class="divTable  table table-striped">
                        <tr><th>ID</th><th>姓名</th><th>设备代码</th><th>敏感计分</th><th>计算时间</th><th>计算日期</th></tr>
                    </table>
                </div>

                <div class="floatDiv panel panel-default" id="ObjMonDiv_AreaMonAdd">
                    <a class="divicon divButton_closeDiv" href="#" role="button" onclick="$('#ObjMonDiv_AreaMonAdd').hide('fast')">&#xe61e;</a>
                    <div class="divTitle panel-heading">监控区域添加</div>
                    <div class="panel-body">
                        <div class="alert alert-success ObjMonDiv_Add_Notice" role="alert" id="ObjMonDiv_atAdd_Notice_Success">
                            添加成功
                        </div>
                        <div class="alert alert-danger ObjMonDiv_Add_Notice" role="alert" id="ObjMonDiv_atAdd_Notice_Error">
                            发生错误，添加失败，请检查您的输入
                        </div>
                        <p>请点击“绘制多边形”按钮从地图上选择点，双击以完成添加</p>
                        <div id="ataInfo">
                            <div>
                                <button id="Polygon">绘制多边形</button>
                            </div>
                        </div>
                        <br/>
                        <div>
                            <form class="form">
                                <div class="form-group">
                                    <label for="input_atarea_name">监控区域名称</label>
                                    <div>
                                        <input type="text" class="form-control" id="input_atarea_name" placeholder="Alart Area Name" required="required">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="input_atarea_atpID">多边形点序</label>
                                    <div>
                                        <input type="text" class="form-control" id="input_area_atpID" placeholder="atpID" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="input_atarea_x">坐标点经度</label>
                                    <div>
                                        <input type="text" class="form-control" id="input_atarea_x" placeholder="Coord X" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="input_atarea_y">坐标点纬度</label>
                                    <div>
                                        <input type="text" class="form-control" id="input_atarea_y" placeholder="Coord Y" >
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="panel-footer">
                            <button type="button" class="btn btn-primary" style="float: right" onclick="AtAreaAdd_Submit();">确认添加</button>
                        </div>
                    </div>
                </div>           
                <div class="floatDiv panel panel-default" id="ObjMonDiv_SenAreaAdd">
                    <a class="divicon divButton_closeDiv" href="#" role="button" onclick="$('#ObjMonDiv_SenAreaAdd').hide('fast')">&#xe61e;</a>
                    <div class="divTitle panel-heading">敏感区域添加</div>
                    <div class="panel-body">
                        <div class="alert alert-success ObjMonDiv_Add_Notice" role="alert" id="ObjMonDiv_Add_Notice_Success">
                            添加成功
                        </div>
                        <div class="alert alert-danger ObjMonDiv_Add_Notice" role="alert" id="ObjMonDiv_Add_Notice_Error">
                            发生错误，添加失败，请检查您的输入
                        </div>
                        <p>请点击“绘制点”按钮从地图上选择点，或直接输入敏感区域中心点的坐标</p>
                        <div id="info">
                            <div>
                                <button id="Point">绘制点</button><br/>
                            </div>
                        </div>
                        <br/>
                        <div>
                            <form class="form">
                                <div class="form-group">
                                    <label for="input_area_name">敏感区域名称</label>
                                    <div>
                                        <input type="text" class="form-control" id="input_area_name" placeholder="Area Name" required="required">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="input_area_type">区域类型</label>
                                    <div>
                                        <select class="form-control" id="input_area_type">
                                            <option value="1" selected="selected">中型洗浴中心</option>
                                            <option value="2">大型洗浴中心</option>
                                            <option value="3">小型大众浴池</option>
                                            <option value="4">KTV</option>
                                            <option value="5">舞厅</option>
                                            <option value="6">小型游戏厅</option>
                                            <option value="7">正规游戏厅</option>
                                            <option value="8">台球厅</option>
                                            <option value="9">网吧</option>
                                            <option value="10">旱冰场</option>
                                            <option value="11">棋牌室</option>
                                            <option value="12">美发店</option>
                                            <option value="13">正规按摩店</option>
                                            <option value="14">小型按摩店</option>
                                            <option value="15">主题酒吧</option>
                                            <option value="16">大型酒吧</option>
                                            <option value="17">娱乐城/夜总会</option>
                                            <option value="18">旅馆</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="input_area_x">中心经度</label>
                                    <div>
                                        <input type="text" class="form-control" id="input_area_x" placeholder="Coord X" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="input_area_y">中心纬度</label>
                                    <div>
                                        <input type="text" class="form-control" id="input_area_y" placeholder="Coord Y" >
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="panel-footer">
                            <button type="button" class="btn btn-primary" style="float: right" onclick="SenAreaAdd();">确认添加</button>
                        </div>
                    </div>
                </div>

                <div class="floatDiv panel panel-default" id="SysSetDiv_UIset">
                    <a class="divicon divButton_closeDiv" href="#" role="button" onclick="$('#SysSetDiv_UIset').hide('fast')">&#xe61e;</a>
                    <div class="divTitle panel-heading">界面设置</div>
                    <div class="panel-body">
                        正在建设……
                    </div>
                </div>
                <div class="floatDiv panel panel-default" id="SysSetDiv_Password">
                    <a class="divicon divButton_closeDiv" href="#" role="button" onclick="$('#SysSetDiv_Password').hide('fast')">&#xe61e;</a>
                    <div class="divTitle panel-heading">密码修改</div>
                    <div class="alert alert-success Password_Change_Notice" role="alert" id="Password_Change_Notice_Success">
                        修改成功
                    </div>
                    <div class="alert alert-danger Password_Change_Notice" role="alert" id="Password_Change_Notice_Error">
                        发生错误，修改失败，请检查您的输入
                    </div>
                    <form class="form">
                        <div class="form-group">
                            <label for="input_update_password_oldPassword">旧密码</label>
                            <div>
                                <input type="text" class="form-control" id="input_update_password_oldPassword" placeholder="您原先的密码" required="required">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="input_update_password_newPassword">新密码</label>
                            <div>
                                <input type="password" class="form-control" id="input_update_password_newPassword" placeholder="您的新密码" >
                            </div>
                        </div>
                    </form>
                    <div class="panel-footer">
                        <button type="button" class="btn btn-primary" style="float: right;position: relative;bottom: 5px;" onclick="PasswordChange();">确认更改</button>
                    </div>
                </div>
                <div class="floatDiv panel panel-default" id="SysSetDiv_UserManage" style="overflow:scroll;overflow-x:hidden;">
                    <a class="divicon divButton_closeDiv" href="#" role="button" onclick="$('#SysSetDiv_UserManage').hide('fast')">&#xe61e;</a>
                    <div class="divTitle panel-heading" >用户列表</div>
                    <table id="userListTable" class="table table-striped">
                    </table>
                </div>

                <div class="floatDiv panel panel-default" id="FunHelpDiv_Qusetion">
                    <a class="divicon divButton_closeDiv" href="#" role="button" onclick="$('#FunHelpDiv_Qusetion').hide('fast')">&#xe61e;</a>
                    <div class="divTitle panel-heading">常见问题</div>
                </div>
                <div class="floatDiv panel panel-default" id="FunHelpDiv_About" >
                    <a class="divicon divButton_closeDiv" href="#" role="button" onclick="$('#FunHelpDiv_About').hide('fast')">&#xe61e;</a>
                    <div class="divTitle panel-heading">关于</div>
                    <div class="panel-body">
                        <h1>PBA System</h1>
                        <p>Personality Behavior Analysis System</p>
                        <p>追求精准、快捷、科学的社区矫正个性化行为分析系统</p>
                        <p>版本：0.8.1 dev</p>
                        <a role="button" class="btn btn-primary" href="Mailto:mstar2012@163.com">与我联系</a>
                        <br/><br/><br/><br/>
                    </div>
                    <div class="panel-footer" style="font-size: 0.7em;">
                        <br/>
                        <p>PBA System</p>
                        <p>版权所有： 2016 南通大学 地理科学学院 孙晨星 保留所有权利。</p>
                        <p>PBA System 的诞生离不开各类开源软件的支持。</p>
                        <p>此版本仅为参与Esri开发大赛使用，所用数据皆为虚拟数据</p>
                        <a href="http://www.miibeian.gov.cn" target="_blank">豫ICP备16013438号</a>
                    </div>
                </div>

            </div>
        </div>
        <audio src="audio/warring.wav" preload="metadata" id="audioWaring" >
            您的浏览器不支持 audio 标签。
        </audio>
    </body>
</html>

