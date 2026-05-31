package kt.tippmix.service;

import kt.tippmix.model.Bet;
import kt.tippmix.model.BetHistoryRow;
import kt.tippmix.model.Tip;
import kt.tippmix.repository.BetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BetService {

    private BetRepository betRepository;

    public BetService(@Autowired BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    public List<Bet> getAll() {
        List<Bet> tipList = (List) betRepository.findAll();
        return tipList;
    }

    public List<Bet> getAllByPlayerId(Long playerId) {
        return betRepository.findAllByUser(playerId);
    }

    public List<BetHistoryRow> getHistoryByPlayerId(Long playerId) {
        return betRepository.findHistoryByPlayerId(playerId);
    }

//    public List<Tip> getByDate(String date) {
//        LocalDateTime dateTime = LocalDateTime.parse(date);
//        return tipRepository.findAllAfterDate(dateTime);
//    }
//
//    public List<Tip> getByUser(String user) {
//        return tipRepository.findAllByUser(user);
//    }

    public Bet save(Bet newBet) {
        return betRepository.save(newBet);
    }

    public void clearPreviousBets(Bet newBet) {
        betRepository.deletePreviousBets(newBet.getPlayerId(), newBet.getMatchId());
    }
}
