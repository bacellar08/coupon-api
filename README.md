# Coupon API

API para gestão de cupons de desconto, desenvolvida em Java 21 com Spring Boot.

---

## Endpoints

- **POST /v1/coupon** → cria um cupom
- **GET /v1/coupon/{id}** → busca um cupom pelo ID
- **DELETE /v1/coupon/{id}** → deleta um cupom

---

## Como rodar

### Utilize docker compose

`docker compose up --build`

- A API estará disponível em: http://localhost:8080  
- Swagger UI: http://localhost:8080/swagger-ui.html  
- H2 Console: http://localhost:8080/h2-console
