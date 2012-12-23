
public class Memory 
{
	
	class PageMemory
	{
		int pageAge;			//lru fifo
		int runPage;			//运行的页号
		boolean use;			//nru
	}
	
	int lackCount;				//缺失数
	int orderNumber;
	final int totMemoryPage=4;
	PageMemory []pageMemory=new PageMemory[totMemoryPage];
	int runModel;				//0 fifo
								//1 lru
								//2 nru
	
	int point;					//nru指针
	
	Memory()
	{
		runModel=0;
		for (int i=0; i<4; i++)
			pageMemory[i]=new PageMemory();
		clearPageMemory();
	}
	
	void setRunModel(int runModel)
	{
		this.runModel=runModel;
	}
	
	void clearPageMemory()
	{
		for (int i=0; i<totMemoryPage; i++)
		{
			pageMemory[i].pageAge=0;
			pageMemory[i].runPage=0;
			pageMemory[i].use=false;
		}
		orderNumber=0;
		lackCount=0;
		point=0;
	}
	
	int check(int page)
	{
		for (int i=0; i<totMemoryPage; i++)
		{
			if (pageMemory[i].runPage==page) return i;
		}
		return -1;
	}
	
	boolean FIFO(int order)
	{
		if (check(order/10)!=-1) return false; 
		int which=0,minMemoryAge=pageMemory[0].pageAge;
		for (int i=0; i<totMemoryPage; i++)
		{
			if (pageMemory[i].pageAge<minMemoryAge || pageMemory[i].pageAge==0)
			{
				which=i;
				minMemoryAge=pageMemory[i].pageAge;
			}
		}
		pageMemory[which].pageAge=++orderNumber;
		pageMemory[which].runPage=order/10;
		lackCount++;
		return true;
	}
	
	boolean LRU(int order)
	{
		int which=check(order/10);
		if (which!=-1)
		{
			pageMemory[which].pageAge=++orderNumber;
			return false;
		}
		
		which=0;
		int minMemoryAge=pageMemory[0].pageAge;
		for (int i=0; i<totMemoryPage; i++)
		{
			if (pageMemory[i].pageAge<minMemoryAge || pageMemory[i].pageAge==0)
			{
				which=i;
				minMemoryAge=pageMemory[i].pageAge;
			}
		}
		pageMemory[which].pageAge=++orderNumber;
		pageMemory[which].runPage=order/10;
		lackCount++;
		return true;
	}
	
	boolean NRU(int order)
	{
		int which=check(order/10);
		if (which!=-1)
		{
			pageMemory[which].use=true;
			return false;
		}
		else
		{
			lackCount++;
			while (pageMemory[point].use)
			{
				pageMemory[point].use=false;
				point=(point+1)%totMemoryPage;
			}
			pageMemory[point].use=true;
			pageMemory[point].runPage=order/10;
			return true;
		}
	}
	
	boolean runOrder(int order)
	{
		switch (runModel)
		{
		case 0:
			return FIFO(order);
		case 1:
			return LRU(order);
		case 2:
			return NRU(order);
		default:
			return false;
		}
	}
	
	float getLack(int totPage)
	{
		return (float)lackCount/totPage;
	}
}