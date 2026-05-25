package br.com.escalonador;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Implementa o algoritmo de escalonamento Round Robin.
 *
 * <p>O Round Robin percorre a fila de processos prontos em ordem circular,
 * concedendo a cada processo uma fatia de tempo (quantum). Se o processo
 * não terminar dentro do quantum, ele volta ao final da fila e aguarda
 * sua próxima vez.</p>
 *
 * <p>Essa abordagem garante que nenhum processo sofra inanição (starvation),
 * pois todos recebem CPU periodicamente.</p>
 */
public class EscalonadorRoundRobin {

    /**
     * Simula o escalonamento Round Robin para a lista de processos fornecida.
     *
     * <p>O método NÃO altera os objetos originais da lista — cada ciclo opera
     * sobre a própria instância de {@link Processo}, que mantém seu estado interno.</p>
     *
     * @param processos lista de processos a escalonar (não pode ser nula ou vazia)
     * @param quantum   fatia máxima de CPU por ciclo (deve ser > 0)
     * @return lista de strings descrevendo, linha a linha, cada evento da simulação
     */
    public List<String> simular(List<Processo> processos, int quantum) {
        // Validações de entrada
        if (quantum <= 0) {
            throw new IllegalArgumentException("O quantum deve ser maior que zero.");
        }
        if (processos == null || processos.isEmpty()) {
            throw new IllegalArgumentException("A lista de processos não pode ser nula ou vazia.");
        }

        // Fila circular com todos os processos prontos para execução
        Queue<Processo> fila = new LinkedList<>(processos);

        // Log de saída com todos os eventos da simulação
        List<String> log = new ArrayList<>();

        // Relógio global da CPU (acumulado ao longo de todos os ciclos)
        int tempoGlobal = 0;

        log.add("Iniciando simulacao Round Robin | quantum = " + quantum);
        log.add("Processos: " + processos.stream()
                .map(p -> p.getNome() + "(t=" + p.getTempoTotal() + ")")
                .reduce((a, b) -> a + ", " + b).orElse(""));
        log.add("-".repeat(55));

        // Loop principal: continua enquanto houver processos na fila
        while (!fila.isEmpty()) {

            // Retira o próximo processo da frente da fila
            Processo atual = fila.poll();

            // Salva o instante de início deste ciclo
            int inicio = tempoGlobal;

            // Executa o processo por até 'quantum' unidades de tempo
            int executado = atual.executar(quantum);

            // Avança o relógio global pelo tempo efetivamente executado
            tempoGlobal += executado;

            // Registra o evento de execução no log
            log.add(String.format(
                    "[t=%2d -> t=%2d]  %-4s | executou %d ut | restante: %d ut",
                    inicio, tempoGlobal,
                    atual.getNome(),
                    executado,
                    atual.getTempoRestante()
            ));

            if (atual.finalizado()) {
                // Processo concluído: registra e NÃO devolve à fila
                log.add(String.format("             [X] %s finalizado em t=%d",
                        atual.getNome(), tempoGlobal));
            } else {
                // Processo ainda tem trabalho: volta ao final da fila
                fila.offer(atual);
            }
        }

        // Todos os processos foram concluídos
        log.add("-".repeat(55));
        log.add("[OK] Todos os processos foram concluidos.");
        log.add("Tempo total de execucao: " + tempoGlobal + " unidades de tempo.");

        return log;
    }
}



