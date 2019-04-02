import java.io.*;
import java.util.Objects;

public class Main {
    public static void main(String[] argv) throws IOException {
        /*Creeam memoria principala*/
        MainMemory main_memory = new MainMemory();

        File file = new File(argv[0]);
        BufferedReader br = new BufferedReader(new FileReader(file));

        String st = br.readLine();
        int capacity_cacheM = Integer.parseInt(br.readLine());

        /*Creeam tipul de memorie cache in functie de parametrul citit din fisier*/
        Cache cache_memory;
        switch (st) {
            case "FIFO":
                cache_memory = new FIFOCache(capacity_cacheM);
                break;
            case "LRU":
                cache_memory = new LRUCache(capacity_cacheM);
                break;
            case "LFU":
                cache_memory = new LFUCache(capacity_cacheM);
                break;
            default:
                return;
        }
        int nr_operations = Integer.parseInt(br.readLine());

        FileWriter fileWriter = new FileWriter(argv[1]);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        /*Citim toate datele din fisierul input si in functie de comenzile citite apelam meotdele respective*/
        while ((st = br.readLine()) != null) {
            String[] split_string = st.split(" ");

            if (Objects.equals(split_string[0], "ADD"))
                main_memory.add(split_string, cache_memory);
            else if (Objects.equals(split_string[0], "GET"))
                cache_memory.addInCache(main_memory, split_string[1], capacity_cacheM, printWriter);
        }
        printWriter.close();
    }
}
