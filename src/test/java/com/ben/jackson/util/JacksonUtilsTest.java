package com.ben.jackson.util;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * — <br>
 *
 * @author: 刘恒 <br>
 * @date: 2019/7/3 <br>
 */
class JacksonUtilsTest {

    public static class People {
        private String name;
        private Integer age;
        private Boolean gender;
        private Double height;

        public Double getHeight() {
            return height;
        }

        public void setHeight(Double height) {
            this.height = height;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Boolean getGender() {
            return gender;
        }

        public void setGender(Boolean gender) {
            this.gender = gender;
        }
    }

    @Test
    void parseMap() throws Exception {
        People people = new People();
        people.setName("asdf");
        people.setAge(30);
        people.setGender(true);
        people.setHeight(7.3);

        String str = JacksonUtils.toJsonString(people);


        HashMap<String, Object> stringObjectHashMap = JacksonUtils.parseHashMap(str);

        boolean gender = TypeUtils.castToBoolean(stringObjectHashMap.get("gender"));
        System.out.println(gender);
        int age = TypeUtils.castToInt(stringObjectHashMap.get("age"));
        System.out.println(age);
        String name = TypeUtils.castToString(stringObjectHashMap.get("name"));
        System.out.println(name);
        double height = TypeUtils.castToDouble(stringObjectHashMap.get("height"));
        System.out.println(height);

    }

    @Test
    void parseArray() {
        People people1 = new People();
        people1.setName("asdf1");
        people1.setAge(31);
        people1.setGender(true);

        People people2 = new People();
        people2.setName("asdf2");
        people2.setAge(32);
        people2.setGender(false);

        People people3 = new People();
        people3.setName("asdf3");
        people3.setAge(33);
        people3.setGender(true);

        List<People> peoples = new ArrayList<People>();
        peoples.add(people1);
        peoples.add(people2);
        peoples.add(people3);

        String str = JacksonUtils.toJsonString(peoples);

        People[] peopleArray = JacksonUtils.parseArray(str, People.class);

        List<People> peopleList = JacksonUtils.parseList(str, People.class);

        System.out.println(peopleArray.length);
    }
}