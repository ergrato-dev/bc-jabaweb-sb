# 🎁 Bonus: Integración Frontend + Backend

## Descripción

Este módulo **bonus** proporciona ejercicios progresivos para integrar un frontend con la API REST desarrollada en el bootcamp.

> ⚠️ **Nota**: Este contenido es **complementario** y está diseñado para estudiantes que deseen profundizar en la integración full-stack. No es requisito para completar el bootcamp, pero es **altamente recomendado** para quienes trabajen en proyectos formativos que requieran frontend.

---

## Estructura Progresiva

| Semana | Carpeta | Contenido | Prerrequisito |
|--------|---------|-----------|---------------|
| 6 | `week-06-cors-basics/` | HTML + JavaScript (fetch) + CORS | Swagger funcionando |
| 7 | `week-07-react-auth/` | React + Formularios Login/Registro | Spring Security + JWT |
| 8 | `week-08-full-stack-auth/` | Stack completo con Docker | Todo lo anterior |

---

## Objetivos

### Week 06 - CORS Basics
- Entender qué es CORS y por qué existe
- Consumir API REST desde JavaScript vanilla
- Verificar configuración CORS en Spring Boot

### Week 07 - React Auth
- Crear formularios de Login y Registro en React
- Manejar JWT en el frontend (almacenamiento, envío)
- Proteger rutas en React

### Week 08 - Full Stack Auth
- Integrar Spring Boot + PostgreSQL + React en Docker
- Flujo completo de autenticación
- Ejercicio adaptable al proyecto formativo

---

## Requisitos

- Conocimientos de HTML, CSS y JavaScript básico
- Formación paralela en React (proporcionada en otra clase)
- API REST funcional con Spring Boot

### Gestor de Paquetes

> ⚡ **Importante**: En el bootcamp usamos **pnpm** o **yarn** por rendimiento. **No usamos npm**.

| Gestor | Instalación | Por qué |
|--------|-------------|---------|
| **pnpm** (recomendado) | `corepack enable && corepack prepare pnpm@latest --activate` | Más rápido, usa menos disco |
| **yarn** (alternativa) | `corepack enable && corepack prepare yarn@stable --activate` | Ampliamente usado en la industria |

```bash
# Verificar instalación
pnpm --version
# o
yarn --version
```

---

## Cómo Usar Este Material

1. **Completa primero** el contenido principal de cada semana
2. **Verifica** que tu API funciona con Swagger/Postman
3. **Sigue** los ejercicios del bonus en orden
4. **Adapta** el código final a tu proyecto formativo

---

## Relación con Proyecto Formativo

El ejercicio de **Week 08** está diseñado específicamente para que puedas adaptarlo a tu proyecto formativo que requiere:

- ✅ Registro de usuarios
- ✅ Login
- ✅ Recuperación de contraseña (básico)
- ✅ Caso de uso de negocio

---

## Estructura de Archivos

```
bonus-frontend/
├── README.md                      # Este archivo
├── week-06-cors-basics/
│   ├── README.md
│   ├── index.html                 # HTML + fetch básico
│   └── api-client.js              # Cliente JavaScript
├── week-07-react-auth/
│   ├── README.md
│   ├── package.json
│   ├── src/
│   │   ├── components/
│   │   │   ├── LoginForm.jsx
│   │   │   └── RegisterForm.jsx
│   │   ├── services/
│   │   │   └── authService.js
│   │   └── App.jsx
│   └── Dockerfile
└── week-08-full-stack-auth/
    ├── README.md
    ├── docker-compose.yml         # Spring Boot + PostgreSQL + React
    ├── backend/                   # API con Spring Security + JWT
    ├── frontend/                  # React app completa
    └── docs/
        └── ADAPTATION-GUIDE.md    # Guía para adaptar a tu proyecto
```

---

## 💡 Tip

> Este bonus está diseñado para que sientas que tu esfuerzo extra es reconocido. Completarlo te dará una ventaja significativa en tu proyecto formativo y en tu preparación profesional.

¡Buena suerte! 🚀
