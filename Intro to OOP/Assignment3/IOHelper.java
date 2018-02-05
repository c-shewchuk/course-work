import java.util.Scanner;

public class IOHelper {
	private static Scanner screenInput = new Scanner(System.in);
	public static String getString(String Prompt)
	{
		System.out.println(Prompt);
		return screenInput.nextLine();
	}
	public static int getInt(int i, String Prompt, int m)
	{
		System.out.println(Prompt);
		int orderNumber = screenInput.nextInt();
			while(orderNumber>m||orderNumber<i)
			{
				System.out.println("incorrect number try again");
				orderNumber = screenInput.nextInt();
			}
		screenInput.nextLine();
		return orderNumber;
	}
	public static int getInt(int i,String Prompt)
	{
		System.out.println(Prompt);
		int a = screenInput.nextInt();
		screenInput.nextLine();
		return a;
	}
}
