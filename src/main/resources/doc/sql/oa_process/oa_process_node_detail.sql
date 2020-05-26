CREATE TABLE oa_process_node_detail(
    process_node_id VARCHAR(32) NOT NULL   COMMENT '节点ID' ,
    process_id VARCHAR(32)    COMMENT '流程ID' ,
    process_node_name VARCHAR(32)    COMMENT '节点名称' ,
    is_update VARCHAR(32)    COMMENT '可否修改' ,
    allow_by VARCHAR(32)    COMMENT '负责人' ,
    PRIMARY KEY (process_node_id)
) COMMENT = '流程节点表 ';