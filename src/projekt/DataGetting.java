package com.example.menu;
import java.io.*;
import java.util.Properties;

/**
 * Created by Kuba on 2016-06-14.
 */


/**
 * Klasa odpowiadająca za przygotowanie danych do wysłania klientowi.
 */

public class DataGetting {
    private Properties list = new Properties();
    private InputStream input;
    private OutputStream output;

   /**
     * Konstruktor inicjujacy pobieranie danych z pliku za pomoc properties.
     * @param file Plik, z ktorego chcemy pobrac dane.
     */

    public DataGetting(String file)
    {
        try
        {
            input = new FileInputStream(file);
            list.load(input);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException ie)
        {
            ie.printStackTrace();
        }
    }


/**
     * Metoda tworzaca tablice wynikow do przeslania do klienta.
     * @return Tablica zawierajaca najlepsze wyniki, do przeslania klientowi
     */

    public String[] HighScoresMessageMaker()
    {
        String[] data = new String[list.size()];
        String value;
        int i=0;
        for (int j=0; j<list.size(); j++)
        {
            data[j] = null;
        }
        for(String key : list.stringPropertyNames())
        {
            value = list.getProperty(key);
            data[i] = key+"="+value;
            i++;
        }
        System.out.println("Wyszedlem z tworzenia NW!");
        return data;
    }
/**
     * Metoda zwracajaca ilosc rekordow w tablicy wynikow do wyslania.
     * @return Ilosc rekordow w tablicy wynikow do wyslania.
     */

    public String HSMessagesCounter()
    {
        return Integer.toString(list.size());
    }
}