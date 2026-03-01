package Client;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class Client {

    public static void main(String[] args) throws Exception {

        String param = "Ein Test";

        // Make sure path is correct relative to runtime
        File serverFile = new File("../Server/build/libs/Server.jar");
        if (!serverFile.exists()) {
            throw new RuntimeException("Server.jar not found: " + serverFile.getAbsolutePath());
        }

        URLClassLoader loader = new URLClassLoader(
                new URL[]{serverFile.toURI().toURL()},
                Client.class.getClassLoader()
        );

        // Fully qualified server class
        Class<?> clazz = loader.loadClass("Services.Server");

        // Create instance
        Object instance = clazz.getDeclaredConstructor().newInstance();

        // Get the method
        Method method = clazz.getMethod("test", String.class);

        // Invoke
        method.invoke(instance, param);
    }
}