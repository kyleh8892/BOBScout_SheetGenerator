import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import com.cpjd.main.TBA;
import com.cpjd.models.teams.Team;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataCollect {

    private final static int ID_ROW = 0;
    private final static int NUMBER_COLUMN = 3;
    private final static int NAME_COLUMN = 4;

    private final static String AUTHTOKEN = "wi5cNwKSrOnIJeeePTFtd9fR82gDmRgPBZj7dQLwp169sMVnv1UYudtaLSxjxEKz";
    private final static String EVENTKEY = "2022migul";
    private final static String SCOUTINGFILEPATHNAME = "D:/2022 Robotics/Scouting Data 2022.xlsx";
    public static void main(String[] args) throws Exception {
        TBA.setAuthToken(AUTHTOKEN);
        TBA tba = new TBA();
        Team[] teamList = tba.getEventTeams(EVENTKEY);

        File scoutingFile = null;
        FileInputStream inStream = null;

        Scanner in = new Scanner(System.in);

        try{
            scoutingFile = new File(SCOUTINGFILEPATHNAME);
            inStream = new FileInputStream(scoutingFile);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } 
        
        XSSFWorkbook workbook = new XSSFWorkbook(inStream);
        
        while(true){
            System.out.print("Enter a team number:");
            int num = in.nextInt();

            try {
                XSSFSheet temp = workbook.getSheet(String.valueOf(num));
                int index = 4;
                for(int i = 1; i < 9; i++){
                    Cell cell = temp.getRow(i + 5).getCell(0);
                    if(cell.getNumericCellValue() == 0){
                        index += i;
                        temp.getRow(index).getCell(0).setCellValue(i);
                        break;
                    }
                }
                System.out.println("Enter match data:");

                //Auto
                System.out.print("Auto Low Cargo:");
                temp.getRow(index).getCell(1).setCellValue(in.nextInt());

                System.out.print("Auto High Cargo:");
                temp.getRow(index).getCell(2).setCellValue(in.nextInt());

                System.out.print("Auto Missed Cargo:");
                temp.getRow(index).getCell(3).setCellValue(in.nextInt());

                System.out.print("Auto Taxi:");
                temp.getRow(index).getCell(4).setCellValue(in.nextInt());

                //Teleop
                System.out.print("Teleop Low Cargo:");
                temp.getRow(index).getCell(6).setCellValue(in.nextInt());

                System.out.print("Teleop High Cargo:");
                temp.getRow(index).getCell(7).setCellValue(in.nextInt());

                System.out.print("Teleop Missed Cargo:");
                temp.getRow(index).getCell(8).setCellValue(in.nextInt());
            } catch (Exception e) {
                //TODO: handle exception
            }
            break; 
        }

        try {
            FileOutputStream out =
                new FileOutputStream(scoutingFile);
            workbook.write(out);
            out.close();
            workbook.close();
            System.out.println("Excel written successfully..");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
