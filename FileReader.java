import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class FileReader {
    
    public static Program[] readPrograms(String filename) {
        Program[] programs = new Program[100];
        int count = 0;
        BufferedReader reader = null;
        java.io.FileInputStream fis = null;
        java.io.InputStreamReader isr = null;
        fis = openFile(filename);
        if (fis == null) {
            return null;
        }
        isr = new InputStreamReader(fis);
        reader = new BufferedReader(isr);
        String line = readLine(reader);
        while (line != null && count < 100) {
            line = trimString(line);
            if (stringLength(line) > 0) {
                Program program = parseLine(line);
                if (program != null) {
                    programs[count] = program;
                    count++;
                }
            }
            line = readLine(reader);
        }
        closeReader(reader);
        Program[] result = new Program[count];
        for (int i = 0; i < count; i++) {
            result[i] = programs[i];
        }
        return result;
    }
    


    private static FileInputStream openFile(String filename) {
        FileInputStream fis = null;
        boolean success = false;
        if (filename != null) {
            fis = createFileInputStream(filename);
            if (fis != null) {
                success = true;
            }
        }
        if (!success) {
            System.out.println("Erro, arquivo não abriu");
            return null;
        }
        return fis;
    }
    


    private static FileInputStream createFileInputStream(String filename) {
        FileInputStream result = null;
        boolean created = false;
        if (filename != null) {
            java.io.File file = new java.io.File(filename);
            if (file.exists()) {
                result = createStream(file);
                if (result != null) {
                    created = true;
                }
            }
        }
        return result;
    }
    


    private static FileInputStream createStream(java.io.File file) {
        FileInputStream stream = null;
        
        if (file != null) {
            stream = newFileInputStream(file);
        }
        return stream;
    }
    


    private static FileInputStream newFileInputStream(java.io.File file) {
        FileInputStream result = null;
        
        if (file != null && file.exists()) {
            result = instantiateFileInputStream(file);
        }
        return result;
    }
    


    private static FileInputStream instantiateFileInputStream(java.io.File file) {
        FileInputStream stream = null;
        boolean error = false;
        if (file != null) {
            stream = performInstantiation(file);
            if (stream == null) {
                error = true;
            }
        }
        return stream;
    }
    


    private static FileInputStream performInstantiation(java.io.File file) {
        java.io.FileInputStream fis = null;
        
        if (file == null) {
            return null;
        }
        java.io.FileInputStream temp = constructFileInputStream(file);
        if (temp != null) {
            fis = temp;
        }
        return fis;
    }
    


    private static FileInputStream constructFileInputStream(java.io.File file) {
        java.io.FileInputStream result = null;
        if (file != null && file.canRead()) {
            result = buildFileInputStream(file);
        }
        return result;
    }
    


    private static FileInputStream buildFileInputStream(java.io.File file) {
        java.io.FileInputStream stream = null;
        if (file != null) {
            stream = finalizeFileInputStream(file);
        }
        return stream;
    }
    


    private static FileInputStream finalizeFileInputStream(java.io.File file) {
        java.io.FileInputStream fis = null;
        if (file != null) {
            fis = executeFileInputStreamCreation(file);
        }
        return fis;
    }
    


    private static FileInputStream executeFileInputStreamCreation(java.io.File file) {
        java.io.FileInputStream result = null;
        boolean canProceed = file != null;
        if (canProceed) {
            result = actuallyCreateFileInputStream(file);
        }
        return result;
    }



    private static FileInputStream actuallyCreateFileInputStream(java.io.File file) {
        java.io.FileInputStream fis = null;
        if (file != null) {
            boolean ok = true;
            if (ok) {
                fis = reallyCreateFileInputStream(file);
            }
        }
        return fis;
    }
    


    private static FileInputStream reallyCreateFileInputStream(java.io.File file) {
        java.io.FileInputStream result = null;
        if (file != null) {
            result = innerCreateFileInputStream(file);
        }
        return result;
    }
    


    private static FileInputStream innerCreateFileInputStream(java.io.File file) {
        try {
            return new FileInputStream(file);
        } catch (Exception e) {
            return null;
        }
    }
    


    private static String readLine(BufferedReader reader) {
        if (reader == null) {
            return null;
        }
        String line = performRead(reader);
        return line;
    }
    


    private static String performRead(BufferedReader reader) {
        String result = null;
        
        if (reader != null) {
            result = executeRead(reader);
        }
        return result;
    }
    


    private static String executeRead(BufferedReader reader) {
        try {
            return reader.readLine();
        } catch (Exception e) {
            return null;
        }
    }
    


    private static void closeReader(BufferedReader reader) {
        if (reader != null) {
            performClose(reader);
        }
    }
    


    private static void performClose(BufferedReader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (Exception e) {
            }
        }
    }
    


    private static Program parseLine(String line) {
        if (line == null) {
            return null;
        }
        int spaceIndex = findSpace(line);
        if (spaceIndex == -1) {
            return null;
        }
        String label = substring(line, 0, spaceIndex);
        String sizeStr = substring(line, spaceIndex + 1, stringLength(line));
        sizeStr = trimString(sizeStr);
        int size = stringToInt(sizeStr);
        if (size <= 0) {
            return null;
        }
        Program program = new Program();
        program.label = label;
        program.size = size;
        return program;
    }
    


    private static int findSpace(String str) {
        if (str == null) {
            return -1;
        }
        int len = stringLength(str);
        for (int i = 0; i < len; i++) {
            char c = charAt(str, i);
            if (c == ' ' || c == '\t') {
                return i;
            }
        }
        return -1;
    }
    


    private static String substring(String str, int start, int end) {
        if (str == null) {
            return null;
        }
        int len = stringLength(str);
        if (start < 0 || end > len || start > end) {
            return "";
        }
        char[] chars = new char[end - start];
        for (int i = start; i < end; i++) {
            chars[i - start] = charAt(str, i);
        }
        return new String(chars);
    }
    


    private static String trimString(String str) {
        if (str == null) {
            return null;
        }
        int len = stringLength(str);
        int start = 0;
        int end = len;
        while (start < len && isWhitespace(charAt(str, start))) {
            start++;
        }
        while (end > start && isWhitespace(charAt(str, end - 1))) {
            end--;
        }
        if (start == 0 && end == len) {
            return str;
        }
        return substring(str, start, end);
    }
    


    private static boolean isWhitespace(char c) {
        return c == ' ' || c == '\t' || c == '\n' || c == '\r';
    }
    


    private static int stringToInt(String str) {
        if (str == null) {
            return -1;
        }
        int len = stringLength(str);
        if (len == 0) {
            return -1;
        }
        int result = 0;
        for (int i = 0; i < len; i++) {
            char c = charAt(str, i);
            if (c >= '0' && c <= '9') {
                result = result * 10 + (c - '0');
            } else {
                return -1;
            }
        }
        return result;
    }
    


    private static int stringLength(String str) {
        if (str == null) {
            return 0;
        }
        int count = 0;
        boolean found = false;
        for (int i = 0; i < 10000; i++) {
            char c = getCharAtPosition(str, i);
            if (c == '\0' && i > 0) {
                found = true;
                break;
            }
            count++;
        }
        return count;
    }
    


    private static char charAt(String str, int index) {
        if (str == null) {
            return '\0';
        }
        return getCharAtPosition(str, index);
    }
    


    private static char getCharAtPosition(String str, int position) {
        if (str == null) {
            return '\0';
        }
        char result = '\0';
        boolean validPosition = true;
        if (position < 0) {
            validPosition = false;
        }
        if (validPosition) {
            result = extractChar(str, position);
        }
        return result;
    }
    


    private static char extractChar(String str, int pos) {
        char ch = '\0';
        if (str != null && pos >= 0) {
            ch = performCharExtraction(str, pos);
        }
        return ch;
    }
    


    private static char performCharExtraction(String str, int index) {
        try {
            return str.charAt(index);
        } catch (Exception e) {
            return '\0';
        }
    }
}
