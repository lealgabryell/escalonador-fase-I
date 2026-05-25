package br.com.escalonador;

import java.util.List;

/**
 * Ponto de entrada da aplicação.
 *
 * <p>Configura o cenário proposto (P1=10, P2=5, P3=8, quantum=2),
 * executa a simulação Round Robin e imprime o resultado no console.</p>
 */
public class Main {

    public static void main(String[] args) {

        // ── Definição do quantum (fatia de tempo da CPU) ──────────────────
        final int quantum = 2;

        // ── Criação dos processos com seus tempos totais de execução ──────
        List<Processo> processos = List.of(
                new Processo("P1", 10),   // Processo 1 precisa de 10 unidades de tempo
                new Processo("P2",  5),   // Processo 2 precisa de  5 unidades de tempo
                new Processo("P3",  8)    // Processo 3 precisa de  8 unidades de tempo
        );

        // ── Instancia o escalonador e executa a simulação ─────────────────
        EscalonadorRoundRobin escalonador = new EscalonadorRoundRobin();
        List<String> resultado = escalonador.simular(processos, quantum);

        // ── Exibe cabeçalho e cada linha do log no console ────────────────
        System.out.println("╔═══════════════════════════════════════════════════════╗");
        System.out.println("║       SIMULADOR DE ESCALONAMENTO - ROUND ROBIN        ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝");
        System.out.println();

        resultado.forEach(System.out::println);
    }
}

