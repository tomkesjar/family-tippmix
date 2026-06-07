package kt.tippmix.service;

import jakarta.mail.internet.MimeMessage;
import kt.tippmix.model.DetailedDataRow;
import kt.tippmix.repository.BetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class DataService {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final String CSV_HEADER =
            "userId,userName,other,secretName,favouriteNation,goalScorerNationality,mostGoals," +
            "gameId,gameDate,homeTeam,awayTeam,gameHomeGoals,gameAwayGoals,gameWinner," +
            "betId,betHomeGoals,betAwayGoals,betWinner,betPoint,betExact," +
            "matchPoint,winnerPoint,goalPoint,mostGoalPoint,bonusPoint,totalPoint";

    private final BetRepository betRepository;

    /** Optional — null when spring.mail.host is not configured. */
    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Value("${TARGET_EMAIL_ADDRESS:}")
    private String targetEmail;

    public DataService(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    // ── Public entry points ─────────────────────────────────────────────────────

    public List<DetailedDataRow> getDetailedData() {
        return betRepository.findDetailedData();
    }

    /**
     * Fetches a fresh snapshot of all data, generates a CSV and sends it
     * to TARGET_EMAIL_ADDRESS as an attachment.
     *
     * @throws RuntimeException if CSV generation or email sending fails.
     */
    public void sendEmailWithData() {
        List<DetailedDataRow> data = betRepository.findDetailedData();
        Path csvPath = saveToCSV(data);
        if (csvPath == null) {
            throw new RuntimeException("CSV generálás sikertelen.");
        }
        sendEmail(csvPath);
    }

    // ── CSV ─────────────────────────────────────────────────────────────────────

    private Path saveToCSV(List<DetailedDataRow> data) {
        try {
            StringBuilder sb = new StringBuilder(CSV_HEADER).append("\n");
            for (DetailedDataRow r : data) {
                sb.append(r.userId()).append(",")
                  .append(safe(r.userName())).append(",")
                  .append(safe(r.other())).append(",")
                  .append(safe(r.secretName())).append(",")
                  .append(safe(r.favouriteNation())).append(",")
                  .append(safe(r.goalScorerNationality())).append(",")
                  .append(safe(r.mostGoals())).append(",")
                  .append(r.gameId()).append(",")
                  .append(r.gameDate() != null ? r.gameDate().format(DATE_FMT) : "").append(",")
                  .append(safe(r.homeTeam())).append(",")
                  .append(safe(r.awayTeam())).append(",")
                  .append(safe(r.gameHomeGoals())).append(",")
                  .append(safe(r.gameAwayGoals())).append(",")
                  .append(safe(r.gameWinner())).append(",")
                  .append(r.betId()).append(",")
                  .append(safe(r.betHomeGoals())).append(",")
                  .append(safe(r.betAwayGoals())).append(",")
                  .append(safe(r.betWinner())).append(",")
                  .append(safe(r.betPoint())).append(",")
                  .append(r.betExact() != null ? r.betExact() : "").append(",")
                  .append(safe(r.matchPoint())).append(",")
                  .append(safe(r.winnerPoint())).append(",")
                  .append(safe(r.goalPoint())).append(",")
                  .append(safe(r.mostGoalPoint())).append(",")
                  .append(safe(r.bonusPoint())).append(",")
                  .append(safe(r.totalPoint())).append("\n");
            }
            Path path = Files.createTempFile("tippmix_data_", ".csv");
            Files.writeString(path, sb.toString(), StandardCharsets.UTF_8);
            return path;
        } catch (IOException e) {
            System.err.println("[DataService] CSV save failed: " + e.getMessage());
            return null;
        }
    }

    // ── Email ────────────────────────────────────────────────────────────────────

    private void sendEmail(Path csvPath) {
        if (mailSender == null) {
            throw new RuntimeException("Levelező szerver nincs beállítva (spring.mail.host).");
        }
        if (targetEmail == null || targetEmail.isBlank()) {
            throw new RuntimeException("TARGET_EMAIL_ADDRESS nincs megadva az application.properties fájlban.");
        }
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(targetEmail);
            helper.setSubject("Tippmix adatok export – " + LocalDateTime.now().format(DATE_FMT));
            helper.setText("Csatolva az összes adat CSV formátumban.");
            helper.addAttachment("tippmix_data.csv", new File(csvPath.toString()));
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Email küldés sikertelen: " + e.getMessage(), e);
        }
    }

    // ── Helpers ──────────────────────────────────────────────────────────────────

    private static String safe(Object value) {
        return value == null ? "" : value.toString();
    }
}
