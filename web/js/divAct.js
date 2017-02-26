function ArchListLoad() {

    $('#ArchDiv_List').show('fast');
    $.ajax({
        type: 'get',
        url: "action/divDataLoadAction",
        data: {requestType: "ArchListLoad"},
        dataType: "json",
        success: function (archListJson) {
            $("#ArchDiv_List_Table").empty();
            console.dir(archListJson);
            var trTitle = "<tr><th>ID</th><th>姓名</th><th>设备代码</th><th>身份证号</th>" +
                    "<th>性别</th><th>年龄</th><th>服刑开始时间</th><th>服刑期限</th></tr>"
            $("#ArchDiv_List_Table").append(trTitle);
            $.each(archListJson, function (i, archJson) {
                var trData = $("<tr><td>" + archJson.pepID +
                        "</td><td>" + archJson.pepName +
                        "</td><td>" + archJson.deviceID +
                        "</td><td>" + archJson.pepIDcardNum +
                        "</td><td>" + archJson.pepSex +
                        "</td><td>" + archJson.pepAge +
                        "</td><td>" + archJson.startDate +
                        "</td><td>" + archJson.manaDayLimit +
                        "</td></tr>");
                $("#ArchDiv_List_Table").append(trData);
            });
        }
    });
}

function ArchListQuery() {

    $.ajax({
        type: 'post',
        url: "action/divDataLoadAction",
        data: {
            requestType: "ArchListQuery",
            queryType: $("#ArchDiv_Query_Type").val(),
            queryInput: $("#ArchDiv_Query_Input").val(),
        },
        dataType: "json",
        success: function (archQueryJson) {
            $("#ArchDiv_Query_Table").empty();
            console.dir(archQueryJson);
            var trTitle = "<tr><th>ID</th><th>姓名</th> <th>设备代码</th><th>身份证号</th>" +
                    "<th>性别</th><th>年龄</th><th>服刑开始时间</th><th>服刑期限</th></tr>"
            $("#ArchDiv_Query_Table").append(trTitle);
            $.each(archQueryJson, function (i, archJson) {
                var trData = $("<tr><td>" + archJson.pepID +
                        "</td><td>" + archJson.pepName +
                        "</td><td>" + archJson.deviceID +
                        "</td><td>" + archJson.pepIDcardNum +
                        "</td><td>" + archJson.pepSex +
                        "</td><td>" + archJson.pepAge +
                        "</td><td>" + archJson.startDate +
                        "</td><td>" + archJson.manaDayLimit +
                        "</td></tr>");
                $("#ArchDiv_Query_Table").append(trData);
            });
        }
    });
}

function ManaPepAdd() {
    $.ajax({
        type: 'post',
        url: "action/divDataPushAction",
        data: {
            pushType: "ManaPepAdd",
            pepName: $("#input_pep_name").val(),
            deviceID: $("#input_pep_deviceID").val(),
            pepIDcardNum: $("#input_pep_IDcardNumber").val(),
            pepSex: $("#input_pep_sex").val(),
            pepAge: $("#input_pep_age").val(),
            startDate: $("#input_pep_startDate").val(),
            manaDayLimit: $("#input_pep_dayLimit").val()
        },
        datatype: "text",
        success: function (flag) {
            if (flag == "true")
                $("#ArchDiv_Add_Notice_Success").show("fast");
            else
                $("#ArchDiv_Add_Notice_Error").show("fast");
        },
        error: function () {
            $("#ArchDiv_Add_Notice_Error").show("fast");
        }
    });
}

function SenScoreLoad() {

    var maxSenScore = 100;
    var minSenScore = 50
    $('#ObjMonDiv_BehAny').show('fast');
    $.ajax({
        type: 'get',
        url: "action/divDataLoadAction",
        data: {requestType: "SenScoreDayLoad"},
        dataType: "json",
        success: function (senScoreListJson) {
            $("#ObjMonDiv_BehAny_Table").empty();
            console.dir(senScoreListJson);
            var trTitle = "<tr><th>ID</th><th>姓名</th><th>设备代码</th><th>敏感计分</th>" +
                    "<th>计算时间</th><th>计算日期</th></tr>"
            $("#ObjMonDiv_BehAny_Table").append(trTitle);
            $.each(senScoreListJson, function (i, senJson) {
                var trData;
                if(parseInt(senJson.senScore)>=maxSenScore){
                    trData = $("<tr class='danger'><td>" + senJson.pepID +
                        "</td><td>" + senJson.pepName +
                        "</td><td>" + senJson.deviceID +
                        "</td><td>" + senJson.senScore +
                        "</td><td>" + senJson.calcTime +
                        "</td><td>" + senJson.clacDate +
                        "</td></tr>");
                }
                else if(parseInt(senJson.senScore)>=minSenScore&&parseInt(senJson.senScore)<=maxSenScore){
                    trData = $("<tr class='warning'><td>" + senJson.pepID +
                        "</td><td>" + senJson.pepName +
                        "</td><td>" + senJson.deviceID +
                        "</td><td>" + senJson.senScore +
                        "</td><td>" + senJson.calcTime +
                        "</td><td>" + senJson.clacDate +
                        "</td></tr>"); 
                }
                else{
                    trData = $("<tr class='success'><td>" + senJson.pepID +
                        "</td><td>" + senJson.pepName +
                        "</td><td>" + senJson.deviceID +
                        "</td><td>" + senJson.senScore +
                        "</td><td>" + senJson.calcTime +
                        "</td><td>" + senJson.clacDate +
                        "</td></tr>"); 
                }
                
                $("#ObjMonDiv_BehAny_Table").append(trData);
                
            });
        }
    });
}

function BehAnlSet() {
    $("#ObjMonDiv_BehAny").hide("fast");
    $("#ObjMonDiv_SenAreaAdd").show("fast");
}

function SenAreaAdd() {
    $.ajax({
        type: 'post',
        url: "action/divDataPushAction",
        data: {
            pushType: "SenAreaAdd",
            areaName: $("#input_area_name").val(),
            areaTypeID: $("#input_area_type").val(),
            aCoordX: $("#input_area_x").val(),
            aCoordY: $("#input_area_y").val()
        },
        datatype: "text",
        success: function (flag) {
            if (flag == "true")
                $("#ObjMonDiv_Add_Notice_Success").show("fast");
            else
                $("#ObjMonDiv_Add_Notice_Error").show("fast");
        },
        error: function () {
            $("#ObjMonDiv_Add_Notice_Error").show("fast");
        }
    });
}

function LoadUserList() {
    $('#SysSetDiv_UserManage').show('fast');
    $.ajax({
        type: 'get',
        url: "action/divDataLoadAction",
        data: {requestType: "UserListLoad"},
        dataType: "json",
        success: function (userListJson) {
            $("#userListTable").empty();
            console.dir(userListJson);
            var trTitle = "<tr><th>ID</th><th>用户名</th><th>姓名</th><th>归属</th>" +
                    "<th>类型</th><th>权限</th><th>状态</th></tr>"
            $("#userListTable").append(trTitle);
            $.each(userListJson, function (i, userJson) {
                var trData = $("<tr><td>" + userJson.uID +
                        "</td><td>" + userJson.userName +
                        "</td><td>" + userJson.realName +
                        "</td><td>" + userJson.from +
                        "</td><td>" + userJson.userType +
                        "</td><td>" + userJson.userAuths +
                        "</td><td>" + userJson.activity +
                        "</td></tr>");
                $("#userListTable").append(trData);
            });
        }
    });
}

function PasswordChange() {
    $.ajax({
        type: 'post',
        url: "action/divDataPushAction",
        data: {
            pushType: "PasswordChange",
            oldPassword: $("#input_update_password_oldPassword").val(),
            newPassword: $("#input_update_password_newPassword").val()
        },
        datatype: "text",
        success: function (flag) {
            if (flag == "true")
                $("#Password_Change_Notice_Success").show("fast");
            else
                $("#Password_Change_Notice_Error").show("fast");
        },
        error: function () {
            $("#Password_Change_Notice_Error").show("fast");
        }
    });
}




function AtAreaLoad() {
    $('#ObjMonDiv_AreaMon').show('fast')
    $.ajax({
        type: 'get',
        url: "action/divDataLoadAction",
        data: {requestType: "AtAreaListLoad"},
        dataType: "json",
        success: function (AtAreaJson) {
            $("#ObjMonDiv_AreaMon_Table").empty();
            console.dir(AtAreaJson);
            
            var trTitle = "<tr><th>区域ID</th><th>区域名称</th><th>区域点序</th>" +
                    "<th>坐标经度</th><th>坐标纬度</th></tr>";
            $("#ObjMonDiv_AreaMon_Table").append(trTitle);
            
            $.each(AtAreaJson, function (indexID, atArea) {
                var atpNum = atArea.atCoordXs.length;
                indexID = indexID + 1;
                for(var i=0;i<atpNum;i++) {
                    var trData = $("<tr><td>" + atArea.alertAreaID +
                            "</td><td>" + atArea.alertAreaName +
                            "</td><td>" + i +
                            "</td><td>" + atArea.atCoordXs[i] +
                            "</td><td>" + atArea.atCoordYs[i] +
                            "</td></tr>");
                    $("#ObjMonDiv_AreaMon_Table").append(trData);
                }
            });
        }
    });
}