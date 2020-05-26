CREATE TABLE sys_person(
    person_id VARCHAR(32) NOT NULL   COMMENT '人员ID' ,
    person_name VARCHAR(32)    COMMENT '姓名' ,
    sex VARCHAR(1)    COMMENT '性别' ,
    age INT    COMMENT '年龄' ,
    mobile_phone INT    COMMENT '手机号码' ,
    office_phone VARCHAR(32)    COMMENT '办公电话' ,
    email VARCHAR(32)    COMMENT '邮箱' ,
    identity_id VARCHAR(32)    COMMENT '身份证号' ,
    home_address VARCHAR(32)    COMMENT '家庭住址' ,
    office_address VARCHAR(32)    COMMENT '办公地址' ,
    dept_id VARCHAR(32)    COMMENT '部门' ,
    office_position VARCHAR(32)    COMMENT '职位' ,
    office_id VARCHAR(32)    COMMENT '工号' ,
    remarks VARCHAR(32)    COMMENT '备注' ,
    PRIMARY KEY (person_id)
) COMMENT = '人员信息表 ';