package covid;

import com.sun.tools.javac.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Regras {
    private static List<Pair<Integer, Integer>> getVizinhos(int x, int y, int[][] tabuleiro) {
        List<Pair<Integer, Integer>> vizinhos = new ArrayList<Pair<Integer, Integer>>();

        vizinhos.add(new Pair<>(x + 1, y + 1));
        vizinhos.add(new Pair<>(x + 1, y));
        vizinhos.add(new Pair<>(x + 1, y - 1));

        vizinhos.add(new Pair<>(x, y + 1));
        vizinhos.add(new Pair<>(x, y - 1));

        vizinhos.add(new Pair<>(x - 1, y + 1));
        vizinhos.add(new Pair<>(x - 1, y));
        vizinhos.add(new Pair<>(x - 1, y - 1));

        return vizinhos;
    }

    private static int contaVizinhosDoentes(int x, int y, int[][] tabuleiro) {
        int doentes;
        List<Pair<Integer, Integer>> vizinhos = getVizinhos(x, y, tabuleiro);

        doentes = (int) vizinhos.stream().filter(vizinho -> {
            try {
                return tabuleiro[vizinho.fst][vizinho.snd] == PessoaStatus.DOENTE;
            } catch (ArrayIndexOutOfBoundsException ignored) {
                return false;
            }
        }).count();

        return doentes;
    }

    public static int calculaStatus(int x, int y, int[][] tabuleiro){
        int status;

        switch (tabuleiro[x][y]){
            case PessoaStatus.SAUDAVEL:
                status = getStatusPessoaSaudavel(x, y, tabuleiro);
                break;
            default:
                status = tabuleiro[x][y];
        }

        return status;
    }

    private static int getStatusPessoaSaudavel(int x, int y, int[][] tabuleiro) {
        int status = PessoaStatus.SAUDAVEL;

        float chanceContagio = 3.5f * contaVizinhosDoentes(x, y, tabuleiro);
        Random rand = new Random();

        if(rand.nextInt(100) < chanceContagio)
            status = PessoaStatus.DOENTE;

        return status;
    }
}
