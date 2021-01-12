package com.lavgorush.graduation_project.voting.web.user;

import com.lavgorush.graduation_project.voting.HasId;
import com.lavgorush.graduation_project.voting.model.User;
import com.lavgorush.graduation_project.voting.repository.UserRepository;
import com.lavgorush.graduation_project.voting.repository.VoteRepository;
import com.lavgorush.graduation_project.voting.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import static com.lavgorush.graduation_project.voting.util.ValidationUtil.assureIdConsistent;
import static com.lavgorush.graduation_project.voting.util.ValidationUtil.checkSingleModification;

@Slf4j
public abstract class AbstractUserController {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected VoteRepository voteRepository;

    @Autowired
    private UniqueMailValidator emailValidator;

    @Autowired
    private LocalValidatorFactoryBean defaultValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator);
    }

    public ResponseEntity<User> get(int id) {
        log.info("get {}", id);
        return ResponseEntity.of(userRepository.findById(id));
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) {
        log.info("delete {}", id);
        checkSingleModification(userRepository.delete(id), "User id=" + id + " not found");
    }

    public ResponseEntity<User> getWithVotes(int id) {
        log.info("getWithVotes {}", id);
        return ResponseEntity.of(userRepository.getWithVotes(id));
    }

    protected User prepareAndSave(User user) {
        return userRepository.save(UserUtil.prepareToSave(user));
    }

    protected void validateBeforeUpdate(HasId user, int id) throws BindException {
        assureIdConsistent(user, id);
        DataBinder binder = new DataBinder(user);
        binder.addValidators(emailValidator, defaultValidator);
        binder.validate();
        if (binder.getBindingResult().hasErrors()) {
            throw new BindException(binder.getBindingResult());
        }
    }

}
