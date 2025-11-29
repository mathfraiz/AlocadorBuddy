# Alocador de Memória Buddy Binário

Implementação de um alocador de memória dinâmica usando o algoritmo Buddy Binário para gerenciar uma memória principal de 4 MB.

## Como Compilar

Para compilar o projeto, execute o seguinte comando no terminal:

```bash
javac Main.java BuddyAllocator.java FileReader.java Program.java
```

## Como Executar

Após compilar, execute o programa com:

```bash
java Main programas.txt
```

Se não especificar um arquivo, o programa usará `programas.txt` por padrão.

## Formato do Arquivo de Entrada

O arquivo de entrada deve conter uma lista de programas, um por linha, no seguinte formato:

```
ROTULO TAMANHO_EM_KB
```

Exemplo:
```
A 512
B 1024
C 256
```

Onde:
- ROTULO: identificador do programa (uma letra ou string)
- TAMANHO_EM_KB: tamanho do programa em kilobytes (número inteiro)

## Principais Decisões de Projeto

### Estrutura de Dados

A memória é representada por arrays simples que armazenam informações sobre os blocos:
- `blockSizes[]`: tamanho de cada bloco em bytes
- `blockStatus[]`: status do bloco (0 = livre, 1 = ocupado)
- `blockLabels[]`: rótulo do programa alocado no bloco
- `programSizes[]`: tamanho real do programa alocado

### Algoritmo Buddy Binário

O alocador funciona da seguinte forma:

1. Inicia com um único bloco de 4 MB
2. Para cada solicitação de alocação:
    - Calcula a menor potência de 2 que comporta o programa
    - Busca o menor bloco livre adequado (estratégia first fit)
    - Divide o bloco recursivamente até obter o tamanho necessário
    - Marca o bloco como ocupado e registra informações do programa

### Estratégia de Alocação

Utilizei uma estratégia similar a **first fit** com otimização:
- Busca o menor bloco disponível que atenda à requisição
- Isso minimiza a fragmentação ao evitar dividir blocos maiores desnecessariamente
- Os blocos são divididos apenas até o tamanho necessário

### Divisão de Blocos

Quando um bloco precisa ser dividido:
1. O bloco é dividido em dois "buddies" de tamanho igual (metade do original)
2. O processo continua recursivamente até atingir o tamanho desejado
3. Um dos buddies é usado para alocação, o outro permanece livre


## Saída do Programa

O programa gera um relatório detalhado contendo:

1. **Memória Total**: 4 MB
2. **Memória Livre**: total de bytes livres
3. **Memória Alocada**: total de bytes ocupados
4. **Fragmentos Livres**: número e lista de blocos livres com posições e tamanhos
5. **Programas Alocados**: lista completa com:
    - Rótulo do programa
    - Tamanho real do programa
    - Tamanho do bloco alocado
    - Posição na memória

## Exemplo de Uso

```bash
$ javac *.java
$ java Main programas.txt
Iniciando Alocador de Memoria Buddy Binario
Memoria Total: 4 MB
...
```

## Arquivos do Projeto

- `Main.java`: classe principal com ponto de entrada do programa
- `BuddyAllocator.java`: implementação do alocador buddy binário
- `FileReader.java`: classe auxiliar para leitura do arquivo de entrada
- `Program.java`: classe simples para representar um programa
- `programas.txt`: arquivo de exemplo com 20 programas
- `README.md`: este arquivo
