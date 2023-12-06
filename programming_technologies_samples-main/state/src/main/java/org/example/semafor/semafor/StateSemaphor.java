package org.example.semafor.semafor;

import org.example.semafor.graphicsModel.GraphicsModel;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.example.semafor.semafor.ColorEnum.*;

public class StateSemaphor implements Runnable {

    ChangeColor green;
    ChangeColor red;
    ChangeColor yellow;
    ChangeColor BlinkGreen;
    ChangeColor state;
    ChangeColor oldState;
    GraphicsModel gm;
    ColorEnum colorEnum;
    boolean suspendFlag = false;
    final int TIMES = 5;// кол-во миганий(констанкта)
    int times;

    public StateSemaphor(GraphicsModel model) {
        green = new Green();
        red = new Red();
        yellow = new Yellow();
        BlinkGreen = new BlinkGreen();
        state = red;
        oldState = red;
        gm = model;
        colorEnum = ColorEnum.GreenYellowTRed;
        suspendFlag = false;
    }

    public ColorEnum print() {
        return colorEnum;
    }

    public void changeState() {
        state.changeColor();
        gm.setColor(colorEnum);
    }

    @Override
    public void run() {
        for (int i = 0; i < 200; i++) {
            changeState();
            stop();
        }

    }

    private void stop() {
        try {
            Thread.sleep(500);
            synchronized (this) {
                while (suspendFlag) {
                    wait();
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(StateSemaphor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void mysuspend() {
        suspendFlag = true;
    }

    public synchronized void myresume() {
        suspendFlag = false;
        notify();
    }

    public interface ChangeColor { // состояние

        void changeColor();
    }

    public class Green implements ChangeColor {

        @Override
        public void changeColor() {
            times--;
            if(times > 0) { //
                oldState = green; //
                state = BlinkGreen; //
                colorEnum = GreenYellowRed; //
            }
            else{
                oldState = green;
                state = yellow;
                colorEnum = GreenTYellowRed;
                }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Green.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public class Red implements ChangeColor {

        @Override
        public void changeColor() {
            times = TIMES;
            oldState = red;
            state = yellow;
            colorEnum = GreenTYellowRed;
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Red.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public class Yellow implements ChangeColor {

        @Override
        public void changeColor() {
            if (oldState == red) {
                state = green;
                oldState = yellow;
                colorEnum = TGreenYellowRed;
            } else {
                state = red;
                oldState = yellow;
                colorEnum = GreenYellowTRed;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Yellow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public class BlinkGreen implements ChangeColor{
        @Override
        public void changeColor() {
            oldState = BlinkGreen;
            state = green;
            colorEnum = TGreenYellowRed;
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Green.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
