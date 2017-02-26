$(document).ready(function() {

	$("#toolBar").mouseover(toolBarMouseOn);
	$("#toolBar").mouseleave(toolBarMouseOut);

	$("#MessageTool").click(function() {
		$("#toolBar").unbind("mouseleave", toolBarMouseOut);
		$(".secMenu").hide("fast");
		$("#MesToolMenu").show("fast");
	});
	$("#MesToolMenu").mouseleave(function() {
		$("#toolBar").bind("mouseleave", toolBarMouseOut);
		toolBarMouseOut();
		$(".secMenu").hide("fast");
	});

	$("#ArchiveTool").click(function() {
		$("#toolBar").unbind("mouseleave", toolBarMouseOut);
		$(".secMenu").hide("fast");
		$("#ArchToolMenu").show("fast");
	});
	$("#ArchToolMenu").mouseleave(function() {
		$("#toolBar").bind("mouseleave", toolBarMouseOut);
		toolBarMouseOut();
		$(".secMenu").hide("fast");
	});

	$("#ObjMonitTool").click(function() {
		$("#toolBar").unbind("mouseleave", toolBarMouseOut);
		$(".secMenu").hide("fast");
		$("#ObjMonToolMenu").show("fast");
	});
	$("#ObjMonToolMenu").mouseleave(function() {
		$("#toolBar").bind("mouseleave", toolBarMouseOut);
		toolBarMouseOut();
		$(".secMenu").hide("fast");
	});

	$("#SysSetTool").click(function() {
		$("#toolBar").unbind("mouseleave", toolBarMouseOut);
		$(".secMenu").hide("fast");
		$("#SysSetToolMenu").show("fast");
	});
	$("#SysSetToolMenu").mouseleave(function() {
		$("#toolBar").bind("mouseleave", toolBarMouseOut);
		toolBarMouseOut();
		$(".secMenu").hide("fast");
	});

	$("#FunHelpTool").click(function() {
		$("#toolBar").unbind("mouseleave", toolBarMouseOut);
		$(".secMenu").hide("fast");
		$("#FunHelpToolMenu").show("fast");
	});
	$("#FunHelpToolMenu").mouseleave(function() {
		$("#toolBar").bind("mouseleave", toolBarMouseOut);
		toolBarMouseOut();
		$(".secMenu").hide("fast");
	});

});

function toolBarMouseOn() {
	$("#toolBar").animate({
		width: '160px'
	}, "fast");
	$(".nav-text").delay(200).show("fast");
}

function toolBarMouseOut() {
	$("#toolBar").animate({
		width: '75px'
	}, "slow");
	$(".nav-text").hide("fast");
}