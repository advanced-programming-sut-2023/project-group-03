package model.server;

public enum ClientType {

    USER("user"),
    WORKER("worker"),
    NON("non"),
    ;
    private String name;

    ClientType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
