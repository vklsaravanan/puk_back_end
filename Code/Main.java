import java.util.Scanner;
 
public class Main
{            
    public static void main(String[] args)
    {
        System.out.print("Mmbu");
        int alphabet = 65;
                for (int i = 0; i <= 5; i++)
        {
            for (int j = 0; j <= i; j++)
            {
                System.out.print((char) (alphabet + j) + " ");
            }
            System.out.print("");
        }
    }
}