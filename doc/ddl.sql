-- officialsite.admin_table definition

CREATE TABLE `admin_table` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `table_name` varchar(255) NOT NULL COMMENT '表名',
  `creator` bigint(20) unsigned DEFAULT '0' COMMENT '创建者',
  `updater` bigint(20) unsigned DEFAULT '0' COMMENT '更新者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台管理可操作表';


-- officialsite.article definition

CREATE TABLE `article` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `title` varchar(256) DEFAULT NULL COMMENT '标题',
  `author` varchar(64) DEFAULT NULL COMMENT '作者',
  `summary` varchar(2048) DEFAULT NULL COMMENT '摘要',
  `cover` varchar(512) DEFAULT NULL COMMENT '封面图片',
  `cover_alt` varchar(100) DEFAULT NULL,
  `cover_direct` varchar(100) DEFAULT NULL COMMENT '封面点击时跳转；尤其是轮播图类型时有用',
  `content` text NOT NULL COMMENT '内容',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态，0草稿；1发布；2私密',
  `category` bigint(20) DEFAULT '0' COMMENT '分类',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `is_top` tinyint(4) DEFAULT '0' COMMENT '是否置顶；0否；1是；',
  `creator` bigint(20) unsigned DEFAULT '0' COMMENT '创建者',
  `updater` bigint(20) unsigned DEFAULT '0' COMMENT '更新者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章表';


-- officialsite.article_category definition

CREATE TABLE `article_category` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `name` varchar(255) NOT NULL COMMENT '表名',
  `description` varchar(1024) DEFAULT NULL COMMENT '描述',
  `creator` bigint(20) unsigned DEFAULT '0' COMMENT '创建者',
  `updater` bigint(20) unsigned DEFAULT '0' COMMENT '更新者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章分类表';


-- officialsite.article_tag definition

CREATE TABLE `article_tag` (
  `article` bigint(20) NOT NULL COMMENT '文章id',
  `tag` varchar(255) NOT NULL,
  `creator` bigint(20) unsigned DEFAULT '0' COMMENT '创建者',
  `updater` bigint(20) unsigned DEFAULT '0' COMMENT '更新者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`article`,`tag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章标签关联';


-- officialsite.banner definition

CREATE TABLE `banner` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `title` varchar(256) DEFAULT NULL COMMENT '标题',
  `img` varchar(512) NOT NULL COMMENT '图片',
  `url` varchar(512) DEFAULT NULL COMMENT '跳转地址',
  `summary` varchar(1024) DEFAULT NULL COMMENT '摘要',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态，0草稿；1发布；2私密',
  `category` bigint(20) DEFAULT '0' COMMENT '分类',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `is_top` tinyint(4) DEFAULT '0' COMMENT '是否置顶；0否；1是；',
  `creator` bigint(20) unsigned DEFAULT '0' COMMENT '创建者',
  `updater` bigint(20) unsigned DEFAULT '0' COMMENT '更新者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='轮播图';