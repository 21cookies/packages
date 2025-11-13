

interface Manufacture {
    void produceEngine();
    void assembleBody();
}

class Produce implements Manufacture {
    public void produceEngine() { System.out.println("Producing engine parts"); }
    public void assembleBody() { System.out.println("Assembling body parts"); }
}

class Assemble implements Manufacture {
    public void produceEngine() { System.out.println("Assemble specialized engine"); }
    public void assembleBody() { System.out.println("Assemble specialized body"); }
}

abstract class Vehicle {
    protected Manufacture manufacture;
    protected Vehicle(Manufacture m) { this.manufacture = m; }
    public abstract void build();
}


class Car extends Vehicle {
    public Car(Manufacture m) { super(m); }
    public void build() {
        System.out.println("Building car:");
        manufacture.produceEngine();
        manufacture.assembleBody();
    }
}

class Bike extends Vehicle {
    public Bike(Manufacture m) { super(m); }
    public void build() {
        System.out.println("Building bike:");
        manufacture.produceEngine();
        manufacture.assembleBody();
    }
}

class BridgeDemo {
    public static void main(String[] args) {
        Manufacture produce = new Produce();
        Manufacture assemble = new Assemble();

        Vehicle car = new Car(produce);
        Vehicle bike = new Bike(assemble);

        car.build();
        bike.build();
    }
}

