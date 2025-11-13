
interface Coffee {
    String getDescription();
    double cost();
}

class BasicCoffee implements Coffee {
    public String getDescription() { return "Basic Coffee"; }
    public double cost() { return 50.0; }
}

// Base decorator
abstract class CoffeeDecorator implements Coffee {
    protected final Coffee wrapped;
    protected CoffeeDecorator(Coffee c) { this.wrapped = c; }
    public String getDescription() { return wrapped.getDescription(); }
    public double cost() { return wrapped.cost(); }
}

class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee c) { super(c); }
    public String getDescription() { return super.getDescription() + ", Milk"; }
    public double cost() { return super.cost() + 10; }
}

class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee c) { super(c); }
    public String getDescription() { return super.getDescription() + ", Sugar"; }
    public double cost() { return super.cost() + 5; }
}

class SyrupDecorator extends CoffeeDecorator {
    public SyrupDecorator(Coffee c) { super(c); }
    public String getDescription() { return super.getDescription() + ", Syrup"; }
    public double cost() { return super.cost() + 15; }
}

// Demo
class DecoratorDemo {
    public static void main(String[] args) {
        Coffee c = new BasicCoffee();
        c = new MilkDecorator(c);
        c = new SugarDecorator(c);
        System.out.println(c.getDescription() + " -> Rs." + c.cost());
    }
}

