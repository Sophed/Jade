package systems.soph.jade.register;

import systems.soph.jade.Jade;
import systems.soph.jade.event.*;

public class EventRegister {
    public EventRegister(Jade p) {
        // Register Events
        new PlayerJoin(p);
        new PlayerQuit(p);
        new PlayerChat(p);
        new PlayerInteract(p);
        new ExploitPrevention(p);
        new EntityDamage(p);
        new EntityLifetimes(p);
    }
}
