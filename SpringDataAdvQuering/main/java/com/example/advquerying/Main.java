package com.example.advquerying;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.entities.Size;
import com.example.advquerying.services.IngredientService;
import com.example.advquerying.services.ShampooService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Main implements CommandLineRunner {

    private ShampooService shampooService;
    private IngredientService ingredientService;

    public Main(ShampooService shampooService, IngredientService ingredientService) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {

        Scanner sc = new Scanner(System.in);

        //Task 1;
//        System.out.println("Enter size(SMALL, MEDIUM, LARGE) : ");
//        Size size = Size.valueOf(sc.nextLine());
//        printAllBySize(size);

        //Task 2;
//        System.out.println("Enter size(SMALL, MEDIUM, LARGE) : ");
//        Size size = Size.valueOf(sc.nextLine());
//        System.out.println("Enter Label id: ");
//        long labelId = Long.parseLong(sc.nextLine());
//        printAllBySizeAndLabelId(size, labelId);

        //Task 3;
//        System.out.println("Enter price: ");
//        BigDecimal price = BigDecimal.valueOf(Long.parseLong(sc.nextLine()));
//        printAllWithPriceGreaterThan(price);

        //Task 4;
//        System.out.println("Enter a letter: ");
//        String startingLetter = sc.nextLine().toUpperCase();
//        findIngredientsStartingWith(startingLetter);

        //Task 5
//        System.out.println("Enter ingredients: ");
//        List<String> ingredients = Arrays.asList(sc.nextLine().split(" "));
//        this.ingredientService.getAllByNameInOrderByPrice(ingredients)
//                .forEach(System.out::println);

        //Task 6;
//        System.out.println("Enter a price: ");
//        BigDecimal price = new BigDecimal(Long.parseLong(sc.nextLine()));
//        System.out.println(this.shampooService.countAllByPriceIsLessThan(price));

        //Task 7;
//        System.out.println("Enter ingredients: ");
//        List<String> ingredients = Arrays.stream(sc.nextLine().split("\\s+")).toList();
//        printShampoosUsingIngredients(ingredients);

        //Task 8;
//        System.out.println("Please enter count: ");
//        int count = Integer.parseInt(sc.nextLine());
//        this.shampooService.selectShampoosByIngredientCount(count)
//                .forEach(System.out::println);

        //Task 9;
//        System.out.println("Please enter name: ");
//        String name = sc.nextLine();
//        this.ingredientService.deleteByName(name);

        //Task 10;
//        this.ingredientService.updatePriceBy10percent();

        //Task 11;
        double amount = Double.parseDouble(sc.nextLine());
        List<String> names = Arrays.asList(sc.nextLine().split(" "));
        this.ingredientService.updatePriceByAmountOnGivenNames(amount, names);
    }

    private void printShampoosUsingIngredients(List<String> ingredients) {
        this.shampooService
                .selectShampoosByIngredients(ingredients)
                .forEach(System.out::println);
    }

    private void findIngredientsStartingWith(String startingLetter) {
        this.ingredientService.findAllByNameStartingWith(startingLetter)
                .stream()
                .map(Ingredient::getName)
                .forEach(System.out::println);
    }

    private void printAllWithPriceGreaterThan(BigDecimal price) {
        this.shampooService.findAllByPriceGreaterThanOrderByPriceDesc(price)
                .stream()
                .map(s -> String.format("%s %s %.2flv.", s.getBrand(),
                        s.getSize().name(),
                        s.getPrice()))
                .forEach(System.out::println);
    }

    private void printAllBySizeAndLabelId(Size size, long labelId) {
        this.shampooService.findAllBySizeOrLabel_IdOrderByPriceDesc(size, labelId)
                .stream()
                .map(s -> String.format("%s %s %.2flv.", s.getBrand(),
                        s.getSize().name(),
                        s.getPrice()))
                .forEach(System.out::println);
    }

    private void printAllBySize(Size size) {
        this.shampooService.findAllBySizeOrderById(size)
                .stream().map(shampoo -> String.format("%s %s %.2flv.",
                        shampoo.getBrand(),
                        shampoo.getSize().name(),
                        shampoo.getPrice()))
                .forEach(System.out::println);
    }
}
