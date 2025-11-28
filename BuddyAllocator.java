public class BuddyAllocator {
    private static final int MEMORY_SIZE = 4 * 1024 * 1024;
    private static final int MIN_BLOCK_SIZE = 1024;



    private int[] blockSizes;
    private int[] blockStatus;
    private String[] blockLabels;
    private int[] programSizes;
    private int totalBlocks;
    private int blockCount;



    public BuddyAllocator() {
        int maxBlocks = MEMORY_SIZE / MIN_BLOCK_SIZE;
        blockSizes = new int[maxBlocks];
        blockStatus = new int[maxBlocks];
        blockLabels = new String[maxBlocks];
        programSizes = new int[maxBlocks];
        totalBlocks = maxBlocks;
        blockCount = 0;
        blockSizes[0] = MEMORY_SIZE;
        blockStatus[0] = 0;
        blockLabels[0] = null;
        programSizes[0] = 0;
        blockCount = 1;
    }



    private int nextPowerOf2(int size) {
        int power = MIN_BLOCK_SIZE;
        while (power < size) {
            power = power * 2;
        }
        return power;
    }



    private int findFreeBlock(int requiredSize) {
        int bestIndex = -1;
        int bestSize = -1;
        for (int i = 0; i < blockCount; i++) {
            if (blockStatus[i] == 0 && blockSizes[i] >= requiredSize) {
                if (bestIndex == -1 || blockSizes[i] < bestSize) {
                    bestIndex = i;
                    bestSize = blockSizes[i];
                }
            }
        }
        return bestIndex;
    }



    private void splitBlock(int index, int targetSize) {
        while (blockSizes[index] > targetSize) {
            int currentSize = blockSizes[index];
            int newSize = currentSize / 2;
            blockSizes[index] = newSize;
            for (int i = blockCount; i > index + 1; i--) {
                blockSizes[i] = blockSizes[i - 1];
                blockStatus[i] = blockStatus[i - 1];
                blockLabels[i] = blockLabels[i - 1];
                programSizes[i] = programSizes[i - 1];
            }
            blockSizes[index + 1] = newSize;
            blockStatus[index + 1] = 0;
            blockLabels[index + 1] = null;
            programSizes[index + 1] = 0;
            blockCount++;
        }
    }



    public boolean allocate(String label, int sizeInKB) {
        int sizeInBytes = sizeInKB * 1024;
        if (sizeInBytes > MEMORY_SIZE) {
            return false;
        }
        int requiredSize = nextPowerOf2(sizeInBytes);
        int blockIndex = findFreeBlock(requiredSize);
        if (blockIndex == -1) {
            return false;
        }
        splitBlock(blockIndex, requiredSize);
        blockStatus[blockIndex] = 1;
        blockLabels[blockIndex] = label;
        programSizes[blockIndex] = sizeInBytes;
        return true;
    }



    public void printMemoryStatus() {
        int totalFree = 0;
        int fragmentCount = 0;
        for (int i = 0; i < blockCount; i++) {
            if (blockStatus[i] == 0) {
                totalFree += blockSizes[i];
                fragmentCount++;
            }
        }
        System.out.println("Memória que sobrou: " + formatBytes(totalFree) + " Memória que foi alocada: " + formatBytes(MEMORY_SIZE - totalFree) + " Espaços livres fragmentados: " + intToString(fragmentCount));

        if (fragmentCount > 0) {
            System.out.println("Blocos livres:");
            int position = 0;
            for (int i = 0; i < blockCount; i++) {
                if (blockStatus[i] == 0) {
                    System.out.println("Posição: " + formatBytes(position) + " Tamanho: " + formatBytes(blockSizes[i]));

                }
                position += blockSizes[i];
            }
        }
        System.out.println("Programas alocados:");

        int position = 0;
        for (int i = 0; i < blockCount; i++) {
            if (blockStatus[i] == 1) {
                System.out.println("Programa: " + blockLabels[i] + " Tamanho Real: " + formatBytes(programSizes[i]) + " Bloco Alocado: " + formatBytes(blockSizes[i]) + " Posição: " + formatBytes(position));
            }
            position += blockSizes[i];
        }
    }



    private String formatBytes(int bytes) {
        if (bytes >= 1024 * 1024) {
            int mb = bytes / (1024 * 1024);
            int remainder = bytes % (1024 * 1024);
            if (remainder == 0) {
                return intToString(mb) + " MB";
            } else {
                int kb = remainder / 1024;
                return intToString(mb) + " MB " + intToString(kb) + " KB";
            }
        } else if (bytes >= 1024) {
            int kb = bytes / 1024;
            return intToString(kb) + " KB";
        } else {
            return intToString(bytes) + " B";
        }
    }



    private String intToString(int value) {
        if (value == 0) return "0";

        boolean negative = false;
        if (value < 0) {
            negative = true;
            value = -value;
        }
        char[] digits = new char[12];
        int index = 0;
        while (value > 0) {
            digits[index] = (char) ('0' + (value % 10));
            value = value / 10;
            index++;
        }
        char[] result = new char[negative ? index + 1 : index];
        int resultIndex = 0;
        if (negative) {
            result[resultIndex++] = '-';
        }
        for (int i = index - 1; i >= 0; i--) {
            result[resultIndex++] = digits[i];
        }
        return new String(result);
    }
}
