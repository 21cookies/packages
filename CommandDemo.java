

import java.util.*;

interface Command {
    void execute();
    void undo();
}


class Light {
    private final String name;
    private boolean on;
    public Light(String name) { this.name = name; this.on = false; }
    public void on() { on = true; System.out.println(name + " light ON"); }
    public void off() { on = false; System.out.println(name + " light OFF"); }
}
class Fan {
    private boolean running;
    public void start() { running = true; System.out.println("Fan started"); }
    public void stop() { running = false; System.out.println("Fan stopped"); }
}

// Concrete commands
class TurnOnLightCommand implements Command {
    private final Light light;
    public TurnOnLightCommand(Light l) { this.light = l; }
    public void execute() { light.on(); }
    public void undo() { light.off(); }
}
class TurnOffLightCommand implements Command {
    private final Light light;
    public TurnOffLightCommand(Light l) { this.light = l; }
    public void execute() { light.off(); }
    public void undo() { light.on(); }
}
class StartFanCommand implements Command {
    private final Fan fan;
    public StartFanCommand(Fan f) { this.fan = f; }
    public void execute() { fan.start(); }
    public void undo() { fan.stop(); }
}


class RemoteControl {
    private final Queue<Command> queue = new LinkedList<>();
    private final Deque<Command> history = new ArrayDeque<>();

    public void executeCommand(Command c) {
        c.execute();
        history.push(c);
    }

    public void queueCommand(Command c) { queue.add(c); }

    public void runQueue() {
        while (!queue.isEmpty()) {
            Command c = queue.poll();
            c.execute();
            history.push(c);
        }
    }

    public void undo() {
        if (!history.isEmpty()) {
            Command c = history.pop();
            c.undo();
        } else {
            System.out.println("Nothing to undo");
        }
    }
}

// Demo
class CommandDemo {
    public static void main(String[] args) {
        Light living = new Light("LivingRoom");
        Fan fan = new Fan();

        RemoteControl remote = new RemoteControl();
        Command on = new TurnOnLightCommand(living);
        Command startFan = new StartFanCommand(fan);
        Command off = new TurnOffLightCommand(living);

        remote.executeCommand(on);
        remote.executeCommand(startFan);

        remote.queueCommand(off);
        remote.runQueue();

        remote.undo(); 
    }
}

