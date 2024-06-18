import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class FileSystem {
    public static void listDirectory(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        if (files == null) {
            System.err.println("No such file or directory");
            return;
        }
        for (File value : files) {
            System.out.print(value.getName() + " ");
        }
        System.out.println();
    }

    public static void listPythonFiles(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        if (files == null) {
            System.err.println("No such file or directory");
            return;
        }
        for (File value : files) {
            if (value.isFile() && value.getName().endsWith(".py")) {
                System.out.print(value.getName() + " ");
            }
        }
        System.out.println();
    }

    public static void isDirectory(String path) {
        File file = new File(path);
        if (!file.exists()) {
            System.err.println("Directory does not exist");
            return;
        }
        if (file.isDirectory()) {
            System.out.println("true");
        }else {
            System.out.println("false");
        }
    }

    public static void define(String path) {
        File file = new File(path);
        if (!file.exists()) {
            System.err.println("Directory does not exist");
            return;
        }
        if (file.isDirectory()) {
            System.out.println("Директория");
        }else {
            System.out.println("Файл");
        }
    }

    public static void printPermissions(String substring) {
        File file = new File(substring);
        if (!file.exists()) {
            System.err.println("Directory or file does not exist");
            return;
        }
        String response = "";
        if(file.canRead()) {
            response += "r";
        }else {
            response += "-";
        }
        if(file.canWrite()) {
            response += "w";
        }else {
            response += "-";
        }
        if(file.canExecute()) {
            response += "x";
        }else {
            response += "-";
        }
        System.out.println(response);
    }
    public static void setPermissions(String path, String permissions) {
        File file = new File(path);
        if (!file.exists()) {
            System.err.println("Directory or file does not exist");
            return;
        }
        file.setWritable(false);
        file.setReadable(false);
        file.setExecutable(false);
        for (char c : permissions.toCharArray()) {
            if (c == 'r') {
                System.out.print(file.setReadable(true) ? "+r " : "Can't set readable ");
            }else if (c == 'w') {
                System.out.print(file.setWritable(true) ? "+w " : "Can't set writeable ");
            }else if (c == 'x') {
                System.out.print(file.setExecutable(true) ? "+x " : "Can't set executable ");
            }
        }
        System.out.println();
    }
    public static void printContent(String path) {
        File file = new File(path);
        if (!file.exists()) {
            System.err.println("File does not exist");
            return;
        }
        if (file.isDirectory()) {
            System.err.println("This is a directory");
            return;
        }
        if (file.canRead()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                reader.lines().forEach(System.out::println);
            } catch (FileNotFoundException e) {
                System.err.println("File does not exist");

            }
        }else {
            System.err.println("No permissions for read");
        }
    }
    public static void appendFooter(String path) {
        File file = new File(path);
        if (!file.exists()) {
            System.err.println("File does not exist");
            return;
        }
        if (file.isDirectory()) {
            System.err.println("This is a directory");
            return;
        }
        if (!file.canWrite()) {
            System.err.println("Can't write to file: No permissions");
            return;
        }
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(file, true));
            writer.println("# Autogenerated line");
            writer.close();
        } catch (IOException e) {
            System.out.println("Can't write to file: " + e.getMessage());
        }
    }
    public static void createBackup(String path) {
        File file = new File(path);
        if (!file.exists()) {
            System.err.println("File does not exist");
            return;
        }
        if (!file.canRead()) {
            System.err.println("Can't read file: No permissions");
            return;
        }

        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String backupPathString = "/tmp/" + dateFormat.format(currentDate) + ".backup";
        Path backupPath = Paths.get(backupPathString);

        Path sourcePath = Paths.get(path);
        if(file.isDirectory()) {
            try {
                Files.walkFileTree(sourcePath, new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        Files.copy(file, backupPath.resolve(sourcePath.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        Path targetPath = backupPath.resolve(sourcePath.relativize(dir));
                        if (Files.notExists(targetPath)) {
                            Files.createDirectory(targetPath);
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                System.err.println("Can't backup dir: " + e.getMessage());
            }
        } else {
            try {
                Files.copy(sourcePath, backupPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.err.println("Can't copy file: " + e.getMessage());
            }
        }

    }
    public static void grepLong(String path) {
        File file = new File(path);
        if (!file.exists()) {
            System.err.println("File does not exist");
            return;
        }
        if (file.isDirectory()) {
            System.err.println("This is a directory");
            return;
        }
        if (!file.canRead()) {
            System.err.println("Can't read file: No permissions");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            var ref = new Object() {
                String longestWord = "";
            };
            reader.lines().forEach(
                    line -> Arrays.stream(line.split(" ")).forEach(
                            word -> {
                                if (word.length() > ref.longestWord.length())
                                    ref.longestWord = word;
                            }
                    )
            );
            System.out.println(ref.longestWord);
        } catch (IOException e) {
            System.err.println("Can't process operation: " + e.getMessage());
        }
    }
}
