import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class AnaliseDesempenho {

    // Algoritmo Lento: Bubble Sort
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Troca os elementos
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        // Define o Locale para garantir que o separador decimal seja o ponto (ex: 0.523s)
        Locale.setDefault(Locale.US);
        
        ArrayList<Integer> listaNumeros = new ArrayList<>();

        // 1. Leitura do arquivo original
        try (Scanner scanner = new Scanner(new File("arq.txt"))) {
            while (scanner.hasNextInt()) {
                listaNumeros.add(scanner.nextInt());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Erro: O arquivo 'arq.txt' não foi encontrado. Certifique-se de que ele está no mesmo diretório.");
            return;
        }

        if (listaNumeros.isEmpty()) {
            System.out.println("Nenhum número válido encontrado no arquivo.");
            return;
        }

        // Converter o ArrayList para um array primitivo de int para melhor desempenho
        int n = listaNumeros.size();
        int[] original = new int[n];
        for (int i = 0; i < n; i++) {
            original[i] = listaNumeros.get(i);
        }

        System.out.println("Total de " + n + " números lidos.\n");

        // 2. Execução do Algoritmo Lento (Bubble Sort)
        System.out.println("Algoritmo: Bubble Sort");
        double tempoTotalBubble = 0;

        for (int i = 1; i <= 5; i++) {
            // Cria uma cópia nova e desordenada do array original para a execução atual
            int[] copia = Arrays.copyOf(original, n);

            long inicio = System.nanoTime();
            bubbleSort(copia);
            long fim = System.nanoTime();

            // Converte nanossegundos para segundos
            double tempoGasto = (fim - inicio) / 1_000_000_000.0;
            tempoTotalBubble += tempoGasto;
            System.out.printf("Execução %d: %.3fs\n", i, tempoGasto);
        }
        System.out.printf("Média: %.3fs\n\n", tempoTotalBubble / 5.0);

        // 3. Execução do Algoritmo Rápido (Quick Sort)
        // O método Arrays.sort() em Java utiliza um Dual-Pivot Quicksort para tipos primitivos
        System.out.println("Algoritmo: Quick Sort");
        double tempoTotalQuick = 0;
        int[] arrayFinalOrdenado = null;

        for (int i = 1; i <= 5; i++) {
            int[] copia = Arrays.copyOf(original, n);

            long inicio = System.nanoTime();
            Arrays.sort(copia);
            long fim = System.nanoTime();

            double tempoGasto = (fim - inicio) / 1_000_000_000.0;
            tempoTotalQuick += tempoGasto;
            System.out.printf("Execução %d: %.3fs\n", i, tempoGasto);
            
            // Salva o resultado da última execução para gravar no arquivo depois
            if (i == 5) {
                arrayFinalOrdenado = copia;
            }
        }
        System.out.printf("Média: %.3fs\n\n", tempoTotalQuick / 5.0);

        // 4. Salvar o resultado final em arq-ordenado.txt
        try (PrintWriter writer = new PrintWriter(new File("arq-ordenado.txt"))) {
            for (int i = 0; i < n; i++) {
                writer.print(arrayFinalOrdenado[i] + " ");
            }
            System.out.println("Números ordenados salvos em 'arq-ordenado.txt' com sucesso!");
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao tentar criar o arquivo de saída 'arq-ordenado.txt'.");
        }
    }
}