package equinox;

import entities.GNP;
import entities.GNPdiff;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class CSVReader {

    public List<GNP> ReadFullFile(String path){
        String line = "";
        List<GNP> gnp = new ArrayList<>();
        path = "C:\\Users\\Lucas\\Documents\\SFTP TMT\\eGNP_TMT_DO_full_20231114.csv";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String[] values;
            boolean headerFlag = true;
            while((line = br.readLine()) != null){
                if(headerFlag){
                    headerFlag = false;
                }else{
                    GNP gnpAux = new GNP();
                    line = line.replace("\"", "");
                    values = line.split("\\|");
                    gnpAux.setStart(values[1]);
                    gnpAux.setStop(values[2]);
                    gnpAux.setNetworkId(values[5]);
                    gnpAux.setMcc(values[6]);
                    gnpAux.setMnc(values[7]);
                    try {
                        gnpAux.setEType(Byte.parseByte(values[10]));
                    }catch(NumberFormatException ex){
                        byte eType = -1;
                        gnpAux.setEType(eType);
                    }
                    gnpAux.setInsertionTimestamp(LocalDateTime.now());
                    gnp.add(gnpAux);
                }
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return gnp;
    }
    public List<GNP> ReadDiffFile(String path){
        String line = "";
        List<GNP> gnp = new ArrayList<>();
        path = "/var/TMT/" + path;
        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            String[] values;
            boolean headerFlag = true;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            while((line = br.readLine()) != null){
                if(headerFlag){
                    headerFlag = false;
                }else{
                    GNP gnpAux = new GNP();
                    line = line.replace("\"", "");
                    values = line.split("\\|");
                    gnpAux.setAction(values[1]);
                    gnpAux.setStart(values[3]);
                    gnpAux.setStop(values[4]);
                    gnpAux.setNetworkId(values[7]);
                    try {
                        gnpAux.setEType(Byte.parseByte(values[12]));
                    }catch(NumberFormatException ex){
                        byte eType = -1;
                        gnpAux.setEType(eType);
                    }
                    String dateString = values[0] + "T00:00:00";
                    LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);
                    gnpAux.setInsertionTimestamp(localDateTime);
                    gnp.add(gnpAux);
                }
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return gnp;
    }
}
