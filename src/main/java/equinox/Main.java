package equinox;

import entities.GNP;
import com.jcraft.jsch.*;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repositories.GNPRepository;
import repositories.NetworkRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        String path = getDiffFile();
        diffToDatabase(path);
    }

    private static String getDiffFile() {
        //Connection parameters
        String localPath = "/var/TMT/";
        String fileName = "eGNP_TMT_DO_diff_";
        String sftpPath = "";
        String sftpHost = "dls-de01.tmtanalysis.com";
        int sftpPort = 2222;
        String sftpUser = "equinox";
        String sftpPassword = "j2su46J@7FpLhbT";

        //Folder name
        LocalDate today = LocalDate.now();
        today = today.minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dateFormatted = today.format(formatter);

        //Logging messages
//        Map<Integer, String> logMap = new HashMap<>();
//        logMap.put(1, "Connecting...");
//        logMap.put(2, "Established session");
//        logMap.put(3, "Opened folder ");
//        logMap.put(4, "The specified directory could not be found. Directory: ");
//        logMap.put(5, "Unable to find any directory or file");
//        logMap.put(6, "No directories found in the home directory");
//        logMap.put(7, "Channel connected");

        try {
            JSch jsch = new JSch();
            com.jcraft.jsch.Session session = jsch.getSession(sftpUser, sftpHost, sftpPort);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(sftpPassword);
//            TMTLogger.logger.info(logMap.get(1));
            logger.info("Connecting...");
            //System.out.println(logMap.get(1));
            //TODO: handle connect error
            session.connect();
            logger.info("Established session");

            Channel channel = session.openChannel("sftp");
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            sftpChannel.connect();
            logger.info("Channel connected");

            boolean fileFlag = false;
            @SuppressWarnings("unchecked")
            Vector<ChannelSftp.LsEntry> folderList = sftpChannel.ls("*");
            if (folderList != null) {
                for (int i = folderList.size() - 1; i >= 0; i--) {
                    if (folderList.get(i).getAttrs().isDir() && dateFormatted.equals(folderList.get(i).getFilename())) {
                        Vector<ChannelSftp.LsEntry> fileList = sftpChannel.ls(folderList.get(i).getFilename() + "/*");
                        logger.info("Directory " + sftpPath + " opened");
                        for (ChannelSftp.LsEntry entryFiles : fileList) {
                            logger.info("Found file: " + entryFiles.getFilename());
                        }
                        sftpPath = folderList.get(i).getFilename();
                        fileName += sftpPath + ".csv";
                        //localPath += "\\" + entry.getFilename() + "\\";
                        fileFlag = true;
                        break;
                    }
                }
            } else {
                logger.error("Unable to find any directory or file");
            }
            if (!fileFlag) {
                logger.error("No directories found in the home directory");
                sftpChannel.disconnect();
                logger.info("Closed sftp channel");
                session.disconnect();
                logger.info("Closed session");
                System.exit(0);
            }

            logger.info("Opened sftp channel");

            logger.info("Copying file to local");

            try {
                sftpChannel.get(sftpPath + "/*", localPath);
                logger.info("Completed");
            } catch (SftpException ex) {
                if (ex.id == ChannelSftp.SSH_FX_NO_SUCH_FILE) {
                    logger.error("The specified directory could not be found. Directory: " + sftpPath);
                } else {
                    logger.error("SFTP Error: " + ex.getMessage());
                }
            }
            sftpChannel.disconnect();
            logger.info("Closed sftp channel");
            session.disconnect();
            logger.info("Closed session");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return fileName;
    }

    public static void diffToDatabase(String path) {
        CSVReader reader = new CSVReader();
        Session session = DataAccess.get().openSession();
        GNPRepository repository = new GNPRepository(session);
        List<GNP> GNPdiff = reader.ReadDiffFile(path);
        List<GNP> GNPtoDigitalk = new ArrayList<>();
        for (GNP gnp : GNPdiff) {
            if (gnp.getAction().equals("D")) {
                GNP existingEntity = session
                        .createQuery("FROM GNP WHERE start = :start AND stop = :stop", GNP.class)
                        .setParameter("start", gnp.getStart())
                        .setParameter("stop", gnp.getStop())
                        .uniqueResult();
                if (existingEntity != null) {
                    existingEntity.setExpirationDate(LocalDateTime.now());
                    repository.update(existingEntity);
                }
            }
            if (gnp.getAction().equals("A")) {
                GNPtoDigitalk.add(gnp);
                repository.save(gnp);
            }
        }
        CSVGenerator generator = new CSVGenerator();
        NetworkRepository networkRepository = new NetworkRepository(session);
        for (int i = 0; i < GNPtoDigitalk.size(); i++) {
            GNPtoDigitalk.get(i).setNetworkEntity(networkRepository.findById(GNPtoDigitalk.get(i).getNetworkId()));
        }
        generator.generateCSV(GNPtoDigitalk);
        session.close();
        System.out.printf("Session closed");
    }

}