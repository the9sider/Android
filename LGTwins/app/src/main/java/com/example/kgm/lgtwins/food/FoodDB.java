package com.example.kgm.lgtwins.food;

import com.example.kgm.lgtwins.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by KGM on 2016-06-20.
 */
public class FoodDB {

    public static List<FoodVO> foodList;
    public static List<Float> ratingList;

    private static List<FoodVO> testFoodList;
    private static FoodVO chicken;
    private static FoodVO hamburger;
    private static FoodVO pizza;

    public static void prepareData() {

        testFoodList = new ArrayList<FoodVO>();

        chicken = new FoodVO(
                R.drawable.chicken,
                "굽네",
                "치킨",
                "종합운동장역 6번출구"
        );
        testFoodList.add(chicken);

        hamburger = new FoodVO(
                R.drawable.kfc,
                "KFC",
                "햄버거",
                "종합운동장역 6번출구"
        );
        testFoodList.add(hamburger);

        pizza = new FoodVO(
                R.drawable.pizza,
                "도미노",
                "피자",
                "종합운동장역 6번출구"
        );
        testFoodList.add(pizza);

        ratingList = new ArrayList<Float>();
        ratingList.add(0.0f);
        ratingList.add(0.5f);
        ratingList.add(1.0f);
        ratingList.add(1.5f);
        ratingList.add(2.0f);
        ratingList.add(2.5f);
        ratingList.add(3.0f);
        ratingList.add(3.5f);
        ratingList.add(4.0f);
        ratingList.add(4.5f);
        ratingList.add(5.0f);

        foodList = new ArrayList<FoodVO>();

        for(int i = 0; i < 30; i++) {
            FoodVO testFood = testFoodList.get(new Random().nextInt(3));
            testFood.setRating(ratingList.get(new Random().nextInt(11)));
            foodList.add(testFood);
        }
    }

    public static List<FoodVO> getFoodList() {
        return foodList;
    }
}
