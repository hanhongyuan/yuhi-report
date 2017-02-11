/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50547
Source Host           : localhost:3306
Source Database       : report

Target Server Type    : MYSQL
Target Server Version : 50547
File Encoding         : 65001

Date: 2017-02-11 15:52:29
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
  CONSTRAINT `数据关联` FOREIGN KEY (`template`) REFERENCES `templet` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of data
-- ----------------------------

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of data_source
-- ----------------------------
INSERT INTO `data_source` VALUES ('1', '数据源1', '1', '0', '11');
INSERT INTO `data_source` VALUES ('2', '数据源2', 'data', 'param:1', '11');
INSERT INTO `data_source` VALUES ('3', '数据源3', 'Template', 'sss', 'sss');
INSERT INTO `data_source` VALUES ('4', '数据源4', 'data_source', 'sss', '111');
INSERT INTO `data_source` VALUES ('5', 'ssss', 'ssss', 'sss', 'ss');

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of templet
-- ----------------------------
INSERT INTO `templet` VALUES ('9', '/report/9/version8/1b9448a3-fdea-46f8-b6cf-f7e2cd70231c.jasper', '8afa6ad1-b3fc-4a43-af1b-f937e8ea159f', '8', '纯外部参数报表demo', '1', '/report/9/version8/ac665647-4f28-443a-b87d-a7b8e34f1e8f.jrxml', '', '', '1');
INSERT INTO `templet` VALUES ('13', '/report/13/version5/58744ae7-6608-46c0-a73e-0451d43aaa42.jasper', 'c7c80191-dfa5-4129-b980-e55c84e5645b', '5', '纯数据源报表demo', '1', '/report/13/version5/3da7c81f-f599-4747-970f-58aa9e9aff0c.jrxml', 'SELECT * FROM DATA_SOURCE WHERE  SORT=?', 'sss', '1');
INSERT INTO `templet` VALUES ('14', '/report/14/version5/d7d51960-e346-4cc3-b335-f7b78f863fa5.jasper', 'b3ab149e-92e1-4ffa-a650-824975ee52f6', '5', '外部参数与数据源混合报表demo', '1', '/report/14/version5/77e780e9-38dc-4df0-937e-e4300cecb1e9.jrxml', 'SELECT * FROM DATA', '', '2');
INSERT INTO `templet` VALUES ('15', '/report/15/version19/4854c141-a6eb-47d2-8f2c-d16d653339a9.jasper', '874faab1-8a29-47bb-927b-682d80af7fe0', '19', '客户报修派工单', '1', '/report/15/version19/fa49bb0d-4833-4af7-9011-eee5c3489f43.jrxml', 'SELECT * FROM DATA_SOURCE', '', '2');
INSERT INTO `templet` VALUES ('16', '/report/16/version7/e61b35b3-fa33-4f3d-881b-70c87606f46e.jasper', 'c596436c-e542-495b-b7a7-f64b04a8df73', '7', '物业公告通知', '1', '/report/16/version7/2c7a24a5-37a8-4313-bb08-d8743385357f.jrxml', '', '', '0');
INSERT INTO `templet` VALUES ('50', null, '9e4f6390-cc3b-4bb0-99c2-2bba3e857e26', '1', '阿', null, null, '1', '1', '0');
INSERT INTO `templet` VALUES ('51', '/report/51/version5/cbafc54a-a919-4408-859a-25b6c0ab530e.jasper', 'b373accf-308c-45ed-8c09-fcc9cf5d02b4', '5', '1', null, '/report/51/version5/079d82f8-2ad8-4fa5-9b93-d6512f0a6b30.jrxml', '1', '1', '0');
INSERT INTO `templet` VALUES ('52', '/report//version1/8f1f2bf1-cdf5-4cf9-b963-3fe8e466af8f.jasper', '1859dab5-66cf-4a2a-bbb4-662affdee6ba', '2', '1', null, '/report/52/version2/5303a30b-d82f-4c4f-a636-4d1feda39790.jrxml', '1', '1', '1');

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
  CONSTRAINT `版本关联` FOREIGN KEY (`templet_id`) REFERENCES `templet` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='版本历史信息';

-- ----------------------------
-- Records of version
-- ----------------------------
INSERT INTO `version` VALUES ('29', '51', '1', '2017-02-11 10:18:36', null, '/report//version1/025890a5-554f-4e16-a197-ab8abef320a5.jasper', '/report//version1/d789300e-4f62-41ce-8d38-b12d2eeca3eb.jrxml', '0');
INSERT INTO `version` VALUES ('30', '51', '1', '2017-02-11 10:19:21', null, '/report/51/version2/0ec30dd8-d2e1-42c8-9866-70a4d4a6716e.jasper', '/report/51/version2/b4ef41ed-b155-4400-bd66-cf7bdb81e6e3.jrxml', '0');
INSERT INTO `version` VALUES ('31', '51', '1', '2017-02-11 10:20:13', null, '/report/51/version3/d07434be-8f0d-4193-b494-0ed3fd0d013e.jasper', '/report/51/version3/720cacff-40f0-4900-9ddb-bfd5353301ce.jrxml', '0');
INSERT INTO `version` VALUES ('32', '51', '1', '2017-02-11 10:21:22', null, '/report/51/version4/2b8f0cd7-2a5e-4c36-b54f-ce1b30715667.jasper', '/report/51/version4/83b39797-e9a0-44eb-a572-c7df30df0e0c.jrxml', '0');
INSERT INTO `version` VALUES ('33', '51', '5', '2017-02-11 10:22:24', null, '/report/51/version5/cbafc54a-a919-4408-859a-25b6c0ab530e.jasper', '/report/51/version5/079d82f8-2ad8-4fa5-9b93-d6512f0a6b30.jrxml', '0');
INSERT INTO `version` VALUES ('34', '52', '1', '2017-02-11 10:26:33', null, '/report//version1/8f1f2bf1-cdf5-4cf9-b963-3fe8e466af8f.jasper', '/report//version1/32fedca6-351a-498b-bc95-d59ebb96603b.jrxml', '0');
INSERT INTO `version` VALUES ('35', '52', '2', '2017-02-11 10:26:53', null, '/report//version1/8f1f2bf1-cdf5-4cf9-b963-3fe8e466af8f.jasper', '/report/52/version2/5303a30b-d82f-4c4f-a636-4d1feda39790.jrxml', '0');
