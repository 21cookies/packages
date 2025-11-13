


class Order {
    public final String id;
    public final String type; 
    public final double baseAmount;
    public Order(String id, String type, double baseAmount) {
        this.id = id; this.type = type; this.baseAmount = baseAmount;
    }
}

// Validation
interface IValidator {
    void validate(Order o) throws IllegalArgumentException;
}
class BasicOrderValidator implements IValidator {
    public void validate(Order o) {
        if (o.id == null || o.id.isEmpty()) throw new IllegalArgumentException("Order id required");
        if (o.baseAmount < 0) throw new IllegalArgumentException("Amount negative");
    }
}


interface IPricingStrategy {
    double calculatePrice(Order o);
}
class StandardPricing implements IPricingStrategy {
    public double calculatePrice(Order o) { return o.baseAmount; }
}
class PremiumPricing implements IPricingStrategy {
    public double calculatePrice(Order o) { return o.baseAmount * 0.9; } // 10% discount
}

// Persistence (DIP)
interface IOrderRepository {
    void save(Order o, double finalAmount);
}
class FileOrderRepository implements IOrderRepository {
    public void save(Order o, double finalAmount) {
        // simple print to simulate save (replace with real file/db persistence)
        System.out.println("Saved order " + o.id + " amount " + finalAmount);
    }
}

interface IOrderTypeStrategy {
    boolean supports(Order o);
    IPricingStrategy pricing();
}
class StandardOrderTypeHandler implements IOrderTypeStrategy {
    public boolean supports(Order o) { return "STANDARD".equalsIgnoreCase(o.type); }
    public IPricingStrategy pricing() { return new StandardPricing(); }
}
class PremiumOrderTypeHandler implements IOrderTypeStrategy {
    public boolean supports(Order o) { return "PREMIUM".equalsIgnoreCase(o.type); }
    public IPricingStrategy pricing() { return new PremiumPricing(); }
}


class OrderProcessor {
    private final IValidator validator;
    private final IOrderRepository repo;
    private final IOrderTypeStrategy[] handlers;

    public OrderProcessor(IValidator validator, IOrderRepository repo, IOrderTypeStrategy... handlers) {
        this.validator = validator;
        this.repo = repo;
        this.handlers = handlers;
    }

    public void process(Order o) {
        validator.validate(o);
        IPricingStrategy pricing = null;
        for (IOrderTypeStrategy h : handlers) {
            if (h.supports(o)) { pricing = h.pricing(); break; }
        }
        if (pricing == null) throw new IllegalArgumentException("No handler for type " + o.type);
        double finalAmt = pricing.calculatePrice(o);
        repo.save(o, finalAmt);
    }
}

class SOLIDDemo {
    public static void main(String[] args) {
        IValidator validator = new BasicOrderValidator();
        IOrderRepository repo = new FileOrderRepository();
        IOrderTypeStrategy[] handlers = { new StandardOrderTypeHandler(), new PremiumOrderTypeHandler() };

        OrderProcessor processor = new OrderProcessor(validator, repo, handlers);
        processor.process(new Order("O1", "STANDARD", 100));
        processor.process(new Order("O2", "PREMIUM", 200));
    }
}

