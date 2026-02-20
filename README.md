# 🔐 Spring Boot JWT Authentication API

API REST sécurisée développée avec **Spring Boot 3** et **Spring Security**, implémentant une authentification stateless basée sur **JSON Web Token (JWT)**.

Ce projet démontre une architecture propre et moderne de sécurité backend incluant :

- Authentification via `AuthenticationManager`
- Génération et validation de JWT (HS256)
- Filtrage des requêtes HTTP avec `OncePerRequestFilter`
- Gestion du `SecurityContext`
- Hashing sécurisé des mots de passe avec BCrypt
- Configuration moderne via `SecurityFilterChain`

---

## 📌 Objectif

Mettre en place un système d’authentification robuste, stateless et conforme aux bonnes pratiques Spring Security, adapté aux applications REST modernes.

---

## 🏗 Architecture technique

```
Client
   │
   ▼
AuthController
   │
   ▼
AuthenticationManager
   │
   ▼
CustomUserDetailsService
   │
   ▼
Database (User)
   │
   ▼
JWT Generation
   │
   ▼
JwtFilter (Validation)
   │
   ▼
SecurityContext
   │
   ▼
Protected Controllers
```

---

## 🛠 Stack technique

| Technologie | Version |
|------------|----------|
| Java | 17+ |
| Spring Boot | 3.x |
| Spring Security | 6.x |
| JPA / Hibernate | ✔ |
| MySQL | ✔ |
| JWT | io.jsonwebtoken (jjwt) |
| Maven | ✔ |
| Lombok | ✔ |

---

## 🔐 Fonctionnement de l’authentification

### 1️⃣ Authentification (Login)

- L'utilisateur envoie `username` + `password`
- `AuthenticationManager` valide les credentials
- `CustomUserDetailsService` charge l'utilisateur
- `PasswordEncoder` vérifie le hash BCrypt
- Un JWT signé (HS256) est généré
- Le token est retourné au client

### 2️⃣ Accès aux routes protégées

- Le client envoie le JWT dans le header HTTP :

```
Authorization: Bearer <token>
```

- `JwtFilter` intercepte la requête
- Le token est validé
- L'utilisateur est injecté dans le `SecurityContext`
- L'accès au contrôleur est autorisé

---

## ⚙️ Configuration

### 🔹 application.properties

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/crudspringboot
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

app.secret-key=your-super-secret-key-at-least-256-bits-long
app.jwt-expiration=3600000
```

⚠️ En production :
- Ne jamais exposer la clé secrète
- Utiliser des variables d’environnement
- Utiliser une clé encodée Base64 d’au moins 256 bits

---

## 🚀 Installation

### 1️⃣ Cloner le projet

```bash
git clone https://github.com/your-username/your-repository.git
cd your-repository
```

### 2️⃣ Créer la base de données

```sql
CREATE DATABASE crudspringboot;
```

### 3️⃣ Lancer l’application

```bash
mvn clean install
mvn spring-boot:run
```

Application accessible sur :

```
http://localhost:8080
```

---

## 📌 Endpoints

### 🔓 Public

| Méthode | Endpoint | Description |
|----------|----------|-------------|
| POST | /api/auth/register | Créer un utilisateur |
| POST | /api/auth/login | Authentification |

### 🔒 Protégés

Toutes les routes sauf `/api/auth/**` nécessitent un JWT valide.

---

## 📦 Exemple de requête Login

```json
POST /api/auth/login
{
  "username": "john",
  "password": "123456"
}
```

Réponse :

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

---

## 🧠 Concepts Spring Security implémentés

- `SecurityFilterChain`
- `AuthenticationManager`
- `UserDetailsService`
- `OncePerRequestFilter`
- `SecurityContextHolder`
- `UsernamePasswordAuthenticationToken`
- BCrypt Password Hashing
- Stateless Session Policy

---

## 🔍 Sécurité

✔ Authentification stateless  
✔ Signature HMAC SHA-256  
✔ Expiration configurable  
✔ Password hashing sécurisé  
✔ Injection contrôlée dans le SecurityContext  

---

## 🚧 Améliorations futures

- Refresh Token sécurisé
- Rotation de tokens
- Blacklist JWT
- Gestion centralisée des erreurs (401/403)
- Dockerisation
- Déploiement cloud (Render / Railway / AWS)

---

## 🧪 Tests

Peut être testé via :

- Postman
- Insomnia
- Curl

---

## 📄 Licence

Projet à but pédagogique et démonstratif.

---

## 👨‍💻 Auteur

Projet développé dans le cadre d’un apprentissage avancé de Spring Security et des mécanismes d’authentification JWT.

---
