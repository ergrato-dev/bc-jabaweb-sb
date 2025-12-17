# Práctica 01: Primeros Pasos con Docker

## 🎯 Objetivos

- Instalar y verificar Docker
- Ejecutar contenedores básicos
- Trabajar con imágenes
- Usar volúmenes para desarrollo
- Ejecutar Java en contenedor

---

## Ejercicio 1: Verificar Instalación

### 1.1 Comprobar versiones

```bash
# Verificar Docker
docker --version

# Verificar Docker Compose
docker compose version

# Información del sistema
docker info
```

**✅ Resultado esperado**: Versiones de Docker y Docker Compose sin errores.

### 1.2 Tu primer contenedor

```bash
# Ejecutar hello-world
docker run hello-world
```

**✅ Resultado esperado**: Mensaje "Hello from Docker!" y explicación del proceso.

### 1.3 Entender qué pasó

```bash
# Ver contenedores (ninguno en ejecución)
docker ps

# Ver todos los contenedores (incluido el detenido)
docker ps -a

# Ver imágenes descargadas
docker images
```

> 💡 El contenedor `hello-world` se ejecutó y terminó inmediatamente.

---

## Ejercicio 2: Trabajar con Imágenes

### 2.1 Descargar imágenes

```bash
# Descargar imagen de Java 21
docker pull eclipse-temurin:21-jdk

# Descargar imagen de Alpine Linux (muy pequeña)
docker pull alpine:latest

# Ver imágenes descargadas
docker images
```

### 2.2 Inspeccionar imagen

```bash
# Ver detalles de la imagen
docker inspect eclipse-temurin:21-jdk

# Ver historial de capas
docker history eclipse-temurin:21-jdk
```

### 2.3 Comparar tamaños

```bash
# Listar imágenes con tamaños
docker images --format "table {{.Repository}}\t{{.Tag}}\t{{.Size}}"
```

**Pregunta**: ¿Cuál es la diferencia de tamaño entre `alpine` y `eclipse-temurin:21-jdk`? ¿Por qué?

---

## Ejercicio 3: Ejecutar Contenedores

### 3.1 Modo interactivo

```bash
# Ejecutar Alpine en modo interactivo
docker run -it alpine sh

# Dentro del contenedor:
cat /etc/os-release
ls -la
exit
```

### 3.2 Ejecutar Java

```bash
# Verificar versión de Java
docker run eclipse-temurin:21-jdk java --version

# Ejecutar comando Java
docker run eclipse-temurin:21-jdk java -version
```

### 3.3 Modo interactivo con Java

```bash
# Entrar al contenedor con bash
docker run -it eclipse-temurin:21-jdk bash

# Dentro del contenedor:
java --version
echo $JAVA_HOME
which java
exit
```

### 3.4 Contenedor con nombre

```bash
# Ejecutar con nombre personalizado
docker run --name mi-java -it eclipse-temurin:21-jdk bash

# En otra terminal, ver el contenedor
docker ps

# Detener
docker stop mi-java

# Iniciar de nuevo
docker start -i mi-java

# Eliminar
docker rm mi-java
```

---

## Ejercicio 4: Volúmenes - Sincronizar Código

### 4.1 Preparar código local

```bash
# Crear directorio de trabajo
mkdir -p ~/docker-practice/java
cd ~/docker-practice/java

# Crear archivo Java
cat > HelloDocker.java << 'EOF'
public class HelloDocker {
    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("  Hello from Docker Container!");
        System.out.println("  Java Version: " + System.getProperty("java.version"));
        System.out.println("  OS: " + System.getProperty("os.name"));
        System.out.println("=================================");
    }
}
EOF
```

### 4.2 Compilar en contenedor

```bash
# Compilar usando contenedor
docker run --rm \
  -v $(pwd):/app \
  -w /app \
  eclipse-temurin:21-jdk \
  javac HelloDocker.java

# Verificar que se creó el .class
ls -la
```

**Explicación del comando**:
- `--rm`: Elimina el contenedor al terminar
- `-v $(pwd):/app`: Monta el directorio actual en `/app`
- `-w /app`: Establece `/app` como directorio de trabajo
- `javac HelloDocker.java`: Comando a ejecutar

### 4.3 Ejecutar en contenedor

```bash
# Ejecutar el programa compilado
docker run --rm \
  -v $(pwd):/app \
  -w /app \
  eclipse-temurin:21-jdk \
  java HelloDocker
```

### 4.4 Compilar y ejecutar en un solo comando

```bash
docker run --rm \
  -v $(pwd):/app \
  -w /app \
  eclipse-temurin:21-jdk \
  sh -c "javac HelloDocker.java && java HelloDocker"
```

---

## Ejercicio 5: Programa Interactivo

### 5.1 Crear programa con input

```bash
cat > Greeting.java << 'EOF'
import java.util.Scanner;

public class Greeting {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("¿Cuál es tu nombre? ");
        String name = scanner.nextLine();

        System.out.print("¿Cuál es tu lenguaje favorito? ");
        String language = scanner.nextLine();

        System.out.println();
        System.out.println("¡Hola " + name + "!");
        System.out.println("¡" + language + " es genial!");
        System.out.println("Ejecutándose en Docker con Java " +
                          System.getProperty("java.version"));

        scanner.close();
    }
}
EOF
```

### 5.2 Ejecutar programa interactivo

```bash
# Compilar
docker run --rm -v $(pwd):/app -w /app eclipse-temurin:21-jdk javac Greeting.java

# Ejecutar con modo interactivo (-it)
docker run --rm -it -v $(pwd):/app -w /app eclipse-temurin:21-jdk java Greeting
```

> ⚠️ Sin `-it`, el programa no podrá leer input del usuario.

---

## Ejercicio 6: Gestión de Contenedores

### 6.1 Crear contenedor persistente

```bash
# Crear contenedor sin iniciarlo
docker create --name java-dev \
  -v $(pwd):/app \
  -w /app \
  -it \
  eclipse-temurin:21-jdk bash

# Ver contenedor (estado: Created)
docker ps -a

# Iniciar contenedor
docker start -i java-dev

# Dentro del contenedor, compila y ejecuta
javac HelloDocker.java
java HelloDocker
exit

# El contenedor sigue existiendo (estado: Exited)
docker ps -a
```

### 6.2 Ejecutar comandos en contenedor existente

```bash
# Iniciar el contenedor en background
docker start java-dev

# Ejecutar comando sin entrar
docker exec java-dev java --version

# Entrar al contenedor en ejecución
docker exec -it java-dev bash

# Hacer algo y salir
ls -la
exit

# Detener contenedor
docker stop java-dev
```

### 6.3 Limpiar recursos

```bash
# Eliminar contenedor específico
docker rm java-dev

# Eliminar contenedores detenidos
docker container prune

# Eliminar imágenes no usadas
docker image prune

# Ver uso de disco
docker system df
```

---

## Ejercicio 7: Reto Final

### 7.1 Calculadora Simple

Crea un programa `Calculator.java` que:
1. Pida dos números al usuario
2. Pida la operación (+, -, *, /)
3. Muestre el resultado
4. Maneje errores (división por cero, input inválido)

```bash
# Crea el archivo Calculator.java
# (Implementa tu solución)

# Compila
docker run --rm -v $(pwd):/app -w /app eclipse-temurin:21-jdk javac Calculator.java

# Ejecuta
docker run --rm -it -v $(pwd):/app -w /app eclipse-temurin:21-jdk java Calculator
```

<details>
<summary>💡 Ver solución</summary>

```java
import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Ingresa el primer número: ");
            double num1 = Double.parseDouble(scanner.nextLine());

            System.out.print("Ingresa el segundo número: ");
            double num2 = Double.parseDouble(scanner.nextLine());

            System.out.print("Ingresa la operación (+, -, *, /): ");
            String operation = scanner.nextLine();

            double result;
            switch (operation) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 == 0) {
                        System.out.println("Error: División por cero");
                        return;
                    }
                    result = num1 / num2;
                    break;
                default:
                    System.out.println("Error: Operación no válida");
                    return;
            }

            System.out.printf("Resultado: %.2f %s %.2f = %.2f%n",
                            num1, operation, num2, result);

        } catch (NumberFormatException e) {
            System.out.println("Error: Ingresa números válidos");
        } finally {
            scanner.close();
        }
    }
}
```

</details>

---

## ✅ Checklist de la Práctica

- [ ] Docker está instalado y funcionando
- [ ] Puedo descargar imágenes con `docker pull`
- [ ] Puedo ejecutar contenedores en modo interactivo
- [ ] Sé usar volúmenes para montar código local
- [ ] Puedo compilar y ejecutar Java en contenedor
- [ ] Entiendo la diferencia entre `docker run`, `docker start`, `docker exec`
- [ ] Sé limpiar contenedores e imágenes no usados

---

## 🔧 Troubleshooting

| Problema | Solución |
|----------|----------|
| `permission denied` | Agregar usuario al grupo docker: `sudo usermod -aG docker $USER` |
| `Cannot connect to Docker daemon` | Iniciar Docker Desktop o servicio docker |
| Contenedor se cierra inmediatamente | Usar `-it` para modo interactivo |
| Cambios en código no se ven | Verificar que el volumen está bien montado |
| `javac: command not found` | Asegurar usar imagen con JDK, no JRE |
