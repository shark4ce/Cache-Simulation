import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;

public class FIFOCache implements Cache {
    private ArrayList<Subscriptie> array_cacheM;

    FIFOCache(int capacity) {
        array_cacheM = new ArrayList<Subscriptie>(capacity);
    }

    public Subscriptie searchInCacheM(String name) {
        if (!array_cacheM.isEmpty())
            for (Subscriptie str : array_cacheM)
                if (Objects.equals(name, str.name))
                    return str;
        return null;
    }

    public void addInCache(MainMemory main_memory, String name, int capacity, PrintWriter printWriter) {
        /*Apelam metoda search_in_main din clasa MainMemory pentru
         * a vedea daca obiectul dat exista sau nu in momoria principala*/
        Subscriptie str = main_memory.searchInMainM(name);
        Subscriptie aux = searchInCacheM(name);

        /*Daca a fost efectuata o comanda "GET" asupra unui obiect care
        nu exista nici in memoria principala, nici in cache*/
        if (aux == null && str == null) {
            printWriter.print("2" + "\n");
        } else if (str != null && aux == null) {
            /*Daca obiectul se afla doar in memoria principala. afisam 1,
             aplicam operatiunea de decrementare si copiem obiectul in memoria cache*/
            printWriter.print("1 " + str.decrement() + "\n");
            if (array_cacheM.size() < capacity) {
                array_cacheM.add(str);
            } else {
                /*Daca nu mai e destul spatiu in memoria cache, stergem primul obiect
                adaugat in memoria cache*/
                array_cacheM.remove(0);
                array_cacheM.add(str);
            }
        } else if (str != null)
            /*Daca acesta deja exista in memoria cache, printam 0 si aplicam operatiunea
             de decrementare*/
            printWriter.print("0 " + aux.decrement() + "\n");
    }

    public void remove(Subscriptie obj) {
        array_cacheM.remove(obj);
    }
}