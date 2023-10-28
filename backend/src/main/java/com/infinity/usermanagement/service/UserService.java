package com.infinity.usermanagement.service;

import com.infinity.common.constants.Constants;
import com.infinity.common.exceptionHandling.CustomResponseException;
import com.infinity.common.exceptionHandling.ErrorCodeEnum;
import com.infinity.common.utils.JwtTokenProvider;
import com.infinity.couchbaseRefereceListMaintainer.UserReferenceList;
import com.infinity.usermanagement.config.UserConfigs;
import com.infinity.usermanagement.model.document.User;
import com.infinity.usermanagement.model.view.JWTToken;
import com.infinity.usermanagement.model.view.UserVM;
import com.infinity.usermanagement.repository.UserReferenceListMaintainer;
import com.infinity.usermanagement.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserConfigs userConfigs;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /***
     * This method used to create user
     *
     * @param signUpRequest
     * @return
     */
    public User addUser(UserVM signUpRequest) {
        if (StringUtils.isBlank(signUpRequest.getEmail()) || StringUtils.isBlank(signUpRequest.getPassword())) {
            log.error("Either email id or password is blank");
            throw new CustomResponseException(request, ErrorCodeEnum.ER1002, HttpStatus.BAD_REQUEST);
        }
        Optional<User> alreadyHaveAUser = userRepository.findByEmail(signUpRequest.getEmail());
        if (alreadyHaveAUser.isPresent()) {
            log.error("User is already present with same email id: {}", signUpRequest.getEmail());
            throw new CustomResponseException(request, ErrorCodeEnum.ER1001, HttpStatus.CONFLICT);
        }
        User user = new User();
        try {
            user.setEmail(signUpRequest.getEmail());
            user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            String newIdForUser = addUserToReferenceList();
            user.setId(userConfigs.getClassIdValue() + Constants.DOUBLE_COLON + newIdForUser);
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Unable to create a user. Reason: {}", e.getMessage());
            throw new CustomResponseException(request, ErrorCodeEnum.ER1000, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return user;
    }

    public JWTToken getUserDetails(UserVM signInRequest) {
        Optional<User> fetchedUser = Optional.empty();
        if (StringUtils.isBlank(signInRequest.getEmail()) || StringUtils.isBlank(signInRequest.getPassword())) {
            log.error("Either email id or password is blank");
            throw new CustomResponseException(request, ErrorCodeEnum.ER1002, HttpStatus.BAD_REQUEST);
        }
        fetchedUser = userRepository.findByEmail(signInRequest.getEmail());
        if(fetchedUser.isEmpty()){
            log.error("User not found with email: {}", signInRequest.getEmail());
            throw new CustomResponseException(request, ErrorCodeEnum.ER1003, HttpStatus.UNAUTHORIZED);
        }
        try {
            log.debug("Validated user name successfully");
            passwordVerification(signInRequest.getPassword(), fetchedUser.get());
        } catch (Exception e){
            log.error("Exception occurred while fetching user, Reason: {}", e.getMessage());
            throw new CustomResponseException(request, ErrorCodeEnum.ER1004, HttpStatus.UNAUTHORIZED);
        }
        JWTToken newJwtToken = new JWTToken();
        newJwtToken.setIdToken(jwtTokenProvider.createToken(fetchedUser.get()));
        return newJwtToken;
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
        userReferenceList1.setLatestCount(userConfigs.getInitialValue());
        newList.add(userConfigs.getInitialValue());
        userReferenceList1.setUserIdList(newList);
        log.debug("final document of reference list: {} and returning default value as 1000", userReferenceList1);
        userReferenceListMaintainer.save(userReferenceList1);
        return userConfigs.getInitialValue();
    }

    public void passwordVerification(String password, User user) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.debug("Incorrect password found");
            throw new CustomResponseException(request, ErrorCodeEnum.ER1004, HttpStatus.UNAUTHORIZED);
        }
    }

}