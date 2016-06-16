package com.example.menu;
import java.io.*;
import java.util.*;

/*
 * Created by Kuba on 2016-06-14.
*/

public class NajlepszeWyniki {
    int poprzednia;
    private static NajlepszeWyniki najlepsze_wyniki = null;
    private Properties lista = new Properties();
    private InputStream wczytanie;
    private OutputStream zapis;
    private int[] ListaPunktow;
    private Integer[][] PunktoKod;

    private NajlepszeWyniki()
    {
        try
        {
            int i = 0;
            wczytanie = new FileInputStream("wyniki.txt");
            lista.load(wczytanie);
            ListaPunktow = new int[lista.size()];
            PunktoKod = new Integer[lista.size()][2];
            Odkoduj_Wyniki();
for(String key : lista.stringPropertyNames())
			{
				ListaPunktow[i] = Integer.parseInt(lista.getProperty(key));
				i++;
			}

            System.out.println("TABLICA została odkodowana:");
            for(int j=0; j<ListaPunktow.length; j++)
            {
                System.out.println("I: " + j + " => " + ListaPunktow[j]);
            }
            System.out.println("PUNKTOKOD");
            for(int j=0; j<ListaPunktow.length; j++)
            {
                System.out.println("I: " + j + " => " + PunktoKod[j][1]);
            }
            System.out.println("PUNKTOKOD 0: " + PunktoKod[0]);
            posortuj_wyniki(ListaPunktow);
            System.out.println("posortowana TABLICA:");
            for(int j=0; j<ListaPunktow.length; j++)
            {
                System.out.println("I: " + j + " => " + ListaPunktow[j]);
            }
            sortuj_punkto_kod();
            System.out.println("posortowany punktokod:");
            for(int j=0; j<ListaPunktow.length; j++)
            {
                System.out.println("Wartosc: " + PunktoKod[j][0] + " //  Kod: " + PunktoKod[j][1]);
            }
        }
        catch(IOException ex){}
        finally
        {
            if (wczytanie != null)
            {
                try
                {
                    wczytanie.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    private NajlepszeWyniki(String plik)
    {
        try
        {
            int i = 0;
            wczytanie = new FileInputStream(plik);
            lista.load(wczytanie);
            ListaPunktow = new int[lista.size()];
            PunktoKod = new Integer[lista.size()][2];
            //profil = Profil.ZwrocProfilNaKoniec();
            Odkoduj_Wyniki();
for(String key : lista.stringPropertyNames())
			{
				ListaPunktow[i] = Integer.parseInt(lista.getProperty(key));
				i++;
			}

            System.out.println("TABLICA została odkodowana:");
            for(int j=0; j<ListaPunktow.length; j++)
            {
                System.out.println("I: " + j + " => " + ListaPunktow[j]);
            }
            System.out.println("PUNKTOKOD");
            for(int j=0; j<ListaPunktow.length; j++)
            {
                System.out.println("I: " + j + " => " + PunktoKod[j][1]);
            }
            System.out.println("PUNKTOKOD 0: " + PunktoKod[0]);
            posortuj_wyniki(ListaPunktow);
            System.out.println("posortowana TABLICA:");
            for(int j=0; j<ListaPunktow.length; j++)
            {
                System.out.println("I: " + j + " => " + ListaPunktow[j]);
            }
            sortuj_punkto_kod();
            System.out.println("posortowany punktokod:");
            for(int j=0; j<ListaPunktow.length; j++)
            {
                System.out.println("Wartosc: " + PunktoKod[j][0] + " //  Kod: " + PunktoKod[j][1]);
            }
        }
        catch(IOException ex){}
        finally
        {
            if (wczytanie != null)
            {
                try
                {
                    wczytanie.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public static NajlepszeWyniki Zwroc_Dzialania_Na_Wynikach()
    {
        if (najlepsze_wyniki == null)
        {
            najlepsze_wyniki = new NajlepszeWyniki();
        }
        return najlepsze_wyniki;
    }

    public static NajlepszeWyniki Zwroc_Dzialania_Na_Wynikach(String plik)
    {
        if (najlepsze_wyniki == null)
        {
             najlepsze_wyniki = new NajlepszeWyniki(plik);
        }
        return najlepsze_wyniki;
    }
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
public int partition(int nizszy_index, int wyzszy_index)
{
    int i = nizszy_index;
    int j = wyzszy_index;
    int liczba_do_podzialu = ListaPunktow[nizszy_index];
    while (i<=j)
    {
        while (ListaPunktow[i] < liczba_do_podzialu)
        {
            i++;
        }
        while (ListaPunktow[j] > liczba_do_podzialu)
        {
            j--;
        }
        if (i <= j)
        {
            zamien_miejscami(i, j);
            i++;
            j--;
        }
    }
    return i;
}
    public void quick_sort(int[] nieposortowana_tablica, int nizszy_index, int wyzszy_index)
    {
        ListaPunktow = nieposortowana_tablica;
        int index = partition(nizszy_index, wyzszy_index);
        if (nizszy_index < index - 1) quick_sort(ListaPunktow,nizszy_index, index - 1);
        if (index < wyzszy_index) quick_sort(ListaPunktow,index, wyzszy_index);
    }
    private void zamien_miejscami(int i,int j)
    {
        int pomoc = ListaPunktow[i];
        ListaPunktow[i] = ListaPunktow[j];
        ListaPunktow[j] = pomoc;
    }
    public void posortuj_wyniki(int[] nieposortowana_tablica )
    {

        if (nieposortowana_tablica == null || nieposortowana_tablica.length == 0) {
            return;
        }
        ListaPunktow = nieposortowana_tablica;
        quick_sort(ListaPunktow, 0, ListaPunktow.length - 1);
    }
    public boolean porownaj_wynik_i_ustaw_property(String nick, int punkty, long Czas_startu_apki)
    {
        int i=0;
        boolean Czylepszy = false;
System.out.println("NIEODWROCONA TABLICA:");
		for(int j=0; j<ListaPunktow.length; j++)
		{
			System.out.println("I: " + j + " => " + ListaPunktow[j]);
		}

        System.out.println("0 = " + ListaPunktow[0] + " // ost = " + ListaPunktow[ListaPunktow.length-1]);
        if (ListaPunktow[0]<ListaPunktow[ListaPunktow.length-1]) Odwroc_Tablice();
        System.out.println("ODWROCONA TABLICA:");
        for(int j=0; j<ListaPunktow.length; j++)
        {
            System.out.println("I: " + j + " => " + ListaPunktow[j]);
        }
        while(i<ListaPunktow.length)
        {
            if (punkty<=ListaPunktow[i])
            {
                i++;
                System.out.println("Krece I: "+i);
            }
            else break;

        }
        System.out.println("WYszedlem I: "+i);
        System.out.println("TABLICA KEY:");
        String wartos2c;
        for(String key : lista.stringPropertyNames())
        {
            wartos2c = lista.getProperty(key);
            System.out.println("Key: "+key + " // Wartosc: " + wartos2c);
        }
        if (i<=ListaPunktow.length-1)
        {
            if (punkty>ListaPunktow[i])
            {
                System.out.println("TEN KLUCZ WYWALE: "+porownaj_value2(Przypisz_Kod_Wynikowi(ListaPunktow.length-1)));
                if (lista.getProperty(porownaj_value2(Przypisz_Kod_Wynikowi(ListaPunktow.length-1))) != null)
                {
                    String przypisany = Przypisz_Kod_Wynikowi(ListaPunktow.length-1);
                    System.out.println("PRZYPISANY: "+przypisany);
                    String sub1;
                    String sub2;
                    int pomoc1=0;
                    int pomoc2=0;
                    int index=0;
                    System.out.println("WYWALAM");

                    System.out.println("TEN KLUCZ WYWALE: "+porownaj_value2(Przypisz_Kod_Wynikowi(ListaPunktow.length-1)));
                    lista.remove(porownaj_value2(Przypisz_Kod_Wynikowi(ListaPunktow.length-1)));
                    sub1 = przypisany.substring(0,przypisany.indexOf(";"));
                    sub2 = przypisany.substring(przypisany.indexOf(";")+1,przypisany.length());
                    for (int j=0;j<ListaPunktow.length;j++)
                    {
                        if (Integer.parseInt(sub1)==PunktoKod[j][0] && j!=ListaPunktow.length-1)
                        {
                            pomoc1= PunktoKod[j][0];
                            pomoc2 = PunktoKod[j][1];
                            for (int k=j+1;k<ListaPunktow.length;k++)
                            {
                                if (Integer.parseInt(sub1) == PunktoKod[k][0])
                                {
                                    PunktoKod[j][0] = PunktoKod[k][0];
                                    PunktoKod[j][1] = PunktoKod[k][1];
                                    PunktoKod[k][0] = pomoc1;
                                    PunktoKod[k][1] = pomoc2;
                                }
                            }
                            break;
                        }
                    }

                }
                else System.out.println("Cos nie tak,bo nic tu nie ma");
                //lista.remove(lista.getProperty(porownaj_value(ListaPunktow[ListaPunktow.length-1]),Integer.toString(ListaPunktow[ListaPunktow.length-1])));
                System.out.println("TABLICA KEY:");
                for(String key : lista.stringPropertyNames())
                {
                    System.out.println("Key: "+key + " // Wartosc: " + (lista.getProperty(key)));
                }
                for (int iteracja = ListaPunktow.length-1; iteracja > i; iteracja--)
                {
                    ListaPunktow[iteracja] = ListaPunktow[iteracja-1];
                    PunktoKod[iteracja][0] = PunktoKod[iteracja-1][0];
                    PunktoKod[iteracja][1] = PunktoKod[iteracja-1][1];
                }
                System.out.println("przesunieta TABLICA:");
                for(int j=0; j<ListaPunktow.length; j++)
                {
                    System.out.println("I: " + j + " => " + ListaPunktow[j]);
                }
                System.out.println("Podmieniam liste o indexie: " + i);
                ListaPunktow[i] = punkty;
                System.out.println("przesunieta TABLICA i dodana:");
                for(int j=0; j<ListaPunktow.length; j++)
                {
                    System.out.println("I: " + j + " => " + ListaPunktow[j]);
                }
                long czas = System.currentTimeMillis();
                lista.setProperty(nick+";"+System.currentTimeMillis(),Integer.toString(punkty)+";"+(czas-Czas_startu_apki));
                PunktoKod[i][0] = punkty;
                PunktoKod[i][1] = (int)(czas-Czas_startu_apki);
                Czylepszy = true;
                //System.out.println("Key: "+key + " // Wartosc: " + wartosc);

                //		lista.setProperty(profil.Zwroc_Nick(),Integer.toString(profil.Zwroc_Punkty()));
                System.out.println("TABLICA KEY po dodaniu:");
                for(String key : lista.stringPropertyNames())
                {
                    System.out.println("Key: "+key + " // Wartosc: " + (lista.getProperty(key)));
                }
                System.out.println("PUNKTOKOD PO WKLEPANIU:");
                for(int j=0; j<ListaPunktow.length; j++)
                {
                    System.out.println("0: " + PunktoKod[j][0] + " // 1: " + PunktoKod[j][1]);
                }
            }
        }
        return Czylepszy;
    }
    public String porownaj_value(int value)
    {
        boolean powtorka = false;
        int wartosc;
        for(String key : lista.stringPropertyNames())
        {
            wartosc = Integer.parseInt(lista.getProperty(key));
            System.out.println("Value: "+value + " // Wartosc: " + wartosc);
            if (value == wartosc)
            {
                return key;
            }
        }
        return null;
    }

    public void sortuj_punkto_kod()
    {
        Arrays.sort(PunktoKod,new Comparator<Integer[]>()
        {
            public int compare(Integer[] d1, Integer[] d2)
            {
                Integer d_1 = d1[0];
                Integer d_2 = d2[0];
                return -(d_1.compareTo(d_2));
            }
        });
    }

    public String porownaj_value2(String value)
    {
        boolean powtorka = false;
        String wartosc;
        for(String key : lista.stringPropertyNames())
        {
            wartosc = lista.getProperty(key);
            System.out.println("Value: "+value + " // Wartosc: " + wartosc);
            if (value.equals(wartosc))
            {
                System.out.println("WLAZLEM I WRACAM!!!!");
                return key;
            }
        }
        return null;
    }

    public void zapisz_liste_w_pliku(String plik)
    {
        try
        {
            zapis = new FileOutputStream(plik);
        }
        catch (FileNotFoundException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try
        {
            lista.store(zapis, null);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


        finally
        {
            if (zapis != null)
            {
                try
                {
                    zapis.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

    }
    public int[] Zwroc_liste_punktow()
    {
        return ListaPunktow;
    }

    public int Zwroc_dlugosc_listy_punktow()
    {
        return ListaPunktow.length;
    }
    public void Odwroc_Tablice()
    {
        int j=0;
        int[] tablica_pomocna = new int[ListaPunktow.length];
        for(int i=ListaPunktow.length-1; i>-1 ; i--)
        {
            tablica_pomocna[j] = ListaPunktow[i];
            j++;
        }
        ListaPunktow = tablica_pomocna;
    }

    public void Odkoduj_Wyniki()
    {
        int i=0;
        String wartosc;
        String sub1;
        String sub2;
        for(String key : lista.stringPropertyNames())
        {
            wartosc = (lista.getProperty(key));
            sub1 = wartosc.substring(0,wartosc.indexOf(";"));
            sub2 = wartosc.substring(wartosc.indexOf(";")+1,wartosc.length());
            PunktoKod[i][0] = Integer.parseInt(sub1);
            ListaPunktow[i] = Integer.parseInt(sub1);
            PunktoKod[i][1] = Integer.parseInt(sub2);
            i++;
        }
    }

    public String Przypisz_Kod_Wynikowi(int index)
    {
        //int i=0;
        for (int i=0; i<ListaPunktow.length ; i++)
        {
            if (ListaPunktow[index]==PunktoKod[i][0])
            {
                System.out.println("WYCHODZE Z: " + ListaPunktow[index]+";"+PunktoKod[i][1]);
                return (PunktoKod[i][0]+";"+PunktoKod[i][1]);
                //	break;
            }
        }
        return null;
    }
    public String Ostateczny_Kod(int index)
    {
        return (PunktoKod[index][0]+";"+PunktoKod[index][1]);
    }
    public void Wynuluj()
    {
        najlepsze_wyniki = null;
    }
}