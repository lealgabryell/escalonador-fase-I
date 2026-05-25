package br.com.escalonador;

/**
 * Representa um processo a ser escalonado.
 *
 * Cada processo possui um nome identificador, o tempo total de CPU
 * que ele necessita e o tempo restante (decrementado a cada execução).
 */
public class Processo {

    private final String nome;
    private final int tempoTotal;
    private int tempoRestante;

    /**
     * Cria um novo processo.
     *
     * @param nome       identificador do processo (ex: "P1")
     * @param tempoTotal unidades de tempo de CPU necessárias (deve ser > 0)
     */
    public Processo(String nome, int tempoTotal) {
        if (tempoTotal <= 0) {
            throw new IllegalArgumentException("tempoTotal deve ser maior que zero.");
        }
        this.nome = nome;
        this.tempoTotal = tempoTotal;
        this.tempoRestante = tempoTotal;
    }

    /** @return nome do processo */
    public String getNome() {
        return nome;
    }

    /** @return tempo total de CPU necessário ao processo */
    public int getTempoTotal() {
        return tempoTotal;
    }

    /** @return tempo restante até a conclusão */
    public int getTempoRestante() {
        return tempoRestante;
    }

    /**
     * Executa o processo por, no máximo, {@code quantum} unidades de tempo.
     * Se o tempo restante for menor que o quantum, executa apenas o necessário.
     *
     * @param quantum fatia máxima de tempo cedida ao processo
     * @return quantidade de unidades efetivamente executadas
     */
    public int executar(int quantum) {
        // Executa apenas o mínimo entre o quantum e o tempo que ainda falta
        int tempoExecutado = Math.min(quantum, tempoRestante);
        tempoRestante -= tempoExecutado;
        return tempoExecutado;
    }

    /**
     * Verifica se o processo foi completamente executado.
     *
     * @return {@code true} se tempoRestante == 0
     */
    public boolean finalizado() {
        return tempoRestante == 0;
    }

    @Override
    public String toString() {
        return String.format("Processo{nome='%s', tempoTotal=%d, tempoRestante=%d}",
                nome, tempoTotal, tempoRestante);
    }
}

