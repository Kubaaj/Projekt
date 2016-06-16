
package com.example.menu;
import java.io.*;

/**
 * Created by Kuba on 2016-06-14.
 */


/**
 * Klasa, której obiekty odpowiadają za odkodowanie danych przesłanych przez serwer
 */

public class DataEncoding {
    private final String nw_file = "wyniki.txt";
    private PrintWriter _saving;
    private int NWLicznik = 0;

   /**
     * Metoda informująca obiekt o ilosci rekordow w odbieranej tablicy wynikow.
     * @param counter Ilosc wynikow w tablicy wynikow
     */

    public void NWLicznik_set(int counter)
    {
        NWLicznik = counter;
    }

/**
     * hs_encoding
     * Metoda odkodowujaca i zapisujaca przesylany przez serwer plik tablicy najlepszych wynikow.
     * @param hs_data_f Przesylana przez serwer tablica stringow z najlepszymi wynikami.
     */

    public void NWEncoding(String[] hs_data_f)
    {
        try
        {
            System.out.println("Wszedlem do NWEncoding!");
            _saving = new PrintWriter(nw_file);
            for (int i=0; i<NWLicznik;i++)
            {
                _saving.println(hs_data_f[i]);

            }
            System.out.println("Wyszedlem z NW!");
        } catch (FileNotFoundException e)
        {
            System.out.println("Problem w NWEncoding");
            e.printStackTrace();
        }
        finally
        {
            _saving.close();
        }
    }
}
