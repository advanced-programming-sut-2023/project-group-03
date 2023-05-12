package Model;

import java.util.Objects;

public abstract class Element {

    private static int counter = 0;
    private int id;

    public Element() {
        counter++;
        id = counter;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Element.counter = counter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element element = (Element) o;
        return id == element.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
