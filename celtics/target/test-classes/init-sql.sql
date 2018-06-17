DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`(
  `id` INT(11)  NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(64) NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  `salt` VARCHAR(64) NOT NULL,
  `head_image_url` VARCHAR(256) NOT NULL,
  PRIMARY KEY(`id`),
  UNIQUE (`username`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `news`;
CREATE TABLE `news`(
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `type` TINYINT DEFAULT 3,
  `user_id` INT(11) NOT NULL,
  `like_count` INT(11) DEFAULT 0,
  `comment_count` INT(11) DEFAULT 0,
  `title` VARCHAR(256) NOT NULL,
  `link` TEXT,
  `image_link` VARCHAR(256),
  `create_date` DATETIME NOT NULL,
  PRIMARY KEY(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;