# escalonador-fase-I
Escalonador De Processos com Round Robin - Projeto da competência Desenvolver Simulador de Abstrações de Recursos de S.O. - 221121

---

## 📋 Descrição

Simulação do algoritmo de escalonamento **Round Robin** em **Java 21**, com quantum fixo de **2 unidades de tempo**.

O sistema simula a execução de três processos distintos e apresenta a sequência de execução ciclo a ciclo até que todos sejam concluídos.

O **Round Robin** é um dos algoritmos de escalonamento mais utilizados em sistemas operacionais. Ele distribui a CPU de forma igualitária entre todos os processos prontos, garantindo que nenhum processo sofra **inanição (starvation)**. Cada processo recebe uma fatia de tempo (quantum) e, caso não conclua sua execução nesse período, volta ao final da fila para aguardar sua próxima vez.

---

## 🗂️ Estrutura do Projeto

```
escalonador-fase-I/
├── pom.xml
└── src/
    ├── main/java/br/com/escalonador/
    │   ├── Processo.java               # Modelo de processo (nome, tempo total, tempo restante)
    │   ├── EscalonadorRoundRobin.java  # Lógica do algoritmo Round Robin
    │   └── Main.java                   # Ponto de entrada / cenário de simulação
    └── test/java/br/com/escalonador/
        └── RoundRobinTest.java         # Testes unitários com JUnit 5
```

---

## ⚙️ Parâmetros da Simulação

| Parâmetro | Valor |
|-----------|-------|
| Quantum   | **2 unidades de tempo** |
| Nº de processos | **3** |
| Tempo total de execução | **23 unidades de tempo** |

---

## 🔵 Processos Simulados

### Processo 1 — P1
- **Tempo total necessário:** 10 unidades de tempo
- **Descrição:** É o processo mais longo da simulação. Devido ao quantum de 2, ele precisará de **5 ciclos** para concluir sua execução. Será o último a finalizar, em **t=23**.

### Processo 2 — P2
- **Tempo total necessário:** 5 unidades de tempo
- **Descrição:** Processo de duração intermediária-baixa. Em seus primeiros ciclos consome 2 ut por vez, mas no último ciclo consome apenas **1 ut** (tempo restante menor que o quantum). Finaliza em **t=15**, sendo o **primeiro** a concluir.

### Processo 3 — P3
- **Tempo total necessário:** 8 unidades de tempo
- **Descrição:** Processo de duração intermediária. Consome exatamente 2 ut por ciclo em todos os seus **4 ciclos**. Finaliza em **t=21**, sendo o **segundo** a concluir.

---

## 🔄 Fluxo de Execução — Ciclo a Ciclo

A tabela abaixo mostra a sequência completa da simulação com quantum = 2:

| Ciclo | Processo | t início | t fim | Tempo executado | Tempo restante | Status       |
|-------|----------|----------|-------|-----------------|----------------|--------------|
| 1     | P1       | 0        | 2     | 2 ut            | 8 ut           | Aguardando   |
| 2     | P2       | 2        | 4     | 2 ut            | 3 ut           | Aguardando   |
| 3     | P3       | 4        | 6     | 2 ut            | 6 ut           | Aguardando   |
| 4     | P1       | 6        | 8     | 2 ut            | 6 ut           | Aguardando   |
| 5     | P2       | 8        | 10    | 2 ut            | 1 ut           | Aguardando   |
| 6     | P3       | 10       | 12    | 2 ut            | 4 ut           | Aguardando   |
| 7     | P1       | 12       | 14    | 2 ut            | 4 ut           | Aguardando   |
| 8     | P2       | 14       | 15    | 1 ut            | 0 ut           | ✅ Finalizado |
| 9     | P3       | 15       | 17    | 2 ut            | 2 ut           | Aguardando   |
| 10    | P1       | 17       | 19    | 2 ut            | 2 ut           | Aguardando   |
| 11    | P3       | 19       | 21    | 2 ut            | 0 ut           | ✅ Finalizado |
| 12    | P1       | 21       | 23    | 2 ut            | 0 ut           | ✅ Finalizado |

> **Observação:** No ciclo 8, P2 executa apenas **1 ut** pois seu tempo restante (1 ut) é menor que o quantum (2 ut). O escalonador detecta isso e concede apenas o tempo necessário.

---

## 🚀 Cenário de Simulação

| Processo | Tempo Total | Nº de Ciclos | Finaliza em |
|----------|-------------|--------------|-------------|
| P1       | 10 ut       | 5 ciclos     | t = 23      |
| P2       | 5 ut        | 3 ciclos     | t = 15      |
| P3       | 8 ut        | 4 ciclos     | t = 21      |

- **Quantum:** 2 unidades de tempo
- **Tempo total esperado:** 23 unidades de tempo
- **Ordem de conclusão:** P2 → P3 → P1

---

## ▶️ Como Executar

### Pré-requisitos
- Java 21+
- Maven 3.8+

### Compilar e rodar a simulação
```powershell
mvn -q clean compile
java -cp target\classes br.com.escalonador.Main
```

### Executar os testes
```powershell
mvn clean test
```

---

## 📊 Saída Esperada

```
╔═══════════════════════════════════════════════════════╗
║       SIMULADOR DE ESCALONAMENTO - ROUND ROBIN        ║
╚═══════════════════════════════════════════════════════╝

Iniciando simulacao Round Robin | quantum = 2
Processos: P1(t=10), P2(t=5), P3(t=8)
-------------------------------------------------------
[t= 0 -> t= 2]  P1   | executou 2 ut | restante: 8 ut
[t= 2 -> t= 4]  P2   | executou 2 ut | restante: 3 ut
[t= 4 -> t= 6]  P3   | executou 2 ut | restante: 6 ut
[t= 6 -> t= 8]  P1   | executou 2 ut | restante: 6 ut
[t= 8 -> t=10]  P2   | executou 2 ut | restante: 1 ut
[t=10 -> t=12]  P3   | executou 2 ut | restante: 4 ut
[t=12 -> t=14]  P1   | executou 2 ut | restante: 4 ut
[t=14 -> t=15]  P2   | executou 1 ut | restante: 0 ut
             [X] P2 finalizado em t=15
[t=15 -> t=17]  P3   | executou 2 ut | restante: 2 ut
[t=17 -> t=19]  P1   | executou 2 ut | restante: 2 ut
[t=19 -> t=21]  P3   | executou 2 ut | restante: 0 ut
             [X] P3 finalizado em t=21
[t=21 -> t=23]  P1   | executou 2 ut | restante: 0 ut
             [X] P1 finalizado em t=23
-------------------------------------------------------
[OK] Todos os processos foram concluidos.
Tempo total de execucao: 23 unidades de tempo.
```
