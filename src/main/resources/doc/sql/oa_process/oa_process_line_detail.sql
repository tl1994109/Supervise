CREATE TABLE oa_process_line_detail(
    line_id VARCHAR(32) NOT NULL   COMMENT '线条ID' ,
    process_id VARCHAR(32)    COMMENT '流程ID' ,
    line_name VARCHAR(32)    COMMENT '线条名称' ,
    line_type VARCHAR(32)    COMMENT '线条状态 0通过，1不通过，2结束流程' ,
    process_node_id VARCHAR(32)    COMMENT '上一节点ID' ,
    process_next_node_id VARCHAR(32)    COMMENT '下一节点ID' ,
    PRIMARY KEY (line_id)
) COMMENT = '流程线条表 ';