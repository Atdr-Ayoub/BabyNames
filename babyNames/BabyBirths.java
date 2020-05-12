/**
 * Print out the names for which 100 or fewer babies were born in a chosen CSV file of baby name data.
 * 
 * @author Duke Software Team 
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class BabyBirths {
   public void printNames () {
	   FileResource fr = new FileResource();
	   for (CSVRecord rec : fr.getCSVParser(false)) {
		int numBorn = Integer.parseInt(rec.get(2));
		if (numBorn <= 100) {
		    System.out.println("Name " + rec.get(0) +
					   " Gender " + rec.get(1) +
	      				    " Num Born " + rec.get(2));
		}
	   }
   }
   
   public void totalBirths(FileResource fr){
      int totalGirls = 0;
      int totalBoys = 0;
      int total = 0;
      int numBorn = 0;
      for(CSVRecord rec : fr.getCSVParser(false)){
        numBorn = Integer.parseInt(rec.get(2));
        String genderSearch = rec.get(1);
        if(genderSearch.equals ("F")){
           totalGirls = totalGirls + 1 ;
        }
        else{
          totalBoys = totalBoys + 1;
        }
        
      }	
      total = totalBoys + totalGirls; 
      System.out.println("\n\nTotal boys names = " + totalBoys + "\nand total girls names = " + totalGirls + " \ntotal names = " + total );
   }
   public void testTotalBirths(){
      FileResource fr = new FileResource();
      totalBirths(fr);
   }
   
   public int getRank(int year, String name, String gender){
     String filePath = "yob" + year + ".csv" ;
     FileResource fr = new FileResource(filePath);
     int Rank = -1;
     int count = 0;
     int frequency = 0;
     for(CSVRecord rec : fr.getCSVParser(false)){
        String nameSearch = rec.get(0);
        String genderRank = rec.get(1);
        frequency = Integer.parseInt(rec.get(2));
        if(gender.equals(genderRank)){
          count++;
          if(name.equals(nameSearch)){
            Rank = count;   
            break;
          }
        }
     }
     return Rank;
   }
   public void testGetRank(){
     System.out.println("***************************************\\n");
     System.out.println("Emily = " + getRank(1960, "Emily" , "F" ));
     System.out.println("Frank = " +getRank(1971, "Frank" , "M" ));
   }
   
   public String getName(int year, int rank, String gender){
      String filePath = "yob" + year + ".csv";
      FileResource fr = new FileResource(filePath);
      int count = 0;
      String namePerson = "No Name";
      for(CSVRecord rec : fr.getCSVParser(false)){
         String genderRank = rec.get(1);
         if(gender.equals(genderRank)){
            count = count + 1;
            if(count == rank){
              namePerson = rec.get(0);
              break;
            }
         }
      }  
      return namePerson;
   }
   public void testGetName(){
      System.out.println("-------------------------------------");
      System.out.println("350 in 1980 = " + getName(1980, 350 , "F" ));
      System.out.println("450 in 1982 = " + getName(1982, 450 , "M" ));
   }
   
   public void whatIsNameInYear(String name, int year, int newYear, String gender){
      int rankName = getRank(year , name, gender );
      String newName = getName(newYear, rankName, gender);
      System.out.println(name + " borne in "+ year + " would be " + newName + " if she was borne in" + newYear);
    
   }
   public void testWhatIsNameInYear(){
      System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n Susan : ");
      whatIsNameInYear("Susan", 1972, 2014, "F");
      System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n Owen : ");
      whatIsNameInYear("Owen", 1974, 2014, "M");
   } 
   
   public int yearOfHighestRank(String name, String gender){
      int highestRank = -1;
      int currRank = -1 ;
      String yearHighestRankStr = "";
      int yearHighestRank = -1 ;
      DirectoryResource dr = new DirectoryResource();
      for (File f : dr.selectedFiles()){
         FileResource fr = new FileResource(f);
         String fileName = f.getName();
         for(CSVRecord rec : fr.getCSVParser(false)){
            String nameSearch = rec.get(0);
            String genderSearch = rec.get(1);
            if(nameSearch.equals(name)){
                if(genderSearch.equals(gender)){
                   currRank = Integer.parseInt(rec.get(2));
                   if(highestRank < currRank){ 
                       highestRank = currRank;
                       int startPos = fileName.indexOf("yob");
                       int currYear = Integer.parseInt(fileName.substring(startPos+3, startPos+7));
                       yearHighestRank = currYear;
                    }
                }
                
            }
         }
      }
      return yearHighestRank ;
   }
   public void testYearOfHighestRank(){
     int myYear = yearOfHighestRank("Genevieve", "F");
     System.out.println("The year with highest rank 1 :" + myYear);
     int myYear2 = yearOfHighestRank("Mich", "M");
     System.out.println("The year with highest rank 1 :" + myYear2);
    }
   
   public double getAverageRank(String name, String gender){
      double averageRank = 0.0;
      int totalRank = 0;
      int compt = 0;
      int currRank = 0;
      DirectoryResource dr = new DirectoryResource();
      for(File f : dr.selectedFiles()){
        FileResource fr = new FileResource(f);
        String fileName = f.getName();
        int startindex = fileName.indexOf("yob");
        int year = Integer.parseInt(fileName.substring(startindex+3, startindex+7));
        currRank = getRank(year, name, gender);
        totalRank = totalRank + currRank ;
        compt = compt + 1;
      }
      if(totalRank == 0){
          averageRank = -1.0 ;
      }
      else{
          averageRank = Double.valueOf(totalRank) / compt ;  
      }
      return averageRank;
    }
   public void testAverageRank(){
      double myAverage2 = getAverageRank("Robert", "M") ;
      System.out.println("The average rank 2 = "+ myAverage2);
   }
   
   public int getTotalBirthsRankedHigher(int year, String name, String gender){
      String filePath = "yob" + year + ".csv";
      int totalBirths = 0 ;
      int exist = 0;
      int currRank = 0;
      FileResource fr = new FileResource(filePath);
      for(CSVRecord rec : fr.getCSVParser(false)){
         String genderSearch = rec.get(1);
         String nameSearch = rec.get(0);
         if(genderSearch.equals(gender)){
           if(nameSearch.equals(name)){
             exist = 1;
             break ;
           }   
           else{
             currRank = Integer.parseInt(rec.get(2));
             totalBirths = totalBirths + currRank;
            }
         }
      }
      if(exist == 0){
         totalBirths = -1;
      }
      return totalBirths ;
    }
   public void testGetTotalBirths(){
      int myTotal1 = getTotalBirthsRankedHigher(1990, "Emily", "F");
      System.out.println("the total births 1 = "+ myTotal1);
      int myTotal2 = getTotalBirthsRankedHigher(1990, "Drew", "M");
      System.out.println("the total births 2 = "+ myTotal2);
    
    }
}