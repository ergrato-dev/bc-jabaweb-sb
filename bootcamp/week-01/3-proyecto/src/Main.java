/**
 * Bootcamp Java Web con Spring Boot
 * Semana 01 - Proyecto: Entorno Docker
 * 
 * Este programa demuestra:
 * - Ejecución de Java en contenedores Docker
 * - Lectura de variables de entorno
 * - Información del sistema
 * 
 * @author Bootcamp Java Web
 * @version 1.0.0
 */
public class Main {
    
    // Constantes para formato de salida
    private static final String LINE = "═".repeat(50);
    private static final String THIN_LINE = "─".repeat(50);
    
    public static void main(String[] args) {
        printBanner();
        printSystemInfo();
        printEnvironmentInfo();
        
        if (args.length > 0) {
            printArguments(args);
        }
        
        printFooter();
    }
    
    /**
     * Imprime el banner de bienvenida
     */
    private static void printBanner() {
        System.out.println();
        System.out.println("╔" + LINE + "╗");
        System.out.println("║" + center("BOOTCAMP JAVA WEB", 50) + "║");
        System.out.println("║" + center("con Spring Boot", 50) + "║");
        System.out.println("╠" + LINE + "╣");
        System.out.println("║" + center("Semana 01: Docker + Fundamentos REST", 50) + "║");
        System.out.println("╚" + LINE + "╝");
        System.out.println();
    }
    
    /**
     * Imprime información del sistema Java
     */
    private static void printSystemInfo() {
        System.out.println("📋 Información del Sistema");
        System.out.println(THIN_LINE);
        printProperty("Java Version", "java.version");
        printProperty("Java Vendor", "java.vendor");
        printProperty("Java Home", "java.home");
        printProperty("OS Name", "os.name");
        printProperty("OS Version", "os.version");
        printProperty("OS Arch", "os.arch");
        printProperty("User Name", "user.name");
        printProperty("User Dir", "user.dir");
        System.out.println();
    }
    
    /**
     * Imprime las variables de entorno configuradas
     */
    private static void printEnvironmentInfo() {
        System.out.println("🔧 Variables de Entorno");
        System.out.println(THIN_LINE);
        printEnvVar("APP_NAME", "(no configurada)");
        printEnvVar("APP_VERSION", "0.0.0");
        printEnvVar("APP_ENV", "unknown");
        printEnvVar("JAVA_OPTS", "(por defecto)");
        System.out.println();
    }
    
    /**
     * Imprime los argumentos de línea de comandos
     * @param args argumentos recibidos
     */
    private static void printArguments(String[] args) {
        System.out.println("📝 Argumentos de Línea de Comandos");
        System.out.println(THIN_LINE);
        for (int i = 0; i < args.length; i++) {
            System.out.printf("   [%d] %s%n", i, args[i]);
        }
        System.out.println();
    }
    
    /**
     * Imprime el pie del programa
     */
    private static void printFooter() {
        System.out.println(THIN_LINE);
        System.out.println("✅ Entorno Docker configurado correctamente");
        System.out.println("🚀 ¡Listo para comenzar el bootcamp!");
        System.out.println();
    }
    
    // ==========================================
    // Métodos auxiliares
    // ==========================================
    
    /**
     * Imprime una propiedad del sistema
     */
    private static void printProperty(String label, String propertyName) {
        String value = System.getProperty(propertyName, "(no disponible)");
        System.out.printf("   %-12s : %s%n", label, value);
    }
    
    /**
     * Imprime una variable de entorno
     */
    private static void printEnvVar(String name, String defaultValue) {
        String value = System.getenv(name);
        System.out.printf("   %-12s : %s%n", name, value != null ? value : defaultValue);
    }
    
    /**
     * Centra un texto en un ancho dado
     */
    private static String center(String text, int width) {
        if (text.length() >= width) {
            return text.substring(0, width);
        }
        int padding = (width - text.length()) / 2;
        return " ".repeat(padding) + text + " ".repeat(width - text.length() - padding);
    }
}
