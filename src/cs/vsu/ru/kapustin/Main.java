package cs.vsu.ru.kapustin;

import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        Locale.setDefault(Locale.ROOT);

        java.awt.EventQueue.invokeLater(() -> new StartFrame().setVisible(true));
    }
}
