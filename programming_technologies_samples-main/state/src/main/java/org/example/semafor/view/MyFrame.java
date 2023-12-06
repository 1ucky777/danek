/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.semafor.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import org.example.semafor.semafor.StateSemaphor;

public class MyFrame extends JFrame {

    MyPanel myPanel;
    StateSemaphor ss;
//    Controller controller;

    public MyFrame(MyPanel myPanel, StateSemaphor s) {
        this.myPanel = myPanel;
        ss = s;
        JToolBar bar = new JToolBar();
        add(bar, BorderLayout.NORTH);
        JMenuItem item = new JMenuItem(new ImageIcon("images/start.png"));
        JMenuItem item1 = new JMenuItem(new ImageIcon("images/stop.png"));
        JMenuItem item2 = new JMenuItem(new ImageIcon("images/continue.png"));
        item1.setEnabled(false);
        item2.setEnabled(false);
        item.setEnabled(true);
        bar.add(item);
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(ss).start();
                item1.setEnabled(true);
                item2.setEnabled(false);
                item.setEnabled(false);
            }
        });

        bar.add(item1);
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ss.mysuspend();
                item.setEnabled(false);
                item2.setEnabled(true);
                item1.setEnabled(false);
            }
        });

        bar.add(item2);
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ss.myresume();
                item.setEnabled(false);
                item2.setEnabled(false);
                item1.setEnabled(true);
            }
        });
        add(this.myPanel);
        this.setSize(350, 780);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
