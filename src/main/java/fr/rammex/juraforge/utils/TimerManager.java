package fr.rammex.juraforge.utils;

import org.bukkit.entity.Player;
import java.util.Timer;
import java.util.TimerTask;

public class TimerManager {

    public void start15SecondTimer(Player player, TimerListener listener) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Timer de 15 secondes terminé !");
                timer.cancel();
                if (listener != null) {
                    listener.onTimerFinished(player);
                }
            }
        }, 15000); // 15000 millisecondes = 15 secondes
    }
}