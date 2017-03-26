/*
Navicat MySQL Data Transfer

Source Server         : Tencent
Source Server Version : 50626
Source Host           : 139.199.155.232:3306
Source Database       : lianluoquan

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2016-12-15 00:58:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `articleuuid` varchar(32) NOT NULL,
  `useruuid` varchar(32) DEFAULT NULL,
  `circleuuid` varchar(32) DEFAULT NULL,
  `title` varchar(32) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `zancount` int(5) DEFAULT '0',
  `photourl` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`articleuuid`),
  KEY `FK_Reference_13` (`circleuuid`),
  KEY `FK_Reference_4` (`useruuid`),
  CONSTRAINT `FK_Reference_13` FOREIGN KEY (`circleuuid`) REFERENCES `circle` (`circleuuid`),
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`useruuid`) REFERENCES `user` (`useruuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES ('251c604c33ca4ac5a0c5f3b56a9c40c3', '957d9588ae494bd5b83e600dc3c1880e', '873beaa1a31f452bb386fd124108cb16', '计算机你好', '通知', '欢迎！！', '2015-05-09 22:48:20', '1', 'upload/img/1431182906243.jpg');
INSERT INTO `article` VALUES ('358ba150d98f49f2b3c88a5ebecd3991', '07218d727d4240ea8615a9b6d4531579', '873beaa1a31f452bb386fd124108cb16', '看见了', '通知', '吐了', '2015-05-09 22:57:23', '0', 'null');
INSERT INTO `article` VALUES ('429745e87e2749e4a3604d8845c9b47d', '07218d727d4240ea8615a9b6d4531579', 'abc03331ed504156ace358e71be133b1', '马上好了', '就业信息', '来测试了', '2015-05-09 22:19:50', '2', 'upload/img/1431181154256.jpg');
INSERT INTO `article` VALUES ('4ca1a6e62fd440d49d141f98f47ee7fa', '529175d047234cf9be58c26eb76dc257', 'abc03331ed504156ace358e71be133b1', 'running man开始报名', '活动进展', '5月10号 开始报名啦！', '2015-05-09 22:21:41', '1', 'null');
INSERT INTO `article` VALUES ('590106e2baa1475eaed6bea3b673caa2', '07218d727d4240ea8615a9b6d4531579', '873beaa1a31f452bb386fd124108cb16', '第二天', '会议', '开会了', '2015-05-09 22:56:51', '0', 'null');
INSERT INTO `article` VALUES ('7ae78427c50347c18626f88ec0ee9668', '07218d727d4240ea8615a9b6d4531579', 'fcfe2cd046ac4583bc5dbe4e8c06b602', '哈哈', '通知', '来了', '2015-05-09 23:01:44', '0', 'upload/img/1431183711245.jpg');
INSERT INTO `article` VALUES ('ce2a534970df4107b21b5b6e66ed4bb1', '07218d727d4240ea8615a9b6d4531579', '873beaa1a31f452bb386fd124108cb16', '哈哈', '通知', '我也加计算机了', '2015-05-09 22:48:51', '0', 'null');

-- ----------------------------
-- Table structure for attachment
-- ----------------------------
DROP TABLE IF EXISTS `attachment`;
CREATE TABLE `attachment` (
  `attachuuid` varchar(32) NOT NULL,
  `articleuuid` varchar(32) DEFAULT NULL,
  `attachname` varchar(255) DEFAULT NULL,
  `attachurl` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`attachuuid`),
  KEY `FK_Reference_11` (`articleuuid`),
  CONSTRAINT `FK_Reference_11` FOREIGN KEY (`articleuuid`) REFERENCES `article` (`articleuuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of attachment
-- ----------------------------

-- ----------------------------
-- Table structure for circle
-- ----------------------------
DROP TABLE IF EXISTS `circle`;
CREATE TABLE `circle` (
  `circleuuid` varchar(32) NOT NULL,
  `circlename` varchar(32) DEFAULT NULL,
  `circlehead` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`circleuuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of circle
-- ----------------------------
INSERT INTO `circle` VALUES ('48af0975c9f1425bb9b2385a8e85d080', '0241202', null);
INSERT INTO `circle` VALUES ('5e52e13cefd1490ab26c074c8d77e68a', '电子与通信工程', null);
INSERT INTO `circle` VALUES ('7be0c1c3f45f406784f57bd6cf08344a', '电子工程学院', null);
INSERT INTO `circle` VALUES ('873beaa1a31f452bb386fd124108cb16', '计算机科学与技术学院', null);
INSERT INTO `circle` VALUES ('abc03331ed504156ace358e71be133b1', '通信与信息工程学院', null);
INSERT INTO `circle` VALUES ('c62308892508440e917600eeba32dcf4', '0121401', null);
INSERT INTO `circle` VALUES ('fcfe2cd046ac4583bc5dbe4e8c06b602', '0191203', null);

-- ----------------------------
-- Table structure for classinf
-- ----------------------------
DROP TABLE IF EXISTS `classinf`;
CREATE TABLE `classinf` (
  `classuuid` varchar(32) NOT NULL,
  `grade` int(4) DEFAULT NULL,
  `college` varchar(32) DEFAULT NULL,
  `classname` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`classuuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of classinf
-- ----------------------------
INSERT INTO `classinf` VALUES ('351a61882d304d7b97d841eaae2a1ff0', '2012', 'tx', '012');
INSERT INTO `classinf` VALUES ('690ffc24b9cc42c79bda2da3664e71ef', '2012', 'tx33', '01233');
INSERT INTO `classinf` VALUES ('73affb159e8b479f9a4cb52d12ccf8d9', '2012', '通信与信息工程学院', '0191203');
INSERT INTO `classinf` VALUES ('74861165803d45eab7499bf7c435b5b6', '2012', '通信与信息工程学院', '0121401');
INSERT INTO `classinf` VALUES ('aca2aa14d9e14ee3be0b899e203d0cba', '2012', '计算机科学与技术学院', '0241202');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `commentuuid` varchar(32) NOT NULL,
  `pubuseruuid` varchar(32) DEFAULT NULL,
  `reuseruuid` varchar(32) DEFAULT NULL,
  `articleuuid` varchar(32) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `layer` int(11) DEFAULT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`commentuuid`),
  KEY `FK_Reference_14` (`articleuuid`),
  KEY `FK_Reference_5` (`pubuseruuid`),
  KEY `FK_Reference_6` (`reuseruuid`),
  CONSTRAINT `FK_Reference_14` FOREIGN KEY (`articleuuid`) REFERENCES `article` (`articleuuid`),
  CONSTRAINT `FK_Reference_5` FOREIGN KEY (`pubuseruuid`) REFERENCES `user` (`useruuid`),
  CONSTRAINT `FK_Reference_6` FOREIGN KEY (`reuseruuid`) REFERENCES `user` (`useruuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('310ad448032f4e9b9caacf8abd38e557', '07218d727d4240ea8615a9b6d4531579', null, '251c604c33ca4ac5a0c5f3b56a9c40c3', '评论了', '0', '2015-05-09 22:53:40');
INSERT INTO `comment` VALUES ('abe05a4bf9ca42a9baa6d2d7c33e3e91', '529175d047234cf9be58c26eb76dc257', null, '429745e87e2749e4a3604d8845c9b47d', '我来给你评论啦', '0', '2015-05-09 22:51:08');
INSERT INTO `comment` VALUES ('fe91c362147446b0912cd8f79e1a73cd', '957d9588ae494bd5b83e600dc3c1880e', null, 'ce2a534970df4107b21b5b6e66ed4bb1', '我来评论啦！', '0', '2015-05-09 22:49:48');

-- ----------------------------
-- Table structure for org
-- ----------------------------
DROP TABLE IF EXISTS `org`;
CREATE TABLE `org` (
  `orguuid` varchar(32) NOT NULL,
  `orgname` varchar(32) DEFAULT NULL,
  `fatheruuid` varchar(32) DEFAULT NULL,
  `layer` int(11) DEFAULT NULL,
  PRIMARY KEY (`orguuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of org
-- ----------------------------
INSERT INTO `org` VALUES ('5e52e13cefd1490ab26c074c8d77e68a', '电子与通信工程', '7be0c1c3f45f406784f57bd6cf08344a', null);
INSERT INTO `org` VALUES ('7be0c1c3f45f406784f57bd6cf08344a', '电子工程学院', 'null', null);

-- ----------------------------
-- Table structure for org_user
-- ----------------------------
DROP TABLE IF EXISTS `org_user`;
CREATE TABLE `org_user` (
  `org_useruuid` varchar(32) NOT NULL,
  `useruuid` varchar(32) DEFAULT NULL,
  `orguuid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`org_useruuid`),
  KEY `FK_Reference_8` (`orguuid`),
  KEY `FK_Reference_9` (`useruuid`),
  CONSTRAINT `FK_Reference_8` FOREIGN KEY (`orguuid`) REFERENCES `org` (`orguuid`),
  CONSTRAINT `FK_Reference_9` FOREIGN KEY (`useruuid`) REFERENCES `user` (`useruuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of org_user
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `useruuid` varchar(32) NOT NULL,
  `classuuid` varchar(32) DEFAULT NULL,
  `name` varchar(8) DEFAULT NULL,
  `password` varchar(16) DEFAULT NULL,
  `studentnum` varchar(10) DEFAULT NULL,
  `userhead` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`useruuid`),
  KEY `FK_Reference_10` (`classuuid`),
  CONSTRAINT `FK_Reference_10` FOREIGN KEY (`classuuid`) REFERENCES `classinf` (`classuuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('07218d727d4240ea8615a9b6d4531579', '73affb159e8b479f9a4cb52d12ccf8d9', '李双', '123456', '2012210625', null);
INSERT INTO `user` VALUES ('529175d047234cf9be58c26eb76dc257', '74861165803d45eab7499bf7c435b5b6', '张天乐', '123456', '2012210568', null);
INSERT INTO `user` VALUES ('957d9588ae494bd5b83e600dc3c1880e', 'aca2aa14d9e14ee3be0b899e203d0cba', '朱权威', '123456', '2012211565', null);

-- ----------------------------
-- Table structure for user_circle
-- ----------------------------
DROP TABLE IF EXISTS `user_circle`;
CREATE TABLE `user_circle` (
  `ucuuid` varchar(32) NOT NULL,
  `useruuid` varchar(32) DEFAULT NULL,
  `circleuuid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ucuuid`),
  KEY `FK_Reference_12` (`useruuid`),
  KEY `FK_Reference_15` (`circleuuid`),
  CONSTRAINT `FK_Reference_12` FOREIGN KEY (`useruuid`) REFERENCES `user` (`useruuid`),
  CONSTRAINT `FK_Reference_15` FOREIGN KEY (`circleuuid`) REFERENCES `circle` (`circleuuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_circle
-- ----------------------------
INSERT INTO `user_circle` VALUES ('02701214157742c19b886193789354d0', '529175d047234cf9be58c26eb76dc257', '873beaa1a31f452bb386fd124108cb16');
INSERT INTO `user_circle` VALUES ('19dbe8a28fb849e3aec9b1e9abb79a19', '529175d047234cf9be58c26eb76dc257', 'c62308892508440e917600eeba32dcf4');
INSERT INTO `user_circle` VALUES ('1f7a403e92c14221a437712f2bed98da', '957d9588ae494bd5b83e600dc3c1880e', '873beaa1a31f452bb386fd124108cb16');
INSERT INTO `user_circle` VALUES ('35580af49e764766aa17375a486151c6', '529175d047234cf9be58c26eb76dc257', 'abc03331ed504156ace358e71be133b1');
INSERT INTO `user_circle` VALUES ('5686be827f0440d496c4e49bd0fc766c', '957d9588ae494bd5b83e600dc3c1880e', '48af0975c9f1425bb9b2385a8e85d080');
INSERT INTO `user_circle` VALUES ('7a386da997e0412c8158dcef83ab5f88', '07218d727d4240ea8615a9b6d4531579', 'fcfe2cd046ac4583bc5dbe4e8c06b602');
INSERT INTO `user_circle` VALUES ('d0f9ba7e3d1941028a0b9e480f5da535', '07218d727d4240ea8615a9b6d4531579', 'abc03331ed504156ace358e71be133b1');
INSERT INTO `user_circle` VALUES ('df2d89c81adb4987b48a58e83868822c', '07218d727d4240ea8615a9b6d4531579', '873beaa1a31f452bb386fd124108cb16');

-- ----------------------------
-- Table structure for user_friend
-- ----------------------------
DROP TABLE IF EXISTS `user_friend`;
CREATE TABLE `user_friend` (
  `ufuuid` varchar(32) NOT NULL,
  `useruuid` varchar(32) DEFAULT NULL,
  `frienduuid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ufuuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_friend
-- ----------------------------
INSERT INTO `user_friend` VALUES ('9d1b103476074645afd13b3804f5d277', '529175d047234cf9be58c26eb76dc257', '07218d727d4240ea8615a9b6d4531579');
INSERT INTO `user_friend` VALUES ('d75602eb577540d3b06d313f78048a3d', '07218d727d4240ea8615a9b6d4531579', '529175d047234cf9be58c26eb76dc257');
