# ShopTamDV

## Backend

```bash
cd shop
mvn clean package
mvn spring-boot:run
```

Swagger: <http://localhost:8080/swagger-ui.html>

H2 console: <http://localhost:8080/h2-console> (jdbc:h2:mem:testdb)

## Frontend

```bash
cd frontend
cp .env.local.example .env.local
npm install
npm run dev
```

App: <http://localhost:3000>
