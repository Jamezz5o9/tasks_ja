package store;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import store.data.dto.CustomerRegistrationRequest;
import store.data.dto.CustomerRegistrationResponse;
import store.data.dto.LoginRequest;
import store.data.dto.LoginResponse;
import store.service.CustomerService;
import store.service.CustomerServiceImpl;
import store.service.ProductService;
import store.service.ProductServiceImpl;

import java.util.Scanner;

import static java.lang.System.exit;


public class UnicornStoreApplication {
    private static CustomerService customerService = new CustomerServiceImpl();
    private ProductService productService = new ProductServiceImpl();
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        startStoreApp();
    }

    private static void startStoreApp() {
        System.out.println("""
                Welcome to unicorn store how might we help you?
                1. Register
                2. Login
                3. Exit              
                 """);

        String userChoice = inputStr("Select an option: ");
        switch(userChoice){
            case "1" -> collectCustomerRegistrationDetails();
            case "2" -> collectUserLoginDetails();
            case "3" -> exit(3);
        }
    }

    private static void collectUserLoginDetails(){
        String email = inputStr("Enter your email");
        String password = inputStr("Enter your password");
        LoginResponse response = buildCustomerLoginRequest(email, password);
        try{
            String jsonResponse = objectMapper.writeValueAsString(response);
            System.out.println(jsonResponse);
        }
        catch(JsonProcessingException e){
            System.err.println(e.getMessage());
            exit(1);
        }
        startStoreApp();
    }

    private static LoginResponse buildCustomerLoginRequest(String email, String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);
        input.nextLine();
        return customerService.login(loginRequest);
    }

    private static void collectCustomerRegistrationDetails(){
        String email = inputStr("Enter your Email");
        String password = inputStr("Enter your password");
        String phoneNumber = inputStr("Enter your Phone number");
        String address = inputStr("Enter your address: ");
        input.nextLine();
        CustomerRegistrationResponse response = buildCustomerRegistrationRequest(email, password, phoneNumber, address);
        try{
            String jsonResponse = objectMapper.writeValueAsString(response);
            System.out.println(jsonResponse);
        }
        catch(JsonProcessingException e){
            System.err.println(e.getMessage());
            exit(1);
        }
        startStoreApp();
    }
    private static CustomerRegistrationResponse buildCustomerRegistrationRequest(String email, String password, String phoneNumber, String address){
        CustomerRegistrationRequest request = new CustomerRegistrationRequest();
        request.setEmail(email);
        request.setPassword(password);
        request.setPhoneNumber(phoneNumber);
        request.setAddress(address);
        return customerService.register(request);
    }

    private static String inputStr(String s) {
       prompt(s);
       return input.next();
    }

    private static int inputInt(String e) {
        prompt(e);
        return input.nextInt();
    }

    private static void prompt(String e) {
        System.out.println(e);
    }

}
