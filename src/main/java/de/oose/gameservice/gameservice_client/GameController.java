package de.oose.gameservice.gameservice_client;

public class GameController {
    Thread housekeeper;

    private void update() {

    }

    public void initialize() {
        if (ClientApplication.api.isGod()) {
//            godfield.show();
        } else {
//            godfield.hide()
            while (!ClientApplication.api.isStarted()) {
            }
        }
        try {
            housekeeper = new Thread(() -> {
                while (true) {
                    this.update();
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                    }
                }
            });
            housekeeper.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
