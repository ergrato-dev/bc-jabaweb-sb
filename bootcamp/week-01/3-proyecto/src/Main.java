/**
 * Bootcamp Java Web con Spring Boot
 * Semana 01 - Proyecto Integrador: Entorno Docker
 * 
 * ╔════════════════════════════════════════════════════════════════╗
 * ║  INSTRUCCIONES: Completa los TODOs para crear tu programa     ║
 * ║  que demuestre el uso de Docker y variables de entorno.       ║
 * ╚════════════════════════════════════════════════════════════════╝
 * 
 * OBJETIVOS:
 * 1. Mostrar un banner de bienvenida
 * 2. Leer y mostrar propiedades del sistema (System.getProperty)
 * 3. Leer y mostrar variables de entorno (System.getenv)
 * 4. Procesar argumentos de línea de comandos (args)
 * 
 * PISTAS:
 * - System.getProperty("java.version") → versión de Java
 * - System.getProperty("os.name") → nombre del sistema operativo
 * - System.getenv("NOMBRE_VAR") → valor de variable de entorno
 * - args.length → cantidad de argumentos recibidos
 * - args[i] → argumento en posición i
 * 
 * @author [Tu nombre aquí]
 * @version 1.0.0
 */
public class Main {
    
    public static void main(String[] args) {
        // TODO 1: Llama al método que imprime el banner
        // Pista: printBanner();
        
        // TODO 2: Llama al método que muestra información del sistema
        
        // TODO 3: Llama al método que muestra variables de entorno
        
        // TODO 4: Si hay argumentos (args.length > 0), muéstralos
        // Pista: usa un if y llama a printArguments(args)
        
        // TODO 5: Imprime un mensaje de despedida
        System.out.println("\n✅ Programa completado");
    }
    
    /**
     * TODO 6: Implementa este método para mostrar un banner de bienvenida
     * 
     * Debe imprimir algo como:
     * ════════════════════════════════════════
     *   BOOTCAMP JAVA WEB - SEMANA 01
     *   [Tu nombre]
     * ════════════════════════════════════════
     * 
     * Pista: usa System.out.println() múltiples veces
     */
    private static void printBanner() {
        // Escribe tu código aquí
        
    }
    
    /**
     * TODO 7: Implementa este método para mostrar información del sistema
     * 
     * Debe mostrar al menos:
     * - Versión de Java: System.getProperty("java.version")
     * - Vendor de Java: System.getProperty("java.vendor")
     * - Sistema Operativo: System.getProperty("os.name")
     * - Arquitectura: System.getProperty("os.arch")
     * - Directorio actual: System.getProperty("user.dir")
     * 
     * Formato sugerido:
     *   Java Version : 21.0.1
     *   OS Name      : Linux
     */
    private static void printSystemInfo() {
        System.out.println("\n📋 Información del Sistema");
        System.out.println("─".repeat(40));
        
        // TODO: Obtén e imprime cada propiedad
        // Ejemplo: String javaVersion = System.getProperty("java.version");
        //          System.out.println("   Java Version : " + javaVersion);
        
    }
    
    /**
     * TODO 8: Implementa este método para mostrar variables de entorno
     * 
     * Debe mostrar las variables definidas en .env:
     * - APP_NAME
     * - APP_VERSION  
     * - APP_ENV
     * 
     * IMPORTANTE: System.getenv() puede retornar null si la variable
     * no existe. Maneja ese caso mostrando "(no definida)".
     * 
     * Pista: 
     *   String valor = System.getenv("APP_NAME");
     *   if (valor != null) { ... } else { ... }
     * 
     * O usando el operador ternario:
     *   String resultado = (valor != null) ? valor : "(no definida)";
     */
    private static void printEnvironmentInfo() {
        System.out.println("\n🔧 Variables de Entorno");
        System.out.println("─".repeat(40));
        
        // TODO: Obtén e imprime cada variable de entorno
        
    }
    
    /**
     * TODO 9: Implementa este método para mostrar los argumentos recibidos
     * 
     * @param args los argumentos de línea de comandos
     * 
     * Debe mostrar cada argumento con su índice:
     *   [0] primer_argumento
     *   [1] segundo_argumento
     * 
     * Pista: usa un bucle for
     *   for (int i = 0; i < args.length; i++) {
     *       // args[i] es el argumento en posición i
     *   }
     */
    private static void printArguments(String[] args) {
        System.out.println("\n📝 Argumentos recibidos");
        System.out.println("─".repeat(40));
        
        // TODO: Recorre e imprime cada argumento
        
    }
    
    // ════════════════════════════════════════════════════════════════
    // DESAFÍO EXTRA (opcional):
    // ════════════════════════════════════════════════════════════════
    // 
    // 1. Crea un método "printSeparator()" que imprima una línea
    //    decorativa y úsalo para separar secciones.
    // 
    // 2. Agrega un método que calcule y muestre:
    //    - Memoria total disponible: Runtime.getRuntime().totalMemory()
    //    - Memoria libre: Runtime.getRuntime().freeMemory()
    //    - Procesadores disponibles: Runtime.getRuntime().availableProcessors()
    // 
    // 3. Implementa un método "center(String text, int width)" que
    //    centre un texto en un ancho dado (para hacer banners bonitos).
    // ════════════════════════════════════════════════════════════════
}
