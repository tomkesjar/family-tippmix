package kt.tippmix.service;

import kt.tippmix.model.Tip;
import kt.tippmix.repository.TipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TipService {

    private TipRepository tipRepository;

    public TipService(@Autowired TipRepository tipRepository) {
        this.tipRepository = tipRepository;
    }

    public List<Tip> getAll() {
        List<Tip> tipList = (List) tipRepository.findAll();
        return tipList;
    }

    public List<Tip> getByDate(String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date);
        return tipRepository.findAllAfterDate(dateTime);
    }

    public List<Tip> getByUser(String user) {
        return tipRepository.findAllByUser(user);
    }

    public Tip save(Tip newTip) {
        return tipRepository.save(newTip);
    }
}
