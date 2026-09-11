-- Execute this once in the xiaolanshu database before setting LEAF_SEGMENT_ENABLED=true.
CREATE TABLE IF NOT EXISTS leaf_alloc (
    biz_tag VARCHAR(128) NOT NULL PRIMARY KEY,
    max_id BIGINT NOT NULL DEFAULT 1,
    step INT NOT NULL DEFAULT 1000,
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO leaf_alloc (biz_tag, max_id, step)
VALUES
    ('leaf-segment-user-id', 1, 1000),
    ('leaf-segment-comment-id', 1, 1000),
    ('leaf-segment-xiaolanshu-id', 1, 1000)
ON DUPLICATE KEY UPDATE biz_tag = VALUES(biz_tag);