CREATE TABLE sys_notice(
    notice_id VARCHAR(32) NOT NULL   COMMENT '公告ID' ,
    notice_name VARCHAR(32)    COMMENT '公告名称' ,
    notice_content VARCHAR(32)    COMMENT '公告内容' ,
    notice_type VARCHAR(32)    COMMENT '公告类型' ,
    notice_cycle VARCHAR(32)    COMMENT '公告周期' ,
    notice_status VARCHAR(32)    COMMENT '状态' ,
    release_by VARCHAR(32)    COMMENT '发布者' ,
    release_date VARCHAR(32)    COMMENT '发布日期' ,
    remarks VARCHAR(32)    COMMENT '备注' ,
    create_by VARCHAR(32)    COMMENT '创建者' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32)    COMMENT '修改者' ,
    update_time DATETIME    COMMENT '修改时间' ,
    delete_by VARCHAR(32)    COMMENT '删除者' ,
    dalete_time DATETIME    COMMENT '删除时间' ,
    PRIMARY KEY (notice_id)
) COMMENT = '公告表 ';