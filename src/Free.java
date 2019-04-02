public class Free extends Subscriptie {

    public Free(String name) {
        super(name);
    }

    @Override
    String decrement() {
        return "Free";
    }
}
