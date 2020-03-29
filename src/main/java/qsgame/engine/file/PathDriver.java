package qsgame.engine.file;

public class PathDriver {
    private static PathDriver pathDriverInstance = null;

    private PathDriver() {
    }

    public static PathDriver getInstance() {
        if (pathDriverInstance == null) {
            synchronized (PathDriver.class) {
                if (pathDriverInstance == null) {
                    pathDriverInstance = new PathDriver();
                }
            }
        }
        return pathDriverInstance;
    }

    public String getPath(String path) {
        ClassLoader loader = getClass().getClassLoader();
        Object object = loader.getResource(path);
        if (object != null) {
            return object.toString().replaceAll("%20", " ");
        } else {
            return "";
        }
    }
}
