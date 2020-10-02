package com.zrzhen.admin.module.admin.controller;

import com.zrzhen.admin.module.admin.dao.AdminTableMapper;
import com.zrzhen.admin.module.admin.po.AdminTable;
import com.zrzhen.admin.module.admin.req.AdminReq;
import com.zrzhen.admin.result.RestResult;
import com.zrzhen.admin.util.idworker.IdUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestMapping("/admin/AdminTable")
@RestController
public class AdminTableController {

    private static Logger log = LoggerFactory.getLogger(AdminTableController.class);


    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;

    @Autowired
    AdminTableMapper adminTableMapper;


    @GetMapping("/all")
    public RestResult<List<AdminTable>> all() {
        List<AdminTable> all = adminTableMapper.all();

        return RestResult.buildSuccess(all);
    }

    @GetMapping("/table/{tableName}")
    public RestResult<Map<String, Object>> table(@PathVariable String tableName) {

        String pageNumStr = request.getParameter("pageNum");
        String pageSideStr = request.getParameter("pageSide");
        Integer start = StringUtils.isBlank(pageNumStr) ? 1 : Integer.valueOf(pageNumStr);
        Integer row = StringUtils.isBlank(pageSideStr) ? 5 : Integer.valueOf(pageSideStr);

        List<Map> all = adminTableMapper.listFromTable(tableName, (start - 1) * row, row);
        List<Map> columns = adminTableMapper.columns(tableName);

        List<Map> pri = new ArrayList<>();
        List<Map> notPri = new ArrayList<>();

        for (Map column : columns) {
            if ("PRI".equalsIgnoreCase((String) column.get("COLUMN_KEY"))) {
                pri.add(column);
            } else {
                notPri.add(column);
            }
        }

        Map<String, Object> data = new HashMap<>();
        data.put("data", all);
        data.put("pri", pri);
        data.put("notPri", notPri);
        data.put("tableName", tableName);

        return RestResult.buildSuccess(data);
    }

    @PostMapping("/insert")
    public RestResult insert(@RequestBody AdminReq adminReq) {
        try {
            //List<Map> pri = adminReq.getPri();
            List<Map> columnEdit = adminReq.getNotPri();
            String tableName = adminReq.getTableName();
            StringBuilder sb = new StringBuilder();
            StringBuilder sbv = new StringBuilder();
            sb.append("INSERT INTO ");
            sb.append(tableName);
            sb.append(" (");
            sbv.append(" VALUES (");


            //主键
            sb.append("id");
            sb.append(",");
            sbv.append(IdUtil.getId());
            sbv.append(",");

            for (Map map : columnEdit) {
                String columnName = (String) map.get("COLUMN_NAME");
                String dataType = (String) map.get("DATA_TYPE");
                Object value = map.get("value");
                if ("varchar".equalsIgnoreCase(dataType)||"text".equalsIgnoreCase(dataType)) {
                    value = "'" + value + "'";
                }
                sb.append(columnName);
                sb.append(",");
                sbv.append(value);
                sbv.append(",");
            }


            sb.deleteCharAt(sb.length() - 1);
            sbv.deleteCharAt(sbv.length() - 1);
            sb.append(")");
            sbv.append(")");
            sb.append(sbv);
            String sql = sb.toString();
            adminTableMapper.execute(sql);
            return RestResult.buildSuccessWithMsg("新增成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return RestResult.buildFailWithMsg("新增失败");
        }
    }

    @PostMapping("/update")
    public RestResult update(@RequestBody AdminReq adminReq) {
        try {
            List<Map> pri = adminReq.getPri();
            List<Map> notPri = adminReq.getNotPri();
            String tableName = adminReq.getTableName();
            String sql = "UPDATE " + tableName + " SET ";

            for (Map map : notPri) {
                String columnName = (String) map.get("COLUMN_NAME");
                String dataType = (String) map.get("DATA_TYPE");
                Object value = map.get("value");
                if ("varchar".equalsIgnoreCase(dataType)) {
                    value = "'" + value + "'";
                }
                sql += columnName + " = " + value;
            }
            for (int i = 0; i < pri.size(); i++) {
                String columnName = (String) pri.get(i).get("COLUMN_NAME");
                String dataType = (String) pri.get(i).get("DATA_TYPE");
                Object value = pri.get(i).get("value");

                if ("varchar".equalsIgnoreCase(dataType)) {
                    value = "'" + value + "'";
                }

                if (i == 0) {
                    sql += " where " + columnName + " = " + value;
                } else {
                    sql += " and " + columnName + " = " + value;
                }
            }
            adminTableMapper.execute(sql);
            return RestResult.buildSuccessWithMsg("更新成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return RestResult.buildFailWithMsg("更新失败");
        }

    }


    @PostMapping("/remove")
    public RestResult remove(@RequestBody AdminReq adminReq) {
        try {
            List<Map> pri = adminReq.getPri();
            String tableName = adminReq.getTableName();
            String sql = "DELETE FROM " + tableName + " WHERE ";
            for (int i = 0; i < pri.size(); i++) {
                String columnName = (String) pri.get(i).get("COLUMN_NAME");
                String dataType = (String) pri.get(i).get("DATA_TYPE");
                Object value = pri.get(i).get("value");
                if ("varchar".equalsIgnoreCase(dataType)) {
                    value = "'" + value + "'";
                }
                if (i == 0) {
                    sql += columnName + " = " + value;
                } else {
                    sql += " and " + columnName + " = " + value;
                }
            }
            adminTableMapper.execute(sql);
            return RestResult.buildSuccessWithMsg("删除成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return RestResult.buildFailWithMsg("删除失败");
        }
    }


}
