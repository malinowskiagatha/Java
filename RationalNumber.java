//-----------------------------------------------------------------
// Agatha Malinowski
// Spring’20 CS 284QB
// Homework Assignment 1
//-----------------------------------------------------------------


import java.text.DecimalFormat;
//-----------------------------------------------------------------
// The class RationalNumber represents rational numbers and a 
// few simple operations performed below. A rational 
// number can be expressed as the fraction p, q, where p and q are 
// two integers, q cannot equal 0.
//-----------------------------------------------------------------
public class RationalNumber
{
    // Define global variables for
    public int num, denom;
    
    // Characteristic, Antiperiod, and Period
    public String s_character, s_antiper, s_per;
    public int i_character, i_per;
    public double d_antiper;

    //-------------------------------------------------------------
    // Creates a rational number of the form p/q, given p 
    // and q. When q = 0 the program throws a RuntimeException.
    //-------------------------------------------------------------
    public RationalNumber(int p, int q) 
    {
        num = p;
        denom = q;
        if (Math.abs(denom) == 0) 
        {
            throw new RuntimeException("ERROR: Denominator cannot be equal to zero");
        }
        else 
        {
            simplify();
        }
    }
    
    //-------------------------------------------------------------
    // Return numerator
    //-------------------------------------------------------------
    public int getNumerator ()
    {
        return num;
    }
    
    //-------------------------------------------------------------
    // Return denominator
    //-------------------------------------------------------------
    public int getDenominator ()
    {
        return denom;
    }
    
    //-------------------------------------------------------------
    // Simplifies a rational number of the form num/denom to its 
    // simplest form r/s, where r = num/gcd(num, denom) and 
    // s = denom/gcd(denom, num). The method replaces the receiving 
    // rational number with its simplified form.
    //-------------------------------------------------------------
    private void simplify ()
    {
    int simp;
    if (num != 0)
        {
            simp = gcd (Math.abs(num), denom);

            num = num / simp;
            denom = denom / simp;
        }
    }
    
    //-------------------------------------------------------------
    // Calculate greatest common divisor (gcd) useing the Euclidean 
    // Algorithm. This is a helper fuction to simplify().
    //-------------------------------------------------------------
   private int gcd (int number1, int number2)
   {
    int temp;
    while (number2 != 0)
    {
        temp = number2;
        number2 = number1 % number2;
        number1 = temp;
    }

      return number1;
    }

    //-------------------------------------------------------------
    // Adds the rational number given as parameter to the one on 
    // which this method is invoked (the receiving rational number).
    // This operation modifies the receiving rational number with 
    // the result of the addition.
    //-------------------------------------------------------------
    public void add (RationalNumber rn2)
    {
      int num1 = num * rn2.getDenominator();
      int num2 = rn2.getNumerator() * denom;
      denom = denom * rn2.getDenominator();
      num = num1 + num2;

      simplify();
    }    

    ///-------------------------------------------------------------
    // Multiplies the rational number given as parameter to the one 
    // on which this method is invoked (the receiving rational 
    // number). This operation modifies the receiving rational 
    // number with the result of the multiplication in simplified 
    // form.
    //-------------------------------------------------------------
    public void multiply (RationalNumber denom2)
    {
        num = num * denom2.getNumerator();
        denom = denom * denom2.getDenominator();

        simplify();
    }

    //-------------------------------------------------------------
    // Returns a double which represents the rational number in
    // decimal form, including up to 3 decimal digits.
    //-------------------------------------------------------------
    public double doubleValue() 
    {
        DecimalFormat df = new DecimalFormat("#0.000");
        double RNum;
        RNum = (double) num/denom;
        RNum = Double.parseDouble(df.format(RNum));
        return RNum;
    }

    //-------------------------------------------------------------
    // Returns a float which represents the rational number in 
    // decimal form, including up to 3 decimal digits.
    //-------------------------------------------------------------
    public float floatValue()
    {
        DecimalFormat df = new DecimalFormat("#0.000");
        float RNum;
        RNum = (float) num/denom;
        RNum = Float.parseFloat(df.format(RNum));
        return RNum;
    }

    //-------------------------------------------------------------
    // Returns a int which represents the integer part of the 
    // decimal representation of the rational number.
    //-------------------------------------------------------------
    public int intValue()
    {
        int RNum;
        RNum = (int) num/denom;
        return RNum;
    }

    //-------------------------------------------------------------
    // Returns a long which represents the integer part of the 
    // decimal representation of the rational number.
    //-------------------------------------------------------------
    public long longValue()
    {
        long RNum;
        RNum = (long) num/denom;
        return RNum;
    }

    //-------------------------------------------------------------
    // From right to left, multiply each digit by increasing powers 
    // of 2 (starting from 2^0 = 1).
    //-------------------------------------------------------------
    private void characteristic ()
    {
       int dec = 0;
       int count = s_character.length();
       
       while (count > 0) 
       {
           dec += Integer.parseInt(String.valueOf(s_character.charAt(count-1))) * Math.pow(2, s_character.length()-count);
           count -= 1;
        }

       num = dec;
       denom = 1;
       i_character = dec;
    } 

    //-------------------------------------------------------------
    // From left to right, divide each digit by increasing powers 2 
    // (starting from 2^1 = 2).
    //-------------------------------------------------------------
    private void anti_period ()
    {
       double dec = 0;
       int count = 1;
       
       RationalNumber temp = new RationalNumber(0,1);
        
       while (count <= s_antiper.length()) 
       {
           dec +=  Integer.parseInt(String.valueOf(s_antiper.charAt(count-1))) / Math.pow(2, count);
           
           temp.num = Integer.parseInt(String.valueOf(s_antiper.charAt(count-1)));
           temp.denom = (int) Math.pow(2, count);
           this.add(temp);
           
           count += 1;
        }
    }   
    
    //-------------------------------------------------------------
    // From right to left, multiply each digit by increasing
    // powers of 2 (starting from 2^0 = 1). Then, divide the result 
    // by 2^a(2^p − 1), where "a" is the length of the anti-period, 
    // and "b" is the length of the period.
    //-------------------------------------------------------------
    private void period ()
    {
        RationalNumber temp = new RationalNumber(0,1);

        int dec = 0;
        int count = s_per.length();
        
        while (count > 0) 
        {
            dec += Integer.parseInt(String.valueOf(s_per.charAt(count-1))) * Math.pow(2, s_per.length()-count);
            count -= 1;
        }

        temp.num = dec;
        temp.denom = (int) (Math.pow(2,s_antiper.length()) * (Math.pow(2,s_per.length())-1));
        this.add(temp);
    }

    //-------------------------------------------------------------
    // A constructor RationalNumber(String s) where string s is of 
    // the form characteristic.antiperiod period. 
    // RationalNumber(String s) parses s and converts the binary 
    // periodic number to a rational number.
    //-------------------------------------------------------------
    public RationalNumber (String s)
    {
        s_character = "";
        s_antiper = "";
        s_per = "";

        num = 0;
        denom = 0; 

        String[] tokens;
        
        //Split BPN into three components
        try 
        {
            tokens = s.split("\\.");
            s_character = tokens[0];
        
            tokens = tokens[1].split("_");
            s_antiper = tokens[0];
            s_per = tokens[1];
        } 
        catch (Exception e) {
            //System.out.println("Booo!!!");
         }

        //start of error handling         
        //check if s_character is a binary number
        if (s_character.length() > 0 && !s_character.matches("[01]+")) 
        {
            throw new RuntimeException("ERROR: Invalid input provided; " + s_character + " is not a binary number.");
        }

        //check if s_antiper is a binary number
        if (s_antiper.length() > 0 && !s_antiper.matches("[01]+")) 
        {
            throw new RuntimeException("ERROR: Invalid input provided; " + s_character + " is not a binary number.");
        }

        //check if s_per is a binary number
        if (s_per.length() > 0 && !s_per.matches("[01]+")) 
        {
            throw new RuntimeException("ERROR: Invalid input provided; " + s_character + " is not a bianry number.");
        }
          
        //check if at least one component of the Binary Periodic Number was provided
        if (s_character.length() == 0 && s_antiper.length() == 0 && s_per.length() == 0)  
        {
            throw new RuntimeException("ERROR: Invalid input provided; " + s + " is an invalid string");
        }
        
         // end of error handling 

         //System.out.println("init --- num: " + num + " denom: " + denom);
         
         characteristic();
         //System.out.println("after character --- num: " + num + " denom: " + denom);
         
         anti_period ();
         //System.out.println("after anti_period --- num: " + num + " denom: " + denom);
         
         period();
         //System.out.println("after period --- num: " + num + " denom: " + denom);   
    }


//-----------------------------------------------------------------
// Main Method
//-----------------------------------------------------------------
    public static void main(String[] args) {
        
        // Instantiate object RationalNumber as fra variable
        RationalNumber fra = new RationalNumber(1,2);
        // Display numerator and denumerator 
        System.out.println(fra.getNumerator ());
        System.out.println(fra.getDenominator ());

        // Instantiate object RationalNumber as fra1 variable supplying BPN
        RationalNumber fra1 = new RationalNumber("100.01_101");
        System.out.println("BPN: " + fra1.s_character +"."+fra1.s_antiper + "_" +fra1.s_per);
        // Display numerator and denumerator 
        System.out.println("Numerator: " + fra1.getNumerator ());
        System.out.println("Denominator: " + fra1.getDenominator ());

        //Add fra to fra1
        fra1.add(fra);
        // Display numerator and denumerator after the addition
        System.out.println("BPN: " + fra1.s_character +"."+fra1.s_antiper + "_" +fra1.s_per);
        System.out.println("Numerator: " + fra1.getNumerator ());
        System.out.println("Denominator: " + fra1.getDenominator ());

        //Multiply fra1 and fra; 
        fra1.multiply(fra);
        // Display numerator and denumerator after the multiplication
        System.out.println("BPN: " + fra1.s_character +"."+fra1.s_antiper + "_" +fra1.s_per);
        System.out.println("Numerator: " + fra1.getNumerator ());
        System.out.println("Denominator: " + fra1.getDenominator ());

        // Instantiate a new object RationalNumber as fra2 variable
        RationalNumber fra2 = new RationalNumber(2,1);
        //Multiply fra1 and fra2
        fra1.multiply(fra2);
        // Display numerator and denumerator after the multiplication
        System.out.println("BPN: " + fra1.s_character +"."+fra1.s_antiper + "_" +fra1.s_per);
        System.out.println("Numerator: " + fra1.getNumerator ());
        System.out.println("Denominator: " + fra1.getDenominator ());

    }
}