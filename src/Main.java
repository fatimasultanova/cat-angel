public class Main {
    public static void main(String[] args) {
        System.out.println("Codes: \n" + "food=> 111\n" + "sleep=> 222\n" + "power=> 333\n");
        Cat cat= new Cat();
        Power power = new Power();
        Sleep sleep = new Sleep();
        cat.run();
        power.run();
        sleep.run();

    }

}