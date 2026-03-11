import java.util.Scanner;

public class PasswordChecker {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        int length = password.length();

        for(int i = 0; i < length; i++) {

            char ch = password.charAt(i);

            if(Character.isUpperCase(ch))
                hasUpper = true;

            else if(Character.isLowerCase(ch))
                hasLower = true;

            else if(Character.isDigit(ch))
                hasDigit = true;

            else
                hasSpecial = true;
        }

        if(length >= 8 && hasUpper && hasLower && hasDigit && hasSpecial)
            System.out.println("Strong Password");

        else if(length >= 6 && (hasUpper || hasLower) && hasDigit)
            System.out.println("Medium Password");

        else
            System.out.println("Weak Password");

        sc.close();
    }
}