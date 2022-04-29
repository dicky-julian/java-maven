package insw;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class LambdaMethod
{
    static String text = "Marsha Lenathea";
    static void print(Object text) {
        System.out.println(text);
    }

    public static void main( String[] args ) {
        print(getHello(null));
    }

    // implements interface by anonymous class
    public static void anonymousClass() {
        // case 1 :
        SimpleAction simpleAction = new SimpleAction() {
            @Override
            public String action(String name) {
                return text;
            }
        };
        print(simpleAction.action(text));

        // case 2 :
        Consumer<String> consumer = new Consumer<>() {
            @Override
            public void accept(String name) {
                print(name);
            }
        };
        consumer.accept(text);

        // case 3 :
        Function<String, Integer> functionLength = new Function<>() {
            @Override
            public Integer apply(String name) {
                return name.length();
            }
        };
        print(functionLength.apply(text));

        // case 4 :
        Predicate<String> predicate = new Predicate<>() {
            @Override
            public boolean test(String name) {
                return name.isBlank();
            }
        };
        print(predicate.test(text));

        // case 5 :
        Supplier<String> supplier = new Supplier<>() {
            @Override
            public String get() {
                return "this is empty supplier";
            }
        };
        print(supplier.get());
    }

    // implements interface by anonymous function (lambda)
    public static void anonymousFunction() {
        // case 1 :
        SimpleAction simpleAction1 = (String name) -> {
            return name;
        };
        print(simpleAction1.action(text));

        // or with some shorter template :

        SimpleAction simpleAction2 = (String name) -> name;
        print(simpleAction2.action(text));

        // case 2 :
        // with Consumer interface from java.util.function that
        // provide a function just to accept anonymous function
        // public interface Consumer<Argument's type>
        Consumer<String> consumer = LambdaMethod::print;
        consumer.accept(text);

        // case 3 :
        // with Function interface that allow us to create anonymous
        // function with result (non-void)
        // public interface Function<Argument's type, Result's type>
        Function<String, Integer> functionLength = String::length;
        print(functionLength.apply(text));

        // case 4 :
        // with Predicate interface that creates a function with boolean type result
        // public interface Predicate<Argument's type>
        Predicate<String> predicate = String::isBlank;
        print(predicate.test(text));

        // case 5 :
        // with Supplier interface, the same tool likes Function that create anonymous
        // function with result (non-void), but without some argument
        // public interface Supplier<Result's type>
        Supplier<String> supplier = () -> "this is empty supplier";
        print(supplier.get());
    }

    // penggunaan lambda method secara otomatis menerapkan lazy parameter
    public static void lazyParameter() {
        getProfile(21, LambdaMethod::getName);
    }

    // secara default, parameter akan dimuat walaupun tidak dibutuhkan
    // dengan lazy, parameter hanya akan disiapkan ketika dibutuhkan saja
    public static void getProfile(int age, Supplier<String> name) {
        if (age > 20) {
            print(name.get() + " lulus uji kedewasaan");
        } else {
            // case "else" tidak akan memuat parameter name (tidak memanggil getName())
            print("Tidak lulus uji kedewasaan");
        }
    }

    public static String getName() {
        print("Method getName() dipanggil!");
        return "Marsha Lenathea";
    }

    // optional in lambda
    // validate parameter of function, which just run when parameter is not null
    public static void sayHello(String name) {
        // LambdaMethod::print will run while name is not null
        // this is help us to avoid NullPointerException
        Optional.ofNullable(name)
                .map(String::toUpperCase)
                .ifPresent(LambdaMethod::print);

        // VS

        Optional<String> optionalName = Optional.ofNullable(name);
        Optional<String> optionalNameUpper = optionalName.map(String::toUpperCase);
        optionalNameUpper.ifPresent(LambdaMethod::print);
    }

    // optional in lambda with else case
    public static void sayHelloElse(String name) {
        Optional.ofNullable(name)
                .map(String::toUpperCase)
                .ifPresentOrElse(
                        value -> print("Hello " + value),
                        () -> print("Just Hello")
                );
    }

    // optional in lambda with return value
    public static String getHello(String name) {
        String newName = Optional.ofNullable(name)
                .map(String::toUpperCase)
                .orElse("Anonymous");
        return "Hello " + newName;
    }
}
