/*
 Navicat Premium Data Transfer

 Source Server         : KT（ORACLE175正式使用）
 Source Server Type    : Oracle
 Source Server Version : 110200
 Source Host           : 10.1.51.175:1521
 Source Schema         : TEST

 Target Server Type    : Oracle
 Target Server Version : 110200
 File Encoding         : 65001

 Date: 16/07/2023 16:40:21
*/


-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE "TEST"."student";
CREATE TABLE "TEST"."student" (
  "s_id" NUMBER(20,0) NOT NULL,
  "student_name" NVARCHAR2(255) NOT NULL,
  "age" NUMBER(11,0),
  "phone" NVARCHAR2(40),
  "addr" NVARCHAR2(255),
  "test01" NUMBER(4,0)
)
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "TEST"."student"."s_id" IS '自增id';
COMMENT ON COLUMN "TEST"."student"."student_name" IS '姓名';
COMMENT ON COLUMN "TEST"."student"."age" IS '年龄';
COMMENT ON COLUMN "TEST"."student"."phone" IS '联系方式';
COMMENT ON COLUMN "TEST"."student"."addr" IS '地址';
COMMENT ON COLUMN "TEST"."student"."test01" IS '测试';
COMMENT ON TABLE "TEST"."student" IS '学生信息';

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO "TEST"."student" VALUES ('9679639', '04、从oracle中获取', '4', '1346000444', '0404', NULL);
INSERT INTO "TEST"."student" VALUES ('9679641', '小李21', '343', '1346000', '上海5000', NULL);
INSERT INTO "TEST"."student" VALUES ('9679668', '小李29', '298', '1340', '上海5000', NULL);
INSERT INTO "TEST"."student" VALUES ('9679738', '小李99', '835', '1346000', NULL, NULL);

-- ----------------------------
-- Primary Key structure for table student
-- ----------------------------
ALTER TABLE "TEST"."student" ADD CONSTRAINT "SYS_C0011314" PRIMARY KEY ("s_id");

-- ----------------------------
-- Checks structure for table student
-- ----------------------------
ALTER TABLE "TEST"."student" ADD CONSTRAINT "SYS_C0011310" CHECK ("s_id" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "TEST"."student" ADD CONSTRAINT "SYS_C0011311" CHECK ("student_name" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "TEST"."student" ADD CONSTRAINT "SYS_C0011312" CHECK ("s_id" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "TEST"."student" ADD CONSTRAINT "SYS_C0011313" CHECK ("student_name" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "TEST"."student" ADD CONSTRAINT "SYS_C0080783" CHECK ("s_id" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE "TEST"."student" ADD CONSTRAINT "SYS_C0080784" CHECK ("student_name" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
