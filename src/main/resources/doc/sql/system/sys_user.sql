CREATE TABLE sys_user(
    user_id VARCHAR(32) NOT NULL   COMMENT '用户ID' ,
    person_id VARCHAR(32)    COMMENT '人员ID' ,
    user_name VARCHAR(32)    COMMENT '用户名' ,
    password VARCHAR(32)    COMMENT '密码' ,
    avatar VARCHAR(32)    COMMENT '头像路径' ,
    login_ip VARCHAR(32)    COMMENT '登录IP' ,
    login_time DATETIME    COMMENT '登录时间' ,
    status VARCHAR(1)    COMMENT '状态' ,
    create_by VARCHAR(32)    COMMENT '创建者' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32)    COMMENT '修改者' ,
    update_time DATETIME    COMMENT '修改时间' ,
    delete_by VARCHAR(32)    COMMENT '删除者' ,
    dalete_time DATETIME    COMMENT '删除时间' ,
    PRIMARY KEY (user_id)
) COMMENT = '用户信息表 ';