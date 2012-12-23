import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


class TextPanel extends JPanel
{
	JTextArea text;
	public TextPanel()
	{
		this.setLayout(new GridLayout(1,1));

		text=new JTextArea();
		text.setBackground(Color.white);
		text.setEditable(false);
		
		JScrollPane scroll = new JScrollPane(text);
		text.setLineWrap(false);
		add(scroll);
	}
	
	public void addText()
	{
		text.setText(text.getText()+"\n指令数"+"\t"+"指令编号"+"\t"+"指令页号"+"\t"+"是否缺页"+"\t"+"内存1"+"\t"+"内存2"+"\t"+"内存3"+"\t"+"内存4");
	}
}


class SelectPanel extends JPanel
{
	JButton fifoButton;
	JButton lruButton;
	JButton nruButton;
	
	TextPanel textPanel;
	JTextArea lackArea;
	Memory memory=new Memory();
	Page page=new Page();
	int orderCount;
	
	public SelectPanel(TextPanel text)
	{
		textPanel=text;
		setLayout(null);
		fifoButton=new JButton("FIFO调页模拟");
		fifoButton.setBounds(50, 0, 150, 100);
		fifoButton.addActionListener(FifoListener);
		
		lruButton=new JButton("LRU调页模拟");
		lruButton.setBounds(50, 200, 150, 100);
		lruButton.addActionListener(LruListener);
		
		nruButton=new JButton("NRU调页模拟");
		nruButton.setBounds(50, 400, 150, 100);
		nruButton.addActionListener(NruListener);
		
		add(fifoButton);
		add(lruButton);
		add(nruButton);
	}
	
	void getTextString(int order)
	{
		String orderString=new Integer(++orderCount)+"\t"+new Integer(order).toString()+"\t"+"["+new Integer(order/10+1).toString()+"]"+"";
		
		if (memory.runOrder(order))	orderString+="\tYes";
		else orderString+="\tNo";
		
		for (int i=0; i<memory.totMemoryPage; i++)
		{
			orderString+="\t"+new Integer(memory.pageMemory[i].runPage);
		}

		textPanel.text.setText(textPanel.text.getText()+"\n"+orderString);
	}
	
	void runOrder()
	{
		for (int i=0; i<page.totOrder/4; i++)
		{
			int order=page.getRandomNumber(10, 0);
			getTextString(order);
			
			order=page.getRandomNumber(0, order);
			getTextString(order);

			order=page.getRandomNumber(-1, order);
			getTextString(order);

			order=page.getRandomNumber(1, order);
			getTextString(order);
		}
//		int []order={10,20,30,40,31,50,60,70};
//		for (int i=0; i<8; i++)
//			getTextString(order[i]);
		textPanel.text.setText(textPanel.text.getText()+"\n缺页率："+new Float(memory.getLack(page.totOrder)));
	}
	
	ActionListener FifoListener = new ActionListener() 
	{
		public void actionPerformed(ActionEvent e) 
		{
			orderCount=0;
			memory.clearPageMemory();
			page.clearPage();
			textPanel.text.setText("FIFO调页");
			textPanel.addText();
			memory.setRunModel(0);
			runOrder();
		}				
	};
	
	ActionListener LruListener = new ActionListener() 
	{
		public void actionPerformed(ActionEvent e) 
		{
			orderCount=0;
			memory.clearPageMemory();
			page.clearPage();
			textPanel.text.setText("LRU调页");
			textPanel.addText();
			memory.setRunModel(1);
			runOrder();
		}				
	};
	
	ActionListener NruListener = new ActionListener() 
	{
		public void actionPerformed(ActionEvent e) 
		{
			orderCount=0;
			memory.clearPageMemory();
			page.clearPage();
			textPanel.text.setText("LRU调页");
			textPanel.addText();
			memory.setRunModel(2);
			runOrder();
		}				
	};
}

class MemoryPanel extends JPanel
{
	TextPanel text;
	SelectPanel select;
	MemoryPanel()
	{
		this.setBounds(50, 50, 900, 575);
		setLayout(null);
		text=new TextPanel();
		text.setBounds(0, 0, getSize().width/4*3,getSize().height);
		add(text);

		select=new SelectPanel(text);
		select.setBounds(this.getSize().width/4*3, 0,getSize().width/4, getSize().height);
		add(select);
		this.setBackground(Color.red);
	}
}

public class MemoryFrame extends JFrame
{
	public MemoryFrame()
	{	
		this.setResizable(false);
		setBounds(50, 50, 900, 600);
		add(new MemoryPanel());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}



