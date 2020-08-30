import java.io.*;
import java.sql.*;

public class SimpleCsv2DbInserter {

    public static void main(String[] args) {
        String jdbcURL = "jdbc:postgresql://localhost:5432/";
        String username = "postgres";
        String password = "postgres";

        String csvFilePath = "temp.csv";

        int batchSize = 100;

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);

            String sql = "INSERT INTO Employee " +
                    "(Emp_ID,First_Name,Middle_Name,Last_Name,Date_of_Birth,Age_of_Emp,Start_Date,End_Date,Salary,Email,Phone_Number,Address,Description,Country) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText ;

            int count = 0;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");
                String Emp_Id = data[0];
                String First_Name = data[1];
                String Middle_Name  = data[2];
                String Last_Name  = data[3];
                String Age_of_Emp = data[4];
                String Salary = data[5];
                String Email = data[6];
                String Phone_Number = data[7];
                String Address = data[8];
                String Description = data[9];
                String Country = data.length == 11 ? data[10] : "";

                int empId = Integer.parseInt(Emp_Id);
                statement.setInt(1, empId);

                statement.setString(2,First_Name );
                statement.setString(3, Middle_Name);
                statement.setString(4, Last_Name);

                int sqlAgeOfEmp = Integer.parseInt(Age_of_Emp);
                statement.setInt(6, sqlAgeOfEmp);

                float sqlSalary = Float.parseFloat(Salary);
                statement.setFloat(9, sqlSalary);

                statement.setString(10, Email);

                int sqlPhoneNumber = Integer.parseInt(Phone_Number);
                statement.setInt(11, sqlPhoneNumber);

                statement.setString(12, Address);
                statement.setString(13, Description);
                statement.setString(14, Country);

                /*statement.setString(1,Emp_Id);
                statement.setString(2,First_Name);
                statement.setString(3,Middle_Name);
                statement.setString(4,Last_Name);
                statement.setString(5,Date_of_Birth);
                statement.setString(6,Age_of_Emp);
                statement.setString(7,Start_Date);
                statement.setString(8,End_Date);
                statement.setString(9,Salary);
                statement.setString(10,Email);
                statement.setString(11,Phone_Number);
                statement.setString(12,Address);
                statement.setString(13,Description);
                statement.setString(14,Country);
*/
                statement.addBatch();

                if (count % batchSize == 0) {
                    statement.executeLargeBatch();
                }
            }

            lineReader.close();

            // execute the remaining queries
            statement.executeBatch();

            connection.commit();
            connection.close();

        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            catch (NullPointerException e){
                System.out.println("RollBack Null");
            }
        }

    }
}
