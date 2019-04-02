import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;

public class LFUCache implements Cache {

    private ArrayList<Subscriptie> array_cacheM;

    LFUCache(int capacity) {

        array_cacheM = new ArrayList<Subscriptie>(capacity);
    }

    /*Aceasta functie cauta si returneaza obiectul care va fi eliminat
     * in cazul in care memoria cache va fi plina si va trebui sa adaugam
     * un obiect nou*/
    private Subscriptie forRemove() {
        int min = array_cacheM.get(0).nr_acces;
        Subscriptie last = array_cacheM.get(0);

        for (Subscriptie str : array_cacheM) {
            if (str.nr_acces < min) {
                min = str.nr_acces;
                last = str;
            } else if (min == str.nr_acces)
                if (array_cacheM.indexOf(last) <= array_cacheM.indexOf(str))
                    last = str;
        }
        return last;
    }

    public Subscriptie searchInCacheM(String name) {
        if (!array_cacheM.isEmpty()) {
            for (Subscriptie str : array_cacheM) {
                if (Objects.equals(name, str.name))
                    return str;
            }
        }
        return null;
    }

    public void addInCache(MainMemory main_memory, String name, int capacity, PrintWriter printWriter) {

        Subscriptie str = main_memory.searchInMainM(name);
        Subscriptie aux = searchInCacheM(name);

        if (str == null && aux == null) {
            /*Daca a fost efectuata o comanda "GET" asupra unui obiect care
              nu exista nici in memoria principala, nici in cache*/
            printWriter.print("2" + "\n");
        } else if (str != null && aux == null) {
            /*Daca obiectul se afla doar in memoria principala, afisam 1,
             aplicam operatiunea de decrementare si copiem obiectul in memoria cache*/
            printWriter.print("1 " + str.decrement() + "\n");
            if (array_cacheM.size() < capacity) {
                str.nr_acces++;
                array_cacheM.add(0, str);
            } else {
                /*Daca memoria cache e plina, apelam metoda for_remove pentru a gasi
                 * obiectul care va trebui eliminat*/
                Subscriptie for_rem = forRemove();
                for_rem.nr_acces = 0;
                array_cacheM.remove(for_rem);
                str.nr_acces++;
                array_cacheM.add(0, str);
            }
        } else if (str != null) {
            /*Daca acesta deja exista in memoria cache, printam 0 si aplicam operatiunea
             de decrementare,dupa care actualizam numarul de accese si pozitia acestuia
             in memoria cache, plasandu-l pe prima pozitie*/
            printWriter.print("0 " + aux.decrement() + "\n");
            array_cacheM.remove(aux);
            str.nr_acces++;
            array_cacheM.add(0, str);
        }
    }

    public void remove(Subscriptie obj) {
        array_cacheM.remove(obj);
    }
}