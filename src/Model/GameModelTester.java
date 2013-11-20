package Model;

import java.util.Scanner;

public class GameModelTester {
	/*
	public static void main(String args[]){
		GameModel gm = new GameModel(GameModel.FIXED_TIME_MODE, new MemoirDAO(LinkPhaseActivity.this));
		Scanner scanner = new Scanner(System.in);
		System.out.println("WordCount: "+gm.getWordCount());
		System.out.println("WordList:");
		for(int i=0;i<gm.getWordCount()-1;i++){
			int index = gm.getCurrentWordIndex()+1;
			System.out.println("----"+index+"/"+gm.getWordCount()+"-----");
			System.out.println(gm.getWordOne());
			try {
				System.out.println(gm.getWordTwo());
			} catch (Exception e) {
			}
			System.out.println("---------");
			if(scanner.nextInt()==1)
				gm.linkNextWord();
		}
		System.out.println("QUIZ PHASE!");
		gm.startQuizPhase();
		String guess;
		for(int i=0;i<gm.getWordCount()-1;i++){
			int index = gm.getCurrentWordIndex()+1;
			System.out.println("----"+index+"/"+gm.getWordCount()+"-----");
			System.out.println(gm.getWordOne());
			boolean firstTime = true;
			do{
				
				if(!firstTime)
					System.out.println("Wrong!");
				firstTime=false;
				guess = scanner.next();
				if(guess.equals("skip")){
					gm.skipWord();
					break;
				}else if(guess.equals("end")){
					gm.endQuizPhase();
				}

			}while(!gm.answerQuiz(guess));
			System.out.println("Correct!");
			System.out.println("---------");
		}
		
		System.out.println("End of Game");
		System.out.println(gm.getWordCount()-gm.getSkips()+"/"+gm.getWordCount()+" words");
		System.out.println("Mistakes: "+gm.getMistakes());
	}
	*/
}
