package com.company;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;


public class BFS {

    // queue node used in BFS
    class Node { // klasa wierzchołek (komórka)
        // (x, y) współrzędne komórki w labiryncie
        // dist minimalny dystans od źródła
        int x, y, dist;

        // wierzchołek poprzedzający - aby naryswoać ścieżkę
        Node parent;

        Node(int x, int y, int dist, Node parent) {
            this.x = x;
            this.y = y;
            this.dist = dist;
            this.parent = parent;
        }

        @Override
        public String toString() {
            return "{" + x + ", " + y + '}';
        }
    }


    // rozmiar labiryntu m x n
    private int M;
    private int N;
    private Node cel;

    // tablice do przesuwania się w 4 kierunkach
    private final int row[] = { -1, 0, 0, 1 };
    private final int col[] = { 0, -1, 1, 0 };

    private int iloscKrokowCalkowita = 0;
    private int iloscKrokowDoZnalezieniaNajkrotszejDrogi = 0;
    private int iloscSciezek = 0;
    private boolean znalezionoNajKr = false;

    private int najKrTrasa[][] = new int[9000000][2]; // wierzcholki najkrotszej trasy
    private int wspolTrasy[][] = new int [9000000][2]; // wszystkie wierzcholki przebiegu

    private Node trasy [] = new Node [4];

    public BFS(int m, int n){

        M = m; // przypisz rozmiar labiryntu
        N = n;

    }


   // Funkcja sprawdzania, czy możliwe jest przejście do pozycji (wiersz, kolumna)
   // od bieżącej pozycji. Funkcja zwraca false, jeśli (wiersz, kolumna)
   // nie jest prawidłową pozycją lub ma wartość 1 lub jest już odwiedzona
    private boolean isValid(int mat[][], boolean visited[][], int row, int col)
    {

        if(row == M-1 && col == N-1)
            return true;

        else if((row >= 0) && (row < M) && (col >= 0) && (col < N)
                && mat[row][col] == 0 && !visited[row][col])
            return true;

        else
            return false;
    }

    //  Znajdź nakrótszą droge z komórki(i, j) do (x, y)
    public void szukajDrogi(int mat[][], int i, int j, int x, int y)
    {
        // tablica śledząca odwiedzone komórki
        boolean[][] visited = new boolean[M][N];

        // Stworzenie pustej kolejki
        Queue<Node> q = new ArrayDeque<>();

        // zaznacz komórkę źródłową jako odwiedzoną i umieść w kolejce węzeł źródłowy
        visited[i][j] = true;
        q.add(new Node(i, j, 0, null));

        // przechowuje długość najdłuższej ścieżki od źródła do miejsca docelowego
        int min_dist = Integer.MAX_VALUE;
        Node node = null;

        // działa dopóki kolejka nie jest pusta
        while (!q.isEmpty())
        {

            // usuń przedni węzeł z kolejki i przetworz go
            node = q.poll();

            // (i, j) reprezentuje bieżącą komórkę i dist przechowuje jej minimalną odległość od źródła
            i = node.x;
            j = node.y;

            // współrzedne obecnie sprawdzanego prawidłowego wierzchołka
            wspolTrasy[iloscKrokowCalkowita][0] = i;
            wspolTrasy[iloscKrokowCalkowita][1] = j;

            int dist = node.dist;

            // dodaj krok
            iloscKrokowCalkowita++;

            // jeżeli znaleziono punkt docelowy(x ,y), update min_dist i wyjdź z pętli
            if (i == x && j == y)
            {
                trasy[iloscSciezek] = node;
                iloscSciezek++;
                if(!znalezionoNajKr)
                {
                    min_dist = dist;
                    iloscKrokowDoZnalezieniaNajkrotszejDrogi = iloscKrokowCalkowita;
                    cel = node;
                    znalezionoNajKr = true;
                //break;
                }
            }

            // sprawdź wszystkie możliwe ruchy(prawo, lewo, góra dół)
            // i dodaj do kolejki możliwe ruchy
            for (int k = 0; k < 4; k++)
            {
                // sprawdź, czy można przejść do pozycji
                // (i + row[k], j + col[k]) z obecnej pozycji
                if (isValid(mat, visited, i + row[k], j + col[k]))
                {
                    // odznacz pozycje jako odwiedzoną i dodaj ją do kolejki
                    //if(i != x && j != y)
                        visited[i + row[k]][j + col[k]] = true;

                    q.add(new Node(i + row[k], j + col[k], dist + 1, node));
                }
            }
        }

        // jeżeli znależliśmy ścieżke to wypisz jej długość
        if (min_dist != Integer.MAX_VALUE) {
            System.out.println("Najkrótsza ścieżka ma dystans: " + min_dist);
            System.out.println("Znaleziono ścieżek: " + iloscSciezek);
            System.out.println("W którym kroku znaleziono najkrótszą ścieżkę: " + iloscKrokowDoZnalezieniaNajkrotszejDrogi);
            System.out.println("Liczba wszystkich kroków: " + iloscKrokowCalkowita);
            printPath(node, mat);
        }
        else {
            System.out.println("Nie odnaleziono ścieżki.");
        }
    }

    private void printPath(Node node, int[][] mat) {

        int k = 0;
        cel = trasy[0];
        // wpisz 2 to labiryntu jako ścieżka
        while (cel != null) {
            mat[cel.x][cel.y] = 2;
            // Wpisz współrzędne wierzchołków najkrótszej trasy
            najKrTrasa[k][0] = cel.x;
            najKrTrasa[k][1] = cel.y;
            cel = cel.parent;
            k++;
        }

        // wypisz labirynt
        for (int i = 0; i < M; i++) {
            System.out.println(Arrays.toString(mat[i]));
        }
    }

    public int[][] getWspolTrasy(){
        return wspolTrasy;
    }
    public int[][] getNajKrTrasa(){
        return najKrTrasa;
    }
    public int getIloscKrokowCalkowita(){
        return iloscKrokowCalkowita;
    }

}



/*    // Shortest path in a Maze
    public static void main(String[] args)
    {
        // input maze
        int[][] mat =
                {
                        {0, 0, 0, 0, 1, 0, 0, 0, 0, 1 },
                        {0, 1, 1, 0, 0, 0, 1, 0, 0, 0 },
                        {0, 1, 0, 0, 0, 1, 1, 0, 1, 0 },
                        {0, 0, 0, 0, 0, 0, 1, 0, 0, 1 },
                        {1, 0, 1, 0, 1, 0, 1, 0, 1, 0 },
                        {0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
                        {1, 1, 1, 0, 0, 1, 0, 0, 1, 0 },
                        {0, 0, 0, 0, 0, 1, 1, 0, 0, 0 },
                        {0, 1, 1, 1, 0, 0, 0, 1, 1, 0 },
                        {0, 1, 0, 0, 0, 0, 0, 0, 0, 0 }
                };

        // Find shortest path from source (0, 0) to destination (7, 5)
        BFS(mat, 0, 0, 9, 9);
    }*/
