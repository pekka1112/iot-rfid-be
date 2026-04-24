# Walkthrough - IoT RFID Spring Boot Backend

## Tong quan

Da xay dung hoan chinh backend Spring Boot cho he thong kiem soat ra vao IoT (RFID + Camera).
Project compile thanh cong voi **BUILD SUCCESS**.

---

## Cau truc thu muc da tao

```
src/main/java/com/example/iotrfidbe/
├── config/
│   ├── CorsConfig.java              CORS cho ReactJS (localhost:3000, 5173, 5174)
│   └── GlobalExceptionHandler.java  Xu ly loi tap trung, tra JSON sach
├── entity/
│   ├── Residents.java               (co san)
│   ├── Vehicles.java                (co san)
│   ├── Faces.java                   (co san)
│   ├── RfidCards.java               (co san)
│   ├── AccessLogs.java              (co san)
│   ├── Cameras.java                 (co san)
│   ├── FireAlerts.java              (co san)
│   ├── RfidUsersBackups.java        [MOI] Khach van lai
│   └── DeviceBackups.java           [MOI] Lien ket thiet bi nhan dien -> cu dan
├── repository/                      [MOI] 9 JPA Repository
├── dto/                             [MOI] 8 DTO class
├── service/
│   ├── ResidentService.java         [MOI] CRUD cu dan
│   ├── AccessLogService.java        [MOI] CRUD lich su
│   ├── CheckAccessService.java      [MOI] Kiem tra cu dan (FaceID + bien so)
│   └── GuestService.java            [MOI] Check-in/out khach van lai
└── controller/                      [MOI] 7 REST Controller
```

---

## Danh sach API day du

### CRUD - Cu dan
| Method | URL | Mo ta |
|--------|-----|-------|
| GET | /api/residents | Danh sach tat ca cu dan |
| GET | /api/residents/{id} | Cu dan theo ID |
| GET | /api/residents/search?name= | Tim kiem theo ten |
| GET | /api/residents/status/{status} | Loc theo trang thai |
| POST | /api/residents | Tao moi cu dan |
| PUT | /api/residents/{id} | Cap nhat cu dan |
| DELETE | /api/residents/{id} | Xoa cu dan |

### CRUD - Lich su ra vao (Access Log)
| Method | URL | Mo ta |
|--------|-----|-------|
| GET | /api/access-logs | Toan bo nhat ky (moi nhat truoc) |
| GET | /api/access-logs/{id} | Nhat ky theo ID |
| GET | /api/access-logs/resident/{id} | Lich su cua mot cu dan |
| GET | /api/access-logs/range?from=&to= | Loc theo thoi gian |
| POST | /api/access-logs | Tao nhat ky thu cong |
| DELETE | /api/access-logs/{id} | Xoa nhat ky |

### CRUD - Xe, Camera, Khuon mat, RFID Card
| URL prefix | Method |
|------------|--------|
| /api/vehicles | GET, GET/{id}, GET/resident/{id}, POST, PUT/{id}, DELETE/{id} |
| /api/cameras | GET, GET/{id}, POST, PUT/{id}, DELETE/{id} |
| /api/faces | GET, GET/{id}, GET/resident/{id}, POST?residentId=, PUT/{id}, DELETE/{id} |
| /api/rfid-cards | GET, GET/{id}, GET/resident/{id}, POST, PUT/{id}, DELETE/{id} |

### Kiem tra cu dan (Dac biet)
```
POST /api/check/resident
Body: { "deviceUserId": 101, "licensePlate": "59-A1 123.45" }
```
Logic:
1. Tim device_backups theo deviceUserId → lay resident
2. Tim vehicles theo licensePlate → lay chu xe
3. So khop 2 resident_id
4. Ghi access_log
5. Tra ve: { isResident, residentId, fullName, grantAccess, message }

### Khach van lai (Dac biet)
```
POST /api/guests/checkin
Body: { "cardUid": "UID_GUEST_001", "licensePlate": "72-C1 555.55", "gateInId": 1 }

POST /api/guests/checkout
Body: { "cardUid": "UID_GUEST_001", "licensePlate": "72-C1 555.55", "gateOutId": 2 }

GET  /api/guests              - Toan bo lich su khach
GET  /api/guests/in-building  - Khach dang trong toa nha
```

---

## Ket qua Build

```
[INFO] BUILD SUCCESS
[INFO] Compiling 41 source files
```

---

## Huong dan chay

1. Dam bao MySQL dang chay va da chay file `iot-rfid.sql`
2. Chinh sua `application.properties` neu can:
   - `spring.datasource.username` / `password`
3. Mo IntelliJ IDEA va chay `IotRfidBeApplication`
   hoac: Set JAVA_HOME va chay `mvnw spring-boot:run`
4. Server se lang nghe tai `http://localhost:8080`
