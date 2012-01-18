

import javax.swing.JOptionPane;


public class Polynom 
{
	private static final double max = Double.MAX_VALUE;
	private static final char[] eSimbols = {'E', 'e'};
	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args)
	{
		if (args.length == 0)
		{ 
			JOptionPane.showMessageDialog(null,"Не введены параметры!"); 
			System.exit(-1); 
		} 
	    
		if (args.length == 1)
		{ 
			JOptionPane.showMessageDialog(null,"Вы ввели только 1 параметр!"); 
			System.exit(-1); 
		} 
	    
	    if (args.length > 2)
	    { 
		   	JOptionPane.showMessageDialog(null,"Вы ввели больше 2 параметров!"); 
			System.exit(-1); 
		}   
	   
	    Double x = TryParseDouble(args[0], "в первом");
	    Double y = TryParseDouble(args[1], "во втором");
			
		int
			dlinx = (int) Math.log10(Math.abs(x)) + 1,
			dliny = (int) Math.log10(Math.abs(y)) + 1,
			
			dlinA = (int) Math.log10(max) + 1,
			dlinB = (int) Math.log10(Math.pow( (max - (x + 1)) / 5, new Double("0.3333333") ) ) + 1;
	
		if (Math.abs(x) > max)
		{
			JOptionPane.showMessageDialog(null, 
					"Ошибка ввода первого аргумента. " +
					"\nМаксимальная длина значения не должна превосходить "+ dlinA +" символа." +
					"\nВы ввели "+ dlinx +" символов.");
			System.exit(-1);			
		}		
		
		if (Math.abs(y) > Math.pow( (max - (x + 1)) / 5, new Double("0.3333333") ) )
		{
			JOptionPane.showMessageDialog(null, 
					"Ошибка ввода второго аргумента. " +
					"\nМаксимальная длина значения не должна превосходить "+ dlinB +" символа." +
					"\nВы ввели "+ dliny +" символов.");
			System.exit(-1);
		}
		
		double result = x + 5 * Math.pow(y, 3) + 1;
		
		System.out.println("x + 5 * y^3 + 1 = " + result);
		System.out.println("x = " + args[0]);
		System.out.println("y = " + args[1]);
		System.out.println("x + y = "+ (x + y));
	}
	
	private static Double TryParseDouble(String argument, String argumentName)
	{
		/**
		 * Проверка аргумента на знак и правильность ввода
		 */
		if (argument.charAt(0) == '-' || argument.charAt(0) == '+')	
		{
			if (argument.length() == 1 || argument.charAt(1) == '.')
			{
				JOptionPane.showMessageDialog(null, 
						"Ошибка ввода "+argumentName+" аргументе. " +
						"\nВы ввели: "+argument.charAt(0));
				System.exit(-1);		
			}
			else 
			{
				DetermineDouble(argument, 1, argumentName);
			}
		}
		else
		{		
			
			if (argument.charAt(0) == '.' && argument.length() == 1)
			{				
				JOptionPane.showMessageDialog(null, 
						"Ошибка ввода "+argumentName+" аргументе."+
						"\nВведена точка в первой позиции!");
				System.exit(-1);
		    }		
			else	
			{
				DetermineDouble(argument, 0, argumentName);
			}
		}
		
		//Проверяем на наличе символов "Е" и выдаем ошибку, если их слишком много
		checkForESimbol(argument, argumentName);
		
		double value = Double.parseDouble(argument);
		
		if(Math.abs(value) >= Double.MAX_VALUE)
		{			
				JOptionPane.showMessageDialog(null, 
						argumentName +" аргументе значение выходит за рамки double.");
				System.exit(-1);				
				
		}
		return value;
	}
	
	private static void checkForESimbol(String argument, String argumentName) 
	{
		boolean hasE = false;
		boolean hasMoreThanOneE = false;
		int errorPos = 0;
		int ePos = 0;
		
		for (int i = 0; i < argument.length(); i++) 
		{
			if (!Character.isDigit(argument.charAt(i))) 
			{
				for (char ch  : eSimbols) 
				{
					if (ch == argument.charAt(i)) 
					{
						if (hasE) 
						{
							errorPos = i;
							hasMoreThanOneE = true;
							break;
						}
						ePos = i;
						hasE = true;
					}
				}
			}
		}
		
		if ((ePos + 1) == argument.length()) 
		{
			JOptionPane.showMessageDialog(null, 
					"Ошибка " + argumentName + " аргументе." +
					"\nВы не ввели число после символа 'Е' в позиции "+ (ePos + 1) + ".");
					System.exit(-1);
		}
		
		if (ePos > 0 && (argument.charAt(ePos - 1) == '+' || argument.charAt(ePos - 1) == '-')) 
		{
			JOptionPane.showMessageDialog(null, 
					"Ошибка " + argumentName + " аргументе." +
					"\nВы ввели неверный символа перед символом 'Е' в позиции "+ (ePos) + ".");
					System.exit(-1);
		}
		
		if (hasE && (ePos == 0)) 
		{
			JOptionPane.showMessageDialog(null, 
					"Ошибка " + argumentName + " аргументе." +
					"\nВы не ввели число перед символом 'Е' в позиции "+ (ePos + 1) + ".");
					System.exit(-1);
		}
		
		if (hasMoreThanOneE) 
		{
			JOptionPane.showMessageDialog(null, 
					"Ошибка " + argumentName + " аргументе." +
					"\nВы ввели лишний символ 'Е' в позиции "+ (errorPos + 1) + ".");
					System.exit(-1);
		}
	}

	private static void DetermineDouble(String argument, int stepover, String argumentName)
	{
		
		for (int k = stepover; k < argument.length(); k ++)
		{
			if (argument.charAt(k) == '.')
			{			
				for (int l = argument.indexOf('.') + 1; l < argument.length(); l++)
				{
					if (argument.charAt(l) == '.')
					{
						JOptionPane.showMessageDialog(null, 
								"Ошибка "+ argumentName +" аргументе." +
								"\nВведена лишняя точка в позиции "+ (l + 1) + ".");
								System.exit(-1);					
					}
				}
			}			
	
			if ( (argument.charAt(k) < '0' || argument.charAt(k) > '9') && 
					(k >= 0 && argument.charAt(k) != '.') && 
					(argument.charAt(k) != 'E' && argument.charAt(k) != 'e'))
			{
				if ( !(argument.charAt(k) == '-' &&  (argument.charAt(k - 1) == 'E' || argument.charAt(k - 1) == 'e')) ) 
				{
					JOptionPane.showMessageDialog(null, 
							"Ошибка " + argumentName + " аргументе." +
							"\nВы ввели '" + argument.charAt(k) + "' вместо числа в позиции "+ (k + 1) + ".");
							System.exit(-1);
				}
									
			}	
		}
	}

}
