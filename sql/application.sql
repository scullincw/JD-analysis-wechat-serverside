USE miniprogram_db;

DROP TABLE IF EXISTS application;

CREATE TABLE application (
id int(100) NOT NULL auto_increment COMMENT 'id',
create_time timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
app_type int(10) NOT NULL COMMENT '申请类型：1.请假 2.出差 3.报销 4.会议室预约',
applicant varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '申请人姓名',
app_content varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '申请理由',
additional_content varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '附加事项，如果是报销则是金额，如果是会议室申请则是block和room',
applicant_openid varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '申请人微信账号的openid, 外键',
reviewer_openid varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '审核人微信账号的openid, 外键',
review_time timestamp NULL COMMENT '审核时间',
PRIMARY KEY (`id`) USING BTREE,
FOREIGN KEY (`applicant_openid`) REFERENCES user(`open_id`),
FOREIGN KEY (`reviewer_openid`) REFERENCES user(`open_id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '申请信息' ROW_FORMAT = Dynamic;

#测试添加申请
INSERT INTO application(app_type, applicant, app_content, applicant_openid)
VALUES (1, 'scullin', '请假', 'oY1mB4g3MhuTNLA1de7JuRFCKZ0E');

INSERT INTO application(app_type, applicant, app_content, additional_content, applicant_openid)
VALUES (3, '程为', '打车报销', '300元', 'oY1mB4g3MhuTNLA1de7JuRFCKZ0E');

INSERT INTO application(app_type, applicant, app_content, additional_content, applicant_openid)
VALUES (4, '戴', '借用会议室开会', '文泰楼 105', 'oY1mB4g3MhuTNLA1de7JuRFCKZ0E');

#测试审批
UPDATE application
SET reviewer_openid = 'oY1mB4g3MhuTNLA1de7JuRFCKZ0E', review_time = current_timestamp()
WHERE id = 1;

SELECT * FROM application;


