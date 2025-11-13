/*
Q7. Factory Method for PizzaStore:
 - Abstract PizzaStore defines orderPizza() that uses createPizza() factory method.
 - Concrete stores override createPizza to return particular Pizza implementations.
*/

abstract class Pizza {
    public abstract String name();
    public void prepare() { System.out.println("Preparing " + name()); }
    public void bake() { System.out.println("Baking " + name()); }
    public void cut() { System.out.println("Cutting " + name()); }
}

class NYCheesePizza extends Pizza { public String name() { return "NY Cheese Pizza"; } }
class ChicagoCheesePizza extends Pizza { public String name() { return "Chicago Cheese Pizza"; } }
class NYVeggiePizza extends Pizza { public String name() { return "NY Veggie Pizza"; } }
class ChicagoVeggiePizza extends Pizza { public String name() { return "Chicago Veggie Pizza"; } }

abstract class PizzaStore {
    public Pizza orderPizza(String type) {
        Pizza pizza = createPizza(type);
        pizza.prepare(); pizza.bake(); pizza.cut();
        return pizza;
    }
    protected abstract Pizza createPizza(String type);
}

class NYPizzaStore extends PizzaStore {
    protected Pizza createPizza(String type) {
        if ("cheese".equalsIgnoreCase(type)) return new NYCheesePizza();
        if ("veggie".equalsIgnoreCase(type)) return new NYVeggiePizza();
        throw new IllegalArgumentException("Unknown type");
    }
}

class ChicagoPizzaStore extends PizzaStore {
    protected Pizza createPizza(String type) {
        if ("cheese".equalsIgnoreCase(type)) return new ChicagoCheesePizza();
        if ("veggie".equalsIgnoreCase(type)) return new ChicagoVeggiePizza();
        throw new IllegalArgumentException("Unknown type");
    }
}

// Demo
class FactoryMethodDemo {
    public static void main(String[] args) {
        PizzaStore ny = new NYPizzaStore();
        ny.orderPizza("cheese");
        PizzaStore ch = new ChicagoPizzaStore();
        ch.orderPizza("veggie");
    }
}
