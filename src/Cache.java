import java.io.PrintWriter;

public interface Cache {

    /*Acesta metoda adauga obiectul in memoria cache in functie de constrangeri si parametrii acestuia,
     * operatiune efectuata ca urmare a comenzii "GET"*/
    default void addInCache(MainMemory main_memory, String name, int capacity, PrintWriter printWriter) {
    }

    default void remove(Subscriptie object) {

    }

    /*Cautam daca un anumit obiect exista in memoria cache*/
    default Subscriptie searchInCacheM(String name) {
        return null;
    }
}