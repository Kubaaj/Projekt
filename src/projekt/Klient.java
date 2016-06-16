package com.example.menu;

import java.util.*;
import javax.swing.JOptionPane;
import java.io.*;
import java.net.*;

/*
 * Created by Kuba on 2016-06-14.
 */

/*
 * Klasa klienta Władek Invaders, służy do pobierania wyników oraz ewentualnego przesyłania wyników na serwer.
 */

public class Klient implements Runnable{
    private Socket _socket;
    private PrintWriter output;
    private BufferedReader input;
    private InetAddress _host;
    private int NWLicznik = 0;
    private String[] NW;
    private DataEncoding data_encoding = new DataEncoding();
    private boolean _start;
    private boolean ifOnline;
    private String _nick;
    private int points;
    private long time;
    private int _port = 9876;

/*
     * Konstruktor tworzący obiekt komunikacji klienta w grze.
     * Służy on do utworzenia gniazdka klienta oraz wysłanie wstępnej wiadomości na serwer - w zależności od tego działania
     * Informuje się aplikację czy działamy w trybie online czy offline.
     * @param host - Adres serwera
     * @param port - Port, na którym tworzymy gniazdko klienta
     * @param start - Określa czy działamy klientem w celu pobrania tablicy wyników (początek gry, start = true), czy do wysłania
     * wyniku na serwer (koniec gry, start = false)
 */

    public Klient(InetAddress host, int port, boolean start) throws Exception {
        try {
            _start = start;
            _host = host;
            _port = port;
            _socket = new Socket(_host, _port);
        } catch (UnknownHostException e) {
            throw new Exception("Unknown host.");
        } catch (IOException e) {
            throw new Exception("IO exception while connecting to the server.");
        } catch (NumberFormatException e) {
            throw new Exception("Port value must be a number.");
        }

        init();
        try {
            sendMessage();
        } catch (IOException e) {
        }
        new Thread(this).start();
        SetConnection(true);
        send(Protocol.LOGIN);
        }
        /*
         * Metoda inicjujaca strumienie danych od i do klienta.
        */

        public void init() {
            try {
                input = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
                output = new PrintWriter(new OutputStreamWriter(_socket.getOutputStream()), true);
            } catch (IOException ex) {
                }
        }
            /*
             * Metoda inicjujaca połączenie z serwerem - wysyłamy wstępną wiadomość
             * @throws IOException
            */

            public void sendMessage() throws IOException
            {
                output.println(Protocol.LOGIN);
            }
            /*
             * disconnect
             * Metoda zamykajaca gniazdko klienta oraz strumienie z i do niego.
             * @throws IOException
            */

            public void disconnect() throws IOException
            {
                input.close();
                output.close();
                _socket.close();
            }
    /*
     * Cialo dzialania obslugi komunikacji z serwerem po stronie klienta.
     */

    public void run() {
        System.out.println("Funkcja run klienta działa");
        while (true)
            try {
                String command = input.readLine();
                if (!handleCommand(command)) {
                    disconnect();
                    break;
                }
            } catch (IOException e) {
            }
        output = null;
        input = null;
        synchronized (this) {
            _socket = null;
        }
    }
/*
     * Metoda, która określa kolejność i sposób komunikacji klient-serwer.
     * @param command Polecenie przeslane ze strony serwera.
*/

    private boolean handleCommand(String command) {
        StringTokenizer st = new StringTokenizer(command);
        String cd = st.nextToken();
        System.out.println("Klient otrzymał: "+command);
        if (cd.equals(Protocol.LOGGEDIN)) {
            if (_start) {
                send(Protocol.SEND_LISTA);
            } else {
                send(Protocol.NEW_SCORE + " " + _nick + " " + Integer.toString(points) + " " + Long.toString(time));
            }
        }
        else if (command.equals(Protocol.HIGHSCORE_COUNTER))
        {
            System.out.println("Klient otrzymał: "+command);
            int count = Integer.parseInt(st.nextToken());
            NW = new String[count];
            data_encoding.NWLicznik_set(count);

        }
        else if (command.equals(Protocol.HIGHSCORE_LIST))
        {
            System.out.println("Klient otrzymał: "+command);
            NW[NWLicznik] = st.nextToken();
            NWLicznik++;
        }
        else if (command.equals(Protocol.HIGHSCORE_LIST_FINISHED))
        {
            System.out.println("Klient otrzymał: "+command);
            data_encoding.NWEncoding(NW);
        }
        else if(command.equals(Protocol.NEW_SCORE_SAVED) || command.equals(Protocol.NEW_SCORE_UNSAVED) )
        {
            System.out.println("Klient otrzymał: "+command);
            send(Protocol.LOGOUT);
        }
        else if(command.equals(Protocol.LOGGEDOUT))
        {
            System.out.println("Klient otrzymał: "+command);
            return false;
        }
        else if (command.equals(Protocol.NULLCOMMAND))
        {
            System.out.println("Klient otrzymał: "+command);
            return false;
        }
        return true;
    }

/*
     * Metoda wysylajaca polecenia do serwera.
     * @param command Polecenie wysylane do serwera.
*/

    public void send(String command) {
        output.println(command);
    }
/*
     * Metoda ustawiajaca zmienna stanu polaczenia.
     * @param online Stan polaczenia - true = jest, false = nie ma.
*/

    public void SetConnection(boolean online)
    {
        ifOnline = online;
    }
/*
     * ReturnConnection
     * Metoda zwracajaca zmienna stanu polaczenia.
     * @return stan polaczenia
*/

    public boolean ReturnConnection()
    {
        return ifOnline;
    }
/*
     * setNewScore
     * Metoda ustawiająca elementy niezbedne do przeslania nowego wyniku na serwer.
     * @param nick Nick Gracza.
     * @param points Punkty zdobyte przez gracza.
     * @param app_time Czas rozpoczecia gry.
*/

    public void setNewScore(String Nick,int Points,long Time)
    {
        _nick = Nick;
        points = Points;
        time = Time;
    }
}
