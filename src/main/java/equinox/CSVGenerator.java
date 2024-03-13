package equinox;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import repositories.*;
import entities.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class CSVGenerator {

    private GNPRepository gnpRepository;

    private NetworkRepository networkRepository;

    public void generateCSV(List<GNP> GNPtoDigitalk) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String filePath = "/var/TMT/import_digitalk_" + today.format(formatter) + ".csv";
        try (FileWriter writer = new FileWriter(filePath)) {
            System.out.println("archivo creado con exito");
            writer.write("Code,Zone\n");
            List<String> codesResult = new ArrayList<>();
            List<String> networkResult = new ArrayList<>();
            long start;
            long stop;
            for (GNP gnp : GNPtoDigitalk) {
                start = Long.parseLong(gnp.getStart());
                stop = Long.parseLong(gnp.getStart());
                if (stop > start){
                    for (long i = start; i <= stop ; i++){
                        codesResult.add(Long.toString(i));
                        Network network = gnp.getNetworkEntity();
                        String digitalkName = (network != null) ? network.getDigitalkName() : "";
                        networkResult.add(digitalkName);
                    }
                }
                else {

                    Network network = gnp.getNetworkEntity();
                    String digitalkName = (network != null) ? network.getDigitalkName() : "";
                    networkResult.add(digitalkName);
                    codesResult.add(Long.toString(start));

                }
            }
            for (int i = 0; i < codesResult.size(); i++){
                writer.write(codesResult.get(i) + "," + networkResult.get(i) + "\n");
            }
            System.out.println("Escritura de archivo finalizada");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateCodes(String start, String stop) {
        return start + "-" + stop;
    }
}