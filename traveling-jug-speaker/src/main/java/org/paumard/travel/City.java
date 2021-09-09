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

public record City(int x, int y, String name) {

    public static City of(String line) {
        String[] elements = line.split("[ ,]+");
        String name = elements[0];
        int x = Integer.parseInt(elements[1]);
        int y = Integer.parseInt(elements[2]);
        return new City(x, y, name);
    }
}
