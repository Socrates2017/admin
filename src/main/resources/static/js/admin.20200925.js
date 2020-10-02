/**
 * 可操作表列表
 */
function tableList(tableName, dom, pageNum, pageSide) {
    showReflesh();
    try {
        var url = "/admin/AdminTable/table/" + tableName + "?pageNum=" + pageNum + "&pageSide=" + pageSide;
        $.ajax({
            url: url,
            type: "GET",
            datatype: "json",
            success: function (result) {
                showData(result, dom, pageNum, pageSide)
            },
            error: function (result) {
                removeReflesh();
                $.MsgBox.Confirm("提示", result.msg, function () {
                });
            }
        });
    } catch (err) {
    } finally {
        removeReflesh();
    }
}

/**
 * 展示数据
 * @param result
 * @param dom
 */
function showData(result, dom, pageNum, pageSide) {
    /*表名*/
    var tableName = result.data.tableName;
    /*数据列表*/
    var data = result.data.data;
    /*主键列*/
    var pri = result.data.pri;
    /*非主键列*/
    var notPri = result.data.notPri;

    /*表头，分别遍历主键列和非主键列获取*/
    var headStr = '<table border="2" id="' + "table" + dom + '"> <tr><td>序号</td> ';
    for (var i in pri) {
        var column = pri[i]['COLUMN_NAME'];
        headStr += '<td>' + column + '(主键)</td>'
    }
    for (var i in notPri) {
        var column = notPri[i]['COLUMN_NAME'];
        headStr += '<td>' + column + '</td>'
    }

    /*用于新增的参数*/
    var columnAdd = {};
    columnAdd.pri = pri
    columnAdd.notPri = notPri
    columnAdd.tableName = tableName
    columnAdd.dom = dom
    var columnAddJsonStr = JSON.stringify(columnAdd);

    /*数据行*/
    var dataStr = '';
    for (var k in data) {
        var map = data[k]

        /*用于删除的参数*/
        var columnDeletJsonStr;

        /*用于封装参数的主json*/
        var columnJson = {}
        columnJson.tableName = tableName;
        /*包含列值的主键json*/
        var priJson = []

        /*序号*/
        var num = parseInt(k) + 1
        dataStr += '<tr id="' + dom + num + '"' + '>';
        dataStr += '<td>' + num + '</td>';

        /*遍历主键，主键不可能为空*/
        for (var i in pri) {
            var column = pri[i]['COLUMN_NAME'];
            var value = map[column];
            dataStr += '<td>' + value + '</td>';

            pri[i]['value'] = value
            priJson.push(pri[i])
        }
        columnJson.pri = priJson;

        /*遍历非主键字段，空的置为空字符串*/
        for (var i in notPri) {
            var column = notPri[i]['COLUMN_NAME'];
            var value = map[column] == undefined ? '' : map[column];
            if ("creator" == column ||"updater" == column ||'create_time' == column || 'update_time' == column) {
                dataStr += '<td>' + value + '</td>';
            } else {
                /*要更新的列*/
                var columnEdit = []
                notPri[i]['value'] = value
                columnEdit.push(notPri[i])
                columnJson.notPri = columnEdit;
                var columnJsonStr = JSON.stringify(columnJson);
                dataStr += "<td>" + value + "<button class='button' type='submit' onclick='update(" + columnJsonStr + ");'>更新</button>" + "</td>";
            }
        }
        if ("tableList" == dom) {
            var rowTablename = notPri[0]['value']
            dataStr += "<td><button class='button' type='submit' onclick='show(" + '"' + rowTablename + '"' + ");'>查看</button>" + "</td>";
        }
        columnJson.notPri = notPri;
        columnJson.dom = dom
        columnDeletJsonStr = JSON.stringify(columnJson);
        dataStr += "<td><button class='button' type='submit' onclick='remove(" + columnDeletJsonStr + ");'>删除</button>" + "</td>";
        dataStr += '</tr>'
    }

    headStr += "<td><button class='button' type='submit' onclick='add(" + columnAddJsonStr + ");'>新增</button>" + "</td>";
    headStr += '</tr>';
    headStr += dataStr;
    headStr += '</table>'

    $("#" + dom).html(headStr);
    var listhaed = tableName;
    $("#" + dom + "head").html(listhaed);

}


function toSearch(e) {
    showReflesh();
    try {
        //var q = $("#q").val();//查询参数
        var tableName = $("#datalisthead").html();
        var dom = "datalist"
        if (tableName == undefined || tableName == "") {
            tableName = "admin_table"
            dom = "tableList"
        }


        var pageNum = 1;//所选页序,默认为1
        var eo = $(e);//js对象转jquery对象
        var pageNumS = eo.html();
        if (null != e) {
            pageNum = parseInt(pageNumS);
        }

        var activeObj = $(".active");//活跃页序对象
        var active = 1;
        if (undefined != activeObj) {
            active = parseInt(activeObj.html()); //活跃页序
            activeObj.removeClass("active");//清除活跃页序
        }

        if (null == e) {
            var foot = '<ul class="pagination"><li><a href="#" onclick="toSearch(this);return false;">«</a></li>' +
                '<li><a class ="active" href="#" onclick="toSearch(this);return false;">' + pageNum + '</a></li>' +
                '<li><a href="#" onclick="toSearch(this);return false;">' + (pageNum + 1) + '</a></li>' +
                '<li><a href="#" onclick="toSearch(this);return false;">' + (pageNum + 2) + '</a></li>' +
                '<li><a href="#" onclick="toSearch(this);return false;">' + (pageNum + 3) + '</a></li>' +
                '<li><a href="#" onclick="toSearch(this);return false;">' + (pageNum + 4) + '</a></li>' +
                '<li><a href="#" onclick="toSearch(this);return false;">»</a></li></ul>';
            $("#foot").html(foot);
        } else if ("«" == pageNumS) {
            pageNum = active - 1;
            if (pageNum > 5) {
                var foot = '<ul class="pagination"><li><a href="#" onclick="toSearch(this);return false;">«</a></li>' +
                    '<li><a href="#" onclick="toSearch(this);return false;">' + (pageNum - 4) + '</a></li>' +
                    '<li><a href="#" onclick="toSearch(this);return false;">' + (pageNum - 3) + '</a></li>' +
                    '<li><a href="#" onclick="toSearch(this);return false;">' + (pageNum - 2) + '</a></li>' +
                    '<li><a href="#" onclick="toSearch(this);return false;">' + (pageNum - 1) + '</a></li>' +
                    '<li><a class ="active" href="#" onclick="toSearch(this);return false;">' + pageNum + '</a></li>' +
                    '<li><a href="#" onclick="toSearch(this);return false;">»</a></li></ul>';
                $("#foot").html(foot);
            } else {
                var foot = '<ul class="pagination"><li><a href="#" onclick="toSearch(this);return false;">«</a></li>' +
                    '<li><a';
                if (2 > pageNum) {
                    foot += ' class ="active"';
                    pageNum = 1;
                }
                foot += ' href="#" onclick="toSearch(this);return false;">' + 1 + '</a></li>' +
                    '<li><a';
                if (2 == pageNum) {
                    foot += ' class ="active"';
                }
                foot += ' href="#" onclick="toSearch(this);return false;">' + 2 + '</a></li>' +
                    '<li><a';
                if (3 == pageNum) {
                    foot += ' class ="active"';
                }
                foot += ' href="#" onclick="toSearch(this);return false;">' + 3 + '</a></li>' +
                    '<li><a';
                if (4 == pageNum) {
                    foot += ' class ="active"';
                }
                foot += ' href="#" onclick="toSearch(this);return false;">' + 4 + '</a></li>' +
                    '<li><a';
                if (5 == pageNum) {
                    foot += ' class ="active"';
                }
                foot += ' href="#" onclick="toSearch(this);return false;">' + 5 + '</a></li>' +
                    '<li><a href="#" onclick="toSearch(this);return false;">»</a></li></ul>';
                $("#foot").html(foot);
            }
        } else if ("»" == pageNumS) {
            pageNum = active + 1;
            var foot = '<ul class="pagination"><li><a href="#" onclick="toSearch(this);return false;">«</a></li>' +
                '<li><a class ="active" href="#" onclick="toSearch(this);return false;">' + pageNum + '</a></li>' +
                '<li><a href="#" onclick="toSearch(this);return false;">' + (pageNum + 1) + '</a></li>' +
                '<li><a href="#" onclick="toSearch(this);return false;">' + (pageNum + 2) + '</a></li>' +
                '<li><a href="#" onclick="toSearch(this);return false;">' + (pageNum + 3) + '</a></li>' +
                '<li><a href="#" onclick="toSearch(this);return false;">' + (pageNum + 4) + '</a></li>' +
                '<li><a href="#" onclick="toSearch(this);return false;">»</a></li></ul>';
            $("#foot").html(foot);
        } else {
            eo.addClass("active");//赋予所选项为活跃页序
        }

        tableList(tableName, dom, pageNum, 5);

    } catch (err) {

    } finally {
        removeReflesh();//移除缓冲图标
    }
}


function add(priJsonStr) {
    showReflesh();
    try {
        var colums = eval(priJsonStr);
        var notPri = colums.notPri;
        var pri = colums.pri;
        var tableName = colums.tableName;
        var dom = colums.dom;

        var trId = "addTr" + tableName;
        var trnext = $("#" + trId);
        if (trnext.length > 0) {
            $.MsgBox.Confirm("提示", "一次只能新增一行", function () {
            });
            return;
        }

        var inputClassPri = "inputClassPri" + tableName;
        var inputClass = "inputClass" + tableName;

        var tableId = "#table" + dom;
        var tb = document.querySelector(tableId);
        var tr = document.createElement('tr');
        tr.id = trId;


        /*序号留空*/
        var td = document.createElement('td');
        tr.appendChild(td);

        /*主键*/
        for (var i in pri) {
            var column = pri[i]['COLUMN_NAME'];
            var tdInfo = document.createElement('td');

            if ("id" == column ) {
                var td = document.createElement('td');
                tr.appendChild(td);
            } else {
                var inputInfo = document.createElement('input');
                inputInfo.setAttribute('type', 'text');
                inputInfo.setAttribute('name', 'details');
                inputInfo.setAttribute('placeholder', column);

                var columnInfo = JSON.stringify(pri[i]);
                inputInfo.setAttribute('alt', columnInfo);

                inputInfo.classList.add('borderNone');
                inputInfo.classList.add(inputClassPri);
                tdInfo.appendChild(inputInfo);
                tr.appendChild(tdInfo);
            }

        }
        /*非主键*/
        for (var i in notPri) {
            var column = notPri[i]['COLUMN_NAME'];
            var td = document.createElement('td');
            /*过滤时间*/
            if ("creator" == column ||"updater" == column ||"create_time" == column || "update_time" == column) {
                var td = document.createElement('td');
                tr.appendChild(td);
            } else {
                var inputInfo = document.createElement('input');
                inputInfo.setAttribute('type', 'text');
                inputInfo.setAttribute('name', 'details');
                inputInfo.setAttribute('placeholder', column);
                var columnInfo = JSON.stringify(notPri[i]);
                inputInfo.setAttribute('alt', columnInfo);
                inputInfo.classList.add('borderNone');
                inputInfo.classList.add(inputClass);
                td.appendChild(inputInfo);
                tr.appendChild(td);
            }
        }

        var tdOp = document.createElement('td');
        var delBtn = document.createElement('button');
        var title = document.createTextNode("取消");
        delBtn.appendChild(title);
        delBtn.addEventListener('click', function () {
            tb.removeChild(tr);
        });
        tdOp.appendChild(delBtn);

        var submit = document.createElement('td');
        var submitBtn = document.createElement('button');
        var submitTitle = document.createTextNode("提交");
        submitBtn.appendChild(submitTitle);
        submitBtn.addEventListener('click', function () {

            var addParam = {}
            var inputClassPriJson = []
            var inputClassJson = []

            $("." + inputClassPri).each(function () {
                var input = $(this);
                var value = input.val();
                if ("" != value) {
                    var columnInfo = input.attr("alt");
                    var columnInfoJson = JSON.parse(columnInfo);
                    columnInfoJson["value"] = value
                    var columnInfoStr = JSON.stringify(columnInfoJson)
                    var columnInfoStrJson = JSON.parse(columnInfoStr)
                    inputClassPriJson.push(columnInfoStrJson)
                }
            });

            $("." + inputClass).each(function () {
                var input = $(this);
                var value = input.val();
                if ("" != value) {
                    var columnInfo = input.attr("alt");
                    var columnInfoJson = JSON.parse(columnInfo);
                    columnInfoJson["value"] = value
                    var columnInfoStr = JSON.stringify(columnInfoJson)
                    var columnInfoStrJson = JSON.parse(columnInfoStr)
                    inputClassJson.push(columnInfoStrJson)
                }
            });

            addParam.notPri = inputClassJson
            addParam.pri = inputClassPriJson
            addParam.tableName = tableName
            var addParamStr = JSON.stringify(addParam);
            showReflesh();
            $.ajax({
                url: urlRootContext() + "/admin/AdminTable/insert",
                type: "POST",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: addParamStr,
                success: function (result) {
                    removeReflesh();
                    $.MsgBox.Confirm("提示", result.msg, function () {
                    });
                    show(tableName)
                },
                error: function (result) {
                    removeReflesh();//移除缓冲图标
                    $.MsgBox.Confirm("提示", result.msg, function () {
                    });
                }
            });


        });
        submit.appendChild(submitBtn);

        /*如果是表列表则空一格，以对称*/
        if ("tableList" == dom) {
            var td = document.createElement('td');
            tr.appendChild(td);
        }

        tr.appendChild(tdOp);
        tr.appendChild(submit);
        tb.appendChild(tr);


    } catch (err) {
        console.error(err)
    } finally {
        removeReflesh();
    }
}

function findObj(theObj, theDoc) {
    var p, i, foundObj;
    if (!theDoc) theDoc = document;
    if ((p = theObj.indexOf("?")) > 0 && parent.frames.length) {
        theDoc = parent.frames[theObj.substring(p + 1)].document;
        theObj = theObj.substring(0, p);
    }
    if (!(foundObj = theDoc[theObj]) && theDoc.all)
        foundObj = theDoc.all[theObj];
    for (i = 0; !foundObj && i < theDoc.forms.length; i++)
        foundObj = theDoc.forms[i][theObj];
    for (i = 0; !foundObj && theDoc.layers && i < theDoc.layers.length; i++)
        foundObj = findObj(theObj, theDoc.layers[i].document);
    if (!foundObj && document.getElementById)
        foundObj = document.getElementById(theObj);
    return foundObj;
}

/**
 *获取表数据
 */
function show(tableName) {
    showReflesh();
    try {
        var url = "/admin/AdminTable/table/" + tableName
        $.ajax({
            url: url,
            type: "GET",
            datatype: "json",
            success: function (result) {
                //var data = eval("(" + result.data + ")");
                var dom = 'datalist'
                if ("admin_table" == tableName) {
                    dom = "tableList"
                }
                showData(result, dom)
                removeReflesh();
            },
            error: function (result) {
                removeReflesh();
                $.MsgBox.Confirm("提示", result.msg, function () {
                });
            }
        });
    } catch (err) {
    } finally {
        removeReflesh();
    }
}

function remove(priJsonStr) {
    showReflesh();
    try {
        $.MsgBox.Cancel("提示", "确定删除吗", function () {
            var colums = eval(priJsonStr);
            var tableName = colums.tableName;
            var colStr = JSON.stringify(colums);
            showReflesh();//显示缓冲图标
            $.ajax({
                url: urlRootContext() + "/admin/AdminTable/remove",
                type: "POST",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                data: colStr,
                success: function (result) {
                    removeReflesh();//移除缓冲图标
                    $.MsgBox.Confirm(result.msg, result.data, function () {
                    });
                    show(tableName);
                },
                error: function (result) {
                    removeReflesh();
                    $.MsgBox.Confirm("提示", result.msg, function () {
                    });
                }
            });

        });
    } catch (err) {
    } finally {
        removeReflesh();
    }
}

/**
 * 更新字段
 * @param priJsonStr
 */
function update(priJsonStr) {
    showReflesh();
    try {
        var content = '<div class="panel panel-info"><textarea name="content" id="content" placeholder="填入新的值" style="width: 100%;"></textarea></div>'
        $.MsgBox.Cancel2("提示", content, function () {
            var content = $("#content").val();
            if ($("#content").val() == "") {
                $.MsgBox.Confirm("提示", "内容不能为空！", function () {
                });
                return false;
            }
            var v = content.replace(/\r/g, '');
            if (v == '') {
                $.MsgBox.Confirm("提示", "内容不能为空！", function () {
                });
                return false;
            } else {
                var colums = eval(priJsonStr);
                var notPri = colums.notPri;
                /*替换掉要更新的值*/
                notPri[0]['value'] = v;
                colums.notPri = notPri;
                var tableName = colums.tableName;
                var colStr = JSON.stringify(colums);
                showReflesh();//显示缓冲图标
                $.ajax({
                    url: urlRootContext() + "/admin/AdminTable/update",
                    type: "POST",
                    contentType: "application/json;charset=utf-8",
                    dataType: "json",
                    data: colStr,
                    success: function (result) {
                        removeReflesh();//移除缓冲图标
                        $.MsgBox.Confirm(result.msg, result.data, function () {
                        });
                        show(tableName);
                    },
                    error: function (result) {
                        removeReflesh();//移除缓冲图标
                        $.MsgBox.Confirm("提示", result.msg, function () {
                        });
                    }
                });
            }
        });
    } catch (err) {
    } finally {
        removeReflesh();
    }
}

$(function () {

    toSearch(null)
});
