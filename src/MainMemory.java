import java.util.ArrayList;
import java.util.Objects;

class MainMemory {
    private ArrayList<Subscriptie> array_mainM;

    MainMemory() {
        array_mainM = new ArrayList<Subscriptie>();
    }

    /*Metoda care creeaza un obiect de tip subscriptie in functie de parametrii acestuia*/
    private Subscriptie createObject(String[] split_string) {
        Subscriptie obj;
        if (split_string.length == 4) {

            int nr_B = Integer.parseInt(split_string[2]);
            int nr_P = Integer.parseInt(split_string[3]);

            if (nr_B == 0 && nr_P == 0) {
                obj = new Free(split_string[1]);
            } else if (nr_B != 0 && nr_P == 0) {
                obj = new Basic(split_string[1], nr_B);
            } else
                obj = new Premium(split_string[1], nr_B, nr_P);
        } else if (split_string.length == 3) {

            int nr_B = Integer.parseInt(split_string[2]);
            if (nr_B == 0)
                obj = new Free(split_string[1]);
            else
                obj = new Basic(split_string[1], nr_B);
        } else {
            obj = new Free(split_string[1]);
        }
        return obj;
    }

    /*Meotda data cauta un anumit obiect daca este sau nu in memoria principala*/
    Subscriptie searchInMainM(String name) {
        if (!array_mainM.isEmpty())
            for (Subscriptie str : array_mainM)
                if (Objects.equals(name, str.name))
                    return str;
        return null;
    }

    void add(String[] split_string, Cache cache_memory) {

        /*In cazul in care vrem sa adaugam un obiect deja existent in memoria principala*/
        Subscriptie temp = searchInMainM(split_string[1]);
        if (temp != null) {
            /*Verificam daca acest obiect este prezent si in memoria Cache*/
            Subscriptie aux = cache_memory.searchInCacheM(split_string[1]);
            if (aux != null) {
                aux.nr_acces = 0;
                cache_memory.remove(aux);
            }
            array_mainM.remove(temp);
            Subscriptie temp1 = createObject(split_string);
            array_mainM.add(0, temp1);
        } else {
            /* Daca obiectul pe care vrem sa-l adaugam nu exista in memorie sau nu avem nimic in memoria principala */
            Subscriptie aux1 = createObject(split_string);
            array_mainM.add(0, aux1);
        }
    }
}
