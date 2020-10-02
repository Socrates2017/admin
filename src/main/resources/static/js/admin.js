var isCreator = false;

/**
 * 访问url并写到frame中
 * @param url
 */
function getContent(url) {
    $('#content').attr('src', url);
    changeFrameHeight();
    var html = '<a href="' + url + '" target="_blank" >' + url + '</a>&emsp;<a href="' + url +
        '" target="_blank" ><span class="glyphicon glyphicon-share"></span></a>'
    $('#new-window-content').html(html);
}

/**
 * iframe高度自适应
 * 左边目录栏高度自适应
 */
function changeFrameHeight() {
    var ifm = document.getElementById("content");
    ifm.height = document.documentElement.clientHeight - 30;
    $("#left-scroll").height(ifm.height);
}

/**
 * 窗口高度改变时触发
 */
window.onresize = function () {
    changeFrameHeight();
}


/**
 * 隐藏章节目录
 * @param id
 */
function hideIndex(id) {
    $("#index_" + id).html("");
    var showHtml = '<a href="javascript:void(0);" onclick="getIndex(' + id + ')"><span class="glyphicon glyphicon-chevron-right"></span></a>'
    $("#show-" + id).html(showHtml);
}


/**
 * 缩小右边栏
 */
function indentRight() {
    var classes = document.getElementById("left").classList;
    var leftNum = 3;

    for (var j = 0, len = classes.length; j < len; j++) {
        var className = classes[j];
        if (className.substr(0, 7) == "col-sm-") {
            leftNum = parseInt(className.substr(7))
            break;
        }
    }

    var numRight = 12 - leftNum;
    var leftClassOld = 'col-sm-' + leftNum + ' col-md-' + leftNum + ' col-lg-' + leftNum;
    var rightClassOld = 'col-sm-' + numRight + ' col-md-' + numRight + ' col-lg-' + numRight;

    var newNum = leftNum + 1;
    var newNumRight = 12 - newNum;
    var leftClassNew = 'col-sm-' + newNum + ' col-md-' + newNum + ' col-lg-' + newNum;
    var rightClassNew = 'col-sm-' + newNumRight + ' col-md-' + newNumRight + ' col-lg-' + newNumRight;

    $("#left").removeClass(leftClassOld);
    $("#left").addClass(leftClassNew)
    $("#right").removeClass(rightClassOld);
    $("#right").addClass(rightClassNew)

}

/**
 * 缩小左边栏
 */
function indentLeft() {
    var classes = document.getElementById("left").classList;
    var leftNum = 3;
    for (var j = 0, len = classes.length; j < len; j++) {
        var className = classes[j];
        if (className.substr(0, 7) == "col-sm-") {
            leftNum = parseInt(className.substr(7))
            break;
        }
    }

    var numRight = 12 - leftNum;
    var leftClassOld = 'col-sm-' + leftNum + ' col-md-' + leftNum + ' col-lg-' + leftNum;
    var rightClassOld = 'col-sm-' + numRight + ' col-md-' + numRight + ' col-lg-' + numRight;

    var newNum = leftNum - 1;
    var newNumRight = 12 - newNum;
    var leftClassNew = 'col-sm-' + newNum + ' col-md-' + newNum + ' col-lg-' + newNum;
    var rightClassNew = 'col-sm-' + newNumRight + ' col-md-' + newNumRight + ' col-lg-' + newNumRight;

    $("#left").removeClass(leftClassOld);
    $("#left").addClass(leftClassNew)
    $("#right").removeClass(rightClassOld);
    $("#right").addClass(rightClassNew)

}

/**
 * 隐藏左边栏
 */
function hideLeft() {
    var classes = document.getElementById("left").classList;
    var leftNum = 3;
    for (var j = 0, len = classes.length; j < len; j++) {
        var className = classes[j];
        if (className.substr(0, 7) == "col-sm-") {
            leftNum = parseInt(className.substr(7))
            break;
        }
    }
    var numRight = 12 - leftNum;
    var rightClassOld = 'col-sm-' + numRight + ' col-md-' + numRight + ' col-lg-' + numRight;

    $("#left").hide()
    $("#right").removeClass(rightClassOld);
    $("#right").addClass('col-sm-12 col-md-12 col-lg-12')

    $("#indentRight").hide()
}


/**
 * 显示左边栏
 */
function showLeft() {
    var classes = document.getElementById("left").classList;
    var leftNumOld = 3;
    for (var j = 0, len = classes.length; j < len; j++) {
        var className = classes[j];
        if (className.substr(0, 7) == "col-sm-") {
            leftNumOld = parseInt(className.substr(7))
            break;
        }
    }

    var newNumRight = 12 - leftNumOld;
    var rightClassNew = 'col-sm-' + newNumRight + ' col-md-' + newNumRight + ' col-lg-' + newNumRight;

    $("#left").show()
    $("#right").removeClass('col-sm-12 col-md-12 col-lg-12');
    $("#right").addClass(rightClassNew)

    $("#indentRight").show()

}

/**
 * 控制隐藏或显示左边栏
 */
function hideOrShowLeft() {
    var ishide = $("#indentRight").is(':hidden');
    if (ishide) {
        showLeft()
    } else {
        hideLeft()
    }
}

/**
 * 获取章节目录并局部刷新
 *
 * @param id
 */
function getIndex(id) {

    var bookId = $("#bookId").val();
    if ($.isBlank(bookId)) {
        $.MsgBox.Confirm("提示", "错误：bookId 为空！", function () {
        });
        return;
    }

    $.ajax({
        url: "/bookIndex/children?book=" + bookId + "&parent=" + id,
        type: "GET",
        datatype: "json",
        async: true,
        success: function (result) {
            var code = result.code;

            if (code == 10000) {
                var data = result.data;
                var html = '<ul class="" id="ul-' + id + '" >';
                for (var i in data) {
                    var bookIndex = data[i];
                    var isLeaf = bookIndex.isLeaf;

                    html += '<li id="li-' + bookIndex.id + '" class=""  style="margin-top:20px;" draggable="true" ondragstart="drag(event)" ';

                    if (isLeaf == 0) {
                        html += 'ondrop="drop(event)" ondragenter="dragEnter(event)" ondragleave="dragLeave(event)" ondragover="allowDrop(event)">';
                        html += '<div style="display: inline" id="show-' + bookIndex.id + '"><a href="javascript:void(0);" onclick="getIndex(' + bookIndex.id + ')"><span class="glyphicon glyphicon-chevron-right"></span></a></div>';
                    } else {
                        html += '>';
                    }

                    html += '&emsp;<a href="javascript:void(0);" onclick="getContent(\'' + bookIndex.url + '\')"><span id="name-' + bookIndex.id + '">' + bookIndex.name + '</span></a>';
                    if (isCreator) {
                        html += '&emsp;&emsp;<div style="display: inline" id="opear-' + bookIndex.id + '"><a href="javascript:void(0);" onclick="showUpdateIndexModal(' + id + "," + bookIndex.id + ",\'" + bookIndex.name + "\',\'" + bookIndex.url + '\',' + bookIndex.indexOrder + ',' + isLeaf
                            + ')"><span class="glyphicon glyphicon-pencil"></span></a>';

                        if (isLeaf == 0) {
                            html += '<a href="javascript:void(0);" onclick="showAddIndexModal(' + bookIndex.id + ')"><span class="glyphicon glyphicon-plus-sign"></span></a>';
                        }

                        html += '<a href="javascript:void(0);" onclick="showDeleteIndexModal(' + id + "," + bookIndex.id + ')"><span class="glyphicon glyphicon-minus-sign"></span></a></div></li> ';
                    }
                    html += '<div id="index_' + bookIndex.id + '" style="padding-left:20px;" ></div>';
                }
                html += '</ul>'
                $("#index_" + id).html(html);

                var showHtml = '<a href="javascript:void(0);" onclick="hideIndex(' + id + ')"><span class="glyphicon glyphicon-chevron-down"></span></a>';
                $("#show-" + id).html(showHtml);

            } else {
                console.log(result.message)
            }
        }
    });
}





$(function () {
    $("#left-scroll").height(document.documentElement.clientHeight - 30);
    var uri = window.location.pathname;

    getContent("admin/article/center");
});