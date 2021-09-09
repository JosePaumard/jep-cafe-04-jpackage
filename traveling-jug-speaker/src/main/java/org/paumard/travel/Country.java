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

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Country extends JPanel {

    private static final long serialVersionUID = 1L;

    public static int COUNTRY_WIDTH = 1200;
    public static int COUNTRY_HEIGHT = 1136;
    public static int MARGIN = 40;
    public static int TOWN_SIZE = 16;
    public static float STROKE_SIZE = 3.0f;
    private List<City> cities;

    private Image buffer;
    private Graphics2D gBuffer;

    private Travel travel;

    private int numberOfCities;
    private List<Integer> selectedIndexes;

    private Stroke stroke = new BasicStroke(STROKE_SIZE, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);


    public Country() {

        try (InputStream resource = Country.class.getResourceAsStream("/cities/cities.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(resource));
             Stream<String> lines = reader.lines();) {

            this.cities = lines.filter(line -> !line.startsWith("#"))
                    .map(City::of)
                    .toList();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initCities(int numberOfCities) {
        this.numberOfCities = numberOfCities;
        travel = new Travel(numberOfCities);
        List<Integer> cityIndexes = IntStream.range(0, cities.size()).boxed().collect(Collectors.toList());
        Collections.shuffle(cityIndexes);
        this.selectedIndexes = cityIndexes.subList(0, numberOfCities);

        addCitiesToTravel();
    }

    public void addCitiesToTravel() {
        travel = new Travel(numberOfCities);
        selectedIndexes.stream()
                .map(cities::get)
                .forEach(travel::addCity);
    }

    public void initTravel() {
        addCitiesToTravel();
        this.travel.init();
    }

    public void addNotify() {
        super.addNotify();
        createBuffer();
    }

    public void createBuffer() {

        buffer = createImage(COUNTRY_WIDTH + MARGIN, COUNTRY_HEIGHT + MARGIN);
        gBuffer = (Graphics2D) buffer.getGraphics();
    }

    public void drawImage() {

        if (gBuffer == null) {
            return;
        }

        try {
            BufferedImage image = ImageIO.read(Country.class.getResourceAsStream("/cities/france.jpg"));
            gBuffer.drawImage(image, 0, 0, COUNTRY_WIDTH + MARGIN, COUNTRY_HEIGHT + MARGIN, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        gBuffer.setColor(Color.RED);

        if (travel != null) {
            City[] cities = travel.getCities();

            for (City city : cities) {

                int xv = city.x();
                int yv = city.y();

                gBuffer.fillOval(xv - (TOWN_SIZE / 2), yv - (TOWN_SIZE / 2), TOWN_SIZE, TOWN_SIZE);
            }
        }
    }

    public void drawTravel() {

        if (gBuffer == null || travel == null) {
            return;
        }

        City[] travels = travel.getTravel();
        City city0 = travels[0];
        if (city0 == null) {
            return;
        }

        City city1 = travels[0];
        City city2 = null;

        gBuffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gBuffer.setColor(Color.red);

        GeneralPath path = new GeneralPath();
        path.moveTo(city1.x(), city1.y());
        for (int i = 1; i < travels.length; i++) {
            city2 = travels[i];

            path.lineTo(city2.x(), city2.y());

            city1 = city2;
        }
        path.lineTo(city0.x(), city0.y());

        gBuffer.setStroke(stroke);
        gBuffer.draw(path);
    }

    public void paintComponent(Graphics graphics) {

        super.paintComponent(graphics);

        setBackground(Color.LIGHT_GRAY);

        if (buffer != null) {
            drawImage();
            drawTravel();
            graphics.drawImage(buffer, 0, 0, COUNTRY_WIDTH + MARGIN, COUNTRY_HEIGHT + MARGIN, this);
        }
    }

    public Travel getTravel() {
        return travel;
    }
}
