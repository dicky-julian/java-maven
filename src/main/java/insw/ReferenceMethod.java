package insw;

import java.util.function.Predicate;
import static insw.LambdaMethod.print;

public class ReferenceMethod {

    // calling static method reference
    public static void main(String[] args) {
        runStatic();
    }

    // calling non-static method reference
    public static void runStatic() {
        Predicate<String> predictLowerCase = ReferenceMethod::isLowerCase;
        print(predictLowerCase.test("Marsha"));
    }

    // calling non-static method reference
    public void runNonStatic() {
        // call from inline class (same class)
        Predicate<String> predictLowerCase = this::isLowerCaseNonStatic;

        // call from objects (difference class)
        ReferenceMethod app1 = new ReferenceMethod();
        Predicate<String> predictLowerCase1 = app1::isLowerCaseNonStatic;

        print(predictLowerCase.test("Marsha"));
    }

    // define method reference, calling by class::method
    // ex: ReferenceMethod::isLowerCase
    public static boolean isLowerCase(String value) {
        for (char str : value.toCharArray()) {
            if (!Character.isLowerCase(str)) {
                return false;
            }
        }
        return true;
    }

    // define as non-static method
    public boolean isLowerCaseNonStatic(String value) {
        for (char str : value.toCharArray()) {
            if (!Character.isLowerCase(str)) {
                return false;
            }
        }
        return true;
    }
}
