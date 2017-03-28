//500 ms gap between colors? see comments at the bottom
//shapes
//get rid of old unnecessary code
//change so extends to other monitor automatically


// catherine buttafish
// sameer belija
// belajage



// proposal: weaton
//All the stuff we need to import to run this program
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import java.util.Random;
import java.lang.System;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.File;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;
import javafx.scene.text.Font;
import javafx.animation.SequentialTransition;
import javafx.animation.PauseTransition;
import javafx.scene.Group;
import javafx.animation.Timeline;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import java.util.concurrent.TimeUnit;
import javafx.animation.PathTransition;
import javafx.scene.shape.Path;
import javafx.scene.shape.MoveTo;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCombination;
import javafx.stage.StageStyle;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.KeyCode;
import javafx.stage.WindowEvent;
import javafx.scene.image.WritableImage;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.Cursor;
import javafx.scene.paint.Paint;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;

//single class. everything in here 

public class Quads extends Application{
	private long time = 1000l;
	//variable we will use in various methods throughout the program
	private String seq = "423142124323";
	private String predetermined = "";
	public static String newline = System.getProperty("line.separator");
	private Rectangle rectangle;
	private int count = 0; // keeps a count of how many times we have gone through the cycle method
	private int trials; // total number of trials aka how many times we want them to press the key
	private String correct; // says which key is the correct key for rectangle that's pressed
	private long[] onset; //array of the onsets of the time when the rectangle changes color
	private long[] offset; //arrry of the offsets of the time when the user presses the key
	private long[] reactionTimes; // array of the reaction times. where at each index -- starting with 0 and ending at trials - 1
	//represents the amount of time (in milliseconds the participant took to react)
	//the value of the reactiontime will be negative if an incorrect key was pressed
	private int viableTrials; //trails minus the wrong keys pressed
	private int[] bad;
	private String text;
	private String id; //test or train
	private int countdown;
	private StackPane stack;
	private int runs;
	private long avgReaction; //average reactiontime of the participant across all of the trials
	private long[] avgSeqReaction;
	private long[] interpress;
	private long randFirstAvg;
	private long randLastAvg; 
	private long trialsAvg;
	private double percentage; //percentage of trials in which the participant pressed the correct key
	private double[] avgSeqAccuracy;
	private double randFirstAcc;
	private double randLastAcc;
	private double trialsAcc;
	private StackPane primaryStack;
	private Group rmid;
	private String correctColor = "G";
	private Timer t;
	private TimerTask task;
	private long[] greens;
	private int numGreen;
	private int greenFinal50;
	private double greenSeq = 0.0;
	private int greenTotal;

	public static void main(String[] args) {
		launch(args);
	}

	// this corresponds to the main method in normal java programs
	// this prepares everything for the program and allows the program
	// to begin only after the start button is pressed
	public void start(Stage stage) {
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		// Rectangle2D primaryScreenBounds = Screen.getScreens().get(1).getVisualBounds();

		stage.setX(primaryScreenBounds.getMinX());
		stage.setY(primaryScreenBounds.getMinY());
		//set the dimesions to the screen size:
		stage.setWidth(primaryScreenBounds.getWidth());
		stage.setHeight(primaryScreenBounds.getHeight());
		stage.setFullScreen(true);
		stage.sizeToScene();
		stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		stage.show();

		TextInputDialog dialog = new TextInputDialog("Insert Info");
		dialog.setTitle("Enter Data");
		dialog.setContentText("Enter TESTR, TESTL, or TRAIN");
		Optional<String> result = dialog.showAndWait();
		result.ifPresent(x -> text = x);
		setString(text);
		setBad(predetermined);

		stack = new StackPane();
		Button start = new Button("Start");
		start.setFont(new Font(100.0));
		start.setTextFill(Color.CYAN);
		start.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		start.setPrefSize(primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight() + 500);

		ImageView logo = new ImageView(new Image("http://s1.postimg.org/u35sxjjsf/NPRL_Icon_Tag_sm.jpg"));
		logo.setFitHeight(100);
		logo.setPreserveRatio(true);
		stack.setAlignment(logo, Pos.TOP_LEFT);
		ImageView hand = new ImageView(new Image("http://s27.postimg.org/fek82icaa/Hand_With_Block.jpg"));
		hand.setFitHeight(300);
		hand.setPreserveRatio(true);
		stack.setAlignment(start, Pos.BASELINE_CENTER);
		stack.setAlignment(hand, Pos.BOTTOM_CENTER);
		stack.getChildren().addAll(start, logo, hand);
		Scene s = new Scene(stack, 1000, 1000);

		s.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent code) {
				if(code.getCode().equals(KeyCode.ENTER)) {
					main(stage);
				}
			}
		});

		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent w) {
				close(stage);
				System.exit(0);

			}
		});

		start.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				main(stage); // calls "main" method upon clicking start button
			}
		});

		stage.setScene(s);
		stage.show();
	}

	// this is the main method that sets the background, rectangles, and adds them all to the window
	public void main(Stage s2) {
		primaryStack = new StackPane();
		HBox box = new HBox(); //horizontal box in which the rectangles are placed. seperated by 25 pixels
		box.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		rectangle = new Rectangle(250.0, 250.0);
		rectangle.setFill(Color.WHITE); // make white
		box.getChildren().addAll(rectangle);
		box.setAlignment(Pos.CENTER); //centers the box horizontally on the window
		primaryStack.getChildren().addAll(box);
		Scene scene = new Scene(primaryStack, Color.BLACK); //creates a 500px X 500px window with a black background
		scene.setCursor(Cursor.NONE);
		s2.setScene(scene); 
		s2.show(); //necessary to see javafx
		s2.setFullScreen(true);

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() { // when key pressed
			public void handle(KeyEvent k){
				String key = k.getText();
				if (k.getCode().equals(KeyCode.BACK_SPACE)) {
					close(s2);
				}
			}
		});

		scene.setOnMousePressed(new EventHandler<MouseEvent>() { // when mouse clicked
			public void handle(MouseEvent k){
				// because count + 1 happens so quickly we need to put all the info in count - 1
				offset[count - 1] = System.currentTimeMillis();
				reactionTimes[count - 1] = offset[count - 1] - onset[count - 1];
				if (Character.getNumericValue(predetermined.charAt(count-1)) == 1) {
					System.out.println("correct");
					bad[count - 1] = bad[count - 1] + 1;
					greens[numGreen - 1] = System.currentTimeMillis() - onset[count - 1];
					System.out.println(greens[numGreen - 1]);
				} else {
					System.out.println("incorrect");
					bad[count - 1] = bad[count - 1] - 1;
					System.out.println(bad[count - 1]);
				}
			}
		});

		t = new Timer();
		task = new TimerTask() {
			//if want to get rid of exception.
			// public void run() {
			// 	Platform.runLater(new Runnable(){
					public void run() {
					
						read(predetermined, count, s2);
						count++;

						if (count == predetermined.length()) {
							close(s2);
						}
						
					}
			// 	});
			// }
		};
		t.schedule(task, 0l, time);

		// w = new TimerTask() {
		// 	public void run() {
		// 		// read(predetermined, count, s2);
		// 		// count++;
		// 		System.out.println(count);
		// 		if (count >= predetermined.length() - 1) {
		// 			System.out.println("here");
		// 			close(s2);
		// 		} else {
		// 			white();
		// 		}
		// 	}
		// };
		// t.schedule(w, 50l, 100l);
	}

	/**
	 *This method sets the color of the passed in rectangle to CYAN
	 *and saves the onset time. 
	 */
	public void blink(int num) {
		if (num == 1) { //correct one
			rectangle.setFill(Color.rgb(36, 255, 36));
			correct = "G"; //green
			numGreen++;
		} else if (num == 2) {
			rectangle.setFill(Color.rgb(109, 182, 255));
			correct = "B"; // blue
		} else if (num == 3) {
			rectangle.setFill(Color.rgb(255, 109, 182));
			correct = "P"; //pink
		} else if (num == 4) {
			rectangle.setFill(Color.rgb(255, 255, 109));
			correct = "Y"; //yellow
		}
		onset[count] = System.currentTimeMillis();
	}

	/**
	 *This method changes the passed in rectangle to blue
	 *also sets a fade method for 500ms so the next rectangle does
	 *not come up until 500 milliseconds afterwards
	 */
	public void white() {
		rectangle.setFill(Color.BLACK);
		FadeTransition ft = new FadeTransition(new Duration(50.0), rectangle);
		ft.setFromValue(0);
		ft.setToValue(1.0);
		ft.setCycleCount(1);
		ft.setAutoReverse(true);
		ft.play();
	}

	//reads a string at an index and returns the coresponding rectangle
	//that needs to light up later.
	public void read(String order, int x, Stage s2) {
		int num = Character.getNumericValue(order.charAt(x));
		blink(num);
	}


	// this method turns the blank predetermined string to a string that is already set depending on
	// weather this is a test session or training session
	//
	public void setString(String test) { //creates the sequence
		if (test.contains("TESTR") || test.contains("TESTL")) { //if user enters test
			if (test.contains("1")) {
				seq = invert(seq);
			}
			predetermined = predetermined + "24132413412432431243124132432412432134231241324321";
			runs = 15;
			for (int x = 0; x < runs; x++) {
				predetermined = predetermined + seq; // seq repeated 15 times
			}
			predetermined = predetermined + "12341243214213214231234231231423142341231231412432";
			trials =  predetermined.length();
			viableTrials = predetermined.length();
			onset = new long[trials];
			offset = new long[trials];
			interpress = new long[trials];
			reactionTimes = new long[trials];
			bad = new int[trials];
		} else if (test.contains("TRAIN")) { // if user enters training
			if (test.contains("1")) {
				seq = invert(seq);
			}
			predetermined = predetermined + "24321321421341234312314324323143213243124142314321";
			runs = 25;
			for (int x = 0; x < runs; x++){
				predetermined = predetermined + seq; //seq repeated 25 times
			}
			predetermined = predetermined + "12314312342341243241321324324324312341324321341342";
			trials =  predetermined.length();
			viableTrials = predetermined.length();
			onset = new long[trials];
			offset = new long[trials];
			interpress = new long[trials];
			reactionTimes = new long[trials];
			bad = new int[trials];
		} else { // if user enters something that isn't test or training
			// System.out.println("Please try again. Enter TEST or TRAINING");
			TextInputDialog dialog = new TextInputDialog("TRAINING");
			dialog.setTitle("Enter Data");
			dialog.setContentText("Enter TESTR, TESTL, or TRAIN");
			Optional<String> result = dialog.showAndWait();
			result.ifPresent(x -> text = x);
			setString(text);
		}
	}

	// instantiate greentotal, greenFinal50, and greenSeq;
	public void setBad(String order) {
		int green = 0;
		for (int x = 0; x < order.length(); x++) {
			int num = Character.getNumericValue(order.charAt(x));
			if (num == 1) {
				bad[x] = bad[x] - 1;
				green++;
			}
		}
		greenTotal = green;

		int green3 = 0; 
		for (int x = 0; x < 50; x++) {
			int num = Character.getNumericValue(order.charAt(x));
			if (num == 1) {
				green3 = green3 + 1;
			}
		}
		int greenFirst50 = green3;

		double green2 = 0.0;
		for (int x = 0; x < seq.length(); x++) {
			int num = Character.getNumericValue(seq.charAt(x));
			if (num == 1) {
				green2 = green2 + 1.0;
			}
		}
		greenSeq = green2;

		int green1 = 0;
		for (int x = order.length() - 50; x < order.length(); x++) {
			int num = Character.getNumericValue(order.charAt(x));
			if (num == 1) {
				green1++;
			}
		}
		greenFinal50 = green1;

		greens = new long[green];
		for(int x = 0; x < green; x++) {
			greens[x] = time; // set it equal to max time.
		}

	}
	//Calculates the accuracy and avg reaction times of overall session
	public void calculations() {
		double total = 0;
		int badTotal = 0;
		for (int a: bad) {
			badTotal = badTotal + a;
		}
		// for (int x = 0; x < count; x++) {
		// 	badTotal = badTotal + bad[x];
		// }

		percentage = (double)(Math.round((100.0*(count -1 + (double)badTotal))/((double)count - 1))/100.0);
		// might want to implement
		// so just in case
		// if (percentage < 0) {
		// 	percentage = 0.0;
		// }

		for (int x = 0; x < numGreen; x++) {
			total = total + greens[x];
		}
		avgReaction = Math.round(total/(double)numGreen);

	}

	// writes to a text file called ReactionTimes.txt
	public  void writer(String id) {
		try {
			// System.out.println("Enter participant ID");
			// String id = scan.next();
			// for (long g: greens) {
			// 	System.out.println(g);
			// }
			FileWriter file = new FileWriter(new File("Quads.txt"), true);
			file.write("ID:\tCond\tSeg\tRxt(ms)\tAccuracy" + newline);
			// file.write(id + "\t" + text+ "\tall\t" + avgReaction + "\t " + percentage +"\n");
			
			file.write(id+ "\t" +text+ "\tRandBeg\t" +randFirstAvg + "\t" + randFirstAcc + newline);
			for (int x = 0; x < runs; x++) {
				file.write(id+ "\t" +text+ "\tRep"+(x+1)+"\t" +avgSeqReaction[x] + "\t" + avgSeqAccuracy[x] + newline);
			}
			file.write(id+ "\t" +text + "\tRepAvg\t"+trialsAvg + "\t" +trialsAcc + newline);
			file.write(id+ "\t" +text+ "\tRandEnd\t" +randLastAvg + "\t" + randLastAcc + newline);
			file.write(id + "\t" + text+ "\tOverall\t" + avgReaction + "\t" + percentage + newline);
			
			// file.write(id+ "\t" + text + "\t" + percentage + newline);
			file.write("End at " +(count) + newline + newline);
			file.close();
		} catch(Exception e) {
			System.out.println(e);
		}
	}

	// inverts the string that is passed
	public String invert(String p) {
		String q = "";
		for (int x = p.length()-1; x > -1; x--) {
			q = q + p.charAt(x);
		}
		return q;
	}
	// attempts to analyze the data in relation to the particular sequence
	// gives the avg reaction time and accurace of the beginning random sequence,
	// the repeated sequences, and the random sequence at the end
	public void analyze() {
		//legit meaning that it works only when end at end
		count = count - 1;
		double end = 0.0;
		int curGreen = 0;
		if (count > 50) {
			end = 50.0;
		} else {
			end = (double)(count);
		}
		
		long randFirstTotal = 0L;
		int randFirstBadTotal = 0;
		for (int x = 0; x < end; x++) {
			if (Character.getNumericValue(predetermined.charAt(x)) == 1) {
				// randFirstTotal = randFirstTotal + reactionTimes[x];
				randFirstTotal = randFirstTotal + greens[curGreen];
				curGreen++;
			}
			randFirstBadTotal = randFirstBadTotal + bad[x];
		}
		int greenAt50 = curGreen; // should equal greenFirst50;
		// System.out.println("curGreen " + curGreen);
		// System.out.println("green at 50 " + greenAt50);
		// System.out.println(greenAt50 == curGreen);

		randFirstAvg = Math.round(randFirstTotal/(double)curGreen);
		randFirstAcc = Math.round(100.0*(end + (double)(randFirstBadTotal))/(end))/100.0;
		
		// System.out.println("RAND FIRST AVG" + randFirstAvg);
		// System.out.println("RAND FIRST ACCURACY" + randFirstAcc);
		long avgTotal = 0l;
		int avgBad = 0;
		avgSeqReaction = new long[runs];
		avgSeqAccuracy = new double[runs];
		for (int x = 0; x < runs; x++){
			long temptotal = 0L;
			int tempbad = 0;
			if (count > 50+(seq.length()*(x+1))) { //if you went through this seq
				end = seq.length();
				for (int y = 0; y < seq.length(); y++) {
					if (Character.getNumericValue(predetermined.charAt(50+(seq.length()*x)+y)) == 1) {
						temptotal += greens[curGreen];
						avgTotal += greens[curGreen];
						curGreen++;					
					}
						// temptotal = temptotal + reactionTimes[50+(seq.length()*x)+y];
						// avgTotal = avgTotal + reactionTimes[50+(seq.length()*x)+y];
					tempbad = tempbad + bad[50+(seq.length()*x)+y];
					avgBad = avgBad + bad[50+(seq.length()*x)+y];	
					

				}
			} else if (((count - 50 - (seq.length() * x)) % seq.length()) >= 0) { //if you stopped mid sequence
				end = ((count - 50 - (seq.length() * x)) % seq.length());
				for (int y = 0; y < end; y++) {
					if(Character.getNumericValue(predetermined.charAt(50+(seq.length()*x)+y)) == 1) {
						temptotal += greens[curGreen];
						avgTotal += greens[curGreen];
						curGreen++;
					}
						// temptotal = temptotal + reactionTimes[50+(seq.length()*x)+y];
						// avgTotal = avgTotal + reactionTimes[50+(seq.length()*x)+y];
						tempbad = tempbad + bad[50+(seq.length()*x)+y];
						avgBad = avgBad + bad[50+(seq.length()*x)+y];
					
				}
			} else { //if you never got to this sequence
				end = 1.0;
				temptotal = 0L;
				tempbad = 0;
				// curGreen = 0;
			}
			// System.out.println("temptotal: " + temptotal);
			// System.out.println("curGreen: " + curGreen);
			// System.out.println("greenAt50: " + greenAt50);

			// avgSeqReaction[x] = Math.round(temptotal/(double)(curGreen - greenAt50 - (2*x)));
			// System.out.println("run at " + x + " " + Math.round(temptotal/ (double) (curGreen - greenAt50 - (greenSeq * x))));// check if same
			// System.out.println("run at " + x + " " + Math.round(temptotal/ (double) greenSeq)); //legit
			
			avgSeqReaction[x] = Math.round(temptotal/ (double) (curGreen - greenAt50 - (greenSeq * x)));
			
			// System.out.println("avg " + x + "rxn" + avgSeqReaction[x]);
			avgSeqAccuracy[x] = Math.round(100.0*(end + (double)(tempbad))/ (double)end)/100.0;
			// System.out.println("avgSeqReaction" + "[" + x+ "]: " +avgSeqReaction[x]);
			// System.out.println("avgSegAccuracy" +"[ "+x+"]: " + avgSeqAccuracy[x] );
		}

		if (count > 50 + (seq.length()*12)) { // if stop after all sequences
			// System.out.println("avg of all runs" + Math.round((avgTotal/(double)(greenSeq * runs))));// legit
			// System.out.println("avg of all runs" + Math.round((avgTotal/(double)(curGreen - greenAt50))));
			
			trialsAvg = Math.round((avgTotal/(double)(curGreen - greenAt50)));
			trialsAcc = Math.round(100.0*(count - 100.0 + (double)avgBad)/((double)(seq.length()*runs)))/100.0;
		} else if (count > 50) { // if stop in middle of seq

			trialsAvg = Math.round((avgTotal/(double)(curGreen - greenAt50)));
			// System.out.println("avg of all runs" + Math.round((avgTotal/(double)(greenSeq * runs))));// legit
			// System.out.println("avg of all runs" + Math.round((avgTotal/(double)(curGreen - greenAt50))));
			// trialsAvg = Math.round((avgTotal/(double)(curGreen - greenAt50)));
			trialsAcc = Math.round(100.0*(count -50.0 + (double)avgBad)/((double)count - 50.0))/100.0;
		} else { // if didn't get to sequence
			trialsAvg = 0l;
			trialsAcc = 0.0;
		}
		// System.out.println();
		// System.out.println("cur green (after runs) " + curGreen);
		// System.out.println("green total " + greenTotal);
		// System.out.println("greenFinal50 " + greenFinal50);
		// System.out.println("greenTotal - greenFinal50" + (greenTotal - greenFinal50));
		// System.out.println();
		// int greenAfterSeqs = curGreen;
		int greenAfterSeqs = curGreen;

		long randLastTotal = 0L;
		int randLastBadTotal = 0; 
		if (count == predetermined.length() - 1) { // if break after end of 50
			end = 50.0;
			// count = count + 1;
		} else if (count > (predetermined.length() - 51)) { // if break inbetween 50
			end = 50.0 - (predetermined.length()-count);
		} else { // if didn't reach last fiffty
			end = 1;
			randLastBadTotal = -1;
			curGreen = 0;
		}

		// System.out.println("cur green (before 50)" + curGreen);
		for (int x = count; x > predetermined.length() - 51; x--) {
			if (Character.getNumericValue(predetermined.charAt(x)) == 1){
				randLastTotal += greens[curGreen];
				curGreen++;
			}
				// randLastTotal = randLastTotal + reactionTimes[x];
			randLastBadTotal = randLastBadTotal + bad[x];
			
		}

		// System.out.println("cur green (at end)" + curGreen);
		// old
		// for (int x = predetermined.length() - 1; x > predetermined.length() - 51; x--) {
		// 	randLastTotal = randLastTotal + reactionTimes[x];
		// 	randLastBadTotal = randLastBadTotal + bad[x];

		// }

		// System.out.println("random last total" + randLastTotal);
		// System.out.println("cur Green" + curGreen);
		// System.out.println("greenAfterSeqs" + greenAfterSeqs);
		// System.out.println(randLastTotal);
		// System.out.println(greenAfterSeqs);
		// System.out.println("final " + Math.round(randLastTotal/(double)(curGreen - greenAfterSeqs)));
		// System.out.println("final " + Math.round(randLastTotal/(double)greenFinal50)); //legit
		randLastAvg = Math.round(randLastTotal/(double)(curGreen - greenAfterSeqs));
		randLastAcc = Math.round(100.0*(end + (double)(randLastBadTotal))/(end))/100.0;
		// System.out.println("RAND LAST AVG" + randLastAvg);
		// System.out.println("RAND LAST ACCURACY" + randLastAcc);
		if (count == predetermined.length() - 1) {
			count = count + 1;
		}
	}


	// a method that should be called whenever someone wants to exit the program
	public void close(Stage s2) {
		task.cancel();
		t.cancel();
		calculations();
		analyze();
		//working
		// TextInputDialog typebox = new TextInputDialog("ID");
		// typebox.setTitle("Enter Data");
		// typebox.setContentText("Enter ID:");
		// Optional<String> result = typebox.showAndWait();
		// result.ifPresent(x -> id = x);
		// writer(id);
		// s2.close();

		// testing
		TextInputDialog typebox = new TextInputDialog("ID");
		typebox.setTitle("Enter Data");
		typebox.setContentText("Enter ID:");
		Optional<String> result = typebox.showAndWait();
		if (result.isPresent()) {
			id = result.get();
			writer(id);
		}
		// result.ifPresent(x -> id = x);
		
		s2.close();

	}
}



// a wait method that doesnt work well we are currently implementing the fade tranistion instead

// try {
//     Thread.sleep(100);                 //1000 milliseconds is one second.
// } catch(InterruptedException ex) {
// 	Thread.currentThread().interrupt();
// }



// with black in middle:
// 		t = new Timer();
// 		task = new TimerTask() {
// 			public void run() {
// 				if (count >= predetermined.length()) {
// 					close(s2);
// 				}
// 				read(predetermined, count, s2);
// 				count++;		
// 			}
// 		};
// 		t.schedule(task, 0l, 100l);
// 		w = new TimerTask() {
// 			public void run() {
// 				// read(predetermined, count, s2);
// 				// count++;
// 				System.out.println(count);			
// 				if (count >= predetermined.length() - 1) {
// 					// System.out.println("here");
// 					close(s2);
// 				} else {
// 					white();
// 				}				
// 			}



// // a wait method that doesnt work well we are currently implementing the fade tranistion instead
// // try {
// //     Thread.sleep(100);                 //1000 milliseconds is one second.
// // } catch(InterruptedException ex) {
// // 	Thread.currentThread().interrupt();
// // }



//analysis where it works at the very end

// // attempts to analyze the data in relation to the particular sequence
// 	// gives the avg reaction time and accurace of the beginning random sequence,
// 	// the repeated sequences, and the random sequence at the end
// 	public void analyze() {
// 		count = count - 1;
// 		double end = 0.0;
// 		int curGreen = 0;
// 		if (count > 50) {
// 			end = 50.0;
// 		} else {
// 			end = (double)(count);
// 		}
		
// 		long randFirstTotal = 0L;
// 		int randFirstBadTotal = 0;
// 		for (int x = 0; x < end; x++) {
// 			if (Character.getNumericValue(predetermined.charAt(x)) == 1) {
// 				// randFirstTotal = randFirstTotal + reactionTimes[x];
// 				randFirstTotal = randFirstTotal + greens[curGreen];
// 				curGreen++;
// 			}
// 			randFirstBadTotal = randFirstBadTotal + bad[x];
// 		}
// 		int greenAt50 = curGreen; // should equal greenFirst50;
// 		System.out.println("curGreen " + curGreen);
// 		System.out.println("green at 50 " + greenAt50);
// 		System.out.println(greenAt50 == curGreen);

// 		randFirstAvg = Math.round(randFirstTotal/(double)curGreen);
// 		randFirstAcc = Math.round(100.0*(end + (double)(randFirstBadTotal))/(end))/100.0;
		
// 		// System.out.println("RAND FIRST AVG" + randFirstAvg);
// 		// System.out.println("RAND FIRST ACCURACY" + randFirstAcc);
// 		long avgTotal = 0l;
// 		int avgBad = 0;
// 		avgSeqReaction = new long[runs];
// 		avgSeqAccuracy = new double[runs];
// 		for (int x = 0; x < runs; x++){
// 			long temptotal = 0L;
// 			int tempbad = 0;
// 			if (count > 50+(seq.length()*(x+1))) { //if you went through this seq
// 				end = seq.length();
// 				for (int y = 0; y < seq.length(); y++) {
// 					if (Character.getNumericValue(predetermined.charAt(50+(seq.length()*x)+y)) == 1) {
// 						temptotal += greens[curGreen];
// 						avgTotal += greens[curGreen];
// 						curGreen++;					
// 					}
// 						// temptotal = temptotal + reactionTimes[50+(seq.length()*x)+y];
// 						// avgTotal = avgTotal + reactionTimes[50+(seq.length()*x)+y];
// 					tempbad = tempbad + bad[50+(seq.length()*x)+y];
// 					avgBad = avgBad + bad[50+(seq.length()*x)+y];	
					

// 				}
// 			} else if (((count - 50 - (seq.length() * x)) % seq.length()) >= 0) { //if you stopped mid sequence
// 				end = ((count - 50 - (seq.length() * x)) % seq.length());
// 				for (int y = 0; y < end; y++) {
// 					if(Character.getNumericValue(predetermined.charAt(50+(seq.length()*x)+y)) == 1) {
// 						temptotal += greens[curGreen];
// 						avgTotal += greens[curGreen];
// 						curGreen++;
// 					}
// 						// temptotal = temptotal + reactionTimes[50+(seq.length()*x)+y];
// 						// avgTotal = avgTotal + reactionTimes[50+(seq.length()*x)+y];
// 						tempbad = tempbad + bad[50+(seq.length()*x)+y];
// 						avgBad = avgBad + bad[50+(seq.length()*x)+y];
					
// 				}
// 			} else { //if you never got to this sequence
// 				end = 1.0;
// 				temptotal = 0L;
// 				tempbad = 0;
// 				// curGreen = 0;
// 			}
// 			// System.out.println("temptotal: " + temptotal);
// 			// System.out.println("curGreen: " + curGreen);
// 			// System.out.println("greenAt50: " + greenAt50);

// 			// avgSeqReaction[x] = Math.round(temptotal/(double)(curGreen - greenAt50 - (2*x)));
			
// 			avgSeqReaction[x] = Math.round(temptotal/greenSeq);
			
// 			// System.out.println("avg " + x + "rxn" + avgSeqReaction[x]);
// 			avgSeqAccuracy[x] = Math.round(100.0*(end + (double)(tempbad))/ (double)end)/100.0;
// 			// System.out.println("avgSeqReaction" + "[" + x+ "]: " +avgSeqReaction[x]);
// 			// System.out.println("avgSegAccuracy" +"[ "+x+"]: " + avgSeqAccuracy[x] );
// 		}

// 		if (count > 50 + (seq.length()*12)) { // if stop after all sequences
// 			trialsAvg = Math.round(avgTotal/(double)(greenSeq *runs));
// 			trialsAcc = Math.round(100.0*(count - 100.0 + (double)avgBad)/((double)(seq.length()*runs)))/100.0;
// 		} else if (count > 50) { // if stop in middle of seq

// 			trialsAvg = Math.round((avgTotal/(double)(greenSeq * runs)));

// 			// trialsAvg = Math.round((avgTotal/(double)(curGreen - greenAt50)));
// 			trialsAcc = Math.round(100.0*(count -50.0 + (double)avgBad)/((double)count - 50.0))/100.0;
// 		} else { // if didn't get to sequence
// 			trialsAvg = 0l;
// 			trialsAcc = 0.0;
// 		}
// 		System.out.println();
// 		System.out.println("cur green (after runs) " + curGreen);
// 		System.out.println("green total " + greenTotal);
// 		System.out.println("greenFinal50 " + greenFinal50);
// 		System.out.println("greenTotal - greenFinal50" + (greenTotal - greenFinal50));
// 		System.out.println();
// 		// int greenAfterSeqs = curGreen;
// 		int greenAfterSeqs = curGreen;

// 		long randLastTotal = 0L;
// 		int randLastBadTotal = 0; 
// 		if (count == predetermined.length() - 1) { // if break after end of 50
// 			end = 50.0;
// 			// count = count + 1;
// 		} else if (count > (predetermined.length() - 51)) { // if break inbetween 50
// 			end = 50.0 - (predetermined.length()-count);
// 		} else { // if didn't reach last fiffty
// 			end = 1;
// 			randLastBadTotal = -1;
// 			curGreen = 0;
// 		}

// 		System.out.println("cur green (before 50)" + curGreen);
// 		for (int x = count; x > predetermined.length() - 51; x--) {
// 			if (Character.getNumericValue(predetermined.charAt(x)) == 1){
// 				randLastTotal += greens[curGreen];
// 				curGreen++;
// 			}
// 				// randLastTotal = randLastTotal + reactionTimes[x];
// 			randLastBadTotal = randLastBadTotal + bad[x];
			
// 		}

// 		System.out.println("cur green (at end)" + curGreen);
// 		// old
// 		// for (int x = predetermined.length() - 1; x > predetermined.length() - 51; x--) {
// 		// 	randLastTotal = randLastTotal + reactionTimes[x];
// 		// 	randLastBadTotal = randLastBadTotal + bad[x];

// 		// }

// 		// System.out.println("random last total" + randLastTotal);
// 		// System.out.println("cur Green" + curGreen);
// 		// System.out.println("greenAfterSeqs" + greenAfterSeqs);
		
// 		randLastAvg = Math.round(randLastTotal/(double)greenFinal50);
// 		randLastAcc = Math.round(100.0*(end + (double)(randLastBadTotal))/(end))/100.0;
// 		// System.out.println("RAND LAST AVG" + randLastAvg);
// 		// System.out.println("RAND LAST ACCURACY" + randLastAcc);
// 		if (count == predetermined.length() - 1) {
// 			count = count + 1;
// 		}
// 	}