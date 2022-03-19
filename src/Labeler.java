import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.cpjd.main.TBA;
import com.cpjd.models.teams.Team;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Labeler {

    private final static int ID_ROW = 1;
    private final static int NUMBER_COLUMN = 2;
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
        try{
            scoutingFile = new File(SCOUTINGFILEPATHNAME);
            inStream = new FileInputStream(scoutingFile);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } 
        
        XSSFWorkbook workbook = new XSSFWorkbook(inStream);

        for(Team team : teamList){
            //System.out.println(team.getTeamNumber() + "  " + team.getNickname());
            XSSFSheet temp = workbook.cloneSheet(1);

            Row row = temp.getRow(ID_ROW);
            Cell number = row.getCell(NUMBER_COLUMN);
            Cell name = row.getCell(NAME_COLUMN);
            
            number.setCellValue(team.getTeamNumber());
            name.setCellValue(team.getNickname());
            
            System.out.println(team.getTeamNumber());
            System.out.println(team.getNickname());

            workbook.setSheetName(workbook.getSheetIndex(temp), String.valueOf(team.getTeamNumber()));
        } 

        try {
            FileOutputStream out = new FileOutputStream(scoutingFile);
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
