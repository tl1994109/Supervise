CREATE TABLE oa_process_clue_detail(
    id VARCHAR(32) NOT NULL   COMMENT '主键ID' ,
    process_id VARCHAR(32)    COMMENT '流程ID' ,
    clue_id VARCHAR(32)    COMMENT '线索ID' ,
    process_node_id VARCHAR(32)    COMMENT '流程节点ID' ,
    process_node_name VARCHAR(32)    COMMENT '流程节点名称' ,
    process_next_node_id VARCHAR(32)    COMMENT '下一节点ID' ,
    allow_by VARCHAR(32)    COMMENT '负责人' ,
    PRIMARY KEY (id)
) COMMENT = '线索流程详细表 ';