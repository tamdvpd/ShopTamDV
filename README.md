# TamDvShop

TamDvShop là ứng dụng thương mại điện tử gồm backend Spring Boot và frontend Next.js. README này mô tả nhanh kiến trúc, các bước khởi chạy backend và lưu ý cấu trúc theo chuẩn doanh nghiệp.

## Kiến trúc & cấu trúc mã nguồn
- **Backend (shop/):** Spring Boot + Spring Security, JWT, H2 (dev), quản lý theo mô-đun chức năng: `auth/`, `user/`, `product/`, `category/`, `cart/`, `order/`, `admin/`, `repository/`, `security/`, `config/` và `common/`. Mỗi mô-đun chứa controller/service/DTO/domain tách biệt, phù hợp mở rộng và kiểm thử.
- **Frontend (frontend/):** Next.js kết nối REST API, cấu hình qua `.env.local`.
- **Build & CI:** sử dụng Maven Wrapper (`./mvnw`) để đồng nhất môi trường; có thể cắm thêm pipeline kiểm thử/sonar dễ dàng nhờ cấu trúc thư mục chuẩn.

## Yêu cầu hệ thống
- Java 17+, Maven Wrapper (đã kèm repo).
- Node.js 18+ cho frontend (tùy chọn nếu cần chạy UI).

## Chạy backend (development)
1. Cài đặt phụ thuộc và build:
   ```bash
   cd shop
   ./mvnw clean package
   ```
2. Khởi động ứng dụng:
   ```bash
   ./mvnw spring-boot:run
   ```
3. Truy cập:
   - Swagger UI: <http://localhost:8080/swagger-ui.html>
   - H2 Console (dev): <http://localhost:8080/h2-console> với JDBC URL `jdbc:h2:mem:testdb`.

### Ghi chú vận hành chuẩn doanh nghiệp
- Tách riêng cấu hình môi trường qua `application-*.yml`/biến môi trường; không hard-code secret.
- Sử dụng profile `dev` (H2), `prod` (RDBMS thực) để triển khai CI/CD.
- Tài liệu API nên đồng bộ qua Swagger/OpenAPI và kiểm thử tự động bằng Maven `test` trong pipeline.
- Đặt log, audit, và quyền truy cập trong `security/` rõ ràng; mỗi module nên có lớp service và repository riêng để tránh phụ thuộc chéo.

## Chạy frontend (tùy chọn)
```bash
cd frontend
cp .env.local.example .env.local
npm install
npm run dev
```
Ứng dụng frontend chạy tại <http://localhost:3000>.

## Liên hệ & đóng góp
- Tuân thủ cấu trúc mô-đun hiện có khi thêm tính năng.
- Viết test cho service/repository mới; dùng `./mvnw test` trước khi mở PR.
