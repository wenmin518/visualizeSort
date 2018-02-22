/**
  *  Author: Wenmin He
  *  CPE 103 - 16
  */


public class InsertionTest
{

    public static void sortLinear(Comparable [] x)
    {        
        for(int i = 1; i < x.length; i++)
        {    
            int position = linearSearch(x, i, x[i]);
            sort(x, x[i], position, i);            
        }

    }


    public static void sortBinary(Comparable [] x)
    {
        for(int i = 1; i < x.length; i++)
        {     
            int position = binarySearch(x, x[i], 0, i);
            sort(x, x[i], position, i);           
        }    
    }


    public static int linearSearch(Comparable[] x, int end, Comparable key)
    { 
        //average case
        for(int i= end-1; i >= 0; i--)
        {
            if(x[i].compareTo(key) <= 0)
            {
                //find smaller comparable object and replace it with the next item
                return i+1;
            }

	    //base case when key is smaller than all the compraable object in the list
            if( i == 0 && x[i].compareTo(key)  ==1)
            {
                return 0;
            }
        }
     
        //base case when all the object in the array is greater than the key;        
        return end;       
    }
    

    public static void sort(Comparable[] list, Comparable element, int position, int limit)
    {
        for(int i = limit; i >= position; i--)
        {
            if(i > 0 && i < list.length)
            {
                list[i] = list[i-1];
            }
        }

        list[position] = element;
    }
    
    public static int binarySearch(Comparable[] x, Comparable key, int begin, int end)
    {

        //base case when all the comparable object in array is greater
        if(x[begin].compareTo(key)  == 1)
        {
            return 0;
        }
        //average cases
        while(begin < end)
        {
            int mid = (begin + end) /2;
            if(x[mid].compareTo(key)<=0 && x[mid + 1].compareTo(key) >= 0)
            {
                //replace the bigger number, not the smaller number
                return mid+1;
            }
            else if(x[mid].compareTo(key) ==1)
            {   
                end = mid - 1;
            }
            else if(x[mid].compareTo(key) == -1)
            {   
                begin = mid + 1; 
            }
        }
        //base case when key is greater than all comparable objects in the list
        return end+1;
    }  
    
}

