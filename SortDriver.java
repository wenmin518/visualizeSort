/**
 * Author: Wenmin He
 * CPE-103
 */

import java.awt.*;
import java.applet.Applet;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.util.Date;
import java.util.Random;
import java.text.*;

public class SortDriver extends Applet {
   
   private int array[];  // array to be sorted
   private int limit = 1000;  // size of array to be sorted - you may have to make
                             // this bigger for faster sorts
   private int largestNum; // need to know for color scaling purposes in paint();


   //flag to tell paint() whether to paint a single location or the whole array
   private enum PaintType {ALL, RANGE, SINGLE};
   private PaintType doPaint = PaintType.ALL;

   private int index = -1;  // index of single array location to be painted
   private int leftRange = -1;  // left end of range to be drawn
   private int rightRange = -1;  // right end of range to be drawn
   
   //this listener object responds to button events
   private ButtonActionListener buttonListener;

   //button to start the sort
   private JButton startSort;
   //buttons to get random and default setting
   private JButton randomChoose, defaultsetting;
   
   // the picture of the sort will appear on this canvas
   private SortCanvas picture;
   private final int pictureWidth = 1001;  // size of the sort bar
   private final int pictureHeight = 200;  // Height of image

   // put buttons and canvas on this panel
   private JPanel sortPanel;

   // Panels for radiobuttons
   private JPanel sortingPanel, colorPanel, orderPanel,buttonPanel;

   // declarations for some more GUI elements
   private JLabel label, dateLabel, sortingLabel, colorLabel, orderlabel; // a non-interactive text field
   private JRadioButton bubble, insertion, mergeButton, quick, silver, yellow, green,blue, order,reverse,randOrder;// radio buttons
   private ButtonGroup rButtons, orderButtons, sortButtons, colorButtons;
   private JTextField rText, datatext; // you can type text into this field 
   private Box buttonbox, labelbox; //Box Objects to hold a list of items

   //Current time in the real world
   private Date date = new Date();
   private DateFormat dataFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");


   public void init() {

      buttonListener = new ButtonActionListener();      

      //----------------------------------------------------------------------
      //default array
      //----------------------------------------------------------------------
      array = new int[limit];
      // load the array
      largestNum = array[0] = (int) (Math.random()*1000000.0);
      for (int i=1; i<limit; i++) {
          array[i] = (int) (Math.random()*1000000.0);
          // also keep track of the largest so that we can scale by it in paint()
          if (array[i] > largestNum) largestNum = array[i]; 
      }

      // set up the window
      sortPanel = new JPanel();
      sortPanel.setLayout(new BoxLayout(sortPanel, BoxLayout.Y_AXIS));
      
      // first place the sort bar on top
      picture = new SortCanvas();
      sortPanel.add(picture);

      //----------------------------------------------------------------
      //Initialize the startsort button
      //----------------------------------------------------------------
      startSort = new JButton("Start");
      startSort.setFont(new Font("Serif", Font.PLAIN, 20));
      startSort.setPreferredSize(new Dimension(100, 75));
      startSort.addActionListener(buttonListener);
      sortPanel.add(startSort);

      //initialize all the boxes
      buttonbox = Box.createHorizontalBox();
      labelbox = Box.createHorizontalBox();

      //----------------------------------------------------------------
      //Lables and text adds to the sortPanel
      //----------------------------------------------------------------

      label = new JLabel("Please Enter a valid number                        ");
      label.setFont(new Font("Serif", Font.PLAIN, 20));
      label.setHorizontalAlignment(JLabel.CENTER);
      labelbox.add(label);

      buttonbox.add(Box.createHorizontalGlue());

      dateLabel = new JLabel("                Time you open this program : " +dataFormat.format(date));
      dateLabel.setFont(new Font("Serif", Font.PLAIN, 20));
      labelbox.add(dateLabel);

      sortPanel.add(labelbox);

      // text field with room for 20 characters
      rText = new JTextField("How long do you want this array to be", 20); 
      rText.addActionListener(buttonListener); 
      sortPanel.add(rText);


      //----------------------------------------------------------------
      //the sorting methods and its corresponding buttons
      //----------------------------------------------------------------
      sortingPanel = new JPanel();
      sortingPanel.setLayout(new BoxLayout(sortingPanel, BoxLayout.Y_AXIS));

      sortingPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));

      sortingLabel = new JLabel("Sorting Methods");
      sortingLabel.setFont(new Font("Serif", Font.PLAIN, 20));
      sortingPanel.add(sortingLabel);

      bubble = new JRadioButton("Bubble Sort", true);
      bubble.addActionListener(buttonListener);
      bubble.setFont(new Font("Serif", Font.PLAIN, 20));
      sortingPanel.add(bubble);
      
      insertion = new JRadioButton("Insertion Sort", false);
      insertion.setFont(new Font("Serif", Font.PLAIN, 20));
      insertion.addActionListener(buttonListener);
      insertion.setPreferredSize(new Dimension(200, 100));
      sortingPanel.add(insertion);
      
      mergeButton = new JRadioButton("Merge Sort", false);
      mergeButton.setFont(new Font("Serif", Font.PLAIN, 20));
      mergeButton.addActionListener(buttonListener);
      sortingPanel.add(mergeButton);
      
      quick = new JRadioButton("Quick Sort", false);
      quick.addActionListener(buttonListener);
      quick.setFont(new Font("Serif", Font.PLAIN, 20));
      sortingPanel.add(quick);

      sortButtons = new ButtonGroup();
      sortButtons.add(bubble);
      sortButtons.add(insertion);
      sortButtons.add(mergeButton);
      sortButtons.add(quick);

      //----------------------------------------------------------------
      //the colors and it's corresponding buttons
      //----------------------------------------------------------------
      colorPanel = new JPanel();
      colorPanel.setLayout(new BoxLayout(colorPanel, BoxLayout.Y_AXIS));
      colorPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));

      colorLabel = new JLabel("Colors");
      colorLabel.setFont(new Font("Serif", Font.PLAIN, 20));
      colorPanel.add(colorLabel);

      silver=new JRadioButton("Silver", true);
      silver.addActionListener(buttonListener);
      silver.setFont(new Font("Serif", Font.PLAIN, 20));
      colorPanel.add(silver);

      yellow = new JRadioButton("Yellow", false);
      yellow.addActionListener(buttonListener);
      yellow.setFont(new Font("Serif", Font.PLAIN, 20));
      colorPanel.add(yellow);
      
      green = new JRadioButton("Green", false);
      green.addActionListener(buttonListener);
      green.setFont(new Font("Serif", Font.PLAIN, 20));
      colorPanel.add(green);
      
      blue = new JRadioButton("Blue", false);
      blue.addActionListener(buttonListener);
      blue.setFont(new Font("Serif", Font.PLAIN, 20));
      colorPanel.add(blue);

      colorButtons = new ButtonGroup();
      colorButtons.add(silver);
      colorButtons.add(yellow);
      colorButtons.add(green);
      colorButtons.add(blue);

      //----------------------------------------------------------------
      // Order lable, orders and its corresponding buttons
      //----------------------------------------------------------------
      orderPanel = new JPanel();
      orderPanel.setLayout(new BoxLayout(orderPanel, BoxLayout.Y_AXIS));
      orderPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));

      orderlabel = new JLabel("Orders");
      orderlabel.setFont(new Font("Serif", Font.PLAIN, 25));
      orderPanel.add(orderlabel);

      order = new JRadioButton("In Order", false);
      order.addActionListener(buttonListener);
      order.setFont(new Font("Serif", Font.PLAIN, 25));
      orderPanel.add(order);
      
      reverse = new JRadioButton("Reverse Order", false);
      reverse.addActionListener(buttonListener);
      reverse.setFont(new Font("Serif", Font.PLAIN, 25));
      orderPanel.add(reverse);
      
      randOrder = new JRadioButton("Random Order", true);
      randOrder.addActionListener(buttonListener);
      randOrder.setFont(new Font("Serif", Font.PLAIN, 25));
      orderPanel.add(randOrder);

      orderButtons = new ButtonGroup();
      orderButtons.add(order);
      orderButtons.add(reverse);
      orderButtons.add(randOrder);


      //--------------------------------------------------------------------
      //More radio buttons and boxes adds to the sort panel
      //--------------------------------------------------------------------

      randomChoose = new JButton("Random Setting");
      randomChoose.setPreferredSize(new Dimension(200, 100));
      randomChoose.addActionListener(buttonListener);

      defaultsetting = new JButton("Default setting");
      defaultsetting.setPreferredSize(new Dimension(200, 100));
      defaultsetting.addActionListener(buttonListener);

      buttonPanel = new JPanel(new BorderLayout());
      buttonPanel.add(randomChoose,BorderLayout.NORTH);
      buttonPanel.add(defaultsetting,BorderLayout.SOUTH);
      buttonbox.add(buttonPanel);
      buttonbox.add(Box.createHorizontalGlue());
      buttonbox.add(sortingPanel);
      buttonbox.add(Box.createHorizontalGlue());
      buttonbox.add(colorPanel);
      buttonbox.add(Box.createHorizontalGlue());
      buttonbox.add(orderPanel);
      buttonbox.add(Box.createHorizontalGlue());

      sortPanel.add(buttonbox);
      
      // add the panel to the window
      add(sortPanel);
      
      picture.paint(picture.getGraphics());
   }

   //the BUttonAction Listener, need a bunch of if statements
   // this object is triggered whenever a button is clicked
   private class ButtonActionListener implements ActionListener {
      public void actionPerformed(ActionEvent event) {
         
         // find out which button was clicked 
         Object source = event.getSource();

         //----------------------------------------------------------------
         //conditional statements when corresponding buttons clicked
         //----------------------------------------------------------------
         if(source == order) inOrder(limit);
         if(source == reverse) reverseOrder(limit);
         if(source == randOrder) randomOrder(limit);

         // start sort button was clicked
         if (source == startSort) {
            if(bubble.isSelected()) doBubblesort();
            if(insertion.isSelected()) doBinaryInsertionSort();
            if(mergeButton.isSelected()) doMergeSort();
            if(quick.isSelected()) doQuickSort(array);

         }
         if (source == randomChoose){
            Random rand = new Random();
            int i = rand.nextInt(4);
            if(i == 0) {bubble.setSelected(true);}
            if(i == 1) {insertion.setSelected(true);}
            if(i == 2) {mergeButton.setSelected(true);}
            if(i == 3) {quick.setSelected(true);}

            i = rand.nextInt(3);
            if(i == 0) {order.setSelected(true); inOrder(limit);}
            if(i == 1) {reverse.setSelected(true); reverseOrder(limit);}
            if(i == 2) {randOrder.setSelected(true); randomOrder(limit);}

            i = rand.nextInt(4);
            if(i == 0) {silver.setSelected(true);}
            if(i == 1) {yellow.setSelected(true);}
            if(i == 2) {green.setSelected(true);}
            if(i == 3) {blue.setSelected(true);}
         }

         if(source == defaultsetting){
            bubble.setSelected(true);
            randOrder.setSelected(true);
            randomOrder(limit);
            silver.setSelected(true);


         }
         // called when user hits return in text field
         if (source == rText) {
            try{
               //int size = Integer.parseInt(rText.getText());
               limit = Integer.parseInt(rText.getText());
               if(order.isSelected()) inOrder(limit);
               if(reverse.isSelected()) reverseOrder(limit);
               if(randOrder.isSelected()) randomOrder(limit);
               
               //limit = size;
            }
            catch(NumberFormatException e){
               rText.setText("Invalid format type, Please Enter a valid integer");
            }
         }

         //draw the lines
         doPaint = PaintType.ALL;
         picture.paint(picture.getGraphics());
      }    
   }
//do the sort right away
//----------------------------------------------------------------------
//The order set ups
//----------------------------------------------------------------------


   public void inOrder(int size){
      array = new int[limit];
      // load the array
      largestNum = array[0];
      for (int i=0; i<limit; i++) {
          array[i] = i;
          // also keep track of the largest so that we can scale by it in paint()
          if (array[i] > largestNum) largestNum = array[i]; 
      }
   }

   public void reverseOrder(int size){
      array = new int[limit];
      largestNum = array[0];
      for (int i=0; i<limit; i++) {
          array[i] = limit - i;
          if (array[i] > largestNum) largestNum = array[i]; 
       }
   }

   public void randomOrder(int size){
      array = new int[limit];
      largestNum = array[0] = (int) (Math.random()*1000000.0);
      for (int i=1; i<limit; i++) {
          array[i] = (int) (Math.random()*1000000.0);
          if (array[i] > largestNum) largestNum = array[i]; 
      }
   }

//----------------------------------------------------------------------
//Color options
//----------------------------------------------------------------------
   public Color colorDisplay(float brightness){
      if(silver.isSelected()) return new Color(brightness, brightness, brightness);
      if(yellow.isSelected()) return new Color(brightness, brightness, 0);
      if(green.isSelected()) return new Color(0, brightness ,0);
      if(blue.isSelected()) return new Color(0,0,brightness);
      return new Color(brightness, 0, brightness);
   }





//----------------------------------------------------------------------
//the Bubble sort
//----------------------------------------------------------------------
   private void doBubblesort() {
      int temp;
      // this is just bubblesort
      for (int i=0; i<limit-1; i++) {
         for (int j=0; j<limit-1-i; j++) {
            if (array[j]>array[j+1]) {
               temp = array[j]; array[j] = array[j+1]; array[j+1] = temp;
              
               // draws the bars between j and j+1
               doPaint = PaintType.RANGE;
               leftRange = j;
               rightRange = j+1;
               picture.paint(picture.getGraphics());
            }
         }
      }
   }
   
//----------------------------------------------------------------------
//binary sorting algorithm   
//----------------------------------------------------------------------
             
   private void doBinaryInsertionSort(){
      doBinaryInsertionSort(array, 0, limit-1);
   }

   private void doBinaryInsertionSort(int[] data,int first,int last){
      for(int i=first+1;i<=last;++i){
         int target = binarySearch(data,data[i],first,i-1);
         if (target != i){
            int temp = data[i];

            for(int j=i;j>target;--j){
               data[j] = data[j-1];
               

            }

            doPaint = PaintType.RANGE;
            leftRange = first;
            rightRange = last;
            picture.paint(picture.getGraphics());
            data[target] = temp;
         }
      }
   }

   private int binarySearch(int[] data, int key, int first, int last){
      int middle = 0;
      while(first <= last){
         middle = (first + last) / 2;
         if(key > data[middle])
            first = middle + 1;
         else if (key < data[middle])
            last = middle -1;
         else 
            return middle + 1;
      }

      if(key > data[middle])
         return middle + 1;
      
      else
         return middle;
   }

//----------------------------------------------------------------------
//Merge sort algorithm
//----------------------------------------------------------------------


   private void doMergeSort(){
      mergeSort(array, 0, limit-1);
   }

   private void mergeSort(int [ ] a, int begin, int end){
      if(begin < end){
         int middle = (begin + end) / 2;
         mergeSort(a, begin, middle);
         mergeSort(a, middle + 1, end);
         merge(a, begin, middle + 1, end);
         doPaint = PaintType.RANGE;
         leftRange = begin;
         rightRange = end;
         picture.paint(picture.getGraphics());
      }
   }


   private void merge(int[ ] data, int begin, int end, int rightEnd ){
      int[] temp = new int[limit];
      int leftEnd = end - 1;
      int k = begin;
      int num = rightEnd - begin + 1;

      while(begin <= leftEnd && end <= rightEnd)
      {
         if(data[begin] <= data[end]){
            temp[k] = data[begin];
            k++;
            begin++;
         }
         else{  
            temp[k] = data[end];
            k++;
            end++;
         }
      }

      // Copy rest of first half
      while(begin <= leftEnd){   
         temp[k] = data[begin];
         k++;
         begin++;
      }

      // Copy rest of right half
      while(end <= rightEnd){   
         temp[k] = data[end];
         k++;
         end++;
      }

       // Copy the array to the temp
      for(int i = 0; i < num; i++, rightEnd--){  

         data[rightEnd] = temp[rightEnd];
      }
   }
 
//paint with white and fill the height
//----------------------------------------------------------------------
//the quick sort algorithm and methods
//----------------------------------------------------------------------
   private void doQuickSort(int[] data) {
      int [][] s= new int[data.length][2];
      int TOS = 0;

      s[TOS][0] = 0;
      s[TOS][1] = data.length-1;

      while(TOS != -1){

         int begin = s[TOS][0];
         int end = s[TOS][1];
         TOS--;
          
         int size = end - begin+1;
         if(size < 2){}

//         else if(size <= 0){
//            binaryInsertionSort(data,begin,end);
//         }

         else{
            medianOfThree(data,begin,end);
            int middle = partition(data,begin,end);
            TOS++;
            s[TOS][0] = middle+1;
            s[TOS][1] = end;
            doPaint = PaintType.RANGE;
            leftRange = s[TOS][0];
            rightRange = s[TOS][1];
            picture.paint(picture.getGraphics());
            
            TOS++;
            s[TOS][0] = begin;
            s[TOS][1] = middle -1;
            doPaint = PaintType.RANGE;
            leftRange = s[TOS][0];
            rightRange = s[TOS][1];
            picture.paint(picture.getGraphics());
             
          }
      }
   }

   private static int partition(int[] data,int first,int last){
      int left = first+1;
      int right = last;
      int temp;
      while(true){
         while(left<=right && data[left] <= data[first])
            ++left;
         while(right>=left && data[first] <= data[right])
            --right;
         if (left > right)
            break;
         temp = data[left];
         data[left] = data[right];
         data[right] = temp;
         ++left;
         --right;
      } 

      temp = data[first];
      data[first] = data[right];
      data[right] = temp;
      return right;
   }

   private void medianOfThree(int[] data,int first,int last){

      int temp,middle,median;

      if (last-first+1 < 3)
         return;
      middle = (first+last)/2;
      if (data[first] <= data[middle])
         if (data[middle] <= data[last])
            median = middle;
         else if (data[last] <= data[first])
            median = first;
         else
            median = last;
      else
         if (data[first] <=data[last])
            median = first;
         else if (data[last] <= data[middle])
            median = middle;
         else
            median = last;
      temp = data[first];
      data[first] = data[median];
      data[median] = temp;
   }

   private void binaryInsertionSort(int[] data,int first,int last){

      for(int i=first+1;i<=last;++i){
         int target = binarySearch(data,data[i],first,i-1);
         if (target != i){
            int temp = data[i];
            for(int j=i;j>target;--j){   
               data[j] = data[j-1]; 
            }

            data[target] = temp;
         }
      }
   }



//----------------------------------------------------------------------
//the drawing part of the sort driver
//----------------------------------------------------------------------
   class SortCanvas extends Canvas {
      // this class paints the sort bar 
      int height = pictureHeight + 20;
      SortCanvas() {
         setSize(pictureWidth, height);
         setBackground(Color.white);
      }

       
      public void paint(Graphics g) {
         
         float position = ((float)pictureWidth/ (float)limit);
      
         
         if (doPaint == PaintType.ALL) {
            // paint whole array - this takes time so it shouldn't be done too frequently
            setBackground(Color.white);
            g.setColor(Color.white);
            g.fillRect(0, 0, pictureWidth, pictureHeight);
            
            for (int i=0; i<limit; i++) {
               // the larger the number, the brighter percentage it is
               // percentage is between 0.0 and 1.0
               // divide by the largest number to get a value between 0 and 1
               float percentage = (float)(array[i]/(float)largestNum);

               // clamp if necessary - it shouldn't be
               if (percentage<0f) percentage = 0f;
               if (percentage>1f) percentage = 1f;

               g.setColor(Color.WHITE);
               g.drawLine((int)(i*pictureWidth/limit), 0, 
                          (int)(i*pictureWidth/limit), pictureHeight);
               g.setColor(colorDisplay(percentage));
               // array location 0 is painted at left; 
               //   array location limit-1 is painted to right
               //this is a single vertical line in the bar
               g.drawLine((int)(i*pictureWidth/limit), pictureHeight, 
                          (int)(i*pictureWidth/limit), pictureHeight-(int) Math.floor(percentage*pictureHeight));
            
               //triangle keeps track of the current painting position
               g.setColor(Color.black);
               int [] x = {(int) Math.floor((i)*position) -5, (int) Math.floor((i)*position) + 5, (int) Math.floor((i)*position)};
               int [] y = {pictureHeight + 15, pictureHeight + 15, pictureHeight + 5};
               Polygon triangle = new Polygon(x, y, 3);
               g.fillPolygon(triangle);
               g.setColor(Color.white);
               g.fillPolygon(triangle);
            }

         }
         
         else if (doPaint == PaintType.RANGE) {
            for (int i=leftRange; i<=rightRange; i++) {
               
               float percentage = (float)(array[i]/(float)largestNum);
               if (percentage<0f) percentage = 0f;
               if (percentage>1f) percentage = 1f;

               g.setColor(Color.WHITE);
               g.drawLine((int)(i*pictureWidth/limit), 0, 
                          (int)(i*pictureWidth/limit), pictureHeight);

               g.setColor(colorDisplay(percentage));
               g.drawLine((int)(i*pictureWidth/limit), pictureHeight, 
                          (int)(i*pictureWidth/limit), pictureHeight -(int) Math.floor(percentage*pictureHeight));

               //triangle keeps track of the current painting position
               g.setColor(Color.black);
               int [] x = {(int)Math.floor((i)*position) -5, (int) Math.floor((i)*position) + 5, (int)Math.floor((i)*position)};
               int [] y = {pictureHeight + 15, pictureHeight + 15, pictureHeight + 5};
               Polygon triangle = new Polygon(x, y, 3);
               g.fillPolygon(triangle);
               g.setColor(Color.white);
               g.fillPolygon(triangle);
            }
         }
         else {   // just paint one location on the bar
            float percentage = (float)(array[index]/(float)largestNum);
            if (percentage<0f) percentage = 0f;
            if (percentage>1f) percentage = 1f;

            g.setColor(Color.WHITE);
               g.drawLine((int)(index*pictureWidth/limit), 0, 
                          (int)(index*pictureWidth/limit), pictureHeight);

            g.setColor(colorDisplay(percentage));
            g.drawLine((int)(index*pictureWidth/limit), pictureHeight, 
                       (int)(index*pictureWidth/limit), pictureHeight-(int) Math.floor(percentage*pictureHeight));
            
            //triangle that keeps track of the current painting position
            g.setColor(Color.black);
            int [] x = {(int) Math.floor((index)*position) -5, (int)Math.floor((index)*position) + 5, (int)Math.floor((index)*position)};
            int [] y = {pictureHeight + 15, pictureHeight + 15, pictureHeight + 5};
            Polygon triangle = new Polygon(x, y, 3);
            g.fillPolygon(triangle);
            g.setColor(Color.white);
            g.fillPolygon(triangle);

         }  

 
      }
   }
}
