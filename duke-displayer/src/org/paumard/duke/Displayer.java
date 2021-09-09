/**
 * Copyright 2021 José Paumard
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.paumard.duke;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Displayer extends JPanel {

    private File duke;
    private BufferedImage image;
    private Image buffer;

    public Displayer(File duke) {
        this.duke = duke;
    }


    public void addNotify() {
        super.addNotify();
        createBuffer();
    }

    public void createBuffer() {
        buffer = createImage(this.image.getWidth(), this.image.getHeight());
    }

    public void loadImage() {
        try {
            this.image = ImageIO.read(new FileInputStream(duke));
            this.setPreferredSize(new Dimension(this.image.getWidth(), this.image.getHeight()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        setBackground(Color.WHITE);
        if (buffer != null) {
            Graphics2D gBuffer = (Graphics2D) buffer.getGraphics();
            gBuffer.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
            graphics.drawImage(buffer, 0, 0, image.getWidth(), image.getHeight(), this);
        }
    }


    public static void main(String[] args) {

        File duke = new File(args[0]);
//        File duke = new File("C:\\Users\\José\\Desktop\\tmp\\france.duke");

        Displayer displayer = new Displayer(duke);
        displayer.loadImage();

        final JFrame frame = new JFrame("Duke Displayer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(displayer, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
