package kt.tippmix.service;

import kt.tippmix.model.Point;
import kt.tippmix.model.User;
import kt.tippmix.repository.PointRepository;
import org.springframework.stereotype.Service;

@Service
public class PointService {

    private final PointRepository repository;

    public PointService(PointRepository repository) {
        this.repository = repository;
    }

    public void initializePointEntry(User user) {
        Point newPointEntry = new Point();
        newPointEntry.setPlayerId(user.getId());
        Point savedPointEntry = repository.save(newPointEntry);
    }

    public void saveMatchPoint(User user, int matchPoint) {
//        repository.findById()

    }
}
