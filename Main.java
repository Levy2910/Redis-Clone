import InputOutputLearning.ConsoleEcho;
import day1HashMap.MapStore;

public static void main(String[] args) throws FileNotFoundException {
//    MapStore mapStore = new MapStore();
//
//    mapStore.set("username", "LeVy");
//    mapStore.set("email", "levy@example.com");
//    mapStore.set("country", "Australia");
//
//    System.out.println("Email: " + mapStore.get("email"));
//
//    mapStore.del("country");
//
//    System.out.println("Country after deletion: " + mapStore.get("country"));

    ConsoleEcho consoleEcho = new ConsoleEcho();
    consoleEcho.getContentFromFile("InputOutputLearning/dummy.txt", "InputOutputLearning/result.txt");
}
