
var atIndex = 0;
var atCoordXs = new Array();
var atCoordYs = new Array();
var atPnts = new Array();

var map, tb;
require(["esri/map",
    "esri/toolbars/draw",
    "esri/geometry/Point",
    "esri/geometry/Circle",
    "esri/geometry/Polygon",
    "esri/symbols/SimpleMarkerSymbol",
    "esri/symbols/SimpleFillSymbol",
    "esri/symbols/SimpleLineSymbol",
    "esri/symbols/CartographicLineSymbol",
    "esri/SpatialReference",
    "esri/graphic",
    "esri/InfoTemplate",
    "esri/layers/GraphicsLayer",
    "esri/dijit/BasemapGallery",
    "dojo/dom",
    "dojo/on",
    "dojo/_base/Color",
    "dojo/domReady!"
],
        function (Map, Draw, Point, Circle, Polygon, SimpleMarkerSymbol, SimpleFillSymbol, SimpleLineSymbol, CartographicLineSymbol, SpatialReference, Graphic, InfoTemplate, GraphicsLayer, BasemapGallery, dom, on, Color) {
            map = new Map("mapDiv", {
                basemap: "osm",
                center: [120.90265431176772, 32.02045045560068],
                zoom: 17,
                sliderStyle: "small"
            });

            addBasemapGallery();

            map.on("load", function () {
                loadAtArea();
                loadSenArea();
                loadOnlineObj();
                initToolbar();
                initAtToolbar();
            });

            // markerSymbol is used for point and multipoint, see http://raphaeljs.com/icons/#talkq for more examples
            var markerSymbol = new SimpleMarkerSymbol();
            markerSymbol.setPath("M8,0C3.582,0,0,3.582,0,8c0,4.418,3.582,8,8,8s8-3.582,8-8C16,3.582,12.418,0,8,0z M8,14c-3.314,0-6-2.686-6-6c0-3.314,2.686-6,6-6s6,2.686,6,6C14,11.314,11.314,14,8,14z M9,4H7v3H4v2h3v3h2V9h3V7H9V4z");
            markerSymbol.setColor(new Color("#00FFFF"));

            // lineSymbol used for freehand polyline, polyline and line. 
            var lineSymbol = new CartographicLineSymbol(
                    CartographicLineSymbol.STYLE_SOLID,
                    new Color([255, 0, 0]), 10,
                    CartographicLineSymbol.CAP_ROUND,
                    CartographicLineSymbol.JOIN_MITER, 5
                    );

            // fill symbol used for extent, polygon and freehand polygon, use a picture fill symbol
            // the images folder contains additional fill images, other options: sand.png, swamp.png or stiple.png


            var lineFillSymbol = new SimpleFillSymbol(
                    SimpleFillSymbol.STYLE_NULL,
                    new SimpleLineSymbol(
                            SimpleLineSymbol.STYLE_SHORTDASHDOTDOT,
                            new Color([105, 105, 105]),
                            2
                            ), new Color([255, 255, 0, 0.25])
                    );


            var mapClickEvent = on(map, "click", addPoint);

            function loadOnlineObj() {
                var iconPath = "M32,3.867c-9.35,0-16.955,7.607-16.955,16.955c0,8.991,15.541,36.792,16.203,37.971L32,60.133l0.753-1.34c0.661-1.18,16.202-28.98,16.202-37.971C48.955,11.474,41.348,3.867,32,3.867z M32,25.912c-3.522,0-6.388-2.866-6.388-6.389c0-3.524,2.866-6.39,6.388-6.39c3.524,0,6.39,2.866,6.39,6.39C38.39,23.046,35.523,25.912,32,25.912z";
                var initColor = "#ce641d";
                var onlineObjLayer = new GraphicsLayer({id: "onlineObjLayer"});
                $.ajax({
                    type: 'get',
                    url: "action/onlineLoadAction",
                    dataType: "json",
                    data: {getFlag: "loadOnlineObj"},
                    success: function (pntListJson) {
                        var messNum = parseInt($("#messageBtn_Badge").text());
                        $.each(pntListJson, function (i, pntJson) {
                            var point = new Point([pntJson.sCoordX, pntJson.sCoordY]);
                            console.log(pntJson.deviceID + "_" + pntJson.pTime + ":" + pntJson.sCoordX + "_" + pntJson.sCoordY);
                            var SVGSymbol = createSVGSymbol(iconPath, initColor);
                            var pointAttributes = {
                                "经度": pntJson.sCoordX,
                                "纬度": pntJson.sCoordY,
                                "设备ID": pntJson.deviceID,
                                "位置获取时间": pntJson.pTime,
                                "目标姓名": pntJson.pepName,
                                "敏感计分": pntJson.senScore,
                                "计算时间": pntJson.calcTime
                            };
                            var atFlag = parseInt(raycasting(point, atPnts));
                            var messageAdd = null;
                            messNum = parseInt($("#messageBtn_Badge").text());
                            switch (atFlag) {
                                case 1:
                                    break;
                                case 0:
                                    messageAdd = pntJson.pepName + "超出监控区域边界";
                                    $("#messageBtn_Badge").text(++messNum);
                                    break;
                                case 2:
                                    messageAdd = pntJson.pepName + "处于监控区域边界上";
                                    $("#messageBtn_Badge").text(++messNum);
                                    break;
                            }
                            if (messageAdd != null) {
                                var messageHTML = $("<tr><td>" + messageAdd + "</td></tr>");
                                $("messageDiv_table").append(messageHTML);
                            }

                            if (parseInt(pntJson.senScore) > 500) {
                                var messageHTML = $("<tr><td>" + pntJson.pepName + "敏感分数超出阈值，请检查:" + pntJson.calcTime + "_" + pntJson.senScore + "</td></tr>");
                                $("messageDiv_table").append(messageHTML);
                                $("#messageBtn_Badge").text(++messNum);
                            }

                            var pointInfoTemplate = new InfoTemplate("监控对象信息");
                            var graphic = new Graphic(point, SVGSymbol, pointAttributes);
                            graphic.setInfoTemplate(pointInfoTemplate);

                            onlineObjLayer.add(graphic);
                        });
                        if (messNum != 0) {
                            $("#audioWaring")[0].play();
                            setTimeout("$('#audioWaring')[0].play()", 2000);
                            setTimeout("$('#audioWaring')[0].play()", 4000);
                        }
                        map.removeLayer("onlineObjLayer");
                        map.addLayer(onlineObjLayer);
                        console.dir(onlineObjLayer);
                    }
                });

                console.log("reload online OBJ OK!");
                var t = setTimeout(loadOnlineObj, 300000);
            }

            function loadSenArea() {

                var senAreaLayer = new GraphicsLayer({id: "senAreaLayer"});
                $.ajax({
                    type: 'get',
                    url: "action/onlineLoadAction",
                    dataType: "json",
                    data: {getFlag: "loadSenArea"},
                    success: function (senAreaListJson) {
                        $.each(senAreaListJson, function (i, senJson) {
                            var centerPoint = new Point([senJson.aCoordX, senJson.aCoordY], new SpatialReference({wkid: 4326}));
                            var circleGeometry = new Circle({
                                center: centerPoint,
                                radius: 30,
                                geodesic: true
                            });
                            var areaAttributes = {
                                "经度": senJson.aCoordX,
                                "纬度": senJson.aCoordY,
                                "区域ID": senJson.aID,
                                "区域名称": senJson.areaName,
                                "区域类型": senJson.areaTypeName,
                                "敏感程度": senJson.areaTypeGra
                            };
                            var infoTemplate = new InfoTemplate("敏感区域信息");
                            var areaFillSymbol = new SimpleFillSymbol(
                                    SimpleFillSymbol.STYLE_SOLID,
                                    new SimpleLineSymbol(
                                            SimpleLineSymbol.STYLE_SHORTDASHDOTDOT,
                                            new Color([105, 105, 105]),
                                            2
                                            ), new Color([255, 255, 0, 0.25])
                                    );
                            var graphic = new Graphic(circleGeometry, areaFillSymbol, areaAttributes);
                            graphic.setInfoTemplate(infoTemplate);
                            console.dir(graphic);
                            senAreaLayer.add(graphic);
                        });
                        map.addLayer(senAreaLayer);
                        console.dir(senAreaLayer);
                    }
                });
                console.log("load senArea OK!");
            }

            function loadAtArea() {

                var atAreaLayer = new GraphicsLayer({id: "atAreaLayer"});
                $.ajax({
                    type: 'get',
                    url: "action/onlineLoadAction",
                    dataType: "json",
                    data: {getFlag: "loadAtArea"},
                    success: function (atAreaListJson) {
                        $.each(atAreaListJson, function (i, atJson) {

                            atCoordXs = atJson.atCoordXs;
                            atCoordYs = atJson.atCoordYs;
                            var atpNum = atCoordXs.length;
                            var coordPnts = new Array(atpNum);
                            atPnts = new Array();

                            for (var atpID = 0; atpID < atpNum; atpID++) {
                                coordPnts[atpID] = [parseFloat(atCoordXs[atpID]), parseFloat(atCoordYs[atpID])];
                                var atAreaPoint = new Point([atCoordXs[atpID], atCoordYs[atpID]], new SpatialReference({wkid: 4326}));
                                atPnts.push(atAreaPoint);
                            }
                            var polygonGeometry = new Polygon(new SpatialReference({wkid: 4326}));
                            polygonGeometry.addRing(coordPnts);

                            var areaAttributes = {
                                "区域ID": atJson.alertAreaID,
                                "区域名称": atJson.alertAreaName,
                            };
                            var infoTemplate = new InfoTemplate("监控区域信息");
                            var areaFillSymbol = new SimpleFillSymbol(
                                    SimpleFillSymbol.STYLE_SOLID,
                                    new SimpleLineSymbol(
                                            SimpleLineSymbol.STYLE_SHORTDASHDOTDOT,
                                            new Color([105, 105, 105]),
                                            2
                                            ), new Color([60, 179, 113, 0.05])
                                    );

                            var graphic = new Graphic(polygonGeometry, areaFillSymbol, areaAttributes);
                            graphic.setInfoTemplate(infoTemplate);
                            atAreaLayer.add(graphic);
                        });
                        console.dir(atAreaLayer);
                        map.addLayer(atAreaLayer);
                    }
                });
                console.log("load atArea OK!");
            }

            function initToolbar() {
                tb = new Draw(map);
                tb.on("draw-end", addGraphic);

                // event delegation so a click handler is not
                // needed for each individual button
                on(dom.byId("info"), "click", function (evt) {
                    if (evt.target.id === "info") {
                        return;
                    }
                    var tool = evt.target.id.toLowerCase();
                    map.disableMapNavigation();
                    tb.activate(tool);
                });
            }

            function initAtToolbar() {
                tb = new Draw(map);
                tb.on("draw-end", addGraphic);

                // event delegation so a click handler is not
                // needed for each individual button
                on(dom.byId("ataInfo"), "click", function (evt) {
                    if (evt.target.id === "ataInfo") {
                        return;
                    }
                    var tool = evt.target.id.toLowerCase();
                    map.disableMapNavigation();
                    tb.activate(tool);
                });
            }

            function addGraphic(evt) {
                //deactivate the toolbar and clear existing graphics 
                tb.deactivate();
                map.enableMapNavigation();

                // figure out which symbol to use
                var symbol;
                if (evt.geometry.type === "point" || evt.geometry.type === "multipoint") {
                    symbol = markerSymbol;
                } else if (evt.geometry.type === "line" || evt.geometry.type === "polyline") {
                    symbol = lineSymbol;
                } else {
                    symbol = lineFillSymbol;
                }

                map.graphics.add(new Graphic(evt.geometry, symbol));
            }

            function createSVGSymbol(path, color) {
                var markerSymbol = new SimpleMarkerSymbol();
                markerSymbol.setPath(path);
                markerSymbol.setColor(new Color(color));
                markerSymbol.setOutline(null);
                return markerSymbol;
            }

            function addPoint(evt) {
                var mercator = {x: evt.mapPoint.x, y: evt.mapPoint.y}
                var lonlat = mercator2lonlat(mercator);

                $("#input_area_x").val(lonlat.x);
                $("#input_area_y").val(lonlat.y);

                $("#input_atarea_x").val(lonlat.x);
                $("#input_atarea_y").val(lonlat.y);
                $("#input_atarea_atpID").val(atIndex);
                atCoordXs[atIndex] = lonlat.x;
                atCoordYs[atIndex] = lonlat.y;
                atIndex++;

                console.dir(lonlat);
            }

            function addBasemapGallery() {
                var basemapGallery = new BasemapGallery({showArcGISBasemaps: true, map: map}, "basemapGalleryDiv");
                basemapGallery.startup();
                console.log("Add BasemapGallery Successful");
            }


            //判断点与多边形的位置关系（多边形内1 多边形边上2 多边形外0）
            function raycasting(coordPnt, atAreaPoints) {

                var count = 0;
                var p1 = new Point();
                var p2 = new Point();

                for (var i = 0; i < atAreaPoints.length; i++) {

                    p1 = atAreaPoints[i];

                    if (i == atAreaPoints.length - 1) {
                        p2 = atAreaPoints[i];
                    } else {
                        p2 = atAreaPoints[i + 1];
                    }

                    if ((coordPnt.y >= p1.y && coordPnt.y <= p2.y) || (coordPnt.y >= p2.y && coordPnt.y <= p1.y)) {
                        var t = (coordPnt.y - p1.y) / (p2.y - p1.y);
                        var xt = p1.x + t * (p2.x - p1.x);
                        if (coordPnt.x == xt)
                            return 2;
                        if (coordPnt.x < xt)
                            ++count;
                    }
                }

                if (count % 2) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
);



function AtAreaAdd() {
    $('#ObjMonDiv_AreaMon').hide('fast');
    $('#ObjMonDiv_AreaMonAdd').show('fast');
    atIndex = 0;
    atCoordXs = new Array();
    atCoordYs = new Array();
}

function AtAreaAdd_Submit() {
    console.dir(atCoordXs);
    console.dir(atCoordYs);
    $.ajax({
        type: 'post',
        url: "action/divDataPushAction",
        data: {
            pushType: "AtAreaAdd",
            traditional: true,
            atAreaName: $("#input_atarea_name").val(),
            atCoordXs: atCoordXs,
            atCoordYs: atCoordYs
        },
        datatype: "text",
        success: function (flag) {
            if (flag == "true")
                $("#ObjMonDiv_atAdd_Notice_Success").show("fast");
            else
                $("#ObjMonDiv_atAdd_Notice_Error").show("fast");
        },
        error: function () {
            $("#ObjMonDiv_atAdd_Notice_Error").show("fast");
        }
    });
}