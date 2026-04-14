# Web ban ve xem phim

Du an gom 2 module Spring Boot:
- `cinema_back_end-master`: REST API, xac thuc, xu ly du lieu rap/phim/lich/chon ghe/hoa don.
- `cinema_client`: giao dien web (Thymeleaf) cho nguoi dung va trang quan tri.

## Chuc nang chinh
- Dang ky, dang nhap, quan ly thong tin tai khoan.
- Xem danh sach phim, chi tiet phim, lich chieu.
- Dat ve, chon ghe, xem hoa don.
- Quan tri phim, rap, phong, lich chieu, tai khoan, khuyen mai, feedback.

## Cong nghe su dung
- Java 19
- Spring Boot 2.6.2
- Spring Security, Spring Data JPA
- Thymeleaf (client)
- MySQL (profile local), PostgreSQL (profile prod)
- Maven

## Cau truc thu muc
- `cinema_back_end-master/`: Backend API.
- `cinema_client/`: Frontend server-side rendering.
- `cinema (2).sql`: File du lieu SQL de khoi tao CSDL.

## Yeu cau moi truong
- JDK 19
- Maven 3.8+ (hoac dung Maven Wrapper co san `mvnw`/`mvnw.cmd`)
- MySQL 8.x

## Huong dan cai dat va chay local

### 1) Tao database
1. Tao database ten `cinema` trong MySQL.
2. Import file `cinema (2).sql` vao database `cinema`.

### 2) Cau hinh backend
Sua file `cinema_back_end-master/src/main/resources/application-local.properties`:
- `spring.datasource.url=jdbc:mysql://localhost/cinema`
- `spring.datasource.username=<mysql_user>`
- `spring.datasource.password=<mysql_password>`

Neu dung tinh nang gui mail, sua trong `cinema_back_end-master/src/main/resources/application.properties`:
- `spring.mail.username`
- `spring.mail.password`

### 3) Chay backend (port 8080)
Tu thu muc goc du an:

```bash
cd cinema_back_end-master
mvnw.cmd spring-boot:run
```

Backend se chay tai `http://localhost:8080`.

### 4) Cau hinh client
Client mac dinh goi API den:
- `cinema_client/src/main/java/com/example/cinema_client/constants/Api.java`
- `baseURL = "http://localhost:8080"`

Neu backend chay cong khac, doi lai gia tri `baseURL`.

### 5) Chay client (port 8081)
Mo terminal moi:

```bash
cd cinema_client
mvnw.cmd spring-boot:run
```

Truy cap web tai `http://localhost:8081`.

## Build jar

### Backend
```bash
cd cinema_back_end-master
mvnw.cmd clean package
```

### Client
```bash
cd cinema_client
mvnw.cmd clean package
```

## Ghi chu
- Profile mac dinh cua backend la `local` (`spring.profiles.active=local`).
- Thu muc build (`target`) va file moi truong nhay cam (`.env`, `local.env`) da duoc ignore trong Git.
