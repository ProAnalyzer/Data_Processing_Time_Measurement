import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;

public class CreateCSV {

    public static void main(String[] args) throws IOException {
        CSVWriter csvWriter = new CSVWriter(new FileWriter("Employee100001.csv"));
        String[] header = { "Emp_ID", "First_Name", "Middle_Name", "Last_Name", "Date_of_Birth",
                "Age_of_Emp", "Start_Date", "End_Date", "Salary", "Email", "Phone_Number",
                "Address", "Description", "Country"};
        csvWriter.writeNext(header);
        for(int i =0; i<10000; i++)
            csvWriter.writeNext(new String[]{ "null", "Pronay", "Kumar", "Ghosh", "16-04-1998",
                    "22", "16-03-2020", "null", "0000.00", "kumarpronayghosh@gmail.com", "6291081728",
                    "Baranagar", "Engineer", "India"});

        csvWriter.close();
    }
}

/*Your CSV file is in your project root path*/

