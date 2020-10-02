package com.zrzhen.admin.codemake;

import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

public class CodeGen {

    private static final String AUTHOR = "chenanlian";
    private static final String EMAIL = "a@zrzhen.com";


    /**
     * 配置文件放置的模块
     */
    public static final String module = "admin";

    /**
     * 是否只生成po、dao层
     */
    private static final boolean onlyGenPoAndDao = true;

    /**
     * 是否是关系表
     */
    private static final boolean isRelationTable = false;

    /**
     * 表名
     */
    private static final String table = "article";

    /**
     * 忽略的表名前缀
     */
    private static final String table_pre = "";

    public static void main(String[] args) {
        initTypeMap();
        genCode(table);
    }


    private static final String PROJECT_PATH = System.getProperty("user.dir");//项目在硬盘上的基础路径

    //生成代码所在的基础包名称
    public static final String BASE_PACKAGE = "com.nino.officialsite.module." + module;
    //模板位置
    private static String TEMPLATE_FILE_PATH = PROJECT_PATH + "/src/main/resources/codemake";


    //JDBC配置
    private static final String JDBC_URL = "jdbc:mysql://192.168.1.7:3306/officialsite?useUnicode=true&useSSL=true&verifyServerCertificate=false&characterEncoding=utf-8&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai";
    private static final String JDBC_USERNAME = "nino";
    private static final String JDBC_PASSWORD = "Nino@1234";
    private static final String JDBC_DIVER_CLASS_NAME = "com.mysql.jdbc.Driver";


    private static final String JAVA_PATH = "/src/main/java"; //java文件路径
    private static final String RESOURCES_PATH = "/src/main/resources";//资源文件路径


    public static final String PO_PACKAGE = BASE_PACKAGE + ".PO";//生成的Model所在包
    public static final String PARAM_PACKAGE = BASE_PACKAGE + ".param";//生成的Service所在包

    public static final String DAO_PACKAGE = BASE_PACKAGE + ".dao";//生成的Mapper所在包
    public static final String PROVIDER_PACKAGE = BASE_PACKAGE + ".dao";//生成的Mapper所在包
    public static final String SERVICE_PACKAGE = BASE_PACKAGE + ".service";//生成的Service所在包
    public static final String BASE_SERVICE_PACKAGE = BASE_PACKAGE + ".persistence.service";//生成的Service所在包
    public static final String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";//生成的ServiceImpl所在包
    public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".controller";//生成的Controller所在包

    public static final String CONTROLLER_MODEL_PACKAGE = BASE_PACKAGE + ".dto";//生成的Controller所在包

    public static final String MAPPER_INTERFACE_REFERENCE = BASE_PACKAGE + ".common.core.Mapper";//Mapper插件基础接口的完全限定名

    private static final String PACKAGE_PATH_PO = packageConvertPath(PO_PACKAGE);//生成的Service存放路径
    private static final String PACKAGE_PATH_DAO = packageConvertPath(DAO_PACKAGE);//生成的Service存放路径
    private static final String PACKAGE_PATH_PROVIDER = packageConvertPath(PROVIDER_PACKAGE);//生成的Service存放路径
    private static final String PACKAGE_PATH_PARAM = packageConvertPath(PARAM_PACKAGE);//生成的Service存放路径
    private static final String PACKAGE_PATH_SERVICE = packageConvertPath(SERVICE_PACKAGE);//生成的Service存放路径
    private static final String BASE_PACKAGE_PATH_SERVICE = packageConvertPath(BASE_SERVICE_PACKAGE);//生成的Service存放路径
    private static final String PACKAGE_PATH_SERVICE_IMPL = packageConvertPath(SERVICE_IMPL_PACKAGE);//生成的Service实现存放路径
    private static final String PACKAGE_PATH_CONTROLLER = packageConvertPath(CONTROLLER_PACKAGE);//生成的Controller存放路径
    private static final String PACKAGE_PATH_CONTROLLER_MODEL = packageConvertPath(CONTROLLER_MODEL_PACKAGE);


    private static final String DATE = new SimpleDateFormat("yyyy/MM/dd").format(new Date());//@date
    private static final Map<String, String> TYPE_MAP = new HashMap<>();


    private static void initTypeMap() {
        TYPE_MAP.put("bigint", "Long");
        TYPE_MAP.put("varchar", "String");
        TYPE_MAP.put("text", "String");
        TYPE_MAP.put("int", "Integer");
        TYPE_MAP.put("tinyint", "Integer");
        TYPE_MAP.put("smallint", "Integer");
        TYPE_MAP.put("mediumint", "Integer");
        TYPE_MAP.put("bit", "Boolean");
        TYPE_MAP.put("decimal", "BigDecimal");
        TYPE_MAP.put("datetime", "Date");
    }

    /**
     * 通过数据表名称生成代码，Model 名称通过解析数据表名称获得，下划线转大驼峰的形式。
     * 如输入表名称 "t_user_detail" 将生成 TUserDetail、TUserDetailMapper、TUserDetailService ...
     *
     * @param tableNames 数据表名称...
     */
    public static void genCode(String... tableNames) {
        for (String tableName : tableNames) {
            genCodeByCustomModelName(tableName, null);
        }
    }

    /**
     * 通过数据表名称，和自定义的 Model 名称生成代码
     * 如输入表名称 "t_user_detail" 和自定义的 Model 名称 "User" 将生成 User、UserMapper、UserService ...
     *
     * @param tableName 数据表名称
     * @param modelName 自定义的 Model 名称
     */
    public static void genCodeByCustomModelName(String tableName, String modelName) {
        //genModelAndMapper(tableName, modelName);
        //genService(tableName, modelName);
        //genController(tableName, modelName);
        generatorCodeAll(tableName);
    }

    private static void generatorCodeAll(String tableName) {
        Connection conn = null;
        try {
            String tableSql = "select table_name tableName, engine, table_comment tableComment, create_time createTime from information_schema.tables where table_schema = (select database()) and table_name = '%s'";

            String columnSql = "select column_name columnName, data_type dataType, column_comment columnComment, column_key columnKey, extra from information_schema.columns where table_name = '%s' and table_schema = (select database()) order by ordinal_position";
            // 1.加载数据访问驱动
            Class.forName(JDBC_DIVER_CLASS_NAME);
            //2.连接到数据"库"上去
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
            //3.构建SQL命令
            Statement statement = conn.createStatement();
            ResultSet table_Rs = statement.executeQuery(String.format(tableSql, tableName));
            Map<String, String> table = resultSetToList(table_Rs).get(0);
            ResultSet column_Rs = statement.executeQuery(String.format(columnSql, tableName));
            List<Map<String, String>> columns = resultSetToList(column_Rs);
            generatorCode(table, columns);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("queryTableInfo失败", e);
        } finally {
            if (null != conn) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }


    public static void generatorCode(Map<String, String> table, List<Map<String, String>> columns) {
        // 表信息
        String tableName = table.get("TableName");
        if (tableName.startsWith(table_pre)) {
            tableName = tableName.substring(table_pre.length());
        }
        String tableComment = table.get("TableComment");

        if (StringUtils.isNotBlank(tableComment)) {
            int nIndex = tableComment.indexOf("\r\n");
            if (nIndex < 0) {
                nIndex = tableComment.indexOf("\n");

            }
            if (nIndex > 0) {
                tableComment = tableComment.substring(0, nIndex);
            }
        }

        // 表名转换成Java类名
        String className = tableToJava(tableName, "", true);
        String classNameLow = StringUtils.uncapitalize(className);

        String classNameSuffix = tableToJava(tableName, "", false);
        String classNameSuffixLow = StringUtils.uncapitalize(classNameSuffix);

        //主键列
        List<ColumnDO> pklist = new ArrayList<>();
        //非主键列
        List<ColumnDO> columsList = new ArrayList<>();
        StringBuffer columnDesc = new StringBuffer();
        for (Map<String, String> column : columns) {
            ColumnDO columnDO = new ColumnDO();
            columnDO.setColumnName(column.get("ColumnName"));

            columnDO.setComments(column.get("ColumnComment"));

            String dataType = column.get("DataType");
            columnDO.setDataType(dataType);
            if (dataType.equalsIgnoreCase("datetime")) {
                columnDO.setDataTypeUpcase("TIMESTAMP");
            } else if (dataType.equalsIgnoreCase("int")) {
                columnDO.setDataTypeUpcase("INTEGER");
            } else if (dataType.equalsIgnoreCase("tinyint")) {
                columnDO.setDataTypeUpcase("TINYINT");
            } else if (dataType.equalsIgnoreCase("bigint")) {
                columnDO.setDataTypeUpcase("BIGINT");
            } else if (dataType.equalsIgnoreCase("smallint")) {
                columnDO.setDataTypeUpcase("SMALLINT");
            } else if (dataType.equalsIgnoreCase("mediumint")) {
                columnDO.setDataTypeUpcase("MEDIUMINT");
            } else {
                columnDO.setDataTypeUpcase(columnDO.getDataType().toUpperCase());
            }

            columnDO.setExtra(column.get("Extra"));
            columnDesc.append(column.get("ColumnName") + "|").append(column.get("DataType") + "|").append(column.get("ColumnComment")).append(";");
            // 列名转换成Java属性名
            String attrName = columnToJava(columnDO.getColumnName());
            columnDO.setAttrName(attrName);
            columnDO.setAttrname(StringUtils.uncapitalize(attrName));

            // 列的数据类型，转换成Java类型
            String attrType = TYPE_MAP.get(columnDO.getDataType());
            columnDO.setAttrType(attrType);

            // 是否主键
            if ("PRI".equalsIgnoreCase(column.get("ColumnKey"))) {
                pklist.add(columnDO);
            } else {
                columsList.add(columnDO);
            }

        }


        // 封装模板数据
        Map<String, Object> map = new HashMap<>(16);
        map.put("tableName", tableName);
        map.put("comments", tableComment == null ? "" : tableComment.replaceAll("表", ""));
        map.put("pkList", pklist);
        map.put("columns", columsList);
        map.put("classname", classNameSuffix);
        map.put("classnameLowCase", classNameSuffixLow);
        map.put("classNameSuffix", classNameSuffix);
        map.put("classnameSuffix", classNameSuffixLow);
        //map.put("pathName", config.getString("package").substring(config.getString("package").lastIndexOf(".") + 1));

        //map.put("package", config.getString("package"));
        map.put("author", AUTHOR);
        map.put("email", EMAIL);
        map.put("datetime", DATE);
        map.put("columnDesc", columnDesc);
        map.put("module", module);
        map.put("isRelationTable", isRelationTable ? 1 : 0);

        try {
            freemarker.template.Configuration cfg = getConfiguration();


            String path = PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_PO + "/" + classNameSuffix + ".java";
            File file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("po.ftl").process(map, new FileWriter(file));


            if (!onlyGenPoAndDao) {
                path = PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_PARAM + "/" + classNameSuffix + "BaseAddParam.java";
                file = new File(path);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                cfg.getTemplate("addParam.ftl").process(map, new FileWriter(file));

                path = PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_PARAM + "/" + classNameSuffix + "BaseUpdateParam.java";
                file = new File(path);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                cfg.getTemplate("updateParam.ftl").process(map, new FileWriter(file));

                path = PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_PARAM + "/" + classNameSuffix + "BasePageParam.java";
                file = new File(path);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                cfg.getTemplate("pageParam.ftl").process(map, new FileWriter(file));
            }


            path = PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_DAO + "/" + classNameSuffix + "BaseMapper.java";
            file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("mapper.ftl").process(map, new FileWriter(file));

            path = PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_PROVIDER + "/" + classNameSuffix + "BaseProvider.java";
            file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("provider.ftl").process(map, new FileWriter(file));

            //cfg.getTemplate("editDomain.ftl").process(map, new FileWriter(new File(path + "Edit" + className + "Request.java")));
            //cfg.getTemplate("queryDomain.ftl").process(map, new FileWriter(new File(path + className + "QueryRequest.java")));
            //cfg.getTemplate("advancedQueryDomain.ftl").process(map, new FileWriter(new File(path+className+"AdvancedQueryRequest.java")));
            //cfg.getTemplate("responseDomain.ftl").process(map, new FileWriter(new File(path + className + "Response.java")));
            //cfg.getTemplate("exportDomain.ftl").process(map, new FileWriter(new File(path + className + "Export.java")));

//            path = PROJECT_PATH + JAVA_PATH + BASE_PACKAGE_PATH_SERVICE + "/Base" + classNameSuffix + "Service.java";
//            file = new File(path);
//            if (!file.getParentFile().exists()) {
//                file.getParentFile().mkdirs();
//            }
            //cfg.getTemplate("BaseService.ftl").process(map, new FileWriter(file));

            if (!onlyGenPoAndDao) {
                path = PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_SERVICE + "/" + classNameSuffix + "BaseService.java";
                file = new File(path);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                cfg.getTemplate("service.ftl").process(map, new FileWriter(file));

                path = PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_SERVICE + "/impl/" + classNameSuffix + "BaseServiceImpl.java";
                file = new File(path);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                cfg.getTemplate("service-impl.ftl").process(map, new FileWriter(file));

                path = PROJECT_PATH + JAVA_PATH + PACKAGE_PATH_CONTROLLER + "/" + classNameSuffix + "BaseController.java";
                file = new File(path);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                cfg.getTemplate("controller.ftl").process(map, new FileWriter(file));

            }

        } catch (Exception e) {
            throw new RuntimeException("生成失败", e);
        }

    }


    public static List<Map<String, String>> resultSetToList(ResultSet rs) throws SQLException {
        List<Map<String, String>> results = new ArrayList<Map<String, String>>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();
        List<String> colNameList = new ArrayList<String>();
        for (int i = 0; i < colCount; i++) {
            colNameList.add(rsmd.getColumnName(i + 1));
        }
        while (rs.next()) {
            Map map = new HashMap<String, String>();
            for (int i = 0; i < colCount; i++) {
                String key = colNameList.get(i);
                String value = rs.getString(i + 1);
                map.put(columnToJava(key), value);
            }
            results.add(map);
        }
        return results;
    }

    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    private static String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }


    public static String tableToJava(String tableName, String tablePrefix, boolean flag) {
        if (flag) {
            tableName = tableName.substring(tableName.indexOf("_") + 1);
        }
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "");
        }

        return columnToJava(tableName);
    }

    private static freemarker.template.Configuration getConfiguration() throws IOException {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_FILE_PATH));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }
}
