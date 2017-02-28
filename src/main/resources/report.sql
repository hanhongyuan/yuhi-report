/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50547
Source Host           : localhost:3306
Source Database       : report

Target Server Type    : MYSQL
Target Server Version : 50547
File Encoding         : 65001

Date: 2017-02-28 13:49:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for data
-- ----------------------------
DROP TABLE IF EXISTS `data`;
CREATE TABLE `data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `template` int(11) DEFAULT NULL,
  `params` text,
  `type` varchar(10) DEFAULT NULL,
  `datasource` varchar(10) DEFAULT NULL,
  `key` varchar(40) DEFAULT NULL,
  `mode` int(2) DEFAULT NULL,
  `dataset` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `数据关联` (`template`),
  CONSTRAINT `数据关联` FOREIGN KEY (`template`) REFERENCES `templet` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for data_source
-- ----------------------------
DROP TABLE IF EXISTS `data_source`;
CREATE TABLE `data_source` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) DEFAULT NULL,
  `table_name` varchar(40) DEFAULT NULL,
  `param` varchar(200) DEFAULT NULL,
  `sort` varchar(40) DEFAULT NULL,
  `create_user` varchar(200) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `status` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for params
-- ----------------------------
DROP TABLE IF EXISTS `params`;
CREATE TABLE `params` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `templet_id` int(20) DEFAULT NULL COMMENT '模板id',
  `templet_version` int(8) DEFAULT NULL COMMENT '版本号',
  `class` varchar(100) DEFAULT NULL COMMENT '参数类型',
  `type` int(2) DEFAULT NULL COMMENT '参数种类:0:params,1:field',
  `name` varchar(100) DEFAULT NULL COMMENT '名字',
  `default_value` varchar(100) DEFAULT NULL COMMENT '默认值',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `status` int(2) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=197 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for templet
-- ----------------------------
DROP TABLE IF EXISTS `templet`;
CREATE TABLE `templet` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `jasperurl` varchar(100) DEFAULT NULL COMMENT '地址',
  `code` varchar(40) DEFAULT NULL COMMENT '验证码',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `name` varchar(40) CHARACTER SET utf8 DEFAULT NULL COMMENT '名称',
  `status` int(2) DEFAULT NULL COMMENT '状态',
  `jrxmlurl` varchar(100) DEFAULT NULL,
  `sql_sentence` varchar(200) DEFAULT NULL,
  `params` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `mode` int(2) DEFAULT NULL,
  `create_user` varchar(200) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(200) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for version
-- ----------------------------
DROP TABLE IF EXISTS `version`;
CREATE TABLE `version` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '自身id',
  `templet_id` int(20) DEFAULT NULL COMMENT '模板id',
  `version` int(8) DEFAULT NULL COMMENT '版本号',
  `create_time` datetime DEFAULT NULL COMMENT ' 创建日期',
  `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
  `jasper_url` varchar(80) DEFAULT NULL COMMENT 'jasper文件地址',
  `jrxml_url` varchar(80) DEFAULT NULL COMMENT 'jrxml地址',
  `status` varchar(2) DEFAULT NULL COMMENT '状态:0可用,1禁用',
  PRIMARY KEY (`id`),
  KEY `版本关联` (`templet_id`),
  CONSTRAINT `版本关联` FOREIGN KEY (`templet_id`) REFERENCES `templet` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8 COMMENT='版本历史信息';
