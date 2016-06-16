package com.example.menu;
import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 * Created by Kuba on 2016-06-13.
 */


/**
 * Klasa, która obsługuje komunikację z serwerem po stronie serwera.
 */

public class ServerService implements Runnable {
    private Server server;
    private Socket clientSocket;
    private BufferedReader input;
    private PrintWriter output;
    private DataGetting najlepszewyniki;
    private NajlepszeWyniki  NajlepszeWynikiOperacje;
    private final String WynikiPlik = "wyniki.txt";
    private String data;
    private byte bytes[];


/**
     * ServerService
     * Konstruktor komunikacji z serwerem po klienta do serwera.
     * @param userSocket Gniazdko klienta, który jest podłączony do serwera
     * @param server Obiekt serwera
     */

    public ServerService(Socket userSocket, Server server) {
        server = server;
        clientSocket = userSocket;
    }
  /**
     * Metoda inicjujaca strumienie danych
     */

    void init() throws IOException {
        Reader reader = new InputStreamReader(clientSocket.getInputStream());
        input = new BufferedReader(reader);
        output = new PrintWriter(clientSocket.getOutputStream(), true);
    }

/**
     * Metoda zamykajaca gniazdko dostepu dla klienta oraz strumienie
     */

    void close() {
        try {
            output.close();
            input.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Problem z zamknięciem połączenia.");
        } finally {
            output = null;
            input = null;
            clientSocket = null;
        }
    }

/**
     * Ciało działania obsługi komunikacji z serwerem po stronie serwera.
     */

    public void run() {
        while (true) {
            String request = receive();
            StringTokenizer st = new StringTokenizer(request);
            String command = st.nextToken();
            System.out.println("Otrzymano: "+command);
            if (command.equals(Protocol.LOGIN)) {
                System.out.println("Otrzymano: " +Protocol.LOGIN);
                send(Protocol.LOGGEDIN);
            } else if (command.equals(Protocol.SEND_LISTA)) {
                System.out.println("Serwer otrzymał: " + command);
                String HScounter;
                najlepszewyniki = new DataGetting(WynikiPlik);
                HScounter = najlepszewyniki.HSMessagesCounter();
                send(Protocol.HIGHSCORE_COUNTER + " " + HScounter);
                String data[] = new String[Integer.parseInt(HScounter)];
                data = najlepszewyniki.HighScoresMessageMaker();
                for (int i = 0; i < Integer.parseInt(HScounter); i++) {
                    send(Protocol.HIGHSCORE_LIST + " " + data[i]);
                }
                send(Protocol.HIGHSCORE_LIST_FINISHED);
            }
            else if (request.equals(Protocol.NEW_SCORE))
            {
                String nick = st.nextToken();
                int points = Integer.parseInt(st.nextToken());
                long app_start_time = Long.parseLong(st.nextToken());
                NajlepszeWynikiOperacje = NajlepszeWyniki.Zwroc_Dzialania_Na_Wynikach(WynikiPlik);
                if(NajlepszeWynikiOperacje.porownaj_wynik_i_ustaw_property(nick,points,app_start_time))
                {
                    NajlepszeWynikiOperacje.zapisz_liste_w_pliku(WynikiPlik);
                    send(Protocol.NEW_SCORE_SAVED);

                }
                else send(Protocol.NEW_SCORE_UNSAVED);
                NajlepszeWynikiOperacje.Wynuluj();
                NajlepszeWynikiOperacje = null;
            } else if (request.equals(Protocol.LOGOUT)) {
                System.out.println("Serwer otrzymał: " +request);
                send(Protocol.LOGGEDOUT);
                break;
            }else if (request.equals(Protocol.STOP))
        {   System.out.println("Serwer otrzymał: " +request);
            send(Protocol.STOPPED);
            break;
        }  else if (command.equals(Protocol.NULLCOMMAND)) {
                break;
            }
        }
        server.removeClientService(this);
    }
/**
     * receive
     * Metoda odbierajaca polecenia przychodzace od klienta.
     *
     */

    private String receive()
    {
        try
        {
            return input.readLine();
        }
        catch (IOException e) {
            System.err.println("Błąd odczytu klienta");
        }
        return Protocol.NULLCOMMAND;
    }

    /**
     * Metoda wysylajaca polecenia do klienta.
     */

    public void send(String request) {
        output.println(request);
    }
}