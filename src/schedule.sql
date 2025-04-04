CREATE TABLE users(
                      user_id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '작성자 식별자', -- 고유키
                      name VARCHAR(100) NOT NULL COMMENT '작성자명', -- 동명이인 가능
                      email VARCHAR(100) UNIQUE COMMENT '이메일', -- 중복될 수 없음
                      password VARCHAR(200) NOT NULL COMMENT '비밀번호', -- 유저 로그인 비밀번호
                      created_at TIMESTAMP COMMENT '등록일', -- 초기 생성 시, 설정 후 변하지 않음
                      updated_at TIMESTAMP COMMENT '수정일' -- 일정 최근 수정일에 따라 업데이트 될 변수
);

CREATE TABLE  tasks(
                       task_id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '일정 식별자', -- 고유키
                       user_id BIGINT NOT NULL COMMENT '작성자 식별자', -- 외래키, 작성자명을 불러올 때 사용함
                       title VARCHAR(100) NOT NULL COMMENT '제목',
                       content VARCHAR(200) NOT NULL COMMENT '할일', -- 최대 200자 제한
                       created_at TIMESTAMP COMMENT '작성일',
                       updated_at TIMESTAMP COMMENT '수정일',
                       FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE -- 외래키 / 작성자 삭제 시, 해당 작성자의 일정이 삭제됨
);

CREATE TABLE  comments(
                          comment_id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '댓글 식별자', -- 고유키
                          task_id BIGINT NOT NULL COMMENT '일정 식별자', -- 외래키, 댓글이 적힌 일정
                          user_id BIGINT NOT NULL COMMENT '작성자 식별자', -- 외래키, 작성자명을 불러올 때 사용함
                          content VARCHAR(200) NOT NULL COMMENT '댓글', -- 최대 200자 제한
                          created_at TIMESTAMP COMMENT '작성일',
                          updated_at TIMESTAMP COMMENT '수정일',
                          FOREIGN KEY (task_id) REFERENCES tasks(task_id) ON DELETE CASCADE, -- 외래키 / 일정 삭제 시, 해당 일정의 댓글이 삭제됨
                          FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE -- 외래키 / 작성자 삭제 시, 해당 작성자의 댓글이 삭제됨
);