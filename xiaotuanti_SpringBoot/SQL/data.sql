-- 删库跑路
DROP DATABASE IF EXISTS `EndProject`;
-- 创库
CREATE DATABASE `EndProject`;
-- 使用 xiaotuanti 数据库
USE EndProject;

COMMIT;

-- 用户表
DROP TABLE IF EXISTS `User`;
CREATE TABLE `User`
(
  -- 唯一标识
  `user_id`         INT(6) AUTO_INCREMENT NOT NULL,
  `user_openid`     CHAR(28)    DEFAULT NULL,
  `user_sessionid`  CHAR(32)    DEFAULT NULL,
  `user_stuno`      CHAR(9)     DEFAULT NULL,
  -- 联系方式
  `user_weixin`     VARCHAR(20) DEFAULT NULL,
  `user_email`      VARCHAR(20) DEFAULT NULL,
  `user_qq`         VARCHAR(10) DEFAULT NULL,
  `user_phone`      CHAR(11)    DEFAULT NULL,
  -- 用户信息
  `user_sessionkey` TEXT        DEFAULT NULL,
  `user_password`   TEXT        DEFAULT NULL,
  `user_nickName`   VARCHAR(20) DEFAULT NULL,
  `user_realName`   VARCHAR(20) DEFAULT NULL,
  -- 住址信息
  `user_province`   VARCHAR(20) DEFAULT NULL,
  `user_country`    VARCHAR(20) DEFAULT NULL,
  `user_address`    VARCHAR(20) DEFAULT NULL,
  `user_city`       VARCHAR(20) DEFAULT NULL,
  `user_dormitory`  VARCHAR(20) DEFAULT NULL,
  -- 其他信息
  `user_grade`      TINYINT(1)  DEFAULT 0,
  `user_gender`     TINYINT(1)  DEFAULT 1,
  `user_avatarUrl`  TEXT        DEFAULT NULL,

  PRIMARY KEY (`user_id`),
  UNIQUE (`user_openid`),
  UNIQUE (`user_stuno`),
  UNIQUE (`user_sessionid`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
COMMIT;

-- 空闲时间表
DROP TABLE IF EXISTS `Freedom`;
CREATE TABLE `Freedom`
(
  `freedom_id`        INT(6)     NOT NULL AUTO_INCREMENT,
  `freedom_userOwner` INT(6)     NOT NULL,
  -- 星期几
  `freedom_day`       TINYINT(1) NOT NULL,
  -- 具体空闲时间
  `freedom_begTime`   INT(6)     NOT NULL,
  `freedom_endTime`   INT(6)     NOT NULL,
  PRIMARY KEY (`freedom_id`),
  FOREIGN KEY (`freedom_userOwner`) REFERENCES User (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
COMMIT;

-- 团体表
DROP TABLE IF EXISTS `Team`;
CREATE TABLE `Team`
(
  `team_id`           INT(6)      NOT NULL AUTO_INCREMENT,
  `team_name`         VARCHAR(20) NOT NULL,
  `team_userOwner`    INT(6)      NOT NULL,
  `team_invite`       CHAR(6)      DEFAULT '000000',
  `team_introduction` VARCHAR(256) DEFAULT '默认介绍',
  PRIMARY KEY (`team_id`),
  FOREIGN KEY (`team_userOwner`) REFERENCES User (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
COMMIT;

-- 团体加入申请表
DROP TABLE IF EXISTS `Apply`;
CREATE TABLE `Apply`
(
  `apply_id`         INT(6) NOT NULL AUTO_INCREMENT,
  `apply_userAuthor` INT(6) NOT NULL,
  `apply_team`       INT(6) NOT NULL,
  PRIMARY KEY (`apply_id`),
  FOREIGN KEY (`apply_team`) REFERENCES Team (`team_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`apply_userAuthor`) REFERENCES User (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
COMMIT;

-- 组合索引
ALTER TABLE `Apply`
  ADD INDEX groupIndex_Apply_user_team (`apply_userAuthor`, `apply_team`);

-- 部门表
DROP TABLE IF EXISTS `Section`;
CREATE TABLE `Section`
(
  `section_id`   INT(6) NOT NULL AUTO_INCREMENT,
  `section_team` INT(6) NOT NULL,
  `section_name` VARCHAR(20) DEFAULT '默认名称',
  PRIMARY KEY (`section_id`),
  FOREIGN KEY (`section_team`) REFERENCES Team (`team_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
COMMIT;

-- 团体成员表
-- 删除已有 Member 表
DROP TABLE IF EXISTS `Member`;
-- 创建 Member 表
CREATE TABLE `Member`
(
  `member_id`        INT(6) NOT NULL AUTO_INCREMENT,
  `member_user`      INT(6) NOT NULL,
  `member_team`      INT(6) NOT NULL,
  `member_section`   INT(6)       DEFAULT NULL,
  `member_job`       VARCHAR(20)  DEFAULT '成员',

  -- 0-普通成员，1-管理者
  `member_level`     TINYINT(1)   DEFAULT 0 COMMENT '0-普通成员，1-管理者, 2-团主',

  -- 联系信息
  `member_stuno`     VARCHAR(9)   DEFAULT NULL,
  `member_weixin`    VARCHAR(20)  DEFAULT NULL,
  `member_email`     VARCHAR(20)  DEFAULT NULL,
  `member_qq`        VARCHAR(10)  DEFAULT NULL,
  `member_phone`     CHAR(11)     DEFAULT NULL,

  -- 住址信息
  `member_province`  VARCHAR(20)  DEFAULT NULL,
  `member_country`   VARCHAR(20)  DEFAULT NULL,
  `member_address`   VARCHAR(20)  DEFAULT NULL,
  `member_city`      VARCHAR(20)  DEFAULT NULL,
  `member_dormitory` VARCHAR(20)  DEFAULT NULL,

  -- 特殊信息
  `member_special`   VARCHAR(100) DEFAULT NULL,

  PRIMARY KEY (`member_id`, `member_user`, `member_team`),
  FOREIGN KEY (`member_user`) REFERENCES User (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (`member_team`) REFERENCES Team (`team_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (`member_section`) REFERENCES Section (`section_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
COMMIT;

-- 组合索引
ALTER TABLE `Member`
  ADD INDEX groupIndex_Member_user_team (`member_user`, `member_team`);

-- 活动表
DROP TABLE IF EXISTS `Activity`;
CREATE TABLE `Activity`
(
  `activity_id`                     INT(6)      NOT NULL AUTO_INCREMENT,
  `activity_sourceActivityTemplate` INT(6)       DEFAULT NULL,
  `activity_userConductor`          INT(6)      NOT NULL,
  `activity_team`                   INT(6)       DEFAULT NULL,
  `activity_name`                   VARCHAR(256) DEFAULT NULL,
  `activity_status`                 VARCHAR(10) NOT NULL,
  `activity_introduction`           TEXT         DEFAULT NULL,
  `activity_begTime`                DATETIME     DEFAULT NULL,
  `activity_endTime`                DATETIME     DEFAULT NULL,
  `activity_createTime`             DATETIME     DEFAULT NULL,
  `activity_open`                   TINYINT(1)   DEFAULT 0 COMMENT '是否对外公开, 0-false, 1-true',
  PRIMARY KEY (`activity_id`),
  FOREIGN KEY (`activity_team`) REFERENCES Team (`team_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (`activity_userConductor`) REFERENCES User (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
COMMIT;
-- 组合索引
ALTER TABLE `Activity`
  ADD INDEX groupIndex_Activity_user_team (`activity_userConductor`, `activity_team`);

-- 活动模板表
DROP TABLE IF EXISTS `ActivityTemplate`;
CREATE TABLE `ActivityTemplate`
(
  `activityTemplate_id`             INT(6) NOT NULL AUTO_INCREMENT,
  `activityTemplate_teamOwner`      INT(6)       DEFAULT NULL,
  `activityTemplate_sourceActivity` INT(6)       DEFAULT NULL,
  `activityTemplate_name`           VARCHAR(256) DEFAULT NULL,
  `activityTemplate_introduction`   TEXT         DEFAULT NULL,
  `activityTemplate_createTime`     DATETIME     DEFAULT NULL,
  `activityTemplate_open`           BIT          DEFAULT 0 COMMENT '是否对外公开, 0-false, 1-true',
  PRIMARY KEY (`activityTemplate_id`),
  FOREIGN KEY (`activityTemplate_teamOwner`) REFERENCES Team (`team_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (`activityTemplate_sourceActivity`) REFERENCES Activity (`activity_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
COMMIT;

alter table Activity
  add constraint fk_activity_template foreign key (`activity_sourceActivityTemplate`) REFERENCES ActivityTemplate (`activityTemplate_id`);

COMMIT;

-- 用户-模板联系 表
DROP TABLE IF EXISTS `Relation_User_ActivityTemplate`;
CREATE TABLE `Relation_User_ActivityTemplate`
(
  `relation_id`               INT(6) NOT NULL AUTO_INCREMENT,
  `relation_user`             INT(6) NOT NULL,
  `relation_activityTemplate` INT(6) NOT NULL,
  PRIMARY KEY (`relation_id`),
  FOREIGN KEY (`relation_user`) REFERENCES User (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (`relation_activityTemplate`) REFERENCES ActivityTemplate (`activityTemplate_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
COMMIT;

ALTER TABLE `Relation_User_ActivityTemplate`
  ADD UNIQUE uniqueIndex_Relation_User_ActivityTemplate (`relation_user`, `relation_activityTemplate`);

-- 活动阶段表
DROP TABLE IF EXISTS `Part`;
CREATE TABLE `Part`
(
  `part_id`       INT(6)     NOT NULL AUTO_INCREMENT,
  `part_activity` INT(6)     NOT NULL,
  `part_order`    TINYINT(2) NOT NULL,
  `part_begTime`  INT        NOT NULL,
  `part_endTime`  INT        NOT NULL,
  `part_content`  TEXT       NOT NULL,
  PRIMARY KEY (`part_id`),
  FOREIGN KEY (`part_activity`) REFERENCES Activity (`activity_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
COMMIT;

-- 阶段模板表
DROP TABLE IF EXISTS `PartTemplate`;
CREATE TABLE `PartTemplate`
(
  `partTemplate_id`               INT(6)     NOT NULL AUTO_INCREMENT,
  `partTemplate_activityTemplate` INT(6)     NOT NULL,
  `part_order`                    TINYINT(2) NOT NULL,
  `partTemplate_begTime`          INT        NOT NULL,
  `partTemplate_endTime`          INT        NOT NULL,
  `partTemplate_content`          TEXT       NOT NULL,
  PRIMARY KEY (`partTemplate_id`),
  FOREIGN KEY (`partTemplate_activityTemplate`) REFERENCES ActivityTemplate (`activityTemplate_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
COMMIT;

-- 活动阶段_用户联系表
DROP TABLE IF EXISTS `Relation_Part_User`;
CREATE TABLE `Relation_Part_User`
(
  `relation_id`   INT(6) NOT NULL AUTO_INCREMENT,
  `relation_part` INT(6) NOT NULL,
  `relation_user` INT(6) NOT NULL,
  PRIMARY KEY (`relation_id`),
  FOREIGN KEY (`relation_part`) REFERENCES Part (`part_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (`relation_user`) REFERENCES User (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
COMMIT;

-- 标签表
DROP TABLE IF EXISTS `Tag`;
CREATE TABLE `Tag`
(
  `tag_id`   INT(6)       NOT NULL AUTO_INCREMENT,
  `tag_name` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`tag_id`),
  UNIQUE (`tag_name`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
COMMIT;

-- 标签-活动 联系表
DROP TABLE IF EXISTS `Relation_Tag_Activity`;
CREATE TABLE `Relation_Tag_Activity`
(
  `relation_id`       INT(6) NOT NULL AUTO_INCREMENT,
  `relation_tag`      INT(6) NOT NULL,
  `relation_activity` INT(6) NOT NULL,
  PRIMARY KEY (`relation_id`),
  FOREIGN KEY (relation_tag) REFERENCES Tag (`tag_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (`relation_activity`) REFERENCES Activity (`activity_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
COMMIT;

ALTER TABLE `Relation_Tag_Activity`
  ADD UNIQUE uniqueIndex_Relation_Tag_Activity (`relation_tag`, `relation_activity`);

-- 标签-活动模板 联系表
DROP TABLE IF EXISTS `Relation_Tag_ActivityTemplate`;
CREATE TABLE `Relation_Tag_ActivityTemplate`
(
  `relation_id`               INT(6) NOT NULL AUTO_INCREMENT,
  `relation_tag`              INT(6) NOT NULL,
  `relation_activityTemplate` INT(6) NOT NULL,
  PRIMARY KEY (`relation_id`),
  FOREIGN KEY (relation_tag) REFERENCES Tag (`tag_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (`relation_activityTemplate`) REFERENCES ActivityTemplate (`activityTemplate_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
COMMIT;

-- 活动记录表
-- 删除已有 Record 表
DROP TABLE IF EXISTS Relation_Activity_User;
-- 创建 Record 表
CREATE TABLE `Relation_Activity_User`
(
  `relation_id`       INT(6) NOT NULL AUTO_INCREMENT,
  `relation_user`     INT(6) NOT NULL,
  `relation_activity` INT(6) NOT NULL,
  PRIMARY KEY (`relation_id`),
  FOREIGN KEY (`relation_activity`) REFERENCES Activity (`activity_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (`relation_user`) REFERENCES User (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
COMMIT;

-- 组合索引
ALTER TABLE Relation_Activity_User
  ADD INDEX groupIndex_Relation_Activity_User (`relation_user`, `relation_activity`);

-- 活动志愿表
-- 删除已有 Willing 表
DROP TABLE IF EXISTS `Willing`;
-- 创建 Willing 表
CREATE TABLE `Willing`
(
  `willing_id`        INT(6) NOT NULL AUTO_INCREMENT,
  `willing_userOwner` INT(6) NOT NULL,
  `willing_activity`  INT(6) NOT NULL,
  `willing_type`      BIT    NOT NULL,
  PRIMARY KEY (`willing_id`),
  FOREIGN KEY (`willing_activity`) REFERENCES Activity (`activity_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (`willing_userOwner`) REFERENCES User (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
COMMIT;

-- 组合索引
ALTER TABLE `Willing`
  ADD INDEX groupIndex_Willing_user_activity (`willing_userOwner`, `willing_activity`);

-- 共享物品表
-- 删除已有 Item 表
DROP TABLE IF EXISTS `Item`;
-- 创建 Item 表
CREATE TABLE `Item`
(
  `item_id`           INT(6)    NOT NULL AUTO_INCREMENT,
  `item_team`         INT(6)    NOT NULL,
  `item_introduction` CHAR(200) NOT NULL,
  `item_contact`      CHAR(30)  NOT NULL,
  PRIMARY KEY (`item_id`),
  FOREIGN KEY (`item_team`) REFERENCES Team (`team_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
COMMIT;

-- 通知信息表
DROP TABLE IF EXISTS `Notice`;
CREATE TABLE `Notice`
(
  `notice_id`       INT(6)    NOT NULL AUTO_INCREMENT,
  `notice_receiver` INT(6)    NOT NULL,
  `notice_content`  CHAR(200) NOT NULL,
  `notice_postTime` DATETIME  NOT NULL,
  PRIMARY KEY (`notice_id`),
  FOREIGN KEY (`notice_receiver`) REFERENCES User (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
COMMIT;

-- 评论表
DROP TABLE IF EXISTS comments;
CREATE TABLE `comments`
(
  `comment_id`         INT(6)    NOT NULL AUTO_INCREMENT,
  `comment_userAuthor` INT(6)    NOT NULL,
  `comment_content`    CHAR(200) NOT NULL,
  `comment_postTime`   DATETIME  NOT NULL,
  PRIMARY KEY (`comment_id`),
  FOREIGN KEY (`comment_userAuthor`) REFERENCES User (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- 经验表
DROP TABLE IF EXISTS `Experience`;
CREATE TABLE `Experience`
(
  `experience_id`         INT(6)    NOT NULL AUTO_INCREMENT,
  `experience_userAuthor` INT(6)    NOT NULL,
  `experience_team`       INT(6)    NOT NULL,
  `experience_activity`   INT(6)    NULL,
  `experience_content`    CHAR(200) NOT NULL,
  `experience_postTime`   DATETIME  NOT NULL,
  PRIMARY KEY (`experience_id`),
  FOREIGN KEY (`experience_userAuthor`) REFERENCES User (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (`experience_team`) REFERENCES Team (`team_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (experience_activity) REFERENCES Activity (`activity_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- 组合索引
ALTER TABLE `Experience`
  ADD INDEX groupIndex_Experience_user_team (`experience_userAuthor`, `experience_team`);

-- 二进制表
-- 删除已有 Bin 表
DROP TABLE IF EXISTS `Bin`;
-- 创建 Bin 表
CREATE TABLE `Bin`
(
  `bin_id`   INT(6) NOT NULL AUTO_INCREMENT,
  `bin_path` TEXT   NOT NULL,
  `bin_type` CHAR(50) DEFAULT 'image/jpg',
  `bin_name` TEXT   NOT NULL,
  PRIMARY KEY (`bin_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

COMMIT;

-- 聊天记录表
DROP TABLE IF EXISTS `Chat`;
CREATE TABLE `Chat`
(
  `chat_id`       INT(6)   NOT NULL AUTO_INCREMENT,
  `chat_activity` INT(6)   NOT NULL,
  `chat_user`     INT(6)   NULL,
  `chat_bin`      INT(6)   NULL,
  `chat_content`  TEXT     NOT NULL,
  `chat_postTime` DATETIME NULL,
  PRIMARY KEY (`chat_id`),
  FOREIGN KEY (`chat_bin`) REFERENCES Bin (`bin_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (`chat_activity`) REFERENCES Activity (`activity_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (`chat_user`) REFERENCES User (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
COMMIT;

-- 组合索引
ALTER TABLE `Chat`
  ADD INDEX groupIndex_Chat_activity_user (`chat_activity`, `chat_user`);

-- 帖子表
DROP TABLE IF EXISTS `Post`;
CREATE TABLE `Post`
(
  `post_id`         INT(6)     NOT NULL AUTO_INCREMENT,
  `post_userAuthor` INT(6)     NOT NULL,
  `post_title`      CHAR(200)  NOT NULL,
  `post_content`    CHAR(200)  NOT NULL,
  `post_postTime`   DATETIME   NOT NULL,
  `post_type`       TINYINT(1) NOT NULL,
  PRIMARY KEY (`post_id`),
  FOREIGN KEY (`post_userAuthor`) REFERENCES User (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
COMMIT;

-- 帖子-评论 关系表
DROP TABLE IF EXISTS `Relation_Post_Comment`;
CREATE TABLE `Relation_Post_Comment`
(
  `relation_id`      INT(6) NOT NULL AUTO_INCREMENT,
  `relation_post`    INT(6) NOT NULL,
  `relation_comment` INT(6) NOT NULL,
  PRIMARY KEY (`relation_id`),
  FOREIGN KEY (`relation_post`) REFERENCES `Post` (`post_id`),
  FOREIGN KEY (`relation_comment`) REFERENCES comments (`comment_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
COMMIT;

-- 活动-评论 关系表
DROP TABLE IF EXISTS `Relation_Activity_Comment`;
CREATE TABLE `Relation_Activity_Comment`
(
  `relation_id`       INT(6) NOT NULL AUTO_INCREMENT,
  `relation_activity` INT(6) NOT NULL,
  `relation_comment`  INT(6) NOT NULL,
  PRIMARY KEY (`relation_id`),
  FOREIGN KEY (`relation_activity`) REFERENCES `Activity` (`activity_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (`relation_comment`) REFERENCES comments (`comment_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
COMMIT;