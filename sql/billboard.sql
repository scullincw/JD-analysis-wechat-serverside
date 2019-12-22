USE miniprogram_db;

DROP TABLE billboard;

/* 公告表 */
CREATE TABLE billboard (
id int(8) NOT NULL auto_increment COMMENT 'id',
create_time timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
billboard_title varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公告标题',
billboard_content varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公告内容',
PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '公告信息' ROW_FORMAT = Dynamic;


INSERT INTO billboard(billboard_title, billboard_content)
VALUES ("12月开会通知",
"全体员工请在12月8日，文泰楼202开会");
INSERT INTO billboard(billboard_title, billboard_content)
VALUES ("停电通知",
"12月10日10：00-18：00停电");
INSERT INTO billboard(billboard_title, billboard_content)
VALUES ("10月月度会议通知",
"全体员工请在10月8日，办公楼202开会");

INSERT INTO billboard(billboard_title, billboard_content)
VALUES ("办公楼停电通知",
"全体员工请注意，10月10号下午停电，公司停班一下午");

INSERT INTO billboard(billboard_title, billboard_content)
VALUES ("年度公司信息更新通知",
"全体员工请注意，即日起至10月31号，请各位员工登录个人信息页面，更新个人信息");

INSERT INTO billboard(billboard_title, billboard_content)
VALUES ("保险表格填报通知",
"全体员工请注意，请申请了新一代商业保险的员工前往所属部门经理处领取保险表格，按要求填报后准时上交");

INSERT INTO billboard(billboard_title, billboard_content)
VALUES ("新任项目经理上任通知",
"全体员工请注意，上任项目经理因公调往总部，新任项目经理由赵亮接任");

INSERT INTO billboard(billboard_title, billboard_content)
VALUES ("公司体育节通知",
"全体员工请注意，下周末将举行公司一年一度的体育节，望各员工积极参加，赛出精神！赛出友谊！");

INSERT INTO billboard(billboard_title, billboard_content)
VALUES ("党建活动通知 ",
"全体员工请注意，本周末将开启党建活动，请参加的员工在周六上午九点准时到公司报道");

INSERT INTO billboard(billboard_title, billboard_content)
VALUES ("十佳员工颁奖活动",
"全体员工请注意，本周五晚上八点的公司晚会上，将会揭晓本年度公司的十佳员工并现场颁奖，请各员工准时到场");

INSERT INTO billboard(billboard_title, billboard_content)
VALUES ("12月年末总结大会",
"全体员工请注意，12月31号将举办公司年末总结大会，届时将开展晚会并发放员工个人年终奖，请各员工准时到场");

INSERT INTO billboard(billboard_title, billboard_content)
VALUES ("新年动员大会",
"全体员工请注意，本公司将在2月8号正式开工，当天举办新年动员大会，开启新一年的工作，请各员工准时到场");


INSERT INTO billboard(billboard_title, billboard_content)
VALUES ("网络故障通知",
"全体员工请注意，经检测，公司某处设备故障，导致部分网络出错，请网上办公的员工保存好网络数据，谨防数据丢失");

INSERT INTO billboard(billboard_title, billboard_content)
VALUES ("下班关机通知",
"全体员工请注意，近期发现部分员工下班后未按规定关闭电脑和电源，请有以上行为的员工及时整改，执意不改者将受到相应处罚");

INSERT INTO billboard(billboard_title, billboard_content)
VALUES ("“送温暖”活动",
"全体员工请注意，为响应上级号召，公司开展“送温暖”给山区人民送去衣服活动，请大家积极参与，将不需要的衣物投入公司置物箱，送与山区贫苦人民");

INSERT INTO billboard(billboard_title, billboard_content)
VALUES ("敬老院义工活动",
"全体员工请注意，本周日公司将开展敬老院做义工活动，请各员工踊跃报名参加");

INSERT INTO billboard(billboard_title, billboard_content)
VALUES ("个人工资发放通知",
"全体员工请注意，本月工资已发放到个人工资卡，全部发放完毕，如有错漏，请与人事部相关人员联系解决");


SELECT * FROM billboard;