package test;

public class fortest {

		static boolean fun (char c){
			System.out.print(c);
			return true;
		}
		
		public static void main(String[] args){
			int i=0;
			for(fun('A');fun('B')&&(i<2);fun('C')){
				i++;
				fun('D');
			}
		}
		
}
