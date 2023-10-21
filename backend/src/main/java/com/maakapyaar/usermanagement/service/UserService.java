package com.maakapyaar.usermanagement.service;

import com.maakapyaar.common.constants.ClassIdConstants;
import com.maakapyaar.common.constants.Constants;
import com.maakapyaar.couchbaseRefereceListMaintainer.UserReferenceList;
import com.maakapyaar.usermanagement.model.document.User;
import com.maakapyaar.usermanagement.model.view.UserVM;
import com.maakapyaar.usermanagement.repository.UserReferenceListMaintainer;
import com.maakapyaar.usermanagement.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserReferenceListMaintainer userReferenceListMaintainer;

    public User addUser(UserVM signUpRequest) {
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        String newIdForUser = addUserToReferenceList();
        user.setId(ClassIdConstants.USER_CLASS_ID_CONSTANT + Constants.DOUBLE_COLON + newIdForUser);
        userRepository.save(user);
        return user;
    }

    private String addUserToReferenceList() {
        Optional<UserReferenceList> userReferenceList = userReferenceListMaintainer.findById("UserReferenceList");
        if (userReferenceList.isPresent()) {
            log.debug("UserReferenceList document is present, fetching latest id =>");
            UserReferenceList fetchedOptional = userReferenceList.get();
            int latestId = Integer.parseInt(fetchedOptional.getLatestCount()) + 1;
            fetchedOptional.setLatestCount(String.valueOf(latestId));
            fetchedOptional.getUserIdList().add(String.valueOf(latestId));
            userReferenceListMaintainer.save(fetchedOptional);
            log.debug("Latest id for user: {}", latestId);
            return String.valueOf(latestId);
        }
        log.debug("UserReferenceList document is not present, saving new document for UserReferenceList and " +
                "setting default values");
        List<String> newList = new ArrayList<>();
        UserReferenceList userReferenceList1 = new UserReferenceList();
        userReferenceList1.setLatestCount("1000");
        newList.add("1000");
        userReferenceList1.setUserIdList(newList);
        log.debug("final document of reference list: {} and returning default value as 1000", userReferenceList1);
        userReferenceListMaintainer.save(userReferenceList1);
        return "1000";
    }
}