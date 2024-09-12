
CREATE TABLE student (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         account VARCHAR(255) UNIQUE,
                         password_md5 VARCHAR(255),
                         user_name VARCHAR(255),
                         create_time TIMESTAMP,
                         update_time TIMESTAMP,
                         address VARCHAR(255),
                         phone VARCHAR(255),
                         time_zone VARCHAR(255),
                         is_delete BOOLEAN,
                         image BLOB

);

INSERT INTO student (account, password_md5, user_name, create_time, update_time, address, phone, time_zone, is_delete, image)
VALUES ('student1@qq.com', 'e10adc3949ba59abbe56e057f20f883e', 'small ming', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '123 Main St', '123-456-7890', 'Sydney', false, null),
       ('student2@qq.com', 'e80b5017098950fc58aad83c8c14978e', 'small red', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '456 Elm St', '987-654-3210', 'Sydney', false, null),
       ('student3@qq.com', 'd8578edf8458ce06fbc5bb76a58c5ca4','small jun', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '789 Oak St', '555-555-5555', 'Sydney', false, null);



CREATE TABLE tutor (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       account VARCHAR(255) UNIQUE,
                       password_md5 VARCHAR(255),
                       user_name VARCHAR(255),
                       create_time TIMESTAMP,
                       update_time TIMESTAMP,
                       address VARCHAR(255),
                       phone VARCHAR(255),
                       time_zone VARCHAR(255),
                       is_delete BOOLEAN,
                       image BLOB,
                       bio TEXT,
                       file_path VARCHAR(255)
);

INSERT INTO tutor (account, password_md5, user_name, create_time, update_time, address, phone, time_zone, is_delete, image, bio, file_path)
VALUES ('tutor1@qq.com', 'e10adc3949ba59abbe56e057f20f883e','tutor1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '123 Main St', '123-456-7890', 'Sydney', false, null, 'hello', null),
       ('tutor2@qq.com', 'e80b5017098950fc58aad83c8c14978e','tutor2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '456 Elm St', '987-654-3210', 'Perth', false, null, 'hello', null),
       ('tutor3@qq.com', 'd8578edf8458ce06fbc5bb76a58c5ca4','tutor3', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '789 Oak St', '555-555-5555', 'Sydney', false, null, 'hello', null);

CREATE TABLE course (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        name VARCHAR(255) UNIQUE,
                        course_type VARCHAR(255),
                        description VARCHAR(255),
                        create_time TIMESTAMP,
                        update_time TIMESTAMP,
                        is_delete BOOLEAN,
                        image BLOB
);


INSERT INTO course (name, course_type, description, create_time, update_time, is_delete, image)
VALUES


    ('English Course 1', 'Language', 'Learn basic English grammar', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE,null),
    ('Mathematics Course 1', 'Science,Math', 'Introduction to algebra', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE,null),
    ('History Course 1', 'Social Studies,History', 'Ancient civilizations', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE,null),
    ('Physics Course 1', 'Science,Physics', 'Fundamentals of physics', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE,null),
    ('Computer Science Course 1', 'Technology,Computer Science', 'Introduction to programming', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE,null),
    ('Art Course 1', 'Arts,Painting', 'Exploring different art techniques', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE,null),
    ('Music Course 1', 'Arts,Music', 'Learning to play the guitar', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE,null),
    ('Biology Course 1', 'Science,Biology', 'Study of living organisms', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE,null),
    ('Chemistry Course 1', 'Science,Chemistry', 'Introduction to chemical reactions', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE,null),
    ('Physical Education Course 1', 'Sports,Physical Education', 'Fitness and sports activities', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE,null);
CREATE TABLE score (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       tutor_id INT UNIQUE NOT NULL,
                       number INT DEFAULT 0,
                       score INT DEFAULT 0,
                       create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       is_delete BOOLEAN DEFAULT FALSE
);
CREATE TABLE student_course (
                                id INT AUTO_INCREMENT PRIMARY KEY,
                                student_id INT NOT NULL,
                                course_id INT NOT NULL,
                                create_time TIMESTAMP,
                                update_time TIMESTAMP,
                                is_delete BOOLEAN,
                                CONSTRAINT unique_student_course UNIQUE (student_id, course_id)
);
CREATE TABLE tutor_course (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              tutor_id INT NOT NULL,
                              course_id INT NOT NULL,
                              create_time TIMESTAMP,
                              update_time TIMESTAMP,
                              is_delete BOOLEAN,
                              CONSTRAINT unique_tutor_course  UNIQUE (tutor_id, course_id)
);
CREATE TABLE book (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      tutor_id INT,
                      student_id INT,
                      start_time TIMESTAMP,
                      is_confirm BOOLEAN DEFAULT FALSE,
                      is_complete BOOLEAN DEFAULT FALSE,
                      is_delete BOOLEAN DEFAULT FALSE,
                      create_time TIMESTAMP,
                      update_time TIMESTAMP
);
INSERT INTO book (tutor_id, student_id, start_time, is_confirm, is_complete, is_delete, create_time, update_time)
VALUES
    (1, 1, '2023-11-17 09:00:00', FALSE, FALSE, FALSE, '2023-11-01 08:00:00', '2023-11-01 08:00:00'),
    (1, 2, '2023-11-18 10:00:00', TRUE, FALSE, FALSE, '2023-11-02 09:00:00', '2023-11-02 09:00:00'),
    (1, 3, '2023-11-19 11:00:00', FALSE, TRUE, FALSE, '2023-11-03 10:00:00', '2023-11-03 10:00:00'),
    (2, 1, '2023-11-20 12:00:00', TRUE, TRUE, FALSE, '2023-11-04 11:00:00', '2023-11-04 11:00:00'),
    (2, 2, '2023-11-21 13:00:00', FALSE, FALSE, TRUE, '2023-11-05 12:00:00', '2023-11-05 12:00:00'),
    (2, 3, '2023-11-22 14:00:00', TRUE, FALSE, FALSE, '2023-11-06 13:00:00', '2023-11-06 13:00:00'),
    (3, 1, '2023-11-23 15:00:00', FALSE, TRUE, FALSE, '2023-11-07 14:00:00', '2023-11-07 14:00:00'),
    (3, 2, '2023-11-24 16:00:00', TRUE, TRUE, FALSE, '2023-11-08 15:00:00', '2023-11-08 15:00:00'),
    (3, 3, '2023-11-25 17:00:00', FALSE, FALSE, TRUE, '2023-11-09 16:00:00', '2023-11-09 16:00:00');

CREATE TABLE chat (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      sender VARCHAR(255) NOT NULL,
                      receiver VARCHAR(255) NOT NULL,
                      message TEXT,
                      load_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      UNIQUE(sender, receiver)
);
