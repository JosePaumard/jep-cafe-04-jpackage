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
package org.paumard.travel;

import javax.swing.*;
import java.awt.*;

public class TravelingJUGSpeaker {

    public static void main(String[] args) {

        final JFrame frame = new JFrame("Traveling JUG Speaker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final Country country = new Country();
        country.setPreferredSize(new Dimension(Country.COUNTRY_WIDTH + Country.MARGIN, Country.COUNTRY_HEIGHT + Country.MARGIN));

        BorderLayout l = new BorderLayout();

        JButton readCities = new JButton("Cities");
        JButton start = new JButton("Start");

        JPanel buttonPanel = new JPanel();
        final JTextField numberOfCitiesTextField = new JTextField("10");
        numberOfCitiesTextField.setPreferredSize(new Dimension(40, 20));

        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(numberOfCitiesTextField);
        buttonPanel.add(readCities);
        buttonPanel.add(start);

        readCities.addActionListener(e -> {
            int numberOfCities = Integer.parseInt(numberOfCitiesTextField.getText());
            country.initCities(numberOfCities);
            SwingUtilities.invokeLater(country::repaint);
        });

        start.addActionListener(e -> {

			Runnable r = () -> {
				country.initTravel();
                country.setPreferredSize(new Dimension(Country.COUNTRY_WIDTH + Country.MARGIN, Country.COUNTRY_HEIGHT + Country.MARGIN));
                frame.getContentPane().add(country, BorderLayout.CENTER);

                country.getTravel().init();
                int k = 0;
                double T = 300d;
                while (country.getTravel().getLength() > 1450d && !country.getTravel().isDone()) {
                    SwingUtilities.invokeLater(country::repaint);

                    country.getTravel().commute(k, T);

                    T = 0.99995d * T;
                    k++;

                    if (k % 50 == 0) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e1) {
                        }
                    }
                }
                if (country.getTravel().isDone()) {
                    System.out.println("[" + k + "] DONE");
                }
            };

            Thread t = new Thread(r);
            t.start();
        });

        frame.getContentPane().setLayout(l);
        frame.getContentPane().add(country, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.EAST);

        frame.pack();
        frame.setVisible(true);
    }
}
