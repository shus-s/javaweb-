-- 创建数据库
CREATE DATABASE IF NOT EXISTS studyroom_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE studyroom_db;

-- 用户表
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(50) NOT NULL,
    student_id VARCHAR(20),
    phone VARCHAR(20),
    email VARCHAR(100),
    role VARCHAR(20) DEFAULT 'student',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 自习室表
CREATE TABLE IF NOT EXISTS study_rooms (
    id INT PRIMARY KEY AUTO_INCREMENT,
    room_name VARCHAR(50) NOT NULL,
    location VARCHAR(100),
    capacity INT NOT NULL,
    description TEXT,
    status VARCHAR(20) DEFAULT 'available',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 座位表
CREATE TABLE IF NOT EXISTS seats (
    id INT PRIMARY KEY AUTO_INCREMENT,
    room_id INT NOT NULL,
    seat_number VARCHAR(20) NOT NULL,
    status VARCHAR(20) DEFAULT 'available',
    FOREIGN KEY (room_id) REFERENCES study_rooms(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 预约表
CREATE TABLE IF NOT EXISTS reservations (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    room_id INT NOT NULL,
    seat_id INT,
    reservation_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    status VARCHAR(20) DEFAULT 'active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES study_rooms(id) ON DELETE CASCADE,
    FOREIGN KEY (seat_id) REFERENCES seats(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 插入初始数据
INSERT INTO users (username, password, name, student_id, phone, email, role) VALUES
('admin', 'admin123', '管理员', 'ADMIN001', '13800000000', 'admin@studyroom.com', 'admin'),
('student1', '123456', '张三', '2021001', '13800000001', 'student1@studyroom.com', 'student'),
('student2', '123456', '李四', '2021002', '13800000002', 'student2@studyroom.com', 'student');

INSERT INTO study_rooms (room_name, location, capacity, description, status) VALUES
('自习室A', '教学楼1楼101', 50, '安静舒适，适合个人学习', 'available'),
('自习室B', '教学楼2楼201', 80, '宽敞明亮，适合小组讨论', 'available'),
('自习室C', '图书馆3楼', 100, '环境优雅，藏书丰富', 'available'),
('自习室D', '实验楼1楼', 60, '配备电脑，适合编程学习', 'available');

INSERT INTO seats (room_id, seat_number, status) VALUES
(1, 'A01', 'available'), (1, 'A02', 'available'), (1, 'A03', 'available'), (1, 'A04', 'available'), (1, 'A05', 'available'),
(2, 'B01', 'available'), (2, 'B02', 'available'), (2, 'B03', 'available'), (2, 'B04', 'available'), (2, 'B05', 'available'),
(3, 'C01', 'available'), (3, 'C02', 'available'), (3, 'C03', 'available'), (3, 'C04', 'available'), (3, 'C05', 'available'),
(4, 'D01', 'available'), (4, 'D02', 'available'), (4, 'D03', 'available'), (4, 'D04', 'available'), (4, 'D05', 'available');



