USE xiaolanshu;

INSERT INTO t_permission
    (id, parent_id, name, type, menu_url, menu_icon, sort, permission_key, status, create_time, update_time, is_deleted)
VALUES
    (1, 0, '发布笔记', 3, '', '', 1, 'app:note:publish', 0, NOW(), NOW(), b'0'),
    (2, 0, '发布评论', 3, '', '', 2, 'app:comment:publish', 0, NOW(), NOW(), b'0')
ON DUPLICATE KEY UPDATE name = VALUES(name), permission_key = VALUES(permission_key);

INSERT INTO t_role
    (id, role_name, role_key, status, sort, remark, create_time, update_time, is_deleted)
VALUES
    (1, '普通用户', 'common_user', 0, 1, '', NOW(), NOW(), b'0')
ON DUPLICATE KEY UPDATE role_name = VALUES(role_name), role_key = VALUES(role_key);

INSERT INTO t_role_permission_rel
    (id, role_id, permission_id, create_time, update_time, is_deleted)
VALUES
    (1, 1, 1, NOW(), NOW(), b'0'),
    (2, 1, 2, NOW(), NOW(), b'0')
ON DUPLICATE KEY UPDATE role_id = VALUES(role_id), permission_id = VALUES(permission_id);
