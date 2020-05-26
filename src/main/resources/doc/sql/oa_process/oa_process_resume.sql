CREATE TABLE oa_process_resume(
    id VARCHAR(32) NOT NULL   COMMENT '主键ID' ,
    process_id VARCHAR(32)    COMMENT '流程ID' ,
    clue_id VARCHAR(32)    COMMENT '线索ID' ,
    process_node_id VARCHAR(32)    COMMENT '流程节点ID' ,
    process_node_name VARCHAR(32)    COMMENT '流程节点名称' ,
    process_next_node_id VARCHAR(32)    COMMENT '下一节点ID' ,
    operation_by VARCHAR(32)    COMMENT '操作者' ,
    operation_time DATETIME    COMMENT '操作时间' ,
    PRIMARY KEY (id)
) COMMENT = '线索流程履历表 ';