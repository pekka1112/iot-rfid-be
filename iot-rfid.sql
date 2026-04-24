DROP DATABASE IF EXISTS iot_rfid;
CREATE DATABASE iot_rfid; USE iot_rfid;
CREATE TABLE residents (
 resident_id INT AUTO_INCREMENT PRIMARY KEY,
 full_name VARCHAR(100) NOT NULL,
 phone VARCHAR(20),
 birth_year INT,
 created_at DATETIME DEFAULT CURRENT_TIMESTAMP, STATUS VARCHAR(20) DEFAULT 'active'
);
CREATE TABLE faces (
 face_id INT AUTO_INCREMENT PRIMARY KEY,
 resident_id INT,
 face_image VARCHAR(255),
 face_embedding TEXT,
 created_at DATETIME DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY (resident_id) REFERENCES residents(resident_id)
);
CREATE TABLE vehicles (
 vehicle_id INT AUTO_INCREMENT PRIMARY KEY,
 resident_id INT,
 license_plate VARCHAR(20) UNIQUE,
 vehicle_type VARCHAR(20),
 created_at DATETIME DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY (resident_id) REFERENCES residents(resident_id)
);
CREATE TABLE rfid_cards (
 rfid_id INT AUTO_INCREMENT PRIMARY KEY,
 card_uid VARCHAR(50) UNIQUE,
 resident_id INT,
 vehicle_id INT,
 issued_date DATETIME,
 expire_date DATETIME, FOREIGN KEY (resident_id) REFERENCES residents(resident_id), FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id)
);
CREATE TABLE device_backups (
 backup_id INT AUTO_INCREMENT PRIMARY KEY,
 resident_id INT,
 device_user_id INT NOT NULL,
 user_name_on_device VARCHAR(50), 
 credential_type VARCHAR(50), -- Loại (ví dụ: Ảnh khuôn mặt, Vân tay, Thẻ)
 backup_num
INT NOT NULL, -- Số Backup (ví dụ: 50 hoặc 22)
 is_admin
INT, -- Quyền Admin trên máy (Có/Không)
 created_at
DATETIME DEFAULT CURRENT_TIMESTAMP,
 updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON
UPDATE CURRENT_TIMESTAMP, FOREIGN KEY (resident_id) REFERENCES residents(resident_id) ON
DELETE CASCADE
);
CREATE TABLE	rfid_users_backups (
 guest_id INT AUTO_INCREMENT PRIMARY KEY,
 card_uid VARCHAR(50) NOT NULL, -- ID thẻ RFID tạm thời phát cho khách
 license_plate
VARCHAR(20) NOT NULL, -- Biển số xe khách
 check_in_at
DATETIME DEFAULT CURRENT_TIMESTAMP,
 check_out_at DATETIME NULL, STATUS VARCHAR(20) DEFAULT 'in_building', -- 'in_building' (đang ở trong), 'departed' (đã ra)
 gate_in_id
INT, -- Lưu ID camera/cổng lúc vào
 gate_out_id
INT, -- Lưu ID camera/cổng lúc ra
 
 -- Các chỉ mục để tìm kiếm nhanh khi khách ra
INDEX idx_guest_card (card_uid), INDEX idx_guest_plate (license_plate)
);
CREATE TABLE cameras (
 camera_id INT AUTO_INCREMENT PRIMARY KEY,
 camera_name VARCHAR(50),
 location VARCHAR(100),
 direction VARCHAR(10)
);
CREATE TABLE access_logs (
 log_id INT AUTO_INCREMENT PRIMARY KEY,
 resident_id INT,
 vehicle_id INT,
 camera_id INT,
 detected_plate VARCHAR(20),
 face_match BOOLEAN,
 plate_match BOOLEAN,
 direction VARCHAR(10),
 image_face VARCHAR(255),
 image_plate VARCHAR(255),
 created_at DATETIME DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY (resident_id) REFERENCES residents(resident_id), FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id), FOREIGN KEY (camera_id) REFERENCES cameras(camera_id)
);
CREATE TABLE fire_alerts (
 alert_id INT AUTO_INCREMENT PRIMARY KEY,
 location VARCHAR(100),
 sensor_id VARCHAR(50),
 alert_level VARCHAR(20),
 created_at DATETIME DEFAULT CURRENT_TIMESTAMP, STATUS VARCHAR(20)
);
CREATE INDEX idx_plate ON vehicles(license_plate);
CREATE INDEX idx_access_time ON access_logs(created_at);
CREATE INDEX idx_resident ON access_logs(resident_id);
-- Index để tìm kiếm nhanh theo ID người dùng trên máy
CREATE INDEX idx_device_user ON device_backups(device_user_id);

-- 1. Thêm Cư dân mẫu
INSERT INTO residents (full_name, phone, birth_year, STATUS) VALUES 
('Nguyễn Văn A', '0901234567', 1990, 'active'),
('Trần Thị B', '0987654321', 1995, 'active');

-- 2. Thêm Phương tiện
INSERT INTO vehicles (resident_id, license_plate, vehicle_type) VALUES 
(1, '59-A1 123.45', 'Motorbike'),
(2, '51-F1 678.90', 'Car');

-- 3. Thêm Dữ liệu dự phòng trên thiết bị (FaceID/Vân tay)
INSERT INTO device_backups (resident_id, device_user_id, user_name_on_device, credential_type, backup_num, is_admin) VALUES 
(1, 101, 'Nguyen A', 'FaceID', 50, 0),
(2, 102, 'Tran B', 'Fingerprint', 1, 0);

-- 4. Thêm Camera
INSERT INTO cameras (camera_name, location, direction) VALUES 
('CAM_GATE_01', 'Cổng chính - Lối vào', 'IN'),
('CAM_GATE_02', 'Cổng chính - Lối ra', 'OUT');

-- 5. Thêm Khách vãn lai
INSERT INTO rfid_users_backups (card_uid, license_plate, STATUS) VALUES 
('UID_GUEST_999', '72-C1 555.55', 'in_building');

-- 6. Thêm Nhật ký ra vào
INSERT INTO access_logs (resident_id, vehicle_id, camera_id, detected_plate, face_match, plate_match, direction) VALUES 
(1, 1, 1, '59-A1 123.45', 1, 1, 'IN');

-- 7. Thêm Cảnh báo cháy
INSERT INTO fire_alerts (location, sensor_id, alert_level, STATUS) VALUES 
('Hầm gửi xe B1', 'SENSOR_SMOKE_05', 'High', 'Resolved'); SHOW TABLES;