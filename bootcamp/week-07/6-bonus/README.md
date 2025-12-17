# 🎁 Bonus Week 07: React Auth

## Objetivo

Crear formularios de Login y Registro en React que se conecten con tu API Spring Boot con JWT.

**Duración estimada**: 60-90 minutos

> 💡 **Este es contenido bonus** - Diseñado para estudiantes que deseen profundizar en la integración frontend-backend. No es requisito para completar el bootcamp.

**Prerrequisitos**:
- Conocimientos básicos de React (componentes, useState, useEffect)
- API con Spring Security + JWT funcionando (contenido principal de esta semana)
- CORS configurado (bonus Week 06)

---

## Parte 1: Crear Proyecto React

### 1.1 Inicializar con Vite

> ⚡ **Nota**: En el bootcamp usamos **pnpm** o **yarn** por rendimiento. No usamos npm.

```bash
# Opción 1: Con pnpm (recomendado)
pnpm create vite@latest auth-frontend -- --template react
cd auth-frontend
pnpm install
pnpm add axios

# Opción 2: Con yarn
yarn create vite auth-frontend --template react
cd auth-frontend
yarn
yarn add axios
```

> 📦 **¿No tienes pnpm?** Instálalo con: `corepack enable && corepack prepare pnpm@latest --activate`

### 1.2 Estructura de Carpetas

```
auth-frontend/
├── src/
│   ├── components/
│   │   ├── LoginForm.jsx
│   │   ├── RegisterForm.jsx
│   │   └── ProtectedRoute.jsx
│   ├── services/
│   │   └── authService.js
│   ├── context/
│   │   └── AuthContext.jsx
│   ├── App.jsx
│   └── main.jsx
├── package.json
└── vite.config.js
```

---

## Parte 2: Servicio de Autenticación

### 2.1 Crear `src/services/authService.js`

```javascript
import axios from 'axios';

const API_URL = 'http://localhost:8080/api/auth';

// Configurar axios para incluir el token en todas las peticiones
axios.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export const authService = {
  // Registro de usuario
  async register(userData) {
    const response = await axios.post(`${API_URL}/register`, userData);
    return response.data;
  },

  // Login
  async login(credentials) {
    const response = await axios.post(`${API_URL}/login`, credentials);
    if (response.data.token) {
      localStorage.setItem('token', response.data.token);
      localStorage.setItem('user', JSON.stringify(response.data.user));
    }
    return response.data;
  },

  // Logout
  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  },

  // Obtener usuario actual
  getCurrentUser() {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
  },

  // Verificar si está autenticado
  isAuthenticated() {
    return !!localStorage.getItem('token');
  },

  // Obtener token
  getToken() {
    return localStorage.getItem('token');
  }
};
```

---

## Parte 3: Contexto de Autenticación

### 3.1 Crear `src/context/AuthContext.jsx`

```jsx
import { createContext, useState, useContext, useEffect } from 'react';
import { authService } from '../services/authService';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Verificar si hay usuario al cargar
    const currentUser = authService.getCurrentUser();
    if (currentUser) {
      setUser(currentUser);
    }
    setLoading(false);
  }, []);

  const login = async (credentials) => {
    const data = await authService.login(credentials);
    setUser(data.user);
    return data;
  };

  const register = async (userData) => {
    const data = await authService.register(userData);
    return data;
  };

  const logout = () => {
    authService.logout();
    setUser(null);
  };

  const value = {
    user,
    login,
    register,
    logout,
    isAuthenticated: !!user,
    loading
  };

  return (
    <AuthContext.Provider value={value}>
      {!loading && children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth debe usarse dentro de AuthProvider');
  }
  return context;
}
```

---

## Parte 4: Componentes de Formularios

### 4.1 Crear `src/components/LoginForm.jsx`

```jsx
import { useState } from 'react';
import { useAuth } from '../context/AuthContext';

export function LoginForm({ onSuccess, onSwitchToRegister }) {
  const { login } = useAuth();
  const [formData, setFormData] = useState({
    email: '',
    password: ''
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      await login(formData);
      onSuccess?.();
    } catch (err) {
      setError(err.response?.data?.message || 'Error al iniciar sesión');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-form">
      <h2>Iniciar Sesión</h2>

      {error && <div className="error-message">{error}</div>}

      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="email">Email</label>
          <input
            type="email"
            id="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            required
            placeholder="tu@email.com"
          />
        </div>

        <div className="form-group">
          <label htmlFor="password">Contraseña</label>
          <input
            type="password"
            id="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            required
            placeholder="••••••••"
          />
        </div>

        <button type="submit" disabled={loading}>
          {loading ? 'Cargando...' : 'Iniciar Sesión'}
        </button>
      </form>

      <p className="switch-form">
        ¿No tienes cuenta?{' '}
        <button type="button" onClick={onSwitchToRegister}>
          Regístrate
        </button>
      </p>
    </div>
  );
}
```

### 4.2 Crear `src/components/RegisterForm.jsx`

```jsx
import { useState } from 'react';
import { useAuth } from '../context/AuthContext';

export function RegisterForm({ onSuccess, onSwitchToLogin }) {
  const { register } = useAuth();
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
    confirmPassword: ''
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    // Validar contraseñas
    if (formData.password !== formData.confirmPassword) {
      setError('Las contraseñas no coinciden');
      return;
    }

    if (formData.password.length < 6) {
      setError('La contraseña debe tener al menos 6 caracteres');
      return;
    }

    setLoading(true);

    try {
      await register({
        name: formData.name,
        email: formData.email,
        password: formData.password
      });
      onSuccess?.();
    } catch (err) {
      setError(err.response?.data?.message || 'Error al registrar');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-form">
      <h2>Crear Cuenta</h2>

      {error && <div className="error-message">{error}</div>}

      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="name">Nombre</label>
          <input
            type="text"
            id="name"
            name="name"
            value={formData.name}
            onChange={handleChange}
            required
            placeholder="Tu nombre"
          />
        </div>

        <div className="form-group">
          <label htmlFor="email">Email</label>
          <input
            type="email"
            id="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            required
            placeholder="tu@email.com"
          />
        </div>

        <div className="form-group">
          <label htmlFor="password">Contraseña</label>
          <input
            type="password"
            id="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            required
            placeholder="Mínimo 6 caracteres"
          />
        </div>

        <div className="form-group">
          <label htmlFor="confirmPassword">Confirmar Contraseña</label>
          <input
            type="password"
            id="confirmPassword"
            name="confirmPassword"
            value={formData.confirmPassword}
            onChange={handleChange}
            required
            placeholder="Repite la contraseña"
          />
        </div>

        <button type="submit" disabled={loading}>
          {loading ? 'Cargando...' : 'Registrarse'}
        </button>
      </form>

      <p className="switch-form">
        ¿Ya tienes cuenta?{' '}
        <button type="button" onClick={onSwitchToLogin}>
          Inicia Sesión
        </button>
      </p>
    </div>
  );
}
```

---

## Parte 5: App Principal

### 5.1 Actualizar `src/App.jsx`

```jsx
import { useState } from 'react';
import { AuthProvider, useAuth } from './context/AuthContext';
import { LoginForm } from './components/LoginForm';
import { RegisterForm } from './components/RegisterForm';
import './App.css';

function AuthenticatedApp() {
  const { user, logout } = useAuth();

  return (
    <div className="dashboard">
      <h1>¡Bienvenido, {user?.name}!</h1>
      <p>Email: {user?.email}</p>
      <button onClick={logout} className="logout-btn">
        Cerrar Sesión
      </button>
    </div>
  );
}

function UnauthenticatedApp() {
  const [showLogin, setShowLogin] = useState(true);

  const handleRegisterSuccess = () => {
    setShowLogin(true);
    alert('¡Registro exitoso! Ahora puedes iniciar sesión.');
  };

  return (
    <div className="auth-container">
      {showLogin ? (
        <LoginForm
          onSwitchToRegister={() => setShowLogin(false)}
        />
      ) : (
        <RegisterForm
          onSuccess={handleRegisterSuccess}
          onSwitchToLogin={() => setShowLogin(true)}
        />
      )}
    </div>
  );
}

function AppContent() {
  const { isAuthenticated } = useAuth();

  return isAuthenticated ? <AuthenticatedApp /> : <UnauthenticatedApp />;
}

function App() {
  return (
    <AuthProvider>
      <div className="app">
        <AppContent />
      </div>
    </AuthProvider>
  );
}

export default App;
```

### 5.2 Crear `src/App.css`

```css
* {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
}

body {
  font-family: 'Segoe UI', sans-serif;
  background: #1e1e1e;
  color: #d4d4d4;
  min-height: 100vh;
}

.app {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  padding: 20px;
}

.auth-container {
  width: 100%;
  max-width: 400px;
}

.auth-form {
  background: #2d2d2d;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.3);
}

.auth-form h2 {
  color: #4fc3f7;
  margin-bottom: 20px;
  text-align: center;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  color: #9e9e9e;
  font-size: 14px;
}

.form-group input {
  width: 100%;
  padding: 12px;
  border: 1px solid #444;
  border-radius: 6px;
  background: #1e1e1e;
  color: #d4d4d4;
  font-size: 16px;
}

.form-group input:focus {
  outline: none;
  border-color: #4fc3f7;
}

button[type="submit"] {
  width: 100%;
  padding: 12px;
  background: #0d47a1;
  color: white;
  border: none;
  border-radius: 6px;
  font-size: 16px;
  cursor: pointer;
  margin-top: 10px;
}

button[type="submit"]:hover {
  background: #1565c0;
}

button[type="submit"]:disabled {
  background: #555;
  cursor: not-allowed;
}

.error-message {
  background: #f44336;
  color: white;
  padding: 10px;
  border-radius: 6px;
  margin-bottom: 15px;
  text-align: center;
}

.switch-form {
  text-align: center;
  margin-top: 20px;
  color: #9e9e9e;
}

.switch-form button {
  background: none;
  border: none;
  color: #4fc3f7;
  cursor: pointer;
  text-decoration: underline;
}

.dashboard {
  text-align: center;
  background: #2d2d2d;
  padding: 40px;
  border-radius: 12px;
}

.dashboard h1 {
  color: #4fc3f7;
  margin-bottom: 10px;
}

.logout-btn {
  margin-top: 20px;
  padding: 10px 30px;
  background: #f44336;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

.logout-btn:hover {
  background: #d32f2f;
}
```

---

## Parte 6: Ejecutar

### 6.1 Asegurar que la API está corriendo

```bash
# En el directorio del proyecto de la semana
cd ../3-proyecto
docker compose up
```

### 6.2 Iniciar React

```bash
cd auth-frontend

# Con pnpm
pnpm dev

# Con yarn
yarn dev
```

Abre http://localhost:5173

---

## ✅ Checklist

- [ ] Proyecto React creado con Vite
- [ ] `authService.js` implementado
- [ ] `AuthContext.jsx` implementado
- [ ] `LoginForm.jsx` funcionando
- [ ] `RegisterForm.jsx` funcionando
- [ ] Token se guarda en localStorage
- [ ] Token se envía en headers
- [ ] Logout limpia el estado

---

## 🔒 Consideraciones de Seguridad

> ⚠️ **localStorage vs httpOnly cookies**

En este ejercicio usamos `localStorage` para simplicidad. En producción, considera usar **httpOnly cookies** para mayor seguridad contra ataques XSS.

---

## ➡️ Siguiente

En la **Semana 08** encontrarás el bonus de integración full-stack con Docker, donde unirás backend + frontend + base de datos en un solo `docker-compose.yml`.
