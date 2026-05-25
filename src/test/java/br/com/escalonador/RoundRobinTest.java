package br.com.escalonador;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários do escalonador Round Robin.
 * Cenário principal testado: P1=10, P2=5, P3=8, quantum=2.
 * Tempo total esperado = 10 + 5 + 8 = 23 unidades.
 */
class RoundRobinTest {

    // ── Cenário proposto pelo enunciado ───────────────────────────────────

    @Test
    void deveConcluirTodosOsProcessos() {
        EscalonadorRoundRobin escalonador = new EscalonadorRoundRobin();
        List<Processo> processos = processosPadrao();

        List<String> log = escalonador.simular(processos, 2);

        // Todos os três processos devem aparecer como finalizados no log
        assertTrue(log.stream().anyMatch(s -> s.contains("P1 finalizado")));
        assertTrue(log.stream().anyMatch(s -> s.contains("P2 finalizado")));
        assertTrue(log.stream().anyMatch(s -> s.contains("P3 finalizado")));
    }

    @Test
    void deveFinalizarComTempoTotalCorreto() {
        EscalonadorRoundRobin escalonador = new EscalonadorRoundRobin();
        List<String> log = escalonador.simular(processosPadrao(), 2);

        // Tempo total deve ser 10 + 5 + 8 = 23
        assertTrue(log.stream().anyMatch(s -> s.contains("Tempo total de execucao: 23")),
                "Tempo total esperado: 23 unidades de tempo");
    }

    @Test
    void deveMensagemDeConclusao() {
        EscalonadorRoundRobin escalonador = new EscalonadorRoundRobin();
        List<String> log = escalonador.simular(processosPadrao(), 2);

        assertTrue(log.stream().anyMatch(s -> s.contains("Todos os processos foram concluidos.")));
    }

    // ── Testes de validação de entrada ────────────────────────────────────

    @Test
    void deveLancarExcecaoParaQuantumZero() {
        EscalonadorRoundRobin escalonador = new EscalonadorRoundRobin();
        assertThrows(IllegalArgumentException.class,
                () -> escalonador.simular(processosPadrao(), 0));
    }

    @Test
    void deveLancarExcecaoParaListaVazia() {
        EscalonadorRoundRobin escalonador = new EscalonadorRoundRobin();
        assertThrows(IllegalArgumentException.class,
                () -> escalonador.simular(List.of(), 2));
    }

    @Test
    void deveLancarExcecaoParaTempoTotalZero() {
        assertThrows(IllegalArgumentException.class,
                () -> new Processo("P_invalido", 0));
    }

    // ── Cenário com quantum maior que todos os tempos ─────────────────────

    @Test
    void deveFinalizarProcessoNoPrimeiroQuantumSeTempoMenorOuIgual() {
        EscalonadorRoundRobin escalonador = new EscalonadorRoundRobin();
        List<Processo> processos = List.of(new Processo("P1", 3));

        List<String> log = escalonador.simular(processos, 10);

        assertTrue(log.stream().anyMatch(s -> s.contains("P1 finalizado")));
        assertTrue(log.stream().anyMatch(s -> s.contains("Tempo total de execucao: 3")));
    }

    // ── Helper ────────────────────────────────────────────────────────────

    private List<Processo> processosPadrao() {
        return List.of(
                new Processo("P1", 10),
                new Processo("P2",  5),
                new Processo("P3",  8)
        );
    }
}

