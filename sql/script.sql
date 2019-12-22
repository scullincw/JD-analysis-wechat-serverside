USE miniprogram_db;

/* 微信用户表，登录与发起业务时用 */
CREATE TABLE `user`  (
  `open_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'open_id',
  `skey` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'skey',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_visit_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后登录时间',
  `session_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'session_key',
  `city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市',
  `province` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省',
  `country` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `gender` tinyint(11) NULL DEFAULT NULL COMMENT '性别',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网名',
  PRIMARY KEY (`open_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '微信用户信息' ROW_FORMAT = Dynamic;

#DROP TABLE user;

INSERT INTO user(name, level)
VALUES ("creator", 1);

SELECT * FROM user;




/* 会议室表 */
CREATE TABLE meeting_room (
id int(3) not null auto_increment,
block varchar(20) not null,
room varchar(20) not null,
primary key(id),
unique key (id)
) engine=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=UTF8MB4;

#DROP TABLE meeting_room

INSERT INTO meeting_room
VALUES (001, "文澜楼", 101);
INSERT INTO meeting_room(block, room)
VALUES ("文澜楼", 102);
INSERT INTO meeting_room(block, room)
VALUES ("文澜楼", 103);
INSERT INTO meeting_room(block, room)
VALUES ("文澜楼", 104);
INSERT INTO meeting_room(block, room)
VALUES ("文澜楼", 201);
INSERT INTO meeting_room(block, room)
VALUES ("文澜楼", 202);
INSERT INTO meeting_room(block, room)
VALUES ("文澜楼", 203);
INSERT INTO meeting_room(block, room)
VALUES ("文澜楼", 204);
INSERT INTO meeting_room(block, room)
VALUES ("文澜楼", 301);
INSERT INTO meeting_room(block, room)
VALUES ("文澜楼", 302);
INSERT INTO meeting_room(block, room)
VALUES ("文澜楼", 303);
INSERT INTO meeting_room(block, room)
VALUES ("文澜楼", 304);

INSERT INTO meeting_room(block, room)
VALUES ("文波楼", 101);
INSERT INTO meeting_room(block, room)
VALUES ("文波楼", 102);
INSERT INTO meeting_room(block, room)
VALUES ("文波楼", 103);
INSERT INTO meeting_room(block, room)
VALUES ("文波楼", 104);
INSERT INTO meeting_room(block, room)
VALUES ("文波楼", 201);
INSERT INTO meeting_room(block, room)
VALUES ("文波楼", 202);
INSERT INTO meeting_room(block, room)
VALUES ("文波楼", 203);
INSERT INTO meeting_room(block, room)
VALUES ("文波楼", 204);
INSERT INTO meeting_room(block, room)
VALUES ("文波楼", 301);
INSERT INTO meeting_room(block, room)
VALUES ("文波楼", 302);
INSERT INTO meeting_room(block, room)
VALUES ("文波楼", 303);
INSERT INTO meeting_room(block, room)
VALUES ("文波楼", 304);

INSERT INTO meeting_room(block, room)
VALUES ("文泰楼", 101);
INSERT INTO meeting_room(block, room)
VALUES ("文泰楼", 102);
INSERT INTO meeting_room(block, room)
VALUES ("文泰楼", 103);
INSERT INTO meeting_room(block, room)
VALUES ("文泰楼", 104);
INSERT INTO meeting_room(block, room)
VALUES ("文泰楼", 201);
INSERT INTO meeting_room(block, room)
VALUES ("文泰楼", 202);
INSERT INTO meeting_room(block, room)
VALUES ("文泰楼", 203);
INSERT INTO meeting_room(block, room)
VALUES ("文泰楼", 204);
INSERT INTO meeting_room(block, room)
VALUES ("文泰楼", 301);
INSERT INTO meeting_room(block, room)
VALUES ("文泰楼", 302);
INSERT INTO meeting_room(block, room)
VALUES ("文泰楼", 303);
INSERT INTO meeting_room(block, room)
VALUES ("文泰楼", 304);

INSERT INTO meeting_room(block, room)
VALUES ("文永楼", 101);
INSERT INTO meeting_room(block, room)
VALUES ("文永楼", 102);
INSERT INTO meeting_room(block, room)
VALUES ("文永楼", 103);
INSERT INTO meeting_room(block, room)
VALUES ("文永楼", 104);
INSERT INTO meeting_room(block, room)
VALUES ("文永楼", 201);
INSERT INTO meeting_room(block, room)
VALUES ("文永楼", 202);
INSERT INTO meeting_room(block, room)
VALUES ("文永楼", 203);
INSERT INTO meeting_room(block, room)
VALUES ("文永楼", 204);
INSERT INTO meeting_room(block, room)
VALUES ("文永楼", 301);
INSERT INTO meeting_room(block, room)
VALUES ("文永楼", 302);
INSERT INTO meeting_room(block, room)
VALUES ("文永楼", 303);
INSERT INTO meeting_room(block, room)
VALUES ("文永楼", 304);