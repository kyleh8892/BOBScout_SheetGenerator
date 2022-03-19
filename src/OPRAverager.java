
import java.util.ArrayList;
import java.util.Collections;

import com.cpjd.main.TBA;
import com.cpjd.models.events.Event;
import com.cpjd.models.events.EventOPR;
import com.cpjd.models.teams.Team;


public class OPRAverager {

    private final static String AUTHTOKEN = "wi5cNwKSrOnIJeeePTFtd9fR82gDmRgPBZj7dQLwp169sMVnv1UYudtaLSxjxEKz";
    private final static String EVENTKEY = "2022miwmi";
    public static void main(String[] args) throws Exception {
        TBA.setAuthToken(AUTHTOKEN);
        TBA tba = new TBA();
        Team[] teamList = tba.getEventTeams(EVENTKEY);
        
        ArrayList<MyTeam> teams = new ArrayList<>();
        
        
        for(Team team : teamList){
            try{
                Event[] events = tba.getTeamEvents((int)team.getTeamNumber());
                for(Event event : events){
                    if(event.getYear() == 2022){
                        try{
                            EventOPR[] OPRs = tba.getOprs(event.getKey());
                            for (EventOPR opr : OPRs) {
                                if(opr.getTeamKey().equals(team.getKey())){
                                    teams.add(new MyTeam((int)team.getTeamNumber(), opr.getOpr()));
                                }   
                            }
                        }catch(NullPointerException e){
    
                        }
                        
                    }
                }
                //System.out.println(team.getTeamNumber() + "  " + avg);
                
            }catch(Exception e){
            }
        }

        Collections.sort(teams);

        System.out.println("\n\n\n\n");

        for(MyTeam team : teams){System.out.println(team.getNumber() + "  " + team.getOpr());}
    }
}

class MyTeam implements Comparable<MyTeam>{

    public int number;
    public double opr;

    public MyTeam(int number, double opr){
        this.number = number;
        this.opr = opr;
    }

    public int getNumber() {
        return number;
    }

    public double getOpr() {
        return opr;
    }

    @Override
    public int compareTo(MyTeam anotherInstance) {
        if(anotherInstance.getOpr() == this.getOpr()){
            return 0;
        }else{
            return (anotherInstance.getOpr() > this.getOpr()) ? 1 : -1;
        }
    }
}
