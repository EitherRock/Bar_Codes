// Nathan D. Lesmann
// Bar Codes
// February 18th, 2019
import java.util.Scanner;
public class Code
{
    public static final int MOD_NUMBER = 10;
    public static final int TENS = 10;
    public static final int HUNDREDS = 100;
    public static final int THOUSANDS = 1000;
    public static final int TEN_THOUSANDS = 10000;
    /**
     * Asks the user for a valid barcode
     *
     *  @param prompt whats displayed to ask the user to input a barcode
     *
     * @return valid barcode to be used
     */
    public static String getBarCode(String prompt) {

        var scan = new Scanner(System.in);
        var input = scan.nextLine();
        System.out.print(prompt);
        while (!scan.hasNextLine()) {
            System.out.println("Invalid, please try again.");
            scan.nextLine(); // .nextLine to rid of the wrong input
            System.out.print(prompt);
        }
        var valid_length = 32;
        while (input.length() != valid_length) // length of barcode needs to be 32 chars long
        {
            System.out.println("Not enough characters or too many characters, try again.");
            scan.nextLine();
            System.out.print(prompt);
            input = scan.nextLine();
        }
        while (input.charAt(0) != '|') // beginning of barcode needs to have '|'
        {
            System.out.println("Beginning of barcode must have '|'.");
            scan.nextLine();
            System.out.print(prompt);
            input = scan.nextLine();
        }
        while (input.charAt(32) != '|') // checks if the end of barcode has '|'
        {
            System.out.println("End of barcode must have '|'.");
            scan.nextLine();
            System.out.print(prompt);
            input = scan.nextLine();
        }

        for (var count = 0; count < input.length(); count++) // checks if only : and | are used
        {
            var index = input.charAt(count);
            if (index != ':' && index != '|')
            {
                System.out.print("Wrong input, try again. ");
                scan.nextLine();
                System.out.print(prompt);
                input = scan.nextLine();
            }

        }
        return input;
    }
    /**
     * Displays prompt and asks the user for an integer. If integer is not provided,
     * then the user will be prompted to try again until the correct value is entered.
     *
     * @param prompt the display that will be shown to the user
     *
     * @return the integer that was provided by the user.
     */
    public static int getInteger(String prompt) //get an integer from the user using the given prompt
    {
        var scan = new Scanner(System.in);
        System.out.print(prompt);
        while (!scan.hasNextInt())
        {
            System.out.println("Invalid, please try again.");
            scan.nextLine(); // .nextLine to rid of the wrong input
            System.out.print(prompt);
        }
        return scan.nextInt();
    }
    /**
     * User enters number and then this method checks to see if the number is in range for the application
     * If the number is too low or too high the user will be prompted to try again.
     *
     * @param prompt display asking the user to enter a number
     *
     * @param min the minimum amount the user can enter
     *
     * @param max the maximum amount the user can enter
     *
     * @return the number that is between min and max.
     */
    public static int getInteger(String prompt, int min, int max) // get an integer from the user with
    // the given prompt within the given range - min and max inclusive
    {
        if (min > max)
        {
            return min - 1;
        } // number is in range
        var input = getInteger(prompt);
        while (input < min || input > max)
        {
            // grabs the number if the input is in range
            System.out.println("Number is not in range, please try again.");
            input = getInteger(prompt);
        }
        return input;
    }
    /**
     * Turns a single digit into the 5 char symbol fragment for the barcode.
     *
     * @param digit This is the digit the method pulls from the integer the user enters.
     *
     * @return once the digit is turned into the barcode symbol, the symbol gets returned.
     */
    public static String encodeDigit(int digit) // given a digit get the symbol for that digit, (1 = :::||)
    {
            // prints wrong input if the input does not meet the if requirements.
            var symbol = "Wrong input";
            // compares the digit to the numbers and prints out the corresponding symbol for the number
            if (digit == 0)
                 symbol = "||:::";
            if (digit == 1)
                symbol = ":::||";
            if (digit == 2)
                symbol = "::|:|";
            if (digit == 3)
                symbol = "::||:";
            if (digit == 4)
                symbol = ":|::|";
            if (digit == 5)
                symbol = ":|:|:";
            if (digit == 6)
                symbol = ":||::";
            if (digit == 7)
                symbol = "|:::|";
            if (digit == 8)
                symbol = "|::|:";
            if (digit == 9)
                symbol = "|:|::";
            return symbol;
    }

    /**
     * grabs single digits from an integer and turns each single digit into a symbol and combines those digits
     * together to make a bar code.
     *
     * @param zipCode 5 digit code to be turned into bar code
     *
     * @return individual symbols combined together to create the barcode.
     */
    public static String encodeZipCode(int zipCode) // given the zipcode, return the
    // entire bar code for the zipcode - 32 chars long
    {
        // breaks down zipcode to get idividual numbers
        var zipNum5 = zipCode % MOD_NUMBER; // ex 12345 would get 5
        var zipNum4 = zipCode / TENS % MOD_NUMBER; // would get 4
        var zipNum3 = zipCode / HUNDREDS % MOD_NUMBER;// would get 3
        var zipNum2 = zipCode / THOUSANDS % MOD_NUMBER; // would get 2
        var zipNum1 = zipCode / TEN_THOUSANDS % MOD_NUMBER; // would get 1

        return "|" + encodeDigit(zipNum1) + encodeDigit(zipNum2) + encodeDigit(zipNum3) +
                encodeDigit(zipNum4) + encodeDigit(zipNum5) +
                encodeDigit(getCheckDigit(sumDigits(zipCode))) + "|";
    }

    /**
     * converts barcode symbols to number. A symbol must have 5 chars to it.
     * @param symbol barcode fragment to be changed to a number
     * @return int value from the symbol
     */
    public static int decodeSymbol(String symbol) // given a symbol like :::||, return the integer value.
    {
         // matches numbers to symbols
        var zipCode = -1;
        if (symbol.equals("||:::") )
            zipCode = 0;
        if (symbol.equals(":::||") )
            zipCode = 1;
        if (symbol.equals("::|:|") )
            zipCode = 2;
        if (symbol.equals("::||:") )
            zipCode = 3;
        if (symbol.equals(":|::|") )
            zipCode = 4;
        if (symbol.equals(":|:|:") )
            zipCode = 5;
        if (symbol.equals(":||::") )
            zipCode = 6;
        if (symbol.equals("|:::|") )
            zipCode = 7;
        if (symbol.equals("|::|:") )
            zipCode = 8;
        if (symbol.equals("|:|::") )
            zipCode = 9;
        return zipCode;
    }

    /**
     * Takes sections of a barcode and turns each section into a number. Those numbers will then be added together to return the zip code/
     * @param barCode barcode that will be entered by the user.
     * @return after the barcode symbols are converted to numbers, the numbers will be the zipcode
     */
    public static int decodeBarCode(String barCode) //given entire bar code, return the 5 digit bar code
    {
        // creates new substrings for a segment grabbed from the barcode
        var num1 = barCode.substring(1,6);
        var num2 = barCode.substring(6, 11);
        var num3 = barCode.substring(11, 16);
        var num4 = barCode.substring(16, 21);
        var num5 = barCode.substring(21, 26);
        //decodes the segment into a number and places it in the correct position of the barcode
        var zipNum1 = decodeSymbol(num1) * TEN_THOUSANDS;
        var zipNum2 = decodeSymbol(num2) * THOUSANDS;
        var zipNum3 = decodeSymbol(num3) * HUNDREDS;
        var zipNum4 = decodeSymbol(num4) * TENS;
        var zipNum5 = decodeSymbol(num5);
        return zipNum1 + zipNum2 + zipNum3 + zipNum4 + zipNum5;
    }

    /**
     * takes a number, separates the numbers into single digits then adds the single digits to return the sum of the digits
     *
     * @param number 5 digit number to be added together
     *
     * @return the sum of all numbers added together
     */
    public static int sumDigits(int number) // given a number, returnn the sum of the digits
    {
        // separates numbers from zipcode. 12345 --> 1,2,3,4,5
        var num5 = number % TENS;
        var num4 = number / TENS % MOD_NUMBER;
        var num3 = number / HUNDREDS % MOD_NUMBER;
        var num2 = number / THOUSANDS % MOD_NUMBER;
        var num1 = number / TEN_THOUSANDS % MOD_NUMBER;
        // adds number together to get the sum
        return num5 + num4 + num3 + num2 + num1;
    }

    /**
     * takes a sum and subtracts it from the nearest 10th number to be the check digit
     *
     * @param sum a total of numbers added together
     *
     * @return returns the result of nearest 10th - sum
     */
    public static int getCheckDigit(int sum) // given a sum, return the check digit for that number
    {
        var checkDigit = 0;
        if (sum < 10)
        {
            for (var count = sum; count < 10; count ++ )
            {
                checkDigit++;
            }
        }
        if (sum > 10 && sum < 20)
        {
            for (var count = sum; count < 20; count++)
            {
                checkDigit++;
            }
        }
        if (sum > 20 && sum < 30)
        {
            for (var count = sum; count < 30; count++)
            {
                checkDigit++;
            }
        }
        if (sum > 30 && sum < 40)
        {
            for (var count = sum; count < 40; count++)
            {
                checkDigit++;
            }
        }
        if (sum > 40 && sum < 50)
        {
            for (var count = sum; count < 50; count++)
            {
                checkDigit++;
            }
        }
        return checkDigit;
    }

    /**
     * Gets the check digit when the barcode is entered
     * @param barCode 32 characters long
     * @return sum of digits subtracted from nearest 10th number
     */
    public static int getCheckDigit(String barCode)
    {
        return getCheckDigit(sumDigits(decodeBarCode(barCode)));
    }

    public static void main(String[] args)
    {


        var input = getInteger("Enter '1' to encode a zipcode or '2' to decode a barcode: ", 1, 2);
        System.out.println(input);

        if (input == 1)
        {
            var zipCode = getInteger("Please enter a 5 digit number: ", 10000, 99999);
            System.out.println(encodeZipCode(zipCode));
            System.out.printf("Sum of digits: %d%n", sumDigits(zipCode));
            System.out.printf("Check digit: %d%n", getCheckDigit(sumDigits(zipCode)));
            System.out.printf("%d + %d = %d", sumDigits(zipCode), getCheckDigit(sumDigits(zipCode)),
                    sumDigits(zipCode) + getCheckDigit(sumDigits(zipCode)));

        }
        if(input == 2) {
            System.out.printf("A barcode must be 32 characters long, must consist of only ':' ,'|', and must begin and end with '|'. %n" +
                    "Press 'Enter' to continue.");

            var barCode = getBarCode("Please enter a barcode: ");
            System.out.println();
            System.out.printf("Zipcode: %d-%d%n", decodeBarCode(barCode), getCheckDigit(barCode));
            System.out.printf("5-digit code: %d%n",decodeBarCode(barCode));
            System.out.printf("Check digit: %d", getCheckDigit(barCode));
        }
        // method testing
//        System.out.println("Testing getInteger");
//        System.out.println("Using the value .5, expecting 'Invalid, please try again'");
//        System.out.println(getInteger("Please enter a number: "));
//
//        System.out.println("Testing getInteger");
//        System.out.println("Using range 0 - 99999 and value 1000000");
//        System.out.println(getInteger("Please enter a number ", 0, 99999));
//
//        System.out.println("Testing encodeDigit");
//        System.out.println("Using number 8, expecting |::|:");
//        System.out.println(encodeDigit(8));
//
//        System.out.println("Testing encodeZipCode");
//        System.out.println("Using 24680, expecting |::|:|:|::|:||::|::|:||:::||:::| ");
//        System.out.println(encodeZipCode(getInteger("Please enter a 5 digit number: ", 0, 99999)));
//
//        System.out.println("Testing decodeSymbol");
//        System.out.println("Using ::|:|, expecting 2");
//        System.out.print(decodeSymbol("::|:|"));
//
//        System.out.println("Testing sumDigits()");
//        System.out.println("Using 8675309, expecting 'Number not in range'");
//        System.out.println(sumDigits(getInteger(
//                "Please enter a 5 digit number: ", 0, 99999)));
//
//        System.out.println("Testing getCheckDigit()");
//        System.out.println("Using 00001, expecting 9");
//        System.out.println(getCheckDigit((
//                sumDigits(getInteger("please enter a 5 " +
//                        "digit number: ", 0, 99999 )))));
//        System.out.println("Testing decodeBarCode");
//        System.out.println("Using |::|:|::|:|::|:|::|:|::|:||, expecting 22222 ");
//        System.out.println(decodeBarCode("|::|:|::|:|::|:|::|:|::|:||"));

//        System.out.println("Testing getCheckDigit()");
//        System.out.println("Using ||:::|:||::|::|:||:::||:::|, expecting 9");
//        System.out.println(getCheckDigit("||:::|:||::|::|:||:::||:::|"));

    }
}

