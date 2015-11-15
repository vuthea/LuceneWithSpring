/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50617
Source Host           : localhost:3306
Source Database       : bignews

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2015-11-16 00:10:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `articles`
-- ----------------------------
DROP TABLE IF EXISTS `articles`;
CREATE TABLE `articles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` text,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of articles
-- ----------------------------
INSERT INTO `articles` VALUES ('1', 'Cambodia Kingdom of wonder', '2015-11-16 23:46:05');
INSERT INTO `articles` VALUES ('2', 'I love Cambodia', '2015-11-09 20:10:40');
INSERT INTO `articles` VALUES ('3', 'Study online with Khmer Academy', '2015-11-16 20:10:45');
INSERT INTO `articles` VALUES ('4', 'I am khmer', '2015-11-02 21:22:33');
INSERT INTO `articles` VALUES ('5', 'I like Studying from Khmer Academy', '2015-11-07 21:22:51');
INSERT INTO `articles` VALUES ('6', 'Are you Khmer? ', '2015-11-29 21:23:04');
INSERT INTO `articles` VALUES ('7', 'Study basic concept with Lucene', '2015-11-10 23:47:48');
INSERT INTO `articles` VALUES ('8', 'My name is vuthea. I\'m a country boy', '2015-11-05 23:47:59');
