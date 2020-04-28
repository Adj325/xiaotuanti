/*
Navicat MySQL Data Transfer

Source Server         : Adj
Source Server Version : 50515
Source Host           : localhost:3306
Source Database       : endproject

Target Server Type    : MYSQL
Target Server Version : 50515
File Encoding         : 65001

Date: 2020-03-26 10:36:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `activity`
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity` (
  `activity_id` int(6) NOT NULL AUTO_INCREMENT,
  `activity_userConductor` int(6) NOT NULL,
  `activity_team` int(6) DEFAULT NULL,
  `activity_name` varchar(256) DEFAULT NULL,
  `activity_introduction` text,
  `activity_begTime` datetime DEFAULT NULL,
  `activity_endTime` datetime DEFAULT NULL,
  `activity_createTime` datetime DEFAULT NULL,
  `activity_open` tinyint(1) DEFAULT '0' COMMENT '是否对外公开, 0-false, 1-true',
  `activity_status` varchar(10) DEFAULT NULL COMMENT '活动类型, 0-工作, 1-活动',
  `activity_active` tinyint(1) DEFAULT '1' COMMENT '0-结束,   1-进行中',
  `activity_sourceActivityTemplate` int(6) DEFAULT NULL,
  PRIMARY KEY (`activity_id`),
  KEY `activity_team` (`activity_team`),
  KEY `groupIndex_Activity_user_team` (`activity_userConductor`,`activity_team`),
  KEY `fk_activity_template` (`activity_sourceActivityTemplate`),
  CONSTRAINT `activity_ibfk_1` FOREIGN KEY (`activity_team`) REFERENCES `team` (`team_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `activity_ibfk_2` FOREIGN KEY (`activity_userConductor`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_activity_template` FOREIGN KEY (`activity_sourceActivityTemplate`) REFERENCES `activitytemplate` (`activityTemplate_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity` VALUES ('7', '1', '1', '一个活动', '默认介绍', '2020-02-29 00:00:00', '2020-02-29 00:00:00', null, '0', 'PLANNING', '1', null);
INSERT INTO `activity` VALUES ('8', '1', '1', '一个活动', '默认介绍', '2020-02-29 00:00:00', '2020-02-29 00:00:00', null, '0', 'PLANNING', '1', null);
INSERT INTO `activity` VALUES ('13', '1', '1', '一个活动44', '默认介绍', '2020-02-29 00:00:00', '2020-02-29 00:00:00', null, '0', 'END', '1', null);
INSERT INTO `activity` VALUES ('15', '1', '1', '一个活动', '默认介绍', '2020-03-02 00:00:00', '2020-03-02 00:00:00', null, '0', 'PLANNING', '1', null);

-- ----------------------------
-- Table structure for `activitytemplate`
-- ----------------------------
DROP TABLE IF EXISTS `activitytemplate`;
CREATE TABLE `activitytemplate` (
  `activityTemplate_id` int(6) NOT NULL AUTO_INCREMENT,
  `activityTemplate_teamOwner` int(6) DEFAULT NULL,
  `activityTemplate_name` varchar(256) DEFAULT NULL,
  `activityTemplate_introduction` text,
  `activityTemplate_createTime` datetime DEFAULT NULL,
  `activityTemplate_open` bit(1) DEFAULT b'0' COMMENT '是否对外公开, 0-false, 1-true',
  `activityTemplate_sourceActivity` int(6) DEFAULT NULL,
  PRIMARY KEY (`activityTemplate_id`),
  KEY `activityTemplate_teamOwner` (`activityTemplate_teamOwner`),
  CONSTRAINT `activitytemplate_ibfk_1` FOREIGN KEY (`activityTemplate_teamOwner`) REFERENCES `team` (`team_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of activitytemplate
-- ----------------------------
INSERT INTO `activitytemplate` VALUES ('1', '1', '一个活动44', '默认介绍', '2020-03-03 21:27:41', '', '13');

-- ----------------------------
-- Table structure for `apply`
-- ----------------------------
DROP TABLE IF EXISTS `apply`;
CREATE TABLE `apply` (
  `apply_id` int(6) NOT NULL AUTO_INCREMENT,
  `apply_userAuthor` int(6) NOT NULL,
  `apply_team` int(6) NOT NULL,
  PRIMARY KEY (`apply_id`),
  KEY `apply_team` (`apply_team`),
  KEY `groupIndex_Apply_user_team` (`apply_userAuthor`,`apply_team`),
  CONSTRAINT `apply_ibfk_1` FOREIGN KEY (`apply_team`) REFERENCES `team` (`team_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `apply_ibfk_2` FOREIGN KEY (`apply_userAuthor`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of apply
-- ----------------------------
INSERT INTO `apply` VALUES ('1', '1', '1');

-- ----------------------------
-- Table structure for `bin`
-- ----------------------------
DROP TABLE IF EXISTS `bin`;
CREATE TABLE `bin` (
  `bin_id` int(6) NOT NULL AUTO_INCREMENT,
  `bin_path` text NOT NULL,
  `bin_type` char(50) DEFAULT 'image/jpg',
  `bin_name` text NOT NULL,
  PRIMARY KEY (`bin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of bin
-- ----------------------------

-- ----------------------------
-- Table structure for `chat`
-- ----------------------------
DROP TABLE IF EXISTS `chat`;
CREATE TABLE `chat` (
  `chat_id` int(6) NOT NULL AUTO_INCREMENT,
  `chat_activity` int(6) NOT NULL,
  `chat_user` int(6) DEFAULT NULL,
  `chat_bin` int(6) DEFAULT NULL,
  `chat_content` text NOT NULL,
  `chat_postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`chat_id`),
  KEY `chat_bin` (`chat_bin`),
  KEY `chat_user` (`chat_user`),
  KEY `groupIndex_Chat_activity_user` (`chat_activity`,`chat_user`),
  CONSTRAINT `chat_ibfk_1` FOREIGN KEY (`chat_bin`) REFERENCES `bin` (`bin_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `chat_ibfk_2` FOREIGN KEY (`chat_activity`) REFERENCES `activity` (`activity_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `chat_ibfk_3` FOREIGN KEY (`chat_user`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of chat
-- ----------------------------
INSERT INTO `chat` VALUES ('12', '15', '1', null, '你有啥好点子？', '2020-03-07 02:33:48');

-- ----------------------------
-- Table structure for `comments`
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments` (
  `comment_id` int(6) NOT NULL AUTO_INCREMENT,
  `comment_userAuthor` int(6) NOT NULL,
  `comment_content` char(200) DEFAULT NULL,
  `comment_postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `comment_userAuthor` (`comment_userAuthor`),
  CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`comment_userAuthor`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of comments
-- ----------------------------
INSERT INTO `comments` VALUES ('2', '1', '23456789', '2020-02-21 23:00:56');
INSERT INTO `comments` VALUES ('3', '1', '63463737', '2020-02-27 14:40:01');
INSERT INTO `comments` VALUES ('5', '1', '12345678i', '2020-03-03 01:27:33');

-- ----------------------------
-- Table structure for `experience`
-- ----------------------------
DROP TABLE IF EXISTS `experience`;
CREATE TABLE `experience` (
  `experience_id` int(6) NOT NULL AUTO_INCREMENT,
  `experience_userAuthor` int(6) NOT NULL,
  `experience_team` int(6) NOT NULL,
  `experience_activity` int(6) DEFAULT NULL,
  `experience_content` char(200) DEFAULT NULL,
  `experience_postTime` datetime DEFAULT NULL,
  PRIMARY KEY (`experience_id`),
  KEY `experience_userAuthor` (`experience_userAuthor`),
  KEY `experience_team` (`experience_team`),
  KEY `experience_activity` (`experience_activity`),
  CONSTRAINT `experience_ibfk_1` FOREIGN KEY (`experience_userAuthor`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `experience_ibfk_2` FOREIGN KEY (`experience_team`) REFERENCES `team` (`team_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `experience_ibfk_3` FOREIGN KEY (`experience_activity`) REFERENCES `activity` (`activity_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of experience
-- ----------------------------
INSERT INTO `experience` VALUES ('2', '1', '1', null, '35456uio', '2020-02-26 20:39:13');
INSERT INTO `experience` VALUES ('6', '1', '1', '13', '需要准备好物资。', '2020-03-07 20:40:28');

-- ----------------------------
-- Table structure for `freedom`
-- ----------------------------
DROP TABLE IF EXISTS `freedom`;
CREATE TABLE `freedom` (
  `freedom_id` int(6) NOT NULL AUTO_INCREMENT,
  `freedom_userOwner` int(6) NOT NULL,
  `freedom_day` tinyint(1) NOT NULL,
  `freedom_begTime` int(6) NOT NULL,
  `freedom_endTime` int(6) NOT NULL,
  PRIMARY KEY (`freedom_id`),
  KEY `freedom_userOwner` (`freedom_userOwner`),
  CONSTRAINT `freedom_ibfk_1` FOREIGN KEY (`freedom_userOwner`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=484 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of freedom
-- ----------------------------
INSERT INTO `freedom` VALUES ('38', '1', '2', '360', '1320');
INSERT INTO `freedom` VALUES ('39', '1', '3', '360', '1320');
INSERT INTO `freedom` VALUES ('40', '1', '4', '360', '1320');
INSERT INTO `freedom` VALUES ('41', '1', '5', '360', '840');
INSERT INTO `freedom` VALUES ('42', '1', '5', '935', '940');
INSERT INTO `freedom` VALUES ('43', '1', '5', '985', '1320');
INSERT INTO `freedom` VALUES ('44', '1', '6', '360', '1320');
INSERT INTO `freedom` VALUES ('45', '1', '7', '360', '1320');
INSERT INTO `freedom` VALUES ('48', '1', '1', '720', '780');
INSERT INTO `freedom` VALUES ('381', '2', '2', '360', '1320');
INSERT INTO `freedom` VALUES ('382', '3', '2', '360', '1320');
INSERT INTO `freedom` VALUES ('383', '4', '2', '360', '1320');
INSERT INTO `freedom` VALUES ('391', '2', '3', '360', '1320');
INSERT INTO `freedom` VALUES ('392', '3', '3', '360', '1320');
INSERT INTO `freedom` VALUES ('393', '4', '3', '360', '1320');
INSERT INTO `freedom` VALUES ('401', '2', '4', '360', '1320');
INSERT INTO `freedom` VALUES ('402', '3', '4', '360', '1320');
INSERT INTO `freedom` VALUES ('403', '4', '4', '360', '1320');
INSERT INTO `freedom` VALUES ('411', '2', '5', '360', '840');
INSERT INTO `freedom` VALUES ('412', '3', '5', '360', '840');
INSERT INTO `freedom` VALUES ('413', '4', '5', '360', '840');
INSERT INTO `freedom` VALUES ('421', '2', '5', '935', '940');
INSERT INTO `freedom` VALUES ('422', '3', '5', '935', '940');
INSERT INTO `freedom` VALUES ('423', '4', '5', '935', '940');
INSERT INTO `freedom` VALUES ('431', '2', '5', '985', '1320');
INSERT INTO `freedom` VALUES ('432', '3', '5', '985', '1320');
INSERT INTO `freedom` VALUES ('433', '4', '5', '985', '1320');
INSERT INTO `freedom` VALUES ('441', '2', '6', '360', '1320');
INSERT INTO `freedom` VALUES ('442', '3', '6', '360', '1320');
INSERT INTO `freedom` VALUES ('443', '4', '6', '360', '1320');
INSERT INTO `freedom` VALUES ('451', '2', '7', '360', '1320');
INSERT INTO `freedom` VALUES ('452', '3', '7', '360', '1320');
INSERT INTO `freedom` VALUES ('453', '4', '7', '360', '1320');
INSERT INTO `freedom` VALUES ('481', '2', '1', '720', '780');
INSERT INTO `freedom` VALUES ('482', '3', '1', '720', '780');
INSERT INTO `freedom` VALUES ('483', '4', '1', '720', '780');

-- ----------------------------
-- Table structure for `item`
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
  `item_id` int(6) NOT NULL AUTO_INCREMENT,
  `item_team` int(6) NOT NULL,
  `item_introduction` char(200) DEFAULT NULL,
  `item_contact` char(30) DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  KEY `item_team` (`item_team`),
  CONSTRAINT `item_ibfk_1` FOREIGN KEY (`item_team`) REFERENCES `team` (`team_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES ('2', '1', '闲置音箱一个', 'QQ123456789');

-- ----------------------------
-- Table structure for `member`
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `member_id` int(6) NOT NULL AUTO_INCREMENT,
  `member_user` int(6) NOT NULL,
  `member_team` int(6) NOT NULL,
  `member_section` int(6) DEFAULT NULL,
  `member_job` varchar(20) DEFAULT '成员',
  `member_level` tinyint(1) DEFAULT '0' COMMENT '0-普通成员，1-管理者, 2-团主',
  `member_stuno` varchar(9) DEFAULT NULL,
  `member_weixin` varchar(20) DEFAULT NULL,
  `member_email` varchar(20) DEFAULT NULL,
  `member_qq` varchar(10) DEFAULT NULL,
  `member_phone` char(11) DEFAULT NULL,
  `member_province` varchar(20) DEFAULT NULL,
  `member_country` varchar(20) DEFAULT NULL,
  `member_address` varchar(20) DEFAULT NULL,
  `member_city` varchar(20) DEFAULT NULL,
  `member_dormitory` varchar(20) DEFAULT NULL,
  `member_special` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`member_id`,`member_user`,`member_team`),
  KEY `member_team` (`member_team`),
  KEY `member_section` (`member_section`),
  KEY `groupIndex_Member_user_team` (`member_user`,`member_team`),
  CONSTRAINT `member_ibfk_1` FOREIGN KEY (`member_user`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `member_ibfk_2` FOREIGN KEY (`member_team`) REFERENCES `team` (`team_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `member_ibfk_3` FOREIGN KEY (`member_section`) REFERENCES `section` (`section_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES ('1', '1', '1', '1', '团主', '2', null, null, null, '23456789', null, null, null, null, null, null, null);
INSERT INTO `member` VALUES ('2', '2', '1', '1', '成员', '0', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `member` VALUES ('3', '3', '1', '1', '成员', '0', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `member` VALUES ('4', '4', '1', '1', '成员', '0', null, null, null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for `notice`
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `notice_id` int(6) NOT NULL AUTO_INCREMENT,
  `notice_receiver` int(6) NOT NULL,
  `notice_content` char(200) NOT NULL,
  `notice_postTime` datetime NOT NULL,
  `notice_isRead` bit(1) DEFAULT b'0',
  PRIMARY KEY (`notice_id`),
  KEY `notice_receiver` (`notice_receiver`),
  CONSTRAINT `notice_ibfk_1` FOREIGN KEY (`notice_receiver`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES ('1', '1', '你已成功加入 一个团体', '2020-03-06 21:39:32', '');
INSERT INTO `notice` VALUES ('2', '1', '你报名参加的由“214354656”主办的“一个活动44”活动已经发布了！', '2020-03-01 20:36:35', '');

-- ----------------------------
-- Table structure for `part`
-- ----------------------------
DROP TABLE IF EXISTS `part`;
CREATE TABLE `part` (
  `part_id` int(6) NOT NULL AUTO_INCREMENT,
  `part_activity` int(6) NOT NULL,
  `part_order` tinyint(2) NOT NULL,
  `part_begTime` int(11) NOT NULL,
  `part_endTime` int(11) NOT NULL,
  `part_content` text NOT NULL,
  `part_targetNumber` int(11) DEFAULT NULL,
  PRIMARY KEY (`part_id`),
  KEY `part_activity` (`part_activity`),
  CONSTRAINT `part_ibfk_1` FOREIGN KEY (`part_activity`) REFERENCES `activity` (`activity_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of part
-- ----------------------------
INSERT INTO `part` VALUES ('1', '7', '0', '480', '550', '工作内容1', '4');
INSERT INTO `part` VALUES ('2', '7', '1', '550', '630', '工作内容2', '4');
INSERT INTO `part` VALUES ('3', '7', '2', '630', '710', '工作内容2', '4');
INSERT INTO `part` VALUES ('4', '7', '3', '710', '790', '工作内容3', '4');
INSERT INTO `part` VALUES ('5', '7', '4', '790', '870', '工作内容4', '4');
INSERT INTO `part` VALUES ('6', '7', '5', '870', '950', '工作内容5', '4');
INSERT INTO `part` VALUES ('7', '7', '6', '950', '1030', '工作内容6', '4');
INSERT INTO `part` VALUES ('8', '7', '7', '1030', '1090', '工作内容7', '4');
INSERT INTO `part` VALUES ('9', '7', '8', '1090', '1190', '工作内容8', '4');
INSERT INTO `part` VALUES ('10', '8', '0', '480', '550', '工作内容1', '4');
INSERT INTO `part` VALUES ('11', '8', '1', '550', '630', '工作内容2', '4');
INSERT INTO `part` VALUES ('12', '8', '2', '630', '710', '工作内容2', '4');
INSERT INTO `part` VALUES ('13', '8', '3', '710', '790', '工作内容3', '4');
INSERT INTO `part` VALUES ('14', '8', '4', '790', '870', '工作内容4', '4');
INSERT INTO `part` VALUES ('15', '8', '5', '870', '950', '工作内容5', '4');
INSERT INTO `part` VALUES ('16', '8', '6', '950', '1030', '工作内容6', '4');
INSERT INTO `part` VALUES ('17', '8', '7', '1030', '1090', '工作内容7', '4');
INSERT INTO `part` VALUES ('18', '8', '8', '1090', '1190', '工作内容8', '4');
INSERT INTO `part` VALUES ('101', '13', '0', '480', '550', '工作内容1', '4');
INSERT INTO `part` VALUES ('102', '13', '1', '870', '950', '工作内容5', '4');
INSERT INTO `part` VALUES ('103', '13', '2', '1030', '1090', '工作内容7', '4');
INSERT INTO `part` VALUES ('104', '13', '3', '1090', '1190', '工作内容8', '4');
INSERT INTO `part` VALUES ('132', '15', '0', '480', '550', '工作内容1', '4');
INSERT INTO `part` VALUES ('133', '15', '1', '550', '630', '工作内容2', '4');
INSERT INTO `part` VALUES ('134', '15', '2', '630', '710', '工作内容2', '4');
INSERT INTO `part` VALUES ('135', '15', '3', '710', '790', '工作内容3', '4');
INSERT INTO `part` VALUES ('136', '15', '4', '790', '870', '工作内容4', '4');
INSERT INTO `part` VALUES ('137', '15', '5', '870', '950', '工作内容5', '4');
INSERT INTO `part` VALUES ('138', '15', '6', '950', '1030', '工作内容6', '4');
INSERT INTO `part` VALUES ('139', '15', '7', '1030', '1090', '工作内容7', '4');
INSERT INTO `part` VALUES ('140', '15', '8', '1090', '1190', '工作内容8', '4');

-- ----------------------------
-- Table structure for `parttemplate`
-- ----------------------------
DROP TABLE IF EXISTS `parttemplate`;
CREATE TABLE `parttemplate` (
  `partTemplate_id` int(6) NOT NULL AUTO_INCREMENT,
  `partTemplate_activityTemplate` int(6) NOT NULL,
  `partTemplate_order` tinyint(2) NOT NULL,
  `partTemplate_begTime` int(11) NOT NULL,
  `partTemplate_endTime` int(11) NOT NULL,
  `partTemplate_content` text NOT NULL,
  PRIMARY KEY (`partTemplate_id`),
  KEY `partTemplate_activityTemplate` (`partTemplate_activityTemplate`),
  CONSTRAINT `parttemplate_ibfk_1` FOREIGN KEY (`partTemplate_activityTemplate`) REFERENCES `activitytemplate` (`activityTemplate_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of parttemplate
-- ----------------------------
INSERT INTO `parttemplate` VALUES ('5', '1', '0', '480', '550', '工作内容1');
INSERT INTO `parttemplate` VALUES ('6', '1', '1', '870', '950', '工作内容5');
INSERT INTO `parttemplate` VALUES ('7', '1', '2', '1030', '1090', '工作内容7');
INSERT INTO `parttemplate` VALUES ('8', '1', '3', '1090', '1190', '工作内容8');

-- ----------------------------
-- Table structure for `post`
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post` (
  `post_id` int(6) NOT NULL AUTO_INCREMENT,
  `post_userAuthor` int(6) NOT NULL,
  `post_title` char(200) NOT NULL,
  `post_content` char(200) NOT NULL,
  `post_postTime` datetime NOT NULL,
  `post_type` tinyint(1) NOT NULL,
  PRIMARY KEY (`post_id`),
  KEY `post_userAuthor` (`post_userAuthor`),
  CONSTRAINT `post_ibfk_1` FOREIGN KEY (`post_userAuthor`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of post
-- ----------------------------
INSERT INTO `post` VALUES ('1', '1', '举办活动需要注意的几个点', '暂未想到', '2020-02-21 22:26:05', '1');

-- ----------------------------
-- Table structure for `relation_activity_comment`
-- ----------------------------
DROP TABLE IF EXISTS `relation_activity_comment`;
CREATE TABLE `relation_activity_comment` (
  `relation_id` int(6) NOT NULL AUTO_INCREMENT,
  `relation_activity` int(6) NOT NULL,
  `relation_comment` int(6) NOT NULL,
  PRIMARY KEY (`relation_id`),
  KEY `relation_activity` (`relation_activity`),
  KEY `relation_comment` (`relation_comment`),
  CONSTRAINT `relation_activity_comment_ibfk_1` FOREIGN KEY (`relation_activity`) REFERENCES `activity` (`activity_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `relation_activity_comment_ibfk_2` FOREIGN KEY (`relation_comment`) REFERENCES `comments` (`comment_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of relation_activity_comment
-- ----------------------------
INSERT INTO `relation_activity_comment` VALUES ('1', '13', '5');

-- ----------------------------
-- Table structure for `relation_activity_user`
-- ----------------------------
DROP TABLE IF EXISTS `relation_activity_user`;
CREATE TABLE `relation_activity_user` (
  `relation_id` int(6) NOT NULL AUTO_INCREMENT,
  `relation_user` int(6) NOT NULL,
  `relation_activity` int(6) NOT NULL,
  PRIMARY KEY (`relation_id`),
  KEY `relation_activity` (`relation_activity`),
  KEY `groupIndex_Relation_Activity_User` (`relation_user`,`relation_activity`),
  CONSTRAINT `relation_activity_user_ibfk_1` FOREIGN KEY (`relation_activity`) REFERENCES `activity` (`activity_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `relation_activity_user_ibfk_2` FOREIGN KEY (`relation_user`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of relation_activity_user
-- ----------------------------
INSERT INTO `relation_activity_user` VALUES ('1', '1', '13');

-- ----------------------------
-- Table structure for `relation_part_user`
-- ----------------------------
DROP TABLE IF EXISTS `relation_part_user`;
CREATE TABLE `relation_part_user` (
  `relation_id` int(6) NOT NULL AUTO_INCREMENT,
  `relation_part` int(6) NOT NULL,
  `relation_user` int(6) NOT NULL,
  PRIMARY KEY (`relation_id`),
  KEY `relation_part` (`relation_part`),
  KEY `relation_user` (`relation_user`),
  CONSTRAINT `relation_part_user_ibfk_1` FOREIGN KEY (`relation_part`) REFERENCES `part` (`part_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `relation_part_user_ibfk_2` FOREIGN KEY (`relation_user`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of relation_part_user
-- ----------------------------
INSERT INTO `relation_part_user` VALUES ('7', '101', '1');

-- ----------------------------
-- Table structure for `relation_post_comment`
-- ----------------------------
DROP TABLE IF EXISTS `relation_post_comment`;
CREATE TABLE `relation_post_comment` (
  `relation_id` int(6) NOT NULL AUTO_INCREMENT,
  `relation_post` int(6) NOT NULL,
  `relation_comment` int(6) NOT NULL,
  PRIMARY KEY (`relation_id`),
  KEY `relation_post` (`relation_post`),
  KEY `relation_comment` (`relation_comment`),
  CONSTRAINT `relation_post_comment_ibfk_1` FOREIGN KEY (`relation_post`) REFERENCES `post` (`post_id`),
  CONSTRAINT `relation_post_comment_ibfk_2` FOREIGN KEY (`relation_comment`) REFERENCES `comments` (`comment_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of relation_post_comment
-- ----------------------------
INSERT INTO `relation_post_comment` VALUES ('1', '1', '2');
INSERT INTO `relation_post_comment` VALUES ('2', '1', '3');

-- ----------------------------
-- Table structure for `relation_tag_activity`
-- ----------------------------
DROP TABLE IF EXISTS `relation_tag_activity`;
CREATE TABLE `relation_tag_activity` (
  `relation_id` int(6) NOT NULL AUTO_INCREMENT,
  `relation_tag` int(6) NOT NULL,
  `relation_activity` int(6) NOT NULL,
  PRIMARY KEY (`relation_id`),
  KEY `relation_tag` (`relation_tag`),
  KEY `relation_activity` (`relation_activity`),
  CONSTRAINT `relation_tag_activity_ibfk_1` FOREIGN KEY (`relation_tag`) REFERENCES `tag` (`tag_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `relation_tag_activity_ibfk_2` FOREIGN KEY (`relation_activity`) REFERENCES `activity` (`activity_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of relation_tag_activity
-- ----------------------------
INSERT INTO `relation_tag_activity` VALUES ('41', '2', '13');
INSERT INTO `relation_tag_activity` VALUES ('42', '3', '13');
INSERT INTO `relation_tag_activity` VALUES ('47', '2', '15');
INSERT INTO `relation_tag_activity` VALUES ('48', '3', '15');

-- ----------------------------
-- Table structure for `relation_tag_activitytemplate`
-- ----------------------------
DROP TABLE IF EXISTS `relation_tag_activitytemplate`;
CREATE TABLE `relation_tag_activitytemplate` (
  `relation_id` int(6) NOT NULL AUTO_INCREMENT,
  `relation_tag` int(6) NOT NULL,
  `relation_activityTemplate` int(6) NOT NULL,
  PRIMARY KEY (`relation_id`),
  KEY `relation_tag` (`relation_tag`),
  KEY `relation_activityTemplate` (`relation_activityTemplate`),
  CONSTRAINT `relation_tag_activitytemplate_ibfk_1` FOREIGN KEY (`relation_tag`) REFERENCES `tag` (`tag_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `relation_tag_activitytemplate_ibfk_2` FOREIGN KEY (`relation_activityTemplate`) REFERENCES `activitytemplate` (`activityTemplate_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of relation_tag_activitytemplate
-- ----------------------------
INSERT INTO `relation_tag_activitytemplate` VALUES ('3', '2', '1');
INSERT INTO `relation_tag_activitytemplate` VALUES ('4', '3', '1');

-- ----------------------------
-- Table structure for `relation_user_activitytemplate`
-- ----------------------------
DROP TABLE IF EXISTS `relation_user_activitytemplate`;
CREATE TABLE `relation_user_activitytemplate` (
  `relation_id` int(6) NOT NULL AUTO_INCREMENT,
  `relation_user` int(6) NOT NULL,
  `relation_activityTemplate` int(6) NOT NULL,
  PRIMARY KEY (`relation_id`),
  UNIQUE KEY `uniqueIndex_Relation_User_ActivityTemplate` (`relation_user`,`relation_activityTemplate`),
  KEY `relation_activityTemplate` (`relation_activityTemplate`),
  CONSTRAINT `relation_user_activitytemplate_ibfk_1` FOREIGN KEY (`relation_user`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `relation_user_activitytemplate_ibfk_2` FOREIGN KEY (`relation_activityTemplate`) REFERENCES `activitytemplate` (`activityTemplate_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of relation_user_activitytemplate
-- ----------------------------

-- ----------------------------
-- Table structure for `section`
-- ----------------------------
DROP TABLE IF EXISTS `section`;
CREATE TABLE `section` (
  `section_id` int(6) NOT NULL AUTO_INCREMENT,
  `section_team` int(6) NOT NULL,
  `section_name` varchar(20) DEFAULT '默认名称',
  PRIMARY KEY (`section_id`),
  KEY `section_team` (`section_team`),
  CONSTRAINT `section_ibfk_1` FOREIGN KEY (`section_team`) REFERENCES `team` (`team_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of section
-- ----------------------------
INSERT INTO `section` VALUES ('1', '1', '默认名称234567');
INSERT INTO `section` VALUES ('3', '1', '2345');

-- ----------------------------
-- Table structure for `tag`
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
  `tag_id` int(6) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(256) NOT NULL,
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES ('2', '科技');
INSERT INTO `tag` VALUES ('3', '美术');
INSERT INTO `tag` VALUES ('4', '标签');

-- ----------------------------
-- Table structure for `team`
-- ----------------------------
DROP TABLE IF EXISTS `team`;
CREATE TABLE `team` (
  `team_id` int(6) NOT NULL AUTO_INCREMENT,
  `team_name` varchar(20) NOT NULL,
  `team_userOwner` int(6) NOT NULL,
  `team_invite` char(6) DEFAULT '000000',
  `team_introduction` varchar(256) DEFAULT '默认介绍',
  PRIMARY KEY (`team_id`),
  KEY `team_userOwner` (`team_userOwner`),
  CONSTRAINT `team_ibfk_1` FOREIGN KEY (`team_userOwner`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of team
-- ----------------------------
INSERT INTO `team` VALUES ('1', '214354656', '1', '464496', '2135465678');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(6) NOT NULL AUTO_INCREMENT,
  `user_openid` char(28) DEFAULT NULL,
  `user_sessionid` char(32) DEFAULT NULL,
  `user_stuno` char(9) DEFAULT NULL,
  `user_weixin` varchar(20) DEFAULT NULL,
  `user_email` varchar(20) DEFAULT NULL,
  `user_qq` varchar(10) DEFAULT NULL,
  `user_phone` char(11) DEFAULT NULL,
  `user_sessionkey` text,
  `user_password` text,
  `user_nickName` varchar(20) DEFAULT NULL,
  `user_realName` varchar(20) DEFAULT NULL,
  `user_province` varchar(20) DEFAULT NULL,
  `user_country` varchar(20) DEFAULT NULL,
  `user_address` varchar(20) DEFAULT NULL,
  `user_city` varchar(20) DEFAULT NULL,
  `user_dormitory` varchar(20) DEFAULT NULL,
  `user_grade` tinyint(1) DEFAULT '0',
  `user_gender` tinyint(1) DEFAULT '1',
  `user_avatarUrl` text,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_openid` (`user_openid`),
  UNIQUE KEY `user_stuno` (`user_stuno`),
  UNIQUE KEY `user_sessionid` (`user_sessionid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'owDOu4rBYc7ffb0vLQ-Xeh2DxD08', null, null, null, null, null, null, 'uE2iPwvEUXP8XTzRStcVvg==', null, '感叹号', '吴杰春', 'Guangdong', 'China', null, 'Zhanjiang', null, '0', '1', 'https://wx.qlogo.cn/mmopen/vi_32/vhPLb5yB1LFIQbITniax6cnTbNaowyiayIc9N3tM2ibQNNht9ibYCtdukfNChQqkk5MY9weY388N69r3xmnbRGxwZg/132');
INSERT INTO `user` VALUES ('2', '234567', null, null, null, null, null, null, null, null, '123456', '请假', null, null, null, null, null, '0', '1', null);
INSERT INTO `user` VALUES ('3', '1234', null, null, null, null, null, null, null, null, null, '小明', null, null, null, null, null, '0', '1', null);
INSERT INTO `user` VALUES ('4', null, null, null, null, null, null, null, null, null, null, '报名', null, null, null, null, null, '0', '1', null);

-- ----------------------------
-- Table structure for `userexample`
-- ----------------------------
DROP TABLE IF EXISTS `userexample`;
CREATE TABLE `userexample` (
  `userExample_id` int(6) NOT NULL AUTO_INCREMENT,
  `userExample_openid` char(28) DEFAULT NULL,
  `userExample_sessionid` char(32) DEFAULT NULL,
  `userExample_sessionkey` text,
  `userExample_avatarUrl` text,
  PRIMARY KEY (`userExample_id`),
  UNIQUE KEY `userExample_openid` (`userExample_openid`),
  UNIQUE KEY `userExample_sessionid` (`userExample_sessionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userexample
-- ----------------------------

-- ----------------------------
-- Table structure for `willing`
-- ----------------------------
DROP TABLE IF EXISTS `willing`;
CREATE TABLE `willing` (
  `willing_id` int(6) NOT NULL AUTO_INCREMENT,
  `willing_userOwner` int(6) NOT NULL,
  `willing_activity` int(6) NOT NULL,
  `willing_type` bit(1) NOT NULL,
  PRIMARY KEY (`willing_id`),
  KEY `willing_activity` (`willing_activity`),
  KEY `groupIndex_Willing_user_activity` (`willing_userOwner`,`willing_activity`),
  CONSTRAINT `willing_ibfk_1` FOREIGN KEY (`willing_activity`) REFERENCES `activity` (`activity_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `willing_ibfk_2` FOREIGN KEY (`willing_userOwner`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of willing
-- ----------------------------
INSERT INTO `willing` VALUES ('1', '4', '15', '');
INSERT INTO `willing` VALUES ('2', '2', '15', '');
