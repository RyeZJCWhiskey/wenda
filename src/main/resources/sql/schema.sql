-- 数据库初始化脚本

-- 为什么手写数据库模式定义语言DDL(Data Definition Language)？
-- 记录每次上线的DDL修改


-- 连接数据库控制台
--  mysql -uroot -p
-- 创建数据库
CREATE DATABASE wenda;
-- 使用数据库
USE wenda;

-- 创建user表
DROP TABLE IF EXISTS user;
CREATE TABLE user
(
  id       INT(15) UNSIGNED NOT NULL AUTO_INCREMENT,
  name     VARCHAR(64)      NOT NULL DEFAULT '',
  password VARCHAR(128)     NOT NULL DEFAULT '',
  salt     VARCHAR(32)      NOT NULL DEFAULT '',
  head_url VARCHAR(156)     NOT NULL DEFAULT '',
  PRIMARY KEY (id),
  UNIQUE KEY (name)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='user表';

-- 创建question表
DROP TABLE IF EXISTS question;
CREATE TABLE question
(
  id            INT          NOT NULL AUTO_INCREMENT,
  title         VARCHAR(255) NOT NULL,
  content       TEXT         NULL,
  user_id       INT          NOT NULL,
  created_date  DATETIME     NOT NULL,
  comment_count INT          NOT NULL,
  PRIMARY KEY (id),
  INDEX data_index (created_date ASC) /*创建索引*/
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='question表';

-- 创建login_ticket表
DROP TABLE IF EXISTS login_ticket;
CREATE TABLE login_ticket
(
  id      INT         NOT NULL AUTO_INCREMENT,
  user_id INT         NOT NULL,
  ticket  VARCHAR(45) NOT NULL,
  expired DATETIME    NOT NULL,
  status  INT         NULL DEFAULT 0,
  PRIMARY KEY (id),
  UNIQUE INDEX ticket_UNIQUE (ticket ASC)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='login_ticket表';

-- 创建comment表
DROP TABLE IF EXISTS comment;
CREATE TABLE comment
(
  id           INT      NOT NULL AUTO_INCREMENT,
  user_id      INT      NOT NULL,
  created_date DATETIME NOT NULL,
  entity_id    INT      NOT NULL,
  entity_type  INT      NOT NULL,
  content      TEXT     NOT NULL,
  status       INT      NOT NULL,
  PRIMARY KEY (id),
  INDEX entity_index (entity_id, entity_type ASC),
  INDEX user_index (user_id ASC)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='comment表';

-- 创建message表
DROP TABLE IF EXISTS message;
CREATE TABLE message
(
  id              INT         NOT NULL AUTO_INCREMENT,
  from_id         INT         NOT NULL,
  to_id           INT         NOT NULL,
  content         TEXT        NOT NULL,
  created_date    DATETIME    NOT NULL,
  has_read        INT         NOT NULL,
  conversation_id VARCHAR(32) NOT NULL,
  PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='message表';