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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Travel {

	public static Random RAND = new Random(10L) ;

	private City[] cities;
	private City[] travel;


	public Travel(int n) {
		cities = new City[n] ;
		travel = new City[n] ;
	}

	public void addCity(City city) {

		for (int i = 0; i < cities.length ; i++) {
			if (cities[i] == null) {
				cities[i] = city;

				return ;
			}
		}
	}

	public City[] getCities() {
		return this.cities;
	}

	public City[] getTravel() {
		return this.travel;
	}

	public void init() {
		List<Integer> list = new ArrayList<>() ;
		for (int i = 0; i < cities.length ; i++) {
			list.add(i) ;
		}

		for (int k = 0; k < travel.length ; k++) {

			int i = RAND.nextInt(list.size()) ;
			int j = list.remove(i) ;
			travel[k] = cities[j] ;
		}
	}

	public double length(City[] travel) {

		double length = 0d ;

		City city0 = travel[0] ;

		City city1 = travel[0] ;
		City city2 = null ;
		for (int i = 1 ; i < travel.length ; i++) {
			city2 = travel[i] ;

			length += Math.sqrt((city2.x() - city1.x())*(city2.x() - city1.x()) +
					(city2.y() - city1.y())*(city2.y() - city1.y())) ;
			city1 = city2;
		}
		if (city2 != null) {
			length += Math.sqrt((city2.x() - city0.x())*(city2.x() - city0.x()) +
					(city2.y() - city0.y())*(city2.y() - city0.y())) ;
		}

		return length ;

	}

	private int loops = 0 ;

	public boolean isDone() {
		return loops > 1000 ;
	}

	public void commute(int k, double T) {

		double length1 = length(this.travel) ;

		City[] newTravel = this.travel.clone() ;

		int i = 0 ;
		int j = 0 ;

		if (newTravel.length > 1) {
			i = RAND.nextInt(newTravel.length) ;
			j = RAND.nextInt(newTravel.length) ;
			while (j == i) {
				j = RAND.nextInt(newTravel.length) ;
			}
			City v = newTravel[i] ;
			newTravel[i] = newTravel[j] ;
			newTravel[j] = v ;
		}

		double length2 = length(newTravel) ;

		if (length2 < length1) {
			this.travel = newTravel ;
			System.out.println("[-][" + k + "] length = " + length2) ;
			loops = 0 ;
		} else {

			double p = Math.exp(-(length2 - length1)/T) ;
			if (RAND.nextDouble() < p) {
				this.travel = newTravel ;
				System.out.println("[+][" + k + "] length = " + length2) ;
				loops = 0 ;
			} else {
				loops++ ;
			}
		}
	}

	public double getLength() {
		return length(this.travel) ;
	}
}

