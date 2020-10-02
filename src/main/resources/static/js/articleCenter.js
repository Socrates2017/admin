/**
 * 获取文章列表
 */
function tableList(tag, dom, currentPage, pageSize) {

    try {
        var t={};

        var category = $("#category").val();
        var isTop = $("#article_isTop").val();
        var status = $("#article_status").val();
        var author = $("#author").val();
        var createTime = $("#createTime").val();
        var updateTime = $("#updateTime").val();

        if(category !=-1){
            t.category = category;
        }
        if(status != undefined && status !=-1){
            t.status = status;
        }

        if(isTop != undefined && isTop !=-1){
            t.isTop = isTop;
        }

        if(author != undefined && author !=""){
            t.author = author;
        }

        if(createTime != undefined && createTime !=""){
            let timeArr=createTime.split(' - ');
            let timeStart=string2Date(timeArr[0]);
            let timeEnd=string2Date(timeArr[1]);
            t.createTimeStart = timeStart;
            t.createTimeEnd = timeEnd;
        }

        if(updateTime != undefined && updateTime !=""){
            let timeArr=updateTime.split(' - ');
            let timeStart=string2Date(timeArr[0]);
            let timeEnd=string2Date(timeArr[1]);
            t.updateTimeStart = timeStart;
            t.updateTimeEnd = timeEnd;
        }


        var reqBody = {};
        reqBody.t = t;
        reqBody.currentPage = currentPage;
        reqBody.pageSize = pageSize;
        showReflesh();

        $.ajax({
            url: "/api/v1/admin/article/page",
            type: "POST",
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            data: JSON.stringify(reqBody),
            success: function (result) {
                removeReflesh();
                if(result.code==1){
                    showDataDiv(result, dom)
                }else{
                    $.MsgBox.Confirm("提示", result.msg, function () {});
                }
            },
            error: function (result) {
                removeReflesh();
                $.MsgBox.Confirm("提示", result.msg, function () {});
            }
        });

    } catch (err) {
    }
}

/**
 * 展示文章列表
 * @param result
 * @param dom
 */
function showDataDiv(result, dom) {
    var data = result.data.rows;
    var start = result.data.currentPage;
    var row = result.data.pageSize;

    var html = '<thead><tr><th>标题</th><th>封面</th><th>作者</th><th>类别</th><th>创建时间</th><th>操作</th></tr></thead><tbody>'

    for (var i = 0; i < data.length; i++) {
        var article = data[i]
        var id = article.id;
        var title = article.title;
        var categoryName = article.categoryName;
        var createTime = new Date(article.createTime).Format("yyyy-MM-dd hh:mm:ss");
        var author = article.author;
        var isTop = article.isTop;
        var status = article.status;
        var index = (start - 1) * row + parseInt(i) + 1;
        var cover = article.cover;

        html += '<tr><td><a href="' + '/article/' + id + '" target="_blank" style="font-weight:bold;">' + title;

        if(isTop==1){
            html += '&ensp;<span class="glyphicon glyphicon-circle-arrow-up"></span>';
        }

        if(status==0){
            html += '&ensp;<span class="glyphicon glyphicon-edit"></span>';
        }

        html += '</a></td>';
        if(cover==undefined||cover==null||cover==''){
              html += '<td></td>';
        }else{
           html += '<td><img id="coverImg'+ id +'" src="'+cover+'" style="height: 100px; "> </td>';
        }

        html += '<td>'+ author +  '</td>';
        html += '<td>'+ categoryName +  '</td>';
        html += '<td>'+ createTime +  '</td>';
        html += '<td><button type="button" class="btn-default" onclick="window.open(\'/admin/article/update/' + id + '\')"><span class="glyphicon glyphicon-pencil"></span>&ensp;编辑</button>&ensp;&ensp;<button type="button" class="btn-default" onclick="articleDelete(' + id + ')" ><span class="glyphicon glyphicon-trash"></span>&ensp;删除</button></td></tr>';
    }
    html += '</tbody>';
    $("#" + dom).html(html);
}


/**
 * 分页查询
 * @param e 点击的分页元素
 */
function toSearch(e) {
    showReflesh();
    try {
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

        var dom = "tableList"
        var tag = $(".activeTag").html();

        if ($.isBlank(tag) || "全部" == tag) {
            tag = '';
        }

        tableList(tag, dom, pageNum, 20);

    } catch (err) {

    } finally {
        removeReflesh();
    }
}



function articleDelete(id){

        if (id == undefined || id == "") {
            $.MsgBox.Confirm("提示", "id不能为空！", function () {
            });
            return false;
        }

        var reqBody = {};
        reqBody.id = id;

        showReflesh();
        $.ajax({
            url: "/api/v1/admin/article/delete",
            type: "POST",
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            data: JSON.stringify(reqBody),
            success: function (result) {
                removeReflesh();

                $.MsgBox.Confirm("提示", result.msg, function () {

                });

                toSearch(null)

            },
            error: function (result) {
                removeReflesh();
                $.MsgBox.Confirm("提示", result.msg, function () {});
            }
        });
    }


    function getArticleCategoryList() {
        try {
            $.ajax({
                url: "/api/v1/admin/article/getAllArticleCategory",
                type: "GET",
                dataType: "json",
                success: function (result) {
                    if(result.code==1){
                        let data = result.data;
                        var categorySelect =document.getElementById("category");

                        for (var k in data) {
                          var map = data[k]
                          var varItem = new Option(map.name, map.id);
                           categorySelect.options.add(varItem);
                        }

                    }else{
                        $.MsgBox.Confirm("提示", result.msg, function () {});
                    }
                },
                error: function (result) {
                    $.MsgBox.Confirm("提示", result.msg, function () {});
                }
            });
        } catch (err) {
        } finally {
        }
    }




/**
 * 文档加载完毕即执行
 */
$(function () {
    toSearch(null);
    getArticleCategoryList();

//日期时间范围
laydate.render({
  elem: '#createTime'
  ,type: 'datetime'
  ,range: true
});
laydate.render({
  elem: '#updateTime'
  ,type: 'datetime'
  ,range: true
});


});

