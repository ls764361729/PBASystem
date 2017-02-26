$.ajax({
    type: 'get',
    url: "action/divDataLoadAction",
    data: {
        requestType: "Loading",
    },
    dataType: "json",
    success: function (loadingJson) {
        console.dir(loadingJson);
        $("#databasePep").attr("class","label_load label label-success").text(loadingJson.databasePep+"人");
        $("#onlinePep").attr("class","label_load label label-success").text(loadingJson.onlinePep+"人");
        $("#sysState").attr("class","label_load label label-success").text("正常");
    },
    error:function() {
        $("#sysState").attr("class","label_load label label-danger").text("异常");
        $("#onlinePep").attr("class","label_load label label-danger");
        $("#onlinePep").attr("class","label_load label label-danger");
    }
});