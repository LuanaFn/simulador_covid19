package conway.original;

import covid.PessoaStatus;
import covid.Regras;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class TabuleiroGOL extends JPanel implements ComponentListener, MouseListener, MouseMotionListener, Runnable {
    private static final long serialVersionUID = 1L;
    private static final int tamanhoCelula = 10;
    private Dimension dimensaoTabuleiro = null;
    private final ArrayList<Point> pontos = new ArrayList<Point>(0);
    private int iteracaoPorSegundo;
    int[][] tabuleiro;

    public TabuleiroGOL() {
        addComponentListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

    }

    private void atualizarTamanhoAray() {
        ArrayList<Point> removeList = new ArrayList<Point>(0);
        pontos.clear();
        for (int x = 0; x < dimensaoTabuleiro.width; x++)
            for (int y = 0; y < dimensaoTabuleiro.height; y++)
                pontos.add(new Point(x, y));
        repaint();
    }

    public void addPointDoente(int x, int y) {
        tabuleiro[x][y] = PessoaStatus.DOENTE;
        repaint();
    }

    public void addPointDoente(MouseEvent me) {
        int x = me.getPoint().x / tamanhoCelula - 1;
        int y = me.getPoint().y / tamanhoCelula - 1;
        if ((x >= 0) && (x < dimensaoTabuleiro.width) && (y >= 0) && (y < dimensaoTabuleiro.height)) {
            addPointDoente(x, y);
        }
    }

    public void removePoint(int x, int y) {
        pontos.remove(new Point(x, y));
    }

    /**
     * Inicializa o ArrayList que representa o tabuleiro com valores vazios
     */
    public void resetBoard() {
        pontos.clear();
    }

    /**
     * Inicializa o tabuleiro com valores randômicos
     */
    public void preencherTabRandom(int porcentagem) {
        for (int x = 0; x < dimensaoTabuleiro.width; x++) {
            for (int y = 0; y < dimensaoTabuleiro.height; y++) {
                if (Math.random() * 100 < porcentagem) {
                    addPointDoente(x, y);
                }
            }//Fim do for y
        }//Fim do for x
    }

    /**
     * Método para desenhar o grid do tabuleiro do Jogo da Vida
     */
    public void paintComponent(Graphics g) {
        iteracaoPorSegundo = JogoDaVida.getIteracaoPorSegundo();

        super.paintComponent(g);
        try {
            for (Point novoPonto : pontos) {
                Color pcolor;
                int status = tabuleiro[novoPonto.x][novoPonto.y];
                switch (status){
                    case PessoaStatus.SAUDAVEL:
                        pcolor = Color.green;
                        break;
                    case PessoaStatus.DOENTE:
                        pcolor = Color.red;
                        break;
                    case PessoaStatus.IMUNE:
                        pcolor = Color.yellow;
                        break;
                    case PessoaStatus.MORTO:
                        pcolor = Color.black;
                        break;
                    default:
                        pcolor = Color.white;
                }
                g.setColor(pcolor);
                g.fillRect(tamanhoCelula + (tamanhoCelula * novoPonto.x), tamanhoCelula + (tamanhoCelula * novoPonto.y), tamanhoCelula, tamanhoCelula);
            }
        } catch (ConcurrentModificationException cme) {
        }

        g.setColor(Color.BLACK);
        for (int x = 0; x <= dimensaoTabuleiro.width; x++) {
            g.drawLine(((x * tamanhoCelula) + tamanhoCelula), tamanhoCelula, (x * tamanhoCelula) + tamanhoCelula, tamanhoCelula + (tamanhoCelula * dimensaoTabuleiro.height));
        }
        for (int y = 0; y <= dimensaoTabuleiro.height; y++) {
            g.drawLine(tamanhoCelula, ((y * tamanhoCelula) + tamanhoCelula), tamanhoCelula * (dimensaoTabuleiro.width + 1), ((y * tamanhoCelula) + tamanhoCelula));
        }
    }

    public void componentResized(ComponentEvent e) {
        dimensaoTabuleiro = new Dimension(getWidth() / tamanhoCelula - 2, getHeight() / tamanhoCelula - 2);
        tabuleiro = new int[dimensaoTabuleiro.width][dimensaoTabuleiro.height];
        atualizarTamanhoAray();
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
        // Mouse was released (user clicked)
        addPointDoente(e);
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        // Mouse is being dragged, user wants multiple selections
        addPointDoente(e);
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void run() {
        int[][] tabuleiroIteracaoPassada = tabuleiro;

        for (int x = 0; x < dimensaoTabuleiro.width; x++) {
            for (int y = 0; y < dimensaoTabuleiro.height; y++) {
                tabuleiro[x][y] = Regras.calculaStatus(x, y, tabuleiroIteracaoPassada);
            }// Fim do for j
        }//Fim do for i

        repaint();

        try {
            Thread.sleep(1000 / iteracaoPorSegundo);
            //Thread.sleep(4000);
            run();
        } catch (InterruptedException ex) {
        }
    }//Fim do método run()

    /**
     * Contar o número de células vivas, de acordo com a posição i,j
     */
    private int contarVizinhosVivos(int x, int y, boolean[][] tabuleiro) {
        /**
         *   -----------------------------------------------------
         *  |                      |    y=acima    |              |
         *  |         x=esquerda   |     (x,y)     |  x=direita   |
         *  |                      |    y=abaixo   |              |
         *   -----------------------------------------------------
         *
         *   ------------------------------------------------------------
         *  |  (esquerda, acima)   |  (x ,acima)   |   (direita, acima)   |
         *  |  (esquerda, y)       |  (x,y)        |   (direita,y)        |
         *  |  (esquerda, abaixo)  |  (x,abaixo)   |   (direita, abaixo)  |
         *   ------------------------------------------------------------
         *
         */

        int vizinhanca = 0;
        int acima = 0, abaixo = 0, direita = 0, esquerda = 0;

        if (y == 0)
            acima = dimensaoTabuleiro.height - 1;
        else acima = y - 1;

        if (y == dimensaoTabuleiro.height - 1)
            abaixo = 0;
        else abaixo = y + 1;

        if (x == 0)
            esquerda = dimensaoTabuleiro.width - 1;
        else esquerda = x - 1;

        if (x == dimensaoTabuleiro.width - 1)
            direita = 0;
        else direita = x + 1;

        //System.out.println("acima: "+acima+"   abaixo: "+abaixo+"   esquerda: "+esquerda+"    direita: "+direita);

        if (tabuleiro[esquerda][acima])
            vizinhanca++;
        if (tabuleiro[x][acima])
            vizinhanca++;
        if (tabuleiro[direita][acima])
            vizinhanca++;
        if (tabuleiro[esquerda][y])
            vizinhanca++;
        if (tabuleiro[direita][y])
            vizinhanca++;
        if (tabuleiro[esquerda][abaixo])
            vizinhanca++;
        if (tabuleiro[x][abaixo])
            vizinhanca++;
        if (tabuleiro[direita][abaixo])
            vizinhanca++;

        return vizinhanca;
    }//Fim do método contarVizinhosVivos()
}