public static void main(String[] args) {
    MapStore mapStore = new MapStore();

    mapStore.set("username", "LeVy");
    mapStore.set("email", "levy@example.com");
    mapStore.set("country", "Australia");

    System.out.println("Email: " + mapStore.get("email"));

    mapStore.del("country");

    System.out.println("Country after deletion: " + mapStore.get("country"));
}
