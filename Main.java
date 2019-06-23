import javax.swing.JOptionPane;

public class Main implements Runnable{
	
	static graphic gui;
	public static void main(String[] args)  throws InterruptedException{		
		int YesorNo = JOptionPane.YES_OPTION;
		while(YesorNo == JOptionPane.YES_OPTION) {
			gui =new graphic();
			new Thread(new Main()).start();
			while(!gui.reset && !gui.victory) {
				//throws InterruptedException, so no problem 
				Thread.sleep(1000);
			}
			if(gui.reset) {
				YesorNo= JOptionPane.showConfirmDialog(null,"Do you want ot start again", "You Lose! HAHA!", JOptionPane.YES_NO_OPTION);
				if(YesorNo==JOptionPane.YES_OPTION) {
					gui.dispose();
					continue;
				}else if(YesorNo==JOptionPane.NO_OPTION){
					
					System.exit(0);
				}
			}
			else {
				YesorNo= JOptionPane.showConfirmDialog(null,"Do you want ot start again", "You win!", JOptionPane.YES_NO_OPTION);
				if(YesorNo==JOptionPane.YES_OPTION) {
					gui.dispose();
					continue;
				}else if(YesorNo==JOptionPane.NO_OPTION){
					System.exit(0);
				}
			}
			
		}
	}
	@Override
	public void run() {
		while(true) {
			try {
				//prevents heating 100 is optimal
				Thread.sleep(100);
			}catch (InterruptedException e){
				  System.out.println("I was interrupted!");
		           e.printStackTrace();
			}
			gui.repaint();
		}
	}
}
