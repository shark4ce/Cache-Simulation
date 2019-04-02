import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;

public class LRUCache implements Cache {
    private ArrayList<Subscriptie> array_cacheM;

    LRUCache(int capacity) {
        array_cacheM = new ArrayList<Subscriptie>(capacity);
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
            /*Daca obiectul se afla doar in memoria principala. afisam 1,
             aplicam operatiunea de decrementare si copiem obiectul in memoria cache*/
            printWriter.print("1 " + str.decrement() + "\n");
            if (array_cacheM.size() < capacity) {
                array_cacheM.add(0, str);
            } else {
                /*Daca nu mai e destul spatiu in memoria cache, stergem cel mai vechi "element"
                 * din memoria cache*/
                array_cacheM.remove(capacity - 1);
                array_cacheM.add(0, str);
            }
        } else if (str != null) {
            /*Daca acesta deja exista in memoria cache, printam 0 si aplicam operatiunea
             de decrementare,dupa care actualizam pozitia acestuia in memoria cache
             plasandu-l pe prima pozitie*/
            printWriter.print("0 " + aux.decrement() + "\n");
            array_cacheM.remove(aux);
            array_cacheM.add(0, str);
        }
    }

    public void remove(Subscriptie obj) {
        array_cacheM.remove(obj);
    }
}