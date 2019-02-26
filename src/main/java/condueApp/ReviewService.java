package condueApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("reviewService")
public class ReviewService {

    private ReviewRepository revuewRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.reviewRepository = reviewRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByUsername(String username) {
        return reviewRepository.findByUsername(username);
    }

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }
}