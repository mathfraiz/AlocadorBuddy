public class Main {

    public static void main(String[] args) {
        String filename = "programas.txt";
        if (args != null) {
            int argsCount = countArgs(args);
            if (argsCount > 0) {
                filename = args[0];
            }
        }
        Program[] programs = FileReader.readPrograms(filename);

        int programCount = countPrograms(programs);
        System.out.println("Total de programas lidos: " + intToString(programCount));

        BuddyAllocator alocador = new BuddyAllocator();

        int ResultadoConta = 0;
        int ErroConta = 0;

        for (int i = 0; i < programCount; i++) {
            assert programs != null;
            Program program = programs[i];

            System.out.println("Alocando programa " + program.label +  intToString(program.size) + " KB");

            boolean sucesso = alocador.allocate(program.label, program.size);
            if (sucesso) {
                System.out.println("Sucesso !");
                ResultadoConta++;
            } else {
                System.out.println("Falha, memória cheia !");
                ErroConta++;
            }
        }
        System.out.println("Alocacao concluida, sucessos: " + intToString(ResultadoConta) + " falhas: " + intToString(ErroConta));
        alocador.printMemoryStatus();
    }



    private static int countArgs(String[] args) {
        if (args == null) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < 100; i++) {
            boolean hasMore = checkArrayPosition(args, i);
            if (!hasMore) {
                break;
            }
            count++;
        }
        return count;
    }



    private static boolean checkArrayPosition(String[] arr, int pos) {
        if (arr == null || pos < 0) {
            return false;
        }
        try {
            String temp = arr[pos];
            return true;
        } catch (Exception e) {
            return false;
        }
    }



    private static int countPrograms(Program[] programs) {
        if (programs == null) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < 1000; i++) {
            boolean exists = checkProgramPosition(programs, i);
            if (!exists) {
                break;
            }
            if (programs[i] != null) {
                count++;
            }
        }
        return count;
    }




    private static boolean checkProgramPosition(Program[] progs, int pos) {
        if (progs == null || pos < 0) {
            return false;
        }
        try {
            Program temp = progs[pos];
            return true;
        } catch (Exception e) {
            return false;
        }
    }



    private static String intToString(int value) {
        if (value == 0) {
            return "0";
        }
        boolean negative = false;
        if (value < 0) {
            negative = true;
            value = -value;
        }
        char[] digits = new char[12];
        int index = 0;
        while (value > 0) {
            digits[index] = (char)('0' + (value % 10));
            value = value / 10;
            index++;
        }
        char[] result = new char[negative ? index + 1 : index];
        int resultIndex = 0;
        if (negative) {
            result[resultIndex] = '-';
            resultIndex++;
        }
        for (int i = index - 1; i >= 0; i--) {
            result[resultIndex] = digits[i];
            resultIndex++;
        }
        return new String(result);
    }
}
