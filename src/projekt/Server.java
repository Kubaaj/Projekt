package com.example.menu;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Created by Kuba on 2016-06-13.
 */

public class Server extends Frame implements Runnable {
    private ServerSocket serverSocket;
    private Vector<ServerService> clients = new Vector<ServerService>();
    private Properties props;
    private int Port = 9877;

/**
     * Konstruktor, który służy do ustawienia serwera na odpowiednim porcie oraz lokalnym adresie ip.
     * @param p Ustawienia serwera
     * @param title jest to tytul okna serwera
     */

    public Server(Properties p, String title) {
        super(title);
        props = p;
        Port = Integer.parseInt(props.getProperty("port"));
        try {
            serverSocket = new ServerSocket(Port);
            System.out.println("PORT ====> " + Port);
        } catch (IOException e) {
            System.err.println("Error starting Server.");
            System.exit(1);
        }
        new Thread(this).start();
        pack();
        setVisible(true);
}
    public void run() {
        while (true)
            try {
                System.out.println("Czekam");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nowy użytkownik");
                ServerService clientService = new ServerService(clientSocket, this);
                addClientService(clientService);
            } catch (IOException e) {
                System.err.println("Error accepting connection. "
                        + "Client will not be served...");
            }
    }
/**
     * addClientService
     * Metoda odpowiadająca za łączenie klientów z serwerem
     * @param clientService Klient, który wysyła żądanie łączenia z serwerem.
     */
    synchronized void addClientService(ServerService clientService)
            throws IOException {
        clientService.init();
        clients.addElement(clientService);
        new Thread(clientService).start();
        System.out.println("Klienci zostali dodani: " + clients.size());
    }
   /**
     * removeClientService
     * Metoda, która odpowiada za zakończenie połączenia między klientem a serwerem
     * @param clientService Klient, którego połączenie z serwerem jest zamykane.
     */

    synchronized void removeClientService(ServerService clientService) {
        clients.removeElement(clientService);
        clientService.close();
        System.out.println("Klienci zostali usunięci " + clients.size());
    }
//disonnect

    public static void main(String args[]) {
        Properties props = new Properties();
        String propsFile = "UstawieniaSerwera.txt";
        try {
            props.load(new FileInputStream(propsFile));
        } catch (Exception e) {
            props.put("port", "40000");
            props.put("width", "250");
            props.put("height", "250");
        }
        try {
            props.store(new FileOutputStream(propsFile), null);
        } catch (Exception e) {
        }
        new Server(props, "Internet Board Server");
    }
}