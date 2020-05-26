CREATE TABLE oa_process(
    process_id VARCHAR(32) NOT NULL   COMMENT '流程ID' ,
    process_name VARCHAR(32)    COMMENT '流程名称' ,
    process_config VARCHAR(3120)    COMMENT '流程配置' ,
    remarks VARCHAR(32)    COMMENT '备注' ,
    status VARCHAR(1)    COMMENT '状态' ,
    create_by VARCHAR(32)    COMMENT '创建者' ,
    create_time DATETIME    COMMENT '创建时间' ,
    update_by VARCHAR(32)    COMMENT '修改者' ,
    update_time DATETIME    COMMENT '修改时间' ,
    delete_by VARCHAR(32)    COMMENT '删除者' ,
    dalete_time DATETIME    COMMENT '删除时间' ,
    PRIMARY KEY (process_id)
) COMMENT = '流程表 ';