import java.util.Random;

public class Page 
{
	final int totOrder=320;
	final int totPage=totOrder/10;
	int []hashPage;
	public Page()
	{
		hashPage=new int[totPage];
	}
	
	public void clearPage()
	{
		for (int i=0; i<totPage; i++)
			hashPage[i]=0;
	}
	public int getRandomNumber(int direction,int num)
	{
		int randomNumber;
		if (direction==-1)
		{
			if (num==0) return  getRandomNumber(0,num);
			for (int i=0; i<10; i++)
			{
				Random random = new Random();
				randomNumber=Math.abs( random.nextInt() ) %num;
				if ( (hashPage[randomNumber/10] & (1<<(randomNumber%10)) )!=0) continue;
				hashPage[randomNumber/10]=hashPage[randomNumber/10] + (1<<(randomNumber%10));
				return randomNumber;
			}
			return getRandomNumber(0,num);
		}
		if (direction==0)
		{
			randomNumber=(num+1)%totOrder;
			while ( (hashPage[randomNumber/10] & (1<<(randomNumber%10)) )!=0)
			{
				randomNumber=(randomNumber+1)%totOrder;
			}
			hashPage[randomNumber/10]=hashPage[randomNumber/10] | (1<<(randomNumber%10));
			return randomNumber;
		}
		if (direction==1)
		{
			if (num==totOrder-1) return  getRandomNumber(0,num);
			for (int i=0; i<10; i++)
			{
				Random random = new Random();
				randomNumber=Math.abs( random.nextInt() ) %(totOrder-num)+num;
				if ( (hashPage[randomNumber/10] & (1<<(randomNumber%10)) )!=0) continue;
				hashPage[randomNumber/10]=hashPage[randomNumber/10] + (1<<(randomNumber%10));
				return randomNumber;
			}
			return getRandomNumber(0,num);
		}
			
		while (true)
		{
			Random random = new Random();
			randomNumber=Math.abs( random.nextInt() )%totOrder;
			if ( (hashPage[randomNumber/10] & (1<<(randomNumber%10)) )!=0) continue;
			hashPage[randomNumber/10]=hashPage[randomNumber/10] + (1<<(randomNumber%10));
			return randomNumber;
		}
	}
}
