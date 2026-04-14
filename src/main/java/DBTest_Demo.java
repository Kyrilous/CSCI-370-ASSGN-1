import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBTest_Demo {

    public DBTest_Demo (){

    }

    public int testconnection_mysql(int hr_offset) {
//        String connection_host = "18.117.150.218";
        String connection_host = "127.0.0.1";
        Connection connect = null;
        PreparedStatement preparedStatement = null;
        int flag = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

//            connect = DriverManager.getConnection(
//                    "jdbc:mysql://" + connection_host + ":3306/mydatabase?user=student&password=student123&connectTimeout=5000"
//            );
            connect = DriverManager.getConnection(
                    "jdbc:mysql://" + connection_host + ":3307/mydatabase?user=student&password=student123&connectTimeout=5000"
            );

            String qry1a = "SELECT CURDATE() + " + hr_offset;
            preparedStatement = connect.prepareStatement(qry1a);

            ResultSet r1 = preparedStatement.executeQuery();

            if (r1.next()) {
                String nt = r1.getString(1);
                System.out.println(hr_offset + " hour(s) ahead of the system clock of mysql at " + connection_host + " is:" + nt);
            }

            r1.close();
            preparedStatement.close();

        } catch (Exception e) {
            flag = -1;
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connect != null) {
                try {
                    connect.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

//    public int testConnection (int hr_offset) {
//        String oracle_driver = "oracle.jdbc.driver.OracleDriver";
//        //String dbURL1 = "jdbc:oracle:thin:@149.4.211.180:1521:venus";
//        String dbURL1 = "jdbc:oracle:thin:@oodb.cs.qc.cuny.edu:1521:oodb";
//        String userName1 = "SE"; //"ec";
//        String userPassword1 = "SE2020"; //"cs370cs381";
//
//        Connection conn;
//        PreparedStatement pstmt;
//        ResultSet rs;
//
//        int flag = 0;
//        String newTime;
//
//        try
//        {
//            Class.forName(oracle_driver);
//        }
//        catch (Exception e)
//        {
//            System.out.println(e.getMessage());
//        }
//
//        try
//        {
//            conn = DriverManager.getConnection(dbURL1, userName1, userPassword1);
//            String stmtQuery = "select sysdate + " + (float) hr_offset/24 + " from dual";
//            pstmt = conn.prepareStatement(stmtQuery);
//            rs = pstmt.executeQuery();
//            if (rs.next())
//            {
//                newTime = rs.getString(1);
//                System.out.println(hr_offset + " hour(s) ahead of the system clock of Oracle is:" + newTime);
//            }
//            rs.close();
//            pstmt.close();
//
//            try
//            {
//                conn.close();
//            }
//            catch (SQLException e) {};
//
//        }
//        catch (SQLException e)
//        {
//            System.out.println(e.getMessage());
//            flag = -1;
//        }
//
//        return flag;
//    }


    public static void main(String[] args)
    {
        try
        {
            if (args.length != 1) {
                System.out.println("Usage: java -jar NySQL_DBTest.jar <number_of_hr_offset>");
                System.out.println("Success returns errorlevel 0. Error return greater than zero.");
                System.exit(1);
            }

            /* Print a copyright. */
            System.out.println("Example for MySQL DB connection via Java");
            System.out.println("Copyright: Bon Sy");
            System.out.println("Free to use this at your own risk!");

            DBTest_Demo DBConnect_instance = new DBTest_Demo();

            if (DBConnect_instance.testconnection_mysql(Integer.parseInt(args[0])) == 0) {
                System.out.println("MYSQL Remote Connection Successful Completion");
            } else {
                System.out.println("mysql DB connection fail");
            }

        }
        catch (Exception e){
            // probably error in input
            System.out.println("Hmmm... Looks like input error ....");
        }
    }


}

